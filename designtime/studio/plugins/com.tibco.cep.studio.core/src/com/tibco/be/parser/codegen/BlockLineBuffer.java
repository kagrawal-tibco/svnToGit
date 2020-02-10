package com.tibco.be.parser.codegen;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.designtime.model.ModelUtils;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Mar 10, 2009
 * Time: 2:55:59 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BlockLineBuffer implements RuleBlock {
    protected static final String BRK = CGConstants.BRK;
	protected String name;
	protected String ruleSetPath;
	protected StringBuffer decls = new StringBuffer();
	protected StringBuffer attrs = new StringBuffer();
	protected String whenBlock = null;
	protected String thenBlock = null;
	protected Map<String, Block> blockMap = new HashMap<String, Block>();

    protected BlockLineBuffer(String name, String ruleSetPath) {
		// super(name, ruleSetPath);
		this.name = name;
		this.ruleSetPath = ruleSetPath;
	}

	public String getRuleSetPath() {
		return ruleSetPath;
	}

	public void addDecl(String type, String name) {
		decls.append(ModelUtils.convertPathToPackage(type));
		decls.append(" ");
		decls.append(name);
		decls.append(";");
		decls.append(BRK);
	}

	public void addAttr(String name, String value) {
		attrs.append(name);
		attrs.append(" = ");
		attrs.append(value);
		attrs.append(";");
		attrs.append(BRK);
	}
	
	protected String indent(String src, int tabspace) {

        StringBuffer buf = new StringBuffer();
        appendSpace(buf, tabspace);
        for (int i=0; i<src.length(); i++) {
            char c = src.charAt(i);
            buf.append(c);
            if ((c == '\n') && (i+1 != src.length())){
                appendSpace(buf,tabspace);
            }
        }
        return buf.toString();

    }

    protected void appendSpace(StringBuffer buf, int tabspace) {
        for (int i=0; i<tabspace; i++) {
            buf.append("   ");
        }
    }

    public Block getBlock(String name) {
        Block block = null;
        if(!blockMap.containsKey(name)) {
            block = new Block(this);
            blockMap.put(name,block);
        } else {
            block = blockMap.get(name);
        }
        return block;
    }

    public Map<String, Block> getBlockMap() {
        if(blockMap == null) {
            this.blockMap = new HashMap<String,Block>();
        }
        return blockMap;
    }

    protected Block setBlock(String name,int start,int end) {
        Block block = getBlock(name);
        if(!block.isSet()) {
            block.setRange(start,end);
        }
        return block;
    }

    public boolean contains(int lineno) {
        for(Block b : this.blockMap.values() ){
            Block bl = b;
           if(bl.contains(lineno)) {
               return true;
           }
        }
        return false;
    }

    static class Block {
        private BlockLineBuffer parent;
        private int start;
        private int end;
        private boolean set = false;

        public Block(BlockLineBuffer parent) {
            this.parent = parent;
        }

        public int getEnd() {
            return end;
        }

        public int getStart() {
            return start;
        }

        boolean contains(int lineno) {
            if(lineno >= start && lineno <= end ){
                return true;
            } else {
                return false;
            }
        }

        public boolean isSet() {
            return set;
        }

        public void setRange(int start, int end) {
            this.start = start;
            this.end = end;
            this.set = true;
        }



        public String toString() {
            return "["+start+","+end+"] "+parent.getName() +"{"+parent.getRuleSetPath()+"}";
        }
    }
}
