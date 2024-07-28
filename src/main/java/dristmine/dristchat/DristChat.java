package dristmine.dristchat;

import dristmine.dristchat.utils.ConfigManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class DristChat extends JavaPlugin {
	private LuckPerms luckPerms;

	@Override
	public void onEnable() {
		getLogger().info("Enabling DristChat v1.0");

		try {
			luckPerms = LuckPermsProvider.get();
		}
		catch (IllegalStateException e) {
			getLogger().severe("LuckPerms not found! Disabling plugin.");

			getServer().getPluginManager().disablePlugin(this);

			return;
		}

		getServer().getPluginManager().registerEvents(new MessageListener(), this);
	}

	@Override
	public void onDisable() {
		getLogger().info("Disabling DristChat v1.0");
	}
}
