# Commvault

Commvault is an OpCon Connector for Windows that uses the Commvault API to interact with Commvault to submit backup tasks...

It consists of a single program **CVault.exe**.

## Installation

### Environment

- The command line utility needs **Java version 11** to function
  - An embedded JavaRuntimeEnvironment 11 is included along with the delivery zip / tar files. Once the archive extracted, "/java" directory contains the JRE binaries.

### Windows Instructions
Download CommVault_Windows.zip file from the desired [release available here](https://github.com/SMATechnologies/commvault-java/releases).

After download, extract the zip file to the location you'd like to install the connector to. Once unzipped, everything needed should be located under the root folder of that directory.

After extract, copy the Enterprise Manager Job-Subtype from the /emplugins directory to dropins directory of each Enterprise Manager that will create job
definitions (if the directory does not exist, create it).

Restart Enterprise Manager and a new Windows Job Sub-Type Azure Storage should be visible (if not restart Enterprise Manager using 'Run as Administrator'). 

Create a global property **CommvaultPath** that contains the full path of the installation directory.

Create the OpCon global properties associated with the drop-down lists used when creating backup tasks.

**CV_BACKUP_TYPES** Create the global property and add the values contained in **Backup File Types List** using a comma to separate them. The doubles quotes surrounding the values must be retained. These values will then be visible in the drop-down list.

**CV_BACKUP_FILE_NAMES** Create the global property and add the values contained in **Backup File Names List** using a comma to separate them. The doubles quotes surrounding the values must be retained. These values will then be visible in the drop-down list.
 
## Configuration
The Commvault connector uses a configuration file **Connector.config** that contains the Commvault account information.

The storage account information consists of the storage account name and the connection string for that storage account.
The connection string must be encrypted using the **Encrypt.exe** program.

The encryption tool provides basic encryption capabilities to prevent clear text.

**Connector.config** file example:
```
[CONNECTOR]
NAME=CommVault Connector
MSLSAM_ROOT_DIRECTORY=
SESSION_RETRY_VALUE=5
POLL_INTERVAL=5
INITIAL_POLL_DELAY=10
DEBUG=OFF

[COMMVAULT]
SERVER_ADDRESS=
SERVER_NAME=
USE_TLS=False
USER_DOMAIN=
USER=(encrypted value)
USER_PASSWORD=(encrypted value)

```

Keyword                | Type | Description
---------------------- | ---- | -----------
NAME                   | Text | The name associated with the conner (this should not be changed).
MSLSAM_ROOT_DIRECTORY  | Text | The full path to the root directory of the MSLSAM. This is used to pass the status of the Commvault tasks to OpCon so the status can be displayed in the Operations views.
SESSION_RETRY_VALUE    | Text | The maximum number of times a retry should be performed if a connection failure is detected during a running connection. (Default 5).
POLL_INTERVAL          | Text | The time in seconds between checks to determine the status of the Commvault task. (Default 5).
INITIAL_POLL_DELAY     | Text | The time in seconds to wait before the initial check to determine the status of the Commvault task. (Default 10).
DEBUG                  | Text | The maximum number of times a retry should be performed if a connection failure is detected during a running connection. (Default 5).
SERVER_ADDRESS         | Text | The address of the Commvault system that the connector must communicate with. 
SERVER_NAME            | Text | The name of the Commvault server. This value is used for the HOST attribute in the web services header record.
USE_TLS                | Text | Indicates if the connection to the Commvault server uses tls.
USER_DOMAIN            | Text | If the commvault user defined for the connector requires a domain name, then enter the doman name here.
USER                   | Text | A commvault user defined with the appropriate privileges so it can start backup jobs. The user name must be encrypted using the **Encrypt.exe** tool.
USER_PASSWORD          | Text | The password of the Commvault user encrypted using the **Encrypt.exe** tool.

### Encrypt Utility
The Encrypt utility uses standard 64 bit encryption.

Supports a -v argument and displays the encrypted value

On Windows, example on how to encrypt the value "abcdefg":
```
Encrypt.exe -v abcdefg
```

## Exit Codes
The `Commvault` exits with the following code.

Code | Description
---- | -----------
0    | COMPLETED, the job completed processing without warnings
1    | COMPLETED_WITH_WARNINGS, the job completed processing, but contains warnings
2    | COMPLETED_WITH_ERRORS, the job completed processing, but contains errors
3    | FAILED, the job failed
4    | FAILED_TO_START, the job failed to start in the Commvault environment
18   | WEB_SERVER_ERROR, an error occured when communicating with the Commvault web server
 
## Commvault Arguments
The Commvault connector requires arguments to be given to function. These arguments are updated in xml templates which is passed to the Commvault web server to initiate backup tasks. The xml templates are contained in the xmlbackupdefinitions directory associated with the connector. 

Arguments | Description
--------- | -----------
**-bsn**  | (Mandatory) The backup setname which is replaced in the xml template. This is the name of a backup set that contains the logical groupings of the sub clients.
**-c**    | (Mandatory) The client name is the name of the system on which the backup task must be executed.
**-i**    | Is the name of an instance that is associated with the request. If no value is suppied, the value in the xml definition will be used.
**-sc**   | Defines a logical container that identifies and manages the production data to be protected. If no value is suppied, the value in the xml definition will be used.
**-t**    | (Mandatory) The backup task to perform Values are (DIFFERENTIAL, INCREMENTAL, FULL, PRE_SELECT or SYNTHETIC_FULL).
**-xt**   | (Mandatory) The name of the xml template to use for the request. The xml template is loaded from the /xmlbackupdefinitions directory.

## AzureStorage Job Sub-Type
The AzureStorage connector provides a Job Sub-Type that can be used to simplify job definitions within OpCon.

![jobsubtype](/docs/images/commvault_sub_type.PNG)

When using the Job Sub-Type, fill in the backup set name, the client name, the name of the xml template to use for the request and select the backup type from the drop-down list.

### Commvault Job Sub-Type drop-down lists
The Commvault Job Sub-Type uses drop-down lists that presents backup and xml template options. This drop-down list is populated from values extracted from OpCon global properties. This means it is possible to update the values that appear in these lists.
 
**CV_BACKUP_TYPES** contains a list of backup types that can be selected when defining a job.
**CV_BACKUP_FILE_NAMES** contains a list of xml templates that can be selected when defining a job.

##Backup File Types List
```
"DIFFERENTIAL"
"INCREMENTAL"
"FULL"
"PRE_SELECT"
"SYNTHETIC_FULL"
```
##Backup File Names List
```
"file_system.xml"
"script_incr.xml"
"script_synthetic_full.xml"
```
