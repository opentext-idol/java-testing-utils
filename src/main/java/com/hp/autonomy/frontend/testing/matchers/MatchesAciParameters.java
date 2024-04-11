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

import com.autonomy.aci.client.transport.ActionParameter;
import com.autonomy.aci.client.util.ActionParameters;
import org.mockito.ArgumentMatcher;

/**
 * Mockito matcher that checks that two {@link ActionParameters} contain the same keys and values.
 */
public class MatchesAciParameters implements ArgumentMatcher<ActionParameters> {
    private final boolean onlyContains;
    private final ActionParameters parameters;

    /**
     * Creates a new MatchesAciParameters that matches the given parameters
     * @param parameters The parameters to match
     */
    public MatchesAciParameters(final ActionParameters parameters) {
        this(parameters, true);
    }

    /**
     * Creates a new MatchesAciParameters that matches the given parameters
     * @param parameters The parameters to match
     * @param onlyContains true if the parameters must contain only the given parameters; false otherwise
     */
    public MatchesAciParameters(final ActionParameters parameters, final boolean onlyContains) {
        this.parameters = parameters;
        this.onlyContains = onlyContains;
    }

    @Override
    public boolean matches(final ActionParameters other) {
        if (onlyContains && parameters.size() != other.size()) {
            return false;
        }

        for (final ActionParameter<?> parameter : parameters) {
            boolean found = false;

            for (final ActionParameter<?> otherParameter : other) {
                if (parameter.getName().equals(otherParameter.getName()) && parameter.getValue().equals(otherParameter.getValue())) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                return false;
            }
        }

        return true;
    }

    /**
     * Static factory for creating a MatchesAciParameters that exactly matches the given parameters
     * @param aciParameters The parameters to match
     * @return A MatchesAciParameters that will match objects that equal the given parameters
     */
    public static MatchesAciParameters equalsAciParameters(final ActionParameters aciParameters) {
        return new MatchesAciParameters(aciParameters);
    }

    /**
     * Static factory for creating a MatchesAciParameters that matches the given parameters
     * @param aciParameters The parameters to match
     * @return A MatchesAciParameters that will match objects that contain the given parameters
     */
    public static MatchesAciParameters containsAciParameters(final ActionParameters aciParameters) {
        return new MatchesAciParameters(aciParameters, false);
    }
}
