package dristmine.dristchat.utils;

import dristmine.dristchat.utils.config.ConfigKeys;
import dristmine.dristchat.utils.enums.SoundTypes;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;

import static dristmine.dristchat.DristChat.config;
import static dristmine.dristchat.utils.Utils.PAPER_SOUND_DELIMITER;
import static dristmine.dristchat.utils.Utils.MINECRAFT_SOUND_DELIMITER;

public class SoundUtils {
	public static Sound createSound(SoundTypes soundType) {
		return Sound.sound(
				Key.key(getMinecraftSoundPath(config().getString(soundType.getSound()))),
				Sound.Source.MASTER,
				(float) config().getFloatingPoint(ConfigKeys.GLOBAL_SOUND_VOLUME),
				(float) config().getFloatingPoint(ConfigKeys.GLOBAL_SOUND_PITCH)
		);
	}

	public static void playSound(Audience audience, SoundTypes soundType) {
		audience.playSound(createSound(soundType));
	}

	private static String getMinecraftSoundPath(String paperSoundPath) {
		return paperSoundPath
				.toLowerCase()
				.replace(PAPER_SOUND_DELIMITER, MINECRAFT_SOUND_DELIMITER);
	}
}
