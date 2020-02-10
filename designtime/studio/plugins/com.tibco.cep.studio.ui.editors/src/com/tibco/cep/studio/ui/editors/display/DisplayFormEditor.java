package com.tibco.cep.studio.ui.editors.display;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.DisplayProperties;
import com.tibco.cep.studio.core.util.DisplayUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;

public class DisplayFormEditor extends AbstractStudioResourceEditorPart {

	private DisplayFormDesignViewer displayViewer;
	private DisplayProperties displayProperties;
	public static final char BOM_CHARACTER = '\uFEFF';

	@Override
	public String getPerspectiveId() {
		return null;
	}

	protected void addFormPage()  {
		IEditorInput dei = getEditorInput();
		displayViewer = new DisplayFormDesignViewer(this, getDisplayProperties());
		displayViewer.createPartControl(getContainer());
		addPage(displayViewer.getControl());        
		this.setActivePage(0);
		this.setForm(displayViewer.getForm());
		this.setInput(dei);
		updateTitle();
	}

	@Override
	protected void createPages() {
		addFormPage();
		setCatalogFunctionDrag(true);
		updateTab();
	}

	protected void updateTab() {
		if (getPageCount() == 1 && getContainer() instanceof CTabFolder) {
			((CTabFolder) getContainer()).setTabHeight(0);
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		DisplayProperties dProps = getDisplayProperties();
		Properties jProps = new Properties();
		jProps.put(DisplayUtils.DISPLAY_TEXT, dProps.getDisplayText());
		List<DisplayProperties> childProps = dProps.getDisplayProperties();
		for (DisplayProperties childProp : childProps) {
			String key = DisplayUtils.getDisplayKey(childProp);
			jProps.put(key, childProp.getDisplayText());
			if (childProp.isHidden()) {
				String hiddenKey = DisplayUtils.getHiddenKey(childProp);
				jProps.put(hiddenKey, "true");
			}
		}
		
		FileOutputStream fos = null;
		BufferedWriter bufWriter = null;
		try {
			fos = new FileOutputStream(getFile().getLocation().toFile());
			bufWriter = new BufferedWriter(new OutputStreamWriter(fos,
					ModelUtils.DEFAULT_ENCODING));
			bufWriter.write(BOM_CHARACTER);
			jProps.store(bufWriter, DisplayUtils.DEFAULT_COMMENT);
			setModified(false);
			firePropertyChange(IEditorPart.PROP_DIRTY);
			CommonUtil.refresh(getFile(), IResource.DEPTH_ZERO, false);
		} catch (IOException e) {
			EditorsUIPlugin.log("Unable to save Display Model", e);
		} finally {
			try {
				fos.close();
				bufWriter.close();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSaveAsAllowed() {
		return isModified();
	}

	private DisplayProperties getDisplayProperties() {
		if (this.displayProperties == null) {
			Properties props = loadProperties();
			displayProperties = new DisplayProperties(null);
			populateDisplayProperties(props, displayProperties);
		}
		return displayProperties;
	}

	private void populateDisplayProperties(Properties properties, DisplayProperties displayProperties) {
		String fullPath = getElementPath();
		displayProperties.setTargetName(fullPath);
		Set<Object> keySet = properties.keySet();
		for (Object obj : keySet) {
			String key = (String) obj;
			if (key.endsWith(DisplayUtils.HIDDEN)) {
				continue;
			}
			String value = properties.getProperty(key);
			if (key.equals(DisplayUtils.DISPLAY_TEXT)) {
				displayProperties.setDisplayText(value);
			}
			if (key.endsWith(DisplayUtils.DISPLAY_TEXT_SUFFIX)) {
				String targetName = key.substring(0, key.lastIndexOf('.'));
				PropertyDefinition propDef = getTargetProperty(targetName);
				DisplayProperties prop = new DisplayProperties(propDef);
				displayProperties.getDisplayProperties().add(prop);
				prop.setTargetName(targetName);
				prop.setDisplayText(value);
				String hidden = properties.getProperty(targetName+'.'+DisplayUtils.HIDDEN, "false");
				if ("true".equalsIgnoreCase(hidden)) {
					prop.setHidden(true);
				}
			}
		}
	}

	private PropertyDefinition getTargetProperty(String targetName) {
		Entity targetEntity = getTargetEntity();
		if (targetEntity instanceof Event) {
			return ((Event) targetEntity).getPropertyDefinition(targetName, false);
		} else if (targetEntity instanceof Concept) {
			return ((Concept) targetEntity).getPropertyDefinition(targetName, false);
		}
		return null;
	}

	private String getElementPath() {
		IEditorInput input = getEditorInput();
		if (input instanceof FileEditorInput) {
			IPath path = ((FileEditorInput) input).getFile().getProjectRelativePath();
			String fullPath = path.removeFileExtension().toString();
			Locale locale = DisplayUtils.getLocaleFromString(fullPath);
			String pathWithoutLocale = fullPath.replace("_" + locale.toString(), "");
			return "/"+pathWithoutLocale;
		}
		return null;
	}

	private Properties loadProperties() {
		IEditorInput input = getEditorInput();
		if (input instanceof FileEditorInput) {
			InputStream contents = null;
			Reader reader = null;
			try {
				contents = ((FileEditorInput) input).getStorage().getContents();
				 reader = new InputStreamReader(contents, ModelUtils.DEFAULT_ENCODING);
				Properties properties = new Properties();
				properties.load(reader);
				return properties;
			} catch (CoreException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					contents.close();
					reader.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
	}
	
	public Entity getTargetEntity() {
		String projName = getProject().getName();
		Entity entity = IndexUtils.getEntity(projName, displayProperties.getTargetName());
		return entity;
	}

	public IFile getFile() {
		IEditorInput input = getEditorInput();
		if (input instanceof FileEditorInput) {
			return ((FileEditorInput) input).getFile();
		}
		return null;
	}

}
