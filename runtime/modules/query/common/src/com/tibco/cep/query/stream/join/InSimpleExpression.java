package com.tibco.cep.query.stream.join;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.query.stream.expression.AbstractExpression;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
 * Author: Ashwin Jayaprakash Date: Oct 26, 2007 Time: 5:27:10 PM
 */

public class InSimpleExpression extends AbstractExpression {
    protected final AbstractInPetey inPetey;

    public InSimpleExpression(AbstractInPetey inPetey) {
        super(createAliases(inPetey));

        this.inPetey = inPetey;
    }

    private static Map<String, Class<? extends Tuple>> createAliases(AbstractInPetey inPetey) {
        HashMap<String, Class<? extends Tuple>> map = new HashMap<String, Class<? extends Tuple>>();

        Map<String, TupleInfo> infos = inPetey.getOuterTupleAliasAndInfos();
        for (String alias : infos.keySet()) {
            map.put(alias, infos.get(alias).getContainerClass());
        }

        return map;
    }

    public AbstractInPetey getInPetey() {
        return inPetey;
    }
}
