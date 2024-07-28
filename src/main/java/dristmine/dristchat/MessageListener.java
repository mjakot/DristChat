package dristmine.dristchat;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MessageListener implements Listener {
	@EventHandler
	public void onMessage(AsyncChatEvent event) {
		event.setCancelled(true);

		Player sender = event.getPlayer();
		Component message = event.message();

		for (Player player : Bukkit.getOnlinePlayers()) {

		}
	}
}
