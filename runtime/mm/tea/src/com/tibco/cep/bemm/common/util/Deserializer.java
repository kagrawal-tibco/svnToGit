package com.tibco.cep.bemm.common.util;

/**
 * Created with IntelliJ IDEA. User: mgharat Date: 5/14/14 Time: 4:06 PM To
 * change this template use File | Settings | File Templates.
 */
public interface Deserializer {

	public Object decode(byte[] bytes) throws Exception;

}
