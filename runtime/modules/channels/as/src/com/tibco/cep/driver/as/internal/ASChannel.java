package com.tibco.cep.driver.as.internal;

import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_SPACE_NAME;

import java.util.Map;

import com.tibco.as.space.ASCommon;
import com.tibco.as.space.ASException;
import com.tibco.as.space.MemberDef;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.SpaceDef;
import com.tibco.as.space.listener.SpaceDefListener;
import com.tibco.cep.driver.as.ASConstants;
import com.tibco.cep.driver.as.IASChannel;
import com.tibco.cep.driver.as.IASDestination;
import com.tibco.cep.driver.as.utils.StringUtils;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.CommandChannel;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.impl.AbstractChannel;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;

public class ASChannel extends AbstractChannel<IASDestination> implements CommandChannel, IASChannel {

	private Metaspace metaspace;

	public ASChannel(ChannelManager manager, String uri, ASChannelConfig config) {
		super(manager, uri, config);
		postConstruction();
	}

	private void postConstruction() {
        for (Object config : channelConfig.getDestinations()) {
            DestinationConfig destConfig = (DestinationConfig) config;
            IASDestination asDest = createDestination(destConfig);
            this.destinations.put(asDest.getURI(), asDest);
        }
        backwardCompatibility();
	}

	private void backwardCompatibility() {
	    ASChannelConfig asChannelConfig = (ASChannelConfig) channelConfig;
	    String securityRole = asChannelConfig.getSecurityRole();
	    if (securityRole.equals(ASConstants.AS_SECURITY_ROLE_REQUESTOR)) {
	        asChannelConfig.setSecurityRole(ASConstants.AS_SECURITY_ROLE_REQUESTER);
	    }
    }

    @Override
	public void close() throws Exception {

        for (Destination d : getDestinations().values()) {
            d.close();
        }

        destinations.clear();
		if (null != metaspace) {
			metaspace.closeAll();
		}

        setState(State.INITIALIZED);
    }

	@Override
	public void connect() throws Exception {
		State state = getState();

		if (state == State.UNINITIALIZED) {
			init();
		} else if ((state == State.STARTED) || (state == State.CONNECTED) || (state == State.STOPPED)) {
			return;
		} else if ((state == State.INITIALIZED) || (state == State.RECONNECTING)) {
			synchronized (state) {
				state = getState(); // Check the state again, call the getState,
									// as opposed to the state variable
				if ((state != State.STARTED) && (state != State.CONNECTED) && (state != State.STOPPED)) {
					// connect to metaspace
					connectASMetaspace();

                    for (Destination d : getDestinations().values()) {
                        d.connect();
                    }

					setState(State.CONNECTED);
				}
			}
		} else if (state == State.EXCEPTION) {
			// re-connect to metaspace?
			connectASMetaspace();
			setState(State.CONNECTED);
			return;
		} else {
			throw new Exception("Invalid state to connect..." + state);
		}
	}

	private void connectASMetaspace() throws ASException{
		// connect to metaspace 
        ASChannelConfig asChannelConfig = (ASChannelConfig)channelConfig;
        String metaspaceName = asChannelConfig.getMetaspaceName();
        String discoveryUrl = asChannelConfig.getDiscoveryUrl();
        String listenUrl = asChannelConfig.getListenUrl();
        String remoteListenUrl = asChannelConfig.getRemoteListenUrl();
        getLogger().log(Level.INFO, "AS Channel [%s] connecting to metaspace: %s", this.getName(), metaspaceName);
        metaspace = ASCommon.getMetaspace(metaspaceName);
        if (metaspace == null) {
        	MemberDef md = MemberDef.create();
        	if (asChannelConfig.isEnableSecurity()) {
                String idPassword = decryptPwd(asChannelConfig.getIdPassword());
                md.setIdentityPassword(StringUtils.isNotEmpty(idPassword) ? idPassword.toCharArray() : null);
        		String role = asChannelConfig.getSecurityRole();
        		if (role.equals(ASConstants.AS_SECURITY_ROLE_CONTROLLER)) {
					md.setSecurityPolicyFile(asChannelConfig.getPolicyFile())
					    .setListen(listenUrl).setRemoteListen(remoteListenUrl);
				} else {
				    md.setSecurityTokenFile(asChannelConfig.getTokenFile())
				      	.setAuthenticationCallback(new ASAuthenticationCallback(asChannelConfig));
        		}
        	} else {
        	    md.setListen(listenUrl).setRemoteListen(remoteListenUrl);
        	}
            md.setDiscovery(discoveryUrl);
            String memberName = asChannelConfig.getMemberName();
            if (memberName != null && !(memberName.trim().equals(""))) {
				md.setMemberName(memberName);
			}
            metaspace = Metaspace.connect(asChannelConfig.getMetaspaceName(), md);
        }
	}

    @Override
    public void init() throws Exception {
        super.init();

        for (final Destination d : this.getDestinations().values()) {
            d.init();
        }
    }

    @Override
	public boolean isAsync() {
		return false;
	}

	@Override
	public void send(SimpleEvent paramSimpleEvent, String paramString, @SuppressWarnings("rawtypes") Map paramMap) throws Exception {
	}

	@Override
	public Metaspace getMetaspace() {
		return metaspace;
	}

	@Override
	public void start(int mode) throws Exception {
		getLogger().log(Level.INFO, "******** ASChannel:start[%s:%s] ********", this.getURI(), mode);
		if ((getState() == State.INITIALIZED) || (getState() == State.UNINITIALIZED)) {
			connect();
		}

		if ((getState() == State.CONNECTED) || (getState() == State.STOPPED) || (getState() == State.STARTED)) {
			for (Destination dest : getDestinations().values()) {
				dest.start(mode);
			}
		}
        metaspace.listenSpaceDefs(new Handler());
        super.start(mode);
    }

    @Override
    public void stop() {
		getLogger().log(Level.INFO, "******** ASChannel:stop[%s] ********", this.getURI());
        for (Destination dest : getDestinations().values()) {
            dest.stop();
        }
        super.stop();
    }

	@SuppressWarnings("unchecked")
	@Override
	public IASDestination createDestination(DestinationConfig paramDestinationConfig) {
		return new ASDestination(this, paramDestinationConfig);
	}

	/**
	 * Decrypts a password only if it is encrypted, otherwise returns back same string.
	 * @param encryptedPwd
	 * @return
	 */
	public static String decryptPwd(String encryptedPwd) {
        try {
        	if (encryptedPwd == null || encryptedPwd.trim().isEmpty()) {
    			return encryptedPwd;
    		}
            String decryptedPwd = encryptedPwd;
            if (ObfuscationEngine.hasEncryptionPrefix(encryptedPwd)) {
                decryptedPwd = new String(ObfuscationEngine.decrypt(encryptedPwd));
            }
            return decryptedPwd;
        } catch (AXSecurityException e) {
            return encryptedPwd;
        }
    }

	private class Handler implements SpaceDefListener {

        @Override
        public void onDrop(SpaceDef spaceDef) {
            for (final IASDestination d : getDestinations().values()) {
                if (spaceDef.getName().equals(d.getSpaceDef().getName())) {
                    d.stop();
                    getLogger().log(Level.WARN, "Destination " + d.getConfig().getName() + " is suspended because the AS Space " + d.getSpaceDef().getName() + " is dropped.");
                }
            }
        }

        @Override
        public void onDefine(SpaceDef spaceDef) {
            for (final IASDestination d : getDestinations().values()) {
                if (spaceDef.getName().equals(d.getConfig().getProperties().getProperty(K_AS_DEST_PROP_SPACE_NAME))) {
                	// Need to IGNORE onDefine() calls due to the misleading calls:
                	// E.g.: When client registers spacedef listener; existing space definitions comes as put event, which translates to onDefine.
                	//       Example of this 'false' trigger is seen as-soon-as system is started.
                    //d.reconnect();
                    //getLogger().log(Level.WARN, "Destination " + d.getConfig().getName() + " is reconnected because the AS Space " + spaceDef.getName() + " is defined.");
                }
            }
        }

        @Override
        public void onAlter(SpaceDef oldSpaceDef, SpaceDef newSpaceDef) {
            for (final IASDestination d : getDestinations().values()) {
                if (oldSpaceDef.getName().equals(d.getSpaceDef().getName())) {
                    d.stop();
                    getLogger().log(Level.WARN, "Destination " + d.getConfig().getName() + " is suspended because the schema of AS Space " + d.getSpaceDef().getName() + " is altered.");
                }
            }
        }
    }
}
