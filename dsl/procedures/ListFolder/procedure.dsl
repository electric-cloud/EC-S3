import java.io.File

def procName = 'ListFolder'
procedure procName, description: 'List all the available objects under a S3 folder', {

    step 'Setup',
        subproject: '/plugins/EC-S3/project',
        subprocedure: 'Setup'

    step 'listFolder',
        command: new File(pluginDir, "dsl/procedures/$procName/steps/listFolder.groovy").text,
        errorHandling: 'failProcedure',
        exclusiveMode: 'none',
        releaseMode: 'none',
        shell: 'ec-groovy',
        timeLimitUnits: 'minutes',
        resourceName: '$[grabbedResource]'

}
