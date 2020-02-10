package com.tibco.cep.bpmn.ui;

import java.util.Iterator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessAdapter;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessOntologyAdapter;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.xml.data.primitive.XmlSequence;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

public class XPathObjectExpressionValidator extends XPathExpressionValidator {

	public Boolean isConsValue = false;
	public SmType smType = null ;
	public String path = null ;
	public Boolean isValid = false ;
	public String name = null ;
	public String type = null ;
	public Boolean isArray = false ;
	private String fProject;
	private EObjectWrapper<EClass, EObject> process;
	
	public XPathObjectExpressionValidator(EObjectWrapper<EClass, EObject> proc, String projName) {
		super("Variable List");
		this.fProject = projName;
		this.process = proc;
	}

	@Override
	public boolean isValid(SmSequenceType xtype) {
		SmCardinality eltCardinality = SmSequenceTypeSupport.getTermOccurrence(xtype);
		eltCardinality.getMaxOccurs();
		if ( eltCardinality.getMaxOccurs()> 1) {
			isArray = true ;
		}
		XmlSequence cons = xtype.getConstantValue();
		if ( cons !=null) {
			isConsValue = true ;
			return false ;
		} 
		smType = SmSequenceTypeSupport
				.getPrimitiveSchemaType(xtype);
		
		if (smType != null) {
			if (smType == XSDL.DOUBLE || smType == XSDL.INT
					|| smType == XSDL.INTEGER ) {
				type = getRdfType(smType);
				isValid = true;
				return true;
			}
		}
		if (smType == null && SmSequenceTypeSupport.isText(xtype)) {
			smType = XSDL.STRING;
			type = getRdfType(smType);
			isValid = true;
			return true;
		}
		
		smType = SmSequenceTypeSupport.getSchemaType(xtype);
		path = "";
		if (smType != null) {
			path = smType.getSchema().getLocation();
			type = getRdfType(smType);
		}
		if (path!= null && path.endsWith(".concept")) {
			path= path.replace(".concept", "");
			smType = XSDL.ANY_TYPE;
			isValid = true;
			type = "Object";
			return true;
		}
		if(path != null && !path.trim().isEmpty()){
			EObject bpmnIndex = BpmnCorePlugin.getDefault()
					.getBpmnModelManager().getBpmnIndex(fProject);
			ProcessAdapter processModel = new ProcessAdapter(
					process.getEInstance(), new ProcessOntologyAdapter(bpmnIndex));
			Iterator allProperties=processModel.getPropertyDefinitions().iterator();
	    	while (allProperties.hasNext()) {
	    		PropertyDefinition pd = (PropertyDefinition) allProperties.next();
	    		int pdType=pd.getType();
	    		if ((pdType == RDFTypes.CONCEPT_REFERENCE_TYPEID) || 
	    				pdType == RDFTypes.CONCEPT_TYPEID) {
	    			String name = pd.getName();
		    		String localName = xtype.getFirstChildComponent().getName().getLocalName();
		    		if(name.equalsIgnoreCase(localName)){
		    			path = pd.getConceptTypePath();
		    			smType = XSDL.ANY_TYPE;
		    			isValid = true;
		    			type = "Object";
		    			return true;
		    		}
	    		}
	    		
	    	}
		}
		if (smType != null) {
			if (smType == XSDL.INT || smType == XSDL.INTEGER
					|| smType == XSDL.LONG || smType == XSDL.DOUBLE
					|| smType == XSDL.STRING || smType == XSDL.BOOLEAN
					|| smType == XSDL.ANY_TYPE ||  smType == XSDL.DATETIME) {
				type =getRdfType(smType);
				isValid = true;
				return true;
			}
		}
		else {
			smType = getSchemaTypeForAttribute(xtype);
			if (smType != null) {
				if (smType == XSDL.INT || smType == XSDL.INTEGER
						|| smType == XSDL.DOUBLE || smType == XSDL.LONG
						|| smType == XSDL.STRING || smType == XSDL.BOOLEAN
						|| smType == XSDL.ANY_TYPE || smType == XSDL.DATETIME) {
					type = getRdfType(smType);
					isValid = true;
					return true;
				}
			}
		}

		return false;
	}
	
	private String getRdfType(SmType type){
		String name = type.getName();
		if(type == XSDL.DOUBLE)
			name = "double";
		else if(type == XSDL.INT)
			name = "int";
		else if(type == XSDL.INTEGER)
			name = "int";
		else if(type == XSDL.STRING)
		name = "String";
		else if(type == XSDL.LONG)
			name = "long";
		else if(type == XSDL.DATETIME)
			name = "DateTime";
		else if(type == XSDL.BOOLEAN)
			name = "boolean";
		
		return name;
	}
}
