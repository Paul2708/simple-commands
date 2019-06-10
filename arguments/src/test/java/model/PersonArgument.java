package model;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This command arguments represents the {@link Person} argument.
 * The argument "max;10" refers to the Person(name="max", age=10).
 *
 * @author Paul2708
 */
public class PersonArgument implements CommandArgument<Person> {

    private List<Person> list;

    /**
     * Create a new person argument with some persons.
     *
     * @param list list of persons
     */
    public PersonArgument(List<Person> list) {
        this.list = list;
    }

    /**
     * Validate the object by a given command argument.
     *
     * @param argument command argument
     * @return a valid or invalid validation
     */
    @Override
    public Validation<Person> validate(String argument) {
        String[] properties = argument.split(";");

        if (properties.length != 2) {
            return Validation.invalid("invalid format, use name;age");
        }

        // Sample check
        int age;
        try {
            age = Integer.parseInt(properties[1]);
        } catch (NumberFormatException e) {
            return Validation.invalid("invalid age");
        }

        Person givenPerson = new Person(properties[0], age);

        for (Person person : list) {
            if (person.equals(givenPerson)) {
                return Validation.valid(person);
            }
        }

        return Validation.invalid("person not found");
    }

    /**
     * Get the correct usage for this type.
     *
     * @return usage
     */
    @Override
    public String usage() {
        return "[name;age]";
    }

    /**
     * Get a list of strings that are used for the auto completion.
     * The argument can be empty.
     *
     * @param argument command argument (might be empty)
     * @return unmodifiable list of string
     */
    @Override
    public List<String> autoComplete(String argument) {
        List<String> autoComplete = list.stream()
                .map(Person::getName)
                .filter(name -> name.startsWith(argument)).collect(Collectors.toList());

        return Collections.unmodifiableList(autoComplete);
    }
}
