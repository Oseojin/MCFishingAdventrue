package org.osj.fishingAdventure.FISHING_SYSTEM.EVENT;

import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.osj.fishingAdventure.WORLD.WorldManager;

public class PreventPVP implements Listener
{
    @EventHandler
    public void onAttackPlayer(PrePlayerAttackEntityEvent event)
    {
        Player player = event.getPlayer();
        String worldName = player.getWorld().getName();
        if(worldName.equals(WorldManager.wild) || worldName.equals(WorldManager.nether) || worldName.equals(WorldManager.end))
        {
            return;
        }
        if(event.getAttacked() instanceof Player && event.willAttack())
        {
            event.setCancelled(true);
        }
    }
}
