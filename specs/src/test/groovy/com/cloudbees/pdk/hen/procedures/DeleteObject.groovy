package com.cloudbees.pdk.hen.procedures

import groovy.transform.AutoClone
import com.cloudbees.pdk.hen.*
import com.cloudbees.pdk.hen.*

//generated
class DeleteObject extends Procedure {

    static DeleteObject create(Plugin plugin) {
        return new DeleteObject(procedureName: 'DeleteObject', plugin: plugin, )
    }


    DeleteObject flush() {
        this.flushParams()
        this.contextUser = null
        return this
    }

    DeleteObject withUser(User user) {
        this.contextUser = user
        return this
    }


    DeleteObject clone() {
        DeleteObject cloned = new DeleteObject(procedureName: 'DeleteObject', plugin: plugin, )
        cloned.parameters = this.parameters.clone()
        return cloned
    }

    //Generated
    
    DeleteObject bucketName(String bucketName) {
        this.addParam('bucketName', bucketName)
        return this
    }
    
    
    DeleteObject config(String config) {
        this.addParam('config', config)
        return this
    }
    
    
    DeleteObject key(String key) {
        this.addParam('key', key)
        return this
    }
    
    
    
    
}