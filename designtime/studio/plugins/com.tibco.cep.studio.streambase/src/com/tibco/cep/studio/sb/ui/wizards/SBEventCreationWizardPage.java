package com.tibco.cep.studio.sb.ui.wizards;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.streambase.sb.DataType;
import com.streambase.sb.Schema.Field;
import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.EVENT_TYPE;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard;

public class SBEventCreationWizardPage extends EntityFileCreationWizard {

	private Field[] initialFields;
	private String defaultDestName;
	private String channelURI;

	public SBEventCreationWizardPage(String pageName,
			IStructuredSelection selection, String type) {
		super(pageName, selection, type);
		setFileExtension(IndexUtils.EVENT_EXTENSION);
	}

	@Override
	protected InputStream getInitialContents() {

		SimpleEvent event = EventFactory.eINSTANCE.createSimpleEvent();
		
		event.setName(new Path(getFileName()).removeFileExtension().toString());
		event.setDescription(getTypeDesc());
		event.setFolder(StudioResourceUtils.getFolder(getModelFile()));
		event.setNamespace(StudioResourceUtils.getFolder(getModelFile()));
		event.setGUID(GUIDGenerator.getGUID());
		event.setOwnerProjectName(project.getName());
		event.setSuperEventPath("");
		event.setTtl("0");
		event.setTtlUnits(TIMEOUT_UNITS.SECONDS);
		event.setType(EVENT_TYPE.SIMPLE_EVENT);
		
		if (this.defaultDestName != null) {
			event.setChannelURI(this.channelURI);
			event.setDestinationName(this.defaultDestName);
		}
		addCompilable(event, event.getOwnerProjectName(), EventPackage.eINSTANCE.getEvent_ExpiryAction().getName());
		addProperties(event, event.getOwnerProjectName());
		
		try {
			return IndexUtils.getEObjectInputStream(event);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void addProperties(SimpleEvent event, String ownerProjectName) {
		if (this.initialFields == null || this.initialFields.length == 0) {
			return;
		}
		for (Field field : initialFields) {
			PropertyDefinition propDef = ElementFactory.eINSTANCE.createPropertyDefinition();
			propDef.setName(field.getName());
			setPropertyType(propDef, field);
			propDef.setOwnerProjectName(ownerProjectName);
			propDef.setOwnerPath(event.getFullPath());
			event.getProperties().add(propDef);
		}
	}

	private void setPropertyType(PropertyDefinition propDef, Field field) {
//		if (field.getDataType() == DataType.LIST) {
//			propDef.setType(getPropertyType(field.getElementType().getDataType()));
//		} else {
			propDef.setType(getPropertyType(field.getDataType()));
//		}
	}

	private PROPERTY_TYPES getPropertyType(DataType type) {
		switch (type) {
		case STRING:
			return PROPERTY_TYPES.STRING;
		case DOUBLE:
			return PROPERTY_TYPES.DOUBLE;
		case INT:
			return PROPERTY_TYPES.INTEGER;
		case LONG:
			return PROPERTY_TYPES.LONG;
		case BOOL:
			return PROPERTY_TYPES.BOOLEAN;
		case TIMESTAMP:
			return PROPERTY_TYPES.DATE_TIME;
			
		default:
			break;
		}
		// all other types will be converted to a String
		return PROPERTY_TYPES.STRING;
	}

	private  void addCompilable(Event event, 
			String projectName, 
			String feature){
		Rule rule = RuleFactory.eINSTANCE.createRule();
		rule.setOwnerProjectName(projectName);
		rule.setName(event.getName() +"_"+ feature);
		Symbol symbol = RuleFactory.eINSTANCE.createSymbol();
		symbol.setType(event.getFullPath());
		symbol.setIdName(event.getName().toLowerCase());
		rule.getSymbols().getSymbolMap().put(symbol.getIdName(),symbol);
		event.setExpiryAction(rule);
	}

	public void setFields(Field[] selectedFields) {
		this.initialFields = selectedFields;
	}

	public void setDefaultDestination(String destinationName) {
		this.defaultDestName = destinationName;
	}

	public void setChannelURI(String channelURI) {
		this.channelURI = channelURI;
	}
}
