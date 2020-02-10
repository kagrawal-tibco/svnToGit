package com.tibco.cep.repo.mutable.provider;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.EntityChangeListener;
import com.tibco.cep.designtime.model.EntityInputSource;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.mutable.MutableEntity;
import com.tibco.cep.designtime.model.mutable.MutableOntology;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.mutable.MutableBEProject;
import com.tibco.cep.repo.mutable.MutableResourceProvider;
import com.tibco.cep.repo.mutable.ResourceChangeNotificationEvent;
import com.tibco.cep.repo.provider.impl.OntologyResourceProviderImpl;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 1, 2006
 * Time: 10:45:38 AM
 * To change this template use File | Settings | File Templates.
 * @deprecated
 */
public class MutableOntologyResourceProvider extends OntologyResourceProviderImpl<MutableBEProject> implements MutableResourceProvider,  EntityChangeListener {

    private ArrayList entityModificationList;

//    private MutableBEProject project;
    public MutableOntologyResourceProvider(MutableBEProject project) {
    	super(project);
//        this.project = project;
        entityModificationList = new ArrayList();
    }

    public void init() throws Exception {
        super.init();
        ((MutableOntology) ontology).addEntityChangeListener(this);
    }

    public void entityChanged(MutableEntity entity) {

        if (!isEntityModifiedInList(entity)) {
            OntologyResourceChangeEvent evt = new OntologyResourceChangeEvent(entity, Project.ENTITY_UPDATE, null);
            entityModificationList.add(evt);
        }
        documentAddedOrChanged(entity);
    }



    public void entityAdded(MutableEntity e) {
        if ((e instanceof Folder) && (e.getName() == "/")){
            return;
        }

        OntologyResourceChangeEvent evt = new OntologyResourceChangeEvent(e, Project.ENTITY_ADDED, null);
        entityModificationList.add(evt);
        documentAddedOrChanged(e);
    }

    public void entityRemoved(MutableEntity e) {
        OntologyResourceChangeEvent evt = new OntologyResourceChangeEvent(e, Project.ENTITY_DELETE, null);
        entityModificationList.add(evt);
    }

    public void entityRenamed(MutableEntity e, String oldname) {
        OntologyResourceChangeEvent evt = new OntologyResourceChangeEvent(e, Project.ENTITY_RENAME, e.getFolderPath()+oldname);
        entityModificationList.add(evt);
        documentAddedOrChanged(e);
    }

    public void entityMoved(MutableEntity e, String srcPath) {
        OntologyResourceChangeEvent evt = new OntologyResourceChangeEvent(e, Project.ENTITY_MOVED, srcPath + e.getName());
        entityModificationList.add(evt);
        documentAddedOrChanged(e);
    }

    private boolean isEntityModifiedInList(Entity e) {
        for (Iterator itr = entityModificationList.iterator(); itr.hasNext(); ) {
            OntologyResourceChangeEvent evt = (OntologyResourceChangeEvent) itr.next();
            if (evt.e == e) {
                return true;
            }
        }
        return false;
    }

    private void documentAddedOrChanged(MutableEntity e) {
        String uri = project.getName();
        if ((e instanceof Concept) || (e instanceof Event)) {
            EntityInputSource source = new EntityInputSource(e);
            int ii = getIndexForClassName(e.getClass().getName());
            source.setSystemId(uri +  e.getFullPath() + "." + vFileClasses2Ext[ii][1]);
            project.getTnsCache().documentAddedOrChanged(source);
        }
    }

    public List getChangeList() {
        return entityModificationList;
    }

    public boolean isDirty() {
        return entityModificationList.size() > 0;
    }

    public void clearDirty() {
        entityModificationList.clear();
    }




    class OntologyResourceChangeEvent implements ResourceChangeNotificationEvent {



        int code;
        MutableEntity e;
        String oldPath;
        String newPath;
        String newName;
        String ext;

        OntologyResourceChangeEvent (MutableEntity e, int code, String oldPath)
        {
            this.e = e;
            this.code = code;

            this.ext = OntologyResourceProviderImpl.getExtension(e);
            this.newName = e.getName();
            this.newPath = e.getFullPath();
            this.oldPath = oldPath == null ? this.newPath : oldPath;
        }

        public Object getEntity() {
            return e;
        }

        public String getExtension() {
            return ext;
        }

        public String getFullPath() {
            return newPath;
        }

        public InputStream getInputStream() throws Exception {

            return OntologyResourceProviderImpl.getInputStream(e);

        }

        public int getModificationCode() {
            return code;
        }

        public String getName() {
            return this.newName;
        }

        public String getOldPath() {
            return oldPath;
        }

    }

}
