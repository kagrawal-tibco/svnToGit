package com.tibco.cep.studio.dashboard.ui.viewers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.Assertion;

/**
 * @
 *
 */
public class ElementCheckBoxSelectionViewer extends TableViewer {

    protected ElementCheckBoxWrapper[] localElementWrappers;

    protected LocalElement parentElement = null;

    protected String elementType = null;

    protected Action selectAll = null;

    protected Action unselectAll = null;

    protected Action invertSelection = null;

    protected List<LocalElement> localElementChoices = new ArrayList<LocalElement>();

    private TableColumnInfo[] columnInfos;

//	private LocalElement defaultSelectedElement;

	private String defaultElementType;

	private ElementCheckBoxCellModifier cellModifier;

	private boolean multiSelectIsOn;

	private boolean singleSelectIsOn;

    /**
     * @param table
     */
    public ElementCheckBoxSelectionViewer(Table table) {
        super(table);
    }

    /**
     * @param parent
     * @param withDefaultSelector
     */
    public ElementCheckBoxSelectionViewer(Composite parent, LocalElement parentElement, String elementType, ElementCheckBoxSelectionTable checkBoxTable) {
    	this(parent, parentElement, elementType, null, checkBoxTable);
    }

    /**
     * @param parent
     * @param withDefaultSelector
     */
    public ElementCheckBoxSelectionViewer(Composite parent, LocalElement parentElement, String elementType, String defaultElementType, ElementCheckBoxSelectionTable checkBoxTable) {
        super(checkBoxTable.getTable());

        Assertion.isNull(parentElement);
        this.parentElement = parentElement;

        Assertion.isNull(elementType);
        this.elementType = elementType;

        this.defaultElementType = defaultElementType;

        this.columnInfos = checkBoxTable.getColumnInfos();

		multiSelectIsOn = false;
		for (TableColumnInfo columnInfo : columnInfos) {
			if (columnInfo.isMultiSelect() == true) {
				multiSelectIsOn = true;
			}
			else if (columnInfo.isSingleSelect() == true) {
				singleSelectIsOn = true;
			}
		}

        initViewer();
    }

	protected void initViewer() {

        setContentProvider(new ArrayContentProvider());
        setLabelProvider(new ElementCheckBoxLabelProvider(columnInfos));
        setSorter(new ElementCheckBoxNameSorter());
        setColumnProperties(makeColumnNames());

        CellEditor[] editors = new CellEditor[columnInfos.length];
        for (int i = 0; i < columnInfos.length; i++) {
			TableColumnInfo columnInfo = columnInfos[i];
			if (columnInfo.isMultiSelect()) {
	            editors[i] = new CheckboxCellEditor(getTable(), SWT.CHECK);
			}
			else if (columnInfo.isSingleSelect()){
				editors[i] = new CheckboxCellEditor(getTable(), SWT.RADIO);
			}
			else {
				editors[i] = null;
			}
		}
        setCellEditors(editors);

        cellModifier = new ElementCheckBoxCellModifier(this, parentElement, elementType, defaultElementType);
		setCellModifier(cellModifier);
		//decide if we create and hook up the actions
        if (multiSelectIsOn == true) {
			createActions();
			hookContextMenu();
		}
    }

    private String[] makeColumnNames() {
    	String[] columnNames = new String[columnInfos.length];
    	for (int i = 0; i < columnInfos.length; i++) {
			TableColumnInfo columnInfo = columnInfos[i];
			columnNames[i] = columnInfo.getId();
		}
		return columnNames;
	}

	private void hookContextMenu() {
        MenuManager menuMgr = new MenuManager("#PopupMenu");
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(new IMenuListener() {

            public void menuAboutToShow(IMenuManager manager) {
                fillContextMenu(manager);
            }
        });
        Menu menu = menuMgr.createContextMenu(getControl());
        getControl().setMenu(menu);
    }

    private void fillContextMenu(IMenuManager manager) {
        manager.add(selectAll);
        manager.add(unselectAll);
        if (singleSelectIsOn == false) {
			manager.add(new Separator());
			manager.add(invertSelection);
        }
    }

    private void createActions() {
        selectAll = new Action("Select All", SWT.NONE) {
            public void run() {
                try {
                    for (int i = 0; i < localElementWrappers.length; i++) {
                        if (false == localElementWrappers[i].isChecked()) {
                            localElementWrappers[i].setChecked(true);
                            parentElement.addElement(elementType, localElementWrappers[i].getLocalElement());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
	            } finally {
	            	refresh();
	                updateActions();
	            }
            }
        };
        selectAll.setToolTipText("Select all");

        unselectAll = new Action("Unselect All", SWT.NONE) {
            public void run() {
                try {
                    for (int i = 0; i < localElementWrappers.length; i++) {
                        if (true == localElementWrappers[i].isChecked()) {
                            localElementWrappers[i].setChecked(false);
                            if (localElementWrappers[i].isDefaultChecked() == true) {
                            	localElementWrappers[i].setDefaultChecked(false);
                            	parentElement.removeElement(defaultElementType, localElementWrappers[i].getLocalElement().getName(), localElementWrappers[i].getLocalElement().getFolder());
                            }
//                            parentElement.removeElement(localElementWrappers[i].getLocalElement());
                            parentElement.removeElement(elementType, localElementWrappers[i].getLocalElement().getName(), localElementWrappers[i].getLocalElement().getFolder());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                	refresh();
                    updateActions();
                }
            }
        };
        unselectAll.setToolTipText("Unselect all");

        if (singleSelectIsOn == false) {
			invertSelection = new Action("Invert Selection", SWT.NONE) {
				public void run() {
					try {
						for (int i = 0; i < localElementWrappers.length; i++) {
							if (localElementWrappers[i].isChecked() == true) {
								localElementWrappers[i].setChecked(false);
								parentElement.removeElement(elementType, localElementWrappers[i].getLocalElement().getName(), localElementWrappers[i].getLocalElement().getFolder());
							} else {
								localElementWrappers[i].setChecked(true);
								parentElement.addElement(elementType, localElementWrappers[i].getLocalElement());
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						refresh();
					}

				}
			};
			invertSelection.setToolTipText("Invert Selection");
        }

    }

    public void setElementChoices(List<LocalElement> localElementChoices) {
        Assertion.isNull(localElementChoices);
        this.localElementChoices = localElementChoices;
        this.localElementWrappers = ElementCheckBoxWrapper.convertToWrapper(localElementChoices);
        setInput(localElementWrappers);
    }

    public void setSelectedElements(List<LocalElement> localElementList, LocalElement defaultSelectedElement) {
        if (null != localElementWrappers) {
            try {
                for (int i = 0; i < localElementWrappers.length; i++) {
                    ElementCheckBoxWrapper wrapper = localElementWrappers[i];
                    wrapper.setChecked(false);
                    for (Iterator<LocalElement> iter = localElementList.iterator(); iter.hasNext();) {
                        LocalElement element = iter.next();
                        if (true == element.getName().equals(wrapper.getName()) && element.getFolder().equals(wrapper.getFolder()) == true) {
                            wrapper.setLocalElementChecked(element, true);
                        }
                    }
                    if (defaultSelectedElement!= null && true == defaultSelectedElement.getName().equals(wrapper.getName())  && defaultSelectedElement.getFolder().equals(wrapper.getFolder()) == true) {
                        wrapper.setDefaultChecked(true);
                    }
                }
                updateActions();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        refresh();
    }

    public void setSelectedElements(List<LocalElement> localElementList) {
    	this.setSelectedElements(localElementList, null);
    }

    public void setLayoutData(Object layoutData) {
        getTable().setLayoutData(layoutData);
        getTable().layout();
    }

    public LocalElement getSelectedElement() {
        IStructuredSelection selection = (IStructuredSelection) getSelection();
        if (null != selection) {
            return (LocalElement) selection.getFirstElement();
        }

        return null;
    }

    public TableColumnInfo getColumnInfo(String id) {
        for (int i = 0; i < columnInfos.length; i++) {
            if (true == columnInfos[i].getId().equals(id)) {
                return columnInfos[i];
            }

        }
        return null;
    }

    public List<LocalElement> getElementChoices() {
        return localElementChoices;
    }

	public ElementCheckBoxWrapper getDefaultSelectedElement() {
		for (ElementCheckBoxWrapper wrapper : localElementWrappers) {
			if (wrapper.isDefaultChecked()) {
				return wrapper;
			}
		}
		return null;
	}

	public void setParentElement(LocalElement parentElement) {
		this.parentElement = parentElement;
		cellModifier.setParentElement(parentElement);
	}

	public void updateActions(){
		boolean allSelected = true;
		boolean anySelected = false;
		for (ElementCheckBoxWrapper wrapper : localElementWrappers) {
			allSelected = allSelected && wrapper.isChecked();
			anySelected = anySelected || wrapper.isChecked();
		}
        if (selectAll != null) {
			//disable select all
			selectAll.setEnabled(allSelected == false);
		}
		if (unselectAll != null) {
			//enable unselect all
			unselectAll.setEnabled(anySelected == true);
		}
		//enable invert selection
        if (invertSelection != null) {
        	invertSelection.setEnabled(anySelected == true);
        }
	}

}