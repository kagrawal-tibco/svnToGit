package com.tibco.cep.studio.cluster.topology.properties;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.studio.cluster.topology.utils.Messages;
import com.tibco.cep.studio.cluster.topology.wizards.ResourceSelector;

/**
 * 
 * @author ggrigore
 *
 */
public class ClusterDiagramTargetFilesPropertySection extends AbstractClusterTopologyPropertySection {
	
	protected Text nameText;
	protected Text cddText;
	protected Text earText;
    protected Composite composite;

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));

		
		getWidgetFactory().createLabel(composite, Messages.getString("machineunit.name"),  SWT.NONE);
		nameText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		nameText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				
			}});
		GridData gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		nameText.setLayoutData(gd);
		
		getWidgetFactory().createLabel(composite, Messages.getString("targetFiles.cdd"),  SWT.NONE);
		Composite browseComposite = getWidgetFactory().createComposite(composite);
		GridLayout layout = new GridLayout(2,false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		browseComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		browseComposite.setLayoutData(gd);
		
		cddText = getWidgetFactory().createText(browseComposite,"",  SWT.BORDER);
		cddText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				
			}});
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 617;
		cddText.setLayoutData(gd);
		Button cddBrowseButton = new Button(browseComposite, SWT.NONE);
		cddBrowseButton.setText(Messages.getString("Browse"));
		cddBrowseButton.addSelectionListener(new SelectionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				Set<String> extn = new HashSet<String>();
				extn.add("cdd");
				ResourceSelector picker = new ResourceSelector(
						Display.getDefault().getActiveShell(),"Select CDD","Select CDD to be associated with",editor.getProject(),cddText.getText(),extn);
				if (picker.open() == Dialog.OK) {
					if (picker.getFirstResult() != null) {
						cddText.setText(picker.getFirstResult().toString());
					}
				}
			}
		});
		
		getWidgetFactory().createLabel(composite, Messages.getString("cluster.ear"),  SWT.NONE);
		browseComposite = getWidgetFactory().createComposite(composite);
		layout = new GridLayout(2,false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		browseComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		browseComposite.setLayoutData(gd);
		
		earText = getWidgetFactory().createText(browseComposite,"",  SWT.BORDER);
		earText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				
			}});
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 617;
		earText.setLayoutData(gd);
		
		Button earBrowseButton = new Button(browseComposite, SWT.NONE);
		earBrowseButton.setText(Messages.getString("Browse"));
		earBrowseButton.addSelectionListener(new SelectionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				Set<String> extn = new HashSet<String>();
				extn.add("ear");
				ResourceSelector picker = new ResourceSelector(
						Display.getDefault().getActiveShell(),"Select EAR","Select EAR to be associated with",editor.getProject(),earText.getText(),extn);
				if (picker.open() == Dialog.OK) {
					if (picker.getFirstResult() != null) {
						earText.setText(picker.getFirstResult().toString());
					}
				}
			}
		});
	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if (tseNode != null) {

		}
		if (tseEdge != null) { }
		if (tseGraph != null) { }
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		super.widgetSelected(e);
	}
}