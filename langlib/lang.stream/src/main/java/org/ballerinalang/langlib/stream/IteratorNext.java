/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.ballerinalang.langlib.stream;

import org.ballerinalang.jvm.BallerinaValues;
import org.ballerinalang.jvm.scheduling.Strand;
import org.ballerinalang.jvm.types.BFunctionType;
import org.ballerinalang.jvm.types.BRecordType;
import org.ballerinalang.jvm.types.BUnionType;
import org.ballerinalang.jvm.values.*;
import org.ballerinalang.jvm.values.api.BStreamIterator;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.natives.annotations.ReturnType;


/**
 * Native implementation of lang.stream.StreamIterator:next().
 *
 * @since 1.2
 */
@BallerinaFunction(
        orgName = "ballerina", packageName = "lang.stream", functionName = "next",
        receiver = @Receiver(type = TypeKind.OBJECT, structType = "StreamIterator",
                structPackage = "ballerina/lang.stream"),
        returnType = {@ReturnType(type = TypeKind.RECORD)},
        isPublic = true
)
public class IteratorNext {
    //TODO: refactor hard coded values
    public static Object next(Strand strand, ObjectValue m) {
        BStreamIterator strmIterator = (BStreamIterator) m.getNativeData("&iterator&");

        if (strmIterator == null) {
            strmIterator = ((StreamValue) m.get("strm"));
            m.addNativeData("&iterator&", strmIterator);
        }

        Object next = strmIterator.next(strand);
        if (next != null) {
            BFunctionType nextFuncType = m.getType().getAttachedFunctions()[0].type;
            BRecordType recordType = (BRecordType) ((BUnionType) nextFuncType.retType).getMemberTypes().get(0);
            return BallerinaValues.createRecord(new MapValueImpl<>(recordType), next);
        }

        return null;
    }
}
