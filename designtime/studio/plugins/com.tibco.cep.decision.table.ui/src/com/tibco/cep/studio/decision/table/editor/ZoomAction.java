package com.tibco.cep.studio.decision.table.editor;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.command.VisualRefreshCommand;
import org.eclipse.nebula.widgets.nattable.ui.action.IKeyAction;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.FontData;

import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;

public class ZoomAction implements IKeyAction {

	private boolean zoomIn;
	private DecisionTableDesignViewer dtDesignViewer;

	public ZoomAction(boolean zoomIn, DecisionTableDesignViewer decisionTableDesignViewer) {
		this.zoomIn = zoomIn;
		this.dtDesignViewer = decisionTableDesignViewer;
	}

	@Override
	public void run(NatTable natTable, KeyEvent event) {
		IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
		prefStore.removePropertyChangeListener(dtDesignViewer);
		String conditionFont = prefStore.getString(PreferenceConstants.COND_DATA_FONT);
		String actionFont = prefStore.getString(PreferenceConstants.ACTION_DATA_FONT);
		String headerFont = prefStore.getString(PreferenceConstants.COLUMN_HEADER_FONT);
		FontData condFontData = new FontData(conditionFont);
		FontData actionFontData = new FontData(actionFont);
		FontData headerFontData = new FontData(headerFont);
		if (zoomIn) {
			condFontData.height += 2;
			actionFontData.height += 2;
			headerFontData.height += 2;
		} else {
			condFontData.height -= 2;
			actionFontData.height -= 2;
			headerFontData.height -= 2;
		}
		prefStore.setValue(PreferenceConstants.COND_DATA_FONT, condFontData.toString());
		prefStore.setValue(PreferenceConstants.ACTION_DATA_FONT, actionFontData.toString());
		prefStore.setValue(PreferenceConstants.COLUMN_HEADER_FONT, headerFontData.toString());
		prefStore.addPropertyChangeListener(dtDesignViewer);
		natTable.configure();
		dtDesignViewer.resizeRows(natTable);
		natTable.doCommand(new VisualRefreshCommand());
	}
	
}