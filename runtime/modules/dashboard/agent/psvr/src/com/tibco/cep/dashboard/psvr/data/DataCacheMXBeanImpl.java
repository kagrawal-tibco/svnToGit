package com.tibco.cep.dashboard.psvr.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.cep.dashboard.common.utils.StringUtil;

public class DataCacheMXBeanImpl implements DataCacheMXBean {
	
	@Override
	public int getDataSourceHandlerCount() {
		return DataSourceHandlerCache.getInstance().handlers().size();
	}

	@Override
	public DataSourceHandlerRuntimeInfo[] getDataSourceHandlers() {
		Collection<DataSourceHandler> handlers = DataSourceHandlerCache.getInstance().handlers();
		DataSourceHandlerRuntimeInfo[] handlerInfos = new DataSourceHandlerRuntimeInfo[handlers.size()];
		int i = 0;
		for (DataSourceHandler handler : handlers) {
			DataSourceHandlerRuntimeInfo runtimeInfo = getRuntimeInfo(handler);
			handlerInfos[i] = runtimeInfo;
			i++;
		}
		return handlerInfos;
	}

	@Override
	public DataSourceHandlerRuntimeInfo searchDataSourceHandlersById(String id) {
		if (StringUtil.isEmptyOrBlank(id) == true){
			return null;
		}		
		Collection<DataSourceHandler> handlers = DataSourceHandlerCache.getInstance().handlers();
		for (DataSourceHandler handler : handlers) {
			if (handler.id.equals(id) == true){
				return getRuntimeInfo(handler);
			}
		}	
		return null;
	}
	

	@Override
	public DataSourceHandlerRuntimeInfo[] searchDataSourceHandlersByReferencer(String referencerPattern) {
		if (StringUtil.isEmptyOrBlank(referencerPattern) == true){
			return new DataSourceHandlerRuntimeInfo[0];
		}
		referencerPattern = referencerPattern.toLowerCase();
		boolean endsWith = referencerPattern.startsWith("*");
		if (endsWith == true){
			referencerPattern = referencerPattern.substring(1);
		}
		boolean beginsWith = referencerPattern.endsWith("*");
		if (beginsWith == true){
			referencerPattern = referencerPattern.substring(0,referencerPattern.length()-1);
		}
		System.err.println("referencerPattern = "+referencerPattern+",beginsWith="+beginsWith+",endsWith="+endsWith);
		List<DataSourceHandlerRuntimeInfo> handlerInfos = new ArrayList<DataSourceHandlerRuntimeInfo>();
		Collection<DataSourceHandler> handlers = DataSourceHandlerCache.getInstance().handlers();
		for (DataSourceHandler handler : handlers) {
			for (String referencer : handler.references) {
				referencer = referencer.toLowerCase();
				if (beginsWith == true && endsWith == true && referencer.contains(referencerPattern) == true){
					System.err.println("contains "+referencer);
					handlerInfos.add(getRuntimeInfo(handler));	
				}
				else if (beginsWith == true && referencer.startsWith(referencerPattern) == true){
					System.err.println("starts with "+referencer);
					handlerInfos.add(getRuntimeInfo(handler));	
				}
				else if (endsWith == true && referencer.endsWith(referencerPattern) == true){
					System.err.println("ends with "+referencer);
					handlerInfos.add(getRuntimeInfo(handler));	
				}
				else if (referencer.equals(referencerPattern) == true){
					System.err.println("exactly matches "+referencer);
					handlerInfos.add(getRuntimeInfo(handler));
				}
			}
		}	
		return handlerInfos.toArray(new DataSourceHandlerRuntimeInfo[handlerInfos.size()]);		
	}

	@Override
	public DataSourceHandlerRuntimeInfo[] searchDataSourceHandlersBySource(String sourcePattern) {
		if (StringUtil.isEmptyOrBlank(sourcePattern) == true){
			return new DataSourceHandlerRuntimeInfo[0];
		}
		sourcePattern = sourcePattern.toLowerCase();
		boolean endsWith = sourcePattern.startsWith("*");
		if (endsWith == true){
			sourcePattern = sourcePattern.substring(1);
		}
		boolean beginsWith = sourcePattern.endsWith("*");
		if (beginsWith == true){
			sourcePattern = sourcePattern.substring(0,sourcePattern.length()-1);
		}
		List<DataSourceHandlerRuntimeInfo> handlerInfos = new ArrayList<DataSourceHandlerRuntimeInfo>();
		Collection<DataSourceHandler> handlers = DataSourceHandlerCache.getInstance().handlers();
		for (DataSourceHandler handler : handlers) {
			String sourceName = handler.getSourceElement().getName().toLowerCase();
			if (beginsWith == true && endsWith == true && sourceName.contains(sourcePattern) == true){
				handlerInfos.add(getRuntimeInfo(handler));	
			}
			else if (beginsWith == true && sourceName.startsWith(sourcePattern) == true){
				handlerInfos.add(getRuntimeInfo(handler));	
			}
			else if (endsWith == true && sourceName.endsWith(sourcePattern) == true){
				handlerInfos.add(getRuntimeInfo(handler));	
			}
			else if (sourceName.equals(sourcePattern) == true){
				handlerInfos.add(getRuntimeInfo(handler));
			}
		}	
		return handlerInfos.toArray(new DataSourceHandlerRuntimeInfo[handlerInfos.size()]);			
	}
	
	private DataSourceHandlerRuntimeInfo getRuntimeInfo(DataSourceHandler handler) {
		DataSourceHandlerRuntimeInfo runtimeInfo = new DataSourceHandlerRuntimeInfo();
		runtimeInfo.setId(handler.id);
		runtimeInfo.setName(handler.name);
		runtimeInfo.setUniqueName(handler.uniqueName);
		runtimeInfo.setOwner(handler.owner);
		runtimeInfo.setSource(handler.sourceElement.getName());
		runtimeInfo.setQuery(handler.query);
		runtimeInfo.setQueryParams(handler.queryParams.toString());
		runtimeInfo.setBindedQuery(handler.getBindedQuery());
		runtimeInfo.setThreshold(handler.threshold.toString());
		runtimeInfo.setReferencers(handler.references.toArray(new String[handler.references.size()]));
		return runtimeInfo;
	}	
}
