/**
 * 
 */
package com.tibco.cep.security.authen.utils;

/**
 * @author aathalye
 *
 */
public final class LoginUtils {
	
	/*public static List<Role> getUserRoles(final List<Role> allRoles,
			                              final User user) {
		List<Role> userRoles = new ArrayList<Role>();
		if (user == null) {
			return null;
		}
		for (Role role : allRoles) {
			if (role.isMember(user)) {
				userRoles.add(role);
			}
		}
		return userRoles;
	}*/
	
	/**
	 * Search the collection of <code>Role</code>s for 
	 * the username passed in it. If username is found,
	 * immediately return it instead of checking all roles.
	 * @param allRoles
	 * @param user
	 * @return <code>User</code> entry matching the username
	 * @see User
	 */
	/*public static User searchUser(final List<Role> allRoles,
			                      final User user) {
		User searchedUser = null;
		for (Role role : allRoles) {
			//Get its members
			List<User> members = role.getUsers();
			for (User member : members) {
				if (member.equals(user)) {
					searchedUser = member;
				}
			}
		}
		return searchedUser;
	}*/
}
