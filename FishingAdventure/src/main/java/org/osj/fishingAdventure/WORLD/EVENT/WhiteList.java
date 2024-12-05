package org.osj.fishingAdventure.WORLD.EVENT;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.osj.fishingAdventure.FishingAdventure;

import java.util.UUID;

public class WhiteList implements Listener
{
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event)
    {
        Player player = event.getPlayer();
        String playerName = player.getName();
        UUID uuid = player.getUniqueId();
        if(playerName.equals("Happy_Kkyulmik"))
        {
            return;
        }

        if(!FishingAdventure.getConfigManager().getConfig("whitelist").contains("players." + uuid))
        {
            Component result = Component.text("당신은 아직 바다를 접할 자격이 없습니다.").color(TextColor.color(0, 157, 255)).decorate(TextDecoration.BOLD);
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, result);
        }
        else if(!FishingAdventure.getConfigManager().getConfig("whitelist").getBoolean("players." + uuid))
        {
            Component result = Component.text("당신은 바다의 분노를 샀습니다.").color(TextColor.color(255, 12, 0)).decorate(TextDecoration.BOLD);
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, result);
        }
    }
}
