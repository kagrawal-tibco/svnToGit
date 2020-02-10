package com.tibco.cep.studio.ui.editors.rules.text;

import java.io.UnsupportedEncodingException;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationPresenter;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;

import com.tibco.cep.studio.core.utils.ModelUtils;

public class MethodParameterContextValidator implements
		IContextInformationPresenter, IContextInformationValidator {

	private IContextInformation fInfo;
	private ITextViewer fViewer;
	private int fOffset;
	
	@Override
	public void install(IContextInformation info, ITextViewer viewer, int offset) {
		this.fInfo = info;
		this.fViewer = viewer;
		this.fOffset = offset;
	}

	@Override
	public boolean updatePresentation(int offset, TextPresentation presentation) {
		presentation.clear();
		try {
			if (fOffset > offset) {
				// shouldn't happen
				return false;
			}
			String typedSoFar = fViewer.getDocument().get(fOffset, offset-fOffset);
			String displayString = fInfo.getInformationDisplayString();
			int currentArgNum = getCurrentArgNum(typedSoFar, displayString);
			if (currentArgNum > getTotalArgs(displayString)) {
				return false;
			}
			// now, get the offsets for that arg in 'displayString'
			byte[] bytes;
			try {
				bytes = displayString.getBytes(ModelUtils.DEFAULT_ENCODING);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				bytes = displayString.getBytes();
			}
			int start = 0;
			int end = 0;
			int ctr = 1;
			for (int i = 0; i < bytes.length; i++) {
				if (bytes[i] == ',') {
					if (ctr == currentArgNum) {
						end = i;
						break;
					}
					ctr++;
					if (ctr == currentArgNum) {
						start = i;
					}
				}
			}
			if (end == 0) {
				end = bytes.length;
			}
			if (currentArgNum > 1) {
				start++;
			}
			if (start < end) {
				StyleRange range = new StyleRange(start, end-start, null, null, SWT.BOLD);
				presentation.replaceStyleRange(range);
				return true;
			}
			
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return false;
	}

	private int getCurrentArgNum(String typedSoFar, String displayString) {
		// TODO : be smarter about embedded method calls, etc
		int argNum = 1;
		byte[] bytes;
		try {
			bytes = typedSoFar.getBytes(ModelUtils.DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			bytes = typedSoFar.getBytes();
		}
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] == ',') {
				argNum++;
			}
		}
		return argNum;
	}

	@Override
	public boolean isContextInformationValid(int offset) {
		if (fOffset > offset) {
			return false;
		}
		String displayString = fInfo.getInformationDisplayString();
		if (displayString == null || displayString.length() == 0) {
			return false;
		}
		String typedSoFar;
		try {
			typedSoFar = fViewer.getDocument().get(fOffset, offset-fOffset);
			int currentArgNum = getCurrentArgNum(typedSoFar, displayString);
			if (currentArgNum > getTotalArgs(displayString)) {
				return false;
			}
			if (typedSoFar.length() > 0 && typedSoFar.charAt(typedSoFar.length()-1) == ')') {
				return false;
			} else if (fOffset == offset && fOffset > 0) {
				if (fViewer.getDocument().get(fOffset-1, 1).equals(")")) {
					return false;
				}
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return true;
	}

	private int getTotalArgs(String argString) {
		if (argString == null || argString.length() == 0) {
			return 0;
		}
		return argString.split(",").length;
	}

}
