package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration;

import java.io.Serializable;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTree;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTreeConstants;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class TableTreeSizeController implements TableTreeConstants, Serializable {

	private static final long serialVersionUID = 4177472899953535757L;

	protected Logger logger;

	protected ExceptionHandler exceptionHandler;

	protected MessageGenerator messageGenerator;

	private DefaultMutableTreeNode rootNode;

	TableTreeSizeController(Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		this.logger = logger;
		this.exceptionHandler = exceptionHandler;
		this.messageGenerator = messageGenerator;
		rootNode = new DefaultMutableTreeNode(new TableTreeSize("", 0));
	}

	protected void updateOnResponse(String commandType, TableTree tableTree, TableModel tableModel) throws TableModelException {
		if (logger.isEnabledFor(Level.DEBUG)) {
			logger.log(Level.DEBUG, "Initial size of TableTree[name=" + tableTree.getTableTreeName() + "] is " + getTableTreeSize());
		}
		if (tableModel == null || tableTree == null) {
			return;
		}
		boolean bUpdateSize = false;
		if (CMD_FULL_TABLE.equalsIgnoreCase(commandType)) {
			bUpdateSize = true;
		} else if (CMD_ROW_EXPAND.equalsIgnoreCase(commandType)) {
		} else if (CMD_TABLE_EXPAND.equalsIgnoreCase(commandType)) {
			bUpdateSize = true;
		} else if (CMD_REFRESH.equalsIgnoreCase(commandType)) {
			bUpdateSize = true;
		} else if (CMD_GROUP_BY.equalsIgnoreCase(commandType)) {
		} else if (CMD_TABLE_APPEND_ROW_SET.equalsIgnoreCase(commandType)) {
			bUpdateSize = true;
		} else if (CMD_TABLE_NEXT_ROW_SET.equalsIgnoreCase(commandType)) {
			bUpdateSize = true;
		} else if (CMD_TABLE_PREV_ROW_SET.equalsIgnoreCase(commandType)) {
			bUpdateSize = true;
		} else if (CMD_SORT.equalsIgnoreCase(commandType)) {
			bUpdateSize = true;
		} else if (CMD_EXPAND_COLLAPSE.equalsIgnoreCase(commandType)) {
			// Keep the status as it is.
		}
		if (bUpdateSize) {
			if (logger.isEnabledFor(Level.DEBUG)) {
				logger.log(Level.DEBUG, "Updating size for command [" + commandType + "] on TableTree[name=" + tableTree.getTableTreeName() + ",path=" + tableTree.getTablePath() + "] with new size as ["
						+ tableModel.getDisplayedCount() + "]");
			}
			addUpdatePath(tableTree.getTablePath(), tableModel.getDisplayedCount());
			logger.log(Level.DEBUG, "Final size of " + tableTree.getTableTreeName() + " is " + getTableTreeSize());
		} else {
			logger.log(Level.DEBUG, "Returning without updating TableTree[name=" + tableTree.getTableTreeName() + "] on command [" + commandType + "]");
		}
	}

	int getTableSize(String tablePath) {
		TableTreeSize tableTreeSize = getTableTreeSize(tablePath);
		if (tableTreeSize != null) {
			return tableTreeSize.getSize();
		}
		return 0;
	}

	TableTreeSize getTableTreeSize(String tablePath) {
		if (tablePath.equals(ROOT_TABLE_PATH)) {
			return (TableTreeSize) rootNode.getUserObject();
		}
		Enumeration enumeration = rootNode.breadthFirstEnumeration();
		while (enumeration.hasMoreElements()) {
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) enumeration.nextElement();
			TableTreeSize tableTreeSize = (TableTreeSize) treeNode.getUserObject();
			if (tableTreeSize.getPath().equals(tablePath)) {
				return tableTreeSize;
			}
		}
		return null;
	}

	/**
	 * @param commandType
	 * @param mapSize
	 */
	int getTableTreeSize() {
		int totalSize = 0;
		Enumeration enumeration = rootNode.breadthFirstEnumeration();
		while (enumeration.hasMoreElements()) {
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) enumeration.nextElement();
			TableTreeSize tableTreeSize = (TableTreeSize) treeNode.getUserObject();
			totalSize += tableTreeSize.getSize();
		}
		return totalSize;
	}

	/**
	 * @param commandType
	 * @param mapSize
	 */
	void updateOnRequest(String commandType, String requestPath) {
		if (commandType == null) {
			commandType = CMD_FULL_TABLE;
		}
		logger.log(Level.DEBUG, "updateOnRequest() entering with new size:: " + getTableTreeSize());
		if (CMD_FULL_TABLE.equalsIgnoreCase(commandType)) {
			rootNode.removeAllChildren();
			((TableTreeSize) rootNode.getUserObject()).setSize(0);
		} else if (CMD_ROW_EXPAND.equalsIgnoreCase(commandType)) {
		} else if (CMD_TABLE_EXPAND.equalsIgnoreCase(commandType)) {
		} else if (CMD_REFRESH.equalsIgnoreCase(commandType)) {
			removePath(requestPath);
		} else if (CMD_GROUP_BY.equalsIgnoreCase(commandType)) {
			removePath(requestPath);
		} else if (CMD_TABLE_APPEND_ROW_SET.equalsIgnoreCase(commandType)) {
			// Keep the status as it is.
		} else if (CMD_TABLE_NEXT_ROW_SET.equalsIgnoreCase(commandType)) {
			// Keep the status as it is.
		} else if (CMD_TABLE_PREV_ROW_SET.equalsIgnoreCase(commandType)) {
			// Keep the status as it is.
		} else if (CMD_SORT.equalsIgnoreCase(commandType)) {
			removePath(requestPath);
		} else if (CMD_EXPAND_COLLAPSE.equalsIgnoreCase(commandType)) {
			// Keep the status as it is.
		}
		logger.log(Level.DEBUG, "updateOnRequest() returning with new size:: " + getTableTreeSize());
	}

	/**
	 * @param tablePath
	 * @param size
	 */
	private void addUpdatePath(String tablePath, int size) {
		if (!updatePath(tablePath, size)) {
			addPath(tablePath, size);
		}
	}

	/**
	 * @param tablePath
	 * @param displayedCount
	 */
	private void addPath(String tablePath, int size) {
		String parentPath = getParentTableTreePath(tablePath);
		Enumeration enumeration = rootNode.breadthFirstEnumeration();
		while (enumeration.hasMoreElements()) {
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) enumeration.nextElement();
			TableTreeSize tableTreeSize = (TableTreeSize) treeNode.getUserObject();
			if (tableTreeSize.getPath().equals(parentPath)) {
				treeNode.add(new DefaultMutableTreeNode(new TableTreeSize(tablePath, size)));
				break;
			}
		}
	}

	/**
	 * @param tablePath
	 * @param displayedCount
	 */
	private boolean updatePath(String tablePath, int size) {
		if (tablePath.equals(ROOT_TABLE_PATH)) {
			TableTreeSize tableTreeSize = (TableTreeSize) rootNode.getUserObject();
			tableTreeSize.setSize(size);
			tableTreeSize.setPath(tablePath);
			return true;
		}
		Enumeration enumeration = rootNode.breadthFirstEnumeration();
		while (enumeration.hasMoreElements()) {
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) enumeration.nextElement();
			TableTreeSize tableTreeSize = (TableTreeSize) treeNode.getUserObject();
			if (tableTreeSize.getPath().equals(tablePath)) {
				((TableTreeSize) treeNode.getUserObject()).setSize(size);
				return true;
			}
		}
		return false;
	}

	private void removePath(String path) {
		Enumeration enumeration = rootNode.breadthFirstEnumeration();
		while (enumeration.hasMoreElements()) {
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) enumeration.nextElement();
			TableTreeSize tableTreeSize = (TableTreeSize) treeNode.getUserObject();
			if (tableTreeSize.getPath().equals(path)) {
				treeNode.removeFromParent();
				break;
			}
		}
	}

	/**
	 * @return
	 */
	private String getParentTableTreePath(String tableTreePath) {
		try {
			int lastIndex = tableTreePath.lastIndexOf(TableTree.PATH_SEPARATOR);
			tableTreePath = tableTreePath.substring(0, lastIndex);
			lastIndex = tableTreePath.lastIndexOf(TableTree.PATH_SEPARATOR);
			tableTreePath = tableTreePath.substring(0, lastIndex);
		} catch (Exception e) {

		}
		return tableTreePath;
	}

	boolean isCommandToIncreaseTableTreeSize(String commandType) {
		if (commandType == null) {
			commandType = CMD_FULL_TABLE;
		}
		boolean bIncreaseSize = false;
		if (CMD_FULL_TABLE.equalsIgnoreCase(commandType)) {
		} else if (CMD_ROW_EXPAND.equalsIgnoreCase(commandType)) {
		} else if (CMD_TABLE_EXPAND.equalsIgnoreCase(commandType)) {
			bIncreaseSize = true;
		} else if (CMD_REFRESH.equalsIgnoreCase(commandType)) {
		} else if (CMD_GROUP_BY.equalsIgnoreCase(commandType)) {
		} else if (CMD_TABLE_APPEND_ROW_SET.equalsIgnoreCase(commandType)) {
			bIncreaseSize = true;
		} else if (CMD_TABLE_NEXT_ROW_SET.equalsIgnoreCase(commandType)) {
			// bIncreaseSize = true;
		} else if (CMD_TABLE_PREV_ROW_SET.equalsIgnoreCase(commandType)) {
			// bIncreaseSize = true;
		} else if (CMD_SORT.equalsIgnoreCase(commandType)) {
		} else if (CMD_EXPAND_COLLAPSE.equalsIgnoreCase(commandType)) {
			// Keep the status as it is.
		}
		return bIncreaseSize;
	}

	private class TableTreeSize {

		private int size;
		private String path;

		TableTreeSize(String path, int size) {
			this.path = path;
			this.size = size;
		}

		String getPath() {
			return path;
		}

		void setPath(String path) {
			this.path = path;
		}

		int getSize() {
			return size;
		}

		void setSize(int size) {
			this.size = size;
		}

	}

}
