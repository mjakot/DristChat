package dristmine.dristchat;

import dristmine.dristchat.utils.ConfigManager;
import dristmine.dristchat.utils.Utils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class DristChat extends JavaPlugin {
	private LuckPerms luckPerms;

	@Override
	public void onEnable() {
		getLogger().info(Utils.EMPTY_STRING);
		getLogger().info("  + ----------+  ");
		getLogger().info("  | DristChat |  ");
		getLogger().info("  + ----------|  ");
		getLogger().info("             \\| ");
		getLogger().info(Utils.EMPTY_STRING);

		ConfigManager.setup(this);

		try {
			luckPerms = LuckPermsProvider.get();
		}
		catch (IllegalStateException e) {
			getLogger().severe("DristChat error: LuckPerms not found! Disabling plugin.");

			getServer().getPluginManager().disablePlugin(this);

			return;
		}

		getServer().getPluginManager().registerEvents(new MessageListener(), this);
	}

	@Override
	public void onDisable() {
	}
}
