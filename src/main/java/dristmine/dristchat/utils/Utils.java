package dristmine.dristchat.utils;

import org.bukkit.entity.Player;

public class Utils {
	public final static String EMPTY_STRING = "";

	public static boolean featureEnabled(Player player, ConfigSectionsKeys feature, ConfigManager configManager) {
		return player.hasPermission(configManager.getString(feature, ConfigSectionsKeys.PERMISSION));
	}
}
