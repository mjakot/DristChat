package dristmine.dristchat.utils;

import org.bukkit.entity.Player;

public class Utils {
	public final static String EMPTY_STRING = "";
	public final static double DEFAULT_FLOATING_POINT = 0.0;

	public static boolean featureEnabled(Player player, ConfigKeys feature) {
		return player.hasPermission(ConfigManager.getInstance().getString(feature, ConfigKeys.PERMISSION));
	}
}
