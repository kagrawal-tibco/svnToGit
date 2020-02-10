package com.tibco.cep.studio.ui.editors.domain;

import static com.tibco.cep.studio.ui.editors.domain.DomainConstants.DETAILS_PAGE;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
@SuppressWarnings("serial")
public class DomainEntryAction extends AbstractAction {

	private String actionType;
	private DomainFormViewer domainFormViewer;
	
	/**
	 * 
	 * @param actionType
	 * @param domainFormViewer
	 */
	public DomainEntryAction(String actionType,DomainFormViewer domainFormViewer) {
		this.actionType = actionType;
		this.domainFormViewer =domainFormViewer;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		Display.getDefault().asyncExec(new Runnable(){
			public void run() {
				try {
					final String domainDataType = domainFormViewer.getDomainDataTypesCombo().getText();
					final TableViewer viewer = domainFormViewer.getViewer();
					final ScrolledPageBook viewerScrollPageBook= domainFormViewer.getViewerScrollPageBook();
					DomainViewerItem item = null;
					if (actionType.equalsIgnoreCase(Messages.getString("Add"))) {
						if (domainFormViewer.getTableEditor()!=null &&
								domainFormViewer.getTableEditor().getEditor()!=null
								&&  !domainFormViewer.getTableEditor().getEditor().isDisposed()) {
							domainFormViewer.getTableEditor().getEditor().dispose();
						}

						DOMAIN_DATA_TYPES domain_data_types = DOMAIN_DATA_TYPES.get(domainDataType);
						switch(domain_data_types) {
						case STRING:
							item = getItem("", "");
							break;
						case INTEGER:
							item = getItem("", "");
							break;
						case DOUBLE:
							item = getItem("", "");
							break;
						case LONG:
							item = getItem("", "");
							break;
						case BOOLEAN:
							TableItem[] items = viewer.getTable().getItems();
							if (viewer.getTable().getItemCount() == 0) {
								item = getItem("", "true");
							}
							if (viewer.getTable().getItemCount() == 1) {
								if (items[0].getText(1).equalsIgnoreCase("true"))
									item = getItem("", "false");
								if (items[0].getText(1).equalsIgnoreCase("false"))
									item = getItem("", "true");
							}
							break;
						case DATE_TIME:
							item = getItem("", DomainUtils.getFormattedDateTime());
							break;
						}
						if (item != null) {
							viewer.add(item);
							viewer.getTable().setFocus();
							viewer.editElement(item, 1);
							viewerScrollPageBook.showPage(DETAILS_PAGE);
							if (item!=null && new StructuredSelection(item)!=null) {
								viewer.setSelection(new StructuredSelection(item));
							}
							//Domain Editor dirty
							domainFormViewer.getEditor().modified();
							int itemCount = viewer.getTable().getItemCount();
							domainFormViewer.getRemoveRowButton().setEnabled(itemCount > 0);
							domainFormViewer.getDuplicateButton().setEnabled(itemCount > 0);
							domainFormViewer.getDomainDataTypesCombo().setEnabled(itemCount == 0);
							
							//For Boolean data types
							if (domain_data_types == DOMAIN_DATA_TYPES.BOOLEAN 
									&& viewer.getTable().getItemCount() == 2) {
								domainFormViewer.getAddRowButton().setEnabled(false);
								domainFormViewer.getDuplicateButton().setEnabled(false);
							} 
						}
					}
					if (actionType.equalsIgnoreCase(Messages.getString("Remove"))) {
						if(domainFormViewer.getTableEditor()!=null &&
								domainFormViewer.getTableEditor().getEditor()!=null
								&&  !domainFormViewer.getTableEditor().getEditor().isDisposed()){
							domainFormViewer.getTableEditor().getEditor().dispose();
						}
						if (viewer.getSelection() != null) {

							IStructuredSelection selItem = (IStructuredSelection) viewer.getSelection();

							if (selItem.getFirstElement() == null) {
								return;
							}
							
							int oldcnt = viewer.getTable().getItemCount();
							int k = viewer.getTable().getSelectionIndex();
													
							viewer.remove(selItem.getFirstElement());

							if (viewer.getTable().getItemCount() > 0) {
								if (k-1 != -1) {
									setSelection(viewer, viewerScrollPageBook, k-1);
								}
								if (k !=-1) {
									setSelection(viewer, viewerScrollPageBook, k);
								} 
							}
							int newcnt = viewer.getTable().getItemCount();
							if (oldcnt > newcnt) {
								domainFormViewer.getEditor().modified();//Domain Editor dirty
							}
							domainFormViewer.getAddRowButton().setEnabled(true);
							domainFormViewer.getRemoveRowButton().setEnabled(newcnt > 0);
							domainFormViewer.getDuplicateButton().setEnabled(newcnt > 0);
							domainFormViewer.getDomainDataTypesCombo().setEnabled(newcnt == 0);
						}
					}
					if (actionType.equalsIgnoreCase(Messages.getString("duplicate"))) {
 						DOMAIN_DATA_TYPES domain_data_types = DOMAIN_DATA_TYPES.get(domainDataType);
						if (domain_data_types == DOMAIN_DATA_TYPES.BOOLEAN) {
							if (viewer.getTable().getItemCount() == 2) {
								return;
							}
						} 						
						if (!viewer.getSelection().isEmpty()) {
							IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
							for (Object obj  : sel.toList()) {
								viewer.add(obj);
							}
							domainFormViewer.getEditor().modified();
						}
						viewer.getTable().forceFocus();			
						
						//For Boolean data types
						if (domain_data_types == DOMAIN_DATA_TYPES.BOOLEAN 
								&& viewer.getTable().getItemCount() == 2) {
							domainFormViewer.getAddRowButton().setEnabled(false);
							domainFormViewer.getDuplicateButton().setEnabled(false);
						} 
					}
				}
				catch(Exception e){
					EditorsUIPlugin.log(e);
				}
			}});
	}
	
	/**
	 * @param viewer
	 * @param pagebook
	 * @param index
	 */
	private void setSelection(TableViewer viewer, ScrolledPageBook pagebook, int index) {
		viewer.getTable().select(index);
		DomainViewerItem item =(DomainViewerItem)viewer.getElementAt(index);
		pagebook.showPage(DETAILS_PAGE);
		if (item!=null && new StructuredSelection(item)!=null) {
			viewer.setSelection(new StructuredSelection(item));
			viewer.getTable().forceFocus();			
		}
	}
	
	/**
	 * @param description
	 * @param value
	 * @return
	 */
	private DomainViewerItem getItem(String description, String value) {
		return new DomainViewerItem(description,value);
	}
}