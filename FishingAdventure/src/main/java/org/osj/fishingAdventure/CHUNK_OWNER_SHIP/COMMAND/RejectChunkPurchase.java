package org.osj.fishingAdventure.CHUNK_OWNER_SHIP.COMMAND;

import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.osj.fishingAdventure.MESSAGE.MessageManager;
import org.osj.fishingAdventure.CHUNK_OWNER_SHIP.ChunkManager;

public class RejectChunkPurchase implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;
        ItemStack playerHand = player.getInventory().getItemInMainHand();
        CustomStack ticket = CustomStack.byItemStack(playerHand);
        if(ticket == null)
        {
            MessageManager.SendChatContent(player, "청크 소유 티켓을 손에 들어주세요!", TextColor.color(255, 0, 17));
            return false;
        }
        if(!ticket.getPermission().contains("chunkpurchaseticket"))
        {
            MessageManager.SendChatContent(player, "청크 소유 티켓을 손에 들어주세요!", TextColor.color(255, 0, 17));
            return false;
        }
        if(!ChunkManager.purchaseWaitList.containsKey(player))
        {
            return false;
        }

        MessageManager.SendChatContent(player, "청크 소유를 취소하였습니다!", TextColor.color(255, 0, 0));

        ChunkManager.purchaseWaitList.remove(player);
        return false;
    }
}
