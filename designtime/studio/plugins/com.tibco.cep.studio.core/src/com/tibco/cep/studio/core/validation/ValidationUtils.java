package com.tibco.cep.studio.core.validation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;

import com.tibco.be.model.util.EntityNameHelper;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.studio.common.validation.utils.CommonValidationUtils;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.util.CommonUtil;

public class ValidationUtils extends CommonValidationUtils {
	
	protected static final String RESOURCE_VALIDATOR = "projectResourceValidator";

	protected static final String ATTR_VALIDATOR 	= "Validator";
	protected static final String ATTR_NAMES		= "names";
	protected static final String ATTR_EXTENSIONS	= "extensions";
	protected static final String ATTR_INSTANCEOF	= "instanceOf";
	
	protected static final String AFFECTED_RESOURCES = "com.tibco.studio.core.deleteDependentResources";

	private static ValidatorInfo[] fProjectResourceValidatorInfos;

	public static synchronized ValidatorInfo[] getProjectResourceValidators() {
		if (fProjectResourceValidatorInfos == null) {
			List<ValidatorInfo> validatorInfos = new ArrayList<ValidatorInfo>();
			IConfigurationElement[] configurationElementsFor = Platform.getExtensionRegistry().getConfigurationElementsFor(StudioCorePlugin.PLUGIN_ID, RESOURCE_VALIDATOR);
			for (IConfigurationElement configurationElement : configurationElementsFor) {
				String attribute = configurationElement.getAttribute(ATTR_VALIDATOR);
				IResourceValidator validator = null;
				if (attribute != null) {
					try {
						Object executableExtension = configurationElement.createExecutableExtension(ATTR_VALIDATOR);
						if (executableExtension instanceof IResourceValidator) {
							validator = (IResourceValidator) executableExtension;
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
				
				//Check both State Machine Validator and whether plug-in installed
				if(validator instanceof DefaultStateModelResourceValidator && 
						!StudioCorePlugin.getDefault().isStateMachineBundleInstalled()){
					continue;
				}
				
				String instanceAttr = configurationElement.getAttribute(ATTR_INSTANCEOF);
				String extensionsAttr = configurationElement.getAttribute(ATTR_EXTENSIONS);
				String namesAttr = configurationElement.getAttribute(ATTR_NAMES);
				ValidatorInfo info = new ValidatorInfo(validator, instanceAttr, extensionsAttr, namesAttr);
				validatorInfos.add(info);
			}
			fProjectResourceValidatorInfos = new ValidatorInfo[validatorInfos.size()];
			return validatorInfos.toArray(fProjectResourceValidatorInfos);
		}
		return fProjectResourceValidatorInfos;
	}
	/**
	 * checks if any String is null or Empty
	 * @param str
	 * @return
	 */
	
	/**
	 * resolve the resource from the relative path
	 * @param reference --> relative path of the resource with respect to project
	 * @param projectName --> Project Name
	 * @return
	 */
	public static IResource resolveResourceReference(String reference , String projectName){
		if (reference == null || projectName == null || reference.trim().length() ==0 || 
				      projectName.trim().length() == 0) return null;
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if (project == null) return null;
		IPath path = Path.fromOSString(reference);
		IResource referencedResource = project.findMember(path);		
		return referencedResource;
		
	}
	/**
	 * creates a Global Variable
	 * @param name --> name of the global variable , for example : account
	 * @param type --> type of the global variable , for example : Concepts.Account
	 * @param offset
	 * @param length
	 * @param array
	 * @return
	 */
	public static GlobalVariableDef createGVDefinition(String name, String type,
			int offset, int length, boolean array) {
		GlobalVariableDef definition = IndexFactory.eINSTANCE.createGlobalVariableDef();
		definition.setName(name);
		definition.setType(type);
		definition.setOffset(offset);
		definition.setLength(length);
		definition.setArray(array);
		return definition;
	}
	/**
	 * Checks a path if it has invalid characters 
	 * @param name
	 * @param allowPathSeparator --> allow path separator "\" for windows and "/" for Linux
	 * @param allowSpaces --> if spaces are allowed inside path --> by default false
	 * @return
	 */
	public static boolean containsInvalidCharacters(final String name ,boolean allowPathSeparator, boolean allowSpaces){
	       if ((null == name) || (0 == name.trim().length())) {
	            return false;
	        }//if

	        for(int i = 0; i < name.length(); i++) {
	            char ch = name.charAt(i);

	            if(ch == '_' && allowSpaces) continue;

	            boolean invalidSep = (ch == File.separatorChar) && !allowPathSeparator;
	            boolean invalidPart = (ch != File.separatorChar) && (!ModelNameUtil.isIdentifierPart(ch));

	            if(invalidSep || invalidPart) return true;
	        }

	        return false;
	}
	/**
	 * check whether entity path is valid or not , if Key Words are allowed are not , check for java identifier
	 * @param entityPath
	 * @param allowKeyWords
	 * @return
	 */
	
	public static boolean isValidEntityPath(String entityPath , boolean allowKeyWords){		
		if (entityPath == null) return true;
		StringTokenizer st = new StringTokenizer(entityPath , File.separator);
		while (st.hasMoreTokens()){
			String token = st.nextToken();
			if (token == null || token.trim().length() == 0) continue;
		      if(!ModelNameUtil.isValidIdentifier(token)) return false;
	            if(!allowKeyWords && EntityNameHelper.isKeyword(token)) return false;
		}
		
		return true;
		
	}
	
	public static QualifiedName getDependentQN(IResource resource) {
		return new QualifiedName(AFFECTED_RESOURCES, resource.getFullPath().toString());
	}
	
	/**
	 * @param resource
	 * @throws CoreException
	 */
	public static void validateResource(IResource resource) throws CoreException {
		boolean cancelled = false;
		ValidatorInfo[] projectResourceValidators = getProjectResourceValidators();
		for (ValidatorInfo validatorInfo : projectResourceValidators) {
			if (validatorInfo.enablesFor(resource)) {
				ValidationContext vldContext = new ValidationContext(resource, IResourceDelta.CHANGED, IncrementalProjectBuilder.FULL_BUILD);
				if (!validatorInfo.fValidator.validate(vldContext)) {
					if (!validatorInfo.fValidator.canContinue()) {
						cancelled = true;
						// something happened (for example, an unrecoverable error), and we cannot continue
						throw new CoreException(Status.CANCEL_STATUS);
					}
				}
			}
		}
	}
	
	/**
	 * @param project
	 * @param extension
	 */
	public static void validateResourceByExtension(IProject project, String extension) {
		ArrayList<IFile> resourceList = new ArrayList<IFile>();
		CommonUtil.getResourcesByExtension(project, extension, resourceList);
		for (IResource resource: resourceList) {
			try {
				validateResource(resource);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
}
