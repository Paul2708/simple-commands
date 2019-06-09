package component;

import de.paul2708.commands.arguments.Validation;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This component test tests the validation class.
 *
 * @author Paul2708
 */
public class ValidationTest {

    /**
     * Test a valid validation.
     */
    @Test
    public void validValidation() {
        Validation<String> validation = Validation.valid("it's valid");

        assertTrue(validation.isValid());

        assertNull(validation.getErrorMessage());

        assertNotNull(validation.getParsedObject());
        assertEquals("it's valid", validation.getParsedObject());
    }

    /**
     * Test an invalid validation.
     */
    @Test
    public void invalidValidation() {
        Validation<Void> validation = Validation.invalid("error");

        assertFalse(validation.isValid());

        assertNull(validation.getParsedObject());

        assertNotNull(validation.getErrorMessage());
        assertEquals("error", validation.getErrorMessage());
    }
}
