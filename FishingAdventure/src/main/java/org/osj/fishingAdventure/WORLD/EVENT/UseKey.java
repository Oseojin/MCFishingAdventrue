package org.osj.fishingAdventure.WORLD.EVENT;

import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.CustomStack;
import io.papermc.paper.event.player.PlayerItemFrameChangeEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.GlowItemFrame;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.osj.fishingAdventure.FishingAdventure;
import org.osj.fishingAdventure.MESSAGE.MessageManager;
import org.osj.fishingAdventure.WORLD.WorldManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UseKey implements Listener
{
    public static FileConfiguration lastLocConfig = FishingAdventure.getConfigManager().getConfig("lastloc");
    @EventHandler
    public void onUseKey(PlayerItemFrameChangeEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        ItemStack handItem = (player.getInventory().getItemInMainHand());
        Chunk chunk = event.getItemFrame().getChunk();

        if(!FishingAdventure.getChunkManager().canInteractChunk(player, chunk))
        {
            event.setCancelled(true);
        }
        if(!(event.getItemFrame() instanceof GlowItemFrame))
        {
            return;
        }

        String worldName = player.getWorld().getName();
        int index = -10;
        String correctKey = "";
        switch (worldName)
        {
            case "rest_world":
                correctKey = "ia.fishing_adventure.normal_key";
                index = 0;
                break;
            case "normal_blue":
                correctKey = "ia.fishing_adventure.rare_key";
                index = 1;
                break;
            case "rare_blue":
                correctKey = "ia.fishing_adventure.ancient_key";
                index = 2;
                break;
            case "ancient_blue":
                correctKey = "ia.fishing_adventure.overload_key";
                index = 3;
                break;
            case "overload_blue":
                correctKey = "ia.fishing_adventure.legend_key";
                index = 4;
                break;
            case "legend_blue":
                correctKey = "ia.fishing_adventure.myth_key";
                index = 5;
                break;
        }
        Location teleportLoc = WorldManager.blueSpawnLocList.get(index);

        if(lastLocConfig.getInt("players." + uuid) >= index)
        {
            player.teleport(teleportLoc);
            if(player.getGameMode().equals(GameMode.CREATIVE))
            {
                PlayerStructureMode.ChangeFishingMode(player);
            }
            return;
        }

        CustomStack key = CustomStack.byItemStack(handItem);
        if(key == null)
        {
            Component actionConponent = Component.text("올바른 열쇠를 사용하면 다음 지역으로 이동할 수 있습니다.").color(TextColor.color(100, 233, 255)).decorate(TextDecoration.BOLD);
            player.sendActionBar(actionConponent);
            return;
        }

        if(key.getPermission().equals(correctKey))
        {
            lastLocConfig.set("players." + uuid, index);
            FishingAdventure.getConfigManager().saveConfig("lastloc");
            player.teleport(teleportLoc);
            handItem.add(-1);
            if(player.getGameMode().equals(GameMode.CREATIVE))
            {
                PlayerStructureMode.ChangeFishingMode(player);
            }
        }
        else
        {
            Component actionConponent = Component.text("올바른 열쇠가 아닙니다!").color(TextColor.color(255, 13, 0)).decorate(TextDecoration.BOLD);
            player.sendActionBar(actionConponent);
        }
    }
}
