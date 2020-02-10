package com.tibco.cep.studio.core.index.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.antlr.runtime.tree.CommonTree;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.java.JavaFactory;
import com.tibco.cep.designtime.core.model.java.JavaResource;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.index.model.ArchiveElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.EventElement;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.index.model.JavaElement;
import com.tibco.cep.studio.core.index.model.JavaResourceElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedDecisionTableElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.model.StateMachineElement;
import com.tibco.cep.studio.core.java.CommonJavaParserManager;
import com.tibco.cep.studio.core.rules.CommonRulesParserManager;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.utils.CommonRuleCreator;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class BaseBinaryStorageIndexCreator {
	
	protected JarFile fJarFile;
	protected DesignerProject fBinaryIndex;
	protected String fProjectName;
	protected File prjPath;

	public BaseBinaryStorageIndexCreator(String ownerProjectName, DesignerProject index, JarFile file) {
		this.fProjectName = ownerProjectName;
		this.fBinaryIndex = index;
		this.fJarFile = file;
	}

	public void index() {
        if (fJarFile == null) {
            return;
        }
        String jarName = new Path(fJarFile.getName()).lastSegment();
        Enumeration<JarEntry> entries = fJarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String fileName = entry.getName();
            if (fileName.lastIndexOf('\\') != -1) {
                fileName = fileName.substring(fileName.lastIndexOf('\\'));
            }
            if (fileName.lastIndexOf('/') != -1) {
                fileName = fileName.substring(fileName.lastIndexOf('/'));
            }
            if (fileName.lastIndexOf('.') != -1) {
                String extension = fileName.substring(fileName.lastIndexOf('.')+1);
                if(CommonIndexUtils.isJavaType(extension)){
                	reportWorkBegin(entry);
                    indexJavaEntry(entry);
                } else if (CommonIndexUtils.isEMFType(extension)) {
            		reportWorkBegin(entry);
                    indexEMFJarEntry(entry);
                } else if (CommonIndexUtils.isRuleType(extension)) {
                	reportWorkBegin(entry);
                	indexRuleEntry(entry);
                } else if(CommonIndexUtils.isJavaResourceType(fProjectName, File.separator + fProjectName + File.separator + jarName + File.separator + entry.getName(), extension,prjPath)){
                	reportWorkBegin(entry);
                	indexJavaResourceEntry(entry);
                } else {
                	indexEntry(entry);
                }
            }
        }
        reportWork(1);
        finishWork();
    }
	
	public void index(File projectPath){
		prjPath= projectPath;
		index();
	}

	private void indexEntry(ZipEntry entry) {
		SharedElement element = IndexFactory.eINSTANCE.createSharedElement();
		populateSharedEntry(entry, element);
		
		element.setArchivePath(fJarFile.getName());
		insertElement(fBinaryIndex, element, element.getEntryPath());
	}

	private void populateSharedEntry(ZipEntry entry, SharedElement element) {
		String entryName = entry.getName();
		String entryPath = "";
		int idx = entryName.lastIndexOf(File.separator);
		if (idx == -1) {
			idx = entryName.lastIndexOf('/'); // use UNIX style as a backup
		}
		if (idx > 0) {
			entryPath = entryName.substring(0, idx+1);
			entryName = entryName.substring(idx+1);
		}
		element.setFileName(entryName);
		idx = entryName.lastIndexOf('.');
		if (idx > 0) {
			entryName = entryName.substring(0, idx);
		} 
		element.setName(entryName);
		element.setEntryPath(entryPath);
		element.setArchivePath(fJarFile.getName());
	}
	
	private void indexJavaEntry(JarEntry entry) {
		byte[] contents = CommonIndexUtils.getJarEntryContents(fJarFile, entry);
		JavaSource javaSrc = CommonJavaParserManager.parseJavaInputStream(fProjectName, new ByteArrayInputStream(contents));
		Path entryPath = new Path(entry.getName());
		String fileName = entryPath.lastSegment();
		String className = entryPath.removeFileExtension().lastSegment();
		String folderPath = entryPath.removeFileExtension().removeLastSegments(1).addTrailingSeparator().makeAbsolute().toPortableString();
		javaSrc.setName(className);
		javaSrc.setFolder(folderPath);
		javaSrc.setFullSourceText(contents);
		
		JavaElement element = IndexFactory.eINSTANCE.createSharedJavaElement();
		((SharedEntityElement) element).setSharedEntity(javaSrc);
		((SharedEntityElement) element).setFileName(fileName);
		((SharedEntityElement) element).setEntryPath(javaSrc.getFolder());
		Path jarPath = new Path(fJarFile.getName());
		((SharedEntityElement) element).setArchivePath(jarPath.toPortableString());
		
		element.setJavaSource(javaSrc);
		element.setName(javaSrc.getName());
		element.setFolder(javaSrc.getFolder());
		element.setElementType(CommonIndexUtils.getElementType(javaSrc));
		fBinaryIndex.getEntityElements().add(element);
		insertElement(fBinaryIndex, element, element.getFolder());
	}
	
	private void indexJavaResourceEntry(JarEntry entry) {
		String name = entry.getName();
		JavaResource js = JavaFactory.eINSTANCE.createJavaResource();
		Path path = new Path(name);
		name = path.removeFileExtension().lastSegment();
		String folder = path.removeLastSegments(1).toString();
		js.setName(name);
		js.setFolder(folder);
		js.setOwnerProjectName(fProjectName);
		js.setExtension(path.getFileExtension());
		InputStream is = null;
		try {
			is = fJarFile.getInputStream(entry);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			int nRead;
			byte[] data = new byte[16384];
			while ((nRead = is.read(data, 0, data.length)) != -1) {
			  baos.write(data, 0, nRead);
			}
			baos.flush();
			js.setContent(baos.toByteArray());
			String jarName = new Path(fJarFile.getName()).lastSegment();
			Path fullPath = new Path(File.separator + fProjectName + File.separator + jarName + File.separator + entry.getName());
			String sourceFolder = CommonIndexUtils.getJavaSourceFolder(fProjectName, fullPath.toString(),prjPath);
			if (sourceFolder != null) {
				Path sourceFolderPath = new Path(sourceFolder);
				fullPath = fullPath.removeFirstSegments(sourceFolderPath.segmentCount()).removeLastSegments(1);
				js.setPackageName(ModelUtils.convertPathToPackage(fullPath.toString()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}

		JavaResourceElement element = IndexFactory.eINSTANCE.createSharedJavaResourceElement();
		((SharedEntityElement) element).setSharedEntity(js);
		((SharedEntityElement) element).setFileName(name);
		Path jarPath = new Path(fJarFile.getName());
		((SharedEntityElement) element).setArchivePath(jarPath.toPortableString());

		element.setJavaResource(js);
		element.setName(js.getName());
		element.setFolder(js.getFolder());
		element.setElementType(CommonIndexUtils.getElementType(js));
		fBinaryIndex.getEntityElements().add(element);
		insertElement(fBinaryIndex, element, element.getFolder());
	}

	private void indexRuleEntry(JarEntry entry) {
		String ruleText = CommonIndexUtils.getJarEntryText(fJarFile, entry);
		CommonTree tree = CommonRulesParserManager.parseRuleString(fProjectName, ruleText, null, true);
		if (tree instanceof RulesASTNode) {
			Object data = ((RulesASTNode) tree).getData("element");
			if (data instanceof RuleElement) {
				RuleElement rule = (RuleElement) data;
				populateSharedEntry(entry, (SharedElement) rule);
				Compilable compilable = null;
				try {
					compilable = createSharedRule(ruleText.getBytes(ModelUtils.DEFAULT_ENCODING), fProjectName);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				rule.setRule(compilable);
				fBinaryIndex.getRuleElements().add(rule);
				insertElement(fBinaryIndex, rule, rule.getFolder());
			}
		}
	}

	private Compilable createSharedRule(byte[] bytes, String projectName) {
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(bytes);
			CommonRuleCreator creator = new CommonRuleCreator();
			Compilable rule = creator.createRule(is, projectName);
			return rule;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private void indexEMFJarEntry(JarEntry entry) {
		byte[] arr = CommonIndexUtils.getJarEntryContents(fJarFile, entry);
		try {
			EObject obj = CommonIndexUtils.deserializeEObject(arr);
			if (obj instanceof Entity) {
				indexBinaryEObject(fBinaryIndex, ((Entity) obj).getFolder(), obj, entry);
			} else if (obj instanceof Implementation) {
				indexBinaryEObject(fBinaryIndex, ((Implementation) obj).getFolder(), obj, entry);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void indexBinaryEObject(DesignerProject project, String objectPath, EObject eObj, JarEntry entry) {
		if (eObj instanceof StateMachine) {
			StateMachineElement smIndexEntry = new BaseStateMachineElementCreator().createStateMachineElement(project.getName(), (StateMachine) eObj, true);
			smIndexEntry.setIndexName(fProjectName);
			project.getEntityElements().add(smIndexEntry);
			if (fProjectName != null) {
				((StateMachine) eObj).setOwnerProjectName(fProjectName);
			}
			if (smIndexEntry.eResource() != null && smIndexEntry.eResource().getURI() != null) {
				smIndexEntry.eResource().unload();
			}
			populateSharedEntry(entry, (SharedElement) smIndexEntry);
			insertElement(project, smIndexEntry, objectPath);
		} else if (eObj instanceof SimpleEvent) {
			EventElement indexEntry = new EventElementCreator().createEventElement(project.getName(), (SimpleEvent)eObj, true);
			((SimpleEvent) eObj).setOwnerProjectName(fProjectName);
			project.getEntityElements().add(indexEntry);
			if (indexEntry.eResource() != null && indexEntry.eResource().getURI() != null) {
				indexEntry.eResource().unload();
			}
			populateSharedEntry(entry, (SharedElement) indexEntry);
			insertElement(project, indexEntry, objectPath);
		} else if (eObj instanceof Entity) {
			Entity entity = (Entity) eObj;
			SharedEntityElement indexEntry = IndexFactory.eINSTANCE.createSharedEntityElement();
			indexEntry.setSharedEntity(entity);
			indexEntry.setName(entity.getName());
			indexEntry.setFolder(entity.getFolder());
			indexEntry.setElementType(CommonIndexUtils.getElementType(entity));
			if (fProjectName != null) {
				entity.setOwnerProjectName(fProjectName);
			}
			project.getEntityElements().add(indexEntry);
			if (entity.eResource() != null && entity.eResource().getURI() != null) {
				entity.eResource().unload();
			}
			populateSharedEntry(entry, indexEntry);
			insertElement(project, indexEntry, objectPath);
		} else if (eObj instanceof ArchiveResource) {
			ArchiveResource archive = (ArchiveResource) eObj;
			ArchiveElement archiveIndexEntry = IndexFactory.eINSTANCE.createArchiveElement();
			archiveIndexEntry.setName(archive.getName());
			archiveIndexEntry.setArchive(archive);
			archiveIndexEntry.setFolder(objectPath);
			archiveIndexEntry.setElementType(ELEMENT_TYPES.ENTERPRISE_ARCHIVE);
			if (archive.eResource() != null && archive.eResource().getURI() != null) {
				archive.eResource().unload();
			}
			project.getArchiveElements().add(archiveIndexEntry);
			insertElement(project, archiveIndexEntry, objectPath);
		} else if (eObj instanceof Implementation) {
			Implementation impl = (Implementation) eObj;
			SharedDecisionTableElement decisionTableElement = IndexFactory.eINSTANCE.createSharedDecisionTableElement();
			decisionTableElement.setName(impl.getName());
			decisionTableElement.setFolder(impl.getFolder());
			decisionTableElement.setElementType(ELEMENT_TYPES.DECISION_TABLE);
			decisionTableElement.setSharedImplementation(impl);
			if (fProjectName != null) {
				impl.setOwnerProjectName(fProjectName);
			}
			project.getDecisionTableElements().add(decisionTableElement);
			populateSharedEntry(entry, decisionTableElement);
			insertElement(project, decisionTableElement, objectPath);
		}
	}
	
	private void insertElement(DesignerProject index, DesignerElement entry, 
			String fullPath) {
		ElementContainer folder = CommonIndexUtils.getFolderForFile(index, fullPath, true, true);
		// commented because some times fullPath is "/" and folder is null so NPE is coming 
		// better check if folder is null only
		//if (folder == null && fullPath.length() == 0) {
		if (folder == null){
			folder = index;
		}
		folder.getEntries().add(entry);
	}
	
    protected void reportWorkBegin(JarEntry entry) {
		// default does nothing
    }

    protected void reportWork(int work) {
		// default does nothing
    }

	protected void finishWork() {
		// default does nothing
	}
}

