package de.paul2708.commands.language;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

/**
 * This class implements the language provider. It is based on in-code messages.
 *
 * @author Cerus
 */
public class MapLanguageProvider implements LanguageProvider {

    private static final String PREFIX_KEY = "prefix";

    private static final String REPLACE_CHAR = "%";

    private final Map<String, String> messages;
    private final Locale locale;

    /**
     * Create a new language provider based on a locale and a message map.
     *
     * @param locale locale
     * @param messages messages
     */
    public MapLanguageProvider(Map<String, String> messages, Locale locale) {
        Locale.setDefault(LanguageProvider.DEFAULT_LOCALE);

        this.messages = messages;
        this.locale = locale;
    }

    /**
     * Get the translated message by key.
     *
     * @param resource message resource that contains key and parameters
     * @return translated message
     * @see MessageResource
     */
    @Override
    public String translate(MessageResource resource) {
        String key = resource.getKey();
        Object[] arguments = resource.getArguments();

        if (key.equals(MapLanguageProvider.PREFIX_KEY)) {
            return getPrefix();
        }

        String message = messages.getOrDefault(resource.getKey(), resource.getKey())
                .replace(REPLACE_CHAR + PREFIX_KEY + REPLACE_CHAR, getPrefix());

        return MessageFormat.format(replaceColorCodes(message), arguments);
    }

    private String getPrefix() {
        return messages.getOrDefault(MapLanguageProvider.PREFIX_KEY, MapLanguageProvider.PREFIX_KEY);
    }

    /**
     * Get the locale used in {@link #of(String, Locale)}.
     * If the locale doesn't exist, the default locale will be used.
     *
     * @return locale
     */
    @Override
    public Locale getLocale() {
        return locale;
    }

    /**
     * Replace all color codes to display it.
     *
     * @param message message with color codes
     * @return message with applied color codes
     */
    private String replaceColorCodes(String message) {
        return message.replace("&", "§");
    }
}
