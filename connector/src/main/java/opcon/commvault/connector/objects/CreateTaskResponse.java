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

@XmlRootElement(name = CommVaultXMLTags.CreateTaskResponseObjectTag)
@XmlType(propOrder = {"taskId","jobId","anyOtherObjects"})
public class CreateTaskResponse {

	private String taskId = CommVaultConstants.EMPTY_STRING;
	private JobId jobId = null;
	@XmlAnyElement(lax=true)
	private List<Object> anyOtherObjects = new ArrayList<Object>();

	public String getTaskId() {
		return taskId;
	}
	
	@XmlAttribute(name = CommVaultXMLTags.CreateTaskResponseTaskIdTag)
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public JobId getJobId() {
		return jobId;
	}
	
	@XmlElement(name = CommVaultXMLTags.JobIdObjectTag)
	public void setJobId(JobId jobId) {
		this.jobId = jobId;
	}
	
}
