package com.cloudbees.pdk.hen.procedures

import groovy.transform.AutoClone
import com.cloudbees.pdk.hen.*
import com.cloudbees.pdk.hen.*

//generated
class EditConfiguration extends Procedure {

    static EditConfiguration create(Plugin plugin) {
        return new EditConfiguration(procedureName: 'EditConfiguration', plugin: plugin, credentials: [
            
            'credential': null,
            
        ])
    }


    EditConfiguration flush() {
        this.flushParams()
        this.contextUser = null
        return this
    }

    EditConfiguration withUser(User user) {
        this.contextUser = user
        return this
    }


    EditConfiguration clone() {
        EditConfiguration cloned = new EditConfiguration(procedureName: 'EditConfiguration', plugin: plugin, credentials: [
                    
                    'credential': null,
                    
                ])
        cloned.parameters = this.parameters.clone()
        return cloned
    }

    //Generated
    
    EditConfiguration checkConnection(String checkConnection) {
        this.addParam('checkConnection', checkConnection)
        return this
    }
    
    
    EditConfiguration config(String config) {
        this.addParam('config', config)
        return this
    }
    
    
    EditConfiguration debug(String debug) {
        this.addParam('debug', debug)
        return this
    }
    
    
    EditConfiguration desc(String desc) {
        this.addParam('desc', desc)
        return this
    }
    
    
    EditConfiguration resourcepool(String resourcepool) {
        this.addParam('resource_pool', resourcepool)
        return this
    }
    
    
    EditConfiguration serviceurl(String serviceurl) {
        this.addParam('service_url', serviceurl)
        return this
    }
    
    
    EditConfiguration workspace(String workspace) {
        this.addParam('workspace', workspace)
        return this
    }
    
    
    
    EditConfiguration credential(String user, String password) {
        this.addCredential('credential', user, password)
        return this
    }

    EditConfiguration credentialReference(String path) {
        this.addCredentialReference('credential', path)
        return this
    }
    
    
}