package com.tibco.cep.decision.table.editors;

import java.util.Collection;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.decision.table.codegen.DTImplCGTuple;
import com.tibco.cep.decision.table.ui.utils.Messages;
import com.tibco.cep.decision.table.utils.DTImages;


public class DeployAllResultDialog extends ResultGridDialog {

	private final Collection<DTImplCGTuple> cgTups;
	
	public DeployAllResultDialog(Shell shell, Collection<DTImplCGTuple> cgTups) {
		super(shell);
		this.cgTups= cgTups;
	}
	
	protected void addResults(Table table) {
		if (cgTups != null) {
			for (DTImplCGTuple tup : cgTups) {
				TableItem item = new TableItem(table, SWT.NONE);
				com.tibco.cep.decision.table.model.dtmodel.Table dt = tup.getTable();
				String tableName = dt.getImplements();
				if(tableName == null) tableName = "";
				if(!tableName.endsWith("/")) tableName += "/";
				tableName += dt.getName();
				String message = tup.getMessage();
				if(tableName != null && message != null) {
					item.setText(0, tableName);
					item.setText(1, message);
				}
			}
		}
	}
	
	protected void createColumns(Table table) {
		TableColumn col;
		col = new TableColumn(table, SWT.NONE);
		col.setText(Messages.getString("DeployAllDialog_Col_TableName"));
		col.setWidth(200);
		col.setMoveable(false);
		col.setResizable(true);
		col = new TableColumn(table, SWT.NONE);
		col.setText(Messages.getString("DeployAllDialog_Col_DeploymentResul"));
		col.setWidth(200);
		col.setMoveable(false);
		col.setResizable(true);
	}

	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		getShell().setText(Messages.getString("DeployAllDialog_Window_Title"));
		setTitle(Messages.getString("DeployAllDialog_Title"));
		Image image = DTImages.getImage(DTImages.DT_IMAGES_NEW_DT);
		setMessage(Messages.getString("DeployAllDialog_Message"),
				IMessageProvider.INFORMATION);
		if (image != null)
			setTitleImage(image);
		return contents;
	}
}
 