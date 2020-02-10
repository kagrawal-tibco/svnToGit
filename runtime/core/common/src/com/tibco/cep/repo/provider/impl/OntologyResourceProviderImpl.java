package com.tibco.cep.repo.provider.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.tibco.be.model.functions.impl.ModelFunctionsFactory;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.EntityInputSource;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.mutable.impl.DefaultMutableConcept;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStandaloneStateMachine;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.mutable.impl.DefaultMutableEvent;
import com.tibco.cep.designtime.model.mutable.MutableEntity;
import com.tibco.cep.designtime.model.mutable.MutableOntology;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableRuleFunction;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableRuleSet;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableStandaloneRule;
import com.tibco.cep.designtime.model.service.channel.mutable.impl.DefaultMutableChannel;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.ResourceProvider;
import com.tibco.cep.repo.provider.OntologyResourceProvider;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.xml.datamodel.XiNode;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 18, 2006
 * Time: 11:56:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class OntologyResourceProviderImpl<P extends Project> implements OntologyResourceProvider<P> {


    public Ontology ontology;
    protected P project;


    /**
     * @return the project
     */
    public P getProject() {
        return project;
    }


    public static final Object[][] vFileClasses2Ext = {
            {DefaultMutableStandaloneRule.class.getName(), "standalonerule", new Boolean(false)},
            {DefaultMutableRuleSet.class.getName(), "ruleset", new Boolean(false)},
            {DefaultMutableRuleFunction.class.getName(), "rulefunction", new Boolean(false)},
            //{DefaultMutableDecisionTable.class.getName(),"decisiontable", new Boolean(false)},
            {DefaultMutableChannel.class.getName(), "channel", new Boolean(false)},
            {DefaultMutableConcept.class.getName(), "concept", new Boolean(true)},
            {DefaultMutableConcept.class.getName(), "scorecard", new Boolean(true)}, // DUH!
            {DefaultMutableEvent.class.getName(), "event", new Boolean(true)},
            {DefaultMutableEvent.class.getName(), "time", new Boolean(true)},         // DUH!
            {DefaultMutableStandaloneStateMachine.class.getName(), "standalonestatemachine", new Boolean(false)},
    };


    public OntologyResourceProviderImpl(P _project) {
        project = _project;
        ontology = new DefaultMutableOntology(this.project);
    }


    public void init() throws Exception {
        ModelFunctionsFactory.loadOntology((MutableOntology) ontology);
    }


    public boolean supportsResource(String streamName) {
        String ext = streamName.substring(streamName.lastIndexOf(".") + 1);
        return (getIndexForSuffix(ext) != -1);
    }


    public int deserializeResource(String URI, InputStream is, Project project, VFileStream stream) throws Exception {
        String suffix = URI.substring(URI.lastIndexOf(".") + 1);
        int idx = getIndexForSuffix(suffix);
        if (idx == -1) return ResourceProvider.FAILED_CONTINUE;

        Entity e = DefaultMutableOntology.deserializeEntity(is);
        if (project != null) {
            Boolean b = (Boolean) vFileClasses2Ext[idx][2];
            if (b.booleanValue()) {
                is.reset();
                final EntityInputSource source = new EntityInputSource(e);
                //final InputSource source = new InputSource(is);
                source.setSystemId(URI);
                project.getTnsCache().documentAddedOrChanged(source);
            }
        }
        if (e != null) {
            ((MutableEntity) e).setOntology((MutableOntology) ontology);
        }
        return ResourceProvider.SUCCESS_STOP;
    }


    public int deserializeResource(String uri, XiNode entityNode, P project) throws Exception {
        MutableEntity e = DefaultMutableOntology.createEntityFromNode(entityNode);
        e.setOntology((MutableOntology) ontology);
        int ii = getIndexForClassName(e.getClass().getName());
        if (ii == -1) return ResourceProvider.FAILED_CONTINUE;

        if (project != null) {
            Boolean b = (Boolean) vFileClasses2Ext[ii][2];
            if (b.booleanValue()) {
                EntityInputSource source = new EntityInputSource(e);
                source.setSystemId(uri + e.getFullPath() + "." + vFileClasses2Ext[ii][1]);
                project.getTnsCache().documentAddedOrChanged(source);
            }
        }
        return ResourceProvider.SUCCESS_STOP;
    }


    protected int getIndexForSuffix(String suffix) {
        for (int ii = 0; ii < vFileClasses2Ext.length; ii++) {
            String s = (String) vFileClasses2Ext[ii][1];
            if (suffix.toUpperCase().endsWith(s.toUpperCase())) return ii;
        }
        return -1;
    }


    public int getIndexForClassName(String className) {
        for (int ii = 0; ii < vFileClasses2Ext.length; ii++) {
            String s = (String) vFileClasses2Ext[ii][0];
            if (className.equals(s)) return ii;
        }
        return -1;
    }


    public Ontology getOntology() {
        return ontology;
    }

    //TODO - Large file handling to be done.


    public static InputStream getInputStream(Entity e) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        e.serialize(bos);
        return new ByteArrayInputStream(bos.toByteArray());
    }


    public static String getExtension(Entity e) {
        if (null == e) {
            return "";
        }
        if (e instanceof Concept) {
            if (((Concept) e).isAScorecard()) {
                return ".scorecard";
            }
            return ".concept";
        }
        if (e instanceof Event) {
            if (((Event) e).getType() == Event.TIME_EVENT) {
                return ".time";
            }
            return ".event";
        }
        for (int i = 0; i < vFileClasses2Ext.length; i++) {
            final String className = (String) vFileClasses2Ext[i][0];
            if (className.equals(e.getClass().getName())) {
                return "." + vFileClasses2Ext[i][1];
            }
        }
        return "";
    }
}
