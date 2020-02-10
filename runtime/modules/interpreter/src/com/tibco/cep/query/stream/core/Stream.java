package com.tibco.cep.query.stream.core;

import java.util.Set;

import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.monitor.KnownResource;
import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
* Author: Ashwin Jayaprakash Date: Apr 23, 2008 Time: 4:22:51 PM
*/

/**
 * {@link L} is generic because it gives the implementor freedom to decide what kind of {@link
 * com.tibco.cep.query.stream.core.StreamListener} can be chained as this stream's listener via
 * {@link #setListener(StreamListener)} and {@link #getListener()}. It also specifies what the
 * parent should be via {@link #getSource()}.
 */
public interface Stream<L extends StreamListener> extends StreamListener, KnownResource {
    public LocalContext getLocalContext();

    /**
     * Source stream which can accept this stream as a listener.
     *
     * @return Can be <code>null</code>.
     */
    public Stream<? extends StreamListener> getSource();

    public TupleInfo getOutputInfo();

    /**
     * @return <code>true</code> if this stream produces a delete-stream. If there is no
     *         source-stream, then <code>false</code>.
     */
    public boolean producesDStream();

    /**
     * @return Can be <code>null</code>.
     */
    public L getListener();

    public void setListener(L listener);

    /**
     * @return Can be <code>null</code>.
     */
    public Set<L> getAdditionalListeners();

    public void addAdditionalListener(L anotherListener);
}
