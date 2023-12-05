package com.cloudbees.pdk.hen.tests

import com.cloudbees.pdk.hen.procedures.ListBucket
import spock.lang.Shared

class ListBucketSpecs extends PluginTestHelper {
    @Shared
    ListBucket listBucket = pluginWithoutConfig.listBucket
    @Shared
    def bucketName = "cbspec"

    def doSetupSpec() {
        configName = configName.toString().replace("configurations","pluginConfigurations")
        bucketName += generator( (('a'..'z')+('0'..'9')).join(), 9 )
        createBucket(bucketName)
    }
    def doCleanupSpec() {
        deleteBucket(bucketName)
        deleteProject("specs-EC-S3")
        deleteProject("spec-configs-project-EC-S3")
    }
    def 'List buckets'() {
        when:
        def r = listBucket.flush().config(configName)
                .run()
        then:
        assert r.successful
        assert r.jobLog =~ bucketName
        def summary = getJobProperty('/myJob/jobSteps/listBucket/summary', r.jobId)
        def bucketCountFormAWS = getAllBuckets()
        assert summary =~ "Listed ${bucketCountFormAWS} buckets"
    }
}
