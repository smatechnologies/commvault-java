package opcon.commvault.connector.factories;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import opcon.commvault.connector.interfaces.ISMAXMLSerializer;

public class XMLSerializer implements ISMAXMLSerializer {
	
	private static String UTF8_CHARSET = "UTF-8";
	
	public Object deserializeXML(
			String xml,Class<?> clazz
			) throws Exception {
		
		ByteArrayInputStream in = null;
		
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller u = context.createUnmarshaller();
			in = new ByteArrayInputStream(xml.getBytes(UTF8_CHARSET));
			return u.unmarshal(in);
		} catch (Exception ex) {
			throw new Exception(ex);
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException ioex) {
					throw new Exception(ioex);
				}
			}
		}
	}

	public String serializeXML(
			Object obj
			) throws Exception {
		
		ByteArrayOutputStream out = null;
		
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			out = new ByteArrayOutputStream();
			m.marshal(obj, out);
			return out.toString(UTF8_CHARSET);
		} catch (Exception ex) {
			throw new Exception(ex);
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException ioex) {
					throw new Exception(ioex);
				}
			}
		}
	}
	
}
