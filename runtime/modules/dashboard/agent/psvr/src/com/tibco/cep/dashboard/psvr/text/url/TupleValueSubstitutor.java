package com.tibco.cep.dashboard.psvr.text.url;

import com.tibco.cep.kernel.service.logging.Level;

class TupleValueSubstitutor implements Substitutor {
	
	private static final long serialVersionUID = 4471968556181183985L;

	// Keys are 'Field' names from current Event (or Tuple)	
	private static final String[] Keys = new String[] {}; 

	public String substitute(String substitutionKey, SubstitutionContext context) throws Exception {
		if (substitutionKey == null) {
			throw new IllegalArgumentException("NULL substitution key");
		} else {
			if(context.getTuple() == null) {
				context.getLogger().log(Level.WARN, "No tuple found, defaulting "+substitutionKey+" to ''");
				return "";
			}
			return context.getTuple().getFieldValueByName(substitutionKey).toString();
		}
	}
	
	@Override
	public String[] getSupportedKeys() {
		return Keys;
	}

}
