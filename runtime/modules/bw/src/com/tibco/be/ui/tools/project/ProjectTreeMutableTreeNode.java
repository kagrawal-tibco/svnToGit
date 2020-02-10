package com.tibco.be.ui.tools.project; /**
 * User: ishaan
 * Date: May 3, 2004
 * Time: 3:16:26 PM
 */

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.ConceptView;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.service.channel.Channel;
import com.tibco.cep.designtime.model.service.channel.Destination;

public class ProjectTreeMutableTreeNode extends DefaultMutableTreeNode {


    protected static final String ICON_ROOT = "com/tibco/be/ui/tools/project/resources/images/";
    //TODO: use ResourceBundle
    protected static final String ICON_PATHS[] = {
            new String(ICON_ROOT + "concept.png"),
            new String(ICON_ROOT + "concepts_diagram.png"),
            new String(ICON_ROOT + "scorecard.png"),
            new String(ICON_ROOT + "rule.png"),
            new String(ICON_ROOT + "rule-function.png"),
            new String(ICON_ROOT + "event.png"),
            new String(ICON_ROOT + "time-event.gif"),
            new String(ICON_ROOT + "channel.png"),
            new String(ICON_ROOT + "destination.png"),
    };

    protected static final ImageIcon ICONS[] = new ImageIcon[]{
            new ImageIcon(ClassLoader.getSystemResource(ICON_PATHS[0])),
            new ImageIcon(ClassLoader.getSystemResource(ICON_PATHS[1])),
            new ImageIcon(ClassLoader.getSystemResource(ICON_PATHS[2])),
            new ImageIcon(ClassLoader.getSystemResource(ICON_PATHS[3])),
            new ImageIcon(ClassLoader.getSystemResource(ICON_PATHS[4])),
            new ImageIcon(ClassLoader.getSystemResource(ICON_PATHS[5])),
            new ImageIcon(ClassLoader.getSystemResource(ICON_PATHS[6])),
            new ImageIcon(ClassLoader.getSystemResource(ICON_PATHS[7])),
            new ImageIcon(ClassLoader.getSystemResource(ICON_PATHS[8])),
        };

    protected static int index = -1;
    protected static final int FOLDER_ICON_INDEX = index;
    protected static final int CONCEPT_ICON_INDEX = ++index;
    protected static final int CONCEPT_VIEW_ICON_INDEX = ++index;
    protected static final int SCORECARD_ICON_INDEX = ++index;
//    protected static final int RULESET_ICON_INDEX = ++index;
    protected static final int RULE_ICON_INDEX = ++index;
    protected static final int RULE_FUNCTION_ICON_INDEX = ++index;
    protected static final int EVENT_ICON_INDEX = ++index;
    protected static final int TIME_EVENT_ICON_INDEX = ++index;
    protected static final int CHANNEL_ICON_INDEX = ++index;
    protected static final int CHANNEL_DESTINATION_ICON_INDEX = ++index;

    protected int m_iconIndex;
    protected int m_typeFlag = 0x0;


    public ProjectTreeMutableTreeNode(Entity entity) {
        super(entity);
        if (entity instanceof Concept) {
            m_iconIndex = CONCEPT_ICON_INDEX;
            m_typeFlag = ProjectTree.CONCEPT_FLAG;
        } else if (entity instanceof Event) {
            m_iconIndex = EVENT_ICON_INDEX;
            m_typeFlag = ProjectTree.EVENT_FLAG;
//        } else if (entity instanceof RuleSet) {
//            m_iconIndex = RULESET_ICON_INDEX;
//            m_typeFlag = ProjectTree.RULE_SET_FLAG;
        } else if (entity instanceof ConceptView) {
            m_iconIndex = CONCEPT_VIEW_ICON_INDEX;
            m_typeFlag = ProjectTree.CONCEPT_FLAG;
        } else if (entity instanceof RuleFunction) {
            m_iconIndex = RULE_FUNCTION_ICON_INDEX;
            m_typeFlag = ProjectTree.RULE_SET_FLAG;
        } else if (entity instanceof Rule) {
            m_iconIndex = RULE_ICON_INDEX;
            m_typeFlag = ProjectTree.RULE_SET_FLAG;
        } else if (entity instanceof Channel) {
            m_iconIndex = CHANNEL_ICON_INDEX;
            m_typeFlag = ProjectTree.CHANNEL_FLAG;
        } else if (entity instanceof Destination) {
            m_iconIndex = CHANNEL_DESTINATION_ICON_INDEX;
            m_typeFlag = ProjectTree.CHANNEL_FLAG;
        } else m_iconIndex = -1;
    }


    public String toString() {
        Object object = getUserObject();
        if (object == null) return super.toString();
        else {
            if (object instanceof Folder) return ((Folder) object).getName();
            else {
                if (object instanceof Entity) return ((Entity) object).getName();
                else return super.toString();
            }
        }
    }


    public String getToolTipText() {
        return toString();
    }


    public Icon getIcon() {
        if (m_iconIndex == -1) return null;
        return ICONS[m_iconIndex];
    }


    public int getTypeFlag() {
        return m_typeFlag;
    }


    public static ImageIcon getImageIcon(int index) {
        if (index == -1) return null;
        return ICONS[index];
    }

}

