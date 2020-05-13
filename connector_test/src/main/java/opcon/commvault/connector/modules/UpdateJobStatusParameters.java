package opcon.commvault.connector.modules;

public class UpdateJobStatusParameters {

	private String statusPath = null;
	private String statusMessage = null;
	
	public String getStatusPath() {
		return statusPath;
	}
	
	public void setStatusPath(String statusPath) {
		this.statusPath = statusPath;
	}
	
	public String getStatusMessage() {
		return statusMessage;
	}
	
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
}
