package com.tibco.cep.dashboard.integration.embedded;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * The <code>SeriesData</code> represents the data for one series. The series is
 * assumed to be either a chartseries or a textseries. The id in the seriesdata
 * should match the id of one seriesconfig in the accompanying component
 *  
 * @author apatil
 */
public final class ESeriesData {
    
    private String seriesID;
    private String seriesName;
    
    private LinkedHashMap<Comparable<?>, Number> actualData;
    
    private LinkedList<Comparable<?>> categoryValues;
    
    /**
     * Creates a <code>SeriesData</code> for a specific series config
     * @param seriesID The id of the series config being represented
     * @param seriesName The name of the series config being represented
     */
    public ESeriesData(String seriesID, String seriesName) {
        this.seriesID = seriesID;
        this.seriesName = seriesName;
        this.actualData = new LinkedHashMap<Comparable<?>, Number>();
        this.categoryValues = new LinkedList<Comparable<?>>();
    }

    /**
     * Returns the id of the series config being represented
     * @return The id of the series config being represented
     */
    public String getSeriesID() {
        return seriesID;
    }

    /**
     * Returns the name of the series config being represented
     * @return The name of the series config being represented
     */
    public String getSeriesName() {
        return seriesName;
    }
    
    /**
     * Adds a new data point. If a data point exists with the same categoryvalue,
     * then it is overwritten
     * @param categoryValue The value shown on the category axis
     * @param value The value shown on value axis
     */
    public void add(Comparable<?> categoryValue,Number value){
       this.actualData.put(categoryValue, value); 
       this.categoryValues.add(categoryValue);
    }
    
    /**
     * Removes a specific data point 
     * @param categoryValue The value shown on the category axis
     */
    public void remove(Comparable<?> categoryValue){
        this.actualData.remove(categoryValue); 
        this.categoryValues.remove(categoryValue);
    }

    /**
     * Returns an <code>iterator</code> of all the existing category values
     * @return An <code>iterator</code> of all the existing category values
     */
    public Iterator<Comparable<?>> getCategoryValues(){
        return this.actualData.keySet().iterator();
    }

    /**
     * Returns a value based on the categoryvalue. 
     * @param categoryValue The category value whose value is needed 
     * @return a <code>Number</code> if category value is found, else <code>null</code>
     */
    public Number getValue(Comparable<?> categoryValue){
        return (Number) this.actualData.get(categoryValue);
    }
    
    /**
     * Returns the number of data points in the <code>SeriesData</code>
     * @return The number of data points in the <code>SeriesData</code>
     */
    public int getDataCount(){
        return actualData.size();
    }
    
    public Comparable<?> getFirst(){
        return this.categoryValues.getFirst();
    }
    
    public Comparable<?> getLast(){
        return this.categoryValues.getLast();
    }
    
    
    public String toString() {
        return actualData.toString();
    }
}