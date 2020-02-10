package com.tibco.cep.studio.rms.history;

import java.text.Collator;

import org.eclipse.jface.util.Util;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.tibco.cep.studio.rms.history.model.HistoryRevisionItem;

public class RevisionTableSorter extends ViewerSorter {
	
	private int propertyIndex;
	
	// private static final int ASCENDING = 0;
	private static final int DESCENDING = 1;

	private int direction = DESCENDING;

	public RevisionTableSorter() {
		this.propertyIndex = 0;
		direction = DESCENDING;
	}

	public void setColumn(int column) {
		if (column == this.propertyIndex) {
			// Same column as last sort; toggle the direction
			direction = 1 - direction;
		} else {
			// New column; do an ascending sort
			this.propertyIndex = column;
			direction = DESCENDING;
		}
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		HistoryRevisionItem hRevItem1 = (HistoryRevisionItem) e1;
		HistoryRevisionItem hRevItem2 = (HistoryRevisionItem) e2;
		int cVal = 0;
		switch (propertyIndex) {
		case 0:
			cVal = Util.compare(Long.parseLong(hRevItem1.getRevisionId()),Long.parseLong(hRevItem2.getRevisionId()));
			break;
		case 1:
			cVal =  Collator.getInstance().compare(hRevItem1.getCheckinActions(),hRevItem2.getCheckinActions());
			break;
		case 2:
			cVal =  Collator.getInstance().compare(hRevItem1.getAuthor(),hRevItem2.getAuthor());
			break;
		case 3:
			cVal =  Collator.getInstance().compare(hRevItem1.getCheckinTime(),hRevItem2.getCheckinTime());
			break;
		case 4:
			cVal =  Collator.getInstance().compare(hRevItem1.getCheckinComments(),hRevItem2.getCheckinComments());
			break;
		default:
			cVal = 0;
		}
		// If descending order, flip the direction
		if (direction == DESCENDING) {
			cVal = -cVal;
		}
		return cVal;
	}
}
		
