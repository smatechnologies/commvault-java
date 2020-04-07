package opcon.commvault.connector.factories;

import opcon.commvault.connector.interfaces.IBase64EncodeDecode;

import org.apache.commons.codec.binary.Base64;

public class Base64EncodeDecode implements IBase64EncodeDecode {
	
	public String encodeBase64(
			String value
			) throws Exception {
		
		String encoded = null;
		
		try {
			byte[] bytesToEncode = value.getBytes();
			byte[] encodedBytes = Base64.encodeBase64(bytesToEncode);
			encoded = new String(encodedBytes);
			return encoded;
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}	// END : encodeBase64

	public String decodeBase64(
			byte[] bytesToDecode
			) throws Exception {
		
		String decoded = null;
		
		try {
			byte[] decodedBytes = Base64.decodeBase64(bytesToDecode);
			decoded = new String(decodedBytes);
			return decoded;
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}	// END : decodeBase64

}
