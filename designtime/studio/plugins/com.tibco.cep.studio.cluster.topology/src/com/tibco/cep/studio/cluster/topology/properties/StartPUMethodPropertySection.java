package com.tibco.cep.studio.cluster.topology.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.studio.cluster.topology.model.impl.HostResourceImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.StartPuMethodImpl;

/**
 * 
 * @author sasahoo
 *
 */
public class StartPUMethodPropertySection extends AbstractClusterTopologyPropertySection {
	
	private Button useHawkButton;
	private Button usePstoolsButton;
	private Button useSSHButton;

	private Text portText;
	
	private Composite composite;
	private Composite buttonsComposite;
	private ScrolledPageBook pageBook;

	public static final String USE_PSTOOLS_PAGE = "USE_PSTOOLS";
	public static final String USE_SSH_PAGE = "USE_SSH";
	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		this.propertySheetPage = (ClusterDiagramPropertySheetPage) tabbedPropertySheetPage;
		parent.setLayout(new GridLayout(2, false));
		buttonsComposite = getWidgetFactory().createComposite(parent);
		
		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 8;
		buttonsComposite.setLayout(layout);
		
		useHawkButton = getWidgetFactory().createButton(buttonsComposite, "Use Hawk", SWT.RADIO);
		usePstoolsButton = getWidgetFactory().createButton(buttonsComposite, "Use Pstools", SWT.RADIO);
		useSSHButton = getWidgetFactory().createButton(buttonsComposite, "Use SSH", SWT.RADIO);
		getWidgetFactory().createButton(buttonsComposite, "", SWT.NONE).setVisible(false); //for layout
		
		Section detailsSection = getWidgetFactory().createSection(parent,ExpandableComposite.NO_TITLE | ExpandableComposite.EXPANDED);
		composite = getWidgetFactory().createComposite(detailsSection);
		detailsSection.setClient(composite);
		GridData gd = new GridData(GridData.FILL_BOTH);
		detailsSection.setLayoutData(gd);
		composite.setLayout(new FillLayout());
		
		pageBook = getWidgetFactory().createPageBook(composite, SWT.NONE);
//		createPstoolsConfigPage(pageBook.createPage(USE_PSTOOLS_PAGE));
		createSSHConfigPage(pageBook.createPage(USE_SSH_PAGE));
		pageBook.showEmptyPage();
		
		useHawkButton.addSelectionListener(this);
		usePstoolsButton.addSelectionListener(this);
		useSSHButton.addSelectionListener(this);
	}
		
	/**
	 * @param toolkit
	 * @param configPage
	 */
	private void createSSHConfigPage(Composite configPage) {
		configPage.setLayout(new GridLayout(1, false));
		getWidgetFactory().createLabel(configPage, "",  SWT.NONE);//for layout
		Group  group = getWidgetFactory().createGroup(configPage, "Use SSH");
		group.setLayout(new GridLayout(2, false));
		getWidgetFactory().createLabel(group, "Port:",  SWT.NONE);
		portText = getWidgetFactory().createText(group, "22",  SWT.BORDER);
		GridData gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 500;
		portText.setLayoutData(gd);
		portText.addModifyListener(new ModifyListener(){
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			@Override
			public void modifyText(ModifyEvent e) {
				portText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
				portText.setToolTipText("");
				if (!isNumeric(portText.getText())) {
					portText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
					String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.global.var.entry", portText.getText(), "Port");
					portText.setToolTipText(problemMessage);
					return;
				}
				if (hostResource.getStartPuMethod().getSsh() != null 
						&& !portText.getText().equalsIgnoreCase(hostResource.getStartPuMethod().getSsh().getPort())) {
					hostResource.getStartPuMethod().getSsh().setPort(portText.getText());
					hostResource.notifyObservers();
				} 
			}});
	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		pageBook.showEmptyPage();
		
		if (tseNode != null) {
			if (tseNode.getUserObject() instanceof HostResourceImpl) {
				HostResourceImpl hrImpl = (HostResourceImpl) tseNode.getUserObject();
				StartPuMethodImpl startMethod = hrImpl.getStartPuMethod();
				if (startMethod.getHawk() != null) {
					useHawkButton.setSelection(true);
					usePstoolsButton.setSelection(false);
					useSSHButton.setSelection(false);
					pageBook.showEmptyPage();
				}
				else if (startMethod.getPstools() != null) {
					usePstoolsButton.setSelection(true);
					useHawkButton.setSelection(false);
					useSSHButton.setSelection(false);
					pageBook.showEmptyPage();
				}
				else if (startMethod.getSsh() != null) {
					useSSHButton.setSelection(true);
					usePstoolsButton.setSelection(false);
					useHawkButton.setSelection(false);
					pageBook.showPage(USE_SSH_PAGE);
					String port = startMethod.getSsh().getPort();
					if (port != null) {
						portText.setText(port);
						if (!isNumeric(port)) {
							portText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
							String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.global.var.entry", portText.getText(), "Port");
							portText.setToolTipText(problemMessage);
						}
					} else {
						portText.setText("");
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cluster.topology.properties.AbstractClusterTopologyPropertySection#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		try{
			hostResource.notifyObservers();
			StartPuMethodImpl startMethod = null;
			
			if (tseNode != null) {
				if (tseNode.getUserObject() instanceof HostResourceImpl) {
					HostResourceImpl hrImpl = (HostResourceImpl) tseNode.getUserObject();
					startMethod = hrImpl.getStartPuMethod();
				}
			}
			
			if (startMethod == null) {
				//ClusterTopologyPlugin.LOGGER.logError(null, "No Start Method Found");
			}
			
			if(e.getSource() == useHawkButton){
				if(useHawkButton.getSelection() == true){
					pageBook.showEmptyPage();
					startMethod.setHawk(getManager().getFactory().createHawkImpl(null));
					startMethod.setPstools(null);
					startMethod.setSsh(null);
				}
			}
			if(e.getSource() == usePstoolsButton){
				if (usePstoolsButton.isEnabled()) {
					if(usePstoolsButton.getSelection() == true){
						pageBook.showEmptyPage();
						startMethod.setPstools(getManager().getFactory().createPstoolsImpl(null));
						startMethod.setHawk(null);
						startMethod.setSsh(null);
					}					
				}
			}
			if(e.getSource() == useSSHButton){
				if (useSSHButton.isEnabled()) {
					if(useSSHButton.getSelection() == true){
						pageBook.showPage(USE_SSH_PAGE);
						startMethod.setSsh(getManager().getFactory().createSshImpl(null));
						startMethod.getSsh().setPort(portText.getText());
						startMethod.setHawk(null);
						startMethod.setPstools(null);
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}