package com.tibco.cep.studio.core.util;



import java.io.ByteArrayInputStream;
import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

import com.tibco.cep.designtime.core.model.java.JavaResource;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.studio.common.configuration.JavaSourceFolderEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.utils.ModelUtils;


/**
 * This class contains various methods that users of the the Model API will find useful.
 */
public class ModelUtilsCore  extends ModelUtils{

	public static void persistRule(Rule convertedRule, URI fileURI) throws Exception{
		String newRuleContents = getRuleAsSource(convertedRule);
		File ruleFile = null;
		if(fileURI.isFile()) {
			ruleFile = new File(fileURI.toFileString());
			writeFile(newRuleContents, ruleFile);
		} else if(fileURI.isPlatformResource()) {
			IFile res = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileURI.toPlatformString(false)));			
			writeFile(newRuleContents, res);
		}

	}
	
	public static void persistRuleTemplate(RuleTemplate convertedRule, URI fileURI) throws Exception{
		String newRuleContents = getRuleTemplateAsSource(convertedRule);
		File ruleFile = null;
		if(fileURI.isFile()) {
			ruleFile = new File(fileURI.toFileString());
			writeFile(newRuleContents, ruleFile);
		} else if(fileURI.isPlatformResource()) {
			IFile res = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileURI.toPlatformString(false)));			
			writeFile(newRuleContents, res);
		}
		
	}
	
	/**
     * @param convertedRuleFn
     * @param fileURI
     * @throws Exception
     */
    public static void persistRuleFunction(RuleFunction convertedRuleFn, URI fileURI) throws Exception{
		String newRuleContents = getRuleFunctionAsSource(convertedRuleFn);
		File ruleFile = null;
		if(fileURI.isFile()) {
			ruleFile = new File(fileURI.toFileString());
			writeFile(newRuleContents, ruleFile);
		} else if(fileURI.isPlatformResource()) {
			IFile res = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileURI.toPlatformString(false)));			
			writeFile(newRuleContents, res);
		}
	}
	public static String removeBackSlash(String str){
		if(str!= null && str.startsWith("/"));
			str =  str.replaceFirst("/", "");
		return str;
	}
	
	public static String getJavaSrcFolderName(String fulPath ,String projName){
	  	StudioProjectConfiguration pconfig = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projName);
			String packageName = fulPath;
			String javaSrcFolderName = "";
			for (JavaSourceFolderEntry entry : pconfig.getJavaSourceFolderEntries()) {
				String configJavaSrcFolder = entry.getPath();
				configJavaSrcFolder = removeBackSlash(configJavaSrcFolder);
				if(configJavaSrcFolder.contains(projName)){
					configJavaSrcFolder = configJavaSrcFolder.substring(projName.length(),configJavaSrcFolder.length());
				}
				configJavaSrcFolder = removeBackSlash(configJavaSrcFolder);
				if (packageName.contains(configJavaSrcFolder)) {
					javaSrcFolderName = configJavaSrcFolder;
					break;
				}
			}
	    	
			return javaSrcFolderName;
	}
	
    /**
     * @param javaRes
     * @param fileURI
     * @param customfunction
     * @param javaTask
     * @throws Exception
     */
    public static void persistJavaResource(JavaResource javaRes, 
    		                               URI fileURI, 
    		                               boolean customfunction,  
    		                               boolean javaTask) throws Exception {
    	String javaSrcFolderName = getJavaSrcFolderName(javaRes.getFullPath(),javaRes.getOwnerProjectName());
		String newJavaRes = getJavaResourceAsSource(javaRes,javaSrcFolderName, customfunction, javaTask);
		File javaFile = null;
		if(fileURI.isFile()) {
			javaFile = new File(fileURI.toFileString());
			writeFile(newJavaRes, javaFile);
		} else if(fileURI.isPlatformResource()) {
			IFile res = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileURI.toPlatformString(false)));			
			writeFile(newJavaRes, res);
		}
	}
    
    
	protected static void writeFile(String newRuleContents, IFile file) throws Exception{
		final ByteArrayInputStream fos = new ByteArrayInputStream(newRuleContents.getBytes());
		int updateFlags = IFile.FORCE|IFile.KEEP_HISTORY;
		IProgressMonitor pm = new NullProgressMonitor();
		if(!file.exists()) {
			file.create(fos, updateFlags, pm);
		} else {
			file.setContents(fos, updateFlags, pm);
		}
	}
	
	
	
}
