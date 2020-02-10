package com.tibco.cep.runtime.service.security;

import com.tibco.cep.repo.GlobalVariables;
import com.tibco.objectrepo.NotFoundException;
import com.tibco.objectrepo.object.SubstitutionVariableAccessor;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * @author ishaan
 * @version May 3, 2006, 6:04:58 PM
 */
public class BEUrlIdentity extends BEIdentity {
        protected String strUrl = null;
    protected String strFileType = null;
    protected String strPassword = null;

    protected XiNode elementNode = null;

    public static final ExpandedName URL_EXPANDED_NAME = ExpandedName.makeName("url");
    public static final ExpandedName FILETYPE_EXPANDED_NAME = ExpandedName.makeName("fileType");
    public static final ExpandedName PASSPHRASE_EXPANDED_NAME = ExpandedName.makeName("passPhrase");

    public BEUrlIdentity (
        final String strObjectType,
        final XiNode elementNode
    )
    {
        super (strObjectType);
        this.elementNode = elementNode;
    }

    public String getUrl ()
    {
        return strUrl;
    }

    public String getFileType ()
    {
        return strFileType;
    }

    public String getPassword ()
    {
        return strPassword;
    }

    public void setDecryptedPassword (final String strNewPassword)
    {
        strPassword = strNewPassword;
        setPasswordDecrypted(true);
    }

    public void substituteGVars(SubstitutionVariableAccessor va, String providerId)
        throws NotFoundException
    {
        strUrl = XiChild.getString(elementNode, URL_EXPANDED_NAME);
        if (strUrl != null && strUrl.length() > 0) {
            strUrl = va.substitute(strUrl, providerId);
        }
        if ((strUrl != null) && (strUrl.length() > 0) && (strUrl.startsWith("file://"))) {
            strUrl = strUrl.substring(7);
        }
        strFileType = XiChild.getString(elementNode, FILETYPE_EXPANDED_NAME);
        strPassword = XiChild.getString(elementNode, PASSPHRASE_EXPANDED_NAME);
        if (strPassword != null && strPassword.length() > 0) {
            strPassword = va.substitute(strPassword, providerId);
        }
    }

    // Kai: Merged from revision 17873 for JMS SSL client identity
    public void substituteGVars(GlobalVariables gv)
        throws NotFoundException
    {
        strUrl = XiChild.getString(elementNode, URL_EXPANDED_NAME);
        if (strUrl != null && strUrl.length() > 0) {
            final CharSequence cs = gv.substituteVariables(strUrl);
            if (null == cs) {
                strUrl = null;
            } else {
                strUrl = cs.toString();
            }//else
        }
        if ((strUrl != null) && (strUrl.length() > 0) && (strUrl.startsWith("file://"))) {
            strUrl = strUrl.substring(7);
        }
        strFileType = XiChild.getString(elementNode, FILETYPE_EXPANDED_NAME);
        strPassword = XiChild.getString(elementNode, PASSPHRASE_EXPANDED_NAME);
        if (strPassword != null && strPassword.length() > 0) {
            strPassword = gv.substituteVariables(strPassword).toString();
        }
    }
}
