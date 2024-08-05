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
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

import static dristmine.dristchat.utils.enums.ChatFeatures.*;

public class OnDeath implements Listener {
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		List<Player> deathMessageEnabledPlayers = AudienceUtils.sortPlayersByChatFeature(Bukkit.getOnlinePlayers(), DEATHS_MESSAGES);

		//noinspection ConstantConditions
		Component deathMessage = ComponentUtils.createDeathMessage(Utils.isNull(event.deathMessage()) ? Component.empty() : event.deathMessage());

		Audience.audience(deathMessageEnabledPlayers).sendMessage(deathMessage);

		event.deathMessage(null);
	}
}
