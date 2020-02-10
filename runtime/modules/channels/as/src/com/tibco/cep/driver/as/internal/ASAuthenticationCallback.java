package com.tibco.cep.driver.as.internal;

import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_AUTH_DOMAIN;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_AUTH_USERNAME;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_AUTH_PASSWORD;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_AUTH_KEY_FILE;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_AUTH_PRIVATE_KEY;

import java.util.HashMap;
import java.util.Map;

import com.tibco.as.space.security.AuthenticationCallback;
import com.tibco.as.space.security.AuthenticationInfo;
import com.tibco.as.space.security.AuthenticationInfo.Method;
import com.tibco.as.space.security.UserPwdCredential;
import com.tibco.as.space.security.X509V3Credential;
import com.tibco.cep.driver.as.utils.StringUtils;

public class ASAuthenticationCallback implements AuthenticationCallback {

    private Map<String, String> channelProps;
    
    public ASAuthenticationCallback(Map<String, String> props) {
        this.channelProps = props;
    }

    public ASAuthenticationCallback(ASChannelConfig config) {
        this.channelProps = new HashMap<String, String>();
        this.channelProps.put(CHANNEL_PROPERTY_AUTH_DOMAIN.localName, config.getDomain());
        this.channelProps.put(CHANNEL_PROPERTY_AUTH_USERNAME.localName, config.getUserName());
        this.channelProps.put(CHANNEL_PROPERTY_AUTH_PASSWORD.localName, ASChannel.decryptPwd(config.getPassword()));
        this.channelProps.put(CHANNEL_PROPERTY_AUTH_KEY_FILE.localName, config.getKeyFile());
        this.channelProps.put(CHANNEL_PROPERTY_AUTH_PRIVATE_KEY.localName, ASChannel.decryptPwd(config.getPrivateKey()));
    }

    @Override
    public void createUserCredential(AuthenticationInfo info) {
        // Currently the authentication methods are: USERPWD and X509V3
        if (info.getAuthenticationMethod() == Method.USERPWD) {
            // prompt the user for their login domain, user name and password
            // then update the UserPwdCredential object with the given information
            UserPwdCredential userCred = (UserPwdCredential) info.getUserCredential();
            String domain = channelProps.get(CHANNEL_PROPERTY_AUTH_DOMAIN.localName);
            userCred.setDomain(StringUtils.isNotEmpty(domain) ? domain : "");
            String userName = channelProps.get(CHANNEL_PROPERTY_AUTH_USERNAME.localName);
            userCred.setUserName(StringUtils.isNotEmpty(userName) ? userName : "");
            String password = channelProps.get(CHANNEL_PROPERTY_AUTH_PASSWORD.localName);
            userCred.setPassword(StringUtils.isNotEmpty(password) ? password.toCharArray() : "".toCharArray());
        } else if (info.getAuthenticationMethod() == Method.X509V3) {
            X509V3Credential x509V3Cred = (X509V3Credential) info.getUserCredential();
            String keyFile = channelProps.get(CHANNEL_PROPERTY_AUTH_KEY_FILE.localName);
            x509V3Cred.setKeyFile(StringUtils.isNotEmpty(keyFile) ? keyFile : "");
            String privateKey = channelProps.get(CHANNEL_PROPERTY_AUTH_PRIVATE_KEY.localName);
            x509V3Cred.setPassword(StringUtils.isNotEmpty(privateKey) ? privateKey.toCharArray() : "".toCharArray());
        }
    }

    @Override
    public void onCleanup() {
        // TODO Auto-generated method stub

    }

}
