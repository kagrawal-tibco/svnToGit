package com.tibco.cep.query.stream._join_.node.xform;

import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
* Author: Ashwin Jayaprakash Date: Aug 6, 2008 Time: 4:18:55 PM
*/
public interface SourceToMapTransformer<I> extends Transformer {
    String[] getKeys();

    void init(FixedKeyHashMap<String, I> map);

    void copyTransform(I input, FixedKeyHashMap<String, I> map);
}
