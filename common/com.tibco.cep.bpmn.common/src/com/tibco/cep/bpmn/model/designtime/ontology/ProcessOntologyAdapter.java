package com.tibco.cep.bpmn.model.designtime.ontology;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.BpmnModelCache;
import com.tibco.cep.bpmn.model.designtime.ProcessAdapterFactory;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultBpmnIndex;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.designtime.model.AbstractOntologyAdapter;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.process.SubProcessModel;
import com.tibco.cep.designtime.model.registry.ElementTypes;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.RuleTemplate;
import com.tibco.cep.designtime.model.service.channel.Channel;
import com.tibco.xml.data.primitive.ExpandedName;

public class ProcessOntologyAdapter extends AbstractOntologyAdapter<EObject> implements Ontology {
	
	
//	DefaultBpmnIndex bpmnOntology;
	/**
	 * @param index
	 */
	public ProcessOntologyAdapter(final EObject index) {
		super(index);
		if (index != null) {
			EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(index);
			setName((String) wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME));
//			bpmnOntology = new DefaultBpmnIndex(index);
		}
	}
	
	
	/**
	 * @param ontologyName
	 */
	public ProcessOntologyAdapter(String ontologyName) {
		this(BpmnCommonIndexUtils.getIndex(ontologyName));
	}

	@Override
	public Entity getAlias(String alias) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Channel> getChannels() {
		return Collections.emptySet();
	}

	@Override
	public Concept getConcept(String path) {
		BpmnIndex bpmnIndex = getBpmnIndex();
		if(bpmnIndex != null){
			EObject p = bpmnIndex.getProcessByPath(path);		
			if(p != null) {
				ProcessAdapter pa =  new ProcessAdapter(p, getCommonOntology());
				return pa.cast(Concept.class);
			}
		}
		return null;		
	}

	@Override
	public Collection<Concept> getConcepts() {
		List<Concept> cl = new ArrayList<Concept>();
		BpmnIndex bpmnIndex = getBpmnIndex();
		if(bpmnIndex != null){
			for(EObject p:bpmnIndex.getAllProcesses()) {
				if(p != null) {
//					ProcessAdapter pa =  new ProcessAdapter(p, getCommonOntology());
//					cl.add(pa.cast(Concept.class));
				}
			}
		}

		return cl;
	}
	
	public BpmnIndex getBpmnIndex() {
		if(this.index == null) {
			EObject bpmnIndex = BpmnModelCache.getInstance().getIndex(getName());
			if(bpmnIndex != null)
				return new DefaultBpmnIndex(bpmnIndex);
			else 
				return null;
		}
		return new DefaultBpmnIndex(this.index);
	}

	@Override
	public Collection<Entity> getEntities() {
		List<Entity> cl = new ArrayList<Entity>();
		BpmnIndex bpmnIndex = getBpmnIndex();
		if(bpmnIndex != null){
			for(EObject p:bpmnIndex.getAllProcesses()) {
				if(p != null) {
					ProcessAdapter pa =  new ProcessAdapter(p, getCommonOntology() == null? this:getCommonOntology());
					cl.add(pa.cast(Concept.class));
				}
			}
		}
		
		return cl;
	}

	@Override
	public Collection<Entity> getEntities(ElementTypes[] types) {
		Collection<Entity> entities = new HashSet<Entity>();
		BpmnIndex bpmnIndex = getBpmnIndex();
		if(bpmnIndex != null){
			if(Arrays.asList(types).contains(ElementTypes.PROCESS)) {
				for(EObject p: bpmnIndex.getAllProcesses()) {
					Entity pe = ProcessAdapterFactory.INSTANCE.createAdapter(p,getCommonOntology());
					entities.add(pe);
				}
			}
		}
		
		return entities;
	}

	@Override
	public Entity getEntity(ExpandedName name) {
		return null; 
	}

	@Override
	public Entity getEntity(String path) {
//		DesignerProject coreIndex = com.tibco.cep.studio.common.StudioProjectCache.getInstance().getIndex(getName());
//		CoreOntologyAdapter ca = new CoreOntologyAdapter(coreIndex);
//		return ca.getEntity(path);
		BpmnIndex bpmnIndex = getBpmnIndex();
		if(bpmnIndex != null){
			EObject p = null;
			int index = path.indexOf("$");
			if(index != -1) {
				String procPath = path.substring(0,index);
				String subProc = path.substring(index+1);
				p = bpmnIndex.getProcessByPath(procPath);
				if(p != null){
					ProcessAdapter padapter = new ProcessAdapter(p, getCommonOntology());
					String pname = padapter.getName();
					SubProcessModel sp = padapter.getSubProcessById(pname+"."+subProc);
					return sp;
				}
			} else {
				p = bpmnIndex.getProcessByPath(path);		
			}
			if(p != null) {
				return new ProcessAdapter(p, getCommonOntology());
			}
		}
		
		return null;
	}

	@Override
	public Entity getEntity(String folder, String name) {
//		DesignerProject coreIndex = com.tibco.cep.studio.common.StudioProjectCache.getInstance().getIndex(getName());
//		CoreOntologyAdapter ca = new CoreOntologyAdapter(coreIndex);
//		return ca.getEntity(folder,name);
		BpmnIndex bpmnIndex = getBpmnIndex();
		if(bpmnIndex != null){
			EObject p = bpmnIndex.getProcessByPath(folder,name);		
			if(p != null) {
				return new ProcessAdapter(p, getCommonOntology());
			}
		}
		
		return null;
	}

	@Override
	public Event getEvent(String path) {
//		DesignerProject coreIndex = com.tibco.cep.studio.common.StudioProjectCache.getInstance().getIndex(getName());
//		CoreOntologyAdapter ca = new CoreOntologyAdapter(coreIndex);
//		return ca.getEvent(path);
		return null;
	}

	@Override
	public Collection<Event> getEvents() {
		return Collections.emptySet();
	}

	@Override
	public Folder getFolder(String fullPath) {
		EObject element = BpmnCommonIndexUtils.getElement(getName(), fullPath);
		if(element != null) {
//			FolderAdapter adapter = new FolderAdapter(this,element);
		}
		return null;
	}

	@Override
	public Date getLastModifiedDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getLastPersistedDate() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Folder getRootFolder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RuleFunction getRuleFunction(String path) {
//		DesignerProject coreIndex = com.tibco.cep.studio.common.StudioProjectCache.getInstance().getIndex(getName());
//		CoreOntologyAdapter ca = new CoreOntologyAdapter(coreIndex);
//		return ca.getRuleFunction(path);
		return null;
	}

	@Override
	public Collection<RuleFunction> getRuleFunctions() {
		// TODO Auto-generated method stub
		return Collections.emptySet();
	}

	@Override
	public Collection<Rule> getRules() {
		// TODO Auto-generated method stub
		return Collections.emptySet();
	}

    @Override
    public Collection<RuleTemplate> getRuleTemplates() {
        // TODO Auto-generated method stub
        return Collections.emptySet();
    }

    public Collection<?> getStandaloneStateMachines() {
		// TODO Auto-generated method stub
		//FIXME 3.0.2 merge: Need to implement this
		return null;
	}

}
