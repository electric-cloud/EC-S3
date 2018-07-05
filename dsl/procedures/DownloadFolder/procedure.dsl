import java.io.File

def procName = 'DownloadFolder'
procedure procName, description: 'Download the contents of an S3 folder', {

    step 'Setup',
        subproject: '/plugins/EC-S3/project',
        subprocedure: 'Setup'

    step 'downloadFolder',
        command: new File(pluginDir, "dsl/procedures/$procName/downloadFolder.groovy").text,
        errorHandling: 'failProcedure',
        exclusiveMode: 'none',
        releaseMode: 'none',
        shell: 'ec-groovy',
        timeLimitUnits: 'minutes',
        resourceName: '$[grabbedResource]'

}
