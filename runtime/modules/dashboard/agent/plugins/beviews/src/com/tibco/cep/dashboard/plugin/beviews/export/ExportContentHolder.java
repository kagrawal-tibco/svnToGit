package com.tibco.cep.dashboard.plugin.beviews.export;

import java.io.Serializable;
import java.util.Enumeration;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author rajesh
 * 
 */
@SuppressWarnings("serial")
public abstract class ExportContentHolder implements Serializable {

	protected ExportContentNode root;

	protected Logger logger;

	protected ExceptionHandler exceptionHandler;

	protected MessageGenerator messageGenerator;

	protected ExportContentHolder(Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		this.logger = logger;
		this.exceptionHandler = exceptionHandler;
		this.messageGenerator = messageGenerator;
	}

	@SuppressWarnings("unchecked")
	protected ExportContentNode findExportContentNodeByPath(String contentPath) {
		if (root != null) {
			Enumeration enumeration = root.breadthFirstEnumeration();
			while (enumeration.hasMoreElements()) {
				ExportContentNode contentNode = (ExportContentNode) enumeration.nextElement();
				if (contentNode.getContentPath().equals(contentPath)) {
					logger.log(Level.DEBUG, "Found Node for path > " + contentPath);
					return contentNode;
				}
			}
		}
		logger.log(Level.DEBUG, "Did not find Node for path > " + contentPath);
		return null;
	}

	public ExportContentNode getContentRoot() {
		return root;
	}

}