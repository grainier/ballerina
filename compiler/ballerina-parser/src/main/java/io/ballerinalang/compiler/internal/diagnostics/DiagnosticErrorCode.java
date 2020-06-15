/*
 *  Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package io.ballerinalang.compiler.internal.diagnostics;

import io.ballerinalang.compiler.diagnostics.DiagnosticSeverity;

/**
 * Represents a diagnostic error code.
 *
 * @since 2.0.0
 */
public enum DiagnosticErrorCode implements DiagnosticCode {
    // TODO figure out an order of these error codes
    // Tokens
    ERROR_MISSING_TOKEN("BCE0001"),
    ERROR_MISSING_SEMICOLON_TOKEN("BCE0002"),
    ERROR_MISSING_COLON_TOKEN("BCE0003"),
    ERROR_MISSING_OPEN_PAREN_TOKEN("BCE0004"),
    ERROR_MISSING_CLOSE_PAREN_TOKEN("BCE0005"),
    ERROR_MISSING_OPEN_BRACE_TOKEN("BCE0006"),
    ERROR_MISSING_CLOSE_BRACE_TOKEN("BCE0007"),
    ERROR_MISSING_OPEN_BRACKET_TOKEN("BCE0008"),
    ERROR_MISSING_CLOSE_BRACKET_TOKEN("BCE0009"),
    ERROR_MISSING_EQUAL_TOKEN("BCE0010"),
    ERROR_MISSING_COMMA_TOKEN("BCE00011"),
    ERROR_MISSING_PLUS_TOKEN("BCE00012"),
    ERROR_MISSING_SLASH_TOKEN("BCE00013"),
    ERROR_MISSING_AT_TOKEN("BCE00014"),
    ERROR_MISSING_QUESTION_MARK_TOKEN("BCE00015"),
    ERROR_MISSING_GT_TOKEN("BCE00016"),
    ERROR_MISSING_GT_EQUAL_TOKEN("BCE00017"),
    ERROR_MISSING_LT_TOKEN("BCE00018"),
    ERROR_MISSING_LT_EQUAL_TOKEN("BCE00019"),
    ERROR_MISSING_RIGHT_DOUBLE_ARROW_TOKEN("BCE00020"),
    ERROR_MISSING_XML_COMMENT_END_TOKEN("BCE00021"),
    ERROR_MISSING_XML_PI_END_TOKEN("BCE00022"),
    ERROR_MISSING_DOUBLE_QUOTE_TOKEN("BCE00023"),
    ERROR_MISSING_BACKTICK_TOKEN("BCE00024"),
    ERROR_MISSING_OPEN_BRACE_PIPE_TOKEN("BCE00025"),
    ERROR_MISSING_CLOSE_BRACE_PIPE_TOKEN("BCE00026"),
    ERROR_MISSING_ASTERISK_TOKEN("BCE00027"),
    ERROR_MISSING_PIPE_TOKEN("BCE00028"),
    ERROR_MISSING_DOT_TOKEN("BCE00029"),

    // Keywords
    ERROR_MISSING_PUBLIC_KEYWORD("BCE02001"),
    ERROR_MISSING_PRIVATE_KEYWORD("BCE02002"),
    ERROR_MISSING_REMOTE_KEYWORD("BCE02003"),
    ERROR_MISSING_ABSTRACT_KEYWORD("BCE02004"),
    ERROR_MISSING_CLIENT_KEYWORD("BCE02005"),
    ERROR_MISSING_LISTENER_KEYWORD("BCE02006"),
    ERROR_MISSING_XMLNS_KEYWORD("BCE02007"),
    ERROR_MISSING_RESOURCE_KEYWORD("BCE02008"),
    ERROR_MISSING_FINAL_KEYWORD("BCE02009"),
    ERROR_MISSING_WORKER_KEYWORD("BCE02010"),
    ERROR_MISSING_PARAMETER_KEYWORD("BCE02011"),
    ERROR_MISSING_RETURNS_KEYWORD("BCE02012"),
    ERROR_MISSING_RETURN_KEYWORD("BCE02013"),
    ERROR_MISSING_TRUE_KEYWORD("BCE02014"),
    ERROR_MISSING_FALSE_KEYWORD("BCE02015"),
    ERROR_MISSING_ELSE_KEYWORD("BCE02016"),
    ERROR_MISSING_WHILE_KEYWORD("BCE02017"),
    ERROR_MISSING_CHECK_KEYWORD("BCE02018"),
    ERROR_MISSING_CHECKPANIC_KEYWORD("BCE02019"),
    ERROR_MISSING_PANIC_KEYWORD("BCE02020"),
    ERROR_MISSING_CONTINUE_KEYWORD("BCE02021"),
    ERROR_MISSING_BREAK_KEYWORD("BCE02022"),
    ERROR_MISSING_TYPEOF_KEYWORD("BCE020023"),
    ERROR_MISSING_IS_KEYWORD("BCE02024"),
    ERROR_MISSING_NULL_KEYWORD("BCE02025"),
    ERROR_MISSING_LOCK_KEYWORD("BCE02026"),
    ERROR_MISSING_FORK_KEYWORD("BCE02027"),
    ERROR_MISSING_TRAP_KEYWORD("BCE02028"),
    ERROR_MISSING_FOREACH_KEYWORD("BCE02029"),
    ERROR_MISSING_NEW_KEYWORD("BCE02030"),
    ERROR_MISSING_WHERE_KEYWORD("BCE02031"),
    ERROR_MISSING_SELECT_KEYWORD("BCE02032"),
    ERROR_MISSING_START_KEYWORD("BCE02033"),
    ERROR_MISSING_FLUSH_KEYWORD("BCE02034"),
    ERROR_MISSING_WAIT_KEYWORD("BCE02035"),
    ERROR_MISSING_DO_KEYWORD("BCE02036"),
    ERROR_MISSING_TRANSACTION_KEYWORD("BCE02037"),
    ERROR_MISSING_TRANSACTIONAL_KEYWORD("BCE02038"),
    ERROR_MISSING_COMMIT_KEYWORD("BCE02039"),
    ERROR_MISSING_ROLLBACK_KEYWORD("BCE02040"),
    ERROR_MISSING_RETRY_KEYWORD("BCE02041"),
    ERROR_MISSING_BASE16_KEYWORD("BCE02042"),
    ERROR_MISSING_BASE64_KEYWORD("BCE02043"),
    ERROR_MISSING_MATCH_KEYWORD("BCE02044"),
    ERROR_MISSING_DEFAULT_KEYWORD("BCE02045"),
    ERROR_MISSING_TYPE_KEYWORD("BCE02046"),
    ERROR_MISSING_ON_KEYWORD("BCE02047"),
    ERROR_MISSING_ANNOTATION_KEYWORD("BCE02048"),
    ERROR_MISSING_FUNCTION_KEYWORD("BCE02049"),
    ERROR_MISSING_SOURCE_KEYWORD("BCE02050"),
    ERROR_MISSING_ENUM_KEYWORD("BCE02051"),
    ERROR_MISSING_FIELD_KEYWORD("BCE02052"),
    ERROR_MISSING_VERSION_KEYWORD("BCE02053"),
    ERROR_MISSING_OBJECT_KEYWORD("BCE02054"),
    ERROR_MISSING_RECORD_KEYWORD("BCE02055"),
    ERROR_MISSING_SERVICE_KEYWORD("BCE02056"),
    ERROR_MISSING_AS_KEYWORD("BCE02057"),
    ERROR_MISSING_LET_KEYWORD("BCE02058"),
    ERROR_MISSING_TABLE_KEYWORD("BCE02059"),
    ERROR_MISSING_KEY_KEYWORD("BCE02060"),
    ERROR_MISSING_FROM_KEYWORD("BCE02061"),
    ERROR_MISSING_IN_KEYWORD("BCE02062"),
    ERROR_MISSING_IF_KEYWORD("BCE02063"),
    ERROR_MISSING_IMPORT_KEYWORD("BCE02064"),
    ERROR_MISSING_CONST_KEYWORD("BCE02065"),
    ERROR_MISSING_EXTERNAL_KEYWORD("BCE02066"),

    // Type keywords
    ERROR_MISSING_INT_KEYWORD("BCE02101"),
    ERROR_MISSING_BYTE_KEYWORD("BCE02102"),
    ERROR_MISSING_FLOAT_KEYWORD("BCE02103"),
    ERROR_MISSING_DECIMAL_KEYWORD("BCE02104"),
    ERROR_MISSING_STRING_KEYWORD("BCE02105"),
    ERROR_MISSING_BOOLEAN_KEYWORD("BCE02106"),
    ERROR_MISSING_XML_KEYWORD("BCE02107"),
    ERROR_MISSING_JSON_KEYWORD("BCE02108"),
    ERROR_MISSING_HANDLE_KEYWORD("BCE02109"),
    ERROR_MISSING_ANY_KEYWORD("BCE02110"),
    ERROR_MISSING_ANYDATA_KEYWORD("BCE02111"),
    ERROR_MISSING_NEVER_KEYWORD("BCE02112"),
    ERROR_MISSING_VAR_KEYWORD("BCE02113"),
    ERROR_MISSING_MAP_KEYWORD("BCE02114"),
    ERROR_MISSING_FUTURE_KEYWORD("BCE02115"),
    ERROR_MISSING_TYPEDESC_KEYWORD("BCE02116"),
    ERROR_MISSING_ERROR_KEYWORD("BCE02117"),
    ERROR_MISSING_STREAM_KEYWORD("BCE02118"),
    ERROR_MISSING_READONLY_KEYWORD("BCE02119"),
    ERROR_MISSING_DISTINCT_KEYWORD("BCE02120"),

    //Separators
    ERROR_MISSING_ELLIPSIS_TOKEN("BCE02201"),
    ERROR_MISSING_HASH_TOKEN("BCE02202"),
    ERROR_MISSING_SINGLE_QUOTE_TOKEN("BCE02203"),

    // Operators
    ERROR_MISSING_DOUBLE_EQUAL_TOKEN("BCE02301"),
    ERROR_MISSING_TRIPPLE_EQUAL_TOKEN("BCE02302"),
    ERROR_MISSING_MINUS_TOKEN("BCE02303"),
    ERROR_MISSING_PERCENT_TOKEN("BCE02304"),
    ERROR_MISSING_EXCLAMATION_MARK_TOKEN("BCE02305"),
    ERROR_MISSING_NOT_EQUAL_TOKEN("BCE02306"),
    ERROR_MISSING_NOT_DOUBLE_EQUAL_TOKEN("BCE02307"),
    ERROR_MISSING_BITWISE_AND_TOKEN("BCE02308"),
    ERROR_MISSING_BITWISE_XOR_TOKEN("BCE02309"),
    ERROR_MISSING_LOGICAL_AND_TOKEN("BCE02310"),
    ERROR_MISSING_LOGICAL_OR_TOKEN("BCE02311"),
    ERROR_MISSING_NEGATION_TOKEN("BCE02312"),
    ERROR_MISSING_RIGHT_ARROW_TOKEN("BCE02313"),
    ERROR_MISSING_INTERPOLATION_START_TOKEN("BCE02314"),
    ERROR_MISSING_XML_PI_START_TOKEN("BCE02315"),
    ERROR_MISSING_XML_COMMENT_START_TOKEN("BCE02316"),
    ERROR_MISSING_SYNC_SEND_TOKEN("BCE02317"),
    ERROR_MISSING_LEFT_ARROW_TOKEN("BCE02318"),
    ERROR_MISSING_DOUBLE_DOT_LT_TOKEN("BCE02319"),
    ERROR_MISSING_DOUBLE_LT_TOKEN("BCE02320"),
    ERROR_MISSING_ANNOT_CHAINING_TOKEN("BCE02321"),
    ERROR_MISSING_OPTIONAL_CHAINING_TOKEN("BCE02322"),
    ERROR_MISSING_ELVIS_TOKEN("BCE02323"),
    ERROR_MISSING_DOT_LT_TOKEN("BCE02324"),
    ERROR_MISSING_SLASH_LT_TOKEN("BCE02325"),
    ERROR_MISSING_DOUBLE_SLASH_DOUBLE_ASTERISK_LT_TOKEN("BCE02326"),
    ERROR_MISSING_SLASH_ASTERISK_TOKEN("BCE02327"),
    ERROR_MISSING_DOUBLE_GT_TOKEN("BCE02328"),
    ERROR_MISSING_TRIPPLE_GT_TOKEN("BCE02329"),

    // literals
    ERROR_MISSING_IDENTIFIER("BCE02500"),
    ERROR_MISSING_STRING_LITERAL("BCE02501"),
    ERROR_MISSING_DECIMAL_INTEGER_LITERAL("BCE02502"),
    ERROR_MISSING_HEX_INTEGER_LITERAL("BCE02503"),
    ERROR_MISSING_DECIMAL_FLOATING_POINT_LITERAL("BCE02504"),
    ERROR_MISSING_HEX_FLOATING_POINT_LITERAL("BCE02505"),
    ERROR_MISSING_XML_TEXT_CONTENT("BCE02506"),
    ERROR_MISSING_TEMPLATE_STRING("BCE02507"),

    //miscellaneous
    ERROR_MISSING_FUNCTION_NAME("BCE0060"),

    ERROR_MISSING_TYPE_DESC("BCE0100"),
    ERROR_MISSING_EXPRESSION("BCE0101"),
    ERROR_MISSING_SELECT_CLAUSE("BCE0102"),
    ERROR_MISSING_RECEIVE_FIELD_IN_RECEIVE_ACTION("BCE103"),
    ERROR_MISSING_WAIT_FIELD_IN_WAIT_ACTION("BCE104"),
    ERROR_MISSING_WAIT_FUTURE_EXPRESSION("BCE105"),
    ERROR_MISSING_ENUM_MEMBER("BCE106"),
    ERROR_MISSING_XML_ATOMIC_NAME_PATTERN("BCE107"),
    ERROR_MISSING_TUPLE_MEMBER("BCE108"),
    ERROR_EXPRESSION_EXPECTED_ACTION_FOUND("BCE109"),
    ERROR_MISSING_KEY_EXPR_IN_MEMBER_ACCESS_EXPR("BCE109"),

    ERROR_MISSING_ANNOTATION_ATTACH_POINT("BCE200"),
    ERROR_MISSING_LET_VARIABLE_DECLARATION("BCE201"),
    ERROR_MISSING_NAMED_WORKER_DECLARATION("BCE202"),
    ERROR_NAMED_WORKER_NOT_ALLOWED_HERE("BCE203"),
    ERROR_ONLY_NAMED_WORKERS_ALLOWED_HERE("BCE204"),
    ERROR_IMPORT_DECLARATION_AFTER_OTHER_DECLARATIONS("BCE205"),
    // Annotations are not supported for expressions
    ERROR_ANNOTATIONS_ATTACHED_TO_EXPRESSION("BCE206"),
    // Expression followed by the start keyword must be a func-call, a method-call or a remote-method-call
    ERROR_INVALID_EXPRESSION_IN_START_ACTION("BCE207"),
    // Cannot have the  same qualifier twice
    ERROR_SAME_OBJECT_TYPE_QUALIFIER("BCE208"),
    // Mapping constructor expression cannot be used as a wait expression
    ERROR_MAPPING_CONSTRUCTOR_EXPR_AS_A_WAIT_EXPR("BCE209"),
    // lhs must be an identifier or a param list
    ERROR_INVALID_PARAM_LIST_IN_INFER_ANONYMOUS_FUNCTION_EXPR("BCE210"),
    // Cannot have more fields after the rest type descriptor
    ERROR_MORE_RECORD_FIELDS_AFTER_REST_FIELD("BCE211"),
    ERROR_INVALID_XML_NAMESPACE_URI("BCE212"),
    ERROR_INTERPOLATION_IS_NOT_ALLOWED_FOR_XML_TAG_NAMES("BCE213"),
    ERROR_INTERPOLATION_IS_NOT_ALLOWED_WITHIN_ELEMENT_TAGS("BCE214"),
    ERROR_INTERPOLATION_IS_NOT_ALLOWED_WITHIN_XML_COMMENTS("BCE215"),
    ERROR_INTERPOLATION_IS_NOT_ALLOWED_WITHIN_XML_PI("BCE216"),

    ERROR_PARAMETER_AFTER_THE_REST_PARAMETER("BCE300"),
    ERROR_REQUIRED_PARAMETER_AFTER_THE_DEFAULTABLE_PARAMETER("BCE301"),
    ERROR_NAMED_ARG_FOLLOWED_BY_POSITIONAL_ARG("BCE302"),
    ERROR_ARG_FOLLOWED_BY_REST_ARG("BCE303"),

    ERROR_INVALID_BASE16_CONTENT_IN_BYTE_ARRAY_LITERAL("BCE401"),
    ERROR_INVALID_BASE64_CONTENT_IN_BYTE_ARRAY_LITERAL("BCE402"),
    ERROR_INVALID_CONTENT_IN_BYTE_ARRAY_LITERAL("BCE403"),
    ERROR_INVALID_TOKEN("404"),

    ERROR_INVALID_EXPRESSION_STATEMENT("BCE500"),
    ERROR_INVALID_ARRAY_LENGTH("BCE501"),
    ERROR_SELECT_CLAUSE_IN_QUERY_ACTION("BCE502"),
    ERROR_MORE_CLAUSES_AFTER_SELECT_CLAUSE("BCE503"),

    ERROR_NO_WHITESPACES_ALLOWED_IN_RIGHT_SHIFT_OP("BCE601"),
    ERROR_NO_WHITESPACES_ALLOWED_IN_UNSIGNED_RIGHT_SHIFT_OP("BCE602"),

    // Lexer errors
    ERROR_LEADING_ZEROS_IN_NUMERIC_LITERALS("BCE1000"),
    ERROR_MISSING_DIGIT_AFTER_EXPONENT_INDICATOR("BCE1001"),
    ERROR_INVALID_STRING_NUMERIC_ESCAPE_SEQUENCE("BCE1002"),
    ERROR_INVALID_ESCAPE_SEQUENCE("BCE1003"),
    ERROR_MISSING_DOUBLE_QUOTE("BCE1004"),
    ERROR_MISSING_HEX_DIGIT_AFTER_DOT("BCE1005"),
    ERROR_INVALID_WHITESPACE_BEFORE("BCE1006"),
    ERROR_INVALID_WHITESPACE_AFTER("BCE1006"),
    ERROR_INVALID_XML_NAME("BCE1007"),
    ERROR_INVALID_CHARACTER_IN_XML_ATTRIBUTE_VALUE("BCE1008"),
    ERROR_MISSING_ENTITY_REFERENCE_NAME("BCE1009"),
    ERROR_MISSING_SEMICOLON_IN_XML_REFERENCE("BCE1010"),
    ERROR_INVALID_ENTITY_REFERENCE_NAME_START("BCE1011"),
    ERROR_DOUBLE_HYPHEN_NOT_ALLOWED_WITHIN_XML_COMMENT("BCE1012"),
    ;

    String diagnosticId;

    DiagnosticErrorCode(String diagnosticId) {
        this.diagnosticId = diagnosticId;
    }

    @Override
    public DiagnosticSeverity severity() {
        return DiagnosticSeverity.ERROR;
    }

    @Override
    public String diagnosticId() {
        return diagnosticId;
    }
}
