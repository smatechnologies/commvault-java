package opcon.commvault.connector.impl;

import java.io.File;
import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import opcon.commvault.connector.arguments.CommVaultArguments;
import opcon.commvault.connector.configuration.CommVaultConfiguration;
import opcon.commvault.connector.constants.CommVaultConstants;
import opcon.commvault.connector.constants.CommVaultMessages;
import opcon.commvault.connector.exception.WebServicesException;
import opcon.commvault.connector.factories.CommVaultConnectionFactory;
import opcon.commvault.connector.factories.UpdateJobStatus;
import opcon.commvault.connector.interfaces.ICommVaultConnectionFactory;
import opcon.commvault.connector.modules.CommVaultJobStatus;
import opcon.commvault.connector.modules.ConnectionFactoryReturnData;
import opcon.commvault.connector.modules.UpdateJobStatusParameters;
import opcon.commvault.connector.objects.JobSummary;
import opcon.commvault.connector.objects.Login;
import opcon.commvault.connector.util.CommVaultUtilities;
import opcon.commvault.connector.util.CreateCommVaultObjects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommVaultJobExecutorImpl  extends AbstractJobExecutorImpl {

	private final static Logger LOG = LoggerFactory.getLogger(CommVaultJobExecutorImpl.class);
	private ICommVaultConnectionFactory _ICommVaultConnectionFactory = new CommVaultConnectionFactory();
	private CommVaultConfiguration _CommVaultConfiguration = CommVaultConfiguration.getInstance();
	private CommVaultUtilities _CommVaultUtilities = new CommVaultUtilities();
	private CreateCommVaultObjects _CreateCommVaultObjects = new CreateCommVaultObjects();
	
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private int iJobStatus = -1;
	private int retryCounter = 0;

	public int startJob(
			CommVaultArguments _CommVaultArguments
			) {
		
		int returnCode = -1;
		
		try {
			if(_CommVaultConfiguration.isDebug()) {
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine,"Arguments xmltemplate {" + _CommVaultArguments.getCommVaultXmlTemplate() + 
						"} Backupset Name {" + _CommVaultArguments.getCommVaultBackupsetName() + 
						"} Client Name {" + _CommVaultArguments.getCommVaultClientName() + "} SubClient Name {" + _CommVaultArguments.getCommVaultSubClientName() + 
						"} Instance {" + _CommVaultArguments.getCommVaultInstance() + "} Backup Type {" + _CommVaultArguments.getCommVaultBackupType() + "}"));
			}
			iJobStatus = -1;
			// authenticate
			Login login = _CreateCommVaultObjects.createLoginObject();
			ConnectionFactoryReturnData authReturnData = _ICommVaultConnectionFactory.authenticateUser(_CommVaultConfiguration.getServerAddress(), _CommVaultConfiguration.isUseTls(), login);
			if(authReturnData.getWebSvcReturnCode() == 200) {
				// authenticated
				// start task 
				String createTaskInfo = _CreateCommVaultObjects.createTaskRequestXMLString(_CommVaultArguments);
				if(createTaskInfo != null) {
					// start task
					ConnectionFactoryReturnData qcommandReturnData = _ICommVaultConnectionFactory.qcommand(_CommVaultConfiguration.getServerAddress(), _CommVaultConfiguration.getServerName(),
							_CommVaultConfiguration.isUseTls(), createTaskInfo);
					if(qcommandReturnData.getWebSvcReturnCode() == 200) {
						// go monitor for completion
						Integer jobid = Integer.parseInt(qcommandReturnData.getCreateTaskResponse().getJobId().getValue());
						getJobStatus(jobid, _CommVaultConfiguration.getInitialPollDelay(), _CommVaultConfiguration.getPollInterval());
						_ICommVaultConnectionFactory.logout(_CommVaultConfiguration.getServerAddress(), _CommVaultConfiguration.getServerName(),_CommVaultConfiguration.isUseTls());
						LOG.info(CommVaultMessages.SeparatorLine);
						LOG.info(MessageFormat.format(CommVaultMessages.JobCompletedMsg, String.valueOf(result.getStatus()), result.getDescription()));
						returnCode = result.getStatus();
						LOG.info(CommVaultMessages.SeparatorLine);
						_ICommVaultConnectionFactory.logout(_CommVaultConfiguration.getServerAddress(), _CommVaultConfiguration.getServerName(),_CommVaultConfiguration.isUseTls());
						returnCode = result.getStatus();
					} else {
						// set failed to start error and exit
						_ICommVaultConnectionFactory.logout(_CommVaultConfiguration.getServerAddress(), _CommVaultConfiguration.getServerName(),_CommVaultConfiguration.isUseTls());
						returnCode = CommVaultJobStatus.FAILED_TO_START.getId();
					}
				} else {
					// set failed to start error and exit
					_ICommVaultConnectionFactory.logout(_CommVaultConfiguration.getServerAddress(), _CommVaultConfiguration.getServerName(),_CommVaultConfiguration.isUseTls());
					returnCode = CommVaultJobStatus.FAILED_TO_START.getId();
				}
			} else {
				// set failed to start error and exit
				returnCode = CommVaultJobStatus.FAILED_TO_START.getId();
			}
		} catch (WebServicesException ex) {
			LOG.error(MessageFormat.format(CommVaultMessages.ExceptionDetailsLine,_CommVaultUtilities.getExceptionDetails(ex)));
			LOG.error(CommVaultMessages.CommVaultWebServerErrorMsg);
			return CommVaultJobStatus.WEB_SERVER_ERROR.getId();
		} catch (Exception ex) {
			LOG.error(MessageFormat.format(CommVaultMessages.ExceptionDetailsLine,_CommVaultUtilities.getExceptionDetails(ex)));
			if(_CommVaultConfiguration.isDebug()) {
				LOG.info(CommVaultMessages.DebugLine,"Job Code : " + CommVaultJobStatus.FAILED.toString());
			}
			 return CommVaultJobStatus.FAILED.getId();
		}
		return returnCode;
	}	// END : startJob

	@Override
	void getStatus(
			int jobId
			) {
		
		try {

			ConnectionFactoryReturnData returnData = _ICommVaultConnectionFactory.getJobSummary(_CommVaultConfiguration.getServerAddress(), _CommVaultConfiguration.getServerName(), _CommVaultConfiguration.isUseTls(), jobId); 
			if(_CommVaultConfiguration.isDebug()) {
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Get start status of job {" + jobId + "} result {" + returnData.getGetJobSummaryResponse().getJobs().getJobSummary().getStatus() + "}"));
			}
			if(returnData.getWebSvcReturnCode() == 200) {
				int jobStatus = CommVaultJobStatus.getIntValue(returnData.getGetJobSummaryResponse().getJobs().getJobSummary().getStatus());
				CommVaultJobStatus commVaultJobStatus = CommVaultJobStatus.fromValue(jobStatus);
				// reset retry counter
				retryCounter = 0;
				
				switch (commVaultJobStatus) {

					case COMPLETED:
						if(_CommVaultConfiguration.isDebug()) {
							LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Job Code : " + CommVaultJobStatus.COMPLETED.toString()));
						}
						addJobSummaryInformationToLog(returnData.getGetJobSummaryResponse().getJobs().getJobSummary());
						result.setStatus(CommVaultJobStatus.COMPLETED.getId());
						result.setDescription(CommVaultJobStatus.COMPLETED.toString());
						jobFinished();
						futureStart.cancel(true);
						break;
			
					case COMPLETED_WITH_WARNINGS:
						if(_CommVaultConfiguration.isDebug()) {
							LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Job Code : " + CommVaultJobStatus.COMPLETED_WITH_WARNINGS.toString()));
						}
						addJobSummaryInformationToLog(returnData.getGetJobSummaryResponse().getJobs().getJobSummary());
						result.setStatus(CommVaultJobStatus.COMPLETED_WITH_WARNINGS.getId());
						result.setDescription(CommVaultJobStatus.COMPLETED_WITH_WARNINGS.toString());
						jobFinished();
						futureStart.cancel(true);
						break;
			
					case COMPLETED_WITH_ERRORS:
						if(_CommVaultConfiguration.isDebug()) {
							LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Job Code : " + CommVaultJobStatus.COMPLETED_WITH_ERRORS.toString()));
						}
						addJobSummaryInformationToLog(returnData.getGetJobSummaryResponse().getJobs().getJobSummary());
						result.setStatus(CommVaultJobStatus.COMPLETED_WITH_ERRORS.getId());
						result.setDescription(CommVaultJobStatus.COMPLETED_WITH_ERRORS.toString());
						jobFinished();
						futureStart.cancel(true);
						break;
			
					case FAILED:
						if(_CommVaultConfiguration.isDebug()) {
							LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Job Code : " + CommVaultJobStatus.FAILED.toString()));
						}
						addJobSummaryInformationToLog(returnData.getGetJobSummaryResponse().getJobs().getJobSummary());
						result.setStatus(CommVaultJobStatus.FAILED.getId());
						result.setDescription(CommVaultJobStatus.FAILED.toString());
						jobFinished();
						futureStart.cancel(true);
						break;
			
					case FAILED_TO_START:
						if(_CommVaultConfiguration.isDebug()) {
							LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Job Code : " + CommVaultJobStatus.FAILED_TO_START.toString()));
						}
						addJobSummaryInformationToLog(returnData.getGetJobSummaryResponse().getJobs().getJobSummary());
						result.setStatus(CommVaultJobStatus.FAILED_TO_START.getId());
						result.setDescription(CommVaultJobStatus.FAILED_TO_START.toString());
						jobFinished();
						futureStart.cancel(true);
						break;
			
					case RUNNING:
						if(_CommVaultConfiguration.isDebug()) {
							LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Job Code : " + CommVaultJobStatus.RUNNING.toString()));
						}
						if(jobStatus != iJobStatus) {
							// set the new status
							iJobStatus = jobStatus;
							// send status message
							sendStatusMessage(CommVaultJobStatus.RUNNING.toString());
						}
						break;
			
					case WAITING:
						if(_CommVaultConfiguration.isDebug()) {
							LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Job Code : " + CommVaultJobStatus.WAITING.toString()));
						}
						if(jobStatus != iJobStatus) {
							// set the new status
							iJobStatus = jobStatus;
							// send status message
							sendStatusMessage(CommVaultJobStatus.WAITING.toString());
						}
						break;
			
					case PENDING:
						if(_CommVaultConfiguration.isDebug()) {
							LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Job Code : " + CommVaultJobStatus.PENDING.toString()));
						}
						if(jobStatus != iJobStatus) {
							// set the new status
							iJobStatus = jobStatus;
							// send status message
							sendStatusMessage(CommVaultJobStatus.PENDING.toString());
						}
						break;
			
					case SUSPEND:
						if(_CommVaultConfiguration.isDebug()) {
							LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Job Code : " + CommVaultJobStatus.SUSPEND.toString()));
						}
						if(jobStatus != iJobStatus) {
							// set the new status
							iJobStatus = jobStatus;
							// send status message
							sendStatusMessage(CommVaultJobStatus.SUSPEND.toString());
						}
						break;
			
					case SUSPENDED:
						if(_CommVaultConfiguration.isDebug()) {
							LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Job Code : " + CommVaultJobStatus.SUSPENDED.toString()));
						}
						if(jobStatus != iJobStatus) {
							// set the new status
							iJobStatus = jobStatus;
							// send status message
							sendStatusMessage(CommVaultJobStatus.SUSPENDED.toString());
						}
						break;
			
					case KILL_PENDING:
						if(_CommVaultConfiguration.isDebug()) {
							LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Job Code : " + CommVaultJobStatus.KILL_PENDING.toString()));
						}
						if(jobStatus != iJobStatus) {
							// set the new status
							iJobStatus = jobStatus;
							// send status message
							sendStatusMessage(CommVaultJobStatus.KILL_PENDING.toString());
						}
						break;
			
					case INTERRUPT_PENDING:
						if(_CommVaultConfiguration.isDebug()) {
							LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Job Code : " + CommVaultJobStatus.INTERRUPT_PENDING.toString()));
						}
						if(jobStatus != iJobStatus) {
							// set the new status
							iJobStatus = jobStatus;
							// send status message
							sendStatusMessage(CommVaultJobStatus.INTERRUPT_PENDING.toString());
						}
						break;
			
					case INTERRUPTED:
						if(_CommVaultConfiguration.isDebug()) {
							LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Job Code : " + CommVaultJobStatus.INTERRUPTED.toString()));
						}
						if(jobStatus != iJobStatus) {
							// set the new status
							iJobStatus = jobStatus;
							// send status message
							sendStatusMessage(CommVaultJobStatus.INTERRUPTED.toString());
						}
						break;
			
					case QUEUED:
						if(_CommVaultConfiguration.isDebug()) {
							LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Job Code : " + CommVaultJobStatus.QUEUED.toString()));
						}
						if(jobStatus != iJobStatus) {
							// set the new status
							iJobStatus = jobStatus;
							// send status message
							sendStatusMessage(CommVaultJobStatus.QUEUED.toString());
						}
						break;
			
					case RUNNING_CANNOT_BE_VERIFIED:
						if(_CommVaultConfiguration.isDebug()) {
							LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Job Code : " + CommVaultJobStatus.RUNNING_CANNOT_BE_VERIFIED.toString()));
						}
						if(jobStatus != iJobStatus) {
							// set the new status
							iJobStatus = jobStatus;
							// send status message
							sendStatusMessage(CommVaultJobStatus.RUNNING.toString());
						}
						break;
			
					case ABNORMAL_TERMINATED_CLEANUP:
						if(_CommVaultConfiguration.isDebug()) {
							LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Job Code : " + CommVaultJobStatus.ABNORMAL_TERMINATED_CLEANUP.toString()));
						}
						if(jobStatus != iJobStatus) {
							// set the new status
							iJobStatus = jobStatus;
							// send status message
							sendStatusMessage("Abnormal Termination");
						}
						break;
			
					case COMMITTED:
						if(_CommVaultConfiguration.isDebug()) {
							LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Job Code : " + CommVaultJobStatus.COMMITTED.toString()));
						}
						if(jobStatus != iJobStatus) {
							// set the new status
							iJobStatus = jobStatus;
							// send status message
							sendStatusMessage(CommVaultJobStatus.COMMITTED.toString());
						}
						break;
			
					case KILLED:
						if(_CommVaultConfiguration.isDebug()) {
							LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Job Code : " + CommVaultJobStatus.KILLED.toString()));
						}
						if(jobStatus != iJobStatus) {
							// set the new status
							iJobStatus = jobStatus;
							// send status message
							sendStatusMessage(CommVaultJobStatus.KILLED.toString());
						}
						break;
					
					case WEB_SERVER_ERROR:
						if(_CommVaultConfiguration.isDebug()) {
							LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Job Code : " + CommVaultJobStatus.WEB_SERVER_ERROR.toString()));
						}
						result.setStatus(CommVaultJobStatus.WEB_SERVER_ERROR.getId());
						result.setDescription(CommVaultJobStatus.WEB_SERVER_ERROR.toString());
						jobFinished();
						futureStart.cancel(true);
						break;
			
					default:
						break;
					
				}
			}
		} catch (WebServicesException ex) {
			LOG.error(MessageFormat.format(CommVaultMessages.ExceptionDetailsLine,_CommVaultUtilities.getExceptionDetails(ex)));
			if(retryCounter < _CommVaultConfiguration.getSessionRetryValue()) {
				retryCounter++;
				LOG.error(MessageFormat.format(CommVaultMessages.CommVaultWebServerRetryMsg,String.valueOf(retryCounter)));
			} else {
					LOG.error(CommVaultMessages.CommVaultWebServerRetryMaxMsg);
					LOG.error(CommVaultMessages.CommVaultWebServerErrorMsg);
					result.setStatus(CommVaultJobStatus.WEB_SERVER_ERROR.getId());
					result.setDescription(CommVaultJobStatus.WEB_SERVER_ERROR.toString());
					try {
						jobFinished();
					} catch (Exception e) {
						LOG.error(MessageFormat.format(CommVaultMessages.ExceptionDetailsLine,_CommVaultUtilities.getExceptionDetails(e)));
					}
					futureStart.cancel(true);
			}
		} catch (Exception ex) {
			LOG.error(MessageFormat.format(CommVaultMessages.ExceptionDetailsLine,_CommVaultUtilities.getExceptionDetails(ex)));
			if(_CommVaultConfiguration.isDebug()) {
				LOG.error(MessageFormat.format(CommVaultMessages.DebugLine,	"Job Code : " + CommVaultJobStatus.FAILED.toString()));
			}
			result.setStatus(CommVaultJobStatus.FAILED.getId());
			result.setDescription(CommVaultJobStatus.FAILED.toString());
			try {
				jobFinished();
			} catch (Exception e) {
				LOG.error(MessageFormat.format(CommVaultMessages.ExceptionDetailsLine,_CommVaultUtilities.getExceptionDetails(e)));
			}
			futureStart.cancel(true);
		}
	}	// END : getStatus
	
	private void addJobSummaryInformationToLog(
			JobSummary jobsummary
			) throws Exception {
		
		try {
			LOG.info(CommVaultMessages.JobSummaryBeginMsg);
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryJobIdMsg,jobsummary.getJobId()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryJobTypeMsg,jobsummary.getJobType()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryAppTypeNameMsg,jobsummary.getAppTypeName()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryBackupLevelMsg,jobsummary.getBackupLevel()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryBackupSetNameMsg,jobsummary.getBackupSetName()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryStatusMsg,jobsummary.getStatus()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryJobStartTimeMsg,jobsummary.getJobStartTime()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryJobElapsedTimeMsg,jobsummary.getJobElapsedTime()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryTotalNumOfFilesMsg,jobsummary.getTotalNumOfFiles()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryTotalFailedFilesMsg,jobsummary.getTotalFailedFiles()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryTotalFailedFoldersMsg,jobsummary.getTotalFailedFolders()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryPercentCompleteMsg,jobsummary.getPercentComplete()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryPercentSavingsMsg,jobsummary.getPercentSavings()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummarySubclientNameMsg,jobsummary.getSubclientName()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummarySubClientBeginMsg,jobsummary.getPercentSavings()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummarySubClientIdMsg,jobsummary.getSubclient().getSubClientId()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummarySubClientNameMsg,jobsummary.getSubclient().getSubClientName()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummarySubClientClientIdMsg,jobsummary.getSubclient().getClientId()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummarySubClientClientNameMsg,jobsummary.getSubclient().getClientName()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummarySubClientInstanceIdMsg,jobsummary.getSubclient().getInstanceId()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummarySubClientInstanceNameMsg,jobsummary.getSubclient().getInstanceName()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummarySubClientApplicationNameMsg,jobsummary.getSubclient().getAppName()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummarySubClientApplicationIdMsg,jobsummary.getSubclient().getApplicationId()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummarySubClientApplicationNameMsg,jobsummary.getSubclient().getAppName()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummarySubClientBackupsetIdMsg,jobsummary.getSubclient().getBackupsetId()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummarySubClientBackupsetNameMsg,jobsummary.getSubclient().getBackupsetName()));
			LOG.info(CommVaultMessages.JobSummarySubClientEndMsg);
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryDestinationClientNameMsg,jobsummary.getDestClientName()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryLastUpdateTimeMsg,jobsummary.getLastUpdateTime()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryBackupLevelNameMsg,jobsummary.getBackupLevelName()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryIsAgedMsg,jobsummary.getIsAged()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryIsVisibleMsg,jobsummary.getIsVisible()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryLocalizedBackupLevelNameMsg,jobsummary.getLocalizedBackupLevelName()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryLocalizedOperationNameMsg,jobsummary.getLocalizedOperationName()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryLocalizedStatusMsg,jobsummary.getLocalizedStatus()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummarySizeOfApplicationMsg,jobsummary.getSizeOfApplication()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummarySizeOfMediaOnDiskMsg,jobsummary.getSizeOfMediaOnDisk()));
			LOG.info(MessageFormat.format(CommVaultMessages.JobSummaryStatusColorMsg,jobsummary.getStatusColor()));
			LOG.info(CommVaultMessages.JobSummaryEndMsg);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex);
		}
	}	// END : addJobSummaryInformationToLog
	
	private void sendStatusMessage(
			String message
			) throws Exception {

		try {
			if(_CommVaultConfiguration.isDebug()) {
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Sending Status message {" + message + "}"));
			}
			UpdateJobStatusParameters exeStatusParams = new UpdateJobStatusParameters();
			exeStatusParams.setStatusPath(_CommVaultConfiguration.getMsAgentRootDirectory() + File.separator + CommVaultConstants.MSLSAM_SMA_STATUS);
			exeStatusParams.setStatusMessage(message);			
			// start new thread and set Callable so we can get the results
			Runnable workerStatus = new UpdateJobStatus(exeStatusParams);
			executorService.execute(workerStatus);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}	// END : sendStatusMessage
	
}
