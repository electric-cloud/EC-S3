package com.electriccloud.plugin.spec

import spock.lang.Shared

class CreateFolder extends TestHelper {

    @Shared
    def projectName = 'EC-S3 CreateFolder Spec'
    @Shared
    def procedureName = 'CreateFolder'

    @Shared
    def bucketName = randomize('test-bucket').replaceAll('_', '')

    def doSetupSpec() {
        createConfig(false)
        dslFile "dsl/procedure.dsl", [
            projectName: projectName,
            procedureName: procedureName,
            params: [
                config: getConfigName(),
                bucketName: '',
                folderName: ''
            ]
        ]
        helperInstance.createBucket(bucketName)
    }

    def doCleanupSpec() {
        helperInstance.cleanBucket(bucketName)
        helperInstance.deleteBucket(bucketName)
    }

    def 'create folder'() {
        given:
        def folderName = randomize('test-folder')
        when:
        def result = runProcedure(projectName, procedureName, [
            bucketName: bucketName,
            folderName: folderName
        ])
        then:
        logger.debug(result.logs)
        assert result.outcome == 'success'
    }

}

