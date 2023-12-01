package com.cloudbees.pdk.hen.tests

import com.cloudbees.pdk.hen.procedures.DeleteObject
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll

@Stepwise
class DeleteObjectSpec extends PluginTestHelper {
    @Shared
    DeleteObject deleteObject = pluginWithoutConfig.deleteObject
    @Shared
    def bucketName = "cbspec"

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
    def 'Delete object'() {
        when:
        def r = deleteObject.config(configName)
                .bucketName(bucketName)
                .key(FILE_NAME)
                .run()
        then:
        assert r.successful
        assert !isObjectExist(bucketName, FILE_NAME)
    }
    @Unroll
    def 'Negative: Delete object with #caseDes'() {
        when:
        def r = deleteObject.flush().config(configName)
                .bucketName(bucket)
                .key(key)
                .run()
        then:
        assert !r.successful
        assert r.jobLog =~ expectedLog
        where:
        caseDes             |   bucket      |   key         |   expectedLog
        "invalid bucket"    |   "invalid"   |   FILE_NAME   |   "Error : Bucket ${bucket} not present"
        "invalid key"       |   bucketName  |   "invalid"   |   "Error : Object ${key} not present or its not an object or you do not have permissions to delete it"
        "empty bucket name" |   EMPTY       |   FILE_NAME   |   "Error : Bucket name is empty"
        "empty key"         |   bucketName  |   EMPTY       |   "Error : Key is empty"
    }

    def createBucketContents() {
        createBucket(bucketName)
        createFile()
        def r = pluginWithoutConfig.uploadObject.config(configName)
                .bucketName(bucketName)
                .key(FILE_NAME)
                .fileToUpload("${TEMP_LOCATION}/${FILE_NAME}")
                .accesspublic("0")
                .run()
        assert r.isSuccessful()
        assert isObjectExist(bucketName, FILE_NAME)
    }
}
