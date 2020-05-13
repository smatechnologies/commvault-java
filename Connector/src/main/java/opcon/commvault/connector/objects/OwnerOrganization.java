package opcon.commvault.connector.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import opcon.commvault.connector.constants.CommVaultConstants;
import opcon.commvault.connector.constants.CommVaultXMLTags;

@XmlRootElement(name = CommVaultXMLTags.OwnerOrganizationObjectTag)
@XmlType(propOrder = {"ownerOrganizationProviderId","ownerOrganizationProviderDomainName"})
public class OwnerOrganization {

	private String ownerOrganizationProviderId = CommVaultConstants.EMPTY_STRING;
	private String ownerOrganizationProviderDomainName = CommVaultConstants.EMPTY_STRING;

	public String getOwnerOrganizationProviderId() {
		return ownerOrganizationProviderId;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.OwnerOrganizationProviderId)
	public void setOwnerOrganizationProviderId(String ownerOrganizationProviderId) {
		this.ownerOrganizationProviderId = ownerOrganizationProviderId;
	}
	
	public String getOwnerOrganizationProviderDomainName() {
		return ownerOrganizationProviderDomainName;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.OwnerOrganizationProviderDomainName)
	public void setOwnerOrganizationProviderDomainName(
			String ownerOrganizationProviderDomainName) {
		this.ownerOrganizationProviderDomainName = ownerOrganizationProviderDomainName;
	}

}
