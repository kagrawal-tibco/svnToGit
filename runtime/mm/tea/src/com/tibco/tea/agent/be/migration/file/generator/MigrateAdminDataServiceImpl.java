package com.tibco.tea.agent.be.migration.file.generator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataSource;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.management.exception.BEApplicationSaveException;
import com.tibco.cep.bemm.management.exception.FileSaveException;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.tea.agent.be.migration.file.generator.application.config.Application;
import com.tibco.tea.agent.be.migration.file.generator.application.config.Binding;
import com.tibco.tea.agent.be.migration.file.generator.application.config.JAVA;
import com.tibco.tea.agent.be.migration.file.generator.application.config.NVPairs;
import com.tibco.tea.agent.be.migration.file.generator.application.config.NameValuePair;
import com.tibco.tea.agent.be.migration.file.generator.application.config.NameValuePairBoolean;
import com.tibco.tea.agent.be.migration.file.generator.application.config.NameValuePairInteger;
import com.tibco.tea.agent.be.migration.file.generator.application.config.NameValuePairPassword;
import com.tibco.tea.agent.be.migration.file.generator.application.config.Product;
import com.tibco.tea.agent.be.migration.file.generator.application.config.Service;
import com.tibco.tea.agent.be.migration.file.generator.application.config.Services;
import com.tibco.tea.agent.be.migration.utils.UnzipUtility;
import com.tibco.tea.agent.be.ui.model.BE;
import com.tibco.tea.agent.be.ui.model.impl.BEImpl;

/**
 * @author ssinghal
 *
 */

public class MigrateAdminDataServiceImpl implements BeTeaAgentExportService{
	
	List exData = null;
	List<MApplication> apps = null;	
	List<MMachine> machines = new ArrayList<MMachine>();
	/*List<String> machineNames_beHome = new ArrayList<String>();*/
	Map<String, Integer> machineNames_beHome = null;
	Map<String, List<BE>> machineNames = null;
	Map<String, Integer> machineNames_beHome1 = null;
	Map<String, Integer> machineNames1 = null;
	String dataStorePath =null;
	Properties configuration;
	private MessageService messageService;
	
	public static void main(String[] args) {
		MigrateAdminDataServiceImpl migrate = new MigrateAdminDataServiceImpl();
		//migrate.generate(new DataSource("C:/Users/ssinghal/Desktop/FraudDetectionCache.zip") ,"C:/tibco/be/5.3/teagent/migration/m2");
	}
	
	@Override
	public void init(Properties configuration){
		try {
			String dataStorePath = (String) ConfigProperty.BE_TEA_AGENT_APPLICATION_DATASTORE.getValue(configuration);
			dataStorePath = dataStorePath + "/migrate";
			this.dataStorePath = dataStorePath;
			this.configuration = configuration;
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
	}
	
	public String[][] generateUsersAndRoles(DataSource xmlFile) throws Exception{
		
		//delete old file if any
		File oldFile = new File(dataStorePath + "/" + xmlFile.getName());
		oldFile.delete();
		
		byte[] xmlFileByteArray = ManagementUtil.getByteArrayFromStream(xmlFile.getInputStream());
		storeSourceFile(dataStorePath, xmlFileByteArray, xmlFile.getName());
		
		String dest = dataStorePath + "/" + xmlFile.getName();
		return GenerateUserAndRolesData.generate(dest, configuration);
	}
	
	@Override
	public List<MApplication> generate(DataSource zipFile, String source) throws Exception{
		
		deleteTempFiles();
		exData = new ArrayList();
		apps = new ArrayList<MApplication>();
		machines = new ArrayList<MMachine>();
		machineNames_beHome = new HashMap<String, Integer>();
		machineNames = new HashMap<String, List<BE>>();
		
		byte[] zipFileByteArray = ManagementUtil.getByteArrayFromStream(zipFile.getInputStream());
		storeSourceFile(dataStorePath, zipFileByteArray, zipFile.getName());
		UnzipUtility unzipper = new UnzipUtility();
		String dest = dataStorePath + "/" + zipFile.getName().replaceFirst(".zip", "");
		unzipper.unzip(dataStorePath +"/" + zipFile.getName(), dest, null);
		
		List<Application> applications = GenerateApplicationFile.start(dest, source, configuration);
		if(applications==null){
			throw new Exception(messageService.getMessage(MessageKey.READEXPORTEDFILE_ERROR));
		}
		convert(applications);
		
		return exData;
	}
	
	public void deleteTempFiles(){
		deleteFiles(new File(dataStorePath));
	}
	private void deleteFiles(File file) {

		try{
			if(file.isDirectory()){
				String[] list = file.list();
				for(String p : list){
					deleteFiles(new File(file, p));
				}
			}
			file.delete();
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void storeSourceFile(String dataStorePath, byte[] applicationArchive, String zipName)
			throws BEApplicationSaveException {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(applicationArchive);
			saveConfigResource(dataStorePath, bais, zipName);
		} catch (IOException | FileSaveException ex) {
			throw new BEApplicationSaveException(ex);
		} finally {

			if (null != bais) {
				try {
					bais.close();
				} catch (IOException ex) {
					//LOGGER.log(Level.ERROR, ex, ex.getMessage());
				}
			}

		}
	}
	
	private void saveConfigResource(String dataStorePath, InputStream in, String targetFileName)
			throws IOException, FileSaveException {
		File applicationDir = new File(dataStorePath);
		if (!applicationDir.exists()) {
			Files.createDirectory(applicationDir.toPath(), new FileAttribute[0]);
		} else if (!applicationDir.isDirectory()) {
			throw new FileSaveException(messageService.getMessage(MessageKey.NOT_DIRECTORY,applicationDir.getAbsolutePath()));
		} else if (!applicationDir.canWrite()) {
			throw new FileSaveException(messageService.getMessage(MessageKey.NOT_HAVE_PERMISSION_WRITE, applicationDir.getAbsolutePath()));
		}
		File file = new File(applicationDir, targetFileName);
		ManagementUtil.streamCopy(in, file);
	}

	private void convert(List<Application> applications) {
		
		for(Application app : applications)
		{
			getMachines(app);
			
			MApplication mApp = new MApplication();
			mApp.setName(app.getName());
			mApp.setEarPath(app.getExportedEarPath());
			mApp.setCddPath(app.getExportedCddPath());
			//mApp.setGlobalVariables(getGlobalVars(app));
			mApp.setInstance(getInstances(app));
			//mApp.setMachines(getMachines(app));			
			apps.add(mApp);
			
			
		}
		exData.add(apps);
		exData.add(machines);
	}
	
	private List<MMachine> getMachines(Application app) {
		
		Services services = app.getServices();
		if(services!=null){
			for(Service service : services.getService())
			{
				for(Binding binding : service.getBindings().getBinding())
				{
					Product product = binding.getProduct();
					
					if(machineNames.containsKey(product.getMachineName())){ //when machine already created before, we just need to add new behome, betra to machine
						if(!machineNames_beHome.containsKey(product.getMachineName() + "_" + product.getLocation())){ //same behome should not be added again 
							machineNames_beHome.put(product.getMachineName() + "_" + product.getLocation(), null);
							for(MMachine mac : machines){
								if(mac.getMachineName().equals(product.getMachineName())){
									List<BE> beList = mac.getBeList();
									BE be = new BEImpl(); be.setBeHome(product.getLocation()); be.setBeTra(product.getLocation()  + "/bin/be-engine.tra");be.setVersion(product.getVersion());
									beList.add(be);
									mac.setBeList(beList);
									mac.getRelatedApps().add(app.getName());
									break;
								}
							}
						}else{ //add only related app
							for(MMachine mac : machines){
								if(mac.getMachineName().equals(product.getMachineName())){
									mac.getRelatedApps().add(app.getName());
								}
							}
						}
					}else{
						machineNames.put(product.getMachineName(), null);
						machineNames_beHome.put(product.getMachineName() + "_" + product.getLocation(), null);
						
						MMachine machine = new MMachine();
						machine.setMachineName(product.getMachineName());
						machine.setIp(product.getIp());
						machine.setOs(product.getOs());
						
						List<BE> beList = new ArrayList<BE>();
						BE be = new BEImpl(); be.setBeHome(product.getLocation()); be.setBeTra(product.getLocation()  + "/bin/be-engine.tra");be.setVersion(product.getVersion());
						beList.add(be);
						machine.setBeList(beList);
						
						machine.setSshUser(product.getSysUser());
						machine.setSshPass(product.getSysPass());
						machine.setSshPort(product.getSshPort());
						machine.setDeploymentPath(product.getDeploymentPath());
						Set<String> relApps = new HashSet<String>(); relApps.add(app.getName());
						machine.setRelatedApps(relApps);
						
						machines.add(machine);
					}
				}
			}
		}
		return machines;
	}
	
	
	private List<MInstance> getInstances(Application app) {
		
		List<MInstance> instances = new ArrayList<MInstance>();
		Services services = app.getServices();
		if(services!=null)
		{
			for(Service service : services.getService())
			{
				for(Binding binding : service.getBindings().getBinding())
				{
					MInstance instance = new MInstance();					
					instance.setInstanceName(binding.getName());
					
					List<NVPairs> nameValues = binding.getNVPairs();
					boolean puidFlag = false;
					for(NVPairs nv : nameValues)
					{
						if(nv.getName().equalsIgnoreCase("Runtime Variables"))
						{
							for(NameValuePair nvPair : nv.getNameValuePair())
							{
								if(nvPair.getName().equalsIgnoreCase("PUID"))
								{
									instance.setPu(nvPair.getValue());
									puidFlag = true;
								}
							}
						}
					}
					
					if(puidFlag==false){
						for(NVPairs serviceNvs : service.getNVPairs())
				         {
			        		if(serviceNvs.getName().equalsIgnoreCase("Runtime Variables")) {
			        			for(NameValuePair pair :serviceNvs.getNameValuePair())
			        			{
			        				if(pair.getName().equalsIgnoreCase("PUID")){
			        					instance.setPu(pair.getValue());
			        				}
			        			}
			        		}
				         }
					}
					
					Product product = binding.getProduct();
					instance.setMachineName(product.getMachineName());
					//instance.setMachineName(product.getMachineName() + "_" + machineNames_beHome.get(product.getMachineName() + "_" + product.getLocation()));
					instance.setJmxPort(product.getJmxport());
					instance.setDeployPath(product.getDeploymentPath());
					instance.setBeHome(product.getLocation());
					
					JAVA jvmVars = binding.getSetting().getJava();
					Map<String, Object> jvmProps = new HashMap<String, Object>();
					jvmProps.put("java.heap.size.initial", jvmVars.getInitHeapSize());
					jvmProps.put("java.heap.size.max", jvmVars.getMaxHeapSize());
					//jvmProps.put("threadstacksize", jvmVars.getThreadStackSize());
					instance.setJvmProperties(jvmProps);
					
					Map<String, Object> globalVars = getGlobalVars(app, null);
					instance.setGlobalVariables(globalVars);
					//Case where instance overrides a global variable value. So setting all gv at instance level.
					for(NVPairs pair :binding.getNVPairs()){
						if(pair.getName().equalsIgnoreCase("Global Variables")){ // at instance level stored under Global Variables
							instance.setGlobalVariables(getGlobalVars(pair, globalVars, true));
						}
					}
					
					instances.add(instance);
				}
			}
		}
		return instances;
	}
	
	private Map<String, Object> getGlobalVars(Application app, Map<String, Object> globalVars){
		return getGlobalVars(app.getNVPairs(), globalVars, false);
	}

	private Map<String, Object> getGlobalVars(NVPairs nvpairs, Map<String, Object> globalVars, boolean instanceFlag){

		if(globalVars==null){
			globalVars = new HashMap<String, Object>();
		}
		
		if((nvpairs.getName().equalsIgnoreCase("Global Variables")) || ((nvpairs.getName().equalsIgnoreCase("Runtime Variables")) && (instanceFlag==true) ))
		{
			for(NameValuePair pair : nvpairs.getNameValuePair())
				globalVars.put(pair.getName(), pair.getValue());
			for(NameValuePairBoolean pair : nvpairs.getNameValuePairBoolean())
				globalVars.put(pair.getName(), pair.isValue());
			for(NameValuePairInteger pair : nvpairs.getNameValuePairInteger())
				globalVars.put(pair.getName(), pair.getValue());
			for(NameValuePairPassword pair : nvpairs.getNameValuePairPassword())
				globalVars.put(pair.getName(), pair.getValue());
		}
		globalVars.remove("PUID"); // At instance level PUID is coming under global variables, and hence removing.
		return globalVars;
	}

	public List<MApplication> getApps() {
		return apps;
	}

}
