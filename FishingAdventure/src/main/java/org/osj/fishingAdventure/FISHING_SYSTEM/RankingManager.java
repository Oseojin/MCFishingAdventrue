package org.osj.fishingAdventure.FISHING_SYSTEM;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.osj.fishingAdventure.FishingAdventure;

import java.util.*;

public class RankingManager
{
    public static HashMap<UUID, Integer> rankingMap = new HashMap<UUID, Integer>();
    private String developerUUID;
    public RankingManager()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                developerUUID = Bukkit.getOfflinePlayer("MoonAroma04").getUniqueId().toString();
                Set<String> uuidSet = FishingManager.fishingPointConfig.getKeys(true);
                if(uuidSet.isEmpty())
                {
                    return;
                }
                Iterator<String> stringIterator = uuidSet.iterator();
                stringIterator.next();
                while(stringIterator.hasNext())
                {
                    String uuidString = stringIterator.next().replace("players.", "");
                    int point = FishingManager.fishingPointConfig.getInt("players." + uuidString);
                    Bukkit.getConsoleSender().sendMessage(Bukkit.getOfflinePlayer(UUID.fromString(uuidString)).getName() + " " + point);
                    if(uuidString.equals(developerUUID))
                    {
                        point = -1;
                    }
                    rankingMap.put(UUID.fromString(uuidString), point);
                }
                List<UUID> keySet = new ArrayList<>(rankingMap.keySet());
                keySet.sort((o1, o2) -> rankingMap.get(o2).compareTo(rankingMap.get(o1)));
            }
        }.runTaskTimer(FishingAdventure.getServerInstance(), 20L, 20L * 5L);
    }
}
