package com.cloudbees.pdk.hen.tests

import com.cloudbees.pdk.hen.procedures.DownloadFolder
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll

@Stepwise
class DownloadFolderSpec extends PluginTestHelper {
    @Shared
    DownloadFolder downloadFolder = pluginWithoutConfig.downloadFolder
    @Shared
    def bucketName = "cbspec"
    @Shared
    def folderName = "cbspec-folder"

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

    def 'Download folder'() {
        when:
        def r = downloadFolder.flush().config(configName)
                .bucketName(bucketName)
                .key(folderName)
                .downloadLocation("${TEMP_LOCATION}/${FOLDER_NAME}/")
                .run()
        then:
        assert r.successful
        assert checkFolderExistenceInResource("${TEMP_LOCATION}/${FOLDER_NAME}/${folderName}")
    }
    @Unroll
    def 'Negative: Download folder with #caseDes'() {
        when:
        def r = downloadFolder.flush().config(configName)
                .bucketName(bucket)
                .key(key)
                .downloadLocation(downloadLoc)
                .run()
        then:
        assert !r.successful
        assert r.jobLog =~ expectedLog
        where:
        caseDes                         |   bucket      |   key         |   downloadLoc                         |   expectedLog
        "invalid bucket"                |   "invalid"   |   folderName  |   "${TEMP_LOCATION}/${FOLDER_NAME}"   |   "Error : Bucket ${bucket} not present"
        "invalid download location"     |   bucketName  |   folderName  |   "/invalid/${FOLDER_NAME}"           |   "Error : Can not write to ${downloadLoc}"
        "empty bucket name"             |   EMPTY       |   folderName  |   "${TEMP_LOCATION}/${FOLDER_NAME}"   |   "Error : Bucket name is empty"
        "empty download loc"            |   bucketName  |   folderName  |   EMPTY                               |   "Error : Download location is empty"
    }

    def createBucketContents() {
        createBucket(bucketName)
        def folder = pluginWithoutConfig.uploadFolder.flush()
                .config(configName)
                .bucketName(bucketName)
                .key(folderName)
                .folderToUpload("${TEMP_LOCATION}/${FOLDER_NAME}")
                .accesspublic("0")
                .run()
        assert folder.isSuccessful()
        assert isFolderExist(bucketName, folderName)
    }
}
