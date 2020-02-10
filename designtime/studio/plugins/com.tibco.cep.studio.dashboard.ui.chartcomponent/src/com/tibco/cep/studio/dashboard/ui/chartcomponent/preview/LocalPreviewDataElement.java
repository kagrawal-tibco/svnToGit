package com.tibco.cep.studio.dashboard.ui.chartcomponent.preview;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynOptionalProperty;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynRequiredProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynStringType;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class LocalPreviewDataElement extends LocalElement {

	public enum ENABLE { YES, NO, NEVER }

	public static final String PROP_KEY_ENABLE = PROP_KEY_PREFIX + "Enabled";

	public static final String PROP_KEY_MESSAGE = PROP_KEY_PREFIX + "Message";

	public static final String PROP_KEY_COMPLETE_XML = PROP_KEY_PREFIX + "CompleteXML";

	public LocalPreviewDataElement(LocalElement parent, ENABLE enableLevel, String message) {
		super(parent);
		setPropertyValueWithNoException(PROP_KEY_ENABLE, enableLevel.toString());
		setPropertyValueWithNoException(PROP_KEY_MESSAGE, message);
	}

	public LocalPreviewDataElement(LocalElement parent, String completeXML) {
		super(parent);
		setPropertyValueWithNoException(PROP_KEY_COMPLETE_XML, completeXML);
	}

	private LocalPreviewDataElement(){

	}

	@Override
	public void setupProperties() {
		addProperty(this, new SynRequiredProperty(PROP_KEY_ENABLE, new SynStringType(), ENABLE.YES.toString()));
		addProperty(this, new SynOptionalProperty(PROP_KEY_MESSAGE, new SynStringType(), null));
		addProperty(this, new SynOptionalProperty(PROP_KEY_COMPLETE_XML, new SynStringType(), null));
	}

	@Override
	public Object cloneThis() {
		return new LocalPreviewDataElement();
	}

	@Override
	public LocalElement createLocalElement(String elementType) {
		throw new UnsupportedOperationException("createLocalElement");
	}

	@Override
	public EObject createMDChild(LocalElement localElement) {
		throw new UnsupportedOperationException("createMDChild");
	}

	@Override
	public void deleteMDChild(LocalElement localElement) {
		throw new UnsupportedOperationException("deleteMDChild");
	}

	@Override
	protected String getEObjectDescription(EObject object) {
		return "";
	}

	@Override
	protected String getEObjectFolder(EObject object) {
		return "";
	}

	@Override
	protected String getEObjectId(EObject object) {
		return "";
	}

	@Override
	protected String getEObjectName(EObject object) {
		return "";
	}

	@Override
	public String getElementType() {
		return "LocalPreviewData";
	}

	@Override
	public void loadChild(String childrenType, String childName) {
	}

	@Override
	public void loadChildByID(String childrenType, String childID) {
	}

	@Override
	public void loadChildren(String childrenType) {
	}

	@Override
	protected void setEObjectDescription(EObject object, String description) {
	}

	@Override
	protected void setEObjectFolder(EObject object, String folder) {
	}

	@Override
	protected void setEObjectId(EObject object, String id) {
	}

	@Override
	protected void setEObjectName(EObject object, String name) {
	}

	public ENABLE getEnablementLevel(){
		String enable = getPropertyValueWithNoException(PROP_KEY_ENABLE);
		if (enable == null) {
			return ENABLE.NO;
		}
		return ENABLE.valueOf(enable);
	}

	public String getMessage(){
		return getPropertyValueWithNoException(PROP_KEY_MESSAGE);
	}

	public String getCompleteXML(){
		return getPropertyValueWithNoException(PROP_KEY_COMPLETE_XML);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("LocalPreviewDataElement[");
		Iterator<String> iterator = getPropertyNames().iterator();
		while (iterator.hasNext()) {
			String propertyName = (String) iterator.next();
			sb.append(propertyName);
			sb.append("=");
			sb.append(getPropertyValueWithNoException(propertyName));
			if (iterator.hasNext() == true) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}