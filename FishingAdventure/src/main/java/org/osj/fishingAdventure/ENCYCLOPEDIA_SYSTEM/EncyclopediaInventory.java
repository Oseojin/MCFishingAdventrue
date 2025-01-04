package org.osj.fishingAdventure.ENCYCLOPEDIA_SYSTEM;

import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.osj.fishingAdventure.CUSTOMITEMS.CustomItemManager;
import org.osj.fishingAdventure.FISHING_SYSTEM.FishingManager;
import org.osj.fishingAdventure.FishingAdventure;

import java.util.List;
import java.util.UUID;

public class EncyclopediaInventory implements Listener
{
    private Inventory inv;
    private FileConfiguration encyclopediaConfig = FishingAdventure.getConfigManager().getConfig("encyclopedia");
    private FileConfiguration encyclopediastackConfig = FishingAdventure.getConfigManager().getConfig("encyclopediastack");

    private void initItemSetting(Player player, int grade)
    {
        CustomStack fishShadow = FishingAdventure.getCustomItemManager().getFishShadow();
        CustomStack rightBtn = FishingAdventure.getCustomItemManager().getIconRightBlue();
        CustomStack leftBtn = FishingAdventure.getCustomItemManager().getIconLeftBlue();

        UUID uuid = player.getUniqueId();

        List<CustomStack> customFishList = switch (grade)
        {
            case 2 -> FishingAdventure.getCustomItemManager().getRareFishList();
            case 3 -> FishingAdventure.getCustomItemManager().getAncientFishList();
            case 4 -> FishingAdventure.getCustomItemManager().getOverlordFishList();
            case 5 -> FishingAdventure.getCustomItemManager().getLegendFishList();
            case 6 -> FishingAdventure.getCustomItemManager().getMythFishList();
            default -> FishingAdventure.getCustomItemManager().getNormalFishList();
        };
        int offset = switch (grade)
        {
            case 2 -> 34;
            case 3 -> 54;
            case 4 -> 64;
            case 5 -> 71;
            case 6 -> 76;
            default -> 0;
        };
        for(int i = customFishList.size() - 1; i >= 0; i--)
        {
            String path = "players."+uuid+"."+(i+offset);
            if(encyclopediaConfig.getBoolean(path))
            {
                CustomStack encyclopediaFish = CustomItemManager.makeCopy(customFishList.get(customFishList.size() - 1 - i));
                List<Component> loreList = encyclopediaFish.getItemStack().lore();
                loreList.add(Component.text("잡은 마리: " + encyclopediastackConfig.getInt(path)));
                encyclopediaFish.getItemStack().lore(loreList);
                inv.setItem(i, encyclopediaFish.getItemStack());
            }
            else
            {
                inv.setItem(i, fishShadow.getItemStack());
            }
        }

        if(grade != 6)
        {
            inv.setItem(50, rightBtn.getItemStack());
        }
        if(grade != 1)
        {
            inv.setItem(48, leftBtn.getItemStack());
        }
    }

    public EncyclopediaInventory()
    {
        Component invName = Component.text("Encyclopedia").color(TextColor.color(255, 255, 255));
        this.inv = Bukkit.createInventory(null, 54, invName);
    }

    public void open(Player player, int currentPage)
    {
        inv.clear();
        initItemSetting(player, currentPage);
        player.openInventory(inv);
    }

    @EventHandler
    public void onClickInv(InventoryClickEvent event)
    {
        if(event.getClickedInventory() == null)
        {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().contains("Encyclopedia"))
        {
            event.setCancelled(true);
            UUID uuid = player.getUniqueId();
            ItemStack clickedItem = event.getCurrentItem();
            CustomStack clickedCustom = CustomStack.byItemStack(clickedItem);
            if(clickedItem != null && clickedCustom != null)
            {
                if(clickedCustom.getPermission().equals("ia.fishing_adventure.left_btn"))
                {
                    inv.close();
                    open(player, currentPage(inv) - 1);
                    return;
                }
                if(clickedCustom.getPermission().equals("ia.fishing_adventure.right_btn"))
                {
                    inv.close();
                    open(player, currentPage(inv) + 1);
                    return;
                }
                if(!event.getClickedInventory().equals(player.getInventory()))
                {
                    return;
                }
                if(!FishingManager.isFish(clickedCustom.getPermission()))
                {
                    return;
                }
                int index = FishingAdventure.getCustomItemManager().getFishNum(clickedCustom) - 1;
                if(encyclopediaConfig.getBoolean("players."+uuid+"."+index))
                {
                    return;
                }
                String lastLore = ((TextComponent)clickedItem.lore().getLast()).content();
                if(lastLore.equals("물고기가 무언가 물고 있습니다."))
                {
                    player.sendMessage(Component.text("우선 물고기가 물고 있는 것을 빼내세요!").color(TextColor.color(255, 12, 0)));
                    return;
                }
                encyclopediaConfig.set("players."+uuid+"."+index, true);
                FishingAdventure.getConfigManager().saveConfig("encyclopedia");
                clickedItem.add(-1);
                inv.close();
                int grade = FishingManager.getFishGrade(clickedCustom.getPermission());
                FishingAdventure.getFishingManager().onPlayerGetPoint(player, 100 * grade);
                player.sendMessage(Component.text("신규 물고기 등록!! 포인트 +" + 100 * grade).color(TextColor.color(0, 214, 255)));
                open(player, grade);
            }
        }
    }

    private int currentPage(Inventory currInv)
    {
        int count = 0;
        for(ItemStack stack : currInv.getContents())
        {
            if(stack == null)
            {
                continue;
            }
            count++;
        }
        return switch (count)
        {
            case 22 -> 2;
            case 12 -> 3;
            case 9 -> 4;
            case 7 -> 5;
            case 4 -> 6;
            default -> 1;
        };
    }
}
