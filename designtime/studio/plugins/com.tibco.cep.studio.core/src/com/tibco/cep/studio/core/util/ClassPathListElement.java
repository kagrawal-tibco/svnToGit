package com.tibco.cep.studio.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IAccessRule;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import com.tibco.cep.studio.core.StudioCorePlugin;

public class ClassPathListElement {

	public static final String SOURCE_ATTACHMENT = "sourcepath"; //$NON-NLS-1$
	public static final String OUTPUT = "output"; //$NON-NLS-1$
	public static final String INCLUSION = "inclusion"; //$NON-NLS-1$
	public static final String EXCLUSION = "exclusion"; //$NON-NLS-1$

	public static final String ACCESSRULES = "accessrules"; //$NON-NLS-1$
	public static final String COMBINE_ACCESSRULES = "combineaccessrules"; //$NON-NLS-1$
	public static final String JAVADOC = "javadoc_location"; //$NON-NLS-1$
	public static final String NATIVE_LIB_PATH = "org.eclipse.jdt.launching" + ".CLASSPATH_ATTR_LIBRARY_PATH_ENTRY"; //$NON-NLS-1$
	private static final List<String> archiveExtns = Arrays.asList(new String[] { "zip", "jar" });//$NON-NLS-1$

	private IJavaProject fJavaProject;
	private int fEntryKind;
	private IPath fPath, fOrginalPath;
	private IResource fResource;
	private boolean fIsExported;
	private boolean fIsMissing;

	private Object fParentContainer;

	private IClasspathEntry fCachedEntry;
	/**
	 * List of {@link ClassPathListElement} and
	 * {@link ClassPathListElementAttribute}.
	 */
	private ArrayList<Object> fChildren;
	private IPath fLinkTarget, fOrginalLinkTarget;

	public ClassPathListElement(Object parent, IJavaProject project, int entryKind, IPath path, boolean newElement, IResource res, IPath linkTarget) {
		fJavaProject = project;
		fEntryKind = entryKind;
		fPath = path;
		fOrginalPath = newElement ? null : path;
		fLinkTarget = linkTarget;
		fOrginalLinkTarget = linkTarget;
		fChildren = new ArrayList<Object>();
		fResource = res;
		fIsExported = false;

		fIsMissing = false;
		fCachedEntry = null;
		fParentContainer = parent;

		switch (entryKind) {
		case IClasspathEntry.CPE_SOURCE:
			createAttribute(OUTPUT, null, true);
			createAttribute(INCLUSION, new Path[0], true);
			createAttribute(EXCLUSION, new Path[0], true);
			createAttribute(NATIVE_LIB_PATH, null, false);
			break;
		case IClasspathEntry.CPE_LIBRARY:
		case IClasspathEntry.CPE_VARIABLE:
			createAttribute(SOURCE_ATTACHMENT, null, true);
			createAttribute(JAVADOC, null, false);
			createAttribute(NATIVE_LIB_PATH, null, false);
			createAttribute(ACCESSRULES, new IAccessRule[0], true);
			break;
		case IClasspathEntry.CPE_PROJECT:
			createAttribute(ACCESSRULES, new IAccessRule[0], true);
			createAttribute(COMBINE_ACCESSRULES, Boolean.FALSE, true); // not
																		// rendered
			createAttribute(NATIVE_LIB_PATH, null, false);
			break;
		case IClasspathEntry.CPE_CONTAINER:
			createAttribute(ACCESSRULES, new IAccessRule[0], true);
			try {
				IClasspathContainer container = JavaCore.getClasspathContainer(fPath, fJavaProject);
				if (container != null) {
					IClasspathEntry[] entries = container.getClasspathEntries();
					if (entries != null) { // catch invalid container
											// implementation
						for (int i = 0; i < entries.length; i++) {
							IClasspathEntry entry = entries[i];
							if (entry != null) {
								ClassPathListElement curr = createFromExisting(this, entry, fJavaProject);
								fChildren.add(curr);
							} else {
								StudioCorePlugin.logErrorMessage("Null entry in container '" + fPath + "'"); //$NON-NLS-1$//$NON-NLS-2$
							}
						}
					} else {
						StudioCorePlugin.logErrorMessage("container returns null as entries: '" + fPath + "'"); //$NON-NLS-1$//$NON-NLS-2$
					}
				}
			} catch (JavaModelException e) {
			}
			createAttribute(NATIVE_LIB_PATH, null, false);
			break;
		default:
		}
	}

	private ClassPathListElement createFromExisting(ClassPathListElement parent, IClasspathEntry curr, IJavaProject project) {
		return create(parent, curr, false, project);
	}
	
	public static ClassPathListElement createFromExisting(IClasspathEntry curr, IJavaProject javaProject) {
		return create(curr,false,javaProject);
	}

	private static ClassPathListElement create(IClasspathEntry curr, boolean newElement, IJavaProject javaProject) {
		return create(null,curr,newElement,javaProject);
	}

	public static ClassPathListElement create(Object parent, IClasspathEntry curr, boolean newElement, IJavaProject project) {
		IPath path = curr.getPath();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		// get the resource
		IResource res = null;
		boolean isMissing = false;
		IPath linkTarget = null;

		switch (curr.getEntryKind()) {
		case IClasspathEntry.CPE_CONTAINER:
			try {
				isMissing = project != null && (JavaCore.getClasspathContainer(path, project) == null);
			} catch (JavaModelException e) {
				isMissing = true;
			}
			break;
		case IClasspathEntry.CPE_VARIABLE:
			IPath resolvedPath = JavaCore.getResolvedVariablePath(path);
			isMissing = root.findMember(resolvedPath) == null && !resolvedPath.toFile().exists();
			break;
		case IClasspathEntry.CPE_LIBRARY:
			res = root.findMember(path);
			if (res == null) {
				if (!isArchivePath(path)) {
					if (root.getWorkspace().validatePath(path.toString(), IResource.FOLDER).isOK() && root.getProject(path.segment(0)).exists()) {
						res = root.getFolder(path);
					}
				}

				IPath rawPath = path;
				if (project != null) {
					IPackageFragmentRoot[] roots = project.findPackageFragmentRoots(curr);
					if (roots.length == 1)
						rawPath = roots[0].getPath();
				}
				isMissing = !rawPath.toFile().exists(); // look for external
														// JARs and folders
			} else if (res.isLinked()) {
				linkTarget = res.getLocation();
			}
			break;
		case IClasspathEntry.CPE_SOURCE:
			path = path.removeTrailingSeparator();
			res = root.findMember(path);
			if (res == null) {
				if (root.getWorkspace().validatePath(path.toString(), IResource.FOLDER).isOK()) {
					res = root.getFolder(path);
				}
				isMissing = true;
			} else if (res.isLinked()) {
				linkTarget = res.getLocation();
			}
			break;
		case IClasspathEntry.CPE_PROJECT:
			res = root.findMember(path);
			isMissing = (res == null);
			break;
		}
		ClassPathListElement elem = new ClassPathListElement(parent, project, curr.getEntryKind(), path, newElement, res, linkTarget);
		elem.setExported(curr.isExported());
		elem.setAttribute(SOURCE_ATTACHMENT, curr.getSourceAttachmentPath());
		elem.setAttribute(OUTPUT, curr.getOutputLocation());
		elem.setAttribute(EXCLUSION, curr.getExclusionPatterns());
		elem.setAttribute(INCLUSION, curr.getInclusionPatterns());
		elem.setAttribute(ACCESSRULES, curr.getAccessRules());
		elem.setAttribute(COMBINE_ACCESSRULES, new Boolean(curr.combineAccessRules()));

		IClasspathAttribute[] extraAttributes = curr.getExtraAttributes();
		for (int i = 0; i < extraAttributes.length; i++) {
			IClasspathAttribute attrib = extraAttributes[i];
			ClassPathListElementAttribute attribElem = elem.findAttributeElement(attrib.getName());
			if (attribElem == null) {
				elem.createAttribute(attrib.getName(), attrib.getValue(), false);
			} else {
				attribElem.setValue(attrib.getValue());
			}
		}

		elem.setIsMissing(isMissing);
		return elem;
	}
	
	private static boolean isFiltered(Object entry, String[] filteredKeys) {
		if (entry instanceof ClassPathListElementAttribute) {
			ClassPathListElementAttribute curr= (ClassPathListElementAttribute) entry;
			String key= curr.getKey();
			for (int i= 0; i < filteredKeys.length; i++) {
				if (key.equals(filteredKeys[i])) {
					return true;
				}
			}
			if (curr.isNotSupported()) {
				return true;
			}
			if (!curr.isBuiltIn() && !key.equals(ClassPathListElement.JAVADOC) && 
					!key.equals(ClassPathListElement.NATIVE_LIB_PATH)) {
				return !readExtensions().containsKey(key);
			}
		}
		return false;
	}
	
	private static HashMap<String, Descriptor> readExtensions() {
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.jdt.ui", "classpathAttributeConfiguration");
		HashMap<String, Descriptor> descriptors= new HashMap<String, Descriptor>(elements.length * 2);
		for (int i= 0; i < elements.length; i++) {
			try {
				Descriptor curr= new Descriptor(elements[i]);
				descriptors.put(curr.getKey(), curr);
			} catch (CoreException e) {
				StudioCorePlugin.log(e);
			}
		}
		return descriptors;
	}
	
	public static class Descriptor {
		private IConfigurationElement fConfigElement;

		private static final String ATT_NAME = "attributeName"; //$NON-NLS-1$
		private static final String ATT_CLASS = "class"; //$NON-NLS-1$
		public Descriptor(IConfigurationElement iConfigurationElement) throws CoreException {
			fConfigElement = iConfigurationElement;

			String name = iConfigurationElement.getAttribute(ATT_NAME);
			String pageClassName = iConfigurationElement.getAttribute(ATT_CLASS);

			if (name == null) {
				throw new CoreException(new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID, 0, "Invalid extension (missing attributeName)", null)); //$NON-NLS-1$
			}
			if (pageClassName == null) {
				throw new CoreException(new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID, 0, "Invalid extension (missing class name): " + name, null)); //$NON-NLS-1$
			}
		}

		public String getKey() {
				return fConfigElement.getAttribute(ATT_NAME);
		}
		
	}
	
	private Object[] getFilteredChildren(String[] filteredKeys) {
		int nChildren= fChildren.size();
		ArrayList<Object> res= new ArrayList<Object>(nChildren);

		for (int i= 0; i < nChildren; i++) {
			Object curr= fChildren.get(i);
			if (!isFiltered(curr, filteredKeys)) {
				res.add(curr);
			}
		}
		return res.toArray();
	}
	
	public Object[] getChildren(boolean hideOutputFolder) {
		if (hideOutputFolder && fEntryKind == IClasspathEntry.CPE_SOURCE) {
			return getFilteredChildren(new String[] { OUTPUT });
		}
		/*if (isInContainer(JavaRuntime.JRE_CONTAINER)) {
			return getFilteredChildren(new String[] { COMBINE_ACCESSRULES, NATIVE_LIB_PATH });
		}*/
		if (fEntryKind == IClasspathEntry.CPE_PROJECT) {
			return getFilteredChildren(new String[] { COMBINE_ACCESSRULES });
		}
		return getFilteredChildren(new String[0]);
	}

	public ClassPathListElementAttribute findAttributeElement(String key) {
		for (int i = 0; i < fChildren.size(); i++) {
			Object curr = fChildren.get(i);
			if (curr instanceof ClassPathListElementAttribute) {
				ClassPathListElementAttribute elem = (ClassPathListElementAttribute) curr;
				if (key.equals(elem.getKey())) {
					return elem;
				}
			}
		}
		return null;
	}

	public ClassPathListElementAttribute setAttribute(String key, Object value) {
		ClassPathListElementAttribute attribute = findAttributeElement(key);
		if (attribute == null) {
			return null;
		}
		if (key.equals(EXCLUSION) || key.equals(INCLUSION)) {
			Assert.isTrue(value != null || fEntryKind != IClasspathEntry.CPE_SOURCE);
		}

		if (key.equals(ACCESSRULES)) {
			Assert.isTrue(value != null || fEntryKind == IClasspathEntry.CPE_SOURCE);
		}
		if (key.equals(COMBINE_ACCESSRULES)) {
			Assert.isTrue(value instanceof Boolean);
		}

		attribute.setValue(value);
		return attribute;

	}

	public boolean addToExclusions(IPath path) {
		String key= ClassPathListElement.EXCLUSION;
		return addFilter(path, key);
	}

	public boolean addToInclusion(IPath path) {
		String key= ClassPathListElement.INCLUSION;
		return addFilter(path, key);
	}

	public boolean removeFromExclusions(IPath path) {
		String key= ClassPathListElement.EXCLUSION;
		return removeFilter(path, key);
	}

	public boolean removeFromInclusion(IPath path) {
		String key= ClassPathListElement.INCLUSION;
		return removeFilter(path, key);
	}
	
	public Object getAttribute(String key) {
		ClassPathListElementAttribute attrib= findAttributeElement(key);
		if (attrib != null) {
			return attrib.getValue();
		}
		return null;
	}

	private boolean addFilter(IPath path, String key) {
		IPath[] filters= (IPath[]) getAttribute(key);
		if (filters == null)
			return false;

		if (!isExcludedPath(path, filters)) {
			IPath toAdd= path.removeFirstSegments(getPath().segmentCount()).addTrailingSeparator();
			IPath[] newFilters= new IPath[filters.length + 1];
			System.arraycopy(filters, 0, newFilters, 0, filters.length);
			newFilters[filters.length]= toAdd;
			setAttribute(key, newFilters);
			return true;
		}
		return false;
	}

	private boolean removeFilter(IPath path, String key) {
		IPath[] filters= (IPath[]) getAttribute(key);
		if (filters == null)
			return false;

		IPath toRemove= path.removeFirstSegments(getPath().segmentCount()).addTrailingSeparator();
		if (isExcludedPath(toRemove, filters)) {
			List<IPath> l= new ArrayList<IPath>(Arrays.asList(filters));
			l.remove(toRemove);
			IPath[] newFilters= l.toArray(new IPath[l.size()]);
			setAttribute(key, newFilters);
			return true;
		}
		return false;
	}

	private boolean isExcludedPath(IPath resourcePath, IPath[] exclusionPatterns) {
		String path = resourcePath.toString();
		for (int i = 0, length = exclusionPatterns.length; i < length; i++) {
			String pattern= exclusionPatterns[i].toString();
			StringPatternMatcher patternMatcher = new StringPatternMatcher(pattern, false, false);
			if(patternMatcher.isPathPattern()) {
				if (patternMatcher.match(path)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public IStatus getContainerChildStatus(ClassPathListElementAttribute attrib) {
		if (fParentContainer instanceof ClassPathListElement) {
			ClassPathListElement parent= (ClassPathListElement) fParentContainer;
			if (parent.getEntryKind() == IClasspathEntry.CPE_CONTAINER) {
				return parent.evaluateContainerChildStatus(attrib);
			}
			return ((ClassPathListElement) fParentContainer).getContainerChildStatus(attrib);
		}
		return null;
	}
	
	private IStatus evaluateContainerChildStatus(ClassPathListElementAttribute attrib) {
		if (fJavaProject != null) {
			ClasspathContainerInitializer initializer= JavaCore.getClasspathContainerInitializer(fPath.segment(0));
			if (initializer != null && initializer.canUpdateClasspathContainer(fPath, fJavaProject)) {
				if (attrib.isBuiltIn()) {
					if (ClassPathListElement.SOURCE_ATTACHMENT.equals(attrib.getKey())) {
						return initializer.getSourceAttachmentStatus(fPath, fJavaProject);
					} else if (ClassPathListElement.ACCESSRULES.equals(attrib.getKey())) {
						return initializer.getAccessRulesStatus(fPath, fJavaProject);
					}
				} else {
					return initializer.getAttributeStatus(fPath, fJavaProject, attrib.getKey());
				}
			}
			return new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID, ClasspathContainerInitializer.ATTRIBUTE_READ_ONLY, "", null); //$NON-NLS-1$
		}
		return null;
	}

	private void setExported(boolean exported) {
		if (exported != fIsExported) {
			fIsExported = exported;
		}
	}

	private void setIsMissing(boolean isMissing) {
		fIsMissing = isMissing;

	}

	public boolean isMissing() {
		return fIsMissing;
	}

	public boolean isExported() {
		return fIsExported;
	}
	
	public int getEntryKind() {
		return fEntryKind;
	}

	private static boolean isArchivePath(IPath path) {

		String ext = path.getFileExtension();
		if (ext != null && ext.length() != 0) {
			return archiveExtns.contains(ext);
		}
		return false;
	}

	private void createAttribute(String key, Object value, boolean builtIn) {
		fChildren.add(new ClassPathListElementAttribute(this, key, value, builtIn));

	}

	public IPath getPath() {
		return fPath;
	}

	

}
