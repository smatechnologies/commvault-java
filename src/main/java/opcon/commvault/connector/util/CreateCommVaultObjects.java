package opcon.commvault.connector.util;

import java.io.File;
import java.io.StringWriter;
import java.text.MessageFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import opcon.commvault.connector.arguments.CommVaultArguments;
import opcon.commvault.connector.configuration.CommVaultConfiguration;
import opcon.commvault.connector.constants.CommVaultConstants;
import opcon.commvault.connector.constants.CommVaultMessages;
import opcon.commvault.connector.factories.Base64EncodeDecode;
import opcon.commvault.connector.interfaces.IBase64EncodeDecode;
import opcon.commvault.connector.objects.Login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CreateCommVaultObjects {
	
	private static final String MODE_WEBCONSOLE = "Webconsole";
	
	private final static Logger LOG = LoggerFactory.getLogger(CreateCommVaultObjects.class);
	private CommVaultConfiguration _CommVaultConfiguration = CommVaultConfiguration.getInstance();
	private IBase64EncodeDecode _IBase64EncodeDecode = new Base64EncodeDecode();
	
	public Login createLoginObject(
			) throws Exception {
		
		Login login = new Login();
		
		try {
			if(_CommVaultConfiguration.isDebug()) {
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine,"Creating login object Mode {" +  MODE_WEBCONSOLE + "} User {" +
			    _CommVaultConfiguration.getCommVaultUser() + "}"));
			}
			login.setLoginMode(MODE_WEBCONSOLE);
			if(_CommVaultConfiguration.getCommVaultUserDomain() != null) {
				login.setLoginDomain(_CommVaultConfiguration.getCommVaultUserDomain());
			} else {
				login.setLoginDomain(CommVaultConstants.EMPTY_STRING);
			}
			login.setLoginUserName(_CommVaultConfiguration.getCommVaultUser());
			login.setLoginPassword(_IBase64EncodeDecode.encodeBase64(_CommVaultConfiguration.getCommVaultUserPassword()));
			login.setLoginCommServer(CommVaultConstants.EMPTY_STRING);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
		return login;
	}	// END :  createLoginObject
	
	public String createTaskRequestXMLString(
			CommVaultArguments arguments
			) throws Exception {
		
		String taskRequestString = null;
		
		try {
			String filename = _CommVaultConfiguration.getXmlBackupDefinitionsDirectory() + File.separator + arguments.getCommVaultXmlTemplate();
			if(_CommVaultConfiguration.isDebug()) {
				LOG.info(MessageFormat.format(CommVaultMessages.DebugLine,"Creating taskRequest xmlfile {" +  filename + "} Backupset Name {" + 
			         arguments.getCommVaultBackupsetName() + "} Client Name {" + arguments.getCommVaultClientName() +
					 "} Instance Name {" + arguments.getCommVaultInstance() + "} Subclient Name {" + arguments.getCommVaultSubClientName() + 
					 "} Backup Type {" + arguments.getCommVaultBackupType() + "}"));
			}
			// check if xml file exist
			File check = new File(filename);
			if(check.exists()) {
		    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder db = dbf.newDocumentBuilder();
		        Document doc = db.parse(new File(filename));
		        doc = updateTaskRequestXMLValues(doc, arguments);
		        // now convert to string
		        taskRequestString = convertDocumentToString(doc);
			} else {
				LOG.error(MessageFormat.format(CommVaultMessages.XMLFileDoesNotExistsErrorMsg,arguments.getCommVaultXmlTemplate()));
			}
		} catch (Exception ex) {
			throw new Exception(ex);
		}
		return taskRequestString;
	}	// END : createTaskRequestXMLString
	
    private String convertDocumentToString(
    		Document doc
    		) throws Exception {
    	
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        String output = null;
        
        try {
            transformer = tf.newTransformer();
            // below code to remove XML declaration
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            output = writer.getBuffer().toString();
		} catch (Exception ex) {
			throw new Exception(ex);
		}
        return output;
   }	// END : convertDocumentToString

    private Document updateTaskRequestXMLValues(
    		Document doc, 
    		CommVaultArguments arguments
    		) throws Exception {
    	
    	try  {
    		Node associations = doc.getElementsByTagName(CommVaultConstants.NODE_ASSOCIATIONS).item(0);
    		NodeList list = associations.getChildNodes();
    		for(int cntr = 0; cntr < list.getLength(); cntr++) {
    			Node node = list.item(cntr);
    			if(node.getNodeName().equalsIgnoreCase(CommVaultConstants.NODE_ASSOCIATIONS_BACKUPSETNAME)) {
    				if(arguments.getCommVaultBackupsetName() != null) {
    	   				node.setTextContent(arguments.getCommVaultBackupsetName());
    				}
    			} else if(node.getNodeName().equalsIgnoreCase(CommVaultConstants.NODE_ASSOCIATIONS_CLIENTNAME)) {
    				if(arguments.getCommVaultClientName() != null) {
    	   				node.setTextContent(arguments.getCommVaultClientName());
    				}
    			} else if(node.getNodeName().equalsIgnoreCase(CommVaultConstants.NODE_ASSOCIATIONS_INSTANCENAME)) {
    				if(arguments.getCommVaultInstance() != null) {
    	   				node.setTextContent(arguments.getCommVaultInstance());
    				}
    			} else if(node.getNodeName().equalsIgnoreCase(CommVaultConstants.NODE_ASSOCIATIONS_SUBCLIENTNAME)) {
    				if(arguments.getCommVaultSubClientName() != null) {
    	   				node.setTextContent(arguments.getCommVaultSubClientName());
    				}
       			}
    		}
    		Node backupOpts = doc.getElementsByTagName(CommVaultConstants.NODE_BACKUPOPTS).item(0);
    		NodeList listb = backupOpts.getChildNodes();
    		for(int cntr = 0; cntr < listb.getLength(); cntr++) {
    			Node node = listb.item(cntr);
    			if(node.getNodeName().equalsIgnoreCase(CommVaultConstants.NODE_BACKUPOPTS_BACKUPLEVEL)) {
       				if(arguments.getCommVaultBackupType() != null) {
    	   				node.setTextContent(arguments.getCommVaultBackupType());
    				}
       			}
    		}
    	} catch (Exception ex) {
    		throw new Exception(ex);
    	}
    	return doc;
    }	// END : updateTaskRequestXMLValues

    @SuppressWarnings("unused")
	private Document updateTaskRequestXMLAttributeValues(
			Document doc, 
			CommVaultArguments arguments
			) throws Exception {
    	
    	try  {
    		Node associations = doc.getElementsByTagName(CommVaultConstants.NODE_ASSOCIATIONS).item(0);
    		NamedNodeMap associationsAttr = associations.getAttributes();
    		Node nodeAttrBackupsetName = associationsAttr.getNamedItem(CommVaultConstants.NODE_ASSOCIATIONS_BACKUPSETNAME);
			if(arguments.getCommVaultBackupsetName() != null) {
				nodeAttrBackupsetName.setTextContent(arguments.getCommVaultBackupsetName());
			}
    		Node nodeAttrClientName = associationsAttr.getNamedItem(CommVaultConstants.NODE_ASSOCIATIONS_CLIENTNAME);
			if(arguments.getCommVaultClientName() != null) {
				nodeAttrClientName.setTextContent(arguments.getCommVaultClientName());
			}
    		Node nodeAttrInstanceName = associationsAttr.getNamedItem(CommVaultConstants.NODE_ASSOCIATIONS_INSTANCENAME);
			if(arguments.getCommVaultInstance() != null) {
				nodeAttrInstanceName.setTextContent(arguments.getCommVaultInstance());
			}
    		Node nodeAttrSubclientName = associationsAttr.getNamedItem(CommVaultConstants.NODE_ASSOCIATIONS_SUBCLIENTNAME);
			if(arguments.getCommVaultSubClientName()!= null) {
				nodeAttrSubclientName.setTextContent(arguments.getCommVaultSubClientName());
			}

    		Node backupOpts = doc.getElementsByTagName(CommVaultConstants.NODE_BACKUPOPTS).item(0);
    		NamedNodeMap backupOptsAttrs = backupOpts.getAttributes();
    		Node nodeAttrBackupsetLevelName = backupOptsAttrs.getNamedItem(CommVaultConstants.NODE_BACKUPOPTS_BACKUPLEVEL);
			if(arguments.getCommVaultBackupType() != null) {
				nodeAttrBackupsetLevelName.setTextContent(arguments.getCommVaultBackupType());
			}
    	} catch (Exception ex) {
    		throw new Exception(ex);
    	}
    	return doc;
    }	// END : updateTaskRequestXMLAttributeValues


}
