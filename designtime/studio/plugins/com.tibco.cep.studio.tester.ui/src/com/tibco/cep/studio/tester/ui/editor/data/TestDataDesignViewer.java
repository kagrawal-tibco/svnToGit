package com.tibco.cep.studio.tester.ui.editor.data;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.addHyperLinkFieldListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.tester.core.utils.TesterCoreUtils;
import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.tester.ui.utils.TesterUIUtils;
import com.tibco.cep.studio.tester.utilities.TestDataPropertiesTable;
import com.tibco.cep.studio.ui.util.ColorConstants;
import com.tibco.cep.studio.ui.wizards.PreprocessorFileFilter;
import com.tibco.cep.studio.ui.wizards.RuleFunctionSelector;


/**
 * 
 * @author sasahoo
 * 
 */
public class TestDataDesignViewer extends AbstractTestViewer {

	private boolean invalidPart;
	private boolean isScorecard = false;
	private Hyperlink resourceLink;
	
	public TestDataPropertiesTable testerPropertiesTable;
	/**
	 * @param testDataEditor
	 */
	public TestDataDesignViewer(TestDataEditor testDataEditor, boolean invalidPart) {
		this.editor = testDataEditor;
		projectName = testDataEditor.getProjectName();
		this.invalidPart = invalidPart;
		isScorecard = testDataEditor.getEntity() instanceof Scorecard;
	}

	/**
	 * 
	 * @param _container
	 */
	public void createPartControl(Composite _container) {
		this.container = _container;
		managedForm = new ManagedForm(container);
		if (!invalidPart) {
			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			getForm().getBody().setLayout(layout);
			ScrolledForm form = getForm();
			FormToolkit toolkit = managedForm.getToolkit();
			createFormParts(form, toolkit);
		}
		managedForm.initialize();
	}

	/**
	 * 
	 * @param form
	 * @param toolkit
	 */
	@SuppressWarnings("serial")
	private void createFormParts(final ScrolledForm form,final FormToolkit toolkit) {
		
		
		
		testDatasection = toolkit.createSection(form.getBody(),
				Section.EXPANDED | Section.TITLE_BAR | Section.LEFT_TEXT_CLIENT_ALIGNMENT /* | Section.TWISTIE */);
		Composite sectionClient = toolkit.createComposite(testDatasection);
		testDatasection.setClient(sectionClient);
		testDatasection.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		EList<PropertyDefinition> list = null;
		
		if(editor.getEntity() instanceof Scorecard){
			list= 	((Scorecard)editor.getEntity()).getAllProperties();
		}
		else if(editor.getEntity() instanceof Event){
			list= 	((Event)editor.getEntity()).getAllUserProperties();
		}
		else if(editor.getEntity() instanceof Concept){
			list= 	((Concept)editor.getEntity()).getAllProperties();
					
		}
		
		LinkedHashMap<String,String> tableColumnsWithType=new LinkedHashMap<String,String>();
		ArrayList<String> actualColumnNames=new ArrayList<String>();
		
		
		
		tableColumnsWithType.put("Use","use");
		
		
		
		if(editor.getEntity() instanceof Event){
			if (TesterCoreUtils.isPayLoadPropertyUIEnabled) {
				actualColumnNames.add("Payload");
				tableColumnsWithType.put("Payload","payload");
			}
		}
		actualColumnNames.add("ExtId");
		tableColumnsWithType.put("ExtId", "ExtId");
		Iterator<PropertyDefinition> it=list.iterator();
		while(it.hasNext()){
			PropertyDefinition pd=it.next();
			if(pd.getDomainInstances().size()>0){
				tableColumnsWithType.put(pd.getName(),pd.getType().toString()+"-Domain");
				actualColumnNames.add(pd.getName());
			}
			else if(pd.isArray()){
				tableColumnsWithType.put(pd.getName(),pd.getType().toString()+"-Multiple");
				actualColumnNames.add(pd.getName());
			}
			else{
				tableColumnsWithType.put(pd.getName(),pd.getType().toString());
				actualColumnNames.add(pd.getName());
			}
		}
		
		actualColumnNames.add(TesterCoreUtils.UID_COL_NAME);
		tableColumnsWithType.put(TesterCoreUtils.UID_COL_NAME, "String");
		
		
		testerPropertiesTable=new TestDataPropertiesTable(sectionClient, editor.getEntity(), tableColumnsWithType, editor,actualColumnNames);
		Object table=testerPropertiesTable.createTable(sectionClient, editor.getEntity());
		if(table!=null){
			testerPropertiesTable.createCellEditor(tableColumnsWithType);
			toolkit.paintBordersFor(sectionClient);
		}
		
		//Adding resource Link for browsing to associated entity
		//----------------------------------------
		resourceLink = toolkit.createHyperlink(testDatasection,"Test Data - " + editor.getEntity().getName() + "[" + editor.getEntity().getFolder() + "]" ,SWT.FILL);
		resourceLink.setText("Test Data - " + editor.getEntity().getName() + "[" + editor.getEntity().getFolder() + "]");
		resourceLink.setFont(DEFAULT_FONT);
		resourceText = toolkit.createText(testDatasection, "", SWT.FILL);
		resourceLink.setBackground(null);
		
		GridData gd = new GridData();
		gd.widthHint= 400;
			
		resourceText.setLayoutData(gd);
		resourceText.setText(TesterUIUtils.getResourcePath(editor.getEntity(),editor.getEntity().getFullPath()));
		resourceText.setEditable(false);
		resourceText.setEnabled(false);
		resourceText.setVisible(false);
		addHyperLinkFieldListener(resourceLink, resourceText, editor, editor.getProject().getName(), false, true);

		//----------------------------------------
		
		testDatasection.setTextClient(resourceLink);
		
		createExecutionSection(form, toolkit);
		
	}

	private void createExecutionSection(final ScrolledForm form, final FormToolkit toolkit) {
		LinkedHashMap<String,String> properties = testerPropertiesTable.getModel().getProperties();
		
		Section section = toolkit.createSection(form.getBody(), Section.DESCRIPTION | Section.TITLE_BAR);
		section.setText("Test data options"); //$NON-NLS-1$
		section.setDescription("Specify additional runtime/assertion details below");
		section.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// Composite for storing the data
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout layout = new GridLayout(3, false);
		layout.marginWidth = 2;
		layout.marginHeight = 15;
		
		client.setLayout(layout);

		toolkit.paintBordersFor(client);
		
		boolean event = editor.getEntity() instanceof Event;
		if(event){
			String preProc = properties.get("preprocessor");
			boolean invokePreprocessor = preProc != null && !preProc.isEmpty() ? true : false;
			final Button b = toolkit.createButton(client, "Invoke preprocessor while asserting Events", SWT.CHECK); //$NON-NLS-1$
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.heightHint = 20;
			gd.widthHint = 100;
//			gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
			gd.horizontalSpan = 3;
			b.setLayoutData(gd);
			b.setSelection(invokePreprocessor);
			b.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					testerPropertiesTable.updateData();
					editor.modified();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});
			toolkit.createLabel(client, "Preprocessor:");
			final Text preprocURIText = toolkit.createText(client, "", SWT.BORDER);
			gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
			gd.widthHint = 300;
			preprocURIText.setLayoutData(gd);
			if (preProc != null) {
				preprocURIText.setText(preProc);
			}

			preprocURIText.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					preprocURIText.setForeground(ColorConstants.black);
					preprocURIText.setToolTipText("");
					testerPropertiesTable.getModel().getProperties().put("preprocessor", preprocURIText.getText());
					testerPropertiesTable.updateData();
					editor.modified();
					if (preprocURIText.getText() != null && !preprocURIText.getText().isEmpty()) {
						RuleElement element = IndexUtils.getRuleElement(projectName, preprocURIText.getText(), ELEMENT_TYPES.RULE_FUNCTION);
						if (element == null) {
							preprocURIText.setForeground(ColorConstants.red);
							preprocURIText.setToolTipText("Rule Function does not exist");
							return;
						} else {
							Symbols symbols = element.getRule().getSymbols();
							if (symbols.getSymbolList().size() != 1) {
								preprocURIText.setForeground(ColorConstants.red);
								preprocURIText.setToolTipText("Preprocessors must specify the associated event as the only scope argument");
							} else {
								Symbol symbol = symbols.getSymbolList().get(0);
								String type = symbol.getType();
								if (!type.equals(editor.entity.getFullPath())) {
									preprocURIText.setForeground(ColorConstants.red);
									preprocURIText.setToolTipText("Preprocessors must specify the associated event as the only scope argument");
								}
							}
						}
					}
				}
			});
			
			Button browseBtn = toolkit.createButton(client, "Browse...", SWT.NONE);
			browseBtn.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					RuleFunctionSelector picker = new RuleFunctionSelector(Display.getDefault().getActiveShell(),
							editor.getProject().getName(),
							preprocURIText.getText(), false);
					picker.addFilter(new PreprocessorFileFilter(editor.getEntity()));
					if (picker.open() == Dialog.OK) {
						if(picker.getFirstResult() != null){
							preprocURIText.setText(picker.getFirstResult().toString());
						}
					}
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});
		}
		
		Group assertionGroup = new Group(client, SWT.NONE);
		GridLayout layout2 = new GridLayout(3, false);
		layout2.marginHeight = 10;
		layout2.marginWidth = 5;
		assertionGroup.setLayout(layout2);
		GridData gd = new GridData();
		gd.horizontalSpan = 3;
		assertionGroup.setLayoutData(gd);
		assertionGroup.setText("Assertion rate:");
		
		final Button afapBtn = new Button(assertionGroup, SWT.RADIO);
		afapBtn.setText("As fast as possible");
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		gd.horizontalSpan = 3;
		afapBtn.setLayoutData(gd);
		
		final Button rateBtn = new Button(assertionGroup, SWT.RADIO);
		String rateVal = properties.get("rate");
		
		final Spinner rateSpinner = new Spinner(assertionGroup, SWT.NONE);
		rateSpinner.setMinimum(0);
		rateSpinner.setSelection(10);
		if (rateVal != null) {
			int rate = Integer.parseInt(rateVal);
			if (rate == -1) {
				afapBtn.setSelection(true);
				rateSpinner.setEnabled(false);
			} else {
				rateBtn.setSelection(true);
				rateSpinner.setSelection(rate);
			}
		} else {
			afapBtn.setSelection(true);
			rateSpinner.setEnabled(false);
		}
//		rateSpinner.setMaximum(12);
		rateSpinner.setIncrement(1);
//		rateSpinner.setBackground(COLOR_WHITE);
		rateSpinner.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				testerPropertiesTable.getModel().getProperties().put("rate", rateSpinner.getText());
				testerPropertiesTable.updateData();
				editor.modified();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		String label = event ? "Events per second" : "Concepts per second";
		toolkit.createLabel(assertionGroup, label);
		
		afapBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				rateSpinner.setEnabled(!afapBtn.getSelection());
				testerPropertiesTable.getModel().getProperties().put("rate", "-1");
				testerPropertiesTable.updateData();
				editor.modified();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		rateBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				testerPropertiesTable.getModel().getProperties().put("rate", rateSpinner.getText());
				testerPropertiesTable.updateData();
				editor.modified();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		section.setClient(client);
	}

	/**
	 * @param filename
	 * @param vectors
	 */
	@SuppressWarnings("rawtypes")
	protected void resultDataToFile(String filename, Vector<Vector<? extends Object>> vectors) {

		BufferedWriter bufWriter = null;
		File f = new File(filename);
		File parent = f.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		try {

			bufWriter = new BufferedWriter(new FileWriter(filename));
			String[] rows = new String[vectors.size()];

			for (int i = 0; i < vectors.size(); i++) {
				boolean first = true;
				Vector v = vectors.elementAt(i);
				if (v == null) {
					String message = "vectors.elementAt(" + i + ") was null\n";
					bufWriter.write(message);
					continue;
				}
				int rowlength = v.size();

				for (int j = 0; j < rowlength; j++) {
					if (first == false) {
						bufWriter.write(TesterCoreUtils.DELIMITER);
					}
					rows[i] = (String) (v.elementAt(j));
					if (rows[i] != null)
						bufWriter.write(rows[i]);

					if (first) {
						first = false;
					}
				}
				bufWriter.write("\n");
			}
			bufWriter.close();
		} catch (IOException e) {
			StudioTesterUIPlugin.log(e);
		}
	}

	
	/**
	 * @return
	 */
	public Control getControl() {
		return getForm();
	}

	/**
	 * @return
	 */
	private ScrolledForm getForm() {
		return managedForm.getForm();
	}

}