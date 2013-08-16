/*
 * Copyright (C) 2013 Dabo Ross <www.daboross.net>
 */
package net.daboross.bukkitdev.commandexecutorbase;

import org.bukkit.command.CommandSender;

/**
 *
 * @author daboross
 */
public interface CommandPreCondition {

    public boolean canContinue(CommandSender sender, SubCommand subCommand);
}
