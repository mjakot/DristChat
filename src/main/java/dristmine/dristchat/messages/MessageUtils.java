package dristmine.dristchat.messages;

import dristmine.dristchat.utils.ConfigKeys;
import dristmine.dristchat.utils.Utils;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessageUtils {
	private static final String PARSED_PREFIX_PLACEHOLDER = "%dc_prefix_parsed%";
	private static final String PARSED_SUFFIX_PLACEHOLDER = "%dc_suffix_parsed%";

	private static final String PUBLIC_MESSAGE_DELIMITER = ": ";
	private static final String PRONOUN = "you";
	private static final String ACTION = " whispered to ";
	private static final String PRIVATE_MESSAGE_DELIMITER = ACTION + PRONOUN  + PUBLIC_MESSAGE_DELIMITER;
	private static final String PRIVATE_MESSAGE_PREFIX = PRONOUN + ACTION;

	public enum EnabledFeatures {
		PREFIX_AND_SUFFIX,
		PREFIX,
		SUFFIX,
		NONE,
	}

	public static List<? extends Player> getEnabledChatPlayers() {
		return Bukkit.getOnlinePlayers()
				.stream()
				.filter(player -> Utils.featureEnabled(player, ConfigKeys.GLOBAL_PERMISSION))
				.toList();
	}

	public static EnabledFeatures categorizePlayer(Player player) {
		boolean prefixEnabled = Utils.featureEnabled(player, ConfigKeys.GLOBAL_PERMISSION_PREFIX);
		boolean suffixEnabled = Utils.featureEnabled(player, ConfigKeys.GLOBAL_PERMISSION_SUFFIX);

		if (prefixEnabled && suffixEnabled)
			return MessageUtils.EnabledFeatures.PREFIX_AND_SUFFIX;
		if (prefixEnabled)
			return MessageUtils.EnabledFeatures.PREFIX;
		if (suffixEnabled)
			return MessageUtils.EnabledFeatures.SUFFIX;

		return MessageUtils.EnabledFeatures.NONE;
	}

	public static Map<MessageUtils.EnabledFeatures, List<Player>> categorizePlayers(List<? extends Player> players) {
		return players.stream().collect(Collectors.groupingBy(MessageUtils::categorizePlayer));
	}

	public static void sendPublicMessage(EnabledFeatures viewerEnabledFeatures, Player viewer, Player sender, Component message) {
		viewer.sendMessage(createMessageComponent(viewerEnabledFeatures, sender, message));
	}

	public static void sendPrivateMessage(EnabledFeatures viewerEnabledFeatures, Player viewer, Player sender, Component message) {
		viewer.sendMessage(createMessageComponent(
				viewerEnabledFeatures,
				sender,
				message,
				PRIVATE_MESSAGE_DELIMITER,
				true,
				TextDecoration.ITALIC,
				true,
				NamedTextColor.GRAY
		));
	}

	public static void privateMessageSent(EnabledFeatures targetFeatures, Player sender, Player viewer, Component message) {
		sender.sendMessage(Component.text()
				.color(NamedTextColor.GRAY)
				.decoration(TextDecoration.ITALIC, true)
				.content(PRIVATE_MESSAGE_PREFIX)
				.append(Component.text(testPrefix(targetFeatures)))
				.append(Component.text(viewer.getName()))
				.append(Component.text(testSuffix(targetFeatures)))
				.append(Component.text(PUBLIC_MESSAGE_DELIMITER))
				.append(message)
				.build()
		);
	}

	public static void sendMessages(Map<MessageUtils.EnabledFeatures, List<Player>> viewers, Player sender, Component message) {
		if (viewers.containsKey(MessageUtils.EnabledFeatures.PREFIX_AND_SUFFIX)) {
			Audience.audience(viewers.get(MessageUtils.EnabledFeatures.PREFIX_AND_SUFFIX))
					.sendMessage(createMessageComponent(MessageUtils.EnabledFeatures.PREFIX_AND_SUFFIX, sender, message));

			return;
		}

		if (viewers.containsKey(MessageUtils.EnabledFeatures.PREFIX)) {
			Audience.audience(viewers.get(MessageUtils.EnabledFeatures.PREFIX))
					.sendMessage(createMessageComponent(MessageUtils.EnabledFeatures.PREFIX, sender, message));

			return;
		}

		if (viewers.containsKey(MessageUtils.EnabledFeatures.SUFFIX)) {
			Audience.audience(viewers.get(MessageUtils.EnabledFeatures.SUFFIX))
					.sendMessage(createMessageComponent(MessageUtils.EnabledFeatures.SUFFIX, sender, message));

			return;
		}

		Audience.audience(viewers.get(MessageUtils.EnabledFeatures.NONE))
				.sendMessage(createMessageComponent(MessageUtils.EnabledFeatures.NONE, sender, message));
	}

	private static Component createMessageComponent(EnabledFeatures targetFeatures, Player sender, Component originalMessage) {
		return createMessageComponent(
				targetFeatures,
				sender,
				originalMessage,
				PUBLIC_MESSAGE_DELIMITER,
				false,
				TextDecoration.BOLD,
				false,
				NamedTextColor.WHITE
		);
	}

	private static Component createMessageComponent(
			EnabledFeatures targetFeatures,
			Player sender,
			Component originalMessage,
			String delimiter,
			boolean includeDecoration,
			TextDecoration decorationType,
			boolean includeColor,
			NamedTextColor colorType) {
		return Component.text()
				.color(includeColor ? colorType : NamedTextColor.WHITE)                // conditional color assignment
				.decoration(decorationType, includeDecoration)                         // conditional decoration assignment
				.content(testPrefix(targetFeatures))
				.append(Component.text(sender.getName()))                              // append sender name
				.append(Component.text(testSuffix(targetFeatures)))
				.append(Component.text(delimiter))                                     // append delimiter
				.append(originalMessage)                                               // append message
				.build();                                                              // build component
	}

	private static String testPrefix(EnabledFeatures enabledFeatures) {
		if (enabledFeatures == EnabledFeatures.PREFIX_AND_SUFFIX || enabledFeatures == EnabledFeatures.PREFIX)
			return PARSED_PREFIX_PLACEHOLDER;

		return Utils.EMPTY_STRING;
	}

	private static String testSuffix(EnabledFeatures enabledFeatures) {
		if (enabledFeatures == EnabledFeatures.PREFIX_AND_SUFFIX || enabledFeatures == EnabledFeatures.SUFFIX)
			return PARSED_SUFFIX_PLACEHOLDER;

		return Utils.EMPTY_STRING;
	}
}
