package com.tibco.cep.studio.ui.editors.rules.text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.antlr.runtime.tree.CommonTree;
import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;

import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.studio.core.resources.JarEntryFile;
import com.tibco.cep.studio.core.rules.CommonRulesParserManager;
import com.tibco.cep.studio.core.rules.RulesParserManager;
import com.tibco.cep.studio.core.rules.ast.RuleCreatorASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.resources.JarEntryEditorInput;

public class RulesDocumentProvider extends TextFileDocumentProvider implements IRulesWorkingCopyManager {

	protected class RulesElementInfo extends FileInfo {

		private class ASTStateUpdater implements IDocumentListener {

			public void documentAboutToBeChanged(DocumentEvent event) {
				
			}

			public void documentChanged(DocumentEvent event) {
				if (getNode() != null) {
					getNode().setDirty(true);
				}
			}
			
		}
		
		private RulesASTNode fNode;
		private ASTStateUpdater fListener = new ASTStateUpdater();
		private IDocument document;

		public RulesElementInfo(IDocument document) {
			this.document = document;
			document.addDocumentListener(fListener);
		}

		public void setNode(RulesASTNode fNode) {
			this.fNode = fNode;
		}

		public RulesASTNode getNode() {
			return fNode;
		}

		/**
		 * @return the document
		 */
		public IDocument getDocument() {
			return document;
		}
		
		

	}
	
	private final Map fSharedElementInfoMap= new HashMap();
	
//	private static final String[] fPartitionTypes = new String[] { IDocument.DEFAULT_CONTENT_TYPE, 
//			RulesPartitionScanner.STRING, RulesPartitionScanner.HEADER,
//			RulesPartitionScanner.COMMENT, RulesPartitionScanner.XML_TAG,
//			RulesPartitionScanner.KEYWORD };
//	
//		super.setupDocument(element, document);
//		if (document != null) {
//			IDocumentPartitioner partitioner = new FastPartitioner(new RulesPartitionScanner(), 
//					fPartitionTypes);
//			partitioner.connect(document);
//			document.setDocumentPartitioner(partitioner);
//		}
//	}

	@Override
	protected FileInfo createFileInfo(Object element)
			throws CoreException {
		FileInfo info = super.createFileInfo(element);
		if (info instanceof FileInfo) {
			FileInfo fileInfo = (FileInfo) info;
			RulesElementInfo rulesInfo = new RulesElementInfo(fileInfo.fTextFileBuffer.getDocument());
			rulesInfo.fCachedReadOnlyState = fileInfo.fCachedReadOnlyState;
			rulesInfo.fCount = fileInfo.fCount;
			rulesInfo.fElement = fileInfo.fElement;
			rulesInfo.fModel = fileInfo.fModel;
			rulesInfo.fTextFileBuffer = fileInfo.fTextFileBuffer;
			rulesInfo.fTextFileBufferLocationKind = fileInfo.fTextFileBufferLocationKind;

			if (element instanceof IFileEditorInput) {
				IFileEditorInput input = (IFileEditorInput) element;
				String projectName = input.getFile().getProject().getName();
				CommonTree tree = RulesParserManager.parseRuleString(projectName, fileInfo.fTextFileBuffer.getDocument().get(), null);
				rulesInfo.setNode((RulesASTNode) tree);
			} 
			
			return rulesInfo;
		} else if(element instanceof JarEntryEditorInput) {
			return createSharedElementFileInfo(element);
		}
		
		return info;
	}

	public RulesASTNode getWorkingCopy(IEditorInput input) {
		FileInfo info = super.getFileInfo(input);
		if (info instanceof RulesElementInfo) {
			return ((RulesElementInfo) info).getNode();
		}
		return null;
	}
	
	@Override
	public IDocument getDocument(Object element) {
		IDocument doc =  super.getDocument(element);
		if(doc != null) {
			return doc;
		}
		if(element instanceof JarEntryEditorInput) {
			RulesElementInfo rinfo = (RulesElementInfo) fSharedElementInfoMap.get(element);
			return rinfo.getDocument();
		}
		return null;
	}

	public void inputChanged(IEditorInput editorInput, Object input) {
		FileInfo elementInfo = getFileInfo(editorInput);
		if (elementInfo instanceof RulesElementInfo && input instanceof RulesASTNode) {
			((RulesElementInfo) elementInfo).setNode((RulesASTNode) input);
		}
	}
	
	@Override
	public void connect(Object element) throws CoreException {
		super.connect(element);
		if (getFileInfo(element) != null)
			return;
		
		RulesElementInfo info= (RulesElementInfo)fSharedElementInfoMap.get(element);
		if (info == null) {
			if( element instanceof JarEntryEditorInput) {
				JarEntryEditorInput input = (JarEntryEditorInput) element;
				info= (RulesElementInfo) createSharedElementFileInfo(element);
				info.fElement= element;
				info.fModel= new SharedElementAnnotationModel(input);
				fSharedElementInfoMap.put(element, info);
			}
		}
		info.fCount++;
	}
	
	private RulesElementInfo createSharedElementFileInfo(Object element) throws CoreException {
		if(element instanceof JarEntryEditorInput) {
			JarEntryEditorInput input = (JarEntryEditorInput) element;
			String projectName = input.getProjectName();
			IStorage storage = input.getStorage();
			if(storage instanceof JarEntryFile) {
				JarEntryFile file = (JarEntryFile) storage;
				InputStream is = null;
				try {
					is = file.getContents();
					RulesASTNode tree = CommonRulesParserManager.parseRuleInputStream(projectName, is, null, true, true);
					RuleCreatorASTVisitor visitor = new RuleCreatorASTVisitor(true,true,projectName);
					tree.accept(visitor);
					Compilable compilable = visitor.getRule();
					IPath jarFilePath = new Path(file.getJarFilePath());
					URI uri = new URI("jar://"+URIUtil.toURI(jarFilePath).toString()+"!/"+file.getFullPath().toPortableString());
					ITextFileBufferManager bm = FileBuffers.getTextFileBufferManager();
					bm.connect(new Path(uri.toString()),LocationKind.NORMALIZE, new NullProgressMonitor());
					ITextFileBuffer buffer = bm.getTextFileBuffer(new Path(uri.toString()),LocationKind.NORMALIZE);
					buffer.getDocument().set(compilable.getFullSourceText());
					buffer.setDirty(false);
					RulesElementInfo rulesInfo = new RulesElementInfo(buffer.getDocument());
//					IDocument doc = new Document(compilable.getFullSourceText());
//					RulesElementInfo rulesInfo = new RulesElementInfo(doc);
					rulesInfo.fCachedReadOnlyState = true;
					rulesInfo.fCount = 0;
					rulesInfo.fElement = element;
					rulesInfo.fModel= new SharedElementAnnotationModel(input);
					rulesInfo.fNode = tree;
					rulesInfo.fTextFileBuffer = buffer;
					rulesInfo.fTextFileBufferLocationKind = LocationKind.NORMALIZE;
					return rulesInfo;
				} catch (Exception e) {
					throw new CoreException(new Status(IStatus.ERROR,EditorsUIPlugin.PLUGIN_ID,e.getMessage(),e));
				} finally {
						try {
							if (is != null) {
								is.close();
							}
							if (file != null) {
								file.closeJarFile();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
			}
		}
		return null;
	}

	public IAnnotationModel getAnnotationModel(Object element) {
		IAnnotationModel model= super.getAnnotationModel(element);
		if (model != null)
			return model;
		
		FileInfo info= (FileInfo)fSharedElementInfoMap.get(element);
		if (info != null) {
			if (info.fModel != null)
				return info.fModel;
			if (info.fTextFileBuffer != null)
				return info.fTextFileBuffer.getAnnotationModel();
		}
		
		return null;
	}
	
	public void disconnect(Object element) {
		RulesElementInfo info= (RulesElementInfo)fSharedElementInfoMap.get(element);
		if (info != null)  {
			if (info.fCount == 1) {
				fSharedElementInfoMap.remove(element);
				info.fModel= null;
			} else
				info.fCount--;
		}
		super.disconnect(element);
	}

}
