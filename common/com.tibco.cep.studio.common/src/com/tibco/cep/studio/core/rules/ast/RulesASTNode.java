package com.tibco.cep.studio.core.rules.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

public class RulesASTNode extends CommonTree {

//    public static List<RulesASTNode> EMPTY_NODE_LIST = new ArrayList<RulesASTNode>();

    // adds the ability to add arbitrary data to node
    private HashMap<String, Object> fData = new HashMap<String, Object>();
    
	private int fOffset;
	private int fLength;
	
	public boolean fHidden = false;
	
	private Object fBinding;
	
	// flag indicating whether this AST node is dirty,
	// that is, whether a change in the text has occurred
	// and this AST is no longer current
	private boolean fDirty;

	// flag indicating whether this AST is malformed.
	// An AST will be malformed if the originating
	// source contains syntax errors
	private boolean fMalformed = false;
	
	public Object getBinding() {
		return fBinding;
	}

	public void setBinding(Object binding) {
		fBinding = binding;
	}

	public int getOffset() {
		if (startIndex != -1) {
			return fOffset;
		}
		RulesASTNode firstChild = getFirstChild();
		if (token instanceof CommonToken && ((CommonToken)token).getTokenIndex() >= 0) {
			int start = ((CommonToken)token).getStartIndex();
			if (firstChild == null || firstChild.getOffset() >= start) {
				return start;
			}
		}
		if (firstChild != null) {
			return firstChild.getOffset();
		}
		if (token instanceof CommonToken) {
			return ((CommonToken)token).getStartIndex();
		}
		return fOffset;
	}

	public void setOffset(int offset) {
		fOffset = offset;
	}

	public int getLength() {
		if (stopIndex != -1) {
			return fLength;
		}
//		if (token instanceof CommonToken && ((CommonToken)token).getTokenIndex() >= 0) {
//			return ((CommonToken)token).getStopIndex() - getOffset() + 1;
//		}
		RulesASTNode lastChild = getLastChild();
		if (lastChild != null) {
			return lastChild.getOffset() + lastChild.getLength() - getOffset();
		}
		if (token instanceof CommonToken) {
			return ((CommonToken)token).getStopIndex() - getOffset() + 1;
		}
		return fLength;
	}

	public void setLength(int length) {
		fLength = length;
	}

	public RulesASTNode(Token payload) {
		super(payload);
	}

	public RulesASTNode getChildOfType(int type) {
		return (RulesASTNode) getFirstChildWithType(type);
//		List children = getChildren();
//		if (children == null) {
//			return null;
//		}
//		for (Object obj : children) {
//			RulesASTNode child = (RulesASTNode) obj;
//			if (child.getType() == type) {
//				return child;
//			}
//		}
//		return null;
	}
	
    public List<RulesASTNode> getChildren() {
    	if (children == null) {
    		children = new ArrayList<RulesASTNode>();
    	}
        return children;
    }

    public List<RulesASTNode> getChildrenByFeatureId(int featureId) {
    	RulesASTNode att = getFirstChildWithType(featureId);
        return att != null ? att.getChildren() : null;
    }

    public RulesASTNode getChildByFeatureId(int featureId) {
    	RulesASTNode att = getFirstChildWithType(featureId);
        return att != null ? att.getFirstChild() : null;
    }
    
    public RulesASTNode getFirstChild() {
        if (getChildCount() > 0) {
            return getChild(0);
        }
        return null;
    }

    public RulesASTNode getLastChild() {
    	if (getChildCount() > 0) {
    		return getChild(getChildCount()-1);
    	}
    	return null;
    }
    
    public int getFirstLine() {
    	if(getFirstChild() != null) {
    		return getFirstChild().getFirstLine();
    	}
    	return getLine();
    }
    
    public int getLastLine() {
    	if(getLastChild() != null) {
    		return getLastChild().getLastLine();
    	}
    	return getLine();
    }
    
    public RulesASTNode getFirstChildWithType(int type) {
    	Tree child = super.getFirstChildWithType(type);
		if (child instanceof RulesASTNode) {
			return (RulesASTNode) child;
		} else if (child instanceof CommonTree) {
			return new RulesASTNode(((CommonTree)child).token);
		}
		return null;
    }

    public void accept(IASTNodeVisitor<RulesASTNode> visitor) {
        if (!visitor.preVisit(this)) {
        	return;
        }
        if (visitor.visit(this)) {
            doVisitChildren(visitor);
        }
        visitor.postVisit(this);
    }

    private void doVisitChildren(IASTNodeVisitor<RulesASTNode> visitor) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            RulesASTNode child = getChild(i);
            child.accept(visitor);
        }
    }

	@Override
	public RulesASTNode getChild(int i) {
		Tree child = super.getChild(i);
		if (child instanceof RulesASTNode) {
			return (RulesASTNode) child;
		} else if (child instanceof CommonTree) {
			return new RulesASTNode(((CommonTree)child).token);
		}
		return null;
	}
	
	public void setData(String key, Object data) {
		fData.put(key, data);
	}

	public Object getData(String key) {
		return fData.get(key);
	}

	public void setDirty(boolean dirty) {
		this.fDirty = dirty;
	}

	public boolean isDirty() {
		if (fDirty) {
			return fDirty;
		}
		RulesASTNode root = getRoot();
		return (root != null && root.isDirty());
	}
	
	public RulesASTNode getRoot() {
		RulesASTNode parent = (RulesASTNode) getParent();
		if (parent == null) {
			return null;
		}
		while (parent.getParent() != null) {
			parent = (RulesASTNode) parent.getParent();
		}
		return parent;
	}

	public void setHidden(boolean hidden) {
		this.fHidden = hidden;
	}
	
	public boolean isHidden() {
		return fHidden;
	}

	public boolean isMalformed() {
		return fMalformed;
	}

	public boolean isArray() {
		Object bool = getData("array");
		boolean arr = false;
		if (bool instanceof Boolean) {
			arr = (Boolean) bool; 
		}
		return arr;
	}
	
	public void setMalformed(boolean malformed) {
		fMalformed = malformed;
	}

	public void addChildByFeatureId(RulesASTNode childNode,
			int featureId) {
		List<RulesASTNode> children = getChildrenByFeatureId(featureId);
		if (children == null) {
			RulesASTNode child = new RulesASTNode(new CommonToken(featureId));
			addChild(child);
			child.addChild(childNode);
		} else {
			children.add(childNode);
		}
	}
	
}
