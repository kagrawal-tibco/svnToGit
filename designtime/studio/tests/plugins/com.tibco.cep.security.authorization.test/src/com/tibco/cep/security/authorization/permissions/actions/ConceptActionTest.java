/**
 * 
 */
package com.tibco.cep.security.authorization.permissions.actions;

import com.tibco.cep.plugins.test.PluginTestCase;
import com.tibco.cep.security.authz.permissions.actions.ConceptAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;

/**
 * @author aathalye
 *
 */
public class ConceptActionTest extends PluginTestCase {
	
	public void testGetReadMaskFromStringAction() {
		ConceptAction conceptAction = new ConceptAction(0x1);
		assertEquals(conceptAction.getMask("read"), ConceptAction.READ);
	}
	
	public void testGetREADMaskFromStringAction() {
		ConceptAction conceptAction = new ConceptAction(0x1);
		assertEquals(conceptAction.getMask("READ"), ConceptAction.READ);
	}
	
	public void testGetModifyMaskFromStringAction() {
		ConceptAction conceptAction = new ConceptAction(0x1);
		assertEquals(conceptAction.getMask("modify"), ConceptAction.MODIFY);
	}
	
	public void testGetMODIfyMaskFromStringAction() {
		ConceptAction conceptAction = new ConceptAction(0x1);
		assertEquals(conceptAction.getMask("MODIfy"), ConceptAction.MODIFY);
	}
	
	public void testIllegalAction() {
		ConceptAction conceptAction = new ConceptAction(0x1);
		try {
			conceptAction.getMask("write");
		} catch (Exception e) {
			assertEquals(e.getClass().getName(), "java.lang.IllegalArgumentException");
			return;
		}
		fail("Test case failed for illegal action value");
	}
	
	public void testImpliesAllow() {
		ConceptAction c1 = new ConceptAction("read", Permit.ALLOW,  null);
		ConceptAction c2 = new ConceptAction("read");
		assertEquals(c1.implies(c2), true);
	}
	
	public void testImpliesDeny() {
		ConceptAction c1 = new ConceptAction("read", Permit.DENY,  null);
		ConceptAction c2 = new ConceptAction("read");
		assertEquals(c1.implies(c2), false);
	}
	
	public void testImpliesIncorrectActionType() {
		ConceptAction c1 = new ConceptAction("read", Permit.ALLOW,  null);
		ConceptAction c2 = new ConceptAction("modify");
		assertEquals(c1.implies(c2), false);
	}
}
