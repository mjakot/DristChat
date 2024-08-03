package dristmine.dristchat.messages;

import dristmine.dristchat.utils.ConfigKeys;
import dristmine.dristchat.utils.Utils;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
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

	public static void sendPrivateMessage(EnabledFeatures viewerEnabledFeatures, Player viewer, Player sender, Component message) {
		viewer.sendMessage(createMessageComponent(
				viewerEnabledFeatures,
				sender,
				message,
				PRIVATE_MESSAGE_DELIMITER,
				TextDecoration.ITALIC,
				NamedTextColor.GRAY
		));
	}

	public static void privateMessageSent(EnabledFeatures targetFeatures, Player sender, Player viewer, Component message) {
		sender.sendMessage(createMessageComponent(
				Component.text(PRIVATE_MESSAGE_PREFIX + createViewerOrientedSender(targetFeatures, viewer)),
							Component.text(PUBLIC_MESSAGE_DELIMITER),
							message,
							NamedTextColor.GRAY,
							TextDecoration.ITALIC
		));
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

	public static Component createMessageComponent(EnabledFeatures targetFeatures, Player sender, Component originalMessage) {
		return createMessageComponent(targetFeatures, sender, originalMessage, PUBLIC_MESSAGE_DELIMITER, null, null);
	}

	public static Component createMessageComponent(
			EnabledFeatures targetFeatures,
			Player sender,
			Component originalMessage,
			String delimiter,
			@Nullable TextDecoration decorationType,
			@Nullable NamedTextColor colorType) {
	return createMessageComponent(
					createViewerOrientedSender(targetFeatures, sender),
					Component.text(delimiter),
					originalMessage,
					colorType,
					decorationType
			);
	}

	public static Component createMessageComponent(
			Component firstPart,
			Component delimiter,
			Component secondPart,
			@Nullable NamedTextColor color,
			@Nullable TextDecoration decoration
	) {
		return Component.text()
				.color(color)
				.decoration(decoration != null ? decoration : TextDecoration.ITALIC, decoration != null)
				.append(firstPart)
				.append(delimiter)
				.append(secondPart)
				.build();
	}

	public static String testPrefix(EnabledFeatures enabledFeatures) {
		if (enabledFeatures == EnabledFeatures.PREFIX_AND_SUFFIX || enabledFeatures == EnabledFeatures.PREFIX)
			return PARSED_PREFIX_PLACEHOLDER;

		return Utils.EMPTY_STRING;
	}

	public static String testSuffix(EnabledFeatures enabledFeatures) {
		if (enabledFeatures == EnabledFeatures.PREFIX_AND_SUFFIX || enabledFeatures == EnabledFeatures.SUFFIX)
			return PARSED_SUFFIX_PLACEHOLDER;

		return Utils.EMPTY_STRING;
	}

	public static Component createViewerOrientedSender(EnabledFeatures viewerFeatures, Player sender) {
		return Component.text(testPrefix(viewerFeatures) + sender.getName() + testSuffix(viewerFeatures));
	}
}
