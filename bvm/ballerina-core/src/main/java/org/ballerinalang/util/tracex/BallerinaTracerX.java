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

package org.ballerinalang.util.tracex;

import org.ballerinalang.bre.bvm.StackFrame;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Stack;

/**
 * BallerinaTracerX marks pre and post instruction invocations to be used with tracing.
 *
 * @since 0.96.1
 */
public class BallerinaTracerX {
    private List<BallerinaTracing> tracers;

    private Map<String, Stack<Trace>> traceStacks = new HashMap<>(0);

    private boolean traceEnabled;

    private PrintStream printStream = System.out;

    private BallerinaTracerX() {

    }

    public BallerinaTracerX(boolean traceEnabled) {
        this.traceEnabled = traceEnabled;
        if (isTraceEnabled()) {
            tracers = new ArrayList<>(0);
            ServiceLoader<BallerinaTracing> loader = ServiceLoader.load(BallerinaTracing.class);
            loader.forEach(e -> tracers.add(e));
        }
    }

    /**
     * Method to mark entry point of a function.
     */
    public Trace buildSpan(InstructionType type, String name, StackFrame stackFrame) {
        if (isTraceEnabled()) {
            Trace caller = markAndGetCaller(stackFrame);
            Trace trace = new Trace(name);
            push(getWorkerName(stackFrame), trace);
            tracers.forEach(t -> t.markIn((caller != null) ? caller.getUuid() : null, trace.getUuid(), type,
                    getWorkerName(stackFrame), name, (caller != null) ? caller.getName() : null));
            return trace;
        }
        return null;
    }

    /**
     * Method to mark exit point of a function.
     */
    public void finishSpan(InstructionType type, String name, StackFrame stackFrame) {
        if (isTraceEnabled()) {
            Trace caller = markAndGetCaller(stackFrame);
            Trace trace = pop(getWorkerName(stackFrame));
            if (trace != null) {
                if (!name.equals(trace.getName())) {
                    printStream.println("**************************** ERROR A ****************************");
                }
                tracers.forEach(t -> t.markOut(trace.getUuid(), type, getWorkerName(stackFrame), name,
                        (caller != null) ? caller.getName() : null));
            } else {
                printStream.println("**************************** ERROR B ****************************");
            }
        }
    }

    /**
     * Method to mark exit point of a function.
     */
    public void finishSpan(InstructionType type, String name, String caller, StackFrame stackFrame) {
        if (isTraceEnabled()) {
            Trace trace = pop(getWorkerName(stackFrame));
            if (trace != null) {
                if (!name.equals(trace.getName())) {
                    printStream.println("**************************** ERROR C ****************************");
                }
                tracers.forEach(t -> t.markOut(trace.getUuid(), type, getWorkerName(stackFrame), name, caller));
            } else {
                printStream.println("**************************** ERROR D ****************************");
            }
        }
    }

    private void push(String worker, Trace trace) {
        if (!traceStacks.containsKey(worker)) {
            traceStacks.put(worker, new Stack<>());
        }
        traceStacks.get(worker).push(trace);
    }

    private Trace pop(String worker) {
        if (traceStacks.containsKey(worker)) {
            return traceStacks.get(worker).pop();
        }
        return null;
    }

    private Trace peek(String worker) {
        if (traceStacks.containsKey(worker) && !traceStacks.get(worker).empty()) {
            return traceStacks.get(worker).peek();
        }
        return null;
    }


    /**
     * Method to get worker name.
     */
    private String getWorkerName(StackFrame stackFrame) {
        String worker = null;
        if (stackFrame != null && stackFrame.getWorkerInfo() != null) {
            worker = stackFrame.getWorkerInfo().getWorkerName();
        }
        return worker == null ? "default" : worker;
    }

    /**
     * Method to mark exit point of a function.
     */
    private Trace markAndGetCaller(StackFrame stackFrame) {
        Trace callTrace = null;
        if (stackFrame != null && stackFrame.getCallableUnitInfo() != null) {
            String callerName = stackFrame.getCallableUnitInfo().getName();
            callTrace = peek(getWorkerName(stackFrame));
            if (callTrace == null || !callTrace.getName().equals(callerName)) {
                callTrace = buildSpan(InstructionType.CALL, callerName, stackFrame.prevStackFrame);
            }
        }
        return callTrace;
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
        CALL, RETURN, ACTION_CALL, ACTION_RETURN, NATIVE_CALL, NATIVE_RETURN,
        TRANSFORMER_CALL, TRANSFORMER_RETURN, WORKER_START, WORKER_HALT
    }
}
