package org.osj.fishingAdventure.WORLD.EVENT;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.osj.fishingAdventure.FishingAdventure;
import org.osj.fishingAdventure.WORLD.WorldManager;

import java.util.UUID;

public class WorldChangeExit implements Listener
{
    public static FileConfiguration lastLocConfig = FishingAdventure.getConfigManager().getConfig("lastloc");

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if(!lastLocConfig.contains("players."+uuid))
        {
            lastLocConfig.set("players."+uuid, -10);
            FishingAdventure.getConfigManager().saveConfig("lastloc");
        }
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                player.teleport(WorldManager.restWorldSpawnLoc);
            }
        }.runTaskLater(FishingAdventure.getServerInstance(), 5L);
        if(!player.hasPlayedBefore())
        {
            Chest manualChest = (Chest) Bukkit.getWorld(WorldManager.rest_world).getBlockState(0, 152, -13);
            ItemStack manual = manualChest.getBlockInventory().getItem(0);
            player.getInventory().addItem(FishingAdventure.getCustomItemManager().getRod(1).getItemStack());
            player.getInventory().addItem(FishingAdventure.getCustomItemManager().getKeyList().get(0).getItemStack());
            player.getInventory().addItem(manual);
        }
    }

    @EventHandler
    public void onPlayerEnterWorld(PlayerChangedWorldEvent event)
    {
        Player player = event.getPlayer();
        String worldName = player.getWorld().getName();

        int index = -10;

        switch (worldName)
        {
            case "rest_world":
                index = -1;
                break;
            case "normal_blue":
                index = 0;
                break;
            case "rare_blue":
                index = 1;
                break;
            case "ancient_blue":
                index = 2;
                break;
            case "overload_blue":
                index = 3;
                break;
            case "legend_blue":
                index = 4;
                break;
            case "myth_blue":
                index = 5;
                break;
        }
        Title worldEnterTitle;
        if(index == -10)
        {
            return;
        }
        else if(index == -1)
        {
            worldEnterTitle = Title.title(WorldManager.restWorldMainTitle, WorldManager.restWorldSubTitle);
        }
        else
        {
            worldEnterTitle = Title.title(WorldManager.blueEnterMainTitleList.get(index), WorldManager.blueEnterSubTitleList.get(index));
        }
        player.showTitle(worldEnterTitle);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getPlayer();
        String worldName= player.getWorld().getName();

        if(worldName.equals(WorldManager.wild) || worldName.equals(WorldManager.nether) || worldName.equals(WorldManager.end))
        {
            event.setCancelled(true);
            player.teleport(WorldManager.restWorldSpawnLoc);
            Component deathComponent = Component.text("야생 월드에서 사망하여 초승달 섬으로 복귀하였습니다.").color(TextColor.color(255, 3, 0));
            player.sendMessage(deathComponent);
        }
    }
}
