package net.daboross.bukkitdev.commandexecutorbase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

/**
 *
 * @author daboross
 */
public class CommandExecutorBase implements TabExecutor {

    private final Map<String, SubCommand> aliasToCommandMap = new HashMap<String, SubCommand>();
    private final Set<SubCommand> subCommands = new HashSet<SubCommand>();
    private final CommandExecutorBridge commandExecutorBridge = new CommandExecutorBridge(this);
    private final String commandPermission;

    public CommandExecutorBase(String commandPermission) {
        this.commandPermission = commandPermission;
        addSubCommand(new SubCommand("help", new String[]{"?"}, true, null, "This Command Views This Page", new SubCommandHandler() {
            public void runCommand(CommandSender sender, Command mainCommand, String baseCommandLabel, SubCommand subCommand, String subCommandLabel, String[] subCommandArgs, CommandExecutorBridge executorBridge) {
                sender.sendMessage(ColorList.TOP_OF_LIST_SEPERATOR + " -- " + ColorList.TOP_OF_LIST + "Command Help" + ColorList.TOP_OF_LIST_SEPERATOR + " --");
                for (SubCommand subCommandVar : subCommands) {
                    if (hasPermission(sender, subCommandVar)) {
                        sender.sendMessage(getHelpMessage(subCommandVar, baseCommandLabel));
                    }
                }
            }
        }));
    }

    public final void addSubCommand(SubCommand subCommand) {
        if (subCommand == null) {
            throw new IllegalArgumentException("Null subCommand");
        }
        subCommands.add(subCommand);
        aliasToCommandMap.put(subCommand.command, subCommand);
        for (String alias : subCommand.aliasesUnmodifiable) {
            aliasToCommandMap.put(alias, subCommand);
        }
        subCommand.usingCommand(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        SubCommand subCommand = getAndCheckCommand(sender, cmd, label, args);
        if (subCommand != null) {
            String[] subArgs = ArrayHelpers.getSubArray(args, 1, args.length - 1);
            subCommand.commandHandler.runCommand(sender, cmd, label, subCommand, args[0], subArgs, commandExecutorBridge);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> returnList = new ArrayList<String>();
        if (args.length == 0) {
            for (String alias : aliasToCommandMap.keySet()) {
                returnList.add(alias);
            }
        } else if (args.length == 1) {
            for (String alias : aliasToCommandMap.keySet()) {
                if (alias.startsWith(args[0].toLowerCase(Locale.ENGLISH))) {
                    if (hasPermission(sender, aliasToCommandMap.get(alias))) {
                        returnList.add(alias);
                    }
                }
            }
        } else {
            SubCommand subCommand = aliasToCommandMap.get(args[1].toLowerCase(Locale.ENGLISH));
            if (subCommand != null) {
            }
        }
        return returnList;
    }

    private void sendInvalidSubCommandMessage(CommandSender sender, String label, String[] args) {
        sender.sendMessage(ColorList.MAIN + "The subcommand: " + ColorList.CMD + args[0] + ColorList.MAIN + " does not exist for the command " + ColorList.CMD + "/" + label);
        sender.sendMessage(ColorList.MAIN + "To see all possible subcommands, type " + ColorList.CMD + "/" + label + ColorList.SUBCMD + " ?");
    }

    private void sendNoSubCommandMessage(CommandSender sender, String label, String[] args) {
        sender.sendMessage(ColorList.MAIN + "This is a base command, please use a subcommand after it.");
        sender.sendMessage(ColorList.MAIN + "To see all possible subcommands, type " + ColorList.CMD + "/" + label + ColorList.SUBCMD + " ?");
    }

    private void sendNoPermissionMessage(CommandSender sender, String label, String[] args) {
        if (args == null || args.length < 1) {
            sender.sendMessage(ColorList.NOPERM + "You don't have permission to run " + ColorList.CMD + "/" + label);
        } else {
            sender.sendMessage(ColorList.NOPERM + "You don't have permission to run " + ColorList.CMD + "/" + label + " " + ColorList.SUBCMD + args[0]);
        }
    }

    private void sendPlayerOnlyMessage(CommandSender sender, String label, String[] args) {
        sender.sendMessage(ColorList.NOPERM + "The command " + ColorList.CMD + "/" + label + (args.length > 0 ? (ColorList.SUBCMD + args[0]) : "") + ColorList.NOPERM + " must be run by a player");
    }

    protected SubCommand getAndCheckCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!hasMainPermission(sender)) {
            sendNoPermissionMessage(sender, label, null);
            return null;
        }
        if (args.length < 1) {
            sendNoSubCommandMessage(sender, label, args);
            return null;
        }
        SubCommand command = aliasToCommandMap.get(args[0].toLowerCase(Locale.ENGLISH));
        if (command == null) {
            sendInvalidSubCommandMessage(sender, label, args);
            return null;
        }
        if (command.playerOnly && !(sender instanceof Player)) {
            sendPlayerOnlyMessage(sender, label, args);
            return null;
        }
        if (!hasPermission(sender, command)) {
            sendNoPermissionMessage(sender, label, args);
            return null;
        }
        return command;
    }

    protected boolean hasPermission(CommandSender sender, SubCommand command) {
        return (command.permission == null || sender.hasPermission(command.permission) || !(sender instanceof Player));
    }

    protected boolean hasMainPermission(CommandSender sender) {
        return (commandPermission == null || sender.hasPermission(commandPermission) || !(sender instanceof Player));
    }

    void addAlias(SubCommand subCommand, String alias) {
        if (!subCommands.contains(subCommand)) {
            throw new IllegalArgumentException("SubCommand not part of this CommandExectorBase! Add it first!");
        }
        aliasToCommandMap.put(alias, subCommand);
    }

    String getHelpMessage(SubCommand subCommand, String baseCommandLabel) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append(ColorList.CMD).append("/").append(baseCommandLabel).append(ColorList.SUBCMD).append(" ");
        resultBuilder.append(ColorList.SUBCMD).append(subCommand.command);
        for (String alias : subCommand.aliasesUnmodifiable) {
            resultBuilder.append(ColorList.DIVIDER).append("|").append(ColorList.SUBCMD).append(alias);
        }
        if (!subCommand.argumentNamesUnmodifiable.isEmpty()) {
            resultBuilder.append(ColorList.ARGS_SURROUNDER);
            for (String argument : subCommand.argumentNamesUnmodifiable) {
                resultBuilder.append("<").append(ColorList.ARGS).append(argument).append(ColorList.ARGS_SURROUNDER).append("> ");
            }
        }
        resultBuilder.append(ColorList.HELP).append(subCommand.helpMessage);
        return resultBuilder.toString();
    }

    String getHelpMessage(SubCommand subCommand, String subCommandLabel, String baseCommandLabel) {
        if (!subCommand.aliasesUnmodifiable.contains(subCommandLabel)) {
            throw new IllegalArgumentException("given alias doesn't belong to subCommand");
        }
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append(ColorList.CMD).append("/").append(baseCommandLabel).append(ColorList.SUBCMD).append(" ");
        resultBuilder.append(ColorList.SUBCMD).append(subCommand.command);
        for (String alias : subCommand.aliasesUnmodifiable) {
            resultBuilder.append(ColorList.DIVIDER).append("|").append(ColorList.SUBCMD).append(alias);
        }
        if (!subCommand.argumentNamesUnmodifiable.isEmpty()) {
            resultBuilder.append(ColorList.ARGS_SURROUNDER);
            for (String argument : subCommand.argumentNamesUnmodifiable) {
                resultBuilder.append("<").append(ColorList.ARGS).append(argument).append(ColorList.ARGS_SURROUNDER).append("> ");
            }
        }
        resultBuilder.append(ColorList.HELP).append(subCommand.helpMessage);
        return resultBuilder.toString();
    }
}
