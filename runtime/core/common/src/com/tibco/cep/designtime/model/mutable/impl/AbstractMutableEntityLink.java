/**
 * User: ishaan
 * Date: Apr 26, 2004
 * Time: 10:53:45 AM
 */
package com.tibco.cep.designtime.model.mutable.impl;


import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.EntityReference;
import com.tibco.cep.designtime.model.EntityView;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.mutable.MutableEntityLink;
import com.tibco.cep.designtime.model.mutable.MutableFolder;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public abstract class AbstractMutableEntityLink extends AbstractMutableEntity implements MutableEntityLink {


    public static final String NULL_VIEW_OR_NODES_KEY = "AbstractEntityLink.create.nullViewOrNodes";


    protected int m_type;
    protected AbstractMutableEntityView m_view;
    protected AbstractMutableEntityReference m_from;
    protected AbstractMutableEntityReference m_to;


    public void setName(String name, boolean renameOnConflict) throws ModelException {
        if (name == null || name.trim().length() == 0) {
            throw new ModelException(AbstractMutableEntity.EMPTY_NAME_KEY);
        }
        if (name.equals(m_name)) {
            return;
        }
        m_name = name;
    }


    public AbstractMutableEntityLink(DefaultMutableOntology ontology, AbstractMutableEntityView view, AbstractMutableEntityReference from, AbstractMutableEntityReference to) throws ModelException {
        super(ontology, null, "");
        if (view == null || from == null || to == null) {
            BEModelBundle bundle = BEModelBundle.getBundle();
            String error = bundle.getString(NULL_VIEW_OR_NODES_KEY);
            throw new ModelException(error);
        }
        m_view = view;
        m_from = from;
        m_to = to;
    }


    public abstract XiNode toXiNode(XiFactory factory);


    /**
     * NO-OP
     */
    public void setFolder(MutableFolder folder) throws ModelException {
    }


    /**
     * NO-OP
     */
    public void setFolderPath(String fullPath) throws ModelException {
    }


    public String getFullPath() {
        return "";
    }


    public XiNode toXiNode(XiFactory factory, String type) {
        XiNode root = factory.createElement(ExpandedName.makeName(type));
        root.setAttributeStringValue(ExpandedName.makeName("name"), m_name);
        root.setAttributeStringValue(ExpandedName.makeName("from"), m_from.getEntityPath());
        root.setAttributeStringValue(ExpandedName.makeName("to"), m_to.getEntityPath());
        root.setAttributeStringValue(ExpandedName.makeName("type"), String.valueOf(m_type));
        return root;
    }


    public int getType() {
        return m_type;
    }


    public EntityView getView() {
        return m_view;
    }


    public EntityReference getFrom() {
        return m_from;
    }


    public void removeFromOwner() {
        if (m_from != null) {
            m_from.removeLink(this);
        }
    }


    public EntityReference getTo() {
        return m_to;
    }


    public void setFrom(EntityReference reference) {
        m_from = (AbstractMutableEntityReference) reference;
    }


    public void setTo(EntityReference reference) {
        m_to = (AbstractMutableEntityReference) reference;
    }


    public boolean isValid(boolean recurse) {
        if (m_from == null || m_to == null) {
            return false;
        }

        Entity fromEntity = m_from.getEntity();
        if (fromEntity == null) {
            return false;
        }

        Entity toEntity = m_to.getEntity();
        if (toEntity == null) {
            return false;
        }

        return true;
    }

}
