package opcon.commvault.connector.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import opcon.commvault.connector.constants.CommVaultConstants;
import opcon.commvault.connector.constants.CommVaultXMLTags;

@XmlRootElement(name = CommVaultXMLTags.ProcessingInstructionInfoObjectTag)
@XmlType(propOrder = {"name","value"})
public class ProcessingInstructionInfo {

	private String name = CommVaultConstants.EMPTY_STRING;
	private String value = CommVaultConstants.EMPTY_STRING;
	
	public String getName() {
		return name;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.ProcessingInstructionInfoNameTag)
	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.ProcessingInstructionInfoValueTag)
	public void setValue(String value) {
		this.value = value;
	}

}
