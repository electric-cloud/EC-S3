package com.cloudbees.pdk.hen.procedures

import groovy.transform.AutoClone
import com.cloudbees.pdk.hen.*
import com.cloudbees.pdk.hen.*

//generated
class ListFolder extends Procedure {

    static ListFolder create(Plugin plugin) {
        return new ListFolder(procedureName: 'ListFolder', plugin: plugin, )
    }


    ListFolder flush() {
        this.flushParams()
        this.contextUser = null
        return this
    }

    ListFolder withUser(User user) {
        this.contextUser = user
        return this
    }


    ListFolder clone() {
        ListFolder cloned = new ListFolder(procedureName: 'ListFolder', plugin: plugin, )
        cloned.parameters = this.parameters.clone()
        return cloned
    }

    //Generated
    
    ListFolder bucketName(String bucketName) {
        this.addParam('bucketName', bucketName)
        return this
    }
    
    
    ListFolder config(String config) {
        this.addParam('config', config)
        return this
    }
    
    
    ListFolder folderName(String folderName) {
        this.addParam('folderName', folderName)
        return this
    }
    
    
    ListFolder includesubfolder(String includesubfolder) {
        this.addParam('include_sub_folder', includesubfolder)
        return this
    }
    
    
    ListFolder propResult(String propResult) {
        this.addParam('propResult', propResult)
        return this
    }
    
    
    
    
}