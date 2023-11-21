package com.cloudbees.pdk.hen.procedures

import groovy.transform.AutoClone
import com.cloudbees.pdk.hen.*
import com.cloudbees.pdk.hen.*

//generated
class UploadObject extends Procedure {

    static UploadObject create(Plugin plugin) {
        return new UploadObject(procedureName: 'UploadObject', plugin: plugin, )
    }


    UploadObject flush() {
        this.flushParams()
        this.contextUser = null
        return this
    }

    UploadObject withUser(User user) {
        this.contextUser = user
        return this
    }


    UploadObject clone() {
        UploadObject cloned = new UploadObject(procedureName: 'UploadObject', plugin: plugin, )
        cloned.parameters = this.parameters.clone()
        return cloned
    }

    //Generated
    
    UploadObject accesspublic(String accesspublic) {
        this.addParam('access_public', accesspublic)
        return this
    }
    
    
    UploadObject bucketName(String bucketName) {
        this.addParam('bucketName', bucketName)
        return this
    }
    
    
    UploadObject config(String config) {
        this.addParam('config', config)
        return this
    }
    
    
    UploadObject fileToUpload(String fileToUpload) {
        this.addParam('fileToUpload', fileToUpload)
        return this
    }
    
    
    UploadObject key(String key) {
        this.addParam('key', key)
        return this
    }
    
    
    UploadObject propResult(String propResult) {
        this.addParam('propResult', propResult)
        return this
    }
    
    
    
    
}