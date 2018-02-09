/*
*  Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*
*/
package org.ballerinalang.util.tracer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * This class represents the registry for the tracer.
 */
public class TracerRegistry {

    private static final TracerRegistry instance = new TracerRegistry();
    private BallerinaTracer tracer;
    private static final String TRACER = "tracer";
    private static final Logger logger = LoggerFactory.getLogger(TracerRegistry.class);


    private TracerRegistry() {
        String tracer = System.getProperty(TRACER);
        if (tracer == null) {
            tracer = "org.ballerina.tracing.core.OpenTracerFactory";
        }
        Class<?> tracerClass;
        try {
            tracerClass = Class.forName(tracer).asSubclass(BallerinaTracer.class);
            this.tracer = (BallerinaTracer) tracerClass.getMethod("getInstance").invoke(null);
        } catch (ClassNotFoundException | IllegalAccessException |
                NoSuchMethodException | InvocationTargetException e) {
            logger.error("error while loading the tracer. ", e);
        }
    }

    public static TracerRegistry getInstance() {
        return instance;
    }

    public BallerinaTracer getTracer() {
        return tracer;
    }

    public static String getPropertyNameForParentSpanHolder() {
        return "TRACING_SCOPES_" + Thread.currentThread().getId();
    }
}
