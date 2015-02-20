package com.autonomy.frontend.testing.mock;

import org.springframework.beans.factory.FactoryBean;
import static org.mockito.Mockito.mock;

/*
* $Id:$
*
* Copyright (c) 2010, Autonomy Systems Ltd.
*
* Last modified by $Author:$ on $Date:$
*/

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
