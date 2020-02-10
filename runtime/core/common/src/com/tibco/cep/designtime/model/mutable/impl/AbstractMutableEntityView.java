/**
 * User: ishaan
 * Date: Apr 23, 2004
 * Time: 6:30:47 PM
 */
package com.tibco.cep.designtime.model.mutable.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.TreeMap;

import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.EntityReference;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.mutable.MutableEntityReference;
import com.tibco.cep.designtime.model.mutable.MutableEntityView;
import com.tibco.cep.designtime.model.mutable.MutableOntology;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public abstract class AbstractMutableEntityView extends AbstractMutableEntity implements MutableEntityView {


    public static final String REFERENCE_EXISTS_KEY = "AbstractEntityView.addEntityReference.error";

    protected LinkedHashMap m_references;


    public AbstractMutableEntityView(DefaultMutableOntology ontology, DefaultMutableFolder folder, String name) {
        super(ontology, folder, name);
        m_references = new LinkedHashMap();
    }


    public void setOntology(MutableOntology ontology) {
        Iterator it = m_references.values().iterator();
        while (it.hasNext()) {
            AbstractMutableEntityReference ref = (AbstractMutableEntityReference) it.next();
            ref.setOntology(ontology);
        }

        super.setOntology(ontology);
    }


    public Collection getReferences() {
        return new ArrayList(m_references.values());
    }


    public Collection getReferencedPaths() {
        return new ArrayList(m_references.keySet());
    }


    public boolean containsReferenceTo(Entity entity) {
        if (entity == null) {
            return false;
        }
        return containsReferenceTo(entity.getFullPath());
    }


    public boolean containsReferenceTo(String entityPath) {
        return m_references.containsKey(entityPath);
    }


    public void removeReference(String path) {
        Object o = m_references.remove(path);
        if (o != null) {
            notifyListeners();
            notifyOntologyOnChange();
        }
    }


    public Collection getLinks() throws ModelException {
        LinkedHashSet allLinks = new LinkedHashSet();

        Iterator it = m_references.values().iterator();
        while (it.hasNext()) {
            EntityReference entityReference = (EntityReference) it.next();
            Collection refLinks = entityReference.getLinks();
            allLinks.addAll(refLinks);
        }

        return allLinks;
    }


    public Collection refreshLinks() throws ModelException {
        LinkedHashSet allLinks = new LinkedHashSet();

        Iterator it = m_references.values().iterator();
        while (it.hasNext()) {
            MutableEntityReference entityReference = (MutableEntityReference) it.next();
            Collection refLinks = entityReference.refreshLinks();
            allLinks.addAll(refLinks);
        }

        if (!allLinks.isEmpty()) {
            notifyListeners();
            notifyOntologyOnChange();
        }

        return allLinks;
    }


    public EntityReference getReference(String fullPath) {
        return (EntityReference) m_references.get(fullPath);
    }


    public void addEntityReference(EntityReference reference) throws ModelException {
        if (reference == null) {
            return;
        }
        if (containsReferenceTo(reference)) {
            BEModelBundle bundle = BEModelBundle.getBundle();
            String msg = bundle.formatString(REFERENCE_EXISTS_KEY, getFullPath(), reference.getEntityPath());
            throw new ModelException(msg);
        }

        MutableEntityView oldView = (MutableEntityView) reference.getView();
        if (oldView != null) {
            oldView.removeReference(reference.getEntityPath());
        }

        m_references.put(reference.getEntityPath(), reference);
        ((AbstractMutableEntityReference) reference).m_view = this;

        notifyListeners();
        notifyOntologyOnChange();
    }


    public XiNode toXiNode(XiFactory factory, String type) {
        XiNode root = factory.createElement(ExpandedName.makeName(type));
        root.setAttributeStringValue(ExpandedName.makeName("name"), m_name);
        root.setAttributeStringValue(ExpandedName.makeName("description"), m_description);
        root.setAttributeStringValue(ExpandedName.makeName("folder"), m_folder.toString());

        /* We store the EntityReferences first because they are required by the EntityLinks for loading */
        XiNode refs = root.appendElement(ExpandedName.makeName("references"));
        for (Iterator refIt = new TreeMap(m_references).values().iterator(); refIt.hasNext(); ) {
            AbstractMutableEntityReference ref = (AbstractMutableEntityReference) refIt.next();
            refs.appendChild(ref.toXiNode(factory));
        }

        try {
            XiNode linksNode = root.appendElement(ExpandedName.makeName("links"));
            Collection links = getLinks();
            final TreeMap sortedLinks = new TreeMap();
            for (Iterator it = links.iterator(); it.hasNext(); ) {
                final AbstractMutableEntityLink link = (AbstractMutableEntityLink) it.next();
                sortedLinks.put(
                        link.getFrom().getEntityPath() + " -> " + link.getTo().getEntityPath() + " : " + link.getName(),
                        link);
            }
            for (Iterator linkIt = sortedLinks.values().iterator(); linkIt.hasNext(); ) {
                AbstractMutableEntityLink link = (AbstractMutableEntityLink) linkIt.next();
                linksNode.appendChild(link.toXiNode(factory));
            }
        } catch (ModelException e) {
            e.printStackTrace();
        }


        return root;
    }


    public void delete() {
        super.delete();

        AbstractMutableEntityReference[] refs = (AbstractMutableEntityReference[]) m_references.values().toArray(new AbstractMutableEntityReference[0]);
        for (int i = 0; i < refs.length; i++) {
            AbstractMutableEntityReference ref = refs[i];
            ref.delete();
        }

        m_references.clear();
        m_references = null;
    }
}
