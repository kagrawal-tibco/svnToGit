package com.tibco.cep.bpmn.core.doc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Bean class to hold data required while creating ProcessSummary page.
 * 
 * @author moshaikh
 * 
 */
public class DocumentationProcessBean extends DocumentDataBean {

	/**
	 * Name of the Process.
	 */
	private String name;

	/**
	 * Name of the parent process.
	 */
	private String parentProcessName;

	/**
	 * Is check point enabled in case of subProcess.
	 */
	private Boolean checkpoint;

	/**
	 * Is TriggerByEvent enabled in case of subProcess.
	 */
	private Boolean triggerByEvent;

	/**
	 * Description for current process.
	 */
	private String description;

	/**
	 * Process type.
	 */
	private String processType;

	/**
	 * Author of this process.
	 */
	private String author;

	/**
	 * Revision number of process.
	 */
	private Integer revision;

	/**
	 * List of variables
	 */
	private List<DocumentationVariable> variables;

	/**
	 * Map containing link to concepts that are being referred in this process and their descriptions.
	 */
	private Map<DocumentHyperLink, String> concepts;

	public DocumentationProcessBean() {
		super("processSummaryPageTemplate");
		this.setSinceVersion("5.1");
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getRevision() {
		return revision;
	}

	public void setRevision(Integer revision) {
		this.revision = revision;
	}

	public boolean isShowProperties() {
		return (author != null || processType != null || revision != null
				|| checkpoint != null || triggerByEvent != null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentProcessName() {
		return parentProcessName;
	}

	public void setParentProcessName(String parentProcessName) {
		this.parentProcessName = parentProcessName;
	}

	public boolean isShowCheckpoint() {
		return checkpoint != null;
	}

	public Boolean getCheckpoint() {
		return checkpoint;
	}

	public void setCheckpoint(Boolean checkpoint) {
		this.checkpoint = checkpoint;
	}

	public boolean isShowTriggerByEvent() {
		return triggerByEvent != null;
	}

	public Boolean getTriggerByEvent() {
		return triggerByEvent;
	}

	public void setTriggerByEvent(Boolean triggerByEvent) {
		this.triggerByEvent = triggerByEvent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<DocumentationVariable> getVariables() {
		if (variables != null) {
			for (DocumentationVariable var : variables) {
				computeHyperLink(var.getHyperlink());
			}
			Collections.sort(variables);
		}
		return variables;
	}

	public void setVariables(List<DocumentationVariable> variables) {
		this.variables = variables;
	}

	/**
	 * Adds a variable to the list of variables.
	 * 
	 * @param name
	 * @param type
	 * @param collection
	 */
	public void addVariable(String name, String type, boolean collection) {
		if (variables == null) {
			variables = new ArrayList<DocumentationVariable>();

		}
		variables.add(new DocumentationVariable(name, type, collection));
	}

	/**
	 * Adds a variable with attached HyperLink to the list of variables.
	 * 
	 * @param name
	 * @param type
	 * @param collection
	 * @param hyperlink
	 */
	public void addVariable(String name, String type, boolean collection,
			DocumentHyperLink hyperlink) {
		if (variables == null) {
			variables = new ArrayList<DocumentationVariable>();

		}
		DocumentationVariable var = new DocumentationVariable(name, type,
				collection);
		var.setHyperlink(hyperlink);
		variables.add(var);
	}

	public Map<DocumentHyperLink, String> getConcepts() {
		if (concepts != null) {
			for (DocumentHyperLink link : concepts.keySet()) {
				computeHyperLink(link);
			}
		}
		return concepts;
	}

	public void setConcepts(Map<DocumentHyperLink, String> concepts) {
		this.concepts = concepts;
	}

	/**
	 * POJO for representing variables.
	 * 
	 * @author moshaikh
	 * 
	 */
	public static class DocumentationVariable implements
			Comparable<DocumentationVariable> {

		/**
		 * Variable name.
		 */
		private String name;

		/**
		 * Variable type.
		 */
		private String type;

		/**
		 * Denotes whether this variable is multivalued (Array).
		 */
		private boolean collection;

		/**
		 * HyperLink object if this variable links to another page.
		 */
		private DocumentHyperLink hyperlink;

		/**
		 * Create Documentation Variable which does not link to any other page
		 * on UI, HyperLink has to be attached separately.
		 * 
		 * @param name
		 * @param type
		 * @param collection
		 */
		public DocumentationVariable(String name, String type,
				boolean collection) {
			this.name = name;
			this.type = type;
			this.collection = collection;
		}

		public String getName() {
			return name;
		}

		public String getType() {
			return type;
		}

		public boolean isCollection() {
			return collection;
		}

		public void setHyperlink(DocumentHyperLink hyperlink) {
			this.hyperlink = hyperlink;
		}

		public DocumentHyperLink getHyperlink() {
			return hyperlink;
		}

		@Override
		public int compareTo(DocumentationVariable o) {
			return getName().compareTo(o.getName());
		}
	}
}