package dristmine.dristchat;

import dristmine.dristchat.utils.ConfigKeys;
import dristmine.dristchat.utils.Utils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

public class OnDeath implements Listener {
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Audience.audience(getEnabledChatPlayers()).sendMessage(event.deathMessage());
		event.deathMessage(null);
	}

	private static List<? extends Player> getEnabledChatPlayers() {
		return Bukkit.getOnlinePlayers()
				.stream()
				.filter(player -> Utils.featureEnabled(player, ConfigKeys.DEATHS))
				.toList();
	}
}
