package com.tibco.cep.decision.table.editors.dnd;

import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.decisionproject.ontology.AbstractResource;

/**
 * 
 * @author sasahoo
 * 
 */
public class DataTransferModel {
	private static IStructuredSelection selection;
	private static String parent;
//	private static StringBuilder sb = new StringBuilder("");

	public static Object[] getTransferData() {
		if (getSelection() != null) {
			return getSelection().toArray();
//			AbstractResource[] tobject = (AbstractResource[]) getSelection()
//					.toList().toArray(new AbstractResource[selection.size()]);
//			return tobject;
		}

		return null;
	}
    public static String getFullPath(AbstractResource object,
			StringBuilder strBuilder) {
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

	public static String getParent() {
		return parent;
	}

	public static void setParent(String parent) {
		DataTransferModel.parent = parent;
	}

	public static IStructuredSelection getSelection() {
		return selection;
	}

	public static void setSelection(IStructuredSelection _selection) {
		selection = _selection;

	}
}
