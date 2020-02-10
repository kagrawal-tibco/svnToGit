package com.tibco.cep.dashboard.tools;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.common.utils.XMLUtil;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.XMLBizRequestImpl;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.net.mime.Base64Codec;

public class AuthenticationTest extends StartUpShutDownTest {
	
	protected String token;

	protected BizRequest getXMLRequest(String command,Map<String,String> parameters) {
		StringBuilder sb = new StringBuilder();
		sb.append("<request>");
		sb.append("<parameter name=\"command\">"+command+"</parameter>");
		if (token != null) {
			sb.append("<parameter name=\"stoken\">" + token + "</parameter>");
		}
		if (parameters != null && parameters.isEmpty() == false) {
			for (String name : parameters.keySet()) {
				sb.append("<parameter name=\"" + name + "\">" + parameters.get(name) + "</parameter>");
			}
		}
		sb.append("</request>");
		return new XMLBizRequestImpl(sb.toString());
	}
	
	protected String getConfiguration() throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		BizRequest requestXML = getXMLRequest("getconfiguration",map);
		return client.execute(requestXML);
	}
	
	protected String[] loginUsingCommandArgs() throws IOException {
		if (arguments.size() < 2){
			throw new IllegalArgumentException("Insufficient arguments");
		}
		String username = arguments.pop();
		if (StringUtil.isEmptyOrBlank(username) == true){
			throw new IllegalArgumentException("Invalid username");
		}
		String password = arguments.pop();
		login(username, password);
		return new String[]{username,password};
	}

	protected void login(String username, String password) throws IOException {
		String encodedPassword = Base64Codec.encodeBase64(password.getBytes());
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", encodedPassword);
		BizRequest requestXML = getXMLRequest("signin",map);
		token = client.execute(requestXML);
	}

	protected String[] getRoles() throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("stoken", token);
		BizRequest requestXML = getXMLRequest("getroles",map);
		Node rolesXML = XMLUtil.parse(client.execute(requestXML));
		List<String> list = XMLUtil.getStringList(rolesXML, "role@name");
		return list.toArray(new String[list.size()]);
	}
	
	protected String setRoleUsingCommandArgs() throws Exception {
		String role = arguments.pop();
		if (StringUtil.isEmptyOrBlank(role) == true){
			throw new IllegalArgumentException("Invalid role");
		}
		setRole(role);
		return role;
	}
	
	protected void setRole(String preferredPrincipal) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("stoken", token);
		map.put("role", preferredPrincipal);
		BizRequest requestXML = getXMLRequest("setrole",map);
		client.execute(requestXML);
	}
	
	protected void validateRole(String preferredPrincipal) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("stoken", token);
		map.put("role", preferredPrincipal);
		BizRequest requestXML = getXMLRequest("validaterole",map);
		client.execute(requestXML);
	}
	
	protected String verify() throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("stoken", token);
		BizRequest requestXML = getXMLRequest("verify",map);
		return client.execute(requestXML);
	}	
	
	protected void logout() throws IOException{
		if (token != null) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("stoken", token);
			BizRequest requestXML = getXMLRequest("signout", map);
			client.execute(requestXML);
			token = null;
		}
	}
	
	public static void main(String[] args) {
		AuthenticationTest test = new AuthenticationTest();
		try {
			test.setup(args);
			test.start();
			test.logger.log(Level.INFO,test.getConfiguration());
			test.loginUsingCommandArgs();
			test.logger.log(Level.INFO,"Security Token Is "+test.token);
			String[] principals = test.getRoles();
			test.logger.log(Level.INFO,"Principals are "+Arrays.asList(principals));
			for (String principal : principals) {
				try {
					test.validateRole(principal);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					test.validateRole("fff");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			test.setRole(principals[0]);
			test.logger.log(Level.INFO,"Preferred Principal is "+test.verify());
			test.logout();
		} catch (IllegalArgumentException ex){
			System.err.println(ex.getMessage());
			System.err.println("Usage : java "+AuthenticationTest.class.getName()+" [<localfilename>]/[<remotepullrequesturl> <portnumber>] <username> <password>");
		} catch (Exception ex){
			ex.printStackTrace();
		} finally {
			test.shutdown();
		}
	}

}
