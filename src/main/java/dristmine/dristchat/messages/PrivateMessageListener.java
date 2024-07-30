package dristmine.dristchat.messages;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;

public class PrivateMessageListener implements Listener {
	private enum PrivateMessageCommands {
		MSG("msg"),
		TELL("tell");

		private final String command;

		PrivateMessageCommands(String command) {
			this.command = command;
		}

		public String getValue() {
			return command;
		}
	}

	private enum CommandComponents {
		RECEIVER(1),
		MESSAGE(2);

		private final int component;

		CommandComponents(int component) {
			this.component = component;
		}

		public int getValue() {
			return component;
		}
	}

	private static final int COMMAND_START_POSITION = 1;
	private static final String ARGS_SEPARATOR = " ";
	private static final String INVALID_PLAYER_ERROR = "Invalid player";

	@EventHandler
	public void onPrivateMessage(PlayerCommandPreprocessEvent event) {
		String command = event.getMessage();

		if (
			Arrays.stream(PrivateMessageCommands.values())
					.map(PrivateMessageCommands::getValue)
					.noneMatch(x -> command.startsWith(x, COMMAND_START_POSITION))
		) {
			return;
		}

		event.setCancelled(true);

		String[] decomposedCommand = command.split(ARGS_SEPARATOR);

		Player viewer = Bukkit.getPlayer(decomposedCommand[CommandComponents.RECEIVER.getValue()]);
		Component originalMessage = Component.text(decomposedCommand[CommandComponents.MESSAGE.getValue()]);

		if (viewer == null) {
			event.getPlayer().sendMessage(INVALID_PLAYER_ERROR);

			return;
		}

		MessageUtils.sendPrivateMessage(MessageUtils.categorizePlayer(viewer), viewer, event.getPlayer(), originalMessage);
		MessageUtils.privateMessageSent(MessageUtils.categorizePlayer(event.getPlayer()), event.getPlayer(), viewer, originalMessage);
	}
}
