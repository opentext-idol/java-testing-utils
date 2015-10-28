/*
 * Copyright 2013-2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.frontend.testing.matchers;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.mockito.ArgumentMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.AllOf.allOf;

/**
 * Mockito matcher that matches a given item if it is a set containing the given items.
 * <p/>
 * This differs from {@link org.hamcrest.core.IsCollectionContaining} in that it explicitly returns a {@link Set}, which
 * cam help with compilation errors.
 * @param <T> The parameterized type of the set
 */
public class SetContainingItems<T> extends ArgumentMatcher<Set<? super T>> {

    private final Set<? super T> set = new HashSet<>();
    private Matcher<? super T> matcher;

    /**
     * Constructs a new SetContainingItems that matches a set containing the given items
     * @param items The items the set must contain
     */
    @SafeVarargs
    public SetContainingItems(final T... items) {
        set.addAll(Arrays.asList(items));
    }

    /**
     * Constructs a new SetContainingItems that matches a set where all the items match a given matcher
     * @param matcher The matcher that must match all items in the set
     */
    public SetContainingItems(final Matcher<? super T> matcher) {
        this.matcher = matcher;
    }

    /**
     * Convenience method for constructing a SetContainingItems
     * @param items The items the set must contain
     * @param <T> The type of the items
     * @return A SetContainingItems that matches a set containing the items
     */
    @SafeVarargs
    @Factory
    public static <T> SetContainingItems<T> isSetWithItems(final T... items) {
        return new SetContainingItems<>(items);
    }

    /**
     * Convenience method for constructing a SetContainingItems where the items in the set must match the given matchers
     * @param matchers The matchers the items in the set must match
     * @param <T> The type of the items in the set
     * @return A SetContainingItems which will match a set where all the items must match all the given matchers
     */
    @SafeVarargs
    @Factory
    public static <T> Matcher<Set<T>> isSetWithItems(final Matcher<? super T>... matchers) {
        final List<Matcher<? super Set<T>>> all = new ArrayList<>(matchers.length);

        for (final Matcher<? super T> elementMatcher : matchers) {
            // Doesn't forward to hasItem() method so compiler can sort out generics.
            all.add(new SetContainingItems<>(elementMatcher));
        }

        return allOf(all);
    }

    @Override
    public boolean matches(final Object item) {
        if (!(item instanceof Set)) {
            return false;
        }

        final Set<?> itemAsSet = (Set<?>) item;

        if (matcher == null) {
            return set.containsAll(itemAsSet);
        } else {
            for (final Object setItem : itemAsSet) {
                if (matcher.matches(setItem)) {
                    return true;
                }
            }

            return false;
        }
    }
}
