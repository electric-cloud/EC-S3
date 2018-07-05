import java.io.File

def procName = 'CreateBucket'
procedure procName, description: 'Create an S3 bucket', {

    step 'Setup',
        subproject: '/plugins/EC-S3/project',
        subprocedure: 'Setup'

    step 'createBucket',
        command: new File(pluginDir, "dsl/procedures/$procName/steps/createBucket.groovy").text,
        errorHandling: 'failProcedure',
        exclusiveMode: 'none',
        releaseMode: 'none',
        shell: 'ec-groovy',
        timeLimitUnits: 'minutes',
        resourceName: '$[grabbedResource]'

}
