package org.osj.fishingAdventure.FISHING_SYSTEM.EVENT;

import dev.lone.itemsadder.api.CustomStack;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.osj.fishingAdventure.DATA_MANAGEMENT.ConfigManager;
import org.osj.fishingAdventure.FISHING_SYSTEM.FishingManager;
import org.osj.fishingAdventure.FishingAdventure;
import org.osj.fishingAdventure.MESSAGE.MessageManager;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class UseInviteTicket implements Listener
{
    FileConfiguration whiteListConfig = FishingAdventure.getConfigManager().getConfig("whitelist");
    public List<UUID> useInviteTicketList = new LinkedList<>();
    @EventHandler
    public void onUseInviteTicket(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        ItemStack playerHand = player.getInventory().getItemInMainHand();
        CustomStack ticket = CustomStack.byItemStack(playerHand);
        if (ticket == null)
        {
            return;
        }
        if (event.getAction().isRightClick() && ticket.getPermission().contains("inviteticket"))
        {
            MessageManager.SendChatContent(player, "초대할 플레이어 이름을 입력해 주십시요. \"취소\"라고 입력하면 취소됩니다.", TextColor.color(0, 226, 255));
            useInviteTicketList.add(player.getUniqueId());
        }
    }

    @EventHandler
    public void onChatPlayerName(AsyncChatEvent event)
    {
        Player player = event.getPlayer();
        String inviteName = ((TextComponent)event.message()).content();

        if(inviteName.equals("취소"))
        {
            MessageManager.SendChatContent(player, "취소되었습니다.", TextColor.color(255, 10, 0));
            event.setCancelled(true);
            return;
        }

        UUID inviteUUID = Bukkit.getPlayerUniqueId(inviteName);
        if(!useInviteTicketList.contains(player.getUniqueId()))
        {
            return;
        }
        event.setCancelled(true);
        useInviteTicketList.remove(player.getUniqueId());

        // 이미 초대되어 있거나, 없는 플레이어면 리턴
        if(inviteUUID == null)
        {
            MessageManager.SendChatContent(player, "잘못된 사용자 이름입니다.", TextColor.color(255, 10, 0));
            return;
        }
        if(whiteListConfig.contains("players." + inviteUUID))
        {
            MessageManager.SendChatContent(player, "이미 초대되어 있는 플레이어 입니다.", TextColor.color(255, 10, 0));
            return;
        }

        Inventory playerInv = player.getInventory();
        ItemStack ticket;
        for(ItemStack inPlayerInv : playerInv.getContents())
        {
            if(inPlayerInv == null)
            {
                continue;
            }
            CustomStack customItem = CustomStack.byItemStack(inPlayerInv);
            if(customItem == null)
            {
                continue;
            }
            if(customItem.getPermission().contains("inviteticket"))
            {
                ticket = inPlayerInv;
                ticket.add(-1);
            }
        }
        whiteListConfig.set("players."+inviteUUID, true);
        FishingAdventure.getConfigManager().saveConfig("whitelist");
        MessageManager.SendChatContent(player, "성공적으로 초대되었습니다!", TextColor.color(60, 255, 0));
    }
}
