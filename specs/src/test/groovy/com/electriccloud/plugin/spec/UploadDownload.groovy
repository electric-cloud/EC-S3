package com.electriccloud.plugin.spec

import spock.lang.Shared
import spock.lang.Stepwise

@Stepwise
class UploadDownload extends TestHelper {
    @Shared
    def projectName = 'EC-S3 Upload Download'
    @Shared def bucketName = randomize("test-bucket").replaceAll('_', '')

    @Shared def objectKey = randomize('sample')
    @Shared def artifactName = 'ecs3specs:object'
    @Shared def version = '1.0.0'


    def doSetupSpec() {
        deleteProject(projectName)
        dslFile "dsl/procedure.dsl", [
            projectName: projectName,
            procedureName: 'UploadObject',
            params: [
                config: getConfigName(),
                bucketName: bucketName,
                key: '',
                fileToUpload: '',
                access_public: '',
                propResult: ''
            ]
        ]
        dslFile "dsl/procedure.dsl", [
            projectName: projectName,
            procedureName: 'DownloadObject',
            params: [
                config: getConfigName(),
                bucketName: bucketName,
                key: '',
                downloadLocation: '',
                propResult: ''
            ]
        ]
        dslFile "dsl/procedure.dsl", [
            projectName: projectName,
            procedureName: 'ListBucket',
            params: [
                config: getConfigName(),
                propResult: ''
            ]
        ]
        dslFile "dsl/procedure.dsl", [
            projectName: projectName,
            procedureName: 'DeleteObject',
            params: [
                config: getConfigName(),
                key: '',
                bucketName: bucketName
            ]
        ]
        publishArtifact(artifactName, version, "sample")
        retrieveArtifact(artifactName, version, "/tmp")
        helperInstance.createBucket(bucketName)
    }

    def doCleanupSpec() {
        helperInstance.cleanBucket(bucketName)
        helperInstance.deleteBucket(bucketName)
        deleteArtifact(artifactName, version)
    }

    def 'upload object'() {
        given:
        def objectPath = "/tmp/sample"
        when:
        def result = runProcedure projectName, 'UploadObject',
            [key: objectKey, fileToUpload: objectPath, propResult: '/myJob/result']
        then:
        logger.debug(result.logs)
        assert result.outcome == 'success'
        assert result.logs =~ /Uploaded $objectKey successfully/
        assert getJobStepSummary(result.jobId, 'UploadObject', 'uploadObject') =~ /Uploaded $objectKey/
    }

    def 'download object'() {
        when:
        def result = runProcedure projectName, 'DownloadObject', [
            key: objectKey,
            downloadLocation: '/tmp/',
            propResult: '/myJob/result'
        ]
        then:
        logger.info(result.logs)
        assert result.outcome == 'success'
        assert result.logs =~ /Downloaded $objectKey successfully/
        assert getJobStepSummary(result.jobId, 'DownloadObject', 'downloadObject') =~ /Downloaded $objectKey/
    }

    def 'list bucket'() {
        when:
        def result = runProcedure projectName, 'ListBucket',
            [propResult: '/myJob/result']
        then:
        logger.debug(result.logs)
        assert result.outcome == 'success'
        assert getJobStepSummary(result.jobId, 'ListBucket','listBucket') =~ /Listed \d+ buckets/
    }

    def 'delete object'() {
        when:
        def result = runProcedure projectName, 'DeleteObject',
            [key: objectKey]
        then:
        assert result.outcome == 'success'
        assert getJobStepSummary(result.jobId, 'DeleteObject', 'deleteObject') =~ /Object $objectKey deleted successfully/
    }


    def retrieveArtifact(artifactName, artifactVersion, toDirectory) {
        def result = runProcedure """
            project '$projectName', {
                procedure 'RetrieveArtifact', {
                    step 'Retrieve', {
                        command = '''
                        ectool retrieveArtifactVersions --artifactVersionName '$artifactName:$artifactVersion' --toDirectory $toDirectory
                        '''
                    }
                }
            }
            
            runProcedure projectName: '$projectName',
                procedureName: 'RetrieveArtifact'
        """

        assert result.outcome == 'success'
    }
}
