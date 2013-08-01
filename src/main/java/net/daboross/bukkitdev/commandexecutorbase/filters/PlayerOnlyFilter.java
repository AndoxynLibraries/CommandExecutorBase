/*
 * Copyright (C) 2013 Dabo Ross <www.daboross.net>
 */
package net.daboross.bukkitdev.commandexecutorbase.filters;

import net.daboross.bukkitdev.commandexecutorbase.ColorList;
import net.daboross.bukkitdev.commandexecutorbase.CommandFilter;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author daboross
 */
public class PlayerOnlyFilter implements CommandFilter {

    public final String messageFormat;

    public PlayerOnlyFilter() {
        this(ColorList.ERR + "The command %s" + ColorList.ERR + " must be run by a player.");
    }

    public PlayerOnlyFilter(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    @Override
    public boolean canContinue(CommandSender sender, Command baseCommand, SubCommand subCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs) {
        return sender instanceof Player;
    }

    @Override
    public String[] getDeniedMessage(CommandSender sender, Command baseCommand, SubCommand subCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs) {
        return new String[]{String.format(messageFormat, ColorList.CMD + "/" + baseCommandLabel + " " + ColorList.SUBCMD + subCommandLabel)};
    }
}
