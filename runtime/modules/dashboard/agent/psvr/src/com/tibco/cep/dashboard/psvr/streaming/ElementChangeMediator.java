package com.tibco.cep.dashboard.psvr.streaming;

import java.security.Principal;
import java.util.HashMap;

import com.tibco.cep.dashboard.psvr.mal.ElementChangeListener;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.security.SecurityToken;

class ElementChangeMediator implements ElementChangeListener {
	
	private SecurityToken token;
	
	private TokenRoleProfile profile;
	
	private Channel channel;

	ElementChangeMediator(SecurityToken token, Channel channel) throws MALException, ElementNotFoundException {
		this.token = token;
		this.channel = channel;
		principalChanged();
	}
	
	final Principal getPrefferredPrincipal() {
		return this.profile.getPreferredPrincipal();
	}
	
	final void principalChanged() throws MALException, ElementNotFoundException {
		if (this.profile != null) {
			this.profile.getViewsConfigHelper().removeElementChangeListener(this);
		}
		this.profile = TokenRoleProfile.getInstance(token);
		this.profile.getViewsConfigHelper().addElementChangeListener(this);
	}

	@Override
	public void prepareForChange(MALElement element) {
		if (element instanceof MALComponent) {
			HashMap<String, String> request = new HashMap<String, String>();
			request.put("componentid",element.getId());
			channel.unsubscribe(request);
		}
	}

	@Override
	public void changeAborted(MALElement element) {
		if (element instanceof MALComponent) {
			HashMap<String, String> request = new HashMap<String, String>();
			request.put("componentid",element.getId());
			PresentationContext ctx = null;
			try {
				ctx = new PresentationContext(token);
				channel.subscribe(request, ctx);
			} catch (MALException e) {
				throw new RuntimeException(e);
			} catch (ElementNotFoundException e) {
				throw new RuntimeException(e);
			} catch (StreamingException e) {
				throw new RuntimeException(e);
			} finally {
				if (ctx != null){
					ctx.close();
				}
			} 			
		}
	}

	@Override
	public void changeComplete(MALElement element) {
		if (element instanceof MALComponent) {
			HashMap<String, String> request = new HashMap<String, String>();
			request.put("componentid",element.getId());
			PresentationContext ctx = null;
			try {
				ctx = new PresentationContext(token);
				channel.subscribe(request, ctx);
			} catch (MALException e) {
				throw new RuntimeException(e);
			} catch (ElementNotFoundException e) {
				throw new RuntimeException(e);
			} catch (StreamingException e) {
				throw new RuntimeException(e);
			} finally {
				if (ctx != null){
					ctx.close();
				}
			}			
		}		
	}

	@Override
	public void postOp(String parentPath, MALElement child, MALElement replacement, OPERATION operation) {
	}

	@Override
	public void preOp(String parentPath, MALElement child, MALElement replacement, OPERATION operation) {
	}

}