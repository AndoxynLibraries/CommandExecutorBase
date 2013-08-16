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
import net.daboross.bukkitdev.commandexecutorbase.filters.PermissionFilter;
import net.daboross.bukkitdev.commandexecutorbase.filters.PlayerOnlyFilter;
import net.daboross.bukkitdev.commandexecutorbase.conditions.PermissionCondition;
import net.daboross.bukkitdev.commandexecutorbase.conditions.PlayerOnlyCondition;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author daboross
 */
public abstract class SubCommand {

    private final Set<CommandExecutorBase> commandExecutorBasesUsingThis;
    private final String commandName;
    private String help;
    private String permission;
    private final List<String> aliases;
    private final List<String> argumentNames;
    private final List<CommandFilter> commandFilters;
    private final List<CommandPreCondition> helpConditions;

    public SubCommand(String commandName, boolean canConsoleExecute, String permission, String helpMessage) {
        if (commandName == null) {
            throw new IllegalArgumentException("Null commandName argument");
        }
        this.commandName = commandName.toLowerCase(Locale.ENGLISH);
        this.help = (helpMessage == null ? "" : helpMessage);
        this.permission = permission;
        this.aliases = new ArrayList<String>();
        this.argumentNames = new ArrayList<String>();
        this.commandExecutorBasesUsingThis = new HashSet<CommandExecutorBase>();
        this.commandFilters = new ArrayList<CommandFilter>();
        this.helpConditions = new ArrayList<CommandPreCondition>();
        this.helpConditions.add(new PermissionCondition());
        this.commandFilters.add(new PermissionFilter());
        if (!canConsoleExecute) {
            this.helpConditions.add(new PlayerOnlyCondition());
            this.commandFilters.add(new PlayerOnlyFilter());
        }
    }

    /**
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
     * @return this, for chaining.
     */
    public SubCommand addArgumentNames(String... argumentNames) {
        this.argumentNames.addAll(Arrays.asList(argumentNames));
        return this;
    }

    /**
     * @return this, for chaining.
     */
    public SubCommand setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    /**
     * @return this, for chaining.
     */
    public SubCommand addCommandFilter(CommandFilter filter) {
        commandFilters.add(filter);
        return this;
    }

    /**
     * @return this, for chaining.
     */
    public SubCommand addCommandPreCondition(CommandPreCondition helpCondition) {
        helpConditions.add(helpCondition);
        return this;
    }

    public String getName() {
        return commandName;
    }

    public String getHelpMessage() {
        return help;
    }

    public String getPermission() {
        return permission;
    }

    public List<String> getAliases() {
        return Collections.unmodifiableList(aliases);
    }

    public List<String> getArgumentNames() {
        return Collections.unmodifiableList(argumentNames);
    }

    public List<CommandFilter> getCommandFilters() {
        return Collections.unmodifiableList(commandFilters);
    }

    public List<CommandPreCondition> getHelpConditions() {
        return Collections.unmodifiableList(helpConditions);
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
