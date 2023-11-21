package com.cloudbees.pdk.hen.procedures

import groovy.transform.AutoClone
import com.cloudbees.pdk.hen.*
import com.cloudbees.pdk.hen.*

//generated
class DownloadFolder extends Procedure {

    static DownloadFolder create(Plugin plugin) {
        return new DownloadFolder(procedureName: 'DownloadFolder', plugin: plugin, )
    }


    DownloadFolder flush() {
        this.flushParams()
        this.contextUser = null
        return this
    }

    DownloadFolder withUser(User user) {
        this.contextUser = user
        return this
    }


    DownloadFolder clone() {
        DownloadFolder cloned = new DownloadFolder(procedureName: 'DownloadFolder', plugin: plugin, )
        cloned.parameters = this.parameters.clone()
        return cloned
    }

    //Generated
    
    DownloadFolder bucketName(String bucketName) {
        this.addParam('bucketName', bucketName)
        return this
    }
    
    
    DownloadFolder config(String config) {
        this.addParam('config', config)
        return this
    }
    
    
    DownloadFolder downloadLocation(String downloadLocation) {
        this.addParam('downloadLocation', downloadLocation)
        return this
    }
    
    
    DownloadFolder key(String key) {
        this.addParam('key', key)
        return this
    }
    
    
    DownloadFolder propResult(String propResult) {
        this.addParam('propResult', propResult)
        return this
    }
    
    
    
    
}