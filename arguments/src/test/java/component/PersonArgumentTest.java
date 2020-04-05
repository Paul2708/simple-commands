package component;

import de.paul2708.commands.arguments.Validation;
import model.Person;
import model.PersonArgument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class tests the person argument test. These tests should all command argument implement.
 *
 * @author Paul2708
 */
public final class PersonArgumentTest {

    private PersonArgument argument;

    /**
     * Create a new command argument with a list of persons.
     */
    @BeforeEach
    public void setUp() {
        List<Person> list = new ArrayList<>();
        list.add(new Person("testuser", 1337));
        list.add(new Person("paul", 19));
        list.add(new Person("max", 12));
        list.add(new Person("pascal", 23));

        this.argument = new PersonArgument(list);
    }

    /**
     * Test a valid argument.
     */
    @Test
    public void testValidArgument() {
        Validation<Person> validate = this.argument.validate("paul;19");

        assertTrue(validate.isValid());
        assertEquals(validate.getParsedObject(), new Person("paul", 19));
    }

    /**
     * Test an invalid command format.
     */
    @Test
    public void testInvalidFormat() {
        Validation<Person> validate = this.argument.validate("invalid-format");

        assertFalse(validate.isValid());
        assertEquals("argument.person.invalid.format", validate.getErrorResource().getKey());
    }

    /**
     * Test an invalid age.
     */
    @Test
    public void testInvalidAge() {
        Validation<Person> validate = this.argument.validate("paul;age");

        assertFalse(validate.isValid());
        assertEquals("argument.person.invalid.age", validate.getErrorResource().getKey());
    }

    /**
     * Test if the user was not found.
     */
    @Test
    public void testNoneFound() {
        Validation<Person> validate = this.argument.validate("tom;12");

        assertFalse(validate.isValid());
        assertEquals("argument.person.invalid.not_found", validate.getErrorResource().getKey());
    }

    /**
     * Test the usage (trivial).
     */
    @Test
    public void testUsage() {
        assertEquals("argument.person.usage", argument.usage().getKey());
    }

    /**
     * Test an empty auto complete.
     */
    @Test
    public void testEmptyAutoComplete() {
        List<String> autoComplete = argument.autoComplete("");

        assertEqualLists(autoComplete, Arrays.asList("testuser", "paul", "max", "pascal"));
    }

    /**
     * Test standard auto complete.
     */
    @Test
    public void testAutoComplete() {
        List<String> autoComplete = argument.autoComplete("pa");

        assertEqualLists(autoComplete, Arrays.asList("paul", "pascal"));
    }

    /**
     * Test empty auto complete list.
     */
    @Test
    public void testEmptyAutoCompleteList() {
        List<String> autoComplete = argument.autoComplete("sa");

        assertEqualLists(autoComplete, Collections.emptyList());
    }

    /**
     * Test if two lists are equals. They are equal, if the size and each element is equal.
     *
     * @param listA list a
     * @param listB list b
     * @param <T> list types
     */
    private <T> void assertEqualLists(List<T> listA, List<T> listB) {
        assertEquals(listA, listB);

        for (int i = 0; i < listA.size(); i++) {
            assertEquals(listA.get(i), listB.get(i));
        }
    }
}
