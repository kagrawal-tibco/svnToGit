package com.tibco.cep.studio.ui.dialog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.TwoPaneElementSelector;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.StudioElementCollector;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.ui.providers.GeneralIndexLabelProvider;
import com.tibco.cep.studio.ui.providers.SpecificIndexLabelProvider;
import com.tibco.cep.studio.ui.util.Messages;

public class OpenStudioElementDialog extends TwoPaneElementSelector {

	private boolean fIncludeLocalVars = false;

	public OpenStudioElementDialog(Shell parent, String filter, boolean includeLocalVars) {
		super(parent, new GeneralIndexLabelProvider(), new SpecificIndexLabelProvider());
        setTitle(Messages.getString("open.studio.element.dialog.title"));
        setMessage(Messages.getString("open.studio.element.dialog.message"));
        if (filter != null) {
            setFilter(filter);
        }
        this.fIncludeLocalVars  = includeLocalVars;
	}

	public OpenStudioElementDialog(Shell parent, boolean includeLocalVars) {
		this(parent, null, includeLocalVars);
	}

	@Override
	public Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		final Button button = new Button(composite, SWT.CHECK);
		button.setText("Include local variables");
		button.setSelection(fIncludeLocalVars);
		button.addSelectionListener(new SelectionListener() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (button.getSelection()) {
					updateElements(true);
				} else {
					updateElements(false);
				}
			}
		
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		return composite;
	}

	protected void updateElements(boolean includeLocalVars) {
		Collection<DesignerProject> allIndexes = StudioCorePlugin.getDesignerModelManager().getAllIndexes();
		List<EObject> allElements = new ArrayList<EObject>();
		StudioElementCollector collector = new StudioElementCollector(allElements, includeLocalVars);
		for (DesignerProject designerProject : allIndexes) {
			designerProject.accept(collector);
		}
		List<EObject> elements = collector.getElements();
		setListElements(elements.toArray());
	}
	
	
}
