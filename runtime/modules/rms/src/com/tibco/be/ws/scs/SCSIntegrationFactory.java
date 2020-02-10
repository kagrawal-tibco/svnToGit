package com.tibco.be.ws.scs;

import com.tibco.be.ws.scs.impl.file.FileSystemIntegration;
import com.tibco.be.ws.scs.impl.repo.svn.SVNIntegration;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 22/11/11
 * Time: 4:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class SCSIntegrationFactory {

    public static SCSIntegrationFactory INSTANCE = new SCSIntegrationFactory();
    
    private ISCSIntegration scsIntegrationImpl = null;

    private SCSIntegrationFactory() {}

    /**
     * Fetch the class based on the scs type set
     *  
     * @param scsType
     * @return
     * 
     * @throws SCSException
     */
    public ISCSIntegration getSCSIntegrationClass(String scsType) throws SCSException {
    	if (scsIntegrationImpl != null) return scsIntegrationImpl;
    	
    	if (scsType == null) {
    		scsIntegrationImpl = new FileSystemIntegration();
    	} else {
    		try {
    			SCSType currentSCSType = SCSType.getValue(scsType);

    			switch (currentSCSType) {
    			case FILE : scsIntegrationImpl = new FileSystemIntegration(); break; 
    			case SVN : scsIntegrationImpl = new SVNIntegration(); break;
    			default :
    				Class<?> clazz = Class.forName(scsType);
    				if (!ISCSIntegration.class.isAssignableFrom(clazz)) {
    					throw new SCSException(String.format("Classname[%s] does not implement interface com.tibco.be.ws.scs.ISCSIntegration.", scsType));
    				}
    				scsIntegrationImpl = (ISCSIntegration)clazz.newInstance();
    			}

    		} catch (Exception e) {
    			throw new SCSException(e);
    		}
    	}
        
        return scsIntegrationImpl;
    }
}
