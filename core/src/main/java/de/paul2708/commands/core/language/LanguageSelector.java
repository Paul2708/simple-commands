package de.paul2708.commands.core.language;

import de.paul2708.commands.core.annotation.Command;
import de.paul2708.commands.language.MessageResource;
import org.bukkit.command.CommandSender;

import java.util.Locale;

/**
 * This class is an interface for {@link de.paul2708.commands.language.LanguageProvider}.
 * You can select language for players, that will used to sent them the correct translated messages.
 *
 * @author Paul2708
 */
public interface LanguageSelector {

    /**
     * Select the language for a given command sender.
     * The language will be used to sent translated command information like invalid usage.
     *
     * @param sender command sender, can be players or <code>Bukkit.getConsoleSender()</code>
     * @param locale locale, if the locale doesn't exist,
     *               {@link de.paul2708.commands.language.LanguageProvider#DEFAULT_LOCALE} will be used
     */
    void select(CommandSender sender, Locale locale);

    /**
     * Translate the resource with the selected sender and send it to the user.
     *
     * @param sender   command sender, can be players or <code>Bukkit.getConsoleSender()</code>
     * @param resource message resource that will be translated
     */
    void sendMessage(CommandSender sender, MessageResource resource);

    /**
     * Translate the resource with the selected sender.
     *
     * @param sender   command sender, can be players or <code>Bukkit.getConsoleSender()</code>
     * @param resource message resource that will be translated
     * @return translated message
     */
    String translate(CommandSender sender, MessageResource resource);

    /**
     * Get the selected locale for the player uuid.
     * If none locale was selected, {@link de.paul2708.commands.language.LanguageProvider#DEFAULT_LOCALE} will be
     * returned.
     *
     * @param sender command sender, can be players or <code>Bukkit.getConsoleSender()</code>
     * @return selected locale, otherwise {@link de.paul2708.commands.language.LanguageProvider#DEFAULT_LOCALE}
     */
    Locale get(CommandSender sender);
}
