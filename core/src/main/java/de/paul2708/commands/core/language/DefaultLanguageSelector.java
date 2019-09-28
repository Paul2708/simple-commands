package de.paul2708.commands.core.language;

import com.sun.tools.internal.xjc.Language;
import de.paul2708.commands.language.LanguageProvider;
import de.paul2708.commands.language.MessageResource;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.UUID;

/**
 * This class represents the default implementation of {@link LanguageSelector}.
 *
 * @author Paul2708
 */
public final class DefaultLanguageSelector implements LanguageSelector {

    private String directory;

    private final Map<UUID, Locale> languages;
    private Locale consoleLanguage;

    /**
     * Create a new language selector with an empty map of selected languages.
     */
    public DefaultLanguageSelector() {
        this.languages = new HashMap<>();
        this.consoleLanguage = LanguageProvider.DEFAULT_LOCALE;
    }

    /**
     * Load the language files from a certain path.
     *
     * @param path directory of all <code>message_XX.properties</code>, relative to spigot.jar
     */
    @Override
    public void loadFromFile(String path) throws MissingResourceException {
        if (directory != null) {
            throw new IllegalStateException("File aready set.");
        }

        this.directory = path;
    }

    /**
     * Select the language for a given command sender.
     * The language will be used to sent translated command information like invalid usage.
     *
     * @param sender command sender, can be players or <code>Bukkit.getConsoleSender()</code>
     * @param locale locale, if the locale doesn't exist,
     *               {@link LanguageProvider#DEFAULT_LOCALE} will be used
     */
    @Override
    public void select(CommandSender sender, Locale locale) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            languages.put(player.getUniqueId(), provideLanguage(locale).getLocale());
        } else {
            this.consoleLanguage = provideLanguage(locale).getLocale();
        }
    }

    /**
     * Translate the resource with the selected sender.
     *
     * @param sender   command sender, can be players or <code>Bukkit.getConsoleSender()</code>
     * @param resource message resource that will be translated
     */
    @Override
    public void sendMessage(CommandSender sender, MessageResource resource) {
        sender.sendMessage(provideLanguage(get(sender)).translate(resource));
    }

    /**
     * Translate the resource with the selected sender.
     *
     * @param sender   command sender, can be players or <code>Bukkit.getConsoleSender()</code>
     * @param resource message resource that will be translated
     * @return translated message
     */
    @Override
    public String translate(CommandSender sender, MessageResource resource) {
        return provideLanguage(get(sender)).translate(resource);
    }

    /**
     * Get the selected locale for the player uuid.
     * If none locale was selected, {@link LanguageProvider#DEFAULT_LOCALE} will be
     * returned.
     *
     * @param sender command sender, can be players or <code>Bukkit.getConsoleSender()</code>
     * @return selected locale, otherwise {@link LanguageProvider#DEFAULT_LOCALE}
     */
    @Override
    public Locale get(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            return languages.getOrDefault(player.getUniqueId(), LanguageProvider.DEFAULT_LOCALE);
        } else {
            return consoleLanguage;
        }
    }

    private LanguageProvider provideLanguage(Locale locale) {
        if (directory == null) {
            return LanguageProvider.of(locale);
        } else {
            return LanguageProvider.of(directory, locale);
        }
    }
}