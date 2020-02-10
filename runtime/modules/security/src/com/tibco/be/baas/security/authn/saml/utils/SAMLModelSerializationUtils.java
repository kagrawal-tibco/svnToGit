/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.xml.sax.InputSource;

/**
 *
 * @author Aditya Athalye
 * Date : 28 Sep, 2011
 */
public class SAMLModelSerializationUtils {
    
    static {
        ResourceFactoryRegistryImpl.
                INSTANCE.getExtensionToFactoryMap().put("*", new XMLResourceFactoryImpl());
    }

    /**
     * 
     * @param xmlString
     * @param packageString
     * @return
     * @throws Exception 
     */
    public static Object unmarshall(String xmlString, String packageString) throws Exception {
        if (xmlString == null || xmlString.isEmpty()) {
            throw new IllegalArgumentException("Unmarshall string cannot be empty or null");
        }
        InputSource inputSource = new InputSource(new StringReader(xmlString));
        return unmarshall(inputSource, packageString);
    }

    /**
     * 
     * @param xmlString
     * @param packageString
     * @return
     * @throws Exception 
     */
    public static Object unmarshall(File xmlFile, String packageString) throws Exception {
        if (xmlFile == null) {
            throw new IllegalArgumentException("File parameter cannot be empty or null");
        }
        if (!xmlFile.exists()) {
            throw new IllegalArgumentException("File " + xmlFile + " does not exist");
        }
        InputSource inputSource = new InputSource(new FileInputStream(xmlFile));
        return unmarshall(inputSource, packageString);
    }

    /**
     * 
     * @param inputSource
     * @param packageString
     * @return
     * @throws Exception 
     */
    public static Object unmarshall(InputSource inputSource, String packageString) throws Exception {
        //Get JAXBContext
        JAXBContext jaxbContext = JAXBContext.newInstance(packageString);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return unmarshaller.unmarshal(inputSource);
    }

    /**
     * 
     * @param jaxbObject
     * @param packageString
     * @param encoding
     * @return
     * @throws Exception 
     */
    public static String marshall(Object jaxbObject, String packageString, String encoding) throws Exception {
        if (jaxbObject == null) {
            throw new IllegalArgumentException("Object to marshall cannot be null");
        }
        //Get JAXBContext
        JAXBContext jaxbContext = JAXBContext.newInstance(packageString);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(jaxbObject, stringWriter);
        return stringWriter.toString();
    }
    
    /**
     * 
     * @param contents
     * @param options
     * @return
     * @throws Exception
     */
    public static EObject unmarshallEObject(String contents, Map<Object, Object> options) throws Exception {
        return unmarshallEObject(contents.getBytes(Charset.forName("UTF-8")), options);
    }

    /**
     * 
     * @param contents
     * @param options
     * @return
     * @throws Exception
     */
    public static EObject unmarshallEObject(byte[] contents, Map<Object, Object> options) throws Exception {
        if (contents == null || contents.length == 0) {
            throw new IllegalArgumentException("Argument passed invalid");
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(contents);
        return unmarshallEObject(bis, options);
    }

    /**
     * 
     * @param is
     * @return
     * @throws Exception
     */
    public static EObject unmarshallEObject(InputStream is) throws Exception {
        return unmarshallEObject(is, null);
    }

    /**
     * 
     * @param is
     * @param options
     * @return
     * @throws Exception
     */
    public static EObject unmarshallEObject(InputStream is, Map<Object, Object> options) throws Exception {
        if (is == null) {
            throw new IllegalArgumentException("Argument passed invalid");
        }
        if (options == null) {
            options = new HashMap<Object, Object>();
        }
        ResourceFactoryRegistryImpl.
                INSTANCE.getExtensionToFactoryMap().put("*", new XMLResourceFactoryImpl());
        XMLResource resource = new XMLResourceImpl();
        resource.load(is, options);
        EObject eobject = resource.getContents().get(0);
        return eobject;
    }
    
    /**
     * Write an {@link EObject} to a file.
     * @param <E>
     * @param outputFile
     * @param eObject
     * @param options 
     * @throws Exception 
     */
    public static <E extends EObject> void marshallEObject(File outputFile, E eObject, Map<Object, Object> options) throws Exception {
        if (options == null) {
            options = new HashMap<Object, Object>();
        }
        ResourceSet resourceSet = new ResourceSetImpl();
        URI fileUri = URI.createFileURI(outputFile.getAbsolutePath());
        Resource eobjectResource = resourceSet.createResource(fileUri);
        eobjectResource.getContents().add(eObject);
        options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
        eobjectResource.save(options);
    }
    
    /**
     * Write an {@link EObject} to an {@link Writer}
     * @param <E>
     * @param outputFile
     * @param eObject
     * @throws Exception 
     */
    public static <E extends EObject> void marshallEObject(Writer writer, E eObject, Map<Object, Object> options) throws Exception {
        if (options == null) {
            options = new HashMap<Object, Object>();
        }
        XMLResource eobjectResource = new XMLResourceImpl();
        eobjectResource.getContents().add(eObject);
        options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
        eobjectResource.save(writer, options);
    }
}
