package opcon.commvault.connector.interfaces;

import opcon.commvault.connector.exception.WebServicesException;
import opcon.commvault.connector.modules.ConnectionFactoryReturnData;
import opcon.commvault.connector.objects.Login;

public interface ICommVaultConnectionFactory {
	
	public ConnectionFactoryReturnData authenticateUser(String serverAddress, boolean isUsingTls, Login login) throws Exception, WebServicesException;
	public ConnectionFactoryReturnData createTask(String serverAddress, String host, boolean isUsingTls, String taskInformation) throws Exception, WebServicesException;
	public ConnectionFactoryReturnData qcommand(String serverAddress, String host, boolean isUsingTls, String taskInformation) throws Exception, WebServicesException;
	public ConnectionFactoryReturnData getJobSummary(String serverAddress, String host, boolean isUsingTls, Integer jobid) throws Exception, WebServicesException; 
	public ConnectionFactoryReturnData logout(String serverAddress, String host, boolean isUsingTls) throws Exception, WebServicesException;
	public ConnectionFactoryReturnData getClient(String serverAddress, String host, boolean isUsingTls) throws Exception, WebServicesException;
	
}
