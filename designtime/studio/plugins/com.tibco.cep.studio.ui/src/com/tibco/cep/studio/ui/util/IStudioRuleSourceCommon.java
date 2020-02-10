package com.tibco.cep.studio.ui.util;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolItem;

import com.tibco.cep.designtime.core.model.rule.Compilable;

/**
 * 
 * @author sasahoo
 *
 */
public interface IStudioRuleSourceCommon {

	/**
	 * @param type
	 * @param oldIdName
	 * @param newIdName
	 * @param ruleType
	 * @param blockType
	 * @param statementType
	 */
	public boolean updateDeclarationStatements(String type,
									String id,
						            String oldText, 
						            String newText, 
						            int ruleType, 
						            int blockType, 
						            int statementType, 
						            Compilable compilable);
	
	public Compilable getCommonCompilable();
		
	public ToolItem getRemoveDeclarationButton();
	
	public ToolItem getUpDeclarationButton();
	
	public ToolItem getDownDeclarationButton();
	
	public Table getDeclarationTable();
	
	public void openDeclaration();
}
