def projName = args.projectName ?: 'S3 Specs Project'
def procName = args.procedureName
def params = args.params

project projName, {

    procedure procName, {

        params.each {k, v ->
            formalParameter k, defaultValue: v, {
                type = 'textarea'
            }
        }

        step procName, {
            subproject = '/plugins/EC-S3/project'
            subprocedure = procName

            params.each { k, v ->
                actualParameter k, '$[' + k + ']'
            }
        }
    }
}