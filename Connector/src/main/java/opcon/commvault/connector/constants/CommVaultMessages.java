package opcon.commvault.connector.constants;

public interface CommVaultMessages {
	
	public static final String SeparatorLine =                "----------------------------------------------------------------------------";						
	public static final String DebugLine =                    "DEBUG : {0}";						
	public static final String ExceptionDetailsLine =         "Exception Details : {0}";						

	public static final String CommVaultConnectorVersionMsg = "Commvault Connector     : {0}";
	public static final String CommVaultServerAddressMsg =    "Server Address          : {0}";
	public static final String CommVaultServerNameMsg =       "Server Name             : {0}";
	public static final String CommVaultXMLTemplateMsg =      "-xt  (XML Template)     : {0}";
	public static final String CommVaultClientNameMsg =       "-c   (client)           : {0}";
	public static final String CommVaultBackupsetNameMsg =    "-bsn (Backupset Name)   : {0}";
	public static final String CommVaultSubClientMsg =        "-sc  (Subclient)        : {0}";
	public static final String CommVaultInstanceMsg =         "-i   (Instance)         : {0}";
	public static final String CommVaultBackupTypeMsg =       "-t   (Backup Type)      : {0}";

	public static final String CommVaultConnectorCompletionMsg = "Commvault Job Completed with status {0} - ({1})";	

	public static final String CommVaultWebServerRetryMsg = "Connection timeout occured - retry number {0})";	
	public static final String CommVaultWebServerRetryMaxMsg = "Connection timeout occured - retry numbers exceeded";	
	public static final String CommVaultWebServerErrorMsg = "Problems communicating with Commvault Web Server - requires manual intervention)";	

	public static final String AuthenticationMsg = "Authenticating User {0} to Server {1}";
	public static final String AuthenticationSuccesfulMsg = "Authenticating for User {0} successful";
	public static final String AuthenticationFailedMsg = "Authenticating for User {0} failed : return code {1}";

	public static final String GetJobSummaryMsg = "Get Job Summary for JobId {0}";
	public static final String GetJobSummarySuccessfulMsg = "Get Job Summary for JobId {0} successful";
	public static final String GetJobSummaryFailedMsg = "Get Job Summary for JobId {0 failed : return code {1}";
	
	public static final String JobSummaryBeginMsg =                    "Job Summary ----------------------------------------------------------------";						
	public static final String JobSummaryJobIdMsg =                    "Job ID                                     : {0}";
	public static final String JobSummaryJobTypeMsg =                  "Job Type                                   : {0}";
	public static final String JobSummaryStatusMsg =                   "Status                                     : {0}";
	public static final String JobSummaryTotalNumOfFilesMsg =          "Total Number of Files                      : {0}";
	public static final String JobSummaryTotalFailedFilesMsg =         "Total Failed Files                         : {0}";
	public static final String JobSummaryTotalFailedFoldersMsg =       "Total Failed Folders                       : {0}";
	public static final String JobSummaryPercentCompleteMsg =          "Percent Complete                           : {0}";
	public static final String JobSummaryPercentSavingsMsg =           "Percent Savings                            : {0}";
	public static final String JobSummarySubclientNameMsg =            "Sub Client Name                            : {0}";
	public static final String JobSummarySubClientBeginMsg =           "Job Summary Sub Client Information------------------------------------------";						
	public static final String JobSummarySubClientIdMsg =              "Sub Client : ID                            : {0}";						
	public static final String JobSummarySubClientNameMsg =            "Sub Client : Name                          : {0}";						
	public static final String JobSummarySubClientClientIdMsg =        "Sub Client : Client ID                     : {0}";						
	public static final String JobSummarySubClientClientNameMsg =      "Sub Client : Client Name                   : {0}";						
	public static final String JobSummarySubClientInstanceIdMsg =      "Sub Client : Instance ID                   : {0}";						
	public static final String JobSummarySubClientInstanceNameMsg =    "Sub Client : Instance Name                 : {0}";						
	public static final String JobSummarySubClientApplicationIdMsg =   "Sub Client : Application ID                : {0}";						
	public static final String JobSummarySubClientApplicationNameMsg = "Sub Client : Application Name              : {0}";						
	public static final String JobSummarySubClientBackupsetIdMsg =     "Sub Client : Backup Set ID                 : {0}";						
	public static final String JobSummarySubClientBackupsetNameMsg =   "Sub Client : Backup Set Name               : {0}";						
	public static final String JobSummarySubClientEndMsg =             "Job Summary Sub Client Information End--------------------------------------";						
	public static final String JobSummaryDestinationClientNameMsg =    "Destination Client Name                    : {0}";
	public static final String JobSummaryJobStartTimeMsg =             "Job Start Time                             : {0}";
	public static final String JobSummaryJobElapsedTimeMsg =           "Job Elapsed Time                           : {0}";
	public static final String JobSummaryLastUpdateTimeMsg =           "Last Update Time                           : {0}";
	public static final String JobSummaryAppTypeNameMsg =              "Application Type Name                      : {0}";
	public static final String JobSummaryBackupLevelMsg =              "Backup Level                               : {0}";
	public static final String JobSummaryBackupLevelNameMsg =          "Backup Level Name                          : {0}";
	public static final String JobSummaryBackupSetNameMsg =            "Backup Set Name                            : {0}";
	public static final String JobSummaryIsAgedMsg =                   "Is Aged                                    : {0}";
	public static final String JobSummaryIsVisibleMsg =                "Is Visible                                 : {0}";
	public static final String JobSummaryLocalizedBackupLevelNameMsg = "Localized Backup Level Name                : {0}";
	public static final String JobSummaryLocalizedOperationNameMsg =   "Localized Operation Name                   : {0}";
	public static final String JobSummaryLocalizedStatusMsg =          "Localized Status                           : {0}";
	public static final String JobSummarySizeOfApplicationMsg =        "Size of Application                        : {0}";
	public static final String JobSummarySizeOfMediaOnDiskMsg =        "Size of Media on Disk                      : {0}";
	public static final String JobSummaryStatusColorMsg =              "Status Color                               : {0}";
	public static final String JobSummaryEndMsg =                      "Job Summary End -------------------------------------------------------------";						
	
	public static final String CreateTaskMsg = "Create Task";
	public static final String CreateTaskSuccessfulMsg = "Create Task successful TaskId {0} JodId {1}";
	public static final String CreateTaskFailedMsg = " Create Task failed : message ({0}) return code ({1})";

	public static final String QCommandMsg = "QCommand";
	public static final String QCommandSuccessfulMsg = " QCommand successful TaskId {0} JodId {1}";
	public static final String QCommandFailedMsg = "QCommand failed : message ({0}) return code ({1})";

	
	public static final String XMLFileDoesNotExistsErrorMsg = "XML template {0} does not exist";

	public static final String JobCompletedMsg = "Job completed with code {0} : description {1}";

	public static final String LogoutMsg = "Logout";
	public static final String LogoutCompletedMsg = "Logout completed : {0}";

}
