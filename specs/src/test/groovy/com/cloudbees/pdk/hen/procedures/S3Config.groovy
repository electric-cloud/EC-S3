package com.cloudbees.pdk.hen.procedures

import groovy.transform.AutoClone
import com.cloudbees.pdk.hen.*
import com.cloudbees.pdk.hen.*

//generated
class S3Config extends Procedure {

    static S3Config create(Plugin plugin) {
        return new S3Config(procedureName: 'CreateConfiguration', plugin: plugin, credentials: [
            
            'credential': null,
            
        ])
    }


    S3Config flush() {
        this.flushParams()
        this.contextUser = null
        return this
    }

    S3Config withUser(User user) {
        this.contextUser = user
        return this
    }


    S3Config clone() {
        S3Config cloned = new S3Config(procedureName: 'CreateConfiguration', plugin: plugin, credentials: [
                    
                    'credential': null,
                    
                ])
        cloned.parameters = this.parameters.clone()
        return cloned
    }

    //Generated
    
    S3Config checkConnection(String checkConnection) {
        this.addParam('checkConnection', checkConnection)
        return this
    }
    
    
    S3Config config(String config) {
        this.addParam('config', config)
        return this
    }
    
    
    S3Config debug(String debug) {
        this.addParam('debug', debug)
        return this
    }
    
    
    S3Config desc(String desc) {
        this.addParam('desc', desc)
        return this
    }
    
    
    S3Config resourcepool(String resourcepool) {
        this.addParam('resource_pool', resourcepool)
        return this
    }
    
    
    S3Config serviceurl(String serviceurl) {
        this.addParam('service_url', serviceurl)
        return this
    }
    
    
    S3Config workspace(String workspace) {
        this.addParam('workspace', workspace)
        return this
    }
    
    
    
    S3Config credential(String user, String password) {
        this.addCredential('credential', user, password)
        return this
    }

    S3Config credentialReference(String path) {
        this.addCredentialReference('credential', path)
        return this
    }
    
    
}