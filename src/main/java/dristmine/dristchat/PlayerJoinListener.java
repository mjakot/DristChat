package dristmine.dristchat;

import dristmine.dristchat.messages.MessageUtils;
import dristmine.dristchat.utils.ConfigKeys;
import dristmine.dristchat.utils.ConfigManager;

import dristmine.dristchat.utils.Utils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PlayerJoinListener implements Listener, Consumer<BukkitTask> {
	private static final String PAPER_CONFIG_DELIMITER = "_";

	private static final ConfigManager CONFIG = ConfigManager.getInstance();
	private static final List<String> title = CONFIG.getStringList(ConfigKeys.TITLE_ON_JOIN, ConfigKeys.HEADER);
	private static final List<String> subtitle = CONFIG.getStringList(ConfigKeys.TITLE_ON_JOIN, ConfigKeys.FOOTER);
	private static final List<String> action_bar = CONFIG.getStringList(ConfigKeys.ACTIONBAR_ON_JOIN, ConfigKeys.TEXT);

	private final DristChat plugin;
	private final BukkitScheduler scheduler;
	private final int maxCounter = Math.max(title.size(), Math.max(subtitle.size(), action_bar.size()));

	private PlayerJoinEvent event;
	private int currentCounter = Integer.MAX_VALUE;

	public PlayerJoinListener(DristChat plugin) {
		this.plugin = plugin;
		scheduler = plugin.getServer().getScheduler();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		this.event = event;

		currentCounter = 0;

		scheduler.runTaskTimer(plugin, this, 15, 10);

		event.joinMessage(null);

		List<? extends Player> enabledPlayer = getEnabledChatPlayers();

		Map<MessageUtils.EnabledFeatures, List<Player>> categorizedPlayers = MessageUtils.categorizePlayers(enabledPlayer);

		if (categorizedPlayers.containsKey(MessageUtils.EnabledFeatures.PREFIX_AND_SUFFIX))
			Audience.audience(categorizedPlayers.get(MessageUtils.EnabledFeatures.PREFIX_AND_SUFFIX))
					.sendMessage(MessageUtils.createMessageComponent(
							 Component.text("[+] "),
							MessageUtils.createViewerOrientedSender(MessageUtils.EnabledFeatures.PREFIX_AND_SUFFIX, event.getPlayer()),
							Component.empty(),
							null
									,null));

		else if (categorizedPlayers.containsKey(MessageUtils.EnabledFeatures.SUFFIX))
			Audience.audience(categorizedPlayers.get(MessageUtils.EnabledFeatures.SUFFIX))
					.sendMessage(MessageUtils.createMessageComponent(
							Component.text("[+] "),
							MessageUtils.createViewerOrientedSender(MessageUtils.EnabledFeatures.SUFFIX, event.getPlayer()),
							Component.empty(),
							null
							,null));

		else if (categorizedPlayers.containsKey(MessageUtils.EnabledFeatures.PREFIX))
			Audience.audience(categorizedPlayers.get(MessageUtils.EnabledFeatures.PREFIX))
					.sendMessage(MessageUtils.createMessageComponent(
							Component.text("[+] "),
							MessageUtils.createViewerOrientedSender(MessageUtils.EnabledFeatures.PREFIX, event.getPlayer()),
							Component.empty(),
							null
							,null));

		else if (categorizedPlayers.containsKey(MessageUtils.EnabledFeatures.NONE))
			Audience.audience(categorizedPlayers.get(MessageUtils.EnabledFeatures.NONE))
					.sendMessage(MessageUtils.createMessageComponent(
							Component.text("[+] "),
							MessageUtils.createViewerOrientedSender(MessageUtils.EnabledFeatures.NONE, event.getPlayer()),
							Component.empty(),
							null
							,null));

		playChatSound(enabledPlayer);
	}

	@Override
	public void accept(BukkitTask bukkitTask) {
		if (currentCounter >= maxCounter) {
			bukkitTask.cancel();

			return;
		}

		Audience.audience(event.getPlayer()).clearTitle();
		Audience.audience(event.getPlayer()).sendActionBar(Component.empty());

		Audience.audience(event.getPlayer()).showTitle(Title.title(LegacyComponentSerializer.legacyAmpersand().deserialize(title.get(currentCounter)),LegacyComponentSerializer.legacyAmpersand().deserialize(subtitle.get(0)),Title.Times.times(Duration.ofSeconds(0),Duration.ofMillis(500),Duration.ofMillis(100))));
		Audience.audience(event.getPlayer()).sendActionBar(LegacyComponentSerializer.legacyAmpersand().deserialize(action_bar.get(0)));

		++currentCounter;
	}

	private void playChatSound(List<? extends Player> chatEnabledPlayers) {
		Audience.audience( //create audience from players who have chat sound feature enabled
				chatEnabledPlayers
						.stream()
						.filter(player -> player.hasPermission(CONFIG.getString(ConfigKeys.PLAYER_JOINED, ConfigKeys.PERMISSION)))
						.collect(Collectors.toList())
		).playSound( //play chat sound for that audience
				Sound.sound(Key.key(CONFIG.getString(ConfigKeys.PLAYER_JOINED, ConfigKeys.SOUND)
								.toLowerCase()
								.replace(PAPER_CONFIG_DELIMITER, ConfigManager.CONFIG_DELIMITER)),
						Sound.Source.MASTER,
						(float)CONFIG.getFloatingPoint(ConfigKeys.GLOBAL_SOUND_VOLUME),
						(float)CONFIG.getFloatingPoint(ConfigKeys.GLOBAL_SOUND_PITCH)
				));
	}

	private static List<? extends Player> getEnabledChatPlayers() {
		return Bukkit.getOnlinePlayers()
				.stream()
				.filter(player -> Utils.featureEnabled(player, ConfigKeys.PLAYER_JOINED))
				.toList();
	}
}
