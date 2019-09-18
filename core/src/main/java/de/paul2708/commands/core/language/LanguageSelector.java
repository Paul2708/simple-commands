package de.paul2708.commands.core.language;

import java.util.Locale;
import java.util.UUID;

/**
 * This class is an interface for {@link de.paul2708.commands.language.LanguageProvider}.
 * You can select language for players, that will used to sent them the correct translated messages.
 *
 * @author Paul2708
 */
public interface LanguageSelector {

    /**
     * Select the language for a given player uuid.
     * The language will be used to sent translated command information like invalid usage.
     *
     * @param uuid   player uuid
     * @param locale locale, if the locale doesn't exist,
     *               {@link de.paul2708.commands.language.LanguageProvider#DEFAULT_LOCALE} will be used
     */
    void select(UUID uuid, Locale locale);

    /**
     * Get the selected locale for the player uuid.
     * If none locale was selected, {@link de.paul2708.commands.language.LanguageProvider#DEFAULT_LOCALE} will be
     * returned.
     *
     * @param uuid player uuid
     * @return selected locale, otherwise {@link de.paul2708.commands.language.LanguageProvider#DEFAULT_LOCALE}
     */
    Locale get(UUID uuid);
}
