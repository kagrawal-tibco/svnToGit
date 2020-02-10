package com.tibco.be.parser.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.parser.RuleInfo;
import com.tibco.be.parser.tree.RootNode;


/**
 * User: nprade
 * Date: 1/30/12
 * Time: 4:04 PM
 */
public class RuleTemplateInfo
        extends RuleInfo {


    protected List<RootNode> bindingTreeList = new ArrayList<RootNode>();
    protected List<RootNode> viewTreeList = new ArrayList<RootNode>();


    public void addBindingTree(RootNode tree) {
        this.bindingTreeList.add(tree);
    }


    public void addViewTree(RootNode tree) {
        this.viewTreeList.add(tree);
    }


    public Iterator<RootNode> getBindingTrees() {
        return this.bindingTreeList.iterator();
    }


    public Iterator getViewTrees() {
        return this.viewTreeList.iterator();
    }


//    public void setBindingTrees(List<RootNode> trees) {
//        this.bindingTreeList = trees;
//    }
//
//
//    public void setViewTrees(List<RootNode> trees) {
//        this.viewTreeList = trees;
//    }


}
