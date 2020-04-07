package opcon.commvault.connector.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import opcon.commvault.connector.constants.CommVaultConstants;
import opcon.commvault.connector.constants.CommVaultXMLTags;

@XmlRootElement(name = CommVaultXMLTags.ClientEntityObjectTag)
@XmlType(propOrder = {"_type","clientId","clientName"})
public class ClientEntity {

	String _type_ = CommVaultConstants.EMPTY_STRING;
	String clientId = CommVaultConstants.EMPTY_STRING;
	String clientName = CommVaultConstants.EMPTY_STRING;
	
	public String get_type_() {
		return _type_;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.ClientEntityTypeTag)
	public void set_type_(String _type_) {
		this._type_ = _type_;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.ClientEntityClientIdTag)
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getClientName() {
		return clientName;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.ClientEntityCleintNameTag)
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
}
