/**
 * 
 */
package com.tibco.cep.security.authen;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import com.tibco.net.mime.Base64Codec;

/**
 * @author aathalye
 * 
 */
public class RCPAuthCallbackHandler implements CallbackHandler {

	private String username;

	private String encodedPassword;

	public RCPAuthCallbackHandler(final String username, final String encodedString) {
		this.encodedPassword = encodedString;
		this.username = username;
	}

	@Override
	public void handle(final Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for (Callback callback : callbacks) {
			if (callback instanceof RCPAuthCallback) {
				RCPAuthCallback rac = (RCPAuthCallback) callback;
				String creds = extractCredentials();
				rac.setUsername(username);
				rac.setPassword(creds);
				// rac.setRoles(roles);
			} else {
				// For the time-being
				throw new UnsupportedCallbackException(callback, "Callback not supported");
			}
		}
	}

	private String extractCredentials() throws IOException {
		return Base64Codec.decodeBase64(encodedPassword, "UTF-8");
	}
}
