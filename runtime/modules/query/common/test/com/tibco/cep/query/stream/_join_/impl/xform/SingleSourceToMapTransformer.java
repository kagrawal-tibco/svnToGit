package com.tibco.cep.query.stream._join_.impl.xform;

import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.query.stream._join_.node.xform.SourceToMapTransformer;

/*
* Author: Ashwin Jayaprakash Date: Oct 21, 2008 Time: 3:48:19 PM
*/
public class SingleSourceToMapTransformer implements SourceToMapTransformer<Tuple> {
    protected final String[] keys;

    protected int keyPositionInMap;

    public SingleSourceToMapTransformer(String key) {
        this.keys = new String[]{key};
    }

    public String[] getKeys() {
        return keys;
    }

    public void init(FixedKeyHashMap<String, Tuple> map) {
        keyPositionInMap = map.getInternalPosition(keys[0]);
    }

    public void copyTransform(Tuple input, FixedKeyHashMap<String, Tuple> map) {
        map.setValueAtInternalPosition(input, keyPositionInMap);
    }
}
