package opcon.commvault.connector.util;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.prefs.Preferences;

import opcon.commvault.connector.configuration.CommVaultConfiguration;
import opcon.commvault.connector.constants.CommVaultConstants;
import opcon.commvault.connector.constants.CommVaultMessages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommVaultUtilities {

	private static final String PasswordMissingErrorMsg = "Missing password, check job definitions";

	private final static Logger LOG = LoggerFactory.getLogger(CommVaultUtilities.class);
	private Encryption _Encryption = new Encryption();

	public CommVaultConfiguration getAgentConfigInformation(
			Preferences iniPrefs, 
			String rootDirectory
			) throws Exception {
		
		CommVaultConfiguration config = CommVaultConfiguration.getInstance();
		
		try {
			config.setConnectorName(iniPrefs.node(CommVaultConstants.CONNECTOR_HEADER).get(CommVaultConstants.CONNECTOR_NAME, null));
			config.setMsAgentRootDirectory(iniPrefs.node(CommVaultConstants.CONNECTOR_HEADER).get(CommVaultConstants.CONNECTOR_MSLSAM_ROOT_DIRECTORY, null));
			String sessionRetryValue = iniPrefs.node(CommVaultConstants.CONNECTOR_HEADER).get(CommVaultConstants.CONNECTOR_SESSION_RETRY_VALUE, null);
			if(sessionRetryValue != null) {
				config.setSessionRetryValue(Integer.parseInt(sessionRetryValue));
			}
			String initialPollDelay = iniPrefs.node(CommVaultConstants.CONNECTOR_HEADER).get(CommVaultConstants.CONNECTOR_INITIAL_POLL_DELAY, null);
			if(initialPollDelay != null) {
				config.setInitialPollDelay(Integer.parseInt(initialPollDelay));
			}
			String pollInterval = iniPrefs.node(CommVaultConstants.CONNECTOR_HEADER).get(CommVaultConstants.CONNECTOR_POLL_INTERVAL, null);
			if(pollInterval != null) {
				config.setPollInterval(Integer.parseInt(pollInterval));
			}
			String debug = iniPrefs.node(CommVaultConstants.CONNECTOR_HEADER).get(CommVaultConstants.CONNECTOR_DEBUG, null);
			if(debug.equalsIgnoreCase(CommVaultConstants.DEBUG_ON)) {
				config.setDebug(true);
			}
			
			config.setServerAddress(iniPrefs.node(CommVaultConstants.COMMVAULT_HEADER).get(CommVaultConstants.COMMVAULT_SERVER_ADDRESS, null));
			config.setServerName(iniPrefs.node(CommVaultConstants.COMMVAULT_HEADER).get(CommVaultConstants.COMMVAULT_SERVER_NAME, null));
			String tls = iniPrefs.node(CommVaultConstants.COMMVAULT_HEADER).get(CommVaultConstants.COMMVAULT_USE_TLS, null);
			if(tls.equalsIgnoreCase(CommVaultConstants.TRUE)) {
				config.setUseTls(true);
			}
			config.setCommVaultUserDomain(iniPrefs.node(CommVaultConstants.COMMVAULT_HEADER).get(CommVaultConstants.COMMVAULT_COMM_VAULT_USER_DOMAIN, null));
			String user = iniPrefs.node(CommVaultConstants.COMMVAULT_HEADER).get(CommVaultConstants.COMMVAULT_COMM_VAULT_USER, null);
			config.setCommVaultUser(decryptEncodedPassword(user));
			String password = iniPrefs.node(CommVaultConstants.COMMVAULT_HEADER).get(CommVaultConstants.COMMVAULT_COMM_VAULT_USER_PASSWORD, null);
			config.setCommVaultUserPassword(decryptEncodedPassword(password));
			config.setRootDirectory(rootDirectory);
			config.setXmlBackupDefinitionsDirectory(rootDirectory + File.separator + CommVaultConstants.XML_BACKUP_DEFINITIONS_DIRECTORY_NAME);

			if(config.isDebug()) {
				LOG.info(CommVaultMessages.SeparatorLine);
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine,"Configuration information"));
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine,"Server Address        {" + config.getServerAddress() + "}"));
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine,"Server Name           {" + config.getServerName() + "}"));
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine,"Using TLS             {" + config.isUseTls() + "}"));
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine,"CommVault User Domain {" + config.getCommVaultUserDomain() + "}"));
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine,"CommVault User        {" + config.getCommVaultUser() + "}"));
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine,"root directory        {" + config.getRootDirectory() + "}"));
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine,"xml def directory     {" + config.getXmlBackupDefinitionsDirectory() + "}"));
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine,"session retry         {" + String.valueOf(config.getSessionRetryValue()) + "}"));
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine,"initial poll delay    {" + String.valueOf(config.getInitialPollDelay()) + "}"));
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine,"poll interval         {" + String.valueOf(config.getPollInterval()) + "}"));
				LOG.info(CommVaultMessages.SeparatorLine);
			}
		} catch (Exception ex) {
			throw new Exception (ex);
		}
		return config;
	}	// END : getAgentConfigInformation

	public String decryptEncodedPassword(
			String password
			) throws Exception {
		
		String decryptedPassword = null;
		
		try {
			if(password != null) {
				if(password.length() == 0) {
					// empty password
					LOG.error(PasswordMissingErrorMsg);
					System.exit(401);
				} else {
	 				byte[] bencrypted = _Encryption.decodeHexString(password);
	 				decryptedPassword = _Encryption.decode64(bencrypted);
				}
			} else {
				LOG.error(PasswordMissingErrorMsg);
				System.exit(99);
			}
		} catch (Exception ex) {
			throw new Exception(ex);
		}
		return decryptedPassword;
	}	// END : decryptEncodedPassword
	
	public String getLogDateTimeStamp(
			) throws Exception {
		
		SimpleDateFormat timeStampFormat = new SimpleDateFormat(CommVaultConstants.LOG_DATE_TIME_FORMAT);
		String timeStamp = null;
		
		try {
			Calendar cal = Calendar.getInstance();
			timeStamp = timeStampFormat.format(cal.getTime());
		} catch (Exception ex){
			throw new Exception(ex);
		}
	    return timeStamp;
	} // END : getDateTimeStamp

	public String getExceptionDetails(
			Exception e
			) {
		
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionDetails = sw.toString();
		return exceptionDetails;
	}

}
