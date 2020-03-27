package opcon.commvault.connector.modules;

import opcon.commvault.connector.interfaces.IJobExecutionResult;

public class JobExecutionResult implements IJobExecutionResult {

	private int status = -1;
	private String description = null;
	private String opConUniqueId = null;
	
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;		
	}

	public String getOpConUniqueId() {
		return this.opConUniqueId;
	}

	public void setOpConUniqueId(String opConUniqueId) {
		this.opConUniqueId = opConUniqueId;
		
	}

}
