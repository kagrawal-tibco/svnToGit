package com.tibco.rta.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.pattern.dsl.patternParser.main_return;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.store.persistence.model.invm.InMemoryTable;
import com.tibco.store.persistence.model.invm.impl.InMemoryDataStore;
import com.tibco.store.persistence.service.invm.DataServiceFactory;
import com.tibco.store.persistence.service.invm.InMemoryDataStoreService;

public class TestUtilities {
	
	/****
	 * Way to get inmemory tables
	 */
	public static Map<String, InMemoryTable> getInMemoryTables()
	{
		try
		{
			InMemoryDataStoreService dataService = DataServiceFactory.getInstance().getDataStoreService();
		
			Field f = dataService.getClass().getDeclaredField("inMemoryDataStore"); 
			f.setAccessible(true);
			InMemoryDataStore inMemoryDataStore = (InMemoryDataStore)f.get(dataService);
			
			
			Field f1 = inMemoryDataStore.getClass().getDeclaredField("memoryTables"); 
			f1.setAccessible(true);
			Map<String, InMemoryTable> memoryTables = (Map<String, InMemoryTable>)f1.get(inMemoryDataStore);
			
			
			
			
			return memoryTables;
		}
		catch(NoSuchFieldException e)
		{
			System.out.println("No such field");
			return null;
		}
		catch (Exception e) 
		{
			System.out.println("Exception while getting memory tables");
			return null;
		}
		
	}
	
	public static void displayInMemTables()	{
		
		Map<String, InMemoryTable> inMemTable = getInMemoryTables();
		Set<String> tableNames = inMemTable.keySet();
		Set<Entry<String, InMemoryTable>> set = inMemTable.entrySet();
		for(Entry<String, InMemoryTable> entry : set)
		{
			InMemoryTable table = entry.getValue();
			ConcurrentHashMap map = (ConcurrentHashMap) table.getAllTuples();
			Set<Entry> set1 = map.entrySet();
			for(Entry entry1 : set1)
			{
				System.out.println();
			}
		}
	}
	
	
	
	/*
	 * Sample bat file attached in same package
	 * Edit bat file accordingly
	 * 
	 */
	public static void clearDbData()
	{
		Runtime runtime = Runtime.getRuntime();
		try {
		    Process p1 = runtime.exec("cmd /c start C:/Users/ssinghal/Desktop/test/db.bat");
		    p1.waitFor();
		   // p1.destroy();
		   // runtime.exit(0);
		    /*InputStream is = p1.getInputStream();
		    int i = 0;
		    while( (i = is.read() ) != -1) {
		        System.out.print((char)i);
		    }*/
		} catch(IOException ioException) {
		    System.out.println(ioException.getMessage() );
		}
		catch(InterruptedException intException) {
		    System.out.println(intException.getMessage() );
		}
	}
	
	
	public static List<String> readCsv(String csvfilePath) throws Exception
	{
		File file = new File(/*"C:/Users/ssinghal/Desktop/export.csv"*/ csvfilePath);
	    List<String> lines = Files.readAllLines(file.toPath(), 
	            StandardCharsets.UTF_8);
	    for (String line : lines) {
	        String[] array = line.split(",");
	        System.out.println(array[0]);
	    }
	    
	    return lines;
	}
	
	
	

}
