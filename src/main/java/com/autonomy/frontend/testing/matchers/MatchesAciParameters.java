/*
 * Copyright 2013-2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.autonomy.frontend.testing.matchers;

import com.autonomy.aci.client.transport.AciParameter;
import com.autonomy.aci.client.util.AciParameters;
import org.mockito.ArgumentMatcher;

/**
 * Mockito matcher that checks that two {@link AciParameters} contain the same keys and values.
 */
public class MatchesAciParameters extends ArgumentMatcher<AciParameters> {
    private final boolean onlyContains;
    private final AciParameters parameters;

    /**
     * Creates a new MatchesAciParameters that matches the given parameters
     * @param parameters The parameters to match
     */
    public MatchesAciParameters(final AciParameters parameters) {
        this(parameters, true);
    }

    /**
     * Creates a new MatchesAciParameters that matches the given parameters
     * @param parameters The parameters to match
     * @param onlyContains true if the parameters must contain only the given parameters; false otherwise
     */
    public MatchesAciParameters(final AciParameters parameters, final boolean onlyContains) {
        this.parameters = parameters;
        this.onlyContains = onlyContains;
    }

    @Override
    public boolean matches(final Object object) {
        if (!(object instanceof AciParameters)) {
            return false;
        }

        final AciParameters other = (AciParameters) object;

        if (onlyContains && parameters.size() != other.size()) {
            return false;
        }

        for (final AciParameter parameter : parameters) {
            boolean found = false;

            for (final AciParameter otherParameter : other) {
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
    public static MatchesAciParameters equalsAciParameters(final AciParameters aciParameters) {
        return new MatchesAciParameters(aciParameters);
    }

    /**
     * Static factory for creating a MatchesAciParameters that matches the given parameters
     * @param aciParameters The parameters to match
     * @return A MatchesAciParameters that will match objects that contain the given parameters
     */
    public static MatchesAciParameters containsAciParameters(final AciParameters aciParameters) {
        return new MatchesAciParameters(aciParameters, false);
    }
}
