package org.osj.fishingAdventure.FISHING_SYSTEM;

import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.osj.fishingAdventure.CUSTOMITEMS.CustomItemManager;
import org.osj.fishingAdventure.CUSTOMITEMS.LoreColorManager;
import org.osj.fishingAdventure.DATA_MANAGEMENT.ConfigManager;
import org.osj.fishingAdventure.FishingAdventure;
import org.osj.fishingAdventure.MESSAGE.MessageManager;
import org.osj.fishingAdventure.WORLD.EVENT.PlayerStructureMode;
import org.osj.fishingAdventure.WORLD.WorldManager;

import java.time.Duration;
import java.util.*;

public class FishingManager implements Listener
{
    public static FileConfiguration fishingLevelConfig = FishingAdventure.getConfigManager().getConfig("fishinglevel");
    public static FileConfiguration fishingPointConfig = FishingAdventure.getConfigManager().getConfig("fishingpoint");
    public static FileConfiguration encyclopediastackConfig = FishingAdventure.getConfigManager().getConfig("encyclopediastack");

    public static HashMap<UUID, Boolean> onMiniGameMap = new HashMap<>();
    public static HashMap<UUID, Set<Double>> fishingHookYawPitchMap = new HashMap<>();
    public static HashMap<UUID, String> fishingDirectionMap = new HashMap<>();
    public static HashMap<UUID, Integer> fishingTimerMap = new HashMap<>();
    public static HashMap<UUID, Double> fishingProgressMap = new HashMap<>();

    public static HashMap<UUID, Integer> playerFishingLevelMap = new HashMap<>();
    public static HashMap<UUID, Integer> playerFishingExpMap = new HashMap<>();
    public static HashMap<UUID, Integer> playerFishingPointMap = new HashMap<>();

    static double defaultTimeLimit = 20 * 10;

    public FishingManager()
    {
        if(fishingLevelConfig.getConfigurationSection("players.lv.") != null)
        {
            List<String> configUUIDKeyList = fishingLevelConfig.getConfigurationSection("players.lv.").getKeys(true).stream().toList();
            configUUIDKeyList.forEach((key) -> {
                playerFishingLevelMap.put(UUID.fromString(key), fishingLevelConfig.getInt("players.lv." + key));
            });
        }
        if(fishingLevelConfig.getConfigurationSection("players.exp.") != null)
        {
            List<String> configUUIDKeyList = fishingLevelConfig.getConfigurationSection("players.exp.").getKeys(true).stream().toList();
            configUUIDKeyList.forEach((key) -> {
                playerFishingExpMap.put(UUID.fromString(key), fishingLevelConfig.getInt("players.exp." + key));
            });
        }
        if(fishingPointConfig.getConfigurationSection("players.") != null)
        {
            List<String> configUUIDKeyList = fishingPointConfig.getConfigurationSection("players.").getKeys(true).stream().toList();
            configUUIDKeyList.forEach((key) -> {
                playerFishingPointMap.put(UUID.fromString(key), fishingPointConfig.getInt("players." + key));
            });
        }
    }

    @EventHandler
    public void onPlayerEnterServer(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if(!FishingManager.playerFishingLevelMap.containsKey(uuid))
        {
            playerFishingLevelMap.put(uuid, 0);
            fishingLevelConfig.set("players.lv." + uuid, playerFishingLevelMap.get(uuid));
            FishingAdventure.getConfigManager().saveConfig("fishinglevel");
        }
        if(!FishingManager.playerFishingExpMap.containsKey(uuid))
        {
            playerFishingExpMap.put(uuid, 0);
            fishingLevelConfig.set("players.exp." + uuid, playerFishingExpMap.get(uuid));
            FishingAdventure.getConfigManager().saveConfig("fishinglevel");
        }
        if(!FishingManager.playerFishingPointMap.containsKey(uuid))
        {
            playerFishingPointMap.put(uuid, 0);
            fishingPointConfig.set("players." + uuid, playerFishingPointMap.get(uuid));
            FishingAdventure.getConfigManager().saveConfig("fishingpoint");
        }

        Component listNameComponent = Component.empty()
                .append(Component.text("[lv." + playerFishingLevelMap.get(uuid) + "]").color(TextColor.color(0, 223, 255)))
                .append(Component.text(player.getName()));
        player.playerListName(listNameComponent);
    }

    public void startMiniGame(Player player, CustomStack caughtFish, Integer stringLv, Integer fishDirChangeChance, Integer reelLevel, Integer fishStrength, PlayerFishEvent event)
    {
        UUID uuid = player.getUniqueId();
        onMiniGameMap.put(uuid, true);
        fishingTimerMap.put(uuid, 0);
        fishingProgressMap.put(uuid, 0.0);

        fishingDirectionMap.put(uuid, "↓");

        double timeLimit = Math.round(defaultTimeLimit + stringLv) * 10 / 10.0;

        int grade = getFishGrade(caughtFish.getPermission());

        BukkitScheduler minigameDirectionScheduler = Bukkit.getScheduler();
        BukkitTask minigameDirectionTask = dirTask(minigameDirectionScheduler, uuid, fishDirChangeChance, grade);

        BukkitScheduler minigameTitleScheduler = Bukkit.getScheduler();
        minigameTitleScheduler.runTaskTimer(FishingAdventure.getServerInstance(), task ->
        {
            TextColor timerTextColor = TextColor.color(0, 255, 0);
            if(timeLimit <= fishingTimerMap.get(uuid) && onMiniGameMap.get(uuid))
            {
                // 낚시 실패
                FailCatch(player, event.getHook(), "좀 더 힘내보세요!");
                minigameDirectionTask.cancel();
                task.cancel();
            }
            else if((timeLimit) / 1.5 < fishingTimerMap.get(uuid))
            {
                timerTextColor = TextColor.color(255, 0, 0);
            }
            else if((timeLimit) / 2 < fishingTimerMap.get(uuid))
            {
                timerTextColor = TextColor.color(255, 255, 0);
            }
            Component actionComponent = Component.text( ((timeLimit) - (float)fishingTimerMap.get(uuid)) / 10.0 + "").color(timerTextColor);
            fishingTimerMap.put(uuid, fishingTimerMap.get(uuid) + 1);

            player.sendActionBar(actionComponent);

            boolean isCorrect = isCorrectDir(player, event.getHook().getLocation());


            TextColor textColor;
            if(isCorrect)
            {
                textColor = TextColor.color(0, 0, 255);
                if(fishingProgressMap.get(uuid) < 100.0)
                {
                    fishingProgressMap.put(uuid, fishingProgressMap.get(uuid) + 1.0 + (reelLevel * 0.03));
                }
                if(fishingProgressMap.get(uuid) >= 100.0)
                {
                    // 낚시 성공
                    SuccessCatch(event.getHook(), player, caughtFish, fishStrength);
                    minigameDirectionTask.cancel();
                    task.cancel();
                }
            }
            else
            {
                textColor = TextColor.color(255, 0, 0);
                if(fishingProgressMap.get(uuid) > 0)
                {
                    fishingProgressMap.put(uuid, fishingProgressMap.get(uuid) - grade);
                }
            }
            sendFishingTitle(player, textColor);

        }, 20L, 2L);
    }

    private BukkitTask dirTask(BukkitScheduler scheduler, UUID uuid, int fishDirChangeChance, int grade)
    {
        return scheduler.runTaskTimer(FishingAdventure.getServerInstance(), () ->
        {
            Random random = new Random();
            int randDirChangeChance = random.nextInt(1000);
            if(randDirChangeChance <= fishDirChangeChance)
            {
                List<String> dirString = new LinkedList<>();
                dirString.add("↑");
                dirString.add("↓");
                dirString.add("→");
                dirString.add("←");
                int dirIndex;
                if(!fishingDirectionMap.containsKey(uuid))
                {
                    dirIndex = random.nextInt(4);
                    fishingDirectionMap.put(uuid, dirString.get(dirIndex));
                }
                else
                {
                    dirIndex = random.nextInt(3);
                    dirString.remove(fishingDirectionMap.get(uuid));
                    fishingDirectionMap.put(uuid, dirString.get(dirIndex));
                }
            }
        }, 0L, 60L - ((grade-1L) * 5L));
    }

    private void sendFishingTitle(Player player, TextColor textColor)
    {
        Title.Times titleTime = Title.Times.times(Duration.ofSeconds(0), Duration.ofMillis(200), Duration.ofSeconds(0));
        UUID uuid = player.getUniqueId();
        String progressFillString = "";
        int currProgressInt = fishingProgressMap.get(uuid).intValue();
        for(int i = 0; i < currProgressInt; i++)
        {
            progressFillString += "|";
        }
        String progressEmptyString = "";
        for(int i = 0; i < 100 - currProgressInt; i++)
        {
            progressEmptyString += "|";
        }

        Component progressBar = Component.empty()
                .append(Component.text(progressFillString).color(TextColor.color(0, 255, 0)))
                .append(Component.text(progressEmptyString).color(TextColor.color(255, 255, 255)));

        MessageManager.SendTitle(player, fishingDirectionMap.get(uuid), textColor, progressBar, titleTime);
    }

    private boolean isCorrectDir(Player player, Location hookLoc)
    {
        UUID uuid = player.getUniqueId();
        float playerYaw = player.getYaw();
        float playerPitch = player.getPitch();

        fishingHookYawPitchMap.put(uuid, CalcYawPitch(player.getLocation().toVector(), playerYaw, playerPitch, hookLoc.toVector()));
        Iterator<Double> hookIterator = fishingHookYawPitchMap.get(uuid).iterator();
        double deltaYaw = hookIterator.next();
        double deltaPitch = hookIterator.next();;

        if(fishingDirectionMap.get(uuid).equals("↑")) // U -> deltaPitch <= -20, -30 <= deltaYaw <= 30
        {
            return deltaPitch <= -20 && -30 <= deltaYaw && deltaYaw <= 30;
        }
        else if(fishingDirectionMap.get(uuid).equals("↓")) // D -> deltaPitch >= 20, -30 <= deltaYaw <= 30
        {
            return deltaPitch >= 20 && -30 <= deltaYaw && deltaYaw <= 30;
        }
        else if(fishingDirectionMap.get(uuid).equals("→")) // R -> -30 <=  deltaPitch <= 30, deltaYaw >= 20
        {
            return  -30 <= deltaPitch && deltaPitch <= 30 && deltaYaw >= 20;
        }
        else if(fishingDirectionMap.get(uuid).equals("←")) // L -> -30 <= deltaPitch <= 30, yaw <= -20
        {
            return  -30 <= deltaPitch && deltaPitch <= 30 && deltaYaw <= -20;
        }
        else
        {
            return false;
        }
    }

    public void SpawnFireWork(Location loc, Color color)
    {
        Firework firework = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();;
        fireworkMeta.addEffect(FireworkEffect.builder().flicker(false).with(FireworkEffect.Type.BALL).trail(false).withColor(color).build());
        firework.setFireworkMeta(fireworkMeta);
        firework.setTicksFlown(1);
        firework.setTicksToDetonate(1);
    }

    public Set<Double> CalcYawPitch(Vector playerVec, float playerYaw, float playerPitch, Vector hookVec)
    {
        double deltaX = hookVec.getX() - playerVec.getX();
        double deltaY = hookVec.getY() - playerVec.getY();
        double deltaZ = hookVec.getZ() - playerVec.getZ();

        double distanceXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        double yawToHook = Math.atan2(deltaZ, deltaX) * (180.0 / Math.PI) - 90.0;
        double pitchToHook = -Math.atan2(deltaY, distanceXZ) * (180.0 / Math.PI);

        double deltaYaw = playerYaw - yawToHook;
        deltaYaw = ((deltaYaw % 360) + 540) % 360 - 180;

        double deltaPitch = playerPitch - pitchToHook;

        return Set.of(deltaPitch, deltaYaw);
    }

    public void SuccessCatch(FishHook hook, Player player, CustomStack caughtFish, int fishStrength)
    {
        Location hookLoc = hook.getLocation();
        UUID uuid = player.getUniqueId();
        int fishNum = FishingAdventure.getCustomItemManager().getFishNum(caughtFish) - 1;
        String stackPath = "players."+uuid+"."+fishNum;
        int stack = encyclopediastackConfig.getInt(stackPath);
        encyclopediastackConfig.set(stackPath, stack+1);
        FishingAdventure.getConfigManager().saveConfig("encyclopediastack");
        int grade = getFishGrade(caughtFish.getPermission());
        Color gradeColor = switch (grade)
        {
            case 1 -> Color.fromRGB(170, 170, 170);
            case 2 -> Color.fromRGB(85, 255, 255);
            case 3 -> Color.fromRGB(0, 170, 0);
            case 4 -> Color.fromRGB(255, 85, 85);
            case 5 -> Color.fromRGB(255, 170, 0);
            case 6 -> Color.fromRGB(255, 255, 85);
            default -> Color.WHITE;
        };
        if(grade >= 5)
        {
            String gradeString = switch (grade)
            {
                case 5 -> "[전설종] ";
                case 6 -> "[신화종] ";
                default -> "";
            };
            Component announceComponent = Component.empty()
                    .append(Component.text(player.getName() + " 님이 ").color(TextColor.color(0, 232, 255)))
                    .append(Component.text(gradeString + caughtFish.getDisplayName())).color(TextColor.color(gradeColor.asRGB()))
                    .append(Component.text(" 을(를) 낚았습니다!").color(TextColor.color(0, 232, 255)));
            for(Player online : Bukkit.getOnlinePlayers())
            {
                MessageManager.SendChatForm(online);
                online.sendMessage(announceComponent);
                MessageManager.SendChatForm(online);
            }
        }
        SpawnFireWork(hookLoc, gradeColor);
        onMiniGameMap.put(uuid, false);
        hook.teleport(new Location(player.getWorld(), 0, 1000, 0));
        player.getInventory().addItem(caughtFish.getItemStack());
        onPlayerGetExp(player, fishStrength * getFishGrade(caughtFish.getPermission()));
        player.giveExp(fishStrength * getFishGrade(caughtFish.getPermission()));
    }

    public void FailCatch(Player player, FishHook hook, String cause)
    {
        UUID uuid = player.getUniqueId();
        onMiniGameMap.put(uuid, false);
        hook.teleport(new Location(player.getWorld(), 0, 1000, 0));
        ItemStack fishingRod = player.getInventory().getItemInMainHand();
        ItemMeta fishingRodMeta = fishingRod.getItemMeta();
        if(fishingRod.getType().equals(Material.FISHING_ROD))
        {
            List<Component> loreList = fishingRodMeta.lore();
            String durabilityLoreString = ((TextComponent)loreList.get(4)).content();
            String[] durabilityString = durabilityLoreString.replace("[내구도] [", "").replace("]", "").split("/");
            int currDurabilityValue = Integer.parseInt(durabilityString[0]);
            int maxDurabilityValue = Integer.parseInt(durabilityString[1]);
            currDurabilityValue -= 1;
            loreList.remove(4);
            if(currDurabilityValue < 9)
            {
                durabilityLoreString = durabilityLoreString.replace("0" + (currDurabilityValue + 1), "0" + currDurabilityValue);
            }
            else if(currDurabilityValue == 9)
            {
                durabilityLoreString = durabilityLoreString.replace((currDurabilityValue + 1) + "", "0" + currDurabilityValue);
            }
            else
            {
                durabilityLoreString = durabilityLoreString.replaceFirst((currDurabilityValue + 1) + "", currDurabilityValue + "");
            }

            loreList.add(4, Component.text(durabilityLoreString).color(LoreColorManager.fishingRodDurabilityColor).decoration(TextDecoration.ITALIC, false));


            fishingRodMeta.lore(loreList);
            fishingRod.setItemMeta(fishingRodMeta);
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    MessageManager.SendTitle(player, "놓쳐버렸습니다...", TextColor.color(255, 0, 0), cause, TextColor.color(255, 255, 255));
                }
            }.runTaskLater(FishingAdventure.getServerInstance(), 3L);
        }
    }

    public void onPlayerGetPoint(Player player, int point)
    {
        UUID uuid = player.getUniqueId();

        int newPoint = playerFishingPointMap.get(uuid) + point;

        playerFishingPointMap.put(uuid, newPoint);

        fishingPointConfig.set("players." + uuid, newPoint);
        FishingAdventure.getConfigManager().saveConfig("fishingpoint");
    }

    public void onPlayerGetExp(Player player, int exp)
    {
        UUID uuid = player.getUniqueId();
        int lv = playerFishingLevelMap.get(uuid);

        if(lv >= 100)
        {
            Component actionComponent = Component.text("낚시 레벨이 이미 최대치입니다!").color(TextColor.color(0, 209, 255));
            player.sendActionBar(actionComponent);
            return;
        }

        int currExp = playerFishingExpMap.get(uuid) + exp;
        int needExp;

        while(true)
        {
            if(lv <= 16)
            {
                needExp = 2 * lv + 7;
            }
            else if(lv <= 31)
            {
                needExp = 5 * lv - 38;
            }
            else
            {
                needExp = 9 * lv - 158;
            }
            if(lv >= 100)
            {
                Component actionComponent = Component.text("낚시 레벨이 최대치에 도달했습니다!").color(TextColor.color(0, 209, 255));
                player.sendActionBar(actionComponent);
                break;
            }
            // 레벨 업
            if(currExp >= needExp)
            {
                lv += 1;
                Component actionComponent = Component.text("낚시 레벨이 올랐습니다!").color(TextColor.color(0, 209, 255));
                player.sendActionBar(actionComponent);
                currExp = currExp - needExp;
            }
            else
            {
                break;
            }
        }

        BossBar expBar = Bukkit.createBossBar("낚시 숙련도: " + lv, BarColor.BLUE, BarStyle.SOLID);
        expBar.addPlayer(player);
        expBar.setVisible(true);
        double progress = (double)currExp/(double)needExp;
        if(progress < 0.0)
        {
            progress = 0.0;
        }
        else if(progress > 1.0)
        {
            progress = 1.0;
        }
        expBar.setProgress(progress);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                expBar.setVisible(false);
                expBar.removePlayer(player);
            }
        }.runTaskLater(FishingAdventure.getServerInstance(), 40L);

        playerFishingLevelMap.put(uuid, lv);
        playerFishingExpMap.put(uuid, currExp);

        fishingLevelConfig.set("players.lv." + uuid, lv);
        fishingLevelConfig.set("players.exp." + uuid, currExp);
        FishingAdventure.getConfigManager().saveConfig("fishinglevel");

        Component listNameComponent = Component.empty()
                .append(Component.text("[lv." + playerFishingLevelMap.get(uuid) + "]").color(TextColor.color(0, 223, 255)))
                .append(Component.text(player.getName()));
        player.playerListName(listNameComponent);
    }

    public static boolean isFish(String permission)
    {
        return permission.equals("ia.fishing_adventure.normal_fish")
                || permission.equals("ia.fishing_adventure.rare_fish")
                || permission.equals("ia.fishing_adventure.ancient_fish")
                || permission.equals("ia.fishing_adventure.overload_fish")
                || permission.equals("ia.fishing_adventure.legend_fish")
                || permission.equals("ia.fishing_adventure.myth_fish")
                || permission.equals("ia.fishing_adventure.unique_fish");
    }

    public static int getFishGrade(String permission)
    {
        switch (permission)
        {
            case "ia.fishing_adventure.normal_fish":
                return 1;
            case "ia.fishing_adventure.rare_fish":
                return 2;
            case "ia.fishing_adventure.ancient_fish":
                return 3;
            case "ia.fishing_adventure.overload_fish":
                return 4;
            case "ia.fishing_adventure.legend_fish":
                return 5;
            case "ia.fishing_adventure.myth_fish":
                return 6;
            case "ia.fishing_adventure.unique_fish":
                return 10;
            default:
                return 0;
        }
    }
}
