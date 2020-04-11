package de.paul2708.commands.core.math;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * This test tests the combinations generator of {@link Combinator}.
 *
 * @author Paul2708
 */
final class CombinatorTest {

    /**
     * Test an empty list of elements.
     */
    @Test
    public void empty() {
        List<List<Optional<Object>>> combinations = Combinator
                .combinations(Collections.emptyList())
                .collect(Collectors.toList());

        assertEquals(1, combinations.size());
        assertEquals(0, combinations.get(0).size());
    }

    /**
     * Test all combinations of two numbers.
     */
    @Test
    public void twoNumbers() {
        List<List<Optional<Integer>>> combinations = Combinator
                .combinations(List.of(1, 1))
                .collect(Collectors.toList());

        assertEquals(4, combinations.size());
        assertSameSize(2, combinations);

        assertContains(List.of(Optional.empty(), Optional.empty()), combinations);
        assertContains(List.of(Optional.empty(), Optional.of(1)), combinations);
        assertContains(List.of(Optional.of(1), Optional.empty()), combinations);
        assertContains(List.of(Optional.of(1), Optional.of(1)), combinations);
    }

    /**
     * Test all combinations of three strings.
     */
    @Test
    public void threeStrings() {
        List<List<Optional<String>>> combinations = Combinator
                .combinations(List.of("a", "b", "c"))
                .collect(Collectors.toList());

        assertEquals(8, combinations.size());
        assertSameSize(3, combinations);

        assertContains(List.of(Optional.empty(), Optional.empty(), Optional.empty()), combinations);
        assertContains(List.of(Optional.empty(), Optional.empty(), Optional.of("c")), combinations);
        assertContains(List.of(Optional.empty(), Optional.of("b"), Optional.empty()), combinations);
        assertContains(List.of(Optional.empty(), Optional.of("b"), Optional.of("c")), combinations);
        assertContains(List.of(Optional.of("a"), Optional.empty(), Optional.empty()), combinations);
        assertContains(List.of(Optional.of("a"), Optional.empty(), Optional.of("c")), combinations);
        assertContains(List.of(Optional.of("a"), Optional.of("b"), Optional.empty()), combinations);
        assertContains(List.of(Optional.of("a"), Optional.of("b"), Optional.of("c")), combinations);

    }

    /**
     * Assert that the actual list contains the expected item.
     * Otherwise it fails.
     *
     * @param expected expected list item
     * @param actual   actual list
     * @param <T>      list type
     */
    private <T> void assertContains(T expected, List<T> actual) {
        if (!actual.contains(expected)) {
            fail(String.format("Expected %s in list %s", expected, actual));
        }
    }

    /**
     * Assert that every list in the actual list has the excepted size.
     * Otherwise it fails.
     *
     * @param expected expected list size
     * @param actual   actual list of lists
     * @param <T>      list of list type
     */
    private <T> void assertSameSize(int expected, List<List<T>> actual) {
        for (List<?> objects : actual) {
            if (objects.size() != expected) {
                fail(String.format("Expected %d elements in %s", expected, objects));
            }
        }
    }
}