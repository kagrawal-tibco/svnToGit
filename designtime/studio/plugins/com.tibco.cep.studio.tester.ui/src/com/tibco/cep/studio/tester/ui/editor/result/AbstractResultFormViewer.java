package com.tibco.cep.studio.tester.ui.editor.result;

import static com.tibco.cep.studio.tester.core.utils.TesterCoreUtils.getFullPath;
import static com.tibco.cep.studio.ui.navigator.util.ProjectExplorerUtils.linkToStudioExplorer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.tester.core.model.ExecutionObjectType;
import com.tibco.cep.studio.tester.core.model.ReteObjectType;
import com.tibco.cep.studio.tester.core.model.TesterResultsType;
import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.tester.ui.editor.AbstractMasterDetailsFormViewer;
import com.tibco.cep.studio.tester.ui.editor.data.ConceptTypeElement;
import com.tibco.cep.studio.tester.ui.editor.data.EventTypeElement;
import com.tibco.cep.studio.tester.ui.editor.data.InvocationObjectElement;
import com.tibco.cep.studio.tester.ui.editor.data.ReteObjectElement;
import com.tibco.cep.studio.tester.ui.editor.data.TestResultRoot;
import com.tibco.cep.studio.tester.ui.editor.data.TestResultsContentProvider;
import com.tibco.cep.studio.tester.ui.editor.data.TestResultsLabelProvider;
import com.tibco.cep.studio.tester.ui.utils.Messages;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.StudioImages;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractResultFormViewer extends AbstractMasterDetailsFormViewer implements /*SelectionListener,*/ ISelectionChangedListener{

	protected TestResultEditor editor;
	protected ExpandBar expandBar;
	protected ExpandItem newItem;
	protected ExpandItem delItem;
	protected ExpandItem modItem;
	protected ExpandItem rulesItem;
	protected Section testDatasection;
	protected Section testResultsection;

	protected TreeViewer newTreeViewer;
	protected TreeViewer modTreeViewer;
	protected TreeViewer delTreeViewer;
	protected TreeViewer rulesTreeViewer;

	protected String runName;
	protected TesterResultsType testerResultsObject;
	protected String projectName;
	
	protected List<ExecutionObjectType> asserted;
	protected List<ExecutionObjectType> modified;
	protected List<ExecutionObjectType> retracted;
	protected List<ExecutionObjectType> fired;
	
	protected TestResultRoot aRoot;
	protected TestResultRoot mRoot;
	protected TestResultRoot rRoot;
	protected TestResultRoot rulesRoot;
	
	protected String projectPath;
	protected TestResultDetailsPart testResultDetailsPart;
	protected SectionPart spart;
	protected String ruleSessionName;


	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.tester.result.AbstractMasterDetailsFormViewer#createPartControl(org.eclipse.swt.widgets.Composite, java.lang.String, org.eclipse.swt.graphics.Image)
	 */
	@Override
	protected void createPartControl(Composite container,String title,Image titleImage){
		managedForm = new ManagedForm(container);
		final ScrolledForm form = managedForm.getForm();

		FormToolkit toolkit = managedForm.getToolkit();
		form.setText(title);
		form.setImage(titleImage);
		form.setBackgroundImage(StudioImages.getImage(StudioImages.IMG_FORM_BG));
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 1;
		form.getBody().setLayout(layout);

		createGeneralPart(form, toolkit);

		sashForm = new MDSashForm(form.getBody(), SWT.HORIZONTAL);

		sashForm.setData("form", managedForm);
		toolkit.adapt(sashForm, false, false);
		sashForm.setMenu(form.getBody().getMenu());
		sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));

		createMasterPart(managedForm, sashForm);
		createDetailsPart(managedForm, sashForm);

		hookResizeListener();
		managedForm.initialize();
	}

	/**
	 * @param composite
	 * @return
	 */
	protected TreeViewer createTreeViewer(Composite composite, TestResultRoot root) {
		TreeViewer treeViewer = new TreeViewer(composite, SWT.BORDER | SWT.SINGLE);
		treeViewer.setContentProvider(new TestResultsContentProvider(treeViewer));
		treeViewer.setLabelProvider(new TestResultsLabelProvider(treeViewer));
		treeViewer.setInput(root);
		treeViewer.addSelectionChangedListener(this);
		
		//Adding popup menu
		MenuManager popupMenu = new MenuManager();
	    popupMenu.add(new ShowHeirachyAction(treeViewer));
	    Menu menu = popupMenu.createContextMenu(treeViewer.getTree());
	    treeViewer.getTree().setMenu(menu);
	    
		return treeViewer;
	}

	@Override
	protected void createMasterPart(final IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent, Section.NO_TITLE);
		section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));

		Composite client = toolkit.createComposite(section, SWT.WRAP | SWT.V_SCROLL);
		GridLayout flayout = new GridLayout();
		client.setLayout(flayout);
		toolkit.paintBordersFor(client);
		section.setClient(client);

		spart = new SectionPart(section);
		managedForm.addPart(spart);

		GridData gd = new GridData(GridData.FILL_BOTH);
		client.setLayoutData(gd);

		expandBar = new ExpandBar (client, SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		expandBar.setLayoutData(gd);
		
		Listener listener = new Listener() {
			public void handleEvent(Event e) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run () {					
						ExpandItem[] items = expandBar.getItems();
						Rectangle area = expandBar.getClientArea();
						int spacing = expandBar.getSpacing();
//						area.width -= 2 * spacing;
						int header0 = items[0].getHeaderHeight();
						int header1 = items[1].getHeaderHeight();
						int header2 = items[2].getHeaderHeight();
						int header3 = items[3].getHeaderHeight();
						area.height -= (items.length + 1) * spacing + header0 + header1 + header2 + header3;
						
						List<ExpandItem> expandedItems = new ArrayList<ExpandItem>();
						int expandCount = 0;
						for (ExpandItem expandItem : items) {
							if (expandItem.getExpanded()) {
								expandCount++;
								expandedItems.add(expandItem);
							}
						}
						if (expandCount > 0) {
							int expandSize = area.height / expandCount;
							for (ExpandItem expandItem : expandedItems) {
								expandItem.setHeight(expandSize);
							}
						}
						
						if (items[0].getExpanded()) {
							if (!items[1].getExpanded() && !items[2].getExpanded() && !items[3].getExpanded()) {
								items[0].setHeight(area.height);
							}
//							if (!items[1].getExpanded() && items[2].getExpanded()) {
//								items[2].setHeight(area.height);
//							}
//							if (items[1].getExpanded() && !items[2].getExpanded()) {
//								items[2].setHeight(area.height);
//							}
//							if (items[1].getExpanded() && items[2].getExpanded()) {
//								items[2].setHeight(area.height);
//							}
						}else{
							if (!items[1].getExpanded() && !items[3].getExpanded() && items[2].getExpanded()) {
								items[2].setHeight(area.height);
							}
							if (items[1].getExpanded() && !items[2].getExpanded() && !items[3].getExpanded()) {
								items[1].setHeight(area.height);
							}
						}
						
						if (items[1].getExpanded()) {
							if (!items[0].getExpanded() && !items[2].getExpanded() && !items[3].getExpanded()) {
								items[1].setHeight(area.height);
							}
//							if (!items[0].getExpanded() && items[2].getExpanded()) {
//								items[1].setHeight(area.height);
//							}
//							if (items[0].getExpanded() && !items[2].getExpanded()) {
//								items[1].setHeight(area.height);
//							}
//							if (items[0].getExpanded() && items[2].getExpanded()) {
//								items[1].setHeight(area.height);
//							}
						}else{
							if (!items[0].getExpanded() && !items[3].getExpanded() && items[2].getExpanded()) {
								items[2].setHeight(area.height);
							}
							if (items[0].getExpanded() && !items[2].getExpanded() && !items[3].getExpanded()) {
								items[0].setHeight(area.height);
							}
						}
						
						if (items[2].getExpanded()) {
							if (!items[0].getExpanded() && !items[1].getExpanded() && !items[3].getExpanded()) {
								items[2].setHeight(area.height);
							}
//							if (items[0].getExpanded() && !items[1].getExpanded()) {
//								items[2].setHeight(area.height);
//							}
//							if (!items[0].getExpanded() && items[1].getExpanded()) {
//								items[2].setHeight(area.height);
//							}
//							if (items[0].getExpanded() && items[1].getExpanded()) {
//								items[2].setHeight(area.height);
//							}
						}else{
							if (items[0].getExpanded() && !items[1].getExpanded() && !items[3].getExpanded()) {
								items[0].setHeight(area.height);
							}
							if (!items[0].getExpanded() && items[1].getExpanded() && !items[3].getExpanded()) {
								items[1].setHeight(area.height);
							}
						}
						if (items[3].getExpanded()) {
							if (!items[0].getExpanded() && !items[1].getExpanded() && !items[2].getExpanded()) {
								items[3].setHeight(area.height);
							}
						}
//						if (items[1].getExpanded()) {
//							area.height -= items[1].getHeight();// + spacing;
//						}
//						if (items[2].getExpanded()) {
//							area.height = area.height - items[2].getHeight();// + spacing;
//						}
//						items[0].setHeight(area.height);
					}
				});
			}
		};
		expandBar.addListener(SWT.Resize, listener);
		expandBar.addListener(SWT.Expand, listener);
		expandBar.addListener(SWT.Collapse, listener);

		createNewSubSection(toolkit, "Created");
		createModifiedSubSection(toolkit, "Modified");
		createDeletedSubSection(toolkit, "Deleted");		
		createRulesSubSection(toolkit, "Rules Executed");		

		newItem.setExpanded(true);
		modItem.setExpanded(true);
		delItem.setExpanded(true);
		rulesItem.setExpanded(true);
	}

	/**
	 * @param toolkit
	 * @param title
	 */
	protected void createNewSubSection(FormToolkit toolkit, String title){
		Composite newSectionClient = toolkit.createComposite(expandBar);
		newSectionClient.setLayout(new GridLayout());
		createNewHeirachicalPage(toolkit, newSectionClient);
		newItem = new ExpandItem (expandBar, SWT.RESIZE, 0);
		newItem.setText(title);
		newItem.setHeight(120);
		newItem.setControl(newSectionClient);
	}

	/**
	 * @param toolkit
	 * @param parent
	 */
	private void createNewHeirachicalPage(FormToolkit toolkit, Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		newTreeViewer = createTreeViewer(parent, aRoot);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 100;
		data.heightHint = 120;
		Tree treeWidget = newTreeViewer.getTree();
		treeWidget.setLayoutData(data);
		treeWidget.setFont(parent.getFont());
	}

	/**
	 * @param toolkit
	 * @param title
	 */
	protected void createModifiedSubSection(FormToolkit toolkit, String title){
		Composite modeSectionClient = toolkit.createComposite(expandBar);
		modeSectionClient.setLayout(new GridLayout());
		createModHeirachicalPage(toolkit, modeSectionClient);
		modItem = new ExpandItem (expandBar, SWT.RESIZE, 1);
		modItem.setText(title);
		modItem.setHeight(120);
		modItem.setControl(modeSectionClient);
	}

	/**
	 * @param toolkit
	 * @param parent
	 */
	private void createModHeirachicalPage(FormToolkit toolkit, Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		modTreeViewer = createTreeViewer(parent, mRoot);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 100;
		data.heightHint = 120;
		Tree treeWidget = modTreeViewer.getTree();
		treeWidget.setLayoutData(data);
		treeWidget.setFont(parent.getFont());
	}

	/**
	 * @param toolkit
	 * @param title
	 */
	protected void createDeletedSubSection(FormToolkit toolkit, String title){
		Composite delSectionClient = toolkit.createComposite(expandBar);
		delSectionClient.setLayout(new GridLayout());
		createDelHeirachicalPage(toolkit, delSectionClient);
		delItem = new ExpandItem (expandBar, SWT.RESIZE, 2);
		delItem.setText(title);
		delItem.setHeight(120);
		delItem.setControl(delSectionClient);
	}

	/**
	 * @param toolkit
	 * @param parent
	 */
	private void createDelHeirachicalPage(FormToolkit toolkit, Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		delTreeViewer = createTreeViewer(parent, rRoot);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 100;
		data.heightHint = 120;
		Tree treeWidget = delTreeViewer.getTree();
		treeWidget.setLayoutData(data);
		treeWidget.setFont(parent.getFont());
	}

	/**
	 * @param toolkit
	 * @param title
	 */
	protected void createRulesSubSection(FormToolkit toolkit, String title){
		Composite rulesSectionClient = toolkit.createComposite(expandBar);
		rulesSectionClient.setLayout(new GridLayout());
		createRulesHeirachicalPage(toolkit, rulesSectionClient);
		rulesItem = new ExpandItem (expandBar, SWT.RESIZE, 3);
		rulesItem.setText(title);
		rulesItem.setHeight(120);
		rulesItem.setControl(rulesSectionClient);
	}

	/**
	 * @param toolkit
	 * @param parent
	 */
	private void createRulesHeirachicalPage(FormToolkit toolkit, Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		rulesTreeViewer = createTreeViewer(parent, rulesRoot);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 100;
		data.heightHint = 120;
		Tree treeWidget = rulesTreeViewer.getTree();
		treeWidget.setLayoutData(data);
		treeWidget.setFont(parent.getFont());
	}

	/**
	 * @param detailsPart
	 */
	protected void registerPages(TestResultDetailsPart detailsPart) {
		ruleSessionName = runName.substring(0, runName.indexOf('/'));
		ResultDetailsPage resultReteDetailsPage = new ResultDetailsPage(projectName, projectPath, ruleSessionName);
		testResultDetailsPart.registerPage(this, ReteObjectElement.class, resultReteDetailsPage);
		
		ResultDetailsPage conceptTypeElementDetailsPage = new ResultDetailsPage(projectName, projectPath, ruleSessionName);
		testResultDetailsPart.registerPage(this, ConceptTypeElement.class, conceptTypeElementDetailsPage);
		
		ResultDetailsPage resultCausalDetailsPage = new ResultDetailsPage(projectName, projectPath, ruleSessionName);
		testResultDetailsPart.registerPage(this, EventTypeElement.class, resultCausalDetailsPage);
		
		StudioTesterUIPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(resultReteDetailsPage);
		StudioUIPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(resultReteDetailsPage);
		StudioTesterUIPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(conceptTypeElementDetailsPage);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractMasterDetailsFormViewer#createDetailsPart(org.eclipse.ui.forms.IManagedForm, org.eclipse.swt.widgets.Composite)
	 */
	protected  void createDetailsPart(final IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit(); 
		Section section = toolkit.createSection(parent, Section.TITLE_BAR );
		section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		section.setFont(DEFAULT_FONT);
		section.setText(Messages.getString("result.data.title.main"));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		section.setLayoutData(gd);	
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		client.setLayout(new FillLayout());
		section.setClient(client);
		testResultDetailsPart = new TestResultDetailsPart(managedForm, client, SWT.NULL);
		managedForm.addPart(testResultDetailsPart);
		registerPages(testResultDetailsPart);
		toolkit.paintBordersFor(client);
	}

	/**
	 * @return
	 */
	protected TestResultEditor getEditor() {
		return editor;
	}

	/**
	 * @param editor
	 */
	protected void setEditor(TestResultEditor editor) {
		this.editor = editor;
	}
	
	private class ShowHeirachyAction extends org.eclipse.jface.action.Action {
		
		private TreeViewer treeViewer;
		
		/**
		 * @param treeViewer
		 */
		public ShowHeirachyAction(TreeViewer treeViewer) {
			super("Show Heirarchy");
			this.treeViewer = treeViewer;
		}
		/* (non-Javadoc)
		 * @see org.eclipse.jface.action.Action#run()
		 */
		public void run() {
			showReteObjectInStudioExplorer(treeViewer);
		}
	}
	
	/**
	 * @param treeViewer
	 */
	protected void showReteObjectInStudioExplorer(TreeViewer treeViewer) {
		if (!treeViewer.getSelection().isEmpty() && treeViewer.getSelection() instanceof IStructuredSelection) {
			Object obj = ((IStructuredSelection)treeViewer.getSelection()).getFirstElement();
			String path = null;
			if (obj instanceof ReteObjectElement) {
				ReteObjectElement reteObjectElement = (ReteObjectElement)obj;
				ReteObjectType reteObjectType = reteObjectElement.getReteObjectType();
				if (reteObjectType.getConcept() != null) {
					path = getFullPath(reteObjectType.getConcept());
				}
				if (reteObjectType.getEvent() != null) {
					path = getFullPath(reteObjectType.getEvent());
				}
			}
			if (obj instanceof ConceptTypeElement) {
				ConceptTypeElement reteObjectElement = (ConceptTypeElement)obj;
				if (reteObjectElement.getConceptType() != null) {
					path = getFullPath(reteObjectElement.getConceptType());
				
				}
			}
			if (obj instanceof EventTypeElement) {
				EventTypeElement reteObjectElement = (EventTypeElement)obj;
				if (reteObjectElement.getEventType() != null) {
					path = getFullPath(reteObjectElement.getEventType());
				}
			}
			if (obj instanceof InvocationObjectElement) {
				InvocationObjectElement invokElement = (InvocationObjectElement)obj;
				path = invokElement.getInvocationObjectType().getNamespace().getValue(); 
				Compilable compilable = IndexUtils.getRule(projectName, path, ELEMENT_TYPES.RULE);
				if (compilable != null) {
					IFile file = IndexUtils.getFile(projectName, compilable);
					linkToStudioExplorer(getEditor().getSite().getPage(), file);
				}
				return;
			}
			if (path != null) {
				Entity entity = IndexUtils.getEntity(projectName, path);
				if (entity != null) {
					IFile file = IndexUtils.getFile(projectName, entity);
					linkToStudioExplorer(getEditor().getSite().getPage(), file);
				}
			}
		}
	}
}