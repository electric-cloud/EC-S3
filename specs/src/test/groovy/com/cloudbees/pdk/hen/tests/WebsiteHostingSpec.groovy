package com.cloudbees.pdk.hen.tests

import com.cloudbees.pdk.hen.procedures.WebsiteHosting
import spock.lang.Shared
import spock.lang.Unroll

class WebsiteHostingSpec  extends PluginTestHelper {
    @Shared
    WebsiteHosting websiteHosting = pluginWithoutConfig.websiteHosting
    @Shared
    def bucketName = "cbspec"

    def doSetupSpec() {
        configName = configName.toString().replace("configurations","pluginConfigurations")
        bucketName += generator( (('a'..'z')+('0'..'9')).join(), 9 )
        createFileForWebsiteHosting()
        createBucket(bucketName)
    }
    def doCleanupSpec() {
        deleteBucket(bucketName)
        removeFolder()
        deleteProject("specs-EC-S3")
        deleteProject("spec-configs-project-EC-S3")
    }
    def 'Website hosting'() {
        when:
        def r = websiteHosting.flush().config(configName)
                .bucketName(bucketName)
                .websitehosting("1")
                .indexDoc(INDEX_FILE)
                .errorDoc(ERROR_FILE)
                .run()
        then:
        assert r.successful
        def summary = getJobProperty('/myJob/jobSteps/websiteHosting/summary', r.jobId)
        assert summary =~ "Website on ${bucketName} changed successfully"
    }
    @Unroll
    def 'Negative: Website hosting #caseDes'() {
        when:
        def r = websiteHosting.flush().config(configName)
                .bucketName(bucket)
                .websitehosting("1")
                .indexDoc(indesDoc)
                .errorDoc(errorDoc)
                .run()
        then:
        assert !r.successful
        assert r.jobLog =~ expectedLog
        where:
        caseDes                     |   bucket      |   indesDoc    |   errorDoc    |   expectedLog
        "invalid bucket"            |   "invalid"   |   INDEX_FILE  |   ERROR_FILE  |   "Error : Bucket ${bucket} not present"
        "invalid index file path"   |   bucketName  |   "/invalid"  |   ERROR_FILE  |   "Error Message:    The IndexDocument Suffix is not well formed"
        "empty bucket"              |   EMPTY       |   INDEX_FILE  |   ERROR_FILE  |   "Error : Bucket name is empty"
        "empty index"               |   bucketName  |   EMPTY       |   ERROR_FILE  |   "Error Message:    The IndexDocument Suffix is not well formed"
    }
}
