package com.cloudbees.pdk.hen.procedures

import groovy.transform.AutoClone
import com.cloudbees.pdk.hen.*
import com.cloudbees.pdk.hen.*

//generated
class DeleteBucketContents extends Procedure {

    static DeleteBucketContents create(Plugin plugin) {
        return new DeleteBucketContents(procedureName: 'DeleteBucketContents', plugin: plugin, )
    }


    DeleteBucketContents flush() {
        this.flushParams()
        this.contextUser = null
        return this
    }

    DeleteBucketContents withUser(User user) {
        this.contextUser = user
        return this
    }


    DeleteBucketContents clone() {
        DeleteBucketContents cloned = new DeleteBucketContents(procedureName: 'DeleteBucketContents', plugin: plugin, )
        cloned.parameters = this.parameters.clone()
        return cloned
    }

    //Generated
    
    DeleteBucketContents bucketName(String bucketName) {
        this.addParam('bucketName', bucketName)
        return this
    }
    
    
    DeleteBucketContents config(String config) {
        this.addParam('config', config)
        return this
    }
    
    
    
    
}