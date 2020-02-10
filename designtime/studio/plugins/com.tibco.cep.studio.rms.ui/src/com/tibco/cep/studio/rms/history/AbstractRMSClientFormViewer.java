package com.tibco.cep.studio.rms.history;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.rms.client.ui.AbstractSashForm;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;
import com.tibco.cep.studio.ui.util.StudioImages;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractRMSClientFormViewer extends AbstractSashForm implements SelectionListener {
    
	protected Section revisionsSection;
	protected Section detailsSection;
	
	protected Table revisionTable;
	protected TableViewer revisionTableViewer;

	protected Table detailsTable;
	protected TableViewer detailsTableViewer;

	protected static final int[] defaultWeightPropertySections = new int[] { 195, 15 };  
	
	/**
	 * 
	 * @param form
	 * @param toolkit
	 */
	protected abstract void createGeneralPart(final ScrolledForm form,final FormToolkit toolkit);
    
    /**
     * 
     * @param managedForm
     * @param parent
     */
    protected  void createRevisionsPart(final IManagedForm managedForm, Composite parent){
    	//Override this
    }
    /**
     * 
     * @param managedForm
     * @param parent
     */
    protected  void createDetailsPart(final IManagedForm managedForm, Composite parent) {
    	//Override this
    }

	/**
	 * @param e
	 * @param isRevisionSection
	 */
	protected void adjustSections(ExpansionEvent e, boolean isRevisionSection) {
		if (isRevisionSection) {
			if (e.getState() == true && detailsSection.isExpanded()) {
				sashForm.setWeights(new int[] { 105,105 });//When both expanded
			} else if (e.getState() == true && !detailsSection.isExpanded()) {
				sashForm.setWeights(new int[] { 195,15 });
			} else {
				sashForm.setWeights(new int[] { 15,195 });
			}
		} else {
			if (e.getState() == true && revisionsSection.isExpanded()) {
				sashForm.setWeights(new int[] { 105, 105 });//When both expanded
			} else if (e.getState() == true && !revisionsSection.isExpanded()) {
				sashForm.setWeights(new int[] { 15, 195 });
			} else if (e.getState() == false && !revisionsSection.isExpanded()) {
				sashForm.setWeights(new int[] { 15, 195 });
			} else {
				sashForm.setWeights(new int[] { 195, 15 });
			}
		}
		getForm().reflow(true);
	}
	
    /**
     * 
     * @param container
     */
    protected void createPartControl(Composite container,String title, Image titleImage) {
    	
    	managedForm = new ManagedForm(container);
		final ScrolledForm form = managedForm.getForm();
		
		FormToolkit toolkit = managedForm.getToolkit();
		form.setText(title);
		if (titleImage != null) {
			form.setImage(titleImage);
		}
		form.setBackgroundImage(StudioImages.getImage(StudioImages.IMG_FORM_BG));
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 1;
		form.getBody().setLayout(layout);
		
		createGeneralPart(form, toolkit);
		
		sashForm = new MDSashForm(form.getBody(), SWT.VERTICAL);
		
		sashForm.setData("form", managedForm);
		toolkit.adapt(sashForm, false, false);
		sashForm.setMenu(form.getBody().getMenu());
		GridData gd = new GridData(GridData.FILL_BOTH);
//		gd.widthHint = 925;
		sashForm.setLayoutData(gd);
		
		createRevisionsPart(managedForm, sashForm);
		createDetailsPart(managedForm, sashForm);
		hookResizeListener();
		managedForm.initialize();
    }
   
    /**
     * @param title
     * @param titleImage
     */
    protected void updateTitle(String title) {
    	managedForm.getForm().setText(title);
    }
    
	public Control getControl() {
		return getForm();
	}

	/**
	 * Auto Table Layout based on no of columns
	 * @param table
	 * @param weight
	 */
	protected void autoTableLayout(Table table, Integer [] weight) {
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(table);
		for (int i = 0; i< table.getColumnCount(); i++) {
			autoTableLayout.addColumnData(new ColumnWeightData(weight[i]));
		}
		table.setLayout(autoTableLayout);
	}

	/**
	 * Context Menu for Revision/Details Table
	 * @param table
	 * @param actions
	 */
	protected void setContextMenu(Table table, Action[] actions) {
		MenuManager popupMenu = new MenuManager();
		//TODO possible fix needed here in the constructor
//		if (table == revisionTable) {
//			popupMenu.addMenuListener(new RevisionsPopupMenuListener(revisionTableViewer, false));
//		} else {
//			popupMenu.addMenuListener(new RevisionsPopupMenuListener(detailsTableViewer, true));
//		}
		popupMenu.addMenuListener(new RevisionsPopupMenuListener());
		for (Action action : actions) {
			if (action == null) {
				popupMenu.add(new Separator());
			} else {
				popupMenu.add(action);
			}
		}
		Menu menu = popupMenu.createContextMenu(table);
		table.setMenu(menu);
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
	}
	
	   /**
     * Defining Orientation Tool bar.
     */
	protected void createToolBarActions() {
		final ScrolledForm form = getForm();
		/*
		 * UnComment this when functionality implemented.
		 */
//		String[] items = new String [] {"50", "100", "200", "300"}; //TODO: populate with preferences later
//		final HistoryTraverseContributionItem historyTraverseContributionItem = new HistoryTraverseContributionItem(items, "50");
//
//		Action prevaction = new Action("prev", Action.AS_PUSH_BUTTON) {
//			public void run() {
//				String prevVal = historyTraverseContributionItem.getValue();
//				fetchPreviousRevisions(Integer.parseInt(prevVal));
//			}
//		};
//		prevaction.setToolTipText("Previous");
//		prevaction.setImageDescriptor(RMSPlugin.getImageDescriptor("icons/previous.gif"));
//		
//		Action nextAction = new Action("next", Action.AS_PUSH_BUTTON) {
//			public void run() {
//				String nextVal = historyTraverseContributionItem.getValue();
//				fetchNextRevisions(Integer.parseInt(nextVal));
//			}
//		};
//		nextAction.setToolTipText("Next");
//		nextAction.setImageDescriptor(RMSPlugin.getImageDescriptor("icons/next.gif"));
		
		Action refreshAction = new Action("Refresh", Action.AS_PUSH_BUTTON) {
			public void run() {
				refresh();
			}
		};
		refreshAction.setToolTipText("Refresh");
		refreshAction.setImageDescriptor(RMSUIPlugin.getImageDescriptor("icons/refresh_16x16.png"));
		
//		form.getToolBarManager().add(prevaction);
//		form.getToolBarManager().add(historyTraverseContributionItem);
//		form.getToolBarManager().add(nextAction);
		form.getToolBarManager().add(refreshAction);
		
		form.updateToolBar();
	}
	
	protected abstract void fetchNextRevisions(int limit);
	protected abstract void fetchPreviousRevisions(int limit);
	protected abstract void refresh();
}