/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import org.ballerinalang.bre.bvm.StackFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Tracer marks pre and post instruction invocations to be used with tracing.
 *
 * @since 0.96.1
 */
public class Tracer {
    private List<String> entryPoints;
    private List<BallerinaTracing> tracers;

    private boolean traceEnabled;

    private Tracer() {

    }

    public Tracer(boolean traceEnabled) {
        this.traceEnabled = traceEnabled;
        if (isTraceEnabled()) {
            entryPoints = new ArrayList<>(0);
            tracers = new ArrayList<>(0);
            ServiceLoader<BallerinaTracing> loader = ServiceLoader.load(BallerinaTracing.class);
            loader.forEach(e -> tracers.add(e));
        }
    }

    /**
     * Method to mark entry point of a function.
     */
    public void markIn(InstructionType type, String name, StackFrame stackFrame) {
        if (isTraceEnabled()) {
            String caller = markAndGetCaller(stackFrame);
            tracers.forEach(t -> t.markIn(caller + ":" + name, caller));
            entryPoints.add(name); // TODO: name needs package and all, also try recursion
        }
    }

    /**
     * Method to mark exit point of a function.
     */
    public void markOut(InstructionType type, String name, StackFrame stackFrame) {
        if (isTraceEnabled()) {
            String caller = markAndGetCaller(stackFrame);
            tracers.forEach(t -> t.markOut(caller + ":" + name, caller));
            // TODO: decide whether to remove or not (test on recursion).
            // entryPoints.remove(name);
        }
    }

    /**
     * Method to mark exit point of a function.
     */
    private String markAndGetCaller(StackFrame stackFrame) {
        String caller = null;
        if (stackFrame != null && stackFrame.getCallableUnitInfo() != null) {
            caller = stackFrame.getCallableUnitInfo().getName();
            if (!entryPoints.contains(caller)) {
                markIn(InstructionType.CALL, caller, null);
                entryPoints.add(caller);
            }
        }
        return (caller != null) ? caller : "root";
    }

    /**
     * Method to check tracing enabled or not.
     *
     * @return traceEnabled value.
     */
    public boolean isTraceEnabled() {
        return traceEnabled;
    }

    /**
     * Type of the instruction call.
     */
    public enum InstructionType {
        CALL, ACTION_CALL, NATIVE_CALL, TRANSFORMER_CALL, RETURN
    }
}
