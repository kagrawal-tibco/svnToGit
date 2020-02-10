/**
 * 
 */
package com.tibco.be.util.config.topology;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.util.ProcessInfo;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Nick
 *
 */
public class BemmHostResource {
	private String id;
	private String hostFqName;
	private String ipAddress;
	private String username;
	private String password;
	private String osType;
	private HashMap<String, BemmBEHome> installedBEVersionToBE;
	private BemmStartPUMethod startPuMethod;
    private Logger logger;


    public BemmHostResource(XiNode hrXiNode, Logger logger) throws UnknownHostException {
        this.id = hrXiNode.getAttributeStringValue(TopologyNS.Attributes.ID).trim();
        this.logger = logger;
		this.installedBEVersionToBE = new HashMap<String, BemmBEHome>();
		parseAndCreateObjs(hrXiNode);
	}
	
	private void parseAndCreateObjs(XiNode hrXiNode) throws UnknownHostException {
		XiNode childNode = XiChild.getChild(hrXiNode, TopologyNS.Elements.HOSTNAME);
		hostFqName = childNode.getStringValue().trim();

		childNode = XiChild.getChild(hrXiNode, TopologyNS.Elements.IP);
		ipAddress = childNode.getStringValue().trim();

        if (ipAddress.equalsIgnoreCase("localhost"))
            ipAddress = InetAddress.getLocalHost().getHostAddress();


        //Build the fully qualified host name, even if the user only specifies the host's simple name.
        hostFqName = ProcessInfo.ensureFQDN(hostFqName, ipAddress);

        childNode = XiChild.getChild(hrXiNode, TopologyNS.Elements.CREDENTIALS);
		username = childNode.getAttributeStringValue(TopologyNS.Attributes.USERNAME).trim();

        final String encodedPwd = childNode.getAttributeStringValue(TopologyNS.Attributes.PWD).trim();
		password = getDecodedPwd(encodedPwd);
		
		childNode = XiChild.getChild(hrXiNode, TopologyNS.Elements.OS_TYPE);
		osType = childNode.getStringValue().trim();
		
		childNode = XiChild.getChild(hrXiNode, TopologyNS.Elements.SOFTWARE);
        for (Iterator i = XiChild.getIterator(childNode, TopologyNS.Elements.BE); i.hasNext();) {
            XiNode beNode = (XiNode) i.next();
            final String version = beNode.getAttributeStringValue(TopologyNS.Attributes.VERSION).trim();
            final String traPath = XiChild.getChild(beNode, TopologyNS.Elements.TRA).getStringValue();
            final String beHome = XiChild.getChild(beNode, TopologyNS.Elements.HOME).getStringValue();
            installedBEVersionToBE.put(version, new BemmBEHome(version, traPath, beHome));
        }
        
        childNode = XiChild.getChild(hrXiNode, TopologyNS.Elements.START_PU_METHOD);
        final XiNode methodNode = childNode.getFirstChild();
        if(methodNode != null){
            final String startPuMethod = methodNode.getName().localName.trim().toLowerCase();
            if(startPuMethod.equals("ssh")){
                final String port = methodNode.getAttributeStringValue(TopologyNS.Attributes.PORT);
                this.startPuMethod = new BemmSshConfig(startPuMethod,port);
            }
            else if(startPuMethod.equals("pstools")){
                this.startPuMethod = new BemmPstoolsConfig(startPuMethod);
            }
            else if(startPuMethod.equals("hawk")){
                this.startPuMethod = new BemmHawkConfig(startPuMethod);
            }
        }
	}

	public String getId(){
		return id;
	}

	public String getHostFqName() {
		return hostFqName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getOsType() {
		return osType;
	}

	public BemmBEHome getBEHome(String version){
		return installedBEVersionToBE.get(version);
	}

	public void setUsername(String username) {
		this.username = username.trim();
	}

	public void setPassword(String password) {
		this.password = password.trim();
	}

	public BemmStartPUMethod getStartPuMethod() {
		return startPuMethod;
	}
	
    private String getDecodedPwd(String encodedPwd) {
        if (encodedPwd == null || encodedPwd.trim().isEmpty())
              return "";

        String pwd = encodedPwd;
        try {
              if (ObfuscationEngine.hasEncryptionPrefix(encodedPwd)) {
            	  pwd = new String(ObfuscationEngine.decrypt(encodedPwd));
              }
        } catch (AXSecurityException e) {
              logger.log(Level.ERROR, "Exception occurred while decoding password for host: %s", hostFqName);
        }
        return pwd;
	}
	
//	public static void main(String[] args) {
//		String pwd = "#!9/80FqePDpCbuIexg0jPpW2buBsml+tj";
//		String temp;
//        try {
//            if (ObfuscationEngine.hasEncryptionPrefix(pwd)) {
//                  pwd = new String(ObfuscationEngine.decrypt(pwd));
//            }
//            else{
//	            temp = ObfuscationEngine.encrypt(pwd.toCharArray());
//	            pwd = temp;
//            }
//            System.out.print(pwd);
//        } catch (AXSecurityException e) {
//            e.printStackTrace();
//        }
//
//	}
}
