package com.cloudbees.pdk.hen.tests

import com.cloudbees.pdk.hen.procedures.UploadObject
import spock.lang.Shared
import spock.lang.Unroll

class UploadObjectSpec extends PluginTestHelper {
    @Shared
    UploadObject uploadObject = pluginWithoutConfig.uploadObject
    @Shared
    def bucketName = "cbspec"
    @Shared
    def folderName = "cbspec-folder"

    def doSetupSpec() {
        configName = configName.toString().replace("configurations","pluginConfigurations")
        bucketName += generator( (('a'..'z')+('0'..'9')).join(), 9 )
        createBucket(bucketName)
        createFile()
    }
    def doCleanupSpec() {
        deleteBucket(bucketName)
        removeFolder()
        deleteProject("specs-EC-S3")
        deleteProject("spec-configs-project-EC-S3")
    }
    def 'Upload object'() {
        when:
        def r = uploadObject.config(configName)
                .bucketName(bucketName)
                .key(FILE_NAME)
                .fileToUpload("${TEMP_LOCATION}/${FILE_NAME}")
                .accesspublic("0")
                .run()
        then:
        assert r.successful
        assert isObjectExist(bucketName, FILE_NAME)
    }

    @Unroll
    def 'Negative: Upload object #caseDes'() {
        when:
        def r = uploadObject.config(configName)
                .bucketName(bucket)
                .key(key)
                .fileToUpload(file)
                .run()
        then:
        assert !r.successful
        assert r.jobLog =~ expectedLog
        where:
        caseDes                 |   bucket      |   key         |   file                                |   expectedLog
        "invalid bucket name"   |   "invalid"   |   FILE_NAME   |    "${TEMP_LOCATION}/${FILE_NAME}"    |   "Error : Bucket ${bucket} not present"
        "invalid file"          |   bucketName  |   FILE_NAME   |    "${TEMP_LOCATION}/invalid"         |   "Error : File ${file} does not exists"
        "empty bucket name"     |   EMPTY       |   FILE_NAME   |    "${TEMP_LOCATION}/${FILE_NAME}"    |   "Error : Bucket name is empty"
        "empty file name"       |   "invalid"   |   FILE_NAME   |    EMPTY                              |   "Error : File to upload is empty"
    }


}
