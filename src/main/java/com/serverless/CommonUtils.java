package com.serverless;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public final class CommonUtils {

    private CommonUtils() {

    }

    public static boolean isNull(Map<String, String> value) {
        return value == null;
    }

    public static boolean isNotNull(Map<String, String> value) {
        return value != null;
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isNotNullOrEmpty(final Collection<?> coll) {
        return !isNullOrEmpty(coll);
    }
    
    public static boolean isNotNullOrEmpty(final Map<?, ?> map) {
        return !isNullOrEmpty(map);
    }

    /**
     * Checks if a {@link Collection} does not contain any element.
     *
     * @param the
     *            <code>Collection</code> to check.
     * @return <code>true</code> if the <code>Collection</code> does not contain any
     *         element or is <code>null</code>.
     */
    public static boolean isNullOrEmpty(final Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    /**
     * Checks if a {@link Map} does not contain any element.
     *
     * @param the
     *            <code>Map</code> to check.
     * @return <code>true</code> if the <code>Map</code> does not contain any
     *         element or is <code>null</code>.
     */
    public static boolean isNullOrEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Checks if a {@link Collection} does not contain any element.
     *
     * @param the
     *            <code>Collection</code> to check.
     * @return <code>true</code> if the <code>Collection</code> is not null and does
     *         not contain any element </code>.
     */
    public static boolean isEmpty(final Collection<?> coll) {
        return coll != null && coll.isEmpty();
    }

    /**
     * Returns the first element of an {@link Iterable} (eg. any {@link Collection})
     * in a <code>null</code> -safe way.
     *
     * @param iterable
     *            a (possibly empty) <code>Iterable</code>
     * @return The first element of the <code>Iterable</code> or <code>null</code>
     *         if <code>iterable</code> is <code>null</code> or empty.
     * 
     */
    public static <T> T first(final Iterable<T> iterable) {
        if (iterable == null) {
            return null;
        }
        final Iterator<T> iterator = iterable.iterator();
        return next(iterator);

    }

    /**
     * Returns the next element of the provided {@link Iterator} in a
     * <code>null</code>-safe way.
     *
     * @param iterator
     *            a (possibly empty) {@link Iterator}
     * @return The next element returned by the {@link Iterator} or
     *         <code>null</code> if <code>iterator</code> is <code>null</code> or
     *         exhausted.
     * 
     */
    public static <T> T next(final Iterator<T> iterator) {
        if (iterator != null && iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

}
