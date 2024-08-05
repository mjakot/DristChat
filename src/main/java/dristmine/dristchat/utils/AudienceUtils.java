package dristmine.dristchat.utils;

import dristmine.dristchat.DristChat;
import dristmine.dristchat.utils.config.ConfigKeys;
import dristmine.dristchat.utils.enums.ChatDecorations;
import dristmine.dristchat.utils.enums.ChatFeatures;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static dristmine.dristchat.DristChat.config;
import static dristmine.dristchat.utils.enums.ChatDecorations.*;
import static dristmine.dristchat.utils.enums.ChatFeatures.*;

public class AudienceUtils {
	private static JavaPlugin PLUGIN;
	private static BukkitScheduler SCHEDULER;

	public static void setup(DristChat plugin) {
		PLUGIN = plugin;
		SCHEDULER = plugin.getServer().getScheduler();
	}

	public static ChatDecorations determinePlayerChatDecoration(Player player) {
		boolean prefixEnabled = Utils.isFeatureEnabled(player, MESSAGES_PREFIX);
		boolean suffixEnabled = Utils.isFeatureEnabled(player, MESSAGES_SUFFIX);

		if (prefixEnabled && suffixEnabled)
			return PREFIX_AND_SUFFIX;
		if (prefixEnabled)
			return PREFIX;
		if (suffixEnabled)
			return SUFFIX;

		return NONE;
	}

	public static Map<ChatDecorations, List<Player>> mapPlayersToChatDecorations(List<Player> players) {
		return players
				.stream()
				.collect(Collectors.groupingBy(AudienceUtils::determinePlayerChatDecoration));
	}

	public static List<Player> sortPlayersByChatFeature(List<Player> players, ChatFeatures feature) {
		return players
				.stream()
				.filter(player -> Utils.isFeatureEnabled(player, feature))
				.collect(Collectors.toList());
	}

	public static List<Player> sortPlayersByChatFeature(Collection<? extends Player> players, ChatFeatures feature) {
		return sortPlayersByChatFeature(new ArrayList<>(players), feature);
	}

	public static void sendMessageIfCategoryExists(Map<ChatDecorations, List<Player>> mappedPlayers, ChatDecorations category, Component message) {
		if (!mappedPlayers.containsKey(category))
			return;

		Audience.audience(mappedPlayers.get(category))
				.sendMessage(message);
	}

	public static void sendActionbarAnimation(Player receiver, List<Component> animation) {
		int delay = config().getInteger(ConfigKeys.GLOBAL_ANIMATION_OFFSET);
		int frameDelay = config().getInteger(ConfigKeys.GLOBAL_ANIMATION_DELAY);

		for (Component frame : animation) {
			SCHEDULER.runTaskLater(PLUGIN, task -> receiver.sendActionBar(frame), delay);

			delay += frameDelay;
		}
	}

	public static void sendTitleAnimation(Player receiver, ArrayList<Component> titleAnimation, ArrayList<Component> subtitleAnimation) {
		int titleFrameCount = titleAnimation.size();
		int subtitleFrameCount = subtitleAnimation.size();

		int delay = config().getInteger(ConfigKeys.GLOBAL_ANIMATION_OFFSET);
		int frameDelay = config().getInteger(ConfigKeys.GLOBAL_ANIMATION_DELAY);

		if (titleFrameCount > subtitleFrameCount) {
			int diff = titleFrameCount - subtitleFrameCount;

			for (int i = 0; i < diff; i++) {
				Component lastFrame = subtitleAnimation.get(subtitleFrameCount - 1);

				subtitleAnimation.add(lastFrame);
			}
		}
		else {
			int diff = subtitleFrameCount - titleFrameCount;

			for (int i = 0; i < diff; i++) {
				Component lastFrame = titleAnimation.get(titleFrameCount - 1);

				titleAnimation.add(lastFrame);
			}
		}

		int size = Math.max(titleFrameCount, subtitleFrameCount);

		//noinspection MismatchedQueryAndUpdateOfCollection
		ArrayList<Title> animation = new ArrayList<>(size);

		long titleDelay = Utils.ticksToMillis(frameDelay);

		Title.Times times = Title.Times.times(
				Duration.ofMillis(Utils.DEFAULT_INTEGER),
				Duration.ofMillis(titleDelay),
				Duration.ofMillis(titleDelay)
		);
		Title.Times endTimes = Title.Times.times(
				Duration.ofMillis(Utils.DEFAULT_INTEGER),
				Duration.ofMillis(titleDelay),
				Duration.ofMillis(titleDelay + titleDelay)
		);

		for (int i = 0; i < size; i++) {
			animation.add(Title.title(titleAnimation.get(i), subtitleAnimation.get(i), times));
		}

		int lastFrame = size - 1;

		animation.set(lastFrame, Title.title(
				titleAnimation.get(lastFrame),
				subtitleAnimation.get(lastFrame),
				endTimes)
		);

		for (Title frame : animation) {
			SCHEDULER.runTaskLater(PLUGIN, task -> receiver.showTitle(frame), delay);

			delay += frameDelay;
		}
	}
}
