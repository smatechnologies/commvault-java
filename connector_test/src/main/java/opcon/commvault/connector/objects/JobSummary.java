package opcon.commvault.connector.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import opcon.commvault.connector.constants.CommVaultConstants;
import opcon.commvault.connector.constants.CommVaultXMLTags;

@XmlRootElement(name = CommVaultXMLTags.JobSummaryObjectTag)
@XmlType(propOrder = {"appTypeName","backupLevel","backupLevelName","backupSetName","destClientName","isAged","isVisible",
		"jobElapsedTime","jobId","jobStartTime","jobType","lastUpdateTime","localizedBackupLevelName",
		"localizedOperationName","localizedStatus","percentComplete","percentSavings","sizeOfApplication",
		"sizeOfMediaOnDisk","status","statusColor","subclientName","totalFailedFiles","totalFailedFolders",
		"totalNumOfFiles","subclient"})
public class JobSummary {
	
	private String appTypeName = CommVaultConstants.EMPTY_STRING;
	private String backupLevel = CommVaultConstants.EMPTY_STRING;
	private String backupLevelName = CommVaultConstants.EMPTY_STRING;
	private String backupSetName = CommVaultConstants.EMPTY_STRING;
	private String destClientName = CommVaultConstants.EMPTY_STRING;
	private String isAged = CommVaultConstants.EMPTY_STRING;
	private String isVisible = CommVaultConstants.EMPTY_STRING;
	private String jobElapsedTime = CommVaultConstants.EMPTY_STRING;
	private String jobId = CommVaultConstants.EMPTY_STRING;
	private String jobStartTime = CommVaultConstants.EMPTY_STRING;
	private String jobType = CommVaultConstants.EMPTY_STRING;
	private String lastUpdateTime = CommVaultConstants.EMPTY_STRING;
	private String localizedBackupLevelName = CommVaultConstants.EMPTY_STRING;
	private String localizedOperationName = CommVaultConstants.EMPTY_STRING;
	private String localizedStatus = CommVaultConstants.EMPTY_STRING;
	private String percentComplete = CommVaultConstants.EMPTY_STRING;
	private String percentSavings = CommVaultConstants.EMPTY_STRING;
	private String sizeOfApplication = CommVaultConstants.EMPTY_STRING;
	private String sizeOfMediaOnDisk = CommVaultConstants.EMPTY_STRING;
	private String status = CommVaultConstants.EMPTY_STRING;
	private String statusColor = CommVaultConstants.EMPTY_STRING;
	private String subclientName = CommVaultConstants.EMPTY_STRING;
	private String totalFailedFiles = CommVaultConstants.EMPTY_STRING;
	private String totalFailedFolders = CommVaultConstants.EMPTY_STRING;
	private String totalNumOfFiles = CommVaultConstants.EMPTY_STRING;
	private SubClient subclient = null; 
			
	public String getAppTypeName() {
		return appTypeName;
	}

	@XmlAttribute(name = CommVaultXMLTags.JobSummaryAppTypeNameTag)
	public void setAppTypeName(String appTypeName) {
		this.appTypeName = appTypeName;
	}
	
	public String getBackupLevel() {
		return backupLevel;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryBackupLevelTag)
	public void setBackupLevel(String backupLevel) {
		this.backupLevel = backupLevel;
	}
	
	public String getBackupLevelName() {
		return backupLevelName;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryBackupLevelNameTag)
	public void setBackupLevelName(String backupLevelName) {
		this.backupLevelName = backupLevelName;
	}
	
	public String getBackupSetName() {
		return backupSetName;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryBackupSetNameTag)
	public void setBackupSetName(String backupSetName) {
		this.backupSetName = backupSetName;
	}
	
	public String getDestClientName() {
		return destClientName;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryDestClientNameTag)
	public void setDestClientName(String destClientName) {
		this.destClientName = destClientName;
	}
	
	public String getIsAged() {
		return isAged;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryIsAgedTag)
	public void setIsAged(String isAged) {
		this.isAged = isAged;
	}
	
	public String getIsVisible() {
		return isVisible;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryIsVisibleTag)
	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}
	
	public String getJobElapsedTime() {
		return jobElapsedTime;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryJobElapsedTimeTag)
	public void setJobElapsedTime(String jobElapsedTime) {
		this.jobElapsedTime = jobElapsedTime;
	}
	
	public String getJobId() {
		return jobId;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryJobIdTag)
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	public String getJobStartTime() {
		return jobStartTime;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryJobStartTimeTag)
	public void setJobStartTime(String jobStartTime) {
		this.jobStartTime = jobStartTime;
	}
	
	public String getJobType() {
		return jobType;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryJobTypeTag)
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryLastUpdateTimeTag)
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public String getLocalizedBackupLevelName() {
		return localizedBackupLevelName;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryLocalizedBackupLevelNameTag)
	public void setLocalizedBackupLevelName(String localizedBackupLevelName) {
		this.localizedBackupLevelName = localizedBackupLevelName;
	}
	
	public String getLocalizedOperationName() {
		return localizedOperationName;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryLocalizedOperationNameTag)
	public void setLocalizedOperationName(String localizedOperationName) {
		this.localizedOperationName = localizedOperationName;
	}
	
	public String getLocalizedStatus() {
		return localizedStatus;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryLocalizedStatusTag)
	public void setLocalizedStatus(String localizedStatus) {
		this.localizedStatus = localizedStatus;
	}
	
	public String getPercentComplete() {
		return percentComplete;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryPercentCompleteTag)
	public void setPercentComplete(String percentComplete) {
		this.percentComplete = percentComplete;
	}
	
	public String getPercentSavings() {
		return percentSavings;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryPercentSavingsTag)
	public void setPercentSavings(String percentSavings) {
		this.percentSavings = percentSavings;
	}
	
	public String getSizeOfApplication() {
		return sizeOfApplication;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummarySizeOfApplicationTag)
	public void setSizeOfApplication(String sizeOfApplication) {
		this.sizeOfApplication = sizeOfApplication;
	}
	
	public String getSizeOfMediaOnDisk() {
		return sizeOfMediaOnDisk;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummarySizeOfMediaOnDiskTag)
	public void setSizeOfMediaOnDisk(String sizeOfMediaOnDisk) {
		this.sizeOfMediaOnDisk = sizeOfMediaOnDisk;
	}
	
	public String getStatus() {
		return status;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryStatusTag)
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatusColor() {
		return statusColor;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryStatusColorTag)
	public void setStatusColor(String statusColor) {
		this.statusColor = statusColor;
	}
	
	public String getSubclientName() {
		return subclientName;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummarySubClientNameTag)
	public void setSubclientName(String subclientName) {
		this.subclientName = subclientName;
	}
	
	public String getTotalFailedFiles() {
		return totalFailedFiles;
		}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryTotalFailedFilesTag)
	public void setTotalFailedFiles(String totalFailedFiles) {
		this.totalFailedFiles = totalFailedFiles;
	}
	
	public String getTotalFailedFolders() {
		return totalFailedFolders;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryTotalFailedFoldersTag)
	public void setTotalFailedFolders(String totalFailedFolders) {
		this.totalFailedFolders = totalFailedFolders;
	}
	
	public String getTotalNumOfFiles() {
		return totalNumOfFiles;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.JobSummaryTotalNoOfFilesTag)
	public void setTotalNumOfFiles(String totalNumOfFiles) {
		this.totalNumOfFiles = totalNumOfFiles;
	}

	public SubClient getSubclient() {
		return subclient;
	}

	@XmlElement(name = CommVaultXMLTags.SubClientObjectTag)
	public void setSubclient(SubClient subclient) {
		this.subclient = subclient;
	}
	
}
