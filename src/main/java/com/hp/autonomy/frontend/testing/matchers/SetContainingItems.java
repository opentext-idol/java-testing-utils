/*
 * Copyright 2013-2015 Open Text.
 *
 * Licensed under the MIT License (the "License"); you may not use this file
 * except in compliance with the License.
 *
 * The only warranties for products and services of Open Text and its affiliates
 * and licensors ("Open Text") are as may be set forth in the express warranty
 * statements accompanying such products and services. Nothing herein should be
 * construed as constituting an additional warranty. Open Text shall not be
 * liable for technical or editorial errors or omissions contained herein. The
 * information contained herein is subject to change without notice.
 */

package com.hp.autonomy.frontend.testing.matchers;

import org.mockito.ArgumentMatcher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Mockito matcher that matches a given item if it is a set containing the given items.
 *
 * This differs from {@link org.hamcrest.core.IsCollectionContaining} in that it explicitly returns a {@link Set}, which
 * cam help with compilation errors.
 * @param <T> The parameterized type of the set
 */
public class SetContainingItems<T> implements ArgumentMatcher<Set<T>> {
    private final Set<T> set = new HashSet<>();
    private final List<ArgumentMatcher<T>> matchers;

    /**
     * Constructs a new SetContainingItems that matches a set containing the given items
     * @param items The items the set must contain
     */
    @SafeVarargs
    public SetContainingItems(final T... items) {
        set.addAll(Arrays.asList(items));
        matchers = null;
    }

    /**
     * Constructs a new SetContainingItems that matches a set where all the items match a given matcher
     * @param matchers The matchers that must match all items in the set
     */
    private SetContainingItems(final List<ArgumentMatcher<T>> matchers) {
        this.matchers = matchers;
    }

    /**
     * Convenience method for constructing a SetContainingItems
     * @param items The items the set must contain
     * @param <T> The type of the items
     * @return A SetContainingItems that matches a set containing the items
     */
    @SafeVarargs
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
    public static <T> ArgumentMatcher<Set<T>> isSetWithItems(final ArgumentMatcher<T>... matchers) {
        return new SetContainingItems<>(Arrays.asList(matchers));
    }

    @Override
    public boolean matches(final Set<T> other) {
        if (matchers == null) {
            return set.containsAll(other);
        } else if (other == null) {
            return false;
        } else {
            for (final T item : other) {
                boolean matches = false;
                for (final ArgumentMatcher<? super T> matcher : matchers) {
                    if (matcher.matches(item)) {
                        matches = true;
                    }
                }
                if (matches) {
                    return true;
                }
            }

            return false;
        }
    }
}
