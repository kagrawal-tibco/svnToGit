package com.tibco.cep.studio.dashboard.ui.viewers;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.Assertion;

/**
 * @
 *
 */
public class ElementCheckBoxCellModifier implements ICellModifier {

    private ElementCheckBoxSelectionViewer viewer = null;

    private LocalElement parentElement = null;

    private String elementType = null;

	private String defaultElementType;

    public ElementCheckBoxCellModifier(ElementCheckBoxSelectionViewer viewer, LocalElement parentElement, String elementType, String defaultElementType) {
        super();
        Assertion.isNull(viewer);
        this.viewer = viewer;

        Assertion.isNull(parentElement);
        this.parentElement = parentElement;

        Assertion.isNull(elementType);
        this.elementType = elementType;

        this.defaultElementType = defaultElementType;
    }

    final public boolean canModify(Object element, String property) {

        TableColumnInfo columnInfo = viewer.getColumnInfo(property);
    	if (!(TableColumnInfo.SELECT.equals(property) || TableColumnInfo.DEFAULT_SELECT.endsWith(property))) {
    		//selection on some other column
    		return false;
    	}
        return (columnInfo.isMultiSelect() || columnInfo.isSingleSelect());
    }

    final public void modify(Object element, String property, Object value) {
    	//are we processing a table item ?
    	if ((element instanceof TableItem) == false) {
    		return;
    	}
    	TableItem tableItem = (TableItem) element;
    	//which column did the user click on ?
    	boolean targetPropertyIsSelect = TableColumnInfo.SELECT.equals(property);
    	boolean targetPropertyIsDefaultSelect = TableColumnInfo.DEFAULT_SELECT.endsWith(property);
    	if (targetPropertyIsSelect == true) {
    		//user clicked on the select column ; that can be either single select or multi select
    		ElementCheckBoxWrapper localElementWrapper = (ElementCheckBoxWrapper) tableItem.getData();
    		TableColumnInfo columnInfo = viewer.getColumnInfo(property);
    		boolean elementChecked = value.toString().equalsIgnoreCase("true");
    		if (columnInfo.isSingleSelect() == true) {
    			//we are dealing with single select table
    			//remove previous selection
    			for (ElementCheckBoxWrapper wrapper : viewer.localElementWrappers) {
    				if (wrapper.isChecked() == true) {
    					//found the previous selected element
    					//uncheck it
    					wrapper.setChecked(false);
    					//remove the element from the parent element
    					parentElement.removeElement(elementType, wrapper.getName(), wrapper.getFolder());
    					break;
    				}
				}
    			if (elementChecked == true) {
	    			//select the current selection
	    			localElementWrapper.setChecked(true);
	    			//add the element to the parent element
	    			parentElement.addElement(elementType, localElementWrapper.getLocalElement());
    			}
    			else {
    				//the element has been unchecked, see if it is default checked
    				if (localElementWrapper.isDefaultChecked() == true) {
    					//yes, it is, we need to remove it
    					localElementWrapper.setDefaultChecked(false);
    					if (defaultElementType != null) {
							parentElement.removeElement(defaultElementType, localElementWrapper.getName(), localElementWrapper.getFolder());
						}
    				}
    			}
    			//refresh the viewer
    			viewer.refresh();
    			//update actions
    			viewer.updateActions();

    		}
    		else if (columnInfo.isMultiSelect() == true) {
    			//we are dealing with multi select
			    localElementWrapper.setChecked(elementChecked);
			    if (true == elementChecked) {
			        parentElement.addElement(elementType, localElementWrapper.getLocalElement());
			    }
			    else {
			        parentElement.removeElement(elementType, localElementWrapper.getName(), localElementWrapper.getFolder());
    				//the element has been unchecked, see if it is default checked
    				if (localElementWrapper.isDefaultChecked() == true) {
    					//yes, it is, we need to remove it
    					localElementWrapper.setDefaultChecked(false);
    					if (defaultElementType != null) {
							parentElement.removeElement(defaultElementType, localElementWrapper.getName(), localElementWrapper.getFolder());
						}
    				}
			    }
			    //refresh the viewer
			    viewer.refresh();
    			//update actions
    			viewer.updateActions();
    		}
    	}
    	else if (targetPropertyIsDefaultSelect == true){
    		ElementCheckBoxWrapper localElementWrapper = (ElementCheckBoxWrapper) tableItem.getData();
    		boolean elementChecked = value.toString().equalsIgnoreCase("true");
    		TableColumnInfo columnInfo = viewer.getColumnInfo(property);
    		if (elementChecked == true) {
				if (columnInfo.isSingleSelect() == true) {
					//get existing default selection
					ElementCheckBoxWrapper defaultSelectedElement = viewer.getDefaultSelectedElement();
					if (defaultSelectedElement != null) {
						//we have an existing default selection, uncheck it
						defaultSelectedElement.setDefaultChecked(false);
						//remove it from the parent element
						parentElement.removeElement(defaultElementType, defaultSelectedElement.getName(), defaultSelectedElement.getFolder());
					}
					//select the item is not selected
					if (localElementWrapper.isChecked() == false) {
						localElementWrapper.setChecked(true);
						parentElement.addElement(elementType, localElementWrapper.getLocalElement());
					}
					//check the new selection as the default
					localElementWrapper.setDefaultChecked(elementChecked);
					//add the new selection to the parent element
					parentElement.addElement(defaultElementType, localElementWrapper.getLocalElement());
					//refresh the viewer
					viewer.refresh();
	    			//update actions
	    			viewer.updateActions();
				} else if (columnInfo.isMultiSelect() == true) {
					//do nothing , not possible
				}
			} else {
    			localElementWrapper.setDefaultChecked(false);
				if (defaultElementType != null) {
					parentElement.removeElement(defaultElementType, localElementWrapper.getName(), localElementWrapper.getFolder());
				}
				//refresh the viewer
				viewer.refresh();
    			//update actions
    			viewer.updateActions();
    		}

    	}
    }

    public Object getValue(Object element, String property) {
        if (element instanceof ElementCheckBoxWrapper) {
            ElementCheckBoxWrapper localElementWrapper = (ElementCheckBoxWrapper) element;
            if (property.equals(TableColumnInfo.SELECT) == true) {
            	return new Boolean(localElementWrapper.isChecked());
            }
            if (property.equals(TableColumnInfo.DEFAULT_SELECT) == true) {
            	return new Boolean(localElementWrapper.isDefaultChecked());
            }
        }
        return "";
    }

    public void setParentElement(LocalElement parentElement) {
		this.parentElement = parentElement;
	}
}
