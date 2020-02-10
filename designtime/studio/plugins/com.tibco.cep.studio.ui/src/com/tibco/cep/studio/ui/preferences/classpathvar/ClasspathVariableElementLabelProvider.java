package com.tibco.cep.studio.ui.preferences.classpathvar;

import java.util.ArrayList;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.ClasspathVariableUiUtils;
import com.tibco.cep.studio.ui.util.Messages;

public class ClasspathVariableElementLabelProvider extends LabelProvider implements IColorProvider {

	// shared, do not dispose:
	private Image fJARImage;
	private Image fFolderImage;
	private Color fResolvedBackground;

	private Image fDeprecatedJARImage;
	private Image fDeprecatedFolderImage;

	private boolean fHighlightReadOnly;

	public ClasspathVariableElementLabelProvider(boolean highlightReadOnly) {
//		ImageRegistry reg= StudioUIPlugin.getDefault().getImageRegistry();
		fJARImage= StudioUIPlugin.getImageDescriptor("icons/vars/jar_lsrc_obj.gif").createImage();
		fFolderImage= PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);

		fDeprecatedJARImage= new DecorationOverlayIcon(fJARImage,  StudioUIPlugin.getImageDescriptor("icons/vars/deprecated.gif"), IDecoration.TOP_LEFT).createImage();
		fDeprecatedFolderImage= new DecorationOverlayIcon(fFolderImage,  StudioUIPlugin.getImageDescriptor("icons/vars/deprecated.gif"), IDecoration.TOP_LEFT).createImage();

		fHighlightReadOnly= highlightReadOnly;
		fResolvedBackground= null;
	}

	/*
	 * @see LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof ClasspathVariableElement) {
			ClasspathVariableElement curr= (ClasspathVariableElement) element;
			IPath path= curr.getPath();
			if (path.toFile().isFile()) {
				return curr.isDeprecated() ? fDeprecatedJARImage : fJARImage;
			}
			return curr.isDeprecated() ? fDeprecatedFolderImage : fFolderImage;
		}
		return super.getImage(element);
	}

	/*
	 * @see LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof ClasspathVariableElement) {
			ClasspathVariableElement curr= (ClasspathVariableElement)element;
			String name= curr.getName();
			IPath path= curr.getPath();

			String result= name;
			ArrayList<String> restrictions= new ArrayList<String>(2);

			if (curr.isReadOnly() && fHighlightReadOnly) {
				restrictions.add("non modifiable");
			}
			if (curr.isDeprecated()) {
				restrictions.add("deprecated");
			}
			if (restrictions.size() == 1) {
				result= Messages.getString("CPVariableElementLabelProvider_one_restriction", new Object[] {result, restrictions.get(0)});
			} else if (restrictions.size() == 2) {
				result= Messages.getString("CPVariableElementLabelProvider_two_restrictions", new Object[] {result, restrictions.get(0), restrictions.get(1)});
			}

			if (path != null) {
				String appendix;
				if (!path.isEmpty()) {
					appendix= ClasspathVariableUiUtils.getPathLabel(path, true);
				} else {
					appendix= "(empty)";
				}
				result= Messages.getString("CPVariableElementLabelProvider_appendix", new Object[] {result, appendix});
			}

			return result;
		}


		return super.getText(element);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
	 */
	public Color getForeground(Object element) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
	 */
	public Color getBackground(Object element) {
		if (element instanceof ClasspathVariableElement) {
			ClasspathVariableElement curr= (ClasspathVariableElement) element;
			if (fHighlightReadOnly && curr.isReadOnly()) {
				if (fResolvedBackground == null) {
					Display display= Display.getCurrent();
					fResolvedBackground= display.getSystemColor(SWT.COLOR_INFO_BACKGROUND);
				}
				return fResolvedBackground;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		fDeprecatedFolderImage.dispose();
		fDeprecatedJARImage.dispose();
	}

}
