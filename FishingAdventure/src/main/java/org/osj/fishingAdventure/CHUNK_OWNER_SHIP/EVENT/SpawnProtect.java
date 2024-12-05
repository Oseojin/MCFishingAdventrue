package org.osj.fishingAdventure.CHUNK_OWNER_SHIP.EVENT;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.block.BlockPlaceEvent;
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
}
