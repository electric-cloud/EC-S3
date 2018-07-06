import java.io.File

def procName = 'UploadObject'
procedure procName, description: 'Upload an Object to S3 bucket/folder', {

    step 'Setup',
        subproject: '/plugins/EC-S3/project',
        subprocedure: 'Setup'

    step 'uploadObject',
        command: new File(pluginDir, "dsl/procedures/$procName/steps/uploadObject.groovy").text,
        errorHandling: 'failProcedure',
        exclusiveMode: 'none',
        releaseMode: 'none',
        shell: 'ec-groovy',
        timeLimitUnits: 'minutes',
        resourceName: '$[grabbedResource]'

}
