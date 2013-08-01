/*
 * Copyright (C) 2013 Dabo Ross <www.daboross.net>
 */
package net.daboross.bukkitdev.commandexecutorbase.conditions;

import net.daboross.bukkitdev.commandexecutorbase.CommandHelpCondition;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author daboross
 */
public class PlayerOnlyCondition implements CommandHelpCondition {

    @Override
    public boolean canContinue(CommandSender sender, SubCommand subCommand) {
        return sender instanceof Player;
    }
}
