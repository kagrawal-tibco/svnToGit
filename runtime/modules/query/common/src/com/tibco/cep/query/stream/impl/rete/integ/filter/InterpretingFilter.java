package com.tibco.cep.query.stream.impl.rete.integ.filter;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;
import com.tibco.cep.query.stream.impl.rete.integ.CacheServerAgentService;
import com.tibco.cep.query.stream.impl.rete.integ.standalone.QueryObjectManager;
import com.tibco.cep.query.stream.impl.rete.integ.standalone.QueryTypeInfo;
import com.tibco.cep.query.stream.impl.rete.integ.standalone.QueryWorkingMemory;
import com.tibco.cep.query.stream.tuple.LiteTuple;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.FilterContext;

/*
* Author: Ashwin Jayaprakash Date: Jun 16, 2008 Time: 5:29:28 PM
*/
public class InterpretingFilter implements Filter, Externalizable {
    protected ReteEntityFilter actualFilter;

    protected GlobalContext globalContext;

    protected QueryContext queryContext;

    protected transient Object[] dummyTupleInnerArray;

    protected transient LiteTuple dummyTuple;

    protected transient QueryObjectManager queryObjectManager;

    protected transient Logger logger;

    protected transient QueryTypeInfo queryTypeInfo;

    protected transient ThreadPoolExecutor threadPoolExecutor;

    public InterpretingFilter(ReteEntityFilter actualFilter, GlobalContext globalContext, QueryContext queryContext) {
        this.actualFilter = actualFilter;
        this.dummyTupleInnerArray = new Object[1];
        this.dummyTuple = new LiteTuple(0, dummyTupleInnerArray);

        this.globalContext = globalContext;
        this.queryContext = queryContext;
    }

    public InterpretingFilter() {
    }

    @Override
    public boolean evaluate(Object obj, FilterContext context) {
        if (obj instanceof Concept) {
            immerse((Concept) obj);
        }

        dummyTupleInnerArray[0] = obj;

        boolean retVal = actualFilter.allow(globalContext, queryContext, dummyTuple);

        //Clear it.
        dummyTupleInnerArray[0] = null;

        return retVal;
    }

    protected void immerse(final Concept concept) {
        //Init QOM.
        if (queryObjectManager == null) {
            CacheServerAgentService singleton =
                    CacheServerAgentService.getCacheServerAgentService();

            //Nothing can be done.
            if (singleton == null) {
                return;
            }

            //-------------------

            QueryWorkingMemory queryWorkingMemory = singleton.getQueryWorkingMemory();

            queryObjectManager = queryWorkingMemory.getObjectManager();
            logger = LogManagerFactory.getLogManager().getLogger(getClass());
            threadPoolExecutor = singleton.getThreadPoolExecutor();
        }

        //-------------------

        String className = concept.getClass().getName();
        final long id = concept.getId();

        try {
            if (queryTypeInfo == null) {
                queryTypeInfo = queryObjectManager.getSource(className);
            }

            //Still null?!
            if (queryTypeInfo == null) {
                logger.log(Level.INFO,
                        "No source found for Class [" + className + "] and Id [" + id +
                                "] in the special OM");

                return;
            }

            //-------------------

            /*
            This operation must be done in a separate thread because tangosol will otherwise
            throw an exception when eventually making a call out to another NamedCache:

            Cache:  Exception in get(8), Retry num= 1: poll() is a blocking call and cannot be called on the Service thread
            [DistributedCache] - com.tangosol.util.AssertionException: poll() is a blocking call and cannot be called on the Service thread
            */
            Future job = threadPoolExecutor.submit(new Runnable() {
                public void run() {
                    /*
                    Immerse top of the Contained/Referenced Concept chain and the remaining will
                    work automagically.
                    */
                    queryTypeInfo.handleDownloadedEntity(concept, id);
                }
            });

            //Wait for completion.
            job.get();
        }
        catch (Throwable t) {
            logger.log(Level.ERROR,
                    "Error occurred while immersing Concept [" + className + "] and Id [" + id +
                            "] in the special OM", t);
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(globalContext);
        out.writeObject(queryContext);
        out.writeObject(actualFilter);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        dummyTupleInnerArray = new Object[1];
        dummyTuple = new LiteTuple(0, dummyTupleInnerArray);

        globalContext = (GlobalContext) in.readObject();
        queryContext = (QueryContext) in.readObject();
        actualFilter = (ReteEntityFilter) in.readObject();
    }
}
