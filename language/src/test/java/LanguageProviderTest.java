import de.paul2708.commands.language.LanguageProvider;
import de.paul2708.commands.language.MessageResource;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * This test class tests the {@link LanguageProvider}.
 *
 * @author Paul2708
 */
public final class LanguageProviderTest {

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

        assertEquals("deutsch", provider.translate(MessageResource.of("language")));
    }

    /**
     * Test english language detection.
     */
    @Test
    public void testEnglish() {
        LanguageProvider provider = LanguageProvider.of(Locale.ENGLISH);

        assertEquals("english", provider.translate(MessageResource.of("language")));
    }

    /**
     * Test if the default language will be applied, if the language is not supported yet.
     */
    @Test
    public void testInvalidLocale() {
        LanguageProvider provider = LanguageProvider.of(Locale.JAPANESE);

        assertEquals("english", provider.translate(MessageResource.of("language")));
    }
}