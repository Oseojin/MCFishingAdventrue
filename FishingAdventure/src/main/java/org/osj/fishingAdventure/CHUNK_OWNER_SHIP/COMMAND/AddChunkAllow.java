package org.osj.fishingAdventure.CHUNK_OWNER_SHIP.COMMAND;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.osj.fishingAdventure.CHUNK_OWNER_SHIP.CHAT.MessageManager;
import org.osj.fishingAdventure.FishingAdventure;

import java.util.UUID;

public class AddChunkAllow implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;
        if(strings.length != 1)
        {
            return false;
        }

        UUID friendUUID = player.getServer().getPlayerUniqueId(strings[0]);
        if(friendUUID == null)
        {
            MessageManager.SendChatForm(player);
            MessageManager.SendChatContent(player, "존재하지 않는 플레이어 입니다.", TextColor.color(255, 0, 0));
            MessageManager.SendChatForm(player);
            return false;
        }

        FishingAdventure.getChunkManager().addFriendChunk(friendUUID, player.getUniqueId());

        return false;
    }
}
