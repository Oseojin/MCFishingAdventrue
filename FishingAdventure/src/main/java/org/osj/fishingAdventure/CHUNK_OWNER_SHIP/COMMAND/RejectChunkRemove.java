package org.osj.fishingAdventure.CHUNK_OWNER_SHIP.COMMAND;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.osj.fishingAdventure.CHUNK_OWNER_SHIP.CHAT.MessageManager;
import org.osj.fishingAdventure.CHUNK_OWNER_SHIP.ChunkManager;

public class RejectChunkRemove implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;
        ItemStack ticket = player.getInventory().getItemInMainHand();
        if(!ticket.getType().equals(Material.BOOK))
        {
            return false;
        }
        if(!ChunkManager.removeWaitList.containsKey(player))
        {
            return false;
        }

        MessageManager.SendChatForm(player);
        MessageManager.SendChatContent(player, "청크 제거를 취소하였습니다!", TextColor.color(255, 0, 0));
        MessageManager.SendChatForm(player);

        ChunkManager.removeWaitList.remove(player);
        return false;
    }
}
