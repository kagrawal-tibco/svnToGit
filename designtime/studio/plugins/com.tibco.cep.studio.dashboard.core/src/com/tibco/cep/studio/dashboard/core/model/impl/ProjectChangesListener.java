package com.tibco.cep.studio.dashboard.core.model.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.search.SearchResult;
import com.tibco.cep.studio.core.search.SearchUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.dashboard.core.DashboardCorePlugIn;
import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalExternalReference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalSkin;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

class ProjectChangesListener implements IResourceChangeListener {

	private LocalECoreFactory localECoreFactory;

	public ProjectChangesListener(LocalECoreFactory localECoreFactory) {
		super();
		this.localECoreFactory = localECoreFactory;
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		if (event.getDelta() == null) {
			return;
		}
		try {
			event.getDelta().accept(new ResourceDeltaVisitor());
		} catch (CoreException e) {
			// should we open a alert here ?
			e.printStackTrace();
			DashboardCorePlugIn.getDefault().getLog().log(e.getStatus());
		}
	}

	private boolean processAllExternalChanges(IResourceDelta delta) throws CoreException {
		IFile file = (IFile) delta.getResource();
		switch (delta.getKind()) {
			case IResourceDelta.NO_CHANGE:
				// do nothing
				break;
			case IResourceDelta.ADDED:
				// do nothing
				break;
			case IResourceDelta.REMOVED:
				if ((delta.getFlags() & IResourceDelta.MOVED_TO) == IResourceDelta.MOVED_TO) {
					// do nothing
					// processFileMove(file,delta.getMovedFromPath());
				} else {
					//do nothing , since a delete will force a re-factor which will update the user of this reference
				}
				break;
			case IResourceDelta.CHANGED:
				if ((delta.getFlags() & IResourceDelta.CONTENT) == IResourceDelta.CONTENT) {
					try {
						Entity persistedElement = (Entity) IndexUtils.loadEObject(ResourceHelper.getLocationURI(file));
						LocalExternalReference externalReference = new LocalExternalReference(persistedElement);
						// content has changed, call updateDependentElements this will update the state machine component
						updateDependentElements(externalReference, externalReference);
						//fire a element changed event
						localECoreFactory.fireElementChanged(externalReference);
					} catch (Exception e) {
						throw new CoreException(new Status(IStatus.ERROR, DashboardCorePlugIn.PLUGIN_ID, "could not process " + file.getProjectRelativePath()
								+ " as a change. It is recommended to close and open the project.", e));
					}
				} else if ((delta.getFlags() & IResourceDelta.SYNC) == IResourceDelta.SYNC) {
					try {
						Entity persistedElement = (Entity) IndexUtils.loadEObject(ResourceHelper.getLocationURI(file));
						LocalExternalReference externalReference = new LocalExternalReference(persistedElement);
						//fire a element changed event
						localECoreFactory.fireElementChanged(externalReference);
						// content has changed, call updateDependentElements this will update the state machine component
						updateDependentElements(externalReference, externalReference);
					} catch (Exception e) {
						throw new CoreException(new Status(IStatus.ERROR, DashboardCorePlugIn.PLUGIN_ID, "could not process " + file.getProjectRelativePath()
								+ " as a change. It is recommended to close and open the project.", e));
					}
				} else if ((delta.getFlags() & IResourceDelta.REPLACED) == IResourceDelta.REPLACED) {
					try {
						Entity persistedElement = (Entity) IndexUtils.loadEObject(ResourceHelper.getLocationURI(file));
						LocalExternalReference externalReference = new LocalExternalReference(persistedElement);
						//fire a element changed event
						localECoreFactory.fireElementChanged(externalReference);
						// content has changed, call updateDependentElements this will update the state machine component
						updateDependentElements(externalReference, externalReference);
					} catch (Exception e) {
						throw new CoreException(new Status(IStatus.ERROR, DashboardCorePlugIn.PLUGIN_ID, "could not process " + file.getProjectRelativePath()
								+ " as a change. It is recommended to close and open the project.", e));
					}
				}
				break;
			case IResourceDelta.ADDED_PHANTOM:
				// don't worry about it right now
			case IResourceDelta.REMOVED_PHANTOM:
				// don't worry about it right now
			default:
				throw new IllegalArgumentException("Unknown delta kind");
		}
		return true;
	}

	private boolean processAllChanges(IResourceDelta delta) throws CoreException {
		IFile file = (IFile) delta.getResource();
		switch (delta.getKind()) {
			case IResourceDelta.NO_CHANGE:
				// do nothing
				break;
			case IResourceDelta.ADDED:
				if ((delta.getFlags() & IResourceDelta.MOVED_FROM) == IResourceDelta.MOVED_FROM) {
					processFileMove(file, delta.getMovedFromPath());
				} else {
					processFileAdd(file);
				}
				break;
			case IResourceDelta.REMOVED:
				if ((delta.getFlags() & IResourceDelta.MOVED_TO) == IResourceDelta.MOVED_TO) {
					// do nothing
					// processFileMove(file,delta.getMovedFromPath());
				} else {
					processFileRemove(file);
				}
				break;
			case IResourceDelta.CHANGED:
				if ((delta.getFlags() & IResourceDelta.CONTENT) == IResourceDelta.CONTENT) {
					// content has changed
					processFileChange(file);
				} else if ((delta.getFlags() & IResourceDelta.SYNC) == IResourceDelta.SYNC) {
					// possibly content has changed
					processFileChange(file);
				} else if ((delta.getFlags() & IResourceDelta.REPLACED) == IResourceDelta.REPLACED) {
					// possible content has changed
					processFileChange(file);
				}
				break;
			case IResourceDelta.ADDED_PHANTOM:
				// don't worry about it right now
			case IResourceDelta.REMOVED_PHANTOM:
				// don't worry about it right now
			default:
				throw new IllegalArgumentException("Unknown delta kind");
		}
		return true;
	}

	private void processFileAdd(IFile file) throws CoreException {
		System.out.println("ProjectChangesListener.processFileAdd(" + file + ")");
		try {
			// check if we are dealing with system skin elements
			String fileExtension = file.getFileExtension();
			if (BEViewsElementNames.getTopLevelElementExtensions().contains(fileExtension) == false) {
				return;
			}
			if ("system".equalsIgnoreCase(fileExtension) == true) {
				// yes, we are so we let localECoreFactory load the system
				// elements and distribute the elements in right particle
				localECoreFactory.loadSystemElements(ResourceHelper.getLocationURI(file).toString());
				System.out.println("ProjectChangesListener.processFileAdd()::Added System Skin Elements");
				// now get all the elements and fire element added for each
				Collection<LocalElement> systemElements = getSystemElements().values();
				for (LocalElement systemElement : systemElements) {
					localECoreFactory.fireElementAdded(systemElement);
				}
			} else {
				// we are dealing with non system element
				// load and convert
				EObject persistedElement = IndexUtils.loadEObject(ResourceHelper.getLocationURI(file));
				LocalElement localElement = LocalECoreFactory.toLocalElement(localECoreFactory, persistedElement);
				// find the right particle based on the element type
				LocalParticle particle = localECoreFactory.getParticle(localElement.getElementType());
				if (particle != null && particle.isInitialized() == true) {
					// we have previously loaded children in the particle, so we
					// need to add it
					// check if we have a previous entry for localElement
					LocalElement existingElement = particle.getElementByID(localElement.getID());
					if (existingElement != null) {
						// refresh the element
						existingElement.refresh(persistedElement);
						System.out.println("ProjectChangesListener.processFileAdd()::Refreshed " + existingElement);
						// fire element change event
						localECoreFactory.fireElementChanged(existingElement);
						// update dependent elements
						updateDependentElements(existingElement, existingElement);
					} else {
						particle.addElement(localElement);
						System.out.println("ProjectChangesListener.processFileAdd()::Added " + localElement);
						localECoreFactory.fireElementAdded(localElement);
					}
				}

			}
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, DashboardCorePlugIn.PLUGIN_ID, "could not process " + file.getProjectRelativePath()
					+ " as a addition. It is recommended to close and open the project.", e));
		}
	}

	private void processFileMove(IFile file, IPath oldPath) throws CoreException {
		System.out.println("ProjectChangesListener.processFileMove(" + file + "," + oldPath + ")");
		try {
			if ("system".equalsIgnoreCase(file.getFileExtension()) == true) {
				// get current system skin elements
				Map<String, LocalElement> systemElements = getSystemElements();
				// remove existing system skin elements
				for (LocalElement systemElement : systemElements.values()) {
					localECoreFactory.removeElement(systemElement, true);
				}
				// load the new system skin elements
				localECoreFactory.loadSystemElements(ResourceHelper.getLocationURI(file).toString());
				Map<String, LocalElement> newSystemElements = getSystemElements();
				for (LocalElement newSystemElement : newSystemElements.values()) {
					LocalElement oldSystemElement = systemElements.get(newSystemElement.getID());
					System.out.println("ProjectChangesListener.processFileMove()::Replaced " + oldSystemElement + " by " + newSystemElement);
					localECoreFactory.fireElementChanged(newSystemElement);
					updateDependentElements(oldSystemElement, newSystemElement);
				}
				return;
			} else {
				// we are dealing with non system element
				// load and convert
				EObject persistedElement = IndexUtils.loadEObject(ResourceHelper.getLocationURI(file));
				LocalElement localElement = LocalECoreFactory.toLocalElement(localECoreFactory, persistedElement);
				// find the right particle based on the element type
				LocalParticle particle = localECoreFactory.getParticle(localElement.getElementType());
				if (particle != null && particle.isInitialized() == true) {
					LocalElement existingElement = particle.getElementByID(localElement.getID());
					if (existingElement != null) {
						// remove existing element
						particle.removeElementByID(existingElement.getID());
					}
					// add new element
					particle.addElement(localElement);
					System.out.println("ProjectChangesListener.processFileMove()::Replaced " + existingElement + " by " + localElement);
					localECoreFactory.fireElementChanged(localElement);
					updateDependentElements(existingElement, localElement);
				}
			}
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, DashboardCorePlugIn.PLUGIN_ID, "could not process " + file.getProjectRelativePath()
					+ " as a replacement. It is recommended to close and open the project.", e));
		}
	}

	private void processFileRemove(IFile file) throws CoreException {
		System.out.println("ProjectChangesListener.processFileRemove(" + file + ")");
		try {
			if ("system".equalsIgnoreCase(file.getFileExtension()) == true) {
				Collection<LocalElement> systemElements = getSystemElements().values();
				for (LocalElement systemElement : systemElements) {
					localECoreFactory.removeElement(systemElement, true);
					System.out.println("ProjectChangesListener.processFileRemove()::Removed " + systemElement);
					localECoreFactory.fireElementRemoved(systemElement);
					updateDependentElements(systemElement, null);
				}
				return;
			}
			// get the type based on the extension
			String[] types = new String[] { BEViewsElementNames.getType(file.getFileExtension()) };
			if (types == null || types.length == 0) {
				// not a known BEViews type
				return;
			}
			// check if we are dealing with TEXT_OR_CHART_COMPONENT
			if (types[0] == BEViewsElementNames.TEXT_OR_CHART_COMPONENT) {
				List<String> chartOrTextComponentTypes = BEViewsElementNames.getChartOrTextComponentTypes();
				types = chartOrTextComponentTypes.toArray(new String[chartOrTextComponentTypes.size()]);
			}
			String elementName = file.getName().substring(0, file.getName().indexOf("."));
			String folder = "/" + file.getProjectRelativePath().removeLastSegments(1).toString() + "/";
			for (String type : types) {
				// get particle associated with the type in local ecore factory
				LocalParticle particle = localECoreFactory.getParticle(type);
				if (particle != null && particle.isInitialized() == true) {
					// we have previously loaded children in the particle
					LocalElement localElement = particle.getElement(elementName, folder);
					if (localElement != null) {
						particle.removeElementByID(localElement.getID());
						System.out.println("ProjectChangesListener.processFileRemove()::Removed " + localElement);
						localElement.setInternalStatus(InternalStatusEnum.StatusRemove, true);
						localECoreFactory.fireElementRemoved(localElement);
						updateDependentElements(localElement, null);
					}
				}
			}
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, DashboardCorePlugIn.PLUGIN_ID, "could not process " + file.getProjectRelativePath()
					+ " as a deletion. It is recommended to close and open the project", e));
		}
	}

	private void processFileChange(IFile file) throws CoreException {
		System.out.println("ProjectChangesListener.processFileChange(" + file + ")");
		try {
			// get the type based on the extension
			String[] types = new String[] { BEViewsElementNames.getType(file.getFileExtension()) };
			if (types == null || types.length == 0) {
				// not a known BEViews type
				return;
			}
			// check if we are dealing with TEXT_OR_CHART_COMPONENT
			if (types[0] == BEViewsElementNames.TEXT_OR_CHART_COMPONENT) {
				List<String> chartOrTextComponentTypes = BEViewsElementNames.getChartOrTextComponentTypes();
				types = chartOrTextComponentTypes.toArray(new String[chartOrTextComponentTypes.size()]);
			}
			for (String type : types) {
				// get particle associated with the type in local ecore factory
				LocalParticle particle = localECoreFactory.getParticle(type);
				if (particle != null && particle.isInitialized() == true) {
					// we have previously loaded children in the particle
					// get the contents of the file as EMF object
					Entity persistedObject = (Entity) IndexUtils.loadEObject(ResourceHelper.getLocationURI(file));
					LocalElement existingElement = particle.getElementByID(persistedObject.getGUID());
					if (existingElement != null) {
						LocalElement newElement = LocalECoreFactory.toLocalElement(localECoreFactory, persistedObject);
						if (newElement.getElementType().equals(existingElement.getElementType()) == true) {
							// we are the same type
							// refresh the element
							existingElement.refresh(persistedObject);
							System.out.println("ProjectChangesListener.processFileChange()::Refreshed " + existingElement);
							localECoreFactory.fireElementChanged(existingElement);
							updateDependentElements(existingElement, existingElement);
						} else {
							// we have a transition to deal with
							// remove the existingElement
							particle.removeElementByID(existingElement.getID());
							// add the new element
							localECoreFactory.addElement(newElement.getElementType(), newElement);
							System.out.println("ProjectChangesListener.processFileChange()::Transitioned " + existingElement + " to " + newElement);
							localECoreFactory.fireElementRemoved(existingElement);
							localECoreFactory.fireElementAdded(newElement);
							updateDependentElements(existingElement, newElement);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, DashboardCorePlugIn.PLUGIN_ID, "could not process " + file.getProjectRelativePath()
					+ " as a change. It is recommended to close and open the project", e));
		}
	}

	private void updateDependentElements(LocalElement localElement, LocalElement replacement) throws Exception {
		System.out.println("ProjectChangesListener.refreshDependentElements(" + localElement + "," + replacement + ")");
		Entity entity = (Entity) localElement.getEObject();
		// do a search for the referrers of localElement
		SearchResult searchResult = SearchUtils.searchIndex(entity, localECoreFactory.getProject().getName(), entity.getName(), null);
		List<EObject> exactMatches = searchResult.getExactMatches();
		for (EObject object : exactMatches) {
			if (object instanceof BEViewsElement) {
				BEViewsElement referrer = (BEViewsElement) object;
				String type = referrer.eClass().getName();
				LocalElement referringElement = localECoreFactory.getElementByID(type, referrer.getGUID());
				if (referringElement != null) {
					referringElement.replaceElementByObject(localElement.getID(), replacement);
					System.out.println("ProjectChangesListener.refreshDependentElements()::Replaced " + localElement + " in " + referringElement + " by " + replacement);
					// we should reset the status, should not affect any editors
					// since each editor loads the data from the filesystem directly
					referringElement.setInternalStatus(InternalStatusEnum.StatusExisting, true);
					localECoreFactory.fireElementChanged(referringElement);
				}
			}
		}
	}

	private Map<String, LocalElement> getSystemElements() throws Exception {
		Map<String, LocalElement> family = new LinkedHashMap<String, LocalElement>();
		List<LocalElement> skins = localECoreFactory.getChildren(BEViewsElementNames.SKIN);
		for (LocalElement skinElement : skins) {
			LocalSkin skin = (LocalSkin) skinElement;
			if (skin.isSystem() == true) {
				// found the system skin elements
				family.put(skin.getID(), skin);
				for (LocalElement localElement : skin.getFamily()) {
					family.put(localElement.getID(), localElement);
				}
				return family;
			}
		}
		return Collections.emptyMap();
	}

	class ResourceDeltaVisitor implements IResourceDeltaVisitor {

		@Override
		public boolean visit(IResourceDelta delta) throws CoreException {
			// get the resource
			IResource resource = delta.getResource();
			if (resource instanceof IWorkspaceRoot) {
				return true;
			}
			//check if the file belongs to my project
			if (resource.getProject().equals(localECoreFactory.getProject()) == false) {
				//the resource does not belong to us
				return false;
			}
			// check if resource is an instance of file
			if ((resource instanceof IFile) == true && resource.getProject().isAccessible() == true) {
//				System.out.println("ResourceDeltaVisitor.visit()::Received changes for " + delta.getResource() + " with kind as " + deltaToString(delta.getKind()));
				// we are a file , process the change
				IFile file = (IFile) resource;
//				if (file == null) {
//					DashboardCorePlugIn.getDefault().getLog().log(new Status(IStatus.WARNING, "Received a delta " + delta + " which could be processed", null));
//					return true;
//				}
				//check if we can actually process the file change (using extension matching)
				if (BEViewsElementNames.getTopLevelElementExtensions().contains(file.getFileExtension()) == false) {
					//we cannot handle this change
					//check if this an external referencable resource
					//PATCH hardcoding to use state machine extension
					if ("statemachine".equals(file.getFileExtension()) == true) {
						//we are getting changes to a state machine , notify the dashboard elements as local external reference
						return processAllExternalChanges(delta);
					}
					return true;
				}
				return processAllChanges(delta);
			}
			if (resource instanceof IProject) {
				if (delta.getKind() == IResourceDelta.CHANGED && ((delta.getFlags() & IResourceDelta.OPEN) == IResourceDelta.OPEN)) {
					if (((IProject) resource).isOpen() == false) {
						// someone closed the project, wipe out the local ecore
						// factory
						List<LocalParticle> particles = localECoreFactory.getParticles(true);
						for (LocalParticle localParticle : particles) {
							localParticle.removeAll();
						}
					}
				}
			}
			// we are not a file, continue the visit
			return true;
		}

		@SuppressWarnings("unused")
		private String deltaToString(int type) {
			if (type == IResourceDelta.ADDED)
				return "ADDED";
			if (type == IResourceDelta.REMOVED)
				return "REMOVED";
			if (type == IResourceDelta.MOVED_FROM)
				return "MOVED_FROM";
			if (type == IResourceDelta.MOVED_TO)
				return "MOVED_TO";
			if (type == IResourceDelta.COPIED_FROM)
				return "COPIED_FROM";
			if (type == IResourceDelta.CHANGED)
				return "CHANGED";
			return "NONE";
		}

	}

}
