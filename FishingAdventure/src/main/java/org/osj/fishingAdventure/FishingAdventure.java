package org.osj.fishingAdventure;

import org.bukkit.plugin.java.JavaPlugin;
import org.osj.fishingAdventure.CHUNK_OWNER_SHIP.COMMAND.*;
import org.osj.fishingAdventure.CHUNK_OWNER_SHIP.EVENT.PlayerInteractInChunk;
import org.osj.fishingAdventure.CHUNK_OWNER_SHIP.EVENT.SpawnProtect;
import org.osj.fishingAdventure.CUSTOMITEMS.CustomItemManager;
import org.osj.fishingAdventure.CHUNK_OWNER_SHIP.ChunkManager;
import org.osj.fishingAdventure.CHUNK_OWNER_SHIP.EVENT.UseChunkPurchaseTicket;
import org.osj.fishingAdventure.CHUNK_OWNER_SHIP.EVENT.UseChunkRemoveTicket;
import org.osj.fishingAdventure.DATA_MANAGEMENT.ConfigManager;
import org.osj.fishingAdventure.ENCYCLOPEDIA_SYSTEM.COMMAND.OpenEncyclopedia;
import org.osj.fishingAdventure.ENCYCLOPEDIA_SYSTEM.EVENT.EncyclopediaEvent;
import org.osj.fishingAdventure.ENCYCLOPEDIA_SYSTEM.EncyclopediaInventory;
import org.osj.fishingAdventure.FISHING_SYSTEM.EVENT.*;
import org.osj.fishingAdventure.FISHING_SYSTEM.FishingManager;
import org.osj.fishingAdventure.FISHING_SYSTEM.RankingManager;
import org.osj.fishingAdventure.MESSAGE.PlayerScoreboardManager;
import org.osj.fishingAdventure.WORLD.COMMAND.SpawnCommand;
import org.osj.fishingAdventure.WORLD.COMMAND.TestCommand;
import org.osj.fishingAdventure.WORLD.EVENT.PortalTeleport;
import org.osj.fishingAdventure.WORLD.EVENT.UseKey;
import org.osj.fishingAdventure.WORLD.EVENT.WhiteList;
import org.osj.fishingAdventure.WORLD.EVENT.WorldChangeExit;
import org.osj.fishingAdventure.WORLD.WorldManager;

public final class FishingAdventure extends JavaPlugin
{
    private static FishingAdventure serverInstance;
    private static ConfigManager configManager;
    private static WorldManager worldManager;
    private static CustomItemManager customItemManager;
    private static ChunkManager chunkManager;
    private static FishingManager fishingManager;
    private static RankingManager rankingManager;

    @Override
    public void onEnable()
    {
        serverInstance = this;
        configManager = new ConfigManager();
        worldManager = new WorldManager();
        rankingManager = new RankingManager();
        customItemManager = new CustomItemManager();
        chunkManager = new ChunkManager();
        fishingManager = new FishingManager();

        registerEvent();
        registerCommand();
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
    }

    private void registerEvent()
    {
        getServer().getPluginManager().registerEvents(customItemManager, serverInstance);
        getServer().getPluginManager().registerEvents(chunkManager, serverInstance);
        getServer().getPluginManager().registerEvents(fishingManager, serverInstance);
        getServer().getPluginManager().registerEvents(new UseChunkPurchaseTicket(), serverInstance);
        getServer().getPluginManager().registerEvents(new UseChunkRemoveTicket(), serverInstance);
        getServer().getPluginManager().registerEvents(new UseFishingRod(), serverInstance);
        getServer().getPluginManager().registerEvents(new UseFish(), serverInstance);
        getServer().getPluginManager().registerEvents(new PlayerScoreboardManager(), serverInstance);
        getServer().getPluginManager().registerEvents(new PlayerInteractInChunk(), serverInstance);
        getServer().getPluginManager().registerEvents(new UseWildTicket(), serverInstance);
        getServer().getPluginManager().registerEvents(new UseInviteTicket(), serverInstance);
        getServer().getPluginManager().registerEvents(new WhiteList(), serverInstance);
        getServer().getPluginManager().registerEvents(new ItemCraft(), serverInstance);
        getServer().getPluginManager().registerEvents(new SpawnProtect(), serverInstance);
        getServer().getPluginManager().registerEvents(new EncyclopediaEvent(), serverInstance);
        getServer().getPluginManager().registerEvents(new EncyclopediaInventory(), serverInstance);
        getServer().getPluginManager().registerEvents(new PortalTeleport(), serverInstance);
        getServer().getPluginManager().registerEvents(new UseKey(), serverInstance);
        getServer().getPluginManager().registerEvents(new WorldChangeExit(), serverInstance);
        getServer().getPluginManager().registerEvents(new PreventPVP(), serverInstance);
    }
    private void registerCommand()
    {
        serverInstance.getServer().getPluginCommand("purchasechunkaccept").setExecutor(new AcceptChunkPurchase());
        serverInstance.getServer().getPluginCommand("purchasechunkreject").setExecutor(new RejectChunkPurchase());
        serverInstance.getServer().getPluginCommand("removechunkaccept").setExecutor(new AcceptChunkRemove());
        serverInstance.getServer().getPluginCommand("removechunkreject").setExecutor(new RejectChunkRemove());
        serverInstance.getServer().getPluginCommand("addallowchunk").setExecutor(new AddChunkAllow());
        serverInstance.getServer().getPluginCommand("removeallowchunk").setExecutor(new RemoveChunkAllow());
        serverInstance.getServer().getPluginCommand("encyclopedia").setExecutor(new OpenEncyclopedia());
        serverInstance.getServer().getPluginCommand("spawn").setExecutor(new SpawnCommand());
        serverInstance.getServer().getPluginCommand("test").setExecutor(new TestCommand());
    }

    public static FishingAdventure getServerInstance()
    {
        return serverInstance;
    }
    public static ConfigManager getConfigManager()
    {
        return configManager;
    }
    public static WorldManager getWorldManager()
    {
        return worldManager;
    }
    public static CustomItemManager getCustomItemManager()
    {
        return customItemManager;
    }
    public static ChunkManager getChunkManager()
    {
        return chunkManager;
    }
    public static FishingManager getFishingManager()
    {
        return fishingManager;
    }
    public static RankingManager getRankingManager()
    {
        return rankingManager;
    }
}
