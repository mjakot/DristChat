package dristmine.dristchat.utils;

import java.util.Arrays;

public class ConfigSectionsKeys {
	private String key;

	private boolean containsInnerKeys;

	private ConfigSectionsKeys[] keys;

	private ConfigSectionsKeys(String sectionName) {
		setKey(sectionName);
		setContainsInnerKeys(false);
	}

	private ConfigSectionsKeys(String sectionName, ConfigSectionsKeys[] keys) {
		setKey(sectionName);

		setContainsInnerKeys((keys != null) && keys.length != 0);

		this.keys = keys;
	}

	public static final ConfigSectionsKeys MESSAGE;
	public static final ConfigSectionsKeys SEND;
	public static final ConfigSectionsKeys RECEIVE;
	public static final ConfigSectionsKeys SOUND;
	public static final ConfigSectionsKeys PERMISSION;
	public static final ConfigSectionsKeys HEADER;
	public static final ConfigSectionsKeys FOOTER;
	public static final ConfigSectionsKeys TEXT;
	public static final ConfigSectionsKeys GLOBAL_PERMISSION;
	public static final ConfigSectionsKeys GLOBAL_PERMISSION_PREFIX;
	public static final ConfigSectionsKeys GLOBAL_PERMISSION_SUFFIX;
	public static final ConfigSectionsKeys MESSAGE_FORMAT;
	public static final ConfigSectionsKeys PRIVATE_MESSAGE;
	public static final ConfigSectionsKeys PLAYER_FIRST_JOINED;
	public static final ConfigSectionsKeys PLAYER_JOINED;
	public static final ConfigSectionsKeys PLAYER_LEFT;
	public static final ConfigSectionsKeys TITLE_ON_JOIN;
	public static final ConfigSectionsKeys ACTIONBAR_ON_JOIN;
	public static final ConfigSectionsKeys SOUND_ON_JOIN;
	public static final ConfigSectionsKeys ADVANCEMENTS;
	public static final ConfigSectionsKeys DEATHS;

	static {
		MESSAGE = new ConfigSectionsKeys("message");
		SEND = new ConfigSectionsKeys("send");
		RECEIVE = new ConfigSectionsKeys("receive");
		SOUND = new ConfigSectionsKeys("sound");
		PERMISSION = new ConfigSectionsKeys("permission");
		HEADER = new ConfigSectionsKeys("header");
		FOOTER = new ConfigSectionsKeys("footer");
		TEXT = new ConfigSectionsKeys("text");
		GLOBAL_PERMISSION = new ConfigSectionsKeys("permission_global");
		GLOBAL_PERMISSION_PREFIX = new ConfigSectionsKeys("permission_global_prefix");
		GLOBAL_PERMISSION_SUFFIX = new ConfigSectionsKeys("permission_global_suffix");
		MESSAGE_FORMAT = new ConfigSectionsKeys("message_format", new ConfigSectionsKeys[] { ConfigSectionsKeys.MESSAGE });
		PRIVATE_MESSAGE = new ConfigSectionsKeys("private_messages", new ConfigSectionsKeys[] { ConfigSectionsKeys.SEND, ConfigSectionsKeys.RECEIVE, ConfigSectionsKeys.SOUND });
		PLAYER_FIRST_JOINED = new ConfigSectionsKeys("player_first_joined", new ConfigSectionsKeys[] { ConfigSectionsKeys.MESSAGE, ConfigSectionsKeys.SOUND, ConfigSectionsKeys.PERMISSION });
		PLAYER_JOINED = new ConfigSectionsKeys("player_joined", new ConfigSectionsKeys[] { ConfigSectionsKeys.MESSAGE, ConfigSectionsKeys.SOUND, ConfigSectionsKeys.PERMISSION });
		PLAYER_LEFT = new ConfigSectionsKeys("player_left", new ConfigSectionsKeys[] { ConfigSectionsKeys.MESSAGE, ConfigSectionsKeys.SOUND, ConfigSectionsKeys.PERMISSION });
		TITLE_ON_JOIN = new ConfigSectionsKeys("title_on_join", new ConfigSectionsKeys[] { ConfigSectionsKeys.HEADER, ConfigSectionsKeys.FOOTER, ConfigSectionsKeys.PERMISSION });
		ACTIONBAR_ON_JOIN = new ConfigSectionsKeys("actionbar_on_join", new ConfigSectionsKeys[] { ConfigSectionsKeys.TEXT, ConfigSectionsKeys.PERMISSION });
		SOUND_ON_JOIN = new ConfigSectionsKeys("sound_on_join", new ConfigSectionsKeys[] { ConfigSectionsKeys.SOUND, ConfigSectionsKeys.PERMISSION });
		ADVANCEMENTS = new ConfigSectionsKeys("advancements", new ConfigSectionsKeys[] { ConfigSectionsKeys.MESSAGE, ConfigSectionsKeys.PERMISSION });
		DEATHS = new ConfigSectionsKeys("deaths", new ConfigSectionsKeys[] { ConfigSectionsKeys.MESSAGE, ConfigSectionsKeys.PERMISSION });
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

	public String getKey(ConfigSectionsKeys key) {
		return getKey(key.getKey());
	}

	private void setKey(String sectionName) {
		this.key = sectionName;
	}

	private void setContainsInnerKeys(boolean contains) {
		this.containsInnerKeys = contains;
	}
}
