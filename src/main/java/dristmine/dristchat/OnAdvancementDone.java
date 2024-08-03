package dristmine.dristchat;

import dristmine.dristchat.utils.ConfigKeys;
import dristmine.dristchat.utils.Utils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.util.List;

public class OnAdvancementDone implements Listener {
	@EventHandler
	public void onAdvancementDone(PlayerAdvancementDoneEvent event) {
		Audience.audience(getEnabledChatPlayers()).sendMessage(event.message());

		event.message(null);
	}

	private static List<? extends Player> getEnabledChatPlayers() {
		return Bukkit.getOnlinePlayers()
				.stream()
				.filter(player -> Utils.featureEnabled(player, ConfigKeys.ADVANCEMENTS))
				.toList();
	}
}
