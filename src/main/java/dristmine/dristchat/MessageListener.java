package dristmine.dristchat;

import dristmine.dristchat.utils.ConfigManager;
import dristmine.dristchat.utils.ConfigKeys;
import dristmine.dristchat.utils.Utils;

import io.papermc.paper.event.player.AsyncChatEvent;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MessageListener implements Listener {
	private enum EnabledFeatures {
		PREFIX_AND_SUFFIX,
		PREFIX,
		SUFFIX,
		NONE,
	}

	private static final String PAPER_CONFIG_DELIMITER = "_";
	private static final String MESSAGE_DELIMITER = ": ";
	private static final String PARSED_PREFIX_PLACEHOLDER = "%dc_prefix_parsed%";
	private static final String PARSED_SUFFIX_PLACEHOLDER = "%dc_suffix_parsed%";


	private static final ConfigManager CONFIG = ConfigManager.getInstance();

	public MessageListener() {}

	@EventHandler
	public void onMessage(AsyncChatEvent event) {
		event.setCancelled(true);

		List<? extends Player> chatEnabledPlayers = Bukkit.getOnlinePlayers()
				.stream()
				.filter(x -> x.hasPermission(CONFIG.getString(ConfigKeys.GLOBAL_PERMISSION)))
				.toList();

		sendMessages(categorizeChatEnabledPlayer(chatEnabledPlayers), event);
		playChatSound(chatEnabledPlayers);
	}

	private Map<EnabledFeatures, List<Player>> categorizeChatEnabledPlayer(List<? extends Player> chatEnabledPlayers) {
		return chatEnabledPlayers.stream().collect(Collectors.groupingBy(player -> {
			boolean prefixEnabled = player.hasPermission(CONFIG.getString(ConfigKeys.GLOBAL_PERMISSION_PREFIX));
			boolean suffixEnabled = player.hasPermission(CONFIG.getString(ConfigKeys.GLOBAL_PERMISSION_SUFFIX));

			if (prefixEnabled && suffixEnabled)
				return EnabledFeatures.PREFIX_AND_SUFFIX;
			if (prefixEnabled)
				return EnabledFeatures.PREFIX;
			if (suffixEnabled)
				return EnabledFeatures.SUFFIX;

			return EnabledFeatures.NONE;
		}));
	}

	private void sendMessages(Map<EnabledFeatures, List<Player>> viewers, AsyncChatEvent event) {
		Function<EnabledFeatures, Component> getMessage = enabledFeature -> //lambda for message generation
				Component.text(
						enabledFeature == EnabledFeatures.PREFIX_AND_SUFFIX || //conditional prefix placeholder assignment
						enabledFeature == EnabledFeatures.PREFIX ?
								PARSED_PREFIX_PLACEHOLDER : Utils.EMPTY_STRING
				)
				.append(Component.text(event.getPlayer().getName()))
				.append(Component.text(
						enabledFeature == EnabledFeatures.PREFIX_AND_SUFFIX || //conditional suffix placeholder assignment
						enabledFeature == EnabledFeatures.SUFFIX ?
								PARSED_SUFFIX_PLACEHOLDER : Utils.EMPTY_STRING)
				)
				.append(Component.text(MESSAGE_DELIMITER))
				.append(event.message());

		if (viewers.containsKey(EnabledFeatures.PREFIX_AND_SUFFIX)) {
			Audience.audience(viewers.get(EnabledFeatures.PREFIX_AND_SUFFIX))
					.sendMessage(getMessage.apply(EnabledFeatures.PREFIX_AND_SUFFIX));

			return;
		}

		if (viewers.containsKey(EnabledFeatures.PREFIX)) {
			Audience.audience(viewers.get(EnabledFeatures.PREFIX))
					.sendMessage(getMessage.apply(EnabledFeatures.PREFIX));

			return;
		}

		if (viewers.containsKey(EnabledFeatures.SUFFIX)) {
			Audience.audience(viewers.get(EnabledFeatures.SUFFIX))
					.sendMessage(getMessage.apply(EnabledFeatures.SUFFIX));

			return;
		}

		Audience.audience(viewers.get(EnabledFeatures.NONE))
				.sendMessage(getMessage.apply(EnabledFeatures.NONE));
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
