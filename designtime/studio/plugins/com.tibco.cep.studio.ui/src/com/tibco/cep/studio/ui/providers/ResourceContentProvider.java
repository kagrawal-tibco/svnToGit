package com.tibco.cep.studio.ui.providers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.model.WorkbenchContentProvider;

public class ResourceContentProvider extends WorkbenchContentProvider {

	private String[] fDisplayTypes;

	public ResourceContentProvider() {
		this(null);
	}
	
	public ResourceContentProvider(String[] displayTypes) {
		super();
		this.fDisplayTypes = displayTypes;
		Arrays.sort(fDisplayTypes);
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return super.getElements(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		Object[] elements = super.getChildren(parentElement);
		if (fDisplayTypes == null) {
			return elements;
		}
		List<Object> filteredElements = new ArrayList<Object>();
		for (Object object : elements) {
			if (object instanceof IFile) {
				IFile file = (IFile)object;
				String extension = file.getFileExtension();
				//This can be null for folders (files) like those created by source control systems
				if (extension != null) {
					if (Arrays.binarySearch(fDisplayTypes, extension) >= 0) {
						filteredElements.add(object);
					}
				}
			} else {
				filteredElements.add(object);
			}
		}
		return filteredElements.toArray();
	}

}
