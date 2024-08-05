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
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static dristmine.dristchat.utils.enums.ChatFeatures.*;
import static dristmine.dristchat.utils.enums.SoundTypes.*;

public class OnPlayerJoin implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player sender = event.getPlayer();

		if (Utils.isFeatureEnabled(sender, ACTIONBAR_ON_PLAYER_JOIN))
			AudienceUtils.sendActionbarAnimation(sender, ComponentUtils.createPlayerJoinActionbarAnimation());

		if (Utils.isFeatureEnabled(sender, TITLE_ON_PLAYER_JOIN))
			AudienceUtils.sendTitleAnimation(
					sender,
					new ArrayList<>(ComponentUtils.createPlayerJoinTitleAnimation()),
					new ArrayList<>(ComponentUtils.createPlayerJoinSubtitleAnimation())
			);

		if (Utils.isFeatureEnabled(sender, SOUND_ON_PLAYER_JOIN))
			SoundUtils.playSound(Audience.audience(sender), PLAYER_JOINED_SENT);

		List<Player> joinMessageEnabledPlayers = AudienceUtils.sortPlayersByChatFeature(Bukkit.getOnlinePlayers(), PLAYER_JOIN_MESSAGES);

		SoundTypes soundToPlay;

		if (!sender.hasPlayedBefore()) {
			Audience.audience(joinMessageEnabledPlayers).sendMessage(ComponentUtils.createPlayerFirstJoinMessage(sender));

			soundToPlay = PLAYER_FIRST_JOINED_RECEIVED;
		}
		else {
			Map<ChatDecorations, List<Player>> playersToChatDecorations = AudienceUtils.mapPlayersToChatDecorations(joinMessageEnabledPlayers);

			for (ChatDecorations decoration : ChatDecorations.values()) {
				Component joinMessage = ComponentUtils.createPlayerJoinMessage(decoration, sender);

				AudienceUtils.sendMessageIfCategoryExists(playersToChatDecorations, decoration, joinMessage);
			}

			soundToPlay = PLAYER_JOIN_RECEIVED;
		}

		joinMessageEnabledPlayers.remove(sender);

		SoundUtils.playSound(Audience.audience(joinMessageEnabledPlayers), soundToPlay);

		event.joinMessage(null);
	}
}
