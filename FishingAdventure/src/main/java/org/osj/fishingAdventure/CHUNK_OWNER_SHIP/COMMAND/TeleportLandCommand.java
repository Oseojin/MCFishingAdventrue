package org.osj.fishingAdventure.CHUNK_OWNER_SHIP.COMMAND;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.osj.fishingAdventure.FishingAdventure;
import org.osj.fishingAdventure.WORLD.WorldManager;

public class TeleportLandCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;

        if(!player.getWorld().getName().equals(WorldManager.rest_world))
        {
            player.sendMessage(Component.text("초승달 섬 에서만 내 섬으로 이동할 수 있습니다.").color(TextColor.color(255,0,0)).decorate(TextDecoration.BOLD));
            return false;
        }
        Chunk myChunk = FishingAdventure.getChunkManager().getMyChunk(player.getUniqueId());
        if(myChunk == null)
        {
            player.sendMessage(Component.text("가진 섬이 없습니다.").color(TextColor.color(255,0,0)).decorate(TextDecoration.BOLD));
            return false;
        }
        player.teleport(myChunk.getBlock(0, -60, 0).getLocation());

        return false;
    }
}
