package com.tibco.cep.studio.dashboard.ui.refactoring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationFactory;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.states.StatesFactory;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.refactoring.EntityRefactoringParticipant;
import com.tibco.cep.studio.core.search.ISearchParticipant;
import com.tibco.cep.studio.core.search.SearchResult;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.DashboardCoreResourceUtils;
import com.tibco.cep.studio.dashboard.core.util.LocalECoreUtils;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.search.DashboardSearchParticipant;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public abstract class DashboardRefactoringParticipant extends EntityRefactoringParticipant<BEViewsElement> {

	private static final boolean SUPPORT_DELETE_REFACTORING = true;

	protected String[] supportedExtensions;

	protected List<String> interestingElements;

	protected DashboardRefactoringParticipant(String supportedExtension, String... interestingElements) {
		this.supportedExtensions = new String[] { supportedExtension };
		this.interestingElements = Arrays.asList(interestingElements);
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm, CheckConditionsContext context) throws OperationCanceledException {
		if (!isValidProject()) {
			return null;
		}
		IResource resource = getResource();
		if (isDeleteRefactor()) {
			// we are dealing with a delete
			if (!(resource instanceof IFile)) {
				return super.checkConditions(pm, context);
			}
			// are we capable of handling the resource ?
			if (isSupportedResource((IFile) getResource())) {
				// yes, so lets check conditions
				return super.checkConditions(pm, context);
			}
			// we are not capable of handling the resource, return null
			return null;
		}
		if (!(resource instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) resource;
		String elementType = LocalECoreUtils.fileToElementType(file);
		if (elementType != null && isElementOfInterest(elementType)) {
			return super.checkConditions(pm, context);
		}
		if (elementType == null && isExtensionOfInterest(file.getFileExtension())) {
			return super.checkConditions(pm, context);
		}
		return new RefactoringStatus();
	}

	@Override
	protected String[] getSupportedExtensions() {
		return supportedExtensions;
	}

	@Override
	protected BEViewsElement preProcessEntityChange(BEViewsElement renameParticipant) {
		if (isRenameRefactor()) {
			//Modified by Anand to fix BE-11269 on 04/12/2011
			List<EAttribute> attributes = renameParticipant.eClass().getEAllAttributes();
			for (EAttribute attribute : attributes) {
				if (attribute.getName().equals("displayName") == true) {
					String displayName = (String) renameParticipant.eGet(attribute);
					if (renameParticipant.getName().equals(displayName) == true) {
						renameParticipant.eSet(attribute, getNewElementName());
						break;
					}
				}
			}
			return renameParticipant;
		}
		preProcessMoveEntityChange(renameParticipant);
		return renameParticipant;
	}

	protected void preProcessMoveEntityChange(BEViewsElement moveParticipant) {
		// Do sub element folder and namespace changes here too
		String newElementPath = getNewElementPath();
		updateFolderNamespaceAll(moveParticipant, newElementPath, newElementPath);
	}

	private void updateFolderNamespaceAll(EObject eObject, String folder, String namespace) {
		TreeIterator<Object> contents = EcoreUtil.getAllProperContents(eObject, false);
		while (contents.hasNext()) {
			Object object = (Object) contents.next();
			if (object instanceof EList) {
				EList<?> elements = (EList<?>) object;
				for (Iterator<?> iterator = elements.iterator(); iterator.hasNext();) {
					Object item = (Object) iterator.next();
					if (item instanceof BEViewsElement) {
						updateFolderNamespace((BEViewsElement) item, folder, namespace);
					} else if (item instanceof PropertyDefinition) {
						updateFolderNamespace((PropertyDefinition) item, folder, namespace);
					}
				}
			} else if (object instanceof BEViewsElement) {
				updateFolderNamespace((BEViewsElement) object, folder, namespace);
			} else if (object instanceof PropertyDefinition) {
				updateFolderNamespace((PropertyDefinition) object, folder, namespace);
			}
		}
	}

	private void updateFolderNamespace(Entity entity, String folder, String namespace) {
		entity.setFolder(folder);
		entity.setNamespace(namespace);
	}

	private void updateFolderNamespace(PropertyDefinition propertyDefinition, String folder, String namespace) {
		propertyDefinition.setFolder(folder);
		propertyDefinition.setNamespace(namespace);
	}

	@Override
	public Change processEntity(Object elementToRefactor, String projectName, IProgressMonitor pm, boolean preChange) throws CoreException, OperationCanceledException {
		try {
			if (isDeleteRefactor() && SUPPORT_DELETE_REFACTORING == false) {
				return new NullChange();
			}
			if (elementToRefactor instanceof EntityElement) {
				elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
			}
			if (!shouldUpdateReferences()) {
				System.out.println("not updating references");
				return null;
			}
			CompositeChange compositeChange = new CompositeChange(changeTitle());
			if (isProjectRefactor() && elementToRefactor instanceof EObject) {
				try {
					updateProjectReference((EObject) elementToRefactor, projectName, compositeChange);
					if (compositeChange.getChildren() == null || compositeChange.getChildren().length == 0) {
						return null;
					}
					return compositeChange;
				} catch (Exception e) {
					throw new CoreException(new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "Failed to handle changes to " + projectName + " for " + supportedExtensions, e));
				}
			}
			IResource resource = getResource();
			if (!(resource instanceof IFile)) {
				return null;
			}
			IFile file = (IFile) resource;
			String elementType = LocalECoreUtils.fileToElementType(file);
			if ((elementType != null && isElementOfInterest(elementType)) || (elementType == null && isExtensionOfInterest(file.getFileExtension()))) {
				Entity entityToRefactor = (Entity) elementToRefactor;
				try {
					if (isDeleteRefactor() == true && SUPPORT_DELETE_REFACTORING == true) {
						deleteReferences(entityToRefactor, projectName, compositeChange);
					} else {
						updateReferences(entityToRefactor, projectName, compositeChange);
					}
				} catch (Exception e) {
					throw new CoreException(new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "Failed to handle changes to " + file.getFullPath() + " for " + supportedExtensions, e));
				}
			}
			if (compositeChange.getChildren() != null && compositeChange.getChildren().length > 0) {
				return compositeChange;
			}
			return null;
		} catch (CoreException e) {
			DashboardUIPlugin.getInstance().getLog().log(e.getStatus());
			throw e;
		} catch (RuntimeException e) {
			DashboardUIPlugin.getInstance().getLog().log(
					new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "Failed to handle changes " + getResource().getFullPath() + " for " + supportedExtensions, e));
			throw e;
		}
	}

	protected EObject createProxyToNewPath(URI refactoredURI, URI baseURI, EClass eClass) {
		EObject proxyObject = null;
		if (eClass.getName().equals("Metric")) {
			proxyObject = ElementFactory.eINSTANCE.create(eClass);
		} else if (eClass.getName().equals("PropertyDefinition")) {
			proxyObject = ElementFactory.eINSTANCE.create(eClass);
		} else if (eClass.getName().equals("StateMachine")) {
			proxyObject = StatesFactory.eINSTANCE.create(eClass);
		} else if (eClass.getName().equals("State")) {
			proxyObject = StatesFactory.eINSTANCE.create(eClass);
		} else {
			proxyObject = BEViewsConfigurationFactory.eINSTANCE.create(eClass);
		}
		URI proxyUri = refactoredURI.deresolve(baseURI);
		((InternalEObject) proxyObject).eSetProxyURI(proxyUri);
		return proxyObject;
	}

	protected boolean checkEquals(EObject eObject, EObject otherEObject) {
		if (eObject == null && otherEObject == null) {
			throw new IllegalArgumentException("Both eObject and otherEObject cannot be null");
		}
		if (eObject == null || otherEObject == null) {
			return false;
		}
		URI eURI = EcoreUtil.getURI(eObject);
		URI otherEURI = EcoreUtil.getURI(otherEObject);
		if (eURI == null && otherEURI == null) {
			throw new IllegalStateException("Computed URI's for both eObject and otherEObject are be null");
		}
		if (eURI == null && otherEURI == null) {
			return false;
		}
		return eURI.equals(otherEURI);
		// EObject resolved = (EObject) EcoreUtil.resolve(otherEObject,
		// eObject);
		// if (resolved.eIsProxy()) {
		// // Check what to do here
		// EObjectImpl eImplOther = (EObjectImpl) otherEObject;
		// URI otherProxyURI = eImplOther.eProxyURI();
		// EObjectImpl eImplRefactored = (EObjectImpl) eObject;
		// URI refactoredProxyURI = eImplRefactored.eProxyURI();
		// return otherProxyURI.equals(refactoredProxyURI);
		// }
		// return eObject.getGUID().equals(((Entity) resolved).getGUID());
	}

	protected URI createRefactoredURI(EObject element) {
		boolean renameEntity = isRenameRefactor();
		boolean moveEntity = isMoveRefactor();

		URI currentUri = EcoreUtil.getURI(element);
		String fragment = currentUri.fragment();
		if (fragment == null || fragment.length() == 0) {
			fragment = "/";
		}
		URI newUri = null;
		if (renameEntity) {
			String fileExtension = currentUri.fileExtension();
			String newName = getNewElementName();
			newUri = currentUri.trimSegments(1).appendSegment(newName + "." + fileExtension);
		}
		if (moveEntity) {
			IResource parentResource;
			String newPath = getNewElementPath();
			if (newPath == null || newPath.length() == 0 || newPath.equals("/")) {
				parentResource = getResource().getProject();
			} else {
				parentResource = getResource().getProject().getFolder(newPath);
			}

			URI parentURI = URI.createURI(ResourceHelper.getLocationURI(parentResource).toString());
			newUri = parentURI.appendSegment(currentUri.lastSegment());
		}
		newUri = newUri.appendFragment(fragment);
		return newUri;
	}

	/**
	 * This is to handle delete cases.
	 */
	@Override
	protected ISearchParticipant getSearchParticipant() {
		return new DashboardSearchParticipant();
	}

	protected boolean isExtensionOfInterest(String extension) {
		return false;
	}

	protected abstract String changeTitle();

	protected boolean isElementOfInterest(String elementType) {
		return interestingElements.contains(elementType);
	}

	protected abstract void updateReferences(EObject object, String projectName, CompositeChange compositeChange) throws Exception;

	protected void deleteReferences(EObject object, String projectName, CompositeChange compositeChange) throws Exception {
		List<String> extensions = Arrays.asList(getSupportedExtensions());
		SearchResult searchResult = getSearchParticipant().search(object, projectName, null, new NullProgressMonitor());
		List<EObject> exactMatches = searchResult.getExactMatches();
		List<EObject> family = null;
		for (EObject match : exactMatches) {
			String extension = LocalECoreUtils.getExtensionFor(match);
			if (extensions.contains(extension) == true) {
				if (family == null) {
					family = getFamily(object);
				}
				Entity matchCopy = (Entity) EcoreUtil.copy(match);
				EcoreUtil.resolveAll(matchCopy);
				for (EObject member : family) {
					removeReference(matchCopy, member);
				}
				Change change = createTextFileChange(IndexUtils.getFile(projectName, matchCopy), matchCopy);
				compositeChange.add(change);
			}
		}
	}

	protected List<EObject> getFamily(EObject object) throws Exception {
		URI uri = EcoreUtil.getURI(object);
		if ("system".equals(uri.fileExtension()) == true) {
			return DashboardCoreResourceUtils.loadMultipleElements(uri.toFileString());
		}
		return Arrays.asList(object);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void removeReference(EObject eParentObject, EObject deletedEObject) {
		EList<EStructuralFeature> allStructuralFeatures = eParentObject.eClass().getEAllStructuralFeatures();
		for (EStructuralFeature structuralFeature : allStructuralFeatures) {
			if (structuralFeature instanceof EReference) {
				EReference referenceFeature = (EReference) structuralFeature;
				boolean isTypeMatching = referenceFeature.getEReferenceType().equals(deletedEObject.eClass());
				if (isTypeMatching == false) {
					isTypeMatching = deletedEObject.eClass().getEAllSuperTypes().contains(referenceFeature.getEReferenceType());
				}
				boolean isContainment = referenceFeature.isContainment();
				boolean isMany = referenceFeature.isMany();
				//System.err.println("DashboardRefactoringParticipant.removeReference()::referenceFeature[name="+referenceFeature.getName()+",type="+referenceFeature.getEReferenceType().getName()+",isTypeMatching = "+isTypeMatching+",isContainment = "+isContainment+",isMany = "+isMany+"]");
				if (isTypeMatching == true) {
					if (isMany == true) {
						EList<?> eChildren = (EList<?>) eParentObject.eGet(referenceFeature, true);
						ListIterator<?> listIterator = eChildren.listIterator();
						while (listIterator.hasNext()) {
							EObject eChild = (EObject) listIterator.next();
							if (checkEquals(deletedEObject, eChild) == true) {
								System.err.println("DashboardRefactoringParticipant.removeReference()::Removing "+deletedEObject+" from "+eParentObject+" referenced against "+referenceFeature.getName());
								listIterator.remove();
								eParentObject.eSet(referenceFeature, new BasicEList(eChildren));
								break;
							}
						}
					} else {
						if (checkEquals((EObject) eParentObject.eGet(referenceFeature), deletedEObject) == true) {
							System.err.println("DashboardRefactoringParticipant.removeReference()::Unsetting "+deletedEObject+" from "+eParentObject+" referenced against "+referenceFeature.getName());
							eParentObject.eUnset(referenceFeature);
						}
					}
				} else if (isContainment == true) {
					if (isMany == true) {
						EList<?> eChildren = (EList<?>) eParentObject.eGet(referenceFeature, true);
						for (Object eChild : eChildren) {
							removeReference((EObject) eChild, deletedEObject);
						}
					} else {
						EObject eChild = (EObject) eParentObject.eGet(referenceFeature, true);
						if (eChild != null) {
							removeReference(eChild, deletedEObject);
						}
					}
				}
			}
		}
	}

	@Override
	protected void pasteEntity(String newName, Entity entity, IResource source, IContainer target, boolean overwrite) throws CoreException {
		if (target.getProject().getName().equals(entity.getOwnerProjectName()) == false) {
			// we are processing a paste across projects, update the reference paths in the entity
			updateReferencePaths(entity, source, target);
		}
		super.pasteEntity(newName, entity, source, target, overwrite);
	}

	@SuppressWarnings("unchecked")
	private void updateReferencePaths(Entity entity, IResource source, IContainer target) {
		IProject sourceProject = source.getProject();
		IProject targetProject = target.getProject();
		URI entityBaseURI = URI.createURI(ResourceHelper.getLocationURI(target).toString()+"/");
		EList<EStructuralFeature> allStructuralFeatures = entity.eClass().getEAllStructuralFeatures();
		for (EStructuralFeature structuralFeature : allStructuralFeatures) {
			if (structuralFeature instanceof EReference) {
				EReference referenceFeature = (EReference) structuralFeature;
				referenceFeature.getEReferenceType();
				if (referenceFeature.isContainment() == false) {
					// we are dealing with references
					Object get = entity.eGet(structuralFeature);
					if (get instanceof EObject) {
						EObject getEObject = (EObject) get;
						URI targetRefObjURI = EcoreUtil.getURI(getEObject);
						targetRefObjURI = replaceProjectPath(targetRefObjURI, sourceProject, targetProject);
						EObject proxyEObject = createProxyToNewPath(targetRefObjURI, entityBaseURI, getEObject.eClass());
						entity.eSet(structuralFeature, proxyEObject);
					} else if (get instanceof List) {
						List<EObject> existingList = (List<EObject>) get;
						BasicEList<EObject> newList = new BasicEList<EObject>();
						for (EObject eObject : existingList) {
							URI targetRefObjURI = EcoreUtil.getURI((EObject) eObject);
							targetRefObjURI = replaceProjectPath(targetRefObjURI, sourceProject, targetProject);
							newList.add(createProxyToNewPath(targetRefObjURI, entityBaseURI, eObject.eClass()));
						}
						entity.eSet(structuralFeature, newList);
					}
				} else {
					Object get = entity.eGet(structuralFeature);
					if (get instanceof Entity) {
						updateReferencePaths((Entity) get, source, target);
					} else if (get instanceof List) {
						List<EObject> existingList = (List<EObject>) get;
						for (EObject eObject : existingList) {
							if (eObject instanceof Entity) {
								updateReferencePaths((Entity) eObject, source, target);
							}
						}
					}
				}
			}
		}
	}

	private URI replaceProjectPath(URI uri, IProject currentProject, IProject replacingProject) {
		// deresolve the absolute URI
		URI deresolvedURI = uri.deresolve(URI.createURI(ResourceHelper.getLocationURI(currentProject).toString()));
		// remove the project segment
		List<String> segmentsList = new ArrayList<String>(deresolvedURI.segmentsList());
		segmentsList.remove(0);
		//create a new URI with replacing project
		URI newURI = URI.createURI(ResourceHelper.getLocationURI(replacingProject).toString());
		//append remaining segments from old uri
		newURI = newURI.appendSegments(segmentsList.toArray(new String[segmentsList.size()]));
		//append the fragment if any
		if (uri.hasFragment() == true) {
			newURI = newURI.appendFragment(uri.fragment());
		}
		return newURI;
	}

	protected void updateProjectReference(EObject object, String projectName, CompositeChange compositeChange) throws Exception {
		LocalECoreFactory coreFactory = LocalECoreFactory.getInstance(getResource().getProject());
		String[] extensions = getSupportedExtensions();
		for (String extension : extensions) {
			String type = BEViewsElementNames.getType(extension);
			List<LocalElement> children = coreFactory.getChildren(type);
			for (LocalElement child : children) {
				Entity childEObject = (Entity) child.getEObject();
				Entity copyChildEObject = (Entity) EcoreUtil.copy(childEObject);
				processChildren(projectName, copyChildEObject, getNewElementName());
				Change change = createTextFileChange(IndexUtils.getFile(projectName, copyChildEObject), copyChildEObject);
				compositeChange.add(change);
			}
		}
	}
}
