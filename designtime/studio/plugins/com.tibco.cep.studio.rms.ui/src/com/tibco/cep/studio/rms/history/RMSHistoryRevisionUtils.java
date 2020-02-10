package com.tibco.cep.studio.rms.history;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.rms.history.model.HistoryRevisionItem;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;
import com.tibco.cep.studio.ui.util.ColorConstants;

/**
 * 
 * @author sasahoo
 *
 */
public class RMSHistoryRevisionUtils {

	public static final String REVISION_PROPERTY = "Revision";
	public static final String ACTIONS_PROPERTY = "Actions";
	public static final String AUTHOR_PROPERTY = "Author";
	public static final String DATE_PROPERTY = "Date";
	public static final String CHECKIN_COMMENTS_PROPERTY = "Checkin Comments";
	
	public static final String[] REVISION_COLS = new String[]{REVISION_PROPERTY, ACTIONS_PROPERTY, AUTHOR_PROPERTY, DATE_PROPERTY, CHECKIN_COMMENTS_PROPERTY};
	
	public static final String DETAILS_ACTION_PROPERTY = "Action";
	public static final String DETAILS_PATH_PROPERTY = "Path";
	public static final String DETAILS_APPROVAL_STATUS_PROPERTY = "ApprovalStatus";
	
	public static final String[] REVISION_DETAIL_COLS = new String[]{DETAILS_ACTION_PROPERTY, DETAILS_PATH_PROPERTY, DETAILS_APPROVAL_STATUS_PROPERTY};
	
	private static TableItem item;
	

	/**
	 * @param viewer
	 * @param readOnly
	 * @param column
	 * @return
	 */
	public static TableEditor createTableEditor(final TableViewer viewer, final int column) {

		final TableEditor tableEditor = new TableEditor(viewer.getTable()) {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.custom.TableEditor#layout()
			 */
			/* (non-Javadoc)
			 * @see org.eclipse.swt.custom.TableEditor#layout()
			 */
			@Override
			public void layout() {
				//the text area is resized when initially selected
				if (getEditor() != null && !getEditor().isDisposed() && getEditor().isFocusControl()) {
					return;
				}
				super.layout();
			}

		};
		tableEditor.horizontalAlignment = SWT.LEFT;
		tableEditor.grabHorizontal = true;
		viewer.getTable().addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				Point pt = new Point(event.x, event.y);
				item = viewer.getTable().getItem(pt);
				if (item == null) {
					return;
				}

				for (int i = 0; i < viewer.getTable().getColumnCount(); i++) {
					Rectangle rect = item.getBounds(i);
					if (rect.contains(pt)) {
						HistoryRevisionItem hItem = (HistoryRevisionItem) item.getData();
						if (i == column) {
							String desc = hItem.getCheckinComments();
							createTextArea(item, desc, i);
						}
					}
				}
			}

			private void createTextArea(TableItem textItem,	final String desc, int column) {
				item = textItem;
				int style = SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY;
				final Text text = new Text(viewer.getTable(), style);
				text.setBackground(ColorConstants.tooltipBackground);
				text.setText(desc);
				tableEditor.setEditor(text, item, column);
				text.addFocusListener(new FocusListener() {

					public void focusLost(FocusEvent e) {
					}

					public void focusGained(FocusEvent e) {
						text.setSize(text.getSize().x, 75);
					}

				});
				Listener textListener = new Listener () {
					public void handleEvent (final org.eclipse.swt.widgets.Event e) {
						try {
							switch (e.type) {
							case SWT.FocusOut:
								text.dispose ();
								break;
							case SWT.Traverse:
								switch (e.detail) {
								case SWT.TRAVERSE_RETURN:
									text.dispose ();
								case SWT.TRAVERSE_ESCAPE:
									text.dispose ();
									e.doit = false;
								}
								break;
							}
							switch (e.character) {
							case SWT.CR:
								text.dispose ();
								break;	

							}
						} catch(Exception ex) {
							ex.printStackTrace();
						}
					}
				};
				text.addListener (SWT.FocusOut, textListener);
				text.addListener (SWT.Traverse, textListener);
				text.addListener (SWT.KeyDown | SWT.KeyUp, textListener);
				text.setFocus();
			}
		});
		return tableEditor;
	}
	
	public static Image getRevisionDetailsActionImageColumn (String value) {
//		if (value.contains("MODIFY") && value.contains("ADD") && value.contains("DELETE")) {
//			return RMSPlugin.getImageDescriptor("icons/mod_add_del.png").createImage();
//		}
//		if (value.contains("MODIFY") && value.contains("ADD")) {
//			return RMSPlugin.getImageDescriptor("icons/mod_add.png").createImage();
//		}
//		if (value.contains("MODIFY") && value.contains("DELETE")) {
//			return RMSPlugin.getImageDescriptor("icons/mod_del.png").createImage();
//		}
//		if (value.contains("ADD") && value.contains("DELETE")) {
//			return RMSPlugin.getImageDescriptor("icons/add_del.png").createImage();
//		}
		if (value.contains("MODIFY")) {
			return RMSUIPlugin.getImageDescriptor("icons/mod.png").createImage();
		}
		if ( value.contains("ADD")) {
			return RMSUIPlugin.getImageDescriptor("icons/add.png").createImage();
		}
		if ( value.contains("DELETE")) {
			return RMSUIPlugin.getImageDescriptor("icons/del.png").createImage();
		}
		return null;
	}
}