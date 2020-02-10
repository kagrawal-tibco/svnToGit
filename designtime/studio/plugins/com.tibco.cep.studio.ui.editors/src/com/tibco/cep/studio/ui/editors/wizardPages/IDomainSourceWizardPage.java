/**
 * 
 */
package com.tibco.cep.studio.ui.editors.wizardPages;


/**
 * Event domain import wizard page should implement this interface.
 * @author aathalye
 *
 */
public interface IDomainSourceWizardPage {
	
	
	/**
	 * The actual source of data.
	 * <p>
	 * e.g: Excel file/DB connection etc.
	 * @return
	 */
	<O extends Object> O getDataSource();
	
	/**
	 * Get project name
	 * @return
	 */
	String getProjectName();
	
	/**
	 * Set project name
	 * @param projectName
	 */
	void setProjectName(String projectName);
}
