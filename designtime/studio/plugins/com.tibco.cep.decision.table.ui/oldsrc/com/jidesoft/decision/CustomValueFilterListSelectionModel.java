/*
 * @(#)CustomValueFilterListSelectionModel.java 10/13/2008
 *
 * Copyright 2002 - 2008 JIDE Software Inc. All rights reserved.
 *
 */

package com.jidesoft.decision;

import javax.swing.ListModel;

import com.jidesoft.swing.CheckBoxListSelectionModel;

class CustomValueFilterListSelectionModel extends CheckBoxListSelectionModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7432934274768493264L;

	public CustomValueFilterListSelectionModel() {
    }

    public CustomValueFilterListSelectionModel(ListModel model) {
        super(model);
    }

    @Override
    public boolean isSelectedIndex(int index) {
        if (index == 1) {
            return super.isSelectedIndex(1);
        }
        return super.isSelectedIndex(0) || super.isSelectedIndex(index);
    }

    // implements javax.swing.ListSelectionModel
    @Override
    public void setSelectionInterval(int index0, int index1) {
        if (index0 == 1 || index1 == 1) {
            super.clearSelection();
            super.setSelectionInterval(1, 1);
        }
        else if (!selectAll(index0, index1)) {
            super.setSelectionInterval(index0, index1);
            selectAllIf();
        }
    }

    private void selectAllIf() {
        for (int i = 2; i < getModel().getSize(); i++) {
            if (!isSelectedIndex(i)) {
                return;
            }
        }
        super.addSelectionInterval(0, 0);
    }

    @Override
    public int getMinSelectionIndex() {
        if (super.isSelectedIndex(0)) {
            return 0;
        }
        int index = super.getMinSelectionIndex();
        return index == 0 ? 1 : index;
    }

    @Override
    public int getMaxSelectionIndex() {
        if (super.isSelectedIndex(0)) {
            return 0;
        }
        return super.getMaxSelectionIndex();
    }

    private boolean selectAll(int index0, int index1) {
        if ((index0 == 0 && index1 != getModel().getSize() - 1)
                || (index1 == 0 && index0 != getModel().getSize() - 1)) {
            super.setSelectionInterval(2, getModel().getSize() - 1);
            super.addSelectionInterval(0, 0);
            return true;
        }
        else {
            return false;
        }
    }

    private boolean unselectAll(int index0, int index1) {
        if (index0 == 1 || index1 == 1) {
            super.removeSelectionInterval(index0, index1);
            return true;
        }
        else if (index0 == 0 || index1 == 0) {
            clearSelection();
            return true;
        }
        else {
            return false;
        }
    }

    // implements javax.swing.ListSelectionModel
    @Override
    public void addSelectionInterval(int index0, int index1) {
        if (index0 == 1 || index1 == 1) {
            super.clearSelection();
            super.addSelectionInterval(1, 1);
        }
        else {
            if (!selectAll(index0, index1)) {
                removeSelectionInterval(1, 1);
                super.addSelectionInterval(index0, index1);
                selectAllIf();
            }
        }
    }

    // implements javax.swing.ListSelectionModel
    @Override
    public void removeSelectionInterval(int index0, int index1) {
        if (index0 == 1 || index1 == 1) {
            super.removeSelectionInterval(1, 1);
        }
        else {
            if (!unselectAll(index0, index1)) {
                super.removeSelectionInterval(0, 0);
                super.removeSelectionInterval(index0, index1);
            }
        }
    }
}