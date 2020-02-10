/**
 * 
 */
package com.tibco.cep.security.authz.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;

import com.tibco.cep.security.authz.core.impl.ACLContext;
import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.IResourcePermission;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;

/**
 * @author aathalye
 *
 */
public interface ACLManager {
	
	/**
	 * @param context
	 * @return permit
	 * @throws Exception
	 */
	public Permit checkPermission(ACLContext context) throws Exception;
	
	/**
	 * @return
	 */
	public ACL getConfiguredACL();
	
	/**
	 * @param principal
	 * @param resource
	 * @param action
	 * @param permissionType
	 * @return
	 * @throws Exception
	 */
	public ACLContext createACLContext(Principal principal, 
			                           IDomainResource resource,
			                           IAction action,
			                           PermissionType permissionType) throws Exception;
	
	/**
	 * @param out: The output stream to write to
	 * @param mode
	 * <p>
	 * If mode is 0, then encrypt the <tt>ACL</tt>,
	 * else write out the ACL in XML format in clear text
	 * </p>
	 */
	public void writeACL(OutputStream out, int mode) throws Exception;
	
	/**
	 * It is assumed that the input stream will be a file
	 * which has encrypted content.
	 * <p>
	 * <li>
	 * This stream should return true for {@link InputStream#markSupported()}.
	 * </li>
	 * </p>
	 * @param is
	 * @return The decrypted {@link ACL} object
	 * @throws Exception
	 */
	public ACL readACL(InputStream is, String privateKeyFilePath) throws Exception;
	
	/**
	 * @param entry
	 * @return
	 */
	public boolean addACLEntry(ACLEntry entry);
	
	/**
	 * @param entry
	 * @return
	 */
	public boolean removeACLEntry(ACLEntry entry);
	
	/**
	 * @param aclEntry
	 * @param permission
	 * @return
	 */
	public boolean addPermission(ACLEntry aclEntry, IResourcePermission permission);
	
	/**
	 * @param aclEntry
	 * @param permission
	 * @return
	 */
	public boolean removePermission(ACLEntry aclEntry, IResourcePermission permission);
	
	/**
	 * Call this method upon constructing instance of {@link ACLManager} to load it
	 * with actual contents.
	 * @param inputStream
	 * @param encrypted
	 * @param privateKeyPath 
	 */
	public void load(InputStream inputStream, boolean encrypted, String privateKeyPath) throws Exception;
	
	/**
	 * Call this method upon constructing instance of {@link ACLManager} to load it
	 * with actual contents.
	 * @param inputStream
	 * @param encrypted
	 * */
	public void load(InputStream inputStream, boolean encrypted) throws Exception;
}
