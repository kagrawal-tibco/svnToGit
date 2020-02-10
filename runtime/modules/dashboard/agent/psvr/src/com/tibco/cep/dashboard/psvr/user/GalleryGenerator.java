package com.tibco.cep.dashboard.psvr.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.mal.MALComponentGallery;
import com.tibco.cep.dashboard.psvr.mal.MALComponentGalleryCategory;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALFlowLayoutConstraint;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.mal.model.MALPanel;
import com.tibco.cep.dashboard.psvr.mal.model.MALPartition;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentCategory;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentGallery;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentSize;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentDefinition;
import com.tibco.cep.dashboard.psvr.ogl.model.types.BooleanOptions;
import com.tibco.cep.dashboard.security.SecurityToken;

/**
 * @author apatil
 *
 */
public class GalleryGenerator {

	private SecurityToken token;
	private ViewsConfigHelper viewsConfigHelper;
	private MALComponentGallery componentGalleryWrapper;

    private Map<String,GalleryXMLHolder> generatedXMLIndexMap;

    GalleryGenerator(SecurityToken token,ViewsConfigHelper viewsConfigHelper,MALComponentGallery malComponentGallery) {
    	this.token = token;
    	this.viewsConfigHelper = viewsConfigHelper;
    	this.componentGalleryWrapper = malComponentGallery;
    	generatedXMLIndexMap = new HashMap<String,GalleryXMLHolder>();
    }

    public String generate() throws OGLException{
    	String currentPageID = viewsConfigHelper.getCurrentPage().getId();
    	GalleryXMLHolder galleryXMLHolder = generatedXMLIndexMap.get(currentPageID);
    	if (galleryXMLHolder == null){
    		Map<String, ComponentDefinition> collapsedCompCfgObjs = new HashMap<String, ComponentDefinition>();
    		ComponentGallery componentGallery = generateComponentGallery(collapsedCompCfgObjs);
    		galleryXMLHolder = new GalleryXMLHolder();
    		galleryXMLHolder.lastGalleryModifiedTime = componentGalleryWrapper.getLastModifiedTime();
    		galleryXMLHolder.lastViewsConfigModifiedTime = viewsConfigHelper.getLastModifiedTime();
    		galleryXMLHolder.oglComponentGallery = componentGallery;
    		galleryXMLHolder.oglGalleryCompCfgMap = collapsedCompCfgObjs;
    		computeMetaInfo(galleryXMLHolder.oglGalleryCompCfgMap);
    		galleryXMLHolder.galleryXML = OGLMarshaller.getInstance().marshall(token, componentGallery);
    		generatedXMLIndexMap.put(currentPageID,galleryXMLHolder);
    	}
    	else {
    		if (componentGalleryWrapper.getLastModifiedTime() > galleryXMLHolder.lastGalleryModifiedTime){
        		Map<String, ComponentDefinition> collapsedCompCfgObjs = new HashMap<String, ComponentDefinition>();
        		ComponentGallery componentGallery = generateComponentGallery(collapsedCompCfgObjs);
        		galleryXMLHolder.lastGalleryModifiedTime = componentGalleryWrapper.getLastModifiedTime();
        		galleryXMLHolder.lastViewsConfigModifiedTime = viewsConfigHelper.getLastModifiedTime();
        		galleryXMLHolder.oglComponentGallery = componentGallery;
        		galleryXMLHolder.oglGalleryCompCfgMap = collapsedCompCfgObjs;
        		computeMetaInfo(galleryXMLHolder.oglGalleryCompCfgMap);
        		galleryXMLHolder.galleryXML = OGLMarshaller.getInstance().marshall(token, componentGallery);
    		}
    		else if (viewsConfigHelper.getLastModifiedTime() > galleryXMLHolder.lastViewsConfigModifiedTime){
                computeMetaInfo(galleryXMLHolder.oglGalleryCompCfgMap);
                galleryXMLHolder.galleryXML = OGLMarshaller.getInstance().marshall(token, galleryXMLHolder.oglComponentGallery);
                galleryXMLHolder.lastViewsConfigModifiedTime = viewsConfigHelper.getLastModifiedTime();
    		}
    	}
    	return galleryXMLHolder.galleryXML;
    }

    void destroy(){
		for (GalleryXMLHolder galleryXMLHolder : generatedXMLIndexMap.values()) {
			galleryXMLHolder.oglGalleryCompCfgMap.clear();
			galleryXMLHolder.galleryXML = null;
			galleryXMLHolder.oglComponentGallery = null;
		}
		generatedXMLIndexMap.clear();
		this.componentGalleryWrapper = null;
		this.viewsConfigHelper = null;
		this.token = null;
    }

    private ComponentGallery generateComponentGallery(Map<String, ComponentDefinition> collapsedCompCfgObjs) {
        MALPanel[] metricPanels = getMetricViewPanels(new MALPage[]{viewsConfigHelper.getCurrentPage()});
        ComponentGallery gallery = new ComponentGallery();
        Iterator<MALComponentGalleryCategory> topLevelCategoryIter = componentGalleryWrapper.getTopLevelCategories();
        while (topLevelCategoryIter.hasNext()) {
            MALComponentGalleryCategory malCategory = topLevelCategoryIter.next();
            ComponentCategory category = generateComponentCategory(metricPanels,"ROOT",malCategory,collapsedCompCfgObjs);
            gallery.addComponentCategory(category);
        }
        return gallery;
    }

    /**
     * @param malCategory
     * @return
     * @throws IOException
     * @throws NonFatalException
     */
    private ComponentCategory generateComponentCategory(MALPanel[] metricPanels,String parentCategoryID, MALComponentGalleryCategory malCategory,Map<String,ComponentDefinition> collapsedComponentsCfgMap) {
        ComponentCategory category = new ComponentCategory();
        category.setId(malCategory.getId());
        category.setName(malCategory.getName());
        category.setParentID(parentCategoryID);
        Iterator<MALComponent> componentsIter = malCategory.getComponents();
        while (componentsIter.hasNext()) {
            MALComponent malComponent = componentsIter.next();
            ComponentDefinition compCfg = collapsedComponentsCfgMap.get(malComponent.getId());
            if (compCfg == null){
            	compCfg = new ComponentDefinition();
                compCfg.setId(malComponent.getId());
                compCfg.setTitle(malComponent.getDisplayName());
                compCfg.setName(malComponent.getName());
                compCfg.setType(malComponent.getDefinitionType());
                if (malComponent.getLayoutConstraint() != null){
                    MALFlowLayoutConstraint layoutConstraints = (MALFlowLayoutConstraint) malComponent.getLayoutConstraint();
                    ComponentSize componentSize = new ComponentSize();
                    int rowSpan = layoutConstraints.getComponentRowSpan();
                    int colSpan = layoutConstraints.getComponentColSpan();
                    componentSize.setHeight(rowSpan*viewsConfigHelper.getComponentHeightUnit(malComponent.getDefinitionType()));
                    componentSize.setWidth(colSpan*viewsConfigHelper.getComponentWidthUnit(malComponent.getDefinitionType()));
                    compCfg.setPrefferredSize(componentSize);
                }
                collapsedComponentsCfgMap.put(malComponent.getId(),compCfg);
            }
            category.addComponentDefinition(compCfg);
        }
        category.setCount(category.getComponentDefinitionCount());
        Iterator<MALComponentGalleryCategory> categoryIter = malCategory.getCategories();
        while (categoryIter.hasNext()) {
            MALComponentGalleryCategory childCategory = categoryIter.next();
            category.addComponentCategory(generateComponentCategory(metricPanels,malCategory.getId(),childCategory,collapsedComponentsCfgMap));
        }
        return category;
    }

    private void computeMetaInfo(Map<String,ComponentDefinition> collapsedComponentsMap) {
    	MALPanel[] metricViewPanels = getMetricViewPanels(new MALPage[]{viewsConfigHelper.getCurrentPage()});
        Iterator<ComponentDefinition> compCfgsIter = collapsedComponentsMap.values().iterator();
        while (compCfgsIter.hasNext()) {
            ComponentDefinition compCfg = compCfgsIter.next();
			computeMetaInfo(metricViewPanels,compCfg);
        }
    }

    private void computeMetaInfo(MALPanel[] metricViewPanels, ComponentDefinition compCfg) {
    	//MALComponent component = componentGalleryWrapper.searchComponent(compCfg.getId());
    	//a component is deletable if it is owned by the user and is not in use in the entire view config
    	//PORT in 4.0 release isDeletable is always false;
        boolean isDeletable = false;//component.isOwnedBy(token) && viewsConfigHelper.getComponentById(compCfg.getId()) != null;
        boolean isDisabled = false;
        int fndCnt = 0;
        for (int i = 0; i < metricViewPanels.length; i++) {
            MALPanel metricPanel = metricViewPanels[i];
            for (MALComponent componentInPnl : metricPanel.getComponent()) {
                if (componentInPnl.getId().equals(compCfg.getId()) == true){
                    fndCnt++;
                    break;
                }
			}
        }
        //is the component in all the metric panels shown
        if (fndCnt == metricViewPanels.length){
            //yes it is, then the component is disabled
            isDisabled = true;
        }
        if (isDeletable == true){
            compCfg.setDeletable(BooleanOptions.TRUE);
        }
        else{
            //we maintain the same model , so we need to set the flag to null to remove a "true" state
            compCfg.setDeletable(null);
        }
        if (isDisabled == true){
            compCfg.setDisabled(BooleanOptions.TRUE);
        }
        else{
            //we maintain the same model , so we need to set the flag to null to remove a "true" state
            compCfg.setDisabled(null);
        }
    }

	private MALPanel[] getMetricViewPanels(MALPage[] dashboardPages) {
        List<MALPanel> metricViewPanels = new ArrayList<MALPanel>();
        for (int i = 0; i < dashboardPages.length; i++) {
            MALPage dashboardPage = dashboardPages[i];
            MALPartition[] partitions = dashboardPage.getPartition();
            for (int j = 0; j < partitions.length; j++) {
                MALPartition partition = partitions[j];
                MALPanel[] panels = partition.getPanel();
                for (int k = 0; k < panels.length; k++) {
                    MALPanel panel = panels[k];
                    //DOUBT check the code for zero components in the metric panel
                    if (panel.getLayout() != null){
                        metricViewPanels.add(panel);
                        break;
                    }
                }
            }
        }
        return metricViewPanels.toArray(new MALPanel[metricViewPanels.size()]);
    }

    class GalleryXMLHolder {
    	long lastGalleryModifiedTime;
    	ComponentGallery oglComponentGallery;
    	long lastViewsConfigModifiedTime;
    	Map<String, ComponentDefinition> oglGalleryCompCfgMap;
    	String galleryXML;
    }
}