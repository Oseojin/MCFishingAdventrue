package org.osj.fishingAdventure.CHUNK_OWNER_SHIP.EVENT;

import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.osj.fishingAdventure.MESSAGE.MessageManager;
import org.osj.fishingAdventure.CHUNK_OWNER_SHIP.ChunkManager;
import org.osj.fishingAdventure.FishingAdventure;
import org.osj.fishingAdventure.WORLD.WorldManager;

public class UseChunkPurchaseTicket implements Listener
{
    @EventHandler
    public void onUseChunkPurchaseItem(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        ItemStack playerHand = player.getInventory().getItemInMainHand();
        CustomStack ticket = CustomStack.byItemStack(playerHand);
        if(ticket == null)
        {
            return;
        }

        if(event.getAction().isRightClick() && ticket.getPermission().contains("chunkpurchaseticket"))
        {
            if(!player.getWorld().getName().equals(WorldManager.rest_world))
            {
                MessageManager.SendChatForm(player);
                MessageManager.SendChatContent(player, "초승달 섬에서만 청크를 소유할 수 있습니다!", TextColor.color(255, 0, 0));
                MessageManager.SendChatForm(player);

                return;
            }
            if(-3 <= player.getChunk().getX() && player.getChunk().getX() <= 3 && -3 <= player.getChunk().getZ() && player.getChunk().getZ() <= 3)
            {
                MessageManager.SendChatForm(player);
                MessageManager.SendChatContent(player, "초승달 섬 스폰에서 너무 가깝습니다!", TextColor.color(255, 0, 0));
                MessageManager.SendChatForm(player);
                
                return;
            }
            if(!FishingAdventure.getChunkManager().isOwnerless(player.getChunk().getChunkKey()))
            {
                MessageManager.SendChatForm(player);
                MessageManager.SendChatContent(player, "이미 누군가가 소유중인 청크 입니다!", TextColor.color(255, 0, 0));
                MessageManager.SendChatForm(player);

                return;
            }
            if(!ChunkManager.purchaseWaitList.containsKey(player))
            {
                ChunkManager.purchaseWaitList.put(player, player.getChunk());
            }

            MessageManager.SendChatForm(player);

            MessageManager.SendChatContent(player, "청크소유티켓을 사용하여 해당 청크를 소유하시겠습니까?", TextColor.color(255, 255, 0));

            Component acceptMessage = Component.text("[수락]").hoverEvent(HoverEvent.showText(Component.text("클릭하면 청크 소유가 확정됩니다.")))
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/purchasechunkaccept")).color(TextColor.color(0, 255, 0)).decorate(TextDecoration.BOLD);
            Component rejectMessage = Component.text("[거절]").hoverEvent(HoverEvent.showText(Component.text("클릭하면 청크 소유가 취소됩니다.")))
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/purchasechunkreject")).color(TextColor.color(255, 0, 0)).decorate(TextDecoration.BOLD);
            player.sendMessage(acceptMessage);
            player.sendMessage(rejectMessage);

            MessageManager.SendChatForm(player);
        }
    }


}
