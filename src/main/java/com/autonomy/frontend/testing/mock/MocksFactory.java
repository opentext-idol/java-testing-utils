/*
 * Copyright 2013-2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.autonomy.frontend.testing.mock;

import org.springframework.beans.factory.FactoryBean;
import static org.mockito.Mockito.mock;

/**
 * {@link FactoryBean} for creating Mockito mock objects. This is useful if you're using a test application context
 * written in XML.
 *
 * If your test context uses Java configuration, there is no need to use this method.
 */
public class MocksFactory implements FactoryBean<Object> {

    private Class<?> type;// the created object type

    /**
     * @param type The type of the mock object. This is a required property.
     */
    public void setType(final Class<?> type) {
        this.type = type;
    }

    /**
     * @return A Mockito mock of the given type
     * @throws Exception
     */
    @Override
    public Object getObject() throws Exception {
        return mock(type);
    }

    /**
     * @return The type of the mock object
     */
    @Override
    public Class<?> getObjectType() {
        return type;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
