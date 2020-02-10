package com.tibco.be.functions.trax.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ParseTest {
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

    
	public static void main(String[] a){
		String xmlFilePath = "E:/be/QA/4.0/g11n77ws/SimpleStateRepo/SimpleStateRepo.cdd";
		String schemaFilePath = "E:/be/Repo/4.0/common/src/config-4.0.0-2009-11-25.xsd";
		try {
			//FileInputStream fSteam = new FileInputStream(fileName);
			//InputSource source = new InputSource(fSteam);
			InputStream iStream = readXMLFile(xmlFilePath);
			Schema schema = parseSchema(schemaFilePath);
			parseAndValidate(iStream, schema);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void parseAndValidate(InputStream xmlInputStream, Schema schema)
			throws SAXException, ParserConfigurationException, IOException,
			TransformerException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(false);
		factory.setSchema(schema);
		//System.out.println(factory.isValidating());
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(xmlInputStream);
		System.out.println(XMLSerializer.serialize(doc));
	}
	
	private static Schema parseSchema(String schemaFile) throws SAXException{
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		schemaFactory.setErrorHandler(new SAXErrorHandler());
		return schemaFactory.newSchema(new File(schemaFile));
	}
	
	private static InputStream readXMLFile(String xmlFile) throws IOException {
		FileInputStream fStream = new FileInputStream(xmlFile);
		BufferedInputStream iStream = new BufferedInputStream(fStream);
		return iStream;
	}
	
	public static class SAXErrorHandler implements ErrorHandler {

		@Override
		public void error(SAXParseException exception) throws SAXException {
			System.out.println("SAXErrorHandler:error:" + exception.getMessage());
			
		}

		@Override
		public void fatalError(SAXParseException exception) throws SAXException {
			System.out.println("SAXErrorHandler:fatalError:" + exception.getMessage());
		}

		@Override
		public void warning(SAXParseException exception) throws SAXException {
			System.out.println("SAXErrorHandler:warning:" + exception.getMessage());
		}
		
	}

}
