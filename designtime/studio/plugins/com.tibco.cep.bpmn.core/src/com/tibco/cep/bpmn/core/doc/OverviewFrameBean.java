package com.tibco.cep.bpmn.core.doc;

import java.util.Collections;
import java.util.List;

/**
 * Bean class to hold data needed by overviewFrameTemplate.
 * @author moshaikh
 *
 */
public class OverviewFrameBean extends DocumentDataBean {
	private List<DocumentHyperLink> entries;
	
	public OverviewFrameBean() {
		super("overviewFrameTemplate");
	}

	public List<DocumentHyperLink> getEntries() {
		Collections.sort(entries);
		return entries;
	}

	public void setEntries(List<DocumentHyperLink> entries) {
		this.entries = entries;
	}
}
