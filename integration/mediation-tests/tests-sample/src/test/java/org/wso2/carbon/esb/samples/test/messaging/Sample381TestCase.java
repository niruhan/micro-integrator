/*
 *Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *WSO2 Inc. licenses this file to you under the Apache License,
 *Version 2.0 (the "License"); you may not use this file except
 *in compliance with the License.
 *You may obtain a copy of the License at
 *
 *http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing,
 *software distributed under the License is distributed on an
 *"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *KIND, either express or implied.  See the License for the
 *specific language governing permissions and limitations
 *under the License.
 */
package org.wso2.carbon.esb.samples.test.messaging;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.carbon.automation.engine.annotations.ExecutionEnvironment;
import org.wso2.carbon.automation.engine.annotations.SetEnvironment;
import org.wso2.carbon.esb.samples.test.messaging.utils.MDDProducer;
import org.wso2.carbon.esb.samples.test.util.ESBSampleIntegrationTest;
import org.wso2.esb.integration.common.utils.CarbonLogReader;

public class Sample381TestCase extends ESBSampleIntegrationTest {

    @BeforeClass(alwaysRun = true)
    public void startJMSBrokerAndConfigureESB() throws Exception {

        super.init();
    }

    @SetEnvironment(executionEnvironments = { ExecutionEnvironment.STANDALONE })
    @Test(groups = { "wso2.esb" }, description = "Test JMS broker with topic")
    public void JMSBrokerTopicTest() throws Exception {

        int numberOfMsgToExpect = 5;

        CarbonLogReader carbonLogReader = new CarbonLogReader();
        carbonLogReader.start();

        MDDProducer mddProducerMSTF = new MDDProducer();

        for (int i = 0; i < numberOfMsgToExpect; i++) {
            mddProducerMSTF.sendMessage("MSTF", "dynamicQueues/JMSBinaryProxy");
        }
        Thread.sleep(5000);
        Assert.assertTrue(carbonLogReader.checkForLog("MSTF", DEFAULT_TIMEOUT), "Request log not found");
        carbonLogReader.stop();
    }

}
