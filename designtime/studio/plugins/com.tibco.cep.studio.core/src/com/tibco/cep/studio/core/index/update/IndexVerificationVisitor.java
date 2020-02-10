package com.tibco.cep.studio.core.index.update;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.tibco.cep.studio.core.index.DefaultStructuredElementVisitor;
import com.tibco.cep.studio.core.index.model.StructuredElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class IndexVerificationVisitor extends DefaultStructuredElementVisitor {

	private String fProjectName;
	private HashMap<IFile, TypeElement> fProcessedFiles = new HashMap<IFile, TypeElement>();
	private List<IResource> fInvalidResources = new ArrayList<IResource>();
	private HashMap<IFile, List<TypeElement>> fDuplicateElements = new HashMap<IFile, List<TypeElement>>();
	
	public IndexVerificationVisitor(String projectName) {
		this.fProjectName = projectName;
	}

	@Override
	public boolean visit(StructuredElement element) {
		if (!(element instanceof TypeElement)) {
			return super.visit(element);
		}
		TypeElement typeElement = (TypeElement) element;
		Date lastModified = typeElement.getLastModified();
		IFile file = IndexUtils.getFile(fProjectName, typeElement);
		if (file == null) {
			// either the element is a binary/referenced one, or perhaps the file was removed from the file system?
			return true;
		}
		long modificationStamp = file.getLocalTimeStamp();
		long elementLastModStamp = lastModified == null ? 0 : lastModified.getTime();
		if (modificationStamp > elementLastModStamp) {
			if (!fInvalidResources.contains(file)) {
				fInvalidResources.add(file);
			}
		}
		if (fProcessedFiles.containsKey(file)) {
			// duplicate entry in index, mark for removal
			List<TypeElement> list = fDuplicateElements.get(file);
			if (list == null) {
				TypeElement dupElement = fProcessedFiles.get(file);
				list = new ArrayList<TypeElement>();
				list.add(dupElement);
				list.add(typeElement);
				fDuplicateElements.put(file, list);
			} else {
				list.add(typeElement);
			}
		} else {
			fProcessedFiles.put(file, typeElement);
		}
		return true;
	}

	public HashMap<IFile, List<TypeElement>> getDuplicateElements() {
		return fDuplicateElements;
	}

	public List<IResource> getInvalidResources() {
		return fInvalidResources;
	}

	
	
}
