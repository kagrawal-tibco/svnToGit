package com.tibco.cep.studio.decision.table.editor;

import java.text.SimpleDateFormat;

import org.eclipse.jface.window.Window;
import org.eclipse.nebula.widgets.nattable.edit.gui.AbstractDialogCellEditor;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer.MoveDirectionEnum;
import org.eclipse.swt.widgets.FileDialog;

import com.tibco.cep.studio.decision.table.calendar.DateTimeCalendar;

public class DatePickerCellEditor extends AbstractDialogCellEditor {

    /**
     * The selection result of the {@link FileDialog}. Needed to update the data model
     * after closing the dialog.
     */
    private String selectedDateTime;
    /**
     * Flag to determine whether the dialog was closed or if it is still open.
     */
    private boolean closed = false;
    
    private final SimpleDateFormat sdf = new SimpleDateFormat(com.tibco.cep.studio.core.utils.ModelUtils.DATE_TIME_PATTERN);
   
    /* (non-Javadoc)
     * @see org.eclipse.nebula.widgets.nattable.edit.editor.AbstractDialogCellEditor#open()
     */
    @Override
    public int open() {
            getDialogInstance().open();
            selectedDateTime = getDialogInstance().getWidgetDateTime();
            if (selectedDateTime == null) {
                    closed = true;
                    return Window.CANCEL;
            }
            else {
                    commit(MoveDirectionEnum.NONE);
                    closed = true;
                    return Window.OK;
            }
    }

    /* (non-Javadoc)
     * @see org.eclipse.nebula.widgets.nattable.edit.editor.AbstractDialogCellEditor#createDialogInstance()
     */
    @Override
    public DateTimeCalendar createDialogInstance() {
            closed = false;
//            return new FileDialog(this.parent.getShell(), SWT.OPEN);
            return new DateTimeCalendar(this.parent.getShell(), sdf) {
			};
    }

    /* (non-Javadoc)
     * @see org.eclipse.nebula.widgets.nattable.edit.editor.AbstractDialogCellEditor#getDialogInstance()
     */
    @Override
    public DateTimeCalendar getDialogInstance() {
            return (DateTimeCalendar) this.dialog;
    }

    /* (non-Javadoc)
     * @see org.eclipse.nebula.widgets.nattable.edit.editor.AbstractDialogCellEditor#getEditorValue()
     */
    @Override
    public Object getEditorValue() {
            return this.selectedDateTime;
    }

    /* (non-Javadoc)
     * @see org.eclipse.nebula.widgets.nattable.edit.editor.AbstractDialogCellEditor#setEditorValue(java.lang.Object)
     */
    @Override
    public void setEditorValue(Object value) {
            //do nothing ... usually it should set the selection
    }

    /* (non-Javadoc)
     * @see org.eclipse.nebula.widgets.nattable.edit.editor.AbstractDialogCellEditor#close()
     */
    @Override
    public void close() {
            //as the FileDialog does not support a programmatical way of closing, this method is forced to do nothing
    	getDialogInstance().close();
    }

    /* (non-Javadoc)
     * @see org.eclipse.nebula.widgets.nattable.edit.editor.AbstractDialogCellEditor#isClosed()
     */
    @Override
    public boolean isClosed() {
            return this.closed;
    }

}