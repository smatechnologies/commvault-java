package opcon.commvault.connector.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import opcon.commvault.connector.constants.CommVaultConstants;
import opcon.commvault.connector.constants.CommVaultXMLTags;


@XmlRootElement(name = CommVaultXMLTags.GenericResponseObjectTag)
@XmlType(propOrder = {"errorMessage","errorCode","anyOtherObjects"})
public class GenericResponse {
	
	private String errorMessage = CommVaultConstants.EMPTY_STRING;
	private String errorCode = CommVaultConstants.EMPTY_STRING;
	@XmlAnyElement(lax=true)
	private List<Object> anyOtherObjects = new ArrayList<Object>();

	public String getErrorMessage() {
		return errorMessage;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.GenericResponseErrorMsgTag)
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.GenericResponseErrorCodeTag)
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}

