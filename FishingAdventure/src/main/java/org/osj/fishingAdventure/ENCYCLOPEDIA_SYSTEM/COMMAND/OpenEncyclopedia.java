package org.osj.fishingAdventure.ENCYCLOPEDIA_SYSTEM.COMMAND;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.osj.fishingAdventure.ENCYCLOPEDIA_SYSTEM.EncyclopediaInventory;

public class OpenEncyclopedia implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;
        EncyclopediaInventory inv = new EncyclopediaInventory();
        inv.open(player, 1);
        return false;
    }
}
