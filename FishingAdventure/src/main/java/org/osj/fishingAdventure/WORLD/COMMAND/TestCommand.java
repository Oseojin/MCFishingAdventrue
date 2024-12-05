package org.osj.fishingAdventure.WORLD.COMMAND;

import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.osj.fishingAdventure.FishingAdventure;

public class TestCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player)commandSender;
        if(!player.isOp())
        {
            return false;
        }
        FishingAdventure.getFishingManager().SpawnFireWork(player.getLocation(), Color.BLUE);

        return false;
    }
}
