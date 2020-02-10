package com.tibco.cep.studio.dashboard.ui.viewers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnWeightData;

public class TableColumnInfo {

	public static final String DEFAULT_SELECT = "Default";
	public static final String SELECT = "Select";
	public static final String NAME = "Name";
	public static final String FOLDER = "Folder";
	public static final String DISPLAY_NAME = "Display Name";
	public static final String DESCRIPTION = "Description";
	public static final String PARENT_NAME = "Parent Element";

	public static TableColumnInfo[] get() {
		TableColumnInfo[] columnsInfo = new TableColumnInfo[2];
		columnsInfo[0] = new TableColumnInfo(SELECT, SELECT, true, true, false, new ColumnPixelData(50, true));
		columnsInfo[1] = new TableColumnInfo(NAME, NAME, true, false, true, new ColumnWeightData(1, 200, true));
		return columnsInfo;
	}

	public static TableColumnInfo[] get(boolean withParent, boolean withDefault) {
		List<TableColumnInfo> nameList = new ArrayList<TableColumnInfo>();

		nameList.add(new TableColumnInfo(SELECT, SELECT, true, true, false, new ColumnPixelData(50, true)));

		if (withDefault) {
			nameList.add(new TableColumnInfo(DEFAULT_SELECT, DEFAULT_SELECT, true, false, true, new ColumnPixelData(50, true)));
		}

		nameList.add(new TableColumnInfo(NAME, NAME, true, false, true, new ColumnWeightData(1, 200, true)));
		nameList.add(new TableColumnInfo(FOLDER, FOLDER, true, false, true, new ColumnWeightData(1, 150, true)));
		nameList.add(new TableColumnInfo(DISPLAY_NAME, DISPLAY_NAME, true, false, true, new ColumnWeightData(1, 200, true)));
		nameList.add(new TableColumnInfo(DESCRIPTION, DESCRIPTION, true, false, true, new ColumnWeightData(1, 300, true)));

		if (withParent) {
			nameList.add(new TableColumnInfo(PARENT_NAME, PARENT_NAME, true, false, true, new ColumnWeightData(1, 200, true)));
		}
		return nameList.toArray(new TableColumnInfo[0]);
	}

	private String id;
	private String title;
	private boolean imageRequired;
	private boolean multiSelect;
	private boolean singleSelect;

	private ColumnLayoutData layoutData;

	public TableColumnInfo(String id, String title, boolean imageRequired, boolean multiSelect, boolean singleSelect, ColumnLayoutData layoutData) {
		this.id = id;
		this.title = title;
		this.imageRequired = imageRequired;
		this.multiSelect = multiSelect;
		this.singleSelect = singleSelect;
		this.layoutData = layoutData;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public boolean isImageRequired() {
		return imageRequired;
	}

	public boolean isMultiSelect() {
		return multiSelect;
	}

	public boolean isSingleSelect() {
		return singleSelect;
	}

	public ColumnLayoutData getLayoutData() {
		return layoutData;
	}

}