import java.io.File

def procName = 'CreateConfiguration'
procedure procName, description: 'Create an S3 configuration', {

    step 'Setup',
        subproject: '/plugins/EC-S3/project',
        subprocedure: 'Setup',
        condition: '$[attempt]'

    step 'AttemptConnection',
        command: new File(pluginDir, "dsl/procedures/$procName/steps/attemptConnection.groovy").text,
        shell: 'ec-groovy',
        condition: '$[attempt]',
        resourceName: '$[grabbedResource]',
        errorHandling: 'abortProcedure'

    step 'createConfiguration',
        command: new File(pluginDir, "dsl/procedures/$procName/steps/createConfiguration.pl").text,
        errorHandling: 'abortProcedure',
        exclusiveMode: 'none',
        postProcessor: 'postp',
        releaseMode: 'none',
        shell: 'ec-perl',
        timeLimitUnits: 'minutes'

    step 'createAndAttachCredential',
        command: new File(pluginDir, "dsl/procedures/$procName/steps/createAndAttachCredential.pl").text,
        errorHandling: 'failProcedure',
        exclusiveMode: 'none',
        releaseMode: 'none',
        shell: 'ec-perl',
        timeLimitUnits: 'minutes'

}
