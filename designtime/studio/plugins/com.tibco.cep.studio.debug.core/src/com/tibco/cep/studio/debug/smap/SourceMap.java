package com.tibco.cep.studio.debug.smap;

import java.util.Map;
import java.util.TreeMap;

/*
@author ssailapp
@date Jul 30, 2009 4:00:52 PM
 */

public class SourceMap {

    String source;      
    String target;

    private TreeMap<Integer,Integer> lineMap = new TreeMap<Integer,Integer>();


    public SourceMap(String source, String target) {
        this.source = source;
        this.target = target;
    }

    public String getSource() {
        return this.source;
    }

    public String getTarget() {
        return this.target;
    }

    /**
     * Map the lines from source vector to target vector. The map data is form
     * i1 : m1..mk or i : m (most case) or i1..in : mk 
     * The boolean flag tells which of the following (i or m) to use as keys. If true use i, or else use m
     * @param mapData
     * @param forward
     */
    public void mapLine(String mapData, boolean forward) {
        String[] maps = mapData.split(":");

        //Lets take of the simple case
        if (forward) {
            lineMap.put(Integer.parseInt(maps[0]), Integer.parseInt(maps[1]));
        }
        else {
            lineMap.put(Integer.parseInt(maps[1]), Integer.parseInt(maps[0]));            
        }
    }

    public TreeMap<Integer, Integer> getLineMap() {
        return lineMap;
    }

    public Map.Entry<Integer,Integer> getFirstLineEntry() {
        for(Map.Entry<Integer,Integer> es: lineMap.entrySet()) {
           if(es.getKey().equals(lineMap.firstKey())) {
               return  es;
           }
        }
        return null;
    }

     public Map.Entry<Integer,Integer> getLastLineEntry() {
        for(Map.Entry<Integer,Integer> es: lineMap.entrySet()) {
           if(es.getKey().equals(lineMap.lastKey())) {
               return es;
           }
        }
        return null;
    }
}
