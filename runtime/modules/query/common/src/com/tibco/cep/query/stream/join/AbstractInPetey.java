package com.tibco.cep.query.stream.join;

import com.tibco.cep.query.stream.expression.TupleValueExtractor;

/*
 * Author: Ashwin Jayaprakash Date: Oct 16, 2007 Time: 4:28:44 PM
 */

public abstract class AbstractInPetey extends AbstractPetey {
    protected final TupleValueExtractor outerTupleValueExtractor;

    protected final TupleValueExtractor innerTupleValueExtractor;

    public AbstractInPetey(InPeteyInfo inPeteyInfo) {
        super(inPeteyInfo);

        this.outerTupleValueExtractor = inPeteyInfo.getOuterTupleValueExtractor();
        this.innerTupleValueExtractor = inPeteyInfo.getInnerTupleValueExtractor();
    }

    // ----------

    /**
     * Builder class.
     */
    public static class InPeteyInfo extends PeteyInfo {
        protected TupleValueExtractor outerTupleValueExtractor;

        protected TupleValueExtractor innerTupleValueExtractor;

        public TupleValueExtractor getInnerTupleValueExtractor() {
            return innerTupleValueExtractor;
        }

        public void setInnerTupleValueExtractor(TupleValueExtractor innerTupleValueExtractor) {
            this.innerTupleValueExtractor = innerTupleValueExtractor;
        }

        public TupleValueExtractor getOuterTupleValueExtractor() {
            return outerTupleValueExtractor;
        }

        public void setOuterTupleValueExtractor(TupleValueExtractor outerTupleValueExtractor) {
            this.outerTupleValueExtractor = outerTupleValueExtractor;
        }
    }
}
