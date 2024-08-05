package dristmine.dristchat.utils.component;

import dristmine.dristchat.utils.component.ComponentTemplate;
import dristmine.dristchat.utils.enums.ChatDecorations;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

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
		return ComponentTemplate.template(config().getString(MESSAGE_FORMAT, MESSAGE))
				.sender(sender.getName())
				.senderPrefix(testPrefix(viewersChatDecorations, sender))
				.senderSuffix(testSuffix(viewersChatDecorations, sender))
				.message(PlainTextComponentSerializer.plainText().serialize(message))
				.build(null, NamedTextColor.WHITE);
	}

	public static @NotNull Component createPrivateMessage(
			@NotNull ChatDecorations viewerChatDecorations,
			@NotNull Player sender,
			@NotNull Player receiver,
			@NotNull Component message
	) {
		return ComponentTemplate.template(config().getString(PRIVATE_MESSAGE, MESSAGE))
				.sender(sender.getName())
				.senderPrefix(testPrefix(viewerChatDecorations, sender))
				.senderSuffix(testSuffix(viewerChatDecorations, sender))
				.receiver(receiver.getName())
				.receiverPrefix(testPrefix(viewerChatDecorations, receiver))
				.receiverSuffix(testSuffix(viewerChatDecorations, receiver))
				.message(PlainTextComponentSerializer.plainText().serialize(message))
				.build(TextDecoration.ITALIC, NamedTextColor.DARK_GRAY);
	}

	public static @NotNull Component createPlayerFirstJoinMessage(
			@NotNull Player sender
	) {
		return ComponentTemplate.template(config().getString(PLAYER_FIRST_JOINED, MESSAGE))
				.sender(sender.getName())
				.build(TextDecoration.BOLD, NamedTextColor.DARK_GREEN);
	}

	public static @NotNull Component createPlayerJoinMessage(
			@NotNull ChatDecorations viewersChatDecorations,
			@NotNull Player sender
	) {
		return ComponentTemplate.template(config().getString(PLAYER_JOINED, MESSAGE))
				.sender(sender.getName())
				.senderPrefix(testPrefix(viewersChatDecorations, sender))
				.senderSuffix(testSuffix(viewersChatDecorations, sender))
				.build(TextDecoration.BOLD, NamedTextColor.YELLOW);
	}

	public static @NotNull Component createPlayerLeftMessage(
			@NotNull ChatDecorations viewersChatDecorations,
			@NotNull Player sender
	) {
		return ComponentTemplate.template(config().getString(PLAYER_LEFT, MESSAGE))
				.sender(sender.getName())
				.senderPrefix(testPrefix(viewersChatDecorations, sender))
				.senderSuffix(testSuffix(viewersChatDecorations, sender))
				.build(TextDecoration.BOLD, NamedTextColor.YELLOW);
	}

	public static @NotNull List<Component> createPlayerJoinTitleAnimation() {
		List<String> frames = config().getStringList(TITLE_ON_JOIN, HEADER);
		List<Component> result = new ArrayList<>(frames.size());

		for (String message : frames) {
			result.add(
					ComponentTemplate.template(message)
							.build(null, null)
			);
		}

		return result;
	}

	public static @NotNull List<Component> createPlayerJoinSubtitleAnimation() {
		List<String> frames = config().getStringList(TITLE_ON_JOIN, FOOTER);
		List<Component> result = new ArrayList<>(frames.size());

		for (String message : frames) {
			result.add(
					ComponentTemplate.template(message)
							.build(null, null)
			);
		}

		return result;
	}

	public static @NotNull List<Component> createPlayerJoinActionbarAnimation() {
		List<String> frames = config().getStringList(ACTIONBAR_ON_JOIN, MESSAGE);
		List<Component> result = new ArrayList<>(frames.size());

		for (String message : frames) {
			result.add(
					ComponentTemplate.template(message)
							.build(TextDecoration.UNDERLINED, NamedTextColor.GOLD)
			);
		}

		return result;
	}

	public static @NotNull Component createAdvancementMessage(
			@NotNull Component message
	) {
		return ComponentTemplate.template(config().getString(ADVANCEMENTS, MESSAGE))
				.message(message)
				.build(TextDecoration.BOLD, NamedTextColor.GREEN);
	}

	public static @NotNull Component createDeathMessage(
			@NotNull Component message
	) {
		return ComponentTemplate.template(config().getString(DEATHS, MESSAGE))
				.message(message)
				.build(TextDecoration.BOLD, NamedTextColor.RED);
	}
}
