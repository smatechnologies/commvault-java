package com.sma.commvault.connector;

import opcon.commvault.connector.factories.XMLSerializer;
import opcon.commvault.connector.interfaces.ISMAXMLSerializer;
import opcon.commvault.connector.objects.GenericResponse;

public class TestReturnXMLExtract {
	
	private static String data = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><TMMsg_GenericResp errorCode=\"587204050\" errorMessage=\"Failed to run task. No associations exist.\"/>";
	private static String data1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><TMMsg_GenericResp errorCode=\"587204050\" errorMessage=\"Failed to run task. No associations exist.\" exit=\"55\"/>";
	
	public static void main(String[] args) {
		ISMAXMLSerializer _ISMAXMLSerializer = new XMLSerializer();
		
		try {
			if(data.contains("<TMMsg_GenericResp")) {
				GenericResponse genericResponse = (GenericResponse) _ISMAXMLSerializer.deserializeXML(data1,GenericResponse.class);
				System.out.println("genericResponse.getErrorCode()    : " + genericResponse.getErrorCode());
				System.out.println("genericResponse.getErrorMessage() : " + genericResponse.getErrorMessage());
			} else {
				System.out.println("not found");
			}
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		
	}

}
