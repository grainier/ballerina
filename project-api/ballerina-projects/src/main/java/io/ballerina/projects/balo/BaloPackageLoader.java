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
package io.ballerina.projects.balo;

import io.ballerina.projects.PackageConfig;
import io.ballerina.projects.PackageDescriptor;
import io.ballerina.projects.PackageName;
import io.ballerina.projects.PackageOrg;
import io.ballerina.projects.PackageVersion;
import io.ballerina.projects.directory.PackageData;
import io.ballerina.projects.directory.PackageLoader;

import java.nio.file.Path;
import java.util.Collections;

/**
 * Contains a set of utility methods that creates the config hierarchy from balo file.
 *
 * @since 2.0.0
 */
public class BaloPackageLoader extends PackageLoader {

    public static PackageConfig loadPackage(String packageDir) {
        // TODO Refactor this code
        PackageData packageData = BaloFiles.loadPackageData(packageDir);
        Path baloName = packageData.packagePath().getFileName();
        PackageName packageName = PackageName.from(getPackageNameFromBaloName(String.valueOf(baloName)));
        PackageOrg packageOrg = PackageOrg.from(getOrgFromBaloName(String.valueOf(baloName)));
        PackageVersion packageVersion = PackageVersion.from(getVersionFromBaloName(String.valueOf(baloName)));
        PackageDescriptor packageDescriptor = new PackageDescriptor(packageName,
                packageOrg, packageVersion, Collections.emptyList(), Collections.emptyMap());
        return createPackageConfig(packageData, packageDescriptor);
    }
}
