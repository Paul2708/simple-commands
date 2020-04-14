import de.paul2708.commands.language.LanguageProvider;
import de.paul2708.commands.language.MessageResource;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This test class tests the {@link LanguageProvider}.
 *
 * @author Paul2708
 */
public final class DefaultLanguageProviderTest {

    // TODO: Add abstract test class that combines both

    /**
     * Test if the prefix is correct.
     */
    @Test
    public void testColorCodes() {
        String prefix = LanguageProvider.of(LanguageProvider.DEFAULT_LOCALE).translate(MessageResource.of("prefix"));

        assertEquals("§8[§9Simple-Commands§8]§7", prefix);
    }

    /**
     * Test german language detection.
     */
    @Test
    public void testGerman() {
        LanguageProvider provider = LanguageProvider.of(Locale.GERMAN);

        assertEquals(Locale.GERMAN, provider.getLocale());
        assertEquals("deutsch", provider.translate(MessageResource.of("language")));
    }

    /**
     * Test english language detection.
     */
    @Test
    public void testEnglish() {
        LanguageProvider provider = LanguageProvider.of(Locale.ENGLISH);

        assertEquals(Locale.ENGLISH, provider.getLocale());
        assertEquals("english", provider.translate(MessageResource.of("language")));
    }

    /**
     * Test if the default language will be applied, if the language is not supported yet.
     */
    @Test
    public void testInvalidLocale() {
        LanguageProvider provider = LanguageProvider.of(Locale.JAPANESE);

        assertEquals(Locale.ENGLISH, provider.getLocale());
        assertEquals("english", provider.translate(MessageResource.of("language")));
    }
}