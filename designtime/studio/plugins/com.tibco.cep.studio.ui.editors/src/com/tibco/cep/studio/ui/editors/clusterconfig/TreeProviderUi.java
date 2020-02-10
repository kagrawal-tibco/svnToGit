package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Property;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElementList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyGroup;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.studio.ui.util.ToolBarProvider;

/*
@author ssailapp
@date Dec 8, 2009 2:57:53 PM
 */

public class TreeProviderUi {

	public static int TYPE_TEXT = 0;
	public static int TYPE_COMBO = 1;
	
	private Composite comp;
	protected Composite treeParent;
	private String[] columns;
	private String[] keys;
	private boolean hasToolBar;
	private TreeProviderModel treeProviderModel;
	private ToolBarProvider toolBarProvider;
	private ToolBar toolBar;
	private Tree tree;
	
	public TreeProviderUi(Composite comp, String columns[], boolean hasToolBar, TreeProviderModel treeProviderModel, String[] keys) {
		this.comp = comp;
		this.columns = columns;
		this.hasToolBar = hasToolBar;
		this.treeProviderModel = treeProviderModel;
		this.keys = keys;
	}
	
	public Tree createTree() {
		treeParent = new Composite(comp, SWT.NONE);
		treeParent.setLayout(PanelUiUtil.getCompactGridLayout(1, true));
		GridData gd = new GridData(GridData.FILL_BOTH);
		treeParent.setLayoutData(gd);
		
		if (hasToolBar)
        	initializeToolBar(treeParent);
        
		tree = new Tree(treeParent, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 250;
		gd.heightHint = 120;
		tree.setLayoutData(gd);
		for (String column: columns) {
			new TreeColumn(tree, SWT.LEFT).setText(column);	
		}

		tree.addListener(SWT.Selection, getTreeSelectionListener());
		treeParent.addControlListener(getResizeListener());
		tree.addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event event) {
				event.height = 18;
			}
		});
		
		tree.pack();
		return tree;
	}
	
	public void setTreeData(PropertyElementList propList) {
		setTreeData(propList, true);
	}
	
	public void setTreeData(PropertyElementList propList, boolean refresh) {
		if (propList == null)
			return;
		if (refresh)
			tree.removeAll();
		for (PropertyElement prop: propList.propertyList) {
	    	TreeItem item = new TreeItem(tree, SWT.NONE);
	    	setTreeData(prop, item);
	    }
		
	    packTree();
	    enableActionButtons();
	}

	public void setTreeData(PropertyGroup propGrp, TreeItem propGrpItem) {
		propGrpItem.setText(0, propGrp.name);
		treeProviderModel.setPropertyIconImage(propGrpItem);
		for (PropertyElement prop: propGrp.propertyList) {
	    	TreeItem item = new TreeItem(propGrpItem, SWT.NONE);
	    	setTreeData(prop, item);
	    }
	}
	
	private void setTreeData(PropertyElement prop, TreeItem item) {
    	item.setData(prop);
		if (prop instanceof Property) {
	    	for (int i=0; i<tree.getColumnCount(); i++) {
	    		String cellData = (String) ((Property)prop).fieldMap.get(keys[i]);
	    		item.setText(i, cellData != null ? cellData: "");
	    	}
	    	treeProviderModel.setPropertyIconImage(item);
    	} else if (prop instanceof PropertyGroup) {
    		setTreeData((PropertyGroup) prop, item);
    	}
	}
	
	public void packTree() {
		Rectangle area = tree.getClientArea();
		int colWidth = area.width/tree.getColumnCount();
		if (colWidth < PanelUiUtil.TEXT_FIELD_SIZE_HINT)
			colWidth = PanelUiUtil.TEXT_FIELD_SIZE_HINT;
		for (int i=0; i<tree.getColumnCount(); i++) {
			tree.getColumn(i).pack();
			if (tree.getColumn(i).getWidth() < colWidth)
				tree.getColumn(i).setWidth(colWidth);
		}
	}
	
	private Listener getTreeSelectionListener() {
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
		toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_ADD_GRP, true);
		toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_ADD, true);
		toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_MOVE_UP, true);
		toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_MOVE_DOWN, true);
		toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_DELETE, false);		
		
		if (tree.getItemCount()==0)
			return;
		TreeItem[] items = tree.getSelection();
		if (items.length <= 0)
			return;
		toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_DELETE, true);
	}
	
	/*
	private int getSelectionIndex(Tree tree) {
		TreeItem[] items = tree.getSelection();
		if (items.length <= 0)
			return -1;
		TreeItem item = items[0];
		return tree.indexOf(item);
	}
	*/
	
	/*
	 * Enable Droptarget for given column 
	 * */
	public DropTarget setDropTarget(final int editCol, final int startRow) {
		if (tree == null)
			return null;

		DropTarget dt = new DropTarget(tree, DND.DROP_MOVE | DND.DROP_COPY);
		dt.setTransfer(new Transfer[] { TextTransfer.getInstance() });
		dt.addDropListener(new DropTargetAdapter() {
			public void drop(DropTargetEvent event) {
				TreeEditor editor = new TreeEditor(tree);
				Control old = editor.getEditor();
				if (old != null)
					old.dispose();

				Point pt = Display.getCurrent().map(null, tree, event.x,
						event.y);// new Point(event.x, event.y);
				final TreeItem item = getSelectedTreeItem(tree, pt, startRow);
				if (item != null) {
					int column = -1;
					for (int i = 0, n = tree.getColumnCount(); i < n; i++) {
						Rectangle rect = item.getBounds(i);
						if (rect.contains(pt)) {
							column = i;
							break;
						}
					}
					if (column != editCol) {
						return;
					}
					if (column != 0
							&& (item.getData() instanceof PropertyGroup)) {
						return;
					}
					String curText = item.getText(column);
					curText += (String) event.data;
					item.setText(column, curText);

				}
			}

		});

		return dt;
	}
	
	private TreeItem getSelection(Tree tree) {
		TreeItem[] items = tree.getSelection();
		if (items.length <= 0)
			return null;
		TreeItem item = items[0];
		return item;
	}
	
	private TreeItem[] getAllSelected(Tree tree) {
		TreeItem[] items = tree.getSelection();
		if (items.length <= 0)
			return null;
		return items;
	}
	
	private TreeItem getNextSelection(TreeItem item) {
		TreeItem selItem = item.getParentItem();
		if (selItem != null) {
			if (selItem.indexOf(item) < selItem.getItemCount()-1)
				selItem = selItem.getItem(selItem.indexOf(item)+1);
			else if ( (selItem.indexOf(item) == selItem.getItemCount()-1) && selItem.getItemCount()>1)
				selItem = selItem.getItem(selItem.getItemCount()-2);
				
		}
		return selItem;
	}
	
	private void setSelection(Tree tree, TreeItem item) {
		if (item != null && !item.isDisposed())
			tree.setSelection(item);
	}
	
	public ControlAdapter getResizeListener() {
		ControlAdapter adapter = new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = treeParent.getClientArea();
				Point preferredSize = tree.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2*tree.getBorderWidth();
				if (preferredSize.y > area.height + tree.getHeaderHeight()) {
					Point vBarSize = tree.getVerticalBar().getSize();
					width -= vBarSize.x;
				}
				Point oldSize = tree.getSize();
				if (oldSize.x > area.width) {
					int colWidth = width/tree.getColumnCount();
					for (int i=0; i<tree.getColumnCount(); i++) {
						tree.getColumn(i).setWidth(colWidth);
					}
					tree.setSize(area.width, area.height);
				} else {
					tree.setSize(area.width, area.height);
					int colWidth = width/tree.getColumnCount();
					for (int i=0; i<tree.getColumnCount(); i++) {
						tree.getColumn(i).setWidth(colWidth);
					}
				}
			}
		};
		return adapter;  
	}
	
	private void initializeToolBar(Composite treeParent) {
		toolBarProvider = new ToolBarProvider(treeParent);
		toolBar = toolBarProvider.createToolbar(true, true);

		Listener addGrpItemListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				TreeItem item = treeProviderModel.addGroupItem(tree, getSelection(tree));
				setSelection(tree, item);
				enableActionButtons();
			}
		};
		toolBarProvider.setAddGrpItemListener(addGrpItemListener);
		
		Listener addItemListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				TreeItem item = treeProviderModel.addItem(tree, getSelection(tree));
				setSelection(tree, item);
				enableActionButtons();
			}	
		};
		toolBarProvider.setAddItemListener(addItemListener);
		
		Listener delItemListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				tree.notifyListeners(SWT.MouseDown, new Event());
				//int index = getSelectionIndex(tree);
				TreeItem[] items= getAllSelected(tree);
				//TreeItem selItem = getNextSelection(item);
				for(TreeItem item:items){
					treeProviderModel.deleteItem(tree, item);
				}
				
				//setSelection(tree, selItem);
				enableActionButtons();
			}
		};
		toolBarProvider.setDelItemListener(delItemListener);		
		
		Listener mouseUpItemListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				tree.notifyListeners(SWT.MouseDown, new Event());
				TreeItem selectedItem = getSelection(tree);
				TreeItem parentTreeItem = null;
				int index = 0;
            	
            	if(selectedItem.getParentItem() != null)
            		parentTreeItem = selectedItem.getParentItem();
            	
				
				if(parentTreeItem != null){
					int count = parentTreeItem.getItemCount();		
					for(int i = 0; i < count; i++){
						if(selectedItem.equals(parentTreeItem.getItem(i)))
					       index = i;
					}
				}
				else{
					int count = tree.getItemCount(); 
					for(int i = 0; i < count; i++){
						if(selectedItem.equals(tree.getItem(i)))
					       index = i;
					}
				}
				if(index > 0)
				   treeProviderModel.moveUpItem(tree, index, parentTreeItem);
				
				enableActionButtons();
				
					if(parentTreeItem != null){
						selectedItem = parentTreeItem.getItem(index - 1);
						setSelection(tree, selectedItem);
						if(index - 1 == 0 ){
							toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_MOVE_UP, false);
						}
					}
					else{
					   selectedItem = tree.getItem(index -1 );	
					   setSelection(tree, selectedItem);
					   if(index - 1 == 0)
						   toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_MOVE_UP, false); 
					}
				
			}
			
		};
		toolBarProvider.setMoveUpItemListener(mouseUpItemListener);
		
		Listener mouseDownItemListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				tree.notifyListeners(SWT.MouseDown, new Event());
				TreeItem selectedItem = getSelection(tree);  
				TreeItem parentTreeItem = null;
				int index = 0;
            	
            	if(selectedItem.getParentItem() != null)
            		parentTreeItem = selectedItem.getParentItem();
            	
				if(parentTreeItem != null){
					int count = parentTreeItem.getItemCount();		
					for(int i = 0; i < count; i++){
						if(selectedItem.equals(parentTreeItem.getItem(i)))
					       index = i;
					}
				}
				else{
					int count = tree.getItemCount(); 
					for(int i = 0; i < count; i++){
						if(selectedItem.equals(tree.getItem(i)))
					       index = i;
					}
				}
				enableActionButtons();
				if(index < tree.getItemCount()-1 || (parentTreeItem != null && index < parentTreeItem.getItemCount()-1))
					treeProviderModel.moveDownItem(tree, index, parentTreeItem);
				
				if(parentTreeItem != null){
					selectedItem = parentTreeItem.getItem(index + 1);
					setSelection(tree, selectedItem);
					if(index + 1 == parentTreeItem.getItemCount() -1 ){
						toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_MOVE_DOWN, false);
					}
				}
				else{	
				   selectedItem = tree.getItem(index + 1);	
				   setSelection(tree, selectedItem);
				   if(index + 1 == tree.getItemCount() - 1)
					   toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_MOVE_DOWN, false); 
				}
			}
			
		};
		toolBarProvider.setMoveDownItemListener(mouseDownItemListener);
	}
	
	/*
	private void setNewSelection(Tree tree, int index) {
		if (tree.getItemCount() > index)
			setSelection(tree, index);
		else if (tree.getItemCount() == index)
			setSelection(tree, index-1);
		else if (tree.getItemCount() > 0)
			setSelection(tree, 0);
	}
	*/
	
	
    private TreeItem getSelectedTreeItem(Tree tree, Point pt, int startRow) {
        for (int i=startRow; i<tree.getItemCount(); i++) {
        	TreeItem chd = getSelectedTreeItemChild(tree.getItem(i), pt, startRow);
        	if (chd != null)
        		return chd;
        }
        return null;
    }
	
    private TreeItem getSelectedTreeItem(TreeItem treeItem, Point pt, int startRow) {
        for (int i=startRow; i<treeItem.getItemCount(); i++) {
        	TreeItem chd = getSelectedTreeItemChild(treeItem.getItem(i), pt, startRow);
        	if (chd != null)
        		return chd;
        }
        return null;
    }
    
    private TreeItem getSelectedTreeItemChild(TreeItem par, Point pt, int startRow) {
        for (int j=0; j<tree.getColumnCount(); j++) {
            if (par.getBounds(j).contains(pt))
                return par;
        }
        return getSelectedTreeItem(par, pt, startRow);
    }
    
    public MouseListener treeTextModifyMouseListener(final Tree tree, final int editCol) {
    	return treeTextModifyMouseListener(tree, editCol, 0, TYPE_TEXT, null);
    }

    public MouseListener treeTextModifyMouseListener(final Tree tree, final int editCol, final int startRow, final int type, final String[] items) {
        final TreeEditor editor = new TreeEditor (tree);
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
                final TreeItem item = getSelectedTreeItem(tree, pt, startRow);
                if (item != null) {
                    int column = -1;
                    for (int i = 0, n = tree.getColumnCount(); i < n; i++) {
                        Rectangle rect = item.getBounds(i);
                        if (rect.contains(pt)) {
                            column = i;
                            break;
                        }
                    }
                    if (column != editCol)
                        return;
                    if (column!=0 && (item.getData() instanceof PropertyGroup))
                    	return;
                    if (columnMoveListener != null)
                        tree.getColumn(column).removeControlListener(columnMoveListener);
                    columnMoveListener = getColumnMoveListener(tree, editor, column);
                    tree.setFocus();
                    if (type == TYPE_TEXT)
                    	treeTextEditor(tree, item, column, editor, columnMoveListener);
                    else if (type == TYPE_COMBO)
                    	treeComboEditor(tree, item, column, editor, columnMoveListener, items);
                }
            }
            
            public void mouseUp(MouseEvent e) {
            	TreeItem selectedItem = getSelection(tree);
            	TreeItem parentTreeItem = null;
            	
            	if(selectedItem.getParentItem() != null)
            		parentTreeItem = selectedItem.getParentItem();
				int index = 0;
				if(parentTreeItem != null){
					int count = parentTreeItem.getItemCount();		
					for(int i = 0; i < count; i++){
						if(selectedItem.equals(parentTreeItem.getItem(i)))
					       index = i;
					}
				}
				else{
					int count = tree.getItemCount(); 
					for(int i = 0; i < count; i++){
						if(selectedItem.equals(tree.getItem(i)))
					       index = i;
					}
				}
				if(index == 0)
					toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_MOVE_UP, false);
			   
								
			    if(parentTreeItem != null){
			    	if(index == parentTreeItem.getItemCount()-1)
			    		toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_MOVE_DOWN, false);
			    }				
			    else if(index == tree.getItemCount()-1)
					toolBarProvider.enableActionButton(ToolBarProvider.BUTTON_MOVE_DOWN, false);
           }
        };
        return listener;
    }
 
    private void treeTextEditor(final Tree tree, final TreeItem item, final int column, final TreeEditor editor, final ControlListener columnMoveListener) {
        final Text text = new Text(tree, SWT.NONE);
        text.setBackground(item.getBackground());
        text.setText(item.getText(column));
        text.selectAll();
        text.setFocus();
        
        editor.setEditor(text, item, column);
        if (column==0)
        	editor.minimumWidth = Math.max(tree.getColumn(column).getWidth(), text.computeSize(SWT.DEFAULT, SWT.DEFAULT).x) - item.getTextBounds(column).x;
        else
        	editor.minimumWidth = Math.max(tree.getColumn(column).getWidth(), text.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
        
        //tree.getColumn(column).setWidth(editor.minimumWidth);
        tree.getColumn(column).addControlListener(columnMoveListener);

        text.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent event) {
            	boolean isModified = false;
            	if (!item.getText(column).equals(text.getText())) {
                	isModified = true;
                }
            	item.setText(column, text.getText());
            	if (isModified)
            		tree.notifyListeners(SWT.Modify, new Event());
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
	
    private void treeComboEditor(final Tree tree, final TreeItem item, final int column, final TreeEditor editor, final ControlListener columnMoveListener, final String[] items) {
        final Combo combo = new Combo(tree, SWT.DROP_DOWN | SWT.READ_ONLY);
        combo.setBackground(item.getBackground());
        combo.setItems(items);
        combo.setText(item.getText(column));
        combo.setFocus();
        
        editor.setEditor(combo, item, column);
        editor.minimumWidth = Math.max(tree.getColumn(column).getWidth(), combo.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
        tree.getColumn(column).setWidth(editor.minimumWidth);
        tree.getColumn(column).addControlListener(columnMoveListener);

        combo.addListener(SWT.Selection, new Listener() {
        	public void handleEvent(Event event) {
            	boolean isModified = false;
            	if (!item.getText(column).equals(combo.getText())) {
                	isModified = true;
                }
            	item.setText(column, combo.getText());
            	if (isModified)
            		tree.notifyListeners(SWT.Modify, new Event());
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
    
    private ControlListener getColumnMoveListener(final Tree tree, final TreeEditor editor, final int column) {
        ControlListener listener = new ControlListener() {
            public void controlMoved(ControlEvent e) {
                Control old = editor.getEditor();
                if (old != null) old.dispose();
                tree.getColumn(column).removeControlListener(this);            
            }

            public void controlResized(ControlEvent e) {
                Control old = editor.getEditor();
                if (old != null) old.dispose();
                tree.getColumn(column).removeControlListener(this);
            }
        };
        return listener;
    }
   
    public ToolBar getToolBar() {
    	return toolBar;
    }
}
