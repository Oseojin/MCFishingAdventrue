package org.osj.fishingAdventure.MESSAGE;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.osj.fishingAdventure.FISHING_SYSTEM.FishingManager;
import org.osj.fishingAdventure.FishingAdventure;

public class PlayerChat implements Listener
{
    @EventHandler
    public void onChat(AsyncChatEvent event)
    {
        event.setCancelled(true);
        Player player = event.getPlayer();
        Component chatNameComponent = Component.empty()
                .append(Component.text("[lv." + FishingManager.playerFishingLevelMap.get(player.getUniqueId()) + "]").color(TextColor.color(0, 223, 255)))
                .append(Component.text(player.getName()));
        Component chat = event.renderer().render(player, chatNameComponent, event.message(), Audience.audience(Bukkit.getOnlinePlayers()));
        for(Player online : Bukkit.getOnlinePlayers())
        {
            online.sendMessage(chat);
        }
    }
}
