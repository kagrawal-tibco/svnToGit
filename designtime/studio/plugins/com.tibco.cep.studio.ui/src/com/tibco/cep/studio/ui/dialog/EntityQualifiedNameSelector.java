package com.tibco.cep.studio.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.SelectionStatusDialog;

import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.providers.GeneralIndexLabelProvider;
import com.tibco.cep.studio.ui.providers.QualifiedNameContentProvider;

public class EntityQualifiedNameSelector extends SelectionStatusDialog {

	private List<Symbol> scopeList;
	private TreeViewer treeViewer;
	private Object selectedObj;
	private String projectName;
	private GeneralIndexLabelProvider labelProvider;

	public EntityQualifiedNameSelector(Shell parent, String title, String message, String projectName, EList<Symbol> symbolList) {
		super(parent);
		setTitle(title);
		setMessage(message);
		this.projectName = projectName;
		this.scopeList = symbolList;
	}

	@Override
	protected void computeResult() {
		ISelection selection = treeViewer.getSelection();
		List<Object> selPath = new ArrayList<>();
		if (selection instanceof TreeSelection) {
			TreePath[] paths = ((TreeSelection) selection).getPaths();
			if (paths.length > 0) {
				TreePath treePath = paths[0];
				int segmentCount = treePath.getSegmentCount();
				for (int i = 0; i < segmentCount; i++) {
					Object segment = treePath.getSegment(i);
					selPath.add(segment);
				}
			}
			setResult(selPath);
		}
	}
	
	@Override
	public Object[] getResult() {
		return super.getResult();//new Object[] { getQualifiedName() };
	}

	public String getQualifiedName() {
		Object[] result = getResult();
		if (result.length == 0) {
			return "";
		}
		StringBuilder b = new StringBuilder();
		for (int i=0; i<result.length; i++) {
			Object obj = result[i];
			String text = labelProvider.getText(obj);
			b.append(text);
			if (i < result.length - 1) {
				b.append('.');
			}
		}
		return b.toString();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);

        // Create a message and tree view area
        Label messageLabel = createMessageArea(composite);
        treeViewer = createTreeViewer(composite);

        GridData data = new GridData(GridData.FILL_BOTH);
        data.widthHint = 350;
        data.heightHint = 400;
        
        Tree treeWidget = treeViewer.getTree();
        treeWidget.setLayoutData(data);
        treeWidget.setFont(parent.getFont());

        // If the tree is empty then disable the controls
        if (scopeList.isEmpty()) {
            messageLabel.setEnabled(false);
            treeWidget.setEnabled(false);

            updateStatus(new Status(Status.ERROR, StudioUIPlugin.PLUGIN_ID,
            		Status.OK/*Status.ERROR*/, "No entities in scope", null));

        }

        return composite;
	}

	private TreeViewer createTreeViewer(Composite composite) {
		TreeViewer qNameTreeViewer = new TreeViewer(composite);
		labelProvider = new GeneralIndexLabelProvider(projectName);
		qNameTreeViewer.setLabelProvider(labelProvider);
		qNameTreeViewer.setContentProvider(new QualifiedNameContentProvider(projectName));
		qNameTreeViewer.setInput(this.scopeList);
		return qNameTreeViewer;
	}

}
