package com.tibco.cep.dashboard.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.cep.dashboard.integration.embedded.PresentationServer;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.Component;
import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.Skin;
import com.tibco.cep.designtime.core.model.beviewsconfig.Visualization;
import com.tibco.cep.designtime.core.model.impl.ModelPackageImpl;
import com.tibco.cep.studio.dashboard.preview.SeriesDataSet;

public class EmbeddedPSVRTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 2){
			System.err.println("java "+EmbeddedPSVRTest.class.getName()+" <system elements file> <chart file>");
			System.exit(-1);
		}
		ResourceSet resourceSet = new ResourceSetImpl();                       
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		BEViewsConfigurationPackage.eINSTANCE.eClass();
		ModelPackageImpl.eINSTANCE.eClass();

		// set the document builder factory property
//		String xmlParserFactory = (String) MALProperties.XML_PARSERS_FACTORY.getValue(properties);
//		System.setProperty(MALProperties.XML_PARSERS_FACTORY.getName(), xmlParserFactory);
		System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");

		//load system elements 
		Skin skin = null;
		URI fileURI = URI.createFileURI(args[0]);
		Resource resource = resourceSet.getResource(fileURI, true);
		EList<EObject> contents = resource.getContents();
		for (EObject object : contents) {
			Entity entity = (Entity) object;
			if (entity instanceof Skin) {
	            skin = (Skin)entity;
	            break;
			}
		}

		//load chart 
		Component component = null;
		fileURI = URI.createFileURI(args[1]);
		resource = resourceSet.getResource(fileURI, true);
		contents = resource.getContents();
		for (EObject object : contents) {
			Entity entity = (Entity) object;
			if (entity instanceof Component) {
                component = (Component)entity;
                break;
			}
		}
		
		//create dummy data
//		ESeriesData[] sData = new ESeriesData[1];
		
//		for(int i = 0; i < sData.length; i++){
//			String seriesID = i%2==0 ? "C5969042-38B2-82A1-4BF6-BC36FFF2E6BD":"8E671FFB-FC97-9CBF-09D6-23F56B587B56";
//			sData[i] = new ESeriesData(seriesID, "MarxSeries_"+i);
//			for(int j = 0; j < 6; j++){
//				sData[i].add("Cat_"+j, Math.random()*100);
//			}
//		}
//		sData[0] = new ESeriesData("BB31EFF7-3CE0-2E72-170E-57A53F0C3FD6", "MarxSeries");
//		for(int j = 0; j < 6; j++){
//			sData[0].add("Cat_"+j, Math.random()*150);
//		}
		
		SeriesDataSet[] sData = getData(component);
		
		String xml = "<root>GIVE ME XML!!!</root>";
		PresentationServer eps = null;
		try {
			eps = new PresentationServer();
			eps.setSkin(skin);
			eps.start();
			xml = eps.getComponentConfigWithData(component, sData);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (eps != null) {
				eps.shutdown();
			}
		}
		
		System.err.println(xml);
//		BufferedWriter writer = null;
//		try{
//			writer = new BufferedWriter(new FileWriter("C:\\wamp\\www\\epsvr_output.xml"));
//			writer.write(xml);
//		}
//		catch(IOException ex){
//			System.err.println("\n\n"+ex.toString()+"\n");
//		}
//		finally{
//			try {
//				writer.close();
//			}
//			catch(IOException e){
//				System.err.println("\n\nFailed closing of output file!\n"+e.toString());
//				e.printStackTrace();
//			}
//		}

	}
	
	static SeriesDataSet[] getData(Component component){
		Random random = new Random();
		SeriesConfig[] seriesConfigs = getSeries(component);
		SeriesDataSet[] seriesDataSet = new SeriesDataSet[seriesConfigs.length];
		LinkedList<String> categoryValues = new LinkedList<String>();
		double minValue = 0;
		double maxValue = 0;
		double difference = 0;		
		PropertyMap extendedPropertiesHolder = component.getExtendedProperties();
		if (extendedPropertiesHolder != null) {
			List<Entity> extendedProperties = extendedPropertiesHolder.getProperties();
			for (Entity extendedProperty : extendedProperties) {
				if (extendedProperty.getName().equals("Category") == true) {
					PropertyMap categoryExtendedPropertiesHolder = extendedProperty.getExtendedProperties();
					if (categoryExtendedPropertiesHolder != null) {
						List<Entity> categoryValueProperties = categoryExtendedPropertiesHolder.getProperties();
						for (Entity categoryValueProperty : categoryValueProperties) {
							categoryValues.add(categoryValueProperty.getName());
						}
					}
				} else if (extendedProperty.getName().equals("MinValue") == true) {
					minValue = Double.parseDouble(((SimpleProperty) extendedProperty).getValue());
				} else if (extendedProperty.getName().equals("MaxValue") == true) {
					maxValue = Double.parseDouble(((SimpleProperty) extendedProperty).getValue());
				}
			}
			difference = maxValue - minValue;
		}	
		for (int i = 0; i < seriesConfigs.length; i++) {
			SeriesConfig seriesConfig = seriesConfigs[i];
			SeriesDataSet seriesData = new SeriesDataSet(seriesConfig.getGUID(),seriesConfig.getName());
			for (String categoryValue : categoryValues) {
				seriesData.add(categoryValue, minValue + difference * random.nextDouble());
			}
			seriesDataSet[i] = seriesData;
		}
		return seriesDataSet;
	}
	
	static SeriesConfig[] getSeries(Component component){
		List<SeriesConfig> seriesConfigs = new ArrayList<SeriesConfig>();
		for (Visualization visualization : component.getVisualization()) {
			seriesConfigs.addAll(visualization.getSeriesConfig());
		}
		return seriesConfigs.toArray(new SeriesConfig[seriesConfigs.size()]);
	}

}
