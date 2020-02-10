package com.tibco.cep.studio.tester.ui.editor.result;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

//import com.jidesoft.grid.TableModelWrapperUtils;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.core.model.RuleDebugModel;
import com.tibco.cep.studio.debug.input.WMContentsInputTask;
import com.tibco.cep.studio.debug.input.WorkingMemoryManipulatorInputTask;
import com.tibco.cep.studio.tester.core.model.ConceptType;
import com.tibco.cep.studio.tester.core.model.EntityType;
import com.tibco.cep.studio.tester.core.model.EventType;
import com.tibco.cep.studio.tester.core.model.ReteObjectType;
import com.tibco.cep.studio.tester.ui.editor.AbstractSashForm;
import com.tibco.cep.studio.tester.ui.editor.data.AbstractTestViewer;
import com.tibco.cep.studio.tester.ui.editor.data.ConceptTypeElement;
import com.tibco.cep.studio.tester.ui.editor.data.EventTypeElement;
import com.tibco.cep.studio.tester.ui.editor.data.ReteObjectElement;
import com.tibco.cep.studio.tester.ui.utils.Messages;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.preferences.StudioUIPreferenceConstants;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractResultDetailsPage extends AbstractTestViewer implements IDetailsPage, IPropertyChangeListener {

	protected IManagedForm managedForm;
	protected IProject project;
	protected Composite details;
	protected Section testResultInitValSection;
	protected Section testResultPrevValSection;
	protected Section testResultNewSection;
	protected Vector<String> resultDataChange = null;
	protected Vector<String> resultDataChangeColumns = null;
	protected Vector<Integer> resultNewObject = null;
	protected Vector<Integer> resultDataChangeRows = null;
	protected List<Integer> resultColList = null;

	protected String projectPath;
	protected String projectName;
	protected ConceptType conceptType;
	protected EventType eventType;
	protected EntityType entityType;
	protected AbstractSashForm form;
	protected String ruleSessionName;
//	protected Object[] changedElementArray;
	
	private Map<String, String> changedPropertiesMap = new HashMap<String, String>();

	protected Button modifyInstanceButton;
//	protected Composite sectionClient;
	protected Composite sectionClient;
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	public void createContents(Composite parent) {
		GridLayout layout1 = new GridLayout();
		parent.setLayout(layout1);
		FormToolkit toolkit = managedForm.getToolkit();
		Section mainDetailsSection = toolkit.createSection(parent,  Section.NO_TITLE | Section.EXPANDED );
		mainDetailsSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		mainDetailsSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		mainDetailsSection.setFont(DEFAULT_FONT);
		GridData gd = new GridData(GridData.FILL_BOTH);
		mainDetailsSection.setLayoutData(gd);
		sectionClient = toolkit.createComposite(mainDetailsSection);
		mainDetailsSection.setClient(sectionClient);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;
		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.grabHorizontal = true;
		sectionClient.setLayout(layout);
		sectionClient.setLayoutData(td);
		createResultDetailsPart(managedForm, toolkit, sectionClient);
		createOldResultDetailsPart(managedForm, toolkit, sectionClient);
		createInitValDetailsPart(managedForm, toolkit, sectionClient);
		toolkit.paintBordersFor(sectionClient);
	}

	/**
	 * @param managedForm
	 * @param toolkit
	 * @param parent
	 */
	protected Composite newValSectionClient ;
	protected Composite previousValSectionClient ;
	protected Composite initialValSectionClient ;

	protected void createResultDetailsPart(final IManagedForm managedForm, FormToolkit toolkit, Composite parent) {

		testResultNewSection = toolkit.createSection(parent, Section.TITLE_BAR| Section.EXPANDED);
		testResultNewSection.setText(Messages.getString("result.show.after") + "         ");
		testResultNewSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		testResultNewSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		testResultNewSection.setFont(DEFAULT_FONT);
		
		newValSectionClient = toolkit.createComposite(testResultNewSection, SWT.NONE);
		testResultNewSection.setClient(newValSectionClient);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;
		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.grabHorizontal = true;
		td.heightHint = 150;
		newValSectionClient.setLayout(layout);
		newValSectionClient.setLayoutData(td);
		
		TableWrapLayout layout1 = new TableWrapLayout();
		layout1.numColumns = 1;
		TableWrapData td1 = new TableWrapData(TableWrapData.FILL);
		td1.grabHorizontal = true;
		td1.heightHint = 150;
		testResultNewSection.setLayout(layout1);
		testResultNewSection.setLayoutData(td1);
		toolkit.paintBordersFor(newValSectionClient);

		Composite composite1 = toolkit.createComposite(parent);
		composite1.setLayout(new GridLayout(1, false));
		modifyInstanceButton = toolkit.createButton(composite1, "Modify", SWT.NONE);
		modifyInstanceButton.setToolTipText("Modify Instance in Working Memory");
		modifyInstanceButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if(conceptType != null) {
						IRuleRunTarget rTarget = null;
						ILaunch[] launches = DebugPlugin.getDefault().getLaunchManager().getLaunches();
						for(ILaunch launch:launches) {
							IDebugTarget[] targets = launch.getDebugTargets();
							for(IDebugTarget target: targets) {
								if(!target.isTerminated() && !target.isDisconnected()){
									if(target.getModelIdentifier().equals(RuleDebugModel.getModelIdentifier())) {
										rTarget = (IRuleRunTarget) target;
									}
								}
							}
						}
						Map<String, String> changedPropertiesMap = getChangedPropertiesMap(/*conceptType*/);
						String[] index = getIndexArray(changedPropertiesMap);
						String[] changedProperties = getPropertiesArray(changedPropertiesMap);
						if(rTarget != null) {
							WorkingMemoryManipulatorInputTask testerMemoryManipulatorInputTask = new WorkingMemoryManipulatorInputTask(rTarget, ruleSessionName, new Long(conceptType.getId()), index, changedProperties);
							rTarget.addInputVmTask(testerMemoryManipulatorInputTask);
							int numberOfObjects = StudioUIPlugin.getDefault().
									getPreferenceStore().getInt(StudioUIPreferenceConstants.WM_OBJCECTS_SHOW_MAX_NO);
							WMContentsInputTask inputTask = new WMContentsInputTask(rTarget, ruleSessionName, numberOfObjects);
							rTarget.addInputVmTask(inputTask);
							getChangedPropertiesMap().clear();
						}
					}
				}catch(Exception e1) {
					e1.printStackTrace();
				}				
			}
		});
		//}
	}
	
	

	private String[] getPropertiesArray(Map<String, String> changedPropertiesMap) {
		String[] properties = new String[changedPropertiesMap.size()];
		Iterator<String> iterator = changedPropertiesMap.keySet().iterator();
		int index = 0;
		while(iterator.hasNext()) {
			properties[index++] = changedPropertiesMap.get(iterator.next());
		}
		return properties;
	}

	private String[] getIndexArray(Map<String, String> changedPropertiesMap) {
		return changedPropertiesMap.keySet().toArray(new String[changedPropertiesMap.size()]);

	}
	
//	private Map<String, String> getChangedPropertiesMap(ConceptType conceptType) {
//		
//		List<PropertyType> properties = conceptType.getProperty();
//		Object[] propertiesArray =  properties.toArray();
//		for(int index = 0; index<propertiesArray.length; index++) {
//			PropertyType property = (PropertyType) propertiesArray[index];
//			if(property.getValue() != changedElementArray[index + 2 ]) {
//				changedPropertiesMap.put(property.getName(), changedElementArray[index + 2].toString());
//			}
//		}
//		return changedPropertiesMap;
//	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IPartSelectionListener#selectionChanged(org.eclipse.ui.forms.IFormPart, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		try{
			modifiedResult = false;
			getChangedPropertiesMap().clear();
			IStructuredSelection sel = (IStructuredSelection)selection;
			Object obj = sel.getFirstElement();
			if (sel.size() == 1) {
				if (obj instanceof ReteObjectElement) {
					ReteObjectElement reteObjectElement = (ReteObjectElement)obj;
					ReteObjectType reteObjectType = reteObjectElement.getReteObjectType();
					if (reteObjectType.getChangeType() == ReteObjectType.ReteChangeType.MODIFY) {
						modifiedResult = true;
					}
					if (reteObjectType.getConcept() != null) {
						conceptType = (ConceptType)reteObjectType.getConcept();
						entityType = conceptType;
						eventType = null;
					}
					if (reteObjectType.getEvent() != null) {
						eventType = (EventType)reteObjectType.getEvent();
						entityType = eventType;
						conceptType = null;
					}
				}
				if (obj instanceof ConceptTypeElement) {
					ConceptTypeElement reteObjectElement = (ConceptTypeElement)obj;
					if (reteObjectElement.getConceptType() != null) {
						conceptType = (ConceptType)reteObjectElement.getConceptType();
						entityType = conceptType;
						eventType = null;
					}
				}
				if (obj instanceof EventTypeElement) {
					EventTypeElement reteObjectElement = (EventTypeElement)obj;
					if (reteObjectElement.getEventType() != null) {
						eventType = (EventType)reteObjectElement.getEventType();
						entityType = eventType;
						conceptType = null;
					}
				}
			} else {
				eventType = null;
				conceptType = null;
				eventType = null;
			}

		}catch(Exception e){
			//			StudioTesterUIPlugin.log(e);
		}
	}

	/**
	 * @param managedForm
	 * @param toolkit
	 * @param parent
	 */
	protected void createOldResultDetailsPart(final IManagedForm managedForm, FormToolkit toolkit, Composite parent){
		
		testResultPrevValSection = toolkit.createSection(parent, Section.EXPANDED | Section.TITLE_BAR);
		testResultPrevValSection.setText(Messages.getString("result.show.before"));
		testResultPrevValSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		testResultPrevValSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		testResultPrevValSection.setFont(DEFAULT_FONT);
		GridData dtgd = new GridData(GridData.FILL_BOTH);
		testResultPrevValSection.setLayoutData(dtgd);
		
		previousValSectionClient = toolkit.createComposite(testResultPrevValSection, SWT.NONE);
		testResultPrevValSection.setClient(previousValSectionClient);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;
		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.grabHorizontal = true;
		td.heightHint = 150;
		previousValSectionClient.setLayout(layout);
		previousValSectionClient.setLayoutData(td);
		
		TableWrapLayout layout1 = new TableWrapLayout();
		layout1.numColumns = 1;
		TableWrapData td1 = new TableWrapData(TableWrapData.FILL);
		td1.grabHorizontal = true;
		td1.heightHint = 150;
		testResultPrevValSection.setLayout(layout1);
		testResultPrevValSection.setLayoutData(td1);

		toolkit.paintBordersFor(previousValSectionClient);
		
	}
	
	protected void createInitValDetailsPart(final IManagedForm managedForm, FormToolkit toolkit, Composite parent){
		
		testResultInitValSection = toolkit.createSection(parent, Section.EXPANDED | Section.TITLE_BAR);
		testResultInitValSection.setText(Messages.getString("result.show.initial"));
		testResultInitValSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		testResultInitValSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		testResultInitValSection.setFont(DEFAULT_FONT);
		GridData dtgd = new GridData(GridData.FILL_BOTH);
		testResultInitValSection.setLayoutData(dtgd);
		
		initialValSectionClient = toolkit.createComposite(testResultInitValSection, SWT.NONE);
		testResultInitValSection.setClient(initialValSectionClient);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;
		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.grabHorizontal = true;
		td.heightHint = 150;
		initialValSectionClient.setLayout(layout);
		initialValSectionClient.setLayoutData(td);
		
		TableWrapLayout layout1 = new TableWrapLayout();
		layout1.numColumns = 1;
		TableWrapData td1 = new TableWrapData(TableWrapData.FILL);
		td1.grabHorizontal = true;
		td1.heightHint = 150;
		testResultInitValSection.setLayout(layout1);
		testResultInitValSection.setLayoutData(td1);

		toolkit.paintBordersFor(initialValSectionClient);
		
	}

	@Override
	public void commit(boolean onSave) {
//		if(form instanceof AbstractWMResultViewer) {
//			((AbstractWMResultViewer)form).getEditor().doSave(new NullProgressMonitor());
//		}
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#initialize(org.eclipse.ui.forms.IManagedForm)
	 */
	public void initialize(IManagedForm managedForm) {
		this.managedForm = managedForm;
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setDirty() {
		if(form instanceof AbstractWMResultViewer) {
			((AbstractWMResultViewer)form).getEditor().modified();
		}
	}

	@Override
	public boolean isStale() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean setFormInput(Object input) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
/*	public void propertyChange(PropertyChangeEvent event) {
		try {
			String property = event.getProperty();
			if (property
					.equals(TesterPreferenceConstants.TEST_RESULT_CHANGED_VALUE_BACK_GROUND_COLOR)
					|| property
					.equals(TesterPreferenceConstants.TEST_RESULT_CHANGED_VALUE_FORE_GROUND_COLOR)
					|| property
					.equals(TesterPreferenceConstants.TEST_RESULT_NEW_INSTANCE_BACK_GROUND_COLOR)
					|| property
					.equals(TesterPreferenceConstants.TEST_RESULT_NEW_INSTANCE_FORE_GROUND_COLOR)
					|| property
					.equals(TesterPreferenceConstants.TEST_RESULT_CHANGED_FONT)) {
				if (testDataResultTable != null) {
					testDataResultTable.updateUI();
				}
				if (testDataOldResultTable != null) {
					testDataOldResultTable.updateUI();
				}
			}
			if (property.equals(StudioUIPreferenceConstants.AUTO_SCROLL_RESULT_TABLE)) {
				boolean autoScroll = StudioUIPlugin.getDefault().
				getPreferenceStore().getBoolean(StudioUIPreferenceConstants.AUTO_SCROLL_RESULT_TABLE);
				if (autoScroll) {
					if (testOldResultScrollPane != null) {
						testOldResultScrollPane.getHorizontalScrollBar().addAdjustmentListener(this);
						testOldResultScrollPane.updateUI();
					}
					testResultScrollPane.getHorizontalScrollBar().addAdjustmentListener(this);
					testResultScrollPane.updateUI();
				} else {
					if (testOldResultScrollPane != null) {
						testOldResultScrollPane.getHorizontalScrollBar().removeAdjustmentListener(this);
						testOldResultScrollPane.updateUI();
					}
					testResultScrollPane.getHorizontalScrollBar().removeAdjustmentListener(this);
					testResultScrollPane.updateUI();
				}
			}
		} catch (Exception e) {
			//			e.printStackTrace();
		}
	}

*/

	public void setForm(AbstractSashForm form) {
		this.form = form;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.AdjustmentListener#adjustmentValueChanged(java.awt.event.AdjustmentEvent)
	 */
	/*
	public void adjustmentValueChanged(AdjustmentEvent ae) {
		if (testResultScrollPane.getHorizontalScrollBar().getValueIsAdjusting()) {
			if (testOldResultScrollPane != null) {
				testOldResultScrollPane.getHorizontalScrollBar().setValue(ae.getValue());
			}
		}
		if (testOldResultScrollPane != null 
				&& testOldResultScrollPane.getHorizontalScrollBar().getValueIsAdjusting()) {
			testResultScrollPane.getHorizontalScrollBar().setValue(ae.getValue());
		}
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected,
			int row, int column) {
		System.out.println(value);
		return table;

	}
	*/

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	public Map<String, String> getChangedPropertiesMap() {
		return changedPropertiesMap;
	}

	

	/**
	 * @param table
	 * @param fullElementPath
	 * @param entityType
	 * @param isOldResultTable
	 * @throws Exception
	 */
//	protected void refreshResultData(JTable table, String fullElementPath, final EntityType entityType, boolean isOldResultTable) throws Exception {
//
//		Vector<Vector<? extends Object>> dataVector = new Vector<Vector<? extends Object>>();
//		Map<String, Vector<Vector<? extends Object>>> map = saveResult(dataVector, entityType, fullElementPath, projectName, isOldResultTable);
//		if (map != null) {
//			final Set<String> keys = map.keySet();
//			DefaultTableModel model = (DefaultTableModel) TableModelWrapperUtils.getActualTableModel(table.getModel());
//			for (int k = model.getRowCount() - 1; k > -1; k--) {
//				model.removeRow(k);
//			}
//
//			for (String key : keys) {
//				Vector<Vector<? extends Object>> data = map.get(key);
//
//				int count = 0;
//				for(Vector<? extends Object> v : data){
//					model.insertRow(count, v);
//					count++;
//				}
//			}
//		}
//		if (entityType == eventType) {
//			table.setEnabled(false);
//		} else {
//			table.setEnabled(true);
//		}	
//		Display.getDefault().asyncExec(new Runnable() {
//			@Override
//			public void run() {
//				boolean isResult = form instanceof AbstractResultFormViewer;
//				if (!isResult) { 
//					if (entityType == eventType) {
//						modifyInstanceButton.setVisible(false);
//					} else {
//						modifyInstanceButton.setVisible(true);
//					}	
//				}
//			}
//		});
//	}
	



}