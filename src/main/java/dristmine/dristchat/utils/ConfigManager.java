package dristmine.dristchat.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {
	public static final String CONFIG_DELIMITER = ".";

	private final FileConfiguration configuration;

	ConfigManager(JavaPlugin plugin) {
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

	public String getString(ConfigSectionsKeys sections, ConfigSectionsKeys key) {
		return getString(sections.getKey(key));
	}
}
