package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xsd.XSDSchema;

import com.tibco.cep.bpmn.ui.mapper.BpmnSchemaCache;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.DefaultMutableStateEntity;
import com.tibco.cep.designtime.model.mutable.MutableEntity;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntity;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableRuleFunction;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.build.MutableComponentFactoryTNS;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.impl.DefaultComponentFactory;

public class BpmnSchemaGenerator {

	
	
	 static MutableComponentFactoryTNS mcf = DefaultComponentFactory.getTnsAwareInstance();
     final static String TYPE_RULEFUNCTION  = "rulefunction" ;
	 static String TYPE_EVENT = "event" ;
	 public final static String BE_NAMESPACE= "www.tibco.com/be/ontology" ;
	    
	 static MutableSchema getSchema(String ns, boolean createIfNotExists) {
     
	        /**
	         MutableSchema cached=(MutableSchema) schemaCache.get(ns);
	         **/

		
	        //if (cached == null) {
	        MutableSchema cached;
	        cached= mcf.createSchema();
	        cached.setNamespace(ns);
	        cached.setFlavor(XSDL.FLAVOR);
	        //schemaCache.put(ns, cached);
	        //}
	        return cached;
	    }
	    static MutableType getSmType(MutableSchema schema, ExpandedName typeName, boolean overWrite) {
	        return newSmType(typeName, schema);
	    }
	    
	    
	    public static XSDSchema getSchema( String Uri , MutableSchema smElt ,BpmnSchemaCache esm ) {
	    	if (Uri!= null &&  Uri.startsWith( "/" ) ) {
				Uri =   Uri.substring(1);
			}
			BpmnXSDResourceImpl resource = null;
			resource = new BpmnXSDResourceImpl(URI.createURI(Uri));
			addRemoveFromResourceSet(esm, Uri, resource);
				
			try {
				if (!resource.isLoaded()) {
					resource.generateXsdSchema(smElt);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			XSDSchema schema = resource.getSchema();
			return schema ;
			
		}
	    
	    //Wsdl poc 
	    
	    public static void addRemoveFromResourceSet( BpmnSchemaCache esm , String Uri , BpmnXSDResourceImpl resource ) {
	    	
	    	try {
				esm.getBpmnfSchemaResourceSet( ).getResources( ).remove( esm.getBpmnfEntitySchemaMap( ).get( Uri ) ) ;
				esm.getBpmnfEntitySchemaMap( ).remove( Uri ); 
				esm.getBpmnfEntitySchemaMap( ).put( Uri, resource );
				esm.getBpmnfSchemaResourceSet( ).getResources( ).add( resource);
				
				} catch ( Exception e ) { 
					System.out.println( "Exception while adding to resource set" );
			}
			
	    }
	    
	    public static XSDSchema getSchemaForWsdl( String Uri , String  schemaString ,BpmnSchemaCache esm ) {
			BpmnXSDResourceImpl resource = null;
			  resource =  new BpmnXSDResourceImpl(URI.createURI( Uri ) );
			  addRemoveFromResourceSet( esm , Uri , resource );
			try {
				if (!resource.isLoaded()) {
					resource.generateXsdSchemaForWsdl(schemaString);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			XSDSchema schema = resource.getSchema();
			return schema ;
			
		}
	    
	    
	    public static void loadWsdlSchema(Map<String,String> map ,BpmnSchemaCache esm ) {
	    	
	    	for ( Map.Entry< String, String > entry :map.entrySet( ) ) {
	    		getSchemaForWsdl(entry.getKey() , entry.getValue() , esm ) ;
	    	}
	    }
	    
	    static MutableType newSmType( ExpandedName typeName, MutableSchema schema ) {
	        MutableType smType = mcf.createType( ) ;
	        smType.setExpandedName( typeName ) ;
	        smType.setSchema( schema ) ;
	        smType.setComplex( ) ;
	        smType.setAllowedDerivation( SmComponent.EXTENSION | SmComponent.RESTRICTION ) ;

	        schema.addSchemaComponent( smType ) ;

	        // Add the element
	        MutableElement elem = mcf.createElement( ) ;
	        elem.setType(smType);
	        elem.setSchema(schema);
	        elem.setExpandedName(typeName);
	        /**
	         MutableType typeRef= mcf.createTypeRef();
	         typeRef.setReference(schema);
	         typeRef.setSchema(schema);
	         typeRef.setExpandedName(smType.getExpandedName());
	         MutableElement element= MutableSupport.createElement(schema, smType.getExpandedName().getLocalName(), typeRef);
	         **/
	        schema.addSchemaComponent( elem ) ;
	        return smType ;
	    }

	  public static MutableSchema getSchema(Entity e, boolean createIfNotExists) {
	        String ns=BE_NAMESPACE+e.getFullPath();
	        return getSchema(ns, createIfNotExists);
	    }
	  

	  
	  public static MutableEntity createEntityFromNode(XiNode root) throws ModelException {
	        String typeName = root.getName().getLocalName();
	        MutableEntity model = null;

	       if (typeName.equals("ruleFunction")) {
	            model = DefaultMutableRuleFunction.createDefaultRuleFunctionFromNode(root);
	       }
	        if (model == null) return null;

	        if (model instanceof DefaultMutableStateEntity) {
	            ((DefaultMutableStateEntity) model).fromXiNode(root);
	        }//e
	        // ndif
	        /* *****************/

	        String bindings = root.getAttributeStringValue(ExpandedName.makeName("bindings"));
	        String iconPath = root.getAttributeStringValue(ExpandedName.makeName("icon"));
	        String namespace = root.getAttributeStringValue(ExpandedName.makeName("namespace"));

	        String lastModified;

	        XiNode lastModifiedNode = XiChild.getChild(root, AbstractMutableEntity.LAST_MODIFIED_NAME);
	        if (lastModifiedNode == null) {
//	            lastModified = XsDateTime.currentDateTime().castAsString();
	            lastModified = "";
	        } else {
	            lastModified = lastModifiedNode.getStringValue();
	        }

	        /** Model should not be null */
	        XiNode hiddenPropsNode = XiChild.getChild(root, AbstractMutableEntity.HIDDEN_PROPERTIES_NAME);
	        //createHiddenPropertiesFromNode(hiddenPropsNode, model);
	        if (hiddenPropsNode != null) {
	            Iterator it = hiddenPropsNode.getChildren();
	            while (it.hasNext()) {
	                XiNode hiddenPropNode = (XiNode) it.next();
	                String key = hiddenPropNode.getAttributeStringValue(AbstractMutableEntity.HIDDEN_PROPERTY_KEY_NAME);
	                String value = hiddenPropNode.getAttributeStringValue(AbstractMutableEntity.HIDDEN_PROPERTY_VALUE_NAME);
	                model.setHiddenProperty(key, value);
	            }

	        }

	        model.setBindingString((bindings != null) ? bindings : "");
	        model.setIconPath(iconPath);
	        model.setNamespace(namespace);
	        model.setLastModified(lastModified);

	        return model;
	    }
	  
	  
	  
	  
	  
}