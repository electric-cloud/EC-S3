import java.io.File

def procName = 'UploadFolder'
procedure procName, description: 'Upload the contents of a folder to a S3 bucket/folder', {

    step 'Setup',
        subproject: '/plugins/EC-S3/project',
        subprocedure: 'Setup'

    step 'uploadFolder',
        command: new File(pluginDir, "dsl/procedures/$procName/uploadFolder.groovy").text,
        errorHandling: 'failProcedure',
        exclusiveMode: 'none',
        releaseMode: 'none',
        shell: 'ec-groovy',
        timeLimitUnits: 'minutes',
        resourceName: '$[grabbedResource]'

}
