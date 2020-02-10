package com.tibco.cep.bpmn.core.doc;

import java.util.Collections;
import java.util.List;

/**
 * Bean class to hold data needed by packageFrameTemplate.
 * 
 * @author moshaikh
 * 
 */
public class PackagesFrameBean extends DocumentDataBean {
	private String packageName;
	private List<DocumentHyperLink> events;
	private List<DocumentHyperLink> elements;
	private List<DocumentHyperLink> sequences;
	private List<DocumentHyperLink> subProcesses;

	public PackagesFrameBean() {
		super("packageFrameTemplate");
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<DocumentHyperLink> getEvents() {
		if (events != null) {
			for (DocumentHyperLink link : events) {
				computeHyperLink(link);
			}
			Collections.sort(events);
		}
		return events;
	}

	public void setEvents(List<DocumentHyperLink> events) {
		this.events = events;
	}

	public List<DocumentHyperLink> getElements() {
		if (elements != null) {
			for (DocumentHyperLink link : elements) {
				computeHyperLink(link);
			}
			Collections.sort(elements);
		}
		return elements;
	}

	public void setElements(List<DocumentHyperLink> elements) {
		this.elements = elements;
	}

	public List<DocumentHyperLink> getSequences() {
		if (sequences != null) {
			for (DocumentHyperLink link : sequences) {
				computeHyperLink(link);
			}
			Collections.sort(sequences);
		}
		return sequences;
	}

	public void setSequences(List<DocumentHyperLink> sequences) {
		this.sequences = sequences;
	}

	public List<DocumentHyperLink> getSubProcesses() {
		if (subProcesses != null) {
			for (DocumentHyperLink link : subProcesses) {
				computeHyperLink(link);
			}
			Collections.sort(subProcesses);
		}
		return subProcesses;
	}

	public void setSubProcesses(List<DocumentHyperLink> subProcesses) {
		this.subProcesses = subProcesses;
	}
}