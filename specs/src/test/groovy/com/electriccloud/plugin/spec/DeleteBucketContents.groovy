package com.electriccloud.plugin.spec

import spock.lang.Shared

class DeleteBucketContents extends TestHelper {

    @Shared
    def projectName = 'EC-S3 DeleteBucketContents Spec'
    @Shared
    def procedureName = 'DeleteBucketContents'

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
            ]
        ]
        helperInstance.createBucket(bucketName)
    }

    def doCleanupSpec() {
        helperInstance.cleanBucket(bucketName)
        helperInstance.deleteBucket(bucketName)
    }

    def 'delete bucket contents'() {
        when:
        def result = runProcedure(projectName, procedureName, [
            bucketName: bucketName,
        ])
        then:
        logger.debug(result.logs)
        assert result.outcome == 'success'
    }

}

