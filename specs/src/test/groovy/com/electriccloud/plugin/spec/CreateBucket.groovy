package com.electriccloud.plugin.spec

import spock.lang.Shared
import spock.lang.Stepwise

@Stepwise
class CreateBucket extends TestHelper {

    @Shared
    def projectName = 'EC-S3 CreateBucket Spec'
    @Shared
    def procedureName = 'CreateBucket'

    def doSetupSpec() {
        createConfig(false)
        dslFile "dsl/procedure.dsl", [
            projectName: projectName,
            procedureName: procedureName,
            params: [
                config: getConfigName(),
                bucketName: ''
            ]
        ]
    }


    def 'create bucket'() {
        given:
        def bucketName = randomize('test-bucket')
        bucketName = bucketName.replaceAll('_', '')
        when:
        def result = runProcedure(projectName, procedureName, [
            bucketName: bucketName
        ])
        then:
        logger.debug(result.logs)
        assert result.outcome == 'success'
        assert result.logs =~ /Bucket $bucketName created successfully/
        cleanup:
        helperInstance.deleteBucket(bucketName)
    }

}
