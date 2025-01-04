package org.osj.fishingAdventure.FISHING_SYSTEM.EVENT;

import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.osj.fishingAdventure.CUSTOMITEMS.CustomItemManager;
import org.osj.fishingAdventure.FISHING_SYSTEM.FishingManager;
import org.osj.fishingAdventure.FishingAdventure;

import java.util.List;
import java.util.Random;

public class UseFish implements Listener
{
    @EventHandler
    public void UseCaughtFish(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Action action = event.getAction();
        CustomStack fishCustom = CustomStack.byItemStack(player.getInventory().getItemInMainHand());
        if(fishCustom != null && action.isLeftClick())
        {
            if(FishingManager.isFish(fishCustom.getPermission()))
            {
                int grade = FishingManager.getFishGrade(fishCustom.getPermission());
                int fishNum = FishingAdventure.getCustomItemManager().getFishNum(fishCustom);
                if(((TextComponent)fishCustom.getItemStack().getItemMeta().lore().getLast()).content().equals("물고기가 무언가 물고 있습니다."))
                {
                    CustomStack gemstone = FishingAdventure.getCustomItemManager().getItemInFishList(grade);
                    player.getInventory().addItem(gemstone.getItemStack());
                    if(fishCustom.getItemStack().getAmount() >= 2)
                    {
                        player.getInventory().getItemInMainHand().add(-1);
                        CustomStack newFish = CustomStack.getInstance(fishCustom.getNamespacedID());
                        ItemMeta fishMeta = fishCustom.getItemStack().getItemMeta();
                        List<Component> fishLoreList = fishMeta.lore();
                        fishLoreList.removeLast();
                        fishMeta.lore(fishLoreList);
                        newFish.getItemStack().setItemMeta(fishMeta);
                        player.getInventory().addItem(newFish.getItemStack());
                    }
                    else
                    {
                        ItemMeta fishMeta = fishCustom.getItemStack().getItemMeta();
                        List<Component> fishLoreList = fishMeta.lore();
                        fishLoreList.removeLast();
                        fishMeta.lore(fishLoreList);
                        player.getInventory().getItemInMainHand().setItemMeta(fishMeta);
                    }
                }
                else
                {
                    FishingAdventure.getFishingManager().onPlayerGetPoint(player, fishNum * grade);
                    player.getInventory().getItemInMainHand().add(-1);
                }
            }
        }
    }
}
