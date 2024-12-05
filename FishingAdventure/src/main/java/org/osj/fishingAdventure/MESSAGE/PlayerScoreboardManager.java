package org.osj.fishingAdventure.MESSAGE;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.*;
import org.osj.fishingAdventure.FISHING_SYSTEM.FishingManager;
import org.osj.fishingAdventure.FISHING_SYSTEM.RankingManager;
import org.osj.fishingAdventure.FishingAdventure;

import java.util.List;
import java.util.UUID;

public class PlayerScoreboardManager implements Listener
{
    public PlayerScoreboardManager()
    {
        BukkitScheduler scoreboardUpdateScheduler = Bukkit.getScheduler();
        scoreboardUpdateScheduler.runTaskTimer(FishingAdventure.getServerInstance(), this::updateScoreBoard, 20L, 20L);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        createScoreBoard(event.getPlayer());
    }


    public void createScoreBoard(Player player)
    {
        UUID uuid = player.getUniqueId();
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Component nameComponent = Component.text("MIKMIK4::ALLBLUE").color(TextColor.color(0, 192, 255)).decorate(TextDecoration.BOLD);
        Objective o = board.registerNewObjective("FishingAdventure", Criteria.DUMMY, nameComponent, RenderType.HEARTS);
        //o.displayName(nameComponent);
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score formatScore1 = o.getScore(ChatColor.GRAY + "=================\n");
        formatScore1.setScore(5);
        Score nameScore = o.getScore(ChatColor.YELLOW + player.getName());
        nameScore.setScore(4);
        Score levelScore = o.getScore(ChatColor.BLUE + "숙련도:  " + FishingManager.playerFishingLevelMap.get(uuid));
        levelScore.setScore(3);
        Score pointScore = o.getScore( ChatColor.AQUA +"포인트:  " + FishingManager.playerFishingPointMap.get(uuid));
        pointScore.setScore(2);
        List<UUID> rankingList = RankingManager.rankingMap.keySet().stream().toList();
        int rank = rankingList.indexOf(uuid) + 1;
        Score rankingScore = o.getScore(ChatColor.LIGHT_PURPLE + "랭킹  :  " + (rankingList.indexOf(uuid) + 1));;
        rankingScore.setScore(1);
        Score formatScore2 = o.getScore(ChatColor.GRAY + "=================\t");
        formatScore2.setScore(0);
        player.setScoreboard(board);
    }

    public void updateScoreBoard()
    {
        for(Player online : Bukkit.getOnlinePlayers())
        {
            createScoreBoard(online);
        }
    }
}
