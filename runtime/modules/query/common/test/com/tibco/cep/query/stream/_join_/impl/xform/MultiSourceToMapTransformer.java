package com.tibco.cep.query.stream._join_.impl.xform;

import com.tibco.cep.query.stream._join_.node.xform.SourceToMapTransformer;
import com.tibco.cep.query.stream.join.JoinedTuple;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
* Author: Ashwin Jayaprakash Date: Oct 21, 2008 Time: 3:48:19 PM
*/
public class MultiSourceToMapTransformer implements SourceToMapTransformer<Tuple> {
    protected final String[] keys;

    protected int[] keyPositionsInMap;

    /**
     * Expects the <code>input</code> in {@link #copyTransform(com.tibco.cep.query.stream.tuple.Tuple,
     * com.tibco.cep.query.stream.util.FixedKeyHashMap)} to be a {@link
     * com.tibco.cep.query.stream.join.JoinedTuple}.
     *
     * @param keys
     */
    public MultiSourceToMapTransformer(String... keys) {
        this.keys = keys;
    }

    public String[] getKeys() {
        return keys;
    }

    public void init(FixedKeyHashMap<String, Tuple> map) {
        keyPositionsInMap = new int[keys.length];

        for (int i = 0; i < keys.length; i++) {
            keyPositionsInMap[i] = map.getInternalPosition(keys[i]);
        }
    }

    public void copyTransform(Tuple input, FixedKeyHashMap<String, Tuple> map) {
        JoinedTuple joinedInput = (JoinedTuple) input;

        for (int i = 0; i < keyPositionsInMap.length; i++) {
            Tuple joinMember = joinedInput.getColumn(i);
            int positionInMap = keyPositionsInMap[i];

            map.setValueAtInternalPosition(joinMember, positionInMap);
        }
    }
}