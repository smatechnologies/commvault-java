package opcon.commvault.connector.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



import opcon.commvault.connector.constants.CommVaultConstants;
import opcon.commvault.connector.constants.CommVaultXMLTags;

@XmlRootElement(name = CommVaultXMLTags.JobIdObjectTag)
@XmlType(propOrder = {"value"})
public class JobId {

	private String value = CommVaultConstants.EMPTY_STRING;

	public String getValue() {
		return value;
	}

	@XmlAttribute(name = CommVaultXMLTags.JobIdValueTag)
	public void setValue(String value) {
		this.value = value;
	}


}
