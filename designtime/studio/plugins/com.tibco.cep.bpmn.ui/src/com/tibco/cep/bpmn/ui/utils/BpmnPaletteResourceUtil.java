package com.tibco.cep.bpmn.ui.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupEmfItemType;
import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupItemType;
import com.tibco.cep.bpmn.core.BpmnCoreConstants;
import com.tibco.cep.bpmn.model.designtime.utils.BPMNCommonImages.IMAGE_SIZE;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonPaletteResourceUtil;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tibco.cep.bpmn.ui.graph.BpmnImages;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeAdapterFactory;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeListener;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroup;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.bpmn.ui.preferences.BpmnPreferenceConstants;
import com.tibco.cep.studio.common.palette.DocumentRoot;
import com.tibco.cep.studio.common.palette.Help;
import com.tibco.cep.studio.common.palette.PaletteFactory;
import com.tibco.cep.studio.common.palette.PaletteModel;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.ui.palette.StudioPaletteUI;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * 
 * @author majha
 * 
 */
public class BpmnPaletteResourceUtil extends BpmnCommonPaletteResourceUtil{

	@SuppressWarnings("unused")
	private static final Object IFile = null;
	private static HashMap<String, List<Help>> defaultHelpMap;

	static public BpmnPaletteModel loadBpmnPalette(URI uri,
			ModelChangeAdapterFactory factory) {
		Resource resource = getResource(uri);
		Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMIResource.OPTION_ENCODING, "UTF-8");
		try {
			resource.load(options);
			DocumentRoot dr = (DocumentRoot) resource.getContents().get(0);
			PaletteModel model = dr.getModel();
			BpmnPaletteModel bpmnPaletteModel = new BpmnPaletteModel(model);
			if (factory != null) {
				factory.adapt(model, ModelChangeListener.class);
				List<BpmnPaletteGroup> bpmnPaletteGroups = bpmnPaletteModel
						.getBpmnPaletteGroups();
				for (BpmnPaletteGroup bpmnPaletteGroup : bpmnPaletteGroups) {
					factory.adapt(bpmnPaletteGroup.getGroup(),
							ModelChangeListener.class);
					List<BpmnPaletteGroupItem> paletteItems = bpmnPaletteGroup
							.getPaletteItems();
					for (BpmnPaletteGroupItem bpmnPaletteGroupItem : paletteItems) {
						factory.adapt(bpmnPaletteGroupItem.getItem(),
								ModelChangeListener.class);
						BpmnCommonPaletteGroupItemType itemType = bpmnPaletteGroupItem
								.getItemType();
						factory.adapt(itemType.getWrappedType(), ModelChangeListener.class);
						EList<Help> helpContent = bpmnPaletteGroupItem.getItem().getHelpContent();
						for (Help help : helpContent) {
							factory.adapt(help, ModelChangeListener.class);
						}
					}
				}
			}
			return bpmnPaletteModel;

		} catch (IOException e) {
			BpmnUIPlugin.log(e);
		}

		return null;
	}

	static public BpmnPaletteModel loadBpmnPalette(IResource file,
			ModelChangeAdapterFactory factory) {
		URI uri = URI.createPlatformResourceURI(file.getFullPath().toPortableString(), false);
		return loadBpmnPalette(uri, factory);
	}

	static public BpmnPaletteModel loadBpmnPalette(File file,
			ModelChangeAdapterFactory factory) {
		URI uri = URI.createFileURI(file.getAbsolutePath());
		return loadBpmnPalette(uri, factory);
	}

	static public void saveBpmnPalette(BpmnPaletteModel model, IResource file) {
		URI uri = URI.createPlatformResourceURI(file.getFullPath().toPortableString(), false);
		Resource resource = getResource(uri);
		Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMIResource.OPTION_ENCODING, "UTF-8");
		try {
			DocumentRoot createDocumentRoot = PaletteFactory.eINSTANCE
					.createDocumentRoot();
			createDocumentRoot.setModel(model.getModel());
			resource.getContents().add(createDocumentRoot);
			resource.save(options);

		} catch (IOException e) {
			BpmnUIPlugin.log(e);
		}

	}

	static public BpmnPaletteModel loadDefault() {
		try {
//			List<URL> urlList = new LinkedList<URL>();
//			Enumeration<URL> e = BpmnPaletteResourceUtil.class.getClassLoader()
//					.getSystemResources("/schema/" + BpmnCoreConstants.DEFAULT_BEBPMNPALETTE);
//			while (e.hasMoreElements()) {
//				urlList.add(e.nextElement());
//			}
//			e = BpmnUIPlugin.class.getClassLoader().getResources("/schema/" + 
//					BpmnCoreConstants.DEFAULT_BEBPMNPALETTE);
//			while (e.hasMoreElements()) {
//				urlList.add(e.nextElement());
//			}
//			URL url = urlList.get(0);
//			URI uri = URI.createFileURI(url.getPath());
//			Resource resource = getResource(uri);
//			Map<Object, Object> options = new HashMap<Object, Object>();
//			options.put(XMIResource.OPTION_ENCODING, "UTF-8");
//			resource.load(url.openStream(),options);
//			DocumentRoot dr = (DocumentRoot) resource.getContents().get(0);
//			PaletteModel model = dr.getModel();
			PaletteModel model=BpmnCommonPaletteResourceUtil.loadDefaultProcesspalette();
			BpmnPaletteModel bpmnPaletteModel = new BpmnPaletteModel(model);
			return bpmnPaletteModel;

		}catch (Exception ex) {
			BpmnUIPlugin.log(ex);
		}

		return null;
	}
	
	static public Map<String, List<Help>> getDefultHelpMap(){
		if(defaultHelpMap == null){
			defaultHelpMap = new HashMap<String, List<Help>>();
			BpmnPaletteModel loadDefault = loadDefault();
			List<BpmnPaletteGroup> bpmnPaletteGroups = loadDefault.getBpmnPaletteGroups();
			for (BpmnPaletteGroup bpmnPaletteGroup : bpmnPaletteGroups) {
				List<BpmnPaletteGroupItem> paletteItems = bpmnPaletteGroup.getPaletteItems();
				for (BpmnPaletteGroupItem bpmnPaletteGroupItem : paletteItems) {
					BpmnCommonPaletteGroupItemType itemType = bpmnPaletteGroupItem.getItemType();
					if(itemType instanceof BpmnCommonPaletteGroupEmfItemType){
						BpmnCommonPaletteGroupEmfItemType eItemType = (BpmnCommonPaletteGroupEmfItemType)itemType;
						ExpandedName emfType = eItemType.getEmfType();
						ExpandedName extendedType = eItemType.getExtendedType();
						String type = "";
						if(emfType != null)
							type = type + emfType.toString();
						if(extendedType != null)
							type = type + extendedType.toString();
						
						if(!type.trim().isEmpty())
							defaultHelpMap.put(type, bpmnPaletteGroupItem.getHelps());
					}
					
				}
			}
		}
		return defaultHelpMap;
	}

	
	
	public static Map<IFile, BpmnPaletteModel> getAllPalettes(IProject project)  {
		final Map<IFile, BpmnPaletteModel> paletteList = new HashMap<IFile, BpmnPaletteModel>();
		try {
			IResourceVisitor visitor = new IResourceVisitor() {

				@Override
				public boolean visit(IResource resource)
						throws CoreException {

					if (resource instanceof IContainer) {
						return true;
					} else if (resource instanceof IFile) {
						IFile file = (IFile) resource;
						if (isBpmnPaletteType(file)) {
							BpmnPaletteModel loadBpmnPalette = loadBpmnPalette(file, null);
							if(loadBpmnPalette != null)
								paletteList.put(file, loadBpmnPalette);
						}
					}
					return false;
				}

			};
			
			project.accept(visitor);
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
		
		
		return paletteList;
		
	}

	public static boolean isBpmnPaletteType(IFile file){
		return BpmnCoreConstants.PALETTE_FILE_EXTN.equals(file
				.getFileExtension());
			
	}

	public static boolean isDuplicateProcessResource(IResource parentResource,
			String newName, StringBuilder duplicateFileName) {
		Object[] object = CommonUtil.getResources((IContainer) parentResource);
		for (Object obj : object) {
			if (obj instanceof IFile) {
				if (isBpmnPaletteType((IFile) obj)) {
					IFile file = (IFile) obj;
					int index = file.getName().indexOf('.');
					if (newName.equalsIgnoreCase(file.getName().substring(0, index))) {
						duplicateFileName.append(((IFile) obj).getName());
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static byte[] getBpmnPaletteContents(BpmnPaletteModel model, IResource file) {
		URI uri = URI.createPlatformResourceURI(file.getFullPath().toPortableString(), false);
		Resource resource = getResource(uri);
		Resource oldResource = model.getModel().eResource();
		

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMIResource.OPTION_ENCODING, "UTF-8");
		try {
			DocumentRoot createDocumentRoot = PaletteFactory.eINSTANCE
					.createDocumentRoot();
			createDocumentRoot.setModel(model.getModel());
			resource.getContents().add(createDocumentRoot);
			resource.save(os, options);
			return os.toByteArray();

		} catch (IOException e) {
			BpmnUIPlugin.log(e);
		}finally {
			try {
				os.close();
				if (oldResource != null) {
					oldResource.getContents().add(model.getModel());
				}
			} catch (Exception e) {
			}
		}
		
		return null;
	}
	
	public static Image getPaletteImage(String img) {
		boolean showChangePaletteImagePixels = 
				BpmnUIPlugin.getDefault().getPreferenceStore()
				.getBoolean(BpmnPreferenceConstants.PREF_CHANGE_PALLETE_IMAGE_PIXELS);
		if (showChangePaletteImagePixels) {
			String imageSizePref = BpmnUIPlugin.getDefault().getPreferenceStore()
					.getString(BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS);
			IMAGE_SIZE imageSize = IMAGE_SIZE.get(imageSizePref);
			return BpmnImages.getInstance().getImage(img, imageSize);
		} else {
			return BpmnImages.getInstance().getImage(img);
		}
	}
	
	public static void setPaletteFont(Control control) {
		if (control != null) {
			boolean showChangePaletteImagePixels = 
					BpmnUIPlugin.getDefault().getPreferenceStore()
					.getBoolean(BpmnPreferenceConstants.PREF_CHANGE_PALLETE_IMAGE_PIXELS);
			String imageSizePref = BpmnUIPlugin.getDefault().getPreferenceStore()
					.getString(BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS);
			if (showChangePaletteImagePixels) {
				IMAGE_SIZE imageSize = IMAGE_SIZE.get(imageSizePref);
				switch(imageSize) {
				case SIZE_16:
					Font font = new Font(null, "Tahoma", 8, SWT.NONE);
					control.setFont(font);
					break;
				case SIZE_24:
					font = new Font(null, "Tahoma", 10, SWT.NONE);
					control.setFont(font);
					break;
				case SIZE_32:
					font = new Font(null, "Tahoma", 12, SWT.NONE);
					control.setFont(font);
					break;
				case SIZE_48:
					font = new Font(null, "Tahoma", 14, SWT.NONE);
					control.setFont(font);
					break;
				}
			} else {
				Font font = new Font(null, "Tahoma", 8, SWT.NONE);
				control.setFont(font);
			}
		}
	}
	
	public static void updatePaletteDrawerFont(ExpandBar expandBar) {
		boolean showChangePaletteImagePixels = 
				BpmnUIPlugin.getDefault().getPreferenceStore()
				.getBoolean(BpmnPreferenceConstants.PREF_CHANGE_PALLETE_IMAGE_PIXELS);
		String imageSizePref = BpmnUIPlugin.getDefault().getPreferenceStore()
				.getString(BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS);
		if (showChangePaletteImagePixels) {
			IMAGE_SIZE imageSize = IMAGE_SIZE.get(imageSizePref);
			switch(imageSize) {
			case SIZE_16:
				Device device = expandBar.getFont().getDevice();
				Font font = new Font(device, "Segoe UI", 9, SWT.NONE);
				expandBar.setFont(font);
//				expandBar.getFont().getFontData()[0].setHeight(9);
				break;
			case SIZE_24:
				device = expandBar.getFont().getDevice();
				font = new Font(device, "Segoe UI", 11, SWT.NONE);
				expandBar.setFont(font);
//				expandBar.getFont().getFontData()[0].setHeight(11);
				break;
			case SIZE_32:
				device = expandBar.getFont().getDevice();
				font = new Font(device, "Segoe UI", 13, SWT.NONE);
				expandBar.setFont(font);
//				expandBar.getFont().getFontData()[0].setHeight(13);
				break;
			case SIZE_48:
				device = expandBar.getFont().getDevice();
				font = new Font(device, "Segoe UI", 15, SWT.NONE);
				expandBar.setFont(font);
//				expandBar.getFont().getFontData()[0].setHeight(15);
				break;
			}
		} else {
			Device device = expandBar.getFont().getDevice();
			Font font = new Font(device, "device", 9, SWT.NONE);
			expandBar.setFont(font);
//			expandBar.getFont().getFontData()[0].setHeight(9);
		}
	}

	public static void refreshPalette() {
		IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (e instanceof BpmnEditor) {
			BpmnEditor bpmnEditor = (BpmnEditor) e;
			if (bpmnEditor.getPartListener() != null) {
				StudioPaletteUI.resetSwitchEditorPalette(bpmnEditor.getEditorSite(), true);
				bpmnEditor.getPartListener().updatePaletteView(e, e.getEditorSite(), true);
			}
		}
	}
	
}
