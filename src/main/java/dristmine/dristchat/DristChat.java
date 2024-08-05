package dristmine.dristchat;

import dristmine.dristchat.listeners.*;
import dristmine.dristchat.utils.AudienceUtils;
import dristmine.dristchat.utils.config.ConfigManager;
import dristmine.dristchat.utils.Utils;

import net.luckperms.api.LuckPermsProvider;

import org.bukkit.plugin.java.JavaPlugin;

public class DristChat extends JavaPlugin {
	private static ConfigManager config;

	@Override
	public void onEnable() {
		AudienceUtils.setup(this);
		ConfigManager.setup(this);

		config = ConfigManager.getInstance();

		try {
			//noinspection ResultOfMethodCallIgnored
			LuckPermsProvider.get();
		}
		catch (IllegalStateException e) {
			getLogger().severe("DristChat error: LuckPerms not found! Disabling plugin.");

			getServer().getPluginManager().disablePlugin(this);

			return;
		}

		getServer().getPluginManager().registerEvents(new OnPublicMessage(), this);
		getServer().getPluginManager().registerEvents(new OnPrivateMessage(), this);
		getServer().getPluginManager().registerEvents(new OnDeath(), this);
		getServer().getPluginManager().registerEvents(new OnAdvancementDone(), this);
		getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);

		getLogger().info(Utils.EMPTY_STRING);
		getLogger().info("  + ----------+ ");
		getLogger().info("  |   hello   | ");
		getLogger().info("  + ----------| ");
		getLogger().info("             \\|");
		getLogger().info("       DristChat");
		getLogger().info(Utils.EMPTY_STRING);
	}

	@Override
	public void onDisable() {
		getLogger().info(Utils.EMPTY_STRING);
		getLogger().info("  + ----------+ ");
		getLogger().info("  |  bye bye  | ");
		getLogger().info("  + ----------| ");
		getLogger().info("             \\|");
		getLogger().info("       DristChat");
		getLogger().info(Utils.EMPTY_STRING);
	}

	public static ConfigManager config() {
		return config;
	}
}
