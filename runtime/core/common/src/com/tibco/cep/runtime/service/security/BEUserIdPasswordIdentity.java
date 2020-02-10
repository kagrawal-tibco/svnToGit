package com.tibco.cep.runtime.service.security;

import com.tibco.be.util.config.sharedresources.id.Identity;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.security.AXSecurityException;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * @author ishaan
 * @version May 3, 2006, 6:06:40 PM
 */
public class BEUserIdPasswordIdentity extends BEIdentity {


    private static final ExpandedName USER_NAME = ExpandedName.makeName("username");
    private static final ExpandedName PASSWORD = ExpandedName.makeName("password");

    private String username;
    private String password;

    public BEUserIdPasswordIdentity(
            XiNode elementNode,
            GlobalVariables globalVariables)
            throws AXSecurityException
    {
        super("usernamePassword");
        this.passwordDecrypted = true;
        this.username = globalVariables.substituteVariables(XiChild.getString(elementNode, USER_NAME)).toString();
        this.password = BEIdentityUtilities.decryptPassword(
                globalVariables.substituteVariables(XiChild.getString(elementNode, PASSWORD)).toString()
        );
    }


    public BEUserIdPasswordIdentity(
            Identity identity,
            GlobalVariables globalVariables)
            throws AXSecurityException
    {
        super("usernamePassword");
        this.passwordDecrypted = true;
        this.username = globalVariables.substituteVariables(identity.getUsername()).toString();
        this.password = globalVariables.substituteVariables(identity.getPassword()).toString();
    }


    public String getUserId ()
    {
        return this.username;
    }


    public String getPassword ()
    {
        return this.password;
    }

}
