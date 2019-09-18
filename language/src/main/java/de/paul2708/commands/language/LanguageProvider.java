package de.paul2708.commands.language;

import java.util.Locale;

/**
 * This class provides methods to translate and resolve translated messages by key.
 *
 * @author Paul2708
 */
public interface LanguageProvider {

    /**
     * Default locale for messages.
     */
    Locale DEFAULT_LOCALE = Locale.US;

    /**
     * Get the translated message by key.
     *
     * @param resource message resource that contains key and parameters
     * @return translated message
     * @see MessageResource
     */
    String translate(MessageResource resource);

    /**
     * Create a new instance to the given locale.
     * If there is no resource bundle for the given locale, the {@link #DEFAULT_LOCALE} will be used instead.
     *
     * @param locale locale
     * @return new language provider instance
     */
    static LanguageProvider of(Locale locale) {
        return new LocaleLanguageProvider(locale);
    }
}