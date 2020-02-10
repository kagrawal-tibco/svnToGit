package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.studio.ui.util.PanelUiUtil;

public class CompositeIndexSelectionDialog extends ConfigElementSelectionDialog {
    protected List<String> selectedProperties;
    protected String indexName;

    public CompositeIndexSelectionDialog(Composite comp) {
        super(comp.getShell());
        this.selectedProperties = new ArrayList<String>();
        this.indexName = "";
    }

    @Override
    public void open(ArrayList<?> properties, ArrayList<?> filter) {
        initDialog("Create Composite Index", true);
        
        for (Object property : properties) {
            TableItem item = new TableItem(table, SWT.NONE);
            item.setImage(ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY));
            item.setText((String) property);
            item.setData(item);
            if (filter.contains(property))
                item.setChecked(true);
        }

        bOK.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
                for (TableItem item : table.getItems()) {
                    if (item.getChecked()) {
                        selectedProperties.add(item.getText(0));
                    }
                }
                dialog.dispose();
            }
        });
        openDialog();
    }

    public void initDialog(String title, final boolean multiSelect) {
        shell = getParent();
        dialog = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.TITLE | SWT.BORDER);
        dialog.setText(title);
        dialog.setImage(ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY_GROUP));
        dialog.setLayout(new GridLayout(2, false));

        Group selectIndexes = new Group(dialog, SWT.NONE);
        selectIndexes.setLayout(new GridLayout(1, false));
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 2;
        gd.widthHint = 500;
        selectIndexes.setLayoutData(gd);
        selectIndexes.setText("Select Properties");

        table = new Table(selectIndexes, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        table.setSize(300, 400);

        table.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
                bOK.setEnabled(false);
                if (enableBOK()) {
                    bOK.setEnabled(true);
                }
            }
        });

        table.setLayoutData(new GridData(GridData.FILL_BOTH));

        Composite butComp = new Composite(dialog, SWT.NONE);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        butComp.setLayoutData(gd);
        butComp.setLayout(new GridLayout(3, false));

        PanelUiUtil.createLabelFiller(butComp);

        bOK = new Button(butComp, SWT.PUSH);
        bOK.setText("OK");
        gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        gd.widthHint = 80;
        bOK.setLayoutData(gd);
        bOK.setEnabled(false);

        bCancel = new Button(butComp, SWT.PUSH);
        bCancel.setText("Cancel");
        gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        gd.widthHint = 80;
        bCancel.setLayoutData(gd);
        butComp.pack();
    }

    public List<String> getselectedProperties() {
        return this.selectedProperties;
    }

    private boolean enableBOK() {
        for (TableItem item : table.getItems()) {
            if (item.getChecked()) {
                return true;
            }
        }
        return false;
    }
}