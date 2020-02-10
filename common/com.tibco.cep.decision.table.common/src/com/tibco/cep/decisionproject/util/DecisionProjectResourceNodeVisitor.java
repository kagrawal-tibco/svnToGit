/**
 * 
 */
package com.tibco.cep.decisionproject.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.ParentResource;

/**
 * @author aathalye
 *
 */
public class DecisionProjectResourceNodeVisitor {
	
	private AbstractResource wrapped;
	
	private List<DecisionProjectResourceNodeVisitor> list = 
				new ArrayList<DecisionProjectResourceNodeVisitor>();
	
	private boolean visited;
	
	public DecisionProjectResourceNodeVisitor(final AbstractResource abstractResource) {
		this.wrapped = abstractResource;
		if (abstractResource instanceof ParentResource) {
			Iterator<? extends AbstractResource> children = 
				((ParentResource)abstractResource).getChildren();
			while (children.hasNext()) {
				AbstractResource ar = children.next();
				list.add(new DecisionProjectResourceNodeVisitor(ar));
			}
		}
	}
	
	public boolean isVisited() {
		return visited;
	}
	
	public void setVisited(final boolean visited) {
		this.visited = visited;
	}
	
	public boolean hasUnvisitedChildren() {
		if (!(wrapped instanceof ParentResource)) {
			return false;
		}
		for (DecisionProjectResourceNodeVisitor arw : list) {
			//If a single child is also unvisited return true
			if (!arw.isVisited()) {
				return true;
			}
		}
		return false;
	}
	
	public Iterator<DecisionProjectResourceNodeVisitor> getUnvisitedChildren() {
		Iterator<DecisionProjectResourceNodeVisitor> iter;
		if (!(wrapped instanceof ParentResource)) {
			return null;
		}
		//ParentResource pr = (ParentResource)wrapped;
		List<DecisionProjectResourceNodeVisitor> unvisited = 
				new ArrayList<DecisionProjectResourceNodeVisitor>();
		if (!list.isEmpty()) {
			for (int loop = 0; loop < list.size(); loop++) {
				DecisionProjectResourceNodeVisitor wrapper = list.get(loop);
				if (!wrapper.isVisited()) {
					unvisited.add(wrapper);
				}
			}
			iter = unvisited.iterator();
		} 
		iter = list.iterator();
		return iter;
	}
	
	public AbstractResource getWrappedResource() {
		return wrapped;
	}
}
