package opcon.commvault.connector.modules;

import opcon.commvault.connector.objects.CreateTaskResponse;
import opcon.commvault.connector.objects.GenericResponse;
import opcon.commvault.connector.objects.GenericResponse1;
import opcon.commvault.connector.objects.GetClientResponse;
import opcon.commvault.connector.objects.GetJobSummaryResponse;
import opcon.commvault.connector.objects.LoginResponse;


public class ConnectionFactoryReturnData {
	
	private int webSvcReturnCode = 0;
	private LoginResponse loginResponse = new LoginResponse();
	private GenericResponse genericResponse = new GenericResponse();
	private GenericResponse1 genericResponse1 = new GenericResponse1();
	private CreateTaskResponse createTaskResponse = new CreateTaskResponse();
	private GetJobSummaryResponse getJobSummaryResponse = new GetJobSummaryResponse();
	private GetClientResponse getClientResponse = new GetClientResponse();

	public int getWebSvcReturnCode() {
		return webSvcReturnCode;
	}

	public void setWebSvcReturnCode(int webSvcReturnCode) {
		this.webSvcReturnCode = webSvcReturnCode;
	}

	public LoginResponse getLoginResponse() {
		return loginResponse;
	}

	public void setLoginResponse(LoginResponse loginResponse) {
		this.loginResponse = loginResponse;
	}

	public GenericResponse getGenericResponse() {
		return genericResponse;
	}

	public void setGenericResponse(GenericResponse genericResponse) {
		this.genericResponse = genericResponse;
	}

	public CreateTaskResponse getCreateTaskResponse() {
		return createTaskResponse;
	}

	public void setCreateTaskResponse(CreateTaskResponse createTaskResponse) {
		this.createTaskResponse = createTaskResponse;
	}

	public GetJobSummaryResponse getGetJobSummaryResponse() {
		return getJobSummaryResponse;
	}

	public void setGetJobSummaryResponse(GetJobSummaryResponse getJobSummaryResponse) {
		this.getJobSummaryResponse = getJobSummaryResponse;
	}

	public GetClientResponse getGetClientResponse() {
		return getClientResponse;
	}

	public void setGetClientResponse(GetClientResponse getClientResponse) {
		this.getClientResponse = getClientResponse;
	}

	public GenericResponse1 getGenericResponse1() {
		return genericResponse1;
	}

	public void setGenericResponse1(GenericResponse1 genericResponse1) {
		this.genericResponse1 = genericResponse1;
	}
	
	
}
