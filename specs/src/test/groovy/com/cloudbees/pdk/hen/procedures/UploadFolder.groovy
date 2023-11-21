package com.cloudbees.pdk.hen.procedures

import groovy.transform.AutoClone
import com.cloudbees.pdk.hen.*
import com.cloudbees.pdk.hen.*

//generated
class UploadFolder extends Procedure {

    static UploadFolder create(Plugin plugin) {
        return new UploadFolder(procedureName: 'UploadFolder', plugin: plugin, )
    }


    UploadFolder flush() {
        this.flushParams()
        this.contextUser = null
        return this
    }

    UploadFolder withUser(User user) {
        this.contextUser = user
        return this
    }


    UploadFolder clone() {
        UploadFolder cloned = new UploadFolder(procedureName: 'UploadFolder', plugin: plugin, )
        cloned.parameters = this.parameters.clone()
        return cloned
    }

    //Generated
    
    UploadFolder accesspublic(String accesspublic) {
        this.addParam('access_public', accesspublic)
        return this
    }
    
    
    UploadFolder bucketName(String bucketName) {
        this.addParam('bucketName', bucketName)
        return this
    }
    
    
    UploadFolder config(String config) {
        this.addParam('config', config)
        return this
    }
    
    
    UploadFolder folderToUpload(String folderToUpload) {
        this.addParam('folderToUpload', folderToUpload)
        return this
    }
    
    
    UploadFolder key(String key) {
        this.addParam('key', key)
        return this
    }
    
    
    UploadFolder propResult(String propResult) {
        this.addParam('propResult', propResult)
        return this
    }
    
    
    
    
}