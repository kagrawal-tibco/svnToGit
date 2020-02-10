package com.tibco.cep.studio.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class DisplayUtils {

	public static final String DISPLAY = "display";
	public static final String DISPLAY_SUFFIX = "."+DISPLAY;
	public static final String DISPLAY_TEXT = "displayText";
	public static final String DISPLAY_TEXT_SUFFIX = "."+DISPLAY_TEXT;
	public static final String HIDDEN = "hidden";
	public static final String DEFAULT_COMMENT = "TIBCO Display Model";
	
	public static String getDisplayKey(DisplayProperties childProp) {
		return childProp.getTargetName()+DISPLAY_TEXT_SUFFIX;
	}
	
	public static String getHiddenKey(DisplayProperties childProp) {
		return childProp.getTargetName()+"."+HIDDEN;
	}
	

    /**
     * Convert a string based locale into a Locale Object.
     * Assumes the string has form "{language}_{country}_{variant}".
     * Examples: "en", "de_DE", "_GB", "en_US_WIN", "de__POSIX", "fr_MAC"
     *  
     * @param localeString The String
     * @return the Locale
     */
   /* public static Locale getLocaleFromString(String localeString)
    {
        if (localeString == null)
        {
            return null;
        }
        localeString = localeString.trim();
        if (localeString.toLowerCase().equals("default"))
        {
            return Locale.getDefault();
        }

        // Extract language
        int languageIndex = localeString.indexOf('_');
        String language = null;
        if (languageIndex == -1)
        {
            // No further "_" so is "{language}" only
            return new Locale(localeString, "");
        }
        else
        {
            language = localeString.substring(0, languageIndex);
        }

        // Extract country
        int countryIndex = localeString.indexOf('_', languageIndex + 1);
        String country = null;
        if (countryIndex == -1)
        {
            // No further "_" so is "{language}_{country}"
            country = localeString.substring(languageIndex+1);
            return new Locale(language, country);
        }
        else
        {
            // Assume all remaining is the variant so is "{language}_{country}_{variant}"
            country = localeString.substring(languageIndex+1, countryIndex);
            String variant = localeString.substring(countryIndex+1);
            return new Locale(language, country, variant);
        }
    }*/
	
	public static Locale getLocaleFromString(String localeString){
		
		 if (localeString.toLowerCase().equals("default")){
	            return Locale.getDefault();
	     }

		String isoLanguages[] = Locale.getISOLanguages();
		String isoCountries[] = Locale.getISOCountries();
		String language = null;
		String country = null;
		String variant = null;
		
		HashSet<String> languages = new HashSet<String>(Arrays.asList(isoLanguages));
		HashSet<String> countries = new HashSet<String>(Arrays.asList(isoCountries));
		
		String tokensGenerated[] = localeString.split("_");
		for(int i=0; i<tokensGenerated.length; i++){
			
			if(languages.contains(tokensGenerated[i].toLowerCase())){	
				language = tokensGenerated[i];
				if(( (i+1) < tokensGenerated.length) && countries.contains(tokensGenerated[i+1].toUpperCase())){
					country = tokensGenerated[i+1];
				}	
				if((i+2) < tokensGenerated.length){
					variant = tokensGenerated[i+2];
				}
				break;
			}
		}
		
		if(language == null){
			
			return Locale.getDefault();
		}
		else if(language !=null && country == null && variant == null){
			return new Locale(language);
		}
		else if(country != null  && variant == null){
			
			return new Locale(language, country);
		}
		else if(country == null && variant !=null ){
			
			return new Locale(language,"", variant);
		}
		else if(country!=null && variant !=null){
			
			return new Locale(language, country, variant);
		}
		return null;
	}
    
    public static IFile[] getDisplayModelFiles(IProject project) {
    	final List<IFile> dispFiles = new ArrayList<>();
    	try {
			project.accept(new IResourceVisitor() {

				@Override
				public boolean visit(IResource resource) throws CoreException {
					if (!(resource instanceof IFile)) {
						return true;
					}
					IFile file = (IFile) resource;
					if ("display".equalsIgnoreCase(file.getFileExtension())) {
						dispFiles.add(file);
					}
					return false;
				}
			});
		} catch (CoreException e) {
			StudioCorePlugin.log(e);
		}
    	return (IFile[]) dispFiles.toArray(new IFile[dispFiles.size()]);
    }
    
    public static IFile[] getDisplayModelFilesForEntity(Entity entity, boolean includeSubEntities) {
    	IProject proj = ResourcesPlugin.getWorkspace().getRoot().getProject(entity.getOwnerProjectName());
    	IFile[] allFiles = getDisplayModelFiles(proj);
    	List<IFile> targetFiles = new ArrayList<>();
    	List<IPath> relatedFiles = new ArrayList<>();
    	IFile entFile = IndexUtils.getFile(proj, entity);
    	if (entFile == null) {
    		return new IFile[0];
    	}
    	relatedFiles.add(entFile.getFullPath().removeFileExtension());
    	if (includeSubEntities) {
    		List<? extends Entity> subEntities = ModelUtils.collectAncestorEntities(entity, false);
			if (subEntities != null) {
				for (Entity sub : subEntities) {
					IFile file = IndexUtils.getFile(proj, sub);
					relatedFiles.add(file.getFullPath().removeFileExtension());
				}
			}
    	}
    	for (IFile dispFile : allFiles) {
			// check whether file is tied to entity or any super of entity
    		IPath dispPath = dispFile.getFullPath().removeFileExtension();
    		String filename = dispPath.lastSegment();
    		int idx = filename.indexOf('_');
    		if (idx >= 0) {
    			filename = filename.substring(0, idx);
    			dispPath = dispPath.removeLastSegments(1).append(filename);
    		}
    		if (relatedFiles.contains(dispPath)) {
    			targetFiles.add(dispFile);
    		}
		}
    	return (IFile[]) targetFiles.toArray(new IFile[targetFiles.size()]);
    }
}
