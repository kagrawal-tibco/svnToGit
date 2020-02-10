package com.tibco.cep.query.model.visitor.filter;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 9, 2007
 * Time: 9:29:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class CollectionItems<T> extends ArrayList<T> implements Items {


    /**
     * eval block for each
     * @param aBlock
     */
    public void enumerate(UnaryProcedure aBlock) throws Exception {
        Iterator at = this.iterator();
        while (at.hasNext()) {
            Object each = at.next();
            aBlock.run(each);
        }
    }

    /**
     * increment count for each true
     * @param aBlock
     * @return  int
     */
    public int count(UnaryPredicate aBlock) {
        int count = 0;
        Iterator at = this.iterator();
        while (at.hasNext()) {
            Object each = at.next();
            if (aBlock.is(each))
                count++;
        }
        return count;
    }

    /**
     * remove item for each block that answer true
     * @param aBlock
     */
    public void remove(UnaryPredicate aBlock) {
        Iterator at = this.iterator();
        while (at.hasNext()) {
            Object each = at.next();
            if (aBlock.is(each))
                this.remove(each);
        }
    }

    /**
     * detect first for block answers true
     * @param aBlock
     * @return  Object
     */
    public Object detect(UnaryPredicate aBlock) {
        Iterator at = this.iterator();
        while (at.hasNext()) {
            Object each = at.next();
            if (aBlock.is(each))
                return each;
        }
        return null;  // indicate none-detected
    }

    /**
     * inject value into block with each item
     * @param value
     * @param aBlock
     * @return  Object
     */
    public Object inject(Object value, BinaryFunction aBlock) {
        Iterator at = this.iterator();
        while (at.hasNext()) {
            Object each = at.next();
            aBlock.eval(value, each);
        }
        return null;
    }

    /**
     * answer new collection for each true
     * @param aBlock
     * @return Items
     */
    public Items select(UnaryPredicate aBlock) {
        CollectionItems c = new CollectionItems();
        Iterator at = this .iterator();
        while (at.hasNext()) {
            Object each = at.next();
            if (aBlock.is(each))
                c.add(each);
        }
        return c;
    }

    /**
     * answer new collection for each false
     * @param aBlock
     * @return Items
     */
    public Items reject(UnaryPredicate aBlock) {
        CollectionItems c = new CollectionItems();
        Iterator at = this .iterator();
        while (at.hasNext()) {
            Object each = at.next();
            if (!aBlock.is(each))
                c.add(each);
        }
        return c;
    }

    /**
     * answer new collection of non-null results
     * @param aBlock
     * @return Items
     */
    public Items collect(UnaryFunction aBlock) {
        CollectionItems c = new CollectionItems();
        Iterator at = this .iterator();
        while (at.hasNext()) {
            Object each = at.next();
            if (aBlock.eval(each) != null)
                c.add(each);
        }
        return c;
    }
}
