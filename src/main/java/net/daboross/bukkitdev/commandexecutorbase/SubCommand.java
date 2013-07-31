/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.commandexecutorbase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author daboross
 */
public abstract class SubCommand {

    private final Set<CommandExecutorBase> commandExecutorBasesUsingThis;
    final String commandName;
    final boolean playerOnly;
    final String permission;
    private final List<String> aliases;
    final List<String> aliasesUnmodifiable;
    final String helpMessage;
    private final List<String> argumentNames;
    final List<String> argumentNamesUnmodifiable;

    public SubCommand(final String commandName, final boolean canConsoleExecute, final String permission, String helpMessage) {
        if (commandName == null) {
            throw new IllegalArgumentException("Null commandName argument");
        }
        this.commandName = commandName.toLowerCase(Locale.ENGLISH);
        this.aliases = new ArrayList<String>();
        this.aliasesUnmodifiable = Collections.unmodifiableList(this.aliases);
        this.playerOnly = !canConsoleExecute;
        this.permission = permission;
        this.helpMessage = (helpMessage == null ? "" : helpMessage);
        this.argumentNames = new ArrayList<String>();
        this.argumentNamesUnmodifiable = Collections.unmodifiableList(this.argumentNames);
        this.commandExecutorBasesUsingThis = new HashSet<CommandExecutorBase>();
    }

    /**
     *
     * @param alias
     * @return this, for chaining.
     */
    public SubCommand addAlias(String alias) {
        this.aliases.add(alias);
        for (CommandExecutorBase commandExecutorBase : commandExecutorBasesUsingThis) {
            commandExecutorBase.addAlias(this, alias);
        }
        return this;
    }

    /**
     *
     * @param aliases
     * @return this, for chaining.
     */
    public SubCommand addAliases(String... aliases) {
        List<String> aliasesList = Arrays.asList(aliases);
        this.aliases.addAll(aliasesList);
        for (CommandExecutorBase commandExecutorBase : commandExecutorBasesUsingThis) {
            commandExecutorBase.addAliases(this, aliases);
        }
        return this;
    }

    /**
     *
     * @param argumentNames
     * @return this, for chaining.
     */
    public SubCommand addArgumentNames(String... argumentNames) {
        this.argumentNames.addAll(Arrays.asList(argumentNames));
        return this;
    }

    public String getName() {
        return commandName;
    }

    public String getHelpMessage(String baseCommandLabel) {
        return CommandExecutorBase.getHelpMessage(this, baseCommandLabel);
    }

    public String getHelpMessage(String baseCommandLabel, String subCommandLabel) {
        return CommandExecutorBase.getHelpMessage(this, baseCommandLabel, subCommandLabel);
    }

    void usingCommand(CommandExecutorBase commandExecutorBase) {
        commandExecutorBasesUsingThis.add(commandExecutorBase);
    }

    public abstract void runCommand(CommandSender sender, Command baseCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs);

    /**
     *
     * @param sender the sender initiating the command
     * @param baseCommand the base command being called
     * @param baseCommandLabel the label for the base command used by the sender
     * @param subCommand the subcommand being called
     * @param subCommandLabel the label for the subcommand being used by the
     * sender
     * @param subCommandArgs the arguments so far including the one currently
     * being typed not including the subcommand.
     * @return a list of possible completes for the given argument (the last one
     * in subCommandArgs)
     */
    public List<String> tabComplete(CommandSender sender, Command baseCommand, String baseCommandLabel, SubCommand subCommand, String subCommandLabel, String[] subCommandArgs) {
        return null;
    }
}
