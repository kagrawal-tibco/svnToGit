package com.tibco.cep.studio.ui.widgets;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ICheckable;
import org.eclipse.jface.viewers.IElementComparer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.navigator.CommonViewer;


public class CommonCheckBoxTreeViewer extends CommonViewer implements ICheckable {

    private ListenerList checkStateListeners = new ListenerList();
    private boolean preserveSelection = true;
    private ICheckStateProvider checkStateProvider;
    private TreeItem lastClickedItem = null;
    

	/**
	 * @param viewerId
	 * @param parent
	 * @param style
	 */
	public CommonCheckBoxTreeViewer(String viewerId, Composite parent, int style) {
		super(viewerId, parent, style);
	}

    /* (non-Javadoc)
     * Method declared on ICheckable.
     */
    public void addCheckStateListener(ICheckStateListener listener) {
        checkStateListeners.add(listener);
    }
    
    /**
     * @param checkStateProvider
     */
    public void setCheckStateProvider(ICheckStateProvider checkStateProvider) {
    	this.checkStateProvider = checkStateProvider;
    	refresh();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.AbstractTreeViewer#doUpdateItem(org.eclipse.swt.widgets.Item, java.lang.Object)
     */
    protected void doUpdateItem(Item item, Object element) {
    	super.doUpdateItem(item, element);
    	if(!item.isDisposed() && checkStateProvider != null) {
			setChecked(element, checkStateProvider.isChecked(element));
			setGrayed(element, checkStateProvider.isGrayed(element));
    	}
	}

    /**
     * @param checked
     * @param grayed
     * @param widget
     */
    private void applyState(CommonCustomHashtable checked, CommonCustomHashtable grayed,
            Widget widget) {
        Item[] items = getChildren(widget);
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            if (item instanceof TreeItem) {
                Object data = item.getData();
                if (data != null) {
                    TreeItem ti = (TreeItem) item;
                    ti.setChecked(checked.containsKey(data));
                    ti.setGrayed(grayed.containsKey(data));
                }
            }
            applyState(checked, grayed, item);
        }
    }

     /**
     * @param event
     */
    protected void fireCheckStateChanged(final CheckStateChangedEvent event) {
        Object[] array = checkStateListeners.getListeners();
        for (int i = 0; i < array.length; i++) {
            final ICheckStateListener l = (ICheckStateListener) array[i];
            SafeRunnable.run(new SafeRunnable() {
                public void run() {
                    l.checkStateChanged(event);
                }
            });
        }

    }

    /**
     * @param checked
     * @param grayed
     * @param widget
     */
    private void gatherState(CommonCustomHashtable checked, CommonCustomHashtable grayed,
            Widget widget) {
        Item[] items = getChildren(widget);
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            if (item instanceof TreeItem) {
                Object data = item.getData();
                if (data != null) {
                    TreeItem ti = (TreeItem) item;
                    if (ti.getChecked()) {
						checked.put(data, data);
					}
                    if (ti.getGrayed()) {
						grayed.put(data, data);
					}
                }
            }
            gatherState(checked, grayed, item);
        }
    }

    /* (non-Javadoc)
     * Method declared on ICheckable.
     */
    public boolean getChecked(Object element) {
        Widget widget = findItem(element);
        if (widget instanceof TreeItem) {
			return ((TreeItem) widget).getChecked();
		}
        return false;
    }

    /**
     * @return
     */
    @SuppressWarnings("rawtypes")
	public Object[] getCheckedElements() {
        ArrayList v = new ArrayList();
        Control tree = getControl();
        internalCollectChecked(v, tree);
        return v.toArray();
    }

    /**
     * @param element
     * @return
     */
    public boolean getGrayed(Object element) {
        Widget widget = findItem(element);
        if (widget instanceof TreeItem) {
            return ((TreeItem) widget).getGrayed();
        }
        return false;
    }

    /**
     * @return
     */
    @SuppressWarnings("rawtypes")
	public Object[] getGrayedElements() {
        List result = new ArrayList();
        internalCollectGrayed(result, getControl());
        return result.toArray();
    }

    /* (non-Javadoc)
     * Method declared on StructuredViewer.
     */
    protected void handleDoubleSelect(SelectionEvent event) {

        if (lastClickedItem != null) {
            TreeItem item = lastClickedItem;
            Object data = item.getData();
            if (data != null) {
                boolean state = item.getChecked();
                setChecked(data, !state);
                fireCheckStateChanged(new CheckStateChangedEvent(this, data,
                        !state));
            }
            lastClickedItem = null;
        } else {
			super.handleDoubleSelect(event);
		}
    }

    /* (non-Javadoc)
     * Method declared on StructuredViewer.
     */
    protected void handleSelect(SelectionEvent event) {

        lastClickedItem = null;
        if (event.detail == SWT.CHECK) {
            TreeItem item = (TreeItem) event.item;
            lastClickedItem = item;
            super.handleSelect(event);

            Object data = item.getData();
            if (data != null) {
                fireCheckStateChanged(new CheckStateChangedEvent(this, data,
                        item.getChecked()));
            }
        } else {
			super.handleSelect(event);
		}
    }

    /**
     * @param result
     * @param widget
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void internalCollectChecked(List result, Widget widget) {
        Item[] items = getChildren(widget);
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            if (item instanceof TreeItem && ((TreeItem) item).getChecked()) {
                Object data = item.getData();
                if (data != null) {
					result.add(data);
				}
            }
            internalCollectChecked(result, item);
        }
    }

    /**
     * @param result
     * @param widget
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void internalCollectGrayed(List result, Widget widget) {
        Item[] items = getChildren(widget);
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            if (item instanceof TreeItem && ((TreeItem) item).getGrayed()) {
                Object data = item.getData();
                if (data != null) {
					result.add(data);
				}
            }
            internalCollectGrayed(result, item);
        }
    }

    /**
     * @param checkedElements
     * @param widget
     */
    private void internalSetChecked(CommonCustomHashtable checkedElements,
            Widget widget) {
        Item[] items = getChildren(widget);
        for (int i = 0; i < items.length; i++) {
            TreeItem item = (TreeItem) items[i];
            Object data = item.getData();
            if (data != null) {
                boolean checked = checkedElements.containsKey(data);
                if (checked != item.getChecked()) {
                    item.setChecked(checked);
                }
            }
            internalSetChecked(checkedElements, item);
        }
    }

    /**
     * @param grayedElements
     * @param widget
     */
    private void internalSetGrayed(CommonCustomHashtable grayedElements, Widget widget) {
        Item[] items = getChildren(widget);
        for (int i = 0; i < items.length; i++) {
            TreeItem item = (TreeItem) items[i];
            Object data = item.getData();
            if (data != null) {
                boolean grayed = grayedElements.containsKey(data);
                if (grayed != item.getGrayed()) {
                    item.setGrayed(grayed);
                }
            }
            internalSetGrayed(grayedElements, item);
        }
    }

    /* (non-Javadoc)
     * Method declared on Viewer.
     */
    protected void preservingSelection(Runnable updateCode) {
    	if (!getPreserveSelection()) {
    		return;
    	}
    	if(checkStateProvider != null) {
    		//Try to preserve the selection, let the ICheckProvider manage 
    		//the check states
    		super.preservingSelection(updateCode);
    		return;
    	}
    	
    	//Preserve checked items
        int n = getItemCount(getControl());
        CommonCustomHashtable checkedNodes = newHashtable(n * 2 + 1);
        CommonCustomHashtable grayedNodes = newHashtable(n * 2 + 1);

        gatherState(checkedNodes, grayedNodes, getControl());

        super.preservingSelection(updateCode);

        applyState(checkedNodes, grayedNodes, getControl());
    }
    
	boolean getPreserveSelection() {
		return this.preserveSelection;
	}

    /* (non-Javadoc)
     * Method declared on ICheckable.
     */
    public void removeCheckStateListener(ICheckStateListener listener) {
        checkStateListeners.remove(listener);
    }
    
    CommonCustomHashtable newHashtable(int capacity) {
		return new CommonCustomHashtable(capacity, getComparer());
	}

    /* (non-Javadoc)
     * Method declared on ICheckable.
     */
    public boolean setChecked(Object element, boolean state) {
        Assert.isNotNull(element);
        Widget widget = internalExpand(element, false);
        if (widget instanceof TreeItem) {
            ((TreeItem) widget).setChecked(state);
            return true;
        }
        return false;
    }

    /**
     * @param item
     * @param state
     */
    private void setCheckedChildren(Item item, boolean state) {
        createChildren(item);
        Item[] items = getChildren(item);
        if (items != null) {
            for (int i = 0; i < items.length; i++) {
                Item it = items[i];
                if (it.getData() != null && (it instanceof TreeItem)) {
                    TreeItem treeItem = (TreeItem) it;
                    treeItem.setChecked(state);
                    setCheckedChildren(treeItem, state);
                }
            }
        }
    }

     /**
     * @param elements
     */
    public void setCheckedElements(Object[] elements) {
        assertElementsNotNull(elements);
        CommonCustomHashtable checkedElements = newHashtable(elements.length * 2 + 1);
        for (int i = 0; i < elements.length; ++i) {
            Object element = elements[i];
            // Ensure item exists for element
            internalExpand(element, false);
            checkedElements.put(element, element);
        }
        Control tree = getControl();
        tree.setRedraw(false);
        internalSetChecked(checkedElements, tree);
        tree.setRedraw(true);
    }

     /**
     * @param element
     * @param state
     * @return
     */
    public boolean setGrayed(Object element, boolean state) {
        Assert.isNotNull(element);
        Widget widget = internalExpand(element, false);
        if (widget instanceof TreeItem) {
            ((TreeItem) widget).setGrayed(state);
            return true;
        }
        return false;
    }

    /**
     * @param element
     * @param state
     * @return
     */
    public boolean setGrayChecked(Object element, boolean state) {
        Assert.isNotNull(element);
        Widget widget = internalExpand(element, false);
        if (widget instanceof TreeItem) {
            TreeItem item = (TreeItem) widget;
            item.setChecked(state);
            item.setGrayed(state);
            return true;
        }
        return false;
    }

    /**
     * @param elements
     */
    public void setGrayedElements(Object[] elements) {
        assertElementsNotNull(elements);
        CommonCustomHashtable grayedElements = newHashtable(elements.length * 2 + 1);
        for (int i = 0; i < elements.length; ++i) {
            Object element = elements[i];
            // Ensure item exists for element
            internalExpand(element, false);
            grayedElements.put(element, element);
        }
        Control tree = getControl();
        tree.setRedraw(false);
        internalSetGrayed(grayedElements, tree);
        tree.setRedraw(true);
    }

    /**
     * @param element
     * @param state
     * @return
     */
    public boolean setParentsGrayed(Object element, boolean state) {
        Assert.isNotNull(element);
        Widget widget = internalExpand(element, false);
        if (widget instanceof TreeItem) {
            TreeItem item = (TreeItem) widget;
            item.setGrayed(state);
            item = item.getParentItem();
            while (item != null) {
                item.setGrayed(state);
                item = item.getParentItem();
            }
            return true;
        }
        return false;
    }

    /**
     * @param element
     * @param state
     * @return
     */
    public boolean setSubtreeChecked(Object element, boolean state) {
        Widget widget = internalExpand(element, false);
        if (widget instanceof TreeItem) {
            TreeItem item = (TreeItem) widget;
            item.setChecked(state);
            setCheckedChildren(item, state);
            return true;
        }
        return false;
    }

	
	boolean optionallyPruneChildren(Item item, Object element) {
		return false;
	}
}

final class CommonCustomHashtable {
    private static class HashMapEntry {
        Object key, value;

        HashMapEntry next;

        HashMapEntry(Object theKey, Object theValue) {
            key = theKey;
            value = theValue;
        }
    }

    @SuppressWarnings("rawtypes")
	private static final class EmptyEnumerator implements Enumeration {
        public boolean hasMoreElements() {
            return false;
        }

        public Object nextElement() {
            throw new NoSuchElementException();
        }
    }

    @SuppressWarnings("rawtypes")
    private class HashEnumerator implements Enumeration {
    	boolean key;

    	int start;

    	HashMapEntry entry;

    	HashEnumerator(boolean isKey) {
    		key = isKey;
    		start = firstSlot;
    	}

    	public boolean hasMoreElements() {
    		if (entry != null) {
    			return true;
    		}
    		while (start <= lastSlot) {
    			if (elementData[start++] != null) {
    				entry = elementData[start - 1];
    				return true;
    			}
    		}
    		return false;
    	}

    	public Object nextElement() {
    		if (hasMoreElements()) {
    			Object result = key ? entry.key : entry.value;
    			entry = entry.next;
    			return result;
    		} else {
    			throw new NoSuchElementException();
    		}
    	}
    }

    transient int elementCount;
    transient HashMapEntry[] elementData;
    private float loadFactor;
    private int threshold;
    transient int firstSlot = 0;
    transient int lastSlot = -1;
    transient private IElementComparer comparer;
    private static final EmptyEnumerator emptyEnumerator = new EmptyEnumerator();
    public static final int DEFAULT_CAPACITY = 13;

    public CommonCustomHashtable() {
    	this(13);
    }

    public CommonCustomHashtable(int capacity) {
    	this(capacity, null);
    }

    public CommonCustomHashtable(IElementComparer comparer) {
    	this(DEFAULT_CAPACITY, comparer);
    }

    public CommonCustomHashtable(int capacity, IElementComparer comparer) {
    	if (capacity >= 0) {
    		elementCount = 0;
    		elementData = new HashMapEntry[capacity == 0 ? 1 : capacity];
    		firstSlot = elementData.length;
    		loadFactor = 0.75f;
    		computeMaxSize();
    	} else {
    		throw new IllegalArgumentException();
    	}
    	this.comparer = comparer;
    }

    public CommonCustomHashtable(CommonCustomHashtable table, IElementComparer comparer) {
    	this(table.size() * 2, comparer);
    	for (int i = table.elementData.length; --i >= 0;) {
    		HashMapEntry entry = table.elementData[i];
    		while (entry != null) {
    			put(entry.key, entry.value);
    			entry = entry.next;
    		}
    	}
    }

    public IElementComparer getComparer() {
    	return comparer;
    }

    private void computeMaxSize() {
    	threshold = (int) (elementData.length * loadFactor);
    }

    public boolean containsKey(Object key) {
    	return getEntry(key) != null;
    }

    @SuppressWarnings("rawtypes")
    public Enumeration elements() {
    	if (elementCount == 0) {
    		return emptyEnumerator;
    	}
    	return new HashEnumerator(false);
    }

    public Object get(Object key) {
    	int index = (hashCode(key) & 0x7FFFFFFF) % elementData.length;
    	HashMapEntry entry = elementData[index];
    	while (entry != null) {
    		if (keyEquals(key, entry.key)) {
    			return entry.value;
    		}
    		entry = entry.next;
    	}
    	return null;
    }

    private HashMapEntry getEntry(Object key) {
    	int index = (hashCode(key) & 0x7FFFFFFF) % elementData.length;
    	HashMapEntry entry = elementData[index];
    	while (entry != null) {
    		if (keyEquals(key, entry.key)) {
    			return entry;
    		}
    		entry = entry.next;
    	}
    	return null;
    }

    private int hashCode(Object key) {
    	if (comparer == null) {
    		return key.hashCode();
    	} else {
    		return comparer.hashCode(key);
    	}
    }

    private boolean keyEquals(Object a, Object b) {
    	if (comparer == null) {
    		return a.equals(b);
    	} else {
    		return comparer.equals(a, b);
    	}
    }

    @SuppressWarnings("rawtypes")
    public Enumeration keys() {
    	if (elementCount == 0) {
    		return emptyEnumerator;
    	}
    	return new HashEnumerator(true);
    }

    public Object put(Object key, Object value) {
    	if (key != null && value != null) {
    		int index = (hashCode(key) & 0x7FFFFFFF) % elementData.length;
    		HashMapEntry entry = elementData[index];
    		while (entry != null && !keyEquals(key, entry.key)) {
    			entry = entry.next;
    		}
    		if (entry == null) {
    			if (++elementCount > threshold) {
    				rehash();
    				index = (hashCode(key) & 0x7FFFFFFF) % elementData.length;
    			}
    			if (index < firstSlot) {
    				firstSlot = index;
    			}
    			if (index > lastSlot) {
    				lastSlot = index;
    			}
    			entry = new HashMapEntry(key, value);
    			entry.next = elementData[index];
    			elementData[index] = entry;
    			return null;
    		}
    		Object result = entry.value;
    		entry.key = key;
    		entry.value = value;
    		return result;
    	} else {
    		throw new NullPointerException();
    	}
    }

    private void rehash() {
    	int length = elementData.length << 1;
    	if (length == 0) {
    		length = 1;
    	}
    	firstSlot = length;
    	lastSlot = -1;
    	HashMapEntry[] newData = new HashMapEntry[length];
    	for (int i = elementData.length; --i >= 0;) {
    		HashMapEntry entry = elementData[i];
    		while (entry != null) {
    			int index = (hashCode(entry.key) & 0x7FFFFFFF) % length;
    			if (index < firstSlot) {
    				firstSlot = index;
    			}
    			if (index > lastSlot) {
    				lastSlot = index;
    			}
    			HashMapEntry next = entry.next;
    			entry.next = newData[index];
    			newData[index] = entry;
    			entry = next;
    		}
    	}
    	elementData = newData;
    	computeMaxSize();
    }

    public Object remove(Object key) {
    	HashMapEntry last = null;
    	int index = (hashCode(key) & 0x7FFFFFFF) % elementData.length;
    	HashMapEntry entry = elementData[index];
    	while (entry != null && !keyEquals(key, entry.key)) {
    		last = entry;
    		entry = entry.next;
    	}
    	if (entry != null) {
    		if (last == null) {
    			elementData[index] = entry.next;
    		} else {
    			last.next = entry.next;
    		}
    		elementCount--;
    		return entry.value;
    	}
    	return null;
    }

    public int size() {
    	return elementCount;
    }

    public String toString() {
    	if (size() == 0) {
    		return "{}"; //$NON-NLS-1$
    	}

    	StringBuffer buffer = new StringBuffer();
    	buffer.append('{');
    	for (int i = elementData.length; --i >= 0;) {
    		HashMapEntry entry = elementData[i];
    		while (entry != null) {
    			buffer.append(entry.key);
    			buffer.append('=');
    			buffer.append(entry.value);
    			buffer.append(", "); //$NON-NLS-1$
    			entry = entry.next;
    		}
    	}
    	// Remove the last ", "
    	if (elementCount > 0) {
    		buffer.setLength(buffer.length() - 2);
    	}
    	buffer.append('}');
    	return buffer.toString();
    }
}
