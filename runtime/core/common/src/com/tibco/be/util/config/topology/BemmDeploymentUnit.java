package com.tibco.be.util.config.topology;

import com.tibco.be.util.XiSupport;
import com.tibco.be.util.config.ConfigNS;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import org.xml.sax.InputSource;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class BemmDeploymentUnit {

    private static final String DEPLOY_REL_PATH = File.separator+ "mm" + File.separator + "deployment" + File.separator;

    private enum COLUMN_NAME {DU_NAME, STATUS, LOCAL_FILE_PATH, INFO}

    private Logger logger;
    private String id;
    private String name;
    private String fqName;
    private String localCdd;               //TODO Can I do this better?
    private String localEar;               //TODO Can I do this better?
    private String targetCdd;
    private String targetEar;
    private String savedCddFqPath;
    private File duDeployDirFqPath;
    private HashMap<String,BemmPU> puStIdToPu;          //PuStId is the id attribute of the PU element in the st file. Do not confuse with puid

    private LinkedHashMap<String, GV> gvNameToGV;       //key: gvName. This map contains all the GVs. The values are first read from the EAR,
                                                        //then updated with the values that were previously
                                                        //changed in the UI and saved to the CDD stored locally for deployment, and
                                                        //lastly with the values set by the user in his last UI GV changes.
    private LinkedHashMap<String, GV> savedGvNameToGV;  //key: gvName. This map contains ONLY the GVs whose values were previously
                                                        //changed in the UI and saved to the CDD stored locally for deployment, plus
                                                        //the values set by the user in the current UI GV changes. It does not include the values
                                                        //read from the EAR
    private MMGlobalVarsUtil gvUtil;
    private XiNode masterCddXml;
    private String[] operStatusColsVals;


    public BemmDeploymentUnit(XiNode duXiNode,
            			String masterCdd,
            			String masterEar,
            			Logger logger)  throws Exception {
        this.localCdd = masterCdd;
        this.localEar = masterEar;
        this.logger = logger;
        this.id = duXiNode.getAttributeStringValue(TopologyNS.Attributes.ID).trim();
        this.name = duXiNode.getAttributeStringValue(TopologyNS.Attributes.NAME).trim();
        this.puStIdToPu = new HashMap<String,BemmPU>();

        parseAndCreateObjs(duXiNode);
	}

    private void parseAndCreateObjs(XiNode duXiNode) throws Exception {
        XiNode childNode = XiChild.getChild(duXiNode, TopologyNS.Elements.DEPLOY_FILES);
        final XiNode cddNode = XiChild.getChild(childNode, TopologyNS.Elements.CDD_DEPLOYED);
        final XiNode earNode = XiChild.getChild(childNode, TopologyNS.Elements.EAR_DEPLOYED);

        targetCdd = cddNode != null ? cddNode.getStringValue().trim() : null;
        targetEar = earNode != null ? earNode.getStringValue().trim() : null;

		childNode = XiChild.getChild(duXiNode, TopologyNS.Elements.PROCESS_UNITS);
        if (childNode != null) {
            for (Iterator i = XiChild.getIterator(childNode, TopologyNS.Elements.PROCESS_UNIT); i.hasNext(); ) {
                final XiNode puNode = (XiNode) i.next();
                final String stId = puNode.getAttributeStringValue(TopologyNS.Attributes.ID).trim();
                if( stId != null && !stId.isEmpty() )
                	puStIdToPu.put(stId, new BemmPU(localCdd, puNode, name, logger));
                else
                	logger.log(Level.ERROR, "Found processing unit without name specified for deployment unit with ID [" + this.id + "]. PU ignored.");
            }
        } else {// should not get here
            logger.log(Level.ERROR, "No processing unit configured for deployment unit with ID [" + id + "]");
        }
	}


    public XiNode readGVs() {
        return readGVs(true);
    }

    public XiNode readGVs(boolean isReadGVs) {
        initGVs();
        gvUtil.putGVsFromEARIntoMap();       // Calling this every time will allow the user to see the
                                             // changes without having to restart the server
        if (savedCddFqPath == null )
            setDuDeployPath();

        gvUtil.putGVsFromCddFileIntoMap(savedCddFqPath);          //It's called after putGVsFromEar, such that GVs values

        //To speed things up a little bit during the Save GVs operation
        if (isReadGVs)
            return createGVXml();
        else
            return null;
    }

    private void initGVs() {
        if (gvNameToGV == null || savedGvNameToGV == null || gvUtil == null) {
            gvNameToGV = new LinkedHashMap<String, GV>();
            savedGvNameToGV = new LinkedHashMap<String, GV>();
            gvUtil = new MMGlobalVarsUtil(logger, localEar, gvNameToGV, savedGvNameToGV);
        }
    }

    private XiNode createGVXml() {
        XiNode gvsElem = XiSupport.getXiFactory().createElement(MMGlobalVarsNS.Elements.GVS);
        gvsElem.setAttributeStringValue(MMGlobalVarsNS.Attributes.DUN, name);

        for (BemmDeploymentUnit.GV gv : gvNameToGV.values()) {
            XiNode gvElem = XiSupport.getXiFactory().createElement(MMGlobalVarsNS.Elements.GV);
            gvElem.setAttributeStringValue(MMGlobalVarsNS.Attributes.NAME, gv.getName());
            gvElem.setAttributeStringValue(MMGlobalVarsNS.Attributes.DEF_VALUE, gv.getDv());
            gvElem.setAttributeStringValue(MMGlobalVarsNS.Attributes.CURR_VALUE, gv.getCv());

            gvsElem.appendChild(gvElem);
        }
        return gvsElem;
    }

    /*
    * Updates the values of the global variables for this DU in memory, and attempts to save the values to a CDD file.
    * The values returned in the array are to be used as the values of the columns
    * comprising the table with the Save operation status.
    * @return An array with the status of this operation.
    * */
    public String[] saveGVs(XiNode gvsElem) {
        logger.log(Level.INFO, "Saving Global Variables for deployment unit  %s.", fqName);

        //To guarantee that the maps are loaded with the latest values of GVs (either from EAR or from CDD file)
        readGVs(false);

        gvUtil.updateGVsMaps(gvsElem);  //updates value of GVs in memory
        logger.log(Level.DEBUG, "Global Variables put in memory for deployment unit %s", fqName);
        locallySaveTheCddFileToDeploy();     //If everything goes well saves the value of the new GVs to a file

        return operStatusColsVals;      //returns the status of the operation
    }

    /** Saves a local copy of the CDD file that is to be deployed */
    private void locallySaveTheCddFileToDeploy() {
        operStatusColsVals =new String[] {"","","",""};  //resets the outcome of the previous save operation
        operStatusColsVals[COLUMN_NAME.DU_NAME.ordinal()] = name;

        if (hasCreatedSaveDestDir()) {
            if (hasCreatedMasterCddXmlObj()) {
                if(hasAddedGVsToMasterCddXmlObj()) {
                    if(hasSavedCddFile()) {
                        operStatusColsVals[COLUMN_NAME.STATUS.ordinal()] = MMIoNS.OperationStatus.SaveGvs.SAVED;
                        operStatusColsVals[COLUMN_NAME.LOCAL_FILE_PATH.ordinal()] = savedCddFqPath;
                        logger.log(Level.INFO, "Global Variables saved to local file %s, for deployment unit %s", savedCddFqPath, fqName);
                        return;
                    }
                }
            }
        }
      logger.log(Level.ERROR, "Global Variables NOT saved to local file %s, for deployment unit %s", savedCddFqPath, fqName);
    }

    private boolean hasSavedCddFile() {
        FileWriter fw = null;
        try {
            logger.log(Level.DEBUG, "Attempting to save Global Variables to local file %s, for deployment unit %s", savedCddFqPath, fqName);
            fw = new FileWriter(savedCddFqPath);
            fw.write(XiSerializer.serialize(masterCddXml));
            fw.close();
            return true;
        } catch (IOException e) {
            final String msg = "Error occurred when trying to save local copy of CDD file: " + savedCddFqPath;
            logger.log(Level.ERROR, e, msg);
            operStatusColsVals[COLUMN_NAME.STATUS.ordinal()] = MMIoNS.OperationStatus.SaveGvs.NOT_SAVED;
            operStatusColsVals[COLUMN_NAME.INFO.ordinal()]+= msg;
            return false;
        }
    }

    /** Creates directory
     * @return true if directory successfully created, false otherwise */
    private boolean hasCreatedSaveDestDir() {
        if(duDeployDirFqPath==null) {
            setDuDeployPath();
        }

        if (duDeployDirFqPath.exists()) {
            return true;
        }

        if (duDeployDirFqPath.mkdirs()) {
            logger.log(Level.INFO, "Created directory %s to save CDD file with new global " +
                    "variables values, for deployment unit %s", duDeployDirFqPath.getAbsolutePath(), fqName);
            return true;
        }  else {
            final String msg = String.format("FAILED to create directory %s to save CDD file with new global " +
                             "variables values, for deployment unit %s", duDeployDirFqPath.getAbsolutePath(), fqName);

            logger.log(Level.ERROR, msg);
            operStatusColsVals[COLUMN_NAME.STATUS.ordinal()] = MMIoNS.OperationStatus.SaveGvs.NOT_SAVED;
            operStatusColsVals[COLUMN_NAME.INFO.ordinal()]+= msg;
            duDeployDirFqPath = null;
            return false;
        }
    }

    private void setDuDeployPath() {
        final String[] fqNameSplit = fqName.startsWith("/") ? fqName.substring(1).split("/") : fqName.split("/");

        //replace ' ' by '.' to make it a valid dir name
        final String siteName = fqNameSplit[0].replaceAll(" ",".");
        final String clusterName = fqNameSplit[1].replaceAll(" ",".");

        duDeployDirFqPath = new File(
                System.getProperty(SystemProperty.BE_HOME.getPropertyName()) +      //BE_HOME
                        DEPLOY_REL_PATH +                                           //DEPLOY_REL_PATH
                        siteName + File.separator +                                 //Site name
                        clusterName + File.separator + name);                       //Cluster name / DU name

        savedCddFqPath = duDeployDirFqPath.getAbsolutePath() +
                                        File.separator + getCddFileName();
    }

    private boolean hasCreatedMasterCddXmlObj() {
        InputSource is=null;
        try {
            FileReader fr = new FileReader(localCdd);
            is = new InputSource(fr);
        } catch (FileNotFoundException e) {
            final String msg = String.format("Could not create input source for Master Cdd file " +
                      "because file not found: %s.",localCdd);
            logger.log(Level.ERROR, e , msg);
            operStatusColsVals[COLUMN_NAME.STATUS.ordinal()] = MMIoNS.OperationStatus.SaveGvs.NOT_SAVED;
            operStatusColsVals[COLUMN_NAME.INFO.ordinal()]+= msg;
            return false;
        }

        try {
            masterCddXml = XiSupport.getParser().parse(is);
            return true;
        } catch (Exception e) {
            final String msg = String.format("Exception occurred while parsing Master Cdd file: %s.", localCdd);
            logger.log(Level.ERROR, e, msg);
            operStatusColsVals[COLUMN_NAME.STATUS.ordinal()] = MMIoNS.OperationStatus.SaveGvs.NOT_SAVED;
            operStatusColsVals[COLUMN_NAME.INFO.ordinal()]+= msg;
            return false;
        }
    }

    private boolean hasAddedGVsToMasterCddXmlObj() {
        try {
            XiNode clusterNode = XiChild.getChild(masterCddXml, ConfigNS.Elements.CLUSTER);
            XiNode lastPropGrpNode = XiChild.getChild(clusterNode, ConfigNS.Elements.PROPERTY_GROUP);
            XiNode mmPropGrpNode = null;
            Iterator iterator = XiChild.getIterator(lastPropGrpNode, ConfigNS.Elements.PROPERTY_GROUP);
            while (iterator.hasNext()) {
				XiNode propGrpNode = (XiNode) iterator.next();
				if ("global.variables.set.with.mm".equals(propGrpNode.getAttributeStringValue(ConfigNS.Attributes.NAME))){
					mmPropGrpNode = propGrpNode;
					//wipe all existing values 
					Iterator children = mmPropGrpNode.getChildren();
					while (children.hasNext()) {
						mmPropGrpNode.removeChild((XiNode) children.next());
					}
					break;
				}
			}
            if (mmPropGrpNode == null){
            	mmPropGrpNode = XiSupport.getXiFactory().createElement(ConfigNS.Elements.PROPERTY_GROUP);
            	mmPropGrpNode.setAttributeStringValue(ConfigNS.Attributes.COMMENT, "GVs updated from the MM UI and used for deployment");
            	mmPropGrpNode.setAttributeStringValue(ConfigNS.Attributes.NAME, "global.variables.set.with.mm");
            	lastPropGrpNode.appendChild(mmPropGrpNode);      //Add prop group with updated GVs as child of CDD's last prop group
            }
            for (String gvName : savedGvNameToGV.keySet()) {
                XiNode prop = XiSupport.getXiFactory().createElement(ConfigNS.Elements.PROPERTY);
                //Added by Anand on 07/11/14 to fix Be-20854
                String prefixedGVName = gvName;
                if (prefixedGVName.startsWith("tibco.clientVar.") == false) {
                	prefixedGVName = "tibco.clientVar."+gvName;
                }
                prop.setAttributeStringValue(MMGlobalVarsNS.Attributes.NAME, prefixedGVName);
                prop.setAttributeStringValue(MMGlobalVarsNS.Attributes.VALUE, savedGvNameToGV.get(gvName).getCv());
                mmPropGrpNode.appendChild(prop);
            }
            return true;
        } catch (Exception e) {
            final String msg = "Exception occurred when attempting to put new global variables values in memory";
            logger.log(Level.ERROR, msg);
            logger.log(Level.DEBUG, "Check the GVs XML build step");
            operStatusColsVals[COLUMN_NAME.STATUS.ordinal()] = MMIoNS.OperationStatus.SaveGvs.NOT_SAVED;
            operStatusColsVals[COLUMN_NAME.INFO.ordinal()]+= msg;
            return false;
        }
    }

    //******************************* Getter methods ********************************

    public String getCddWithSavedGVs() {
        if (savedCddFqPath == null)
            return null;

        RandomAccessFile raf = null;
        byte[] b = null;
        try {
            final File file = new File(savedCddFqPath);
            raf = new RandomAccessFile(file,"r");
            b = new byte[(int)file.length()];
            raf.readFully(b);
            return new String(b);
        } catch (EOFException e){
            logger.log(Level.ERROR, e, "CDD file saved to MM server's deployment location: %s is too big to be read.", savedCddFqPath);
            return null;
        }catch (FileNotFoundException e) {
            logger.log(Level.WARN, "No CDD file saved to MM server's deployment location: %s. Deploying CDD file from master location: %s",savedCddFqPath, localCdd);
            return null;
        } catch (IOException e) {
            logger.log(Level.ERROR, e, "Error reading CDD file saved to MM server's deployment location: %s", savedCddFqPath);
            return null;
        }finally {
            try {
                if (raf != null)
                    raf.close();
            } catch (IOException e) {
                logger.log(Level.WARN, e, "Error closing CDD file saved to MM server's deployment location: %s after a successful read", savedCddFqPath);
            }
        }
    }

    public String getId() {
		return id;
	}

    /**
     * Gets the BemmPU object with the specified ID for this DU
     * @param puStId the id attribute of the PU element in the st file. Do not confuse with puid
     * @return The BemmPU associated with the specified StId
     * */
	public BemmPU getPU(String puStId){
		return (puStId == null || puStId.trim().isEmpty()) ? null : puStIdToPu.get(puStId);
	}

	public String getTargetCddFqPath() {
		return targetCdd;
	}

	public String getTargetEarFqPath() {
		return targetEar;
	}

    public String getTargetCddDir() {
        return getDir(targetCdd);
    }

    public String getTargetEarDir() {
        return getDir(targetEar);
    }

    public String getCddFileName() {
        return getFileName(targetCdd);
    }

    public String getEarFileName() {
        return getFileName(targetEar);
    }

    public String getDir(String fileFqPath) {
		int idx = fileFqPath.lastIndexOf('/');
		int idx1 = fileFqPath.lastIndexOf('\\');

		if(idx == -1 && idx1 == -1)
            return null;

        if(idx != -1){
			return fileFqPath.substring(0, idx);
		}

		return fileFqPath.substring(0, idx1);
	}

    private String getFileName(String fileFqPath) {
        int idx = fileFqPath.lastIndexOf('/');
		int idx1 = fileFqPath.lastIndexOf('\\');

		if(idx == -1 && idx1 == -1)
            return null;

        if(idx != -1){
			return fileFqPath.substring(idx+1, fileFqPath.length());
		}

		return fileFqPath.substring(idx1+1, fileFqPath.length());
    }

    public String getName() {
        return name;
    }

    public void setFqName(String hostFqName) {
        this.fqName = hostFqName + '/' + name;
    }

    public String getFqName() {
        return fqName;
    }

    public Object[] getMappedPus() {
        return puStIdToPu.values().toArray();
    }

    public  static class GV {
        private String name;
        private String cv;                //current value
        private String dv;                //default value

        public GV(String name, String cv, String dv) {
            this.name = name;
            this.cv = cv;
            this.dv = dv;
        }

        public String getName() {
            return name;
        }

        public String getCv() {
            return cv;
        }

        public String getDv() {
            return dv;
        }
    }
}

