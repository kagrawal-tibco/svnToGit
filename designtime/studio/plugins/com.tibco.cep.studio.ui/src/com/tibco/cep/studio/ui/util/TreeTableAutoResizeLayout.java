package com.tibco.cep.studio.ui.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

public class TreeTableAutoResizeLayout extends TreeColumnLayout implements ControlListener{
	private final Tree tree;
	private List<ColumnLayoutData> columns = new ArrayList<ColumnLayoutData>();
	private boolean autosizing = false;
	public TreeTableAutoResizeLayout(Tree tree){
		this.tree=tree;
		tree.addControlListener(this);
	}
	public TreeTableAutoResizeLayout(Tree tree, Boolean autosizing){
		this.tree = tree;
		this.autosizing=autosizing;
		tree.addControlListener(this);
	}
	public void addColumnData(Widget column,ColumnLayoutData data) {
		columns.add(data);
		super.setColumnData(column, data);
	}

	@Override
	public void controlMoved(ControlEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controlResized(ControlEvent e) {
		if (autosizing) {
			return;
		}
		autosizing = true;
		try {
			autoSizeColumns();
		} finally {
			autosizing = false;
		}
		
	}
	
	private void autoSizeColumns() {
		int width = tree.getClientArea().width;
		if (width <= 1) {
			return;
		}

		TreeColumn[] tableColumns = tree.getColumns();
		int size = Math.min(columns.size(), tableColumns.length);
		int[] widths = new int[size];
		int fixedWidth = 0;
		int numberOfWeightColumns = 0;
		int totalWeight = 0;

		GC gc = new GC(tree);
		// First do some calculations
		for (int i = 0; i < size; i++) {
			ColumnLayoutData col= columns.get(i);
			if (col instanceof ColumnPixelData) {
				int fixPixels = ((ColumnPixelData) col).width;
				if (fixPixels == 0) {
					Point measurement = gc.textExtent(tree.getColumn(i).getText());
					int maxWidth = measurement.x;
					for (TreeItem tableItem : tree.getItems()) {
						measurement = gc.textExtent(tableItem.getText(i));
						maxWidth = Math.max(maxWidth, measurement.x);
					}
					widths[i] = maxWidth + 15;
					fixedWidth += maxWidth + 15;
				} else {
					widths[i] = fixPixels;
					fixedWidth += fixPixels;
				}
			} else if (col instanceof ColumnWeightData) {
				ColumnWeightData cw = (ColumnWeightData) col;
				numberOfWeightColumns++;
				int weight = cw.weight;
				totalWeight += weight;
			} else {
				throw new IllegalStateException("Unknown column layout data");
			}
		}
		gc.dispose();

		// Do we have columns that have a weight?
		if (numberOfWeightColumns > 0) {
			// Now, distribute the rest
			// to the columns with weight.
			int rest = width - fixedWidth;
			int totalDistributed = 0;
			for (int i = 0; i < size; i++) {
				ColumnLayoutData col = (ColumnLayoutData) columns.get(i);
				if (col instanceof ColumnWeightData) {
					ColumnWeightData cw = (ColumnWeightData) col;
					int weight = cw.weight;
					int pixels = totalWeight == 0 ? 0 : weight * rest / totalWeight;
					totalDistributed += pixels;
					widths[i] = pixels;
				}
			}

			// Distribute any remaining pixels
			// to columns with weight.
			int diff = rest - totalDistributed;
			for (int i = 0; diff > 0; i++) {
				if (i == size) {
					i = 0;
				}
				ColumnLayoutData col = (ColumnLayoutData) columns.get(i);
				if (col instanceof ColumnWeightData) {
					++widths[i];
					--diff;
				}
			}
		}

		for (int i = 0; i < size; i++) {
			if (tableColumns[i].getWidth() != widths[i]) {
				tableColumns[i].setWidth(widths[i]);
			}
		}
	}

}
