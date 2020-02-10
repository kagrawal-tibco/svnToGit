/**
 * 
 */
package com.tibco.cep.security.authz.core.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;

import com.tibco.cep.security.authz.core.ACL;
import com.tibco.cep.security.authz.core.ACLEntry;
import com.tibco.cep.security.authz.core.ACLManager;
import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.domain.IDomainResourceCollection;
import com.tibco.cep.security.authz.permissions.IResourcePermission;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.ACLUtils;
import com.tibco.cep.security.authz.utils.AccessLookupCache;
import com.tibco.cep.security.authz.utils.IOUtils;
import com.tibco.cep.security.authz.utils.PermissionType;
import com.tibco.cep.security.util.EncryptionHandler;

/**
 * @author aathalye
 *
 */
public class ACLManagerImpl implements ACLManager {
	
	private static ACLConfigurationParser2 parser;
		
	private ACL acl;
	
	//Hold looked up context objects
	private AccessLookupCache lookupCache;
	
	/**
	 * The cached stream for this {@link ACLManager}
	 */
	private InputStream cachedStream;
	
		
	public ACLManagerImpl() {
		//encHandler = new ACLEncryptionHandler();
		lookupCache = new AccessLookupCache();
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.core.ACLManager#load(java.io.InputStream, boolean)
	 */
	public void load(InputStream inputStream, boolean encrypted, String privateKeyPath) throws Exception {
		if (cachedStream != null) {
			if (!matchStreamsForEquality(cachedStream, inputStream)) {
				cachedStream = inputStream;
				lookupCache.invalidate();
				parser = new ACLConfigurationParser2(cachedStream);
			} 
		} else {
			cachedStream = inputStream;
		}
		//TODO avoid this creation again.
		parser = new ACLConfigurationParser2(cachedStream);
		acl = (encrypted == true) ?
				readACL(cachedStream, privateKeyPath) : parser.parse();
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.core.ACLManager#addACLEntry(com.tibco.cep.projectexplorer.cache.security.authz.core.ACLEntry)
	 */
	
	public boolean addACLEntry(ACLEntry entry) {
		throw new UnsupportedOperationException("To be done");
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.core.ACLManager#addPermission(com.tibco.cep.projectexplorer.cache.security.authz.core.ACLEntry, com.tibco.cep.projectexplorer.cache.security.authz.permissions.IResourcePermission)
	 */
	
	public boolean addPermission(ACLEntry aclEntry,
			IResourcePermission permission) {
		throw new UnsupportedOperationException("To be done");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.core.ACLManager#checkPermission(com.tibco.cep.projectexplorer.cache.security.authz.core.impl.ACLContext)
	 */
	
	public Permit checkPermission(ACLContext context) throws Exception {
		Permit permit = null;
		if (context == null) {
			throw new IllegalArgumentException("ACL Context cannot be null");
		}
		//Check if this context exists inside the cache
		boolean isPresent = lookupCache.contains(context);
		if (isPresent) {
//			TRACE.logDebug(this.getClass().getName(),
//					       "Found an entry {0} in cache", 
//					       context);
			return lookupCache.get(context);
		}
//		TRACE.logDebug(this.getClass().getName(),
//				       "Calling checkPermission for role {0} for resource {1} for action {2}",
//				       context.getPrincipal().getName(), context.getRequestedResource(), context.getRequestedAction());
		
		synchronized (acl) {
			if (acl.getResources().isCollectionOrganized()) {
//				TRACE.logDebug(this.getClass().getName(),
//					       "Collection organized");
				permit = context.checkPermission();
				lookupCache.putCacheEntry(context, permit);
			}
		}
		return permit;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.core.ACLManager#getConfiguredACL()
	 */
	
	public ACL getConfiguredACL() {
		return acl;
	}
	
	/**
	 * TODO -> Not sure why this check is never succeeding.
	 * Match the digests of the 2 streams to compare their equality
	 * @param stream1
	 * @param stream2
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	private static boolean matchStreamsForEquality(final InputStream stream1,
			                                       final InputStream stream2) throws NoSuchAlgorithmException,
			                                                                         IOException {
		//Compute digests on both stream, and compare for equality
		
		MessageDigest md1 = MessageDigest.getInstance("SHA");
		MessageDigest md2 = MessageDigest.getInstance("SHA");
		//Calc Dig1
		readInto(stream1, md1);
		readInto(stream2, md2);
		byte[] dig1 = md1.digest();
		byte[] dig2 = md2.digest();
		if (MessageDigest.isEqual(dig1, dig2)) {
			return true;
		}
		return false;
	}
	
	private static void readInto(InputStream stream,
			                     final MessageDigest md) throws IOException {
		if (stream == null) {
			throw new IllegalArgumentException("Input parameter cannot be null");
		}
		/**
		 * The streams could have been read before in which case
		 * The "pos" value would be somewhere in the middle
		 * or even end of the stream. Need to reset it to last 
		 * marked position which in this case is 0.
		 * If input stream is not a mark supporting stream wrap it into one.
		 * 
		 */
		if (!stream.markSupported()) {
			//BufferedInputStream wrapped = new BufferedInputStream(stream);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			IOUtils.writeBytes(stream, bos);
			byte[] bytes = bos.toByteArray();
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			stream = bis;
		} 
		stream.reset();
		ByteBuffer buf = ByteBuffer.allocate(1024);
		byte[] bufferArray = buf.array();
		int count = 0;
		int index = 0;
		while (count >= 0) {
			if (index == count) {
				count = stream.read(bufferArray);
				index = 0;
			}
			while (index < count && buf.hasRemaining()) {
				buf.put(bufferArray[index++]);
			}
			((Buffer)buf).flip();
			md.update(buf);
			if (buf.hasRemaining()) {
				buf.compact();
			} else {
				((Buffer)buf).clear();
			}
		}
	    //stream.close();
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.core.ACLManager#removeACLEntry(com.tibco.cep.projectexplorer.cache.security.authz.core.ACLEntry)
	 */
	
	public boolean removeACLEntry(ACLEntry entry) {
		throw new UnsupportedOperationException("To be done");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.core.ACLManager#removePermission(com.tibco.cep.projectexplorer.cache.security.authz.core.ACLEntry, com.tibco.cep.projectexplorer.cache.security.authz.permissions.IResourcePermission)
	 */
	
	public boolean removePermission(ACLEntry aclEntry,
			IResourcePermission permission) {
		throw new UnsupportedOperationException("To be done");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.core.ACLManager#writeACL(java.io.OutputStream)
	 */
	
	public void writeACL(OutputStream out, int mode) throws Exception {
		ACLUtils.writeACL(acl, out, mode, null);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.security.authz.core.ACLManager#readACL(java.io.InputStream)
	 */
	public ACL readACL(InputStream is, String privateKeyPath) throws Exception {
		EncryptionHandler encryptionHandler = new EncryptionHandler(512, true);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		if (is.markSupported()) {
			is.reset();
		} else {
			throw new IllegalArgumentException("Input stream passed does not support marking");
		}
		IOUtils.writeBytes(is, bos);
		byte[] encBytes = bos.toByteArray();
		byte[] decryptedBytes = encryptionHandler.decrypt(encBytes, privateKeyPath);

		if (decryptedBytes.length > 0) {
			ObjectInputStream ois =
				new ObjectInputStream(new ByteArrayInputStream(decryptedBytes));
			acl = (ACL)ois.readObject();
		}
		//Get the resources inside
		if (acl == null) {
			throw new Exception("The decryption of local policy file failed");
		}
		final IDomainResourceCollection resourceCollection =
			acl.getResources();
		resourceCollection.close();
		return acl;
	}


	
	/* (non-Javadoc)
	 * @see com.tibco.cep.projectexplorer.cache.security.authz.core.ACLManager#createACLContext(java.security.Principal, com.tibco.cep.projectexplorer.cache.security.authz.domain.IDomainResource, com.tibco.cep.projectexplorer.cache.security.authz.permissions.actions.IAction)
	 */
	public ACLContext createACLContext(Principal principal,
			IDomainResource resource, IAction action, PermissionType permissionType) throws Exception {
		
		ACLContext context = null;
		try {
			context = new ACLContext(principal, 
							             acl.getACLEntries(),
							             resource, 
							             action,
							             permissionType);
		} catch (IllegalArgumentException iae) {
			throw new Exception(iae);
		}
		return context;
	}


	@Override
	public void load(InputStream inputStream, boolean encrypted)
			throws Exception {
		this.load(inputStream, encrypted, null);
		
	}
}
