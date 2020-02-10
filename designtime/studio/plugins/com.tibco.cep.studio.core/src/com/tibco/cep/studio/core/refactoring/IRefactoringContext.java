package com.tibco.cep.studio.core.refactoring;

public interface IRefactoringContext {

	public static final int RENAME_REFACTORING = 0;
	public static final int MOVE_REFACTORING = 1;
	public static final int DELETE_REFACTORING = 2;
	
	/**
	 * The type of the refactoring (rename, move, delete)
	 * @return
	 */
	public int getType();
	
	/**
	 * The element upon which the refactoring is taking place
	 * @return
	 */
	public Object getElement();
	
	/**
	 * The name of the project in which this refactoring is taking place
	 * @return
	 */
	public String getProjectName();
	
}
