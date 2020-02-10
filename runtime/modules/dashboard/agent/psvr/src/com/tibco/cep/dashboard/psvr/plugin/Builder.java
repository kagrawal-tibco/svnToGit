package com.tibco.cep.dashboard.psvr.plugin;

import java.util.List;

import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;

public abstract class Builder extends PlugInFunctionHelper {
	
	public Builder(PlugIn plugIn) {
		super(plugIn);
	}

	public abstract List<BuilderResult> build(TokenRoleProfile profile) throws MALException;
	
	@Override
	public String toString() {
		return super.toString()+" Builder";
	}
	
}