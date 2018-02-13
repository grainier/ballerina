/*
 * Copyright (c)  2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.util.trace;

/**
 * API to expose ballerina tracing hooks.
 *
 * @since 0.96.1
 */
public interface BallerinaTracing {
    void markIn(String parentUuid, String uuid, BallerinaTracer.InstructionType type, String workerName,
                String methodName, String callerName);

    void markOut(String uuid, BallerinaTracer.InstructionType type, String workerName, String methodName,
                 String callerName);
}
