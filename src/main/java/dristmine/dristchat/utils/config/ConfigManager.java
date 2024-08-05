package dristmine.dristchat.utils.config;

import dristmine.dristchat.DristChat;
import dristmine.dristchat.utils.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ConfigManager {
	public static final String CONFIG_DELIMITER = ".";

	private static JavaPlugin DristChatInstance;
	private static FileConfiguration configuration;

	private ConfigManager(JavaPlugin plugin) {
		configuration = plugin.getConfig();
	}

	public static void setup(DristChat plugin) {
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

	public final List<String> getStringList(String key) {
		return configuration.getStringList(key);
	}
	public final List<String> getStringList(ConfigKeys section, ConfigKeys key) {
		return getStringList(section.getKey(key));
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

	public int getInteger(String key) {
		return (int) getFloatingPoint(key);
	}
	public int getInteger(ConfigKeys key) {
		return (int) getFloatingPoint(key);
	}
}
