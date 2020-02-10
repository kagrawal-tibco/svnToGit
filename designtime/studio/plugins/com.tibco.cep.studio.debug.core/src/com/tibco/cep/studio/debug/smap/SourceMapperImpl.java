package com.tibco.cep.studio.debug.smap;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerException;

import org.eclipse.debug.core.model.IDebugTarget;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.tibco.cep.repo.Project;
import com.tibco.cep.studio.debug.core.model.AbstractDebugTarget;
import com.tibco.cep.studio.debug.core.model.impl.IMappedResourcePosition;
import com.tibco.cep.studio.debug.core.model.impl.MappedResourcePosition;

/*
@author ssailapp
@date Jul 30, 2009 3:59:43 PM
 */

/**
 * This class provides the Source Mapping Table for sources
 */
public class SourceMapperImpl implements SourceMapper {

    AbstractDebugTarget target;
    Project project;
    HashMap<String, SourceMap> sourceMapTable;
    
    
    public SourceMapperImpl(IDebugTarget target) {
        this.target = (AbstractDebugTarget) target;
        sourceMapTable = new HashMap<String, SourceMap>();
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.studio.debug.smap.SourceMapper#init()
     */
    public void init() throws Exception {
        this.project = target.getBEProject();
        Collection<String> col = target.getSMapResourceProvider().getAllResourceURI();
        for (Object o : col) {
            String uri = o.toString();
            byte[] buf = target.getSMapResourceProvider().getResourceAsByteArray(uri);
            parseSMap(buf);
        }

    }
    
    public void init(List<byte[]> smapList) throws Exception {
    	for (byte[] bs : smapList) {
			parseSMap(bs);
		}
    }
    
    @Override
    public boolean isEmpty() {
    	return sourceMapTable.isEmpty();
    }


    void parseSMap(byte[] smdata) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        SourceMapContentHandler smapHandler = new SourceMapContentHandler(this);
        SAXParserFactory pf = SAXParserFactory.newInstance();
        pf.setValidating(false);
        pf.setNamespaceAware(false);
        SAXParser parser = pf.newSAXParser();
        parser.parse(new ByteArrayInputStream(smdata), smapHandler);

    }


    public String getEntityName(String className) {
        SourceMap map = sourceMapTable.get(className);
        if (map != null) return map.getTarget();
        return null;
    }

    public IMappedResourcePosition getJavaPosition(String entityName, int beEntityPosition) {
        return getCrossMappedPosition(entityName, beEntityPosition, MappedResourcePosition.JAVA_POSITION);
    }

    public IMappedResourcePosition getBEPosition(String javaName, int javaPosition) {
        return getCrossMappedPosition(javaName, javaPosition, MappedResourcePosition.BE_POSITION);
    }

    private IMappedResourcePosition getCrossMappedPosition(String sourceResourceName, int sourceResourceLine, int positionType) {
        MappedResourcePosition  targetPosition = null;
        MappedResourcePosition  sourcePosition = null;
        SourceMap sourceMap = sourceMapTable.get(sourceResourceName);
        if(sourceMap != null) {
            if(sourceMap.getLineMap().size() == 0) {
                return null;
            }
            // check bounds
            if(sourceResourceLine < sourceMap.getLineMap().firstKey() || sourceResourceLine > sourceMap.getLineMap().lastKey()) {
                return targetPosition;
            }
            int sourceLineMask = getLineMask(sourceResourceLine, sourceMap);
            
            int firstKey = sourceMap.getLineMap().tailMap(Integer.valueOf(sourceResourceLine)).firstKey(); //TODO - index problem in rule document
            int targetLine = sourceMap.getLineMap().get(firstKey);
//            targetPosition = new MappedResourcePosition(targetLine,sourceMap.getTarget(), positionType);


            SourceMap reverseMap = null;
            for(Iterator<SourceMap> it = sourceMapTable.values().iterator();it.hasNext();){
                SourceMap smap = it.next();
                if(smap.getTarget().equals(sourceResourceName)){
                    if(smap.getLineMap().get(targetLine) != null) {
                        reverseMap = smap;
                        break;
                    }
                }
            }
            if(reverseMap != null) {
                targetPosition = new MappedResourcePosition(targetLine,reverseMap.getSource(), positionType);
            } else {
                targetPosition = new MappedResourcePosition(targetLine,sourceMap.getTarget(), positionType);
            }
            if(reverseMap != null) {
                int sourceLine = reverseMap.getLineMap().get(targetLine);
                int targetLineMask = getLineMask(targetLine,reverseMap);
                sourcePosition = new MappedResourcePosition(sourceLine,reverseMap.getTarget(),
                        positionType == MappedResourcePosition.BE_POSITION ? MappedResourcePosition.JAVA_POSITION : MappedResourcePosition.BE_POSITION);
                targetPosition.setLineMask(targetLineMask);
                sourcePosition.setLineMask(sourceLineMask);
                targetPosition.setCrossMappedPosition(sourcePosition);
                sourcePosition.setCrossMappedPosition(targetPosition);
            }

//            return line;
        }
        return targetPosition;
    }

    private int getLineMask(int inputResourceLine, SourceMap smap) {
        int lineMask = 0;
        int firstMapLine = smap.getLineMap().firstKey();
        int lastMapLine = smap.getLineMap().lastKey();
        lineMask |= (inputResourceLine == firstMapLine) ? MappedResourcePosition.FIRST_LINE : lineMask;
        lineMask |= (inputResourceLine == lastMapLine) ? MappedResourcePosition.LAST_LINE : lineMask;
        lineMask |= (inputResourceLine > firstMapLine && inputResourceLine < lastMapLine) ? MappedResourcePosition.MIDDLE_LINE : lineMask;
        lineMask |= (firstMapLine == lastMapLine) ? MappedResourcePosition.MIDDLE_LINE : lineMask;
        return lineMask;
    }


    public List<SourceMap> getJavaMaps(String clazzName) {
        List<SourceMap> list = new ArrayList<SourceMap>();
        for (Iterator<SourceMap> it = sourceMapTable.values().iterator(); it.hasNext();) {
            SourceMap smap = it.next();
            if (smap.getTarget().equals(clazzName)) {
                list.add(smap);
            }
        }
        return list;
    }

    public List<SourceMap> getEntityMaps(String entityName) {
        List<SourceMap> list = new ArrayList<SourceMap>();
        for (Iterator<SourceMap> it = sourceMapTable.values().iterator(); it.hasNext();) {
            SourceMap smap = it.next();
            if (smap.getSource().equals(entityName)) {
                list.add(smap);
            }
        }
        return list;
    }

    public String getJavaName(String entityName) {
        SourceMap map = sourceMapTable.get(entityName);
        if (map != null) return map.getTarget();
        return null;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public String[] getJavaNames(String entityName) {
    	ArrayList<String> names = new ArrayList();
    	for(Entry<String, SourceMap> entry: sourceMapTable.entrySet()) {
    		SourceMap map = entry.getValue();
    		if(map.getTarget().equals(entityName)) {
    			names.add(map.source);
    		}
    	}
    	
    	return names.toArray(new String[names.size()]);
    }
    


	class SourceMapContentHandler extends DefaultHandler {
        SourceMapperImpl smapperImpl;

        SourceMap src2targetMap = null;
        SourceMap target2srcMap = null;

        SourceMapContentHandler (SourceMapperImpl smapperImpl) {
            this.smapperImpl = smapperImpl;
        }

        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if ("smap".equals(qName)) {
                String srcValue = attributes.getValue("src");
                String codeValue = attributes.getValue("code");

                if (smapperImpl.sourceMapTable.containsKey(srcValue)) {
                    src2targetMap = smapperImpl.sourceMapTable.get(srcValue);
                } else {
                    src2targetMap = new SourceMap(srcValue, codeValue);
                    smapperImpl.sourceMapTable.put(srcValue, src2targetMap);
                }
                if (smapperImpl.sourceMapTable.containsKey(codeValue)) {
                    target2srcMap = smapperImpl.sourceMapTable.get(codeValue);
                } else {
                    target2srcMap = new SourceMap(codeValue, srcValue);
                    smapperImpl.sourceMapTable.put(codeValue, target2srcMap);
                }
            }
            else if ("map".equals(qName)) {
                String lineMap = attributes.getValue("line");
                src2targetMap.mapLine(lineMap, true); //forward mapping
                target2srcMap.mapLine(lineMap, false); //do the reverse mapping also
            }

        }
    }
}

