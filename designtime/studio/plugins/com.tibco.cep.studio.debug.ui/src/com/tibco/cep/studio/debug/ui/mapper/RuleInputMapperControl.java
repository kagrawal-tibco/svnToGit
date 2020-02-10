package com.tibco.cep.studio.debug.ui.mapper;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.xsd.XSDTerm;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.manager.GlobalVariablesMananger;
import com.tibco.cep.studio.core.mapper.EntitySchemaCache;
import com.tibco.cep.studio.core.util.mapper.MapperXSDUtils;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.ui.views.MapperControl;
import com.tibco.cep.studio.debug.ui.views.NullDebugTarget;
import com.tibco.cep.studio.ui.util.ColorConstants;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.ui.xml.utils.MapperUtils;
import com.tibco.xml.mapperui.emfapi.EMapperFactory;
import com.tibco.xml.mapperui.emfapi.IEMapperControl;

/**
 * 
 * @author sasahoo
 *
 */
public class RuleInputMapperControl extends MapperControl {

	private RuleInputMapperInputOutputAdapter inputOutputAdapter;
	private IEMapperControl mapperControl;

	protected static EntitySchemaCache entitySchemaCache;
	protected RuleInputMapperInvocationContext context;
	protected String projectName = null;

	/**
	 * @param debugTarget
	 * @param parent
	 */
	public RuleInputMapperControl(IRuleRunTarget debugTarget, Composite parent, RuleInputMapperInvocationContext context) {
		super(debugTarget, parent);
		this.context = context;
		if (!(debugTarget instanceof NullDebugTarget)) {
			projectName = debugTarget.getBEProject().getName();
			entitySchemaCache = StudioCorePlugin.getSchemaCache(debugTarget.getBEProject().getName());
		}
		createControl();
	}

	@Override
	protected void createControl() {
		setFrameParent(new Composite(parent,SWT.FILL));
		GridLayout tablelayout = new GridLayout(1 , false) ;
		tablelayout.numColumns = 1;
		getFrameParent().setLayout(tablelayout) ;
		GridData gdtableComp = new GridData( GridData.BEGINNING | GridData.FILL_HORIZONTAL) ;
		gdtableComp.minimumHeight = 100 ;
		getFrameParent().setLayoutData(gdtableComp);
		getFrameParent().setBackground(ColorConstants.menuBackground );
		inputOutputAdapter = createMapperControl(getFrameParent());
		mapperControl = inputOutputAdapter.getMapperControl();
	}

	@Override
	public void updateMapperPanel( String xslt) {
		if(this.parent == null) {
			return;
		}

		if (context != null) {
			Entity entity   = IndexUtils.getEntity(getDebugTarget().getBEProject().getName(), entityURI);
			GlobalVariablesProvider provider = GlobalVariablesMananger.getInstance().getProvider(getDebugTarget().getBEProject().getName());
			XSDTerm gvarsTerm = MapperXSDUtils.convertGlobalVariables(provider, true);

			inputOutputAdapter.getMapperArgs().setXSLT(xslt);
			inputOutputAdapter.getMapperArgs().setTargetElement(entity);
			inputOutputAdapter.getMapperArgs().setSourceElement(gvarsTerm);
			inputOutputAdapter.getMapperArgs().setEntityUri(entityURI);
			inputOutputAdapter.getMapperArgs().setSourceSchema(getDebugTarget().getBEProject().getGlobalVariables().toSmElement()); 
			inputOutputAdapter.getMapperArgs().setTargetSchema(getEntitySchema()); 

			inputOutputAdapter.getMapperArgs().setInvocationContext(context);
			inputOutputAdapter.getMapperArgs().getInvocationContext().setXslt(xslt);
			inputOutputAdapter.setEntitySchemaCache(entitySchemaCache);
		}

		StudioUIUtils.invokeOnDisplayThread( new Runnable() {
			@Override
			public void run() {
				mapperControl.setInput(inputOutputAdapter);
			}
		}, false);

	}

	private  RuleInputMapperInputOutputAdapter createMapperControl(Composite parent) {
		RuleInputMapperControlArgs mEArgs = new RuleInputMapperControlArgs();
		//mEArgs.setExpandToShowAllMappingsOnStart(true);
		mEArgs.setAutoExpandSourceTreeLevel(1);
		mEArgs.setAutoExpandTargetTreeLevel(4);
		initArgs(mEArgs);
		
		MapperUtils.refreshFunctions();

		mapperControl = EMapperFactory.createMapperControl(parent, mEArgs);
		RuleInputMapperInputOutputAdapter input = new RuleInputMapperInputOutputAdapter(mapperControl, mEArgs);
		return input;
	}

	@SuppressWarnings("rawtypes")
	private  void initArgs(RuleInputMapperControlArgs mEArgs) {
		if (mEArgs.getXSLT() != null) { 
			List receivingParams = XSTemplateSerializer.getReceivingParms(mEArgs.getXSLT());
			if (receivingParams.size() == 1) {
				String entityPath = (String) receivingParams.get(0);
				Entity entity = IndexUtils.getEntity(getDebugTarget().getBEProject().getName(), entityPath);
				if ( entity != null )
					mEArgs.setTargetElement(entity);
			}
		}
	}

	public RuleInputMapperInputOutputAdapter getInputOutputAdapter() {
		return inputOutputAdapter;
	}

	public void setInputOutputAdapter(
			RuleInputMapperInputOutputAdapter inputOutputAdapter) {
		this.inputOutputAdapter = inputOutputAdapter;
	}

	@Override
	public void setVisible(boolean visible) {
		if (getFrameParent() != null && !getFrameParent().isDisposed()) {
			getFrameParent().setVisible(visible);
		}
	}

	@Override
	public boolean isVisible() {
		if (getFrameParent() != null && !getFrameParent().isDisposed()) {
			return getFrameParent().isVisible();
		}
		return true;
	}

	@Override
	protected String getXSLTString() {
		String xslt = inputOutputAdapter.getMapperArgs().getInvocationContext().getXslt();
		return  updateXpathVersion(xslt);
	}

	public String updateXpathVersion(String xslt) {
		if (xslt != null && projectName != null) {
			XPATH_VERSION xpathVersion = StudioProjectConfigurationManager.getInstance().
					getProjectConfiguration(projectName).getXpathVersion();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(new InputSource(new StringReader(xslt)));
				doc.getDocumentElement().normalize();

				NamedNodeMap nameNode = doc.getDocumentElement().getAttributes();
				String version = nameNode.getNamedItem("version").getTextContent();
				if (xpathVersion != XPATH_VERSION.get(version)) {
					nameNode.getNamedItem("version").setTextContent(xpathVersion.getLiteral());
					xslt = getStringFromDoc(doc);
				}
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return xslt;
	}

	public String getStringFromDoc(Document doc) {
		DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
		LSSerializer lsSerializer = domImplementation.createLSSerializer();
		return lsSerializer.writeToString(doc);   
	}

	@Override
	public void dispose() {
		if (getFrameParent() != null && !getFrameParent().isDisposed()) {
			getFrameParent().dispose();
		}
	}

	@Override
	public boolean isEnabled() {
		if (getFrameParent() != null && !getFrameParent().isDisposed()) {
			return getFrameParent().isEnabled();
		}
		return true;
	}

	@Override
	public void setEnabled(final boolean enabled) {
		if (getFrameParent() != null && !getFrameParent().isDisposed()) {
			getFrameParent().setEnabled(enabled);
		}
	}

}