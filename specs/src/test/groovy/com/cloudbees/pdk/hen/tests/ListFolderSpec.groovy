package com.cloudbees.pdk.hen.tests

import com.cloudbees.pdk.hen.procedures.ListFolder
import spock.lang.Shared
import spock.lang.Unroll

class ListFolderSpec extends PluginTestHelper {
    @Shared
    ListFolder listFolder = pluginWithoutConfig.listFolder
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
    def 'List folder'() {
        when:
        def r = listFolder.flush().config(configName)
                .bucketName(bucketName)
                .folderName(folderName)
                .includesubfolder("1")
                .run()
        then:
        assert r.successful
        assert r.jobLog =~ "${folderName}/${FILE_NAME}"
        def summary = getJobProperty('/myJob/jobSteps/listFolder/summary', r.jobId)
        def folderCountFormAWS = getAllFolders(bucketName, folderName)
        assert summary =~ "Listed ${folderCountFormAWS} objects"
    }
    @Unroll
    def 'Negative: List folder #caseDes'() {
        when:
        def r = listFolder.flush().config(configName)
                .bucketName(bucket)
                .folderName(folder)
                .includesubfolder("1")
                .run()
        then:
        assert !r.successful
        assert r.jobLog =~ expectedLog
        where:
        caseDes             |   bucket      |   folder      |   expectedLog
        "invalid bucket"    |   "invalid"   |  folderName   |   "Error : Bucket ${bucket} not present"
        "empty bucket name" |   EMPTY       |   folderName  |   "Error : Bucket name is empty"
    }

    def createBucketContents() {
        createBucket(bucketName)
        def r = pluginWithoutConfig.uploadFolder.flush().config(configName)
                .bucketName(bucketName)
                .key(folderName)
                .folderToUpload("${TEMP_LOCATION}/${FOLDER_NAME}")
                .accesspublic("0")
                .run()
        assert r.isSuccessful()
        assert isFolderExist(bucketName, folderName)
    }
}
