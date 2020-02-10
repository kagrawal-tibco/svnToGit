package com.tibco.cep.functions.channel.as;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.as.space.MemberDef;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.security.AuthenticationCallback;
import com.tibco.as.space.security.AuthenticationInfo;
import com.tibco.as.space.security.UserPwdCredential;
import com.tibco.as.space.security.X509V3Credential;
import com.tibco.as.space.security.AuthenticationInfo.Method;
import com.tibco.be.model.functions.BEPackage;
import com.tibco.be.model.functions.FunctionParamDescriptor;

public class ASMemberDefHelper extends MetaspaceHelper {


    public static Object create () {
		return MemberDef.create();
	}


    public static Object getContext (Object memberDef) {
        if (memberDef instanceof MemberDef) {
            return ((MemberDef)memberDef).getContext();
        }
        return null;
	}


	public static String getDataStore (Object memberDef) {
        if (memberDef instanceof MemberDef) {
            return ((MemberDef)memberDef).getDataStore();
        }
		return "";
	}


	public static String getDiscovery (Object memberDef) {
		if (memberDef instanceof MemberDef) {
            return ((MemberDef)memberDef).getDiscovery();
		}
		return "";
	}


    public static String getListen (Object memberDef) {
        if (memberDef instanceof MemberDef) {
			return ((MemberDef) memberDef).getListen();
        }
		return "";
	}


    public static String getMemberName (Object memberDef) {
        if (memberDef instanceof MemberDef) {
            return ((MemberDef)memberDef).getMemberName();
        }
		return "";
	}


    public static String getProcessName (Object memberDef) {
        if (memberDef instanceof MemberDef) {
            return ((MemberDef)memberDef).getProcessName();
        }
		return "";
	}


    public static long getConnectTimeout (Object memberDef) {
        if (memberDef instanceof MemberDef) {
            return ((MemberDef)memberDef).getConnectTimeout();
        }
        return 0;
    }


    public static Object setConnectTimeout (Object memberDef, long timeInMillis) {
        if (memberDef instanceof MemberDef) {
            return ((MemberDef)memberDef).setConnectTimeout(timeInMillis);
        }
        return null;
    }


    public static long getMemberTimeout (Object memberDef) {
        if (memberDef instanceof MemberDef) {
            return ((MemberDef)memberDef).getMemberTimeout();
        }
        return 0;
    }


    public static Object setMemberTimeout (Object memberDef, long timeInMillis) {
        if (memberDef instanceof MemberDef) {
            return ((MemberDef)memberDef).setMemberTimeout(timeInMillis);
        }
        return null;
    }


    public static String getRemoteDiscovery (Object memberDef) {
        if (memberDef instanceof MemberDef) {
            return ((MemberDef)memberDef).getRemoteDiscovery();
        }
		return null;
	}


    public static String getRemoteListen (Object memberDef) {
        if (memberDef instanceof com.tibco.as.space.MemberDef) {
            return ((com.tibco.as.space.MemberDef)memberDef).getRemoteListen();
        }
		return null;
	}


    public static int getWorkerThreadCount (Object memberDef) {
        if (memberDef instanceof com.tibco.as.space.MemberDef) {
            return ((com.tibco.as.space.MemberDef)memberDef).getWorkerThreadCount();
        }
		return 0;
	}


    public static void setContext (Object memberDef, Object contextTuple) {
        ((MemberDef)memberDef).setContext((Tuple)contextTuple);
		return;
	}


    public static void setDataStore (Object memberDef, String dataStore) {
        ((MemberDef)memberDef).setDataStore(dataStore);
		return;
	}


    public static void setDiscovery (Object memberDef, String discoveryUrl) {
        ((MemberDef)memberDef).setDiscovery(discoveryUrl);
		return;
	}


    public static void setListen (Object memberDef, String listenUrl) {
        ((MemberDef)memberDef).setListen(listenUrl);
		return;
	}


    public static void setMemberName (Object memberDef, String memberName) {
        ((MemberDef)memberDef).setMemberName(memberName);
		return;
	}

	
    public static void setProcessName (Object memberDef, String processName) {
        ((MemberDef)memberDef).setProcessName(processName);
		return;
	}

	
    public static void setRemoteDiscovery (Object memberDef, String remoteDiscovery) {
        ((MemberDef)memberDef).setRemoteDiscovery(remoteDiscovery);
		return;
	}


    public static void setRemoteListen (Object memberDef, String remoteListen) {
        ((MemberDef)memberDef).setRemoteListen(remoteListen);
		return;
	}


    public static void setWorkerThreadCount (Object memberDef, int numWorkers) {
        ((MemberDef)memberDef).setWorkerThreadCount(numWorkers);
		return;
	}
    
	
    public static void setSecurity (Object memberDef, boolean isController, String filePath) {
    	if (isController) {
    		((MemberDef)memberDef).setSecurityPolicyFile(filePath);
    	} else {
    		((MemberDef)memberDef).setSecurityTokenFile(filePath);
    	}
        return;
    }
	
	
    public static void setIdentityPassword (Object memberDef, String password) {
		if (password != null) {
			((MemberDef)memberDef).setIdentityPassword(password.toCharArray());
		}
    }
	
	
    public static void setUserName (Object memberDef, String userName) {
    	MemberDef lMemberDef = (MemberDef) memberDef;
    	BEASCredentialHolder authCallback = (BEASCredentialHolder) lMemberDef.getAuthenticationCallback();
    	if (authCallback == null) {
    		authCallback = new BEASCredentialHolder();
    		lMemberDef.setAuthenticationCallback(authCallback);
    	}
   		authCallback.setUserName(userName);
    }

	
    public static void setPassword (Object memberDef, String password) {
    	MemberDef lMemberDef = (MemberDef) memberDef;
    	BEASCredentialHolder authCallback = (BEASCredentialHolder) lMemberDef.getAuthenticationCallback();
    	if (authCallback == null) {
    		authCallback = new BEASCredentialHolder();
    		lMemberDef.setAuthenticationCallback(authCallback);
    	}
    	authCallback.setPassword(password);
    }
    
	
	public static void setDomainName (Object memberDef, String domainName) {
    	MemberDef lMemberDef = (MemberDef) memberDef;
    	BEASCredentialHolder authCallback = (BEASCredentialHolder) lMemberDef.getAuthenticationCallback();
    	if (authCallback == null) {
    		authCallback = new BEASCredentialHolder();
    		lMemberDef.setAuthenticationCallback(authCallback);
    	}
    	authCallback.setDomainName(domainName);
    }
    
	
    public static void setKeyFile (Object memberDef, String keyFile) {
    	MemberDef lMemberDef = (MemberDef) memberDef;
    	BEASCredentialHolder authCallback = (BEASCredentialHolder) lMemberDef.getAuthenticationCallback();
    	if (authCallback == null) {
    		authCallback = new BEASCredentialHolder();
    		lMemberDef.setAuthenticationCallback(authCallback);
    	}
    	authCallback.setKeyFile(keyFile);
    }

}

/**
 * This class holds AS credentials as set by the user functions.
 * AS will callback into createUserCredential during authentication.
 * 
 * @author bala
 *
 */
class BEASCredentialHolder  implements AuthenticationCallback {
	
	protected String domainName;
	protected String userName;
	protected String password;
	protected String keyFile;
	
	public String setDomainName (String domainName) {
		String domainNamePrev = this.domainName;
		this.domainName = domainName;
		return domainNamePrev;
	}
	
	public String setUserName(String userName) {
		String userNamePrev = this.userName;
		this.userName = userName;
		return userNamePrev;
	}
	
	public String setPassword (String password) {
		String passwordPrev = this.password;
		this.password = password;
		return passwordPrev;
	}
	
	public String setKeyFile (String keyFile) {
		String keyFilePrev = this.keyFile;
		this.keyFile = keyFile;
		return keyFilePrev;
	}
	
	public String getDomainName() {
		return this.domainName;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public String getPassword() {
		return this.password;
	}

	public String getKeyFile() {
		return this.keyFile;
	}
	
	@Override
	public void createUserCredential(AuthenticationInfo info) {

        // Currently the authentication methods are: USERPWD and X509V3
        if (info.getAuthenticationMethod() == Method.USERPWD) {
            UserPwdCredential userCredential = (UserPwdCredential) info.getUserCredential();
            if (domainName != null) {
            	userCredential.setDomain(domainName);
            }
            if (userName != null) {
            	userCredential.setUserName(userName);
			}
            if (password != null) {
            	userCredential.setPassword(password.toCharArray());
			}
        } else if (info.getAuthenticationMethod() == Method.X509V3) {
        	X509V3Credential userCredential = (X509V3Credential) info.getUserCredential();
        	if (keyFile != null) {
        		userCredential.setKeyFile(keyFile);
        	}
        	if (password != null) {
        		userCredential.setPassword(password.toCharArray());
        	}
        }
		
	}

	@Override
	public void onCleanup() {
		// TODO Auto-generated method stub
	}
}