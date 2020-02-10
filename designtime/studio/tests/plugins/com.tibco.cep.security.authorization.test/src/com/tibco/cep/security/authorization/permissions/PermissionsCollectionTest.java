/**
 * 
 */
package com.tibco.cep.security.authorization.permissions;

import java.util.Iterator;

import com.tibco.cep.plugins.test.PluginTestCase;
import com.tibco.cep.security.authz.domain.DomainResource;
import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.ConceptResourcePermission;
import com.tibco.cep.security.authz.permissions.IResourcePermission;
import com.tibco.cep.security.authz.permissions.PermissionsCollection;
import com.tibco.cep.security.authz.utils.ResourceType;


/**
 * @author aathalye
 *
 */
public class PermissionsCollectionTest extends PluginTestCase {
	
	private PermissionsCollection permissionsCollection;
	private IDomainResource resource;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		permissionsCollection = new PermissionsCollection();
		resource = new DomainResource("/Concepts/Account", ResourceType.CONCEPT);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		permissionsCollection = null;
	}
	
	public void testAddNewConceptPermission() {
		IResourcePermission permission =
			new ConceptResourcePermission(resource);
		permissionsCollection.addPermission(permission);
		Iterator<IResourcePermission> iter = permissionsCollection.getElements();
		assertEquals(iter.hasNext(), true);
		assertEquals(iter.next(), permission);
	}
	
	public void testRemoveConceptPermission() {
		IResourcePermission permission =
			new ConceptResourcePermission(resource);
		permissionsCollection.removePermission(permission);
		Iterator<IResourcePermission> iter = permissionsCollection.getElements();
		assertEquals(iter.hasNext(), false);
	}
}
