package org.osj.fishingAdventure.DATA_MANAGEMENT;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.osj.fishingAdventure.FishingAdventure;

import java.util.HashMap;

public class ConfigManager
{
    private final String path = FishingAdventure.getServerInstance().getDataFolder().getAbsolutePath();

    private HashMap<String, ConfigMaker> configSet = new HashMap<>();


    // config 전체 재시작
    public ConfigManager()
    {
        configSet.put("whitelist", new ConfigMaker(path, "whitelist.yml"));
        configSet.put("chunkownership", new ConfigMaker(path, "chunkownership.yml"));
        configSet.put("fishinglevel", new ConfigMaker(path, "fishinglevel.yml"));
        configSet.put("fishingpoint", new ConfigMaker(path, "fishingpoint.yml"));
        configSet.put("encyclopedia", new ConfigMaker(path, "encyclopedia.yml"));
        configSet.put("encyclopediastack", new ConfigMaker(path, "encyclopediastack.yml"));
        configSet.put("lastloc", new ConfigMaker(path, "lastloc.yml"));
        configSet.put("playernum", new ConfigMaker(path, "playernum.yml"));
        loadSettings();
        saveConfigs();
    }


    // config 전체 리로드
    public void reloadConfigs()
    {
        for(String key : configSet.keySet())
        {
            FishingAdventure.getServerInstance().getLogger().info(key);
            configSet.get(key).reloadConfig();
        }
    }


    // 특정 config 리로드
    public void reloadConfig(String fileName)
    {
        configSet.get(fileName).reloadConfig();
    }


    // config 전체 저장
    public void saveConfigs()
    {
        for(String key : configSet.keySet())
        {
            configSet.get(key).saveConfig();
        }
    }


    // 특정 config 저장
    public void saveConfig(String fileName)
    {
        configSet.get(fileName).saveConfig();
    }


    // config 가져오기
    public FileConfiguration getConfig(String fileName)
    {
        return configSet.get(fileName).getConfig();
    }

    // config 기본 세팅
    public void loadSettings()
    {
        FileConfiguration whitelistConfig = getConfig("whitelist");
        FileConfiguration chunkownershipConfig = getConfig("chunkownership");
        FileConfiguration fishinglevelConfig = getConfig("fishinglevel");
        FileConfiguration fishingpointConfig = getConfig("fishingpoint");
        FileConfiguration encyclopediaConfig = getConfig("encyclopedia");
        FileConfiguration encyclopediastackConfig = getConfig("encyclopediastack");
        FileConfiguration lastLocConfig = getConfig("lastloc");
        FileConfiguration playernumConfig = getConfig("playernum");

        whitelistConfig.options().copyDefaults(true);
        chunkownershipConfig.options().copyDefaults(true);
        fishinglevelConfig.options().copyDefaults(true);
        fishingpointConfig.options().copyDefaults(true);
        encyclopediaConfig.options().copyDefaults(true);
        encyclopediastackConfig.options().copyDefaults(true);
        lastLocConfig.options().copyDefaults(true);
        playernumConfig.options().copyDefaults(true);
    }
}
