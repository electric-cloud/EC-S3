import java.io.File

def procName = 'CreateFolder'
procedure procName, description: 'Create an S3 folder', {

    step 'Setup',
        subproject: '/plugins/EC-S3/project',
        subprocedure: 'Setup'

    step 'createFolder',
        command: new File(pluginDir, "dsl/procedures/$procName/steps/createFolder.groovy").text,
        errorHandling: 'failProcedure',
        exclusiveMode: 'none',
        releaseMode: 'none',
        shell: 'ec-groovy',
        timeLimitUnits: 'minutes',
        resourceName: '$[grabbedResource]'

}
