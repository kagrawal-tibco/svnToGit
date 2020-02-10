package com.tibco.cep.studio.ui.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;


public class NavigatorTreeGroup extends EventManager implements	ICheckStateListener, ISelectionChangedListener, ITreeViewerListener {

	private Object root;

	@SuppressWarnings("unused")
	private Object currentTreeSelection;
	@SuppressWarnings("rawtypes")
	private Collection expandedTreeNodes = new HashSet();
    @SuppressWarnings("rawtypes")
	private Map checkedStateStore = new HashMap(9);
    @SuppressWarnings("rawtypes")
	private Collection whiteCheckedTreeItems = new HashSet();
    private ITreeContentProvider treeContentProvider;
    private IStructuredContentProvider listContentProvider;
    private ILabelProvider treeLabelProvider;
    @SuppressWarnings("unused")
	private ILabelProvider listLabelProvider;
    private CommonCheckBoxTreeViewer treeViewer;

    private static int PREFERRED_HEIGHT = 150;

    /**
     * @param parent
     * @param rootObject
     * @param treeContentProvider
     * @param treeLabelProvider
     * @param listContentProvider
     * @param listLabelProvider
     * @param style
     * @param useHeightHint
     */
    public NavigatorTreeGroup(Composite parent, Object rootObject,
            ITreeContentProvider treeContentProvider,
            ILabelProvider treeLabelProvider,
            IStructuredContentProvider listContentProvider,
            ILabelProvider listLabelProvider, int style, boolean useHeightHint) {

        root = rootObject;
        this.treeContentProvider = treeContentProvider;
        this.listContentProvider = listContentProvider;
        this.treeLabelProvider = treeLabelProvider;
        this.listLabelProvider = listLabelProvider;
        createContents(parent, style, useHeightHint);
    }

    public void aboutToOpen() {
        determineWhiteCheckedDescendents(root);
        checkNewTreeElements(treeContentProvider.getElements(root));
        currentTreeSelection = null;

        //select the first element in the list
        Object[] elements = treeContentProvider.getElements(root);
        Object primary = elements.length > 0 ? elements[0] : null;
        if (primary != null) {
            treeViewer.setSelection(new StructuredSelection(primary));
        }
        treeViewer.getControl().setFocus();
    }

    /**
     * @param listener
     */
    public void addCheckStateListener(ICheckStateListener listener) {
        addListenerObject(listener);
    }

    /**
     * @param treeElement
     * @return
     */
    protected boolean areAllChildrenWhiteChecked(Object treeElement) {
        Object[] children = treeContentProvider.getChildren(treeElement);
        for (int i = 0; i < children.length; ++i) {
            if (!whiteCheckedTreeItems.contains(children[i])) {
				return false;
			}
        }
        return true;
    }

    /**
     * @param treeElement
     * @return
     */
    @SuppressWarnings("rawtypes")
	protected boolean areAllElementsChecked(Object treeElement) {
        List checkedElements = (List) checkedStateStore.get(treeElement);
        if (checkedElements == null) {
			return false;
		}

        return getListItemsSize(treeElement) == checkedElements.size();
    }

    /**
     * @param elements
     */
    protected void checkNewTreeElements(Object[] elements) {
        for (int i = 0; i < elements.length; ++i) {
            Object currentElement = elements[i];
            boolean checked = checkedStateStore.containsKey(currentElement);
            treeViewer.setChecked(currentElement, checked);
            treeViewer.setGrayed(currentElement, checked
                    && !whiteCheckedTreeItems.contains(currentElement));
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ICheckStateListener#checkStateChanged(org.eclipse.jface.viewers.CheckStateChangedEvent)
     */
    public void checkStateChanged(final CheckStateChangedEvent event) {
    	BusyIndicator.showWhile(treeViewer.getControl().getDisplay(),
    			new Runnable() {
    		/* (non-Javadoc)
    		 * @see java.lang.Runnable#run()
    		 */
    		public void run() {
    			if (event.getCheckable().equals(treeViewer)) {
    				treeItemChecked(event.getElement(), event.getChecked());
    			} 
    			notifyCheckStateChangeListeners(event);
    		}
    	});
    }

    /**
     * @param parent
     * @param style
     * @param useHeightHint
     */
    protected void createContents(Composite parent, int style,
            boolean useHeightHint) {
        createTreeViewer(parent, useHeightHint);
        initialize();
    }


    /**
     * @param parent
     * @param useHeightHint
     */
    protected void createTreeViewer(Composite parent, boolean useHeightHint) {
        treeViewer = new CommonCheckBoxTreeViewer(null, parent, SWT.CHECK | SWT.BORDER);
        GridData data = new GridData();
        data.widthHint = 500;
        data.heightHint = 300;
        if (useHeightHint) {
			data.heightHint = PREFERRED_HEIGHT;
		}
        treeViewer.getTree().setLayoutData(data);
        treeViewer.getTree().setFont(parent.getFont());
        treeViewer.setContentProvider(treeContentProvider);
        treeViewer.setLabelProvider(treeLabelProvider);
        treeViewer.addTreeListener(this);
        treeViewer.addCheckStateListener(this);
        treeViewer.addSelectionChangedListener(this);
    }

    /**
     * @param treeElement
     * @return
     */
    @SuppressWarnings("rawtypes")
	protected boolean determineShouldBeAtLeastGrayChecked(Object treeElement) {
        List checked = (List) checkedStateStore.get(treeElement);
        if (checked != null && (!checked.isEmpty())) {
			return true;
		}
        if (expandedTreeNodes.contains(treeElement)) {
            Object[] children = treeContentProvider.getChildren(treeElement);
            for (int i = 0; i < children.length; ++i) {
                if (checkedStateStore.containsKey(children[i])) {
					return true;
				}
            }
        }
        return false;
    }

    /**
     * @param treeElement
     * @return
     */
    protected boolean determineShouldBeWhiteChecked(Object treeElement) {
        return areAllChildrenWhiteChecked(treeElement)
                && areAllElementsChecked(treeElement);
    }

    /**
     * @param treeElement
     */
    protected void determineWhiteCheckedDescendents(Object treeElement) {
        Object[] children = treeContentProvider.getElements(treeElement);
        for (int i = 0; i < children.length; ++i) {
			determineWhiteCheckedDescendents(children[i]);
		}
        if (determineShouldBeWhiteChecked(treeElement)) {
			setWhiteChecked(treeElement, true);
		}
    }

    public void expandAll() {
        treeViewer.expandAll();
    }

    /**
     * @param item
     */
    private void expandTreeElement(final Object item) {
        BusyIndicator.showWhile(treeViewer.getControl().getDisplay(),
                new Runnable() {
					@SuppressWarnings({ "unchecked", "rawtypes" })
					public void run() {

                        // First see if the children need to be given their checked state at all.  If they've
                        // already been realized then this won't be necessary
                        if (expandedTreeNodes.contains(item)) {
							checkNewTreeElements(treeContentProvider
                                    .getChildren(item));
						} else {

                            expandedTreeNodes.add(item);
                            if (whiteCheckedTreeItems.contains(item)) {
                                //If this is the first expansion and this is a white checked node then check the children
                                Object[] children = treeContentProvider
                                        .getChildren(item);
                                for (int i = 0; i < children.length; ++i) {
                                    if (!whiteCheckedTreeItems
                                            .contains(children[i])) {
                                        Object child = children[i];
                                        setWhiteChecked(child, true);
                                        treeViewer.setChecked(child, true);
                                        checkedStateStore.put(child,
                                                new ArrayList());
                                    }
                                }

                                //Now be sure to select the list of items too
                                setListForWhiteSelection(item);
                            }
                        }

                    }
                });
    }

    /**
     * @param treeElement
     * @param parentLabel
     * @param addAll
     * @param filter
     * @param monitor
     * @throws InterruptedException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void findAllSelectedListElements(Object treeElement,
            String parentLabel, boolean addAll, IElementFilter filter,
            IProgressMonitor monitor) throws InterruptedException {

        String fullLabel = null;
        if (monitor != null && monitor.isCanceled()) {
			return;
		}
        if (monitor != null) {
            fullLabel = getFullLabel(treeElement, parentLabel);
            monitor.subTask(fullLabel);
        }

        if (addAll) {
			filter.filterElements(listContentProvider.getElements(treeElement),
                    monitor);
		} else { //Add what we have stored
            if (checkedStateStore.containsKey(treeElement)) {
				filter.filterElements((Collection) checkedStateStore
                        .get(treeElement), monitor);
			}
        }

        Object[] treeChildren = treeContentProvider.getChildren(treeElement);
        for (int i = 0; i < treeChildren.length; i++) {
            Object child = treeChildren[i];
            if (addAll) {
				findAllSelectedListElements(child, fullLabel, true, filter,
                        monitor);
			} else { //Only continue for those with checked state
                if (checkedStateStore.containsKey(child)) {
					findAllSelectedListElements(child, fullLabel,
                            whiteCheckedTreeItems.contains(child), filter,
                            monitor);
				}
            }

        }
    }

    /**
     * @param treeElement
     * @param result
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void findAllWhiteCheckedItems(Object treeElement, Collection result) {

        if (whiteCheckedTreeItems.contains(treeElement)) {
			result.add(treeElement);
		} else {
            Collection listChildren = (Collection) checkedStateStore
                    .get(treeElement);
            //if it is not in the store then it and it's children are not interesting
            if (listChildren == null) {
				return;
			}
            result.addAll(listChildren);
            Object[] children = treeContentProvider.getChildren(treeElement);
            for (int i = 0; i < children.length; ++i) {
                findAllWhiteCheckedItems(children[i], result);
            }
        }
    }

    /**
     * @param filter
     * @param monitor
     * @throws InterruptedException
     */
    public void getAllCheckedListItems(IElementFilter filter,
            IProgressMonitor monitor) throws InterruptedException {
        Object[] children = treeContentProvider.getChildren(root);
        for (int i = 0; i < children.length; ++i) {
            findAllSelectedListElements(children[i], null,
                    whiteCheckedTreeItems.contains(children[i]), filter,
                    monitor);
        }
    }

    /**
     * @return
     */
    @SuppressWarnings("rawtypes")
	public List getAllCheckedListItems() {
        final ArrayList returnValue = new ArrayList();
        IElementFilter passThroughFilter = new IElementFilter() {
            @SuppressWarnings("unchecked")
			public void filterElements(Collection elements,
                    IProgressMonitor monitor) {
                returnValue.addAll(elements);
            }
            @SuppressWarnings("unchecked")
			public void filterElements(Object[] elements,
                    IProgressMonitor monitor) {
                for (int i = 0; i < elements.length; i++) {
                    returnValue.add(elements[i]);
                }
            }
        };

        try {
            getAllCheckedListItems(passThroughFilter, null);
        } catch (InterruptedException exception) {
            return new ArrayList();
        }
        return returnValue;

    }

    /**
     * @return
     */
    @SuppressWarnings("rawtypes")
	public List getAllWhiteCheckedItems() {
        List result = new ArrayList();
        Object[] children = treeContentProvider.getChildren(root);
        for (int i = 0; i < children.length; ++i) {
            findAllWhiteCheckedItems(children[i], result);
        }

        return result;
    }

    public int getCheckedElementCount() {
        return checkedStateStore.size();
    }

    /**
     * @param treeElement
     * @param parentLabel
     * @return
     */
    protected String getFullLabel(Object treeElement, String parentLabel) {
        String label = parentLabel;
        if (parentLabel == null){
        	label = ""; //$NON-NLS-1$
        }
        IPath parentName = new Path(label);

        String elementText = treeLabelProvider.getText(treeElement);
        if(elementText == null) {
			return parentName.toString();
		}
        return parentName.append(elementText).toString();
    }

     protected int getListItemsSize(Object treeElement) {
        Object[] elements = listContentProvider.getElements(treeElement);
        return elements.length;
    }

    /**
     * @param treeElement
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected void grayCheckHierarchy(Object treeElement) {
        expandTreeElement(treeElement);
        if (checkedStateStore.containsKey(treeElement)) {
			return;
		}
        checkedStateStore.put(treeElement, new ArrayList());
        Object parent = treeContentProvider.getParent(treeElement);
        if (parent != null) {
			grayCheckHierarchy(parent);
		}
    }

    /**
     * @param treeElement
     */
    private void grayUpdateHierarchy(Object treeElement) {

        boolean shouldBeAtLeastGray = determineShouldBeAtLeastGrayChecked(treeElement);
        treeViewer.setGrayChecked(treeElement, shouldBeAtLeastGray);
        if (whiteCheckedTreeItems.contains(treeElement)) {
			whiteCheckedTreeItems.remove(treeElement);
		}
        Object parent = treeContentProvider.getParent(treeElement);
        if (parent != null) {
            grayUpdateHierarchy(parent);
        }
    }

 
    /**
     * @param element
     */
    public void initialCheckTreeItem(Object element) {
        treeItemChecked(element, true);
        selectAndReveal(element);
    }

    /**
     * @param treeElement
     */
    private void selectAndReveal(Object treeElement) {
        treeViewer.reveal(treeElement);
        IStructuredSelection selection = new StructuredSelection(treeElement);
        treeViewer.setSelection(selection);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	protected void initialize() {
        treeViewer.setInput(root);
        this.expandedTreeNodes = new ArrayList();
        this.expandedTreeNodes.add(root);

    }
 
    /**
     * @param event
     */
    protected void notifyCheckStateChangeListeners(
            final CheckStateChangedEvent event) {
        Object[] array = getListeners();
        for (int i = 0; i < array.length; i++) {
            final ICheckStateListener l = (ICheckStateListener) array[i];
            SafeRunner.run(new SafeRunnable() {
                public void run() {
                    l.checkStateChanged(event);
                }
            });
        }
    }
 
    /**
     * @param item
     * @param selectedNodes
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void primeHierarchyForSelection(Object item, Set selectedNodes) {
        if (selectedNodes.contains(item)) {
			return;
		}
        checkedStateStore.put(item, new ArrayList());
        expandedTreeNodes.add(item);
        selectedNodes.add(item);
        Object parent = treeContentProvider.getParent(item);
        if (parent != null) {
			primeHierarchyForSelection(parent, selectedNodes);
		}
    }

    /**
     * @param listener
     */
    public void removeCheckStateListener(ICheckStateListener listener) {
        removeListenerObject(listener);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
     */
    public void selectionChanged(SelectionChangedEvent event) {
        IStructuredSelection selection = (IStructuredSelection) event
                .getSelection();
        Object selectedElement = selection.getFirstElement();
        if (selectedElement == null) {
            currentTreeSelection = null;
            return;
        }
        currentTreeSelection = selectedElement;
    }

    /**
     * @param selection
     */
    public void setAllSelections(final boolean selection) {
    	if (root == null) {
    		return;
    	}
    	BusyIndicator.showWhile(treeViewer.getControl().getDisplay(),
    			new Runnable() {
    		/* (non-Javadoc)
    		 * @see java.lang.Runnable#run()
    		 */
    		public void run() {
    			setTreeChecked(root, selection);
    		}
    	});
    }

    /**
     * @param treeElement
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void setListForWhiteSelection(Object treeElement) {
        Object[] listItems = listContentProvider.getElements(treeElement);
        List listItemsChecked = new ArrayList();
        for (int i = 0; i < listItems.length; ++i) {
            listItemsChecked.add(listItems[i]);
        }
        checkedStateStore.put(treeElement, listItemsChecked);
    }

    public void setRoot(Object newRoot) {
        this.root = newRoot;
        initialize();
    }

    /**
     * @param treeElement
     * @param state
     */
    protected void setTreeChecked(Object treeElement, boolean state) {
        if (state) {
            setListForWhiteSelection(treeElement);
        } else {
			checkedStateStore.remove(treeElement);
		}
        setWhiteChecked(treeElement, state);
        treeViewer.setChecked(treeElement, state);
        treeViewer.setGrayed(treeElement, false);
        if (expandedTreeNodes.contains(treeElement)) {
            Object[] children = treeContentProvider.getChildren(treeElement);
            for (int i = 0; i < children.length; ++i) {
                setTreeChecked(children[i], state);
            }
        }
    }

    /**
     * @param contentProvider
     * @param labelProvider
     */
    public void setTreeProviders(ITreeContentProvider contentProvider,
            ILabelProvider labelProvider) {
        treeViewer.setContentProvider(contentProvider);
        treeViewer.setLabelProvider(labelProvider);
    }

    /**
     * @param comparator
     */
    public void setTreeComparator(ViewerComparator comparator) {
        treeViewer.setComparator(comparator);
    }

    /**
     * @param treeElement
     * @param isWhiteChecked
     */
    @SuppressWarnings("unchecked")
	protected void setWhiteChecked(Object treeElement, boolean isWhiteChecked) {
        if (isWhiteChecked) {
            if (!whiteCheckedTreeItems.contains(treeElement)) {
				whiteCheckedTreeItems.add(treeElement);
			}
        } else {
			whiteCheckedTreeItems.remove(treeElement);
		}
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITreeViewerListener#treeCollapsed(org.eclipse.jface.viewers.TreeExpansionEvent)
     */
    public void treeCollapsed(TreeExpansionEvent event) {
    	
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITreeViewerListener#treeExpanded(org.eclipse.jface.viewers.TreeExpansionEvent)
     */
    public void treeExpanded(TreeExpansionEvent event) {
        expandTreeElement(event.getElement());
    }

    /**
     * @param treeElement
     * @param state
     */
    protected void treeItemChecked(Object treeElement, boolean state) {
        setTreeChecked(treeElement, state);
        Object parent = treeContentProvider.getParent(treeElement);
        if (parent == null) {
			return;
		}
        if (state) {
			grayCheckHierarchy(parent);
		} else {
			ungrayCheckHierarchy(parent);
		}
        grayUpdateHierarchy(parent);
    }
    /**
     * @param treeElement
     */
    protected void ungrayCheckHierarchy(Object treeElement) {
        if (!determineShouldBeAtLeastGrayChecked(treeElement)) {
			checkedStateStore.remove(treeElement);
		}
        Object parent = treeContentProvider.getParent(treeElement);
        if (parent != null) {
			ungrayCheckHierarchy(parent);
		}
    }

    /**
     * @param treeElement
     */
    protected void updateHierarchy(Object treeElement) {
        boolean whiteChecked = determineShouldBeWhiteChecked(treeElement);
        boolean shouldBeAtLeastGray = determineShouldBeAtLeastGrayChecked(treeElement);

        treeViewer.setChecked(treeElement, shouldBeAtLeastGray);
        setWhiteChecked(treeElement, whiteChecked);
        if (whiteChecked) {
			treeViewer.setGrayed(treeElement, false);
		} else {
			treeViewer.setGrayed(treeElement, shouldBeAtLeastGray);
		}
        Object parent = treeContentProvider.getParent(treeElement);
        if (parent != null) {
            grayUpdateHierarchy(parent);
        }
    }

    /**
     * @param items
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateSelections(Map items) {
        this.treeViewer.setCheckedElements(new Object[0]);
        this.whiteCheckedTreeItems = new HashSet();
        Set selectedNodes = new HashSet();
        checkedStateStore = new HashMap();
        Iterator keyIterator = items.keySet().iterator();
        while (keyIterator.hasNext()) {
            Object key = keyIterator.next();
            List selections = (List) items.get(key);
            checkedStateStore.put(key, selections);
            selectedNodes.add(key);
            Object parent = treeContentProvider.getParent(key);
            if (parent != null) {
                primeHierarchyForSelection(parent, selectedNodes);
            }
        }
        treeViewer.setCheckedElements(checkedStateStore.keySet().toArray());
        treeViewer.setGrayedElements(checkedStateStore.keySet().toArray());
    }

    public void setFocus() {
        this.treeViewer.getTree().setFocus();
    }

    /**
     * @return
     */
    public CommonCheckBoxTreeViewer getTreeViewer() {
		return treeViewer;
	}
}