package com.tibco.cep.dashboard.tools.streamer;

import java.io.IOException;

interface StreamerFileReader {

	public EventInfo getNextEventInfo() throws IOException;

}