package com.tibco.cep.studio.wizard.as.internal.utils;

import static com.tibco.cep.studio.wizard.as.ASPlugin._PLUGIN_ID;
import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.eclipse.core.runtime.IStatus.WARNING;
import static org.eclipse.jface.fieldassist.FieldDecorationRegistry.DEC_ERROR;
import static org.eclipse.swt.SWT.LEFT;
import static org.eclipse.swt.SWT.TOP;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.swt.widgets.Control;

import com.tibco.as.space.SpaceDef;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.impl.data.spacedef.ASFullSpaceDef;
import com.tibco.as.space.impl.data.spacedef.ASSpaceDef;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASSpaceSelectionWizardPageController;

public class UIUtils {

	public static ControlDecoration createDecorator(Control control, String message) {
		ControlDecoration controlDecoration = new ControlDecoration(control, LEFT | TOP);
		controlDecoration.setDescriptionText(message);
		FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault().getFieldDecoration(DEC_ERROR);
		controlDecoration.setImage(fieldDecoration.getImage());
		return controlDecoration;
	}

	public static Object createTreeNodesForChannels(List<Channel> channels, IASSpaceSelectionWizardPageController controller) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		for (Channel channel : channels) {
			// Channel
			TreeNode channelNode = new TreeNode(channel);

			// Space
			TreeNode[] childrenNodes = null;
            try {
            	List<SpaceDef> spaceDefs = TO_FIX_ASSPACEDEF_ISSUE(controller.getUserSpaceDefs(channel));
            	if (null != spaceDefs && false == spaceDefs.isEmpty()) {
            		childrenNodes = createTreeNodesForSpaceDefs(spaceDefs);
            	} else {
            		childrenNodes = createTreeNodesForEmptyMetaspace();
            	}
            }
            catch (Exception ex) {
            	controller.getModel().addError(ex);
            	childrenNodes = createTreeNodesForException(ex);
            }

			channelNode.setChildren(childrenNodes);
			setParentForChildren(childrenNodes, channelNode);

			nodes.add(channelNode);
		}
		return nodes.toArray(new TreeNode[nodes.size()]);
	}

	private static List<SpaceDef> TO_FIX_ASSPACEDEF_ISSUE(List<SpaceDef> userSpaceDefs) {
		Field asSpaceDefFieldOfASFullSpaceDef = null;
		Field contextFieldOfASSpaceDef = null;
		try {
			asSpaceDefFieldOfASFullSpaceDef = ASFullSpaceDef.class.getDeclaredField("spaceDef"); //$NON-NLS-1$
			asSpaceDefFieldOfASFullSpaceDef.setAccessible(true);
			contextFieldOfASSpaceDef = ASSpaceDef.class.getDeclaredField("context"); //$NON-NLS-1$
			contextFieldOfASSpaceDef.setAccessible(true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
		for (SpaceDef spaceDef : userSpaceDefs) {
			ASSpaceDef asSpaceDef = null;
			try {
	            asSpaceDef = (ASSpaceDef) asSpaceDefFieldOfASFullSpaceDef.get(spaceDef);
            }
            catch (Exception ex) {
	            ex.printStackTrace();
            }
			try {
				contextFieldOfASSpaceDef.set(asSpaceDef, Tuple.create());
            }
            catch (Exception ex) {
	            ex.printStackTrace();
            }
		}
	    return userSpaceDefs;
    }

	private static TreeNode[] createTreeNodesForSpaceDefs(List<SpaceDef> spaceDefs) {
		List<TreeNode> spaceDefNodes = new ArrayList<TreeNode>();
		for (SpaceDef spaceDef : spaceDefs) {
			spaceDefNodes.add(new TreeNode(spaceDef));
		}
		return spaceDefNodes.toArray(new TreeNode[spaceDefNodes.size()]);
	}

	private static TreeNode[] createTreeNodesForException(Exception ex) {
		List<TreeNode> exceptionNodes = new ArrayList<TreeNode>();
		String hintMessage = ex.getMessage();
		IStatus status = new Status(ERROR, _PLUGIN_ID, hintMessage);
		exceptionNodes.add(new TreeNode(status));
		return exceptionNodes.toArray(new TreeNode[exceptionNodes.size()]);
	}

	private static TreeNode[] createTreeNodesForEmptyMetaspace() {
		List<TreeNode> exceptionNodes = new ArrayList<TreeNode>();
		String hintMessage = Messages.getString("UIUtils.empty_metaspace"); //$NON-NLS-1$
		IStatus status = new Status(WARNING, _PLUGIN_ID, hintMessage);
		exceptionNodes.add(new TreeNode(status));
		return exceptionNodes.toArray(new TreeNode[exceptionNodes.size()]);
	}

	private static void setParentForChildren(TreeNode[] children, TreeNode parent) {
		for (TreeNode node : children) {
			node.setParent(parent);
		}
	}

}
