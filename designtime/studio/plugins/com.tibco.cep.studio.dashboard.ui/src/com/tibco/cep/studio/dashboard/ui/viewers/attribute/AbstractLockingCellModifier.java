package com.tibco.cep.studio.dashboard.ui.viewers.attribute;

import org.eclipse.jface.viewers.ICellModifier;

/**
 * @
 *  
 */
public abstract class AbstractLockingCellModifier implements ICellModifier {

    protected AttributeViewer attributeViewer;

    /**
     * Constructor
     * 
     * @param LocalMetricUserDefinedFieldView
     *            an instance of a AttributeViewer
     */
    public AbstractLockingCellModifier(AttributeViewer tableViewer) {
        super();
        this.attributeViewer = tableViewer;
    }

    public abstract boolean internalCanModify(Object element, String property);

    public abstract void internalModify(Object element, String property, Object value);

    final public boolean canModify(Object element, String property) {

        boolean result = internalCanModify(element, property);

        if (true == result) {
            if (true == attributeViewer.isEditToolbarSupported()) {
                attributeViewer.getToolBar().setLockAll(true);
                attributeViewer.getToolBar().setReadOnly(true);
            }
        }

        return result;
    }

    final public void modify(Object element, String property, Object value) {
        internalModify(element, property, value);
        if (true == attributeViewer.isEditToolbarSupported()) {
            attributeViewer.getToolBar().setLockAll(false);
        }
    }

}