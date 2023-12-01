package com.cloudbees.pdk.hen.tests

import com.cloudbees.pdk.hen.procedures.DownloadObject
import spock.lang.Shared
import spock.lang.Unroll

class DownloadObjectSpec extends PluginTestHelper {
    @Shared
    DownloadObject downloadObject = pluginWithoutConfig.downloadObject
    @Shared
    def bucketName = "cbspec"

    def doSetupSpec() {
        configName = configName.toString().replace("configurations","pluginConfigurations")
        bucketName += generator( (('a'..'z')+('0'..'9')).join(), 9 )
        createFile()
        createBucketContents()
    }
    def doCleanupSpec() {
        deleteBucket(bucketName)
        removeFolder()
        deleteProject("specs-EC-S3")
        deleteProject("spec-configs-project-EC-S3")
    }

    def 'Download object'() {
        when:
        def r = downloadObject.flush().config(configName)
                .bucketName(bucketName)
                .key(FILE_NAME)
                .downloadLocation("${TEMP_LOCATION}/specFolder")
                .run()
        then:
        assert r.successful
        assert checkFileExistenceInResource("${TEMP_LOCATION}/specFolder/${FILE_NAME}")
    }
    @Unroll
    def 'Negative: Download object #caseDes'() {
        when:
        def r = downloadObject.flush().config(configName)
                .bucketName(bucket)
                .key(key)
                .downloadLocation(downloadLoc)
                .run()
        then:
        assert !r.successful
        assert r.jobLog =~ expectedLog
        where:
        caseDes                     |   bucket      |   key         |   downloadLoc                     |   expectedLog
        "invalid bucket"            |   "invalid"   |   FILE_NAME   |   "${TEMP_LOCATION}/specFolder"   |   "Error : Bucket ${bucket} not present"
        "invalid key"               |   bucketName  |   "invalid"   |   "${TEMP_LOCATION}/specFolder"   |   "Error : Object ${key} not present or its not an object or you do not have permissions to download it"
        "invalid download location" |   bucketName  |   FILE_NAME   |   "/invalid/loc"                  |   "Error : Can not write to ${downloadLoc}"

        "empty bucket name"         |   EMPTY       |   FILE_NAME   |   "${TEMP_LOCATION}/specFolder"   |   "Error : Bucket name is empty"
        "empty key"                 |   bucketName  |   EMPTY       |   "${TEMP_LOCATION}/specFolder"   |   "Error : Key is empty"
        "empty download location"   |   bucketName  |   FILE_NAME   |   EMPTY                           |   "Error : Download location is empty"
    }


    def createBucketContents() {
        createBucket(bucketName)
        def file = pluginWithoutConfig.uploadObject.flush()
                .config(configName)
                .bucketName(bucketName)
                .key(FILE_NAME)
                .fileToUpload("${TEMP_LOCATION}/${FILE_NAME}")
                .accesspublic("0")
                .run()
        assert file.isSuccessful()
        assert isObjectExist(bucketName, FILE_NAME)
    }
}
