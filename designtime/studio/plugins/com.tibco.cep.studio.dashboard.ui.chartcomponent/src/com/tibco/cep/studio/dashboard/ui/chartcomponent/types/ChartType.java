package com.tibco.cep.studio.dashboard.ui.chartcomponent.types;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ChartType {

	private String id;
	
	private String family;

	private String name;

	private String description;

	private String iconName;

	private ChartSubTypeGroup[] subTypeGroups;

	private TypeProcessor processor;

	private String editorOptionsFormProvider;
	
	private String wizardOptionsFormProvider;

	private String editorDataFormProvider;
	
	private String wizardDataFormProvider;

	public ChartType(String id, String family, String name, String description, TypeProcessor processor, ChartSubTypeGroup[] subTypes) {
		this(id, family, name, description, id.toLowerCase() + ".gif", processor, subTypes);
	}

	public ChartType(String id, String family, String name, String description, String iconName, TypeProcessor processor, ChartSubTypeGroup[] subTypes) {
		super();
		this.id = id;
		this.family = family;
		this.name = name;
		this.description = description;
		this.iconName = iconName;
		this.processor = processor;
		this.subTypeGroups = subTypes;
	}

	public String getId() {
		return id;
	}
	
	public String getFamily() {
		return family;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getIconName() {
		return iconName;
	}

	public TypeProcessor getProcessor() {
		return processor;
	}
	
	public void setEditorOptionsFormProvider(String editorOptionsFormProvider) {
		this.editorOptionsFormProvider = editorOptionsFormProvider;
	}

	public String getEditorOptionsFormProvider() {
		return editorOptionsFormProvider;
	}
	
	public void setWizardOptionsFormProvider(String wizardOptionsFormProvider) {
		this.wizardOptionsFormProvider = wizardOptionsFormProvider;
	}
	
	public String getWizardOptionsFormProvider() {
		return wizardOptionsFormProvider;
	}
	
	public void setEditorDataFormProvider(String editorDataFormProvider) {
		this.editorDataFormProvider = editorDataFormProvider;
	}

	public String getEditorDataFormProvider() {
		return editorDataFormProvider;
	}
	
	public void setWizardDataFormProvider(String wizardDataFormProvider) {
		this.wizardDataFormProvider = wizardDataFormProvider;
	}
	
	public String getWizardDataFormProvider() {
		return wizardDataFormProvider;
	}

	public ChartSubTypeGroup[] getSubTypeGroups() {
		return subTypeGroups;
	}

	public ChartSubTypeGroup getSubTypeGroup(String id) {
		for (ChartSubTypeGroup subTypeGroup : subTypeGroups) {
			if (subTypeGroup.getId().equals(id) == true) {
				return subTypeGroup;
			}
		}
		return null;
	}

	public ChartSubType[] getSubTypes() {
		List<ChartSubType> subTypes = new LinkedList<ChartSubType>();
		for (ChartSubTypeGroup chartSubTypeGroup : subTypeGroups) {
			subTypes.addAll(Arrays.asList(chartSubTypeGroup.getSubTypes()));
		}
		return subTypes.toArray(new ChartSubType[subTypes.size()]);
	}

	public ChartSubType getSubType(String id) {
		if (ChartSubType.NONE.getId().equals(id) == true){
			return ChartSubType.NONE;
		}
		for (ChartSubTypeGroup chartSubTypeGroup : subTypeGroups) {
			ChartSubType subType = chartSubTypeGroup.getSubType(id);
			if (subType != null) {
				return subType;
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ChartType other = (ChartType) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("ChartType[id=");
		sb.append(id);
		sb.append("]");
		return sb.toString();
	}

}
