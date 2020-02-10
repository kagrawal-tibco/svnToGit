package com.tibco.cep.dashboard.plugin.beviews.export;

import javax.swing.tree.DefaultMutableTreeNode;

public class ExportContentNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -349894342652811727L;

	public static final String TYPE_GROUP_ROW = "group";
	public static final String TYPE_TUPLE_ROW = "tuple";
	public static final String TYPE_HEADER_ROW = "header";

	private String contentPath;
	private boolean bDeleted = false;
	private String nodeType = "";

	public ExportContentNode(String path, String nodeType, Object content) {
		super(content);
		contentPath = path;
		this.nodeType = nodeType;
	}

	public String getContentPath() {
		return contentPath;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setDeleted(boolean deleted) {
		bDeleted = deleted;
	}

	public boolean IsDeleted() {
		return bDeleted;
	}

}