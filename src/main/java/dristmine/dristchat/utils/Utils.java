package dristmine.dristchat.utils;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

public class Utils {
	public final static String EMPTY_STRING = "";
	public final static double DEFAULT_FLOATING_POINT = 0.0;

	public static boolean featureEnabled(Player player, ConfigKeys feature) {
		return player.hasPermission(ConfigManager.getInstance().getString(feature, ConfigKeys.PERMISSION));
	}

	public static User getUser(LuckPerms luckPermsSingleton, Player player) {
		return luckPermsSingleton.getPlayerAdapter(Player.class).getUser(player);
	}
}
