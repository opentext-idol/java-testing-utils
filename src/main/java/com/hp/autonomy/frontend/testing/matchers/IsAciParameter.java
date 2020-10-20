/*
 * (c) Copyright 2013-2015 Micro Focus or one of its affiliates.
 *
 * Licensed under the MIT License (the "License"); you may not use this file
 * except in compliance with the License.
 *
 * The only warranties for products and services of Micro Focus and its affiliates
 * and licensors ("Micro Focus") are as may be set forth in the express warranty
 * statements accompanying such products and services. Nothing herein should be
 * construed as constituting an additional warranty. Micro Focus shall not be
 * liable for technical or editorial errors or omissions contained herein. The
 * information contained herein is subject to change without notice.
 */

package com.hp.autonomy.frontend.testing.matchers;

import com.autonomy.aci.client.transport.AciParameter;
import org.hamcrest.Factory;
import org.mockito.ArgumentMatcher;

/**
 * Mockito Matcher for matching ACI parameters. It will match an ACI parameter with a given name (ignoring case) and a
 * given value.
 */
public class IsAciParameter extends ArgumentMatcher<AciParameter> {

    private final String name;
    private final Object value;

    /**
     * Creates a new IsAciParameter matcher
     * @param name The name of the parameter
     * @param value The value of the parameter
     */
    public IsAciParameter(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Static factory method to improve test readability
     * @param name The name of the parameter
     * @param value The value of the parameter
     * @return A new IsAciParameter with the given name and value
     */
    @Factory
    public static IsAciParameter aciParameter(final String name, final String value) {
        return new IsAciParameter(name, value);
    }

    @Override
    public boolean matches(final Object argument) {
        if (!(argument instanceof AciParameter)) {
            return false;
        }

        final AciParameter parameter = (AciParameter) argument;

        return name.equalsIgnoreCase(parameter.getName())
            && value.equals(parameter.getValue());
    }
}
