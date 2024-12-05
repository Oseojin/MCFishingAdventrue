package org.osj.fishingAdventure.WORLD;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class WorldManager
{
    public static String rest_world = "rest_world";
    public static Location restWorldSpawnLoc;
    public static String wild = "wild";
    public static String nether = "wild_nether";
    public static String end = "wild_the_end";
    public static List<String> blueList = new ArrayList<>(Arrays.asList("normal_blue", "rare_blue", "ancient_blue", "overload_blue", "legend_blue", "myth_blue"));
    public static List<Location> blueSpawnLocList = new ArrayList<>();
    public static List<Component> blueEnterMainTitleList = new ArrayList<>();
    public static List<Component> blueEnterSubTitleList = new ArrayList<>();
    public static Component restWorldMainTitle;
    public static Component restWorldSubTitle;

    public WorldManager()
    {
        restWorldSpawnLoc = Bukkit.getWorld(rest_world).getSpawnLocation();
        blueSpawnLocList.add(Bukkit.getWorld(blueList.get(0)).getSpawnLocation());
        blueSpawnLocList.add(Bukkit.getWorld(blueList.get(1)).getSpawnLocation());
        blueSpawnLocList.add(Bukkit.getWorld(blueList.get(2)).getSpawnLocation());
        blueSpawnLocList.add(Bukkit.getWorld(blueList.get(3)).getSpawnLocation());
        blueSpawnLocList.add(Bukkit.getWorld(blueList.get(4)).getSpawnLocation());
        blueSpawnLocList.add(Bukkit.getWorld(blueList.get(5)).getSpawnLocation());

        blueEnterMainTitleList.add(Component.text("망망대해").color(TextColor.color(85,85,85)).decorate(TextDecoration.BOLD));
        blueEnterSubTitleList.add(Component.text("개요의 원양어선").color(TextColor.color(170,170,170)));

        blueEnterMainTitleList.add(Component.text("거인의 손자국 섬").color(TextColor.color(85,255,255)).decorate(TextDecoration.BOLD));
        blueEnterSubTitleList.add(Component.text("바다 한가운데 생겨난 신비한 섬").color(TextColor.color(170,170,170)));

        blueEnterMainTitleList.add(Component.text("푸른별의 옛 호수").color(TextColor.color(0,170,0)).decorate(TextDecoration.BOLD));
        blueEnterSubTitleList.add(Component.text("과거의 지구").color(TextColor.color(170,170,170)));

        blueEnterMainTitleList.add(Component.text("제왕의 안식처").color(TextColor.color(170,0,0)).decorate(TextDecoration.BOLD));
        blueEnterSubTitleList.add(Component.text("한 시대를 풍미한 생명체들의 안식처").color(TextColor.color(170,170,170)));

        blueEnterMainTitleList.add(Component.text("별이 잠든 나무").color(TextColor.color(255,170,0)).decorate(TextDecoration.BOLD));
        blueEnterSubTitleList.add(Component.text("세계수의 가호 아래 수많은 이야기가 퍼져나간 원천").color(TextColor.color(170,170,170)));

        blueEnterMainTitleList.add(Component.text("증명의 바다").color(TextColor.color(255,255,85)).decorate(TextDecoration.BOLD));
        blueEnterSubTitleList.add(Component.text("끝을 볼 자격").color(TextColor.color(170,170,170)));

        restWorldMainTitle = Component.text("초승달 섬").color(TextColor.color(255,255,255)).decorate(TextDecoration.BOLD);
        restWorldSubTitle = Component.text("어부들의 안식처").color(TextColor.color(170,170,170));
    }
}
