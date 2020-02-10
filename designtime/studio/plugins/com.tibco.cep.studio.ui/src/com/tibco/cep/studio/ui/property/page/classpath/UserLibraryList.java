package com.tibco.cep.studio.ui.property.page.classpath;

import java.util.ArrayList;
import java.util.List;

public class UserLibraryList<E> {

	protected List<E> fElements;
	
	/**
	 * @param adapter Can be <code>null</code>.
	 */
	public UserLibraryList() {

		fElements= new ArrayList<E>(10);
	}

   /**
	* Sets the elements shown in the list.
	*/
	public void setElements(List<E> elements) {
		fElements= new ArrayList<E>(elements);
	}

	/**
	* Gets the elements shown in the list.
	* The list returned is a copy, so it can be modified by the user.
	*/
	public List<E> getElements() {
		return new ArrayList<E>(fElements);
	}

	/**
	* Gets the element shown at the given index.
	*/
	public E getElement(int index) {
		return fElements.get(index);
	}

	/**
	* Gets the index of an element in the list or -1 if element is not in list.
    */
	public int getIndexOfElement(Object elem) {
		return fElements.indexOf(elem);
	}

   /**
	* Replace an element.
	*/
	public void replaceElement(E oldElement, E newElement) throws IllegalArgumentException {
		int idx= fElements.indexOf(oldElement);
		if (idx != -1) {
			fElements.set(idx, newElement);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	* Adds an element at the end of the tree list.
	*/
	public boolean addElement(E element) {
		if (fElements.contains(element)) {
			return false;
		}
		fElements.add(element);
		return true;
	}

	/**
	* Adds elements at the end of the tree list.
	*/
	public boolean addElements(List<E> elements) {
		int nElements= elements.size();

		if (nElements > 0) {
			// filter duplicated
			ArrayList<E> elementsToAdd= new ArrayList<E>(nElements);

			for (int i= 0; i < nElements; i++) {
				E elem= elements.get(i);
				if (!fElements.contains(elem)) {
					elementsToAdd.add(elem);
				}
			}
			if (!elementsToAdd.isEmpty()) {
				fElements.addAll(elementsToAdd);
				return true;
			}
		}
		return false;
	}

	/**
	* Adds an element at a position.
	*/
	public void insertElementAt(E element, int index) {
		if (fElements.contains(element)) {
			return;
		}
		fElements.add(index, element);
	}

	/**
	* Adds an element at a position.
	*/
	public void removeAllElements() {
		if (fElements.size() > 0) {
			fElements.clear();
		}
	}

	/**
	* Removes an element from the list.
	*/
	public void removeElement(E element) throws IllegalArgumentException {
		if (fElements.remove(element)) {
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	* Removes elements from the list.
	*/
	public void removeElements(List<?> elements) {
		if (elements.size() > 0) {
			fElements.removeAll(elements);
		}
	}

	/**
	* Gets the number of elements
	*/
	public int getSize() {
		return fElements.size();
	}


}
