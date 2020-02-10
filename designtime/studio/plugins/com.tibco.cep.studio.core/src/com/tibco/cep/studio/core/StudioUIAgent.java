package com.tibco.cep.studio.core;

import java.awt.Font;
import java.awt.Frame;
import java.io.File;
import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.parser.semantic.FunctionsCatalogManager;
import com.tibco.be.util.BECustomFunctionResolver;
import com.tibco.cep.mapper.xml.xdata.XPathFunctionResolver;
import com.tibco.cep.mapper.xml.xdata.bind.DefaultStylesheetResolver;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetResolver;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionResolver;
import com.tibco.cep.studio.core.util.IDisplayFrameProvider;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.tns.cache.TnsCache;

public class StudioUIAgent implements UIAgent {

    private static final String SWT_AWT_DISPLAY_FRAME_PROVIDER_EXTENSION_POINT = "com.tibco.cep.studio.core.displayFrameProvider";
	private static final String SWT_AWT_DISPLAY_FRAME = "frame";
	ConcurrentHashMap<String, String> preferences = new ConcurrentHashMap<String, String>();
    Font appFont;
    Font scriptFont;
    FunctionResolver funcResolver;
    StylesheetResolver styleSheetResolver;
    Frame defaultFrame;
    String projectName;
    String rootPath;
	private IDisplayFrameProvider frameProvider;

    public StudioUIAgent(String projectName) {
    	this((Frame)null, projectName);
	}

    public StudioUIAgent(Frame frame, String projectName) {
    	if(!projectName.trim().isEmpty()){
    		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
    		this.rootPath = project.getLocation().toOSString();
    	}
    	else{
    		rootPath="";
    	}
    	this.projectName = projectName;

    	appFont = new Font("sansserif", Font.PLAIN, 12);
        scriptFont =  new Font("sansserif", Font.PLAIN, 12);
        initFunctionResolver(projectName);
        styleSheetResolver = new DefaultStylesheetResolver();
        if (frame != null) {
        	this.defaultFrame = frame;
        }
//        tnsCache = new TargetNamespaceCache();
//        TargetNamespaceProvider targetNamespaceProvider = ((TargetNamespaceCache)tnsCache).getNamespaceProvider();
//        smNamespaceProvider = new SmNamespaceProviderImpl(targetNamespaceProvider);
    }
    
    public void reloadFunctionResolver() {
    	initFunctionResolver(this.projectName);
    }
    
	private void initFunctionResolver(String projectName) {
    	if ("true".equalsIgnoreCase(System.getProperty("xpath.disable.customfunctions", "false"))) {
            funcResolver = new XPathFunctionResolver();
            return;
    	}
		if (projectName != null) {
			try {
				FunctionsCatalog customRegistry = FunctionsCatalogManager.getInstance().getCustomRegistry(projectName);
				if (customRegistry != null) {
					funcResolver = new XPathFunctionResolver(new BECustomFunctionResolver(customRegistry));
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
			
        funcResolver = new XPathFunctionResolver();
	}

	@Override
	public TnsCache getTnsCache() {
		return StudioCorePlugin.getCache(getProjectName());
	}

	@Override
	public SmNamespaceProvider getSmNamespaceProvider() {
		return StudioCorePlugin.getCache(getProjectName()).getSmNamespaceProvider();
	}

    public Font getAppFont() {
        return appFont;
    }

    public Font getScriptFont() {
        return scriptFont;
    }

    public String getUserPreference(String name) {
        return preferences.get(name);
    }

    public String getUserPreference(String name, String defaultValue) {
        String value = preferences.get(name);
        if  (value == null) return defaultValue;
        return value;
    }

    public void setUserPreference(String name, String val) {
        preferences.put(name, val);
    }

    public FunctionResolver getFunctionResolver() {
        return funcResolver;
    }

    public StylesheetResolver getStyleSheetResolver() {
        return styleSheetResolver;
    }

    public Frame getFrame() {
    	if (defaultFrame == null) {
    		// should maybe be synchronized, but this should only be called in a modal context (so multiple threads won't call it)
    		IExtensionRegistry reg = Platform.getExtensionRegistry();
    		IConfigurationElement[] extensions = reg
    				.getConfigurationElementsFor(SWT_AWT_DISPLAY_FRAME_PROVIDER_EXTENSION_POINT);
    		for (int i = 0; i < extensions.length; i++) {
    			IConfigurationElement element = extensions[i];
    			Object o;
				try {
					o = element.createExecutableExtension(SWT_AWT_DISPLAY_FRAME);
					if (o instanceof IDisplayFrameProvider) {
						frameProvider = (IDisplayFrameProvider) o;
						frameProvider.createFrame();
					}
				} catch (CoreException e) {
					StudioCorePlugin.log(e);
				}
    		}	
    		defaultFrame = frameProvider.getFrame();
    	}
    	if (defaultFrame == null || !defaultFrame.isDisplayable()) {
    		return new Frame();
    	}
        return defaultFrame;
    }

    public String getAbsoluteURIFromProjectRelativeURI(String location) {
    	try {
    		return URI.create(location).isAbsolute() ? location : getRootProjectPath() + File.separator + location;
		} catch (IllegalArgumentException e) {
		}
		return getRootProjectPath() + File.separator + location;
    }

    public String getProjectRelativeURIFromAbsoluteURI(String location) {
    	int idx = location.indexOf('!');
    	if (idx >= 0) {
    		return location.substring(idx+1);
    	}
		String rootPath = getRootProjectPath();
		if (rootPath.length() >= location.length()) {
			return location; // throw error?
		}
		return location.substring(rootPath.length()+1);
	}

	@Override
	public String getProjectName() {
		return projectName;
	}
	
	@Override
	public String getRootProjectPath() {
		return rootPath;
	}

	@Override
	public SmComponentProviderEx getSmComponentProviderEx() {
		return StudioCorePlugin.getCache(getProjectName()).getSmComponentProvider();
	}

	@Override
	public void openResource(String location) {
		UIAgentExtension[] agentExtensions = UIAgentUtils.getAgentExtensions();
		for (UIAgentExtension agentExtension : agentExtensions) {
			agentExtension.openResource(this, location);
		}
	}

}
