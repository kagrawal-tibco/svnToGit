package com.tibco.cep.bpmn.ui.graph.properties;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.addHyperLinkFieldListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.TimerTaskExpressionXPathWizard;
import com.tibco.cep.bpmn.ui.XPathIntegerExpressionValidator;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tibco.cep.bpmn.ui.graph.palette.PaletteLoader;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.bpmn.ui.utils.PaletteHelpTextGenerator;
import com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.dialog.StudioFilteredResourceSelectionDialog;
import com.tibco.cep.studio.ui.dialog.StudioResourceSelectionDialog;
import com.tibco.cep.studio.ui.filter.RuleBasedTimerEventOnlyFilter;
import com.tibco.cep.studio.ui.wizards.IDiagramEntitySelection;

/**
 * 
 * @author ggrigore
 * 
 */
public class TimeoutPropertySection extends AbstractFormPropertySection implements IDiagramEntitySelection {

	private boolean refresh;
	private Composite composite;

	private Button enableButton;
	private Text expressionText;
	private Text eventText;
	private CCombo unitCombo;
	private Button expressionButton;
	private Button eventResourceButton;
	private WidgetListener widgetListener;
	private IFile newFile;
	
	protected FormText browser;
	
	protected Section helpSection;
	protected Composite browserComposite;
	
	protected String help = "";
	private Hyperlink createHyperlink;

	public TimeoutPropertySection() {
		super();
		widgetListener = new WidgetListener();
	}

	@Override
	public void aboutToBeHidden() {
		if (!composite.isDisposed()) {
			if (unitCombo != null)
				unitCombo.removeModifyListener(widgetListener);
			if (eventText != null)
				eventText.removeModifyListener(widgetListener);
			if (expressionButton != null)
				expressionButton.removeSelectionListener(widgetListener);
			if (eventResourceButton != null)
				eventResourceButton.removeSelectionListener(widgetListener);
			if (enableButton != null)
				enableButton.removeSelectionListener(widgetListener);

		}
	}

	@Override
	public void aboutToBeShown() {
		if (!composite.isDisposed()) {
			if (unitCombo != null)
				unitCombo.addModifyListener(widgetListener);
			if (eventText != null)
				eventText.addModifyListener(widgetListener);
			if (expressionButton != null)
				expressionButton.addSelectionListener(widgetListener);
			if (eventResourceButton != null)
				eventResourceButton.addSelectionListener(widgetListener);
			if (enableButton != null)
				enableButton.addSelectionListener(widgetListener);

		}
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createPropertyPartControl(IManagedForm form, Composite parent) {
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));

		getWidgetFactory().createLabel(composite, BpmnMessages.getString("timeoutPropSection_enableButton_Label"), SWT.NONE);
		enableButton = getWidgetFactory()
				.createButton(composite, "", SWT.CHECK);

		createExpressionComponents();
		createResourceProperty();
		createTimeoutUnits();
		enableTimerProperty(false);
	}

	private void createExpressionComponents() {
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		// Add Event
		getWidgetFactory().createLabel(composite,  BpmnMessages.getString("timeoutPropSection_expression_Label"), SWT.NONE);

		Composite browseComposite = getWidgetFactory().createComposite(
				composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		browseComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		browseComposite.setLayoutData(gd);

		expressionText = getWidgetFactory().createText(browseComposite, "",
				SWT.BORDER);
		gd = new GridData(/* GridData.FILL_HORIZONTAL */);
		gd.widthHint = 562;
		expressionText.setLayoutData(gd);
		expressionText.setEditable(false);

		expressionButton = new Button(browseComposite, SWT.NONE);
		expressionButton.setText(BpmnMessages.getString("edit_label"));
	}

	private void createTimeoutUnits() {
		getWidgetFactory().createLabel(composite, BpmnMessages.getString("timeoutPropSection_unit_Label"), SWT.NONE);
		unitCombo = getWidgetFactory().createCCombo(composite, SWT.READ_ONLY);
		GridData gd = new GridData();
		gd.widthHint = 150;
		gd.heightHint = 20;
		gd.horizontalIndent = 1;
		unitCombo.setLayoutData(gd);
		unitCombo.setLayoutData(gd);
		unitCombo.add(BpmnModelClass.ENUM_TIME_UNITS_Milliseconds.getLiteral());
		unitCombo.add(BpmnModelClass.ENUM_TIME_UNITS_Seconds.getLiteral());
		unitCombo.add(BpmnModelClass.ENUM_TIME_UNITS_Minutes.getLiteral());
		unitCombo.add(BpmnModelClass.ENUM_TIME_UNITS_Hours.getLiteral());
		unitCombo.add(BpmnModelClass.ENUM_TIME_UNITS_Days.getLiteral());
		unitCombo.setText(BpmnModelClass.ENUM_TIME_UNITS_Milliseconds
				.getLiteral());
	}

	private void createResourceProperty() {
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		// Add Event
		createHyperlink = getWidgetFactory().createHyperlink(
				composite,BpmnMessages.getString("timeoutPropSection_eventLink_Label"), SWT.NONE);

		Composite browseComposite = getWidgetFactory().createComposite(
				composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		browseComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		browseComposite.setLayoutData(gd);

		eventText = getWidgetFactory().createText(browseComposite, "",
				SWT.BORDER);
		gd = new GridData(/* GridData.FILL_HORIZONTAL */);
		gd.widthHint = 562;
		eventText.setLayoutData(gd);

		addHyperLinkFieldListener(this, createHyperlink, eventText, fEditor, fEditor
				.getProject().getName(), false, false,  new ELEMENT_TYPES[] { ELEMENT_TYPES.TIME_EVENT}, true);

		eventResourceButton = new Button(browseComposite, SWT.NONE);
		eventResourceButton.setText(BpmnMessages.getString("browse_label"));
	}

	private void enableTimerProperty(boolean enable) {
		unitCombo.setEnabled(enable);
		eventText.setEnabled(enable);
		expressionText.setEnabled(enable);
		expressionButton.setEnabled(enable);
		eventResourceButton.setEnabled(enable);
		createHyperlink.setEnabled(enable);
		if (enable) {
			if (fTSENode != null) {
				EObject userObject = (EObject) fTSENode.getUserObject();
				if (userObject != null
						&& BpmnModelClass.ACTIVITY.isSuperTypeOf(userObject
								.eClass())) {
					EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper
							.wrap(userObject);
					EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
							.getAddDataExtensionValueWrapper(wrapper);
					if (valueWrapper != null) {
						if (valueWrapper
								.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_ENABLED)) {
							EObject timerData = valueWrapper
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_DATA);
							if (timerData != null) {
								EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
										.wrap(timerData);
								String exp = wrap
										.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION);
								if (exp != null)
									expressionText.setText(exp);
								else
									expressionText.setText("");

								String event = wrap
										.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT);
								if (event != null)
									eventText.setText(event);
								else
									eventText.setText("");

								EEnumLiteral unit = wrap
										.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_UNIT);
								unitCombo.setText(unit.getLiteral());

								return;
							}else{
								unitCombo.setText(BpmnModelClass.ENUM_TIME_UNITS_Milliseconds.getLiteral());
								eventText.setText("");
								expressionText.setText("");
								return;
							}
						}
					}
				}
			}
		}

		unitCombo.setText("");
		eventText.setText("");
		expressionText.setText("");
	}

	/*
	 * @see
	 * org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh
	 * ()
	 */
	public void refresh() {
		this.refresh = true;
		if (fTSENode != null) {
			super.refresh();
			enableButton.setSelection(false);
			EObject userObject = (EObject) fTSENode.getUserObject();
			if (userObject != null
					&& BpmnModelClass.ACTIVITY.isSuperTypeOf(userObject
							.eClass())) {
				EClass nodeType = (EClass) fTSENode
						.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
				EClass nodeExtType = (EClass) fTSENode
						.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
				EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper
						.wrap(userObject);
				//Get Help for the Node from the Palette Model
				BpmnPaletteModel _toolDefinition = PaletteLoader.getBpmnPaletteModel(fProject);
				if (_toolDefinition != null) {
					String id = (String) ExtensionHelper.getExtensionAttributeValue(wrapper,	BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID);
					BpmnPaletteGroupItem item = null;
					if (id != null && !id.isEmpty()) {
						item = _toolDefinition.getPaletteItemById(id);
					} 
					if(item == null){
						List<BpmnPaletteGroupItem> paletteToolByType = _toolDefinition
								.getPaletteToolItemByType(nodeType, nodeExtType);
						if (paletteToolByType.size() > 0)
							item = paletteToolByType.get(0);
					}
					if (item != null) {
						String h = /*item.getHelp(BpmnUIConstants.TIMEOUT_HELP_SECTION)*/PaletteHelpTextGenerator.getHelpText(item,BpmnUIConstants.TIMEOUT_HELP_SECTION);
						if(h!= null){
							help = h.replace("&nbsp;", "").replace("<ul class=\"noindent\">", "").replace("</ul>", "");
							help = "<form>" + help + "</form>";
							browser.setText(help, true, false);
						}
					}
				}
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(wrapper);
				if (valueWrapper != null) {
					if (valueWrapper
							.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_ENABLED)) {
						Boolean enabled = valueWrapper
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_ENABLED);
						enableButton.setSelection(enabled);
						enableTimerProperty(enabled);
					} else {
						enableTimerProperty(false);
						enableButton.setSelection(false);
					}
				}
			}

		}

		this.refresh = false;
	}

	private EObjectWrapper<EClass, EObject> createTimerData() {
		return EObjectWrapper
				.createInstance(BpmnModelClass.EXTN_TIMER_TASK_DATA);
	}

	@Override
	protected void updatePropertySection(Map<String, Object> updateList) {
		if(updateList.size() == 0)
			return;
		if(fTSENode != null){
			BpmnGraphUtils.fireUpdate(updateList,fTSENode ,fPropertySheetPage.getEditor().getBpmnGraphDiagramManager());
		}

	}

	private String openXpathDialogBox() {
		EObject data = (EObject) fTSENode.getUserObject();
		final EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(data);
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
				.getAddDataExtensionValueWrapper(wrap);
		EObject timerData = valueWrapper
				.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_DATA);
		if (timerData == null)
			timerData = createTimerData().getEInstance();
		final EObjectWrapper<EClass, EObject> timerDataWrap = EObjectWrapper
				.wrap(timerData);
		final String oldValue = timerDataWrap
				.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION);

		Display.getCurrent().syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					EObjectWrapper<EClass, EObject> modelRoot = getDiagramManager()
							.getModelController().getModelRoot();
					TimerTaskExpressionXPathWizard wizard = new TimerTaskExpressionXPathWizard(
							getProject(), wrap, modelRoot,
							new XPathIntegerExpressionValidator());
					WizardDialog dialog = new WizardDialog(fEditor.getSite()
							.getShell(), wizard) {
						@Override
						protected void createButtonsForButtonBar(
								Composite parent) {
							super.createButtonsForButtonBar(parent);
							Button finishButton = getButton(IDialogConstants.FINISH_ID);
							finishButton.setText(IDialogConstants.OK_LABEL);
						}
					};
					dialog.setMinimumPageSize(700, 500);
					try {
						dialog.create();
					} catch (RuntimeException e) {
						if (e.getCause() instanceof InterruptedException) {
							return;
						}
					}
					int open = dialog.open();
					if (open == Window.OK) {
						String xPath = wizard.getXPath();
						if (oldValue == null || !oldValue.equals(xPath)) {
							timerDataWrap
									.setAttribute(
											BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION,
											xPath);
							HashMap<String, Object> updateList = new HashMap<String, Object>();
							EObject copy = EcoreUtil.copy(timerDataWrap.getEInstance());
							updateList
									.put(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_DATA,
											copy);
							updatePropertySection(updateList);
						}
					}
				} catch (Exception e) {
					BpmnUIPlugin.log(e);
				}
			}
		});

		String newValue = timerDataWrap
				.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION);
		
		if(newValue == null)
			newValue = "";
		
		return newValue;
	}

	private boolean isRefresh() {
		return refresh;
	}

	protected StudioFilteredResourceSelectionDialog getSeletionDialog() {
		Object input = getProject();

		String project = ((IProject) input).getName();

		boolean onlyRF = false;
		ELEMENT_TYPES[] types = new ELEMENT_TYPES[] { ELEMENT_TYPES.TIME_EVENT };

		return new StudioFilteredResourceSelectionDialog(Display.getDefault()
				.getActiveShell(), project, eventText.getText().trim(), types,
				true, false, onlyRF);
	}

	public void resourceBrowse() {
		StudioFilteredResourceSelectionDialog selectionDialog = getSeletionDialog();
		Set<String> extensions = new HashSet<String>();
		extensions.add(IndexUtils.getFileExtension(ELEMENT_TYPES.TIME_EVENT));
		selectionDialog.addFilter(new RuleBasedTimerEventOnlyFilter((extensions)));
		int status = selectionDialog.open();
		if (status == StudioResourceSelectionDialog.OK) {
			Object element = selectionDialog.getFirstResult();
			String path = "";
			if (element instanceof IFile) {
				IResource res = (IFile) element;
				path = IndexUtils.getFullPath(res);
				eventText.setText(path);
			} else if (element instanceof SharedEntityElement) {
				path = ((SharedEntityElement) element).getSharedEntity()
						.getFullPath();
				if(!path.startsWith("/"))
					path="/"+path;
				eventText.setText(path);
			}
		}
	}
	
	protected boolean isValidEventResource(){
		String resourcePath = eventText.getText();
		boolean valid = false;
		if(!resourcePath.trim().isEmpty()){
			resourcePath = resourcePath.replace("\\", "/");
			Event element = IndexUtils.getTimeEvent(fProject.getName(), resourcePath);
			
			if(element != null){
					if(element instanceof TimeEvent){
						TimeEvent timeEvent = (TimeEvent) element;
						EVENT_SCHEDULE_TYPE scheduleType = timeEvent.getScheduleType();
						valid=  (scheduleType == EVENT_SCHEDULE_TYPE.RULE_BASED);
					}
			} 
			if (valid) {
				eventText.setForeground(COLOR_BLACK);
			} else{
				eventText.setForeground(COLOR_RED);
				resourcePath = null;
			}	
		}else{
			eventText.setForeground(COLOR_BLACK);
			valid = true;
		}

		return valid;
	}

	private class WidgetListener implements SelectionListener, ModifyListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			if(isRefresh())
				return;
			Widget widget = e.widget;
			if (widget == expressionButton) {
				String value = openXpathDialogBox();
				expressionText.setText(value);
			} else if (widget == eventResourceButton) {
				resourceBrowse();
			} else if (widget == enableButton) {
				boolean sel = enableButton.getSelection();
				HashMap<String, Object> updateList = new HashMap<String, Object>();
				updateList.put(
						BpmnMetaModelExtensionConstants.E_ATTR_TIMER_ENABLED,
						sel);
				updatePropertySection(updateList);
				refresh = true;
				enableTimerProperty(sel);
				refresh = false;
			}

		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}

		@Override
		public void modifyText(ModifyEvent e) {
			if (isRefresh()) {
				return;
			}
			EObject data = (EObject) fTSENode.getUserObject();
			final EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
					.wrap(data);
			EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(wrap);
			EObject timerData = valueWrapper
					.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_DATA);
			if(timerData == null)
				timerData = createTimerData().getEInstance();
			if (timerData != null) {
				EObjectWrapper<EClass, EObject> timerDataWrap = EObjectWrapper
						.wrap(timerData);
				HashMap<String, Object> updateList = new HashMap<String, Object>();
				Widget widget = e.widget;
				if (widget == eventText) {
					boolean validEventResource = isValidEventResource();
					if (validEventResource) {
						String oldValue = timerDataWrap
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT);
						if (oldValue == null
								|| !oldValue.equals(eventText.getText())) {

							timerDataWrap
									.setAttribute(
											BpmnMetaModelExtensionConstants.E_ATTR_EVENT,
											eventText.getText());
							EObject copy = EcoreUtil.copy(timerDataWrap
									.getEInstance());
							updateList
									.put(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_DATA,
											copy);
						}

					}
				} else if (widget == unitCombo) {
					String unitText = unitCombo.getText();
					if (unitText != null && unitText.trim().length() > 0) {
						EEnumLiteral lit = timerDataWrap
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_UNIT);
						if (!lit.getLiteral().equals(unitText)) {
							if (unitText
									.equals(BpmnModelClass.ENUM_TIME_UNITS_Hours
											.getLiteral())) {
								timerDataWrap
										.setAttribute(
												BpmnMetaModelExtensionConstants.E_ATTR_UNIT,
												BpmnModelClass.ENUM_TIME_UNITS_Hours);
							} else if (unitText
									.equals(BpmnModelClass.ENUM_TIME_UNITS_Seconds
											.getLiteral())) {
								timerDataWrap
										.setAttribute(
												BpmnMetaModelExtensionConstants.E_ATTR_UNIT,
												BpmnModelClass.ENUM_TIME_UNITS_Seconds);
							} else if (unitText
									.equals(BpmnModelClass.ENUM_TIME_UNITS_Minutes
											.getLiteral())) {
								timerDataWrap
										.setAttribute(
												BpmnMetaModelExtensionConstants.E_ATTR_UNIT,
												BpmnModelClass.ENUM_TIME_UNITS_Minutes);
							} else if (unitText
									.equals(BpmnModelClass.ENUM_TIME_UNITS_Days
											.getLiteral())) {
								timerDataWrap
										.setAttribute(
												BpmnMetaModelExtensionConstants.E_ATTR_UNIT,
												BpmnModelClass.ENUM_TIME_UNITS_Days);
							} else {
								timerDataWrap
										.setAttribute(
												BpmnMetaModelExtensionConstants.E_ATTR_UNIT,
												BpmnModelClass.ENUM_TIME_UNITS_Milliseconds);
							}
							EObject copy = EcoreUtil.copy(timerDataWrap.getEInstance());
							updateList
									.put(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_DATA,
											copy);
						}

					}
				}

				updatePropertySection(updateList);
			}

		}

	}
	
	@Override
	protected void createHelpPartControl(IManagedForm form, Composite parent) {
		FormToolkit toolkit = form.getToolkit();
		helpSection = getWidgetFactory().createSection(parent, Section.TITLE_BAR | Section.EXPANDED);
		helpSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		helpSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		helpSection.setText(BpmnMessages.getString("helpSection_Label"));
		
//		configureHelpToolBar(helpSection); TODO: later
		
		browserComposite = getWidgetFactory().createComposite(helpSection);
		helpSection.setClient(browserComposite);
		
		browserComposite.setLayout(new GridLayout(1,false));
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 500;
    	browserComposite.setLayoutData(data);

    	createBrowserContentArea(browserComposite);
    	data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 100;
		data.heightHint = 500;
		browser.setLayoutData(data);
	}
	
	protected void createBrowserContentArea(Composite parent) {
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		browser = toolkit.createFormText(parent, true);
		browser.setForeground(new Color(Display.getDefault(), 63, 95, 191));
	}
	
	@Override
	public IFile getEntityFile() {
		return newFile;
	}

	@Override
	public void setEntityFile(IFile file) {
	      this.newFile = file; 
	}

}