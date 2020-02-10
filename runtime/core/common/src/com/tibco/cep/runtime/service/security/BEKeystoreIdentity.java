package com.tibco.cep.runtime.service.security;

import static com.tibco.cep.runtime.service.security.BEIdentityConstants.KS_PASSWORD;
import static com.tibco.cep.runtime.service.security.BEIdentityConstants.KS_TYPE;
import static com.tibco.cep.runtime.service.security.BEIdentityConstants.KS_URL;

import com.tibco.be.util.config.sharedresources.id.Identity;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.objectrepo.NotFoundException;
import com.tibco.security.AXSecurityException;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 26, 2009
 * Time: 11:41:23 AM
 * <!--
 * Add Description of the class here
 * -->
 */
public class BEKeystoreIdentity extends BEIdentity {
    /**
     * Path of the keystore file
     */
    protected String strKeystoreURL = null;

    /**
     * Password for the keystore -> Enrypted Password
     */
    protected String strStorePassword = null;
    /**
     * Type of keystore (PKCS12/JKS etc.)
     */

    protected String strStoreType = null;

    /**
     * Whether password is obfuscated
     */
    protected boolean isPasswordObfuscated;

    protected XiNode elementNode = null;

    protected GlobalVariables globalVariables;

    public BEKeystoreIdentity(final String strObjectType,
                              final XiNode elementNode,
                              final GlobalVariables globalVariables) {
        super(strObjectType);
        this.elementNode = elementNode;
        this.globalVariables = globalVariables;
        populateIdentityParams();
    }


    public BEKeystoreIdentity(
            Identity identity,
            GlobalVariables globalVariables)
            throws AXSecurityException
    {
        super("url");
        this.elementNode = null;
        this.passwordDecrypted = true;
        final String url = globalVariables.substituteVariables(identity.getUrl()).toString();
        this.strKeystoreURL = ((null != url) && url.startsWith("file://"))
                ? url.substring("file://".length())
                : url;
        this.strStorePassword = BEIdentityUtilities.decryptPassword(
                globalVariables.substituteVariables(identity.getPassPhrase()).toString());
        this.strStoreType = globalVariables.substituteVariables(identity.getFileType().toString()).toString();
    }


    public String getStrKeystoreURL() {
        return strKeystoreURL;
    }

    public String getStrStorePassword() {
        return strStorePassword;
    }

    public String getStrStoreType() {
        return strStoreType;
    }



    //TODO - remove the legacy horrors below



    public boolean isPasswordObfuscated() {
        return isPasswordObfuscated;
    }

    private void populateIdentityParams() {
        strKeystoreURL = getSubstitutedStringValue(elementNode, KS_URL);
        strStorePassword = getSubstitutedStringValue(elementNode, KS_PASSWORD);
        checkObfuscatedPass();
        strStoreType = getSubstitutedStringValue(elementNode, KS_TYPE);
    }

    private void checkObfuscatedPass() {
        //We will assume that passwords starting with #! are obfuscated
        if (strStorePassword.startsWith("#!")) {
            isPasswordObfuscated = true;
        }
    }

    private String getSubstitutedStringValue(XiNode node,
                                             ExpandedName name) {
        final CharSequence cs = globalVariables.substituteVariables(XiChild.getString(node, name));
        if (null == cs) {
            return "";
        } else {
            return cs.toString();
        }//else
    }

    public void setDecryptedPassword (final String strNewPassword)
    {
        strStorePassword = strNewPassword;
        setPasswordDecrypted(true);
        this.isPasswordObfuscated = false;
    }


    public void substituteGVars(
            GlobalVariables gv)
            throws NotFoundException {

        if (null == this.elementNode) {
            return;
        }

        strKeystoreURL = XiChild.getString(elementNode, KS_URL);
        if ((strKeystoreURL != null) && (strKeystoreURL.length() > 0)) {
            final CharSequence cs = gv.substituteVariables(strKeystoreURL);
            strKeystoreURL = (null == cs) ? null : cs.toString();
        }
        if ((strKeystoreURL != null) && (strKeystoreURL.length() > 0) && strKeystoreURL.startsWith("file://")) {
            strKeystoreURL = strKeystoreURL.substring(7);
        }

        strStoreType = XiChild.getString(elementNode, KS_TYPE);
        if ((strStoreType != null) && (strStoreType.length() > 0)) {
            final CharSequence cs = gv.substituteVariables(strStoreType);
            strStoreType = (null == cs) ? null : cs.toString();
        }

        strStorePassword = XiChild.getString(elementNode, KS_PASSWORD);
        if (strStorePassword != null && strStorePassword.length() > 0) {
            strStorePassword = gv.substituteVariables(strStorePassword).toString();
        }
    }
}
