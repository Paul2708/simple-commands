package de.paul2708.commands.arguments.util;

/**
 * This class represents a pair of a key and a value. It's a 2-tuple like (key, value).
 *
 * @param <K> key type
 * @param <V> value type
 * @author Paul2708
 */
public final class Pair<K, V> {

    private K key;
    private V value;

    /**
     * Create a new pair with key and value.
     *
     * @param key key
     * @param value value
     */
    private Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Set the key.
     *
     * @param key new key
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Set the value.
     *
     * @param value new value
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Get the key.
     *
     * @return key
     */
    public K getKey() {
        return key;
    }

    /**
     * Get the value.
     *
     * @return value
     */
    public V getValue() {
        return value;
    }

    /**
     * Create a new pair with key and value.
     *
     * @param key key
     * @param value value
     * @param <K> key type
     * @param <V> value type
     * @return new pair
     */
    public static <K, V> Pair<K, V> of(K key, V value) {
        return new Pair<>(key, value);
    }

    /**
     * Get the pair as string by representing key and value.
     *
     * @return pair as string
     */
    @Override
    public String toString() {
        return "Pair{key=" + key + ", value=" + value + "}";
    }

    /**
     * Two pairs are equal, if the keys and the values are equal.
     *
     * @param o object to check
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

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (key != null ? !key.equals(pair.key) : pair.key != null) {
            return false;
        }

        return value != null ? value.equals(pair.value) : pair.value == null;
    }

    /**
     * Get the hash code by key and pair.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);

        return result;
    }
}