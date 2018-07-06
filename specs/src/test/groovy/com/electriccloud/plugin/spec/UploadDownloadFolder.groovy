package com.electriccloud.plugin.spec

import spock.lang.Shared
import spock.lang.Stepwise

@Stepwise
class UploadDownloadFolder extends TestHelper {
    @Shared
    def projectName = 'EC-S3 Upload Download Folder Spec'
    @Shared def bucketName = randomize("test-bucket").replaceAll('_', '')

    @Shared def objectKey = randomize('sample')
    @Shared def artifactName = 'ecs3specs:folder'
    @Shared def version = '1.0.0'


    def doSetupSpec() {
        deleteProject(projectName)
        dslFile "dsl/procedure.dsl", [
            projectName: projectName,
            procedureName: 'UploadFolder',
            params: [
                config: getConfigName(),
                bucketName: bucketName,
                key: '',
                folderToUpload: '',
                access_public: '',
                propResult: ''
            ]
        ]
        dslFile "dsl/procedure.dsl", [
            projectName: projectName,
            procedureName: 'DownloadFolder',
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
            procedureName: 'ListFolder',
            params: [
                config: getConfigName(),
                propResult: '',
                folderName: '',
                bucketName: bucketName,
                include_sub_folder: ''
            ]
        ]

        publishArtifact(artifactName, version, "test_directory/")
        retrieveArtifact(artifactName, version, "/tmp/test_directory")
        helperInstance.createBucket(bucketName)
    }

    def doCleanupSpec() {
        helperInstance.cleanBucket(bucketName)
        helperInstance.deleteBucket(bucketName)
    }

    def 'upload folder'() {
        given:
        def objectPath = "/tmp/test_directory"
        when:
        def result = runProcedure projectName, 'UploadFolder',
            [key: objectKey, folderToUpload: objectPath, propResult: '/myJob/result']
        then:
        logger.debug(result.logs)
        assert result.outcome == 'success'
        assert getJobStepSummary(result.jobId, 'UploadFolder', 'uploadFolder') =~ /Uploaded $objectPath/
    }

    def 'download folder'() {
        given:
        def dest = '/tmp/download_folder'
        dsl """
        project '$projectName', {
            procedure 'CreateFolder', {
                step 'CreateFolder', {
                    shell = 'ec-groovy'
                    command = 'new File($dest).mkdir()'
                }
            }
        }
        """
        when:
        def result = runProcedure projectName, 'DownloadFolder',
            [key: objectKey, downloadLocation: dest, propResult: '/myJob/result', key: objectKey]
        then:
        logger.debug(result.logs)
        assert result.outcome == 'success'
        assert result.logs =~ /Downloaded $bucketName/
        assert getJobStepSummary(result.jobId, 'DownloadFolder', 'downloadFolder') =~ /Downloaded $bucketName/
    }

    def 'list folder'() {
        when:
        def result = runProcedure projectName, 'ListFolder',
            [folderName: objectKey, propResult: '/myJob/result']
        then:
        logger.debug(result.logs)
        assert result.outcome == 'success'
        assert getJobStepSummary(result.jobId, 'ListFolder', 'listFolder') =~ /Listed \d+ objects/
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
