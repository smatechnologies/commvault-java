package opcon.commvault.connector.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import opcon.commvault.connector.constants.CommVaultConstants;
import opcon.commvault.connector.constants.CommVaultXMLTags;

@XmlRootElement(name = CommVaultXMLTags.SubClientObjectTag)
@XmlType(propOrder = {"appName","applicationId","backupsetId","backupsetName","clientId",
		"clientName","instanceId","instanceName","subClientId","subClientName"})
public class SubClient {
	
	private String appName = CommVaultConstants.EMPTY_STRING;
	private String applicationId = CommVaultConstants.EMPTY_STRING;
	private String backupsetId = CommVaultConstants.EMPTY_STRING;
	private String backupsetName = CommVaultConstants.EMPTY_STRING;
	private String clientId = CommVaultConstants.EMPTY_STRING;
	private String clientName = CommVaultConstants.EMPTY_STRING;
	private String instanceId = CommVaultConstants.EMPTY_STRING;
	private String instanceName = CommVaultConstants.EMPTY_STRING;
	private String subClientId = CommVaultConstants.EMPTY_STRING;
	private String subClientName = CommVaultConstants.EMPTY_STRING;
	
	public String getAppName() {
		return appName;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.SubClientAppNameTag)
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public String getApplicationId() {
		return applicationId;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.SubClientApplicationIdTag)
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	
	public String getBackupsetId() {
		return backupsetId;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.SubClientBackupsetIdTag)
	public void setBackupsetId(String backupsetId) {
		this.backupsetId = backupsetId;
	}
	
	public String getBackupsetName() {
		return backupsetName;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.SubClientBackupsetName)
	public void setBackupsetName(String backupsetName) {
		this.backupsetName = backupsetName;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.SubClientClientIdTag)
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getClientName() {
		return clientName;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.SubClientClientNameTag)
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public String getInstanceId() {
		return instanceId;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.SubClientInstanceIdTag)
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	public String getInstanceName() {
		return instanceName;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.SubClientInstanceNameTag)
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	
	public String getSubClientId() {
		return subClientId;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.SubClientSubClientIdTag)
	public void setSubClientId(String subClientId) {
		this.subClientId = subClientId;
	}
	
	public String getSubClientName() {
		return subClientName;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.SubClientSubClientNameTag)
	public void setSubClientName(String subClientName) {
		this.subClientName = subClientName;
	}
    
}
