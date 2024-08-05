package dristmine.dristchat.utils.enums;

import dristmine.dristchat.utils.config.ConfigKeys;

import static dristmine.dristchat.utils.config.ConfigKeys.*;

public enum ChatFeatures {
	CHAT_MESSAGES(GLOBAL_PERMISSION),
	MESSAGES_PREFIX(GLOBAL_PERMISSION_PREFIX),
	MESSAGES_SUFFIX(GLOBAL_PERMISSION_SUFFIX),
	MESSAGES_SOUND(GLOBAL_PERMISSION_SOUND),
	PLAYER_FIRST_JOIN_MESSAGES(PLAYER_FIRST_JOINED),
	PLAYER_JOIN_MESSAGES(PLAYER_JOINED),
	PLAYER_LEFT_MESSAGES(PLAYER_LEFT),
	TITLE_ON_PLAYER_JOIN(TITLE_ON_JOIN),
	ACTIONBAR_ON_PLAYER_JOIN(ACTIONBAR_ON_JOIN),
	SOUND_ON_PLAYER_JOIN(SOUND_ON_JOIN),
	ADVANCEMENTS_MESSAGES(ADVANCEMENTS),
	DEATHS_MESSAGES(DEATHS);

	private final ConfigKeys featureKey;

	ChatFeatures(ConfigKeys featureKey) {
		this.featureKey = featureKey;
	}

	public final String getFeature() {
		return featureKey.getKey(PERMISSION);
	}
}
