package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLRenderer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.TableTreeUtils;



/**
 * @author rajesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TableColumn implements HTMLRenderer{

    public static final String DATATYPE_STRING = "string";
    public static final String DATATYPE_NUMBER = "number";
    public static final String DATATYPE_DATE = "date";
    
    public static final String ID_PREFIX = "tableTree_COLUMN";
    
    private String normalStyle ="columnStyle";
    private String ascendingStyle ="ascendingColumnStyle";
    private String descendingStyle ="descendingColumnStyle";
    private boolean isSortable;
    private String textAlign = "left";
    private String columnDataType =DATATYPE_STRING; 
    private int index;
    private TableTree tableTree;
	private TableCellRenderer columnCellRenderer;
    public TableColumn(TableTree tableTree)
    {
        this.tableTree = tableTree;
    }   
    
    public TableColumn(TableTree tableTree,int index)
    {
        this(tableTree);
        this.index = index;
    }   
    public TableColumn(TableTree tableTree, int index,String textAlign)
    {
        this(tableTree, index);
        this.textAlign = textAlign;
    }
    
    public TableColumn(TableTree tableTree, int index, String textAlign, String normalStyle)
    {
        this(tableTree, index, textAlign);
        this.normalStyle = normalStyle;
    }

    
    public StringBuffer getHTML() {
        StringBuffer buffer = new StringBuffer();
        render(buffer);
        return buffer;
    }

    /**
     * @param buffer
     */
    private void render(StringBuffer buffer) {
        buffer.append("<COL");
        buffer.append(" id=\"" + getColumnId() + "\"" );
        TableTreeUtils.outAttribute(buffer, "ALIGN", textAlign);
        TableTreeUtils.outAttribute(buffer, "CLASS", getStyle());
        TableTreeUtils.outAttribute(buffer, "c_normalStyle", getNormalStyle());
        TableTreeUtils.outAttribute(buffer,"style","visibility: visible;");
        TableTreeUtils.outAttribute(buffer, CustomAttributeConstants.SORT_ASCENDING_STYLE, getAscendingStyle());
        TableTreeUtils.outAttribute(buffer, CustomAttributeConstants.SORT_DESCENDING_STYLE, getDescendingStyle());
        TableTreeUtils.outAttribute(buffer, CustomAttributeConstants.COLUMN_DATA_TYPE, getColumnDataType());
        buffer.append("></COL>");
    }

    public String getAscendingStyle() {
        return ascendingStyle;
    }

    public void setAscendingStyle(String ascendingStyle) {
        this.ascendingStyle = ascendingStyle;
    }

    public String getDescendingStyle() {
        return descendingStyle;
    }

    public void setDescendingStyle(String descendingStyle) {
        this.descendingStyle = descendingStyle;
    }

    public boolean isSortable() {
        return isSortable;
    }

    public void setSortable(boolean isSortable) {
        this.isSortable = isSortable;
    }
    /**
     * @return
     */
    public String getNormalStyle() {
        return normalStyle;
    }

    /**
     * @param string
     */
    public void setNormalStyle(String string) {
        normalStyle = string;
    }
    
    public String getStyle()
    {
        return normalStyle;
    }
    public String getColumnDataType() {
        return columnDataType;
    }

    public void setColumnDataType(String columnDataType) {
        this.columnDataType = columnDataType;
    }

    public String getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(String textAlign) {
        this.textAlign = textAlign;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getColumnId(){
        return tableTree.computeId(ID_PREFIX, getIndex());
    }

    /**
     * @param renderer
     */
    public void setCellRenderer(TableCellRenderer renderer)
    {
    	this.columnCellRenderer = renderer;
    }

	/**
	 * @return
	 */
	public TableCellRenderer getCellRenderer() {
		return columnCellRenderer;
	}
}
