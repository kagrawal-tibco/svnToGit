package com.tibco.cep.studio.ui.util;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;


/*
@author ssailapp
@date Jan 21, 2010 12:25:22 PM
 */

public class TableProviderUi {

	public static int TYPE_TEXT = 0;
	public static int TYPE_COMBO = 1;
	
	private Composite comp;
	private Composite tableParent;
	private String[] columns;
	private boolean hasToolBar;
	private Table table;
	private TableProviderModel tableProviderModel;
	private ToolBarProvider toolBarProvider;
	private ToolBar toolBar;
	
	public TableProviderUi(Composite comp, String columns[], boolean hasToolBar, TableProviderModel tableProviderModel) {
		this.comp = comp;
		this.columns = columns;
		this.hasToolBar = hasToolBar;
		this.tableProviderModel = tableProviderModel;
	}
	
	public Table createTable() {
		return createTable(true, true);
	}
	
	public Table createTable(boolean grabVerticalSpace, boolean hasOrdering) {
        return createTable(grabVerticalSpace, hasOrdering, false, false);
	}
	
   public Table createTable(boolean grabVerticalSpace, boolean hasOrdering, boolean hasCheck) {
        return createTable(grabVerticalSpace, hasOrdering, hasCheck, false);
    }
	
	public Table createTable(boolean grabVerticalSpace, boolean hasOrdering, boolean hasCheck, boolean hasEdit) {
		tableParent = new Composite(comp, SWT.NONE);
		//if (grabVerticalSpace)
		tableParent.setLayoutData(new GridData(GridData.FILL_BOTH));
		tableParent.setLayout(PanelUiUtil.getCompactGridLayout(1, true));
		
		if (hasToolBar)
            initializeToolBar(tableParent, hasOrdering, hasEdit);
        
		int style = SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION;
		if (hasCheck)
			style |= SWT.CHECK; 
				
		table = new Table(tableParent, style);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 250;
		table.setLayoutData(gd);
		for (String column: columns) {
			new TableColumn(table, SWT.LEFT).setText(column);
		}

		table.addListener(SWT.Selection, getTableSelectionListener());
		tableParent.addControlListener(getResizeListener());
		table.addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event event) {
				event.height = 18;
			}
		});
		
		return table;
	}

	public void setTableData(ArrayList<ArrayList<String>> tableData) {
		setTableData(tableData, PanelUiUtil.TEXT_FIELD_SIZE_HINT);
	}
	
	public void setTableData(ArrayList<ArrayList<String>> tableData, int minColumnWidth) {
		setTableData(tableData, minColumnWidth, -1);
	}
	
	public void setTableData(ArrayList<ArrayList<String>> tableData, int minColumnWidth, int checkColumn) {
		if (checkColumn != -1)
			removeTableEditors();
		table.removeAll();
		int rowIndex;
		for (ArrayList<String> rowData: tableData) {
	    	TableItem item = new TableItem(table, SWT.NONE);
	    	TableEditor editor = new TableEditor (table);
	    	if (checkColumn != -1) {
	    		tableCheckboxEditor(table, item, checkColumn, editor);
	    		item.setData(editor);
	      	}
	    	for (int i=0; i<table.getColumnCount(); i++) {
	    		String cellData = (String) rowData.get(i);
	    		if (checkColumn == i ){
	    			((Button) editor.getEditor()).setSelection(new Boolean(cellData).booleanValue());
	    		}else{
	    			item.setText(i, cellData != null ? cellData: "");
	    		}
	    	}
		}
	    packTable(minColumnWidth);
	    enableActionButtons();
	}
	
	private void removeTableEditors() {
		for (TableItem item: table.getItems()) {
			TableEditor editor = (TableEditor) item.getData();
			if (editor != null) {
				if (editor.getEditor() != null)
					editor.getEditor().dispose();
				editor.dispose();
			}
		}
	}
	
	private void packTable(int minColumnWidth) {
		Rectangle area = table.getClientArea();
		int colWidth = area.width/table.getColumnCount();
		if (colWidth < minColumnWidth)
			colWidth = minColumnWidth;
		for (int i=0; i<table.getColumnCount(); i++) {
			table.getColumn(i).pack();
			if (table.getColumn(i).getWidth() < colWidth)
				table.getColumn(i).setWidth(colWidth);
		}
	}
	
	private Listener getTableSelectionListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				enableActionButtons();
			}
		};
		return listener;
	}
	
	private void enableActionButtons() {
		if (toolBarProvider == null)
			return;
		toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_ADD, true);
		toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_DELETE, false);
		toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_MOVE_UP, false);
		toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_MOVE_DOWN, false);
		toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_EDIT, false);
		
		if (table.getItemCount()==0)
			return;
		int index = table.getSelectionIndex();
		if (index == -1)
			return;
		toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_DELETE, true);
		toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_EDIT, true);
		if (index==0) {
			if (table.getItemCount()!=1)
				toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_MOVE_DOWN, true);
		} else if (index == table.getItemCount()-1) {
			toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_MOVE_UP, true);
		} else {
			toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_MOVE_UP, true);
			toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_MOVE_DOWN, true);
		}
	}
	
	private ControlAdapter getResizeListener() {
		ControlAdapter adapter = new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = tableParent.getClientArea();
				Point preferredSize = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2*table.getBorderWidth();
				if (preferredSize.y > area.height + table.getHeaderHeight()) {
					Point vBarSize = table.getVerticalBar().getSize();
					width -= vBarSize.x;
				}
				Point oldSize = table.getSize();
				if (oldSize.x > area.width) {
					int colWidth = width/table.getColumnCount();
					for (int i=0; i<table.getColumnCount(); i++) {
						table.getColumn(i).setWidth(colWidth);
					}
					table.setSize(area.width, area.height);
				} else {
					table.setSize(area.width, area.height);
					int colWidth = width/table.getColumnCount();
					for (int i=0; i<table.getColumnCount(); i++) {
						table.getColumn(i).setWidth(colWidth);
					}
				}
			}
		};
		return adapter;  
	}
	
	private void initializeToolBar(Composite tableParent, boolean hasOrdering, boolean hasEdit) {
		toolBarProvider = new ToolBarProvider(tableParent);
        toolBar = toolBarProvider.createToolbar(false, hasOrdering, hasEdit);

		Listener addItemListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				tableProviderModel.addItem(table);
				table.setSelection(table.getItemCount()-1);
				enableActionButtons();
			}
		};
		toolBarProvider.setAddItemListener(addItemListener);
		
		Listener delItemListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				table.notifyListeners(SWT.MouseDown, new Event());
				int index = table.getSelectionIndex();
				tableProviderModel.deleteItem(table, index);
				setNewSelection(table, index);
				enableActionButtons();				
			}
		};
		toolBarProvider.setDelItemListener(delItemListener);
		
		Listener moveUpItemListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				int index = table.getSelectionIndex();
				tableProviderModel.moveUpItem(table, index);
				setNewSelection(table, index-1);
				enableActionButtons();
			}
		};
		toolBarProvider.setMoveUpItemListener(moveUpItemListener);

		Listener moveDownItemListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				int index = table.getSelectionIndex();
				tableProviderModel.moveDownItem(table, index);
				setNewSelection(table, index+1);
				enableActionButtons();
			}
		};
		toolBarProvider.setMoveDownItemListener(moveDownItemListener);
		
		if (hasEdit) {
			Listener editItemListener = new Listener() {
				@Override
				public void handleEvent(Event event) {
					int index = table.getSelectionIndex();
					((TableProviderEditModel) tableProviderModel).editItem(table, index);
					setNewSelection(table, index);
					enableActionButtons();
				}
			};
			toolBarProvider.setEditItemListener(editItemListener);
		}
	}
	
	private void setNewSelection(Table table, int index) {
		if (table.getItemCount() > index)
			table.setSelection(index);
		else if (table.getItemCount() == index)
			table.setSelection(index-1);
		else if (table.getItemCount() > 0)
			table.setSelection(0);
	}
	
    private TableItem fetchSelectedTableItem(Table table, Point pt, int startRow) {
        TableItem par = null;
        for (int i=startRow; i<table.getItemCount(); i++) {
            par = table.getItem(i);
            for (int j=0; j<table.getColumnCount(); j++) {
                if (par.getBounds(j).contains(pt))
                    return par;
            }
        }
        return null;
    }
    
    public MouseListener tableTextModifyMouseListener(final Table table, final int editCol) {
    	return tableTextModifyMouseListener(table, editCol, 0, TYPE_TEXT, null);
    }

    public MouseListener tableTextModifyMouseListener(final Table table, final int editCol, final int startRow, final int type, final String[] items) {
        final TableEditor editor = new TableEditor (table);
        MouseListener listener = new MouseListener() {
            ControlListener columnMoveListener = null;
            public void mouseDown(MouseEvent e) {
                Control old = editor.getEditor();
                if (old != null) old.dispose();
            }
            
            public void mouseDoubleClick(MouseEvent event) {
                Control old = editor.getEditor();
                if (old != null) old.dispose();

                Point pt = new Point(event.x, event.y);
                final TableItem item = fetchSelectedTableItem(table, pt, startRow);
                if (item != null) {
                    int column = -1;
                    for (int i = 0, n = table.getColumnCount(); i < n; i++) {
                        Rectangle rect = item.getBounds(i);
                        if (rect.contains(pt)) {
                            column = i;
                            break;
                        }
                    }
                    if (column != editCol)
                        return;
                    if (columnMoveListener != null)
                        table.getColumn(column).removeControlListener(columnMoveListener);
                    columnMoveListener = getColumnMoveListener(table, editor, column);
                    table.setFocus();
                    if (type == TYPE_TEXT)
                    	tableTextEditor(table, item, column, editor, columnMoveListener);
                    else if (type == TYPE_COMBO)
                    	tableComboEditor(table, item, column, editor, columnMoveListener, items);
                }
            }
            
            public void mouseUp(MouseEvent e) {
            }
        };
        return listener;
    }
 
    private void tableTextEditor(final Table table, final TableItem item, final int column, final TableEditor editor, final ControlListener columnMoveListener) {
        final Text text = new Text(table, SWT.NONE);
        text.setBackground(item.getBackground());
        text.setText(item.getText(column));
        text.selectAll();
        text.setFocus();
        
        editor.setEditor(text, item, column);
        editor.minimumWidth = Math.max(table.getColumn(column).getWidth(), text.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
        table.getColumn(column).setWidth(editor.minimumWidth);
        table.getColumn(column).addControlListener(columnMoveListener);

        text.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent event) {
            	boolean isModified = false;
            	if (!item.getText(column).equals(text.getText())) {
                	isModified = true;
                }
            	item.setText(column, text.getText());
            	if (isModified)
            		table.notifyListeners(SWT.Modify, new Event());
            }
        });
    
        text.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                switch (event.keyCode) {
                case SWT.CR:
                    item.setText(column, text.getText());
                case SWT.ESC:
                    text.dispose();
                    break;
                }
            }
        });
    }
	
    private void tableComboEditor(final Table table, final TableItem item, final int column, final TableEditor editor, final ControlListener columnMoveListener, final String[] items) {
        final Combo combo = new Combo(table, SWT.DROP_DOWN | SWT.READ_ONLY);
        combo.setBackground(item.getBackground());
        combo.setItems(items);
        combo.setText(item.getText(column));
        combo.setFocus();
        
        editor.setEditor(combo, item, column);
        editor.minimumWidth = Math.max(table.getColumn(column).getWidth(), combo.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
        table.getColumn(column).setWidth(editor.minimumWidth);
        table.getColumn(column).addControlListener(columnMoveListener);

        combo.addListener(SWT.Selection, new Listener() {
        	public void handleEvent(Event event) {
            	boolean isModified = false;
            	if (!item.getText(column).equals(combo.getText())) {
                	isModified = true;
                }
            	item.setText(column, combo.getText());
            	if (isModified)
            		table.notifyListeners(SWT.Modify, new Event());
            }
        });
        
        combo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                switch (event.keyCode) {
                case SWT.CR:
                    item.setText(column, combo.getText());
                case SWT.ESC:
                    combo.dispose();
                    break;
                }
            }
        });
        
    }
    
    private void tableCheckboxEditor(final Table table, final TableItem item, final int column, TableEditor editor) {
    	final Button button = new Button(table, SWT.CHECK);
        button.setBackground(item.getBackground());
        button.setFocus();
        button.pack();
        
        editor.minimumWidth = button.getSize().x;
        //editor.minimumWidth = Math.max(table.getColumn(column).getWidth(), button.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
        //table.getColumn(column).setWidth(editor.minimumWidth);
        editor.horizontalAlignment = SWT.CENTER;
    	editor.setEditor(button, item, column);

        button.addListener(SWT.Selection, new Listener() {
        	public void handleEvent(Event event) {
        		table.notifyListeners(SWT.Modify, new Event());
        	}
        });
    }
    
    private ControlListener getColumnMoveListener(final Table table, final TableEditor editor, final int column) {
        ControlListener listener = new ControlListener() {
            public void controlMoved(ControlEvent e) {
                Control old = editor.getEditor();
                if (old != null) old.dispose();
                table.getColumn(column).removeControlListener(this);            
            }

            public void controlResized(ControlEvent e) {
                Control old = editor.getEditor();
                if (old != null) old.dispose();
                table.getColumn(column).removeControlListener(this);
            }
        };
        return listener;
    }
   
    public ToolBar getToolBar() {
    	return toolBar;
    }
    
}
