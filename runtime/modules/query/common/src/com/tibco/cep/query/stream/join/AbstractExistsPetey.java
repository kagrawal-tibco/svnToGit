package com.tibco.cep.query.stream.join;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
 * Author: Ashwin Jayaprakash Date: Oct 16, 2007 Time: 4:36:31 PM
 */

public abstract class AbstractExistsPetey extends AbstractPetey {
    protected final ExpressionEvaluator simpleExpression;

    public AbstractExistsPetey(ExistsPeteyInfo peteyInfo) {
        super(peteyInfo);

        this.simpleExpression = peteyInfo.getExpression();
    }

    public Map<String, Class<? extends Tuple>> populateAndGetAliasAndTypes() {
        HashMap<String, Class<? extends Tuple>> map = new HashMap<String, Class<? extends Tuple>>();

        Map<String, TupleInfo> infos = getOuterTupleAliasAndInfos();
        for (String alias : infos.keySet()) {
            map.put(alias, infos.get(alias).getContainerClass());
        }
        map.put(getInnerTupleAlias(), getInnerTupleInfo().getContainerClass());

        return map;
    }

    // ----------

    /**
     * Builder class.
     */
    public static class ExistsPeteyInfo extends PeteyInfo {
        protected ExpressionEvaluator simpleExpression;

        public ExpressionEvaluator getExpression() {
            return simpleExpression;
        }

        public void setExpression(ExpressionEvaluator simpleExpression) {
            this.simpleExpression = simpleExpression;
        }
    }
}
