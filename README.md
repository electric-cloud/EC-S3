EC-S3
============

The ElectricFlow Amazon S3 plugin

## Compile ##

Run gradlew to compile the plugin

`./gradlew jar`


## Tests ##

#### Creating ecplugin.properties ####
Create an ecplugin.properties at the root of this repository with the following content

    COMMANDER_USER=<COMMANDER_USER>
    COMMANDER_PASSWORD=<COMMANDER_PASSWORD>

    ##Service URL and credentials to access S3##
    SERVICE_URL=https://s3.amazonaws.com
    ACCESS_ID=<ACCESS_ID>
    SECRET_ACCESS_ID=<SECRET_ACCESS_ID>
    SAMPLE_TEST_LOCATION=/tmp


These represent secrets that **should not** be checked in.

#### Running tests ####
Run the `test` task to run the system tests.

`./gradlew test`

