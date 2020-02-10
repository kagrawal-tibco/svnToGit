/**
 * 
 */
package com.tibco.cep.security.authorization.permissions;

import com.tibco.cep.plugins.test.PluginTestCase;
import com.tibco.cep.security.authz.domain.DomainResource;
import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.permissions.ConceptResourcePermission;
import com.tibco.cep.security.authz.permissions.IResourcePermission;
import com.tibco.cep.security.authz.permissions.actions.ConceptAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.ResourceType;


/**
 * @author aathalye
 *
 */
public class ConceptResourcePermissionTest extends PluginTestCase {
	
	private IDomainResource r;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		r = new DomainResource("/Concepts/Account", ResourceType.CONCEPT);
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	
	public void testImpliesMatchingActions() {
		ConceptAction action1 = new ConceptAction("read", Permit.ALLOW, null);
		ConceptAction action2 = new ConceptAction("read", Permit.ALLOW, null);
		IResourcePermission p1 = new ConceptResourcePermission(r, action1);
		IResourcePermission p2 = new ConceptResourcePermission(r, action2);
		assertEquals(p1.implies(p2), Permit.ALLOW);
	}
	
	public void testImpliesDifferentAction_Permit() {
		ConceptAction action1 = new ConceptAction("read", Permit.ALLOW, null);
		ConceptAction action2 = new ConceptAction("read");
		IResourcePermission p1 = new ConceptResourcePermission(r, action1);
		IResourcePermission p2 = new ConceptResourcePermission(r, action2);
		assertEquals(p1.implies(p2), Permit.ALLOW);
	}
	
	public void testImpliesDifferentAction_Value() {
		ConceptAction action1 = new ConceptAction("read", Permit.ALLOW, null);
		ConceptAction action2 = new ConceptAction("modify");
		IResourcePermission p1 = new ConceptResourcePermission(r, action1);
		IResourcePermission p2 = new ConceptResourcePermission(r, action2);
		assertEquals(p1.implies(p2), Permit.DENY);
	}
}
