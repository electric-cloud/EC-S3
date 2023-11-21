package com.cloudbees.pdk.hen.procedures

import groovy.transform.AutoClone
import com.cloudbees.pdk.hen.*
import com.cloudbees.pdk.hen.*

//generated
class WebsiteHosting extends Procedure {

    static WebsiteHosting create(Plugin plugin) {
        return new WebsiteHosting(procedureName: 'WebsiteHosting', plugin: plugin, )
    }


    WebsiteHosting flush() {
        this.flushParams()
        this.contextUser = null
        return this
    }

    WebsiteHosting withUser(User user) {
        this.contextUser = user
        return this
    }


    WebsiteHosting clone() {
        WebsiteHosting cloned = new WebsiteHosting(procedureName: 'WebsiteHosting', plugin: plugin, )
        cloned.parameters = this.parameters.clone()
        return cloned
    }

    //Generated
    
    WebsiteHosting bucketName(String bucketName) {
        this.addParam('bucketName', bucketName)
        return this
    }
    
    
    WebsiteHosting config(String config) {
        this.addParam('config', config)
        return this
    }
    
    
    WebsiteHosting errorDoc(String errorDoc) {
        this.addParam('errorDoc', errorDoc)
        return this
    }
    
    
    WebsiteHosting indexDoc(String indexDoc) {
        this.addParam('indexDoc', indexDoc)
        return this
    }
    
    
    WebsiteHosting propResult(String propResult) {
        this.addParam('propResult', propResult)
        return this
    }
    
    
    WebsiteHosting websitehosting(String websitehosting) {
        this.addParam('website_hosting', websitehosting)
        return this
    }
    
    
    
    
}