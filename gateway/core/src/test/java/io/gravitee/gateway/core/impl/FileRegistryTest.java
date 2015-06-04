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
package io.gravitee.gateway.core.impl;

import io.gravitee.gateway.core.Registry;
import io.gravitee.gateway.core.registry.FileRegistry;
import org.junit.Assert;
import org.junit.Test;

import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author David BRASSELY (brasseld at gmail.com)
 */
public class FileRegistryTest {

    @Test
    public void testConfigurations() throws URISyntaxException {
        URL url = FileRegistryTest.class.getResource("/registry/conf");

        Registry registry = new FileRegistry(url.getPath());
        Assert.assertTrue(registry != null);
        Assert.assertTrue(registry.listAll().size()  == 1);
    }
}
