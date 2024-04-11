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

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.mockito.ArgumentMatcher;
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;

import java.util.*;

import static org.hamcrest.core.AllOf.allOf;

/**
 * Mockito matcher that matches a given item if it is a set containing the given items.
 *
 * This differs from {@link org.hamcrest.core.IsCollectionContaining} in that it explicitly returns a {@link Set}, which
 * cam help with compilation errors.
 * @param <T> The parameterized type of the set
 */
class SetContainingItems<T> extends BaseMatcher<Set<? super T>> {

    private final Set<? super T> set = new HashSet<>();
    private ArgumentMatcher<? super T> matcher;

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
    private SetContainingItems(final ArgumentMatcher<? super T> matcher) {
        this.matcher = matcher;
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
    static <T> ArgumentMatcher<Set<T>> isSetWithItems(final ArgumentMatcher<T>... matchers) {
        final Collection<Matcher<? super Set<T>>> all = new ArrayList<>(matchers.length);

        for (final ArgumentMatcher<T> elementMatcher : matchers) {
            // Doesn't forward to hasItem() method so compiler can sort out generics.
            all.add(new SetContainingItems<>(elementMatcher));
        }

        return new HamcrestArgumentMatcher<>(allOf(all));
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
                if (matcher.matches((T) setItem)) {
                    return true;
                }
            }

            return false;
        }
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("matches");
    }
}
