package com.tibco.be.bemm.functions;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 4/12/11
 * Time: 6:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class MMActionsCallbackHandler implements CallbackHandler {

        private String uiUserName;
        private String uiPassword;

        public MMActionsCallbackHandler(String uiUserName, String uiPassword) {
            this.uiUserName = uiUserName;
            this.uiPassword = uiPassword;
        }

        @Override
        public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
            for (Callback callback : callbacks) {
                if (callback instanceof NameCallback) {
                    ((NameCallback) callback).setName(uiUserName);
                } else if (callback instanceof PasswordCallback) {
                    ((PasswordCallback) callback).setPassword(uiPassword.toCharArray());
                }
                else {
                    throw new UnsupportedCallbackException(callback, "Callback not supported");
                }
            }
        }
    }
