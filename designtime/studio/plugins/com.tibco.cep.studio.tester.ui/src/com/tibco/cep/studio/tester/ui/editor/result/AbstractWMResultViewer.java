package com.tibco.cep.studio.tester.ui.editor.result;

import static com.tibco.cep.studio.tester.core.utils.TesterCoreUtils.getFullPath;
import static com.tibco.cep.studio.ui.navigator.util.ProjectExplorerUtils.linkToStudioExplorer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.debug.core.DebugException;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
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
import com.tibco.cep.studio.debug.core.model.AbstractDebugTarget;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.input.ClearWorkingMemoryContentsInputTask;
import com.tibco.cep.studio.debug.input.WMContentsInputTask;
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
import com.tibco.cep.studio.tester.ui.editor.data.TestResultsLabelProvider;
import com.tibco.cep.studio.tester.ui.editor.data.WMResultContentProvider;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.preferences.StudioUIPreferenceConstants;
import com.tibco.cep.studio.ui.util.StudioImages;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractWMResultViewer extends AbstractMasterDetailsFormViewer implements SelectionListener, ISelectionChangedListener {

	protected WMResultEditor wmResultEditor;
	protected Section testDatasection;
	protected Section testResultsection;

	protected TreeViewer wmTreeViewer;	

	protected String projectName;
	protected String projectPath;
	protected TestResultDetailsPart testResultDetailsPart;
	protected SectionPart spart;
	protected String ruleSessionName;
	protected TesterResultsType testerResultsObject;
	protected TestResultRoot testResultRoot;
	protected List<ExecutionObjectType> contents = new ArrayList<ExecutionObjectType>();
	protected IRuleRunTarget runTarget;
	protected Button refreshButton;
	protected Button clearButton;
	
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

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.tester.ui.AbstractMasterDetailsFormViewer#createMasterPart(org.eclipse.ui.forms.IManagedForm, org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createMasterPart(final IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent, Section.NO_TITLE);
		section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));

		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout flayout = new GridLayout(2, false);
		client.setLayout(flayout);
		toolkit.paintBordersFor(client);
		section.setClient(client);

		spart = new SectionPart(section);
		managedForm.addPart(spart);

		client.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		createNewHeirachicalPage(toolkit, client/*, "Working Memory Contents"*/);
		
		
		Composite buttonsClient = new Composite(client, SWT.NONE);
		buttonsClient.setLayout(new GridLayout(1, false));
		buttonsClient.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		buttonsClient.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		refreshButton = toolkit.createButton(buttonsClient, "Refresh", SWT.PUSH);
		refreshButton.setToolTipText("Refresh to reflect working memory changes");
		refreshButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		refreshButton.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					callWMRefresh();
				} catch (DebugException e1) {
					StudioTesterUIPlugin.log(e1);
				}
			}
			
		});
		clearButton = toolkit.createButton(buttonsClient, "Clear", SWT.PUSH);
		clearButton.setToolTipText("Clear Working Memory Contents");
		clearButton.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		clearButton.addSelectionListener(new SelectionAdapter() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				ClearWorkingMemoryContentsInputTask clearContentsInputTask = new ClearWorkingMemoryContentsInputTask(runTarget, ruleSessionName);
				try {
					if (runTarget.getDebugTarget() instanceof AbstractDebugTarget) {
						if (((AbstractDebugTarget)runTarget.getDebugTarget()).getSession() != null) {
							runTarget.addInputVmTask(clearContentsInputTask);
						}
					}
				} catch (DebugException e1) {
					e1.printStackTrace();
				}
			}

		});
		
	}

	/**
	 * @param toolkit
	 * @param parent
	 */
	private void createNewHeirachicalPage(FormToolkit toolkit, Composite parent) {
		wmTreeViewer = createTreeViewer(parent, testResultRoot);
		Tree treeWidget = wmTreeViewer.getTree();
		treeWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		treeWidget.setFont(parent.getFont());
	}

	/**
	 * @param detailsPart
	 */
	protected void registerPages(TestResultDetailsPart detailsPart) {
		ResultDetailsPage resultReteDetailsPage = new ResultDetailsPage(projectName, projectPath, ruleSessionName);
		testResultDetailsPart.registerPage(this, ReteObjectElement.class, resultReteDetailsPage);
		
		ResultDetailsPage conceptTypeElementDetailsPage = new ResultDetailsPage(projectName, projectPath, ruleSessionName);
		testResultDetailsPart.registerPage(this, ConceptTypeElement.class, conceptTypeElementDetailsPage);
		
		ResultDetailsPage resultCausalDetailsPage = new ResultDetailsPage(projectName, projectPath, ruleSessionName);
		testResultDetailsPart.registerPage(this, EventTypeElement.class, resultCausalDetailsPage);
		
		StudioTesterUIPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(resultReteDetailsPage);
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
		section.setText("Result Test Data");
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
	 * @param composite
	 * @return
	 */
	protected TreeViewer createTreeViewer(Composite composite, TestResultRoot root) {
		TreeViewer treeViewer = new TreeViewer(composite, SWT.BORDER | SWT.SINGLE);
		treeViewer.setContentProvider(new WMResultContentProvider(treeViewer));
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
	
	/**
	 * @return
	 */
	protected WMResultEditor getEditor() {
		return wmResultEditor;
	}

	/**
	 * @param editor
	 */
	protected void setEditor(WMResultEditor editor) {
		this.wmResultEditor = editor;
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

	public TesterResultsType getTesterResultsObject() {
		return testerResultsObject;
	}

	public void setTesterResultsObject(TesterResultsType testerResultsObject) {
		this.testerResultsObject = testerResultsObject;
	}

	public void callWMRefresh() throws DebugException {
		int numberOfObjects = StudioUIPlugin.getDefault().
				getPreferenceStore().getInt(StudioUIPreferenceConstants.WM_OBJCECTS_SHOW_MAX_NO);
		WMContentsInputTask inputTask = new WMContentsInputTask(runTarget,ruleSessionName, numberOfObjects);
		try {
			if (runTarget.getDebugTarget() instanceof AbstractDebugTarget) {
				if (((AbstractDebugTarget)runTarget.getDebugTarget()).getSession() != null) {
					runTarget.addInputVmTask(inputTask);
				}
			}
		} catch (DebugException e1) {
			e1.printStackTrace();
		}
	}
}