package com.tibco.tea.agent.be.migration.file.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;


import java.util.HashSet;
import java.util.List;

import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.tea.agent.be.migration.file.generator.roles.config.AccessControlList;
import com.tibco.tea.agent.be.migration.file.generator.roles.config.Role;
import com.tibco.tea.agent.be.migration.file.generator.roles.config.User;
import com.tibco.tea.agent.be.migration.file.generator.roles.config.UserManagement;

/**
 * @author ssinghal
 *
 */

public class GenerateUserAndRolesData {
	
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GenerateUserAndRolesData.class);	
	
	public static String[][] generate(String dataStorepath, Properties configuration) throws Exception{
		
		String exportedFilePath = dataStorepath;
		
		try{
			JAXBContext jc = JAXBContext.newInstance(UserManagement.class);		
	        Unmarshaller unmarshaller = jc.createUnmarshaller();  
	        UserManagement userManagement = (UserManagement) unmarshaller.unmarshal(new File(exportedFilePath));
	        
	        
	        String[] users = new String[userManagement.getUser().size()];
	        String[] roles = new String[userManagement.getRole().size()];
	        
	        List<User> usersList = userManagement.getUser();
	        for(int i=0; i<usersList.size(); i++){
	        	users[i] = new String(usersList.get(i).getName() + ":" + usersList.get(i).getPassword());
	        }
	        
	        List<Role> rolesList = userManagement.getRole();
	        for(int i=0; i<rolesList.size(); i++){
	        	Role role = rolesList.get(i);
	        	StringBuffer roleString = new StringBuffer();
	        	roleString.append(role.getName());roleString.append(":");
	        	List<AccessControlList> accessUsers = role.getAccessControlList();
	        	Set<String> accessUsersSet = null;
	        	for(int j=0; j<accessUsers.size(); j++){
	        		AccessControlList access = accessUsers.get(j);
	        		accessUsersSet = new HashSet<String>(); 
	        		accessUsersSet.add(access.getUser());
	        	}
	        	if(accessUsersSet!=null && accessUsersSet.size()!=0)
	        	for(String s : accessUsersSet){
	        		roleString.append(s+",");
	        	}
	        	
	        	roles[i] = new String(roleString);
	        }
			return new String[][]{users, roles};
	        
		}catch(UnmarshalException e){
			LOGGER.log(Level.ERROR, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.READEXPORTEDFILE_ERROR) + e.getMessage());
			if(e.getLinkedException().getClass().equals(FileNotFoundException.class)){
				throw new Exception(e.getLinkedException().getMessage(), e);
			}
			e.printStackTrace();
			throw e;
		}
		catch(Exception e){
			LOGGER.log(Level.ERROR, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.READEXPORTEDFILE_ERROR) + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
}
