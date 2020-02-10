package com.tibco.cep.studio.core.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class ValidatorInfo {
	
	private String[] 			fNames;
	private String[] 			fExtensions;
	private Class<?> 			fInstanceOfClass;
	public IResourceValidator 	fValidator;
	
	public ValidatorInfo(IResourceValidator validator) {
		this.fValidator = validator;
	}

	public ValidatorInfo(IResourceValidator validator, String instanceAttr,
			String extensionsAttr, String namesAttr) {
		this.fValidator = validator;
		setInstanceAttr(instanceAttr);
		setExtensionsAttr(extensionsAttr);
		setNamesAttr(namesAttr);
	}

	private void setNamesAttr(String namesAttr) {
		fNames = processString(namesAttr);
	}

	private String[] processString(String attribute) {
		if (attribute == null) {
			fNames = null;
			return null;
		}
		String[] split = attribute.split(",");
		List<String> atts = new ArrayList<String>();
		for (String string : split) {
			atts.add(string.trim());
		}
		Collections.sort(atts);
		return atts.toArray(new String[atts.size()]);
	}

	private void setExtensionsAttr(String extensionsAttr) {
		fExtensions = processString(extensionsAttr);
	}

	private void setInstanceAttr(String instanceAttr) {
		if (instanceAttr == null) {
			fInstanceOfClass = null;
			return;
		}
		try {
			fInstanceOfClass = Class.forName(instanceAttr);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean enablesFor(Object obj) {
		if (obj instanceof IResource) {
			return enablesFor((IResource)obj);
		}
		if (obj instanceof SharedElement) {
			String fileName = ((SharedElement) obj).getFileName();
			int idx = fileName.lastIndexOf('.');
			if (idx > -1) {
				String ext = fileName.substring(idx+1);
				if (Arrays.binarySearch(fExtensions, ext) < 0) {
					return false;
				}
				return true;
			}
		}
		return false;
	}
	
	private boolean enablesFor(IResource resource) {
		if (fNames != null) {
			if (Arrays.binarySearch(fNames, resource.getName()) < 0) {
				return false;
			}
		}
		if (fExtensions != null) {
			if (resource.getFileExtension() == null) {
				return false;
			}
			if (Arrays.binarySearch(fExtensions, resource.getFileExtension()) < 0) {
				return false;
			}
		}
		if (fInstanceOfClass != null) {
			if (!fInstanceOfClass.isAssignableFrom(resource.getClass())) {
				return false;
			}
		}
		if (resource instanceof IFile) {
			if (IndexUtils.isProjectLibType((IFile)resource)) {
				return false;
			}
		}
		return true;
	}

	public boolean validate(ValidationContext validationContext) {
		return fValidator.validate(validationContext);
	}
	
}
