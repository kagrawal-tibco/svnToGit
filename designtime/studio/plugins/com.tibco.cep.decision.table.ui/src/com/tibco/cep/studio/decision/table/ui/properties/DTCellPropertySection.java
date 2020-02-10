package com.tibco.cep.studio.decision.table.ui.properties;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.impl.PropertyUpdateCommand;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.decision.table.editor.DTBodyLayerStack;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.decision.table.utils.Messages;

public class DTCellPropertySection extends AbstractDTPropertySection {

	/**
	 * Optional comments text.
	 */
	private Text propComment;

	private Button propStatus;
	
	/**
	 * The cell value (editable)
	 */
	private Text propValue;
	
	private Button applyChangesButton;

	protected Composite composite;

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		createCellPropertyPage(getWidgetFactory());
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.decision.table.ui.properties.AbstractDTPropertySection#disablePropertyPage()
	 */
	public void disablePropertyPage() {
		propComment.setEnabled(false);		
		propStatus.setEnabled(false);		
		propValue.setEnabled(false);		
		applyChangesButton.setEnabled(false);
	}
	
	/**
	 * @param widgetFactory
	 */
	private void createCellPropertyPage(TabbedPropertySheetWidgetFactory widgetFactory) {
		widgetFactory.createLabel(composite, Messages
				.getString("RULE_META_DATA_VIEW_COMPONENT_TAB_VALUE"));
		propValue = widgetFactory.createText(composite, "", SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		GridData data1 = new GridData(GridData.FILL_HORIZONTAL);
		data1.heightHint = 50;
		propValue.setLayoutData(data1);
		propValue.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				executeTextModify();
			}
		});
		
		Transfer[] types = new Transfer[] {TextTransfer.getInstance()};
    	DropTarget dropTarget = new DropTarget(propValue, DND.DROP_COPY | DND.DROP_MOVE);
    	dropTarget.setTransfer(types);
    	dropTarget.addDropListener(new DropTargetAdapter() {

			/* (non-Javadoc)
			 * @see org.eclipse.swt.dnd.DropTargetAdapter#drop(org.eclipse.swt.dnd.DropTargetEvent)
			 */
			@Override
			public void drop(DropTargetEvent event) {
				propValue.setText(propValue.getText() + (String)event.data);
				executeTextModify();
			}
    		
    	});

		widgetFactory.createLabel(composite, Messages
				.getString("RULE_META_DATA_VIEW_COMPONENT_TAB_ENABLED"));
		propStatus = widgetFactory.createButton(composite, "", SWT.CHECK);
		propStatus.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		propStatus.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tableRuleVariable != null) {
					applyChangesButton.setEnabled(true);
					
					PropertyUpdateCommand.PROPERTY_FEATURE propertyFeature = PropertyUpdateCommand.PROPERTY_FEATURE.ENABLED;
					CommandFacade.getInstance().executeCellPropertyUpdates(
							project, tableEModel, tableRuleVariable,
							propStatus.getSelection(), propertyFeature);
				}
			}
		});

		widgetFactory.createLabel(composite, Messages
				.getString("RULE_META_DATA_VIEW_COMPONENT_TAB_COMMENT"));
		propComment = widgetFactory.createText(composite, "", SWT.MULTI | SWT.V_SCROLL);
		GridData data2 = new GridData(GridData.FILL_HORIZONTAL);
		data2.heightHint = 50;
		propComment.setLayoutData(data2);
		propComment.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (tableRuleVariable != null) {
					applyChangesButton.setEnabled(true);
//					tableRuleVariable.setComment(propComment.getText());
					PropertyUpdateCommand.PROPERTY_FEATURE propertyFeature = PropertyUpdateCommand.PROPERTY_FEATURE.COMMENT;
					CommandFacade.getInstance().executeCellPropertyUpdates(
							project, tableEModel, tableRuleVariable,
							propComment.getText(), propertyFeature);
				}
			}
		});
		
		applyChangesButton = widgetFactory.createButton(composite, Messages
				.getString("RULE_META_DATA_VIEW_COMPONENT_TAB_APPLY"), SWT.PUSH);
		GridData buttonData = new GridData();
		buttonData.heightHint = 20;
		buttonData.widthHint = 50;
		applyChangesButton.setLayoutData(buttonData);
		applyChangesButton.setEnabled(false);
		applyChangesButton.addSelectionListener(new SelectionListener() {

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				String text = propValue.getText();
				NatTable table;
				if (TableTypes.DECISION_TABLE.equals(tableType)) {
					table = editor.getDtDesignViewer().getDecisionTable();
				} else {
					table = editor.getDtDesignViewer().getExceptionTable();
				}
				DTBodyLayerStack<TableRule> dataLayer = (DTBodyLayerStack<TableRule>) ((GridLayer)table.getLayer()).getBodyLayer();
				dataLayer.getBodyDataProvider().setDataValue(selectedColumn, selectedRow, text);
				editor.doSave(new NullProgressMonitor());
				applyChangesButton.setEnabled(false);
			}
		});
	}
	
	private void executeTextModify() {
		applyChangesButton.setEnabled(tableRuleVariable != null && !tableRuleVariable.getExpr().equals(propValue.getText()));
	}

	/*
	 * @see
	 * org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh
	 * ()
	 */
	public void refresh() {
		if (tableRuleVariable != null) {
			try {
				setCellData();
			} catch (Exception e) {
				DecisionTableUIPlugin.log(e);
			}
		}
	}

	private void setCellData() throws Exception {
		if (tableRuleVariable.getComment() != null) {
			propComment.setText(tableRuleVariable.getComment());
		} else {
			propComment.setText("");
		}
		propStatus.setSelection(tableRuleVariable.isEnabled());
		String text = tableRuleVariable.getExpr();
		if (text != null) {
			propValue.setText(text);
		}
	}
}
