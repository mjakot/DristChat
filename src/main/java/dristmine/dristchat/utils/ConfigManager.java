package dristmine.dristchat.utils;

import dristmine.dristchat.DristChat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {
	public static final String CONFIG_DELIMITER = ".";

	private final FileConfiguration configuration;

	public static final ConfigManager getInstance() {
		return new ConfigManager(new DristChat());
	}

	private ConfigManager(JavaPlugin plugin) {
		plugin.saveDefaultConfig();

		this.configuration = plugin.getConfig();
	}

	public String getString(String key) {
		if (key == null)
			return Utils.EMPTY_STRING;

		if (key.isEmpty())
			return key;

		return configuration.getString(key);
	}

	public String getString(ConfigKeys sections, ConfigKeys key) {
		return getString(sections.getKey(key));
	}

	public String getString(ConfigKeys key) {
		return getString(key.getKey());
	}
}
