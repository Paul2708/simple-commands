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
public class LanguageProviderTest {

    // TODO: Add argument test cases

    /**
     * Test if the prefix is correct.
     */
    @Test
    public void testPrefix() {
        String prefix = LanguageProvider.of(Locale.GERMANY).translate(MessageResource.of("prefix"));

        assertEquals("§8[§9Simple-Commands§8]§7", prefix);
    }

    /**
     * Test if the default language will be applied, if the language is not supported yet.
     */
    @Test
    public void testInvalidLocale() {
        LanguageProvider provider = LanguageProvider.of(Locale.JAPAN);

        assertEquals("english", provider.translate(MessageResource.of("language")));
    }
}