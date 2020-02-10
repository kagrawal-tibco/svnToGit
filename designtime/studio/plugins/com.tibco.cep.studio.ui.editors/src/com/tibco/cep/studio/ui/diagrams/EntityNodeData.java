package com.tibco.cep.studio.ui.diagrams;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.tomsawyer.drawing.TSConnector;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.interactive.swing.TSSwingCanvas;


/**
 * author: ggrigore
 * 
 * This is a data storage class for rule nodes that contains a list of
 * attributes, the action, a user object, as well as the corresponding
 * TS node that represents the rule node.
 */
public class EntityNodeData
{
    private List attributes;
    private EntityNodeItem concept;
    private Object userObject;
    private TSENode owner;
    private boolean stateExpanded;

//    private static TSEColor START_GRADIENT_COLOR = new TSEColor(151, 217, 237);
//    private static TSEColor END_GRADIENT_COLOR = new TSEColor(248, 176, 182);

    public static TSEColor START_FUTURE_COLOR = new TSEColor(218, 234, 253);
    public static TSEColor END_FUTURE_COLOR = new TSEColor(139, 176, 229);

    public static TSEColor START_PAST_COLOR = new TSEColor(235, 233, 237);
    public static TSEColor END_PAST_COLOR = new TSEColor(203, 203, 203);

    public static TSEColor START_PRESENT_COLOR = new TSEColor(254, 223, 175);
    public static TSEColor END_PRESENT_COLOR = new TSEColor(254, 145, 78);

    public static int MINIMUM_WIDTH = 75;
    public static int HEIGHT_BUFFER = 8;


    public EntityNodeData(TSENode ownerNode)
    {
        this.owner = ownerNode;
        this.owner.setUserObject(this);
        this.owner.setUI(new EntityNodeUI());
        this.setGradient(START_PRESENT_COLOR, END_PRESENT_COLOR);

        this.attributes = new LinkedList();
        this.stateExpanded = true;

        //set default action
        this.concept = new EntityNodeItem(EntityNodeItem.EXT_ATTRIBUTE, this);
        this.setEntity("", null);
        createConnectorFor(this.concept);
    }

    public String getLabel()
    {
        if (this.concept != null)
        {
            return this.concept.getLabel();
        }
        else
        {
            return null;
        }
    }

    public void setLabel(String label)
    {
        if (this.concept != null)
        {
            this.concept.setLabel(label);
        }
        else
        {
            System.err.println("Error, cannot set label to null action!");
        }
    }

    public List getAttributes()
    {
        return this.attributes;
    }

    public EntityNodeItem addAttribute(String label, int attrType, Object userObject)
    {
        this.repositionConnectors();

        EntityNodeItem condition =
            new EntityNodeItem(EntityNodeItem.ATTRIBUTE, attrType, label, userObject, this);

        this.attributes.add(condition);

        this.createConnectorFor(condition);

        return condition;
    }

    private void createConnectorFor(EntityNodeItem item)
    {
        TSConnector connector = ((TSENode) this.owner).addConnector();

        if (item.getType() == EntityNodeItem.ATTRIBUTE)
        {
            connector.setProportionalXOffset(-0.5);

            //count action also in denominator:
            double portionRatio = 1.0 / (getAttributes().size() + 1.0);
            double portionsFromBottom = 1.5;

            connector.setProportionalYOffset(
                    -0.5 + (portionRatio * portionsFromBottom));

            item.setConnector(connector);
        }
        else
        {
            connector.setProportionalXOffset(0.5);
            connector.setProportionalYOffset(0);

            this.concept.setConnector(connector);
        }

        //System.out.println("Creating connector at:");
        //System.out.println(connector.getProportionalYOffset());
    }

    public void repositionConnectors()
    {
        int index = 1;
        double portionRatio = 1.0 / (getAttributes().size() + 2.0);
        TSConnector connector;

        // for (ConceptNodeItem condition : this.conditions)
        Iterator conditionIter = this.attributes.iterator();
        EntityNodeItem condition;

        while (conditionIter.hasNext())
        {
            condition = (EntityNodeItem) conditionIter.next();
            connector = condition.getConnector();

            double portionsFromBottom = getAttributes().size() - index++ + 2.5;

            if (this.isCollapsed())
            {
                connector.setProportionalYOffset(0);
            }
            else
            {
                connector.setProportionalYOffset(
                    -0.5 + (portionRatio * portionsFromBottom));
            }

            //System.out.println(connector.getProportionalYOffset());
        }

        connector = this.getEntity().getConnector();

        double portionsFromBottom = 0.5;

        if (this.isCollapsed())
        {
            connector.setProportionalYOffset(0);
        }
        else
        {
            connector.setProportionalYOffset(
                -0.5 + (portionRatio * portionsFromBottom));
        }

        //System.out.println(connector.getProportionalYOffset());
    }

    public EntityNodeItem getEntity()
    {
        return this.concept;
    }

    public EntityNodeItem setEntity(String label, Object userObject)
    {
        this.concept.setLabel(label);
        this.concept.setUserObject(userObject);

        //((RuleNodeUI)this.owner.getUI()).readjustNodeSize();

        return this.concept;
    }

    public static void calculateConceptNodeSizes(List ruleNodes, TSSwingCanvas canvas)
    {
        Iterator iter = ruleNodes.iterator();

        // go through all the custom RuleNodeUI's and ask them to resize
        // themselves and reposition their corresponding connectors.
        while(iter.hasNext())
        {
        	try{
        		((EntityNodeUI)((TSENode)iter.next()).getUI()).resize(canvas);
        	}catch (Exception e){
        		e.printStackTrace();
        	}
        }

        canvas.drawGraph();
        canvas.repaint();
    }

    public TSENode getOwner()
    {
        return this.owner;
    }

    public Object getUserObject()
    {
        return this.userObject;
    }

    public void setUserObject(Object userObject)
    {
        this.userObject = userObject;
    }

    public boolean isCollapsed()
    {
        return !this.stateExpanded;
    }

    public boolean isExpanded()
    {
        return this.stateExpanded;
    }

    public void setCollapsed(boolean collapsed)
   {
       this.stateExpanded = !collapsed;
       this.repositionConnectors();
   }

   public void setExpanded(boolean expanded)
   {
       this.stateExpanded = expanded;
       this.repositionConnectors();
   }

     public void setGradient(TSEColor startColor, TSEColor endColor)
    {
        ((EntityNodeUI)this.owner.getUI()).setGradient(startColor, endColor);
    }

    public void removeGradient()
    {
        ((EntityNodeUI)this.owner.getUI()).removeGradient();
    }
}