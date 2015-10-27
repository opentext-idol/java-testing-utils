/*
 * Copyright 2013-2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.autonomy.frontend.testing.mock;

import org.springframework.beans.factory.FactoryBean;
import static org.mockito.Mockito.mock;

public class MocksFactory implements FactoryBean<Object> {

    private Class<?> type;// the created object type

    public void setType(final Class<?> type) {
        this.type = type;
    }

    @Override
    public Object getObject() throws Exception {
        return mock(type);
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
