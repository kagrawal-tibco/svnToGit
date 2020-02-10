/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.functions.object;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.BUI;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;
import static com.tibco.be.model.functions.FunctionDomain.QUERY;
import com.tibco.be.model.functions.Enabled;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;

import com.tibco.be.functions.trax.dom.builder.impl.GVXMLNodeBuilder;
import com.tibco.be.functions.trax.transform.XSLTransformer;
import com.tibco.be.functions.xinodeassist.SimpleRegexAssistant;
import com.tibco.be.functions.xinodeassist.XiNodeAssistant;
import com.tibco.be.functions.xpath.ConceptSequenceHandler;
import com.tibco.be.functions.xpath.XSLT2Helper;
import com.tibco.be.model.functions.MapperElementType;
import com.tibco.be.model.functions.Variable;
import com.tibco.be.model.functions.VariableList;
import com.tibco.be.model.rdf.XiNodeBuilder;
import com.tibco.be.util.EngineTraxSupport;
import com.tibco.be.util.TemplatesArgumentPair;
import com.tibco.be.util.TraxSupport;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiSerializer;

/**
 * User: ssubrama
 * Date: Sep 1, 2004
 * Time: 8:47:53 PM
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Instance",
        synopsis = "Functions to create and modify instances of type Concept")

public class ObjectHelper extends XSLTransformer {

  @com.tibco.be.model.functions.BEFunction(
        name = "newInstance",
        synopsis = "Creates a new Concept instance of the type specified by <code>uri</code> and adds\nit to the working memory. Adding the instance to the working memory will\ncause any rule conditions that depend on the concept to be evaluated.",
        signature = "Concept newInstance (String uri, String extId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI in the project of the Concept to create"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "extId of the newly created Concept instance")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The newly created Concept instance."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a new Concept instance of the type specified by <code>uri</code> and adds\nit to the working memory. Adding the instance to the working memory will\ncause any rule conditions that depend on the concept to be evaluated.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Concept newInstance(String uri, String extId) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            TypeDescriptor descriptor = session.getRuleServiceProvider().getTypeManager().getTypeDescriptor(uri);
            Constructor cons = descriptor.getConstructor();
            Concept cept = (Concept) cons.newInstance(session.getRuleServiceProvider().getIdGenerator().nextEntityId(descriptor.getImplClass()));
            cept.setExtId(extId);

            if (cept.hasMainStateMachine()) {
                if (cept.isAutoStartupStateMachine()) {
                	cept.startMainStateMachine();
                }
            }
            session.assertObject(cept, false);
            return cept;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "newTransientInstance",
        synopsis = "Creates a new Concept instance of the type specified by <code>uri</code> without adding it\nto the working memory. Thus no rules will be evaluated when this concept is created.\nShould be used when concept is intended to be used only as data holder without need for it to participate in Rete cycle.",
        signature = "Concept newTransientInstance (String uri)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI in the project of the Concept to create")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The newly created Concept instance."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a new Concept instance of the type specified by <code>uri</code> without adding it\nto the working memory. Thus no rules will be evaluated when this concept is created.\nShould be used when concept is intended to be used only as data holder without need for it to participate in Rete cycle.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Concept newTransientInstance(String uri) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            Class entityClass = session.getRuleServiceProvider().getTypeManager()
                    .getTypeDescriptor(uri).getImplClass();
            Constructor cons = entityClass.getConstructor(new Class[]{long.class, String.class});
            return (Concept) cons.newInstance(new Object[]{
                    new Long(session.getRuleServiceProvider().getIdGenerator().nextEntityId(entityClass)),
                    null});
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public static Concept createInstance2(String xslt, VariableList varlist) {
        return createInstance(xslt, xslt, varlist);
    }

    public static void updateInstance2(String xslt, VariableList varlist) {
    	updateInstance(xslt, xslt, varlist);
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "createInstance",
            synopsis = "Creates a new Concept instance based on the data provided in the XSLT \nMapper and adds it to the working memory. Adding the instance to the working memory will\ncause any rule conditions that depend on the concept to be evaluated.",
            signature = "Concept createInstance (String xslt-template)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xslt-template", type = "String", desc = "String formed using the XSLT Mapper.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The newly created Concept instance."),
            version = "1.0",
            see = "",
            mapper =  @com.tibco.be.model.functions.BEMapper(
            		enabled=@com.tibco.be.model.functions.Enabled(value=true),
            		type=MapperElementType.XSLT,
            		inputelement="<xsd:schema xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
            				"			<xsd:element name=\"createObject\">\n" +
            				"				<xsd:complexType>\n" +
            				"					<xsd:sequence>\n" +
            				"						<xsd:element name=\"object\" type=\"xsd:anyType\"\n" +
            				"							minOccurs=\"1\" maxOccurs=\"1\" />\n" +
            				"						</xsd:sequence>\n" +
            				"				</xsd:complexType>\n" +
            				"			</xsd:element>\n" +
            				"</xsd:schema>"),
            description = "Creates a new Concept instance based on the data provided in the XSLT \nMapper and adds it to the working memory. Adding the instance to the working memory will\ncause any rule conditions that depend on the concept to be evaluated.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static Concept createInstance(String key, String xslt, VariableList varlist) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            Concept cept = createInstance3(session, key, xslt, varlist);
            return cept;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @com.tibco.be.model.functions.BEFunction(
    		name = "updateInstance",
    		synopsis = "Updates a Concept instance based on the data provided in the XSLT \nMapper. Modifying the instance in the working memory will\ncause any rule conditions that depend on the concept to be evaluated.",
    		signature = "void updateInstance (String xslt-template)",
    		params = {
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "xslt-template", type = "String", desc = "String formed using the XSLT Mapper.")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
    		version = "1.0",
    		see = "",
    		mapper =  @com.tibco.be.model.functions.BEMapper(
    				enabled=@com.tibco.be.model.functions.Enabled(value=true),
    				type=MapperElementType.XSLT,
    				inputelement="<xsd:schema xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
    						"			<xsd:element name=\"updateObject\">\n" +
    						"				<xsd:complexType>\n" +
    						"					<xsd:sequence>\n" +
    						"						<xsd:element name=\"object\" type=\"xsd:anyType\"\n" +
    						"							minOccurs=\"0\" maxOccurs=\"1\" />\n" +
    						"						</xsd:sequence>\n" +
    						"				</xsd:complexType>\n" +
    						"			</xsd:element>\n" +
    				"</xsd:schema>"),
    				description = "Updates a Concept instance based on the data provided in the XSLT \nMapper. Modifying the instance in the working memory will\ncause any rule conditions that depend on the concept to be evaluated.",
    				cautions = "none",
    				fndomain = {ACTION},
    				example = ""
    		)
    public static void updateInstance(String key, String xslt, VariableList varlist) {
    	try {
    		RuleSession session = RuleSessionManager.getCurrentRuleSession();
            RuleServiceProvider provider = session.getRuleServiceProvider();
            TypeManager manager = provider.getTypeManager();
            GlobalVariables gVars = provider.getGlobalVariables();

            TemplatesArgumentPair tap = EngineTraxSupport.getTemplates(key, xslt, manager);
            List<String> paramNames = tap.getParamNames();
            String varName = tap.getInstanceVarName();
            Variable variable = varlist.getVariable(varName);
            Concept rootConcept = (Concept) variable.getObject();
            
            XiNode nodes[] = new XiNode[paramNames.size()];
            int i = 0;
            for(String paramName : paramNames) {
                if (paramName.equalsIgnoreCase(GlobalVariablesProvider.NAME)) {
                    nodes[i] = gVars.toXiNode();
                }
                else {
                    Variable v = varlist.getVariable(paramName);
                    if (v != null) {
                        nodes[i] = XiNodeBuilder.makeXiNode(v);
                    }
                    else {
                        nodes[i] = null;
                    }
                }
                ++i;
            }
            
            ConceptSequenceHandler sh = new ConceptSequenceHandler(rootConcept, session);
        	XSLT2Helper.doTransform(tap, nodes, xslt, varlist, sh, rootConcept);
        	return;
    	}
    	catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }

    public static void createStateMachine(RuleSession session, ConceptImpl rootConcept, boolean startChildren, boolean forceStart) {
        if (forceStart || rootConcept.isAutoStartupStateMachine()) {
            rootConcept.startMainStateMachine();
        }

        if (startChildren) {
            Property.PropertyContainedConcept[] containedProperties =
                    ((ConceptImpl) rootConcept).getContainedConceptProperties();

            if (containedProperties != null) {
                for (int i = 0; i < containedProperties.length; i++) {
                    if (containedProperties[i] instanceof PropertyAtomContainedConcept) {
                        ConceptImpl childConcept =
                                (ConceptImpl) ((PropertyAtomContainedConcept) containedProperties[i])
                                        .getContainedConcept();
                        if (childConcept != null) {
                            if (forceStart || childConcept.isAutoStartupStateMachine()) {
                                createStateMachine(session, childConcept, startChildren, forceStart);
                            }
                        }
                    }
                    else if (containedProperties[i] instanceof PropertyArrayContainedConcept) {
                        PropertyArrayContainedConcept cp =
                                (PropertyArrayContainedConcept) containedProperties[i];
                        for (int j = 0; j < cp.length(); j++) {
                            ConceptImpl childConcept =
                                    (ConceptImpl) ((PropertyAtomContainedConcept) cp.get(j))
                                            .getContainedConcept();
                            if (childConcept != null) {
                                if (forceStart || childConcept.isAutoStartupStateMachine()) {
                                    createStateMachine(session, childConcept, startChildren, forceStart);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static Concept createInstance3(RuleSession session, String key, String xslt,
                                          VariableList varlist) {
        try {
            Concept rootConcept = transform2Instance(session, key, xslt, varlist);
            createStateMachine(session, (ConceptImpl) rootConcept, true, false);
            session.assertObject(rootConcept, false);
            return rootConcept;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Concept transform2Instance(RuleSession session, String key, String xslt,
                                             VariableList varlist) throws Exception {
        RuleServiceProvider provider = session.getRuleServiceProvider();
        TypeManager manager = provider.getTypeManager();
        GlobalVariables gVars = provider.getGlobalVariables();

        TemplatesArgumentPair tap = EngineTraxSupport.getTemplates(key, xslt, manager);
        List<String> paramNames = tap.getParamNames();
        XiNode nodes[] = new XiNode[paramNames.size()];
        int i = 0;
        for(String paramName : paramNames) {
            if (paramName.equalsIgnoreCase(GlobalVariablesProvider.NAME)) {
                nodes[i] = gVars.toXiNode();
            }
            else {
                Variable v = varlist.getVariable(paramName);
                if (v != null) {
                    nodes[i] = XiNodeBuilder.makeXiNode(v);
                }
                else {
                    nodes[i] = null;
                }
            }
            ++i;
        }
        Class clz = tap.getRecvParameterClass();
        Concept rootConcept = null;
        if (clz != null) {
            long id = session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz);
            Constructor cons = clz.getConstructor(new Class[]{long.class});
            rootConcept = (Concept) cons.newInstance(new Object[]{new Long(id)});
        }
        if (TraxSupport.isXPath2()) {
        	ConceptSequenceHandler sh = new ConceptSequenceHandler(rootConcept, session);
        	XSLT2Helper.doTransform(tap, nodes, xslt, varlist, sh, rootConcept);
        	return rootConcept;
        }
        
        SAX2ConceptInstance ci = new SAX2ConceptInstance(rootConcept, session);
        TraxSupport.doTransform(tap.getTemplates(), nodes, ci);
        return rootConcept;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getStateMachineOwner",
        synopsis = "Returns the owner Concept instance with which a state machine is associated.\nReturns null if <code>instance</code> is not a state machine.",
        signature = "Concept getStateMachineOwner (Concept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "Concept instance")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The owner Concept instance or null if <code>instance</code> is not a state machine."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the owner Concept instance with which a state machine is associated.\nReturns null if <code>instance</code> is not a state machine.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Concept getStateMachineOwner(Concept instance) {
        if (instance instanceof StateMachineConcept) {
            return ((StateMachineConcept) instance).getParent();
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isStateMachine",
        synopsis = "Checks if a concept is a state machine.  Concepts that have state machines are state machine owners but are not state machines themselves.",
        signature = "boolean isStateMachine (Concept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "Concept instance")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if <code>instance</code> is a state machine"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Checks if a concept is a state machine.  Concepts that have state machines are state machine owners but are not state machines themselves.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean isStateMachine(Concept instance) {
        return (instance instanceof StateMachineConcept);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "deleteInstance",
        synopsis = "Retracts <code>instance</code> from the working memory and deletes it. Contained concept instances are also deleted. Referenced concept instances must be explicitly deleted",
        signature = "Concept deleteInstance (Concept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "Concept instance to be retracted from the working memory and deleted.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "<code>instance</code>."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retracts <code>instance</code> from the working memory and deletes it. Contained concept instances are also deleted. Referenced concept instances must be explicitly deleted",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Concept deleteInstance(Concept instance) {
        if (instance == null) {
            return null;
        }

        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
                if (instance != null) {
                    session.retractObject(instance, true);
	            }
                return instance;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        throw new RuntimeException("Rule Session is NULL");
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "deleteByExtId",
        synopsis = "Retracts and deletes the Concept instance identified by <code>extId</code>.",
        enabled = @Enabled(value=false),
        signature = "Concept deleteByExtId (String extId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "The extId of the Concept instance to be retracted and deleted.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The retracted Concept instance, null if not found."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retracts and deletes the Concept instance identified by <code>extId</code>.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Concept deleteByExtId(String extId) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        Concept entity = (Concept) session.getObjectManager().getElement(extId);
        if (entity != null) {
            session.retractObject(entity, true);
            return entity;
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "deleteById",
        synopsis = "Retracts and deletes the Concept instance identified by <code>id</code>.",
        enabled = @Enabled(value=false),
        signature = "Concept deleteById (long id)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "id", type = "long", desc = "The id of the Concept instance to be retracted and deleted.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The retracted Concept instance, null if not found."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retracts and deletes the Concept instance identified by <code>id</code>.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Concept deleteById(long id) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        Concept entity = (Concept) session.getObjectManager().getElement(id);
        if (entity != null) {
            session.retractObject(entity, true);
            return entity;
        }
        return null;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getByExtId",
        synopsis = "Returns the Concept instance identified by <code>extId</code>.\n<b>Note:</b> This method should not be used to load cache-only concepts into working\nmemory for modifications, especially if they have not already been loaded in a pre-processor.",
        signature = "Concept getByExtId (String extId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "The extId of the Concept instance to be returned.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The Concept instance identified by <code>extId</code> or null if not found."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Concept instance identified by <code>extId</code>.\n<b>Note:</b> This method should not be used to load cache-only concepts into working\nmemory for modifications, especially if they have not already been loaded in a pre-processor.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Concept getByExtId(String extId) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();

        ObjectManager om = session.getObjectManager();
//      if (om instanceof DefaultDistributedCacheBasedStore) {
//           if (useMutableGets) {
//                return CoherenceFunctions.C_CacheLoadConceptByExtId(extId, true);
//           }
//      }

        return (Concept) om.getElement(extId);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getById",
        synopsis = "Returns the Concept instance Concept instance identified by <code>id</code>.\n<b>Note:</b> This method should not be used to load cache-only concepts into working\nmemory for modifications, especially if they have not already been loaded in a pre-processor.",
        signature = "Concept getById (long Id)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "id", type = "long", desc = "The id of the Concept instance to be returned.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "Concept instance identified by <code>id</code> or null if not found."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Concept instance identified by <code>id</code>.\n<b>Note:</b> This method should not be used to load cache-only concepts into working\nmemory for modifications, especially if they have not already been loaded in a pre-processor.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Concept getById(long Id) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        return (Concept) session.getObjectManager().getElement(Id);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getReverseReferences",
        synopsis = "Returns ids of all concepts which have concept reference properties that refer to <code>instance</code>.",
        signature = "long[] getReverseReferences(Concept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "A Concept instance.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long[]", desc = "Returns ids of all concepts which have concept reference properties that refer to <code>instance</code>."),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns ids of all concepts which have concept reference properties that refer to <code>instance</code>.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static long[] getReverseReferences(Concept instance) {
    	if (instance == null) {
    		return new long[]{ };
    	}
        return instance.getReverseReferences();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getRuntimeTypeById",
        synopsis = "Returns the java class name of the Entity instance with id matching the argument <code>id</code> or null if not found.",
        enabled = @Enabled(value=false),
        signature = "String getRuntimeTypeById(long id)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "id", type = "long", desc = "The id of an Entity instance")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The java class name of the Entity instance with id matching the argument <code>id</code> or null if not found."),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns The java class name of the Entity instance with id matching the argument <code>id</code> or null if not found.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static String getRuntimeTypeById(long id) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        BaseHandle h = ((BaseObjectManager) session.getObjectManager()).getElementHandle(id);
        if (h != null) {
            return h.getTypeInfo().getType().getName();
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getRuntimeType",
        synopsis = "Returns the java class name of <code>instance</code>.",
        enabled = @Enabled(value=false),
        signature = "String getRuntimeType(Concept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "A Concept instance.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The java class name of <code>instance</code>"),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the java class name of <code>instance</code>.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static String getRuntimeType(Concept instance) {
        return instance.getClass().getName();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "serialize",
        synopsis = "Serializes a Concept instance into an XML string.",
        signature = "String serialize (Concept instance, boolean changedOnly, String nameSpace, String root)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "The Concept instance to serialize."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "changedOnly", type = "boolean", desc = "ignored"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "nameSpace", type = "String", desc = "The namespace for the serialized data."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "root", type = "String", desc = "The name of the root element for the serialized data.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "An XML string serialization of <code>instance</code>"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Serializes a Concept instance into an XML string.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )

    public static String serialize(Concept instance, boolean changedOnly, String nameSpace,
                                   String root) {
        try {
//            if(instance == null) return null;
//            ExpandedName rootNm=ExpandedName.makeName(nameSpace, root);
//            Writer writer=new StringWriter();
//            DefaultXmlContentSerializer handler = new DefaultXmlContentSerializer(writer, "UTF-8");
//            handler.startElement(rootNm,null,null);
//            ((XmlSerializable) instance).content(handler,changedOnly);
//            handler.endElement(rootNm,null,null);
//            writer.flush();
//            return writer.toString();

            //todo - find out why not use XiNode?  this doesn't serialize the reference
            if (instance == null) {
                return null;
            }
            ExpandedName rootNm = ExpandedName.makeName(nameSpace, root);
            XiNode node = XiSupport.getXiFactory().createElement(rootNm);
            instance.toXiNode(node, changedOnly);
            return XiSerializer.serialize(node);

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "serializeInstance",
        synopsis = "Serializes a Concept instance into an XML string.",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.Instance.serialize.instance", value=false),
        signature = "String serializeInstance (Concept instance, boolean pretty)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "The Concept instance to serialize."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pretty", type = "boolean", desc = "If true, the output will be formatted for human-readability.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "An XML string serialization of <code>instance</code>."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Serializes a Concept instance into an XML string.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static String serializeInstance(Concept instance, boolean pretty) {
        if (instance == null) {
            return null;
        }
        
        boolean useXi = RuleSessionManager.getCurrentRuleSession()
				.getRuleServiceProvider().getProperties()
				.getProperty("tibco.be.instance.serialize.usexinodes", "false").
				equals("true");
        String xml = "";
        if (useXi) {
            try {
    			ExpandedName rootNm = instance.getExpandedName();
    			XiNode node = XiSupport.getXiFactory().createElement(rootNm);
    			ContentHandler handler = new XiContentHandler(node);
    			OutputStream oStream = new ByteArrayOutputStream();
    			InstanceSerializationHelper.serialize(handler, instance, pretty);
    			XiSerializer.serialize(node, oStream, pretty);
    			xml = oStream.toString();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        } else {
            StringBuilder writer = new StringBuilder();
            ContentHandler handler = new XMLContentHandler(writer);
            InstanceSerializationHelper.serialize(handler, instance, pretty);
            //writer.flush();
            xml = writer.toString();
        }
        return xml;
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "serializeUsingDefaults",
        synopsis = "Serializes a Concept instance into an XML string using the default namespace URI for Concepts.",
        signature = "String serializeUsingDefaults (Concept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "The Concept instance to serialize.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "An XML String serialization of <code>instance</code>."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Serializes a Concept instance into an XML string using the default namespace URI for Concepts.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static String serializeUsingDefaults(Concept instance) {
        try {
            if (instance == null) {
                return null;
            }
            ExpandedName rootNm = instance.getExpandedName();
            XiNode node = XiSupport.getXiFactory().createElement(rootNm);
            instance.toXiNode(node);
            String xml = XiSerializer.serialize(node);
            return xml;
        }
        catch (Exception e) {
            throw new RuntimeException("Error while serializing concept "
                    + instance.getExpandedName().getNamespaceURI(), e);
        }
    }
    
    static final int maxCachedAssistants = 128;

    static final ConcurrentHashMap<String, XiNodeAssistant> recentAssistants =
            new ConcurrentHashMap<String, XiNodeAssistant>(maxCachedAssistants * 2);

    static final AtomicInteger numRecentAssistants = new AtomicInteger();

    @com.tibco.be.model.functions.BEFunction(
        name = "filterAndSerializeUsingDefaults",
        synopsis = "Serializes a Concept instance into an XML String with the default namespace URI for concepts,\nand with the inclusion of properties controlled by a regex filter.",
        signature = "String filterAndSerializeUsingDefaults (Concept instance, String propertyNameRegexFilter)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "The Concept instance to serialize."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyNameRegexFilter", type = "String", desc = "java.util.regex.Pattern.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "An XML string serialization of <code>instance</code> containing a filtered set of properties."),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Serializes a Concept instance into an XML String with the default namespace URI for concepts,\nand with the inclusion of properties controlled by a regex filter",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static String filterAndSerializeUsingDefaults(Concept instance,
                                                         String propertyNameRegexFilter) {
        try {
            if (instance == null) {
                return null;
            }
            ExpandedName rootNm = instance.getExpandedName();
            XiNode node = XiSupport.getXiFactory().createElement(rootNm);

            XiNodeAssistant nodeAssistant = recentAssistants.get(propertyNameRegexFilter);
            if (nodeAssistant == null) {
                nodeAssistant = new SimpleRegexAssistant(propertyNameRegexFilter);

                /*
                Code to cleanup the cache. Crude but effective. No LRU/LFU. Just removes some
                random element.
                */
                if (recentAssistants.putIfAbsent(propertyNameRegexFilter, nodeAssistant) == null) {
                    if (numRecentAssistants.incrementAndGet() > maxCachedAssistants) {
                        Iterator<Map.Entry<String, XiNodeAssistant>> entries =
                                recentAssistants.entrySet().iterator();

                        //Pick a random index/position to delete since we don't use LRU/LFU.
                        final int index =
                                (int) (System.nanoTime() % (maxCachedAssistants - 1));
                        int counter = 0;
                        while (entries.hasNext()) {
                            entries.next();

                            if (counter == index) {
                                entries.remove();
                                /*
                                No guarantee that remove() succeeded. So, this could be a false
                                decrement.
                                */
                                numRecentAssistants.decrementAndGet();

                                break;
                            }
                            counter++;
                        }
                    }
                }
            }

            nodeAssistant.filterAndFill(instance, node);

            return XiSerializer.serialize(node);
        }
        catch (Exception e) {
            throw new RuntimeException("Error while serializing concept "
                    + instance.getExpandedName().getNamespaceURI(), e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isModified",
        synopsis = "This function returns true if <code>instance</code> has been modified by a currently running RTC.\nThis function will return false again once any of the modifying RTCs has ended.",
        signature = "boolean isModified (Concept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "The Concept instance to test for modification.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "True if <code>instance</code> has been modified during a current RTC."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This function returns true if <code>instance</code> has been modified by a currently running RTC.",
        cautions = "This function works differently with scorecards than with concept instances. There is only one instance of a scorecard per agent, rather than one per RTC. So after a scorecard is modified it will return true until the agent is restarted.",
        fndomain = {ACTION, CONDITION, BUI},
        reevaluate = true,
        example = ""
    )
    public static boolean isModified(Concept instance) {
        return ((ConceptImpl) instance).isModified();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isNew",
        synopsis = "This function returns true if <code>instance</code> was created by a currently running RTC.\nIt also returns true if <code>instance</code> has been configured for cache-only mode and has not been loaded into any current RTC.",
        signature = "boolean isNew (Concept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "The Concept instance to test.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "True if <code>instance</code> was created by a currently running RTC."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This function returns true if <code>instance</code> was created by a currently running RTC.\nIt also returns true if <code>instance</code> has been configured for cache-only mode and has not been loaded into any current RTC.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        reevaluate = true,
        example = ""
    )
    public static boolean isNew(Concept instance) {
        return ((ConceptImpl) instance).isNew();
    }

    public static Concept newConceptInstance(RuleSession session, Class clz, XiNode node,
                                             boolean startSM) throws Exception {
        if (!Concept.class.isAssignableFrom(clz)) {
            throw new Exception("Invalid class type specified - " + clz);
        }

        if (Modifier.isAbstract(clz.getModifiers())) {
            ExpandedName name = node.getType().getExpandedName(); //Type Substitution should happen
            clz = session.getRuleServiceProvider().getTypeManager().getTypeDescriptor(name)
                    .getImplClass();
            if (Modifier.isAbstract(clz.getModifiers())) {
                throw new Exception(
                        "Cannot instantiate abstract Concept class, provide a concrete Type substitution");
            }
        }

        Constructor cons = clz.getConstructor(new Class[]{long.class});
        Concept cept = (Concept) cons.newInstance(new Object[]{
                new Long(session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz))});
        if (startSM && cept.hasMainStateMachine()) {
            cept.startMainStateMachine();
        }

        //Use SAX4ConceptInstance...
        SAX4ConceptInstance ci = new SAX4ConceptInstance(cept, session);
        node.serialize(ci);

        return cept;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "createInstanceFromXML",
        synopsis = "This function creates a new Concept instance by deserializing the XML data provided.\nIf <code>uri</code> is null, then the URI will be inferred from the namespace in <code>xml</code>.\nThe form of the XML data provided is expected to be the same as the output from <code>serialize()</code>.\nThe new Concept instance is added to the working memory. Adding the instance to the working memory will\ncause any rule conditions that depend on the concept to be evaluated.",
        signature = "Concept createInstanceFromXML (String uri, String xml)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "URI", desc = "concept."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xml", type = "String", desc = "XML string to be parsed")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The newly created concept"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This function creates a new Concept instance by deserializing the XML data provided.\nIf <code>uri</code> is null, then the URI will be inferred from the namespace in <code>xml</code>.\nThe form of the XML data provided is expected to be the same as the output from <code>serialize()</code>.\nThe new Concept instance is added to the working memory. Adding the instance to the working memory will\ncause any rule conditions that depend on the concept to be evaluated.",
        cautions = "none. This function asserts the created concept but this assert will not fire any rules.",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Concept createInstanceFromXML(String uri, String xml) {
		//  xml=<?xml version="1.0" encoding="ISO-8859-1"?>
		//      <ns0:NewConcept xmlns:ns0="www.tibco.com/be/ontology/NewConcept" extId="95">
		//          <NewConcept_property_1>custom</NewConcept_property_1>
    	//      	<NewConcept_property_2>hello world</NewConcept_property_2>
		//      </ns0:NewConcept>
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
                Concept cept = createTransientInstanceFromXML(uri, xml);
                session.assertObject(cept, false);
                return cept;
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        throw new RuntimeException("Function createInstanceFromXML not allowed outside of a rule session");
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "createTransientInstanceFromXML",
        synopsis = "This function creates a new Concept instance by deserializing the XML data provided.\nIf <code>uri</code> is null, then the URI will be inferred from the namespace in <code>xml</code>.\nThe form of the XML data provided is expected to be the same as the output from <code>serialize()</code>.\nThe newly created Concept instance is NOT added to the working memory.",
        signature = "Concept createTransientInstanceFromXML (String uri, String xml)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "concept."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xml", type = "String", desc = "XML string to be parsed")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The newly created concept"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This function creates a new Concept instance by deserializing the XML data provided.\nIf <code>uri</code> is null, then the URI will be inferred from the namespace in <code>xml</code>.\nThe form of the XML data provided is expected to be the same as the output from <code>serialize()</code>.\nThe newly created Concept instance is NOT added to the working memory.",
        cautions = "none. The function does not assert the created concept.",
        fndomain = {ACTION},
        example = ""
    )
    public static Concept createTransientInstanceFromXML(String uri, String xml) {
        RuntimeException exp = null;
        Concept ci = null;
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
                if (uri != null) {
                    TypeManager.TypeDescriptor type = session.getRuleServiceProvider()
                            .getTypeManager().getTypeDescriptor(uri);
                    if (type != null) {
                        Class clz = type.getImplClass();
                        Constructor cons = clz.getConstructor(new Class[]{long.class});
                        Concept cept = (Concept) cons.newInstance(new Object[]{new Long(
                                session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz))});
                        SAX4ConceptInstance tmp = new SAX4ConceptInstance(cept, session);
                        //This is a hack to avoid ClassCastExceptions
                        //arising from XiParserFactory's implementation
                        //which uses context classloader of the current thread
                        //to load DefaultXiParser
                        XiSupport.getParser().parse(new InputSource(new StringReader(xml)), tmp);
                        ci = tmp.ci;
                    }
                    else {
                        exp = new RuntimeException("Invalid Entity URI " + uri);
                    }
                }
                else {
                    SAX4ConceptInstance tmp = new SAX4ConceptInstance(session);
                    XiParserFactory.newInstance()
                            .parse(new InputSource(new StringReader(xml)), tmp);
                    ci = tmp.ci;
                }
                
				if (ci != null) {
                    if (ci instanceof ConceptImpl) {
                        createStateMachine(session, (ConceptImpl) ci, true, false);
                    }
                }
            }
            else {
                exp = new RuntimeException(
                        "Function createInstanceFromXML not allowed outside of a rule session");
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        
        if(exp != null) throw exp;
        return ci;
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "createTransientInstanceFromEvent",
        synopsis = "This function returns a concept instance using the payload from the event passed\nin the <code> event </code> parameter. The payload must adhere to\nthe XML schema corresponding to the concept definition. The concept\ninstance is not asserted (hence it is $1transient$1). You can then, for\nexample, use the transient concept's property values to update an \nasserted concept's properties.",
        signature = "Concept createTransientInstanceFromEvent (String uri, SimpleEvent event)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI of the concept as defined in the project. uri cannot be null."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "SimpleEvent", type = "Event", desc = "received")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The newly created concept."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This function returns a concept instance using the event's payload. Returned\ninstance is not asserted to WM",
        cautions = "none. The function does not assert the created concept.",
        fndomain = {ACTION},
        example = ""
    )
    public static Concept createTransientInstanceFromEvent(String uri, SimpleEvent event) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
                if (uri != null) {
                    TypeManager.TypeDescriptor type = session.getRuleServiceProvider()
                            .getTypeManager().getTypeDescriptor(uri);
                    if (type != null) {
                        Class clz = type.getImplClass();
                        Constructor cons = clz.getConstructor(new Class[]{long.class});
                        Concept cept = (Concept) cons.newInstance(new Object[]{new Long(
                                session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz))});
                        Object object = event.getPayload().getObject();
                        SAX4ConceptInstance tmp = new SAX4ConceptInstance(cept, session);
                        if (object instanceof XiNode) {
                            XiNodeParser.getInstance().parse((XiNode)object, tmp);
                        } else {
                            //Check if it can be converted to XiNode
                            if (object instanceof byte[]) {
                                byte[] bytes = (byte[])object;
                                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                                XiNode xiNode = XiParserFactory.newInstance().parse(new InputSource(bis));
                                if (xiNode != null) {
                                    XiNodeParser.getInstance().parse(xiNode, tmp);
                                }
                            }
                        }
                        return cept;
                    }
                    else {
                        throw new RuntimeException("Invalid Entity URI " + uri);
                    }
                }
                else {
                	throw new RuntimeException("Entity URI can not be null");
                }
            }
            else {
                throw new RuntimeException(
                        "Function createTransientInstanceFromEvent not allowed outside of a rule session");
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getExpandedName",
        synopsis = "Returns the fully qualified namespace URI for <code>instance</code>.",
        signature = "String getExpandedName (Concept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "A Concept instance")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The fully qualified namespace URI of <code>instance</code>"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the fully qualified namespace URI for <code>instance</code>.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI, QUERY},
        example = ""
    )
    public static String getExpandedName(Concept instance) {
        if (instance == null) {
            throw new RuntimeException("Concept instance cannot be null");
        }
        ExpandedName expName = instance.getExpandedName();
        if (expName == null) {
            return null;
        }
        return expName.getNamespaceURI();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "updateInstanceFromXML",
        synopsis = "Update a Concept instance with data contained in an XML serialization of concept data.\nThe Concept instance to be updated is identified by an extId attribute in the serialized concept data.\nAn Exception will be thrown if the extId provided does not match any existing Concept instance.",
        signature = "Concept updateInstanceFromXML (String xml)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xml", type = "String", desc = "An XML string to be deserialized")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The updated Concept instance"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Update a Concept instance with data contained in an XML serialization of concept data.\nThe Concept instance to be updated is identified by an extId attribute in the serialized concept data.\nAn Exception will be thrown if the extId provided does not match any existing Concept instance.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Concept updateInstanceFromXML(String xml) {
		//  xml=<?xml version="1.0" encoding="ISO-8859-1"?>
		//     	<ns0:NewConcept xmlns:ns0="www.tibco.com/be/ontology/NewConcept" extId="95">
		//      	<NewConcept_property_1 modified="true">custom_1</NewConcept_property_1> 
    	//      	<NewConcept_property_2 modified="true"> should not get update hello world</NewConcept_property_2>
		//      </ns0:NewConcept>
    	try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
                SAX5ConceptInstance tmp = new SAX5ConceptInstance(session);
                XiParserFactory.newInstance().parse(new InputSource(new StringReader(xml)), tmp);
                return tmp.ci;
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        throw new RuntimeException("Function updateInstanceFromXML not allowed outside of a rule session");
    }
        
    @com.tibco.be.model.functions.BEFunction(
        name = "update",
        synopsis = "Update a Concept instance with the values from another Concept instance.",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.Instance.update", value=false),
        signature = "Concept update(Concept model, Concept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "model", type = "Concept", desc = "The Concept instance to be updated"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "The Concept instance from which values will be used to update <code>model</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "True if <code>instance</code> is modified."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Update a Concept instance with the values from another Concept instance.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean update(Concept model, Concept instance){
    	if (model == null 
    			|| instance == null 
    			|| !model.getExpandedName().getNamespaceURI().equals(instance.getExpandedName().getNamespaceURI())) {
    		throw new IllegalArgumentException("The parameters must be not null and should of same concept type");
    	}
    	Property[] props = instance.getProperties();
    	for (int i = 0; i < props.length; i++) {
    		// At present handing PropertyAtom only
    		// need to handle PropertyArray
    		if (props[i] instanceof PropertyAtom) {
	    		PropertyAtom prop = (PropertyAtom)props[i];
				if (prop.isSet()) {
					Property p = model.getProperty(prop.getName());
					((PropertyAtom)p).setValue(prop.getValue());
				}
    		}
		}
    	return true;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "startStateMachine",
        synopsis = "Start a Concept instance's main state machine, and optionally, the main state machines of its contained concepts.",
        signature = "void startStateMachine(Concept instance, boolean startChildren)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "A Concept instance"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "startChildren", type = "boolean", desc = "If true, start state machines for all concepts contained by <code>instance</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Start a Concept instance's main state machine, and optionally, the main state machines of its contained concepts.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void startStateMachine(Concept instance, boolean startChildren) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        createStateMachine(session, (ConceptImpl) instance, startChildren, true);
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getByExtIdByUri",
        synopsis = "Returns the Concept instance identified by <code>extId</code> and with URI matching <code>URI</code> or null if not found.\n<b>Note:</b> This method should not be used to load cache-only concepts into working\nmemory for modifications, especially if they have not already been loaded in a pre-processor.",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.getbyuri", value=true),
        signature = "Concept getByExtIdByURI (String extId, String uri)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "The extId of the Concept instance to be returned"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI of a Concept in the project")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The Concept instance identified by <code>extId</code> and with URI matching <code>URI</code> or null if not found."),
        version = "3.0.1-HF9",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Concept instance identified by <code>extId</code> and with URI matching <code>URI</code> or null if not found.\n<b>Note:</b> This method should not be used to load cache-only concepts into working\nmemory for modifications, especially if they have not already been loaded in a pre-processor.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Concept getByExtIdByUri(String extId, String uri) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();

        ObjectManager om = session.getObjectManager();
//      if (om instanceof DefaultDistributedCacheBasedStore) {
//           if (useMutableGets) {
//                return CoherenceFunctions.C_CacheLoadConceptByExtIdByUri(extId, true, uri);
//           }
//      }
		TypeDescriptor td = session.getRuleServiceProvider().getTypeManager().getTypeDescriptor(uri);
		if (td == null) {
			throw new RuntimeException("Function getByExtIdByUri can't find type: " + uri);
		}

        return (Concept) om.getElementByUri(extId, uri);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getByIdByUri",
        synopsis = "Returns the Concept instance identified by <code>id</code> and with URI matching <code>URI</code> or null if not found.\n<b>Note:</b> This method should not be used to load cache-only concepts into working\nmemory for modifications, especially if they have not already been loaded in a pre-processor.",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.getbyuri", value=true),
        signature = "Concept getByIdByURI (long id, String uri)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "id", type = "long", desc = "The id of the Concept instance to be returned"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI of a Concept in the project")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The Concept instance identified by <code>id</code> and with URI matching <code>URI</code> or null if not found."),
        version = "3.0.2-HF1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Concept instance identified by <code>id</code> and with URI matching <code>URI</code> or null if not found.\n<b>Note:</b> This method should not be used to load cache-only concepts into working\nmemory for modifications, especially if they have not already been loaded in a pre-processor.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Concept getByIdByUri(long id, String uri) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        
        ObjectManager om = session.getObjectManager();
        return (Concept) om.getElementByUri(id, uri);
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "createInstanceUsingXSLT",
        synopsis = "Create a Concept using the given xslt, xml source and parameters in scope",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.createinstance.xslt", value=false),
        signature = "Concept createInstanceUsingXSLT (String xslt, String eventURI, String xmlSource, Object[] xsltParams, String[] xsltXMLParams, String[] varName, boolean[] isArray)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xslt", type = "String", desc = "XSLT as string."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ceptURI", type = "String", desc = "Concept URI"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xmlSource", type = "String", desc = "A xml string to be used as source. It should be a valid xml or a null."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xsltParams", type = "Object[]", desc = "An array of objects to be used as xslt parameters"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xsltXMLParams", type = "String[]", desc = "An array of xml strings to be used as xslt parameters"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String[]", desc = "This will be a part of VariableList. The VariableList will contain only the parameters added to xsltParams & xsltXMLParams."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "isArray", type = "boolean[]", desc = "This will be a part of VariableList")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "A Concept created after transformation."),
        version = "3.0.2-HF1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "A Concept created after transformation.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Concept createInstanceUsingXSLT(String xslt, String ceptURI, String xmlSource, Object[] xsltParams, String[] xsltXMLParams, String[] varName, boolean[] isArray) throws RuntimeException{
		try {
			DocumentBuilder docBuilder = getDocumentBuilderFactory().newDocumentBuilder();
			XSLTransformer t = new XSLTransformer();
			
			xsltParams = paramToXMLNode(xsltParams, varName, isArray,
					docBuilder);
			
			Node[] xmlParams = xmlStringToXMLNode(xsltXMLParams, docBuilder);
			
			RuleSession session = RuleSessionManager.getCurrentRuleSession();
			RuleServiceProvider provider = session.getRuleServiceProvider();
			GlobalVariables gVars = provider.getGlobalVariables();
			GVXMLNodeBuilder builder = new GVXMLNodeBuilder();
			Element gvDocElem = builder.build(docBuilder.newDocument(), gVars, "globalVariables");
	
			Concept concept = t.transform2Concept(xslt, ceptURI, varName, xsltParams, gvDocElem, xmlParams);
			
			ObjectHelper.createStateMachine(session,  (ConceptImpl)concept, true, false);
			session.assertObject(concept, false);
	
			return concept;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
    
    @com.tibco.be.model.functions.BEFunction(
		name = "serializeToJSON",
		signature = "String serializeToJSON (Concept instance, boolean pretty, String root)",
		synopsis = "Serializes a Concept instance into an JSON string.",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "The Concept instance to serialize."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "pretty", type = "boolean", desc = "If true, the output will be formatted for human-readability."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "root", type = "String", desc = "The name of the root element for the serialized data.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "An JSON string serialization of <code>instance</code>"),
		version = "5.2",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Serializes a Concept instance into an JSON string.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static String serializeToJSON(Concept instance, boolean pretty, String root) {
		String serializedJSON = null;

		try {
			serializedJSON = JSONHelper.serializeToJSON(instance, pretty, root);
		} catch (Exception e) {
			throw new RuntimeException("Error while serializing concept "
					+ instance.getExpandedName().getNamespaceURI(), e);
		}

		return serializedJSON;
	}
    
    @com.tibco.be.model.functions.BEFunction(
        name = "createInstanceFromJSON",
        signature = "Concept createInstanceFromJSON (String uri, String json)",
        synopsis = "This function creates a new Concept instance by deserializing the JSON data provided.\nThe form of the JSON data provided is expected to be the same as the output from <code>serialize()</code>.\nThe new Concept instance is added to the working memory. Adding the instance to the working memory will\ncause any rule conditions that depend on the concept to be evaluated.",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "URI", desc = "Concept URI."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "json", type = "String", desc = "JSON string to be parsed")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The newly created concept"),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This function creates a new Concept instance by deserializing the JSON data provided.\nThe form of the JSON data provided is expected to be the same as the output from <code>serialize()</code>.\nThe new Concept instance is added to the working memory. Adding the instance to the working memory will\ncause any rule conditions that depend on the concept to be evaluated.",
        cautions = "none. This function asserts the created concept but this assert will not fire any rules.",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Concept createInstanceFromJSON(String uri, String json) {
    	try {
    		RuleSession session = RuleSessionManager.getCurrentRuleSession();
    		if (session != null) {
    			Concept cept = createTransientInstanceFromJSON(uri, json);
    			session.assertObject(cept, false);
    			return cept;
    		}
    	}
    	catch (Exception ex) {
    		throw new RuntimeException(ex);
    	}
    	throw new RuntimeException("createInstanceFromJSON not allowed outside of a rule session");
    }
	 
    @com.tibco.be.model.functions.BEFunction(
        name = "createTransientInstanceFromJSON",
        signature = "Concept createTransientInstanceFromJSON (String uri, String json)",
        synopsis = "This function creates a new Concept instance by deserializing the JSON data provided.\nThe form of the JSON data provided is expected to be the same as the output from <code>serialize()</code>.\nThe newly created Concept instance is NOT added to the working memory.",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "Concept URI."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "json", type = "String", desc = "JSON string to be parsed")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The newly created concept"),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This function creates a new Concept instance by deserializing the JSON data provided.\nThe form of the JSON data provided is expected to be the same as the output from <code>serialize()</code>.\nThe newly created Concept instance is NOT added to the working memory.",
        cautions = "none. The function does not assert the created concept.",
        fndomain = {ACTION},
        example = ""
    )
	public static Concept createTransientInstanceFromJSON(String uri, String json) {
    	RuntimeException exp = null;
		Concept ci = null;
		try {
			RuleSession session = RuleSessionManager.getCurrentRuleSession();
			if (session != null) {
				if (uri != null) {
					TypeManager.TypeDescriptor type = session.getRuleServiceProvider()
							.getTypeManager().getTypeDescriptor(uri);
					if (type != null) {
						Class clz = type.getImplClass();
						Constructor cons = clz.getConstructor(new Class[]{long.class});
						Concept cept = (Concept) cons.newInstance(new Object[]{new Long(
								session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz))});
						ci = JSONHelper.deserializeToConcept(cept, session, json);
					} else {
						exp = new RuntimeException("Invalid Entity URI " + uri);
					}
				}
				
				if (ci != null) {
                    if (ci instanceof ConceptImpl) {
                        createStateMachine(session, (ConceptImpl) ci, true, false);
                    }
                }
			} else {
                exp = new RuntimeException("create[Transient]InstanceFromJSON not allowed outside of a rule session");
            }
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		if (exp != null) throw exp;
        return ci;
	}
    
    @com.tibco.be.model.functions.BEFunction(
        name = "updateInstanceFromJSON",
        synopsis = "Update a Concept instance with data contained in an JSON serialization of concept data.\nThe Concept instance to be updated is identified by an extId attribute in the serialized concept data.\nAn Exception will be thrown if the extId provided does not match any existing Concept instance.",
        signature = "Concept updateInstanceFromJSON (String uri, String json)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI of the Concept to be updated"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "json", type = "String", desc = "An JSON string to be deserialized")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The updated Concept instance"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Update a Concept instance with data contained in an JSON serialization of concept data.\nThe Concept instance to be updated is identified by an extId attribute in the serialized concept data.\nAn Exception will be thrown if the extId provided does not match any existing Concept instance.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Concept updateInstanceFromJSON(String uri, String json) {
    	try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
            	Concept ci = JSONHelper.deserializeToConcept(session, uri, json);
                return ci;
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        throw new RuntimeException("Function updateInstanceFromJSON not allowed outside of a rule session");
    }
    
}
