/**
 * 
 */
package com.tibco.cep.webstudio.client.model;

/**
 * Model to represent portlet properties
 * 
 * @author Vikram Patil
 */
public class DashboardPortlet {

	private String portletId;
	private int column;
	private int row;
	private int height;
	private int colSpan;

	public DashboardPortlet() {
	}
	
	public DashboardPortlet(String portletId) {
		super();
		this.portletId = portletId;
	}
	
	public DashboardPortlet(String portletId, int column, int row, int height, int colSpan) {
		super();
		this.portletId = portletId;
		this.column = column;
		this.row = row;
		this.height = height;
		this.colSpan = colSpan;
	}

	public String getPortletId() {
		return portletId;
	}

	public void setPortletId(String id) {
		this.portletId = id;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getColSpan() {
		return colSpan;
	}

	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		
		DashboardPortlet portlet = (DashboardPortlet) obj;
		return portlet.getPortletId().equals(this.getPortletId());
	}

}
