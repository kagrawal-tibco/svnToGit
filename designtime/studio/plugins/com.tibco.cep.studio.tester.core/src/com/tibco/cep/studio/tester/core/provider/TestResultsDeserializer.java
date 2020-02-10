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
import com.tibco.cep.studio.tester.core.model.ObjectFactory;
import com.tibco.cep.studio.tester.core.model.TesterResultsType;


/**
 * @author aathalye
 *
 */
public class TestResultsDeserializer {
	
	private static final String JAXB_PACKAGE = "com.tibco.cep.studio.tester.core.model";
	
	/**
	 * @param xmlString
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public TesterResultsType deserialize(String xmlString) throws Exception {
		JAXBContext jaxbContext = createNewContext();
	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    JAXBElement<TesterResultsType> document = 
	    	(JAXBElement<TesterResultsType>)unmarshaller.unmarshal(new StringReader(xmlString));
	    return document.getValue();
	}
	
	/**
	 * @param resultsFile
	 * @return
	 * @throws Exception
	 */
	public TesterResultsType deserialize(File resultsFile) throws Exception {
		byte[] fileContents = IOUtils.readBytes(resultsFile.getAbsolutePath());
		return deserialize(fileContents);
	}
	
	/**
	 * @param resultsContents
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private TesterResultsType deserialize(byte[] resultsContents) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(resultsContents);
		JAXBContext jaxbContext = createNewContext();
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		JAXBElement<TesterResultsType> document = 
				(JAXBElement<TesterResultsType>)unmarshaller.unmarshal(bis);
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
	public void serialize(String filePath, TesterResultsType testerResultsType) 
	                   throws JAXBException, FileNotFoundException, IOException, org.eclipse.core.runtime.CoreException {
		JAXBContext jaxbContext = createNewContext();
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
	public TesterResultsType serializeWMResultType(File resultsFile) throws JAXBException {
		JAXBContext jaxbContext = createNewContext();
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		 JAXBElement<TesterResultsType> document = 
		    	(JAXBElement<TesterResultsType>)unmarshaller.unmarshal(resultsFile);			
		return document.getValue();
	}

	private JAXBContext createNewContext() throws JAXBException {
		JAXBContext jaxbContext = null;
		String original = System.getProperty(JAXBContext.JAXB_CONTEXT_FACTORY);
		try {
			jaxbContext = JAXBContext.newInstance(JAXB_PACKAGE, ObjectFactory.class.getClassLoader());
		} catch (JAXBException e) {
			// assume the default factory is not available, use the new embedded one
			// this is a temporary fix due to version 2.3.1 of jaxb, which still references the old 'internal' ContextFactory
			System.setProperty(JAXBContext.JAXB_CONTEXT_FACTORY, "com.sun.xml.bind.v2.ContextFactory");
			jaxbContext = JAXBContext.newInstance(JAXB_PACKAGE, ObjectFactory.class.getClassLoader());
		} finally {
			if (original == null) {
				System.clearProperty(JAXBContext.JAXB_CONTEXT_FACTORY);
			} else {
				System.setProperty(JAXBContext.JAXB_CONTEXT_FACTORY, original);
			}
		}
		return jaxbContext;
	}
}