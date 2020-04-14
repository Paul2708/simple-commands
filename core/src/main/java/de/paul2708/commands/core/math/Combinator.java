package de.paul2708.commands.core.math;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This utility math class provides combinations.
 *
 * @author Paul2708
 */
public final class Combinator {

    /**
     * Disallow instances as utility class.
     */
    private Combinator() {
        throw new IllegalAccessError();
    }

    /**
     * Given a list of elements.
     * Return all possible combinations wrapped in optionals.
     * For example:
     * 1, 1 will produce [empty, empty], [empty, of(1)], [of(1), empty], [of(1), of(1)]
     * <br>
     * The output will have 2 ^ elements objects.
     * <br>
     * Source: https://stackoverflow.com/questions/37835286/generate-all-possible-combinations-java
     *
     * @see Combinator
     * @param list list of elements
     * @param <T>  element type
     * @return stream of list with optional items
     */
    public static <T> Stream<List<Optional<T>>> combinations(List<T> list) {
        long n = (long) Math.pow(2, list.size());

        return StreamSupport.stream(new Spliterators.AbstractSpliterator<>(n, Spliterator.SIZED) {
            long i = 0;

            @Override
            public boolean tryAdvance(Consumer<? super List<Optional<T>>> action) {
                if (i < n) {
                    List<Optional<T>> out = new ArrayList<>(Long.bitCount(i));
                    for (int bit = 0; bit < list.size(); bit++) {
                        if ((i & (1 << bit)) != 0) {
                            out.add(Optional.of(list.get(bit)));
                        } else {
                            out.add(Optional.empty());
                        }
                    }
                    action.accept(out);
                    ++i;
                    return true;
                } else {
                    return false;
                }
            }
        }, false);
    }
}