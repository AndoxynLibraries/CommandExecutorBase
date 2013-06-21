/*
 * Author: Dabo Ross
 * Website: www.daboross.net
 * Email: daboross@daboross.net
 */
package net.daboross.bukkitdev.commandexecutorbase;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author daboross
 */
public interface SubCommandHandler {

    public void runCommand(final CommandSender sender, final Command baseCommand, final String baseCommandLabel, final SubCommand subCommand, final String subCommandLabel, final String[] subCommandArgs);
}
