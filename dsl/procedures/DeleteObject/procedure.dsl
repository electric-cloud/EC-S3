import java.io.File

def procName = 'DeleteObject'
procedure procName, description: 'Delete an S3 object', {

    step 'Setup',
        subproject: '/plugins/EC-S3/project',
        subprocedure: 'Setup'

    step 'deleteObject',
        command: new File(pluginDir, "dsl/procedures/$procName/steps/deleteObject.groovy").text,
        errorHandling: 'failProcedure',
        exclusiveMode: 'none',
        releaseMode: 'none',
        shell: 'ec-groovy',
        timeLimitUnits: 'minutes',
        resourceName: '$[grabbedResource]'

}
