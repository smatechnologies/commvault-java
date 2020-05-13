package opcon.commvault.connector.configuration;

public class CommVaultConfiguration {

	private static CommVaultConfiguration _CommVaultConfiguration = null;

	private String connectorName = null;
	private String serverAddress = null;
	private String serverName = null;
	private String commVaultUserDomain = null;
	private String commVaultUser = null;
	private String commVaultUserPassword = null;
	private String rootDirectory = null;
	private String xmlBackupDefinitionsDirectory = null;
	private String msAgentRootDirectory = null;
	private int sessionRetryValue = 5;
	private int initialPollDelay = 10;
	private int pollInterval = 5;
	private boolean useTls = false;
	private boolean debug = false;

	protected CommVaultConfiguration() {
	}

	public static CommVaultConfiguration getInstance() {
		if(_CommVaultConfiguration == null) {
			_CommVaultConfiguration = new CommVaultConfiguration();
		}
		return _CommVaultConfiguration;
	}

	public String getConnectorName() {
		return connectorName;
	}

	public void setConnectorName(String connectorName) {
		this.connectorName = connectorName;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getCommVaultUserDomain() {
		return commVaultUserDomain;
	}

	public void setCommVaultUserDomain(String commVaultUserDomain) {
		this.commVaultUserDomain = commVaultUserDomain;
	}

	public String getCommVaultUser() {
		return commVaultUser;
	}

	public void setCommVaultUser(String commVaultUser) {
		this.commVaultUser = commVaultUser;
	}

	public String getCommVaultUserPassword() {
		return commVaultUserPassword;
	}

	public void setCommVaultUserPassword(String commVaultUserPassword) {
		this.commVaultUserPassword = commVaultUserPassword;
	}

	public String getRootDirectory() {
		return rootDirectory;
	}

	public void setRootDirectory(String rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	public String getXmlBackupDefinitionsDirectory() {
		return xmlBackupDefinitionsDirectory;
	}

	public void setXmlBackupDefinitionsDirectory(
			String xmlBackupDefinitionsDirectory) {
		this.xmlBackupDefinitionsDirectory = xmlBackupDefinitionsDirectory;
	}

	public String getMsAgentRootDirectory() {
		return msAgentRootDirectory;
	}

	public void setMsAgentRootDirectory(String msAgentRootDirectory) {
		this.msAgentRootDirectory = msAgentRootDirectory;
	}

	public int getSessionRetryValue() {
		return sessionRetryValue;
	}

	public void setSessionRetryValue(int sessionRetryValue) {
		this.sessionRetryValue = sessionRetryValue;
	}

	public int getInitialPollDelay() {
		return initialPollDelay;
	}

	public void setInitialPollDelay(int initialPollDelay) {
		this.initialPollDelay = initialPollDelay;
	}

	public int getPollInterval() {
		return pollInterval;
	}

	public void setPollInterval(int pollInterval) {
		this.pollInterval = pollInterval;
	}

	public boolean isUseTls() {
		return useTls;
	}

	public void setUseTls(boolean useTls) {
		this.useTls = useTls;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

}
