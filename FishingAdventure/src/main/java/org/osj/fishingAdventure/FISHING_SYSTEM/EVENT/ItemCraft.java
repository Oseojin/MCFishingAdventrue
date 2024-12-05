package org.osj.fishingAdventure.FISHING_SYSTEM.EVENT;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.osj.fishingAdventure.FishingAdventure;

public class ItemCraft implements Listener
{
    @EventHandler
    public void onPlayerCraftFishingRod(CraftItemEvent event)
    {
        ItemStack craftItem = event.getInventory().getResult();
        if(craftItem == null)
        {
            return;
        }
        if(craftItem.getType().equals(Material.FISHING_ROD))
        {
            event.getInventory().setResult(FishingAdventure.getCustomItemManager().getRod(1).getItemStack());
        }
    }
}
