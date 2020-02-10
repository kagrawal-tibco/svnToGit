/**
 *
 */
package com.tibco.cep.studio.core.adapters;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.java.JavaResource;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.states.InternalStateTransition;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateSimple;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.ModelAdapterFactory;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.studio.core.index.model.RuleElement;


/**
 * Factory class to create an adapter instance for a BE designtime
 * interface.
 * <p>
 * Checks for cached entities can be made here
 * </p>
 * @author aathalye
 *
 */
public class CoreAdapterFactory implements ModelAdapterFactory {

	public static final CoreAdapterFactory INSTANCE = new CoreAdapterFactory();

	public static HashMap<String, AdapterCache> fProjectAdapterCache = new HashMap<String, AdapterCache>();
    // provide a mechanism to disable caching
	private static String enableCache = System.getProperty("com.tibco.adapter.cache.enable");



	/**
	 * @param <OE>
	 * @param <NE>
	 * @param entity
	 * @param emfOntology
	 * @param params -> Optional parameters to aid the construction of object.
	 * <b>
	 * Example : Instances of StateMachines.
	 * </b>
	 * @return
	 * @throws Exception
	 */
	//TODO Check for cached adapter can be done here to avoid cluttering in the Ontology class
	@SuppressWarnings("unchecked")
	public <OE extends Entity, NE extends EObject> OE createAdapter(NE entity,
                                                                   Ontology emfOntology,
                                                                   Object...params)  {

		OE adapter = null;
		adapter = (OE) getCachedAdapter((com.tibco.cep.designtime.core.model.Entity) entity, emfOntology.getName());
		if (adapter != null) {
			Ontology idxAdapter = adapter.getOntology();
			if (idxAdapter instanceof CoreOntologyAdapter && ((CoreOntologyAdapter) idxAdapter).getIndex() == null) {
				if (emfOntology instanceof CoreOntologyAdapter) {
					// the index is used to get the folder for project library elements, among other things,
					// so be sure to set it here if it has not been already (BE-9948)
					((CoreOntologyAdapter) idxAdapter).setIndex(((CoreOntologyAdapter) emfOntology).getIndex());
				}
			}
			// return the cached adapter
			return adapter;
		}
		try{
			if(entity instanceof JavaSource) {
				Constructor<JavaSourceAdapter> constructor =
						JavaSourceAdapter.class.getConstructor(JavaSource.class,Ontology.class);
				adapter = (OE) constructor.newInstance((JavaSource)entity,emfOntology);
			} else if(entity instanceof JavaResource) {
				Constructor<JavaResourceAdapter> constructor =
						JavaResourceAdapter.class.getConstructor(JavaResource.class,Ontology.class);
				adapter = (OE) constructor.newInstance((JavaResource)entity,emfOntology);
			} else if (entity instanceof Concept) {
				Constructor<ConceptAdapter> constructor =
					ConceptAdapter.class.getConstructor(Concept.class, Ontology.class);
				adapter = (OE)constructor.newInstance((Concept)entity, emfOntology);
			} else if (entity instanceof Scorecard) {
				Constructor<ScorecardAdapter> constructor =
					ScorecardAdapter.class.getConstructor(Scorecard.class, Ontology.class);
				adapter = (OE)constructor.newInstance((Scorecard)entity, emfOntology);
			} else if (entity instanceof StateMachine) {
				Constructor<StateMachineAdapter> constructor =
					StateMachineAdapter.class.getConstructor(StateMachine.class, Ontology.class);
				adapter = (OE)constructor.newInstance((StateMachine)entity, emfOntology);
			} else if  (entity instanceof State) {
				if  (entity instanceof StateStart) {
					Constructor<StateStartAdapter> constructor =
						StateStartAdapter.class.getConstructor(StateStart.class, Ontology.class);
					adapter = (OE)constructor.newInstance((StateStart)entity, emfOntology);
				} else if  (entity instanceof StateEnd) {
					Constructor<StateEndAdapter> constructor =
						StateEndAdapter.class.getConstructor(StateEnd.class, Ontology.class);
					adapter = (OE)constructor.newInstance((StateEnd)entity, emfOntology);
				} else if( entity instanceof StateSimple) {
					Constructor<StateSimpleAdapter> constructor =
						StateSimpleAdapter.class.getConstructor(StateSimple.class, Ontology.class);
					adapter = (OE)constructor.newInstance((StateSimple)entity, emfOntology);
				} else if( entity instanceof StateComposite) {
					if(entity instanceof StateSubmachine) {
						Constructor<StateSubMachineAdapter> constructor =
							StateSubMachineAdapter.class.getConstructor(StateSubmachine.class, Ontology.class);
						adapter = (OE)constructor.newInstance((StateSubmachine)entity, emfOntology);
					} else {
						Constructor<StateCompositeAdapter> constructor =
							StateCompositeAdapter.class.getConstructor(StateComposite.class, Ontology.class);
						adapter = (OE)constructor.newInstance((StateComposite)entity, emfOntology);
					}
				} else {
					Constructor<StateAdapter> constructor =
						StateAdapter.class.getConstructor(State.class, Ontology.class);
					adapter = (OE)constructor.newInstance((State)entity, emfOntology);
				}
			} else if( entity instanceof StateTransition) {
				if(entity instanceof InternalStateTransition) {
					Constructor<InternalStateTransitionAdapter> constructor =
						InternalStateTransitionAdapter.class.getConstructor(State.class, Ontology.class);
					adapter = (OE)constructor.newInstance((InternalStateTransition)entity, emfOntology);
				} else {
					Constructor<StateTransitionAdapter> constructor =
						StateTransitionAdapter.class.getConstructor(StateTransition.class, Ontology.class);
					adapter = (OE)constructor.newInstance((StateTransition)entity, emfOntology);
				}
			} else if (entity instanceof Event) {
				Constructor<EventAdapter> constructor =
					EventAdapter.class.getConstructor(Event.class, Ontology.class);
				adapter = (OE)constructor.newInstance((Event)entity, emfOntology);
			} else if (entity instanceof RuleFunction) {
				Constructor<RuleFunctionAdapter> constructor =
					RuleFunctionAdapter.class.getConstructor(RuleFunction.class, Ontology.class);
				adapter = (OE)constructor.newInstance((RuleFunction)entity, emfOntology);
			} else if (entity instanceof RuleTemplate) {
				Constructor<RuleTemplateAdapter> constructor =
						RuleTemplateAdapter.class.getConstructor(RuleTemplate.class, Ontology.class);
				adapter = (OE)constructor.newInstance((RuleTemplate)entity, emfOntology);
			} else if (entity instanceof Rule) {
				Constructor constructor = null;
				if (entity.eContainer() instanceof StateEntity) {
					constructor = StateRuleAdapter.class.getConstructor(Rule.class, Ontology.class);
				} else {
					constructor = RuleAdapter.class.getConstructor(Rule.class, Ontology.class);
				}
				adapter = (OE)constructor.newInstance((Rule)entity, emfOntology);
			} else if (entity instanceof Channel) {
				Constructor<ChannelAdapter> constructor =
					ChannelAdapter.class.getConstructor(Channel.class, Ontology.class);
				adapter = (OE)constructor.newInstance((Channel)entity, emfOntology);
			} else if (entity instanceof Destination) {
				Constructor<DestinationAdapter> constructor =
					DestinationAdapter.class.getConstructor(Destination.class, Ontology.class);
				adapter = (OE)constructor.newInstance((Destination)entity, emfOntology);
			} else if (entity instanceof PropertyDefinition) {
				if (params.length > 0) {
					if (params[0] instanceof Boolean) {
						Constructor<PropertyDefinitionAdapter> constructor =
							PropertyDefinitionAdapter.class.getConstructor(PropertyDefinition.class, Ontology.class, Boolean.class);
						adapter = (OE)constructor.newInstance((PropertyDefinition)entity, emfOntology, (Boolean) params[0]);
					}
				}
				else {
					Constructor<PropertyDefinitionAdapter> constructor =
						PropertyDefinitionAdapter.class.getConstructor(PropertyDefinition.class, Ontology.class);
					adapter = (OE)constructor.newInstance((PropertyDefinition)entity, emfOntology);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (adapter instanceof ICacheableAdapter) {
			cacheAdapter(emfOntology.getName(), entity, adapter);
		}
		return adapter;
	}

	private void cacheAdapter(String projectName, EObject entity, Entity adapter) {
		if (isCacheDisabled()) {
			return;
		}
		synchronized (fProjectAdapterCache) {
			AdapterCache adapterCache = fProjectAdapterCache.get(projectName);
			if (adapterCache == null) {
				adapterCache = new AdapterCache();
				fProjectAdapterCache.put(projectName, adapterCache);
			}
			String key = getCacheKey(entity);
			adapterCache.put(key, adapter);
		}

	}

	private static boolean isCacheDisabled() {
		return enableCache != null && "false".equalsIgnoreCase(enableCache);
	}

	public static Entity getCachedAdapter(com.tibco.cep.designtime.core.model.Entity entity, String projectName) {
		if (isCacheDisabled()) {
			return null;
		}
		if (entity == null) {
			return null; // nothing can be done
		}
		AdapterCache adapterCache = fProjectAdapterCache.get(projectName);
		if (adapterCache == null) {
			return null; // adapter not yet cached, return null
		}
		String key = getCacheKey(entity);
		Entity adapted = adapterCache.get(key);
//		if (adapted instanceof EntityAdapter) {
//			if (!((EntityAdapter) adapted).getAdapted().getClass().equals(entity.getClass())) {
//				// TODO : handle error?
//				System.err.println("different classes");
//			}
//		}
		return adapted;
	}

	/**
	 * Clear the cache to allow the recreation of all adapters
	 */
	public static void clearCache(String projectName) {
		synchronized (fProjectAdapterCache) {
			fProjectAdapterCache.remove(projectName);
		}
	}

	public static void clearCachedAdapter(String projectName, com.tibco.cep.designtime.core.model.Entity entity) {
		synchronized (fProjectAdapterCache) {
			AdapterCache adapterCache = fProjectAdapterCache.get(projectName);
			String key = getCacheKey(entity);
			if (adapterCache != null && adapterCache.containsKey(key)) {
				adapterCache.remove(key);
			}
		}
	}

	private static String getCacheKey(EObject entity) {
		if (entity == null) {
			return null;
		}
		if (entity instanceof Compilable && ((Compilable)entity).eContainer() != null) {
			Compilable compilable = (Compilable) entity;
			return getCompilableCacheKey(compilable);
		}
		if(entity instanceof com.tibco.cep.designtime.core.model.Entity) {
			return ((com.tibco.cep.designtime.core.model.Entity)entity).getFullPath();
		}
		return "";
	}

	private static String getCompilableCacheKey(Compilable compilable) {
		if (compilable.eContainer() == null || compilable.eContainer() instanceof RuleElement) {
			return compilable.getFullPath();
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(compilable.getName());
		EObject obj = compilable;
		while (obj.eContainer() != null) {
			obj = obj.eContainer();
			if (obj instanceof com.tibco.cep.designtime.core.model.Entity) {
				if (obj.eContainer() == null) {
					// this is the top level container, need to use the full path to distinguish
					// between other entities of the same name in different folders
					buffer.insert(0, '$');
					buffer.insert(0, ((com.tibco.cep.designtime.core.model.Entity)obj).getFullPath());
				} else {
					buffer.insert(0, '$');
					buffer.insert(0, ((com.tibco.cep.designtime.core.model.Entity)obj).getName());
				}
			}
		}
		return buffer.toString();
	}


}
