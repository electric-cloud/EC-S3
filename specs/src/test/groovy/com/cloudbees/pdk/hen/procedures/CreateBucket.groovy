package com.cloudbees.pdk.hen.procedures

import groovy.transform.AutoClone
import com.cloudbees.pdk.hen.*
import com.cloudbees.pdk.hen.*

//generated
class CreateBucket extends Procedure {

    static CreateBucket create(Plugin plugin) {
        return new CreateBucket(procedureName: 'CreateBucket', plugin: plugin, )
    }


    CreateBucket flush() {
        this.flushParams()
        this.contextUser = null
        return this
    }

    CreateBucket withUser(User user) {
        this.contextUser = user
        return this
    }


    CreateBucket clone() {
        CreateBucket cloned = new CreateBucket(procedureName: 'CreateBucket', plugin: plugin, )
        cloned.parameters = this.parameters.clone()
        return cloned
    }

    //Generated
    
    CreateBucket bucketName(String bucketName) {
        this.addParam('bucketName', bucketName)
        return this
    }
    
    
    CreateBucket config(String config) {
        this.addParam('config', config)
        return this
    }
    
    
    
    
}