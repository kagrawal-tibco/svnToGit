package com.tibco.cep.studio.core.doc;

import java.io.IOException;
import java.util.Map;

public interface Callback {
	public void writeFile(String templateName, Object data, String outFile) throws IOException;
}
