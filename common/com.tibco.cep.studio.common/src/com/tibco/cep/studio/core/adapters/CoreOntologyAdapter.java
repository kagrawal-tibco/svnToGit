/**
 * 
 */
package com.tibco.cep.studio.core.adapters;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.util.BidiMap;
import com.tibco.be.util.DualHashBidiMap;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.designtime.model.AbstractOntologyAdapter;
import com.tibco.cep.designtime.model.AddOnRegistry;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.ConceptView;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.designtime.model.registry.ElementTypes;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.RuleSet;
import com.tibco.cep.designtime.model.rule.RuleTemplate;
import com.tibco.cep.designtime.model.service.channel.Channel;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedRuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.utils.CommonRuleCreator;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * This being read-only ontology we don't need to
 * cache all the entities here.
 * We could do it if we had implemented com.tibco.cep.designtime.model.mutable.MutableOntology;
 * <p>
 * This is a query only ontology, hence it will
 * use APIs to query data, and fetch results.
 * </p>
 * @author aathalye
 *
 */
public class CoreOntologyAdapter extends AbstractOntologyAdapter<DesignerProject> implements Ontology {
	
	public static BidiMap<ElementTypes, ELEMENT_TYPES> modelElementTypeMap = new DualHashBidiMap<ElementTypes,ELEMENT_TYPES>();
	static {
        ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
        final EPackage.Registry i = EPackage.Registry.INSTANCE;
        i.put("http:///com/tibco/cep/designtime/core/model/designtime_ontology.ecore", ModelPackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designer/index/core/model/ontology_index.ecore", IndexPackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designtime/core/model/element", ElementPackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designtime/core/model/event", EventPackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designtime/core/model/service/channel", ChannelPackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designtime/core/model/rule", RulePackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designtime/core/model/states", StatesPackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designtime/core/model/archive", ArchivePackage.eINSTANCE);
        i.put("http:///com/tibco/cep/decision/table/model/DecisionTable.ecore", DtmodelPackage.eINSTANCE);
        
        modelElementTypeMap.put(ElementTypes.CONCEPT, ELEMENT_TYPES.CONCEPT);
        modelElementTypeMap.put(ElementTypes.SIMPLEEVENT, ELEMENT_TYPES.SIMPLE_EVENT);
        modelElementTypeMap.put(ElementTypes.TIMEEVENT, ELEMENT_TYPES.TIME_EVENT);
        modelElementTypeMap.put(ElementTypes.CHANNEL, ELEMENT_TYPES.CHANNEL);
        modelElementTypeMap.put(ElementTypes.SCORECARD, ELEMENT_TYPES.SCORECARD);
        modelElementTypeMap.put(ElementTypes.STATEMACHINE, ELEMENT_TYPES.STATE_MACHINE);
        modelElementTypeMap.put(ElementTypes.RULE, ELEMENT_TYPES.RULE);
        modelElementTypeMap.put(ElementTypes.RULEFUNCTION, ELEMENT_TYPES.RULE_FUNCTION);
        modelElementTypeMap.put(ElementTypes.FOLDER, ELEMENT_TYPES.FOLDER);
        modelElementTypeMap.put(ElementTypes.RULESET, ELEMENT_TYPES.RULE_SET);
        modelElementTypeMap.put(ElementTypes.DECISIONTABLE, ELEMENT_TYPES.DECISION_TABLE);
        modelElementTypeMap.put(ElementTypes.DOMAIN, ELEMENT_TYPES.DOMAIN);
        modelElementTypeMap.put(ElementTypes.RULETEMPLATE, ELEMENT_TYPES.RULE_TEMPLATE);
        modelElementTypeMap.put(ElementTypes.VIEW, ELEMENT_TYPES.VIEW);
        modelElementTypeMap.put(ElementTypes.ENTERPRISEARCHIVE, ELEMENT_TYPES.ENTERPRISE_ARCHIVE);
        modelElementTypeMap.put(ElementTypes.METRIC, ELEMENT_TYPES.METRIC);
        modelElementTypeMap.put(ElementTypes.QUERY, ELEMENT_TYPES.QUERY);
    }
	
	

	
	
	/**
	 * @param index
	 */
	public CoreOntologyAdapter(final DesignerProject index) {
		super(index);
		setName(index.getName());
	}
	
	
	/**
	 * @param ontologyName
	 */
	public CoreOntologyAdapter(String ontologyName) {
		super(ontologyName);
	}
	
	


	

	
	

	public Entity getAlias(String alias) {
		// TODO : revisit this.  Very inefficient, do we want to keep a list of aliases as the old ontology does?
		Collection<Entity> entities = getEntities();
		for (Entity entity : entities) {
			String entAlias = entity.getAlias();
			if (entAlias != null && entAlias.equals(alias)) {
				return entity;
			}
		}
		return null;
	}

	
	/**
	 * Return a {@linkplain List} of {@linkplain com.tibco.cep.designtime.core.model.Entity} objects
	 * based on either project name or a {@linkplain DesignerProject}.
	 * @param elementTypes
	 * @return {@linkplain List}
	 */
	private List<com.tibco.cep.designtime.core.model.Entity> getAllEntities(ELEMENT_TYPES[] elementTypes) {
		List<com.tibco.cep.designtime.core.model.Entity> allEntities = 
					new ArrayList<com.tibco.cep.designtime.core.model.Entity>();
		
		if (fOntologyName != null) {
			allEntities = CommonIndexUtils.getAllEntities(fOntologyName, elementTypes);
		} else {
			List<DesignerElement> elements = getAllElements(elementTypes);
			
			for (DesignerElement designerElement : elements) {
				if (designerElement instanceof EntityElement) {
					EntityElement entityElement = (EntityElement)designerElement;
					allEntities.add(entityElement.getEntity());
				}
			}
		}
		
		return allEntities;
	}
	
	/**
	 * Return a {@linkplain List} of {@linkplain DesignerElement} objects
	 * based on either project name or a {@linkplain DesignerProject}.
	 * @param elementTypes
	 * @return {@linkplain List}
	 */
	private List<DesignerElement> getAllElements(ELEMENT_TYPES[] elementTypes) {
		List<DesignerElement> designerElements = 
			  (fOntologyName != null) ?
					  CommonIndexUtils.getAllElements(fOntologyName, elementTypes) :
						  CommonIndexUtils.getAllElements(index, elementTypes);
		return designerElements;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Ontology#getChannels()
	 */
	public Collection<Channel> getChannels() {
		List<com.tibco.cep.designtime.core.model.Entity> allEntities = 
					getAllEntities(new ELEMENT_TYPES[] {ELEMENT_TYPES.CHANNEL});
		Collection<Channel> channels = new LinkedHashSet<Channel>();
		try {
			for (com.tibco.cep.designtime.core.model.Entity entity : allEntities) {
				Channel channel = CoreAdapterFactory.INSTANCE.createAdapter(entity, this);
				channels.add(channel);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return channels;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Ontology#getConcept(java.lang.String)
	 */
	public Concept getConcept(String conceptPath) {
		com.tibco.cep.designtime.core.model.element.Concept concept = 
			CommonIndexUtils.getConcept(fOntologyName, conceptPath);
		try {
			if (concept != null) {
				return CoreAdapterFactory.INSTANCE.createAdapter(concept, this);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}	
		return null;
	}

	public ConceptView getConceptView(String path) {
		throw new UnsupportedOperationException("May not be needed");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Ontology#getConcepts()
	 */
	public Collection<Concept> getConcepts() {
		List<com.tibco.cep.designtime.core.model.Entity> allEntities = 
			getAllEntities(new ELEMENT_TYPES[] { ELEMENT_TYPES.CONCEPT, ELEMENT_TYPES.SCORECARD, ELEMENT_TYPES.METRIC });
		Collection<Concept> concepts = new LinkedHashSet<Concept>();
		try {
			for (com.tibco.cep.designtime.core.model.Entity entity : allEntities) {
				Concept concept = CoreAdapterFactory.INSTANCE.createAdapter(entity, this);
				concepts.add(concept);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return concepts;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Ontology#getEntities()
	 */
	public Collection<Entity> getEntities() {
//		if (allEntities != null) {
//			return allEntities;
//		}
		LinkedHashSet<Entity> allEntities  = new LinkedHashSet<Entity>();
		List<com.tibco.cep.designtime.core.model.Entity> allAdaptedEntities = 
			getAllEntities(ELEMENT_TYPES.values());

		try {
	        for (com.tibco.cep.designtime.core.model.Entity adaptedEntity : allAdaptedEntities) {
	        	Entity entity = CoreAdapterFactory.INSTANCE.createAdapter(adaptedEntity, this);
	        	if (entity != null) {
	        		allEntities.add(entity);
	        	} 
//	        	else {
//	        		System.err.println("Could not adapt " + adaptedEntity);
//	        	}
	        }
	        allEntities.addAll(getRuleFunctions());
	        allEntities.addAll(getRules());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return allEntities;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Ontology#getEntity(java.lang.String)
	 */
	public Entity getEntity(String path) {
		com.tibco.cep.designtime.core.model.Entity entity = CommonIndexUtils.getEntity(fOntologyName, path);
		if (entity == null) {
			//Could be a non-entity
			DesignerElement designerElement = CommonIndexUtils.getElement(fOntologyName, path);
			if (designerElement instanceof RuleElement) {
				RuleElement ruleElement = (RuleElement)designerElement;
				Compilable compilable = convertCompilable(ruleElement);
				entity = compilable;
			}
		}
		try {
			return CoreAdapterFactory.INSTANCE.createAdapter(entity, this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Ontology#getEntity(com.tibco.xml.data.primitive.ExpandedName)
	 */
	public Entity getEntity(ExpandedName name) {
		/**
		 * We have to keep the same namespace here. The runtime uses this namespace
		 */
		String fullPath = name.getNamespaceURI().substring(RDFTnsFlavor.BE_NAMESPACE.length());
		return getEntity(fullPath);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Ontology#getEntity(java.lang.String, java.lang.String)
	 */
	public Entity getEntity(String folder, String name) {
		com.tibco.cep.designtime.core.model.Entity entity = CommonIndexUtils.getEntity(fOntologyName, folder, name);
		try {
			return CoreAdapterFactory.INSTANCE.createAdapter(entity, this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Ontology#getEvent(java.lang.String)
	 */
	public Event getEvent(String eventPath) {
		List<com.tibco.cep.designtime.core.model.Entity> events = 
			getAllEntities(new ELEMENT_TYPES[] {ELEMENT_TYPES.SIMPLE_EVENT, 
					                            ELEMENT_TYPES.TIME_EVENT});
			
		try {
			for (com.tibco.cep.designtime.core.model.Entity adaptedEntity : events) {
				if (adaptedEntity.getFullPath().equals(eventPath)) {
					//If full path is same, it should be the same entity
					return CoreAdapterFactory.INSTANCE.createAdapter(adaptedEntity, this);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}	
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Ontology#getEvents()
	 */
	public Collection<Event> getEvents() {
		List<com.tibco.cep.designtime.core.model.Entity> allEntities = 
			getAllEntities(new ELEMENT_TYPES[] { ELEMENT_TYPES.SIMPLE_EVENT, ELEMENT_TYPES.TIME_EVENT });
		Collection<Event> events = new ArrayList<Event>();
		try {
			for (com.tibco.cep.designtime.core.model.Entity adaptedEntity : allEntities) {
				Event event = CoreAdapterFactory.INSTANCE.createAdapter(adaptedEntity, this);
				events.add(event);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}	
		return events;
	}

	public Folder getFolder(String fullPath) {
		ElementContainer folder = CommonIndexUtils.getFolderForFile(fOntologyName, fullPath);
		if (folder == null) {
			// check whether this folder exists in a project library
			folder = CommonIndexUtils.getFolderForFile(index, fullPath, false, true);
		}
		FolderAdapter adapter = new FolderAdapter(this, folder);
		return adapter;
	}


	public Folder getRootFolder() {
        return this.getFolder(Folder.FOLDER_SEPARATOR_CHAR + "");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Ontology#getRuleFunction(java.lang.String)
	 */
	public RuleFunction getRuleFunction(String path) {
		RuleElement ruleElement = CommonIndexUtils.getRuleElement(fOntologyName, path, ELEMENT_TYPES.RULE_FUNCTION);
		try {
			Compilable compilable = ruleElement.getRule();
			RuleFunction ruleFn = (RuleFunction) CoreAdapterFactory.getCachedAdapter(compilable, ruleElement.getIndexName());
            if (ruleFn == null || isEmptyRule((Compilable)((EntityAdapter)ruleFn).getAdapted())) {
            	// remove the empty cached adapter and cache the one with the action/condition text
            	CoreAdapterFactory.clearCachedAdapter(ruleElement.getIndexName(), compilable);
            	compilable = convertCompilable(ruleElement);
            	ruleFn = CoreAdapterFactory.INSTANCE.createAdapter(compilable, this);
            }
            return ruleFn;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Compilable convertCompilable(RuleElement ruleElement) {
		CommonRuleCreator creator = new CommonRuleCreator(true, true);
		File file = CommonIndexUtils.getJavaFile(ruleElement.getIndexName(), ruleElement);
		Compilable rule = null;
		if (file != null) {
			rule = creator.createRule(file, ruleElement.getIndexName());
//			ruleElement.setRule(rule);
			return rule;
		} else if (ruleElement instanceof SharedRuleElement) {
			rule = parseSharedRuleElement((SharedRuleElement)ruleElement, creator);
			return rule;
		}
		// check if the rule element has the rule
		Compilable compilable = ruleElement.getRule();
		if (compilable != null) {
			String source = null;
			if (compilable instanceof com.tibco.cep.designtime.core.model.rule.Rule) {
				source = ModelUtils.getRuleAsSource((com.tibco.cep.designtime.core.model.rule.Rule) compilable);
			} else if (compilable instanceof com.tibco.cep.designtime.core.model.rule.RuleFunction) {
				source = ModelUtils.getRuleFunctionAsSource((com.tibco.cep.designtime.core.model.rule.RuleFunction) compilable);
			}
			if (source != null && !source.isEmpty()) {
				ByteArrayInputStream bais;
				try {
					bais = new ByteArrayInputStream(source.getBytes(ModelUtils.DEFAULT_ENCODING));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					bais = new ByteArrayInputStream(source.getBytes());
				}
				rule = creator.createRule(bais, ruleElement.getIndexName());
			} else {
                rule = compilable;
            }
		}
		return rule;
	}

	private Compilable parseSharedRuleElement(SharedRuleElement ruleElement,
			CommonRuleCreator creator) {
		String archivePath = ruleElement.getArchivePath();
		String entryPath = ruleElement.getEntryPath()+ruleElement.getFileName();
		InputStream stream = null;
		try {
			JarFile jarFile = new JarFile(archivePath);
			JarEntry entry = (JarEntry) jarFile.getEntry(entryPath);
			stream = jarFile.getInputStream(entry);
			return creator.createRule(stream, ruleElement.getIndexName());
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (stream != null){
					stream.close();
				}
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Ontology#getRuleFunctions()
	 */
	public Collection<RuleFunction> getRuleFunctions() {
		List<DesignerElement> allElements = 
			getAllElements(new ELEMENT_TYPES[] {ELEMENT_TYPES.RULE_FUNCTION});
		Collection<RuleFunction> ruleFunctions = new ArrayList<RuleFunction>();
		try {
			for (DesignerElement designerElement : allElements) {
				RuleElement ruleFnElement = (RuleElement) designerElement;
				Compilable compilable = ruleFnElement.getRule();
				RuleFunction ruleFn = (RuleFunction) CoreAdapterFactory.getCachedAdapter(compilable, ruleFnElement.getIndexName());
                if (ruleFn == null || isEmptyRule((Compilable)((EntityAdapter)ruleFn).getAdapted())) {
                	// remove the empty cached adapter and cache the one with the action/condition text
                	CoreAdapterFactory.clearCachedAdapter(ruleFnElement.getIndexName(), compilable);
                	compilable = convertCompilable(ruleFnElement);
                	ruleFn = (RuleFunction) CoreAdapterFactory.INSTANCE.createAdapter(compilable, this);
                }
				ruleFunctions.add(ruleFn);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return ruleFunctions;
	}

	private boolean isEmptyRule(Compilable ruleFn) {
		if (((ruleFn.getActionText() == null || ruleFn.getActionText().length() == 0)
				&& (ruleFn.getConditionText() == null || ruleFn.getConditionText().length() == 0))
				 || (ruleFn.getFullSourceText() == null || ruleFn.getFullSourceText().length() == 0)) {
			return true;
		}
		return false;
	}

	public RuleSet getRuleSet(String fullPath) {
		ElementContainer folder = CommonIndexUtils.getFolderForFile(fOntologyName, fullPath);
		FolderAdapter adapter = new FolderAdapter(this, folder);
		return adapter;
	}

	public Collection<RuleSet> getRuleSets() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Ontology#getRules()
	 */
	@SuppressWarnings("unchecked")
    public Collection<Rule> getRules() {
		return findRules(getAllElements(new ELEMENT_TYPES[] {ELEMENT_TYPES.RULE}));
	}


    /* (non-Javadoc)
      * @see com.tibco.cep.designtime.model.Ontology#getRules()
      */
    @SuppressWarnings("unchecked")
    public Collection<RuleTemplate> getRuleTemplates() {
        return new ArrayList<RuleTemplate>(
                findRules(getAllElements(new ELEMENT_TYPES[] {ELEMENT_TYPES.RULE_TEMPLATE})));
    }
    
    
    private Collection findRules(
            List<DesignerElement> allElements) {

        Collection<Rule> rules = new ArrayList<Rule>();
        try {
            for (DesignerElement designerElement : allElements) {
                RuleElement ruleElement = (RuleElement) designerElement;
                Compilable compilable = ruleElement.getRule();
                Rule rule = (Rule) CoreAdapterFactory.getCachedAdapter(compilable, ruleElement.getIndexName());
                if (rule == null || isEmptyRule((Compilable)((EntityAdapter)rule).getAdapted())) {
                	// remove the empty cached adapter and cache the one with the action/condition text
                	CoreAdapterFactory.clearCachedAdapter(ruleElement.getIndexName(), compilable);
                	compilable = this.convertCompilable(ruleElement);
                	rule = (Rule) CoreAdapterFactory.INSTANCE.createAdapter(compilable, this);
                }
                if (rule != null) {
                	rules.add(rule);
                }
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return rules;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Ontology#getLastModifiedDate()
	 */
	public Date getLastModifiedDate() {
		return CommonIndexUtils.getLastModifiedDate(fOntologyName);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Ontology#getLastPersistedDate()
	 */
	public Date getLastPersistedDate() {
		return CommonIndexUtils.getLastPersistedDate(fOntologyName);
	}
	
	@Override
	public Collection<Entity> getEntities(ElementTypes[] types) {
		AddOn addon;
		Collection<Entity> entities = new HashSet<Entity>();
		try {
			// make a list of ELEMENT_TYPES supported by this AddOn
			addon = AddOnRegistry.getInstance().getAddOnMap()
					.get(AddOnType.CORE);
			List<ELEMENT_TYPES> etypes = new ArrayList<ELEMENT_TYPES>();
			for (ElementTypes type : addon.getSupportedEntityTypes()
					.getElementType()) {
				if (Arrays.asList(types).contains(type)) {
					ELEMENT_TYPES modelElementType = getModelElementType(type);
					etypes.add(modelElementType);
				}
			}
			// make a list of the selected ELEMENT_TYPES
			List<DesignerElement> allElements = getAllElements(etypes
					.toArray(new ELEMENT_TYPES[etypes.size()]));
			for (DesignerElement designerElement : allElements) {
				com.tibco.cep.designtime.core.model.Entity entity = null;
				String indexName = designerElement.getName();
				ELEMENT_TYPES type = designerElement.getElementType();
				Entity et = null;
				switch (type) {
				case RULE: {
					RuleElement ruleElement = (RuleElement) designerElement;
					entity = ruleElement.getRule();
					indexName = ruleElement.getIndexName();
					et = (Entity) CoreAdapterFactory.getCachedAdapter(
							entity, indexName);
					if (et == null
							|| isEmptyRule((Compilable) ((EntityAdapter) et)
									.getAdapted())) {
						// remove the empty cached adapter and cache the one
						// with the action/condition text
						CoreAdapterFactory.clearCachedAdapter(
								ruleElement.getIndexName(), entity);
						entity = this.convertCompilable(ruleElement);
						et = (Rule) CoreAdapterFactory.INSTANCE.createAdapter(
								entity, this);
					}
					if (et != null) {
						entities.add(et);
					}
				}
					break;
				case RULE_FUNCTION: {
					RuleElement ruleElement = (RuleElement) designerElement;
					entity = ruleElement.getRule();
					indexName = ruleElement.getIndexName();
					et = (Entity) CoreAdapterFactory.getCachedAdapter(
							entity, indexName);
					if (et == null
							|| isEmptyRule((Compilable) ((EntityAdapter) et)
									.getAdapted())) {
						// remove the empty cached adapter and cache the one
						// with the action/condition text
						CoreAdapterFactory.clearCachedAdapter(
								ruleElement.getIndexName(), entity);
						entity = this.convertCompilable(ruleElement);
						et = (Rule) CoreAdapterFactory.INSTANCE.createAdapter(
								entity, this);
					}
					if (et != null) {
						entities.add(et);
					}
				}
				break;
				case CONCEPT:
				case STATE_MACHINE:
				case SCORECARD:
				case SIMPLE_EVENT:
				case TIME_EVENT:
				case CHANNEL:
				case FOLDER:
				case DESTINATION:
				case RULE_TEMPLATE:	{
					entity = ((EntityElement)designerElement).getEntity();
					if (entity != null) {
						et = CoreAdapterFactory.INSTANCE.createAdapter(entity, this);
						if (et != null) {
							entities.add(et);
						}
					}
					
				}
					break;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return entities;
	}


	private ELEMENT_TYPES getModelElementType(ElementTypes type) {
		return modelElementTypeMap.get(type);
	}
	private ElementTypes getModelElementType(ELEMENT_TYPES type) {
		return modelElementTypeMap.inverseMap().get(type);
	}
	
	public Collection<?> getStandaloneStateMachines() {
		// TODO Auto-generated method stub
		//FIXME 3.0.2 merge: Need to implement this
		return null;
	}
}
