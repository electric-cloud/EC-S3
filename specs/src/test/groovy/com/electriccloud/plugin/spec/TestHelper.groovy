package com.electriccloud.plugin.spec

import com.electriccloud.spec.PluginSpockTestSupport
import spock.util.concurrent.PollingConditions
import sun.reflect.generics.reflectiveObjects.NotImplementedException

class TestHelper extends PluginSpockTestSupport {
    private static S3Helper helper
    static final String pluginName = 'EC-S3'

    static S3Helper getHelperInstance() {
        if (!helper) {
            helper = new S3Helper(regionName: getRegionName())
        }
        return helper
    }


    static def getClientId() {
        def id = System.getenv('AWS_ACCESS_KEY_ID')
        assert id
        return id
    }

    static def getClientSecret() {
        def secret = System.getenv('AWS_SECRET_ACCESS_KEY')
        assert secret
        return secret
    }

    static def getEndpoint() {
        def regionName = getRegionName()
        def endpoint = "https://s3.${regionName}.amazonaws.com"
        return endpoint
    }

    static def getRegionName() {
        def regionName = System.getenv('AWS_REGION_NAME') ?: "us-east-2"
        return regionName
    }

    static def getConfigName() {
        return "${getRegionName()}-config"
    }

    def deleteConfig() {
        deleteConfiguration(pluginName, getConfigName())
    }

    def createConfig(withProxy = false) {
        if (withProxy) {
            throw new NotImplementedException()
        }
        def pluginConfig = [
            service_url: getEndpoint(),
            debug: '10',
            attempt: '1',
            desc: 'Spec config',
            resource_pool: 'spec resource pool',
            workspace: 'default',
        ]

        def props = [confPath: 's3_cfgs']

        if (System.getenv('RECREATE_CONFIG')) {
            props.recreate = true
        }
        def configName = getConfigName()
        createPluginConfiguration(
            pluginName,
            configName,
            pluginConfig,
            getClientId(),
            getClientSecret(),
            props
        )
    }

    def deleteArtifact(artifactName, version) {
        dsl "deleteArtifactVersion artifactVersionName: '$artifactName:$version'"
    }

    def getJobStepSummary(def jobId, def procedureName, def stepName) {
        assert jobId
        def summary = null
        def property = "/myJob/jobSteps/$procedureName/steps/$stepName/summary"
        println "Trying to get the summary, property: $property, jobId: $jobId"
        try {
            summary = getJobProperty(property, jobId)
        } catch (Throwable e) {
            logger.error("Can't retrieve Upper Step Summary from the property: '$property'; check job: " + jobId)
        }
        return summary
    }


}
