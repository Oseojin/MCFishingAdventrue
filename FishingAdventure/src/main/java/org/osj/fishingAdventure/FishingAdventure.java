package org.osj.fishingAdventure;

import org.bukkit.plugin.java.JavaPlugin;
import org.osj.fishingAdventure.CHUNK_OWNER_SHIP.COMMAND.*;
import org.osj.fishingAdventure.CHUNK_OWNER_SHIP.ChunkManager;
import org.osj.fishingAdventure.CHUNK_OWNER_SHIP.EVENT.UseChunkPurchaseTicket;
import org.osj.fishingAdventure.CHUNK_OWNER_SHIP.EVENT.UseChunkRemoveTicket;
import org.osj.fishingAdventure.DATA_MANAGEMENT.ConfigManager;

public final class FishingAdventure extends JavaPlugin
{
    private static FishingAdventure serverInstance;
    private static ConfigManager configManager;
    private static ChunkManager chunkManager;

    @Override
    public void onEnable()
    {
        serverInstance = this;
        configManager = new ConfigManager();
        chunkManager = new ChunkManager();

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
        getServer().getPluginManager().registerEvents(new UseChunkPurchaseTicket(), serverInstance);
        getServer().getPluginManager().registerEvents(new UseChunkRemoveTicket(), serverInstance);
        getServer().getPluginManager().registerEvents(chunkManager, serverInstance);
    }
    private void registerCommand()
    {
        serverInstance.getServer().getPluginCommand("purchasechunkaccept").setExecutor(new AcceptChunkPurchase());
        serverInstance.getServer().getPluginCommand("purchasechunkreject").setExecutor(new RejectChunkPurchase());
        serverInstance.getServer().getPluginCommand("removechunkaccept").setExecutor(new AcceptChunkRemove());
        serverInstance.getServer().getPluginCommand("removechunkreject").setExecutor(new RejectChunkRemove());
        serverInstance.getServer().getPluginCommand("addallowchunk").setExecutor(new AddChunkAllow());
        serverInstance.getServer().getPluginCommand("removeallowchunk").setExecutor(new RemoveChunkAllow());
    }

    public static FishingAdventure getServerInstance()
    {
        return serverInstance;
    }
    public static ConfigManager getConfigManager()
    {
        return configManager;
    }
    public static ChunkManager getChunkManager()
    {
        return chunkManager;
    }
}
