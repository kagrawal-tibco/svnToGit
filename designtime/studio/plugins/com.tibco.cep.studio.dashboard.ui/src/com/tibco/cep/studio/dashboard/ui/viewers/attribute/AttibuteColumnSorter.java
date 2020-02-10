package com.tibco.cep.studio.dashboard.ui.viewers.attribute;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * Sorter for the AttributeViewer that displays items of type
 * <code>ExampleTask</code>. The sorter supports three sort criteria:
 * <p>
 * <code>NAME_COLUMN_IDX</code>: Task description (String)
 * </p>
 * <p>
 * <code>DATATYPE_COLUMN_IDX</code>: Task Owner (String)
 * </p>
 * <p>
 * <code>VALUE_COLUMN_IDX</code>: Task percent completed (int).
 * </p>
 */
public class AttibuteColumnSorter extends ViewerSorter {

    /**
     * Constructor argument values that indicate to sort items by description,
     * owner or percent complete.
     */
    public final static int NAME_COLUMN_IDX = 0;

    public final static int VALUE_COLUMN_IDX = 1;

    public final static int DATATYPE_COLUMN_IDX = 2;


    private static boolean[] sortAscending = new boolean[]{false,false,false};

    // Criteria that the instance uses
    private int criteria;

    /**
     * Creates a resource sorter that will use the given sort criteria.
     * 
     * @param criteria
     *            the sort criterion to use: one of <code>NAME</code> or
     *            <code>PROP_KEY_TYPE</code>
     */
    public AttibuteColumnSorter(int criteria) {
        super();
        this.criteria = criteria;
        AttibuteColumnSorter.sortAscending[criteria] = !AttibuteColumnSorter.sortAscending[criteria];
    }

    /*
     * (non-Javadoc) Method declared on ViewerSorter.
     */
    public int compare(Viewer viewer, Object o1, Object o2) {

        
//        ExampleTask task1 = (ExampleTask) o1;
//        ExampleTask task2 = (ExampleTask) o2;
//
//        switch (criteria) {
//        case NAME_COLUMN_IDX:
//            return compareDescriptions(task1, task2);
//        case VALUE_COLUMN_IDX:
//            return comparePercentComplete(task1, task2);
//        case DATATYPE_COLUMN_IDX:
//            return compareOwners(task1, task2);
//        default:
            return 0;
//        }
    }

    /**
     * Returns a number reflecting the collation order of the given tasks based
     * on the percent completed.
     * 
     * @param task1
     * @param task2
     * @return a negative number if the first element is less than the second
     *         element; the value <code>0</code> if the first element is equal
     *         to the second element; and a positive number if the first element
     *         is greater than the second element
     */
//    private int comparePercentComplete(ExampleTask task1, ExampleTask task2) {
//        
//        if (AttibuteColumnSorter.sortAscending[criteria]) {
//            return collator.compare(task1.getPercentComplete(), task2.getPercentComplete());
//        }
//        
//        return collator.compare(task2.getPercentComplete(), task1.getPercentComplete());
//    }

    /**
     * Returns a number reflecting the collation order of the given tasks based
     * on the description.
     * 
     * @param task1
     *            the first task element to be ordered
     * @param resource2
     *            the second task element to be ordered
     * @return a negative number if the first element is less than the second
     *         element; the value <code>0</code> if the first element is equal
     *         to the second element; and a positive number if the first element
     *         is greater than the second element
     */
//    protected int compareDescriptions(ExampleTask task1, ExampleTask task2) {
//        if (AttibuteColumnSorter.sortAscending[criteria]) {
//            return collator.compare(task1.getDescription(), task2.getDescription());
//        }
//        
//        return collator.compare(task2.getDescription(), task1.getDescription());
//    }

    /**
     * Returns a number reflecting the collation order of the given tasks based
     * on their owner.
     * 
     * @param resource1
     *            the first resource element to be ordered
     * @param resource2
     *            the second resource element to be ordered
     * @return a negative number if the first element is less than the second
     *         element; the value <code>0</code> if the first element is equal
     *         to the second element; and a positive number if the first element
     *         is greater than the second element
     */
//    protected int compareOwners(ExampleTask task1, ExampleTask task2) {
//        if (AttibuteColumnSorter.sortAscending[criteria]) {
//        return collator.compare(task1.getOwner(), task2.getOwner());
//        }
//        return collator.compare(task2.getOwner(), task1.getOwner());
//        }

    /**
     * Returns the sort criteria of this this sorter.
     * 
     * @return the sort criterion
     */
    public int getCriteria() {
        return criteria;
    }
    
    
    
}