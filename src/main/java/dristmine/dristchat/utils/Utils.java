package dristmine.dristchat.utils;

import dristmine.dristchat.utils.config.ConfigKeys;
import dristmine.dristchat.utils.enums.ChatDecorations;
import dristmine.dristchat.utils.enums.ChatFeatures;
import me.clip.placeholderapi.PlaceholderAPI;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static dristmine.dristchat.DristChat.config;
import static dristmine.dristchat.utils.config.ConfigKeys.*;
import static dristmine.dristchat.utils.enums.ChatDecorations.*;

public class Utils {
	public final static String NULL_PLACEHOLDER = "null";
	public final static String COMMAND_ARGS_DELIMITER = " ";
	public static final String PAPER_SOUND_DELIMITER = "_";
	public static final String MINECRAFT_SOUND_DELIMITER = ".";
	public final static String EMPTY_STRING = "";
	public final static double DEFAULT_FLOATING_POINT = 0.0;
	public final static int DEFAULT_INTEGER = 0;
	public final static int NOT_FOUND_INDEX = -1;
	public final static int COMMAND_START_INDEX = 1;
	public final static int RECEIVER_INDEX = 1;
	public final static int MESSAGE_INDEX = 2;
	public final static int TICKS_IN_SECOND = 20;
	public final static int MILLIS_IN_SECOND = 1000;
	public final static int HEX_RADIX = 16;
	public final static String[] PRIVATE_MESSAGE_COMMANDS = { "msg", "tell" };

	public static boolean isFeatureEnabled(Player player, ConfigKeys feature) {
		return player.hasPermission(config().getString(feature, PERMISSION));
	}

	public static boolean isFeatureEnabled(Player player, ChatFeatures feature) {
		return player.hasPermission(config().getString(feature.getFeature()));
	}

	public static String testPrefix(ChatDecorations chatDecorations, Player player) {
		if (chatDecorations == PREFIX_AND_SUFFIX || chatDecorations == PREFIX)
			return PlaceholderAPI.setPlaceholders(player, config().getString(GLOBAL_PREFIX_PLACEHOLDER));

		return EMPTY_STRING;
	}

	public static String testSuffix(ChatDecorations chatDecorations, Player player) {
		if (chatDecorations == PREFIX_AND_SUFFIX || chatDecorations == SUFFIX)
			return PlaceholderAPI.setPlaceholders(player, config().getString(GLOBAL_SUFFIX_PLACEHOLDER));

		return EMPTY_STRING;
	}

	public static @NotNull String ensureStringNotNull(@Nullable String nullableString) {
		return isNotNull(nullableString) ? nullableString : EMPTY_STRING;
	}

	public static boolean isNull(Object object) {
		return object == null;
	}

	public static boolean isNotNull(Object object) {
		return !isNull(object);
	}

	public static long ticksToMillis(long ticks) {
		double seconds = (double) ticks / TICKS_IN_SECOND;

		return (long) (seconds * MILLIS_IN_SECOND);
	}
}
