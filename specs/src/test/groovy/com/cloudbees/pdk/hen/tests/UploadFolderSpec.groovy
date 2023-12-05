package com.cloudbees.pdk.hen.tests

import com.cloudbees.pdk.hen.procedures.UploadFolder
import spock.lang.Shared
import spock.lang.Unroll

class UploadFolderSpec extends PluginTestHelper {
    @Shared
    UploadFolder uploadFolder = pluginWithoutConfig.uploadFolder
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
    def 'Upload folder'() {
        when:
        def r = uploadFolder.flush().config(configName)
                .bucketName(bucketName)
                .key(folderName)
                .folderToUpload("${TEMP_LOCATION}/${FOLDER_NAME}")
                .accesspublic("0")
                .run()
        then:
        assert r.successful
        assert isFolderExist(bucketName, folderName)
    }

    @Unroll
    def 'Negative: Upload folder #caseDes'() {
        when:
        def r = uploadFolder.flush().config(configName)
                .bucketName(bucket)
                .key(key)
                .folderToUpload(folder)
                .run()
        then:
        assert !r.successful
        assert r.jobLog =~ expectedLog
        where:
        caseDes                 |   bucket      |   key         |   folder                                |   expectedLog
        "invalid bucket name"   |   "invalid"   |  folderName   |    "${TEMP_LOCATION}/${FOLDER_NAME}"    |   "Error : Bucket ${bucket} not present"
        "invalid folder"        |   bucketName  |  folderName   |    "/invalid/${TEMP_LOCATION}"          |   "Error : Folder ${folder} does not exists"
        "empty bucket name"     |   EMPTY       |  folderName   |    "${TEMP_LOCATION}/${FOLDER_NAME}"    |   "Error : Bucket name is empty"
        "empty folder name"     |   "invalid"   |  folderName   |    EMPTY                                |   "Error : File to upload is empty"
    }
}
