package com.tibco.cep.studio.decision.table.editor;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.edit.editor.AbstractCellEditor;
import org.eclipse.nebula.widgets.nattable.painter.cell.CheckBoxPainter;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer.MoveDirectionEnum;
import org.eclipse.nebula.widgets.nattable.widget.EditModeEnum;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class MixedStateCheckBoxCellEditor extends AbstractCellEditor {

    /**
     * The current state of the checkbox stating the corresponding value.
     */
    private MixedStateBoolean state;

    /**
     * The editor control which is a Canvas that paints the corresponding
     * checkbox images. To adjust the look & feel for checkbox editors you need
     * to look at {@link CheckBoxPainter}
     */
    private Canvas canvas;

    /**
     * As soon as the editor is activated, flip the current data value and
     * commit it. The repaint will pick up the new value and flip the image.
     * This is only done if the mouse click is done within the rectangle of the
     * painted checkbox image.
     */
    @Override
    protected Control activateCell(Composite parent, Object originalCanonicalValue) {
        // if this editor was activated by clicking a letter or digit key, do
        // nothing
        if (originalCanonicalValue instanceof Character) {
            return null;
        }

        setCanonicalValue(originalCanonicalValue);

        moveStates();

        this.canvas = createEditorControl(parent);

        commit(MoveDirectionEnum.NONE, false);

        if (this.editMode == EditModeEnum.INLINE) {
            // Close editor so it will react to subsequent clicks on the cell
            if (this.canvas != null && !this.canvas.isDisposed()) {
                close();
            }
        }

        return this.canvas;
    }

	private void moveStates() {
		switch (this.state) {
		case TRUE:
			this.state = MixedStateBoolean.FALSE;
			break;

		case FALSE:
			this.state = MixedStateBoolean.NOT_SET;
			break;
			
		case NOT_SET:
			this.state = MixedStateBoolean.TRUE;
			break;
			
		default:
			break;
		}
	}

    @Override
    public MixedStateBoolean getEditorValue() {
        return this.state;
    }

    /**
     * Sets the given value to editor control. As this method is called by
     * {@link AbstractCellEditor#setCanonicalValue(Object)} the given value
     * should be already a converted MixedStateBoolean value. The only other values
     * accepted in here are <code>null</code> which is interpreted as
     * <code>NOT_SET</code> and Strings than can be converted to MixedStateBoolean directly.
     * Every other object will result in setting the editor value to
     * <code>FALSE</code>.
     *
     * @param value
     *            The display value to set to the wrapped editor control.
     */
    @Override
    public void setEditorValue(Object value) {
        if (value == null) {
            this.state = MixedStateBoolean.NOT_SET;
        } else {
            if (value instanceof MixedStateBoolean) {
                this.state = (MixedStateBoolean) value;
            } else if (value instanceof String) {
            	if (Boolean.valueOf((String) value).booleanValue()) {
            		this.state = MixedStateBoolean.TRUE;
            	} else {
            		this.state = MixedStateBoolean.FALSE;
            	}
            } else {
                this.state = MixedStateBoolean.FALSE;
            }
        }
    }

    @Override
    public Canvas getEditorControl() {
        return this.canvas;
    }

    @Override
    public Canvas createEditorControl(Composite parent) {
        final Canvas canvas = new Canvas(parent, SWT.NONE);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseUp(MouseEvent e) {
            	moveStates();
//                MixedStateCheckBoxCellEditor.this.state = !MixedStateCheckBoxCellEditor.this.state;
                canvas.redraw();
            }
        });

        return canvas;
    }

    @Override
    public boolean openMultiEditDialog() {
        // as it doesn't make sense to open a subdialog for checkbox multi
        // editing, this is not supported
        return false;
    }

    @Override
    public boolean activateAtAnyPosition() {
        // as the checkbox should only change its value if the icon that
        // represents the checkbox is clicked, this method needs to return
        // false so the IMouseEventMatcher can react on that.
        // Note that on return false here creates the need to add a special
        // matcher for this editor
        // to be activated.
        return false;
    }

    @Override
    public boolean activateOnTraversal(IConfigRegistry configRegistry, List<String> configLabels) {
        // the checkbox editor is immediately changing the value and closing
        // the again on activation. on tab traversal it is not intended that the
        // value changes therefore this editor is not activated on traversal
        return false;
    }
}
