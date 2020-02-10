package com.tibco.cep.bpmn.core.doc.concept;

import static com.tibco.cep.studio.core.doc.DocumentationConstants.TEMPLATES_DIRECTORY;
import static com.tibco.cep.bpmn.core.doc.concept.ConceptDocumentationConstants.TEMPLATE_GROUP_FILENAME;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.doc.DocumentDataBean;
import com.tibco.cep.bpmn.core.doc.DocumentationBeanUtils;
import com.tibco.cep.studio.core.doc.AbstractDocumentationGenerator;
import com.tibco.cep.studio.core.doc.Callback;
import com.tibco.cep.studio.core.doc.ClassElement;
import com.tibco.cep.studio.core.doc.PackageElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * 
 * @author moshaikh
 * 
 */
public class ConceptDocumentationGenerator extends
		AbstractDocumentationGenerator {

	public ConceptDocumentationGenerator() {
		super();
	}

	@Override
	public StringTemplateGroup getStringTemplateGroup() {
		InputStream is = getClass().getClassLoader().getResourceAsStream(
				TEMPLATES_DIRECTORY + "/" + TEMPLATE_GROUP_FILENAME);
		InputStreamReader reader = new InputStreamReader(is);
		return new StringTemplateGroup(reader, DefaultTemplateLexer.class);
	}

	@Override
	public boolean generateDocumentation(Callback callback) {
		try {
			for (DocumentDataBean dataBean : getAllConceptBeans()) {
				callback.writeFile(dataBean.getTemplateName(), dataBean, dataBean.getDocFilePath());
			}
			return true;
		} catch (Exception e) {
			BpmnCorePlugin.log(e);
			return false;
		}
	}

	private List<DocumentDataBean> getAllConceptBeans() {
		List<DocumentDataBean> dataBeans = new ArrayList<DocumentDataBean>();
		
		List<DesignerElement> concepts = CommonIndexUtils.getAllElements(desc.project.getName(), ELEMENT_TYPES.CONCEPT);
		for (DesignerElement concept : concepts) {
			dataBeans.add(DocumentationBeanUtils.createConceptBean(concept, desc));
		}
		return dataBeans;
	}

	@Override
	public ArrayList<PackageElement> getPackageElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ClassElement> getClassElements() {
		// TODO Auto-generated method stub
		return null;
	}
}
