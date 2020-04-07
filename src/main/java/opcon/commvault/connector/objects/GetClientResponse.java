package opcon.commvault.connector.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import opcon.commvault.connector.constants.CommVaultXMLTags;

@XmlRootElement(name = CommVaultXMLTags.ClientPropertiesObjectTag)
@XmlType(propOrder = {"clientProperties"})
public class GetClientResponse {
	
	List<ClientProperties> clientPropertiesList = new ArrayList<ClientProperties>();

	public List<ClientProperties> getClientPropertiesList() {
		return clientPropertiesList;
	}

	@XmlElement(name = CommVaultXMLTags.ClientPropertiesObjectTag)
	public void setClientProperties(List<ClientProperties> clientPropertiesList) {
		this.clientPropertiesList = clientPropertiesList;
	}

}
