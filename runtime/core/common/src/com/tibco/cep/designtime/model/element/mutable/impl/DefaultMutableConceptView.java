/**
 * User: ishaan
 * Date: Apr 26, 2004
 * Time: 9:17:11 AM
 */
package com.tibco.cep.designtime.model.element.mutable.impl;


import java.util.Iterator;

import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.mutable.MutableConceptView;
import com.tibco.cep.designtime.model.mutable.MutableEntityReference;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntity;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntityView;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class DefaultMutableConceptView extends AbstractMutableEntityView implements MutableConceptView {


    public DefaultMutableConceptView(DefaultMutableOntology ontology, DefaultMutableFolder folder, String name) {
        super(ontology, folder, name);
    }


    public void removeReference(String path) {
        /** Need to unregister this ConceptView from the Concept. */
        DefaultMutableConceptReference dcr = (DefaultMutableConceptReference) m_references.remove(path);
        if (dcr == null) {
            return;
        }

        DefaultMutableConcept dc = (DefaultMutableConcept) dcr.getConcept();
        if (dc == null) {
            return;
        }

        String fullPath = getFullPath();
        boolean exists = dc.removeView(fullPath);

        if (exists) {
            notifyListeners();
            notifyOntologyOnChange();
        }
    }


    public void setName(String name, boolean renameOnConflict) throws ModelException {
        if (name == null || name.length() == 0) {
            BEModelBundle bundle = BEModelBundle.getBundle();
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
        if (m_ontology == null) {
            return;
        }
        Iterator it = m_references.keySet().iterator();
        while (it.hasNext()) {
            String conceptPath = (String) it.next();
            DefaultMutableConcept dc = (DefaultMutableConcept) m_ontology.getConcept(conceptPath);
            if (dc != null) {
                dc.modifyView(oldPath, newPath);
            }
        }
    }


    public MutableEntityReference createReference(String conceptPath) throws ModelException {
        if (conceptPath == null || conceptPath.length() == 0 || containsReferenceTo(conceptPath)) {
            throw new ModelException("bad.createConceptReference");
        }
        DefaultMutableConceptReference reference = new DefaultMutableConceptReference(m_ontology, this, conceptPath);

        DefaultMutableConcept dc = (DefaultMutableConcept) reference.getConcept();
        String fullPath = getFullPath();
        dc.addView(fullPath);

        m_references.put(conceptPath, reference);

        notifyListeners();
        notifyOntologyOnChange();
        return reference;
    }


    public XiNode toXiNode(XiFactory factory) {
        XiNode root = super.toXiNode(factory, "conceptView");
        return root;
    }
}
