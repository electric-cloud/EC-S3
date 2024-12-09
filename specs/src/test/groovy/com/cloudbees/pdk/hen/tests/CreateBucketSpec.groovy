package com.cloudbees.pdk.hen.tests

import com.cloudbees.pdk.hen.procedures.CreateBucket
import spock.lang.Shared
import spock.lang.Unroll

class CreateBucketSpec extends PluginTestHelper{
    @Shared
    CreateBucket createBucket = pluginWithoutConfig.createBucket.flush()
    @Shared
    def bucketName = "cbspec"

    def doSetupSpec() {
        configName = configName.toString().replace("configurations","pluginConfigurations")
        bucketName += generator( (('a'..'z')+('0'..'9')).join(), 9 )
    }

    def doCleanupSpec() {
        deleteBucket(bucketName)
        deleteProject("specs-EC-S3")
        deleteProject("spec-configs-project-EC-S3")
    }

    def 'Create bucket'() {
        when:
        def r = createBucket.config(configName)
                .bucketName(bucketName)
                .run()
        then:
        assert r.successful
        assert isBucketExist(bucketName)
    }

    @Unroll
    def 'Negative - Create bucket with #caseDes'() {
        when:
        def r = createBucket.config(configName)
                .bucketName(bucket)
                .run()
        then:
        assert !r.successful
        assert r.summary?: r.jobLog =~ expectedLog
        where:
        caseDes                                 |   bucket          |   expectedLog
        "bucket name contains uppercase"        |   "SPECTEST"      |   "Bucket name should not contain uppercase characters"
        "bucket name contains  special char"    |   "spec_test!"    |   "Bucket name should not contain '_'"
        "bucket name contains space"            |   "spec test"     |   "Bucket name should not contain white space"
        "empty bucket name"                     |   EMPTY           |   "Error : Bucket name is empty"
    }

}
