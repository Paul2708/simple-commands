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
     * @param key properties key to message
     * @param objects objects, that will be replaced for {0} placeholder.
     * @return translated message
     */
    @Override
    public String translate(String key, Object... objects) {
        if (key.equals(LocaleLanguageProvider.PREFIX_KEY)) {
            return prefix;
        }

        Object[] newOne;
        if (objects == null || objects.length == 0) {
            newOne = new Object[] { prefix };
        } else {
            newOne = new Object[objects.length + 1];
            newOne[0] = prefix;

            System.arraycopy(objects, 0, newOne, 1, objects.length);
        }

        return MessageFormat.format(replaceColorCodes(resourceBundle.getString(key)), newOne);
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
