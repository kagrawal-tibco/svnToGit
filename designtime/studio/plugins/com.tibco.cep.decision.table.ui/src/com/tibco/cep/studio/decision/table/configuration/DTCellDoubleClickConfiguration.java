/**
 * 
 */
package com.tibco.cep.studio.decision.table.configuration;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.command.VisualRefreshCommand;
import org.eclipse.nebula.widgets.nattable.config.AbstractUiBindingConfiguration;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.ui.NatEventData;
import org.eclipse.nebula.widgets.nattable.ui.action.IMouseAction;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.nebula.widgets.nattable.ui.matcher.IMouseEventMatcher;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.studio.decision.table.calendar.DateTimeCalendar;
import com.tibco.cep.studio.decision.table.editor.DTBodyLayerStack;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.providers.SpanningGlazedListsDataProvider;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.decision.table.utils.DecisionTableUtil;

/**
 * This class is used to configure the double click event on cell.
 * 
 * @author dijadhav
 *
 */
public class DTCellDoubleClickConfiguration extends
		AbstractUiBindingConfiguration {
	private TableRuleSet ruleSet;
	private IDecisionTableEditor editor;
	private DTBodyLayerStack<TableRule> bodyLayer;

	public DTCellDoubleClickConfiguration(TableRuleSet ruleSet,
			IDecisionTableEditor editor, DTBodyLayerStack<TableRule> bodyLayer) {
		this.ruleSet = ruleSet;
		this.bodyLayer = bodyLayer;
		this.editor = editor;
	}

	/**
	 * Configure the UI bindings for the mouse click
	 */
	public void configureUiBindings(UiBindingRegistry uiBindingRegistry) {

		MouseEventMatcher matcher = new MouseEventMatcher(GridRegion.BODY,
				MouseEventMatcher.LEFT_BUTTON);

		// Fire the action when the matcher matches the mouse double event
		uiBindingRegistry.registerDoubleClickBinding(matcher,
				new MouseDoubleClickAction());
	}

	/**
	 * Action to execute when the {@link IMouseEventMatcher} matches a mouse
	 * double click event
	 */
	class MouseDoubleClickAction implements IMouseAction {

		public void run(final NatTable natTable, MouseEvent event) {
			NatEventData eventData = NatEventData
					.createInstanceFromEvent(event);
			final int rowIndex = natTable.getRowIndexByPosition(eventData
					.getRowPosition());
			final int columnIndex = natTable.getColumnIndexByPosition(eventData
					.getColumnPosition());
			Columns columns = ruleSet.getColumns();

			if (-1 != columnIndex && -1 != rowIndex) {
				final Column column = columns.getColumn().get(columnIndex);
				/**
				 * Open the calendar dialog if column is of type datetime.
				 */
				if (DecisionTableUtil.checkDateType(column.getPropertyType())) {
					DateTimeCalendar calendar = new DatePickerDialog(
							natTable.getShell(), rowIndex, columnIndex,
							natTable);
					Object val = ((DTBodyLayerStack<TableRule>)((GridLayer)natTable.getLayer()).getBodyLayer()).getDataValueByPosition(columnIndex, rowIndex);
					if (val != null && val.toString().length() > 0) {
						calendar.setCurrentDateTime(val.toString());
					}
					calendar.open();
				} else {
					Object tableRule = editor.getModelDataByPosition(columnIndex, rowIndex, natTable);
					if (tableRule == null) {
						DTBodyLayerStack<TableRule> targetLayer = (DTBodyLayerStack<TableRule>) ((GridLayer)natTable.getLayer()).getBodyLayer();
						SpanningGlazedListsDataProvider<TableRule> dataProvider = targetLayer.getBodyDataProvider();
						dataProvider.setDataValue(columnIndex, rowIndex, "");
						tableRule = editor.getModelDataByPosition(columnIndex, rowIndex, natTable);
					}
					if (tableRule != null && tableRule instanceof TableRuleVariable) {
	                    TableRuleVariable ruleVariable = (TableRuleVariable) tableRule;
	                    String currExpr = ruleVariable.getExpr();
	                	Point openLoc = natTable.getShell().getLocation();
	                    String newExpr = editor.getDecisionTableDesignViewer().openExpressionEditor(ruleVariable, natTable, new Point(openLoc.x+event.x, openLoc.y+event.y));
	                    if (newExpr != null && !newExpr.equals(currExpr)) {
	        				bodyLayer.getBodyDataProvider().setDataValue(columnIndex,
	        						rowIndex, newExpr);
	        				natTable.doCommand(new VisualRefreshCommand());
	                    }
	                }
				}
			}
		}

		/**
		 * Open the dialog
		 * 
		 * @author dijadhav
		 *
		 */
		private class DatePickerDialog extends DateTimeCalendar {
			private final int rowIndex;
			private final int columnIndex;
			private final NatTable natTable;

			private DatePickerDialog(Shell parentShell, int rowIndex,
					int columnIndex, NatTable natTable) {
				super(parentShell, editor.getDecisionTableDesignViewer().getDateFormat());
				this.rowIndex = rowIndex;
				this.columnIndex = columnIndex;
				this.natTable = natTable;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org
			 * .eclipse.swt.widgets.Composite)
			 */
			protected void createButtonsForButtonBar(Composite parent) {
				createButtonLayout(parent);

			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org
			 * .eclipse.swt.widgets.Composite)
			 */
			protected void createButtonsForCalendarBetweenOrRangeButtonBar(
					Composite parent) {
				createButtonLayout(parent);
			}

			/**
			 * Create the button layout in calendar dialog.
			 * 
			 * @param parent
			 */
			private void createButtonLayout(Composite parent) {
				GridLayout glayout = new GridLayout(2, false);
				parent.setLayout(glayout);
				parent.setBackground(COLOR_WHITE);

				Button ok = new Button(parent, SWT.FLAT);
				GridData gd = new GridData(SWT.END, SWT.CENTER, false, false);
				gd.widthHint = 100;
				gd.heightHint = 25;
				ok.setText("Ok"); //$NON-NLS-1$
				ok.setLayoutData(gd);

				Button cancel = new Button(parent, SWT.FLAT);
				gd = new GridData(SWT.END, SWT.CENTER, false, false);
				gd.widthHint = 100;
				gd.heightHint = 25;
				cancel.setText("Cancel"); //$NON-NLS-1$
				cancel.setLayoutData(gd);

				cancel.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						close();
					}
				});
				ok.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						okButtonActionListener();
					}

				});
			}

			/**
			 * Action listener of ok button.
			 */
			private void okButtonActionListener() {
				Calendar gcalendar = new GregorianCalendar(startDateCalWidget.getYear(),
						startDateCalWidget.getMonth(), startDateCalWidget.getDay(),
						startDateTimeWidget.getHours(), startDateTimeWidget.getMinutes(), startDateTimeWidget.getSeconds());
				try {
					if (isBefore()) {
						setBeforeDateTime(sdf.format(gcalendar.getTime()));
					} else if (isAfter()) {
						setAfterDateTime(sdf.format(gcalendar.getTime()));
					} else if (isBetweenOrRange()) {
						Calendar startGCalendar = null;
						Calendar endGCalendar = null;
						startGCalendar = new GregorianCalendar(
								startDateCalWidget.getYear(), startDateCalWidget.getMonth(),
								startDateCalWidget.getDay(), startDateTimeWidget.getHours(),
								startDateTimeWidget.getMinutes(), startDateTimeWidget.getSeconds());

						endGCalendar = new GregorianCalendar(
								endDateCalendarWidget.getYear(),
								endDateCalendarWidget.getMonth(),
								endDateCalendarWidget.getDay(),
								endDateTimeWidget.getHours(),
								endDateTimeWidget.getMinutes(),
								endDateTimeWidget.getSeconds());
						
						setRangeDateTime(
								sdf.format(startGCalendar.getTime()),
								sdf.format(endGCalendar.getTime()));

					} else {
						setFormattedDateTimeExpr(sdf.format(gcalendar.getTime()));
					}
					setWidgetDateTime(getFormattedDateTimeExpr());
					setDataValue();
				} catch (Exception ex) {
					DecisionTableUIPlugin.log(ex);
				}
				close();
			}

			/**
			 * Set the value of column.
			 */
			private void setDataValue() {
				bodyLayer.getBodyDataProvider().setDataValue(columnIndex,
						rowIndex, getFormattedDateTimeExpr());
				natTable.repaintCell(columnIndex, rowIndex);
				editor.modified();
			}
		}
	}
}
