package com.tibco.cep.studio.core.clusterconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.ProcessElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;

/*
@author ssailapp
@date Feb 20, 2010 1:16:40 PM
 */

public class ClusterConfigProjectUtils {

	public static int RF_ARGS_TYPE_ALL = 0;	// All
	public static int RF_ARGS_TYPE_STARTUP = 1;	// Has no args
	public static int RF_ARGS_TYPE_PREPROCESSOR = 2;  // Has only one arg, and that arg is a Simple Event
	public static int RF_ARGS_TYPE_SUBSCRIPTION_PREPROCESSOR = 3;  // Has only one arg, and that arg is of a certain type
	public static int RF_ARGS_TYPE_THREAD_AFFINITY_RULEFUNCTION = 4;
	
	public static ArrayList<String> getProjectConceptNames(IProject project) {
		return getProjectEntityElementNames(project, ELEMENT_TYPES.CONCEPT);
	}

	public static ArrayList<String> getProjectDestinationNames(IProject project) {
		ArrayList<String> destinations = new ArrayList<String>();
		Map<String, Destination> destinationElements = CommonIndexUtils.getAllDestinationsURIMaps(project.getName());
		for (String r: destinationElements.keySet()) {
//			r = r.replace(".channel", "");
			destinations.add(r);
		}
		return destinations;
	}
	
	public static ArrayList<String> getProjectEventNames(IProject project) {
		return getProjectEntityElementNames(project, ELEMENT_TYPES.SIMPLE_EVENT);
	}
	
	public static ArrayList<String> getProjectRuleFunctionNames(IProject project, int argsType) {
		return getProjectRuleFunctionNames(project, argsType, null);
	}
	
	public static ArrayList<String> getProjectRuleFunctionNames(IProject project, int argsType, EntityElement domainObjectEntity) {
		ArrayList<RuleElement> allRuleFuncs = getProjectRuleElements(project, ELEMENT_TYPES.RULE_FUNCTION);
		if (argsType == RF_ARGS_TYPE_ALL) {
			return getProjectRuleElementNames(allRuleFuncs);
		} 
		
		ArrayList<RuleElement> selRuleFuncs = new ArrayList<RuleElement>();
		for (RuleElement ruleFunc: allRuleFuncs) {
			RuleFunction rf = (RuleFunction) ruleFunc.getRule();
			Symbols symbols = rf.getSymbols();
			
			if (argsType == RF_ARGS_TYPE_STARTUP) {
				if (symbols.getSymbolMap().size() == 0) {
					selRuleFuncs.add(ruleFunc);
				}
			} else if (argsType == RF_ARGS_TYPE_PREPROCESSOR) {
				if (symbols.getSymbolMap().size() == 1) {
					Symbol symbol = symbols.getSymbolList().get(0);
					DesignerElement element = IndexUtils.getElement(project.getName(), symbol.getType());
					if (element != null) {
						if (element.getElementType() == ELEMENT_TYPES.SIMPLE_EVENT) {
							selRuleFuncs.add(ruleFunc);							
						}
					} else {	// Check if this symbol is an "Event"
						if (symbol.getType().equalsIgnoreCase("Event") || symbol.getType().equalsIgnoreCase("SimpleEvent"))
							selRuleFuncs.add(ruleFunc);
					}
				}
			} else if (argsType == RF_ARGS_TYPE_SUBSCRIPTION_PREPROCESSOR) {
				//setRuleFuncsMatchEntity(project, selRuleFuncs, symbols, ruleFunc, domainObjectEntity);
				if (symbols.getSymbolMap().size() == 5) {
					boolean match = true;
					String subsPreprocSignature[] = new String[] {"long", "String", "int", "int", "boolean"};
					for (int i=0; i<subsPreprocSignature.length; i++) {
						Symbol symbol = symbols.getSymbolList().get(i);
						if (!symbol.getType().equals(subsPreprocSignature[i])) {
							match = false;
							break;
						}
					}
					if(match) {
						String returnType = rf.getReturnType();
						if (returnType != null && rf.getReturnType().equals("boolean")) selRuleFuncs.add(ruleFunc);
					}
				}
			} else if (argsType == RF_ARGS_TYPE_THREAD_AFFINITY_RULEFUNCTION) {
				if (symbols.getSymbolMap().size() == 1) {
					boolean isMatch = false;
					Symbol symbol = symbols.getSymbolList().get(0);
					DesignerElement symbolElement = null;
					if (symbol != null) {
						symbolElement = IndexUtils.getElement(project.getName(), symbol.getType());
						if (symbolElement != null) {
							if (symbolElement.getElementType() == ELEMENT_TYPES.SIMPLE_EVENT)
								isMatch = true;
						} else {
							if (symbol.getType().equalsIgnoreCase("Event") || symbol.getType().equalsIgnoreCase("SimpleEvent"))
								isMatch = true;
						}
					}
					
					isMatch = isMatch && rf.getReturnType() != null; //match if non-void return types
						
					if (isMatch) {
						if (domainObjectEntity != null) {//check for default event & its sub types
							if (domainObjectEntity.getEntity() instanceof Event) {
								//Default event on destination
								Event defaultEvent = (Event) domainObjectEntity.getEntity();
								if (symbolElement instanceof EntityElement) {
									Entity symbolElementEntity = ((EntityElement) symbolElement).getEntity();
									if (symbolElementEntity instanceof Event) {
										//RF Argument event
										Event symbolEvent = (Event) symbolElementEntity;
										//If argument Event is of type/sub-type of default event, add RF to list
//										if (symbolEvent.isA(defaultEvent)) {
										if (defaultEvent.isA(symbolEvent)) {
											selRuleFuncs.add(ruleFunc);
										}
									}	
								}
							}	
						} else {//no default event, so add the matched RF to list
							selRuleFuncs.add(ruleFunc);
						}
					}
				}
			}
		}
		return getProjectRuleElementNames(selRuleFuncs);
	}
	
	private static void setRuleFuncsMatchEntity(IProject project, ArrayList<RuleElement> selRuleFuncs, Symbols symbols, RuleElement ruleFunc, EntityElement ee) {
		if (symbols.getSymbolMap().size() == 1) {
			Symbol symbol = symbols.getSymbolList().get(0);
			DesignerElement element = IndexUtils.getElement(project.getName(), symbol.getType());
			if (element != null) {
				if (element.getElementType() == ee.getElementType()) {
					if (ee.getEntity() instanceof Concept) {
						if (element instanceof EntityElement) {
							Concept concept = (Concept) ee.getEntity();
							if (((EntityElement)element).getEntity() instanceof Concept) {
								if (concept.isA((Concept) ((EntityElement)element).getEntity())) {
									selRuleFuncs.add(ruleFunc);
								} else if ( (ee.getElementType()==ELEMENT_TYPES.CONCEPT && symbol.getType().equalsIgnoreCase("Concept")) ||
										(ee.getElementType()== ELEMENT_TYPES.SCORECARD && symbol.getType().equalsIgnoreCase("Scorecard")) ) {
									selRuleFuncs.add(ruleFunc);
								}
							}
						}
					} else if (ee.getEntity() instanceof Event) {
						if (element instanceof EntityElement) {
							Event event = (Event) ee.getEntity();
							if (((EntityElement)element).getEntity() instanceof Event) {
								if (event.isA((Event) ((EntityElement)element).getEntity())) {
									selRuleFuncs.add(ruleFunc);
								} else if (symbol.getType().equalsIgnoreCase("Event") || symbol.getType().equalsIgnoreCase("SimpleEvent")) {
									selRuleFuncs.add(ruleFunc);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static ArrayList<String> getProjectRuleNames(IProject project) {
		return getProjectRuleElementNames(project, ELEMENT_TYPES.RULE);
	}
	
	public static ArrayList<String> getProjectRuleTemplateNames(IProject project) {
		return getProjectRuleElementNames(project, ELEMENT_TYPES.RULE_TEMPLATE);
	}
	
	public static boolean isProjectRuleTemplate(IProject project, String ruleTemplate){
		ArrayList<String> ruleTemplates =  getProjectRuleElementNames(project, ELEMENT_TYPES.RULE_TEMPLATE);
		if (ruleTemplates==null){
			return false;
		}
		else{
			return ruleTemplates.contains(ruleTemplate);
		}
	}
	
	public static ArrayList<String> getProjectProcessNames(IProject project) {
		ArrayList<String> processesList = CommonUtil.getResourcesByExtension(project, "beprocess");
		ArrayList<String> processesUriList = new ArrayList<String>();
		for (String process: processesList) {
			processesUriList.add("/" + process.replaceAll(".beprocess", ""));
		}
		return processesUriList;
	}
	
	private static ArrayList<EntityElement> getProjectEntityElements(IProject project, ELEMENT_TYPES types[]) {
		ArrayList<EntityElement> entities = new ArrayList<EntityElement>();
		List<DesignerElement> entityElements = IndexUtils.getAllElements(project.getName(), types);
		for (DesignerElement e: entityElements) {
			if (! (e instanceof EntityElement)) {
				continue;
			}
			entities.add((EntityElement)e);
		}
		return entities;
	}
	
	private static ArrayList<String> getProjectEntityElementNames(IProject project, ELEMENT_TYPES type) {
		ArrayList<String> entityNames = new ArrayList<String>();
		ArrayList<EntityElement> entities = getProjectEntityElements(project, new ELEMENT_TYPES[] {type});
		for (EntityElement entity: entities) {
			String path = entity.getFolder() + entity.getName();
			entityNames.add(path);
		}
		return entityNames;
	}
	
	private static ArrayList<RuleElement> getProjectRuleElements(IProject project, ELEMENT_TYPES type) {
		ArrayList<RuleElement> rules = new ArrayList<RuleElement>();
		if(project != null){
			List<DesignerElement> ruleElements = IndexUtils.getAllElements(project.getName(), type);
			for (DesignerElement r: ruleElements) {
				if (!(r instanceof RuleElement)) {
					continue;
				}
				rules.add((RuleElement)r);
			}
		}
		return rules;
	}
	
	private static ArrayList<String> getProjectRuleElementNames(IProject project, ELEMENT_TYPES type) {
		return getProjectRuleElementNames(getProjectRuleElements(project, type));
	}
	
	private static ArrayList<String> getProjectRuleElementNames(ArrayList<RuleElement> rules) {
		ArrayList<String> ruleElementNames = new ArrayList<String>();
		for (RuleElement rule: rules) {
			String path = rule.getFolder() + rule.getName();
			if(path.contains("/")){ // check to add rule validation. "/" means rule is validated and with no errors.
				ruleElementNames.add(path);
			}
		}
		return ruleElementNames;
	}

	public static ArrayList<EntityElement> getProjectDomainObjectOverrideEntities(IProject project) {
		ArrayList<ELEMENT_TYPES> elementTypes = new ArrayList<ELEMENT_TYPES>();
		elementTypes.add(ELEMENT_TYPES.CONCEPT);
		elementTypes.add(ELEMENT_TYPES.SCORECARD);
		elementTypes.add(ELEMENT_TYPES.SIMPLE_EVENT);
		elementTypes.add(ELEMENT_TYPES.METRIC);
		elementTypes.add(ELEMENT_TYPES.PROCESS);
		return getProjectEntityElements(project, elementTypes.toArray(new ELEMENT_TYPES[0]));
	}
	
	public static ArrayList<EntityElement> getProjectConceptEventMetricName(IProject project) {
		ArrayList<ELEMENT_TYPES> elementTypes = new ArrayList<ELEMENT_TYPES>();
		elementTypes.add(ELEMENT_TYPES.CONCEPT);
		elementTypes.add(ELEMENT_TYPES.SIMPLE_EVENT);
		elementTypes.add(ELEMENT_TYPES.METRIC);
		
		return getProjectEntityElements(project, elementTypes.toArray(new ELEMENT_TYPES[0]));
	}

	public static ArrayList<String> getEntityElementProperties(EntityElement ee) {
		ArrayList<String> props = new ArrayList<String>();
		if(ee instanceof ProcessElement || ee.getElementType() == ELEMENT_TYPES.PROCESS) {
			// TODO:Need to add process vars to domain properties
			EClass eClassType = ee.eClass();
			EStructuralFeature sf = eClassType.getEStructuralFeature("process");
			EObject entity = (EObject) ee.eGet(sf);
			if (entity != null) {
				sf = entity.eClass().getEStructuralFeature("properties");
				EList<EObject> properties = (EList<EObject>) entity.eGet(sf);
				for (EObject prop : properties) {

					EStructuralFeature nm = prop.eClass().getEStructuralFeature("name");
					String pname = (String) prop.eGet(nm);
					props.add(pname);
				}
			}
			
		} else if (ee.getEntity() instanceof Concept) {
			Concept concept = (Concept) ee.getEntity();
			for (PropertyDefinition property: concept.getProperties()) {
				props.add(property.getName());
			}
		} else if (ee.getEntity() instanceof Event) {
			Event event = (Event) ee.getEntity();
			for (PropertyDefinition property: event.getProperties()) {
				props.add(property.getName());
			}
		}
		return props;
	}
	
	public static EntityElement getEntityElementForPath(IProject project, String path) {
		if (project == null || path == null)
			return null;
		ArrayList<EntityElement> entities = getProjectDomainObjectOverrideEntities(project);
		for (EntityElement entity: entities) {
			if (path.equalsIgnoreCase(entity.getFolder() + entity.getName()))
				return entity;
		}
		return null;
	}

	public static boolean isDbConcept(EntityElement entityElement) {
		if (entityElement.getEntity() instanceof Concept) {
			return (isDbConcept((Concept)entityElement.getEntity()));
		}
		return false; 
	}
	
	public static boolean isDbConcept(Concept concept) {
		PropertyMap propMap = concept.getExtendedProperties();
		if (propMap == null) {
			return false;
		}
		ArrayList<String> propList = new ArrayList<String>();
		for (Entity e: propMap.getProperties()) {
			propList.add(e.getName());
		}
		if (propList.contains("SCHEMA_NAME") &&
				propList.contains("OBJECT_NAME")&&
				propList.contains("OBJECT_TYPE"))
			return true;
		return false; 
	}

	public static Map<String,Destination> getJMSDestinationsMap(IProject project){
		Map<String,Destination> jmsDestinationMap = new HashMap<String, Destination>();
		List<Entity> channels = CommonIndexUtils.getAllEntities(project.getName() , ELEMENT_TYPES.CHANNEL);
		for(Entity entity:channels){
			Channel channel = (Channel)entity;
			if(DriverManagerConstants.DRIVER_JMS.equals(channel.getDriver().getDriverType().getName())){
				for(Destination dest:channel.getDriver().getDestinations()){
					StringBuilder sBuilder = new StringBuilder();
					String path = sBuilder.append(channel.getFullPath())
					.append("/")
					.append(dest.getName())
					.toString();
					if(!jmsDestinationMap.containsKey(path)){
						jmsDestinationMap.put(path, dest);
					}
				}
			}
		}
		return jmsDestinationMap;
	}

	public static Map<String,Destination> getLocalDestinationsMap(IProject project){
		Map<String,Destination> localDestinationMap = new HashMap<String, Destination>();
		List<Entity> channels = CommonIndexUtils.getAllEntities(project.getName() , ELEMENT_TYPES.CHANNEL);
		for(Entity entity:channels){
			Channel channel = (Channel)entity;
			if(DriverManagerConstants.DRIVER_LOCAL.equals(channel.getDriver().getDriverType().getName())){
				for(Destination dest:channel.getDriver().getDestinations()){
					StringBuilder sBuilder = new StringBuilder();
					String path = sBuilder.append(channel.getFullPath())
					.append("/")
					.append(dest.getName())
					.toString();
					if(!localDestinationMap.containsKey(path)){
						localDestinationMap.put(path, dest);
					}
				}
			}
		}
		return localDestinationMap;
	}
	
}
