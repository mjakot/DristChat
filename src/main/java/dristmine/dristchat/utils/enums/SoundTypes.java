package dristmine.dristchat.utils.enums;

import dristmine.dristchat.utils.config.ConfigKeys;

import static dristmine.dristchat.utils.config.ConfigKeys.*;

public enum SoundTypes {
	PUBLIC_MESSAGE_SENT_RECEIVED(GLOBAL_MESSAGE_SOUND),
	PRIVATE_MESSAGE_SENT_RECEIVED(PRIVATE_MESSAGE),
	PLAYER_FIRST_JOINED_RECEIVED(PLAYER_FIRST_JOINED),
	PLAYER_JOIN_RECEIVED(PLAYER_JOINED),
	PLAYER_LEFT_RECEIVED(PLAYER_LEFT),
	PLAYER_JOINED_SENT(SOUND_ON_JOIN);

	private final ConfigKeys soundKey;

	SoundTypes(ConfigKeys soundKey) {
		this.soundKey = soundKey;
	}

	public final String getSound() {
		return soundKey.getKey(SOUND);
	}
}
