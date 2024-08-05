package dristmine.dristchat.listeners;

import dristmine.dristchat.utils.*;

import dristmine.dristchat.utils.component.ComponentUtils;
import dristmine.dristchat.utils.enums.ChatDecorations;
import dristmine.dristchat.utils.enums.SoundTypes;
import io.papermc.paper.event.player.AsyncChatEvent;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Map;

import static dristmine.dristchat.utils.enums.ChatFeatures.*;

public class OnPublicMessage implements Listener {
	@EventHandler
	public void onPublicMessage(AsyncChatEvent event) {
		event.setCancelled(true);

		List<Player> chatEnabledPlayers = AudienceUtils.sortPlayersByChatFeature(Bukkit.getOnlinePlayers(), CHAT_MESSAGES);
		Map<ChatDecorations, List<Player>> playersToChatDecorations = AudienceUtils.mapPlayersToChatDecorations(chatEnabledPlayers);

		for (ChatDecorations decoration : ChatDecorations.values()) {
			Component message = ComponentUtils.createPublicMessage(decoration, event.getPlayer(), event.message());

			AudienceUtils.sendMessageIfCategoryExists(playersToChatDecorations, decoration, message);
		}

		Audience messageSoundEnabledPlayers = Audience.audience(AudienceUtils.sortPlayersByChatFeature(chatEnabledPlayers, MESSAGES_SOUND));

		SoundUtils.playSound(messageSoundEnabledPlayers, SoundTypes.PUBLIC_MESSAGE_SENT_RECEIVED);
	}
}
