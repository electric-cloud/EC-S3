package com.cloudbees.pdk.hen.tests

import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll

@Stepwise
class DeleteBucketContents extends PluginTestHelper {
    @Shared
    com.cloudbees.pdk.hen.procedures.DeleteBucketContents deleteBucketContents = pluginWithoutConfig.deleteBucketContents
    @Shared
    def bucketName = "cbspec"
    @Shared
    def folderName = "cbspec-folder"

    def doSetupSpec() {
        configName = configName.toString().replace("configurations","pluginConfigurations")
        bucketName += generator( (('a'..'z')+('0'..'9')).join(), 9 )
        createBucketContents()
    }
    def doCleanupSpec() {
        deleteBucket(bucketName)
        deleteProject("specs-EC-S3")
        deleteProject("spec-configs-project-EC-S3")
    }
    def "deleteBucketContents"() {
        when:
        def r = deleteBucketContents.flush()
                .config(configName)
                .bucketName(bucketName)
                .run()
        then:
        assert r.successful
        assert checkBucketContentIsEmpty(bucketName)
    }
    @Unroll
    def "Negative: deleteBucketContents with #caseDes"() {
        when:
        def r = deleteBucketContents.flush().config(configName)
                .bucketName(bucket)
                .run()
        then:
        assert !r.successful
        assert r.jobLog =~ expectedLog

        where:
        caseDes             |   bucket      |   expectedLog
        "invalid bucket"    |   "invalid"   |   "Error : Bucket ${bucket} not present"
        "empty bucket name" |   EMPTY       |   "Error : Bucket name is empty"
    }

    def createBucketContents() {
        createBucket(bucketName)
        def folder = pluginWithoutConfig.createFolder.flush()
                .config(configName)
                .bucketName(bucketName)
                .folderName(folderName)
                .run()
        assert folder.isSuccessful()
        assert isFolderExist(bucketName, folderName)
    }
}
