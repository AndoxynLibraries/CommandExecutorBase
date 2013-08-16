/*
 * Copyright (C) 2013 Dabo Ross <www.daboross.net>
 */
package net.daboross.bukkitdev.commandexecutorbase.conditions;

import net.daboross.bukkitdev.commandexecutorbase.CommandPreCondition;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

/**
 *
 * @author daboross
 */
public class PermissionCondition implements CommandPreCondition {

    @Override
    public boolean canContinue(CommandSender sender, SubCommand subCommand) {
        String permission = subCommand.getPermission();
        return permission == null || sender instanceof ConsoleCommandSender || sender.hasPermission(permission);
    }
}
