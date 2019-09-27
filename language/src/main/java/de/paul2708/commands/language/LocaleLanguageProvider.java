package de.paul2708.commands.language;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * This class implements the language provider. It is based on locales and resource bundles.
 *
 * @author Paul2708
 */
public final class LocaleLanguageProvider implements LanguageProvider {

    private static final String BUNDLE = "messages";

    private static final String PREFIX_KEY = "prefix";

    private static final String REPLACE_CHAR = "%";

    private ResourceBundle resourceBundle;
    private final String prefix;

    /**
     * Create a new language provider based on a locale.
     *
     * @param directory absolute file path to the directory of all message properties
     * @param locale locale
     */
    LocaleLanguageProvider(String directory, Locale locale) {
        Locale.setDefault(LanguageProvider.DEFAULT_LOCALE);

        File file = new File(directory + "/" + LocaleLanguageProvider.BUNDLE + "_"
                + locale.getLanguage() + ".properties");

        try (InputStream inputStream = new FileInputStream(file)) {
            this.resourceBundle =  new PropertyResourceBundle(inputStream);
        } catch (MissingResourceException | IOException e) {
            this.resourceBundle = ResourceBundle.getBundle(LocaleLanguageProvider.BUNDLE, locale);
        }

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
