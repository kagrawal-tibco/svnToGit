package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Dec 19, 2009 12:41:20 PM
 */

public class TreeCellEditorProvider {
	
	private static String DO_MODE_CACHE = "Cache";
	private static String DO_MODE_MEMORY = "Memory";
	private static String DO_MODE_CACHE_MEMORY = "Cache+Memory";
	
	private static String VALUE_FIELD_SEP = ";";
	
	public TreeCellEditorProvider() {
		
	}
	
    
    public void createTreeTextEditor(final Tree tree, final TreeItem item, final int column, final TreeEditor editor) {
        if (column == -1)
        	return;
    	final Text text = new Text(tree, SWT.BORDER);
        text.setBackground(item.getBackground());

        text.setText(item.getText(column));
        text.selectAll();
        text.setFocus();
        if (column==0)
        	editor.minimumWidth = Math.max(tree.getColumn(column).getWidth(), text.computeSize(SWT.DEFAULT, SWT.DEFAULT).x) - item.getTextBounds(column).x;
        else
        	editor.minimumWidth = Math.max(tree.getColumn(column).getWidth(), text.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
        editor.minimumHeight = text.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
        editor.setEditor(text, item, column);
        tree.redraw();
        //tree.getColumn(column).setWidth(editor.minimumWidth);
        
        text.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent event) {
            	boolean isModified = false;
            	if (!item.getText(column).equals(text.getText())) {
                	isModified = true;
                }
            	item.setText(column, text.getText());
            	// Need to cache "isModified" and then refer back, since the editor contents aren't committed otherwise
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
    
    public void createTreeComboEditor(final Tree tree, final TreeItem item, final int column, final TreeEditor editor, final String items[]) {
        if (column == -1)
        	return;
    	final Combo combo = new Combo(tree, SWT.BORDER);
        combo.setBackground(item.getBackground());
        combo.setItems(items);
        
        combo.setText(item.getText(column));
        combo.setFocus();
       	editor.minimumWidth = Math.max(tree.getColumn(column).getWidth(), combo.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
        editor.minimumHeight = combo.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
       	//tree.getColumn(column).setWidth(editor.minimumWidth);
        editor.setEditor(combo, item, column);
        tree.redraw();
        
        combo.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent event) {
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
	
    protected class DomainObjectEditor extends Composite {
    	private Button bDeploy, bSubscribe;
    	private Combo cMode;	
    	private Text tPreprocessor;
    	private Composite comp;
    	
		public DomainObjectEditor(Composite parent, int style) {
			super(parent, style);
			comp = new Composite(this, SWT.BORDER);
			comp.setLayoutData(new GridData(GridData.FILL_BOTH));
			setLayout(PanelUiUtil.getCompactGridLayout(2, false));
			comp.setLayout(new GridLayout(2, false));

			bDeploy = PanelUiUtil.createCheckBox(comp, "Deployed");
			PanelUiUtil.createLabelFiller(comp);
			
			Label lMode = PanelUiUtil.createLabel(comp, "Mode:");
			cMode = PanelUiUtil.createComboBox(comp, new String[] {DO_MODE_MEMORY, DO_MODE_CACHE, DO_MODE_CACHE_MEMORY});

			bSubscribe = PanelUiUtil.createCheckBox(comp, "Subscribe Cluster");
			PanelUiUtil.createLabelFiller(comp);

			Label lPreprocessor = PanelUiUtil.createLabel(comp, "Preprocessor:");
			tPreprocessor = PanelUiUtil.createText(comp);
			
			comp.pack();
		}

		public void setText(String str) {
			String values[] = str.split(VALUE_FIELD_SEP);
			if (values != null && values.length==4) {
				boolean isDeployed = new Boolean(values[0]).booleanValue();
				bDeploy.setSelection(isDeployed);
				cMode.setText(values[1]);
				boolean isSubscribe = new Boolean(values[2]).booleanValue();
				bSubscribe.setSelection(isSubscribe);
				tPreprocessor.setText(values[3]);
			}
		}
		
		public String getText() {
			StringBuffer value = new StringBuffer();
			value.append(new Boolean(bDeploy.getSelection()).toString());
			value.append(VALUE_FIELD_SEP);
			value.append(cMode.getText());
			value.append(VALUE_FIELD_SEP);
			value.append(new Boolean(bSubscribe.getSelection()).toString());
			value.append(tPreprocessor.getText());
			return (value.toString());
		}
		
		public void selectAll() {
			//tSubscribe.selectAll();
		}

		public void addModifyListener(ModifyListener modifyListener) {
			//tSubscribe.addModifyListener(modifyListener);
		}
    }
    
    public void createTreeDomainObjectEditor(final Tree tree, final TreeItem item, final int column, final TreeEditor editor) {
        if (column == -1)
        	return;
        
    	final DomainObjectEditor text = new DomainObjectEditor(tree, SWT.BORDER);
        text.setBackground(item.getBackground());
        text.setText(item.getText(column));
        text.selectAll();
        text.setFocus();
        
       	editor.minimumWidth = Math.max(tree.getColumn(column).getWidth(), text.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
        editor.minimumHeight = text.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
        editor.verticalAlignment = SWT.TOP;
        editor.setEditor(text, item, column);
        tree.redraw();
        //tree.getColumn(column).setWidth(editor.minimumWidth);
        
        text.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent event) {
            	boolean isModified = false;
            	if (!item.getText(column).equals(text.getText())) {
                	isModified = true;
                }
            	item.setText(column, text.getText());
            	// Need to cache "isModified" and then refer back, since the editor contents aren't committed otherwise
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

    protected class ConnectionEditor extends Composite {
    	private Button bPoolEnforce, bActive;
    	private Text tInstance, tUri, tSchema, tInitial, tMin, tMax, tWaitTimeout, tInactivityTimeout, tRetryInterval, tFailback; 
    	private Composite comp;
    	
    	/*
    	 			<property name="instance-id" value="0"/>
					<property name="be.oracle.dburi" value="path to jdbc-connection-resource"/>
					<property name="be.oracle.dburi.pool.enforce" value="false"/>
					<property name="be.oracle.dburi.schema" value="be"/>
					<property name="be.oracle.dburi.pool.initial" value="10"/>
					<property name="be.oracle.dburi.pool.min" value="2" />
					<property name="be.oracle.dburi.pool.max" value="10"/>
					<property name="be.oracle.dburi.pool.waitTimeout" value="1"/>
					<property name="be.oracle.dburi.pool.inactivityTimeout" value="900"/>
					<property name="be.oracle.dburi.pool.retryinterval" value="5"/>
					<property name="be.oracle.dburi.active" value="true"/>
					<property name="be.oracle.dburi.failBack" value="bbbb"/>
    	 */
    	
		public ConnectionEditor(Composite parent, int style) {
			super(parent, style);
			comp = new Composite(this, SWT.BORDER);
			comp.setLayoutData(new GridData(GridData.FILL_BOTH));
			setLayout(PanelUiUtil.getCompactGridLayout(2, false));
			comp.setLayout(new GridLayout(2, false));
			
			PanelUiUtil.createLabel(comp, "Instance Id: ");
			tInstance = PanelUiUtil.createText(comp);

			PanelUiUtil.createLabel(comp, "Uri: ");
			tUri = PanelUiUtil.createText(comp);

			PanelUiUtil.createLabel(comp, "Pool Enforce: ");
			bPoolEnforce = PanelUiUtil.createCheckBox(comp, "");
			
			PanelUiUtil.createLabel(comp, "Schema: ");
			tSchema = PanelUiUtil.createText(comp);

			PanelUiUtil.createLabel(comp, "Initial pool size: ");
			tInitial = PanelUiUtil.createText(comp);

			PanelUiUtil.createLabel(comp, "Min pool size: ");
			tMin = PanelUiUtil.createText(comp);

			PanelUiUtil.createLabel(comp, "Max pool size: ");
			tMax = PanelUiUtil.createText(comp);

			PanelUiUtil.createLabel(comp, "Wait Timeout: ");
			tWaitTimeout = PanelUiUtil.createText(comp);

			PanelUiUtil.createLabel(comp, "Inactivity Timeout: ");
			tInactivityTimeout = PanelUiUtil.createText(comp);

			PanelUiUtil.createLabel(comp, "Retry Interval: ");
			tRetryInterval = PanelUiUtil.createText(comp);
			
			PanelUiUtil.createLabel(comp, "Active: ");
			bActive = PanelUiUtil.createCheckBox(comp, "");
			
			PanelUiUtil.createLabel(comp, "Fail Back: ");
			tFailback = PanelUiUtil.createText(comp);
			
			comp.pack();
		}

		public void setText(String str) {
			String values[] = str.split(VALUE_FIELD_SEP);
			if (values != null && values.length==12) {
				tInstance.setText(values[0]);
				tUri.setText(values[1]);
				boolean poolEnforce = new Boolean(values[2]).booleanValue();
				bPoolEnforce.setSelection(poolEnforce);
				tSchema.setText(values[3]);
				tInitial.setText(values[4]);
				tMin.setText(values[5]);
				tMax.setText(values[6]);
				tWaitTimeout.setText(values[7]);
				tInactivityTimeout.setText(values[8]);
				tRetryInterval.setText(values[9]);
				boolean active = new Boolean(values[10]).booleanValue();
				bActive.setSelection(active);
				tFailback.setText(values[11]);
			}
		}
		
		public String getText() {
			StringBuffer value = new StringBuffer();
			value.append(tInstance.getText());
			value.append(VALUE_FIELD_SEP);
			value.append(tUri.getText());
			value.append(VALUE_FIELD_SEP);
			value.append(new Boolean(bPoolEnforce.getSelection()).toString());
			value.append(VALUE_FIELD_SEP);
			value.append(tSchema.getText());
			value.append(VALUE_FIELD_SEP);
			value.append(tInitial.getText());
			value.append(VALUE_FIELD_SEP);
			value.append(tMin.getText());
			value.append(VALUE_FIELD_SEP);
			value.append(tMax.getText());
			value.append(VALUE_FIELD_SEP);
			value.append(tWaitTimeout.getText());
			value.append(VALUE_FIELD_SEP);
			value.append(tInactivityTimeout.getText());
			value.append(VALUE_FIELD_SEP);
			value.append(tRetryInterval.getText());
			value.append(VALUE_FIELD_SEP);
			value.append(new Boolean(bActive.getSelection()).toString());
			value.append(VALUE_FIELD_SEP);
			value.append(tFailback.getText());
			value.append(VALUE_FIELD_SEP);			
			return (value.toString());
		}
		
		public void selectAll() {
			//tSubscribe.selectAll();
		}

		public void addModifyListener(ModifyListener modifyListener) {
			//tSubscribe.addModifyListener(modifyListener);
		}
    }
    
    public void createTreeConnectionEditor(final Tree tree, final TreeItem item, final int column, final TreeEditor editor) {
        if (column == -1)
        	return;
        
    	final ConnectionEditor text = new ConnectionEditor(tree, SWT.BORDER);
        text.setBackground(item.getBackground());
        text.setText(item.getText(column));
        text.selectAll();
        text.setFocus();
        
        editor.minimumWidth = Math.max(tree.getColumn(column).getWidth(), text.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
        editor.minimumHeight = text.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
        editor.verticalAlignment = SWT.TOP;
        //tree.getColumn(column).setWidth(editor.minimumWidth);
        editor.setEditor(text, item, column);
        tree.redraw();
        
        text.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent event) {
            	boolean isModified = false;
            	if (!item.getText(column).equals(text.getText())) {
                	isModified = true;
                }
            	item.setText(column, text.getText());
            	// Need to cache "isModified" and then refer back, since the editor contents aren't committed otherwise
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

    protected class DirectorySelectionEditor extends Composite {
    	private Button bDir;
    	private Text tDir;
    	private Composite comp;
    	
		public DirectorySelectionEditor(Composite parent, int style) {
			super(parent, style);
			comp = new Composite(this, SWT.BORDER);
			comp.setLayoutData(new GridData(GridData.FILL_BOTH));
			setLayout(PanelUiUtil.getCompactGridLayout(2, false));
			comp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));

			tDir = PanelUiUtil.createText(comp);
			bDir = new Button(comp, SWT.PUSH);
			bDir.setLayoutData(new GridData());
			bDir.setText("...");
			bDir.addListener(SWT.Selection, getDirectoryBrowseListener());
			
			comp.pack();
		}

		private Listener getDirectoryBrowseListener() {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(Event event) {
					DirectoryDialog dlg = new DirectoryDialog(getShell());
					dlg.setFilterPath(tDir.getText());
					//dlg.setText();
					dlg.setMessage("Select a directory");
					String dir = dlg.open();
					if (dir != null) {
						tDir.setText(dir);
					}
				}
			};
			return listener;
		}

		public void setText(String str) {
			tDir.setText(str);
		}
		
		public String getText() {
			return (tDir.getText());
		}
		
		public void selectAll() {
		}

		public void addModifyListener(ModifyListener modifyListener) {
		}
    }
    

    public void createTreeDirectorySelectionEditor(final Tree tree, final TreeItem item, final int column, final TreeEditor editor) {
    	if (column == -1)
    		return;

    	final DirectorySelectionEditor text = new DirectorySelectionEditor(tree, SWT.NONE);
    	text.setBackground(item.getBackground());
    	text.setText(item.getText(column));
    	text.selectAll();
    	text.setFocus();

        editor.minimumWidth = Math.max(tree.getColumn(column).getWidth(), text.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
        editor.minimumHeight = text.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
    	editor.verticalAlignment = SWT.TOP;
    	editor.setEditor(text, item, column);
    	tree.redraw();

    	text.addModifyListener(new ModifyListener() {
    		public void modifyText(ModifyEvent event) {
    			boolean isModified = false;
    			if (!item.getText(column).equals(text.getText())) {
    				isModified = true;
    			}
    			item.setText(column, text.getText());
    			// Need to cache "isModified" and then refer back, since the editor contents aren't committed otherwise
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
}
