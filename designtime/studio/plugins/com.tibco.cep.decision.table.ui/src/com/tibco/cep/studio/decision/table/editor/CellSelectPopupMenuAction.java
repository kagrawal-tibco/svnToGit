package com.tibco.cep.studio.decision.table.editor;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.command.LayerCommandUtil;
import org.eclipse.nebula.widgets.nattable.coordinate.PositionCoordinate;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.action.SelectCellAction;
import org.eclipse.nebula.widgets.nattable.ui.NatEventData;
import org.eclipse.nebula.widgets.nattable.ui.menu.MenuItemProviders;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.impl.PropertyUpdateCommand;
import com.tibco.cep.decision.table.command.impl.RemoveCommand;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.decision.table.command.listeners.DecisionTableCellRemoveCommandListener;
import com.tibco.cep.studio.decision.table.providers.SpanningGlazedListsDataProvider;

public class CellSelectPopupMenuAction extends SelectCellAction{

	private final Menu menu;
	private final SelectionLayer selectionLayer;
	private IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	private PositionCoordinate coords;
	private IDecisionTableEditor editor;
	private NatTable natTable;
	TableRuleVariable ruleVariable;
	MenuItem addComment = null;
	MenuItem disableCell = null;
	MenuItem deleteComment = null;
	MenuItem unsetValue = null;
	
	public CellSelectPopupMenuAction(Menu bodyMenu, IDecisionTableEditor editor,
			NatTable natTable) {
		this.menu = bodyMenu;
		this.editor = editor;
		this.natTable = natTable;
		DTBodyLayerStack<TableRule> targetStack = (DTBodyLayerStack<TableRule>) ((GridLayer)natTable.getLayer()).getBodyLayer();
		this.selectionLayer = targetStack.getSelectionLayer();

	}
	
	@Override
    public void run(NatTable natTable, MouseEvent event) {
        coords = new PositionCoordinate(
                        natTable,
                        natTable.getColumnPositionByX(event.x),
                        natTable.getRowPositionByY(event.y));
        coords = LayerCommandUtil.convertPositionToTargetContext(coords, selectionLayer);
        if (!selectionLayer.isCellPositionSelected(coords.getColumnPosition(), coords.getRowPosition())) {
        	super.run(natTable, event);
        }
		createMenuActions();
        menu.setData(MenuItemProviders.NAT_EVENT_DATA_KEY,event.data);
        menu.setVisible(true);
        menu.setEnabled(editor.isEnabled());
		menu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuShown(MenuEvent event) {
				Menu menu = (Menu) event.getSource();					
				if (menu.getData(MenuItemProviders.NAT_EVENT_DATA_KEY) instanceof NatEventData) {
					MenuItem[] menuItems = menu.getItems();
					boolean isDTEditable = editor.isEnabled();
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
    }

	private void createMenuActions() {

		Object trv = editor.getModelDataByPosition(coords.getColumnPosition(), coords.getRowPosition(), natTable);
		if (trv == null) {
			ruleVariable = null;
			if (unsetValue != null) {
				unsetValue.dispose();
			}
			if(addComment != null){
				addComment.dispose();
			}
			addComment = new MenuItem(menu, SWT.PUSH,0);
			addComment.setText("Add Comment");
			if (deleteComment != null){
				deleteComment.dispose();
			}
			addComment.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					CommentDialog dialog = new CommentDialog(window.getShell());
					dialog.setText("");
					dialog.create();
					dialog.open();
					String newComment = dialog.getText();
					if (newComment == null || newComment.trim().equals("") && ruleVariable != null) {
						ruleVariable.setComment(null);
					} else {
						if (ruleVariable == null) {
							ruleVariable = createEmptyTableRuleVariable();
						}
						ruleVariable.setComment(newComment);
					}
					editor.asyncModified();
				}
			});
			
			if (disableCell != null) {
				disableCell.dispose();
			}
			disableCell = new MenuItem(menu, SWT.PUSH, 1);
			disableCell.setText("Disable");
			disableCell.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					if (ruleVariable == null) {
						ruleVariable = createEmptyTableRuleVariable();
					}
					Table tableEModel = editor.getTable();
					String project = tableEModel.getOwnerProjectName();
					PropertyUpdateCommand.PROPERTY_FEATURE propertyFeature = PropertyUpdateCommand.PROPERTY_FEATURE.ENABLED;
					CommandFacade.getInstance().executeCellPropertyUpdates(
							project, tableEModel, ruleVariable,
							false, propertyFeature);
					editor.asyncModified();
				}
			});
			
		} else if (trv != null && trv instanceof TableRuleVariable) {
			ruleVariable = (TableRuleVariable) trv;
			final String oldComment = ruleVariable.getComment();
			if(addComment != null){
				addComment.dispose();
			}
			addComment = new MenuItem(menu, SWT.PUSH,0);
			if (deleteComment != null){
				deleteComment.dispose();
			}
			if(oldComment != null && oldComment.trim().length() > 0){
				//TODO Add text from resource bundle
				addComment.setText("Edit Comment");
				deleteComment = new MenuItem(menu, SWT.PUSH, 2);
				deleteComment.setText("Delete Comment");
				deleteComment.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						ruleVariable.setComment(null);
						editor.asyncModified();							
					}
				});
			}
			else{
				addComment.setText("Add Comment");
			}

			addComment.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					CommentDialog dialog = new CommentDialog(window.getShell());
					dialog.setText(oldComment);
					dialog.create();
					dialog.open();
					String newComment = dialog.getText();
					if (newComment == null || newComment.trim().equals("")) {
						ruleVariable.setComment(null);
					} else {
						ruleVariable.setComment(newComment);
					}
					editor.asyncModified();
				}
			});

			if (disableCell != null) {
				disableCell.dispose();
			}
			disableCell = new MenuItem(menu, SWT.PUSH, 1);
			final Boolean enable;
			if (ruleVariable.isEnabled()) {
				disableCell.setText("Disable");
				enable = false;
			} else {
				disableCell.setText("Enable");
				enable = true;
			}
			disableCell.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					Table tableEModel = editor.getTable();
					String project = tableEModel.getOwnerProjectName();
					PropertyUpdateCommand.PROPERTY_FEATURE propertyFeature = PropertyUpdateCommand.PROPERTY_FEATURE.ENABLED;
					CommandFacade.getInstance().executeCellPropertyUpdates(
							project, tableEModel, ruleVariable,
							enable, propertyFeature);
					editor.asyncModified();
				}
			});
			
			if (unsetValue != null) {
				unsetValue.dispose();
			}
			if (ruleVariable.getExpr() != null) {
				unsetValue = new MenuItem(menu, SWT.PUSH, 1);
				unsetValue.setText("Unset value");
				unsetValue.addListener(SWT.Selection, new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						DTBodyLayerStack<TableRule> targetLayer = (DTBodyLayerStack<TableRule>) ((GridLayer)natTable.getLayer()).getBodyLayer();
						SpanningGlazedListsDataProvider<TableRule> dataProvider = targetLayer.getBodyDataProvider();
						TableRule rule = dataProvider.getRowObject(coords.getRowPosition());
						TableTypes tableType = TableTypes.DECISION_TABLE;
						if (editor.getTable().getDecisionTable() != rule.eContainer()) {
							tableType = TableTypes.EXCEPTION_TABLE;
						}
						
						ICommandCreationListener<RemoveCommand<TableRuleVariable>, TableRuleVariable> listener =
								new DecisionTableCellRemoveCommandListener(editor.getDtDesignViewer(), editor.getTable(), rule, ruleVariable);
						CommandFacade.getInstance().executeCellRemoval(editor.getProjectName(), editor.getTable(), tableType, listener);
						
						editor.asyncModified();
					}
				});
			}
		}
	}

	private TableRuleVariable createEmptyTableRuleVariable() {
		DTBodyLayerStack<TableRule> targetLayer = (DTBodyLayerStack<TableRule>) ((GridLayer)natTable.getLayer()).getBodyLayer();
		SpanningGlazedListsDataProvider<TableRule> dataProvider = targetLayer.getBodyDataProvider();
		dataProvider.setDataValue(coords.getColumnPosition(), coords.getRowPosition(), "");
		return (TableRuleVariable) editor.getModelDataByPosition(coords.getColumnPosition(), coords.getRowPosition(), natTable);
	}
}
