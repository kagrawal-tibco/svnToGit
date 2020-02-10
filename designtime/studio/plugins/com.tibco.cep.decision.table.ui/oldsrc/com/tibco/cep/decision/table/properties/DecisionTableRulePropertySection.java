package com.tibco.cep.decision.table.properties;

import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.jidesoft.decision.DecisionTablePane;
import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.metadata.RuleMetadataFeature;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.utils.Messages;

/**
 * Handle updates to metadata of rule from property section here.
 * @author sasahoo
 * @author aathalye
 *
 */
public class DecisionTableRulePropertySection extends AbstractDecisionTablePropertySection {
	
	protected Text ruleID;
	
	protected Text ruleDesc;
	
	protected Combo rulePriorities;
	
    protected Composite composite;

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));
		createRuleSection(getWidgetFactory());
	}
	
	/**
	 * @param widgetFactory
	 */
	private void createRuleSection(TabbedPropertySheetWidgetFactory widgetFactory) {
		widgetFactory.createLabel(composite, Messages.getString("RULE_META_DATA_VIEW_RULE_TAB_META_ID"));
		ruleID = widgetFactory.createText(composite, ""); 
		ruleID.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		ruleID.setEnabled(false);
		widgetFactory.createLabel(composite,Messages.getString("RULE_META_DATA_VIEW_RULE_TAB_META_DESC"));
		ruleDesc = widgetFactory.createText(composite, "", SWT.MULTI | SWT.V_SCROLL); 
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 50;

		ruleDesc.setLayoutData(data);
		ruleDesc.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				updateProperty(RuleMetadataFeature.DESCRIPTION.getFeatureName(), ruleDesc.getText());
			}});

		widgetFactory.createLabel(composite, "Priority");
		rulePriorities = new Combo(composite, SWT.READ_ONLY | SWT.BORDER); 
		rulePriorities.setItems(new String[] { "1", "2", "3", "4", "5", "6","7", "8", "9", "10" });
		rulePriorities.setText("5");

		data = new GridData();
		data.widthHint = 200;
		rulePriorities.setLayoutData(data);
		rulePriorities.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				updateProperty(RuleMetadataFeature.PRIORITY.getFeatureName(), rulePriorities.getText());
			}});
	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if (decisionTableRuleSet != null) {
			try {
				setRuleData();
			} catch (Exception e) {
				DecisionTableUIPlugin.log(e);
			}
		}
	}
	
	private void setRuleData() throws Exception{
		EList<TableRule> rule = decisionTableRuleSet.getRule();
		boolean rulePriorityAvailable = false;
		boolean ruleDescriptionAvailable = false;
		if (rule != null) {
			// for (TableRule tableRule : rule) {
			//PivotDataModel dataModel = null;
			Object clientProperty = table.getClientProperty("SimpleDecisionTablePane");
			if (clientProperty instanceof DecisionTablePane) {
				// commented because of jide 2.3.0 library , probably they
				// have defined new interface IPivotDataModel
				// dataModel = ((SimpleDecisionTablePane) clientProperty)
				// .getPivotDataModel();
				DecisionTablePane decisionTablePane = (DecisionTablePane) clientProperty;
				

				/*java.util.List<Integer> data = DefaultDecisionTableModel
				.getActualRowIndex(dataModel, actualSelectedRow);*/
				//TODO Rectify this
				int[] selectedRows = 
					decisionTablePane.getTableScrollPane().getMainTable().getSelectedRows();

				if (selectedRows != null && selectedRows.length > 0) {
					selectedRow = selectedRows[0]; // just display the
					// first one in case the
					// row is collapsed with
					// several rules
				}
			}
			
			if (selectedRow > -1 && selectedRow < rule.size()) {
				//int rowIndex = getActualRowIndex(selectedRow);
				TableRule tableRule = getActualRowIndex(selectedRow);
				if (tableRule != null) {
					if (tableRule.getId() != null)
						ruleID.setText(tableRule.getId());
					else {
						ruleID.setText("");
					}
					MetaData metaData = tableRule.getMd();
					if (metaData != null) {
						EList<com.tibco.cep.decision.table.model.dtmodel.Property> property = metaData.getProp();
						if (property != null)
							for (com.tibco.cep.decision.table.model.dtmodel.Property prop : property) {
								if (prop != null) {
									if (prop.getName() != null
											&& prop.getName().equalsIgnoreCase(
													RuleMetadataFeature.DESCRIPTION.getFeatureName())) {
										ruleDescriptionAvailable = true;
										ruleDesc.setText(prop.getValue());
									}
									if (prop.getName() != null && 
											prop.getName().equalsIgnoreCase(RuleMetadataFeature.PRIORITY.getFeatureName())) {
										rulePriorityAvailable = true;
										if (prop.getValue() == null || 
												prop.getValue().trim().equals("")) {
											rulePriorities.setText("5");
										}
										else {
											rulePriorities.setText(prop.getValue());
										}
									}
								}
							}
						if (ruleDescriptionAvailable == false) {
							com.tibco.cep.decision.table.model.dtmodel.Property prProperty = DtmodelFactory.eINSTANCE.createProperty();
							prProperty.setName(RuleMetadataFeature.DESCRIPTION.getFeatureName());
							prProperty.setType("String");
							prProperty.setValue("");
							property.add(prProperty);
							tableRule.setMd(metaData);
						}
						if (rulePriorityAvailable == false) {
							com.tibco.cep.decision.table.model.dtmodel.Property prProperty = DtmodelFactory.eINSTANCE.createProperty();
							prProperty.setName(RuleMetadataFeature.PRIORITY.getFeatureName());
							prProperty.setType("Integer");
							prProperty.setValue("5");
							property.add(prProperty);
							tableRule.setMd(metaData);
						}
					} else {
						metaData = DtmodelFactory.eINSTANCE.createMetaData();
						EList<com.tibco.cep.decision.table.model.dtmodel.Property> property = metaData.getProp();
						com.tibco.cep.decision.table.model.dtmodel.Property desceProperty = DtmodelFactory.eINSTANCE.createProperty();
						desceProperty.setName(RuleMetadataFeature.DESCRIPTION.getFeatureName());
						desceProperty.setType("String");
						desceProperty.setValue("");
						com.tibco.cep.decision.table.model.dtmodel.Property priorProperty = DtmodelFactory.eINSTANCE.createProperty();
						priorProperty.setName(RuleMetadataFeature.PRIORITY.getFeatureName());
						priorProperty.setType("Integer");
						priorProperty.setValue("5");
						property.add(desceProperty);
						property.add(priorProperty);
						tableRule.setMd(metaData);
					}
				}
			}
		}
	}
	
	private TableRule getActualRowIndex(int selectedRow) {
		DecisionTablePane pane = null;
		if (TableTypes.DECISION_TABLE.equals(tableType)) {
			pane = (DecisionTablePane)editor.getDecisionTablePane();
			
		} else {
			pane = (DecisionTablePane)editor.getExceptionTablePane();
		}
		return (TableRule)pane.getSelectedRule(selectedRow).getValue();

	}

	/**
	 * @param propertyName
	 * @param value
	 */
	private void updateProperty(String propertyName, String newValue) {
		if (decisionTableRuleSet != null) {
			EList<TableRule> rule = decisionTableRuleSet.getRule();
			if (rule != null) {
				if (selectedRow > -1 && selectedRow < rule.size()) {
					TableRule tableRule = getActualRowIndex(selectedRow);
					if (tableRule != null) {
						RuleMetadataFeature ruleMetadataFeature = RuleMetadataFeature.getByFeatureName(propertyName);
						//Execute model updates through the command framework only.
						CommandFacade.getInstance().
							executeTableMetadataUpdate(project, 
									                   tableEModel, 
									                   tableRule,
									                   ruleMetadataFeature, 
									                   newValue, 
									                   null);
					}
				}
			}
		}
	}
}