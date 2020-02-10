package com.tibco.cep.bpmn.model.designtime.metamodel;


import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;

public class BpmnMetaModelURIHelper {
	public static enum URIType {
		INDEX,
		MODEL,
		EXTN
	}
	public static final String BPMN_COMMON_PLUGIN_ID="com.tibco.cep.bpmn.common"; //$NON-NLS-1$
	public static final String INDEX_URI="/be/cep-bpmn-index.ecore"; //$NON-NLS-1$
	public static final String MODEL_URI="/be/cep-bpmn.ecore"; //$NON-NLS-1$
	public static final String EXTN_URI="/be/cep-bpmn-extn.ecore"; //$NON-NLS-1$
	public static final String MODEL_FOLDER="/model"; //$NON-NLS-1$
	public static final URI INDEX_PLUGIN_URI = URI.createPlatformPluginURI(BPMN_COMMON_PLUGIN_ID+MODEL_FOLDER+INDEX_URI, false); //$NON-NLS-1$
	public static final URI MODEL_PLUGIN_URI = URI.createPlatformPluginURI(BPMN_COMMON_PLUGIN_ID+MODEL_FOLDER+MODEL_URI, false);//$NON-NLS-1$
	public static final URI EXTN_PLUGIN_URI = URI.createPlatformPluginURI(BPMN_COMMON_PLUGIN_ID+MODEL_FOLDER+EXTN_URI, false);//$NON-NLS-1$
	
	
	/*
	 * later should be changed to some property based
	 */
	public static Map<URIType, URI> getUriForWebStudio()
			throws URISyntaxException {
		return getUriForRuntime();

	}
	
	/*
	 * later should be changed to some property based
	 */
	public static Map<URIType,URI> getUriForEclipse(){
		Map<URIType,URI> uriMap = new HashMap<URIType,URI>();
		uriMap.put(URIType.INDEX,URI.createPlatformPluginURI(BPMN_COMMON_PLUGIN_ID + MODEL_FOLDER+INDEX_URI,false)); //$NON-NLS-1$
		uriMap.put(URIType.MODEL,URI.createPlatformPluginURI(BPMN_COMMON_PLUGIN_ID + MODEL_FOLDER+MODEL_URI,false));//$NON-NLS-1$
		uriMap.put(URIType.EXTN,URI.createPlatformPluginURI(BPMN_COMMON_PLUGIN_ID + MODEL_FOLDER+EXTN_URI,false));//$NON-NLS-1$
		return uriMap;
	}
	
	/*
	 * later should be changed to some property based
	 */
	public static Map<URIType,URI> getUriForRuntime() throws URISyntaxException{
		Map<URIType,URI> uriMap = new HashMap<URIType,URI>();
		URL indexUrl = BpmnMetaModel.class.getResource(MODEL_FOLDER+INDEX_URI);
		if(indexUrl == null) {
			indexUrl = BpmnMetaModel.class.getResource(INDEX_URI);
		}
		URL modelUrl = BpmnMetaModel.class.getResource(MODEL_FOLDER+MODEL_URI);
		if(modelUrl == null) {
			modelUrl = BpmnMetaModel.class.getResource(MODEL_URI);
			
		}
		URL extnUrl = BpmnMetaModel.class.getResource(MODEL_FOLDER+EXTN_URI);
		if(extnUrl == null) {
			extnUrl = BpmnMetaModel.class.getResource(EXTN_URI);
		}
		final URI indexUri = URI.createURI(indexUrl.toURI().toString());
		final URI modelUri = URI.createURI(modelUrl.toURI().toString());
		final URI extnUri = URI.createURI(extnUrl.toURI().toString());
		
		uriMap.put(URIType.INDEX,indexUri); //$NON-NLS-1$
		uriMap.put(URIType.MODEL,modelUri);//$NON-NLS-1$
		uriMap.put(URIType.EXTN,extnUri);//$NON-NLS-1$
		return uriMap;
	}
	
	

}
