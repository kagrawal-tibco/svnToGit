/**
 * 
 */
package com.tibco.cep.studio.rms.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.Deflater;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.cep.security.authz.utils.IOUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactOperation;
import com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry;
import com.tibco.cep.studio.rms.artifacts.ArtifactsFactory;
import com.tibco.cep.studio.rms.artifacts.ArtifactsType;
import com.tibco.cep.studio.rms.artifacts.manager.impl.RMSArtifactsSummaryManager;
import com.tibco.cep.studio.rms.client.ArtifactsManagerClient;
import com.tibco.cep.studio.rms.core.RMSCorePlugin;
import com.tibco.cep.studio.rms.history.model.HistoryRevisionItem;
import com.tibco.cep.studio.rms.history.model.RevisionDetailsItem;
import com.tibco.cep.studio.rms.model.ArtifactCheckinHistoryEntry;
import com.tibco.cep.studio.rms.model.ArtifactRevisionMetadata;
import com.tibco.cep.studio.rms.model.CommittedArtifactDetails;
import com.tibco.net.mime.Base64Codec;

/**
 * @author aathalye
 *
 */
public class ArtifactsManagerUtils {
	
	private static Transformer TRANSFORMER;
	
	static {
		//load xslt
		ClassLoader classLoader = ArtifactsManagerUtils.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("com/tibco/cep/studio/rms/core/utils/OwnerProjectMigration.xsl");
		if (inputStream != null) {
			StreamSource streamSource = new StreamSource(inputStream);
			try {
				Templates templates = TransformerFactory.newInstance().newTemplates(streamSource);
				TRANSFORMER = templates.newTransformer();
			} catch (TransformerConfigurationException e) {
				RMSCorePlugin.log(e);
			} catch (TransformerFactoryConfigurationError e) {
				RMSCorePlugin.log(e);
			}
		}
	}
	/**
	 * 
	 * @param url
	 * @param username
	 * @param project
	 * @param updatePath -> If set, get only these resources
	 * @param isDM -> Is the client DM?
	 * @return
	 */
	public static final Object getAllRelevantArtifacts(String url, 
			                                           String username,
			                                           String project,
			                                           String updatePath,
			                                           boolean isDM) {
		try {
			Object response = 
				ArtifactsManagerClient.fetchAllArtifacts(username, project, updatePath, url, isDM);
			return response;
		} catch (Exception e) {
			RMSCorePlugin.log(e);
		}
		return null;
	}
	
	/**
	 * Gets contents of the master copy of an artifact specified by artifactPath, and artifactExtension.
	 * @param url
	 * @param loggedInUsername
	 * @param project
	 * @param artifactPath
	 * @param artifactExtn
	 * @return
	 */
	public static final Artifact getArtifactContents(String url,
			                                         String loggedInUsername,		
													 String project, 
			                                         String artifactPath,
			                                         String artifactExtn) {
		Artifact artifact = ArtifactsFactory.eINSTANCE.createArtifact();
		artifact.setArtifactPath(artifactPath);
		artifact.setArtifactExtension(artifactExtn);
		
		try {
			Artifact fetchedartifact = 
				ArtifactsManagerClient.fetchSelectedArtifact(loggedInUsername, project, url, artifact);
			return fetchedartifact;
		} catch (Exception e) {
			RMSCorePlugin.log(e);
		}
		return null;
	}
	
	/**
	 * Deletes a specified file from the local project repository
	 * @param localProjectName
	 * @param rootPath
	 * @param artifactPath
	 * @return
	 * @throws IOException
	 */
	public static final boolean deleteArtifact(String localProjectName, String rootPath, String artifactPath) throws IOException {
		File file = new File(rootPath + artifactPath);
		if (file.exists()) return file.delete();
		return false;
	}
	
	/**
	 * Write contents of artifact to disk.
	 * @param localProjectName
	 * @param rootPath
	 * @param artifactToSave
	 * @throws IOException
	 */
	public static final void saveContents(String localProjectName,
			                              String rootPath,
			                              Artifact artifactToSave) throws IOException {
		String artifactPath = artifactToSave.getArtifactPath();
		//We get this from response
		String artifactExtn = artifactToSave.getArtifactExtension();
		String fullPath = rootPath + artifactPath + "." + artifactExtn;
		
		File file = new File(fullPath);
		File parent = file.getParentFile();
		
		if (!parent.exists()) {
			parent.mkdirs();
		}
		//write contents
		String transformedContents = artifactToSave.getArtifactContent();
		//Do not carry this out for DTs.
		if (!ArtifactsType.DECISIONTABLE.getLiteral().equals(artifactExtn)) {
			transformedContents = transformContents(localProjectName, transformedContents);
		}
		byte[] transformedBytes = transformedContents.getBytes(Charset.forName("UTF-8"));
		IOUtils.writeBytes(new ByteArrayInputStream(transformedBytes), new FileOutputStream(file));	
	}
	
	/**
	 * 
	 * @param newProjectName
	 * @param oldArtifactContents
	 * @return
	 */
	private static String transformContents(String newProjectName, 
			                                String oldArtifactContents) {
		//Use transformer if present
		String transformedContents = null;
		if (TRANSFORMER != null) {
			TRANSFORMER.setParameter("newProjectName", newProjectName);
			//Transform the artifact contents
			StringReader stringReader = new StringReader(oldArtifactContents);
			StreamSource oldStreamSource = new StreamSource(stringReader);
			
			StringWriter stringWriter = new StringWriter();
			StreamResult newStreamResult = new StreamResult(stringWriter);
			
			try {
				TRANSFORMER.transform(oldStreamSource, newStreamResult);
				transformedContents = stringWriter.toString();
			} catch (TransformerException e) {
				//Probably non-xml source
				transformedContents = oldArtifactContents;
				//RMSCorePlugin.log(e);
			} finally {
				stringReader.close();
				try {
					stringWriter.close();
				} catch (IOException e) {
					RMSCorePlugin.log(e);
				}
			}
		} else {
			transformedContents = oldArtifactContents;
		}
		return transformedContents;
	}
		
	/**
	 * 
	 * @param newProjectName
	 * @param oldArtifactContents
	 * @return
	 */
	private static byte[] transformContents(String newProjectName, 
			                                byte[] oldArtifactContents) {
		//Use transformer if present
		byte[] transformedContents = null;
		if (TRANSFORMER != null) {
			TRANSFORMER.setParameter("newProjectName", newProjectName);
			//Transform the artifact contents
			ByteArrayInputStream bis = new ByteArrayInputStream(oldArtifactContents);
			StreamSource oldStreamSource = new StreamSource(bis);
			
			ByteArrayOutputStream bos = new  ByteArrayOutputStream();
			StreamResult newStreamResult = new StreamResult(bos);
			
			try {
				TRANSFORMER.transform(oldStreamSource, newStreamResult);
				transformedContents = bos.toByteArray();
			} catch (TransformerException e) {
				//Probably non-xml source
				transformedContents = oldArtifactContents;
				//RMSCorePlugin.log(e);
			} finally {
				try {
					bis.close();
					bos.close();
				} catch (IOException e) {
					RMSCorePlugin.log(e);
				}
			}
		} else {
			transformedContents = oldArtifactContents;
		}
		return transformedContents;
	}
		
	/**
	 * Return true if this artifact has local presence either remotely or
	 * locally created
	 * @param project
	 * @param artifact
	 * @return
	 */
	public static boolean isArtifactPresent(String project,
			                                String artifactPath) {
		
		RMSArtifactsSummaryManager summaryManager = RMSArtifactsSummaryManager.getInstance();
		
		// check if the artifact exists, irrespective of commit status/artifact changer
		ArtifactSummaryEntry summaryEntry = summaryManager.getSummaryEntry(project, 
				artifactPath, null, true,true,true);
		//Do not treat entries in deleted artifacts as present
		return summaryEntry != null && summaryEntry.getOperationType() != ArtifactOperation.DELETE;
	}
	
	/**
	 * Retrieve the list of Artifacts that are deleted on the server
	 * @param projectName
	 * @param artifacts
	 * @param containerPath
	 * @return
	 */
	public static List<Artifact> getDeletedArtifacts(String projectName, List<Artifact> artifacts, String containerPath){
		List<Artifact> deletedArtifacts = new ArrayList<Artifact>();
		
		List<ArtifactSummaryEntry> undeletedArtifacts = RMSArtifactsSummaryManager.getInstance().getUnDeletedSummaryEntries(projectName, containerPath);
		boolean missingArtifact = false;
		for (ArtifactSummaryEntry summaryEntry : undeletedArtifacts){
			missingArtifact = true;
			for (Artifact artifact : artifacts){
				if (summaryEntry.getArtifact().getArtifactPath().equals(artifact.getArtifactPath())) {
					missingArtifact = false;
					break;
				}
			}
			if (missingArtifact) deletedArtifacts.add(summaryEntry.getArtifact());
		}
		
		return deletedArtifacts;
	}
	
	/**
	 * List artifacts to commit inside project or a folder inside the project.
	 * @param resource
	 * @return
	 * @throws IOException
	 */
	public static List<ArtifactSummaryEntry> listArtifactsToCommit(IResource resource) throws IOException {
		RMSArtifactsSummaryManager summaryManager = RMSArtifactsSummaryManager.getInstance();
		
		//Keep an empty arraylist
		List<ArtifactSummaryEntry> localEntries = new ArrayList<ArtifactSummaryEntry>(0);
		if (resource instanceof IProject) {
			IProject project = (IProject)resource;
			localEntries = summaryManager.getLocalSummaryEntries(project.getName(), false);
		} else if (resource instanceof IFolder) {
			IFolder folder = (IFolder)resource;
			IProject project = folder.getProject();
			String folderPath = "/" + folder.getFullPath().removeFirstSegments(1);
			localEntries = summaryManager.getLocalSummaryEntries(project.getName(), folderPath, false);
		} else if (resource instanceof IFile) {
			IFile file = (IFile)resource;
			String folderPath = "/" + file.getParent().getFullPath().removeFirstSegments(1);
			String filePath = IndexUtils.getFullPath((IFile)resource);
			localEntries = summaryManager.getLocalSummaryEntries(file.getProject().getName(), folderPath, filePath, false);
		}
		//TODO if IFile is selected
		return localEntries;
	}
	
	/**
	 * 
	 * @param project
	 * @param artifact
	 * @return
	 * @throws IOException
	 */
	public static String getEncodedArtifactContents(String repoProject,
			                                        IProject project, 
			                                        Artifact artifact) throws IOException {
		String projectRootPath = project.getLocation().toString();
		return getEncodedArtifactContents(repoProject, projectRootPath, artifact);
	}
	
	/**
	 * ZIP contents of artifact and encode them in Base64 form.
	 * @param repoProject
	 * @param projectRootPath
	 * @param artifact
	 * @return
	 * @throws IOException
	 */
	public static String getEncodedArtifactContents(String repoProject,
			                                        String projectRootPath, 
            										Artifact artifact) throws IOException {
		String artifactPath = artifact.getArtifactPath();
		//Get its type = extn
		String artifactExtn = artifact.getArtifactExtension();
				
		String fullPath = projectRootPath + artifactPath + "." + artifactExtn;
		byte[] bytes = IOUtils.readBytes(fullPath);
		//Reset project name
		byte[] transformedContents = bytes;
		//Do not carry this out for DTs.
		if (!ArtifactsType.DECISIONTABLE.getLiteral().equals(artifactExtn)) {
			transformedContents = transformContents(repoProject, bytes);
		}
		return encodeByteArray(transformedContents);
	}
	
	/**
	 * Base64 encode of bytes after compressing them.
	 * @param bytes
	 * @return
	 */
	public static String encodeByteArray(byte[] bytes) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);
		Deflater compressor = new Deflater();
		compressor.setLevel(Deflater.BEST_COMPRESSION);
		compressor.setInput(bytes);
	    compressor.finish();
		// Compress the data
		byte[] buf = new byte[1024];
		while (!compressor.finished()) {
			int count = compressor.deflate(buf);
			bos.write(buf, 0, count);
		}
		try {
			bos.close();
		} catch (IOException e) {
			RMSCorePlugin.log(e);
		}
		byte[] compressed = bos.toByteArray();
		return Base64Codec.encodeBase64(compressed);
	}
	
	/**
	 * Generate a unique pattern id for every checkin.
	 * @param username
	 * @return
	 */
	public static String generatePatternId(String username) {
		long currentTime = System.nanoTime();
		long combined = username.hashCode() ^ currentTime;
		long combinedAbs = Math.abs(combined << 8);
		return Long.toHexString(combinedAbs);
	}
	
	/**
	 * 
	 * @param url
	 * @param projectName
	 * @param revisionDetailsItem
	 */
	public static ArtifactRevisionMetadata fetchRevisionContents(String url, 
			                                 String projectName, 
			                                 RevisionDetailsItem revisionDetailsItem) {
		//Get revision id
		String revisionId = revisionDetailsItem.getHistoryRevisionItem().getRevisionId();
		String artifactPath = revisionDetailsItem.getArtifactPath();
		String artifactType = revisionDetailsItem.getArtifactType();
		
		return fetchRevisionContents(url, projectName, revisionId, artifactPath, artifactType);
	}
	
	/**
	 * Fetch contents of an artifact at a specific revision.
	 * @param url
	 * @param projectName
	 * @param revisionId -> A non-negative revision id.
	 * @param artifactPath
	 * @param artifactType
	 * @return
	 */
	public static ArtifactRevisionMetadata fetchRevisionContents(String url,
																 String projectName, 
																 String revisionId, 
																 String artifactPath, 
																 String artifactType) {
		Artifact artifact = ArtifactsFactory.eINSTANCE.createArtifact();
		artifact.setArtifactPath(artifactPath);
		artifact.setArtifactType(ArtifactsType.get(artifactType));

		ArtifactRevisionMetadata artifactRevisionMetadata = new ArtifactRevisionMetadata();
		artifactRevisionMetadata.setArtifact(artifact);
		artifactRevisionMetadata.setRevisionId(revisionId);
		artifactRevisionMetadata.setProjectName(projectName);

		try {
			ArtifactRevisionMetadata returnValue = 
				ArtifactsManagerClient.fetchRevisionArtifact(url, 
						projectName,
						artifactRevisionMetadata);
			return returnValue;
		} catch (Exception e) {
			RMSCorePlugin.log(e);
		}
		return null;
	}
	
	/**
	 * Fetch Resource History.
	 * @param url
	 * @param projectName
	 * @param resourcePath
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<HistoryRevisionItem> fetchResourceHistory(String url, String projectName, String resourcePath) {
		try {
			List<ArtifactCheckinHistoryEntry> historyEntries = 
				(List<ArtifactCheckinHistoryEntry>)ArtifactsManagerClient.fetchHistory(url, projectName, resourcePath);
			List<HistoryRevisionItem> historyRevisionItems = new ArrayList<HistoryRevisionItem>(historyEntries.size());
			for (ArtifactCheckinHistoryEntry historyEntry : historyEntries) {
				long revisionId = historyEntry.getRevisionId();
				String author = historyEntry.getUsername();
				Date checkinTime = historyEntry.getCheckinTime();
				String checkinComments = historyEntry.getCheckinComments();
				CommittedArtifactDetails[] artifactsCommitted = historyEntry.getArtifactsCommitted();
				RevisionDetailsItem[] revDetailsItems = new RevisionDetailsItem[artifactsCommitted.length];
				int counter = 0;
				Set<String> checkinActions = new HashSet<String>();
				
				for (CommittedArtifactDetails artifactCommitted : artifactsCommitted) {
					ArtifactOperation artifactOperation = artifactCommitted.getArtifactOperation();
					String checkinAction = artifactOperation.getLiteral();
					RevisionDetailsItem revisionDetailsItem = new RevisionDetailsItem(checkinAction, 
							                                  artifactCommitted.getArtifact().getArtifactPath(), 
							                                  artifactCommitted.getArtifact().getArtifactType().toString(),
							                                  artifactCommitted.getStatus());
					revDetailsItems[counter++] = revisionDetailsItem;
					checkinActions.add(checkinAction);
				}
				StringBuilder checkinActionsBuffer = new StringBuilder();
				Iterator<String> valuesIter = checkinActions.iterator();
				
				while (valuesIter.hasNext()) {
					checkinActionsBuffer.append(valuesIter.next());
					checkinActionsBuffer.append(" ");
				}
				HistoryRevisionItem revisionItem = 
					new HistoryRevisionItem("" + revisionId, checkinActionsBuffer.toString(), author, checkinTime.toString(), checkinComments, revDetailsItems);
				historyRevisionItems.add(revisionItem);
			}
			return historyRevisionItems;
		} catch (Exception e) {
			RMSCorePlugin.log(e);
		}
		return new ArrayList<HistoryRevisionItem>(0);
	}
	
	/**
	 * Is commit supported for artifact from Studio
	 * @param operation
	 * @param artifactType
	 * @return
	 */
	public static boolean isCommitSupported(ArtifactOperation operation, ArtifactsType artifactType) {
		boolean canCommit = false;
		switch(operation) {
		case ADD:
			if (ArtifactsType.DECISIONTABLE.equals(artifactType)) {
				canCommit = true;
			}else if (ArtifactsType.BEPROCESS.equals(artifactType)) {
				canCommit = true;
			}else if (ArtifactsType.DOMAIN.equals(artifactType)) {
				canCommit = true;
			}
			break;
		case MODIFY:
			if (ArtifactsType.DECISIONTABLE.equals(artifactType)) {
				canCommit = true;
			} else if (ArtifactsType.DOMAIN.equals(artifactType)) {
				canCommit = true;
			}else if (ArtifactsType.BEPROCESS.equals(artifactType)) {
				canCommit = true;
			}
			break;
		case DELETE:
			if (ArtifactsType.DECISIONTABLE.equals(artifactType)) {
				canCommit = true;
			}else if (ArtifactsType.BEPROCESS.equals(artifactType)) {
				canCommit = true;
			}else if (ArtifactsType.DOMAIN.equals(artifactType)) {
				canCommit = true;
			}
			break;
		}		
		return canCommit; 
	}
}
