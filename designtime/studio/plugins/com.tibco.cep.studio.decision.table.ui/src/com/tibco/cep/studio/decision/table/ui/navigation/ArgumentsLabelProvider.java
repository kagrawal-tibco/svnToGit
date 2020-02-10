package com.tibco.cep.studio.decision.table.ui.navigation;

import static com.tibco.cep.decision.table.language.DTRegexPatterns.ENDS_WITH_ARRAY;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.ui.navigator.IDescriptionProvider;

import com.tibco.cep.decisionproject.ontology.ArgumentResource;
import com.tibco.cep.studio.ui.util.ColorConstants;

/**
 * 
 * @author sasahoo
 *
 */
public class ArgumentsLabelProvider extends AbstractResourceLabelProvider implements IStyledLabelProvider ,IDescriptionProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		return getOverlayImage(getAdapterFactoryLabelProvider().getImage(element));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof ArgumentResource) {
			ArgumentResource argumentResource = (ArgumentResource)element;
			if (argumentResource.getAlias() != null) {
				String path = argumentResource.getPath();
				String alias = argumentResource.getAlias();
				if (ENDS_WITH_ARRAY.matcher(alias).matches()) {
					//Chunk it for display
					String[] splits = alias.split("\\[\\]");
					//Join all parts
					StringBuilder jointAliasBuffer = new StringBuilder();
					for (String split : splits) {
						jointAliasBuffer.append(split);
					}
					alias = jointAliasBuffer.toString();
				}
				return alias + " [" + path + "]";
			}
		}
		return getAdapterFactoryLabelProvider().getText(element);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider#getStyledText(java.lang.Object)
	 */
	@Override
	public StyledString getStyledText(Object element) {
		String text = getText(element);
		int offset = text.indexOf("[");
		int len = text.length() - offset;
		StyledString styledText =  new StyledString(text);
		if (element instanceof ArgumentResource) {
			styledText.setStyle(offset + 1, len - 2, new TextStyler(ColorConstants.gray));
		}
		return styledText;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.navigator.IDescriptionProvider#getDescription(java.lang.Object)
	 */
	@Override
	public String getDescription(Object anElement) {
		return getLabel(anElement);
	}
	
	/**
	 * @param element
	 * @return
	 */
	private String getLabel(Object element) {
		if (element instanceof ArgumentResource) {
			ArgumentResource argumentResource = (ArgumentResource)element;
			if (argumentResource.getAlias() != null) {
				String path = argumentResource.getPath();
				String alias = argumentResource.getAlias();
				if (ENDS_WITH_ARRAY.matcher(alias).matches()) {
					//Chunk it for display
					alias = alias.substring(0, alias.indexOf('['));
					System.out.printf("Alias in args explorer %s ", alias);
				}
				return alias + " [" + path + "]";
			}
		}
		return getAdapterFactoryLabelProvider().getText(element);
	}

	private static class TextStyler extends Styler {
		
		private Color foreground;
		
		/**
		 * @param foreground
		 */
		public TextStyler (Color foreground) {
			this.foreground = foreground;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.StyledString.Styler#applyStyles(org.eclipse.swt.graphics.TextStyle)
		 */
		public void applyStyles(TextStyle textStyle) {
			textStyle.foreground = foreground;
		}
	}
}