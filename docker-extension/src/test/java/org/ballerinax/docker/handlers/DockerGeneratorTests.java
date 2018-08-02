/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinax.docker.handlers;

import org.ballerinax.docker.DockerArtifactHandler;
import org.ballerinax.docker.models.CopyFileModel;
import org.ballerinax.docker.models.DockerModel;
import org.ballerinax.docker.utils.DockerGenUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Docker generator tests.
 */
public class DockerGeneratorTests {

    private final Logger log = LoggerFactory.getLogger(DockerGeneratorTests.class);

    @Test
    public void testDockerGenerate() throws IOException {
        DockerModel dockerModel = new DockerModel();
        Set<Integer> ports = new HashSet<>();
        ports.add(9090);
        ports.add(9091);
        ports.add(9092);
        dockerModel.setPorts(ports);
        dockerModel.setService(true);
        dockerModel.setBalxFileName("example.balx");
        dockerModel.setEnableDebug(true);
        dockerModel.setDebugPort(5005);

        ClassLoader classLoader = getClass().getClassLoader();
        File confFile = new File(classLoader.getResource("conf/ballerina.conf").getFile());
        Set<CopyFileModel> files = new HashSet<>();
        CopyFileModel confFileModel = new CopyFileModel();
        confFileModel.setSource(confFile.getAbsolutePath());
        confFileModel.setTarget("/home/ballerina/conf/");
        confFileModel.setBallerinaConf(true);
        files.add(confFileModel);

        File dataFile = new File(classLoader.getResource("conf/data.txt").getFile());
        CopyFileModel dataFileModel = new CopyFileModel();
        dataFileModel.setSource(dataFile.getAbsolutePath());
        dataFileModel.setTarget("/home/ballerina/data/");
        dataFileModel.setBallerinaConf(false);
        files.add(dataFileModel);
        dockerModel.setFiles(files);

        String dockerfileContent = new DockerArtifactHandler(dockerModel).generate();
        File dockerfile = new File("target/docker");
        dockerfile.mkdirs();
        dockerfile = new File("target/docker/Dockerfile");
        DockerGenUtils.writeToFile(dockerfileContent, dockerfile.getPath());
        log.info("Dockerfile Content:\n" + dockerfileContent);
        Assert.assertTrue(dockerfile.exists());
        dockerfile.deleteOnExit();
    }
}
