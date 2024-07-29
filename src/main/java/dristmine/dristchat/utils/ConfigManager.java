package dristmine.dristchat.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {
	public static final String CONFIG_DELIMITER = ".";

	private static JavaPlugin DristChatInstance;
	private static FileConfiguration configuration;

	private ConfigManager(JavaPlugin plugin) {
		configuration = plugin.getConfig();
	}

	public static void setup(JavaPlugin plugin) {
		DristChatInstance = plugin;
		plugin.saveDefaultConfig();
	}

	public static ConfigManager getInstance() {
		return new ConfigManager(DristChatInstance);
	}

	public String getString(String key) {
		if (key == null)
			return Utils.EMPTY_STRING;

		if (key.isEmpty())
			return key;

		return configuration.getString(key);
	}
	public final String getString(ConfigKeys sections, ConfigKeys key) {
		return getString(sections.getKey(key));
	}
	public final String getString(ConfigKeys key) {
		return getString(key.getKey());
	}

	public double getFloatingPoint(String key) {
		if (key == null)
			return Utils.DEFAULT_FLOATING_POINT;

		if (key.isEmpty())
			return Utils.DEFAULT_FLOATING_POINT;

		return configuration.getDouble(key);
	}
	public final double getFloatingPoint(ConfigKeys key) {
		return getFloatingPoint(key.getKey());
	}
}
