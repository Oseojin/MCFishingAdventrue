package org.osj.fishingAdventure.WORLD.EVENT;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.osj.fishingAdventure.FishingAdventure;

import java.util.List;
import java.util.UUID;

public class PlayerNum implements Listener
{
    private static FileConfiguration playernumConfig = FishingAdventure.getConfigManager().getConfig("playernum");

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        List<String> playerList = playernumConfig.getStringList("players");
        if(!playerList.contains(uuid.toString()))
        {
            playerList.add(uuid.toString());
            playernumConfig.set("players", playerList);
            FishingAdventure.getConfigManager().saveConfig("playernum");

            int playerNum = playernumConfig.getStringList("players").indexOf(player.getUniqueId().toString());

            Location chestLoc1 = PlayerStructureMode.chestStartLoc.clone().add(playerNum * 2, 0, 0);
            Location chestLoc2 = PlayerStructureMode.chestStartLoc.clone().add(playerNum * 2 + 1, 0, 0);

            chestLoc1.getBlock().setType(Material.CHEST);
            chestLoc2.getBlock().setType(Material.CHEST);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if(!player.hasPlayedBefore())
        {
            player.getInventory().addItem(CustomStack.getInstance("fishing_adventure:chunkpurchaseticket").getItemStack());
            player.getInventory().addItem(FishingAdventure.getCustomItemManager().getRod(1).getItemStack());
            player.getInventory().addItem(FishingAdventure.getCustomItemManager().getKeyList().get(0).getItemStack());

        }
        PlayerStructureMode.ChangeStructureMode(player);
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                PlayerStructureMode.ChangeFishingMode(player);
            }
        }.runTaskLater(FishingAdventure.getServerInstance(), 20L);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        if(player.getGameMode().equals(GameMode.CREATIVE))
        {
            PlayerStructureMode.ChangeFishingMode(player);
        }
    }
}
