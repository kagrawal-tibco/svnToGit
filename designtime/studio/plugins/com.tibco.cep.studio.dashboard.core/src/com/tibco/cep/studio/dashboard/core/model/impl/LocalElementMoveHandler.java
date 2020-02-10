package com.tibco.cep.studio.dashboard.core.model.impl;

import java.util.LinkedList;

public class LocalElementMoveHandler {

	/**
	 * Changes the sorting order of elementToMove to one below that of the targetElement.
	 * All elements below targetElement get their sorting order incremented by 
	 * one
	 * @param elementToMove The {@link LocalElement} which to be moved below
	 * @param targetElement The {@link LocalElement} below which to move the elementToMove
	 * @throws Exception if setting of the sorting order fails
	 */
    public static synchronized void moveToBelow(LocalElement elementToMove, LocalElement targetElement) throws Exception {
        int targetSortingOrder = targetElement.getSortingOrder();
        //get all the active siblings 
        LinkedList<LocalElement> allActiveSiblings = new LinkedList<LocalElement>(elementToMove.getParentParticle().getElements(true));
        //remove the element to be moved
        allActiveSiblings.remove(elementToMove);
        //get the index of the target element
        int idx = allActiveSiblings.indexOf(targetElement);
        //re-add it back at the new location using position information
        allActiveSiblings.add(++idx,elementToMove);
        //now reset the sorting order from element 
        for (int i = idx; i < allActiveSiblings.size(); i++) {
        	allActiveSiblings.get(i).setSortingOrder(++targetSortingOrder);
		}
    }

	/**
	 * Changes the sorting order of elementToMove to one above that of the targetElement.
	 * All elements below targetElement get their sorting order incremented by 
	 * one
	 * @param elementToMove The {@link LocalElement} which to be moved below
	 * @param targetElement The {@link LocalElement} below which to move the elementToMove
	 * @throws Exception if setting of the sorting order fails
	 */
    public static synchronized void moveToAbove(LocalElement elementToMove, LocalElement targetElement) throws Exception {
        int targetSortingOrder = elementToMove.getSortingOrder();
        //get all the active siblings 
        LinkedList<LocalElement> allActiveSiblings = new LinkedList<LocalElement>(elementToMove.getParentParticle().getElements(true));
        int idx = allActiveSiblings.indexOf(targetElement);
        //remove the element to be moved
        allActiveSiblings.remove(elementToMove);
        //re-add it back at the new location using position information
        allActiveSiblings.add(idx,elementToMove);
        //now reset the sorting order from element 
        for (int i = idx + 1; i < allActiveSiblings.size(); i++) {
        	allActiveSiblings.get(i).setSortingOrder(targetSortingOrder++);
		}
    }

    /**
     * Exchanges the sort order of the move candidate with the element above it  
     * @param moveCandidate The {@link LocalElement} which is be moved up
     * @throws Exception if setting of the sorting order fails
     */
    public static synchronized void moveUp(LocalElement moveCandidate) throws Exception {
        if (null == moveCandidate || null == moveCandidate.getParent()){
            return;
        }
        //get the owning particle
        LocalParticle particle = moveCandidate.getParentParticle();
        //find the index of the move candidate
        int index = particle.indexOf(moveCandidate);
        //continue iff the moveCandidate has an element above it (viz. its index is not 0)
        if (index > 0) {
        	//get the element above the move candidate
            LocalElement aboveElement = particle.getElement(true, index - 1);
            //get the sort order of that element
            int targetSortOrder = aboveElement.getSortingOrder();
            //get current sort order of the move candidate
            int currentSortOrder = moveCandidate.getSortingOrder();
            //set moveCandidate's sort order to target value 
            moveCandidate.setSortingOrder(targetSortOrder);
            //set the above element's sort order to that of move candidate
            aboveElement.setSortingOrder(currentSortOrder);
        }

    }

    /**
     * Exchanges the sort order of the move candidate with the element below it  
     * @param moveCandidate The {@link LocalElement} which is be moved down
     * @throws Exception if setting of the sorting order fails
     */    
    public static synchronized void moveDown(LocalElement moveCandidate) throws Exception {
        if (null == moveCandidate || null == moveCandidate.getParent()){
            return;
        }
        //get the owning particle
        LocalParticle particle = moveCandidate.getParentParticle();
        //find the index of the move candidate
        int index = particle.indexOf(moveCandidate);
        //continue iff the moveCandidate has an element above it (viz. its index is not 0)
        if (index + 1 < particle.getActiveElementCount()) {
        	//get the element below the move candidate
            LocalElement belowElement = particle.getElement(true, index + 1);
            //get the sort order of that element
            int targetSortOrder = belowElement.getSortingOrder();
            //get current sort order of the move candidate
            int currentSortOrder = moveCandidate.getSortingOrder();
            //set moveCandidate's sort order to target value 
            moveCandidate.setSortingOrder(targetSortOrder);
            //set the above element's sort order to that of move candidate
            belowElement.setSortingOrder(currentSortOrder);
        }        
    }

    /**
     * Returns whether the given localElement may move to the position occupied
     * by this element.
     * 
     * This is mainly used to check whether a drag-n-drop is allowed on this
     * element.
     * 
     * @param moveCandidate
     * @return
     */
    public static synchronized boolean moveToHereAllowed(LocalElement moveCandidate, LocalElement targetElement) throws Exception {

        /*
         * There is no use to allow an element to move to itself
         */
        if (moveCandidate == targetElement) {
            return false;
        }

        /*
         * Only elements within the same particle are allowed to move to each
         * other
         */
        if (moveCandidate.getParentParticle() == targetElement.getParentParticle()) {
            return false;
        }

        /*
         * Additionally the parents must be the same too.
         */
        if (moveCandidate.getParent().getName() == targetElement.getParent().getName()) {
            return false;
        }

        return true;
    }
}
