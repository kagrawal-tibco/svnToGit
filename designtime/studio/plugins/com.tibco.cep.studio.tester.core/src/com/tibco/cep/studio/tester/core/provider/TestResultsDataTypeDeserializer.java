/**
 * 
 */
package com.tibco.cep.studio.tester.core.provider;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.tibco.cep.security.authz.utils.IOUtils;
import com.tibco.cep.studio.tester.core.model.TesterResultsDatatype;


/**
 * @author aathalye
 *
 */
public class TestResultsDataTypeDeserializer {
	
	private static final String JAXB_PACKAGE = "com.tibco.cep.studio.tester.core.model";
	
	/**
	 * @param xmlString
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public TesterResultsDatatype deserialize(String xmlString) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(JAXB_PACKAGE);
	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    JAXBElement<TesterResultsDatatype> document = 
	    	(JAXBElement<TesterResultsDatatype>)unmarshaller.unmarshal(new StringReader(xmlString));
	    return document.getValue();
	}
	
	/**
	 * @param resultsFile
	 * @return
	 * @throws Exception
	 */
	public TesterResultsDatatype deserialize(File resultsFile) throws Exception {
		byte[] fileContents = IOUtils.readBytes(resultsFile.getAbsolutePath());
		return deserialize(fileContents);
	}
	
	/**
	 * @param resultsContents
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private TesterResultsDatatype deserialize(byte[] resultsContents) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(resultsContents);
		JAXBContext jaxbContext = JAXBContext.newInstance(JAXB_PACKAGE);
	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    JAXBElement<TesterResultsDatatype> document = 
	    	(JAXBElement<TesterResultsDatatype>)unmarshaller.unmarshal(bis);
	    return document.getValue();
	}
	
	/**
	 * @param filePath
	 * @param testerResultsType
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws org.eclipse.core.runtime.CoreException
	 */
	public void serialize(String filePath, TesterResultsDatatype testerResultsType) 
	                   throws JAXBException, FileNotFoundException, IOException, org.eclipse.core.runtime.CoreException {
		JAXBContext jaxbContext = JAXBContext.newInstance(JAXB_PACKAGE);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		FileOutputStream file = new FileOutputStream(filePath);
		marshaller.marshal(testerResultsType, file);		
		file.flush();
		file.close();
	}
	
	/**
	 * @param resultsFile
	 * @return
	 * @throws JAXBException
	 */
	@SuppressWarnings("unchecked")
	public TesterResultsDatatype serializeWMResultType(File resultsFile) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(JAXB_PACKAGE);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		 JAXBElement<TesterResultsDatatype> document = 
		    	(JAXBElement<TesterResultsDatatype>)unmarshaller.unmarshal(resultsFile);			
		return document.getValue();
	}
}