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

import com.autonomy.aci.client.transport.AciParameter;
import org.mockito.ArgumentMatcher;

/**
 * Mockito Matcher for matching ACI parameters. It will match an ACI parameter with a given name (ignoring case) and a
 * given value.
 */
public class IsAciParameter implements ArgumentMatcher<AciParameter> {

    private final String name;
    private final String value;

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
    public static IsAciParameter aciParameter(final String name, final String value) {
        return new IsAciParameter(name, value);
    }

    @Override
    public boolean matches(final AciParameter parameter) {
        return name.equalsIgnoreCase(parameter.getName())
                && value.equalsIgnoreCase(parameter.getValue());
    }
}
