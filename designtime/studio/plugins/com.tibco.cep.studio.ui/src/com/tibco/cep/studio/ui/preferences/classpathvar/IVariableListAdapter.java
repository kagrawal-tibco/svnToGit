package com.tibco.cep.studio.ui.preferences.classpathvar;


public interface IVariableListAdapter<E> {

	/**
	 * A button from the button bar has been pressed.
	 * 
	 * @param field the dialog field 
	 * @param index the button index
	 */
	void customButtonPressed(VariableListDialogField<E> field, int index);

	/**
	 * The selection of the list has changed.
	 * 
	 * @param field the dialog field 
	 */
	void selectionChanged(VariableListDialogField<E> field);

	/**
	 * An entry in the list has been double clicked
	 * 
	 * @param field the dialog field 
	 */
	void doubleClicked(VariableListDialogField<E> field);

}
