/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.gateway.core.manager;

import io.gravitee.common.event.EventManager;
import io.gravitee.gateway.core.builder.ApiDefinitionBuilder;
import io.gravitee.gateway.core.builder.ProxyDefinitionBuilder;
import io.gravitee.gateway.core.definition.Api;
import io.gravitee.gateway.core.definition.validator.ValidationException;
import io.gravitee.gateway.core.definition.validator.Validator;
import io.gravitee.gateway.core.event.ApiEvent;
import io.gravitee.gateway.core.manager.impl.ApiManagerImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

/**
 * @author David BRASSELY (brasseld at gmail.com)
 */
public class ApiManagerTest {

    private ApiManager apiManager;

    @Mock
    private Validator validator;

    @Mock
    private EventManager eventManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        apiManager = spy(new ApiManagerImpl());
        ((ApiManagerImpl)apiManager).setEventManager(eventManager);
        ((ApiManagerImpl)apiManager).setValidator(validator);
    }

    @Test
    public void add_simpleApi() {
        Api api = new ApiDefinitionBuilder().name("api-test")
                .proxy(new ProxyDefinitionBuilder().contextPath("/team").target("http://localhost/target").build()).build();

        apiManager.deploy(api);

        verify(eventManager, only()).publishEvent(ApiEvent.DEPLOY, api);
    }

    @Test
    public void add_simpleApi_validationError() {
        Api api = new ApiDefinitionBuilder().name("api-test")
                .proxy(new ProxyDefinitionBuilder().contextPath("/team").target("http://localhost/target").build()).build();

        doThrow(new ValidationException()).when(validator).validate(api);

        apiManager.deploy(api);

        verify(eventManager, never()).publishEvent(ApiEvent.DEPLOY, api);
    }
}
