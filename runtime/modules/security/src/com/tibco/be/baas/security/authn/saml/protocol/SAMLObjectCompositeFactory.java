package com.tibco.be.baas.security.authn.saml.protocol;

import java.lang.reflect.Constructor;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;
import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType;
import com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType;
import com.tibco.be.baas.security.authn.saml.protocol.impl.AssertionTypeComposite;
import com.tibco.be.baas.security.authn.saml.protocol.impl.AuthnRequestTypeComposite;
import com.tibco.be.baas.security.authn.saml.protocol.impl.SAMLResponseTypeComposite;

/**
 * Created by IntelliJ IDEA.
 * User: aditya
 * Date: 19/9/11
 * Time: 12:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class SAMLObjectCompositeFactory {

    /**
     * Factory for building any {@link ISAMLObject}
     * @param clazz
     * @param <S>
     * @param <B>
     * @return
     * @throws Exception
     */
    public static <S extends ISAMLObject, B extends ISAMLObjectComposite> B getSAMLObjectComposite(Class<? extends ISAMLObject> clazz) throws Exception {
        if (clazz.isAssignableFrom(AssertionType.class)) {
            Constructor<AssertionTypeComposite> constructor = AssertionTypeComposite.class.getConstructor();
            return (B)constructor.newInstance();
        } else if (clazz.isAssignableFrom(ResponseType.class)) {
            Constructor<SAMLResponseTypeComposite> constructor = SAMLResponseTypeComposite.class.getConstructor();
            return (B)constructor.newInstance();
        } else if (clazz.isAssignableFrom(AuthnRequestType.class)) {
            Constructor<AuthnRequestTypeComposite> constructor = AuthnRequestTypeComposite.class.getConstructor();
            return (B)constructor.newInstance();
        }
        //TODO Add other types
        return null;
    }
}
