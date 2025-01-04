package org.osj.fishingAdventure.CHUNK_OWNER_SHIP.EVENT;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.osj.fishingAdventure.CHUNK_OWNER_SHIP.ChunkManager;
import org.osj.fishingAdventure.FishingAdventure;
import org.osj.fishingAdventure.MESSAGE.MessageManager;
import org.osj.fishingAdventure.WORLD.WorldManager;

public class SpawnProtect implements Listener
{
    @EventHandler
    public void onPlayerPlaceBlockInSpawn(BlockPlaceEvent event)
    {
        Player player = event.getPlayer();
        Chunk chunk = event.getBlockPlaced().getChunk();
        if(player.isOp())
        {
            return;
        }
        if(!FishingAdventure.getChunkManager().canInteractChunk(player, chunk))
        {
            MessageManager.SendChatContent(player, "허가되지 않은 행동입니다.", TextColor.color(255, 0, 0));
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerBreakBlockInSpawn(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Chunk chunk = event.getBlock().getChunk();
        if(player.isOp())
        {
            return;
        }
        if(!FishingAdventure.getChunkManager().canInteractChunk(player, chunk))
        {
            MessageManager.SendChatContent(player, "허가되지 않은 행동입니다.", TextColor.color(255, 0, 0));
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onSpawnBurn(BlockBurnEvent event)
    {
        Block block = event.getIgnitingBlock();
        if(block.getWorld().getName().equals(WorldManager.wild) || block.getWorld().getName().equals(WorldManager.nether) || block.getWorld().getName().equals(WorldManager.end))
        {
            return;
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void onPreventInteraction(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if(player.isOp())
        {
            return;
        }
        if(!player.getWorld().getName().equals(WorldManager.rest_world))
        {
            return;
        }
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_AIR))
        {
            return;
        }
        Block clickedBlock = event.getClickedBlock();
        if(clickedBlock != null && FishingAdventure.getChunkManager().canInteractChunk(player, clickedBlock.getChunk()))
        {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event)
    {
        if(event.getBlock().getWorld().getName().equals(WorldManager.rest_world))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFire(BlockIgniteEvent event)
    {
        if(event.getCause().equals(BlockIgniteEvent.IgniteCause.SPREAD))
        {
            event.setCancelled(true);
        }
    }

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
