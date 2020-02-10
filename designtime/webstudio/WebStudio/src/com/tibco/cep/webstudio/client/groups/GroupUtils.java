package com.tibco.cep.webstudio.client.groups;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.webstudio.client.WebStudio;

public class GroupUtils {
	
	public static Group getProjectGroup() {
		List<Group> groups = WebStudio.get().getWorkspacePage().getMyGroups().getGroups();
		for (Group group : groups) {
			if (group.isSystem() && group.getGroupID().equals("group_Projects")) {
				return group;
			}
		}
		return null;
	}
	
	public static List<Group> getSystemGroups() {
		List<Group> systemGroups = new ArrayList<Group>();
		List<Group> groups = WebStudio.get().getWorkspacePage().getMyGroups().getGroups();
		for (Group group : groups) {
			if (group.isSystem()) {
				systemGroups.add(group);
			}
		}
		return systemGroups;
	}
	
	public static List<Group> getCustomGroups() {
		List<Group> systemGroups = new ArrayList<Group>();
		List<Group> groups = WebStudio.get().getWorkspacePage().getMyGroups().getGroups();
		for (Group group : groups) {
			if (!group.isSystem()) {
				systemGroups.add(group);
			}
		}
		return systemGroups;
	}
	
	public static List<Group> getAllGroups() {
		return WebStudio.get().getWorkspacePage().getMyGroups().getGroups();
	}

}
