package com.tibco.cep.studio.core.refactoring;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;

import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CoherenceFunctionMigrator;

public class CoherenceFunctionRefactoring extends Refactoring {

	private class FileCollector implements IResourceVisitor {

		private List<IFile> fResources = new ArrayList<IFile>();
		
		public List<IFile> getResources() {
			return fResources;
		}

		@Override
		public boolean visit(IResource resource) throws CoreException {
			if (!(resource instanceof IFile)) {
				return true;
			}
			IFile file = (IFile) resource;
			if (IndexUtils.isEntityType(file) || IndexUtils.isImplementationType(file)
					|| IndexUtils.isRuleType(file)) {
				fResources.add(file);
			}
			return false;
		}
		
	}

	private String fProjectName;
	
	public CoherenceFunctionRefactoring(String projectName) {
		this.fProjectName = projectName;
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		if (fProjectName == null) {
			return null;
		}
		FileCollector collector = new FileCollector();
		IProject proj = ResourcesPlugin.getWorkspace().getRoot().getProject(fProjectName);
		proj.accept(collector);
		List<IFile> resources = collector.getResources();
		CompositeChange change = new CompositeChange("File changes");
		for (IFile file : resources) {
			processFile(file, change, null);
		}
		return change;
	}

	private void processFile(IFile file, CompositeChange change, RefactoringStatus resultingStatus) {
		ITextFileBufferManager manager= FileBuffers.getTextFileBufferManager();
		InputStream is = null;
		try {
			is = file.getContents();
			InputStreamReader inputStreamReader = new InputStreamReader(is, Charset.forName(file.getCharset()));
			StringBuilder sb = new StringBuilder();
			int c = -1;
			while ((c = inputStreamReader.read()) != -1) {
				// Since it is a char read cast it
				char character = (char) c;
				sb.append(character);
			}
			TextFileChange fileChange = new TextFileChange("Changes to "+file.getName(), file);
			if (createReplaceEdits(sb, fileChange)) {
				change.add(fileChange);
			}
			
//			manager.connect(file.getFullPath(), LocationKind.IFILE, null);
//			ITextFileBuffer textFileBuffer= manager.getTextFileBuffer(file.getFullPath(), LocationKind.IFILE);
//			if (textFileBuffer == null) {
//				resultingStatus.addError("Can't get buffer");
//				return;
//			}
//			IDocument document= textFileBuffer.getDocument();
//			String lineDelimiter= TextUtilities.getDefaultLineDelimiter(document);
//			TextEditChangeGroup textEditChangeGroup= new TextEditChangeGroup(change, new TextEditGroup(SearchMessages.ReplaceRefactoring_group_label_match_replace, replaceEdit));
//			change.addTextEditChangeGroup(textEditChangeGroup);

		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
//				manager.disconnect(file.getFullPath(), LocationKind.IFILE, null);
//			} catch (CoreException e) {
//				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean createReplaceEdits(StringBuilder sb,
			TextFileChange fileChange) {
		boolean changed = false;
		String[][] arr = CoherenceFunctionMigrator.fCoherenceFunctionMappings;
		MultiTextEdit multiEdit = new MultiTextEdit();
		for (String[] mapping : arr) {
			String cFunction = mapping[0];
			int offset = sb.indexOf(cFunction);
			String asFunction = mapping[1];
			while (offset > -1) {
				ReplaceEdit replaceEdit= new ReplaceEdit(offset, cFunction.length(), asFunction);
				multiEdit.addChild(replaceEdit);
				offset = sb.indexOf(cFunction, offset+1);
				changed = true;
			}
		}
		if (changed) {
			fileChange.setEdit(multiEdit);
		}
		return changed;
	}

	@Override
	public String getName() {
		return "Coherence/AS mappings";
	}

}
