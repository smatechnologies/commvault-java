package opcon.commvault.connector.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import opcon.commvault.connector.constants.CommVaultConstants;
import opcon.commvault.connector.constants.CommVaultXMLTags;

@XmlRootElement(name = CommVaultXMLTags.LoginObjectTag)
@XmlType(propOrder = {"loginMode","loginDomain","loginUserName","loginPassword","loginCommServer"})
public class Login {
	
	private String loginMode = "Webconsole";
	private String loginDomain = CommVaultConstants.EMPTY_STRING;
	private String loginUserName = CommVaultConstants.EMPTY_STRING;
	private String loginPassword = CommVaultConstants.EMPTY_STRING;
	private String loginCommServer = CommVaultConstants.EMPTY_STRING;

	public String getLoginMode() {
		return loginMode;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.LoginModeTag)
	public void setLoginMode(String loginMode) {
		this.loginMode = loginMode;
	}
	
	public String getLoginDomain() {
		return loginDomain;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.LoginDomainTag)
	public void setLoginDomain(String loginDomain) {
		this.loginDomain = loginDomain;
	}
	
	public String getLoginUserName() {
		return loginUserName;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.LoginUserNameTag)
	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}
	
	public String getLoginPassword() {
		return loginPassword;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.LoginPasswordTag)
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	
	public String getLoginCommServer() {
		return loginCommServer;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.LoginCommServerTag)
	public void setLoginCommServer(String loginCommServer) {
		this.loginCommServer = loginCommServer;
	}

}
