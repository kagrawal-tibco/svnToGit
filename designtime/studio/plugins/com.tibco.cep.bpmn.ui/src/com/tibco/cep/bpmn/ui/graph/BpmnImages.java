package com.tibco.cep.bpmn.ui.graph;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;

import com.tibco.cep.bpmn.model.designtime.utils.BPMNCommonImages;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;

/**
 * @author pdhar
 *
 */
public final class BpmnImages extends BPMNCommonImages {


	private static BpmnImages instance;
	//private static ImageRegistry dgImageRegistry;
	private static ImageRegistry dgImageRegistry16x16;
	private static ImageRegistry dgImageRegistry24x24;
	private static ImageRegistry dgImageRegistry32x32;
	private static ImageRegistry dgImageRegistry48x48;

	private static int imageNo;
	//static List<Image> externalImages = new ArrayList<Image>();

	static List<String> externalImagePaths =  new ArrayList<String>();

	private BpmnImages() {

	}

	public static BpmnImages getInstance() {
		if(instance == null) {
			instance = new BpmnImages();
		}
		return instance;
	}

	@Override
	public boolean loadImage(String key, String path) {
		if (dgImageRegistry16x16 == null) {
			dgImageRegistry16x16 = new ImageRegistry();
		} 
		if (dgImageRegistry24x24 == null) {
			dgImageRegistry24x24 = new ImageRegistry();
		}
		if (dgImageRegistry32x32 == null) {
			dgImageRegistry32x32 = new ImageRegistry();
		}
		if (dgImageRegistry48x48 == null) {
			dgImageRegistry48x48 = new ImageRegistry();
		}
		
		ImageDescriptor descr = BpmnUIPlugin.getDefault().getImageDescriptor(path);

		if (descr != null) {
			if(path.contains(IMAGE_SIZE.SIZE_16.toString())){ 
				dgImageRegistry16x16.put(key, descr);
			}
			else if(path.contains(IMAGE_SIZE.SIZE_24.toString())){
				dgImageRegistry24x24.put(key, descr);
			}
			else if(path.contains(IMAGE_SIZE.SIZE_32.toString())){
				dgImageRegistry32x32.put(key, descr);
			}
			else if(path.contains(IMAGE_SIZE.SIZE_48.toString())){
				dgImageRegistry48x48.put(key, descr);
			}
			//In case some icons do not have any 16x16 or 24x24 or 32x32 or 64x64 in their name default it to 16x16 for now
			else{
				dgImageRegistry16x16.put(key, descr);
			}
		}
		return true;
	}

	/**
	 * @param icon
	 * @return
	 */
	public static ImageIcon  createIcon(String icon)  {
		ImageIcon imageIcon = null;
		try{
			InputStream resource = BpmnUIPlugin.class.getClassLoader().getResourceAsStream(icon);
			if (resource == null) {
				System.err.println("Image file " + icon + " is missing");
			}
			return new ImageIcon(ImageIO.read(resource));
		} catch (IOException e) {
			BpmnUIPlugin.log(e);
		}
		return imageIcon;
	}

	public Image getImage(String key){
		return getImage(key, IMAGE_SIZE.SIZE_16);
	}

	public Image getImage(String key, IMAGE_SIZE size) {
		Image img = null;

		if(key == null || key.isEmpty()){
			switch(size){
			case SIZE_16: img = dgImageRegistry16x16.get(DEFAULT_NULL_ICON);
			break;
			case SIZE_24: img = dgImageRegistry24x24.get(DEFAULT_NULL_ICON);
			break;
			case SIZE_32: img = dgImageRegistry32x32.get(DEFAULT_NULL_ICON);
			break;
			case SIZE_48: img = dgImageRegistry48x48.get(DEFAULT_NULL_ICON);
			default:
				break;
			}
		} else {
			switch(size) {
			case SIZE_16: 
				if (dgImageRegistry16x16.getDescriptor(key) != null) {
					img = dgImageRegistry16x16.get(key);
				} else {
					try {
						ImageData imageData = new ImageData(key);
						img = (new Image(Display.getDefault(), imageData));
						if (imageData.width > 16 || imageData.height > 16) {
							img =  new Image(Display.getDefault(),
									img.getImageData().scaledTo(16,16));
						}
					} catch (Exception e){
						img = dgImageRegistry16x16.get(DEFAULT_NULL_ICON);
					}
				}
				break;

			case SIZE_24: 
				if (dgImageRegistry24x24.getDescriptor(key) != null) {
					img = dgImageRegistry24x24.get(key);
				} else {
					try {
						ImageData imageData = new ImageData(key);
						img = (new Image(Display.getDefault(), imageData));
					} catch (Exception e) {
						img = dgImageRegistry24x24.get(DEFAULT_NULL_ICON);
					}
				}
				break;
			case SIZE_32: 
				if (dgImageRegistry32x32.getDescriptor(key) != null) {
					img = dgImageRegistry32x32.get(key);
				} else {
					try {
						ImageData imageData = new ImageData(key);
						img = (new Image(Display.getDefault(), imageData));
					} catch (Exception e) {
						img = dgImageRegistry32x32.get(DEFAULT_NULL_ICON);
					}
				}
				break;
			case SIZE_48: 
				if (dgImageRegistry48x48.getDescriptor(key) != null) {
					img = dgImageRegistry48x48.get(key);
				} else {
					try {
						ImageData imageData = new ImageData(key);
						img = (new Image(Display.getDefault(), imageData));
					} catch (Exception e) {
						img = dgImageRegistry48x48.get(DEFAULT_NULL_ICON);
					}
				}
				break;
			default:
				break;
			}
		}

		return img;

	}

	public List<String> getExtrernalImage() throws IOException {
		externalImagePaths.clear();
		imageNo = 0;
		//		org.eclipse.osgi.framework.internal.core.BundleHost b = (org.eclipse.osgi.framework.internal.core.BundleHost) Platform.getBundle(BpmnUIPlugin.PLUGIN_ID);
		// why are we looking up the images this way?
		//		File folder = null;//((org.eclipse.osgi.baseadaptor.BaseData)b.getBundleData()).getBundleFile().getFile("/icons/", true);
		////		File folder = new File("D://svn//be//5.1//designtime//studio//plugins//com.tibco.cep.bpmn.ui//icons");
		//        if(folder!=null) {
		//        	listFilesForFolder(folder);
		//        }

		Bundle bundle = Platform.getBundle(BpmnUIPlugin.PLUGIN_ID);
		URL url = bundle.getEntry("/icons/");
		URL fileURL = org.eclipse.core.runtime.FileLocator.toFileURL(url);
		File folder;
		try {
			folder = new File(fileURL.toURI());
		} catch(URISyntaxException e) {
			folder = new File(fileURL.getPath());
		}
		if ( folder != null ) {
			listFilesForFolder(folder);
		}

		return externalImagePaths;

	}
	public static void listFilesForFolder(final File folder) {
		String imagename = new String();
		String folderPathCheck=folder.getName();
		if (folderPathCheck.startsWith("."))
			return;
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				if(fileEntry.exists()){
					String name = fileEntry.getName();
					name = name.substring(name.indexOf(".")+1);
					if(!name.equalsIgnoreCase("svg")){
						imagename= "Icon_" + imageNo++;
						System.out.println(imagename+"    "+fileEntry.getPath());
						externalImagePaths.add(fileEntry.getPath());
					}
				}

			}
		}

	}

}
