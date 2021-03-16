/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.ballerinalang.compiler.semantics.analyzer;

import org.wso2.ballerinalang.compiler.semantics.model.UniqueTypeVisitor;
import org.wso2.ballerinalang.compiler.semantics.model.symbols.BAttachedFunction;
import org.wso2.ballerinalang.compiler.semantics.model.symbols.BObjectTypeSymbol;
import org.wso2.ballerinalang.compiler.semantics.model.symbols.BTypeSymbol;
import org.wso2.ballerinalang.compiler.semantics.model.types.BAnnotationType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BAnyType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BAnydataType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BArrayType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BBuiltInRefType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BErrorType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BField;
import org.wso2.ballerinalang.compiler.semantics.model.types.BFiniteType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BFutureType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BHandleType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BIntSubType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BIntersectionType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BInvokableType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BJSONType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BMapType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BNeverType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BNilType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BNoType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BObjectType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BPackageType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BParameterizedType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BRecordType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BStreamType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BStructureType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BTableType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BTupleType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BTypedescType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BUnionType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BXMLSubType;
import org.wso2.ballerinalang.compiler.semantics.model.types.BXMLType;
import org.wso2.ballerinalang.compiler.util.Names;
import org.wso2.ballerinalang.compiler.util.TypeTags;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.hash;

/**
 * AnonTypeNameVisitor to visit and generate a hash code for a given type.
 *
 * @since Swan Lake
 */
public class AnonTypeNameVisitor implements UniqueTypeVisitor<Integer> {
    private static final String NO_TYPE = "noType";
    private Map<BType, Integer> visited;
    private Map<Integer, Integer> generated;

    public AnonTypeNameVisitor() {
        visited = new HashMap<>();
        generated = new HashMap<>();
    }

    @Override
    public boolean isVisited(BType type) {
        return visited.containsKey(type);
    }

    @Override
    public void reset() {
        visited.clear();
    }

    @Override
    public Integer visit(BType type) {
        if (type == null) {
            return 0;
        }

        if (isVisited(type)) {
            return visited.get(type);
        }

        switch (type.tag) {
            case TypeTags.TABLE:
                return visit((BTableType) type);
            case TypeTags.ANYDATA:
                return visit((BAnydataType) type);
            case TypeTags.RECORD:
                return visit((BRecordType) type);
            case TypeTags.ARRAY:
                return visit((BArrayType) type);
            case TypeTags.UNION:
                return visit((BUnionType) type);
            case TypeTags.TYPEDESC:
                return visit((BTypedescType) type);
            case TypeTags.MAP:
                return visit((BMapType) type);
            case TypeTags.FINITE:
                return visit((BFiniteType) type);
            case TypeTags.TUPLE:
                return visit((BTupleType) type);
            case TypeTags.INTERSECTION:
                return visit((BIntersectionType) type);
            case TypeTags.SIGNED8_INT:
            case TypeTags.SIGNED16_INT:
            case TypeTags.SIGNED32_INT:
            case TypeTags.UNSIGNED8_INT:
            case TypeTags.UNSIGNED16_INT:
            case TypeTags.UNSIGNED32_INT:
                return visit((BIntSubType) type);
            case TypeTags.XML_ELEMENT:
            case TypeTags.XML_PI:
            case TypeTags.XML_COMMENT:
            case TypeTags.XML_TEXT:
                return visit((BXMLSubType) type);
        }
        return addToVisited(type, baseHash(type));
    }

    @Override
    public Integer visit(BAnnotationType type) {
        Integer hash = hash(baseHash(type), type.getKind().typeName());
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BArrayType type) {
        Integer hash = hash(baseHash(type), type.size, type.state.getValue(), visit(type.eType));
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BBuiltInRefType type) {
        Integer hash = hash(baseHash(type), type.getKind().typeName());
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BAnyType type) {
        Integer hash = hash(baseHash(type), type.getKind().typeName());
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BFutureType type) {
        Integer hash = hash(baseHash(type), visit(type.constraint));
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BHandleType type) {
        Integer hash = hash(baseHash(type), type.getKind().typeName());
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BMapType type) {
        Integer hash = hash(baseHash(type), visit(type.constraint));
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BStreamType type) {
        Integer hash = hash(baseHash(type), visit(type.constraint), visit(type.error));
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BTypedescType type) {
        Integer hash = hash(baseHash(type), visit(type.constraint));
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BXMLType type) {
        Integer hash = hash(baseHash(type), visit(type.constraint));
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BAnydataType type) {
        Integer hash = hash(baseHash(type), type.getKind().typeName());
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BErrorType type) {
        Integer hash = hash(baseHash(type), visit(type.detailType));
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BInvokableType type) {
        Integer hash = hash(baseHash(type), type.paramTypes.stream().mapToInt(this::visit).toArray(),
                visit(type.restType), visit(type.retType));
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BJSONType type) {
        Integer hash = hash(baseHash(type), type.getKind().typeName());
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BParameterizedType type) {
        Integer hash = hash(baseHash(type), type.paramIndex, visit(type.paramValueType));
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BNeverType type) {
        Integer hash = hash(baseHash(type), Names.NEVER.value);
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BNilType type) {
        Integer hash = hash(baseHash(type), Names.NIL_VALUE.value);
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BNoType type) {
        Integer hash = hash(baseHash(type), NO_TYPE);
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BPackageType type) {
        Integer hash = hash(baseHash(type), type.getKind().typeName());
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BTupleType type) {
        List<Integer> tupleTypesHashes = getTypesHashes(type.getTupleTypes());
        Integer hash = hash(baseHash(type), tupleTypesHashes, visit(type.restType));
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BIntersectionType type) {
        List<Integer> constituentTypesHashes = getTypesHashes(type.getConstituentTypes());
        Integer hash = hash(baseHash(type), constituentTypesHashes, visit(type.effectiveType));
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BTableType type) {
        Integer hash = hash(baseHash(type), type.fieldNameList,
                visit(type.constraint), visit(type.keyTypeConstraint));
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BFiniteType type) {
        List<Integer> valueSpaceHashes = type.getValueSpace().stream().map(Object::toString)
                .sorted().map(String::hashCode).collect(Collectors.toList());
        Integer hash = hash(baseHash(type), type.isAnyData, valueSpaceHashes);
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BStructureType type) {
        List<Integer> fieldsHashes = getFieldsHashes(type.fields);
        List<Integer> typeInclHashes = getTypesHashes(type.typeInclusions);
        Integer hash = hash(baseHash(type), fieldsHashes, typeInclHashes);
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BObjectType type) {
        List<Integer> fieldsHashes = getFieldsHashes(type.fields);
        List<Integer> typeInclHashes = getTypesHashes(type.typeInclusions);
        final int initFunctionHash = getFunctionHash(((BObjectTypeSymbol) type.tsymbol).initializerFunc);
        List<Integer> attachedFunctionsHashes = getFunctionsHashes(((BObjectTypeSymbol) type.tsymbol).attachedFuncs);
        Integer hash = hash(baseHash(type), fieldsHashes, typeInclHashes, initFunctionHash,
                attachedFunctionsHashes, type.typeIdSet.hashCode());
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BRecordType type) {
        List<Integer> fieldsHashes = getFieldsHashes(type.fields);
        List<Integer> typeInclHashes = getTypesHashes(type.typeInclusions);
        Integer hash = hash(baseHash(type), type.sealed, fieldsHashes, visit(type.restFieldType), typeInclHashes);
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BUnionType type) {
        List<Integer> memberTypesHashes = getTypesHashes(type.getMemberTypes());
        Integer hash = hash(baseHash(type), type.isAnyData, type.isPureType, type.isCyclic, memberTypesHashes);
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BIntSubType type) {
        Integer hash = hash(baseHash(type), Names.INT.value, type.name.getValue());
        return addToVisited(type, hash);
    }

    @Override
    public Integer visit(BXMLSubType type) {
        Integer hash = hash(baseHash(type), Names.XML.value, type.name.getValue());
        return addToVisited(type, hash);
    }

    private Integer addToVisited(BType type, Integer hash) {
        Integer existing = Optional.ofNullable(generated.get(hash)).orElse(0);
        generated.put(hash, existing + 1);
        if (existing > 0 && !visited.containsKey(type)) {
            hash += existing;
        }
        visited.put(type, hash);
        return hash;
    }

    private Integer baseHash(BType type) {
        BTypeSymbol tSymbol = type.tsymbol;
        if (tSymbol == null) {
            return hash(type.tag, type.flags, type.isNullable());
        }
        return hash(type.tag, type.flags, tSymbol.pkgID, tSymbol.owner.tag,
                tSymbol.owner.name.value, type.isNullable());
    }

    private List<Integer> getTypesHashes(Collection<BType> types) {
        return types.stream().sorted(Comparator.comparingInt(f -> f.tag))
                .map(this::visit).collect(Collectors.toList());
    }

    private List<Integer> getFieldsHashes(Map<String, BField> fields) {
        return fields.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).map(e -> {
            BField f = e.getValue();
            return hash(f.name.value, f.symbol != null ? f.symbol.flags : null, visit(f.type));
        }).collect(Collectors.toList());
    }

    private List<Integer> getFunctionsHashes(List<BAttachedFunction> attachedFunctions) {
        return attachedFunctions.stream().sorted(Comparator.comparing(f -> f.funcName.value))
                .map(this::getFunctionHash).collect(Collectors.toList());
    }

    private int getFunctionHash(BAttachedFunction attachedFunction) {
        return hash(attachedFunction.funcName.value, attachedFunction.symbol.flags,
                visit(attachedFunction.type));
    }

}
