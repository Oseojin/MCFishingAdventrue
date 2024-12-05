package org.osj.fishingAdventure.WORLD.EVENT;

import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.osj.fishingAdventure.FishingAdventure;
import org.osj.fishingAdventure.WORLD.WorldManager;

import java.util.UUID;

public class PortalTeleport implements Listener
{
    public static FileConfiguration lastLocConfig = FishingAdventure.getConfigManager().getConfig("lastloc");

    @EventHandler
    public void onPlayerEnterPortal(EntityPortalEnterEvent event)
    {
        if(!(event.getEntity() instanceof Player))
        {
            return;
        }
        Player player = (Player) event.getEntity();
        UUID uuid = player.getUniqueId();
        String worldName = player.getWorld().getName();
        if(worldName.equals(WorldManager.rest_world))
        {
            player.teleport(WorldManager.blueSpawnLocList.get(lastLocConfig.getInt("players." + uuid)));
        }
        else if(WorldManager.blueList.contains(worldName))
        {
            int index = WorldManager.blueList.indexOf(worldName);
            if(index == 0)
            {
                player.teleport(WorldManager.restWorldSpawnLoc);
            }
            else
            {
                player.teleport(WorldManager.blueSpawnLocList.get(index-1));
            }
        }
    }
}
