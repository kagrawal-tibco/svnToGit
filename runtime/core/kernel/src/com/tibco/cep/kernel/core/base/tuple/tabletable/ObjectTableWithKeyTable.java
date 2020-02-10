package com.tibco.cep.kernel.core.base.tuple.tabletable;

import com.tibco.cep.kernel.core.base.tuple.TableIterator;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Sep 25, 2009
 * Time: 3:04:46 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ObjectTableWithKeyTable extends ObjectTableTable
{
    Handle[] getAllHandles(int key);

    boolean add(Handle objHandle, int key);

    TableIterator keyIterator(int key);

    MigrationIterator<KeyTuple> migrationIterator();
    
    public static class KeyTuple {
        public int key;
        public Handle handle;
        
        public KeyTuple(int _key, Handle _handle) {
            handle = _handle;
            key = _key;
        }
    }
}
