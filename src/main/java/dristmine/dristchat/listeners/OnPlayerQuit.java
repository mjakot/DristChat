package dristmine.dristchat.listeners;

import dristmine.dristchat.utils.AudienceUtils;
import dristmine.dristchat.utils.SoundUtils;
import dristmine.dristchat.utils.component.ComponentUtils;
import dristmine.dristchat.utils.enums.ChatDecorations;
import dristmine.dristchat.utils.enums.SoundTypes;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.Map;

import static dristmine.dristchat.utils.enums.ChatFeatures.*;

public class OnPlayerQuit implements Listener {
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player sender = event.getPlayer();

		List<Player> quitMessageEnabledPlayers = AudienceUtils.sortPlayersByChatFeature(Bukkit.getOnlinePlayers(), PLAYER_LEFT_MESSAGES);

		Map<ChatDecorations, List<Player>> playersToChatDecorations = AudienceUtils.mapPlayersToChatDecorations(quitMessageEnabledPlayers);

		for (ChatDecorations decoration : ChatDecorations.values()) {
			Component quitMessage = ComponentUtils.createPlayerLeftMessage(decoration, sender);

			AudienceUtils.sendMessageIfCategoryExists(playersToChatDecorations, decoration, quitMessage);
		}

		SoundUtils.playSound(Audience.audience(quitMessageEnabledPlayers), SoundTypes.PLAYER_LEFT_RECEIVED);
	}
}
