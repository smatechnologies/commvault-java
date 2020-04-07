package opcon.commvault.connector.interfaces;

public interface IJobExecutionResult {

	public String getOpConUniqueId();
	public void setOpConUniqueId(String opConUniqueId);
	public int getStatus();
	public void setStatus(int status);
	public String getDescription();
	public void setDescription(String description);

}
