package com.tibco.cep.studio.ui.navigator.providers;

import java.util.HashMap;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.resource.ColorDescriptor;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.model.IWorkbenchAdapter2;
import org.eclipse.ui.model.IWorkbenchAdapter3;

import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.StudioJavaUtil;
import com.tibco.cep.studio.ui.navigator.NavigatorPlugin;

public class StudioNavigatorLabelProvider extends LabelProvider implements IColorProvider, IFontProvider, IStyledLabelProvider {

	private HashMap<String, Image> fImageCache = new HashMap<String, Image>();
	private ResourceManager resourceManager;
	public static final String JAVA_PACKAGE_FOLDER = "java_package_folder";
	public static final String JAVA_PACKAGE_FOLDER_EMPTY = "java_package_folder_empty";
	public static final String JAVA_SOURCE_FOLDER = "java_source_folder";
	public static final String JAVA_PACKAGE_FOLDER_TREE_EMPTY = "java_package_folder_tree_empty";

	public static ILabelProvider getDecoratingWorkbenchLabelProvider() {
		return new DecoratingLabelProvider(new StudioNavigatorLabelProvider(),
				PlatformUI.getWorkbench().getDecoratorManager()
				.getLabelDecorator());
	}

	private IPropertyListener editorRegistryListener = new IPropertyListener() {
		public void propertyChanged(Object source, int propId) {
			if (propId == IEditorRegistry.PROP_CONTENTS) {
				fireLabelProviderChanged(new LabelProviderChangedEvent(StudioNavigatorLabelProvider.this));
			}
		}
	};		

	public StudioNavigatorLabelProvider() {
		PlatformUI.getWorkbench().getEditorRegistry().addPropertyListener(editorRegistryListener);
	}

	protected ImageDescriptor decorateImage(ImageDescriptor input,
			Object element) {
		return input;
	}

	protected String decorateText(String input, Object element) {
		return input;
	}

	public void dispose() {
		PlatformUI.getWorkbench().getEditorRegistry().removePropertyListener(editorRegistryListener);
		if (resourceManager != null)
			resourceManager.dispose();
		resourceManager = null;
		super.dispose();
	}

	protected final IWorkbenchAdapter getAdapter(Object o) {
		return (IWorkbenchAdapter)getAdapter(o, IWorkbenchAdapter.class);
	}

	protected final IWorkbenchAdapter2 getAdapter2(Object o) {
		return (IWorkbenchAdapter2)getAdapter(o, IWorkbenchAdapter2.class);
	}

	protected final IWorkbenchAdapter3 getAdapter3(Object o) {
		return (IWorkbenchAdapter3) getAdapter(o, IWorkbenchAdapter3.class);
	}

	private ResourceManager getResourceManager() {
		if (resourceManager == null) {
			resourceManager = new LocalResourceManager(JFaceResources.getResources());
		}
		return resourceManager;
	}

	public Image getImage(Object element) {
		IWorkbenchAdapter adapter = getAdapter(element);
		if (adapter == null) {
			return null;
		}
		ImageDescriptor descriptor = adapter.getImageDescriptor(element);
		if (descriptor == null) {
			return null;
		}

		Image javaSourceImage = getJavaResourceImage(element);
		if ( javaSourceImage != null) {
			return javaSourceImage;
		}

		descriptor = decorateImage(descriptor, element);

		return (Image) getResourceManager().get(descriptor);
	}

	public StyledString getStyledText(Object element) {
		IWorkbenchAdapter3 adapter = getAdapter3(element);
		if (adapter == null) {
			return new StyledString(getText(element));
		}
		StyledString styledString = adapter.getStyledText(element);
		String decorated = decorateText(styledString.getString(), element);
		Styler styler = getDecorationStyle(element);
		return StyledCellLabelProvider.styleDecoratedString(decorated, styler, styledString);
	}

	protected Styler getDecorationStyle(Object element) {
		return StyledString.DECORATIONS_STYLER;
	}

	public final String getText(Object element) {
		//query the element for its label
		IWorkbenchAdapter adapter = getAdapter(element);
		if (adapter == null) {
			return ""; //$NON-NLS-1$
		}
		String label = adapter.getLabel(element);

		//return the decorated label
		return decorateText(label, element);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
	 */
	public Color getForeground(Object element) {
		return getColor(element, true);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
	 */
	public Color getBackground(Object element) {
		return getColor(element, false);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFontProvider#getFont(java.lang.Object)
	 */
	public Font getFont(Object element) {
		IWorkbenchAdapter2 adapter = getAdapter2(element);
		if (adapter == null) {
			return null;
		}

		FontData descriptor = adapter.getFont(element);
		if (descriptor == null) {
			return null;
		}

		return (Font) getResourceManager().get(
				FontDescriptor.createFrom(descriptor));
	}

	private Color getColor(Object element, boolean forground) {
		IWorkbenchAdapter2 adapter = getAdapter2(element);
		if (adapter == null) {
			return null;
		}
		RGB descriptor = forground ? adapter.getForeground(element) : adapter
				.getBackground(element);
		if (descriptor == null) {
			return null;
		}

		return (Color) getResourceManager().get(
				ColorDescriptor.createFrom(descriptor));
	}

	@SuppressWarnings("rawtypes")
	public static Object getAdapter(Object sourceObject, Class adapterType) {
		Assert.isNotNull(adapterType);
		if (sourceObject == null) {
			return null;
		}
		if (adapterType.isInstance(sourceObject)) {
			return sourceObject;
		}

		if (sourceObject instanceof IAdaptable) {
			IAdaptable adaptable = (IAdaptable) sourceObject;

			Object result = adaptable.getAdapter(adapterType);
			if (result != null) {
				// Sanity-check
				Assert.isTrue(adapterType.isInstance(result));
				return result;
			}
		} 

		if (!(sourceObject instanceof PlatformObject)) {
			Object result = Platform.getAdapterManager().getAdapter(sourceObject, adapterType);
			if (result != null) {
				return result;
			}
		}

		return null;
	}

	/**
	 * @param element
	 * @return
	 */
	private Image getJavaResourceImage(Object element) {
		Image image = null;
		if (element instanceof IFolder) {
			IFolder folder = (IFolder)element;
			try {
				IProject fProject = folder.getProject();
				if (folder.getProject().hasNature(JavaCore.NATURE_ID)) {
					IJavaProject javaProject = JavaCore.create(fProject);
					IClasspathEntry entry = StudioJavaUtil.getClassPathEntryForPath(folder.getFullPath(), javaProject, IClasspathEntry.CPE_SOURCE);
					if (entry != null) {
						if (fImageCache.get(JAVA_SOURCE_FOLDER) == null ) {
							image = fImageCache.put(JAVA_SOURCE_FOLDER, NavigatorPlugin.getDefault().getImage("icons/packagefolder_obj.gif"));
						}
						image =  fImageCache.get(JAVA_SOURCE_FOLDER);
					} else {
						for (IPackageFragment packageFolder : javaProject.getPackageFragments()) {
							if (packageFolder.getPath().toString().equals(folder.getFullPath().toString())) {
								if (containsJavaResources(folder)) {
									if (fImageCache.get(JAVA_PACKAGE_FOLDER) == null ) {
										image = fImageCache.put(JAVA_PACKAGE_FOLDER, NavigatorPlugin.getDefault().getImage("icons/package_obj.gif"));
									} else {
										image = fImageCache.get(JAVA_PACKAGE_FOLDER);
									}
								} else {
									if (fImageCache.get(JAVA_PACKAGE_FOLDER_EMPTY) == null ) {
										image = fImageCache.put(JAVA_PACKAGE_FOLDER_EMPTY, NavigatorPlugin.getDefault().getImage("icons/empty_pack_obj.gif"));
									} else {
										image = fImageCache.get(JAVA_PACKAGE_FOLDER_EMPTY);
									}
								}

							}
						}
					}
//					for (IPackageFragment packageFolder : javaProject.getPackageFragments()) {
//						if (packageFolder.getPath().toString().equals(folder.getFullPath().toString())) {
//							if (!traverseAllJavaResources(folder)) {
//								if (fImageCache.get(JAVA_PACKAGE_FOLDER_TREE_EMPTY) == null ) {
//									image = fImageCache.put(JAVA_PACKAGE_FOLDER_TREE_EMPTY, NavigatorPlugin.getDefault().getImage("icons/empty_pack_fldr_obj.gif"));
//								} else {
//									image = fImageCache.get(JAVA_PACKAGE_FOLDER_TREE_EMPTY);
//								}
//							} 
//						}
//					}
				}
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return image;
	}

	/**
	 * @param container
	 * @return
	 */
	public boolean containsJavaResources(IContainer container) {
		for (Object element : CommonUtil.getResources(container)) {
			if (element instanceof IFile) {
				IFile file = (IFile)element;
				if (IndexUtils.isJavaType(file)) {
					return true;
				}
			} 
		}
		return false;
	}

	/**
	 * @param container
	 * @return
	 */
	public boolean traverseAllJavaResources(IContainer container) {
		boolean isPresent = false;
		for (Object element : CommonUtil.getResources(container)) {
			if (element instanceof IFile) { 
				IFile file = (IFile)element;
				if (IndexUtils.isJavaType(file)) {
					isPresent = true;
					break;
				}
			} 
			if (element instanceof IContainer) {
				traverseAllJavaResources((IContainer)element);
			}
		}
		return isPresent;
	}
	
}