package com.tibco.cep.kernel.core.base.tuple.tabletable;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Nov 2, 2009
 * Time: 8:55:28 PM
 * To change this template use File | Settings | File Templates.
 */

//purpose of this class is to define next_migrate so that its impls can extends iterators that already define next to return a different type
public interface MigrationIterator<T>
{
    public static final MigrationIterator NULL_ITER = new MigrationIterator(){

        public boolean hasNext() {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Object next_migrate() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    };
    
    boolean hasNext();
    T next_migrate();
}
