package com.cloudbees.pdk.hen.procedures

import groovy.transform.AutoClone
import com.cloudbees.pdk.hen.*
import com.cloudbees.pdk.hen.*

//generated
class DownloadObject extends Procedure {

    static DownloadObject create(Plugin plugin) {
        return new DownloadObject(procedureName: 'DownloadObject', plugin: plugin, )
    }


    DownloadObject flush() {
        this.flushParams()
        this.contextUser = null
        return this
    }

    DownloadObject withUser(User user) {
        this.contextUser = user
        return this
    }


    DownloadObject clone() {
        DownloadObject cloned = new DownloadObject(procedureName: 'DownloadObject', plugin: plugin, )
        cloned.parameters = this.parameters.clone()
        return cloned
    }

    //Generated
    
    DownloadObject bucketName(String bucketName) {
        this.addParam('bucketName', bucketName)
        return this
    }
    
    
    DownloadObject config(String config) {
        this.addParam('config', config)
        return this
    }
    
    
    DownloadObject downloadLocation(String downloadLocation) {
        this.addParam('downloadLocation', downloadLocation)
        return this
    }
    
    
    DownloadObject key(String key) {
        this.addParam('key', key)
        return this
    }
    
    
    DownloadObject propResult(String propResult) {
        this.addParam('propResult', propResult)
        return this
    }
    
    
    
    
}