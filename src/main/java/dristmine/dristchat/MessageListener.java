package dristmine.dristchat;

import dristmine.dristchat.utils.ConfigManager;
import dristmine.dristchat.utils.ConfigKeys;
import dristmine.dristchat.utils.DristAlerts;
import dristmine.dristchat.utils.Utils;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class MessageListener implements Listener {
	private static final String SENDER_PLACEHOLDER = "%sender%";
	private static final String MESSAGE_PLACEHOLDER = "%message%";
	private static final String	PREFIX_PLACEHOLDER = "%p%";
	private static final String SUFFIX_PLACEHOLDER = "%s%";
	private static final String PARSED_PREFIX_PLACEHOLDER = "%dc_prefix_parsed%";
	private static final String PARSED_SUFFIX_PLACEHOLDER = "%dc_suffix_parsed%";

	public MessageListener() {}

	@EventHandler
	public void onMessage(AsyncChatEvent event) {
		event.setCancelled(true);

		Player sender = event.getPlayer();

		String message = ConfigManager.getInstance().getString(ConfigKeys.MESSAGE_FORMAT, ConfigKeys.MESSAGE)
				.replace(SENDER_PLACEHOLDER, sender.getName())
				.replace(MESSAGE_PLACEHOLDER, Objects.requireNonNull(event.message().insertion()));

		for (Player player : Bukkit.getOnlinePlayers()) {
			message = player.hasPermission(ConfigManager.getInstance().getString(ConfigKeys.GLOBAL_PERMISSION_PREFIX)) ?
					message.replace(PREFIX_PLACEHOLDER, PlaceholderAPI.setPlaceholders(sender, PARSED_PREFIX_PLACEHOLDER)) :
					message.replace(PREFIX_PLACEHOLDER, Utils.EMPTY_STRING);

			message = player.hasPermission(ConfigManager.getInstance().getString(ConfigKeys.GLOBAL_PERMISSION_SUFFIX)) ?
					message.replace(SUFFIX_PLACEHOLDER, PlaceholderAPI.setPlaceholders(sender, PARSED_SUFFIX_PLACEHOLDER)) :
					message.replace(SUFFIX_PLACEHOLDER, Utils.EMPTY_STRING);

			Sound sound = player.hasPermission(ConfigManager.getInstance().getString(ConfigKeys.GLOBAL_PERMISSION_SOUND)) ?
					Sound.valueOf(ConfigManager.getInstance().getString(ConfigKeys.MESSAGE_FORMAT, ConfigKeys.SOUND)) :
					null;

			DristAlerts.sendPrivateMessage(message,
					ConfigManager.getInstance().getString(ConfigKeys.GLOBAL_PERMISSION),
					sound,
					player);
		}
	}
}
