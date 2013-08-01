/*
 * Copyright (C) 2013 Dabo Ross <www.daboross.net>
 */
package net.daboross.bukkitdev.commandexecutorbase.conditions;

import net.daboross.bukkitdev.commandexecutorbase.CommandHelpCondition;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import org.bukkit.command.CommandSender;

/**
 *
 * @author daboross
 */
public class PermissionCondition implements CommandHelpCondition {

    @Override
    public boolean canContinue(CommandSender sender, SubCommand subCommand) {
        String permission = subCommand.getPermission();
        return permission == null || sender.hasPermission(permission);
    }
}
