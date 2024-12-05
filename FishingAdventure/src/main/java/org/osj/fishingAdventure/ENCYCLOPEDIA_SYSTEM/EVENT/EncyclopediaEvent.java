package org.osj.fishingAdventure.ENCYCLOPEDIA_SYSTEM.EVENT;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.osj.fishingAdventure.FishingAdventure;

import java.util.UUID;

public class EncyclopediaEvent implements Listener
{
    public FileConfiguration encyclopediaConfig = FishingAdventure.getConfigManager().getConfig("encyclopedia");
    public FileConfiguration encyclopediastackConfig = FishingAdventure.getConfigManager().getConfig("encyclopediastack");
    @EventHandler
    public void onPlayerJoinFirst(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if(!encyclopediaConfig.contains("players." + uuid))
        {
            for(int i = 0; i < FishingAdventure.getCustomItemManager().getFishListSize(); i++)
            {
                encyclopediaConfig.set("players." + uuid + "." + i, false);
            }
        }
        if(!encyclopediastackConfig.contains("players." + uuid))
        {
            for(int i = 0; i < FishingAdventure.getCustomItemManager().getFishListSize(); i++)
            {
                encyclopediastackConfig.set("players." + uuid + "." + i, 0);
            }
        }
        FishingAdventure.getConfigManager().saveConfig("encyclopedia");
        FishingAdventure.getConfigManager().saveConfig("encyclopediastack");
    }
}
