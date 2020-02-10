package com.tibco.cep.studio.dashboard;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.registry.ElementTypes;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.RuleTemplate;
import com.tibco.cep.designtime.model.service.channel.Channel;
import com.tibco.cep.studio.core.adapters.ConceptAdapter;
import com.tibco.cep.studio.core.adapters.CoreAdapterFactory;
import com.tibco.cep.studio.core.adapters.CoreOntologyAdapter;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.util.PlatformUtil;
import com.tibco.xml.data.primitive.ExpandedName;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.*;

public class DashboardOntologyAdapter extends CoreOntologyAdapter {

	private Map<String, Metric> dvmMetrics;

	public DashboardOntologyAdapter(DesignerProject ind) {
		super(ind);
		dvmMetrics = new HashMap<String, Metric>();
		//optimize the metric dvm lookups when running in runtime mode, design time cannot optimize due to possible changes to metric. see BE-15556
		if (PlatformUtil.INSTANCE.isStudioPlatform() == false) {
			//load all the metrics
			List<com.tibco.cep.designtime.core.model.Entity> metrics = CommonIndexUtils.getAllEntities(this.index.getName(), ELEMENT_TYPES.METRIC);
			for (com.tibco.cep.designtime.core.model.Entity metric : metrics) {
				dvmMetrics.put(metric.getFullPath(), createDVM(metric));
			}
		}
	}

	private Metric createDVM(com.tibco.cep.designtime.core.model.Entity metric) {
		Metric metricDVM = (Metric) EcoreUtil.copy(metric);
		metricDVM.setGUID(GUIDGenerator.getGUID());
		metricDVM.setName(metricDVM.getName()+"DVM");
		return metricDVM;
	}

	@Override
	public Entity getAlias(String alias) {
		return null;
	}

	@Override
	public Collection<Channel> getChannels() {
		return Collections.emptyList();
	}

	@Override
	public Concept getConcept(String path) {
		//are we loading a metric
		Concept concept = super.getConcept(path);
		if (concept == null) {
			if (path.endsWith("DVM") == true) {
				//we are dealing with a metric
				Metric metricDVM = dvmMetrics.get(path);
				if (metricDVM == null) {
					//only in design time , this block will trigger
					String metricPath = path.substring(0,path.length()-3);
					com.tibco.cep.designtime.core.model.Entity metric = CommonIndexUtils.getEntity(this.index.getName(), metricPath, ELEMENT_TYPES.METRIC);
					if (metric != null) {
						metricDVM = createDVM(metric);
					}
				}
				concept = new ConceptAdapter(metricDVM, this);
				concept.enableMetricTracking();
			}
		}
		return concept;
	}

	@Override
	public Collection<Concept> getConcepts() {
		try {
			LinkedList<Concept> concepts = new LinkedList<Concept>();
			//get all the metrics in the project
			List<com.tibco.cep.designtime.core.model.Entity> metrics = CommonIndexUtils.getAllEntities(this.index.getName(), ELEMENT_TYPES.METRIC);
			for (com.tibco.cep.designtime.core.model.Entity entity : metrics) {
				//add basic metric
				Concept adaptedMetric = CoreAdapterFactory.INSTANCE.createAdapter(entity, this);
				concepts.add(adaptedMetric);
				//get the dvm metric
				Metric metricDVM = dvmMetrics.get(entity.getFullPath());
				if (metricDVM == null) {
					metricDVM = createDVM(entity);
				}
				//adapt the metric
				Concept adaptedMetricDVM = new ConceptAdapter(metricDVM, this);
				adaptedMetricDVM.enableMetricTracking();
				concepts.add(adaptedMetricDVM);
			}
			return concepts;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<Entity> getEntities() {
		try {
			LinkedList<Entity> entities = new LinkedList<Entity>();
			//get all the metrics in the project
			List<com.tibco.cep.designtime.core.model.Entity> metrics = CommonIndexUtils.getAllEntities(this.index.getName(), ELEMENT_TYPES.METRIC);
			for (com.tibco.cep.designtime.core.model.Entity entity : metrics) {
				//add basic metric
				Concept adaptedMetric = CoreAdapterFactory.INSTANCE.createAdapter(entity, this);
				entities.add(adaptedMetric);
				//get the dvm metric
				Metric metricDVM = dvmMetrics.get(entity.getFullPath());
				if (metricDVM == null) {
					metricDVM = createDVM(entity);
				}
				//adapt the metric, we cannot use CoreAdapterFactor since it caches the adapter.
				//generally cached adapters work fine since all the studio editor probably modify the instance in the index
				//which gets shared with the adapter
				Concept adaptedMetricDVM = new ConceptAdapter(metricDVM, this);
				adaptedMetricDVM.enableMetricTracking();
				entities.add(adaptedMetricDVM);
			}
			return entities;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<Entity> getEntities(ElementTypes[] types) {
		LinkedList<Entity> entities = new LinkedList<Entity>();
		for (ElementTypes type : types) {
			if (type.compareTo(ElementTypes.METRIC) == 0) {
				entities.addAll(getEntities());
			}
		}
		return entities;
	}

	@Override
	public Entity getEntity(ExpandedName name) {
		//the base implementation invokes com.tibco.cep.studio.dashboard.core.DashboardOntologyAdapter.getEntity(String)
		return super.getEntity(name);
	}

	@Override
	public Entity getEntity(String path) {
		return getConcept(path);
	}

	@Override
	public Entity getEntity(String folder, String name) {
		Entity entity = super.getEntity(folder, name);
		if (entity == null) {
			if (name.endsWith("DVM") == true) {
				//we are dealing with a metric
				Metric dvmMetric = dvmMetrics.get(folder+"/"+name);
				if (dvmMetric == null) {
					String metricName = name.substring(0, name.length() - 3);
					com.tibco.cep.designtime.core.model.Entity metric = CommonIndexUtils.getEntity(index.getName(), folder, metricName);
					if (metric != null) {
						dvmMetric = createDVM(metric);
					}
				}
				Concept adaptedMetricDVM = new ConceptAdapter(dvmMetric, this);
				adaptedMetricDVM.enableMetricTracking();
				return adaptedMetricDVM;
			}
		}
		return entity;
	}

	@Override
	public Event getEvent(String path) {
		return null;
	}

	@Override
	public Collection<Event> getEvents() {
		return Collections.emptyList();
	}

	@Override
	public Folder getFolder(String fullPath) {
		return super.getFolder(fullPath);
	}

	@Override
	public Date getLastModifiedDate() {
		return super.getLastModifiedDate();
	}

	@Override
	public Date getLastPersistedDate() {
		return super.getLastPersistedDate();
	}

	@Override
	public Folder getRootFolder() {
		return super.getRootFolder();
	}

	@Override
	public RuleFunction getRuleFunction(String path) {
		return null;
	}

	@Override
	public Collection<RuleFunction> getRuleFunctions() {
		return Collections.emptyList();
	}

	@Override
	public Collection<Rule> getRules() {
		return Collections.emptyList();
	}

	@Override
	public Collection<RuleTemplate> getRuleTemplates() {
		return Collections.emptyList();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection getStandaloneStateMachines() {
		return Collections.emptyList();
	}



}