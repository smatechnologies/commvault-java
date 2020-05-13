package opcon.commvault.connector.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import opcon.commvault.connector.constants.CommVaultXMLTags;

@XmlRootElement(name = CommVaultXMLTags.ClientPropertiesObjectTag)
@XmlType(propOrder = {"client","clientProps"})
public class ClientProperties {
	
	Client client = null;
	ClientProps clientProps = null;

	public Client getClient() {
		return client;
	}

	@XmlElement(name = CommVaultXMLTags.ClientEntityObjectTag)
	public void setClient(Client client) {
		this.client = client;
	}

	public ClientProps getClientProps() {
		return clientProps;
	}

	@XmlElement(name = CommVaultXMLTags.ClientPropsObjectTag)
	public void setClientProps(ClientProps clientProps) {
		this.clientProps = clientProps;
	}

}

