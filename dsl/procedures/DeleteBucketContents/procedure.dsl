import java.io.File

def procName = 'DeleteBucketContents'
procedure procName, description: 'Delete all the objects in a bucket', {

    step 'Setup',
        subproject: '/plugins/EC-S3/project',
        subprocedure: 'Setup'

    step 'deleteBucketContents',
        command: new File(pluginDir, "dsl/procedures/$procName/steps/deleteBucketContents.groovy").text,
        errorHandling: 'failProcedure',
        exclusiveMode: 'none',
        releaseMode: 'none',
        shell: 'ec-groovy',
        timeLimitUnits: 'minutes',
        resourceName: '$[grabbedResource]'

}
