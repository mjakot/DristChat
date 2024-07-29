package dristmine.dristchat;

import dristmine.dristchat.messages.MessageListener;
import dristmine.dristchat.messages.PrivateMessageListener;
import dristmine.dristchat.utils.ConfigManager;
import dristmine.dristchat.utils.Utils;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;

import org.bukkit.plugin.java.JavaPlugin;

public class DristChat extends JavaPlugin {

	@Override
	public void onEnable() {
		ConfigManager.setup(this);

		try {
			LuckPerms luckPerms = LuckPermsProvider.get();
		}
		catch (IllegalStateException e) {
			getLogger().severe("DristChat error: LuckPerms not found! Disabling plugin.");

			getServer().getPluginManager().disablePlugin(this);

			return;
		}

		getServer().getPluginManager().registerEvents(new MessageListener(), this);
		getServer().getPluginManager().registerEvents(new PrivateMessageListener(), this);

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
}
