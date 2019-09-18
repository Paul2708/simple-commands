package de.paul2708.commands.language;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class implements the language provider. It is based on locales and resource bundles.
 *
 * @author Paul2708
 */
public class LocaleLanguageProvider implements LanguageProvider {

    private static final String PATH = "messages";

    private static final String PREFIX_KEY = "prefix";

    private static String REPLACE_CHAR = "%";

    private final ResourceBundle resourceBundle;
    private final String prefix;

    /**
     * Create a new language provider based on a locale.
     *
     * @param locale locale
     */
    LocaleLanguageProvider(Locale locale) {
        Locale.setDefault(LanguageProvider.DEFAULT_LOCALE);

        this.resourceBundle = ResourceBundle.getBundle(LocaleLanguageProvider.PATH, locale);
        this.prefix = replaceColorCodes(resourceBundle.getString(LocaleLanguageProvider.PREFIX_KEY));
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

        if (key.equals(LocaleLanguageProvider.PREFIX_KEY)) {
            return prefix;
        }

        String message = resourceBundle.getString(key).replace(REPLACE_CHAR + PREFIX_KEY + REPLACE_CHAR,
                prefix);

        return MessageFormat.format(replaceColorCodes(message), arguments);
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