package com.tibco.cep.decision.table.model;

import javax.swing.table.DefaultTableModel;
/**
 * 
 * @author sasahoo
 *
 */
public class DTDeclTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 7597838343050992395L;

	DTDeclTableModel(Object rows[][],Object columns[])
	{
		super(rows,columns);
	}
}
