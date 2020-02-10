package com.tibco.cep.studio.tester.ui.tools;

import java.util.Map;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;


//import com.tibco.cep.decision.table.utils.Messages;


public class TestResultDialog extends com.tibco.cep.studio.tester.ui.tools.ResultGridDialog {

	private Vector<Map<String, String>> testResultData;

	public TestResultDialog(Shell shell,
			Vector<Map<String, String>> testResultData) {
		super(shell);
		this.testResultData = testResultData;
	}
	
	protected void addResults(Table table) {
		if (testResultData != null) {
			TableItem item = null;
			for (Map<String, String> map : testResultData) {
				item = new TableItem(table, SWT.NONE);
				item.setText(0, map.get("Name"));
				item.setText(1, map.get("Type"));
				item.setText(2, map.get("Created"));
			}
		}
	}
	
	protected void createColumns(Table table) {
		TableColumn col;
		col = new TableColumn(table, SWT.NONE);
		col.setText("Name");
		col.setWidth(300);
		col.setMoveable(false);
		col.setResizable(true);
		col = new TableColumn(table, SWT.NONE);
		col.setText("Type");
		col.setWidth(100);
		col.setMoveable(false);
		col.setResizable(false);
		col = new TableColumn(table, SWT.NONE);
		col.setText("Created");
		col.setWidth(80);
		col.setMoveable(false);
		col.setResizable(false);
	}
	
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		getShell().setText("Load Test Data Dialog");
		setTitle("Load Test Data Dialog");
//		Image image = DTImages.getImage(DTImages.DT_IMAGES_NEW_DT);
//		setMessage(Messages.LoadTestDialog_Message,
//				IMessageProvider.INFORMATION);
//		if (image != null)
//			setTitleImage(image);
		return contents;
	}
}
