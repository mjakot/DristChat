package dristmine.dristchat.utils.component;

import dristmine.dristchat.utils.config.ConfigKeys;
import dristmine.dristchat.utils.enums.ChatDecorations;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static dristmine.dristchat.DristChat.config;
import static dristmine.dristchat.utils.config.ConfigKeys.*;
import static dristmine.dristchat.utils.Utils.testPrefix;
import static dristmine.dristchat.utils.Utils.testSuffix;

public class ComponentUtils {
	public static @NotNull Component createPublicMessage(
			@NotNull ChatDecorations viewersChatDecorations,
			@NotNull Player sender,
			@NotNull Component message
	) {
		ConfigKeys section = MESSAGE_FORMAT;

		return ComponentTemplate.template(config().getString(section, MESSAGE))
				.sender(sender.getName())
				.senderPrefix(testPrefix(viewersChatDecorations, sender))
				.senderSuffix(testSuffix(viewersChatDecorations, sender))
				.message(message)
				.build(
						config().getTextDecoration(section),
						config().getTextColor(section)
				);
	}

	public static @NotNull Component createPrivateMessage(
			@NotNull ChatDecorations viewerChatDecorations,
			@NotNull Player sender,
			@NotNull Player receiver,
			@NotNull Component message
	) {
		ConfigKeys section = PRIVATE_MESSAGE;

		return ComponentTemplate.template(config().getString(section, MESSAGE))
				.sender(sender.getName())
				.senderPrefix(testPrefix(viewerChatDecorations, sender))
				.senderSuffix(testSuffix(viewerChatDecorations, sender))
				.receiver(receiver.getName())
				.receiverPrefix(testPrefix(viewerChatDecorations, receiver))
				.receiverSuffix(testSuffix(viewerChatDecorations, receiver))
				.message(message)
				.build(
						config().getTextDecoration(section),
						config().getTextColor(section)
				);
	}

	public static @NotNull Component createPlayerFirstJoinMessage(
			@NotNull Player sender
	) {
		ConfigKeys section = PLAYER_FIRST_JOINED;

		return ComponentTemplate.template(config().getString(section, MESSAGE))
				.sender(sender.getName())
				.build(
						config().getTextDecoration(section),
						config().getTextColor(section)
				);
	}

	public static @NotNull Component createPlayerJoinMessage(
			@NotNull ChatDecorations viewersChatDecorations,
			@NotNull Player sender
	) {
		ConfigKeys section = PLAYER_JOINED;

		return ComponentTemplate.template(config().getString(section, MESSAGE))
				.sender(sender.getName())
				.senderPrefix(testPrefix(viewersChatDecorations, sender))
				.senderSuffix(testSuffix(viewersChatDecorations, sender))
				.build(
						config().getTextDecoration(section),
						config().getTextColor(section)
				);
	}

	public static @NotNull Component createPlayerLeftMessage(
			@NotNull ChatDecorations viewersChatDecorations,
			@NotNull Player sender
	) {
		ConfigKeys section = PLAYER_LEFT;

		return ComponentTemplate.template(config().getString(section, MESSAGE))
				.sender(sender.getName())
				.senderPrefix(testPrefix(viewersChatDecorations, sender))
				.senderSuffix(testSuffix(viewersChatDecorations, sender))
				.build(
						config().getTextDecoration(section),
						config().getTextColor(section)
				);
	}

	public static @NotNull List<Component> createPlayerJoinTitleAnimation() {
		ConfigKeys section = TITLE_ON_JOIN;

		return createAnimationComponents(
				section,
				HEADER,
				config().getTextDecoration(section),
				config().getTextColor(section)
		);
	}

	public static @NotNull List<Component> createPlayerJoinSubtitleAnimation() {
		ConfigKeys section = TITLE_ON_JOIN;

		return createAnimationComponents(
				section,
				FOOTER,
				config().getTextDecoration(section),
				config().getTextColor(section)
		);
	}

	public static @NotNull List<Component> createPlayerJoinActionbarAnimation() {
		ConfigKeys key = ACTIONBAR_ON_JOIN;

		return createAnimationComponents(
				key,
				MESSAGE,
				config().getTextDecoration(key),
				config().getTextColor(key)
		);
	}

	public static @NotNull Component createAdvancementMessage(
			@NotNull Component message
	) {
		ConfigKeys section = ADVANCEMENTS;

		return ComponentTemplate.template(config().getString(section, MESSAGE))
				.message(message)
				.build(
						config().getTextDecoration(section),
						config().getTextColor(section)
				);
	}

	public static @NotNull Component createDeathMessage(
			@NotNull Component message
	) {
		ConfigKeys section = DEATHS;

		return ComponentTemplate.template(config().getString(section, MESSAGE))
				.message(message)
				.build(
						config().getTextDecoration(section),
						config().getTextColor(section)
				);
	}

	private static @NotNull List<Component> createAnimationComponents(
			@NotNull ConfigKeys section,
			@NotNull ConfigKeys key,
			@Nullable TextDecoration decoration,
			@Nullable TextColor color
	) {
		List<String> frames = config().getStringList(section, key);
		List<Component> result = new ArrayList<>(frames.size());

		for (String message : frames) {
			result.add(
					ComponentTemplate.template(message)
							.build(decoration, color)
			);
		}

		return result;
	}
}
