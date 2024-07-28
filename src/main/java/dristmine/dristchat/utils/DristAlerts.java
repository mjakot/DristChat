package dristmine.dristchat.utils;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class DristAlerts extends JavaPlugin {
	private static DristAlerts instance;
	private static LuckPerms luckPerms;

	public DristAlerts() {
		instance = this;
		setupLuckPerms();
	}

	@Override
	public void onEnable() {
		instance = this;
		setupLuckPerms();
	}

	@Override
	public void onDisable() {
		instance = null;
	}

	// Method to send a message with color codes and sound
	public static void sendGlobalMessage(String message, String permission, Sound sound) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (hasPermission(player, permission)) {
				player.sendMessage(convertHexString(message));
				if (sound != null) {
					player.playSound(player.getLocation(), sound, 1, 1);
				}
			}
		}
	}

	public static void sendPrivateMessage(String message, String permission, Sound sound, Player player) {
		if (hasPermission(player, permission)) {
			player.sendMessage(convertHexString(message));
			if (sound != null) {
				player.playSound(player.getLocation(), sound, 1, 1);
			}
		}
	}

	// Check LuckPerms permission
	private static boolean hasPermission(Player player, String permission) {
		return luckPerms.getUserManager().getUser(player.getUniqueId()).getCachedData().getPermissionData().checkPermission(permission).asBoolean();
	}

	// Setup LuckPerms
	private void setupLuckPerms() {
		if (Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) {
			luckPerms = getServer().getServicesManager().getRegistration(LuckPerms.class).getProvider();
		} else {
			getLogger().warning("LuckPerms not found. Please make sure LuckPerms is installed and enabled.");
		}
	}

	// Get the plugin instance
	public static DristAlerts getInstance() {
		return instance;
	}

	private static final Pattern hexPatten = Pattern.compile("&#[0-9A-Fa-f]{6}");
	private static final LegacyComponentSerializer legacyAmpersand = LegacyComponentSerializer.legacyAmpersand();
	private static final LegacyComponentSerializer legacySection = LegacyComponentSerializer.legacySection();

	public static @NotNull TextComponent convertHex(String input) {
		Matcher matcher = hexPatten.matcher(input);

		while (matcher.find()) {
			String hexColor = matcher.group();
			if (!hexColor.startsWith("&#")) {
				input = input.replace(hexColor, "&" + hexColor);
			}
		}

		return legacyAmpersand.deserialize(input);
	}

	public static String convertHexString(String input) {
		return LegacyComponentSerializer.legacySection().serialize(convertHex(input));
	}
}
