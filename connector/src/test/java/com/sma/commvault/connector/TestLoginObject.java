package com.sma.commvault.connector;

import opcon.commvault.connector.factories.Base64EncodeDecode;
import opcon.commvault.connector.factories.XMLSerializer;
import opcon.commvault.connector.interfaces.IBase64EncodeDecode;
import opcon.commvault.connector.interfaces.ISMAXMLSerializer;
import opcon.commvault.connector.objects.GetJobSummaryResponse;
import opcon.commvault.connector.objects.Login;
import opcon.commvault.connector.objects.LoginResponse;

public class TestLoginObject {

	private static String Response = "<DM2ContentIndexing_CheckCredentialResp aliasName=\"1\" userGUID=\"99965E43-C535-49F6-900C-2FFE77002549\" loginAttempts=\"0\" remainingLockTime=\"0\" userName=\"admin\" " + 
									"providerType=\"1\" ccn=\"0\" token=\"QSDK 321865d9d0e468a7b9b2413ef52201f434b2518ea74e72e3bda4a50beb9f282898b75e7f1106d9538a67736725349353d374fd34e163db118bdf2a3f75728cb6e62b" +
									"b6d2318acdcef999facaf4c58300edd65977339d4fe0afba86e93122bd9cc719ba0a885fb16db303bf830f699bf58d77d41f78c4febc6bfea8f6cf4f74f54956f25f12067939ea4f511bafb78d48ee441f0bdfdd33ace4" +
									"3c128bc6605823adab758541a38d08d8dea556654e3f3b8c3d091e1253ac4a0e40a4c071f6cdf7ee935ca47005b0fade0a5b50d207f4fe69b170971910055dab88f7c4exc9f8c439ba0cf2fb625e2492d0e8c55d880d8b" +
									"315aa7ecb4facc8fb98105ee9696b78d\" forcePasswordChange=\"0\" isAccountLocked=\"0\"><ownerOrganization providerId=\"0\" providerDomainName=\"Qinetix Commcell\" />" + 
									"<providerOrganization providerId=\"0\" providerDomainName=\"Qinetix Commcell\" /></DM2ContentIndexing_CheckCredentialResp>";

	private static String JobSummaryResponse = "<JobManager_JobListResponse totalRecordsWithoutPaging=\"1\"><jobs><jobSummary appTypeName=\"File System\" backupLevel=\"INCREMANTAL\" backupLevelName=\"Incremental\" " +
									"backupSetName=\"defaultBackupSet\" destClientName=\"WINTER\" isAged=\"false\" isVisible=\"true\" jobElapsedTime=\"12\" jobId=\"4\" jobStartTime=\"1423087483\" jobType=\"Backup\" " +
									"lastUpdateTime=\"1423087497\" localizedBackupLevelName=\"Incremental\" localizedOperationName=\"Backup\" localizedStatus=\"Completed\" percentComplete=\"100.000000\" percentSavings=\"0.000000\" " +
									"sizeOfApplication=\"0\" sizeOfMediaOnDisk=\"0\" status=\"Completed\" statusColor=\"black\" subclientName=\"default\" totalFailedFiles=\"0\" totalFailedFolders=\"0\" totalNumOfFiles=\"0\"> " +
									"<subclient appName=\"File System\" applicationId=\"33\" backupsetId=\"3\" backupsetName=\"defaultBackupSet\" clientId=\"2\" clientName=\"WINTER\" instanceId=\"1\" instanceName=\"DefaultInstanceName\" " +
									"subclientId=\"2\" subclientName=\"default\"/></jobSummary></jobs></JobManager_JobListResponse>";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ISMAXMLSerializer _ISMAXMLSerializer = new XMLSerializer();
		IBase64EncodeDecode _IBase64EncodeDecode = new Base64EncodeDecode();
		Login loginObject = new Login();
		
		try {
			loginObject.setLoginDomain("DOMAIN");
			loginObject.setLoginUserName("USERNAME");
			loginObject.setLoginPassword(_IBase64EncodeDecode.encodeBase64("PASSWORD"));
			String xml = _ISMAXMLSerializer.serializeXML(loginObject);
			System.out.println(xml);
			LoginResponse response  = (LoginResponse) _ISMAXMLSerializer.deserializeXML(Response, LoginResponse.class);
			String xmlr = _ISMAXMLSerializer.serializeXML(response);
			System.out.println(xmlr);
			GetJobSummaryResponse getJobSummary = (GetJobSummaryResponse) _ISMAXMLSerializer.deserializeXML(JobSummaryResponse, GetJobSummaryResponse.class);
			String xmls = _ISMAXMLSerializer.serializeXML(getJobSummary);
			System.out.println(xmls);
		} catch (Exception ex) {
			System.out.println("exception : " + ex.getMessage());
			ex.printStackTrace();
		}
	}

}
