package com.tibco.cep.bpmn.ui.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;

import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;

public class GraphSelection implements IGraphSelection,IAdaptable {
	
	List<TSEObject> list = new ArrayList<TSEObject>();
	
	public GraphSelection() {		
	}

	public <T extends TSEObject> GraphSelection(T o) {
		add(o);
	}
	
	public <T extends TSEObject> GraphSelection(Collection<T> objs) {
		addAll(objs);
	}
	
	public <T extends TSEObject> void add(T o) {
		list.add(o);
	}
	public <T extends TSEObject> void addAll(Collection<T> o) {
		list.addAll(o);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		if(adapter == IGraphSelection.class) {
			return this;
		}
		return null;
	}

	@Override
	public TSEObject getGraphObject() {
		return list.get(0);
	}
	
	@Override
	public boolean isMultiple() {
		return list.size() > 0;
	}
	
	@Override
	public List<TSEObject> toList() {
		return list;
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	@Override
	public boolean hasEdgeSelection() {
		for(TSEObject o:list) {
			if(o instanceof TSEdge){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean hasGraphSelection() {
		for(TSEObject o:list) {
			if(o instanceof TSEGraph){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean hasNodeSelection() {
		for(TSEObject o:list) {
			if(o instanceof TSENode){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<TSEdge> getSelectedEdges() {
		List<TSEdge> slist = new ArrayList<TSEdge>();
		for(TSEObject o:list) {
			if(o instanceof TSEdge){
				slist.add((TSEdge) o);
			}
		}
		return slist;
	}
	
	@Override
	public List<TSENode> getSelectedNodes() {
		List<TSENode> slist = new ArrayList<TSENode>();
		for(TSEObject o:list) {
			if(o instanceof TSENode){
				slist.add((TSENode) o);
			}
		}
		return slist;
	}
	
	@Override
	public List<TSEGraph> getSelectedGraphs() {
		List<TSEGraph> slist = new ArrayList<TSEGraph>();
		for(TSEObject o:list) {
			if(o instanceof TSEGraph){
				slist.add((TSEGraph) o);
			}
		}
		return slist;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof GraphSelection)) {
			return false;
		}
		GraphSelection other = (GraphSelection) obj;
		if (list == null) {
			if (other.list != null) {
				return false;
			}
		} else if (!list.equals(other.list)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "GraphSelection [" + (list != null ? "list=" + list + ", " : "") + "isMultiple()=" + isMultiple() + ", "
				+ (super.toString() != null ? "toString()=" + super.toString() : "") + "]";
	}
	
	

}
