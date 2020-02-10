package com.tibco.tea.agent.be.migration.file.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.tea.agent.be.migration.file.generator.application.config.Application;
import com.tibco.tea.agent.be.migration.file.generator.application.config.ObjectFactory;
import com.tibco.tea.agent.be.migration.file.generator.application.config.Services;
import com.tibco.tea.agent.be.migration.file.generator.application.config.Service;
import com.tibco.tea.agent.be.migration.file.generator.application.config.Binding;
import com.tibco.tea.agent.be.migration.file.generator.application.config.NVPairs;
import com.tibco.tea.agent.be.migration.file.generator.application.config.NameValuePair;
import com.tibco.tea.agent.be.migration.file.generator.application.config.Product;
import com.tibco.tea.agent.be.migration.file.generator.appmanage.config.App;
import com.tibco.tea.agent.be.migration.file.generator.appmanage.config.Apps;
import com.tibco.tea.agent.be.migration.utils.UnzipUtility;
import com.tibco.tea.agent.be.util.SupportedOS;

/**
 * @author ssinghal
 *
 */

public class GenerateApplicationFile {
	
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GenerateApplicationFile.class);
	private static List<Application> apps = new ArrayList<Application>();
	public static final String SOURCE_GUI = "GUI";
	public static final String SOURCE_CLI = "CLI";
	
	
	
	public static List<Application> start(String dataStorepath, String source, Properties configuration) throws Exception{
		
		String exportedFilePath = dataStorepath+"/AppManage.batch";
		
		try{
			JAXBContext jc = JAXBContext.newInstance(Apps.class);		
	        Unmarshaller unmarshaller = jc.createUnmarshaller();  
	        Apps applications = (Apps) unmarshaller.unmarshal(new File(exportedFilePath));
	        
	        String csvConfigFile = null;
	        Map configValues = null;
	        csvConfigFile = dataStorepath+"/config.csv";
	        File file = new File(csvConfigFile);
	        if(file.exists()){
	        	configValues = readCsv(file);
	        }
	        
	        if(source.equals(SOURCE_CLI)){
	        	if(configValues==null || configValues.isEmpty())
        			throw new Exception(BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.CONFIG_CSV_FILE_NOT_PRESENT));
	        }
	        
	        	
	        for(App app : applications.getApp())
	        {
	        	if(configValues!=null && !configValues.isEmpty()){
	        		startIndividual(dataStorepath + "/" + app.getXml(), dataStorepath + "/" + app.getEar(), dataStorepath+"/"+app.getName()+".cdd", configValues);
	        	}else{
	        		//TODO - First check files paths are valid or not. If yes then only proceed else return with proper message.
		        	startIndividual(dataStorepath + "/" + app.getXml(), dataStorepath + "/" + app.getEar(), dataStorepath+"/"+app.getName()+".cdd", null);
	        	}
	        	
	        }
	        
	        List<Application> copy = new ArrayList<Application>(apps);
			apps.clear();
			
			return copy;
	        
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
		
		//return null;
				
	}
	
	public static Map<String, ConfigDataPojo> readCsv(File file) throws ObjectCreationException
	{
		Map<String, ConfigDataPojo> configData = new HashMap<String, ConfigDataPojo>();
		int flag = 0;
		try{
			boolean headerFlag=true;
		    List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		    for (int i=1;i<lines.size();i++) {
		        String[] array = lines.get(i).split(",");		       
		        ConfigDataPojo data = new ConfigDataPojo();
		        
		        if(array.length==4){
		        	data.setApplicatioName(array[0]);
			        data.setInstanceName(array[1]);
			        data.setIp(array[2]);
			        data.setJmxPort(Integer.parseInt(array[3]));
			        configData.put(array[0]+array[1], data);
		        }else if(array.length==6){
		        	if(headerFlag==false){
		        		for(Map.Entry<String, ConfigDataPojo> entry : configData.entrySet()){
		        			ConfigDataPojo d_ = entry.getValue();
		        			String k_ = entry.getKey();
		        			if(d_.getIp().equals(array[0])){
		        				d_.setDeploymentPath(array[2]);
		        				d_.setSysUser(array[3]);
		        				d_.setSysPass(array[4]);
		        				d_.setSshPort(Integer.parseInt(array[5]));
		        		        if(array[1].equals("unix")){
		        		        	d_.setOs(SupportedOS.Unix.getDescription());
		        		        }else{
		        		        	d_.setOs(SupportedOS.Windows.getDescription());
		        		        }
		        			}
		        			entry.setValue(d_);
			        	}
		        	}else{
		        		headerFlag=false;
		        	}
		        }
		    }
		}catch(IOException ie){
			LOGGER.log(Level.ERROR, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.READING_MIGRATION_CONFIG_FILE_ERROR) +  ie.getMessage());
		}
	    
	    return configData;
	}
	
	public static Map<String, ConfigDataPojo> readCsv_bk(File file) throws ObjectCreationException
	{
		Map<String, ConfigDataPojo> configData = new HashMap<String, ConfigDataPojo>();
		try{
		    List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		    for (int i=1;i<lines.size();i++) {
		        String[] array = lines.get(i).split(",");
		        ConfigDataPojo data = new ConfigDataPojo();
		        data.setApplicatioName(array[0]);
		        data.setInstanceName(array[1]);
		        data.setIp(array[2]);
		        if(array[3].equals("unix")){
		        	data.setOs(SupportedOS.Unix.getDescription());
		        }else{
		        	data.setOs(SupportedOS.Windows.getDescription());
		        }
		        data.setDeploymentPath(array[4]);
		        data.setSysUser(array[5]);
		        data.setSysPass(array[6]);
		        data.setSshPort(Integer.parseInt(array[7]));
		        data.setJmxPort(Integer.parseInt(array[8]));
		        configData.put(array[0]+array[1], data);
		    }
		}catch(IOException ie){
			LOGGER.log(Level.ERROR, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.READING_MIGRATION_CONFIG_FILE_ERROR) +  ie.getMessage());
		}
	    
	    return configData;
	}


	private static void startIndividual(String appFilePath, String appEarFilePath, String appCddFilePath, Map<String, ConfigDataPojo> configValues) throws Exception
	{
		//get exported data from Tibco admin exported files
		Application application = readExportedFile(appFilePath, appEarFilePath, appCddFilePath, configValues);
		if(application!=null)
			apps.add(application);
	}
	
	private static Application readExportedFile(String appFilePath, String appEarFilePath, String appCddFilePath, Map<String, ConfigDataPojo> configValues) throws Exception {
		try{
			JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
	
	        Unmarshaller unmarshaller = jc.createUnmarshaller();  
	        Application application = (Application) unmarshaller.unmarshal(new File(appFilePath));
	        
	        application.setExportedEarPath( appEarFilePath );
	        application.setExportedCddPath(appCddFilePath);
	        
	        Services services = application.getServices();
	        Map<String, Integer> machines = new HashMap<String, Integer>();
	        if(services!=null){
	        	
	        	if(!isABeApp(services)){
	        		return null;
	        	}
		       
	        	for(Service service : services.getService())
				{
	        		Iterator<NVPairs> itr = service.getNVPairs().iterator();
		        	while(itr.hasNext()){
		        		NVPairs nv = itr.next();
		        		if(nv.getName().equalsIgnoreCase("versions")){
		        			itr.remove();
		        		}
		        	}
		        	
					for(Binding binding : service.getBindings().getBinding())
					{
						String identifier = application.getName()+binding.getName();
						
						Product product = binding.getProduct();
						product.setDeploymentPath(configValues!=null? ((ConfigDataPojo)configValues.get(identifier)).getDeploymentPath() : ""   );
						product.setMachineName(binding.getMachine());
						product.setIp(configValues!=null? ((ConfigDataPojo)configValues.get(identifier)).getIp() : "");
						
						if(configValues==null){
							if(machines.containsKey(binding.getMachine())){
								int port = machines.get(binding.getMachine()) + 1;
								product.setJmxport(port);
								machines.put(binding.getMachine(), port);
							}else{
								product.setJmxport(5500);
								machines.put(binding.getMachine(), 5500);
							}
						}else{
							product.setJmxport(((ConfigDataPojo)configValues.get(identifier)).getJmxPort());
						}
						
						product.setOs( configValues!=null? ((ConfigDataPojo)configValues.get(identifier)).getOs() : "" );
						product.setSshPort(configValues!=null? ((ConfigDataPojo)configValues.get(identifier)).getSshPort() : 22);
						product.setSysPass(configValues!=null? ((ConfigDataPojo)configValues.get(identifier)).getSysPass() : "");
						product.setSysUser(configValues!=null? ((ConfigDataPojo)configValues.get(identifier)).getSysUser() : "");
						
					}
				}
	        }
	        
	       return application;
	       
		}catch(Exception e){
			LOGGER.log(Level.ERROR, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.READEXPORTEDFILE_ERROR) + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	private static boolean isABeApp(Services services) {
		
		
		if(services.getService()==null || services.getService().isEmpty()){
			return false;
		}
		
		try{
			for(Service service : services.getService())
			{
				for(Binding binding : service.getBindings().getBinding())
				{
					if(binding.getProduct().getType().equalsIgnoreCase("BE")){
						return true;
					}
				}
			}
		}catch(Exception e){ // Case of bad application export, where binding or product is null, or some other issue.
			return false;
		}
		return false;
	}

	private static void getCddPath(Application application, String appEarFilePath, String dataExportpath, String appName) 
	{
		Services services = application.getServices();
	   	 if(services!=null)
	   	 {
	   		 for(Service service : services.getService())
			 {
	   			 for(NVPairs serviceNvs : service.getNVPairs())
		         {
	        		if(serviceNvs.getName().equalsIgnoreCase("Runtime Variables")) {
	        			for(NameValuePair pair :serviceNvs.getNameValuePair())
	        			{
	        				if(pair.getName().equalsIgnoreCase("CDD")){
	        					if(pair.getValue()==null || pair.getValue().equalsIgnoreCase("")){
	        						UnzipUtility unzipper = new UnzipUtility();
	        				        try {
	        				            unzipper.unzip(appEarFilePath, dataExportpath+"/unzip/", appName+".cdd");
	        				        } catch (Exception ex) {
	        				            // some errors occurred
	        				            ex.printStackTrace();
	        				        }
	        				        pair.setValue("\""+dataExportpath+"/unzip/" + appName+".cdd" + "\"");
	        				        application.setExportedCddPath(dataExportpath+"/unzip/" + appName+".cdd" );
	        					}else{
	        						application.setExportedCddPath(pair.getValue() );
	        					}
	        				}
	        			}
	        		}
		         }
			 }
	   	 }
	}

}
