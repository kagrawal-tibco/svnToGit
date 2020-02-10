package com.tibco.be.ws.rt.model;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tibco.be.ws.functions.WebstudioServerCommonFunctions;
import com.tibco.be.ws.functions.util.CONCEPT_ATTR_NAMES;
import com.tibco.be.ws.functions.util.EVENT_ATTR_NAMES;
import com.tibco.be.ws.functions.util.RULETEMPLATE_PARTICIPANT_EXTENSIONS;
import com.tibco.be.ws.functions.util.TIMEEVENT_ATTR_NAMES;
import com.tibco.be.ws.functions.util.WebstudioFunctionUtils;
import com.tibco.be.ws.scs.ISCSIntegration;
import com.tibco.be.ws.scs.SCSException;
import com.tibco.be.ws.scs.SCSIntegrationFactory;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.rules.CommonRulesParserManager;
import com.tibco.cep.studio.core.rules.ast.RuleCreatorASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/4/12
 * Time: 5:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContainerSymbolChildNode extends AbstractSymbolChildNode<SymbolEssentials> {
	
	private static final String SOAP_EVENT = "SOAPEvent";
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(ContainerSymbolChildNode.class);
	

    /**
     * Flag to indicate status and avoid reload.
     */
    protected volatile boolean entityResolutionStatus;

    /**
     *
     * @param wrapped
     */
    public ContainerSymbolChildNode(SymbolEssentials wrapped) {
        super(wrapped);
        setAlias(wrapped.getAlias());
        setType(wrapped.getSymbolType());
    }

    /**
     * Child nodes of this container. We cannot use right generics here.
     */
    protected List<AbstractSymbolChildNode> children = new ArrayList<AbstractSymbolChildNode>();
    
    private static final String ATTRIBUTE_ID = "id";
    private static final String ATTRIBUTE_LENGTH = "length";
    private static final String ATTRIBUTE_PARENT = "parent";

    public boolean hasUnvisitedChildren() {
        for (AbstractSymbolChildNode childNode : children) {
            //If a single child is also unvisited return true
            if (!childNode.isVisited()) {
                return true;
            }
        }
        return false;
    }

    /**
     *  To be called from rule source if needed.
     * @param scsIntegrationClass
     * @param repoRootURL
     * @param projectName
     * @param isCommandSymbol
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void resolveEntity(String scsIntegrationClass,
                              String repoRootURL,
                              String projectName,
                              boolean isCommandSymbol,
                              String earPath) throws Exception {
        if (!entityResolutionStatus) {
            ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationClass);
            //Hack way
            String symbolType = getType();
            boolean isPrimitive = PROPERTY_TYPES.getByName(symbolType) != null ? true : false;

            String entityContents = null;
            RULETEMPLATE_PARTICIPANT_EXTENSIONS selectedRuleParticipantExtn = null;

            if (!isPrimitive) {
	            //Dont know the extension, hence the hack.
	            for (RULETEMPLATE_PARTICIPANT_EXTENSIONS ruleParticipantExtension : RULETEMPLATE_PARTICIPANT_EXTENSIONS.values()) {
	                if (scsIntegration.fileExists(repoRootURL, projectName, symbolType, ruleParticipantExtension.getLiteral())) {
	                    try {
	                        entityContents = scsIntegration.showFileContents(repoRootURL, projectName, symbolType, ruleParticipantExtension.getLiteral(), null, null);
	                    } catch (SCSException scsException) {
	                        LOGGER.log(Level.WARN, "Content not found in base project, will trying to look up project library");
	                    }
	                    if (entityContents != null) selectedRuleParticipantExtn = ruleParticipantExtension;
	                    break;
	                }
	            }
	
	            // check in project library if not found in the base project
	            if (entityContents == null) entityContents = WebstudioFunctionUtils.getEntityContentFromProjectLibrary(repoRootURL, projectName, symbolType, null, earPath);
	            
	            if (entityContents == null || entityContents.isEmpty()) throw new RuntimeException("Artifact [" + symbolType + "] not found even in project libraries");
	            else {
	              if (selectedRuleParticipantExtn == RULETEMPLATE_PARTICIPANT_EXTENSIONS.RULEFUNCTION) {
	                    processCompilable(projectName, entityContents);
	                } else {
	                    processScopeEntities(scsIntegration, repoRootURL, projectName, entityContents, isCommandSymbol, earPath);
	                }
	            }
            }
            
            entityResolutionStatus = true;
        }
    }

    /**
     * Get all inherited properties of this rule participant.
     * @param scsIntegration
     * @param repoRootURL
     * @param projectName
     * @param baseRuleParticipant
     * @param isCommandSymbol
     * @param <E>
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private <E extends Entity> List<PropertyDefinition> getRuleParticpantProperties(ISCSIntegration scsIntegration,
                                                                      String repoRootURL,
                                                                      String projectName,
                                                                      E baseRuleParticipant,
                                                                      boolean isCommandSymbol,
                                                                      String earPath) throws Exception {
        String superRuleParticipantPath = null;
        String ruleParticipantExtension = null;

        List<PropertyDefinition> allChildrenPropertyDefinitions = null;
        if (baseRuleParticipant instanceof Concept) {
            Concept ruleParticipant = (Concept)baseRuleParticipant;
            ruleParticipantExtension = CommonIndexUtils.CONCEPT_EXTENSION;
            allChildrenPropertyDefinitions = ruleParticipant.getPropertyDefinitions(true);
            for (CONCEPT_ATTR_NAMES concept_attr_names : CONCEPT_ATTR_NAMES.values()) {
            	if (concept_attr_names.getLiteral().equals(ATTRIBUTE_PARENT)) continue;
            	
            	if (isCommandSymbol && (concept_attr_names.getLiteral().equals(ATTRIBUTE_ID) || concept_attr_names.getLiteral().equals(ATTRIBUTE_LENGTH))) {
            		continue;
            	}
                PropertyDefinition attributeDefinition = ruleParticipant.getAttributeDefinition(concept_attr_names.getLiteral());
                if (attributeDefinition != null) {
                    allChildrenPropertyDefinitions.add(attributeDefinition);
                }
            }
            superRuleParticipantPath = ruleParticipant.getSuperConceptPath();
        } else if (baseRuleParticipant instanceof SimpleEvent) {
            SimpleEvent ruleParticipant = (SimpleEvent)baseRuleParticipant;
            ruleParticipantExtension = CommonIndexUtils.EVENT_EXTENSION;
            allChildrenPropertyDefinitions = ruleParticipant.getProperties();
            for (EVENT_ATTR_NAMES event_attr_names : EVENT_ATTR_NAMES.values()) {
            	if (isCommandSymbol && (event_attr_names.getLiteral().equals(ATTRIBUTE_ID) || event_attr_names.getLiteral().equals(ATTRIBUTE_LENGTH))) {
            		continue;
            	}
            	PropertyDefinition attributeDefinition = ruleParticipant.getAttributeDefinition(event_attr_names.getLiteral());
            	if (attributeDefinition != null) {
            		allChildrenPropertyDefinitions.add(attributeDefinition);
            	}
            }
            if (!ruleParticipant.getSuperEventPath().equals(SOAP_EVENT)) {
            	superRuleParticipantPath = ruleParticipant.getSuperEventPath();
            }
        } else if (baseRuleParticipant instanceof TimeEvent) {
        	TimeEvent ruleParticipant = (TimeEvent) baseRuleParticipant;
        	allChildrenPropertyDefinitions = ruleParticipant.getProperties();
        	if (allChildrenPropertyDefinitions == null) allChildrenPropertyDefinitions = new ArrayList<PropertyDefinition>();
        	
        	for (TIMEEVENT_ATTR_NAMES event_attr_names : TIMEEVENT_ATTR_NAMES.values()) {
        		if (isCommandSymbol && (event_attr_names.getLiteral().equals(ATTRIBUTE_ID) || event_attr_names.getLiteral().equals(ATTRIBUTE_LENGTH))) {
        			continue;
        		}

        		PropertyDefinition attributeDefinition = ruleParticipant.getAttributeDefinition(event_attr_names.getLiteral());
            	if (attributeDefinition != null) {
            		allChildrenPropertyDefinitions.add(attributeDefinition);
            	}
        	}
        }

        //Resolve super concept path
        while (superRuleParticipantPath != null && !superRuleParticipantPath.isEmpty()) {
        	String entityContents = null;
        	try {
        		entityContents = scsIntegration.showFileContents(repoRootURL,
        				projectName,
        				superRuleParticipantPath,
        				ruleParticipantExtension,
        				null, null);
        	} catch(SCSException scsException) {
        		entityContents = WebstudioFunctionUtils.getEntityContentFromProjectLibrary(repoRootURL, projectName, superRuleParticipantPath, ruleParticipantExtension, earPath);
            	if (entityContents == null || entityContents.isEmpty()) throw new RuntimeException("Artifact [" + ruleParticipantExtension + "] not found even in project libraries");
        	}
            //TODO fetch this from supposed runtime index if present.
            baseRuleParticipant = (E)CommonIndexUtils.deserializeEObjectFromString(entityContents);
            if (baseRuleParticipant instanceof Concept) {
                Concept ruleParticipant = (Concept)baseRuleParticipant;
                allChildrenPropertyDefinitions.addAll(ruleParticipant.getPropertyDefinitions(true));
                superRuleParticipantPath = ruleParticipant.getSuperConceptPath();
                
            } else if (baseRuleParticipant instanceof Event) {
                Event ruleParticipant = (Event)baseRuleParticipant;
                allChildrenPropertyDefinitions.addAll(ruleParticipant.getProperties());
                superRuleParticipantPath = ruleParticipant.getSuperEventPath();
            }
        }
        return allChildrenPropertyDefinitions;
    }

    /**
     * Process entities like rule function.
     * @param projectName
     * @param entityContents
     * @throws Exception
     */
    private void processCompilable(String projectName, String entityContents) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(entityContents.getBytes("UTF-8"));
        RulesASTNode astNode =
                (RulesASTNode) CommonRulesParserManager.parseRuleInputStream(projectName, bis, true);
        RuleCreatorASTVisitor aSTVisitor = new RuleCreatorASTVisitor(true, true, projectName);
        astNode.accept(aSTVisitor);
        //Get compilable object
        RuleFunction ruleFunction = (RuleFunction)aSTVisitor.getRule();
        //Get scope elements
        Symbols symbols = ruleFunction.getSymbols();
        if (symbols != null) {
            List<Symbol> ruleFunctionSymbols = symbols.getSymbolList();
            for (Symbol ruleFunctionSymbol : ruleFunctionSymbols) {
                //Create children
                children.add(SymbolChildNodeFactory.createSymbolChildNode(ruleFunctionSymbol));
            }
        }
    }

    /**
     * Process entities like concept|event|scorecard
     * @param scsIntegration
     * @param repoRootURL
     * @param projectName
     * @param entityContents
     * @param isCommandSymbol
     * @throws Exception
     */
    private void processScopeEntities(ISCSIntegration scsIntegration,
                                      String repoRootURL,
                                      String projectName,
                                      String entityContents,
                                      boolean isCommandSymbol,
                                      String earPath) throws Exception {
        Entity ruleParticipant = (Entity)CommonIndexUtils.deserializeEObjectFromString(entityContents);
        //Get all propertyDefs
        List<PropertyDefinition> allChildrenPropertyDefinitions = getRuleParticpantProperties(scsIntegration, repoRootURL, projectName, ruleParticipant, isCommandSymbol, earPath);
        if (allChildrenPropertyDefinitions != null) {
            for (PropertyDefinition childPropertyDefinition : allChildrenPropertyDefinitions) {
            	AbstractSymbolChildNode childNode = SymbolChildNodeFactory.createSymbolChildNode(childPropertyDefinition);
            	if (childNode != null) {
            		LOGGER.log(Level.DEBUG, "#### Adding Symbol Alias[%s] & Type[%s]", childNode.getAlias(), childNode.getType());
            		children.add(childNode);
            	}
            }
        }
    }

    public List<AbstractSymbolChildNode> getUnvisitedChildren() {
        List<AbstractSymbolChildNode> unvisited = new ArrayList<AbstractSymbolChildNode>();

        if (!children.isEmpty()) {
            for (AbstractSymbolChildNode childNode : children) {
                if (!childNode.isVisited()) {
                    unvisited.add(childNode);
                }
            }
        }
        return (unvisited.isEmpty()) ? Collections.unmodifiableList(children) : Collections.unmodifiableList(unvisited);
    }
}
