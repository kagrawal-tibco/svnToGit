/**
 *
 */
package com.tibco.cep.studio.core.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE;
import com.tibco.cep.designtime.core.model.METRIC_TYPE;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.impl.MetricImpl;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.Instance;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmSupport;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.impl.DefaultComponentFactory;
import com.tibco.xml.schema.impl.DefaultModelGroup;
import com.tibco.xml.schema.impl.DefaultParticle;
import com.tibco.xml.schema.impl.DefaultSchema;
import com.tibco.xml.schema.impl.DefaultType;
import com.tibco.xml.tns.parse.TnsFlavor;

/**
 * As far as possible do not cache anything in adapter classes.
 * <p>
 * Delegate things to the adapted object. The adapters will not be used later
 * hence keeping too many member variables here will not be a good idea.
 * </p>
 * @author aathalye
 *
 */

public class ConceptAdapter extends EntityAdapter<com.tibco.cep.designtime.core.model.element.Concept> implements Concept, ICacheableAdapter {

    public static final String METRIC_DVM_PARENT_ID_NAME = "pid$";

	private boolean _enableMetricTracking = false;

	//Map<String,HashSet<String>> m_referringPropsMap;

	public ConceptAdapter(com.tibco.cep.designtime.core.model.element.Concept adapted,
			              Ontology emfOntology) {
		super(adapted, emfOntology);
	}

	protected com.tibco.cep.designtime.core.model.element.Concept getAdapted() {
		return adapted;
	}

	public boolean canInheritFrom(Concept concept) {
		return false;

	}

    public void enableMetricTracking() {
        _enableMetricTracking = true;
    }

    public void disableMetricTracking() {
        _enableMetricTracking = false;
    }

    public boolean isMetricTrackingEnabled() {
        return _enableMetricTracking;
    }

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.Concept#contains(com.tibco.cep.designtime.model.element.Concept)
	 */
	public boolean contains(Concept concept) {
		if (concept == null) {
			//Null concept cannot be contained
			return false;
		}
		//Run loop over local props
		List<PropertyDefinition> localPops = getLocalPropertyDefinitions();
		for (PropertyDefinition p : localPops) {
			//A contained concept will be stored with RDF type "Concept"
			int propType = p.getType();
			if (!PROPERTY_TYPES.get(propType).equals(PROPERTY_TYPES.CONCEPT)) {
				continue;
			}

			//Look for conceptType attribute in the old format or conceptTypePath in the new one
			Concept other = p.getConceptType();
			if (concept.equals(other)) {
				return true;
			} else if (other != null && concept.getFullPath().equals(other.getFullPath())) {
				// equality check might fail if concepts were loaded in different ways.
				// In other words, other == concept would fail, but the concepts are actually equal
				return true;
			}
			//If this contained concept contains the requested concept, then that is also treated as contained
			if (other != null) {
				return other.contains(concept);
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.Concept#getAllInstancePaths()
	 */
	public Collection<String> getAllInstancePaths() {
		throw new UnsupportedOperationException("May not be needed");
	}

	public List<PropertyDefinition> getAllPropertyDefinitions() {
//		List<com.tibco.cep.designtime.core.model.element.PropertyDefinition>
//			adaptedProperties = new ArrayList<com.tibco.cep.designtime.core.model.element.PropertyDefinition>();
//		_getPropertyDefinitions(adapted,adaptedProperties);
		EList<com.tibco.cep.designtime.core.model.element.PropertyDefinition> adaptedProperties = adapted.getAllPropertyDefinitions();
		List<PropertyDefinition> pDefs = new ArrayList<PropertyDefinition>(adaptedProperties.size());

		for (com.tibco.cep.designtime.core.model.element.PropertyDefinition p : adaptedProperties) {
			try {
				PropertyDefinitionAdapter propertyDefinitionAdapter =
					CoreAdapterFactory.INSTANCE.createAdapter(p, emfOntology);
				pDefs.add(propertyDefinitionAdapter);
				if (isMetric() && _enableMetricTracking && !p.isGroupByField() &&
					p.getAggregationType().getValue() != METRIC_AGGR_TYPE.SET_VALUE &&
					p.getAggregationType().getValue() != METRIC_AGGR_TYPE.AVERAGE_VALUE &&
					p.getAggregationType().getValue() != METRIC_AGGR_TYPE.VARIANCE_VALUE &&
					p.getAggregationType().getValue() != METRIC_AGGR_TYPE.STANDARD_DEVIATION_VALUE) {
					pDefs.add(new PropertyDefinitionAdapter(p, emfOntology, Boolean.TRUE));
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
        // Metric does not support inheritance
        if (isMetric() && _enableMetricTracking) {
			EList<com.tibco.cep.designtime.core.model.element.PropertyDefinition> adaptedUDefProperties = ((MetricImpl) adapted).getUserDefinedFields();

			for (com.tibco.cep.designtime.core.model.element.PropertyDefinition p : adaptedUDefProperties) {
				try {
					PropertyDefinitionAdapter UDefPropertyDefinitionAdapter =
						CoreAdapterFactory.INSTANCE.createAdapter(p, emfOntology);
					pDefs.add(UDefPropertyDefinitionAdapter);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
        }
        if (isMetric() && _enableMetricTracking) {
			com.tibco.cep.designtime.core.model.element.PropertyDefinition pd = ElementFactory.eINSTANCE.
				createPropertyDefinition();
			pd.setName(METRIC_DVM_PARENT_ID_NAME);
			pd.setType(PROPERTY_TYPES.LONG);
			pDefs.add(new PropertyDefinitionAdapter(pd, emfOntology));
        }
		return pDefs;
	}

	public List<PropertyDefinition> _getAllPropertyDefinitions() {
//		List<com.tibco.cep.designtime.core.model.element.PropertyDefinition>
//			adaptedProperties = new ArrayList<com.tibco.cep.designtime.core.model.element.PropertyDefinition>();
//		EList<com.tibco.cep.designtime.core.model.element.PropertyDefinition> localPropertyDefinitions = adapted.getProperties();
//		java.util.Iterator<com.tibco.cep.designtime.core.model.element.PropertyDefinition> lIterator = localPropertyDefinitions.iterator();
//		while(lIterator.hasNext()) {
//			adaptedProperties.add(lIterator.next());
//		}
//		com.tibco.cep.designtime.core.model.element.Concept ancestor = adapted.getSuperConcept();
//		while(ancestor != null) {
//			EList<com.tibco.cep.designtime.core.model.element.PropertyDefinition> inheritedPDs = ancestor.getProperties();
//			java.util.Iterator<com.tibco.cep.designtime.core.model.element.PropertyDefinition> inhIter = inheritedPDs.iterator();
//			while(inhIter.hasNext()) {
//				adaptedProperties.add(inhIter.next());
//			}
//			ancestor = ancestor.getSuperConcept();
//		}
		List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> adaptedProperties = adapted._getAllPropertyDefinitions();
		List<PropertyDefinition> pDefs = new ArrayList<PropertyDefinition>(adaptedProperties.size());

		for (com.tibco.cep.designtime.core.model.element.PropertyDefinition p : adaptedProperties) {
			try {
				PropertyDefinitionAdapter propertyDefinitionAdapter =
					CoreAdapterFactory.INSTANCE.createAdapter(p, emfOntology);
				pDefs.add(propertyDefinitionAdapter);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return pDefs;
	}

//	public void  _getPropertyDefinitions(com.tibco.cep.designtime.core.model.element.Concept cept, List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> props) {
//		if(cept.getSuperConcept() != null) {
//			_getPropertyDefinitions(cept.getSuperConcept(),props);
//		}
//		java.util.Iterator<com.tibco.cep.designtime.core.model.element.PropertyDefinition> localProperties = cept.getProperties().iterator();
//		while(localProperties.hasNext()) {
//			props.add(localProperties.next());
//		}
//
//	}

	public PropertyDefinition getAttributeDefinition(String attributeName) {
		try {
			PropertyDefinitionAdapter propertyDefinitionAdapter =
				CoreAdapterFactory.INSTANCE.createAdapter(adapted.getAttributeDefinition(attributeName), emfOntology);
			return propertyDefinitionAdapter;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<PropertyDefinition> getAttributeDefinitions() {
		//The implementation is same as default one

		List<PropertyDefinition> allAttributes = new ArrayList<PropertyDefinition>();
		//If no super concept, only add id, and extid. We do not want duplicates here
		//though we could have used a Set here to avoid it
		if (getSuperConcept() == null) {
			populateBaseAttributes(allAttributes);
		}
		//Only attributes are id, and extid for concept not contained
		else {
			allAttributes.addAll(getSuperConcept().getAttributeDefinitions());
		}
		if (isContained()) {
			allAttributes.add(getParentAttribute());
		}
		return allAttributes;
	}

	protected void populateBaseAttributes(List<PropertyDefinition> attributes) {
		com.tibco.cep.designtime.core.model.element.PropertyDefinition idP =
			adapted.getAttributeDefinition(BASE_ATTRIBUTE_NAMES[0]);
		com.tibco.cep.designtime.core.model.element.PropertyDefinition extIdP =
			adapted.getAttributeDefinition(BASE_ATTRIBUTE_NAMES[1]);
		attributes.add(new PropertyDefinitionAdapter(idP, emfOntology));
		attributes.add(new PropertyDefinitionAdapter(extIdP, emfOntology));
	}

	protected PropertyDefinition getParentAttribute() {
		com.tibco.cep.designtime.core.model.element.PropertyDefinition parentP =
			adapted.getAttributeDefinition("parent");
		return new PropertyDefinitionAdapter(parentP, emfOntology);
	}

	public Set<Instance> getInstances() {
		throw new UnsupportedOperationException("To be Done");
	}

	public Collection<String> getLocalInstancesPaths() {
		throw new UnsupportedOperationException("To be Done");
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.Concept#getLocalPropertyDefinitions()
	 */
	public List<PropertyDefinition> getLocalPropertyDefinitions() {
		List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> adaptedProperties =
			new ArrayList<com.tibco.cep.designtime.core.model.element.PropertyDefinition>(adapted.getProperties());
//		Collections.sort(adaptedProperties, new Comparator<com.tibco.cep.designtime.core.model.element.PropertyDefinition>() {
//            public int compare(com.tibco.cep.designtime.core.model.element.PropertyDefinition o1, com.tibco.cep.designtime.core.model.element.PropertyDefinition o2) {
//            	com.tibco.cep.designtime.core.model.element.PropertyDefinition dpd1 =  o1;
//            	com.tibco.cep.designtime.core.model.element.PropertyDefinition dpd2 = o2;
//                return dpd1.getOrder() - dpd2.getOrder();
//            }
//        });
		List<PropertyDefinition> pDefs = new ArrayList<PropertyDefinition>(adaptedProperties.size());
		for (com.tibco.cep.designtime.core.model.element.PropertyDefinition p : adaptedProperties) {
			pDefs.add(new PropertyDefinitionAdapter(p, emfOntology));
			if (isMetric() && _enableMetricTracking && !p.isGroupByField() &&
				p.getAggregationType().getValue() != METRIC_AGGR_TYPE.SET_VALUE &&
				p.getAggregationType().getValue() != METRIC_AGGR_TYPE.AVERAGE_VALUE &&
				p.getAggregationType().getValue() != METRIC_AGGR_TYPE.VARIANCE_VALUE &&
				p.getAggregationType().getValue() != METRIC_AGGR_TYPE.STANDARD_DEVIATION_VALUE) {
				pDefs.add(new PropertyDefinitionAdapter(p, emfOntology, Boolean.TRUE));
			}
		}
        if (isMetric() && _enableMetricTracking) {
			EList<com.tibco.cep.designtime.core.model.element.PropertyDefinition> adaptedUDefProperties = ((MetricImpl) adapted).getUserDefinedFields();

			for (com.tibco.cep.designtime.core.model.element.PropertyDefinition p : adaptedUDefProperties) {
				try {
					PropertyDefinitionAdapter UDefPropertyDefinitionAdapter =
						CoreAdapterFactory.INSTANCE.createAdapter(p, emfOntology);
					pDefs.add(UDefPropertyDefinitionAdapter);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
        }
        if (isMetric() && _enableMetricTracking) {
			com.tibco.cep.designtime.core.model.element.PropertyDefinition pd = ElementFactory.eINSTANCE.
				createPropertyDefinition();
			pd.setName(METRIC_DVM_PARENT_ID_NAME);
			pd.setType(PROPERTY_TYPES.LONG);
			pDefs.add(new PropertyDefinitionAdapter(pd, emfOntology));
        }
		return pDefs;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.Concept#getMainStateMachine()
	 */
	public StateMachine getMainStateMachine() {
		List<com.tibco.cep.designtime.core.model.states.StateMachine> stateMachines = adapted.getStateMachines();
		for (com.tibco.cep.designtime.core.model.states.StateMachine stateMachine : stateMachines) {
			if (stateMachine.isMain()) {
				try {
					return CoreAdapterFactory.INSTANCE.createAdapter(stateMachine, emfOntology);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		// Not found => looks in the parent.
        final Concept parent = this.getSuperConcept();
        if (null != parent) { // Only works if the parent is already loaded.
            return parent.getMainStateMachine();
        }
		return null;
	}

	public String getPOJOImplClassName() {
		throw new UnsupportedOperationException("To be Done");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.element.Concept#getParentConcept()
	 */
	public Concept getParentConcept() {
		PropertyDefinition pd = getParentPropertyDefinition();
		if(pd != null) {
			return pd.getOwner();
		}
//		com.tibco.cep.designtime.core.model.element.Concept adaptedParent = adapted.getParentConcept();
//		if (adaptedParent != null) {
//			return new ConceptAdapter(adaptedParent, emfOntology);
//		}
		return null;
	}

//	public PropertyDefinition getParentPropertyDefinition() {
//		throw new UnsupportedOperationException("To be Done");
//	}

	@SuppressWarnings("rawtypes")
	public PropertyDefinition getParentPropertyDefinition() {
		Map<String,HashSet<String>> map = getReferringPropsMap();
		Set concepts = map.keySet();
		Iterator conceptIt = concepts.iterator();
		while (conceptIt.hasNext()) {
			String path = (String) conceptIt.next();
			com.tibco.cep.designtime.core.model.element.Concept c = (com.tibco.cep.designtime.core.model.element.Concept) CommonIndexUtils
					.getEntity(getOntology().getName(), path);
			if (c == null) {
				System.err.println("Referring Concept missing: " + path);
				continue;
			}

			HashSet<String> propNames = map.get(path);
			Iterator propNamesIt = propNames.iterator();
			while (propNamesIt.hasNext()) {
				String propName = (String) propNamesIt.next();
				com.tibco.cep.designtime.core.model.element.PropertyDefinition pd = c
						.getPropertyDefinition(propName, true);
				if (pd == null) {
					System.err.println("Referring Prop missing: " + path + ":"
							+ propName);
					continue;
				}

				if (pd.getType() == PROPERTY_TYPES.CONCEPT) {
					if (pd.getConceptTypePath().equals(getFullPath())) {
						return new PropertyDefinitionAdapter(pd,getOntology());
					}
				}
			}
		}

		return null;
	}

	private Map<String, HashSet<String>> getReferringPropsMap() {
		Map<String, HashSet<String>> m_referringPropsMap = new LinkedHashMap<String,HashSet<String>>();
		List<Entity> concepts = CommonIndexUtils.getAllEntities(emfOntology.getName(), ELEMENT_TYPES.CONCEPT);
		for(Entity e:concepts) {
			com.tibco.cep.designtime.core.model.element.Concept c = (com.tibco.cep.designtime.core.model.element.Concept) e;
			EList<com.tibco.cep.designtime.core.model.element.PropertyDefinition> pdefs = c.getProperties();
			for(com.tibco.cep.designtime.core.model.element.PropertyDefinition p:pdefs) {
				if(p.getType() == PROPERTY_TYPES.CONCEPT ||
						p.getType() == PROPERTY_TYPES.CONCEPT_REFERENCE) {
					if(p.getConceptTypePath().equals(adapted.getFullPath())) {
						HashSet<String> propSet = m_referringPropsMap.get(c.getFullPath());
						if(propSet == null){
							propSet = new LinkedHashSet<String>();
							m_referringPropsMap.put(c.getFullPath(),propSet);
						}
						propSet.add(p.getName());
					}
				}
			}
		}
		return m_referringPropsMap;
	}

	public PropertyDefinition getPropertyDefinition(String name,
			boolean localOnly) {
		com.tibco.cep.designtime.core.model.element.PropertyDefinition adaptedProperty =
			adapted.getPropertyDefinition(name, localOnly);
		if (adaptedProperty == null) {
			if (isMetric() && _enableMetricTracking) {
				//all regular metric properties are searched via adapted.getPropertyDefinition() call
				EList<com.tibco.cep.designtime.core.model.element.PropertyDefinition> adaptedUDefProperties = ((MetricImpl) adapted).getUserDefinedFields();
				for (com.tibco.cep.designtime.core.model.element.PropertyDefinition p : adaptedUDefProperties) {
					if (p.getName().equals(name) == true) {
						return CoreAdapterFactory.INSTANCE.createAdapter(p, emfOntology);
					}
				}
				if (METRIC_DVM_PARENT_ID_NAME.equals(name) == true) {
					com.tibco.cep.designtime.core.model.element.PropertyDefinition pd = ElementFactory.eINSTANCE.
							createPropertyDefinition();
						pd.setName(METRIC_DVM_PARENT_ID_NAME);
						pd.setType(PROPERTY_TYPES.LONG);
						return new PropertyDefinitionAdapter(pd, emfOntology);
				}
	        }
			return null;
		}
		return new PropertyDefinitionAdapter(adaptedProperty, emfOntology);
	}

	public List<PropertyDefinition> getPropertyDefinitions(boolean localOnly) {
		List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> adaptedProperties =
				new ArrayList<com.tibco.cep.designtime.core.model.element.PropertyDefinition>(adapted.getPropertyDefinitions(localOnly));
			List<PropertyDefinition> pDefs = new ArrayList<PropertyDefinition>(adaptedProperties.size());
			for (com.tibco.cep.designtime.core.model.element.PropertyDefinition p : adaptedProperties) {
				pDefs.add(new PropertyDefinitionAdapter(p, emfOntology));
				if (isMetric() && _enableMetricTracking && !p.isGroupByField() &&
					p.getAggregationType().getValue() != METRIC_AGGR_TYPE.SET_VALUE &&
					p.getAggregationType().getValue() != METRIC_AGGR_TYPE.AVERAGE_VALUE &&
					p.getAggregationType().getValue() != METRIC_AGGR_TYPE.VARIANCE_VALUE &&
					p.getAggregationType().getValue() != METRIC_AGGR_TYPE.STANDARD_DEVIATION_VALUE) {
					pDefs.add(new PropertyDefinitionAdapter(p, emfOntology, Boolean.TRUE));
				}
			}
	        if (isMetric() && _enableMetricTracking) {
				EList<com.tibco.cep.designtime.core.model.element.PropertyDefinition> adaptedUDefProperties = ((MetricImpl) adapted).getUserDefinedFields();

				for (com.tibco.cep.designtime.core.model.element.PropertyDefinition p : adaptedUDefProperties) {
					try {
						PropertyDefinitionAdapter UDefPropertyDefinitionAdapter =
							CoreAdapterFactory.INSTANCE.createAdapter(p, emfOntology);
						pDefs.add(UDefPropertyDefinitionAdapter);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
	        }
	        if (isMetric() && _enableMetricTracking) {
				com.tibco.cep.designtime.core.model.element.PropertyDefinition pd = ElementFactory.eINSTANCE.
					createPropertyDefinition();
				pd.setName(METRIC_DVM_PARENT_ID_NAME);
				pd.setType(PROPERTY_TYPES.LONG);
				pDefs.add(new PropertyDefinitionAdapter(pd, emfOntology));
	        }
			return pDefs;
	}

	public Collection<String> getReferencedConceptPaths() {
		//Is a Doubly-linked list needed here?
		Collection<String> referencedConceptPaths = new LinkedList<String>();

		//Get local properties
		List<PropertyDefinition> properties = getLocalPropertyDefinitions();
		for (PropertyDefinition pDef : properties) {
			int type = pDef.getType();
			PROPERTY_TYPES property_types = PROPERTY_TYPES.get(type);
			if (PROPERTY_TYPES.CONCEPT_REFERENCE.equals(property_types)
					|| PROPERTY_TYPES.CONCEPT.equals(property_types)) {
				//get the path of the concept referenced using this property
				String conceptPath = pDef.getConceptTypePath();
				referencedConceptPaths.add(conceptPath);
			}
		}
		return referencedConceptPaths;
	}

	public Collection<String> getReferringConceptPaths() {
		throw new UnsupportedOperationException("May not be needed");
	}

	public Collection<?> getReferringPropertyDefinitionNames(String conceptPath) {
		throw new UnsupportedOperationException("May not be needed");
	}

	public Collection<String> getReferringViewPaths() {
		throw new UnsupportedOperationException("May not be needed");
	}

	/**
	 * This will be required in codegen
	 */
	public List<StateMachine> getStateMachines() {
		//Get the list of statemachines in the adapted entities
		List<com.tibco.cep.designtime.core.model.states.StateMachine> adaptedStateMachines = adapted.getStateMachines();
		List<StateMachine> stateMachines = new ArrayList<StateMachine>(adaptedStateMachines.size());
		for (com.tibco.cep.designtime.core.model.states.StateMachine stateMachine : adaptedStateMachines) {

			try {
				StateMachineAdapter adapter =
					CoreAdapterFactory.INSTANCE.createAdapter(stateMachine, emfOntology);
				stateMachines.add(adapter);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return stateMachines;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StateMachine> getAllStateMachines() {
		final List<StateMachine> list = new ArrayList<StateMachine>(this.getStateMachines());
        if (this.getSuperConcept() != null) {
            list.addAll(this.getSuperConcept().getAllStateMachines());
        }
        return list;
	}

	/**
	 * This will be required in codegen
	 */
	public Collection<String> getSubConceptPaths() {
		//This can completely work on the underlying EMF model
		//Get the project name of the adapted entity
		String ownerProject = adapted.getOwnerProjectName();
		//Get all Concepts
		//I would have liked this method to return a type safe list
		List<DesignerElement> concepts =
			CommonIndexUtils.getAllElements(ownerProject, ELEMENT_TYPES.CONCEPT);
		List<String> subConceptPaths = new ArrayList<String>(concepts.size());
		for (DesignerElement concept : concepts) {
			EntityElement entityElement = (EntityElement)concept;
			//I am not doing type check here as I am presuming it will return concepts only
			com.tibco.cep.designtime.core.model.element.Concept indexedEntity =
				(com.tibco.cep.designtime.core.model.element.Concept)entityElement.getEntity();
			//Get its Super concept
			if (adapted.getFullPath().equals(indexedEntity.getSuperConceptPath())) {
				subConceptPaths.add(indexedEntity.getFullPath());
			}
		}
		return subConceptPaths;
	}

	public Collection<String> getSubInstancePaths() {
		throw new UnsupportedOperationException("To be Done");
	}

	public PropertyDefinition getSubPropertyDefinition(String name) {
		throw new UnsupportedOperationException("To be Done");
	}

	public Concept getSuperConcept() {
		com.tibco.cep.designtime.core.model.element.Concept superConcept = adapted.getSuperConcept();
		if (superConcept != null) {
			return new ConceptAdapter(superConcept, emfOntology);
		}
		return null;
	}

	public String getSuperConceptPath() {
		com.tibco.cep.designtime.core.model.element.Concept superConcept = adapted.getSuperConcept();
		if (superConcept != null) {
			return superConcept.getFullPath();
		}
		if (adapted.getSuperConceptPath() != null && adapted.getSuperConceptPath().length() > 0) {
			// BE-23237 : this can happen if one project library concept inherits from a concept from a different project library
			// just return the specified path, it will resolve properly
			return adapted.getSuperConceptPath();
		}
		return null;
	}

	/**
	 * An important method
	 */
	public boolean isA(Concept concept) {
		//No harm in this cast
		ConceptAdapter other = (ConceptAdapter)concept;
		return adapted.isA(other.getAdapted());
	}

	public boolean isAScorecard() {
		return adapted.isScorecard();
	}

	public boolean isContained() {
		//return adapted.isContained();
		return (getParentPropertyDefinition() != null);
	}

	public boolean isPOJO() {
		return adapted.isPOJO();
	}

	public boolean isTransient() {
		return adapted.isTransient();
	}

	public boolean isMetric() {
		return adapted.isMetric();
	}

	public boolean isRollingTimeMetric() {
		if (isMetric()) {
			return ((MetricImpl) adapted).getType() == METRIC_TYPE.ROLLING_TIME;
		}
		return false;
	}

    public String getName() {
//        if (_enableMetricTracking == true) {
//            return (super.getName() + "DVM");
//        }
//        else {
            return super.getName();
//        }
    }

    public String getFullPath() {
//        if (_enableMetricTracking == true) {
//            return (super.getFullPath() + "DVM");
//        }
//        else {
            return super.getFullPath();
//        }
    }
	/**
	 * We will write the to XSD implementation
	 * when we implement our own {@linkplain TnsFlavor}
	 */
	public SmElement toSmElement() {
		String fullPath = getFullPath();
        DefaultComponentFactory factory = new DefaultComponentFactory();
        DefaultSchema schema = (DefaultSchema) factory.createSchema();
        schema.setNamespace(fullPath);

        // The complex type for this Concept
        DefaultType type = (DefaultType) factory.createType();
        type.setName(getName());
        type.setNamespace(fullPath);
        type.setSchema(schema);
        type.setComplex();

        // todo Might not need these - rkt
        type.setAllowedDerivation(SmType.RESTRICTION + SmType.EXTENSION);
        type.setAllowedSubstitution(SmType.RESTRICTION + SmType.EXTENSION);
        type.setMixedContent(false);
        type.setIsOnAttributesOnly(false);
        type.setAbstract(false);
        type.setNative(false);

        Concept superConcept = getSuperConcept();
        if (superConcept != null) {
            type.setBaseType(superConcept.toSmElement().getType());
        }
        schema.addSchemaComponent(type); /* Add the complex type to the schema */

        /* The element for this Concept within the schema */
        //DefaultElement element = (DefaultElement) factory.createElement();
        MutableElement element = createElement(fullPath, getName(), type, schema);
        //element.setName(fullPath);
        schema.addSchemaComponent(element); /* Add an element of this type to the schema */

        /* Create the sub-element group for this type */
        DefaultModelGroup modelGroup = (DefaultModelGroup) factory.createModelGroup();
        modelGroup.setCompositor(SmModelGroup.SEQUENCE);
        modelGroup.setSchema(schema);

        Collection<PropertyDefinition> props = this._getAllPropertyDefinitions();
        Iterator<PropertyDefinition> propIt = props.iterator();
        while (propIt.hasNext()) {
            PropertyDefinition dpd = (PropertyDefinition) propIt.next();
            DefaultParticle propParticle = (DefaultParticle) factory.createParticle();
            if (dpd.isArray()) {
                propParticle.setMinOccurrence(0);
                propParticle.setMaxOccurrence(Integer.MAX_VALUE);
            } else {
                propParticle.setMinOccurrence(0);
                propParticle.setMaxOccurrence(1);
            }
            SmType propElemType = getTypeForPropertyDefinition(schema, dpd);
            MutableElement propElement = createElement(fullPath, dpd.getName(), propElemType, schema);
            //propElement.setName(dpd.getName());
            //propElement.setNamespace(fullPath);
            //propElement.setType(propElemType);
            propParticle.setTerm(propElement);
            modelGroup.addParticle(propParticle);
        }//endwhile
        type.setContentModel(modelGroup);
        return element;
	}

	protected MutableElement createElement(String namespace, String localname, SmType type, MutableSchema schema) {
        //throws ConversionException {
        // If element already exists in our schema, just return that element.
        MutableElement element = null; // todo (caching)   SmSupport.getElement(schema, localname);
        // If we still don't have an element, create one.
        if (element == null) {
            MutableElement mutableElement = schema.getComponentFactory().createElement();
            mutableElement.setName(localname);
            mutableElement.setNamespace(namespace);
            mutableElement.setSchema(schema);
            mutableElement.setType(type);
            mutableElement.setAllowedSubstitution(SmType.RESTRICTION + SmType.EXTENSION + SmType.SUBSTITUTION);
            mutableElement.setAllowedDerivation(SmType.RESTRICTION + SmType.EXTENSION);
            mutableElement.setNillable(false);
            mutableElement.setAbstract(false);
            element = mutableElement;
        }
        return element;
    }

	protected SmType getTypeForPropertyDefinition(DefaultSchema schema, PropertyDefinition dpd) {
        SmType type = null;
        switch (dpd.getType()) {
            case PropertyDefinition.PROPERTY_TYPE_BOOLEAN :
                type = XSDL.BOOLEAN;
                break;
            case PropertyDefinition.PROPERTY_TYPE_INTEGER :
                type = XSDL.INTEGER;
                break;
            case PropertyDefinition.PROPERTY_TYPE_REAL :
                type = XSDL.DOUBLE;
                break;
            case PropertyDefinition.PROPERTY_TYPE_STRING :
                type = XSDL.STRING;
                break;
            case PropertyDefinition.PROPERTY_TYPE_CONCEPT :
            case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE :
                type = SmSupport.getType(schema, dpd.getConceptTypePath());
                if (type != null) {
                    break;
                }
                Concept concept = dpd.getConceptType();
                type = concept.toSmElement().getType();
                schema.addSchemaComponent(type);
                break;
        }
        return type;
    }


	public boolean isAutoStartStateMachine() {
		return adapted.isAutoStartStateMachine();
	}

	public List<PropertyDefinition> getAllUserDefinedFields() {
//		List<com.tibco.cep.designtime.core.model.element.PropertyDefinition>
//			adaptedProperties = new ArrayList<com.tibco.cep.designtime.core.model.element.PropertyDefinition>();
//		_getPropertyDefinitions(adapted,adaptedProperties);
		if (isMetric()) {

			EList<com.tibco.cep.designtime.core.model.element.PropertyDefinition> adaptedProperties = ((MetricImpl) adapted).getUserDefinedFields();
			List<PropertyDefinition> pDefs = new ArrayList<PropertyDefinition>(adaptedProperties.size());

			for (com.tibco.cep.designtime.core.model.element.PropertyDefinition p : adaptedProperties) {
				try {
					PropertyDefinitionAdapter propertyDefinitionAdapter =
						CoreAdapterFactory.INSTANCE.createAdapter(p, emfOntology);
					pDefs.add(propertyDefinitionAdapter);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			return pDefs;
		}
		else {
			return new ArrayList<PropertyDefinition>(0);
		}
	}
}
