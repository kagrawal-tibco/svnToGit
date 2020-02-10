package com.tibco.cep.query.utils;

import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 14, 2007
 * Time: 6:43:15 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ObjectTreeNode {

    public ObjectTreeNode getChild(int i);

    public List getNodeChildren();

    public int getChildCount();

    public String getText();

    public int getType();

    public String toStringTree();

    public void addChild(ObjectTreeNode t);

    public ObjectTreeNode dupNode();

    public ObjectTreeNode dupTree();

    void setText(String text);

    void setType(int type);

    boolean isNil();
}
