package dristmine.dristchat.listeners;

import dristmine.dristchat.utils.*;
import dristmine.dristchat.utils.component.ComponentUtils;
import dristmine.dristchat.utils.enums.ChatDecorations;
import dristmine.dristchat.utils.enums.SoundTypes;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;

import static dristmine.dristchat.utils.Utils.PRIVATE_MESSAGE_COMMANDS;
import static dristmine.dristchat.utils.Utils.COMMAND_START_INDEX;
import static dristmine.dristchat.utils.Utils.COMMAND_ARGS_DELIMITER;
import static dristmine.dristchat.utils.Utils.RECEIVER_INDEX;
import static dristmine.dristchat.utils.Utils.MESSAGE_INDEX;

public class OnPrivateMessage implements Listener {
	@EventHandler
	public void onPrivateMessage(PlayerCommandPreprocessEvent event) {
		String command = event.getMessage();

		if (Arrays.stream(PRIVATE_MESSAGE_COMMANDS).noneMatch(
				privateCommand -> command.startsWith(privateCommand, COMMAND_START_INDEX)
		)) return;

		event.setCancelled(true);

		Component originalMessage = extractMessage(command);

		Player sender = event.getPlayer();
		Player receiver = extractReceiver(command);

		ChatDecorations senderChatDecorations = AudienceUtils.determinePlayerChatDecoration(sender);
		ChatDecorations receiverChatDecorations = AudienceUtils.determinePlayerChatDecoration(receiver);

		Component senderMessage = ComponentUtils.createPrivateMessage(senderChatDecorations, sender, receiver, originalMessage);
		Component receiverMessage = ComponentUtils.createPrivateMessage(receiverChatDecorations, sender, receiver, originalMessage);

		sender.sendMessage(senderMessage);
		receiver.sendMessage(receiverMessage);

		SoundUtils.playSound(Audience.audience(receiver), SoundTypes.PRIVATE_MESSAGE_SENT_RECEIVED);
	}

	private Player extractReceiver(String command) {
		return Bukkit.getPlayer(command.split(COMMAND_ARGS_DELIMITER)[RECEIVER_INDEX]);
	}

	private Component extractMessage(String command) {
		return Component.text(command.split(COMMAND_ARGS_DELIMITER)[MESSAGE_INDEX]);
	}
}
