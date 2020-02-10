package com.tibco.cep.designtime;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.cep.designtime.model.AddOnRegistry;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.IndexCache;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.designtime.model.registry.ElementTypes;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.RuleTemplate;
import com.tibco.cep.designtime.model.service.channel.Channel;
import com.tibco.cep.util.PlatformUtil;
import com.tibco.xml.data.primitive.ExpandedName;

public class CommonOntologyAdapter implements Ontology {
	/**
	 * Addon to EMF Index map
	 */
	Map<AddOn, Ontology> ontologyMap;
	/**
	 * element type to Ontology Map
	 */
	Map<ElementTypes,Ontology> elementTypeAdapterMap = new HashMap<ElementTypes,Ontology>();
	/**
	 * Name of the project for which this ontology exists
	 */
	private String fOntologyName;

	public CommonOntologyAdapter(String name) throws Exception {
		this.fOntologyName = name;
		Map<AddOn,Ontology> adapters = new HashMap<AddOn,Ontology>();
		if(PlatformUtil.INSTANCE.isStudioPlatform()) {
			Class<?> platform = Class.forName("org.eclipse.core.runtime.Platform");
			if(platform != null) {
				Method m_extensionReg = platform.getDeclaredMethod("getExtensionRegistry");
				Object extReg =  m_extensionReg.invoke(null);
				Class<?> extRegClass = Class.forName("org.eclipse.core.runtime.IExtensionRegistry");
				Method getConfigs = extRegClass.getMethod("getConfigurationElementsFor", String.class);
				Object[] configs = (Object[]) getConfigs.invoke(extReg, "com.tibco.cep.rt.addonContributor");
				Class<?> cElementClass = Class.forName("org.eclipse.core.runtime.IConfigurationElement");
				Method exEl = cElementClass.getMethod("createExecutableExtension", String.class);
				Class<?> addOnLoaderClass = Class.forName("com.tibco.cep.designtime.model.IAddOnLoader");
				Method m_getAddOn = addOnLoaderClass.getMethod("getAddOn");
				Method m_getOntology = addOnLoaderClass.getMethod("getOntology", String.class);
				for(Object c: configs) {
					Object addonLoader = exEl.invoke(c, "class");
					if(addOnLoaderClass.isAssignableFrom(addonLoader.getClass())) {
						AddOn addon = (AddOn) m_getAddOn.invoke(addonLoader);
						Ontology ontology = (Ontology) m_getOntology.invoke(addonLoader, name);
						if(addon != null) {
							adapters.put(addon,ontology);
						}
					}
				}
			}


		} else {
			for(AddOn addon:AddOnRegistry.getInstance().getAddons()){
				String adapterClassName = addon.getOntologyAdapterClass();
				Class<?> adapterClazz = Class.forName(adapterClassName);
				Class<?> paramType = EObject.class;
				if (adapterClazz.getGenericSuperclass() != null
						&& adapterClazz.getGenericSuperclass() instanceof ParameterizedType) {
					Type[] pTypes = ((ParameterizedType) adapterClazz.getGenericSuperclass()).getActualTypeArguments();
					if (pTypes.length > 0) {
						if (pTypes[0] instanceof Class) {
							paramType = (Class<?>) pTypes[0];
						}
					}

					Constructor<?> constructor = adapterClazz.getConstructor(paramType);

					String indexCacheClassName = addon.getIndexCacheClass();
					Class<?> indexCacheClazz = Class.forName(indexCacheClassName);

					Method m_getInstance = indexCacheClazz.getDeclaredMethod("getInstance");
					if (m_getInstance != null) {
						Object cache = m_getInstance.invoke(null);
						if (cache instanceof IndexCache) {
							IndexCache icache = (IndexCache) cache;
							EObject index = icache.getIndex(name);
							Ontology o = (Ontology) constructor.newInstance(index);
							adapters.put(addon, o);
						}
					}
				}
			}
		}
		this.ontologyMap = adapters;
		for(Entry<AddOn, Ontology> e:adapters.entrySet()) {
			AddOn addon = e.getKey();
			for(ElementTypes et:addon.getSupportedEntityTypes().getElementType()){
				elementTypeAdapterMap.put(et,e.getValue());
			}

		}
	}


	public CommonOntologyAdapter(
			Map<AddOn, Ontology> imap) {

		this.ontologyMap = imap;
		for(Entry<AddOn, Ontology> e:imap.entrySet()) {
			AddOn addon = e.getKey();
			for(ElementTypes et:addon.getSupportedEntityTypes().getElementType()){
				elementTypeAdapterMap.put(et,e.getValue());
			}

		}

	}


	@Override
	public Entity getAlias(String alias) {
		Entity e = null;
		for(Ontology o:ontologyMap.values()) {
			e = o.getAlias(alias);
			if(e != null)
				break;
		}
		return e;
	}

	@Override
	public Collection<Channel> getChannels() {
		return elementTypeAdapterMap.get(ElementTypes.CHANNEL).getChannels();
	}

	@Override
	public Concept getConcept(String path) {
		for(Entry<AddOn, Ontology> e:ontologyMap.entrySet()) {
			AddOn addon = e.getKey();
			Ontology o = e.getValue();
			if(addon.getSupportedEntityTypes().getElementType().contains(ElementTypes.CONCEPT)) {
				Concept c = o.getConcept(path);
				if (c != null) {
					return c;
				}
			}
		}
		return null;
	}

	@Override
	public Collection<Concept> getConcepts() {
		Set<Concept> concepts = new HashSet<Concept>();
		for(Entry<AddOn, Ontology> e:ontologyMap.entrySet()) {
			AddOn addon = e.getKey();
			Ontology o = e.getValue();
			if(addon.getSupportedEntityTypes().getElementType().contains(ElementTypes.CONCEPT)) {
				concepts.addAll(o.getConcepts());
			}
		}
		return concepts;
	}

	@Override
	public Event getEvent(String path) {
		Event event = null;
		event =  elementTypeAdapterMap.get(ElementTypes.SIMPLEEVENT).getEvent(path);
		if(event == null) {
			event =  elementTypeAdapterMap.get(ElementTypes.TIMEEVENT).getEvent(path);
		}
		return event;
	}

	@Override
	public Collection<Event> getEvents() {
		Set<Event> entities = new HashSet<Event>();
		for(Entry<AddOn, Ontology> e:ontologyMap.entrySet()) {
			AddOn addon = e.getKey();
			Ontology o = e.getValue();
			if(addon.getSupportedEntityTypes().getElementType().contains(ElementTypes.SIMPLEEVENT)	||
					addon.getSupportedEntityTypes().getElementType().contains(ElementTypes.TIMEEVENT) ) {
				entities.addAll(o.getEvents());
			}
		}
		return entities;
	}

	@Override
	public RuleFunction getRuleFunction(String path) {
		return elementTypeAdapterMap.get(ElementTypes.RULEFUNCTION).getRuleFunction(path);
	}

	@Override
	public Collection<RuleFunction> getRuleFunctions() {
		Set<RuleFunction> rulefns = new HashSet<RuleFunction>();
		for(Entry<AddOn, Ontology> e:ontologyMap.entrySet()) {
			AddOn addon = e.getKey();
			Ontology o = e.getValue();
			if(addon.getSupportedEntityTypes().getElementType().contains(ElementTypes.RULEFUNCTION)) {
				rulefns.addAll(o.getRuleFunctions());
			}
		}
		return rulefns;
	}

	@Override
	public Collection<Rule> getRules() {
		Set<Rule> rules = new HashSet<Rule>();
		for(Entry<AddOn, Ontology> e:ontologyMap.entrySet()) {
			AddOn addon = e.getKey();
			Ontology o = e.getValue();
			if(addon.getSupportedEntityTypes().getElementType().contains(ElementTypes.RULE)) {
				rules.addAll(o.getRules());
			}
		}
		return rules;
	}

    @Override
    public Collection<RuleTemplate> getRuleTemplates() {
        final Set<RuleTemplate> ruleTemplates = new HashSet<RuleTemplate>();
        for (final Entry<AddOn, Ontology> e : ontologyMap.entrySet()) {
            final AddOn addon = e.getKey();
            final Ontology o = e.getValue();
            if (addon.getSupportedEntityTypes().getElementType().contains(ElementTypes.RULETEMPLATE)) {
                ruleTemplates.addAll(o.getRuleTemplates());
            }
        }
        return ruleTemplates;
    }

	@Override
	public Collection<Entity> getEntities() {
		Set<Entity> entities = new HashSet<Entity>();
		for(Ontology o:ontologyMap.values()) {
			entities.addAll(o.getEntities());
		}
		return entities;
	}

	@Override
	public Collection<Entity> getEntities(ElementTypes[] types) {
		Set<Entity> entities = new HashSet<Entity>();
		for(ElementTypes t: types) {
			for(Entry<AddOn, Ontology> e:ontologyMap.entrySet()) {
				AddOn addon = e.getKey();
				Ontology o = e.getValue();
				if(addon.getSupportedEntityTypes().getElementType().contains(t)) {
					entities.addAll(o.getEntities(types));
				}
			}
		}
		return entities;
	}



	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Ontology#getEntity(com.tibco.xml.data.primitive.ExpandedName)
	 */
	@Override
	public Entity getEntity(ExpandedName name) {
		/**
		 * We have to keep the same namespace here. The runtime uses this namespace
		 */
		String fullPath = name.getNamespaceURI().substring(RDFTnsFlavor.BE_NAMESPACE.length());
		return getEntity(fullPath);
	}

	@Override
	public Entity getEntity(String path) {
		Entity e = null;
		for(Ontology o:ontologyMap.values()) {
			e = o.getEntity(path);
			if(e != null)
				break;
		}
		return e;
	}

	@Override
	public Entity getEntity(String folder, String name) {
		Entity e = null;
		for(Ontology o:ontologyMap.values()) {
			e = o.getEntity(folder,name);
			if(e != null)
				break;
		}
		return e;
	}



	@Override
	public Folder getFolder(String fullPath) {
		Folder folder = null;
		for(Ontology o:ontologyMap.values()) {
			folder = o.getFolder(fullPath);
			if(folder != null)
				break;
		}
		return folder;
	}

	@Override
	public Date getLastModifiedDate() {
		Date dt = null;
		Date maxdt = null;
		for(Ontology o:ontologyMap.values()) {
			dt = o.getLastModifiedDate();
			if(dt.compareTo(maxdt) > 0){
				maxdt = dt;
			}
		}
		return maxdt;
	}

	@Override
	public Date getLastPersistedDate() {
		Date dt = null;
		Date maxdt = null;
		for(Ontology o:ontologyMap.values()) {
			dt = o.getLastModifiedDate();
			if(dt.compareTo(maxdt) > 0){
				maxdt = dt;
			}
		}
		return maxdt;
	}

	@Override
	public String getName() {
		if(fOntologyName == null) {
			String name = null;
			for(Ontology o:ontologyMap.values()) {
				name = o.getName();
				if(name != null){
					fOntologyName = name;
					break;
				}
			}
		}
		return fOntologyName;
	}

	@Override
	public Folder getRootFolder() {
		Folder folder = null;
		for(Ontology o:ontologyMap.values()) {
			folder = o.getRootFolder();
			if(folder != null)
				break;
		}
		return folder;
	}


	public Collection<?> getStandaloneStateMachines() {
		// TODO Auto-generated method stub
		//FIXME 3.0.2 merge: Need to implement this
		return null;
	}


}
