package com.tibco.cep.studio.dashboard.ui.chartcomponent.types;

public class ChartSubType {
	
	public static final ChartSubType NONE = new ChartSubType("none","None","PlaceHolder for indicating no sub type");
	
	private String id;
	
	private String name;
	
	private String description;
	
	private String iconName;
	
	private String selectedIconName;
	
	public ChartSubType(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.iconName = id.toLowerCase()+"_off.gif";
		this.selectedIconName = id.toLowerCase()+"_on.gif";
	}

	public ChartSubType(String id, String name, String description, String imageURL, String selectedImageURL) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.iconName = imageURL;
		this.selectedIconName = selectedImageURL;
	}

	public String getId() {
		return id;
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

	public String getSelectedIconName() {
		return selectedIconName;
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
		ChartSubType other = (ChartSubType) obj;
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
		StringBuilder sb = new StringBuilder("ChartSubType[id=");
		sb.append(id);
		sb.append("]");
		return sb.toString();
	}

}
