package dristmine.dristchat.utils.component;

import dristmine.dristchat.utils.Utils;
import net.kyori.adventure.text.Component;

import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ComponentTemplate {
	public enum Placeholders {
		PREFIX_PLACEHOLDER("%prefix%"),
		SUFFIX_PLACEHOLDER("%suffix%"),
		SENDER_PLACEHOLDER("%sender%"),
		RECEIVER_PLACEHOLDER("%receiver%"),
		MESSAGE_PLACEHOLDER("%message%");

		private final @NotNull String placeholder;

		Placeholders(@NotNull String placeholder) {
			this.placeholder = placeholder;
		}

		public @NotNull String getPlaceholder() {
			return placeholder;
		}
	}

	private final @NotNull String component;

	private @Nullable String sender;
	private @Nullable String senderPrefix;
	private @Nullable String senderSuffix;
	private @Nullable String receiver;
	private @Nullable String receiverPrefix;
	private @Nullable String receiverSuffix;
	private @Nullable Component message;

	private ComponentTemplate(
			@NotNull String component,
			@Nullable String sender,
			@Nullable String senderPrefix,
		    @Nullable String senderSuffix,
		    @Nullable String receiver,
			@Nullable String receiverPrefix,
		    @Nullable String receiverSuffix,
			@Nullable Component message
	) {
		this.component = component;
		this.sender = sender;
		this.senderPrefix = senderPrefix;
		this.senderSuffix = senderSuffix;
		this.receiver = receiver;
		this.receiverPrefix = receiverPrefix;
		this.receiverSuffix = receiverSuffix;
		this.message = message;
	}

	public static @NotNull ComponentTemplate template(@NotNull String templateString) {
		@Nullable String sender = null;
		@Nullable String senderPrefix = null;
		@Nullable String senderSuffix = null;
		@Nullable String receiver = null;
		@Nullable String receiverPrefix = null;
		@Nullable String receiverSuffix = null;
		@Nullable Component message = null;

		if (templateString.contains(Placeholders.SENDER_PLACEHOLDER.getPlaceholder())) {
			sender = Utils.EMPTY_STRING;

			int senderPrefixIndex = templateString.indexOf(Placeholders.PREFIX_PLACEHOLDER.getPlaceholder());
			int senderSuffixIndex = templateString.indexOf(Placeholders.SUFFIX_PLACEHOLDER.getPlaceholder());

			if (senderPrefixIndex != Utils.NOT_FOUND_INDEX)
				senderPrefix = Utils.EMPTY_STRING;

			if (senderSuffixIndex != Utils.NOT_FOUND_INDEX)
				senderSuffix = Utils.EMPTY_STRING;
		}

		if (templateString.contains(Placeholders.RECEIVER_PLACEHOLDER.getPlaceholder())) {
			receiver = Utils.EMPTY_STRING;

			int receiverPrefixIndex = templateString.lastIndexOf(Placeholders.PREFIX_PLACEHOLDER.getPlaceholder());
			int receiverSuffixIndex = templateString.lastIndexOf(Placeholders.SUFFIX_PLACEHOLDER.getPlaceholder());

			if (receiverPrefixIndex != Utils.NOT_FOUND_INDEX)
				receiverPrefix = Utils.EMPTY_STRING;

			if (receiverSuffixIndex != Utils.NOT_FOUND_INDEX)
				receiverSuffix = Utils.EMPTY_STRING;
		}

		if (templateString.contains(Placeholders.MESSAGE_PLACEHOLDER.getPlaceholder()))
			message = Component.empty();

		return new ComponentTemplate(
				templateString,
				sender,
				senderPrefix,
				senderSuffix,
				receiver,
				receiverPrefix,
				receiverSuffix,
				message
		);
	}

	public @NotNull ComponentTemplate sender(@Nullable String sender) {
		return assignIfNotNull(newValue -> this.sender = newValue, sender);
	}

	public @NotNull ComponentTemplate senderPrefix(@Nullable String senderPrefix) {
		return assignIfNotNull(newValue -> this.senderPrefix = newValue, senderPrefix);
	}

	public @NotNull ComponentTemplate senderSuffix(@Nullable String senderSuffix) {
		return assignIfNotNull(newValue -> this.senderSuffix = newValue, senderSuffix);
	}

	public @NotNull ComponentTemplate receiver(@Nullable String receiver) {
		return assignIfNotNull(newValue -> this.receiver = newValue, receiver);
	}

	public @NotNull ComponentTemplate receiverPrefix(@Nullable String receiverPrefix) {
		return assignIfNotNull(newValue -> this.receiverPrefix = newValue, receiverPrefix);
	}

	public @NotNull ComponentTemplate receiverSuffix(@Nullable String receiverSuffix) {
		return assignIfNotNull(newValue -> this.receiverSuffix = newValue, receiverSuffix);
	}

	public @NotNull ComponentTemplate message(@Nullable Component message) {
		return assignIfNotNull(newValue -> this.message = newValue, message);
	}

	public @NotNull Component build(@Nullable TextDecoration decoration, @Nullable TextColor color) {
		Component result = Component.text()
							.decoration(Utils.isNull(decoration) ? TextDecoration.BOLD : decoration, Utils.isNotNull(decoration))
							.color(color)
							.append(LegacyComponentSerializer.legacyAmpersand().deserialize(
									component.replace(Placeholders.SENDER_PLACEHOLDER.getPlaceholder(), Utils.ensureStringNotNull(sender))
										.replace(Placeholders.RECEIVER_PLACEHOLDER.getPlaceholder(), Utils.ensureStringNotNull(receiver))
										.replace(Placeholders.MESSAGE_PLACEHOLDER.getPlaceholder(), Utils.EMPTY_STRING)
										.replaceFirst(Placeholders.PREFIX_PLACEHOLDER.getPlaceholder(), Utils.ensureStringNotNull(senderPrefix))
										.replace(Placeholders.PREFIX_PLACEHOLDER.getPlaceholder(), Utils.ensureStringNotNull(receiverPrefix))
										.replaceFirst(Placeholders.SUFFIX_PLACEHOLDER.getPlaceholder(), Utils.ensureStringNotNull(senderSuffix))
										.replace(Placeholders.SUFFIX_PLACEHOLDER.getPlaceholder(), Utils.ensureStringNotNull(receiverSuffix))
							))
							.build();

		if (Utils.isNotNull(message))
			result = result.append(message);

		return result;
	}

	private @NotNull <T> ComponentTemplate assignIfNotNull(@Nullable Consumer<T> assigner, @Nullable T assignee) {
		if (Utils.isNull(assigner))
			return this;

		assigner.accept(assignee);

		return this;
	}
}
