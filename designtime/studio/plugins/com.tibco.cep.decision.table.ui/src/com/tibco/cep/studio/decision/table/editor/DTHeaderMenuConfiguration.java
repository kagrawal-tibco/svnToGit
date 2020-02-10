package com.tibco.cep.studio.decision.table.editor;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupExpandCollapseLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupModel.ColumnGroup;
import org.eclipse.nebula.widgets.nattable.ui.NatEventData;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.nebula.widgets.nattable.ui.menu.HeaderMenuConfiguration;
import org.eclipse.nebula.widgets.nattable.ui.menu.MenuItemProviders;
import org.eclipse.nebula.widgets.nattable.ui.menu.PopupMenuBuilder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.impl.MoveCommand;
import com.tibco.cep.decision.table.command.impl.PropertyUpdateCommand;
import com.tibco.cep.decision.table.command.impl.RemoveCommand;
import com.tibco.cep.decision.table.command.memento.ColumnPositionStateMemento;
import com.tibco.cep.decision.table.command.memento.TableRuleStateMemento;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.utils.Messages;
import com.tibco.cep.studio.decision.table.command.listeners.DecisionTableColumnMoveCommandListener;
import com.tibco.cep.studio.decision.table.command.listeners.DecisionTableColumnRemovalCommandListener;
import com.tibco.cep.studio.decision.table.command.listeners.DecisionTableRowMoveCommandListener;
import com.tibco.cep.studio.decision.table.command.listeners.DecisionTableRowMoveCommandListener.ReorderType;
import com.tibco.cep.studio.decision.table.providers.SpanningGlazedListsDataProvider;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;

public class DTHeaderMenuConfiguration extends HeaderMenuConfiguration{

	/**
	 * 
	 */
	private IDecisionTableEditor decisionTableSWTEditor;
	private TableTypes tableType;
	private ColumnGroupExpandCollapseLayer columnGroupExpandCollapseLayer;
	private IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	
	public DTHeaderMenuConfiguration(IDecisionTableEditor decisionTableSWTEditor, NatTable natTable, 
											ColumnGroupExpandCollapseLayer columnGroupExpandCollapseLayer, TableTypes tableType) {
		super(natTable);
		this.decisionTableSWTEditor = decisionTableSWTEditor;
		this.tableType = tableType;
		this.columnGroupExpandCollapseLayer = columnGroupExpandCollapseLayer;		
	}
	
	@Override
	protected PopupMenuBuilder createColumnHeaderMenu(final NatTable natTable)
	{
		Menu menu = createPopUpMenu(natTable);
		return new PopupMenuBuilder(natTable, menu)
				.withAutoResizeSelectedColumnsMenuItem();
	}
	
	@Override
	protected PopupMenuBuilder createRowHeaderMenu(final NatTable natTable) {
		Menu menu =  new Menu(natTable.getShell());
		MenuItem enableDisableRow = new MenuItem(menu, 8, 0);
		enableDisableRow.setText("Enable/Disable");
		enableDisableRow.setEnabled(true);
		enableDisableRow.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent event) {
				int rowPosition = MenuItemProviders.getNatEventData(event).getRowPosition();
				int rowIndex = natTable.getRowIndexByPosition(rowPosition);
				TableRuleSet targetTableRuleSet = (tableType == TableTypes.DECISION_TABLE)? decisionTableSWTEditor.getTable().getDecisionTable() : decisionTableSWTEditor.getTable().getExceptionTable();
				int columnCount = targetTableRuleSet.getColumns().getColumn().size();
				
				boolean enable = isRowDisabled(rowIndex, columnCount);//Enable row if disabled. Disable row if at least one of the cells is enabled.
				PropertyUpdateCommand.PROPERTY_FEATURE propertyFeature = PropertyUpdateCommand.PROPERTY_FEATURE.ENABLED;
				Table tableEModel = decisionTableSWTEditor.getTable();
				String project = tableEModel.getOwnerProjectName();
				
				for (int i = 0 ; i < columnCount; i++) {
					Object trv = decisionTableSWTEditor.getModelDataByPosition(i, rowIndex, natTable);
					if (trv == null) {
						trv = createEmptyTableRuleVariable(i, rowIndex);
					}
					if (trv != null && trv instanceof TableRuleVariable) {
						CommandFacade.getInstance().executeCellPropertyUpdates(project, tableEModel, (TableRuleVariable) trv, enable, propertyFeature);
					}
				}
				decisionTableSWTEditor.asyncModified();
				
				switch(tableType) {
				case DECISION_TABLE:
					decisionTableSWTEditor.getDtDesignViewer().getDecisionTable().refresh();
					break;
				case EXCEPTION_TABLE:
					decisionTableSWTEditor.getDtDesignViewer().getExceptionTable().refresh();
					break;
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
			
			/**
			 * Returns whether complete row is disabled or not.
			 * @param rowIndex
			 * @param columnCount
			 * @return
			 */
			private boolean isRowDisabled(int rowIndex, int columnCount) {
				for (int i = 0 ; i < columnCount; i++) {
					Object trv = decisionTableSWTEditor.getModelDataByPosition(i, rowIndex, natTable);
					if (trv != null && trv instanceof TableRuleVariable) {
						TableRuleVariable ruleVariable = (TableRuleVariable) trv;
						if (ruleVariable.isEnabled()) {
							return false;
						}
					}
				}
				return true;
			}
		});
		
		createMoveRowMenu(menu, natTable);

		menu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuShown(MenuEvent event) {
				Menu menu = (Menu) event.getSource();					
				if (menu.getData(MenuItemProviders.NAT_EVENT_DATA_KEY) instanceof NatEventData) {
					MenuItem[] menuItems = menu.getItems();
					boolean isDTEditable = decisionTableSWTEditor.isEnabled();
					for (int i = 0; i < menuItems.length; i++) {	
						menuItems[i].setEnabled(isDTEditable);
					}	
				}
			}
			
			@Override
			public void menuHidden(MenuEvent arg0) {
				// do nothing
				
			}
		});
		
		return new PopupMenuBuilder(natTable, menu);
	}

	private void createMoveRowMenu(Menu menu, final NatTable natTable) {
		//Add Row Move menu options
		final MenuItem rowMoveItem = new MenuItem(menu, SWT.CASCADE, 1);
		rowMoveItem.setText("Move");		
		Menu rowMoveMenu = new Menu(menu);
		rowMoveItem.setMenu(rowMoveMenu);

		//Menu option - Move to beginning
		final MenuItem rowMoveStartMenuItem = new MenuItem(rowMoveMenu, 8, 0);
		rowMoveStartMenuItem.setText("Move to the beginning");
		rowMoveStartMenuItem.addSelectionListener(new SelectionListener() {			
			@SuppressWarnings("unchecked")
			@Override
			public void widgetSelected(SelectionEvent event) {
				int rowPosition = MenuItemProviders.getNatEventData(event).getRowPosition();
				int rowIndex = natTable.getRowIndexByPosition(rowPosition);
				performRowMove(natTable, rowIndex, 0);				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				//Do nothing				
			}
		});

		//Menu option - Move to left
		final MenuItem rowMoveUpMenuItem = new MenuItem(rowMoveMenu, 8, 1);
		rowMoveUpMenuItem.setText("Move up");
		rowMoveUpMenuItem.setEnabled(true);
		rowMoveUpMenuItem.addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent event) {
				int rowPosition = MenuItemProviders.getNatEventData(event).getRowPosition();
				int rowIndex = natTable.getRowIndexByPosition(rowPosition);
				performRowMove(natTable, rowIndex, rowIndex - 1);				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				//Do nothing				
			}
		});

		//Menu option - Move down
		final MenuItem rowMoveDownMenuItem = new MenuItem(rowMoveMenu, 8, 2);
		rowMoveDownMenuItem.setText("Move down");
		rowMoveDownMenuItem.setEnabled(true);
		rowMoveDownMenuItem.addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent event) {
				int rowPosition = MenuItemProviders.getNatEventData(event).getRowPosition();
				int rowIndex = natTable.getRowIndexByPosition(rowPosition);
				performRowMove(natTable, rowIndex, rowIndex + 1);				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				//Do nothing				
			}
		});

		//Menu option - Move to end
		final MenuItem rowMoveEndMenuItem = new MenuItem(rowMoveMenu, 8, 3);
		rowMoveEndMenuItem.setText("Move to the end");
		rowMoveEndMenuItem.setEnabled(true);
		rowMoveEndMenuItem.addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent event) {
				int rowPosition = MenuItemProviders.getNatEventData(event).getRowPosition();
				int rowIndex = natTable.getRowIndexByPosition(rowPosition);
				performRowMove(natTable, rowIndex, -1);				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				//Do nothing				
			}
		});		
	}

	/**
	 * Creates the popupMenu of column header.
	 * @param natTable
	 * @return
	 */
	private Menu createPopUpMenu(final NatTable natTable) {
		Menu menu =  new Menu(natTable.getShell());

		MenuItem colRemove = new MenuItem(menu, 8, 0);
		colRemove.setText("Remove column");
		colRemove.setEnabled(true);
		colRemove.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				int colPosition = MenuItemProviders.getNatEventData(event).getColumnPosition();
				int index = natTable.getColumnIndexByPosition(colPosition);
				TableRuleSet targetTableRuleSet = (tableType == TableTypes.DECISION_TABLE)? decisionTableSWTEditor.getTable().getDecisionTable() : decisionTableSWTEditor.getTable().getExceptionTable();
				Column colToRemove = targetTableRuleSet.getColumns().getColumn().get(index);
				
				boolean isConditionAvailable = false;
				
				// If the type of column to be removed is condition, check if it is a last condition column
				//and check if there are any action columns available
				if(colToRemove.getColumnType() == ColumnType.CONDITION || colToRemove.getColumnType() == ColumnType.CUSTOM_CONDITION) {
					int size = targetTableRuleSet.getColumns().getColumn().size();
					Column lastcolumn = targetTableRuleSet.getColumns().getColumn().get(size - 1);
					
					if(lastcolumn.getColumnType().equals(ColumnType.ACTION)|| lastcolumn.getColumnType().equals(ColumnType.CUSTOM_ACTION)) {
						for(Column column :targetTableRuleSet.getColumns().getColumn()){
							if((column.getColumnType().equals(ColumnType.CONDITION) || column.getColumnType().equals(ColumnType.CUSTOM_CONDITION)) 
									&& !column.getId().equals(colToRemove.getId()) ){

								isConditionAvailable = true;
								break;
							}
						}

						if(!isConditionAvailable){
							String message = Messages.getString("ERROR_REMOVE_CONDITION");
							DecisionTableUIPlugin.showMessageDialog("Error", message, null, decisionTableSWTEditor.getSite().getShell());
							return;
						}
					}
				}
				
				final ICommandCreationListener<RemoveCommand<Object>, Object> 
				columnRemovalCommandListener =
				new DecisionTableColumnRemovalCommandListener(decisionTableSWTEditor, natTable, index);

				CommandFacade.getInstance().executeColumnRemoval(decisionTableSWTEditor.getTable().getOwnerProjectName(), 
						decisionTableSWTEditor.getTable(), 
						tableType, 
						columnRemovalCommandListener);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		//Add Column Move menu options
		final MenuItem colMoveItem = new MenuItem(menu, SWT.CASCADE, 1);
		colMoveItem.setText("Move");		
		Menu colMoveMenu = new Menu(menu);
		colMoveItem.setMenu(colMoveMenu);
		
		//Menu option - Move to beginning
		final MenuItem colMoveStartMenuItem = new MenuItem(colMoveMenu, 8, 0);
		colMoveStartMenuItem.setText("Move to the beginning");
		colMoveStartMenuItem.addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent event) {
				int colPosition = MenuItemProviders.getNatEventData(event).getColumnPosition();
				int fromColumnIndex = natTable.getColumnIndexByPosition(colPosition);
				ColumnGroup columnGroup = columnGroupExpandCollapseLayer.getModel(0).getColumnGroupByIndex(fromColumnIndex);
				ColumnGroup condColumnGroup = columnGroupExpandCollapseLayer.getModel(0).getColumnGroupByIndex(0);
				int toColumnIndex = 0;
				if (columnGroup != condColumnGroup) {
					// we're in the Actions column group
					List<Integer> groupColumnIndexes = condColumnGroup.getMembers();
					toColumnIndex = groupColumnIndexes.size();
				}
				performColumnMove(natTable, fromColumnIndex, toColumnIndex);				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				//Do nothing				
			}
		});
		
		//Menu option - Move to left
		final MenuItem colMoveLeftMenuItem = new MenuItem(colMoveMenu, 8, 1);
		colMoveLeftMenuItem.setText("Move to the left");
		colMoveLeftMenuItem.setEnabled(true);
		colMoveLeftMenuItem.addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent event) {
				int colPosition = MenuItemProviders.getNatEventData(event).getColumnPosition();
				int fromColumnIndex = natTable.getColumnIndexByPosition(colPosition);
				performColumnMove(natTable, fromColumnIndex, fromColumnIndex - 1);				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				//Do nothing				
			}
		});

		//Menu option - Move to right		
		final MenuItem colMoveRightMenuItem = new MenuItem(colMoveMenu, 8, 2);
		colMoveRightMenuItem.setText("Move to the right");
		colMoveRightMenuItem.setEnabled(true);
		colMoveRightMenuItem.addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent event) {
				int colPosition = MenuItemProviders.getNatEventData(event).getColumnPosition();
				int fromColumnIndex = natTable.getColumnIndexByPosition(colPosition);
				performColumnMove(natTable, fromColumnIndex, fromColumnIndex + 1);				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				//Do nothing				
			}
		});

		//Menu option - Move to end
		final MenuItem colMoveEndMenuItem = new MenuItem(colMoveMenu, 8, 3);
		colMoveEndMenuItem.setText("Move to the end");
		colMoveEndMenuItem.setEnabled(true);
		colMoveEndMenuItem.addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent event) {
				int colPosition = MenuItemProviders.getNatEventData(event).getColumnPosition();
				int fromColumnIndex = natTable.getColumnIndexByPosition(colPosition);
				
//				ColumnGroup columnGroup = columnGroupExpandCollapseLayer.getModel().getColumnGroupByIndex(fromColumnIndex);
				//TODO: Check this getModel(int row)
				ColumnGroup columnGroup = columnGroupExpandCollapseLayer.getModel(0).getColumnGroupByIndex(fromColumnIndex);
				
				List<Integer> groupColumnIndexes = columnGroup.getMembers();
				int toColumnIndex = -1;
				if (groupColumnIndexes.size() > 0)
					toColumnIndex = groupColumnIndexes.get(groupColumnIndexes.size() -1);
				performColumnMove(natTable, fromColumnIndex, toColumnIndex);				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				//Do nothing				
			}
		});
		
		//Listener to enable/disable menu options based on the column position
		menu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuShown(MenuEvent event) {
				if (event.getSource() instanceof Menu) {
					Menu menu = (Menu) event.getSource();
					Object data = menu.getData(MenuItemProviders.NAT_EVENT_DATA_KEY);
					if (data instanceof NatEventData) {
						int colPosition = ((NatEventData)data).getColumnPosition();
						int fromColumnIndex = natTable.getColumnIndexByPosition(colPosition);
						
//						ColumnGroup columnGroup = columnGroupExpandCollapseLayer.getModel().getColumnGroupByIndex(fromColumnIndex);
						//TODO: Check this getModel(int row)
						ColumnGroup columnGroup = columnGroupExpandCollapseLayer.getModel(0).getColumnGroupByIndex(fromColumnIndex);
						
						List<Integer> columnIndices = columnGroup.getMembers();
						int firstColumnIndex = columnIndices.size() > 0 ? columnIndices.get(0) : -1;
						int lastColumnIndex = columnIndices.size() > 0 ? columnIndices.get(columnIndices.size() - 1) : -1;
						MenuItem[] menuItems = colMoveItem.getMenu().getItems();
						for (int i = 0; i < menuItems.length; i++) {
							if (menuItems[i] == colMoveStartMenuItem) {
								colMoveStartMenuItem.setEnabled(!(fromColumnIndex == firstColumnIndex));
							} else if (menuItems[i] == colMoveLeftMenuItem) {
								colMoveLeftMenuItem.setEnabled(!(fromColumnIndex == firstColumnIndex));
							} else if (menuItems[i] == colMoveRightMenuItem) {
								colMoveRightMenuItem.setEnabled(!(fromColumnIndex == lastColumnIndex));
							} else if (menuItems[i] == colMoveEndMenuItem) {
								colMoveEndMenuItem.setEnabled(!(fromColumnIndex == lastColumnIndex));
							}
						}
						
						boolean isDTEditable = decisionTableSWTEditor.isEnabled();
						menuItems = menu.getItems();
						for (int i = 0; i < menuItems.length; i++) {	
							menuItems[i].setEnabled(isDTEditable);
						}						
					}
				}	
			}
			
			@Override
			public void menuHidden(MenuEvent e) {
				//do nothing
			}
		});
						
		MenuItem fieldSetting = new MenuItem(menu, 8, 2);
		fieldSetting.setText("Field Settings...");
		fieldSetting.setEnabled(true);
		fieldSetting.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				openFieldSettingsDialog(e, window.getShell(), decisionTableSWTEditor.getDtDesignViewer(), natTable);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub				
			}
		});

		return menu;
	}
	
    protected void openFieldSettingsDialog(SelectionEvent e, Shell shell, DecisionTableDesignViewer dtDesignViewer, NatTable natTable) {
        FieldSettingsDialog dialog = new FieldSettingsDialog(shell, e, natTable, tableType, dtDesignViewer);
        dialog.create();
        dialog.open();
    }

	public void performColumnMove(NatTable natTable, int fromColumnIndex, int toColumnIndex) {
		if (fromColumnIndex != toColumnIndex) {
			ICommandCreationListener<MoveCommand<ColumnPositionStateMemento>, ColumnPositionStateMemento> listener 
																	= new DecisionTableColumnMoveCommandListener(decisionTableSWTEditor.getTable().getOwnerProjectName(), 
																						decisionTableSWTEditor.getTable(), 
																						decisionTableSWTEditor, 
																						natTable,
																						fromColumnIndex,
																						toColumnIndex);
			CommandFacade.getInstance().executeColumnMove(decisionTableSWTEditor.getTable().getOwnerProjectName(), decisionTableSWTEditor.getTable(), tableType, listener);
		}	
	}
	
	public void performRowMove(NatTable natTable, int fromRowIndex, int toRowIndex) {
		
		if (fromRowIndex != toRowIndex) {
			ICommandCreationListener<MoveCommand<TableRuleStateMemento>, TableRuleStateMemento> listener 
			= new DecisionTableRowMoveCommandListener(decisionTableSWTEditor.getTable().getOwnerProjectName(), 
					decisionTableSWTEditor.getTable(), 
					decisionTableSWTEditor, 
					natTable,
					fromRowIndex,
					toRowIndex);
			CommandFacade.getInstance().executeRowMove(decisionTableSWTEditor.getTable().getOwnerProjectName(), decisionTableSWTEditor.getTable(), tableType, listener);
		}	
	}
	
	public void performRowReorder(NatTable natTable, ReorderType type) {

		ICommandCreationListener<MoveCommand<TableRuleStateMemento>, TableRuleStateMemento> listener 
		= new DecisionTableRowMoveCommandListener(decisionTableSWTEditor.getTable().getOwnerProjectName(), 
				decisionTableSWTEditor.getTable(), 
				decisionTableSWTEditor, 
				natTable,
				type);
		CommandFacade.getInstance().executeRowMove(decisionTableSWTEditor.getTable().getOwnerProjectName(), decisionTableSWTEditor.getTable(), tableType, listener);
	}
	
	public void configureUiBindings(UiBindingRegistry uiBindingRegistry) {
		super.configureUiBindings(uiBindingRegistry);
	}

	public void setID(TableRule decisionRule, String newId) {
		decisionRule.setId(newId);
		for ( TableRuleVariable trv : decisionRule.getCondition()) {
				String colId = trv.getColId();
				String newTrvId = newId + "_" + colId;
				trv.setId(newTrvId);
		}
		for (TableRuleVariable trv : decisionRule.getAction()) {
				String colId = trv.getColId();
				String newTrvId = newId + "_" + colId;
				trv.setId(newTrvId);
		}
	}
	
	/**
	 * This method creates the empty PopUp menu for corner column header in the
	 * decision table.
	 * 
	 */
	@Override
	protected PopupMenuBuilder createCornerMenu(final NatTable natTable) {
		Menu menu =  new Menu(natTable.getShell());
		MenuItem resetRuleIDs = new MenuItem(menu, 8, 0);
		resetRuleIDs.setText("Reset All Rule IDs");
		resetRuleIDs.setEnabled(true);
		resetRuleIDs.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent event) {
				performRowReorder(natTable, ReorderType.RESET_ALL_IDS);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
			
		});
		
		MenuItem reorderRulesByID = new MenuItem(menu, 8, 0);
		reorderRulesByID.setText("Reorder All Rules By ID");
		reorderRulesByID.setEnabled(true);
		reorderRulesByID.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent event) {
				performRowReorder(natTable, ReorderType.SORT_ALL_BY_ID);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
			
		});
		
		menu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuShown(MenuEvent event) {
				Menu menu = (Menu) event.getSource();					
				if (menu.getData(MenuItemProviders.NAT_EVENT_DATA_KEY) instanceof NatEventData) {
					MenuItem[] menuItems = menu.getItems();
					boolean isDTEditable = decisionTableSWTEditor.isEnabled();
					for (int i = 0; i < menuItems.length; i++) {	
						menuItems[i].setEnabled(isDTEditable);
					}	
				}
			}
			
			@Override
			public void menuHidden(MenuEvent arg0) {
				// do nothing
				
			}
		});
		
		return new PopupMenuBuilder(natTable, menu);
	}

	private TableRuleVariable createEmptyTableRuleVariable(int col, int row) {
		DecisionTableDesignViewer dtDesignViewer = decisionTableSWTEditor.getDtDesignViewer();
		NatTable target = (tableType == TableTypes.DECISION_TABLE)? dtDesignViewer.getDecisionTable() : dtDesignViewer.getExceptionTable();
		DTBodyLayerStack<TableRule> targetLayer = (DTBodyLayerStack<TableRule>) ((GridLayer)(target.getLayer())).getBodyLayer();
		SpanningGlazedListsDataProvider<TableRule> dataProvider = targetLayer.getBodyDataProvider();
		dataProvider.setDataValue(col, row, "");
		return (TableRuleVariable) decisionTableSWTEditor.getModelDataByPosition(col, row, target);
	}
}