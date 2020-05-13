package opcon.commvault.connector.interfaces;

public interface IBase64EncodeDecode {

	public String encodeBase64(String value) throws Exception;
	public String decodeBase64(byte[] bytesToDecode) throws Exception;
	
}
