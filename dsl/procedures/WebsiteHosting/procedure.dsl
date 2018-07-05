import java.io.File

def procName = 'WebsiteHosting'
procedure procName, description: 'Make a S3 bucket to host static website', {

    step 'Setup',
        subproject: '/plugins/EC-S3/project',
        subprocedure: 'Setup'

    step 'websiteHosting',
        command: new File(pluginDir, "dsl/procedures/$procName/websiteHosting.groovy").text,
        errorHandling: 'failProcedure',
        exclusiveMode: 'none',
        releaseMode: 'none',
        shell: 'ec-groovy',
        timeLimitUnits: 'minutes',
        resourceName: '$[grabbedResource]'

}
