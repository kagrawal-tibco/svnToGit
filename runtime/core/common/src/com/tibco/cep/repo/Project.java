package com.tibco.cep.repo;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.objectrepo.vfile.VFileFactory;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmElement;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 18, 2006
 * Time: 11:42:04 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Project {
    int ENTITY_ADDED = 0;
    int ENTITY_UPDATE = 1;
    int ENTITY_DELETE = 2;
    int ENTITY_RENAME = 3;
    int ENTITY_MOVED  = 4;

    String getName();

    Ontology getOntology();

    GlobalVariables getGlobalVariables();

    <T extends BETargetNamespaceCache> T getTnsCache();

    VFileFactory getVFileFactory();

    void load() throws Exception;

    void close();

    SmElement getSmElement(Entity e) ;

    ResourceProviderFactory getProviderFactory();

    SmElement getSmElement(ExpandedName name) ;

    boolean isValidDesignerProject();
}
