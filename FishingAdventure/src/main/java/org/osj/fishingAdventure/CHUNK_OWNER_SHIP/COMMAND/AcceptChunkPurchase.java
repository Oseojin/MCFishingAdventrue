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
import org.osj.fishingAdventure.FishingAdventure;

public class AcceptChunkPurchase implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;
        ItemStack ticket = player.getInventory().getItemInMainHand();
        if(!ticket.getType().equals(Material.PAPER))
        {
            return false;
        }
        if(!ChunkManager.purchaseWaitList.containsKey(player))
        {
            return false;
        }

        MessageManager.SendChatForm(player);
        MessageManager.SendChatContent(player, "청크를 성공적으로 구매하였습니다!", TextColor.color(0, 255, 0));
        MessageManager.SendChatForm(player);

        ticket.add(-1);
        FishingAdventure.getChunkManager().addMyChunk(player.getUniqueId(), ChunkManager.purchaseWaitList.get(player));
        ChunkManager.purchaseWaitList.remove(player);
        return false;
    }
}
