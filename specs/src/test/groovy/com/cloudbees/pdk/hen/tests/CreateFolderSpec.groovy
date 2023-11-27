package com.cloudbees.pdk.hen.tests

import com.cloudbees.pdk.hen.procedures.CreateFolder
import spock.lang.Shared
import spock.lang.Stepwise
import spock.lang.Unroll

@Stepwise
class CreateFolderSpec  extends PluginTestHelper {

    @Shared
    CreateFolder createFolder = pluginWithoutConfig.createFolder
    @Shared
    def bucketName = "cbspec"
    @Shared
    def folderName = "cbspec-folder"

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

    def 'Create folder'() {
        when:
        def r = createFolder.config(configName)
                .bucketName(bucketName)
                .folderName(folderName)
                .run()
        then:
        assert r.successful
    }

    @Unroll
    def 'Negative - Create folder with #caseDes'() {
        when:
        def r = createFolder.flush().config(configName)
                .bucketName(bucket)
                .folderName(folder)
                .run()
        then:
        assert !r.successful
        assert r.jobLog =~ expectedLog
        where:
        caseDes                 |   bucket          |   folder          |   expectedLog
        "invalid bucket"        |   "invalid"       |   folderName      |   "Error : Bucket ${bucket} not present"
        "empty bucket"          |    EMPTY          |   folderName      |   "Error : Bucket name is empty"
        "empty folder name"     |    bucketName     |   EMPTY           |   "Error : Folder name is empty"
    }
}
