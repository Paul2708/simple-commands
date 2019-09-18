package de.paul2708.commands.core.language;

import de.paul2708.commands.language.LanguageProvider;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * This class represents the default implementation of {@link LanguageSelector}.
 *
 * @author Paul2708
 */
public final class DefaultLanguageSelector implements LanguageSelector {

    private Map<UUID, Locale> languages;

    /**
     * Create a new language selector with an empty map of selected languages.
     */
    public DefaultLanguageSelector() {
        this.languages = new HashMap<>();
    }

    /**
     * Select the language for a given player uuid.
     * The language will be used to sent translated command information like invalid usage.
     *
     * @param uuid   player uuid
     * @param locale locale, if the locale doesn't exist,
     *               {@link LanguageProvider#DEFAULT_LOCALE} will be used
     */
    @Override
    public void select(UUID uuid, Locale locale) {
        languages.put(uuid, locale);
    }

    /**
     * Get the selected locale for the player uuid.
     * If none locale was selected, {@link LanguageProvider#DEFAULT_LOCALE} will be
     * returned.
     *
     * @param uuid player uuid
     * @return selected locale, otherwise {@link LanguageProvider#DEFAULT_LOCALE}
     */
    @Override
    public Locale get(UUID uuid) {
        return languages.getOrDefault(uuid, LanguageProvider.DEFAULT_LOCALE);
    }
}