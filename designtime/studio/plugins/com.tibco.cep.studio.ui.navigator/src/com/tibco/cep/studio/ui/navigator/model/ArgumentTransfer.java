package com.tibco.cep.studio.ui.navigator.model;

import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.decisionproject.ontology.AbstractResource;

/**
 * 
 * @author sasahoo
 * 
 */
public class ArgumentTransfer {

	private static IStructuredSelection selection;
	private static String parent;
	private static ArgumentTransfer dataTransferModel;
	
	private ArgumentTransfer(){
		//TODO
	}
	
	/**
	 * @return
	 */
	public static ArgumentTransfer getInstance(){
		if(dataTransferModel == null){
			dataTransferModel = new ArgumentTransfer();
		}
		return dataTransferModel;
	}
	
	public Object[] getTransferData() {
		if (getSelection() != null) {
			return getSelection().toArray();
		}
		return null;
	}
	
    /**
     * @param object
     * @param strBuilder
     * @return
     */
    public String getFullPath(AbstractResource object, StringBuilder strBuilder) {
		if (strBuilder == null) {
			strBuilder = new StringBuilder("");
		}
		if (object.eContainer() != null) {
			AbstractResource abs = (AbstractResource) object.eContainer();
			getFullPath(abs, strBuilder);
			strBuilder.append("/");
			strBuilder.append(((AbstractResource) object).getName());
		} else if (object.eContainer() == null) {
			AbstractResource abs = (AbstractResource) object;
			if (abs.getFolder() != null) {
				strBuilder.append(abs.getFolder());
			}
			strBuilder.append(abs.getName());
		}
		return strBuilder.toString();
	}

	/**
	 * @return
	 */
	public static String getParent() {
		return parent;
	}

	/**
	 * @param parent
	 */
	public void setParent(String parent) {
		ArgumentTransfer.parent = parent;
	}

	/**
	 * @return
	 */
	public IStructuredSelection getSelection() {
		return selection;
	}

	/**
	 * @param _selection
	 */
	public void setSelection(IStructuredSelection _selection) {
		selection = _selection;
	}
}