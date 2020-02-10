package com.tibco.be.util.config.topology;

import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.zip.ZipInputStream;

import org.xml.sax.InputSource;

import com.tibco.be.util.XiSupport;
import com.tibco.be.util.config.ConfigNS;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.VFileHelper;
import com.tibco.objectrepo.vfile.VFile;
import com.tibco.objectrepo.vfile.VFileDirectory;
import com.tibco.objectrepo.vfile.VFileFactory;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.objectrepo.vfile.zipfile.ZipVFileFactory;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 9/14/11
 * Time: 9:39 PM
 * To change this template use File | Settings | File Templates.
 */
class MMGlobalVarsUtil {
    private static final XiParser PARSER = XiParserFactory.newInstance();

    private Logger logger;
    private String duLocalEarPath;
    private XiNode savedCddXml;

    //For comments for these two fields look at the same fields in class com.tibco.be.util.config.topology.BemmDeploymentUnit
    private LinkedHashMap<String, BemmDeploymentUnit.GV> gvNameToGV;
    private LinkedHashMap<String, BemmDeploymentUnit.GV> savedGvNameToGV;

    MMGlobalVarsUtil(Logger logger, String earPath,
                     LinkedHashMap<String, BemmDeploymentUnit.GV> gvNameToGV,
                     LinkedHashMap<String, BemmDeploymentUnit.GV> savedGvNameToGV) {
        this.logger = logger;
        this.duLocalEarPath = earPath;
        this.gvNameToGV = gvNameToGV;
        this.savedGvNameToGV = savedGvNameToGV;
    }

     void putGVsFromEARIntoMap(){
        //TODO: What if the user added more GVs from the UI?

		VFileFactory factory = VFileHelper.createVFileFactory(duLocalEarPath, null);
		try{
			scanDirectory(factory.getRootDirectory(), "", null);
		}
		catch(Exception e){
			logger.log(Level.ERROR, "Failed to load GV from ear file.");
			logger.log(Level.DEBUG, getStackTrace(e));
		}
		finally{
			factory.destroy();
		}
	}

    private void scanDirectory(VFileDirectory dir, String path, Ontology ontology) throws Exception {
        for (Iterator i = dir.getChildren(); i.hasNext(); ) {
            VFile vff = (VFile) i.next();

            if (vff instanceof VFileStream) {
            	String uri = vff.getFullURI().toLowerCase();
            	if(uri.endsWith(".bar")){
            		InputStream is = ((VFileStream)vff).getInputStream();
            		final ZipVFileFactory vFileFactory = new ZipVFileFactory(new ZipInputStream(is));
                    final VFileDirectory directory = vFileFactory.getRootDirectory();
                    scanDirectory(directory, uri, null);
            	}
            	if(uri.endsWith("tibco.xml") && path.endsWith(".bar")){
            		scanFile((VFileStream) vff, path, ontology);
            	}
            }
        }
    }

    private void scanFile(VFileStream file, String path, Ontology ontology ) throws Exception {
        try {
            final String uri = file.getFullURI();
            if (uri.endsWith("TIBCO.xml")){
                final XiNode doc = PARSER.parse(new InputSource(file.getInputStream()));
                final XiNode node = doc.getFirstChild();
                buildGlobalVariablesUsingEARFile(uri, node);
            }
        }
        catch (Exception e) {
            System.out.println("Exception while processing file: " + path);
            e.printStackTrace();
        }
    }

    private void buildGlobalVariablesUsingEARFile(String path, XiNode root) throws Exception {

        for (Iterator itr = XiChild.getIterator(root, Constants.DD.XNames.NAME_VALUE_PAIRS); itr.hasNext();) {
            final XiNode node = (XiNode) itr.next();
            final String name = XiChild.getChild(node, Constants.DD.XNames.NAME).getStringValue();
            if ("Runtime Variables".equalsIgnoreCase(name)) {
                collectGlobalVariables(node, path, Constants.DD.XNames.NAME_VALUE_PAIR);
                collectGlobalVariables(node, path, Constants.DD.XNames.NAME_VALUE_PAIR_INTEGER);
                collectGlobalVariables(node, path, Constants.DD.XNames.NAME_VALUE_PAIR_BOOLEAN);
                collectGlobalVariables(node, path, Constants.DD.XNames.NAME_VALUE_PAIR_PASSWORD);
            }
        }
    }

    private void collectGlobalVariables(XiNode globalVariables, String path, ExpandedName en) {

        for (Iterator it = XiChild.getIterator(globalVariables, en); it.hasNext();) {
            final XiNode gvNode = (XiNode) it.next();
            final String name = XiChild.getString(gvNode, Constants.DD.XNames.NAME);
            if("CDD".equals(name) || "PUID".equals(name)){
            	continue;
            }
            final String value = XiChild.getString(gvNode, Constants.DD.XNames.VALUE);

            BemmDeploymentUnit.GV gv = new BemmDeploymentUnit.GV(name, value, value);
            gvNameToGV.put(name, gv);
        }
    }

    private String getStackTrace(Throwable aThrowable) {
		Writer result = new StringWriter();
		PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}

    void updateGVsMaps(XiNode gvsElem) {                    //TODO: What if I want to add more GVs?
        try {
            for (Iterator iter = XiChild.getIterator(gvsElem, MMGlobalVarsNS.Elements.GV); iter.hasNext();) {
                XiNode gvElem = (XiNode) iter.next();

                BemmDeploymentUnit.GV gv = new BemmDeploymentUnit.GV(
                                                gvElem.getAttributeStringValue(MMGlobalVarsNS.Attributes.NAME),
                                                gvElem.getAttributeStringValue(MMGlobalVarsNS.Attributes.CURR_VALUE),
                                                gvElem.getAttributeStringValue(MMGlobalVarsNS.Attributes.DEF_VALUE)  );

                savedGvNameToGV.put(gv.getName(), gv);
            }

            //rewrite the map containing all the GVs with the values that were set in the UI
            gvNameToGV.putAll(savedGvNameToGV);
        } catch (Exception e) {
            logger.log(Level.ERROR, e, "Error occurred while parsing Saved GVs XML received from the UI");
        }

    }

     void putGVsFromCddFileIntoMap(String savedCddFqPath) {
         if(hasReadSavedCddXml(savedCddFqPath)) {
            parseSavedCddAndUpdateMaps();
         }
    }

    private void parseSavedCddAndUpdateMaps() {
        savedGvNameToGV.clear();    //reset previous values

        final XiNode clusterNode =XiChild.getChild(savedCddXml, ConfigNS.Elements.CLUSTER);
        final XiNode lastPropGrpNode =XiChild.getChild(clusterNode, ConfigNS.Elements.PROPERTY_GROUP);

        boolean hasFoundGvsGrp=false;
        XiNode gvPropGrp=null;

        for (Iterator propGrpIter =  XiChild.getIterator(lastPropGrpNode, ConfigNS.Elements.PROPERTY_GROUP);
                    propGrpIter.hasNext() && !hasFoundGvsGrp; ) {

            gvPropGrp = (XiNode) propGrpIter.next();

            if( gvPropGrp.getAttribute(ConfigNS.Attributes.NAME).getStringValue().equals("global.variables.set.with.mm") )
                hasFoundGvsGrp = true;
        }

        for (Iterator propIter =  XiChild.getIterator(gvPropGrp, ConfigNS.Elements.PROPERTY);
                    propIter.hasNext(); ) {

            final XiNode prop = (XiNode) propIter.next();
            String gvName = prop.getAttribute(MMGlobalVarsNS.Attributes.NAME).getStringValue();
            //Added by Anand on 07/11/14 to fix Be-20854
            if (gvName.startsWith("tibco.clientVar.") == true) {
            	gvName = gvName.substring("tibco.clientVar.".length());
            }
            final String gvCurrVal = prop.getAttribute(MMGlobalVarsNS.Attributes.VALUE).getStringValue();
            final String gvDefVal= gvNameToGV.get(gvName) != null ? gvNameToGV.get(gvName).getDv() : "";

            //we need to update both maps.
            gvNameToGV.put(gvName, new BemmDeploymentUnit.GV(gvName, gvCurrVal, gvDefVal));       //update needed for read operation
            savedGvNameToGV.put(gvName, new BemmDeploymentUnit.GV(gvName, gvCurrVal, gvDefVal));  //update needed for save operation
        }
    }

    private boolean hasReadSavedCddXml(String savedCddFqPath) {
        if(savedCddFqPath == null || savedCddFqPath.isEmpty())  {
            logger.log(Level.WARN, "Path to the directory with the CDDs " +
                    "containing the new GVs values wasn't specified.");
            return false;
        }

        InputSource is=null;
        try {
            FileReader fr = new FileReader(savedCddFqPath);
            is = new InputSource(fr);
        } catch (Exception e) {
            //Ignore. File not found, so GVs will not be updated with values in file.
            //Expected behavior hence return
            return false;
        }

         try {
            savedCddXml= XiSupport.getParser().parse(is);
             return true;
        } catch (Exception e) {
            final String msg = String.format("Exception occurred while parsing the " +
                    "CDD file with the new global variables values: %s. " +
                    " Global variables will have the values set at design time ", savedCddFqPath);
            logger.log(Level.WARN, msg);
            return false;
        }
    }

}
