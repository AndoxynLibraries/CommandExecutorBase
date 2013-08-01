/*
 * Copyright (C) 2013 Dabo Ross <www.daboross.net>
 */
package net.daboross.bukkitdev.commandexecutorbase.filters;

import net.daboross.bukkitdev.commandexecutorbase.CommandFilter;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author daboross
 */
public class ArgumentFilter implements CommandFilter {

    private final ArgumentCondition condition;
    private final int conditionValue;
    private final String deniedMessage;
    private final boolean showHelp;

    public ArgumentFilter(ArgumentCondition condition, int conditionValue, String deniedMessage, boolean showHelp) {
        this.condition = condition;
        this.conditionValue = conditionValue;
        this.deniedMessage = deniedMessage;
        this.showHelp = showHelp;
    }

    public ArgumentFilter(ArgumentCondition condition, int conditionValue, String deniedMessage) {
        this(condition, conditionValue, deniedMessage, true);
    }

    @Override
    public boolean canContinue(CommandSender sender, Command baseCommand, SubCommand subCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs) {
        return condition.is(conditionValue, subCommandArgs.length);
    }

    @Override
    public String[] getDeniedMessage(CommandSender sender, Command baseCommand, SubCommand subCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs) {
        return showHelp ? new String[]{deniedMessage, subCommand.getHelpMessage(baseCommandLabel, subCommandLabel)} : new String[]{deniedMessage};
    }

    public static enum ArgumentCondition {

        GREATER_THAN {
            @Override
            public boolean is(int conditionValue, int valueToCheck) {
                return valueToCheck > conditionValue;
            }
        }, LESS_THAN {
            @Override
            public boolean is(int conditionValue, int valueToCheck) {
                return valueToCheck < conditionValue;
            }
        }, EQUALS {
            @Override
            public boolean is(int conditionValue, int valueToCheck) {
                return valueToCheck == conditionValue;
            }
        };

        public abstract boolean is(int conditionValue, int valueToCheck);
    }
}
