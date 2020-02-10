/**
 * 
 */
package com.tibco.cep.security.authz.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

import com.tibco.cep.security.authz.core.ACL;
import com.tibco.cep.security.authz.domain.DomainResourceCollection;
import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.util.EncryptionHandler;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiSerializer;


/**
 * @author aathalye
 *
 */
public class ACLUtils {
	
	public static IDomainResource getMatchingResource(DomainResourceCollection resources, 
			                                          String resourceRef) {
		if (resourceRef == null) {
			return null;
		}
		Iterator<IDomainResource> iterator = resources.getElements();
		//Get the resourceref string after hash
		resourceRef = resourceRef.substring(1, resourceRef.length());
		while (iterator.hasNext()) {
			IDomainResource resource = iterator.next();
			if (resource.getId().intern() == resourceRef.intern()) {
				return resource;
			}
		}
		return null;
	}
	
	public static void serializeACL(final ACL acl ,final OutputStream out)throws Exception{
		ObjectOutputStream objOutputStream = new ObjectOutputStream(out);
		objOutputStream.writeObject(acl);
		objOutputStream.close();
		out.close();	
		
	}
	public static ACL deserializeACL(final InputStream inputStream)throws Exception{
		if (inputStream != null){
			ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
			ACL acl = (ACL)objInputStream.readObject();
			return acl;
		}
		return null;
	}
	
	public static void writeBytes(final byte[] bytes,
			                      final String fileName) throws IOException {
		File file = new File(fileName);
		File parentFile = file.getParentFile();
		if (parentFile != null) {
			if (!parentFile.exists()) {
				file.getParentFile().mkdirs();
			}
		}
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(bytes);
		fos.close();
	}
	
	public static byte[] readBytes(final String fileName) throws Exception {
		return IOUtils.readBytes(fileName);
	}
	
	public static byte[] toByteArray(ACL acl) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(acl);
		oos.close();
		//Read from the bos
		return bos.toByteArray();
	}
	
	public static void writeACL(ACL acl, OutputStream out, int mode, String filePath) throws Exception {
		if (out != null){
			if ((mode & ACLConstants.ENCRYPT_MODE) == mode) {
				EncryptionHandler encHandler = new EncryptionHandler(512, true);
				encHandler.encryptFile(out, toByteArray(acl), filePath);
				out.close();
			} else if ((mode & ACLConstants.SERIALIZE_MODE) == mode) {
				//Write code for XML serialization
				//Serialize
				XiFactory factory = XiFactoryFactory.newInstance();
				XiNode document = factory.createDocument();
				acl.serialize(factory, document);
				//Write to stream
				XiSerializer.serialize(document, out, true);
			}
		}
		
	}
	
	/**
	 * Wrap an unmarkable stream specified by {@link InputStream#markSupported()} into a stream that
	 * supports marking iff the stream already doesnt support it.
	 * @param unmarkableStream
	 * @return
	 * @throws IOException
	 */
	public static InputStream wrapUnmarkableStream(InputStream unmarkableStream) throws IOException {
		
		if (unmarkableStream.markSupported()) {
			return unmarkableStream;
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		IOUtils.writeBytes(unmarkableStream, bos);
		byte[] bytes = bos.toByteArray();
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		InputStream markableStream = bis;
		markableStream.reset();
		
		return markableStream;
	}
}
