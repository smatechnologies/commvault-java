package opcon.commvault.connector.factories;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import opcon.commvault.connector.modules.UpdateJobStatusParameters;
import opcon.commvault.connector.util.CommVaultUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateJobStatus implements Runnable {
	
	private static final String ExecuteExceptionErrorLogMsg = "Exception {0}";																					
	
	private final static Logger LOG = LoggerFactory.getLogger(UpdateJobStatus.class);
	private CommVaultUtilities _CommVaultUtilities = new CommVaultUtilities();
	private UpdateJobStatusParameters statusParams = new UpdateJobStatusParameters();

	/**
	 * @param params
	 */
	public UpdateJobStatus(UpdateJobStatusParameters params) {
		statusParams = params;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public void run() {
		List<String> commands = new ArrayList<String>();
		
		try {
			commands.add(statusParams.getStatusPath());
			commands.add(statusParams.getStatusMessage());
			ProcessBuilder processBuilder = new ProcessBuilder(commands);
		    Process process = processBuilder.start();
			try {
				process.waitFor();
			} catch (Throwable ex) {
				LOG.error(MessageFormat.format(ExecuteExceptionErrorLogMsg,ex.getMessage()));										
				throw new Exception(ex.getMessage());
			}
		} catch (Exception ex) {
				LOG.error(MessageFormat.format(ExecuteExceptionErrorLogMsg,_CommVaultUtilities.getExceptionDetails(ex)));										
		}
	} // end : run

}
