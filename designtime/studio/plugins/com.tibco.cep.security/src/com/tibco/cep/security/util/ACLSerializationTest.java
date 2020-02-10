/**
 * 
 */
package com.tibco.cep.security.util;

import java.io.FileOutputStream;

import com.tibco.cep.security.authz.core.ACL;
import com.tibco.cep.security.authz.core.ACLEntry;
import com.tibco.cep.security.authz.core.impl.ACLEntryImpl;
import com.tibco.cep.security.authz.core.impl.ACLImpl;
import com.tibco.cep.security.authz.domain.DomainResource;
import com.tibco.cep.security.authz.domain.DomainResourceCollection;
import com.tibco.cep.security.authz.domain.IDomainResource;
import com.tibco.cep.security.authz.domain.IDomainResourceCollection;
import com.tibco.cep.security.authz.permissions.ConceptResourcePermission;
import com.tibco.cep.security.authz.permissions.EventResourcePermission;
import com.tibco.cep.security.authz.permissions.IResourcePermission;
import com.tibco.cep.security.authz.permissions.RuleFunctionResourcePermission;
import com.tibco.cep.security.authz.permissions.RuleResourcePermission;
import com.tibco.cep.security.authz.permissions.actions.ConceptAction;
import com.tibco.cep.security.authz.permissions.actions.EventAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.permissions.actions.RuleAction;
import com.tibco.cep.security.authz.permissions.actions.RuleFunctionAction;
import com.tibco.cep.security.authz.utils.ACLConstants;
import com.tibco.cep.security.authz.utils.ACLUtils;
import com.tibco.cep.security.authz.utils.ResourceType;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.tokens.TokenFactory;

/**
 * @author aathalye
 * Aug 20, 2010
 *
 */
public class ACLSerializationTest {
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    
	public static void main(String[] r) throws Exception {
		ACL acl = new ACLImpl();
		//Add some resources to it
		IDomainResource dr1 = new DomainResource("/Concepts/C1", "C", ResourceType.CONCEPT);
		IDomainResource dr2 = new DomainResource("/Events/E1", "E", ResourceType.EVENT);
		IDomainResource dr3 = new DomainResource("/Concepts/R1", "R", ResourceType.RULE);
		IDomainResource dr4 = new DomainResource("/Concepts/RF1", "RF", ResourceType.RULEFUNCTION);
		IDomainResource dr5 = new DomainResource(null, "DTs", ResourceType.IMPLEMENTATION);
		
		IDomainResourceCollection drc = new DomainResourceCollection();
		drc.open();
		drc.add(dr1);
		drc.add(dr2);
		drc.add(dr3);
		drc.add(dr4);
		drc.add(dr5);
//		drc.close();
		
		acl.setResources(drc);
		
		
		Role role = TokenFactory.INSTANCE.createRole();
		role.setName("ADMINISTRATOR");
		ACLEntry aclEntry = new ACLEntryImpl(role);
		
		//Add various permissions
		IResourcePermission pr1 = new ConceptResourcePermission(dr1, new ConceptAction("read", Permit.ALLOW, null));
		IResourcePermission pr2 = new EventResourcePermission(dr2, new EventAction("read", Permit.ALLOW, null));
		IResourcePermission pr3 = new RuleResourcePermission(dr3, new RuleAction("read", Permit.DENY, null));
		IResourcePermission pr4 = new RuleFunctionResourcePermission(dr4, new RuleFunctionAction("read", Permit.DENY, null));
		
		aclEntry.addPermission(pr1);
		aclEntry.addPermission(pr2);
		aclEntry.addPermission(pr3);
		aclEntry.addPermission(pr4);
		
		Role role1 = TokenFactory.INSTANCE.createRole();
		role1.setName("BUSINESS_USER");
		ACLEntry aclEntry1 = new ACLEntryImpl(role1);
		
		//Add various permissions
		IResourcePermission pr5 = new ConceptResourcePermission(dr1, new ConceptAction("read", Permit.DENY, null));
		IResourcePermission pr6 = new EventResourcePermission(dr2, new EventAction("create", Permit.DENY, null));
		IResourcePermission pr7 = new RuleResourcePermission(dr3, new RuleAction("read", Permit.DENY, null));
		IResourcePermission pr8 = new RuleFunctionResourcePermission(dr4, new RuleFunctionAction("read", Permit.DENY, null));
		
		aclEntry1.addPermission(pr5);
		aclEntry1.addPermission(pr6);
		aclEntry1.addPermission(pr7);
		aclEntry1.addPermission(pr8);
		
		acl.getACLEntries().add(aclEntry);
		acl.getACLEntries().add(aclEntry1);
		//Serialize
//		XiFactory factory = XiFactoryFactory.newInstance();
//		XiNode document = factory.createDocument();
//		acl.serialize(factory, document);
//		serializeDoc(document);
		serializeDoc(acl);
	}
	
	private static void serializeDoc(ACL acl) throws Exception {
//		StringWriter sw = new StringWriter();
//		XiSerializer.serialize(doc, sw);
//		System.out.println(sw.toString());
		FileOutputStream fos = new FileOutputStream("C:/temp/Test.ac");
		ACLUtils.writeACL(acl, fos, ACLConstants.SERIALIZE_MODE, null);
	}
}
