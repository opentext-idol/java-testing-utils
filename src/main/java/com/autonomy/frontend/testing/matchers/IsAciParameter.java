package com.autonomy.frontend.testing.matchers;

/*
 * $Id:$
 *
 * Copyright (c) 2015, Autonomy Systems Ltd.
 *
 * Last modified by $Author:$ on $Date:$
 */

import com.autonomy.aci.client.transport.AciParameter;
import org.hamcrest.Factory;
import org.mockito.ArgumentMatcher;

public class IsAciParameter extends ArgumentMatcher<AciParameter> {

    private final String name;
    private final Object value;

    public IsAciParameter(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    @Factory
    public static IsAciParameter aciParameter(final String name, final String value) {
        return new IsAciParameter(name, value);
    }

    @Override
    public boolean matches(final Object argument) {
        if(!(argument instanceof AciParameter)) {
            return false;
        }

        final AciParameter parameter = (AciParameter) argument;

        return name.equalsIgnoreCase(parameter.getName())
            && value.equals(parameter.getValue());
    }
}
