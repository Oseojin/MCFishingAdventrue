package org.osj.fishingAdventure.WORLD.EVENT;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.osj.fishingAdventure.FishingAdventure;
import org.osj.fishingAdventure.WORLD.WorldManager;

import java.util.LinkedList;
import java.util.List;

public class PlayerStructureMode implements CommandExecutor
{
    private static FileConfiguration playernumConfig = FishingAdventure.getConfigManager().getConfig("playernum");
    public static List<Player> playerModeList = new LinkedList<>();
    public static Location chestStartLoc;

    public PlayerStructureMode()
    {
        chestStartLoc = new Location(Bukkit.getWorld(WorldManager.wild), 0, 60, 0);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;

        if(!player.getWorld().getName().equals(WorldManager.rest_world))
        {
            return false;
        }

        if(playerModeList.contains(player))
        {
            ChangeFishingMode(player);
        }
        else
        {
            ChangeStructureMode(player);
        }

        return false;
    }

    public static void ChangeStructureMode(Player player)
    {
        playerModeList.add(player);
        Inventory playerInv = player.getInventory();
        int playerNum = playernumConfig.getStringList("players").indexOf(player.getUniqueId().toString());

        Location chestLoc1 = chestStartLoc.clone().add(playerNum * 2, 0, 0);
        Location chestLoc2 = chestStartLoc.clone().add(playerNum * 2 + 1, 0, 0);

        Chest chest1 = (Chest) chestLoc1.getBlock().getState();
        Chest chest2 = (Chest) chestLoc2.getBlock().getState();

        for(int i = 0; i < 27; i++)
        {
            chest1.getBlockInventory().setItem(i, playerInv.getItem(i));
        }
        for(int i = 0; i < 9; i++)
        {
            chest2.getBlockInventory().setItem(i, playerInv.getItem(i + 27));
        }

        playerInv.clear();
        player.setGameMode(GameMode.CREATIVE);
        Bukkit.getConsoleSender().sendMessage("건축 모드 전환 끝");
    }

    public static void ChangeFishingMode(Player player)
    {
        playerModeList.remove(player);
        player.getInventory().clear();
        Inventory playerInv = player.getInventory();
        int playerNum = playernumConfig.getStringList("players").indexOf(player.getUniqueId().toString());

        Location chestLoc1 = chestStartLoc.clone().add(playerNum * 2, 0, 0);
        Location chestLoc2 = chestStartLoc.clone().add(playerNum * 2 + 1, 0, 0);

        Chest chest1 = (Chest) chestLoc1.getBlock().getState();
        Chest chest2 = (Chest) chestLoc2.getBlock().getState();

        for(int i = 0; i < 27; i++)
        {
            playerInv.setItem(i, chest1.getBlockInventory().getItem(i));
        }
        for(int i = 0; i < 9; i++)
        {
            playerInv.setItem(i + 27, chest2.getBlockInventory().getItem(i));
        }
        player.setGameMode(GameMode.SURVIVAL);
        Bukkit.getConsoleSender().sendMessage("낚시 모드 전환 끝");
    }
}
