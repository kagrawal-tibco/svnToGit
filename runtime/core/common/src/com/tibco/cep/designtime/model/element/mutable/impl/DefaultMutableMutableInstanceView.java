/**
 * User: ishaan
 * Date: Apr 24, 2004
 * Time: 9:36:12 PM
 */
package com.tibco.cep.designtime.model.element.mutable.impl;


import java.util.Iterator;

import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.mutable.MutableInstanceView;
import com.tibco.cep.designtime.model.mutable.MutableEntityReference;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntity;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntityView;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class DefaultMutableMutableInstanceView extends AbstractMutableEntityView implements MutableInstanceView {


    public DefaultMutableMutableInstanceView(DefaultMutableOntology ontology, DefaultMutableFolder folder, String name) {
        super(ontology, folder, name);
    }


    public void setName(String name, boolean renameOnConflict) throws ModelException {
        BEModelBundle bundle = BEModelBundle.getBundle();

        if (name == null || name.length() == 0) {
            String msg = bundle.getString(AbstractMutableEntity.EMPTY_NAME_KEY);
            throw new ModelException(msg);
        }
        if (name.equals(m_name)) {
            return;
        }

        String oldPath = getFullPath();
        super.setName(name, renameOnConflict);
        String newPath = getFullPath();

        /* Need to tell each Concept to which we refer about the name change */
        Iterator it = m_references.keySet().iterator();
        while (it.hasNext()) {
            String conceptPath = (String) it.next();
            DefaultMutableInstance di = (DefaultMutableInstance) m_ontology.getConcept(conceptPath);
            if (di != null) {
                di.m_views.remove(oldPath);
                di.m_views.add(newPath);
            }
        }

    }


    public void removeReference(String path) {
        DefaultMutableInstanceReference dir = (DefaultMutableInstanceReference) m_references.remove(path);
        DefaultMutableInstance di = (DefaultMutableInstance) dir.getInstance();
        if (di == null) {
            return;
        }
        di.m_views.remove(getFullPath());
    }


    public XiNode toXiNode(XiFactory factory) {
        XiNode root = super.toXiNode(factory, "instanceView");
        return root;
    }


    public boolean containsReferenceTo(String entityPath) {
        return m_references.containsKey(entityPath);
    }


    public MutableEntityReference createReference(String instancePath) throws ModelException {
        if (instancePath == null || instancePath.length() == 0 || containsReferenceTo(instancePath)) {
            throw new ModelException("bad.createInstanceReference");
        }
        DefaultMutableInstanceReference reference = new DefaultMutableInstanceReference(m_ontology, this, instancePath);

        DefaultMutableInstance di = (DefaultMutableInstance) reference.getInstance();
        di.m_views.add(getFullPath());

        m_references.put(instancePath, reference);
        return reference;
    }
}
