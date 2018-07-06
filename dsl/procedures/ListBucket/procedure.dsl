import java.io.File

def procName = 'ListBucket'
procedure procName, description: 'List all the available buckets for the current account', {

    step 'Setup',
        subproject: '/plugins/EC-S3/project',
        subprocedure: 'Setup'

    step 'listBucket',
        command: new File(pluginDir, "dsl/procedures/$procName/steps/listBucket.groovy").text,
        errorHandling: 'failProcedure',
        exclusiveMode: 'none',
        releaseMode: 'none',
        shell: 'ec-groovy',
        timeLimitUnits: 'minutes',
        resourceName: '$[grabbedResource]'

}
