package de.paul2708.commands.example;

import java.util.Objects;

/**
 * This class models a person with primitive fields.
 * A person is unique by its name and age.
 *
 * @author Paul2708
 */
public final class Person {

    private final String name;
    private final int age;

    /**
     * Create a new person with name and age.
     *
     * @param name name
     * @param age  age
     */
    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Get the name.
     *
     * @return name
     */
    String getName() {
        return name;
    }

    /**
     * Get the age.
     *
     * @return age
     */
    int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{"
                + "name='" + name + '\''
                + ", age=" + age
                + '}';
    }

    /**
     * Two persons are equal, if they have the same name and the same age.
     *
     * @param o object
     * @return true if the objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Person person = (Person) o;

        if (age != person.age) {
            return false;
        }

        return Objects.equals(name, person.name);
    }

    /**
     * Get the hash code.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }
}