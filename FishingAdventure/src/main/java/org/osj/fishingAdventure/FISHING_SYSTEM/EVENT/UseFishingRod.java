package org.osj.fishingAdventure.FISHING_SYSTEM.EVENT;

import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.osj.fishingAdventure.CUSTOMITEMS.CustomItemManager;
import org.osj.fishingAdventure.CUSTOMITEMS.LoreColorManager;
import org.osj.fishingAdventure.FISHING_SYSTEM.FishingManager;
import org.osj.fishingAdventure.FishingAdventure;

import java.util.*;

public class UseFishingRod implements Listener
{
    int waitTimeDefault = 30;

    @EventHandler
    public void onUseFishingRod(PlayerFishEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        CustomStack mainHandCustom = CustomStack.byItemStack(mainHand);
        ItemStack offHand = player.getInventory().getItemInOffHand();
        CustomStack offHandCustom = CustomStack.byItemStack(offHand);
        if (mainHandCustom != null && offHandCustom != null)
        {
            event.setCancelled(true);
            inhanceRod(player, mainHand, mainHandCustom, offHand, offHandCustom);
        }
        if (mainHandCustom == null || !mainHandCustom.getPermission().equals("ia.fishing_adventure.fishing_rod"))
        {
            event.setCancelled(true);
            Component actionComponent = Component.text("낚싯대를 주 손에 제대로 들어주세요!").color(TextColor.color(255, 49, 44)).decorate(TextDecoration.BOLD);
            player.sendActionBar(actionComponent);
            return;
        }

        List<Component> loreList = mainHand.getItemMeta().lore();
        String durabilityLoreString = ((TextComponent) loreList.get(4)).content();
        String[] durabilityString = durabilityLoreString.replace("[내구도] [", "").replace("]", "").split("/");
        int currDurabilityValue = Integer.parseInt(durabilityString[0]);
        int maxDurabilityValue = Integer.parseInt(durabilityString[1]);
        if (currDurabilityValue <= 0)
        {
            event.setCancelled(true);
            Component actionComponent = Component.text("낚싯대가 망가졌습니다...").color(TextColor.color(0, 0, 0)).decorate(TextDecoration.BOLD);
            player.sendActionBar(actionComponent);
            return;
        }

        String strengthString = ((TextComponent) loreList.get(1)).content();
        strengthString = strengthString.replace("[낚싯대] [", "").replace("/99]", "");
        String stringString = ((TextComponent) loreList.get(2)).content();
        stringString = stringString.replace("[낚싯줄] [", "").replace("/99]", "");
        String reelString = ((TextComponent) loreList.get(3)).content();
        reelString = reelString.replace("[낚시릴] [", "").replace("/99]", "");
        int strength = Integer.parseInt(strengthString);
        int string = Integer.parseInt(stringString);
        int reel = Integer.parseInt(reelString);

        FishHook hook = event.getHook();

        if (event.getState() == PlayerFishEvent.State.FISHING)
        {
            if(!player.getWorld().getName().contains("_blue"))
            {
                Component actionComponent = Component.text("낚시는 전용 맵 에서만 할 수 있습니다.").color(TextColor.color(255, 3, 0));
                player.sendActionBar(actionComponent);
                event.setCancelled(true);
                return;
            }
            int newWaitTime = waitTimeDefault * 20 - (FishingManager.playerFishingLevelMap.get(uuid) * 5);
            hook.setWaitTime(newWaitTime, newWaitTime);
            if (!FishingManager.onMiniGameMap.containsKey(uuid))
            {
                FishingManager.onMiniGameMap.put(uuid, false);
            }
        }
        else if (event.getState() == PlayerFishEvent.State.LURED)
        {
            if (FishingManager.onMiniGameMap.containsKey(uuid) && FishingManager.onMiniGameMap.get(uuid))
            {
                event.setCancelled(true);
            }
        }
        else if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH)
        {
            List<CustomStack> fishList = randomCaughtFish(player.getWorld().getName());

            int fishIndexClamp = strength;
            if(fishIndexClamp > fishList.size())
            {
                fishIndexClamp = fishList.size();
            }

            fishList = fishList.subList(fishList.size() - fishIndexClamp, fishList.size());

            Random random = new Random();
            CustomStack caughtedFish = CustomItemManager.makeCopy(fishList.get(random.nextInt(fishList.size())));
            int fishStrength = FishingAdventure.getCustomItemManager().getFishNum(caughtedFish);
            int rootingChance = random.nextInt(100) + 1;
            if(rootingChance <= 20)
            {
                addFishLore(caughtedFish, Component.text("물고기가 무언가 물고 있습니다.").color(TextColor.color(79, 97, 171)).decoration(TextDecoration.ITALIC, false));
            }
            int fishDirChangeChance = ((fishStrength + 30) * 10) - (FishingManager.playerFishingLevelMap.get(uuid) * 5); // int로 소숫점 표현하려고 * 10함
            // 미니게임 시작
            if (!FishingManager.onMiniGameMap.containsKey(uuid) || !FishingManager.onMiniGameMap.get(uuid))
            {
                event.setCancelled(true);
                FishingManager.onMiniGameMap.put(uuid, true);
                FishingAdventure.getFishingManager().startMiniGame(player, caughtedFish, string, fishDirChangeChance, reel, fishStrength, event);
            }
        }
        else if (event.getState() == PlayerFishEvent.State.REEL_IN)
        {
            if (FishingManager.onMiniGameMap.containsKey(uuid) && FishingManager.onMiniGameMap.get(uuid))
            {
                event.setCancelled(true);
                return;
            }
            FishingManager.onMiniGameMap.put(uuid, false);
        }
    }

    private void addFishLore(CustomStack caughtedFish, Component content)
    {
        ItemMeta fishMeta = caughtedFish.getItemStack().getItemMeta();
        List<Component> fishLoreList = fishMeta.lore();

        fishLoreList.add(content);
        fishMeta.lore(fishLoreList);
        caughtedFish.getItemStack().setItemMeta(fishMeta);
    }

    private List<CustomStack> randomCaughtFish(String worldName)
    {
        List<CustomStack> fishList = new ArrayList<>();
        switch (worldName)
        {
            case "myth_blue":
                fishList.addAll(FishingAdventure.getCustomItemManager().getMythFishList());
            case "legend_blue":
                fishList.addAll(FishingAdventure.getCustomItemManager().getLegendFishList());
            case "overload_blue":
                fishList.addAll(FishingAdventure.getCustomItemManager().getOverlordFishList());
            case "ancient_blue":
                fishList.addAll(FishingAdventure.getCustomItemManager().getAncientFishList());
            case "rare_blue":
                fishList.addAll(FishingAdventure.getCustomItemManager().getRareFishList());
            case "normal_blue":
                fishList.addAll(FishingAdventure.getCustomItemManager().getNormalFishList());
        }

        return fishList;
    }

    private void inhanceRod(Player player, ItemStack mainHand, CustomStack mainHandCustom, ItemStack offHand, CustomStack offHandCustom)
    {
        if (mainHandCustom.getPermission().equals("ia.fishing_adventure.fishing_rod") && offHandCustom.getPermission().equals("ia.fishing_adventure.gemstone"))
        {
            ItemMeta fishingRodMeta = mainHand.getItemMeta();
            // 강화
            String itemName = ((TextComponent) offHand.getItemMeta().displayName()).content();
            List<Component> loreList = mainHand.lore();
            String strengthLoreString = ((TextComponent) loreList.get(1)).content();
            String strengthString = strengthLoreString.replace("[낚싯대] [", "").replace("/99]", "");
            String stringLoreString = ((TextComponent) loreList.get(2)).content();
            String stringString = stringLoreString.replace("[낚싯줄] [", "").replace("/99]", "");
            String reelLoreString = ((TextComponent) loreList.get(3)).content();
            String reelString = reelLoreString.replace("[낚시릴] [", "").replace("/99]", "");
            String durabilityLoreString = ((TextComponent) loreList.get(4)).content();
            String[] durabilityString = durabilityLoreString.replace("[내구도] [", "").replace("]", "").split("/");
            int strength = Integer.parseInt(strengthString);
            int string = Integer.parseInt(stringString);
            int reel = Integer.parseInt(reelString);
            int currDurabilityValue = Integer.parseInt(durabilityString[0]);
            int maxDurabilityValue = Integer.parseInt(durabilityString[1]);

            if (itemName.contains("내구도"))
            {
                if (maxDurabilityValue >= 99)
                {
                    // 강화 한계
                    Component actionComponent = Component.text("더 이상 내구도를 강화할 수 없습니다.").color(TextColor.color(255, 0, 0)).decorate(TextDecoration.BOLD);
                    player.sendMessage(actionComponent);
                    return;
                }
                maxDurabilityValue += 25;
                if (maxDurabilityValue >= 100)
                {
                    maxDurabilityValue = 99;
                    durabilityLoreString = durabilityLoreString.replace("/" + (maxDurabilityValue - 24), "/" + maxDurabilityValue);
                }
                else
                {
                    durabilityLoreString = durabilityLoreString.replace("/" + (maxDurabilityValue - 25), "/" + maxDurabilityValue);
                }
                loreList.remove(4);
                loreList.add(4, Component.text(durabilityLoreString).color(LoreColorManager.fishingRodDurabilityColor).decoration(TextDecoration.ITALIC, false));
                Component inhanceComponent = Component.text("[강화성공]최대 내구도 +25").color(TextColor.color(85, 255, 255)).decorate(TextDecoration.BOLD);
                player.sendMessage(inhanceComponent);
            }
            else if(itemName.contains("강화석"))
            {
                if(getRodGrade(mainHandCustom) != getGemstoneGrade(offHandCustom))
                {
                    Component actionComponent = Component.text("등급에 맞는 강화석을 사용해 주세요!").color(TextColor.color(255, 0, 0)).decorate(TextDecoration.BOLD);
                    player.sendMessage(actionComponent);
                    return;
                }
                Random random = new Random();
                int randomInhance = random.nextInt(3);

                if (randomInhance == 0)
                {
                    if (strength >= getInhanceLimit(mainHandCustom))
                    {
                        // 강화 한계
                        Component actionComponent = Component.text("낚싯대 강화가 한계에 부딪혔습니다!").color(TextColor.color(255, 0, 0)).decorate(TextDecoration.BOLD);
                        player.sendMessage(actionComponent);
                        return;
                    }
                    strength++;
                    loreList.remove(1);
                    loreList.add(1, Component.text(addStatRod(strength, strengthLoreString)).color(LoreColorManager.fishingRodStrengthColor).decoration(TextDecoration.ITALIC, false));
                    Component inhanceComponent = Component.text("[강화성공]낚싯대 +1").color(TextColor.color(255, 85, 85)).decorate(TextDecoration.BOLD);
                    player.sendMessage(inhanceComponent);
                }
                else if (randomInhance == 1)
                {
                    if (string >= getInhanceLimit(mainHandCustom))
                    {
                        // 강화 한계
                        Component actionComponent = Component.text("낚싯줄 강화가 한계에 부딪혔습니다!").color(TextColor.color(255, 0, 0)).decorate(TextDecoration.BOLD);
                        player.sendMessage(actionComponent);
                        return;
                    }
                    string++;
                    loreList.remove(2);
                    loreList.add(2, Component.text(addStatRod(string, stringLoreString)).color(LoreColorManager.fishingRodStringColor).decoration(TextDecoration.ITALIC, false));
                    Component inhanceComponent = Component.text("[강화성공]낚싯줄 +1").color(TextColor.color(170, 170, 170)).decorate(TextDecoration.BOLD);
                    player.sendMessage(inhanceComponent);
                }
                else
                {
                    if (reel >= getInhanceLimit(mainHandCustom))
                    {
                        // 강화 한계
                        Component actionComponent = Component.text("낚시릴 강화가 한계에 부딪혔습니다!").color(TextColor.color(255, 0, 0)).decorate(TextDecoration.BOLD);
                        player.sendMessage(actionComponent);
                        return;
                    }
                    reel++;
                    loreList.remove(3);
                    loreList.add(3, Component.text(addStatRod(reel, reelLoreString)).color(LoreColorManager.fishingRodReelColor).decoration(TextDecoration.ITALIC, false));
                    Component inhanceComponent = Component.text("[강화성공]낚시릴 +1").color(TextColor.color(255, 255, 85)).decorate(TextDecoration.BOLD);
                    player.sendMessage(inhanceComponent);
                }
            }
            else if(itemName.contains("복구석"))
            {
                if(currDurabilityValue == maxDurabilityValue)
                {
                    Component actionComponent = Component.text("이미 내구도가 가득 차 있습니다.").color(TextColor.color(0, 231, 255)).decorate(TextDecoration.BOLD);
                    player.sendMessage(actionComponent);
                    return;
                }
                else
                {
                    if(currDurabilityValue <= 9)
                    {
                        durabilityLoreString = durabilityLoreString.replace("0" + currDurabilityValue, maxDurabilityValue + "");
                    }
                    else
                    {
                        durabilityLoreString = durabilityLoreString.replace(currDurabilityValue + "", maxDurabilityValue + "");
                    }
                }
                loreList.remove(4);
                loreList.add(4, Component.text(durabilityLoreString).color(LoreColorManager.fishingRodDurabilityColor).decoration(TextDecoration.ITALIC, false));
                Component inhanceComponent = Component.text("내구도 복구").color(TextColor.color(85, 255, 255)).decorate(TextDecoration.BOLD);
                player.sendMessage(inhanceComponent);
            }

            fishingRodMeta.lore(loreList);
            mainHand.setItemMeta(fishingRodMeta);
            offHand.add(-1);
            upgradeRod(player, mainHand, strength, string, reel);
        }
    }

    private int getInhanceLimit(CustomStack rod)
    {
        String name = rod.getDisplayName();
        if(name.contains("평범한"))
        {
            return 35;
        }
        else if(name.contains("귀한"))
        {
            return 55;
        }
        else if(name.contains("고대의"))
        {
            return 65;
        }
        else if(name.contains("고고한"))
        {
            return 72;
        }
        else if(name.contains("별이 담긴"))
        {
            return 77;
        }
        else if(name.contains("위대한"))
        {
            return 99;
        }
        return -1;
    }

    private int getGemstoneGrade(CustomStack gemstone)
    {
        String name = gemstone.getDisplayName();
        if(name.contains("회색"))
        {
            return 1;
        }
        else if(name.contains("푸른색"))
        {
            return 2;
        }
        else if(name.contains("녹색"))
        {
            return 3;
        }
        else if(name.contains("패왕색"))
        {
            return 4;
        }
        else if(name.contains("별빛"))
        {
            return 5;
        }
        else if(name.contains("신화적"))
        {
            return 6;
        }
        return -1;
    }

    private int getRodGrade(CustomStack rod)
    {
        String name = rod.getDisplayName();
        if(name.contains("평범한"))
        {
            return 1;
        }
        else if(name.contains("귀한"))
        {
            return 2;
        }
        else if(name.contains("고대의"))
        {
            return 3;
        }
        else if(name.contains("고고한"))
        {
            return 4;
        }
        else if(name.contains("별이 담긴"))
        {
            return 5;
        }
        else if(name.contains("위대한"))
        {
            return 6;
        }
        return -1;
    }

    private void upgradeRod(Player player, ItemStack rodStack, int strength, int string, int reel)
    {
        CustomStack nextRod;
        CustomStack rod = CustomStack.byItemStack(rodStack);
        int inhanceLimit = getInhanceLimit(rod);
        if(strength == inhanceLimit && string == inhanceLimit && reel == inhanceLimit)
        {
            switch (inhanceLimit)
            {
                case 35:
                    nextRod = CustomItemManager.makeCopy(FishingAdventure.getCustomItemManager().getRod(2));
                    break;
                case 55:
                    nextRod = CustomItemManager.makeCopy(FishingAdventure.getCustomItemManager().getRod(3));
                    break;
                case 65:
                    nextRod = CustomItemManager.makeCopy(FishingAdventure.getCustomItemManager().getRod(4));
                    break;
                case 72:
                    nextRod = CustomItemManager.makeCopy(FishingAdventure.getCustomItemManager().getRod(5));
                    break;
                case 77:
                    nextRod = CustomItemManager.makeCopy(FishingAdventure.getCustomItemManager().getRod(6));
                    break;
                default:
                    return;
            }

            List<Component> loreList = rod.getItemStack().lore();
            loreList.removeLast();
            loreList.add(nextRod.getItemStack().lore().get(0));
            nextRod.getItemStack().lore(loreList);
            player.getInventory().setItemInMainHand(nextRod.getItemStack());
        }
    }

    private String addStatRod(int stat, String loreString)
    {
        if(stat < 10)
        {
            loreString = loreString.replace("0" + (stat - 1), "0" + stat);
        }
        else if(stat == 10)
        {
            loreString = loreString.replace("0" + (stat - 1), stat + "");
        }
        else
        {
            loreString = loreString.replaceFirst((stat - 1) + "", stat + "");
        }

        return loreString;
    }
}