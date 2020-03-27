package opcon.commvault.connector.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import opcon.commvault.connector.constants.CommVaultConstants;
import opcon.commvault.connector.constants.CommVaultXMLTags;

@XmlRootElement(name = CommVaultXMLTags.ProviderOrganizationObjectTag)
@XmlType(propOrder = {"providerOrganizationProviderId","providerOrganizationProviderDomainName"})
public class ProviderOrganization {

	private String providerOrganizationProviderId = CommVaultConstants.EMPTY_STRING;
	private String providerOrganizationProviderDomainName = CommVaultConstants.EMPTY_STRING;

	public String getProviderOrganizationProviderId() {
		return providerOrganizationProviderId;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.ProviderOrganizationProviderId)
	public void setProviderOrganizationProviderId(String providerOrganizationProviderId) {
		this.providerOrganizationProviderId = providerOrganizationProviderId;
	}
	
	public String getProviderOrganizationProviderDomainName() {
		return providerOrganizationProviderDomainName;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.ProviderOrganizationProviderDomainName)
	public void setProviderOrganizationProviderDomainName(
			String providerOrganizationProviderDomainName) {
		this.providerOrganizationProviderDomainName = providerOrganizationProviderDomainName;
	}

}
