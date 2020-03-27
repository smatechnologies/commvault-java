package opcon.commvault.connector.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import opcon.commvault.connector.constants.CommVaultConstants;
import opcon.commvault.connector.constants.CommVaultXMLTags;


@XmlRootElement(name = "DM2ContentIndexing_CheckCredentialResp")
@XmlType(propOrder = {"loginResponseAliasName","loginResponseUserGUID","loginResponseLoginAttempts","loginResponseRemainingLockTime",
		"loginResponseUserName","loginResponseProviderType","loginResponseCcn","loginResponseToken","loginResponseForcePasswordChange",
		"loginResponseIsAccountLocked","ownerOrganization","providerOrganization"})
public class LoginResponse {

	private String loginResponseAliasName = CommVaultConstants.EMPTY_STRING;
	private String loginResponseUserGUID = CommVaultConstants.EMPTY_STRING;
	private String loginResponseLoginAttempts = CommVaultConstants.EMPTY_STRING;
	private String loginResponseRemainingLockTime = CommVaultConstants.EMPTY_STRING;
	private String loginResponseUserName = CommVaultConstants.EMPTY_STRING;
	private String loginResponseProviderType = CommVaultConstants.EMPTY_STRING;
	private String loginResponseCcn = CommVaultConstants.EMPTY_STRING;
	private String loginResponseToken = CommVaultConstants.EMPTY_STRING;
	private String loginResponseForcePasswordChange = CommVaultConstants.EMPTY_STRING;
	private String loginResponseIsAccountLocked = CommVaultConstants.EMPTY_STRING;
	private OwnerOrganization ownerOrganization = null;
	private ProviderOrganization providerOrganization = null;

	public String getLoginResponseAliasName() {
		return loginResponseAliasName;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.loginResponseAliasNameTag)
	public void setLoginResponseAliasName(String loginResponseAliasName) {
		this.loginResponseAliasName = loginResponseAliasName;
	}
	
	public String getLoginResponseUserGUID() {
		return loginResponseUserGUID;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.loginResponseUserGUIDTag)
	public void setLoginResponseUserGUID(String loginResponseUserGUID) {
		this.loginResponseUserGUID = loginResponseUserGUID;
	}
	
	public String getLoginResponseLoginAttempts() {
		return loginResponseLoginAttempts;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.loginResponseLoginAttemptsTag)
	public void setLoginResponseLoginAttempts(String loginResponseLoginAttempts) {
		this.loginResponseLoginAttempts = loginResponseLoginAttempts;
	}
	
	public String getLoginResponseRemainingLockTime() {
		return loginResponseRemainingLockTime;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.loginResponseRemainingLockTime)
	public void setLoginResponseRemainingLockTime(
			String loginResponseRemainingLockTime) {
		this.loginResponseRemainingLockTime = loginResponseRemainingLockTime;
	}
	
	public String getLoginResponseUserName() {
		return loginResponseUserName;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.loginResponseUserName)
	public void setLoginResponseUserName(String loginResponseUserName) {
		this.loginResponseUserName = loginResponseUserName;
	}
	
	public String getLoginResponseProviderType() {
		return loginResponseProviderType;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.loginResponseProviderType)
	public void setLoginResponseProviderType(String loginResponseProviderType) {
		this.loginResponseProviderType = loginResponseProviderType;
	}
	
	public String getLoginResponseCcn() {
		return loginResponseCcn;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.loginResponseCcn)
	public void setLoginResponseCcn(String loginResponseCcn) {
		this.loginResponseCcn = loginResponseCcn;
	}
	
	public String getLoginResponseToken() {
		return loginResponseToken;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.loginResponseToken)
	public void setLoginResponseToken(String loginResponseToken) {
		this.loginResponseToken = loginResponseToken;
	}
	
	public String getLoginResponseForcePasswordChange() {
		return loginResponseForcePasswordChange;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.loginResponseForcePasswordChange)
	public void setLoginResponseForcePasswordChange(
			String loginResponseForcePasswordChange) {
		this.loginResponseForcePasswordChange = loginResponseForcePasswordChange;
	}
	
	public String getLoginResponseIsAccountLocked() {
		return loginResponseIsAccountLocked;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.loginResponseIsAccountLocked)
	public void setLoginResponseIsAccountLocked(String loginResponseIsAccountLocked) {
		this.loginResponseIsAccountLocked = loginResponseIsAccountLocked;
	}
	
	public OwnerOrganization getOwnerOrganization() {
		return ownerOrganization;
	}
	
	@XmlElement(name = CommVaultXMLTags.OwnerOrganizationObjectTag)
	public void setOwnerOrganization(OwnerOrganization ownerOrganization) {
		this.ownerOrganization = ownerOrganization;
	}
	
	public ProviderOrganization getProviderOrganization() {
		return providerOrganization;
	}
	
	@XmlElement(name = CommVaultXMLTags.ProviderOrganizationObjectTag)
	public void setProviderOrganization(ProviderOrganization providerOrganization) {
		this.providerOrganization = providerOrganization;
	}
	
}

