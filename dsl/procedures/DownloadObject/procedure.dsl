import java.io.File

def procName = 'DownloadObject'
procedure procName, description: 'Download an S3 object to local folder', {

    step 'Setup',
        subproject: '/plugins/EC-S3/project',
        subprocedure: 'Setup'

    step 'downloadObject',
        command: new File(pluginDir, "dsl/procedures/$procName/steps/downloadObject.groovy").text,
        errorHandling: 'failProcedure',
        exclusiveMode: 'none',
        releaseMode: 'none',
        shell: 'ec-groovy',
        timeLimitUnits: 'minutes',
        resourceName: '$[grabbedResource]'

}
