package com.cloudbees.pdk.hen.procedures

import groovy.transform.AutoClone
import com.cloudbees.pdk.hen.*
import com.cloudbees.pdk.hen.*

//generated
class ListBucket extends Procedure {

    static ListBucket create(Plugin plugin) {
        return new ListBucket(procedureName: 'ListBucket', plugin: plugin, )
    }


    ListBucket flush() {
        this.flushParams()
        this.contextUser = null
        return this
    }

    ListBucket withUser(User user) {
        this.contextUser = user
        return this
    }


    ListBucket clone() {
        ListBucket cloned = new ListBucket(procedureName: 'ListBucket', plugin: plugin, )
        cloned.parameters = this.parameters.clone()
        return cloned
    }

    //Generated
    
    ListBucket config(String config) {
        this.addParam('config', config)
        return this
    }
    
    
    ListBucket propResult(String propResult) {
        this.addParam('propResult', propResult)
        return this
    }
    
    
    
    
}