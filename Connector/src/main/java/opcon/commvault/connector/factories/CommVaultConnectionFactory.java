package opcon.commvault.connector.factories;

import java.text.MessageFormat;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import opcon.commvault.connector.configuration.CommVaultConfiguration;
import opcon.commvault.connector.constants.CommVaultConstants;
import opcon.commvault.connector.constants.CommVaultMessages;
import opcon.commvault.connector.exception.WebServicesException;
import opcon.commvault.connector.interfaces.ICommVaultConnectionFactory;
import opcon.commvault.connector.interfaces.ISMAXMLSerializer;
import opcon.commvault.connector.modules.AllCertificatesTrustManager;
import opcon.commvault.connector.modules.ConnectionFactoryReturnData;
import opcon.commvault.connector.objects.CreateTaskResponse;
import opcon.commvault.connector.objects.GenericResponse;
import opcon.commvault.connector.objects.GenericResponse1;
import opcon.commvault.connector.objects.GetClientResponse;
import opcon.commvault.connector.objects.GetJobSummaryResponse;
import opcon.commvault.connector.objects.Login;
import opcon.commvault.connector.objects.LoginResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bvanhinsbergen
 *
 */
public class CommVaultConnectionFactory implements ICommVaultConnectionFactory {

	private static final String LOGIN_URL = "http://{0}/webconsole/api/Login";
	private static final String LOGIN_TLS_URL = "https://{0}/webconsole/api/Login";
	private static final String LOGOUT_URL = "http://{0}/webconsole/api/Logout";
	private static final String LOGOUT_TLS_URL = "https://{0}/webconsole/api/Logout";
	private static final String CREATE_TASK_URL = "http://{0}/webconsole/api/CreateTask";
	private static final String CREATE_TASK_TLS_URL = "https://{0}/webconsole/api/CreateTask";
	private static final String QCOMMAND_URL = "http://{0}/webconsole/api/QCommand/qoperation execute";
	private static final String QCOMMAND_TLS_URL = "https://{0}/webconsole/api/QCommand/qoperation execute";
	private static final String GET_JOB_SUMMARY_URL = "http://{0}/webconsole/api/Job/{1}";
	private static final String GET_JOB_SUMMARY_TLS_URL = "https://{0}/webconsole/api/Job/{1}";
	private static final String GET_CLIENT_URL = "http://{0}/webconsole/api/Client";
	private static final String GET_CLIENT_TLS_URL = "https://{0}/webconsole/api/Client";
	
	private static final String HEADER_FIELD_NAME_ACCEPT = "Accept";
	private static final String HEADER_FIELD_NAME_CONTENT_TYPE = "Content-Type";
	private static final String HEADER_FIELD_NAME_HOST = "Host";
	private static final String HEADER_FIELD_NAME_AUTHTOKEN = "Authtoken";
	
	private final static Logger LOG = LoggerFactory.getLogger(CommVaultConnectionFactory.class);
	private CommVaultConfiguration _CommVaultConfiguration = CommVaultConfiguration.getInstance();
	private ISMAXMLSerializer _ISMAXMLSerializer = new XMLSerializer();
	
	private String authenticationToken = null;
	
	public ConnectionFactoryReturnData authenticateUser(
			String serverAddress,
			boolean isUsingTls,
			Login login
			) throws Exception, WebServicesException {
		
		ConnectionFactoryReturnData returnLoginData = new ConnectionFactoryReturnData();
		Client client = null;
		LoginResponse returnLogin = null;
		WebTarget target = null;
		Response response = null;
		int returnCode = 404;
		String URL = null;

		try {
			if(isUsingTls) {
				URL = MessageFormat.format(LOGIN_TLS_URL,serverAddress);
				client = createAllCertificatesClient();
				target = client.target(URL);
			} else {
				URL = MessageFormat.format(LOGIN_URL,serverAddress);
				client = ClientBuilder.newClient();
				target = client.target(URL);
			}
			LOG.info(MessageFormat.format(CommVaultMessages.AuthenticationMsg, login.getLoginUserName(),URL));
			String loginData = _ISMAXMLSerializer.serializeXML(login);
			if(_CommVaultConfiguration.isDebug()) {
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "URL {" + URL + "}"));
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Data {" + loginData + "}"));
			}
			response = target
					   .request(MediaType.APPLICATION_XML)
  					   .header(HEADER_FIELD_NAME_ACCEPT, MediaType.APPLICATION_XML)
  					   .header(HEADER_FIELD_NAME_CONTENT_TYPE, MediaType.APPLICATION_XML)
					   .post(Entity.xml(loginData));
			returnCode = response.getStatus();
			String authenticateData = response.readEntity(String.class);
			if((returnCode == 200) || (returnCode == 201)) {
				LOG.info(MessageFormat.format(CommVaultMessages.AuthenticationSuccesfulMsg, login.getLoginUserName()));
				if(_CommVaultConfiguration.isDebug()) {
					LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "code(" + String.valueOf(returnCode) + ") ret data " + authenticateData));
				}
				returnLogin = (LoginResponse) _ISMAXMLSerializer.deserializeXML(authenticateData,LoginResponse.class);
				authenticationToken = returnLogin.getLoginResponseToken(); 
				returnLoginData.setWebSvcReturnCode(200);
				returnLoginData.setLoginResponse(returnLogin);
			} else {
				LOG.error(MessageFormat.format(CommVaultMessages.AuthenticationFailedMsg, login.getLoginUserName(), returnCode));
				returnLoginData.setWebSvcReturnCode(returnCode);
				returnLoginData.setLoginResponse(returnLogin);
				if(_CommVaultConfiguration.isDebug()) {
					LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, " code(" + String.valueOf(returnCode) + ") ret data " + authenticateData));
				}
			}
		} catch (javax.ws.rs.ProcessingException jpex) {
			jpex.printStackTrace();
			throw new WebServicesException(jpex);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
		return returnLoginData;
	}	// END : authenticateUser

	public ConnectionFactoryReturnData logout(
			String serverAddress,
			String host,
			boolean isUsingTls
			) throws Exception, WebServicesException {
		
		ConnectionFactoryReturnData returnLogoutData = new ConnectionFactoryReturnData();
		Client client = null;
		WebTarget target = null;
		Response response = null;
		int returnCode = 404;
		String URL = null;

		try {
			if(isUsingTls) {
				URL = MessageFormat.format(LOGOUT_TLS_URL,serverAddress);
				client = createAllCertificatesClient();
				target = client.target(URL);
			} else {
				URL = MessageFormat.format(LOGOUT_URL,serverAddress);
				client = ClientBuilder.newClient();
				target = client.target(URL);
			}
			LOG.info(CommVaultMessages.LogoutMsg);
			if(_CommVaultConfiguration.isDebug()) {
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "URL {" + URL + "}"));
			}
			response = target
					   .request(MediaType.APPLICATION_XML)
  					   .header(HEADER_FIELD_NAME_HOST, host)
  					   .header(HEADER_FIELD_NAME_ACCEPT, MediaType.APPLICATION_XML)
 					   .header(HEADER_FIELD_NAME_AUTHTOKEN, authenticationToken)
					   .post(Entity.text(CommVaultConstants.EMPTY_STRING));
			returnCode = response.getStatus();
			String logoutData = response.readEntity(String.class);
			if((returnCode == 200) || (returnCode == 201)) {
				LOG.info(MessageFormat.format(CommVaultMessages.LogoutCompletedMsg, logoutData));
				returnLogoutData.setWebSvcReturnCode(200);
			}
		} catch (javax.ws.rs.ProcessingException jpex) {
			jpex.printStackTrace();
			throw new WebServicesException(jpex);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
		return returnLogoutData;
	}	// END : Logout
	
	public ConnectionFactoryReturnData createTask(
			String serverAddress,
			String host,
			boolean isUsingTls,
			String taskInformation
			) throws Exception, WebServicesException {
		
		ConnectionFactoryReturnData returnCreateTaskData = new ConnectionFactoryReturnData();
		CreateTaskResponse createTaskResponse = new CreateTaskResponse();
		GenericResponse genericResponse = new GenericResponse();
		Client client = null;
		WebTarget target = null;
		Response response = null;
		int returnCode = 404;
		String URL = null;

		try {
			if(isUsingTls) {
				URL = MessageFormat.format(CREATE_TASK_TLS_URL,serverAddress);
				client = createAllCertificatesClient();
				target = client.target(URL);
			} else {
				URL = MessageFormat.format(CREATE_TASK_URL,serverAddress);
				client = ClientBuilder.newClient();
				target = client.target(URL);
			}
			LOG.info(CommVaultMessages.CreateTaskMsg);
			if(_CommVaultConfiguration.isDebug()) {
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "URL {" + URL + "}"));
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Data {" + taskInformation + "}"));
			}
			response = target
					   .request(MediaType.APPLICATION_XML)
  					   .header(HEADER_FIELD_NAME_HOST, host)
  					   .header(HEADER_FIELD_NAME_ACCEPT, MediaType.APPLICATION_XML)
 					   .header(HEADER_FIELD_NAME_AUTHTOKEN, authenticationToken)
  					   .header(HEADER_FIELD_NAME_CONTENT_TYPE, MediaType.APPLICATION_XML)
					   .post(Entity.xml(taskInformation));
			returnCode = response.getStatus();
			String createTaskData = response.readEntity(String.class);
			if(_CommVaultConfiguration.isDebug()) {
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "createTask ret data {" + createTaskData + "}"));
			}
			if((returnCode == 200) || (returnCode == 201)) {
				if(createTaskData.contains("<TMMsg_GenericResp")) {
					genericResponse = (GenericResponse) _ISMAXMLSerializer.deserializeXML(createTaskData,GenericResponse.class);
					LOG.info(MessageFormat.format(CommVaultMessages.CreateTaskFailedMsg, genericResponse.getErrorMessage(), genericResponse.getErrorCode()));
					if(_CommVaultConfiguration.isDebug()) {
						LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "code(" + String.valueOf(returnCode) + ") ret data " + createTaskData));
					}
					returnCreateTaskData.setWebSvcReturnCode(500);
					returnCreateTaskData.setGenericResponse(genericResponse);
				} else {
					createTaskResponse = (CreateTaskResponse) _ISMAXMLSerializer.deserializeXML(createTaskData,CreateTaskResponse.class);
					LOG.info(MessageFormat.format(CommVaultMessages.CreateTaskSuccessfulMsg, createTaskResponse.getTaskId(), createTaskResponse.getJobId().getValue()));
					if(_CommVaultConfiguration.isDebug()) {
						LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "code(" + String.valueOf(returnCode) + ") ret data " + createTaskData));
					}
					returnCreateTaskData.setWebSvcReturnCode(200);
					returnCreateTaskData.setCreateTaskResponse(createTaskResponse);
				}
			} else {
				LOG.error(MessageFormat.format(CommVaultMessages.CreateTaskFailedMsg, returnCode));
				returnCreateTaskData.setWebSvcReturnCode(returnCode);
				returnCreateTaskData.setCreateTaskResponse(createTaskResponse);
				if(_CommVaultConfiguration.isDebug()) {
					LOG.error(MessageFormat.format(CommVaultMessages.DebugLine, " code(" + String.valueOf(returnCode) + ") ret data " + createTaskData));
				}
			}
		} catch (javax.ws.rs.ProcessingException jpex) {
			jpex.printStackTrace();
			throw new WebServicesException(jpex);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
		return returnCreateTaskData;
	}	// END : createTask
	
	public ConnectionFactoryReturnData qcommand(
			String serverAddress,
			String host,
			boolean isUsingTls,
			String taskInformation
			) throws Exception, WebServicesException {
		
		ConnectionFactoryReturnData returnQCommandData = new ConnectionFactoryReturnData();
		CreateTaskResponse createTaskResponse = new CreateTaskResponse();
		GenericResponse genericResponse = new GenericResponse();
		GenericResponse1 genericResponse1 = new GenericResponse1();
		Client client = null;
		WebTarget target = null;
		Response response = null;
		int returnCode = 404;
		String URL = null;

		try {
			if(isUsingTls) {
				URL = MessageFormat.format(QCOMMAND_TLS_URL,serverAddress);
				client = createAllCertificatesClient();
				target = client.target(URL);
			} else {
				URL = MessageFormat.format(QCOMMAND_URL,serverAddress);
				client = ClientBuilder.newClient();
				target = client.target(URL);
			}
			LOG.info(CommVaultMessages.QCommandMsg);
			if(_CommVaultConfiguration.isDebug()) {
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "URL {" + URL + "}"));
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "Data {" + taskInformation + "}"));
			}
			response = target
					   .request(MediaType.APPLICATION_XML)
  					   .header(HEADER_FIELD_NAME_HOST, host)
  					   .header(HEADER_FIELD_NAME_ACCEPT, MediaType.APPLICATION_XML)
 					   .header(HEADER_FIELD_NAME_AUTHTOKEN, authenticationToken)
  					   .header(HEADER_FIELD_NAME_CONTENT_TYPE, MediaType.APPLICATION_XML)
					   .post(Entity.xml(taskInformation));
			returnCode = response.getStatus();
			String qcommandData = response.readEntity(String.class);
			if(_CommVaultConfiguration.isDebug()) {
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "qcommand ret data {" + qcommandData + "}"));
			}
			if((returnCode == 200) || (returnCode == 201)) {
				if(qcommandData.contains("<TMMsg_GenericResp")) {
					genericResponse = (GenericResponse) _ISMAXMLSerializer.deserializeXML(qcommandData,GenericResponse.class);
					LOG.error(MessageFormat.format(CommVaultMessages.QCommandFailedMsg, genericResponse.getErrorMessage(), genericResponse.getErrorCode()));
					if(_CommVaultConfiguration.isDebug()) {
						LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "code(" + String.valueOf(returnCode) + ") ret data " + qcommandData));
					}
					returnQCommandData.setWebSvcReturnCode(500);
					returnQCommandData.setGenericResponse(genericResponse);
				} else if(qcommandData.contains("<CVGui_GenericResp")) {
					genericResponse1 = (GenericResponse1) _ISMAXMLSerializer.deserializeXML(qcommandData,GenericResponse1.class);
					LOG.error(MessageFormat.format(CommVaultMessages.QCommandFailedMsg, genericResponse1.getErrorMessage(), genericResponse1.getErrorCode()));
					if(_CommVaultConfiguration.isDebug()) {
						LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "code(" + String.valueOf(returnCode) + ") ret data " + qcommandData));
					}
					returnQCommandData.setWebSvcReturnCode(500);
					returnQCommandData.setGenericResponse1(genericResponse1);
				} else {
					createTaskResponse = (CreateTaskResponse) _ISMAXMLSerializer.deserializeXML(qcommandData,CreateTaskResponse.class);
					LOG.info(MessageFormat.format(CommVaultMessages.QCommandSuccessfulMsg, createTaskResponse.getTaskId(), createTaskResponse.getJobId().getValue()));
					if(_CommVaultConfiguration.isDebug()) {
						LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "code(" + String.valueOf(returnCode) + ") ret data " + qcommandData));
					}
					returnQCommandData.setWebSvcReturnCode(200);
					returnQCommandData.setCreateTaskResponse(createTaskResponse);
				}
			} else {
				LOG.error(MessageFormat.format(CommVaultMessages.QCommandFailedMsg, returnCode));
				returnQCommandData.setWebSvcReturnCode(returnCode);
				returnQCommandData.setCreateTaskResponse(createTaskResponse);
				if(_CommVaultConfiguration.isDebug()) {
					LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, " code(" + String.valueOf(returnCode) + ") ret data " + qcommandData));
				}
			}
		} catch (javax.ws.rs.ProcessingException jpex) {
			jpex.printStackTrace();
			throw new WebServicesException(jpex);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
		return returnQCommandData;
	}	// END : qcommand

	public ConnectionFactoryReturnData getJobSummary(
			String serverAddress,
			String host,
			boolean isUsingTls,
			Integer jobid
			) throws Exception, WebServicesException {
		
		ConnectionFactoryReturnData returnJobSummaryData = new ConnectionFactoryReturnData();
		Client client = null;
		GetJobSummaryResponse getJobSummaryResponse = null;
		WebTarget target = null;
		Response response = null;
		int returnCode = 404;
		String URL = null;

		try {
			if(isUsingTls) {
				URL = MessageFormat.format(GET_JOB_SUMMARY_TLS_URL,serverAddress,String.valueOf(jobid));
				client = createAllCertificatesClient();
				target = client.target(URL);
			} else {
				URL = MessageFormat.format(GET_JOB_SUMMARY_URL,serverAddress,String.valueOf(jobid));
				client = ClientBuilder.newClient();
				target = client.target(URL);
			}
			LOG.info(MessageFormat.format(CommVaultMessages.GetJobSummaryMsg, String.valueOf(jobid)));
			if(_CommVaultConfiguration.isDebug()) {
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "URL {" + URL + "}"));
			}
			response = target
					   .request(MediaType.APPLICATION_XML)
  					   .header(HEADER_FIELD_NAME_HOST, host)
  					   .header(HEADER_FIELD_NAME_ACCEPT, MediaType.APPLICATION_XML)
 					   .header(HEADER_FIELD_NAME_AUTHTOKEN, authenticationToken)
					   .get();
			returnCode = response.getStatus();
			String jobSummaryData = response.readEntity(String.class);
			if((returnCode == 200) || (returnCode == 201)) {
				LOG.info(MessageFormat.format(CommVaultMessages.GetJobSummarySuccessfulMsg, String.valueOf(jobid)));
				getJobSummaryResponse = (GetJobSummaryResponse) _ISMAXMLSerializer.deserializeXML(jobSummaryData,GetJobSummaryResponse.class);
				if(_CommVaultConfiguration.isDebug()) {
					LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "code(" + String.valueOf(returnCode) + ") ret data " + jobSummaryData));
				}
				returnJobSummaryData.setWebSvcReturnCode(200);
				returnJobSummaryData.setGetJobSummaryResponse(getJobSummaryResponse);
			} else {
				LOG.error(MessageFormat.format(CommVaultMessages.GetJobSummaryFailedMsg, String.valueOf(jobid), returnCode));
				returnJobSummaryData.setWebSvcReturnCode(returnCode);
				returnJobSummaryData.setGetJobSummaryResponse(getJobSummaryResponse);
				if(_CommVaultConfiguration.isDebug()) {
					LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, " code(" + String.valueOf(returnCode) + ") ret data " + jobSummaryData));
				}
			}
		} catch (javax.ws.rs.ProcessingException jpex) {
			jpex.printStackTrace();
			throw new WebServicesException(jpex);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
		return returnJobSummaryData;
	}	// END : getJobSummary

	public ConnectionFactoryReturnData getClient(
			String serverAddress,
			String host,
			boolean isUsingTls
			) throws Exception, WebServicesException {
		
		ConnectionFactoryReturnData returnJobSummaryData = new ConnectionFactoryReturnData();
		Client client = null;
		GetClientResponse getClientResponse = null;
		WebTarget target = null;
		Response response = null;
		int returnCode = 404;
		String URL = null;

		try {
			if(isUsingTls) {
				URL = MessageFormat.format(GET_CLIENT_TLS_URL,serverAddress);
				client = createAllCertificatesClient();
				target = client.target(URL);
			} else {
				URL = MessageFormat.format(GET_CLIENT_URL,serverAddress);
				client = ClientBuilder.newClient();
				target = client.target(URL);
			}
			LOG.info(CommVaultMessages.GetJobSummaryMsg);
			if(_CommVaultConfiguration.isDebug()) {
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "URL {" + URL + "}"));
			}
			response = target
					   .request(MediaType.APPLICATION_XML)
  					   .header(HEADER_FIELD_NAME_HOST, host)
  					   .header(HEADER_FIELD_NAME_ACCEPT, MediaType.APPLICATION_XML)
 					   .header(HEADER_FIELD_NAME_AUTHTOKEN, authenticationToken)
					   .get();
			returnCode = response.getStatus();
			String clientData = response.readEntity(String.class);
			
			if((returnCode == 200) || (returnCode == 201)) {
				LOG.info(CommVaultMessages.GetJobSummarySuccessfulMsg);
				getClientResponse = (GetClientResponse) _ISMAXMLSerializer.deserializeXML(clientData,GetJobSummaryResponse.class);
				if(_CommVaultConfiguration.isDebug()) {
					LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, "code(" + String.valueOf(returnCode) + ") ret data " + clientData));
				}
				returnJobSummaryData.setWebSvcReturnCode(200);
				returnJobSummaryData.setGetClientResponse(getClientResponse);
			} else {
				LOG.error(MessageFormat.format(CommVaultMessages.GetJobSummaryFailedMsg, returnCode));
				returnJobSummaryData.setWebSvcReturnCode(returnCode);
				returnJobSummaryData.setGetClientResponse(getClientResponse);
				if(_CommVaultConfiguration.isDebug()) {
					LOG.info(MessageFormat.format(CommVaultMessages.DebugLine, " code(" + String.valueOf(returnCode) + ") ret data " + clientData));
				}
			}
		} catch (javax.ws.rs.ProcessingException jpex) {
			jpex.printStackTrace();
			throw new WebServicesException(jpex);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
		return returnJobSummaryData;
	}	// END : getJobSummary
	
	/**
	 * @return
	 * @throws SMAException
	 */
	private Client createAllCertificatesClient(
			) throws Exception {
		
		Client client = null;
		SSLContext sslContext = null;
		AllCertificatesTrustManager allCertificatesTrustManager = null;
		
		try {
			allCertificatesTrustManager = new AllCertificatesTrustManager();
			sslContext = SSLContext.getInstance(CommVaultConstants.TLS_DEFINITION);
			sslContext.init(null, new javax.net.ssl.TrustManager[] { allCertificatesTrustManager }, null);
			client = ClientBuilder.newBuilder()
					.sslContext(sslContext)
					.hostnameVerifier(allCertificatesTrustManager.getHostnameVerifier())
					.build();
		} catch (Exception ex) {
			throw new Exception(ex);
		}
		return client;
	}	// END : createAllCertificatesClient
	
}
