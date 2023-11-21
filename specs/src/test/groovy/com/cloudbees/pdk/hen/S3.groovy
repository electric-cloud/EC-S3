package com.cloudbees.pdk.hen

import com.cloudbees.pdk.hen.procedures.*
import com.cloudbees.pdk.hen.Plugin

import static com.cloudbees.pdk.hen.Utils.env

class S3 extends Plugin {

    static S3 create() {
        S3 plugin = new S3(name: 'EC-S3')
        plugin.configure(plugin.config)
        return plugin
    }
    static S3 createWithoutConfig() {
        S3 plugin = new S3(name: 'EC-S3')
        return plugin
    }

    //user-defined after boilerplate was generated, default parameters setup
    S3Config config = S3Config
        .create(this)
        .serviceurl(System.getenv("SERVICE_URL"))
        .credential(System.getenv("AWS_ACCESS_KEY_ID"), System.getenv("AWS_SECRET_ACCESS_KEY"))
        .resourcepool("default")
        .workspace("default")
        .debug("0")
        //.parameter(value) add parameters here


    CreateBucket createBucket = CreateBucket.create(this)

    CreateFolder createFolder = CreateFolder.create(this)

    DeleteBucketContents deleteBucketContents = DeleteBucketContents.create(this)

    DeleteObject deleteObject = DeleteObject.create(this)

    DownloadFolder downloadFolder = DownloadFolder.create(this)

    DownloadObject downloadObject = DownloadObject.create(this)

    EditConfiguration editConfiguration = EditConfiguration.create(this)

    ListBucket listBucket = ListBucket.create(this)

    ListFolder listFolder = ListFolder.create(this)

    TestConfiguration testConfiguration = TestConfiguration.create(this)

    UploadFolder uploadFolder = UploadFolder.create(this)

    UploadObject uploadObject = UploadObject.create(this)

    WebsiteHosting websiteHosting = WebsiteHosting.create(this)

}