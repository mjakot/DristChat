package dristmine.dristchat.utils.enums;

public enum ChatDecorations {
	PREFIX_AND_SUFFIX(new ChatFeatures[] { ChatFeatures.MESSAGES_PREFIX, ChatFeatures.MESSAGES_SUFFIX }),
	PREFIX(new ChatFeatures[] { ChatFeatures.MESSAGES_PREFIX }),
	SUFFIX(new ChatFeatures[] { ChatFeatures.MESSAGES_SUFFIX }),
	NONE(new ChatFeatures[] {});

	private final ChatFeatures[] decorationFeatures;

	ChatDecorations(ChatFeatures[] decorationFeatures) {
		this.decorationFeatures = decorationFeatures;
	}

	public final ChatFeatures[] getDecorationFeatures() {
		return decorationFeatures;
	}
}
