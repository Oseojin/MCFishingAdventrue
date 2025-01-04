package org.osj.fishingAdventure.CHUNK_OWNER_SHIP.COMMAND;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.osj.fishingAdventure.FishingAdventure;

public class RemoveChunkCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;
        if(!player.isOp())
        {
            return false;
        }
        if(FishingAdventure.getChunkManager().isOwnerless(player.getChunk().getChunkKey()))
        {
            player.sendMessage("주인 없는 섬 입니다.");
            return false;
        }
        FishingAdventure.getChunkManager().removeMyChunk(FishingAdventure.getChunkManager().whosChunk(player.getChunk().getChunkKey()), player.getChunk());
        return false;
    }
}
