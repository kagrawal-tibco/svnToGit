package com.tibco.cep.sharedresource.refactor;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.text.edits.ReplaceEdit;

import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.refactoring.StudioRefactoringContext;
import com.tibco.cep.studio.core.refactoring.StudioRefactoringParticipant;

public abstract class AbstractSharedResourceRefactoring extends StudioRefactoringParticipant {

	public abstract String getExtension();
	
	/**
	 * @param elementPath
	 * @param folder
	 * @return
	 */
	protected String getNewPath(String elementPath, IFolder folder) {
		int offset = 0;
		String initChar = "";
		if (elementPath.startsWith("/")) {
			initChar = "/";
			offset = 1;
		}
		String oldPath = folder.getProjectRelativePath().toString();
		if (isMoveRefactor()) {
			return getNewElementPath() + folder.getName() + elementPath.substring(oldPath.length()+offset);
		} else if (isRenameRefactor()) {
			if (folder.getParent() instanceof IFolder) {
				String parentPath = folder.getParent().getProjectRelativePath().toString();
				return initChar + parentPath + "/" + getNewElementName() + elementPath.substring(oldPath.length()+offset);
			}
			return initChar + getNewElementName() + elementPath.substring(oldPath.length()+offset);
		}
		return elementPath;
	}
	
	/**
	 * @param elementToRefactor
	 * @param element
	 * @return
	 */
	protected boolean isFileProcessed(Object elementToRefactor, IFile element){
		if(elementToRefactor instanceof StudioRefactoringContext){
	    	StudioRefactoringContext context = (StudioRefactoringContext)elementToRefactor;
	    	if(context.getElement() instanceof IFile){
	    		IFile file = (IFile)context.getElement();
	    		if(file.getFullPath().toString().equals(element.getFullPath().toString())){
	    			return true;
	    		}
	    	}
		}
		return false;
	}
	
	/**
	 * @param file
	 * @param modelmgr
	 * @return
	 * @throws CoreException
	 */
	protected Change createTextFileChange(IFile file, String changedText) throws CoreException {
		TextFileChange change = null;
		InputStream fis = null;
		try {
			String contents = new String(changedText.getBytes(file.getCharset()));
			change = new TextFileChange("New name change", file);
			fis = file.getContents();
			int size = IndexUtils.getFileSize(file, fis);
			ReplaceEdit edit = new ReplaceEdit(0, size, contents);
			change.setEdit(edit);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return change;
	}

}
