package com.tibco.cep.driver.ftl;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.CommandChannel;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.impl.AbstractChannel;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.util.ResourceManager;

public class FTLChannel extends AbstractChannel<FTLDestination> implements CommandChannel {

	private String realmServer, username, password, secondary;
	private String trustType, trustFileLocation, trustFileString;
	private boolean useSsl;
	private Properties beProperties;
	
	public FTLChannel(ChannelManager manager, String uri, ChannelConfig config) {
		super(manager, uri, config);
		realmServer = ((FTLChannelConfig) config).getRealmServer();
		username = ((FTLChannelConfig) config).getUsername();
		password = ((FTLChannelConfig) config).getPassword();
		secondary = ((FTLChannelConfig) config).getSecondary();
		trustType = ((FTLChannelConfig) config).getTrustType();
		trustFileLocation = ((FTLChannelConfig) config).getTrustFileLocation();
		trustFileString = ((FTLChannelConfig) config).getTrustFileString();
		useSsl = ((FTLChannelConfig) config).isUseSsl();
		beProperties = getChannelManager().getRuleServiceProvider().getProperties();
		Iterator<?> i = config.getDestinations().iterator();
		while (i.hasNext()) {
			DestinationConfig destConfig = (DestinationConfig) i.next();
			FTLDestination ftlDest = new FTLDestination(this, destConfig);
			this.destinations.put(ftlDest.getURI(), ftlDest);
		}
	}

	public String getSecondary() {
		return secondary;
	}

	public void setSecondary(String secondary) {
		this.secondary = secondary;
	}

	public String getRealmServer() {
		return realmServer;
	}

	public void setRealmServer(String realmServer) {
		this.realmServer = realmServer;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getTrustType() {
		return trustType;
	}

	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}

	public String getTrustFileLocation() {
		return trustFileLocation;
	}

	public void setTrustFileLocation(String trustFileLocation) {
		this.trustFileLocation = trustFileLocation;
	}
	
	public String getTrustFileString() {
		return trustFileString;
	}

	public void setTrustFileString(String trustFileString) {
		this.trustFileString = trustFileString;
	}

	public boolean isUseSsl() {
		return useSsl;
	}

	public void setUseSsl(boolean useSsl) {
		this.useSsl = useSsl;
	}
	
	public Properties getBeProperties() {
		return beProperties;
	}

	public void init() throws Exception {
		Iterator<?> i = getDestinations().values().iterator();
		while (i.hasNext()) {
			((Destination) i.next()).init();
		}
		super.init();
	}

	@Override
	public void connect() throws Exception {
		getLogger().log(Level.INFO, "Connect FTLChannel: " + this.getURI());
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
					for (Destination d : getDestinations().values()) {
						try {
							 d.connect();
						} catch (Exception e) {
							// TODO: handle exception
							getLogger().log(Level.ERROR, e.getMessage());
							throw new Exception("Invalid state to connect ["+e.getMessage()+"] State [" + state + "]");
						}
						
					}
					setState(State.CONNECTED);
				}
			}
		} else {
			throw new Exception("Invalid state to connect..." + state);
		}

	}

	public void start(int mode) throws Exception {
		getLogger().log(Level.INFO, "Start FTLChannel: " + this.getURI());
		State state = getState();

		if (state.equals(State.INITIALIZED) || state.equals(State.UNINITIALIZED)) {
			connect();
		}
		if (!(state.equals(State.CONNECTED) || state.equals(State.STARTED) || state.equals(State.STOPPED))) {
			throw new Exception("Inconsistent state to start channel: " + state.toString());
		}
		getLogger().log(Level.INFO, "Start FTL Destinations...");
		Iterator<?> i = getDestinations().values().iterator();
		while (i.hasNext()) {
			try {
				 ((Destination) i.next()).start(mode);
			} catch (Exception e) {
				// TODO: handle exception
				
				
			}
		}
		super.start(mode);

		this.getLogger().log(Level.INFO, ResourceManager.getInstance().formatMessage("channel.started", this.getURI()));
	}
	
	@Override
	public void stop() {
		getLogger().log(Level.INFO, "Stop FTLChannel: " + this.getURI());
		if(getState() == State.STARTED) {
			Iterator<FTLDestination> i = getDestinations().values().iterator();
			while (i.hasNext()) {
				((Destination) i.next()).stop();
			}
			super.stop();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public FTLDestination createDestination(DestinationConfig destConfig) {
		return new FTLDestination(this, destConfig);
	}

	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void send(SimpleEvent arg0, String arg1, Map arg2) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws Exception {
		Iterator<?> i = getDestinations().values().iterator();
		while (i.hasNext()) {
			((Destination) i.next()).close();
		}
	}

}
