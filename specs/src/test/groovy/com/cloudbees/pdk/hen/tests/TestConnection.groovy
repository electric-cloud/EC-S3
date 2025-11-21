package com.cloudbees.pdk.hen.tests

import com.cloudbees.pdk.hen.ServerHandler
import com.cloudbees.pdk.hen.procedures.TestConfiguration
import com.electriccloud.plugins.annotations.NewFeature
import spock.lang.Unroll
import spock.lang.Shared

class TestConnection extends PluginTestHelper {
    @Shared
    TestConfiguration testConfiguration = pluginWithoutConfig.testConfiguration

    def setupSpec() {
        ServerHandler.getInstance().setupResource("s3-resource", "127.0.0.1", 7800)
    }

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
    @NewFeature(pluginVersion = '1.2.4')
    @Unroll
    def 'Test connection with resource'() {
        when:
        def r = testConfiguration.flush()
                .serviceurl(serviceUrl)
                .credential(awsAccessKeyId, awsSecretAccessKey)
                .workspace(DEFAULT)
                .resourcepool(DEFAULT)
                .debug("1")
                .checkConnectionResource(resource)
                .run()
        then:
        assert r.successful
        where:
        Des                 | resource
        "default resource"  | "local"
        "specific resource" | "s3-resource"
    }
    @NewFeature(pluginVersion = '1.2.4')
    def 'Negative: test config with invalid resource'() {
        when:
        def r = testConfiguration.flush()
                .serviceurl(serviceUrl)
                .credential(awsAccessKeyId, awsSecretAccessKey)
                .workspace(DEFAULT)
                .resourcepool(DEFAULT)
                .debug("1")
                .checkConnectionResource("invalid-resource")
                .run()
        then:
        assert !r.successful
        assert r.jobLog =~ "AGENT ERROR: NONEXISTENT_RESOURCE"
    }

}
