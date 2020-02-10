package com.tibco.cep.query.stream.impl.rete.integ.filter.coherence;

import java.util.Comparator;

/*
* Author: Karthikeyan Subramanian / Date: Apr 7, 2010 / Time: 11:27:09 AM
*/
public class IndexInfoComparator implements Comparator<IndexInfo> {

    @Override
    public int compare(IndexInfo index_1, IndexInfo index_2) {        
        if(index_1.isOrdered() == index_2.isOrdered()) {
            return (int)(index_1.getSelectivity() - index_2.getSelectivity());
        } else {
            if(index_1.isOrdered() == true) {
                return 1;
            }
            return -1;
        }
    }
}
