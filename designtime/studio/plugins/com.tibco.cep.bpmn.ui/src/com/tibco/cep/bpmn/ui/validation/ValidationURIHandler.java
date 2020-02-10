/**
 * 
 */
package com.tibco.cep.bpmn.ui.validation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;

public class ValidationURIHandler extends ECoreHelper.BpmnIndexURIHandler {
	boolean isOldKey = false;
	boolean hasExtensions = false;
	
	Pattern extPattern = Pattern.compile("(.*@extensions.*)");
	Pattern pattern = Pattern.compile("(.*@extensions\\[name=')(-?\\d+)('\\].*)");
	private Collection<URI> invalidURIs = new HashSet<URI>();
	private Collection<String> invalidProjects = new HashSet<String>();
	private IResource resource;
	boolean changed = false;
	
	public ValidationURIHandler(IResource resource,Map<String, URI> locMap) {
		super(resource.getProject(),locMap);
		this.resource = resource;
	}
	

	public String getInvalidProjectNames() {
		StringBuilder sb = new StringBuilder();
		for(String name:invalidProjects){
			sb.append("\"")
				.append(name)
				.append("\",");
		}
		String invalidNames = sb.toString().trim();
		if(invalidNames.endsWith(","))
			invalidNames = invalidNames.substring(0, invalidNames.length()-1);
		return invalidNames;
	}
	
	public boolean isChanged() {
		return changed;
	}
	
	/**
	 * @return the isOldKey
	 */
	public boolean isOldKey() {
		return isOldKey;
	}
	

	/**
	 * @return the hasExtensions
	 */
	public boolean hasExtensions() {
		return hasExtensions;
	}

	/**
	 * @param hasExtensions the hasExtensions to set
	 */
	public void setHasExtensions(boolean hasExtensions) {
		this.hasExtensions = hasExtensions;
	}


	public Collection<URI> getInvalidURIs() {
		return invalidURIs;
	}
	
	@Override
	public URI deresolve(URI uri) {
		if (!uri.hasAbsolutePath()
				&& uri.scheme().equals(
						BpmnCommonIndexUtils.BPMN_INDEX_SCHEME)
				&& !uri.opaquePart().equals(getResourceProjectName())) {
			
			URI newURI = URI.createGenericURI(
					BpmnCommonIndexUtils.BPMN_INDEX_SCHEME, 
					getResourceProjectName(), 
					URI.decode(uri.fragment()));// converting
			changed = true;
			return newURI;
			
		} else if (uri.hasAbsolutePath()
				&& uri.fileExtension().equals(
						BpmnCommonIndexUtils.BPMN_INDEX_EXTENSION)) {
			@SuppressWarnings("unused")
			URI createFileURI = URI.createFileURI(uri.devicePath());// converting
			// to URI
			URI newURI = URI.createGenericURI(
					BpmnCommonIndexUtils.BPMN_INDEX_SCHEME, getResourceProjectName(),
					URI.decode(uri.fragment()));
			BpmnCorePlugin.debug("DeResolved URI:"+uri.toString()+"\n\t->"+newURI);
			return newURI;
			
		}
		return uri;
	}

	
	@Override
	public URI resolve(URI uri) {
		
		// This is for bdx:<ProjectName>#fragment
		if(		uri.scheme() != null && 
				uri.scheme().equals(BpmnIndexUtils.BPMN_INDEX_SCHEME)) {
			if(!getResourceProjectName().equals(uri.opaquePart())) {
				invalidURIs.add(uri);
				invalidProjects.add(uri.opaquePart());
				BpmnUIPlugin.debug("#### Invalid URI->"+uri.toString());
			}
			// convert int base name key to lowercase Hex String to match with index  
			String fragment = uri.fragment();
			Matcher m = pattern.matcher(fragment);
			if(m.matches()) {
				isOldKey = true;
				BpmnUIPlugin.debug("#### Old Key->"+m.group(2));
			} 
			m = extPattern.matcher(fragment);
			if(m.matches()) {
				hasExtensions = true;
				BpmnUIPlugin.debug("#### extension ->"+m.group(1));
				
				
			}
		}
		
		return uri;
	}
	
	String getResourceProjectName() {
		return resource.getProject().getName();
	}
	
	@Override
	public void setBaseURI(URI uri) {
		super.setBaseURI(uri);
	}
	
	
	
}