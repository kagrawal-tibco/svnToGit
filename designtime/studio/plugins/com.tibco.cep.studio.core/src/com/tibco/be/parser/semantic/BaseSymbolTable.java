package com.tibco.be.parser.semantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.tibco.be.parser.tree.NodeType;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: May 2, 2005
 * Time: 7:37:17 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseSymbolTable implements RLSymbolTable{
    private int currentScopeId = 0;
    private HashMap locals = new HashMap();
    private ArrayList visibleScopeIds = new ArrayList();
    
    protected BaseSymbolTable() {
        clearLocals();
    }
    
    public void addLocalDeclaration(String id, NodeType type) {
        if(id != null && type != null) {
            LocalEntry entry = (LocalEntry)locals.get(id);
            if(entry == null) {
                entry = new LocalEntry();
                locals.put(id, entry);
            }
            entry.addType(type);
        }
    }

    public void pushScope() {
        visibleScopeIds.add(new Integer(++currentScopeId));
    }
    
    public void popScope() {
        visibleScopeIds.remove(visibleScopeIds.size() - 1);
    }
        
    public void clearLocals() {
        locals.clear();
        currentScopeId = 0;
        visibleScopeIds.clear();
        //start with one (outermost) scope visible
        visibleScopeIds.add(new Integer(currentScopeId));
    }

    private Integer currentScopeId() {
        return ((Integer)visibleScopeIds.get(visibleScopeIds.size() - 1));
    }
    
    protected NodeType getLocalType(String id) {
        LocalEntry entry = (LocalEntry)locals.get(id);
        if(entry != null) {
            return entry.getType();
        }
        return NodeType.UNKNOWN;
    }
    
    private class LocalEntry {
        private ArrayList scopeIds = new ArrayList();
        private ArrayList types = new ArrayList();
        
        private void addType(NodeType type) {
            scopeIds.add(currentScopeId());
            types.add(type);
        }
        
        private NodeType getType() {
            for(int ii = 0; ii < visibleScopeIds.size(); ii++) {
                int scopeId = ((Integer)visibleScopeIds.get(ii)).intValue();   
                for(int jj = 0; jj < scopeIds.size(); jj++) {
                    if(((Integer)scopeIds.get(jj)).intValue() == scopeId) {
                        return (NodeType)types.get(jj);
                    }
                }
            }
            return NodeType.UNKNOWN;
        }
    }
    
    protected Iterator visibleLocalIds() {
        return new Iterator() {
            private Iterator ids = locals.keySet().iterator();
            private String next = findNextId();
            
            private String findNextId() {
                while(ids.hasNext()) {
                    String id = (String)ids.next();
                    if(!getLocalType(id).isUnknown()) {
                        return id;
                    }
                }
                return null;
            }
            
            public boolean hasNext() {
                return next != null;
            }

            public Object next() {
                if(!hasNext()) throw new NoSuchElementException();
                
                String ret = next;
                next = findNextId();
                return ret;
            }
            
            public void remove() {}
        };
    }
}
/*    
    public static void main(String[] args) {
        BaseSymbolTable bst = new BaseSymbolTable(){

            public NodeType getDeclaredIdentifierType(String id) {
                return getLocalType(id);
            }

            public NodeType getReturnType() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        String id1 = "ii";
        String id2 = "jj";
        bst.addLocalDeclaration(id1, NodeType.NULL);
        System.out.println(bst.getLocalType(id1));
        bst.pushScope();
        bst.pushScope();
        bst.pushScope();
        bst.addLocalDeclaration(id2, NodeType.VOID);
        System.out.println(bst.getLocalType(id2));
        bst.popScope();
        System.out.println(bst.getLocalType(id1));
        System.out.println(bst.getLocalType(id2));
        bst.pushScope();
        System.out.println(bst.getLocalType(id2));
        bst.addLocalDeclaration(id2, NodeType.VOID);
        bst.pushScope();
        System.out.println(bst.getLocalType(id2));
    }
*/
