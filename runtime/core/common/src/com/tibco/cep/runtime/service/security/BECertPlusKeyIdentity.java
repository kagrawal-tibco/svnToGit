package com.tibco.cep.runtime.service.security;


import com.tibco.be.util.config.sharedresources.id.Identity;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.objectrepo.NotFoundException;
import com.tibco.security.AXSecurityException;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;


/**
 * @author ishaan
 * @version May 3, 2006, 6:07:02 PM
 */
public class BECertPlusKeyIdentity extends BEIdentity {
    protected String strCertUrl = null;
    protected String strKeyUrl = null;
    protected String strPassword = null;

    protected XiNode elementNode = null;



    public BECertPlusKeyIdentity(final String strObjectType,
                                 final XiNode elementNode) {
        super(strObjectType);
        this.elementNode = elementNode;

    }


    public BECertPlusKeyIdentity(
            Identity identity,
            GlobalVariables globalVariables)
            throws AXSecurityException
    {
        super("certPlusKeyURL");
        this.elementNode = null;
        this.passwordDecrypted = true;
        String url = globalVariables.substituteVariables(identity.getCertUrl()).toString();
        this.strCertUrl = ((null != url) && url.startsWith("file://"))
                ? url.substring("file://".length())
                : url;
        url = globalVariables.substituteVariables(identity.getPrivateKeyUrl()).toString();
        this.strKeyUrl = ((null != url) && url.startsWith("file://"))
                ? url.substring("file://".length())
                : url;
        this.strPassword = BEIdentityUtilities.decryptPassword(
                globalVariables.substituteVariables(identity.getPassPhrase()).toString());
    }


    public String getCertUrl() {
        return strCertUrl;
    }

    public String getKeyUrl() {
        return strKeyUrl;
    }

    public String getPassword() {
        return strPassword;
    }



    //TODO - remove the legacy horrors below




    public void setDecryptedPassword(final String strNewPassword) {
        strPassword = strNewPassword;
        setPasswordDecrypted(true);
    }

    // Kai: Merged from revision 17873 for JMS SSL client identity
    public void substituteGVars(GlobalVariables gv)
            throws NotFoundException
    {
        if (null == this.elementNode) {
            return;
        }

        strCertUrl = XiChild.getString(elementNode, ExpandedName.makeName("certUrl"));
        if (strCertUrl != null && strCertUrl.length() > 0) {
            strCertUrl = gv.substituteVariables(strCertUrl).toString();
        }
        if ((strCertUrl != null) && (strCertUrl.length() > 0) && (strCertUrl.startsWith("file://"))) {
            strCertUrl = strCertUrl.substring(7);
        }
        strKeyUrl = XiChild.getString(elementNode, ExpandedName.makeName("privateKeyUrl"));
        if (strKeyUrl != null && strKeyUrl.length() > 0) {
            strKeyUrl = gv.substituteVariables(strKeyUrl).toString();
        }
        if ((strKeyUrl != null) && (strKeyUrl.length() > 0) && (strKeyUrl.startsWith("file://"))) {
            strKeyUrl = strKeyUrl.substring(7);
        }
        strPassword = XiChild.getString(elementNode, ExpandedName.makeName("passPhrase"));
        if (strPassword != null && strPassword.length() > 0) {
            strPassword = gv.substituteVariables(strPassword).toString();
        }
    }

}
