package com.tibco.cep.studio.decision.table.ui.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.metadata.DecisionTableMetadataFeature;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.Property;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.studio.decision.table.calendar.PropertyCalendar;
import com.tibco.cep.studio.decision.table.calendar.PropertyCalendar.DatePropertyType;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.decision.table.utils.Messages;

public class DTGeneralPropertySection extends AbstractDTPropertySection {

protected Composite composite;
    
	private Text rTableName;
	
	private Text reffectiveDate;
	
	private Text rterminateDate;
	
	private Button rsingleRowExecution;
	
	private Combo rtablePriority;
	
	private Text rlastModifiedBy;
	
	private Text rversionInfo;
	
	private Text rimpl;
	
    
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(4, false));
		createGeneralTablePropertyPage(getWidgetFactory());
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.decision.table.ui.properties.AbstractDTPropertySection#disablePropertyPage()
	 */
	public void disablePropertyPage() {
		rTableName.setEnabled(false);		
		reffectiveDate.setEnabled(false);		
		rterminateDate.setEnabled(false);		
		rsingleRowExecution.setEnabled(false);		
		rtablePriority.setEnabled(false);		
		rlastModifiedBy.setEnabled(false);		
		rversionInfo.setEnabled(false);		
		rimpl.setEnabled(false);		
	}
		
	/**
	 * @param widgetFactory
	 */
	private void createGeneralTablePropertyPage(TabbedPropertySheetWidgetFactory widgetFactory) {
		widgetFactory.createLabel(composite, "Name");
		rTableName = widgetFactory.createText(composite, "", SWT.READ_ONLY); 
		rTableName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		widgetFactory.createLabel(composite, "");
		widgetFactory.createLabel(composite, "");

		widgetFactory.createLabel(composite, "Effective Date");

		reffectiveDate = widgetFactory.createText(composite, "", SWT.READ_ONLY);
		reffectiveDate.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Button effDatebutton = widgetFactory.createButton(composite, "", SWT.PUSH);
		effDatebutton.setImage(DecisionTableUIPlugin.getDefault().getImage("icons/calendar.gif"));
		effDatebutton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				new PropertyCalendar(editor.getSite().getShell(), reffectiveDate, 
						                                          rterminateDate,
						                                          DatePropertyType.EFFECTIVE).open();
			}
		});
		
		final Button deleteEffDateButton = widgetFactory.createButton(composite, "", SWT.PUSH);
		deleteEffDateButton.setVisible(false);
		deleteEffDateButton.setImage(DecisionTableUIPlugin.getDefault().getImage("icons/delete_calandar.gif"));
		deleteEffDateButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				postDelete(reffectiveDate, DecisionTableMetadataFeature.EFFECTIVE_DATE.getFeatureName());
				deleteEffDateButton.setVisible(false);
				
			}
		});
		
		reffectiveDate.addModifyListener(new ModifyListener() {

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			@Override
			public void modifyText(ModifyEvent e) {
				Property effectiveDateFeature = null;
				MetaData metaData = tableEModel.getMd();
				if (metaData == null) {
					metaData = DtmodelFactory.eINSTANCE.createMetaData();
					tableEModel.setMd(metaData);
				}
				String oldValue = "";
				if ((effectiveDateFeature = metaData.search(DecisionTableMetadataFeature.EFFECTIVE_DATE.getFeatureName())) != null
						&& effectiveDateFeature.getValue() != null) {
					oldValue = effectiveDateFeature.getValue();
				}
				updateProperty(DecisionTableMetadataFeature.EFFECTIVE_DATE.getFeatureName(),
						       oldValue,
					           reffectiveDate.getText(), 
					           "String");
				deleteEffDateButton.setVisible(true);
			}
		});

		
		

		widgetFactory.createLabel(composite, "Expiration Date");

		rterminateDate = widgetFactory.createText(composite, "", SWT.READ_ONLY);
		rterminateDate.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Button termDatebutton = widgetFactory.createButton(composite, "", SWT.PUSH);
		termDatebutton.setImage(DecisionTableUIPlugin.getDefault().getImage("icons/calendar.gif"));
		termDatebutton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				new PropertyCalendar(editor.getSite().getShell(), rterminateDate, 
						                                          reffectiveDate,
						                                          DatePropertyType.TERMINATE).open();
			}
		});
		final Button deleteTermDateButton = widgetFactory.createButton(composite, "",SWT.PUSH);
		deleteTermDateButton.setVisible(false);
		deleteTermDateButton.setImage(DecisionTableUIPlugin.getDefault().getImage("icons/delete_calandar.gif"));
		deleteTermDateButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				postDelete(rterminateDate, DecisionTableMetadataFeature.EXPIRY_DATE.getFeatureName());
				deleteTermDateButton.setVisible(false);
				
			}
		});
		rterminateDate.addModifyListener(new ModifyListener() {

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			@Override
			public void modifyText(ModifyEvent e) {
				Property expiryDateFeature = null;
				MetaData metaData = tableEModel.getMd();
				String oldValue = "";
				if ((expiryDateFeature = metaData.search(DecisionTableMetadataFeature.EXPIRY_DATE.getFeatureName())) != null
						&& expiryDateFeature.getValue() != null) {
					oldValue = expiryDateFeature.getValue();
				}
				updateProperty(DecisionTableMetadataFeature.EXPIRY_DATE.getFeatureName(), 
					           oldValue,
					           rterminateDate.getText(), 
					           "String");
				deleteTermDateButton.setVisible(true);
			}
		});
		
		
		widgetFactory.createLabel(composite, Messages.getString("SINGLE_ROW_EXECUTION"));

		rsingleRowExecution = widgetFactory.createButton(composite, "", SWT.CHECK);
		rsingleRowExecution.setSelection(false);
		rsingleRowExecution.addSelectionListener(new SelectionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				Property rowExecutionFeature = null;
				MetaData metaData = tableEModel.getMd();
				if (metaData == null) {
					metaData = DtmodelFactory.eINSTANCE.createMetaData();
					tableEModel.setMd(metaData);
				}
				String oldValue = "";
				if ((rowExecutionFeature = metaData.search(DecisionTableMetadataFeature.SINGLE_ROW_EXECUTION.getFeatureName())) != null) {
					oldValue = rowExecutionFeature.getValue();
				}
				//Added SingleRowExecution boolean property
				updateProperty(DecisionTableMetadataFeature.SINGLE_ROW_EXECUTION.getFeatureName(), 
						       oldValue,
						       Boolean.toString(rsingleRowExecution.getSelection()), 
						       "Boolean");
			}
		});
		widgetFactory.createLabel(composite, "");
		widgetFactory.createLabel(composite, "");
		widgetFactory.createLabel(composite, Messages.getString("TABLE_PRIORITY"));
		rtablePriority =new Combo(composite,SWT.READ_ONLY | SWT.BORDER);
		GridData gd = new GridData();
		gd.widthHint = 200;
		rtablePriority.setLayoutData(gd);
		rtablePriority.setItems(new String[] { "1", "2", "3", "4", "5", "6","7", "8", "9", "10" });
		rtablePriority.setText("5");
		rtablePriority.addSelectionListener(new SelectionAdapter() {
			
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				Property priorityFeature = null;
				MetaData metaData = tableEModel.getMd();
				if (metaData == null) {
					metaData = DtmodelFactory.eINSTANCE.createMetaData();
					tableEModel.setMd(metaData);
				}
				String oldValue = "";
				if ((priorityFeature = metaData.search(DecisionTableMetadataFeature.PRIORITY.getFeatureName())) != null) {
					oldValue = priorityFeature.getValue();
				}
				updateProperty(DecisionTableMetadataFeature.PRIORITY.getFeatureName(),
						       oldValue,
					           rtablePriority.getText(), 
					           "Integer");
			}
		});
		widgetFactory.createLabel(composite, "");
		widgetFactory.createLabel(composite, "");
		widgetFactory.createLabel(composite, "Last Modified By");
		rlastModifiedBy = widgetFactory.createText(composite, "", SWT.READ_ONLY); 
		rlastModifiedBy.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		widgetFactory.createLabel(composite, "");
		widgetFactory.createLabel(composite, "");

		widgetFactory.createLabel(composite, "Version");
		rversionInfo = widgetFactory.createText(composite, "", SWT.READ_ONLY); 
		rversionInfo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		rversionInfo.setEnabled(false);
		widgetFactory.createLabel(composite, "");
		widgetFactory.createLabel(composite, "");
		widgetFactory.createLabel(composite, "Implements");
		rimpl = widgetFactory.createText(composite, "", SWT.READ_ONLY); 
		rimpl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if (tableEModel != null) {
			setTableData(tableEModel);
		}
	}

	/**
	 * @param text
	 * @param propertyName
	 */
	private void postDelete(Text text, String propertyName){
		if (tableEModel != null) {
			Property dateFeature = null;
			MetaData metaData = tableEModel.getMd();
			String oldValue = "";
			if ((dateFeature = metaData.search(propertyName)) != null) {
					oldValue = dateFeature.getValue();
			}
			updateProperty(propertyName,
						       oldValue,
					           text.getText(), 
					           "String");
			text.setText("");
		}
	}
	
	/**
	 * @param propertyName
	 * @param newValue
	 * @param type
	 */
	private void updateProperty(String propertyName,
			                    String oldValue,
			                    String newValue, 
			                    String dataType) {
		if (tableEModel != null) {
			if (oldValue != null && oldValue.equals(newValue)) {
				return;
			}
			DecisionTableMetadataFeature metadataFeature = 
				DecisionTableMetadataFeature.getByFeatureName(propertyName);
			//TODO Execute model updates through the command framework only.
			CommandFacade.getInstance().
				executeTableMetadataUpdate(project, 
						                   tableEModel, 
						                   tableEModel,
						                   metadataFeature, 
						                   newValue, 
						                   dataType);
		}
	}
	
	/**
	 * @param tableEModel
	 */
	private void setTableData(final Table tableEModel) {
		try {
			if (tableEModel != null) {
				rTableName.setText(tableEModel.getName());

				if (tableEModel.getImplements() != null) {
					rimpl.setText(tableEModel.getImplements());
				}

				if (tableEModel.getVersion() != -1) {
					rversionInfo.setText(new Double(tableEModel.getVersion()).toString());
				}

				if (tableEModel.getLastModifiedBy() != null) {
					rlastModifiedBy.setText(tableEModel.getLastModifiedBy());
				}

				rsingleRowExecution.setSelection(false);
				rtablePriority.setText("5");
				MetaData metaData = tableEModel.getMd();
				if (metaData == null) {
					return;
				}
				String value = "";
				Property effectiveDateFeature = null;
				if ((effectiveDateFeature = metaData.search(DecisionTableMetadataFeature.EFFECTIVE_DATE.getFeatureName())) != null) {
					value = effectiveDateFeature.getValue();
					if (value != null && !value.equalsIgnoreCase("")) {
						reffectiveDate.setText(value);
					}
				}
				Property expiryDateFeature = null;
				if ((expiryDateFeature = metaData.search(DecisionTableMetadataFeature.EXPIRY_DATE.getFeatureName())) != null) {
					value = expiryDateFeature.getValue();
					if (value != null && !value.equalsIgnoreCase("")) {
						rterminateDate.setText(value);
					}
				}
				Property singleRowExecutionFeature = null;
				if ((singleRowExecutionFeature = metaData.search(DecisionTableMetadataFeature.SINGLE_ROW_EXECUTION.getFeatureName())) != null) {
					value = singleRowExecutionFeature.getValue();
					if (value != null && !value.equalsIgnoreCase("")) {
						rsingleRowExecution.setSelection(Boolean.parseBoolean(value));
					}
				}
				Property priorityFeature = null;
				if ((priorityFeature = metaData.search(DecisionTableMetadataFeature.PRIORITY.getFeatureName())) != null) {
					value = priorityFeature.getValue();
					if (value != null) {
						rtablePriority.setText(value);
					}
				}
			} 
		} catch (Exception pe) {
			DecisionTableUIPlugin.log(pe);
		}
	}
}
