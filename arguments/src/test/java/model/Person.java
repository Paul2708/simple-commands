package model;

/**
 * This class models a person with primitive fields. A person is unique by its name and age.
 *
 * @author Paul2708
 */
public class Person {

    private final String name;
    private final int age;

    /**
     * Create a new person with name and age.
     *
     * @param name name
     * @param age age
     */
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Get the name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the age.
     *
     * @return age
     */
    public int getAge() {
        return age;
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

        return name != null ? name.equals(person.name) : person.name == null;
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


