package dristmine.dristchat;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MessageListener implements Listener {
	@EventHandler
	public void onMessage(AsyncChatEvent event) {
		event.setCancelled(true);
	}
}
