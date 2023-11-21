package com.cloudbees.pdk.hen.procedures

import groovy.transform.AutoClone
import com.cloudbees.pdk.hen.*
import com.cloudbees.pdk.hen.*

//generated
class CreateFolder extends Procedure {

    static CreateFolder create(Plugin plugin) {
        return new CreateFolder(procedureName: 'CreateFolder', plugin: plugin, )
    }


    CreateFolder flush() {
        this.flushParams()
        this.contextUser = null
        return this
    }

    CreateFolder withUser(User user) {
        this.contextUser = user
        return this
    }


    CreateFolder clone() {
        CreateFolder cloned = new CreateFolder(procedureName: 'CreateFolder', plugin: plugin, )
        cloned.parameters = this.parameters.clone()
        return cloned
    }

    //Generated
    
    CreateFolder bucketName(String bucketName) {
        this.addParam('bucketName', bucketName)
        return this
    }
    
    
    CreateFolder config(String config) {
        this.addParam('config', config)
        return this
    }
    
    
    CreateFolder folderName(String folderName) {
        this.addParam('folderName', folderName)
        return this
    }
    
    
    
    
}