/**
 * 
 */
package com.tibco.cep.security.authorization.domain;

import com.tibco.cep.plugins.test.PluginTestCase;
import com.tibco.cep.security.authz.domain.DomainResource;
import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.utils.ResourceType;

/**
 * @author aathalye
 *
 */
public class DomainResourceTest extends PluginTestCase {
	
	public void testEqualsConceptSameResourceNameAndType() {
		IDomainResource r1 = new DomainResource("/Concepts/Person", ResourceType.CONCEPT);
		IDomainResource r2 = new DomainResource("/Concepts/Person", ResourceType.CONCEPT);
		assertEquals(r1.equals(r2), true);
	}
	
	public void testEqualsConceptSameResourceNameAndTypeAndId() {
		IDomainResource r1 = new DomainResource("/Concepts/Person", "Id", ResourceType.CONCEPT);
		IDomainResource r2 = new DomainResource("/Concepts/Person", "Id", ResourceType.CONCEPT);
		assertEquals(r1.equals(r2), true);
	}
	
	public void testEqualsConceptSameResourceNameAndTypeAndIdAndParent() {
		IDomainResource parent = new DomainResource("/Concepts/*", ResourceType.CONCEPT);
		IDomainResource r1 = new DomainResource("/Concepts/Person", "Id", ResourceType.CONCEPT, parent);
		IDomainResource r2 = new DomainResource("/Concepts/Person", "Id", ResourceType.CONCEPT, parent);
		assertEquals(r1.equals(r2), true);
	}
	
	public void testEqualsConceptDiffResType() {
		//IDomainResource parent = new DomainResource("/Concepts/*", ResourceType.CONCEPT);
		IDomainResource r1 = new DomainResource("/Concepts/Person", "Id", ResourceType.EVENT);
		IDomainResource r2 = new DomainResource("/Concepts/Person", "Id", ResourceType.CONCEPT);
		assertEquals(r1.equals(r2), false);
	}
	
	public void testEqualsConceptDiffIds() {
		//IDomainResource parent = new DomainResource("/Concepts/*", ResourceType.CONCEPT);
		IDomainResource r1 = new DomainResource("/Concepts/Person", "Id1", ResourceType.CONCEPT);
		IDomainResource r2 = new DomainResource("/Concepts/Person", "Id", ResourceType.CONCEPT);
		assertEquals(r1.equals(r2), false);
	}
	
	public void testEqualsConceptDiffNames() {
		//IDomainResource parent = new DomainResource("/Concepts/*", ResourceType.CONCEPT);
		IDomainResource r1 = new DomainResource("/Concepts/Person", "Id", ResourceType.CONCEPT);
		IDomainResource r2 = new DomainResource("/Concepts/Person1", "Id", ResourceType.CONCEPT);
		assertEquals(r1.equals(r2), false);
	}
	
	public void testEqualsConceptDiffParent() {
		IDomainResource parent1 = new DomainResource("/Concepts/*", ResourceType.CONCEPT);
		IDomainResource parent2 = new DomainResource("/Concepts/*", ResourceType.CONCEPT);
		IDomainResource r1 = new DomainResource("/Concepts/Person", "Id", ResourceType.CONCEPT, parent1);
		IDomainResource r2 = new DomainResource("/Concepts/Person", "Id", ResourceType.CONCEPT, parent2);
		assertEquals(r1.equals(r2), true);
	}
	
	public void testImpliesSameResources() {
		IDomainResource r1 = new DomainResource("/Concepts/Person", ResourceType.CONCEPT);
		IDomainResource r2 = new DomainResource("/Concepts/Person", ResourceType.CONCEPT);
		assertEquals(r1.implies(r2), true);
	}
	
	public void testImpliesWildCardResource() {
		IDomainResource r1 = new DomainResource("/Concepts/Person/*", ResourceType.CONCEPT);
		IDomainResource r2 = new DomainResource("/Concepts/Person/Acc", ResourceType.CONCEPT);
		assertEquals(r1.implies(r2), true);
	}
	
	public void testImpliesBothWildCard() {
		IDomainResource r1 = new DomainResource("/Concepts/Person/*", ResourceType.CONCEPT);
		IDomainResource r2 = new DomainResource("/Concepts/Person/Acc/*", ResourceType.CONCEPT);
		assertEquals(r1.implies(r2), true);
	}
	
	public void testImpliesDiffResourceType() {
		IDomainResource r1 = new DomainResource("/Concepts/Person/*", ResourceType.CONCEPT);
		IDomainResource r2 = new DomainResource("/Concepts/Person/Acc/*", ResourceType.EVENT);
		assertEquals(r1.implies(r2), false);
	}
	
	public void testImpliesSmallerTargetResource1() {
		IDomainResource r1 = new DomainResource("/Concepts/Person/Acc/*", ResourceType.CONCEPT);
		IDomainResource r2 = new DomainResource("/Concepts/Person/*", ResourceType.CONCEPT);
		assertEquals(r1.implies(r2), false);
	}
	
	public void testImpliesSmallerTargetResource2() {
		IDomainResource r1 = new DomainResource("/Concepts/Person/*", ResourceType.CONCEPT);
		IDomainResource r2 = new DomainResource("/Concepts/Person", ResourceType.CONCEPT);
		assertEquals(r1.implies(r2), false);
	}
}
