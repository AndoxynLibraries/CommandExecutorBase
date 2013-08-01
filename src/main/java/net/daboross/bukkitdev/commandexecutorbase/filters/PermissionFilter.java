/*
 * Copyright (C) 2013 Dabo Ross <www.daboross.net>
 */
package net.daboross.bukkitdev.commandexecutorbase.filters;

import net.daboross.bukkitdev.commandexecutorbase.ColorList;
import net.daboross.bukkitdev.commandexecutorbase.CommandFilter;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author daboross
 */
public class PermissionFilter implements CommandFilter {

    private final String messageFormat;

    public PermissionFilter() {
        this(ColorList.ERR + "You don't have permission to use %s");
    }

    public PermissionFilter(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    @Override
    public boolean canContinue(CommandSender sender, Command baseCommand, SubCommand subCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs) {
        String permission = subCommand.getPermission();
        return permission == null || sender.hasPermission(permission);
    }

    @Override
    public String[] getDeniedMessage(CommandSender sender, Command baseCommand, SubCommand subCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs) {
        return new String[]{String.format(messageFormat, ColorList.CMD + "/" + baseCommandLabel + " " + ColorList.SUBCMD + subCommandLabel)};
    }
}
