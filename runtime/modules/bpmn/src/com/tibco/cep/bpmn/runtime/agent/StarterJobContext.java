package com.tibco.cep.bpmn.runtime.agent;

import com.tibco.cep.bpmn.runtime.model.AbstractJobContext;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplate;
import com.tibco.cep.runtime.model.element.impl.BaseGeneratedConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaProperty;
import com.tibco.xml.data.primitive.ExpandedName;

/*
* Author: Suresh Subramani / Date: 7/26/12 / Time: 10:55 AM
*/
public class StarterJobContext extends AbstractJobContext {

    private ProcessTemplate processTemplate;
    private static ExpandedName expandedName = ExpandedName.makeName("www.tibco.com/be/bpmn", "StarterJobContext");

    StarterJobContext(ProcessTemplate processTemplate,long id) {
        super(id);
        this.processTemplate = processTemplate;
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }

    @Override
    public String getDesignTimeProcessUri() {
        return processTemplate.getProcessName().getModeledFQN();
    }

    @Override
    public int getNumTasks() {
        return processTemplate.allTasks().size();
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public ExpandedName getExpandedName() {
        return expandedName;
    }

    @Override
    public boolean excludeNullProps() {
        return false;
    }

    @Override
    public boolean includeNullProps() {
        return true;
    }

    @Override
    public boolean expandPropertyRefs() {
        return false;
    }

    @Override
    public boolean setNilAttribs() {
        return false;
    }

    @Override
    public boolean treatNullValues() {
        return false;
    }

    @Override
    protected BaseGeneratedConceptImpl newInstance() {
        throw new RuntimeException("Method not supported exception");
    }

    @Override
    public int getPropertyIndex(String name) {
        throw new RuntimeException("Method not supported exception");
    }

    @Override
    protected int _getNumProperties_constructor_only() {
        return 0;
    }

    @Override
    public MetaProperty getMetaProperty(int index) {
        throw new RuntimeException("Method not supported exception");
    }

    @Override
    public ProcessTemplate getProcessTemplate() {
        return processTemplate;
    }

    @Override
    public short getProcessTemplateVersion() {
        return (short) processTemplate.getVersionInfo().getRevision();
    }

    @Override
    protected int[] conceptPropIdxs() {
        return null;
    }
}
