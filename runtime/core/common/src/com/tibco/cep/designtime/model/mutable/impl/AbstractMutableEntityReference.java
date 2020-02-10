/**
 * User: ishaan
 * Date: Apr 23, 2004
 * Time: 6:17:30 PM
 */
package com.tibco.cep.designtime.model.mutable.impl;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.EntityLink;
import com.tibco.cep.designtime.model.EntityReference;
import com.tibco.cep.designtime.model.EntityView;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.mutable.MutableEntityReference;
import com.tibco.cep.designtime.model.mutable.MutableFolder;
import com.tibco.cep.designtime.model.mutable.MutableOntology;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;


public abstract class AbstractMutableEntityReference extends AbstractMutableEntity implements MutableEntityReference {


    public static final String PATH_EXISTS_KEY = "AbstractEntityReference.setEntityPath.pathExists";

    protected Point m_point;
    protected String m_entityPath;
    protected AbstractMutableEntityView m_view;
    protected LinkedHashMap m_links;


    public AbstractMutableEntityReference(DefaultMutableOntology ontology, AbstractMutableEntityView view, String entityPath) {
        super(ontology, null, entityPath.substring(entityPath.indexOf(Folder.FOLDER_SEPARATOR_CHAR), entityPath.length()));
        m_point = new Point();
        m_view = view;
        m_entityPath = entityPath;
        m_links = null;
    }


    public void setOntology(MutableOntology ontology) {
        super.setOntology(ontology);
        if (m_links == null) {
            return;
        }

        Iterator it = m_links.values().iterator();
        while (it.hasNext()) {
            AbstractMutableEntityLink link = (AbstractMutableEntityLink) it.next();
            link.setOntology(ontology);
        }
    }


    protected String getKeyForLink(AbstractMutableEntityLink link) {
        if (link == null) {
            return null;
        }
        return link.getLabel();
    }


    protected boolean containsLink(AbstractMutableEntityLink link) {
        if (link == null || m_links == null) {
            return false;
        }
        return m_links.containsKey(getKeyForLink(link));
    }


    public void removeLink(EntityLink link) {
        if (m_links == null || link == null) {
            return;
        }
        if (m_links.containsValue(link)) {
            m_links.remove(getKeyForLink((AbstractMutableEntityLink) link));
            notifyListeners();
            notifyOntologyOnChange();
        }
    }


    public Point getLocation() {
        return m_point;
    }


    public Entity getEntity() {
        if (m_ontology == null) {
            return null;
        }
        return m_ontology.getEntity(m_entityPath);
    }


    public void setLocation(Point point) {
        m_point = point;
        notifyListeners();
        notifyOntologyOnChange();
    }


    public String getEntityPath() {
        return m_entityPath;
    }


    public void setEntityPath(String path) throws ModelException {
        if (m_entityPath.equals(path)) {
            return;
        } /** Ignore duplicate set */

        if (m_view.containsReferenceTo(path)) {
            BEModelBundle bundle = BEModelBundle.getBundle();
            String error = bundle.formatString(PATH_EXISTS_KEY, path);
            throw new ModelException(error);
        }
        m_view.removeReference(m_entityPath);

        /** Setting the path to a null or empty string, removes the reference */
        if (!ModelUtils.IsEmptyString(path)) {
            m_entityPath = path;
            m_view.addEntityReference(this);
        }

        notifyListeners();
        notifyOntologyOnChange();
    }


    public EntityView getView() {
        return m_view;
    }


    public Collection getLinks() throws ModelException {
        if (m_links != null) {
            return m_links.values();
        } else {
            return refreshLinks();
        }
    }


    public String getFullPath() {
        return "";
    }


    public Collection refreshLinks() throws ModelException {
//        if (m_links == null) {
//            m_links = new LinkedHashMap();
//        } else {
//            Iterator it = m_links.values().iterator();
//            while (it.hasNext()) {
//                EntityLink link = (EntityLink) it.next();
//                if ((link.getType() == EntityLink.INHERITANCE_TYPE) && !link.isValid(false)) {
//                    it.remove();
//                }
//            }
//        }
//
//        notifyListeners();
//        notifyOntologyOnChange();
//        return m_links.values();
    	return null;
    }


    /* NO-OP */
    public void setFolder(MutableFolder folder) throws ModelException {
    }


    public XiNode toXiNode(XiNode root) {
        root.setAttributeStringValue(ExpandedName.makeName("entityPath"), m_entityPath);
        root.setAttributeStringValue(ExpandedName.makeName("x"), String.valueOf(m_point.x));
        root.setAttributeStringValue(ExpandedName.makeName("y"), String.valueOf(m_point.y));

        return root;
    }


    /**
     * Finds a Link that matches the Entity specified.
     *
     * @param underlyingEntity A PropertyDefinition, PropertyInstance, or null for Inheritance.
     * @return The link, if found, or null otherwise.
     * @throws ModelException Thrown if there is an error while retrieving the Links.
     */
    public EntityLink getLink(Entity underlyingEntity) throws ModelException {
        Collection links = getLinks();
        Iterator it = links.iterator();
        while (it.hasNext()) {
            EntityLink link = (EntityLink) it.next();
            Entity linkEntity = link.getEntity();
            if (linkEntity == underlyingEntity) {
                return link;
            }
        }
        return null;
    }


    public Collection getLinksTo(EntityReference er) throws ModelException {
        ArrayList toLinks = new ArrayList();

        Collection links = getLinks();
        Iterator it = links.iterator();
        while (it.hasNext()) {
            EntityLink link = (EntityLink) it.next();
            if (link.getTo().equals(er)) {
                toLinks.add(link);
            }
        }

        return toLinks;
    }


    public void delete() {
        /** Remove this reference from its EntityView */
        m_view.removeReference(m_entityPath);

        /** Get rid of the links. */
        if (m_links != null) {
            m_links.clear();
        }

    }
}
