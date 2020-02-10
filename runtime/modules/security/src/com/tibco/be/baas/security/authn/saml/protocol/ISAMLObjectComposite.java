package com.tibco.be.baas.security.authn.saml.protocol;



import java.util.Map;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;

/**
 * Created by IntelliJ IDEA.
 * User: aditya
 * Date: 19/9/11
 * Time: 11:16 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ISAMLObjectComposite {

    /**
     * Build a SAML object based on attribute names and children of that
     * object.
     * <p>
     *     All {@link ISAMLObject}s can be built using individual builders.
     * </p>
     * @param objectAttribues
     * @return
     */
    ISAMLObject composeSAMLObject(Map<String, Object> objectAttribues) throws Exception;
  
}
