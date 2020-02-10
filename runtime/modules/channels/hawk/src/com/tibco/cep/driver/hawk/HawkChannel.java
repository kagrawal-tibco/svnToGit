package com.tibco.cep.driver.hawk;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import COM.TIBCO.hawk.console.hawkeye.TIBHawkConsole;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.CommandChannel;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.impl.AbstractChannel;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.util.ResourceManager;
import com.tibco.hawk.jshma.plugin.HawkConsoleBase;

public class HawkChannel extends AbstractChannel<HawkDestination> implements CommandChannel {
	private TIBHawkConsole hawkConsole;
	private HawkConsoleBase hawkConsoleBase;
	private boolean hasInitHawkConsoleMonitor;
	private boolean hasInitHawkConsoleBase;

	public HawkChannel(ChannelManager manager, String uri, ChannelConfig config) {
		super(manager, uri, config);
		Iterator<?> i = config.getDestinations().iterator();
		while (i.hasNext()) {
			DestinationConfig destConfig = (DestinationConfig) i.next();
			HawkDestination hawkDest = new HawkDestination(this, destConfig);
			this.destinations.put(hawkDest.getURI(), hawkDest);
		}
	}

	public TIBHawkConsole getHawkConsole() {
		return hawkConsole;
	}

	public void setHawkConsole(TIBHawkConsole hawkConsole) {
		this.hawkConsole = hawkConsole;
	}

	public HawkConsoleBase getHawkConsoleBase() {
		return hawkConsoleBase;
	}

	public void setHawkConsoleBase(HawkConsoleBase hawkConsoleBase) {
		this.hawkConsoleBase = hawkConsoleBase;
	}

	public ChannelConfig getChannelConfig() {
		return channelConfig;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Destination createDestination(DestinationConfig destConfig) {
		return new HawkDestination(this, destConfig);
	}

	@Override
	public void init() throws Exception {
		Iterator<?> i = getDestinations().values().iterator();
		while (i.hasNext()) {
			((Destination) i.next()).init();
		}
		super.init();
	}

	@Override
	public void connect() throws Exception {
		getLogger().log(Level.INFO, "Connect HawkChannel: " + this.getURI());
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
					createHawkConsole();
					for (Destination d : getDestinations().values()) {
						d.connect();
					}
					setState(State.CONNECTED);
				}
			}
		} else {
			throw new Exception("Invalid state to connect..." + state);
		}

	}

	public void start(int mode) throws Exception {
		getLogger().log(Level.INFO, "Start HawkChannel: " + this.getURI());
		State state = getState();

		if (state.equals(State.INITIALIZED) || state.equals(State.UNINITIALIZED)) {
			connect();
		}
		if (!(state.equals(State.CONNECTED) || state.equals(State.STARTED) || state.equals(State.STOPPED))) {
			throw new Exception("Inconsistent state to start channel: " + state.toString());
		}

		// init Hawk console base which used for subscription and catalog
		// functions
		if (!hasInitHawkConsoleBase) {
			getLogger().log(Level.INFO, "Initialize Hawk Console Base...");
			int num = 0;
			int times = 0;
			while (num == 0) {
				try {
					if (times < 7) {
						num = hawkConsoleBase.getAllAgentInstances().length;
						times++;
					} else {
						getLogger().log(Level.ERROR, "Time out! Hawk console base initialization failed!");
						return;
					}
					Thread.sleep(5000);
				} catch (ArrayIndexOutOfBoundsException e) {
				}
			}
			hasInitHawkConsoleBase = true;
		}

		// start destinations
		Iterator<?> i = getDestinations().values().iterator();
		while (i.hasNext()) {
			((Destination) i.next()).start(mode);
		}

		// init Hawk console monitor which used for listeners
		if (mode == ChannelManager.SUSPEND_MODE && !hasInitHawkConsoleMonitor) {
			getLogger().log(Level.INFO, "Initialize Hawk Console Monitor...");
			hasInitHawkConsoleMonitor = true;
			hawkConsole.getAgentMonitor().initialize();
		}

		super.start(mode);

		this.getLogger().log(Level.INFO, ResourceManager.getInstance().formatMessage("channel.started", this.getURI()));
	}

	@Override
	public void stop() {
		getLogger().log(Level.INFO, "Stop HawkChannel: " + this.getURI());
		Iterator<?> i = getDestinations().values().iterator();
		while (i.hasNext()) {
			((Destination) i.next()).stop();
		}
		super.stop();
	}

	@Override
	public void close() throws Exception {
		getLogger().log(Level.INFO, "Close HawkChannel: " + this.getURI());
		if (hawkConsoleBase != null) {
			hawkConsoleBase.destroy();
		}
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	@Override
	public void send(SimpleEvent arg0, String arg1, @SuppressWarnings("rawtypes") Map arg2) throws Exception {
	}

	private void createHawkConsole() throws Exception {
		HawkChannelConfig hawkConfig = (HawkChannelConfig) channelConfig;
		String type = hawkConfig.getTransport();
		this.getLogger().log(Level.INFO,
				"Hawk Domain: " + hawkConfig.getHawkDomain() + "\nHawk Transport Type: " + type);
		if (HawkConstants.TRANSPORT_TYPE_EMS.equals(type)) {
			HashMap<String, String> transportEnv = new HashMap<String, String>();
			String emsParams = hawkConfig.getEmsUrl();
			if (hawkConfig.getUserName() != null && !hawkConfig.getUserName().equals("")) {
				emsParams += " " + hawkConfig.getUserName();
			}
			if (hawkConfig.getPassword() != null && !hawkConfig.getPassword().equals("")) {
				emsParams += " " + hawkConfig.getPassword();
			}
			transportEnv.put(HawkConsoleBase.HAWK_CONSOLE_PROPERTY_EMSTRANSPORT, emsParams);
			hawkConsole = new TIBHawkConsole(hawkConfig.getHawkDomain(), hawkConfig.getEmsUrl(),
					hawkConfig.getUserName(), hawkConfig.getPassword(), new Hashtable<Object, Object>());
			hawkConsoleBase = new HawkConsoleBase(transportEnv, hawkConfig.getHawkDomain(), false);
		} else {
			hawkConsole = new TIBHawkConsole(hawkConfig.getHawkDomain(), hawkConfig.getRvService(),
					hawkConfig.getRvNetwork(), hawkConfig.getRvDaemon());
			hawkConsoleBase = new HawkConsoleBase(hawkConfig.getRvService(), hawkConfig.getRvNetwork(),
					hawkConfig.getRvDaemon(), hawkConfig.getHawkDomain(), false);
		}
	}

}
