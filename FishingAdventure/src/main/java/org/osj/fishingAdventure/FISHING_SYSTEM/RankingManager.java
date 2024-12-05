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
                developerUUID = Bukkit.getPlayer("Happy_Kkyulmik").getUniqueId().toString();
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
                    if(uuidSet.equals(developerUUID))
                    {
                        continue;
                    }
                    int point = FishingManager.fishingPointConfig.getInt("players." + uuidString);
                    rankingMap.put(UUID.fromString(uuidString), point);
                }
                List<UUID> keySet = new ArrayList<>(rankingMap.keySet());
                keySet.sort((o1, o2) -> rankingMap.get(o2).compareTo(rankingMap.get(o1)));
                Component announceComponent = Component.text("랭킹이 업데이트 되었습니다!").color(TextColor.color(0, 231, 255)).decorate(TextDecoration.BOLD);
                for(Player player : Bukkit.getOnlinePlayers())
                {
                    player.sendMessage(announceComponent);
                }
            }
        }.runTaskTimer(FishingAdventure.getServerInstance(), 20L, 10L * 60L * 20L);
    }
}
