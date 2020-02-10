/**
 * 
 */
package com.tibco.cep.studio.rms.artifacts.manager.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.rms.artifacts.ArtifactOperation;
import com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry;
import com.tibco.cep.studio.rms.artifacts.ArtifactsFactory;
import com.tibco.cep.studio.rms.artifacts.ArtifactsSummary;
import com.tibco.cep.studio.rms.artifacts.RMSRepo;
import com.tibco.cep.studio.rms.core.RMSCorePlugin;

/**
 * @author aathalye
 *
 */
public class ArtifactsSummaryPersistenceHandler {

	public static final String RMS_ARTIFACTS_SUMMARY_FILENAME = "ars";

	/**
	 * Remove a {@link ArtifactSummaryEntry} from the repository for the project.
	 * <p>
	 * If the entry had been modified earlier, remove the modified entry and add it to deleted entries.
	 * </p>
	 * <p>
	 * If the entry was added locally, simply remove the entry.
	 * </p>
	 * @param summaryEntry
	 * @param artifactsSummary
	 * @param softDelete -> If true add to deleted section else remove from deleted section
	 */
	public void removeEntry(ArtifactSummaryEntry summaryEntry, 
			ArtifactsSummary artifactsSummary,
			boolean softDelete) {
		//Get operation type
		ArtifactOperation operation = summaryEntry.getOperationType();
		switch (operation) {
		case ADD :
			artifactsSummary.getAddedArtifacts().remove(summaryEntry);
			break;
		case MODIFY :
			artifactsSummary.getModifiedArtifacts().remove(summaryEntry);
			break;
		case DELETE :
			summaryEntry.setOperationType(ArtifactOperation.DELETE);
			if (softDelete) {
				artifactsSummary.getDeletedArtifacts().add(summaryEntry);
			} else {
				artifactsSummary.getDeletedArtifacts().remove(summaryEntry);
			}
			break;
		}
	}

	/**
	 * Add a new {@link ArtifactSummaryEntry} for new creation or modification 
	 * or removal if one does not exist already.
	 * @param summaryEntry
	 * @param artifactsSummary
	 */
	public void addEntry(ArtifactSummaryEntry summaryEntry, 
			ArtifactsSummary artifactsSummary) {
		//Get operation type
		ArtifactOperation operation = summaryEntry.getOperationType();
		if (artifactsSummary != null){
			switch (operation) {
			case ADD :
				artifactsSummary.getAddedArtifacts().add(summaryEntry);
				break;
			case MODIFY :
				artifactsSummary.getModifiedArtifacts().add(summaryEntry);
				break;
			case DELETE :
				artifactsSummary.getDeletedArtifacts().add(summaryEntry);
				break;
			}
		}
	}

	public ArtifactsSummary loadArtifactSummaryFromFile(String summaryFilePath) {
		ArtifactsSummary artifactsSummary = null;
		File summaryFile = new File(summaryFilePath);
		if (summaryFile != null && summaryFile.exists()) {
			EObject eObject = CommonUtil.loadEObject(summaryFile.getAbsolutePath());
			if (eObject instanceof ArtifactsSummary) {
				artifactsSummary = (ArtifactsSummary)eObject;
			}
		} 
		return artifactsSummary;
	}

	public ArtifactsSummary createProjectArtifactsSummary(IProject project, 
			IFile summaryFile) {
		ArtifactsSummary artifactsSummary = ArtifactsFactory.eINSTANCE.createArtifactsSummary();
		artifactsSummary.setName(project.getName());
		FileOutputStream fos = null;
		try {
			String resourceLocation = summaryFile.getFullPath().toOSString();
			XMIResource resource = 
					new XMIResourceImpl(URI.createFileURI(resourceLocation));
			resource.getContents().add(artifactsSummary);
			File newFile = new File(resourceLocation);
			File parentFile = newFile.getParentFile();
			if(!parentFile.exists()) {
				boolean created = parentFile.mkdir();
				System.out.print(created);
			}
			if(!newFile.exists()) {
				newFile.createNewFile();
			}
			fos = new FileOutputStream(newFile);
			resource.save(fos, null);			
			CommonUtil.refresh(summaryFile, IResource.DEPTH_ZERO, false);
		} catch (Throwable e) {
			RMSCorePlugin.log(e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					RMSCorePlugin.log(e);
				}
			}
		}
		return artifactsSummary;
	}

	public void save(ArtifactsSummary artifactsSummary) {
		//Save it
		try {
			if (artifactsSummary != null) {
				ModelUtils.saveEObject(artifactsSummary); // G11N encoding changes
				//				artifactsSummary.eResource().save(null);
			}
		} catch (IOException e) {
			RMSCorePlugin.log(e);
		}
	}

	public RMSRepo createRMSRepo() {
		return ArtifactsFactory.eINSTANCE.createRMSRepo();
	}
}
