package com.tibco.cep.kernel.core.base.tuple.tabletable;

import java.util.NoSuchElementException;

import com.tibco.cep.kernel.core.base.tuple.TableIterator;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Oct 1, 2009
 * Time: 2:30:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class ZeroLengthIterator implements TableIterator
{
    public boolean hasNext() {
        return false;
    }

    public Handle[] next() {
        throw new NoSuchElementException();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
