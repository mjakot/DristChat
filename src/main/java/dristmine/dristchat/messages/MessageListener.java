package dristmine.dristchat.messages;

import dristmine.dristchat.utils.ConfigManager;
import dristmine.dristchat.utils.ConfigKeys;

import io.papermc.paper.event.player.AsyncChatEvent;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.stream.Collectors;

public class MessageListener implements Listener {
	private static final String PAPER_CONFIG_DELIMITER = "_";

	private static final ConfigManager CONFIG = ConfigManager.getInstance();

	public MessageListener() {}

	@EventHandler
	public void onMessage(AsyncChatEvent event) {
		event.setCancelled(true);

		List<? extends Player> chatEnabledPlayers = MessageUtils.getEnabledChatPlayers();

		MessageUtils.sendMessages(MessageUtils.categorizePlayers(MessageUtils.getEnabledChatPlayers()), event.getPlayer(), event.message());

		playChatSound(chatEnabledPlayers);
	}

	private void playChatSound(List<? extends Player> chatEnabledPlayers) {
		Audience.audience( //create audience from players who have chat sound feature enabled
				chatEnabledPlayers
						.stream()
						.filter(player -> player.hasPermission(CONFIG.getString(ConfigKeys.GLOBAL_PERMISSION_SOUND)))
						.collect(Collectors.toList())
		).playSound( //play chat sound for that audience
				Sound.sound(Key.key(CONFIG.getString(ConfigKeys.GLOBAL_MESSAGE_SOUND)
										.toLowerCase()
										.replace(PAPER_CONFIG_DELIMITER, ConfigManager.CONFIG_DELIMITER)),
						Sound.Source.MASTER,
						(float)CONFIG.getFloatingPoint(ConfigKeys.GLOBAL_SOUND_VOLUME),
						(float)CONFIG.getFloatingPoint(ConfigKeys.GLOBAL_SOUND_PITCH)
				));
	}
}
