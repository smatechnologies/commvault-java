package opcon.commvault.connector.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import opcon.commvault.connector.constants.CommVaultConstants;
import opcon.commvault.connector.constants.CommVaultXMLTags;

@XmlRootElement(name = CommVaultXMLTags.ClientPropsObjectTag)
@XmlType(propOrder = {"enableAccessControl"})
public class ClientProps {
	
	String enableAccessControl = CommVaultConstants.EMPTY_STRING;

	public String getEnableAccessControl() {
		return enableAccessControl;
	}

	@XmlAttribute(name = CommVaultXMLTags.ClientPropsEnableAccessControlTag)
	public void setEnableAccessControl(String enableAccessControl) {
		this.enableAccessControl = enableAccessControl;
	}
	
}
