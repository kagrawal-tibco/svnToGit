/**
 * 
 */
package com.tibco.cep.security.authorization.domain;

import java.util.Iterator;

import com.tibco.cep.plugins.test.PluginTestCase;
import com.tibco.cep.security.authz.domain.DomainResource;
import com.tibco.cep.security.authz.domain.DomainResourceCollection;
import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.domain.TemplateDomainResource;
import com.tibco.cep.security.authz.utils.ResourceType;

/**
 * @author aathalye
 *
 */
public class DomainResourceCollectionTest extends PluginTestCase {
	
	private DomainResourceCollection drc;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		drc = new DomainResourceCollection();
	}
	
	public void testOpen() {
		boolean open = drc.open();
		assertEquals(open, true);
	}
	
	
	public void testAddResource() {
		IDomainResource r = new DomainResource("/Concepts/*", ResourceType.CONCEPT);
		drc.open();
		drc.add(r);
		Iterator<IDomainResource> iter = drc.getElements();
		assertEquals(iter.next().equals(r), true);
	}
	
	public void testRemoveResource() {
		IDomainResource r = new DomainResource("/Concepts/*", ResourceType.CONCEPT);
		drc.open();
		drc.remove(r);
		Iterator<IDomainResource> iter = drc.getElements();
		assertEquals(iter.hasNext(), false);
	}
	
	public void testRemoveAllResources() {
		IDomainResource r = new DomainResource("/Concepts/*", ResourceType.CONCEPT);
		IDomainResource r1 = new DomainResource("/Concepts/Person/*", ResourceType.CONCEPT);
		IDomainResource r2 = new DomainResource("/Concepts/Account/*", ResourceType.CONCEPT);
		drc.open();
		drc.add(r);
		drc.add(r1);
		drc.add(r2);
		assertEquals(drc.size(), 3);
		drc.removeAll();
		assertEquals(drc.size(), 0);
	}
	
	public void testOrganizeResources1() throws Exception {
		IDomainResource r = new DomainResource("/Concepts/*", ResourceType.CONCEPT);
		IDomainResource r1 = new DomainResource("/Concepts/Person/*", ResourceType.CONCEPT);
		drc.open();
		drc.add(r);
		drc.add(r1);
		drc.close().get();
		assertEquals(r1.getParent(), r);
		drc.removeAll();
	}
	
	public void testOrganizeResources2() throws Exception {
		IDomainResource r = new DomainResource("/Concepts/*", ResourceType.CONCEPT);
		IDomainResource r1 = new DomainResource("/Concepts/Person/*", ResourceType.CONCEPT);
		IDomainResource r2 = new DomainResource("/Concepts/Account/*", ResourceType.CONCEPT);
		drc.open();
		drc.add(r);
		drc.add(r1);
		drc.add(r2);
		drc.close().get();
		assertEquals(r2.getParent(), r);
		drc.removeAll();
	}
	
	public void testOrganizeResources3() throws Exception {
		IDomainResource r = new DomainResource("/Concepts/*", ResourceType.CONCEPT);
		IDomainResource r1 = new DomainResource("/Concepts/Person/*", ResourceType.CONCEPT);
		IDomainResource r2 = new DomainResource("/Concepts/Account/*", ResourceType.CONCEPT);
		IDomainResource r3 = new TemplateDomainResource(ResourceType.CONCEPT);
		drc.open();
		drc.add(r);
		drc.add(r1);
		drc.add(r2);
		drc.add(r3);
		drc.close().get();
		assertEquals(r.getParent(), r3);
		assertEquals(r1.getParent(), r);
		drc.removeAll();
	}
	
	public void testOrganizeResources4() throws Exception {
		IDomainResource r = new DomainResource("/Concepts/*", ResourceType.CONCEPT);
		IDomainResource r1 = new DomainResource("/Concepts/Person/*", ResourceType.CONCEPT);
		IDomainResource r2 = new DomainResource("/Concepts/Account/*", ResourceType.CONCEPT);
		IDomainResource r3 = new TemplateDomainResource(ResourceType.CONCEPT);
		IDomainResource r4 = new DomainResource("/Concepts/Person/Person1", ResourceType.CONCEPT);
		drc.open();
		drc.add(r);
		drc.add(r1);
		drc.add(r2);
		drc.add(r3);
		drc.add(r4);
		drc.close().get();
		assertEquals(r.getParent(), r3);
		assertEquals(r4.getParent(), r1);
	}
	
	public void testOrganizeResources5() throws Exception {
		IDomainResource r = new DomainResource("/Concepts/*", ResourceType.CONCEPT);
		IDomainResource r1 = new DomainResource("/Concepts/Person", ResourceType.CONCEPT);
		IDomainResource r2 = new DomainResource("/Concepts/Account/*", ResourceType.CONCEPT);
		IDomainResource r3 = new TemplateDomainResource(ResourceType.CONCEPT);
		IDomainResource r4 = new DomainResource("/Concepts/Person/Person1", ResourceType.CONCEPT);
		drc.open();
		drc.add(r);
		drc.add(r1);
		drc.add(r2);
		drc.add(r3);
		drc.add(r4);
		drc.close().get();
		assertEquals(drc.size(), 5);
		assertEquals(r4.getParent(), r);
	}
	
	public void testOrganizeResources6() throws Exception {
		IDomainResource r1 = new DomainResource("/Concepts/P", ResourceType.CONCEPT);
		IDomainResource r2 = new DomainResource("/Concepts/Account/*", ResourceType.CONCEPT);
		IDomainResource r3 = new TemplateDomainResource(ResourceType.CONCEPT);
		IDomainResource r4 = new DomainResource("/Concepts", ResourceType.CONCEPT);
		drc.open();
		drc.add(r1);
		drc.add(r2);
		drc.add(r3);
		drc.add(r4);
		drc.close().get();
		assertEquals(r4.getParent(), r3);
		drc.removeAll();
	}
	
	public void testOrganizeResources7() throws Exception {
		IDomainResource r1 = new DomainResource("/Concepts/P", ResourceType.CONCEPT);
		IDomainResource r2 = new DomainResource("/Concepts/Account/*", ResourceType.CONCEPT);
		IDomainResource r3 = new DomainResource("/Concepts/Account/*", ResourceType.EVENT);
		IDomainResource r4 = new DomainResource("/Concepts/Account/Acc1", ResourceType.CONCEPT);
		IDomainResource r5 = new DomainResource("/Concepts/Account/Acc2", ResourceType.EVENT);
		drc.open();
		drc.add(r1);
		drc.add(r2);
		drc.add(r3);
		drc.add(r4);
		drc.add(r5);
		drc.close().get();
		assertEquals(r4.getParent(), r2);
		assertEquals(r5.getParent(), r3);
		drc.removeAll();
	}
	
	public void testOrganizeResourcesNegative1() throws Exception {
		IDomainResource r1 = new DomainResource("/Concepts/P", ResourceType.CONCEPT);
		IDomainResource r2 = new TemplateDomainResource(ResourceType.EVENT);
		IDomainResource r4 = new DomainResource("/Concepts", ResourceType.CONCEPT);
		drc.open();
		drc.add(r1);
		drc.add(r2);
		//drc.add(r3);
		drc.add(r4);
		drc.close().get();
		assertNotSame(r4.getParent(), r2);
		drc.removeAll();
	}
	
	public void testOrganizeResourcesNegative2() throws Exception {
		System.out.println(drc.hashCode());
		IDomainResource r1 = new DomainResource("/Concepts/P", ResourceType.CONCEPT);
		IDomainResource r5 = new DomainResource("/Concepts/P", ResourceType.CONCEPT);
		IDomainResource r2 = new TemplateDomainResource(ResourceType.EVENT);
		IDomainResource r3 = new TemplateDomainResource(ResourceType.EVENT);
		drc.open();
		drc.add(r1);
		drc.add(r2);
		drc.add(r3);
		drc.add(r5);
		drc.close().get();
		assertEquals(drc.isCollectionOrganized(), true);
		assertEquals(drc.size(), 2);
		assertNotSame(r1.getParent(), r2);
	}
}
