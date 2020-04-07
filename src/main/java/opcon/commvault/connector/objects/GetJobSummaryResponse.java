package opcon.commvault.connector.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import opcon.commvault.connector.constants.CommVaultConstants;
import opcon.commvault.connector.constants.CommVaultXMLTags;

@XmlRootElement(name = CommVaultXMLTags.GetJobSummaryResponseObjectTag)
@XmlType(propOrder = {"getJobSummaryTotalRecordsWithoutPaging","jobs","anyOtherObjects"})
public class GetJobSummaryResponse {
	
	private String getJobSummaryTotalRecordsWithoutPaging = CommVaultConstants.EMPTY_STRING;
	private Job jobs = null;
	@XmlAnyElement(lax=true)
	private List<Object> anyOtherObjects = new ArrayList<Object>();
	
	public String getGetJobSummaryTotalRecordsWithoutPaging() {
		return getJobSummaryTotalRecordsWithoutPaging;
	}

	@XmlAttribute(name = CommVaultXMLTags.GetJobSummaryResponseTotalRecordsWithoutPagingTag)
	public void setGetJobSummaryTotalRecordsWithoutPaging(
			String getJobSummaryTotalRecordsWithoutPaging) {
		this.getJobSummaryTotalRecordsWithoutPaging = getJobSummaryTotalRecordsWithoutPaging;
	}
	
	public Job getJobs() {
		return jobs;
	}

	@XmlElement(name = CommVaultXMLTags.JobObjectTag)
	public void setJobs(Job jobs) {
		this.jobs = jobs;
	}

}
