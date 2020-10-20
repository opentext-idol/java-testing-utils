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

package com.hp.autonomy.frontend.testing.mock;

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
        return false;
    }
}
