package com.tibco.cep.driver.as.internal.fillers;

import com.tibco.as.space.Tuple;

public class ASTupleFiller implements IASTupleFiller {

	private String[][] props;

	public ASTupleFiller(String[][] props) {
		this.props = props;
	}

	@Override
	public void fill(Tuple tuple) {
		for (String[] prop : props) {
			String key = prop[0];
			String value = prop[1];
			tuple.put(key, value);
		}
	}

}