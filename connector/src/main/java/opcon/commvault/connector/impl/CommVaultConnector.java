package opcon.commvault.connector.impl;

import java.io.File;
import java.text.MessageFormat;
import java.util.prefs.Preferences;

import opcon.commvault.connector.arguments.CommVaultArguments;
import opcon.commvault.connector.configuration.CommVaultConfiguration;
import opcon.commvault.connector.constants.CommVaultConstants;
import opcon.commvault.connector.constants.CommVaultMessages;
import opcon.commvault.connector.modules.CommVaultJobStatus;
import opcon.commvault.connector.util.CommVaultUtilities;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;


public class CommVaultConnector {
	
	private static CommVaultConfiguration _Config = CommVaultConfiguration.getInstance();
	private final static Logger LOG = LoggerFactory.getLogger(CommVaultConnector.class);

	public static void main(String[] args) {
		CommVaultJobExecutorImpl _CommVaultJobExecutorImpl = new CommVaultJobExecutorImpl();
		CommVaultArguments _CommVaultArguments = new CommVaultArguments();
		JCommander jcCommVaultArguments = null;
		CommVaultUtilities _CommVaultUtilities = new CommVaultUtilities();
		String rootDirectory = null;
		int result = -1; 
		
		try {
    		// get the arguments
			jcCommVaultArguments = JCommander.newBuilder()
					.addObject(_CommVaultArguments)
					.build();
			jcCommVaultArguments.parse(args);
			System.setProperty(CommVaultConstants.ALLOW_RESTRICTED_HEADERS_SYSTEM_PROPERTY_NAME, CommVaultConstants.TRUE_LOWER_CASE);
			rootDirectory = System.getProperty(CommVaultConstants.SYSTEM_USER_DIRECTORY);
			String configFileName = rootDirectory + File.separator + CommVaultConstants.CONFIG_FILE_NAME;
			// go get connector information from Agent.config file
        	Preferences boxiIniPrefs = new IniPreferences(new Ini(new File(configFileName)));
        	// save the extracted information the configuration
        	_Config = _CommVaultUtilities.getAgentConfigInformation(boxiIniPrefs, rootDirectory);
        	LOG.info(CommVaultMessages.SeparatorLine);
        	LOG.info(MessageFormat.format(CommVaultMessages.CommVaultConnectorVersionMsg,CommVaultConstants.SOFTWARE_VERSION));
        	LOG.info(CommVaultMessages.SeparatorLine);
        	LOG.info(MessageFormat.format(CommVaultMessages.CommVaultServerAddressMsg,_Config.getServerAddress()));
        	LOG.info(MessageFormat.format(CommVaultMessages.CommVaultServerNameMsg,_Config.getServerName()));
        	LOG.info(MessageFormat.format(CommVaultMessages.CommVaultXMLTemplateMsg,_CommVaultArguments.getCommVaultXmlTemplate()));
        	LOG.info(MessageFormat.format(CommVaultMessages.CommVaultBackupsetNameMsg,_CommVaultArguments.getCommVaultBackupsetName()));
        	LOG.info(MessageFormat.format(CommVaultMessages.CommVaultClientNameMsg,_CommVaultArguments.getCommVaultClientName()));
        	LOG.info(MessageFormat.format(CommVaultMessages.CommVaultSubClientMsg,_CommVaultArguments.getCommVaultSubClientName()));
        	LOG.info(MessageFormat.format(CommVaultMessages.CommVaultInstanceMsg,_CommVaultArguments.getCommVaultInstance()));
        	LOG.info(MessageFormat.format(CommVaultMessages.CommVaultBackupTypeMsg,_CommVaultArguments.getCommVaultBackupType()));
        	LOG.info(CommVaultMessages.SeparatorLine);
			result = _CommVaultJobExecutorImpl.startJob(_CommVaultArguments);
			LOG.info(CommVaultMessages.SeparatorLine);
			LOG.info(MessageFormat.format(CommVaultMessages.CommVaultConnectorCompletionMsg,String.valueOf(result), CommVaultJobStatus.fromValue(result).toString()));
			LOG.info(CommVaultMessages.SeparatorLine);
		} catch (com.beust.jcommander.ParameterException pe) {
			jcCommVaultArguments.usage();
			System.exit(1);
		} catch (Exception ex) {
			LOG.error(MessageFormat.format(CommVaultMessages.ExceptionDetailsLine,_CommVaultUtilities.getExceptionDetails(ex)));
			System.exit(1);
		}
		System.exit(result);
	}


}
