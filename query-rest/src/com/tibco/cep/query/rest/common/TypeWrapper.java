package com.tibco.cep.query.rest.common;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 4/2/14
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TypeWrapper<T> {

  Object convert(Iterator resultIterator) throws Exception;

}
