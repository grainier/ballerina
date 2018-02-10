/*
*  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing,
*  software distributed under the License is distributed on an
*  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*  KIND, either express or implied.  See the License for the
*  specific language governing permissions and limitations
*  under the License.
*/
package org.ballerinalang.util.codegen;

/**
 * This class creates specific instructions for the given opcode/bytecode.
 *
 * @since 0.87
 */
public class InstructionFactory {

    public static Instruction get(int opcode, int... operands) {
        // TODO Implement subtypes of certain instructions. etc for call, ret bytecodes
        return new Instruction(opcode, operands);
    }

    public static Instruction get(int opcode, int funcRefCPIndex, FunctionInfo functionInfo,
                                  int[] argRegs, int[] retRegs) {
        return new Instruction.InstructionCALL(opcode, funcRefCPIndex, functionInfo, argRegs, retRegs);
    }

    public static Instruction get(int opcode, int actionRefCPIndex, String actionName, int[] argRegs, int[] retRegs) {
        return new Instruction.InstructionACALL(opcode, actionRefCPIndex, actionName, argRegs, retRegs);
    }

    public static Instruction get(int opcode, int transformerRefCPIndex, TransformerInfo transformerInfo,
                                  int[] argRegs, int[] retRegs) {
        return new Instruction.InstructionTCALL(opcode, transformerRefCPIndex, transformerInfo, argRegs, retRegs);
    }
}
