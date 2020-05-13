package opcon.commvault.connector.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import opcon.commvault.connector.constants.CommVaultConstants;
import opcon.commvault.connector.constants.CommVaultXMLTags;

@XmlRootElement(name = CommVaultXMLTags.ClientObjectTag)
@XmlType(propOrder = {"cvdPort","evmgrcPort","clientEntity"})
public class Client {

	String cvdPort = CommVaultConstants.EMPTY_STRING;
	String evmgrcPort = CommVaultConstants.EMPTY_STRING;
	ClientEntity clientEntity = null;
	
	public String getCvdPort() {
		return cvdPort;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.ClientCvcPortTag)
	public void setCvdPort(String cvdPort) {
		this.cvdPort = cvdPort;
	}
	
	public String getEvmgrcPort() {
		return evmgrcPort;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.ClientEvmgrcPortTag)
	public void setEvmgrcPort(String evmgrcPort) {
		this.evmgrcPort = evmgrcPort;
	}

	public ClientEntity getClientEntity() {
		return clientEntity;
	}

	@XmlElement(name = CommVaultXMLTags.ClientEntityObjectTag)
	public void setClientEntity(ClientEntity clientEntity) {
		this.clientEntity = clientEntity;
	}
	
}

