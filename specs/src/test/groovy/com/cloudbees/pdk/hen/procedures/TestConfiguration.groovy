package com.cloudbees.pdk.hen.procedures

import groovy.transform.AutoClone
import com.cloudbees.pdk.hen.*
import com.cloudbees.pdk.hen.*

//generated
class TestConfiguration extends Procedure {

    static TestConfiguration create(Plugin plugin) {
        return new TestConfiguration(procedureName: 'TestConfiguration', plugin: plugin, credentials: [
            
            'credential': null,
            
        ])
    }


    TestConfiguration flush() {
        this.flushParams()
        this.contextUser = null
        return this
    }

    TestConfiguration withUser(User user) {
        this.contextUser = user
        return this
    }


    TestConfiguration clone() {
        TestConfiguration cloned = new TestConfiguration(procedureName: 'TestConfiguration', plugin: plugin, credentials: [
                    
                    'credential': null,
                    
                ])
        cloned.parameters = this.parameters.clone()
        return cloned
    }

    //Generated
    
    TestConfiguration config(String config) {
        this.addParam('config', config)
        return this
    }
    
    
    TestConfiguration debug(String debug) {
        this.addParam('debug', debug)
        return this
    }
    
    
    TestConfiguration resourcepool(String resourcepool) {
        this.addParam('resource_pool', resourcepool)
        return this
    }
    
    
    TestConfiguration serviceurl(String serviceurl) {
        this.addParam('service_url', serviceurl)
        return this
    }
    
    
    TestConfiguration workspace(String workspace) {
        this.addParam('workspace', workspace)
        return this
    }
    
    
    
    TestConfiguration credential(String user, String password) {
        this.addCredential('credential', user, password)
        return this
    }

    TestConfiguration credentialReference(String path) {
        this.addCredentialReference('credential', path)
        return this
    }
    
    
}