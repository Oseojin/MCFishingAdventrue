package org.osj.fishingAdventure.FISHING_SYSTEM.EVENT;

import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.osj.fishingAdventure.FishingAdventure;
import org.osj.fishingAdventure.WORLD.WorldManager;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class UseWildTicket implements Listener
{
    Integer wildTime = 30 * 60;
    HashMap<UUID, Integer> wildTimerMap = new HashMap<>();
    HashMap<UUID, Location> originLocMap = new HashMap<>();

    @EventHandler
    public void onUseWildTicket(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        ItemStack playerHand = player.getInventory().getItemInMainHand();
        CustomStack ticket = CustomStack.byItemStack(playerHand);
        if(ticket == null)
        {
            return;
        }
        if(event.getAction().isRightClick() && ticket.getPermission().contains("wildteleportticket"))
        {
            playerHand.add(-1);
            if(player.getWorld().getName().equals(WorldManager.wild)) // 이미 야생 맵에 있으면
            {
                wildTimerMap.put(player.getUniqueId(), wildTime); // 시간 초기화
                return;
            }
            originLocMap.put(player.getUniqueId(), player.getLocation());
            wildTimerMap.put(player.getUniqueId(), wildTime);

            Random random = new Random();
            double randomX = random.nextDouble(-5000.0, 5000.0);
            double randomZ = random.nextDouble(-5000.0, 5000.0);

            Location newLocation = new Location(Bukkit.getWorld(WorldManager.wild), randomX, 100, randomZ);
            player.teleport(newLocation);
            player.addPotionEffect(PotionEffectType.SLOW_FALLING.createEffect(200, 1));
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    TextColor timerTextColor = TextColor.color(0, 255, 0);
                    int leastTime = wildTimerMap.get(player.getUniqueId()) - 1;
                    if(leastTime <= 0)
                    {
                        player.teleport(originLocMap.get(player.getUniqueId()));
                        cancel();
                    }
                    wildTimerMap.put(player.getUniqueId(), leastTime);
                    if(wildTime / 4 >= leastTime)
                    {
                        timerTextColor = TextColor.color(255, 0, 0);
                    }
                    else if(wildTime / 2 >= leastTime)
                    {
                        timerTextColor = TextColor.color(255, 255, 0);
                    }
                    Component actionComponent = Component.text( (leastTime / 60 + ":" + leastTime % 60)).color(timerTextColor);
                    player.sendActionBar(actionComponent);
                }
            }.runTaskTimer(FishingAdventure.getServerInstance(), 20L, 20L);
        }
    }
}
