package com.tibco.cep.studio.ui.compare.provider;

import java.util.Date;

import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.compare.model.AbstractResourceNode;
import com.tibco.cep.studio.ui.compare.model.AbstractTreeNode;
import com.tibco.cep.studio.ui.compare.model.EMFCompareNode;
import com.tibco.cep.studio.ui.compare.model.TableRuleVariableTreeNode;
import com.tibco.cep.studio.ui.compare.preferences.ComparePreferenceConstants;

public class ResourceDiffLabelProvider extends LabelProvider 
		implements ITableLabelProvider, ITableColorProvider, IPropertyChangeListener {

	private ResourceLabelProvider provider = new ResourceLabelProvider();
	private Color fAdditionColor;
	private Color fDeletionColor;
	private Color fChangedColor;
	private IPreferenceStore fPreferenceStore;
	
	public ResourceDiffLabelProvider() {
		super();
		fPreferenceStore = StudioUIPlugin.getDefault().getPreferenceStore();
		fPreferenceStore.addPropertyChangeListener(this);
	}

	public ResourceDiffLabelProvider(IPreferenceStore store) {
		super();
		fPreferenceStore = store;
		fPreferenceStore.addPropertyChangeListener(this);
	}
	
	public Image getColumnImage(Object element, int columnIndex) {
		return provider.getImage(getElement(element, columnIndex));
	}

	public String getColumnText(Object node, int columnIndex) {
		Object element = node;
		if (node instanceof DiffNode) {
			element = getElement(element, columnIndex);
		}
		String text = "";
		if (element instanceof AbstractTreeNode) {
			text = provider.getText(element);
		} else if (element instanceof AbstractResourceNode) {
			text = provider.getText(((AbstractResourceNode)element).getInput());
		} else if (element instanceof EObject) {
			text = provider.getText(element);
		} 
		if (columnIndex == 0 && node instanceof DiffNode) {
			if (element instanceof TableRuleVariableTreeNode) {
				TableRuleVariable var = (TableRuleVariable) ((TableRuleVariableTreeNode) element).getInput();
				return provider.getTableRuleVariableText((TableRuleVariableTreeNode) element, var);
			}
			text += getChangeType((DiffNode) node);
		}
		return text;
	}

	private Object getElement(Object element, int columnIndex) {
		DiffNode node = (DiffNode) element;
		switch (columnIndex) {
		case 0:
			Date leftModifiedOn = null;
			Date rightModifiedOn = null;
			ITypedElement leftNode = node.getLeft();
			ITypedElement rightNode = node.getRight();
			
			if (leftNode instanceof EMFCompareNode) {
				Object obj = ((EMFCompareNode)leftNode).getInput().getInput();
				if (obj instanceof Table) {
					Table tableImpl = (Table)obj;
					leftModifiedOn = tableImpl.getLastModified();
				}
			} 
			
			if (rightNode instanceof EMFCompareNode) {
				Object obj = ((EMFCompareNode)rightNode).getInput().getInput();
				if (obj instanceof Table) {
					Table tableImpl = (Table)obj;
					rightModifiedOn = tableImpl.getLastModified();
				}
			}

			if (leftModifiedOn != null &&
					rightModifiedOn != null) {
				if (leftModifiedOn.compareTo(rightModifiedOn) < 0) {
					element = ((EMFCompareNode)rightNode).getInput();
				} else {
					element = ((EMFCompareNode)leftNode).getInput();
				}
			}
			
			break;
				
		case 1:
			if (node.getLeft() instanceof EMFCompareNode) {
				element = ((EMFCompareNode)node.getLeft()).getInput();
			}
			break;
			
		case 2:
			if (node.getRight() instanceof EMFCompareNode) {
				element = ((EMFCompareNode)node.getRight()).getInput();
			}
			break;
			
		default:
			break;
		}
		return element;
	}

	private String getChangeType(DiffNode node) {
		String type = "";
		switch (node.getKind()) {
//		case Differencer.ADDITION:
		case Differencer.DELETION:
			type = "has been added"; // these seem to be backward
			break;

//		case Differencer.DELETION:
		case Differencer.ADDITION:
			type = "has been removed"; // these seem to be backward
			break;
			
		case Differencer.CHANGE:
			type = "has changed";
			break;
			
		default:
			break;
		}
		return " " + type;
	}

	public Color getBackground(Object element, int columnIndex) {
		if (element instanceof DiffNode) {
			DiffNode node = (DiffNode) element;
			switch (node.getKind()) {
//			case Differencer.ADDITION:
			case Differencer.DELETION:
				return getAdditionColor(); // these seem to be backward

//			case Differencer.DELETION:
			case Differencer.ADDITION:
				return getDeletionColor(); // these seem to be backward
				
			case Differencer.CHANGE:
				return getChangedColor();
				
			default:
				break;
			}
		}
		return null;
	}

	private Color getAdditionColor() {
		if (fAdditionColor == null) {
			String addedRGB = getPreferenceStore().getString(ComparePreferenceConstants.COMPARE_ADDED_COLOR);
			RGB rgb = StringConverter.asRGB(addedRGB);
			fAdditionColor = new Color(null, rgb);
		}
		return fAdditionColor;
	}

	private Color getDeletionColor() {
		if (fDeletionColor == null) {
			String removedRGB = getPreferenceStore().getString(ComparePreferenceConstants.COMPARE_REMOVED_COLOR);
			RGB rgb = StringConverter.asRGB(removedRGB);
			fDeletionColor = new Color(null, rgb);
		}
		return fDeletionColor;
	}
	
	private Color getChangedColor() {
		if (fChangedColor == null) {
			String changedRGB = getPreferenceStore().getString(ComparePreferenceConstants.COMPARE_CHANGED_COLOR);
			RGB rgb = StringConverter.asRGB(changedRGB);
			fChangedColor = new Color(null, rgb);
		}
		return fChangedColor;
	}
	
	private IPreferenceStore getPreferenceStore() {
		if (fPreferenceStore == null) {
			fPreferenceStore = StudioUIPlugin.getDefault().getPreferenceStore();
		}
		return fPreferenceStore;
	}

	public Color getForeground(Object element, int columnIndex) {
		return null;
	}

	public void propertyChange(PropertyChangeEvent event) {
		if (ComparePreferenceConstants.COMPARE_ADDED_COLOR.equals(event.getProperty())) {
			fAdditionColor = null;
			getAdditionColor();
		} else if (ComparePreferenceConstants.COMPARE_REMOVED_COLOR.equals(event.getProperty())) {
			fDeletionColor = null;
			getDeletionColor();
		} else if (ComparePreferenceConstants.COMPARE_CHANGED_COLOR.equals(event.getProperty())) {
			fChangedColor = null;
			getChangedColor();
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		if (fPreferenceStore != null) {
			fPreferenceStore.removePropertyChangeListener(this);
		}
	}
	
}
