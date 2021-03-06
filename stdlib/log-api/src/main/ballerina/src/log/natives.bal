// Copyright (c) 2017 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
//
// WSO2 Inc. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.


# Logs the specified value at DEBUG level.
#
# + msg - The message to be logged
public function printDebug(string | (function() returns (string)) msg) = external;

# Logs the specified message at ERROR level.
#
# + msg - The message to be logged
# + err - The error struct to be logged
public function printError(string | (function() returns (string)) msg, public error? err = ()) = external;

# Logs the specified message at INFO level.
#
# + msg - The message to be logged
public function printInfo(string | (function() returns (string)) msg) = external;

# Logs the specified message at TRACE level.
#
# + msg - The message to be logged
public function printTrace(string | (function() returns (string)) msg) = external;

# Logs the specified message at WARN level.
#
# + msg - The message to be logged
public function printWarn(string | (function() returns (string)) msg) = external;
