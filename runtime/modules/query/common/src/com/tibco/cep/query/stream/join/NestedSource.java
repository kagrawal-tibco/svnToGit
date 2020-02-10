package com.tibco.cep.query.stream.join;

import com.tibco.cep.query.stream.core.Stream;

/*
 * Author: Ashwin Jayaprakash Date: Oct 18, 2007 Time: 6:27:31 PM
 */

public class NestedSource {
    protected final Stream stream;

    protected final AbstractPetey petey;

    public NestedSource(Stream stream, AbstractPetey petey) {
        this.stream = stream;
        this.petey = petey;
    }

    public String getAlias() {
        return petey.getInnerTupleAlias();
    }

    public AbstractPetey getPetey() {
        return petey;
    }

    public Stream getStream() {
        return stream;
    }
}
