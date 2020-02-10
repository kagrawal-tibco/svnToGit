package com.tibco.cep.bpmn.core.doc;

/**
 * Bean class to hold data needed by displaying Concept documentation.
 * 
 * @author moshaikh
 * 
 */
public class DocumentationConceptBean extends DocumentDataBean {

	/**
	 * Name of the Concept.
	 */
	private String name;

	/**
	 * Description about the Concept.
	 */
	private String description;

	// TODO: Parent Concept, List of stateModels, List of properties

	public DocumentationConceptBean() {
		super("conceptPageTemplate");
	}

	public String getName() {
		return name;
	}

	public void setName(String conceptName) {
		this.name = conceptName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
