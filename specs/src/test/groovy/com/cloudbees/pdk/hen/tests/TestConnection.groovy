package com.cloudbees.pdk.hen.tests

import com.cloudbees.pdk.hen.procedures.TestConfiguration
import spock.lang.Unroll
import spock.lang.Shared

class TestConnection extends PluginTestHelper {
    @Shared
    TestConfiguration testConfiguration = pluginWithoutConfig.testConfiguration

    def 'Test connection'() {
        when:
        def r = testConfiguration.serviceurl(serviceUrl)
            .credential(awsAccessKeyId, awsSecretAccessKey)
            .workspace(DEFAULT)
            .resourcepool(DEFAULT)
            .debug("1")
            .run()
        then:
        assert r.successful
    }

    @Unroll
    def 'Negative: Test connection - #cases'() {
        when:
        def r = testConfiguration.serviceurl(serviceUrl)
                .credential(accessKeyID, secretAccessKey)
                .workspace(DEFAULT)
                .resourcepool(DEFAULT)
                .debug("1")
                .run()
        then:
        assert !r.successful
        assert r.getSummary() =~ expectedLog
        assert r.jobLog =~ expectedLog
        where:
        cases                       |   accessKeyID     |   secretAccessKey     |   expectedLog
        "invalid access key ID"     |   "invalid"       |   awsSecretAccessKey  |   "Status Code: 403"
        "invalid secret access key" |   awsAccessKeyId  |   "invalid"           |   "Status Code: 403"
        "empty access key ID"       |   EMPTY           |   awsSecretAccessKey  |   "a non-empty Access Key \\(AKID\\) must be provided in the credential"
        "empty secret access key"   |   awsAccessKeyId  |   EMPTY               |   "a non-empty Access Key \\(AKID\\) must be provided in the credential"
    }

}
