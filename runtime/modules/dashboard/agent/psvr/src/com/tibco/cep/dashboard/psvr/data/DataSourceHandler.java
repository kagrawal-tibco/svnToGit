package com.tibco.cep.dashboard.psvr.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.common.query.QueryParams;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandler;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;

/**
 * @author anpatil
 */
public abstract class DataSourceHandler extends AbstractHandler {
	
	protected final String id = UUID.randomUUID().toString();
	
	protected String name;
	
	protected String uniqueName;
	
	protected String query;
	
	protected QueryParams queryParams;
	
	protected Threshold threshold;
	
	protected String owner;
	
	protected MALSourceElement sourceElement;
	
	Set<String> references;
	
	private Set<DataChangeListener> listeners;
	
    protected DataSourceHandler() {
		references = new HashSet<String>();
		listeners = new HashSet<DataChangeListener>();
	}
    
    public String getId(){
    	return id;
    }
    
	public final String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	public final String getUniqueName() {
		return uniqueName;
	}

	void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	void setQueryParams(QueryParams queryParams){
		this.queryParams = queryParams;
	}
	
	public final QueryParams getQueryParams(){
		return this.queryParams;
	}
	
	protected void setQuery(String query){
		this.query = query;
	}
	
	public final String getQuery(){
		return query;
	}
	
	protected String getBindedQuery() {
		return query;
	}
	
	protected void setThreshold(Threshold threshold){
		this.threshold = threshold;
	}
	
	public final Threshold getThreshold(){
		return threshold;
	}
	
	@Override
	protected final void init() {
		//do nothing
	}
	
	protected abstract void configure(MALSeriesConfig seriesConfig,PresentationContext pCtx) throws DataException;
	
	public boolean isDataSourceOwnedBy(String user){
		return user.equalsIgnoreCase(owner);
	}
	
	public abstract List<Tuple> getData(PresentationContext ctx) throws DataException;
	
	public boolean dereferenced(String referencer){
		return references.remove(referencer);
	}

	public boolean referenced(String referencer){
		return references.add(referencer);
	}
	
    public int getReferencedCount(){
    	return references.size();
    }	

	public boolean addDataChangeListener(DataChangeListener dataChangeListener){
		return listeners.add(dataChangeListener);
	}
	
	public boolean removeDataChangeListener(DataChangeListener dataChangeListener){
		return listeners.remove(dataChangeListener);
	}
	
	protected void fireThresholdApplied(){
		String uniqueName = getUniqueName();
		for (DataChangeListener listener : listeners) {
			listener.thresholdApplied(uniqueName);
		}
	}
	
	protected void fireRefreshed(){
		String uniqueName = getUniqueName();
		for (DataChangeListener listener : listeners) {
			listener.refreshed(uniqueName);
		}
	}

	protected abstract void shutdown() throws NonFatalException;

	public final MALSourceElement getSourceElement() {
		return sourceElement;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getClass().getSimpleName());
		builder.append("[");
		builder.append("id=");
		builder.append(id);
		builder.append(",uniquename=");
		builder.append(uniqueName);
		builder.append(",name=");
		builder.append(name);
		builder.append(",references=");
		builder.append(references);
		builder.append("]");
		return builder.toString();
	}
	
	//Not sure if we need context?
//	public final Context getContext(){
//		return context;
//	}
	
	
	
}