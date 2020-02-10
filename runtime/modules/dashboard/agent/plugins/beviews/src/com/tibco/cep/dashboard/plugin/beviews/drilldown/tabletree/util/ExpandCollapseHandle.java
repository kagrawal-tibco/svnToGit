package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util;

/**
 * @author rajesh
 *
 *         To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class ExpandCollapseHandle implements HTMLRenderer {

	private boolean expanded;
	private final String EX_COL_IMG_ID_PREFIX = getIDPrefix() + "ExCol_Image";
	private final String EX_COL_LINK_ID_PREFIX = getIDPrefix() + "ExCol_Link";

	private String onclickHanlder = "";
	protected String subPath;

	private Object rowParam;
	private boolean last;
	private boolean first;
	private String rowExpandedLink = null;
	private ExpandCollapseHandleConfigurator configurator;
	protected int rowIndex;

	/**
     *
     */
	public ExpandCollapseHandle(ExpandCollapseHandleConfigurator configurator) {

		this.configurator = configurator;
	}

	public StringBuffer getHTML() {
		StringBuffer buffer = new StringBuffer();
		outExpandCollapseHandler(buffer, first, last);
		return buffer;
	}

	private void outExpandCollapseHandler(StringBuffer buffer, boolean isFirst, boolean isLast) {

		String[] imgExpandCollapse = new String[4];

		configurator.loadExpandCollapseImages(imgExpandCollapse, isFirst, isLast);
		outTreeLineTable(buffer, isFirst, isLast, imgExpandCollapse);
		return;
	}

	private void outTreeLineTable(StringBuffer buffer, boolean isFirst, boolean isLast, String[] imgExpandCollapse) {
		HTMLTag tableTag = new HTMLTag("TABLE");
		tableTag.setComment("Table for extended Tree Line and ExCollpase Imagecell");
		// tableTag.setStyleClass("trtb_celltreeline");
		tableTag.addAttribute("border", "0");
		tableTag.addAttribute("cellpadding", "0");
		tableTag.addAttribute("cellspacing", "0");
		tableTag.addAttribute("width", "100%");
		tableTag.addAttribute("height", "100%");

		HTMLTag trTag1 = tableTag.addTag("TR");
		HTMLTag tdTag1 = trTag1.addTag("TD");

		if (getRowExpandedLink() != null) {
			HTMLTag linkTag = getLinkTag();
			linkTag.addTag(getLinkImageTag(isFirst, isLast, imgExpandCollapse));
			tdTag1.addTag(linkTag);
		} else {
			tdTag1.addTag(getLinkImageTag(isFirst, isLast, imgExpandCollapse));
		}
		HTMLTag trTag2 = tableTag.addTag("TR");
		trTag2.setComment("row for tree lines ");
		HTMLTag tdTag2 = trTag2.addTag("TD");
		tdTag2.addAttribute("width", "100%");
		tdTag2.addAttribute("height", "100%");
		if (isLast == false) {
			tdTag2.setStyleClass("trtb_celltreeline");
		}
		buffer.append(tableTag.toString());

	}

	private HTMLTag getLinkImageTag(boolean isFirst, boolean isLast, String[] imgExpandCollapse) {
		HTMLTag tag = new HTMLTag("img");
		tag.setComment("Expand Collapse Image");
		if (expanded) {
			tag.addAttribute("src", imgExpandCollapse[ExpandCollapseHandleConfigurator.INDEX_OPEN_IMAGE]);
		} else {
			tag.addAttribute("src", imgExpandCollapse[ExpandCollapseHandleConfigurator.INDEX_CLOSE_IMAGE]);
		}
		tag.addAttribute("id", computeId(EX_COL_IMG_ID_PREFIX));
		tag.addAttribute("c_OpenImage", imgExpandCollapse[ExpandCollapseHandleConfigurator.INDEX_OPEN_IMAGE]);
		tag.addAttribute("c_CloseImage", imgExpandCollapse[ExpandCollapseHandleConfigurator.INDEX_CLOSE_IMAGE]);
		tag.addAttribute("c_NoChildImage", imgExpandCollapse[ExpandCollapseHandleConfigurator.INDEX_NOCHILD_IMAGE]);
		tag.addAttribute("c_ProgressImage", imgExpandCollapse[ExpandCollapseHandleConfigurator.INDEX_PROGRESS_IMAGE]);
		tag.addAttribute("c_first", "" + isFirst);
		tag.addAttribute("c_last", "" + isLast);
		tag.addAttribute("border", "0");
		tag.addAttribute("width", "18");
		tag.addAttribute("height", "22");
		return tag;
	}

	private HTMLTag getLinkTag() {
		HTMLTag tag = new HTMLTag("a");
		tag.setComment("Expand Collapse Anchor");
		tag.addAttribute("href", "javascript:postRequest('" + getRowExpandedLink() + getRowIndexParams() + formatRowParam() + "');");
		tag.addAttribute("id", computeId(EX_COL_LINK_ID_PREFIX));
		tag.addAttribute("onclick", onclickHanlder);
		// tag.addAttribute("target", getFrameId());
		return tag;
	}

	/**
	 * @return
	 */
	private String formatRowParam() {
		if (rowParam == null) {
			return "";
		}
		return "&" + rowParam.toString();
	}

	/**
	 * @return
	 */
	private String getRowExpandedLink() {
		if (rowExpandedLink == null) {
			return configurator.getRowExpandedLink();
		}
		return rowExpandedLink;
	}

	public String getLink() {
		return getRowExpandedLink() + getRowIndexParams() + formatRowParam();
	}

	/**
	 * @param string
	 */
	public void setRowIndex(int index) {
		rowIndex = index;
	}

	/**
	 * @return
	 */
	public ExpandCollapseHandleConfigurator getConfigurator() {
		return configurator;
	}

	/**
	 * @return
	 */
	public boolean isFirst() {
		return first;
	}

	/**
	 * @return
	 */
	public boolean isLast() {
		return last;
	}

	/**
	 * @param configurator
	 */
	public void setConfigurator(ExpandCollapseHandleConfigurator configurator) {
		this.configurator = configurator;
	}

	/**
	 * @param b
	 */
	public void setFirst(boolean b) {
		first = b;
	}

	/**
	 * @param b
	 */
	public void setLast(boolean b) {
		last = b;
	}

	/**
	 * @param string
	 */
	public void setRowExpandedLink(String string) {
		rowExpandedLink = string;
	}

	/**
	 * @param rowParam
	 */
	public void setRowParam(Object rowParam) {
		this.rowParam = rowParam;
	}

	/**
	 * @param string
	 */
	public void setSubPath(String string) {
		subPath = string;
	}

	/**
	 * @param string
	 */
	public void setOnClick(String string) {
		onclickHanlder = string;
	}

	/**
	 * @return
	 */
	public abstract String getIDPrefix();

	/**
	 * @return
	 */
	public abstract String getFrameId();

	/**
	 * @param prefix
	 * @return
	 */
	public abstract String computeId(String prefix);

	/**
	 * @return
	 */
	public abstract String getRowIndexParams();

	/**
	 * @param expanded
	 */
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

}
