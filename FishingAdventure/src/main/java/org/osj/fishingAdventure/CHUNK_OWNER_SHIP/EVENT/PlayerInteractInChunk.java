package org.osj.fishingAdventure.CHUNK_OWNER_SHIP.EVENT;

import dev.lone.itemsadder.api.CustomEntity;
import dev.lone.itemsadder.api.CustomFurniture;
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.GlowItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.osj.fishingAdventure.CHUNK_OWNER_SHIP.ChunkManager;
import org.osj.fishingAdventure.FISHING_SYSTEM.FishingManager;
import org.osj.fishingAdventure.FishingAdventure;

import java.util.UUID;

public class PlayerInteractInChunk implements Listener
{
    /*@EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if(player.isOp())
        {
            return;
        }
        //Chunk chunk = player.getChunk();

        *//*if(!FishingAdventure.getChunkManager().canInteractChunk(player, chunk))
        {
            event.setCancelled(true);
        }*//*
    }*/

    @EventHandler
    public void onPlayerAttackFrame(HangingBreakByEntityEvent event)
    {
        if(!(event.getRemover() instanceof Player))
        {
            event.setCancelled(true);
            return;
        }
        Player player = (Player) event.getRemover();
        if(player.isOp())
        {
            return;
        }
        Chunk chunk = event.getEntity().getChunk();
        if(!FishingAdventure.getChunkManager().canInteractChunk(player, chunk))
        {
            event.setCancelled(true);
        }
    }
}
