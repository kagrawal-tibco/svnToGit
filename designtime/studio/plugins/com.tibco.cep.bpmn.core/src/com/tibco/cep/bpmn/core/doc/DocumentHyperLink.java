package com.tibco.cep.bpmn.core.doc;

/**
 * HyperLink POJO, to be used for links while documentation generation.
 * 
 * @author moshaikh
 * 
 */
public class DocumentHyperLink implements Comparable<DocumentHyperLink> {
	private String name;
	private String target;
	private String link;
	private String title;

	/**
	 * Holds the id of the entity that this link points to. Used while creating
	 * linkURL when path is not available upfront and needs to be generated after
	 * the targeted page's bean is created.
	 */
	private String targetId;

	public DocumentHyperLink(String name, String link, String target,
			String title) {
		this.name = (name == null ? "" : name);
		this.link = link;
		this.target = (target == null ? "" : target);
		this.title = (title == null ? "" : title);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	@Override
	public String toString() {
		return name + " : " + link;
	}

	/**
	 * Compares HyperLink instances based on the display names.
	 */
	@Override
	public int compareTo(DocumentHyperLink o) {
		return this.getName().compareToIgnoreCase(o.getName());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DocumentHyperLink && name != null) {
			return name.equals(((DocumentHyperLink) obj).getName());
		}
		return false;
	}

}