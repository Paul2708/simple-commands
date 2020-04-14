package component;

import de.paul2708.commands.arguments.Validation;
import de.paul2708.commands.language.MessageResource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This component test tests the validation class.
 *
 * @author Paul2708
 */
public final class ValidationTest {

    /**
     * Test a valid validation.
     */
    @Test
    public void validValidation() {
        Validation<String> validation = Validation.valid("it's valid");

        assertTrue(validation.isValid());

        assertNull(validation.getErrorResource());

        assertNotNull(validation.getParsedObject());
        assertEquals("it's valid", validation.getParsedObject());
    }

    /**
     * Test an invalid validation.
     */
    @Test
    public void invalidValidation() {
        Validation<Void> validation = Validation.invalid(MessageResource.of("sample_key"));

        assertFalse(validation.isValid());

        assertNull(validation.getParsedObject());

        assertNotNull(validation.getErrorResource());
        assertEquals("sample_key", validation.getErrorResource().getKey());
    }
}
