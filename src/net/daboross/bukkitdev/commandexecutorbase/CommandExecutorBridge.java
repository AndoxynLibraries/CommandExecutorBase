/*
 * Author: Dabo Ross
 * Website: www.daboross.net
 * Email: daboross@daboross.net
 */
package net.daboross.bukkitdev.commandexecutorbase;

/**
 *
 * @author daboross
 */
public class CommandExecutorBridge {

    private final CommandExecutorBase commandExecutorBase;

    CommandExecutorBridge(final CommandExecutorBase commandExecutorBase) {
        this.commandExecutorBase = commandExecutorBase;
    }

    public String getHelpMessage(SubCommand subCommand, String subCommandLabel, String baseCommandLabel) {
        return commandExecutorBase.getHelpMessage(subCommand, subCommandLabel, baseCommandLabel);
    }

    public String getHelpMessage(SubCommand subCommand, String baseCommandLabel) {
        return commandExecutorBase.getHelpMessage(subCommand, baseCommandLabel);
    }
}
