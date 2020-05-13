package opcon.commvault.connector.interfaces;

public interface ISMAXMLSerializer {
	
	public Object deserializeXML(String xml,Class<?> clazz) throws Exception;
	public String serializeXML(Object obj) throws Exception;

}
