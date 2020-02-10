package com.tibco.cep.dashboard.psvr.text.url;

import com.tibco.cep.kernel.service.logging.Level;

class ConstantSubstitutor implements Substitutor {
	
	private static final long serialVersionUID = 2284462353155739001L;

	//Keys are actual values and free-formed
	private static final String[] KEYS = new String[] {}; 

	@Override
	public String substitute(String key, SubstitutionContext context) throws Exception {
		context.getLogger().log(Level.WARN, "Constant resolution is not implemented.");
		return key;		
	}

	@Override
	public String[] getSupportedKeys() {
		return KEYS;
	}
}
