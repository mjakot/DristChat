package dristmine.dristchat.utils.config;

import java.util.Arrays;

public class ConfigKeys {
	private String key;

	private boolean containsInnerKeys;

	private ConfigKeys[] keys;

	private ConfigKeys(String sectionName) {
		setKey(sectionName);
		setContainsInnerKeys(false);
	}

	private ConfigKeys(String sectionName, ConfigKeys[] keys) {
		setKey(sectionName);

		setContainsInnerKeys((keys != null) && keys.length != 0);

		this.keys = keys;
	}

	public static final ConfigKeys MESSAGE;
	public static final ConfigKeys SOUND;
	public static final ConfigKeys PERMISSION;
	public static final ConfigKeys HEADER;
	public static final ConfigKeys FOOTER;
	public static final ConfigKeys COLOR;
	public static final ConfigKeys DECORATION;
	public static final ConfigKeys GLOBAL_PERMISSION;
	public static final ConfigKeys GLOBAL_PERMISSION_PREFIX;
	public static final ConfigKeys GLOBAL_PERMISSION_SUFFIX;
	public static final ConfigKeys GLOBAL_PERMISSION_SOUND;
	public static final ConfigKeys GLOBAL_MESSAGE_SOUND;
	public static final ConfigKeys GLOBAL_SOUND_VOLUME;
	public static final ConfigKeys GLOBAL_SOUND_PITCH;
	public static final ConfigKeys GLOBAL_ANIMATION_OFFSET;
	public static final ConfigKeys GLOBAL_ANIMATION_DELAY;
	public static final ConfigKeys GLOBAL_PREFIX_PLACEHOLDER;
	public static final ConfigKeys GLOBAL_SUFFIX_PLACEHOLDER;
	public static final ConfigKeys MESSAGE_FORMAT;
	public static final ConfigKeys PRIVATE_MESSAGE;
	public static final ConfigKeys PLAYER_FIRST_JOINED;
	public static final ConfigKeys PLAYER_JOINED;
	public static final ConfigKeys PLAYER_LEFT;
	public static final ConfigKeys TITLE_ON_JOIN;
	public static final ConfigKeys ACTIONBAR_ON_JOIN;
	public static final ConfigKeys SOUND_ON_JOIN;
	public static final ConfigKeys ADVANCEMENTS;
	public static final ConfigKeys DEATHS;

	static {
		MESSAGE = new ConfigKeys("message");
		SOUND = new ConfigKeys("sound");
		PERMISSION = new ConfigKeys("permission");
		HEADER = new ConfigKeys("header");
		FOOTER = new ConfigKeys("footer");
		COLOR = new ConfigKeys("color");
		DECORATION = new ConfigKeys("decoration");
		GLOBAL_PERMISSION = new ConfigKeys("permission_global");
		GLOBAL_PERMISSION_PREFIX = new ConfigKeys("permission_global_prefix");
		GLOBAL_PERMISSION_SUFFIX = new ConfigKeys("permission_global_suffix");
		GLOBAL_PERMISSION_SOUND = new ConfigKeys("permission_global_sound");
		GLOBAL_MESSAGE_SOUND = new ConfigKeys("global_message_sound");
		GLOBAL_SOUND_VOLUME = new ConfigKeys("global_sound_volume");
		GLOBAL_SOUND_PITCH = new ConfigKeys("global_sound_pitch");
		GLOBAL_ANIMATION_OFFSET = new ConfigKeys("global_animation_offset");
		GLOBAL_ANIMATION_DELAY = new ConfigKeys("global_animation_delay");
		GLOBAL_PREFIX_PLACEHOLDER = new ConfigKeys("global_prefix_placeholder");
		GLOBAL_SUFFIX_PLACEHOLDER = new ConfigKeys("global_suffix_placeholder");
		MESSAGE_FORMAT = new ConfigKeys("message_format", new ConfigKeys[] { ConfigKeys.MESSAGE, ConfigKeys.COLOR, ConfigKeys.DECORATION });
		PRIVATE_MESSAGE = new ConfigKeys("private_messages", new ConfigKeys[] { ConfigKeys.MESSAGE, ConfigKeys.SOUND, ConfigKeys.COLOR, ConfigKeys.DECORATION });
		PLAYER_FIRST_JOINED = new ConfigKeys("player_first_joined", new ConfigKeys[] { ConfigKeys.MESSAGE, ConfigKeys.SOUND, ConfigKeys.PERMISSION, ConfigKeys.COLOR, ConfigKeys.DECORATION });
		PLAYER_JOINED = new ConfigKeys("player_joined", new ConfigKeys[] { ConfigKeys.MESSAGE, ConfigKeys.SOUND, ConfigKeys.PERMISSION, ConfigKeys.COLOR, ConfigKeys.DECORATION });
		PLAYER_LEFT = new ConfigKeys("player_left", new ConfigKeys[] { ConfigKeys.MESSAGE, ConfigKeys.SOUND, ConfigKeys.PERMISSION, ConfigKeys.COLOR, ConfigKeys.DECORATION });
		TITLE_ON_JOIN = new ConfigKeys("title_on_join", new ConfigKeys[] { ConfigKeys.HEADER, ConfigKeys.FOOTER, ConfigKeys.PERMISSION, ConfigKeys.COLOR, ConfigKeys.DECORATION });
		ACTIONBAR_ON_JOIN = new ConfigKeys("actionbar_on_join", new ConfigKeys[] { ConfigKeys.MESSAGE, ConfigKeys.PERMISSION, ConfigKeys.COLOR, ConfigKeys.DECORATION });
		SOUND_ON_JOIN = new ConfigKeys("sound_on_join", new ConfigKeys[] { ConfigKeys.SOUND, ConfigKeys.PERMISSION });
		ADVANCEMENTS = new ConfigKeys("advancements", new ConfigKeys[] { ConfigKeys.MESSAGE, ConfigKeys.PERMISSION, ConfigKeys.COLOR, ConfigKeys.DECORATION });
		DEATHS = new ConfigKeys("deaths", new ConfigKeys[] { ConfigKeys.MESSAGE, ConfigKeys.PERMISSION, ConfigKeys.COLOR, ConfigKeys.DECORATION });
	}

	public String getKey() {
		return key;
	}

	public String getKey(String key) {
		if (!containsInnerKeys)
			return getKey();

		if (Arrays.stream(keys).noneMatch(x -> x.getKey().equals(key)))
			return getKey();

		return String.join(ConfigManager.CONFIG_DELIMITER, getKey(), key);
	}

	public String getKey(ConfigKeys key) {
		return getKey(key.getKey());
	}

	private void setKey(String sectionName) {
		this.key = sectionName;
	}

	private void setContainsInnerKeys(boolean contains) {
		this.containsInnerKeys = contains;
	}
}
