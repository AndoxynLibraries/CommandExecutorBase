package net.daboross.bukkitdev.commandexecutorbase;

import org.bukkit.ChatColor;

/**
 * This is a class that holds what colors PlayerData should user for different
 * purpose.
 *
 * @author daboross
 */
public final class ColorList {

    /**
     * This is the main color.
     */
    public static final String MAIN = ChatColor.DARK_AQUA.toString();
    /**
     * This is the color used at the top of a list.
     */
    public static final String TOP_OF_LIST = ChatColor.DARK_GREEN.toString();
    /**
     * Separator at the top of a list. E.G. --.
     */
    public static final String TOP_OF_LIST_SEPERATOR = ChatColor.BLUE.toString();
    /**
     * This is the color for Player's Usernames.
     */
    public static final String NAME = ChatColor.GREEN.toString();
    /**
     * This is the color for numbers, or other data.
     */
    public static final String NUMBER = NAME;
    /**
     * This is the color for commands, EG /cc should be this color.
     */
    public static final String CMD = ChatColor.GREEN.toString();
    /**
     * This is be the color for help text.
     */
    public static final String HELP = ChatColor.WHITE.toString();
    /**
     * This is the color for Sub Commands, EG in '/cc k', 'k' should be this
     * color.
     */
    public static final String SUBCMD = ChatColor.BLUE.toString();
    /**
     * This is the color for Arguments of a command, EG in /asdf gl PLAYERNAME
     * NUMBER, PLAYERNAME and NUMBER would be this color.
     */
    public static final String ARGS = ChatColor.AQUA.toString();
    /**
     * This is the color for Arguments of a command, EG in /asdf gl [PLAYERNAME]
     * [NUMBER],the []s would be this color.
     */
    public static final String ARGS_SURROUNDER = ChatColor.DARK_AQUA.toString();
    /**
     * This is the color for messages saying that the user has supplied an
     * Illegal Argument for a command.
     */
    public static final String ILLEGALARGUMENT = MAIN;
    /**
     * This is the color for messages saying that there is an error.
     */
    public static final String ERROR = ChatColor.DARK_RED.toString();
    /**
     * This is the color for messages saying that the user has no permission, or
     * that this is a player command when run from console.
     */
    public static final String NOPERM = ERROR;
    /**
     * This is the color for the arguments that have caused an error, or are
     * Illegal.
     */
    public static final String ERROR_ARGS = ChatColor.RED.toString();
    /**
     * This is the color that the server's name should be displayed in.
     */
    public static final String SERVERNAME = ChatColor.AQUA.toString();
    /**
     * This is the color that the Data Handler uses between a player's username
     * and their nickname in getPossibleUsernames.
     */
    public static final String DATA_HANDLE_SLASH = ChatColor.DARK_GRAY.toString();
    /**
     * This is the color for the divider slash in various places, EG if you were
     * separating two fields with a slash, that slash should be this color.
     */
    public static final String DIVIDER = ChatColor.DARK_GRAY.toString();
    /**
     * This is the color that broadcasts should be.
     */
    public static final String BROADCAST = MAIN;
    /**
     * Broadcast Name for use with String.format(). First argument is the
     * broadcaster's name.
     */
    public static final String BROADCAST_NAME_FORMAT = ChatColor.DARK_GRAY + "[" + ChatColor.RED + "%s" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
}
