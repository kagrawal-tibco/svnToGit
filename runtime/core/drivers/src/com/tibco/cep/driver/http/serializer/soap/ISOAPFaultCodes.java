package com.tibco.cep.driver.http.serializer.soap;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 23, 2009
 * Time: 11:39:28 AM
 * <p>
 * A marker interface to allow for adding fault codes
 * to the out-of-box list of fault codes.
 * The implementation being an {@link Enum} is not
 * extensible using standard inheritance.
 * </p>
 * <p>
 * This interface is intended but not restricted to be implemented
 * by enums. This way non-standard fault codes can be accomodated
 * and these can be based on a mutual understanding
 * between a service provider, and requestor.
 * </p>
 */
public interface ISOAPFaultCodes {

    static final String NS_PREFIX_DELIMITER = ":";

    static final String FAULT_CODE_DELIMITER = ".";

    
}
