/*
 * Author: Dabo Ross
 * Website: www.daboross.net
 * Email: daboross@daboross.net
 */
package net.daboross.bukkitdev.commandexecutorbase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 *
 * @author daboross
 */
public class SubCommand {

    final SubCommandHandler commandHandler;
    final String command;
    final boolean playerOnly;
    final String permission;
    private final Set<String> aliases;
    final Set<String> aliasesUnmodifiable;
    final String helpMessage;
    private final List<String> argumentNames;
    final List<String> argumentNamesUnmodifiable;
    private final Set<CommandExecutorBase> commandExecutorBasesUsingThis;

    public SubCommand(final String command, final String[] aliases, final boolean canConsoleExecute, final String permission, final String[] argumentNames, String helpMessage, SubCommandHandler commandHandler) {
        if (command == null) {
            throw new IllegalArgumentException("Null cmd argument");
        } else if (commandHandler == null) {
            throw new IllegalArgumentException("Null commandreactor argument");
        }
        this.command = command.toLowerCase(Locale.ENGLISH);
        this.aliases = aliases == null ? new HashSet<String>() : ArrayHelpers.copyToSet(aliases);
        this.aliasesUnmodifiable = Collections.unmodifiableSet(this.aliases);
        this.playerOnly = !canConsoleExecute;
        this.permission = permission;
        this.helpMessage = (helpMessage == null ? "Null help message" : helpMessage);
        this.argumentNames = argumentNames == null ? new ArrayList<String>() : ArrayHelpers.copyToList(argumentNames);
        this.argumentNamesUnmodifiable = Collections.unmodifiableList(this.argumentNames);
        this.commandHandler = commandHandler;
        this.commandExecutorBasesUsingThis = new HashSet<CommandExecutorBase>();
    }

    public SubCommand(String cmd, String[] aliases, boolean isConsole, String permission, String helpString, SubCommandHandler commandHandler) {
        this(cmd, aliases, isConsole, permission, null, helpString, commandHandler);
    }

    public SubCommand(String cmd, boolean isConsole, String permission, String[] arguments, String helpString, SubCommandHandler commandHandler) {
        this(cmd, null, isConsole, permission, arguments, helpString, commandHandler);
    }

    public SubCommand(String cmd, boolean isConsole, String permission, String helpString, SubCommandHandler commandHandler) {
        this(cmd, null, isConsole, permission, null, helpString, commandHandler);
    }

    public void addAlias(String alias) {
        this.aliases.add(alias);
        for (CommandExecutorBase commandExecutorBase : commandExecutorBasesUsingThis) {
            commandExecutorBase.addAlias(this, alias);
        }
    }

    void usingCommand(CommandExecutorBase commandExecutorBase) {
        commandExecutorBasesUsingThis.add(commandExecutorBase);
    }
}
