package opcon.commvault.connector.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import opcon.commvault.connector.constants.CommVaultXMLTags;

@XmlRootElement(name = CommVaultXMLTags.JobObjectTag)
@XmlType(propOrder = {"jobSummary"})
public class Job {
	
	private JobSummary jobSummary = null;

	public JobSummary getJobSummary() {
		return jobSummary;
	}

	@XmlElement(name = CommVaultXMLTags.JobSummaryObjectTag)
	public void setJobSummary(JobSummary jobSummary) {
		this.jobSummary = jobSummary;
	}

}
