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

		@Override
		public String toString() {
			return command;
		}
	}

	@EventHandler
	public void onPrivateMessage(PlayerCommandPreprocessEvent event) {
		String command = event.getMessage();

		if (
			Arrays.stream(PrivateMessageCommands.values())
					.map(PrivateMessageCommands::toString)
					.noneMatch(x -> command.startsWith(x, 1))
		) {
			return;
		}

		event.setCancelled(true);

		Player viewer = Bukkit.getPlayer(command.split(" ")[1]);

		if (viewer == null) {
			event.getPlayer().sendMessage("Invalid user");

			return;
		}

		MessageUtils.sendPrivateMessage(MessageUtils.categorizePlayer(viewer), viewer, event.getPlayer(), Component.text(command.split(" ")[2]));
	}
}
