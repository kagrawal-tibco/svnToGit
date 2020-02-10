package com.tibco.cep.studio.common.configuration.update;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry;
import com.tibco.cep.studio.common.configuration.impl.StudioProjectConfigurationImpl;

public class StudioProjectConfigurationDelta extends
		StudioProjectConfigurationImpl implements IStudioProjectConfigurationDelta {

	private StudioProjectConfiguration fConfig;
	private int fType;

	public StudioProjectConfigurationDelta(StudioProjectConfiguration config, int type) {
		this.fConfig = config;
		this.fType = type;
	}

	@Override
	public StudioProjectConfiguration getAffectedChild() {
		return this.fConfig;
	}

	@Override
	public int getType() {
		return fType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (fType == ADDED) {
			builder.append("+");
		} else if (fType == CHANGED) {
			builder.append("*");
		} else if (fType == REMOVED) {
			builder.append("-");
		}
		builder.append("Project delta\n*");
		if(fConfig != null ){
			builder.append(fConfig.getName());
			EList<ProjectLibraryEntry> projectLibEntries = getProjectLibEntries();
			builder.append("\n\tProject Library changes:");
			for (ProjectLibraryEntry entry : projectLibEntries) {
				builder.append("\n\t\t("+getChangeType((IBuildPathEntryDelta)entry)+")"+((IBuildPathEntryDelta)entry).getAffectedChild().getPath());
			}
			EList<CustomFunctionLibEntry> customFnEntries = getCustomFunctionLibEntries();
			builder.append("\n\tCustom Function changes:");
			for (CustomFunctionLibEntry entry : customFnEntries) {
				builder.append("\n\t\t("+getChangeType((IBuildPathEntryDelta)entry)+")"+((IBuildPathEntryDelta)entry).getAffectedChild().getPath());
			}
			EList<ThirdPartyLibEntry> tpEntries = getThirdpartyLibEntries();
			builder.append("\n\tThird Party changes:");
			for (ThirdPartyLibEntry entry : tpEntries) {
				builder.append("\n\t\t("+getChangeType((IBuildPathEntryDelta)entry)+")"+((IBuildPathEntryDelta)entry).getAffectedChild().getPath());
			}
		}
		return builder.toString();
	}

	private String getChangeType(IBuildPathEntryDelta entry) {
		switch (entry.getType()) {
		case IBuildPathEntryDelta.ADDED:
			return "+";
			
		case IBuildPathEntryDelta.REMOVED:
			return "-";
			
		case IBuildPathEntryDelta.CHANGED:
			return "*";
			
		default:
			break;
		}
		return null;
	}

}
