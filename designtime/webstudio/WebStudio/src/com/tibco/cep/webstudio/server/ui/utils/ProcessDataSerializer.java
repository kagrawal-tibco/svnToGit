package com.tibco.cep.webstudio.server.ui.utils;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.tibco.cep.webstudio.server.model.palette.Palette;
import com.tibco.cep.webstudio.server.model.process.Process;

/**
 * 
 * @author sasahoo
 *
 */
public class ProcessDataSerializer {
	
	private static final String JAXB_PROCESS_PACKAGE = "com.tibco.cep.webstudio.server.model.process";
	private static final String JAXB_PALETTE_PACKAGE = "com.tibco.cep.webstudio.server.model.palette";
	
	/**
	 * 
	 * @param paletteXml
	 * @return
	 * @throws Exception
	 */
	public static Object deserializePalette(String paletteXml) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(JAXB_PALETTE_PACKAGE);
	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    Object obj = unmarshaller.unmarshal(new StringReader(paletteXml));
	    return obj;
	}
	
	/**
	 * @param processXml
	 * @return
	 * @throws Exception
	 */
	public static Process deserialize(String processXml) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(JAXB_PROCESS_PACKAGE);
	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    Object obj = unmarshaller.unmarshal(new StringReader(processXml));
	    return (Process)obj;
	}
	
	/**
	 * @param resultsContents
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Process deserialize(byte[] resultsContents) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(resultsContents);
		JAXBContext jaxbContext = JAXBContext.newInstance(JAXB_PROCESS_PACKAGE);
	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    JAXBElement<Process> document = (JAXBElement<Process>)unmarshaller.unmarshal(bis);
	    return document.getValue();
	}
	
	/**
	 * @param filePath
	 * @param Process
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws org.eclipse.core.runtime.CoreException
	 */
	public static String serialize(Process Process) 
	                   throws JAXBException, FileNotFoundException, IOException {
		JAXBContext jaxbContext = JAXBContext.newInstance(JAXB_PROCESS_PACKAGE);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		StringWriter writer = new StringWriter();
		marshaller.marshal(Process, writer);
		String processxml = writer.toString();
		writer.flush();
		writer.close();
		return processxml;
	}

}
