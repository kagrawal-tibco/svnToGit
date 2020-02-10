package com.tibco.cep.bpmn.core.doc.concept;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.bpmn.core.doc.DocumentDataBean;
import com.tibco.cep.bpmn.core.doc.DocumentHyperLink;

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
	
	/**
	 * link to parentConcept if any.
	 */
	private DocumentHyperLink parentConcept;
	private List<String> stateModels;// TODO: hyperlink to stateModle page
	private List<DocumentationConceptProperty> properties;

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

	public DocumentHyperLink getParentConcept() {
		computeHyperLink(parentConcept);
		return parentConcept;
	}

	public void setParentConcept(DocumentHyperLink parentConcept) {
		this.parentConcept = parentConcept;
	}

	public List<String> getStateModels() {
		return stateModels;
	}

	public void setStateModels(List<String> stateModels) {
		this.stateModels = stateModels;
	}

	public List<DocumentationConceptProperty> getProperties() {
		if (properties != null) {
			for (DocumentationConceptProperty property : properties) {
				computeHyperLink(property.getPropertyLink());
				if (property.getDomains() != null) {
					for (DocumentHyperLink domainLink : property.getDomains()) {
						computeHyperLink(domainLink);
					}
				}
			}
		}
		return properties;
	}

	public void addProperty(String name, String type, boolean multiple,
			String policy, int history, List<DocumentHyperLink> domains, DocumentHyperLink propertyLink) {
		if (this.properties == null) {
			this.properties = new ArrayList<DocumentationConceptProperty>();
		}
		this.properties.add(new DocumentationConceptProperty(name, type,
				multiple, policy, history, domains, propertyLink));
	}

	private static class DocumentationConceptProperty {
		private String name;
		private String type;
		private boolean multiple;
		private String policy;
		private int history;
		private List<DocumentHyperLink> domains;
		private DocumentHyperLink propertyLink;
		
		public DocumentationConceptProperty(String name, String type,
				boolean multiple, String policy, int history, List<DocumentHyperLink> domains, DocumentHyperLink propertyLink) {
			this.name = name;
			this.type = type;
			this.multiple = multiple;
			this.policy = policy;
			this.history = history;
			this.domains = domains;
			this.propertyLink = propertyLink;
		}
		
		@SuppressWarnings("unused")
		public String getName() {
			return name;
		}

		@SuppressWarnings("unused")
		public String getType() {
			return type;
		}

		@SuppressWarnings("unused")
		public boolean isMultiple() {
			return multiple;
		}

		@SuppressWarnings("unused")
		public String getPolicy() {
			return policy;
		}

		@SuppressWarnings("unused")
		public int getHistory() {
			return history;
		}
		
		public DocumentHyperLink getPropertyLink() {
			return propertyLink;
		}
		public List<DocumentHyperLink> getDomains() {
			return domains;
		}
	}
}
