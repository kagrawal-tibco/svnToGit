package com.tibco.cep.query.stream.transform;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.join.JoinedStream;
import com.tibco.cep.query.stream.misc.IdGenerator;
import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.impl.property.simple.*;

import java.lang.reflect.Array;
import java.util.*;

/*
 * Author: Ashwin Jayaprakash Date: Nov 6, 2007 Time: 11:01:07 AM
 */

/**
 * Stream to transform one Tuple's components to another Tuple. <p/> Ex: Join Stream transformation.
 * Let's say the Join has 2 source Tuples/Streams A and B. The result of the {@link JoinedStream} is
 * another Tuple that has the Tuples of Streams A and B and not the contents of the Tuples from A
 * and B. So, this Stream would extract the elements from them and produce a Tuple with the contents
 * that are required by the Select clause. </p> <p/> Ex: Grouped-Aggregation Stream transformation.
 * The Aggregated Stream produces a raw Stream which has to be filtered (optional, only when
 * "Having" is used) and then transformed to project the Select elements. </p>
 */
public class TransformedStream extends AbstractStream {
    
    protected final TransformInfo transformInfo;

    protected final TupleValueExtractor[] extractors;

    protected final HashMap<Number, Tuple> sourceIdAndTransformedTuples;

    private AppendOnlyQueue<Tuple> sessionAdds;

    private AppendOnlyQueue<Tuple> sessionDeletes;

    private IdGenerator idGenerator;

    public TransformedStream(Stream source, ResourceId id, TupleInfo outputInfo,
                             TransformInfo transformInfo) {
        super(source, id, outputInfo);

        this.transformInfo = transformInfo;

        this.extractors = new TupleValueExtractor[transformInfo.getItemInfos().size()];
        int i = 0;
        for (TransformItemInfo itemInfo : transformInfo.getItemInfos().values()) {
            this.extractors[i++] = itemInfo.getExtractor();
        }

        if (sourceProducesDStream) {
            this.sourceIdAndTransformedTuples = new HashMap<Number, Tuple>();
        }
        else {
            this.sourceIdAndTransformedTuples = null;
        }
    }

    public TransformInfo getTransformInfo() {
        return transformInfo;
    }

    // ----------

    @SuppressWarnings("unchecked")
    @Override
    protected void doProcessing(Context context) throws Exception {
        super.doProcessing(context);

        final GlobalContext gc = context.getGlobalContext();
        final DefaultQueryContext qc = context.getQueryContext();
        final LocalContext lc = context.getLocalContext();

        if (sessionAdds == null) {
            idGenerator = qc.getIdGenerator();
            sessionDeletes = new AppendOnlyQueue<Tuple>(qc.getArrayPool());
            sessionAdds = new AppendOnlyQueue<Tuple>(qc.getArrayPool());
        }

        final TupleValueExtractor[] cachedExtractors = extractors;
        final HashMap<Number, Tuple> cachedSourceIdAndTransformedTuples =
                sourceIdAndTransformedTuples;
        final AppendOnlyQueue<Tuple> cachedSessionAdds = sessionAdds;
        final AppendOnlyQueue<Tuple> cachedSessionDeletes = sessionDeletes;
        final TupleInfo cachedOutputInfo = outputInfo;

        Collection<? extends Tuple> deadTuples = lc.getDeadTuples();
        if (deadTuples != null) {
            for (Tuple tuple : deadTuples) {
                Tuple resultTuple = cachedSourceIdAndTransformedTuples.remove(tuple.getId());
                cachedSessionDeletes.add(resultTuple);

                if (Flags.TRACK_TUPLE_REFS) {
                    // End of life for the original Tuple.
                    tuple.decrementRefCount();
                }
            }

            localContext.setDeadTuples(cachedSessionDeletes);
        }

        Collection<? extends Tuple> newTuples = lc.getNewTuples();
        if (newTuples != null) {
            for (Tuple tuple : newTuples) {
                Object[] columns = new Object[cachedExtractors.length];
                for (int i = 0; i < cachedExtractors.length; i++) {
                    columns[i] = cachedExtractors[i].extract(gc, qc, tuple);
                    if(columns[i] instanceof PropertyArray) {
                        Class klass = getClass((PropertyArray)columns[i]);
                        PropertyAtom[] properties = ((PropertyArray)columns[i]).toArray(new PropertyAtom[0]);
                        List arrayValues = new ArrayList();
                        for (PropertyAtom property : properties) {
                            Object val = property.getValue();

                            if(val == null){
                                arrayValues.add(null);
                            }
                            else if(property instanceof PropertyAtomContainedConceptSimple && val.getClass().equals(klass)) {
                                // For Contained Concepts, use getContainedConcept to fetch Contained concept.
                                arrayValues.add(((PropertyAtomContainedConceptSimple) property).getContainedConcept());
                            } else if(property instanceof PropertyAtomConceptReferenceSimple && val.getClass().equals(klass)){
                                // For Ref Concepts, use getConcept to fetch Referenced concept.
                                arrayValues.add(((PropertyAtomConceptReferenceSimple) property).getConcept());
                            } else {
                                // For all primitives, use getValue to fetch contained value.
                                arrayValues.add(val);
                            }
                        }
                        // Use an array of type "klass" instead of generic Concept array as
                        // some users may use Concept[] in the code instead of directly using ConceptClass like PurchaseOrder[]
                        if(columns[i] instanceof PropertyArrayConceptReferenceSimple || columns[i] instanceof PropertyArrayContainedConceptSimple) {
                            Concept[] array = (klass == null) ? new Concept[arrayValues.size()] : (Concept[]) Array.newInstance(klass, arrayValues.size());
                            int j = 0;
                            for (Object arrayValue : arrayValues) {
                                array[j++] = (Concept)arrayValue;
                            }
                            columns[i] = array;
                        } else {
                            // For all primitives, use Object array.
                            Object[] array = (klass == null) ? new Object[arrayValues.size()] : (Object[]) Array.newInstance(klass, arrayValues.size());
                            int j = 0;
                            for (Object arrayValue : arrayValues) {
                                array[j++] = arrayValue;
                            }
                            columns[i] = array;
                        }
                    }                    
                }

                Number newTupleId = idGenerator.generateNewId();
                Tuple resultTuple = cachedOutputInfo.createTuple(newTupleId);
                // Will not have any Shared Objects. All references are direct.
                resultTuple.setColumns(columns);

                cachedSessionAdds.add(resultTuple);

                if (cachedSourceIdAndTransformedTuples != null) {
                    cachedSourceIdAndTransformedTuples.put(tuple.getId(), resultTuple);
                }
            }

            localContext.setNewTuples(cachedSessionAdds);
        }
    }

    private Class getClass(PropertyArray propertyArray) {
        if (propertyArray instanceof PropertyArrayConceptReferenceSimple) {
            return ((PropertyArrayConceptReferenceSimple) propertyArray).getType();
        }
        else if (propertyArray instanceof PropertyArrayContainedConceptSimple) {
            return ((PropertyArrayContainedConceptSimple) propertyArray).getType();
        }
        else if (propertyArray instanceof PropertyArrayStringSimple) {
            return String.class;
        }
        else if (propertyArray instanceof PropertyArrayIntSimple) {
            return Integer.class;
        }
        else if (propertyArray instanceof PropertyArrayLongSimple) {
            return Long.class;
        }
        else if (propertyArray instanceof PropertyArrayDoubleSimple) {
            return Double.class;
        }
        else if (propertyArray instanceof PropertyArrayBooleanSimple) {
            return Boolean.class;
        }
        else if (propertyArray instanceof PropertyArrayDateTimeSimple) {
            return GregorianCalendar.class;
        }

        return null;
    }

    @Override
    protected void endProcessing(Context context) {
        super.endProcessing(context);

        if (sessionAdds != null) {
            sessionAdds.clear();
            sessionDeletes.clear();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        if (sessionAdds != null) {
            sessionAdds = null;
            sessionDeletes = null;
        }

        if (sourceIdAndTransformedTuples != null) {
            sourceIdAndTransformedTuples.clear();
        }

        idGenerator = null;
    }
}
