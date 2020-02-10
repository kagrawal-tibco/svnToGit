package com.tibco.cep.dashboard.psvr.mal;

import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;

class InternalViewsConfigHelperListener implements ElementChangeListener {

	private ViewsConfigHelper viewsConfigHelper;

	private MALElement elementChanged;

	private List<OperationInfo> preops;

	private List<OperationInfo> postops;

	InternalViewsConfigHelperListener(ViewsConfigHelper viewsConfigHelper) {
		System.out.println("InternalViewsConfigHelperListener.InternalViewsConfigHelperListener()");
		this.viewsConfigHelper = viewsConfigHelper;
		this.preops = new LinkedList<OperationInfo>();
		this.postops = new LinkedList<OperationInfo>();
	}

	@Override
	public void prepareForChange(MALElement element) {
		System.out.println("InternalViewsConfigHelperListener.prepareForChange(" + element + ")");
		if (elementChanged != null) {
			throw new IllegalStateException("cannot handle cascaded element changes");
		}
		this.elementChanged = element;
	}

	@Override
	public void preOp(String parentPath, MALElement child, MALElement replacement, OPERATION operation) {
		System.out.println("InternalViewsConfigHelperListener.preOp(" + parentPath + "," + child + "," + replacement + "," + operation + ")");
		preops.add(new OperationInfo(parentPath, child, replacement, operation));
	}

	@Override
	public void postOp(String parentPath, MALElement child, MALElement replacement, OPERATION operation) {
		System.out.println("InternalViewsConfigHelperListener.postOp(" + parentPath + "," + child + "," + replacement + "," + operation + ")");
		OperationInfo opInfo = new OperationInfo(parentPath, child, replacement, operation);
		boolean remove = preops.remove(opInfo);
		if (remove == true) {
			postops.add(opInfo);
		}
	}

	@Override
	public void changeComplete(MALElement element) {
		System.out.println("InternalViewsConfigHelperListener.changeComplete(" + element + ")");
		for (OperationInfo opInfo : postops) {
			if (opInfo.child instanceof MALComponent) {
				// we will only deal with components
				switch (opInfo.operation) {
					case ADD:
						viewsConfigHelper.addComponentToIndex((MALComponent) opInfo.child);
						break;
					case UPDATE:
						viewsConfigHelper.replaceComponentInIndex((MALComponent) opInfo.child, (MALComponent) opInfo.replacement);
						break;
					case DELETE:
						viewsConfigHelper.removeComponentFromIndex((MALComponent) opInfo.child);
						break;
					case REPLACE:
						viewsConfigHelper.replaceComponentInIndex((MALComponent) opInfo.child, (MALComponent) opInfo.replacement);
						break;
					default:
						throw new UnsupportedOperationException(opInfo.operation.toString());
				}
			}
		}
		this.elementChanged = null;
	}

	@Override
	public void changeAborted(MALElement element) {
		System.out.println("InternalViewsConfigHelperListener.changeAborted(" + element + ")");
		preops.clear();
		postops.clear();
		this.elementChanged = null;
	}

	private class OperationInfo {

		String parentPath;
		MALElement child;
		MALElement replacement;
		OPERATION operation;

		private OperationInfo(String parentPath, MALElement child, MALElement replacement, OPERATION operation) {
			super();
			this.parentPath = parentPath;
			this.child = child;
			this.replacement = replacement;
			this.operation = operation;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((parentPath == null) ? 0 : parentPath.hashCode());
			result = prime * result + ((operation == null) ? 0 : operation.hashCode());
			result = prime * result + ((child == null) ? 0 : child.hashCode());
			result = prime * result + ((replacement == null) ? 0 : replacement.hashCode());
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
			if (getClass() != obj.getClass()) {
				return false;
			}
			OperationInfo other = (OperationInfo) obj;
			if (operation == null) {
				if (other.operation != null) {
					return false;
				}
				return true;
			} else if (operation.equals(other.operation) == false) {
				return false;
			}
			if (parentPath == null) {
				if (other.parentPath != null) {
					return false;
				}
				return true;
			} else if (parentPath.equals(other.parentPath) == false) {
				return false;
			}
			if (operation.equals(OPERATION.ADD) == true) {
				return true;
			}
			if (child == null) {
				if (other.child != null) {
					return false;
				}
				return true;
			} else if (child.equals(child) == false) {
				return false;
			}
			if (replacement == null) {
				if (other.replacement != null) {
					return false;
				}
				return true;
			} else if (replacement.equals(other.replacement) == false) {
				return false;
			}
			return true;
		}

	}
}