package com.tibco.be.bemm.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.bemm.functions.ComparatorFacade;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.management.exception.BEMMInvalidAccessException;
import com.tibco.cep.runtime.service.management.exception.BEMMInvalidUserRoleException;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnUtil;
import com.tibco.cep.runtime.service.management.jmx.principals.MMJmxPrincipal;
import com.tibco.net.mime.Base64Codec;
import com.tibco.util.StringUtilities;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@com.tibco.be.model.functions.BEPackage(
		catalog = "BEMM",
        category = "BEMM.util",
        synopsis = "Utility Functions used with the Enterprise Monitoring and Management Application")
public class UtilFunctions {

    private static Logger logger = LogManagerFactory.getLogManager().getLogger(UtilFunctions.class);
    private static UtilFunctions instance;

    private UtilFunctions() {
    }

    public static synchronized UtilFunctions getInstance(){
        if (instance == null){
            instance = new UtilFunctions();
        }
        return instance;
    }

	@com.tibco.be.model.functions.BEFunction(
        name = "join",
        synopsis = "Creates a String using the $1joiner$1 to join the elements of the Object[]",
        signature = "void join(Object[] array,String joiner)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "array", type = "Object[]", desc = "The array to be joined"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "joiner", type = "String", desc = "The joining string")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a String using the $1joiner$1 to join the elements of the Object[]",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )

	public static String join(Object[] array,String joiner){
		String[] sArray = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			Object obj = array[i];
			if (obj != null){
				sArray[i] = obj.toString();
			}
			else {
				sArray[i] = null;
			}
		}
		return StringUtilities.join(sArray, joiner);
	}

    public static String decodeBase64Pwd(String encodedPwd) {
        try {
            return Base64Codec.decodeBase64(encodedPwd, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.ERROR,"Password encoded with unsupported coding scheme. Proceeding with encoded password.");
        }
        return encodedPwd;
    }

    /** Iterates over every entry of the String array and performs URL decoding
     * @throws UnsupportedEncodingException - If the encoding scheme is not supported */
    public static void urlDecodeArray(String[] arrayToDecode, String enc) throws UnsupportedEncodingException {
        if (arrayToDecode == null)
            return;

        for (int i = 0; i < arrayToDecode.length; i++) {
            arrayToDecode[i] = URLDecoder.decode(arrayToDecode[i], enc);
        }
    }

    //Exposed as a catalog function to be used by the MM Studio project
    public static String urlDecodeString(String strToDecode, String enc) {
        try {
            return URLDecoder.decode(strToDecode, enc);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    /** Iterates over the exception stack until it finds a cause matching the class specified.
     * @return Exception message if exception is instance of exceptClass. Returns null otherwise*/
    public static String getClassExceptionMsg(Throwable exception, Class exceptClass) {
        if (exception == null)
            return null;

        if (exceptClass.isInstance(exception))  {
            return exception.getMessage();
        }

        return getClassExceptionMsg(exception.getCause(), exceptClass);
    }


    /** Verifies if the user is authorized to execute the intended operation.
     *  @param uiUser Username used to logon into the MM UI
     *  @param uiPass Password used to logon into the MM UI
     *  @return true if the user is authorized to execute the operation
     *  @throws com.tibco.cep.runtime.service.management.exception.BEMMInvalidAccessException if the user is not authorized to execute the operation
     *  @throws com.tibco.cep.runtime.service.management.exception.BEMMInvalidUserRoleException if the user is associated with an invalid role
     *  @throws java.security.GeneralSecurityException if LoginContext cannot be created */
    public boolean isAuthorized(String uiUser, String uiPass) throws BEMMInvalidAccessException,
            BEMMInvalidUserRoleException, GeneralSecurityException {

        //In case the user does not specify the JAAS Config File Location in the CDD file,
        //use the default location under BE_HOME/mm/config/jaas-config.config
        JMXConnUtil.setJAASConfigFile();

		LoginContext lc;
		try {
			lc = new LoginContext(JMXConnUtil.getLoginModuleConfig("JMXAuthenticator"),
                                new MMActionsCallbackHandler(uiUser, uiPass));
		} catch (LoginException le) {
			throw new GeneralSecurityException("could not create the login context required for authentication", le);
		}
		lc.login();

        Set<Principal> principals = lc.getSubject().getPrincipals();

        for(Principal userPrincipal : principals) {
            if (userPrincipal instanceof MMJmxPrincipal) {
                String userName = userPrincipal.getName();

                //if it is an authenticated system operation, authorize it.
                if( ((MMJmxPrincipal) userPrincipal).isAuthenticSysOperation() ) {
                    logger.log(Level.DEBUG, "Authorized authentic system operation");
                    return true;
                }

                // If security is not enabled, allow any kind of operation without checking authorization level.
                 if ( !((MMJmxPrincipal) userPrincipal).isSecurityEnabled() ) {
                    logger.log(Level.DEBUG, "Operation authorized because security is not enabled for user: ", userName);
                    return true;
                }

                //else security is enabled, so check if the user is authorized to execute the operation he pretends
                //allow MM_ADMINISTRATORS to do every operation.
                //restrict MM_USERS to read-only operations
                logger.log(Level.DEBUG,"MBean proxy is checking access for user %s", userName);

                if (((MMJmxPrincipal) userPrincipal).isMMAdministrator()) {
                    logger.log(Level.DEBUG, "Operation authorized for user: ", userName);
                    return true;
                }
                else if (((MMJmxPrincipal) userPrincipal).isMMUser()) {
                    throw new BEMMInvalidAccessException("Only users with with [MM_ADMINISTRATOR] privileges are " +
                            "authorized to execute 'invoke' operations. User [" + uiUser + "] is member of the " +
                            "following user roles: " + ((MMJmxPrincipal) userPrincipal).getRoles().toString().toUpperCase() );

                } else { //invalid user role, throws exception.
                    throw new BEMMInvalidUserRoleException( "User '" + uiUser + "' has an invalid user roles list: " +
                            ((MMJmxPrincipal) userPrincipal).getRoles().toString().toUpperCase() + ". Valid roles are: " +
                             ((MMJmxPrincipal) userPrincipal).getValidRoles().toString().toUpperCase() );
                }
            }
            //else do nothing for now
        }
        return true;
	}
    
    @com.tibco.be.model.functions.BEFunction(
        name = "sortList",
        synopsis = "Sorts a list using the provided spec. The list should contains either Object[]'s \nor SimpleEvents. The sorting spec is an array where each element is a \ncomma-separated String. The first part of which is the index or property name \nand the second part of which whether to sort ascending (true) or descending (false)\nExamples of sort spec \n<ul>\n<li>propertyNameA,true - indicates to sort a list of SimpleEvents by propertyNameA \nin the natural ascending order of propertyNameA.\n<li>1,true - indicates to sort a list of Object[] by value in 1st index \nin the natural ascending order of value. \n</ul>",
        signature = "void sortList(Object list, String[] sortSpec)",
        params = {
        		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "list", type = "Object", desc = "The list which is to be sorted.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sorts a list using the provided spec.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
	)
    public static final void sortList(Object list, String[] sortSpec){
    	if (list instanceof List<?>){
    		Collections.sort((List<?>) list, new ComparatorFacade(sortSpec));
    	}
    }
}
