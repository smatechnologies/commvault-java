package opcon.commvault.connector.constants;

public interface CommVaultXMLTags {
	
	
	// GenericResponse object tags
	public static final String GenericResponseObjectTag = "TMMsg_GenericResp";
	public static final String GenericResponseErrorMsgTag = "errorMessage";
	public static final String GenericResponseErrorCodeTag = "errorCode";

	// GenericResponse1 object tags
	public static final String GenericResponse1ObjectTag = "CVGui_GenericResp";
	public static final String GenericResponse1ErrorMsgTag = "errorMessage";
	public static final String GenericResponse1ErrorCodeTag = "errorCode";
	
	// ProcessingInstructionInfo object tags
	public static final String ProcessingInstructionInfoObjectTag = "processinginstructioninfo";
	public static final String ProcessingInstructionInfoNameTag = "name";
	public static final String ProcessingInstructionInfoValueTag = "value";
	
	// GetClientResponse object tags
	public static final String GetClientResponseObjectTag = "App_GetClientPropertiesResponse";

	// ClientProperties object tags
	public static final String ClientPropertiesObjectTag = "clientProperties";
	
	// Client object tags
	public static final String ClientObjectTag = "client";
	public static final String ClientCvcPortTag = "cvdPort";
	public static final String ClientEntityClientIdTag = "clientId";
	public static final String ClientClientEntityTag = "clientName";

	// ClientEntity object tags
	public static final String ClientEntityObjectTag = "clientEntity";
	public static final String ClientEntityTypeTag = "_type_";
	public static final String ClientEvmgrcPortTag = "evmgrcPort";
	public static final String ClientEntityCleintNameTag = "clientEntity";
	
	// ClientProps object tags
	public static final String ClientPropsObjectTag = "ClientProps";
	public static final String ClientPropsEnableAccessControlTag = "enableAccessControl";
	
	
	
	
	// CreateTaskResponse object tags
	public static final String CreateTaskResponseObjectTag = "TMMsg_CreateTaskResp";
	public static final String CreateTaskResponseTaskIdTag = "taskId";
	
	// GetJobSummaryRespones
	public static final String GetJobSummaryResponseObjectTag = "JobManager_JobListResponse";
	public static final String GetJobSummaryResponseTotalRecordsWithoutPagingTag = "totalRecordsWithoutPaging";

	// Job
	public static final String JobObjectTag = "jobs";

	// JobSummary
	public static final String JobSummaryObjectTag = "jobSummary";
	public static final String JobSummaryAppTypeNameTag = "appTypeName";
	public static final String JobSummaryBackupLevelTag = "backupLevel";
	public static final String JobSummaryBackupLevelNameTag = "backupLevelName";
	public static final String JobSummaryBackupSetNameTag = "backupSetName";
	public static final String JobSummaryDestClientNameTag = "destClientName";
	public static final String JobSummaryIsAgedTag = "isAged";
	public static final String JobSummaryIsVisibleTag = "isVisible";
	public static final String JobSummaryJobElapsedTimeTag = "jobElapsedTime";
	public static final String JobSummaryJobIdTag = "jobId";
	public static final String JobSummaryJobStartTimeTag = "jobStartTime";
	public static final String JobSummaryJobTypeTag = "jobType";
	public static final String JobSummaryLastUpdateTimeTag = "lastUpdateTime";
	public static final String JobSummaryLocalizedBackupLevelNameTag = "localizedBackupLevelName";
	public static final String JobSummaryLocalizedOperationNameTag = "localizedOperationName";
	public static final String JobSummaryLocalizedStatusTag = "localizedStatus";
	public static final String JobSummaryPercentCompleteTag = "percentComplete";
	public static final String JobSummaryPercentSavingsTag = "percentSavings";
	public static final String JobSummarySizeOfApplicationTag = "sizeOfApplication";
	public static final String JobSummarySizeOfMediaOnDiskTag = "sizeOfMediaOnDisk";
	public static final String JobSummaryStatusTag = "status";
	public static final String JobSummaryStatusColorTag = "statusColor";
	public static final String JobSummarySubClientNameTag = "subclientName";
	public static final String JobSummaryTotalFailedFilesTag = "totalFailedFiles";
	public static final String JobSummaryTotalFailedFoldersTag = "totalFailedFolders";
	public static final String JobSummaryTotalNoOfFilesTag = "totalNumOfFiles";

	// JobId object tags
	public static final String JobIdObjectTag = "jobIds";
	public static final String JobIdValueTag = "val";

	// Login object tags
	public static final String LoginObjectTag = "DM2ContentIndexing_CheckCredentialReq";
	public static final String LoginModeTag = "mode";
	public static final String LoginDomainTag = "domain";
	public static final String LoginUserNameTag = "username";
	public static final String LoginPasswordTag = "password";
	public static final String LoginCommServerTag = "commserver";
	
	// LoginResponse object tags
	public static final String loginResponseAliasNameTag = "aliasName";
	public static final String loginResponseUserGUIDTag = "userGUID";
	public static final String loginResponseLoginAttemptsTag = "loginAttempts";
	public static final String loginResponseRemainingLockTime = "remainingLockTime";
	public static final String loginResponseUserName = "userName";
	public static final String loginResponseProviderType = "providerType";
	public static final String loginResponseCcn = "ccn";
	public static final String loginResponseToken = "token";
	public static final String loginResponseForcePasswordChange = "forcePasswordChange";
	public static final String loginResponseIsAccountLocked = "isAccountLocked";
	
	// OwnerOrganization object tags
	public static final String OwnerOrganizationObjectTag = "ownerOrganization";
	public static final String OwnerOrganizationProviderId = "providerId";
	public static final String OwnerOrganizationProviderDomainName = "providerDomainName";
	
	// OwnerOrganization object tags
	public static final String ProviderOrganizationObjectTag = "providerOrganization";
	public static final String ProviderOrganizationProviderId = "providerId";
	public static final String ProviderOrganizationProviderDomainName = "providerDomainName";
	
	// SubClient object tags
	public static final String SubClientObjectTag = "subclient";	
	public static final String SubClientAppNameTag = "appName";	
	public static final String SubClientApplicationIdTag = "applicationId";
	public static final String SubClientBackupsetIdTag = "backupsetId";
	public static final String SubClientBackupsetName = "backupsetName";
	public static final String SubClientClientIdTag = "clientId";
	public static final String SubClientClientNameTag = "clientName";
	public static final String SubClientInstanceIdTag = "instanceId";
	public static final String SubClientInstanceNameTag = "instanceName";
	public static final String SubClientSubClientIdTag = "subClientId";
	public static final String SubClientSubClientNameTag = "subClientName";
	
	
	
}
