package dristmine.dristchat.listeners;

import dristmine.dristchat.utils.AudienceUtils;
import dristmine.dristchat.utils.component.ComponentUtils;
import dristmine.dristchat.utils.Utils;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.util.List;

import static dristmine.dristchat.utils.enums.ChatFeatures.*;

public class OnAdvancementDone implements Listener {
	@EventHandler
	public void onAdvancementDone(PlayerAdvancementDoneEvent event) {
		if (Utils.isNull(event.message()))
			return;

		List<Player> advancementMessagesEnabledPlayers = AudienceUtils.sortPlayersByChatFeature(Bukkit.getOnlinePlayers(), ADVANCEMENTS_MESSAGES);

		//noinspection ConstantConditions
		Component advancementMessage = ComponentUtils.createAdvancementMessage(event.message());

		Audience.audience(advancementMessagesEnabledPlayers).sendMessage(advancementMessage);

		event.message(null);
	}
}
