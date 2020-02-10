package com.tibco.cep.studio.tester.ui.editor.result;


import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * 
 * @author smarathe
 *
 */
public class ResultTableModelListener implements TableModelListener {

	public ResultTableModelListener() {
		
		
	}
	
	@Override
	public void tableChanged(TableModelEvent e) {
		System.out.print("Here"+e);
		
	}

	

}
