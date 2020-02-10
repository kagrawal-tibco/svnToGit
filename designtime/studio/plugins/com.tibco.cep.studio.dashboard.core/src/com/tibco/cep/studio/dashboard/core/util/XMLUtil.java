package com.tibco.cep.studio.dashboard.core.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLUtil {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    
    
	private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger("com.tibco.cep.studio.dashboard.core.util.XMLUtil");

	private static Once sOnce = new Once();

	/**
	 * Parse an xml doc and return a node to the root.
	 */
	public static Node parse(InputStream stream) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// dbf.setNamespaceAware(true);
		dbf.setIgnoringComments(true);
		dbf.setIgnoringElementContentWhitespace(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(stream);
		Node root = (Element) doc.getDocumentElement();
		if (sOnce.firstTime() && logger.isLoggable(java.util.logging.Level.FINE) == true) {
			logger.log(java.util.logging.Level.FINE, "DocumentBuilderFactory [parse] class " + dbf.getClass().getName());
			logger.log(java.util.logging.Level.FINE, "DocumentBuilder [parse] class " + db.getClass().getName());
			logger.log(java.util.logging.Level.FINE, "Node [parse-root] class " + root.getClass().getName());
		} else if (logger.isLoggable(java.util.logging.Level.FINER) == true) {
			logger.log(java.util.logging.Level.FINER, "DocumentBuilderFactory [parse] class " + dbf.getClass().getName());
			logger.log(java.util.logging.Level.FINER, "DocumentBuilder [parse] class " + db.getClass().getName());
			logger.log(java.util.logging.Level.FINER, "Node [parse-root] class " + root.getClass().getName());
		}

		// Get the fragment defines.
		Map<String, DocumentFragment> fragments = new HashMap<String, DocumentFragment>();
		NodeList nl = ((Element) root).getElementsByTagName("fragment-define");
		Document owner = root.getOwnerDocument();
		for (int i = 0; i < nl.getLength(); ++i) {
			Element e = (Element) nl.item(i);
			String name = e.getAttribute("name");
			if (!name.equals("")) {
				DocumentFragment df = owner.createDocumentFragment();
				NodeList children = e.getChildNodes();
				for (int j = 0; j < children.getLength(); ++j) {
					df.appendChild(children.item(j));
				}
				fragments.put(name, df);
				e.getParentNode().removeChild(e);
				if (logger.isLoggable(java.util.logging.Level.FINEST) == true) {
					logger.log(java.util.logging.Level.FINEST, "Got fragment " + name + ": " + XMLUtil.toString(df));
				}
			}
		}

		// Now do the includes. Right now, we don't support nested
		// includes.
		nl = ((Element) root).getElementsByTagName("fragment-include");
		while (nl.getLength() > 0) {
			Element e = (Element) nl.item(0);
			String name = e.getAttribute("name");
			e.getParentNode().replaceChild(fragments.get(name).cloneNode(true), e);
		}
		return root;
	}

	/**
	 * Pare an inputstream to a xml Document objec
	 *
	 * @param stream
	 * @return
	 * @throws Exception
	 */
	public static Document parseToDocument(InputStream stream) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// dbf.setNamespaceAware(true);
		dbf.setIgnoringComments(true);
		dbf.setIgnoringElementContentWhitespace(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(stream);
		return doc;
	}

	/**
	 * Parse an xml doc of type docType (the starting node type) and return a node to the root.
	 */
	public static Node parse(InputStream stream, String docType) throws Exception {
		Node root = parse(stream);
		if (!root.getNodeName().equals(docType)) {
			throw new Exception("XML document does not begin with <" + docType + ">.");
		}
		return root;
	}

	/**
	 * Parse an xml (fragment) string into a tree.
	 */
	public static Node parse(String s) throws Exception {
		try {
			ByteArrayInputStream byteStream = new ByteArrayInputStream(s.getBytes());
			return parse(byteStream);
		} catch (Exception e) {
			throw new Exception("Invalid XML: " + s, e);
		}
	}

	/**
	 * Get the specified 'string' value.
	 *
	 * @param path
	 *            points to an element (in which case the 'content' of that element is returned), or an attribute (if prefixed by @, in which case the value of the attribute is returned).
	 */
	public static String getString(Node node, String path) {
		List<String> strings = new ArrayList<String>();
		getAllStrings(node, path, false, strings);
		Iterator<String> iter = strings.iterator();
		if (iter.hasNext()) {
			return iter.next();
		} else {
			return null;
		}
	}

	/**
	 * Return an iteration of string values.
	 */
	public static Iterator<String> getAllStrings(Node node, String path) {
		return getStringList(node, path).iterator();
	}

	/**
	 * Return a list of string values.
	 */
	public static List<String> getStringList(Node node, String path) {
		List<String> strings = new ArrayList<String>();
		getAllStrings(node, path, true, strings);
		return strings;
	}

	/**
	 * Worker method that actually retrieves the strings.
	 */
	private static void getAllStrings(Node node, String path, boolean all, List<String> strings) {
		List<Node> nodes = new ArrayList<Node>();
		String attr = null;

		int idx;
		if (path != null) {
			idx = path.indexOf('@');
			if (idx >= 0) {
				attr = path.substring(idx + 1);
				if (idx > 0) {
					path = path.substring(0, idx);
				} else {
					path = null;
					nodes.add(node);
				}
			}
		}

		if (path != null) {
			getAllNodes(node, path, all, nodes);
		}

		Iterator<Node> iter = nodes.iterator();
		while (iter.hasNext()) {
			String value;
			value = null;
			Node n = iter.next();
			if (n instanceof Element) {
				if (attr != null && attr.length() != 0) {
					value = ((Element) n).getAttribute(attr);
					if (value != null) {
						value = value.trim();
					}
				} else {
					// logger.log(java.util.logging.Level.FINEST, "Returning the
					// TEXT within a Element");
					Text textNode = (Text) n.getFirstChild();
					if (textNode != null)
						value = (String) textNode.getData().trim();
				}
			}
			if (value != null && value.length() != 0) {
				strings.add(value);
			}
		}
	}

	/**
	 * Return the node specified by path.
	 */
	public static Node getNode(Node node, String path) {
		List<Node> nodes = new ArrayList<Node>();
		getAllNodes(node, path, false, nodes);
		Iterator<Node> iter = nodes.iterator();
		if (iter.hasNext()) {
			return (Element) iter.next();
		} else {
			return null;
		}
	}

	/**
	 * Return an iteration of specified nodes reachable from node n, by path path.
	 */
	public static Iterator<Node> getAllNodes(Node n, String path) {
		return getNodeList(n, path).iterator();
	}

	/**
	 * Return a list of specified nodes reachable from node n, by path path.
	 */
	public static List<Node> getNodeList(Node n, String path) {
		List<Node> nodes = new ArrayList<Node>();
		getAllNodes(n, path, true, nodes);
		return nodes;
	}

	/**
	 * Return a list of specified nodes reachable from node n, by the path, filtered by the attribute name and value.
	 */
	public static List<Node> getNodeListByAttribute(Node n, String path, String attrName, String attrValue) {
		List<Node> nodes = new ArrayList<Node>();
		getAllNodes(n, path, true, nodes);
		for (Iterator<Node> i = nodes.iterator(); i.hasNext();) {
			Node node = i.next();
			if (node instanceof Element) {
				String value = ((Element) node).getAttribute(attrName);
				if (value != null && value.equals(attrValue)) {
					continue;
				}
			}
			i.remove();
		}
		return nodes;
	}

	/**
	 * Return the node specified by path, filtered by the attribute name and value.
	 */
	public static Node getNodeByAttribute(Node node, String path, String attrName, String attrValue) {
		List<Node> nodes = getNodeListByAttribute(node, path, attrName, attrValue);
		Iterator<Node> iter = nodes.iterator();

		if (iter.hasNext()) {
			return iter.next();
		} else {
			return null;
		}
	}

	/**
	 * Return the node specified by path, filtered by multiple attribute name and value pairs.
	 */
	public static Node getNodeByAttribute(Node node, String path, String[] attrName, String[] attrValue) {
		if ((attrName == null) || (attrValue == null) || (attrName.length != attrValue.length)) {
			return null;
		}

		List<Node> nodes = getNodeListByAttribute(node, path, attrName[0], attrValue[0]);
		// Iterator<Node> iter = nodes.iterator();

		for (Iterator<Node> i = nodes.iterator(); i.hasNext();) {
			Node childnode = i.next();
			boolean found = true;
			if (childnode instanceof Element) {
				for (int idx = 1; idx < attrName.length; idx++) {
					String value = ((Element) childnode).getAttribute(attrName[idx]);
					if ((value == null) || (!value.equals(attrValue[idx]))) {
						found = false;
					}
				}
				if (found == true) {
					return childnode;
				}
			}
		}
		return null;
	}

	/**
	 * Return the node specified by path, filtered by the attribute name and value.
	 */
	public static String getAttrByAttribute(Node node, String path, String matchAttr, String matchValue, String attrName) {
		Element elem = (Element) getNodeByAttribute(node, path, matchAttr, matchValue);
		if (elem == null)
			return "";
		return elem.getAttribute(attrName);
	}

	/**
	 * Sets an attribute based on a value of another attribute
	 */
	public static void setAttrByAttribute(Node node, String path, String matchAttr, String matchValue, String attrName, String attrValue) {
		Element elem = (Element) getNodeByAttribute(node, path, matchAttr, matchValue);
		if (elem != null)
			elem.setAttribute(attrName, attrValue);
	}

	/**
	 * External interface method that is called by other functions.
	 */
	private static void getAllNodes(Node node, String path, boolean all, List<Node> nodes) {
		if (node != null && logger.isLoggable(java.util.logging.Level.FINER) == true) {
			logger.log(java.util.logging.Level.FINER, "Getting all nodes - Node hash:" + node.hashCode() + "  Path:" + path + "  Contents:[ " + XMLUtil.toSimpleString(node) + " ]");
		}
		getAllNodesIteratively(node, path, all, nodes);
	}

	/**
	 * Worker method that actually gets nodes.
	 */
	private static void getAllNodesIteratively(Node node, String path, boolean all, List<Node> nodes) {
		try {
			String rest = null;
			String p = null;
			if (path != null) {
				int idx = path.indexOf('/');
				if (idx >= 0) {
					p = path.substring(0, idx);
					try {
						rest = path.substring(idx + 1);
					} catch (StringIndexOutOfBoundsException aie) {
						rest = null;
					}
				} else {
					p = path;
					rest = null;
				}
			}

			if (p != null && p.length() > 0 && p.charAt(0) != '@') {
				// Get the element.
				NodeList nl = node.getChildNodes();
				int n = nl.getLength();
				for (int i = 0; i < n; ++i) {
					Node child = nl.item(i);
					if (child != null) {
						if (child.getNodeType() != Node.ELEMENT_NODE || !child.getNodeName().equals(p))
							continue;
						getAllNodesIteratively(child, rest, all, nodes);
						if (!all)
							break;
					}
				}
			} else {
				nodes.add(node);
			}
		} catch (Exception e) {
			logger.log(java.util.logging.Level.SEVERE, "Failed getting all nodes - Node hash:" + node.hashCode() + "  Path:" + path + "  Contents:[ " + XMLUtil.toSimpleString(node) + " ]", e);
		}
	}

	/**
	 * Find all nodes of a tag.
	 */
	public static List<Node> findAllNodes(Node node, String tag, List<Node> nodes) {
		if (nodes == null)
			nodes = new ArrayList<Node>();

		if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(tag)) {
			nodes.add(node);
		}

		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); ++i) {
			Node child = list.item(i);

			if (child == null || child.getNodeType() != Node.ELEMENT_NODE)
				continue;

			findAllNodes(child, tag, nodes);
		}
		return nodes;
	}

	/**
	 * Find all nodes containing the attribute name, or having the attribute with the value.
	 */
	public static List<Node> findAllNodes(Node node, String attrName, String attrValue, List<Node> nodes) {
		if (nodes == null)
			nodes = new ArrayList<Node>();

		if (node.getNodeType() == Node.ELEMENT_NODE) {
			String value = ((Element) node).getAttribute(attrName);
			if (value != null && value.length() > 0) {
				// If attrValue is null, just add the node.
				// Or if the attrValue equals to the value.
				if (attrValue == null || attrValue.equals(value))
					nodes.add(node);
			}
		}

		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); ++i) {
			Node child = list.item(i);

			if (child == null || child.getNodeType() != Node.ELEMENT_NODE)
				continue;

			findAllNodes(child, attrName, attrValue, nodes);
		}
		return nodes;
	}

	/**
	 * Set the attribute of a node, identify by path. E.g. path1/path2/@attribute.
	 *
	 * @param node
	 *            the starting node to search.
	 * @param path
	 *            the path to reach the attribute.
	 * @return string, or null if not found.
	 */
	public static String getAttribute(Node node, String path) throws Exception {
		int index = path.indexOf('@');

		if (index == -1)
			throw new Exception("Malformed path");

		String nodePath = path.substring(0, index);
		String attrName = path.substring(index + 1);

		node = getNode(node, nodePath);
		if (node != null)
			return ((Element) node).getAttribute(attrName);
		else
			return null;
	}

	/**
	 * Get all attributes of a node
	 *
	 * @param node
	 *            the starting node to search.
	 * @param attrMap
	 *            a hash map which stores all the attribute name/value pairs
	 */
	public static void getAttributes(Node node, Map<String, String> attrMap) throws Exception {
		if (!(node instanceof Element)) {
			throw new Exception("Not an element node");
		}

		NamedNodeMap attrs = node.getAttributes();
		if (null == attrs)
			return;

		int size = attrs.getLength();

		for (int i = 0; i < size; i++) {
			Node n = attrs.item(i);
			attrMap.put(n.getNodeName(), n.getNodeValue());
		}
	}

	/**
	 * Set the attribute of a node, identify by path. E.g. path1/path2/@attribute.
	 *
	 * @param node
	 *            the starting node to search.
	 * @param path
	 *            the path to reach the attribute.
	 * @param attrValue
	 *            the value of the attribute to set.
	 */
	public static void setAttribute(Node node, String path, String attrValue) throws Exception {
		int index = path.indexOf('@');

		if (index == -1)
			throw new Exception("Malformed path");

		String nodePath = path.substring(0, index);
		String attrName = path.substring(index + 1);

		node = getNode(node, nodePath);

		if (node != null)
			((Element) node).setAttribute(attrName, attrValue);
		else
			throw new Exception("Path not found");
	}

	/**
	 * Create a new attribute at a node, identified by name and value.
	 *
	 * @param node
	 *            the starting node to add the attribute.
	 * @param attribute
	 *            the attribute name.
	 * @param value
	 *            the value of the attribute to set.
	 */
	public static Node createAttribute(Node node, String attribute, String value) {
		Element elemNode = (Element) node;
		elemNode.setAttribute(attribute, value);
		return (Node) elemNode;
	}

	/**
	 * Create the node tree based on the path if the nodes don't exist.
	 *
	 * @param node
	 *            the base node
	 * @param path
	 *            the path to create underneath the root. e.g. path1/path2/path3
	 */
	public static Node createPath(Node node, String path) {
		return createPath(node, path, false);
	}

	/**
	 * Create the node tree based on the path if the nodes don't exist, or if forced.
	 *
	 * @param node
	 *            the base node
	 * @param path
	 *            the path to create underneath the root. e.g. path1/path2/path3
	 */
	public static Node createPath(Node node, String path, boolean forced) {
		String tag;
		String rest;

		try {
			tag = path.substring(0, path.indexOf('/'));
			rest = path.substring(tag.length() + 1);
		} catch (Exception e) {
			tag = path;
			rest = null;
		}

		if (tag == null || tag.length() == 0 || tag.charAt(0) == '@')
			return node;

		Node child = null;
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); ++i) {
			Node n = list.item(i);
			if (n == null || n.getNodeType() != Node.ELEMENT_NODE)
				continue;

			if (n.getNodeName().equals(tag)) {
				child = n;
				break;
			}
		}

		if ((child == null) || (forced == true)) {
			child = node.getOwnerDocument().createElement(tag);
			node.appendChild(child);
		}

		return createPath(child, rest, forced);
	}

	/**
	 * Merge the DOM trees. All the children of root2 are moved to root1.
	 *
	 * @param roo1
	 *            the first tree.
	 * @param roo2
	 *            the second tree.
	 * @param root1
	 */
	public static Node mergeTree(Node root1, Node root2) {
		NodeList list = root2.getChildNodes();

		while (list.getLength() > 0) {
			Node child = list.item(0);

			child = root2.removeChild(child);
			root1.appendChild(child);
			list = root2.getChildNodes();
		}
		return root1;
	}

	/**
	 * Get list of immediate child nodes.
	 */
	public static List<Node> getChildNodes(Node node, boolean skipTextNodes) {
		List<Node> children = new ArrayList<Node>();
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node child = nodeList.item(i);
			if (skipTextNodes && (child.getNodeType() == Node.TEXT_NODE)) {
				continue;
			}
			children.add(child);
		}
		return children;
	}

	/**
	 * Return the child (text) value, child node, or a list of children nodes.
	 */
	public static Object getValue(Node node) {
		List<Node> children = new ArrayList<Node>();
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node child = nodeList.item(i);
			if (child.getNodeType() == Node.TEXT_NODE) {
				return (String) child.getNodeValue();
			} else {
				children.add(child);
			}
		}

		if (children.size() == 1)
			return children.get(0);
		else
			return children;
	}

	/**
	 * Modifies/replaces the first child's (text) value with the argument.
	 */
	public static void setValue(Node node, String value) {
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node child = nodeList.item(i);
			if (child.getNodeType() == Node.TEXT_NODE) {
				child.setNodeValue(value);
				return; // Set-or-replace the first child only.
			}
		}
	}

	/**
	 * Return a string version of the node (and its children).
	 */
	public static String toString(Node node) {
		StringBuffer sb = new StringBuffer();
		doToFormattedString(node, sb, "  ", "\n", true, 0);
		return sb.toString();
	}

	/**
	 * Return a compact string representation of the node (and its children).
	 */
	public static String toCompactString(Node node) {
		StringBuffer sb = new StringBuffer();
		doToString(node, sb, "", "", true);
		return sb.toString();
	}

	/**
	 * Return a simple, single line string representation of the node (and its children). Warning: Note that this method does not escape node contents.
	 */
	public static String toSimpleString(Node node) {
		if (node == null) {
			logger.log(java.util.logging.Level.WARNING, "Can not process NULL node.");
			return "";
		}

		StringBuffer sb = new StringBuffer();
		try {
			doToSimpleString(node, sb, "", "");
		} catch (Exception e) {
			logger.log(java.util.logging.Level.WARNING, "Failed processing node  " + node.getNodeName() + "=" + node.getNodeValue(), e);
		}
		return sb.toString();
	}

	private static String getIndent(String indent, int howmanyindents) {
		StringBuffer sb = new StringBuffer("");
		for (int i = 1; i <= howmanyindents; i++) {
			sb.append(indent);
		}
		return sb.toString();
	}

	private static void doToFormattedString(Node node, StringBuffer sb, String indent, String nl, boolean escape, int nodeDepth) {
		sb.append(getIndent(indent, nodeDepth) + "<" + node.getNodeName());
		NamedNodeMap attrs = node.getAttributes();
		if (attrs != null) {
			for (int i = 0; i < attrs.getLength(); ++i) {
				String name = attrs.item(i).getNodeName();
				if (escape) {
					sb.append(" " + name + "=\"" + escape(attrs.item(i).getNodeValue()) + "\"");
				} else {
					sb.append(" " + name + "=\"" + attrs.item(i).getNodeValue() + "\"");
				}
			}
		}
		sb.append(">" + nl);

		++nodeDepth;
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); ++i) {
			Node n = children.item(i);
			if (n instanceof Element) {
				doToFormattedString(n, sb, indent + "", nl, escape, nodeDepth);
			} else if (n instanceof Text) {
				String value = ((Text) n).getNodeValue();
				if (value != null && value.trim().length() > 0) {
					if (escape) {
						sb.append(indent + escapeContent(value.trim()));
					} else {
						sb.append(indent + value.trim());
					}
				}
			}
		}
		sb.append(getIndent(indent, nodeDepth - 1) + "</" + node.getNodeName() + ">" + nl);
	}

	private static void doToString(Node node, StringBuffer sb, String indent, String nl, boolean escape) {
		sb.append(indent + "<" + node.getNodeName());
		NamedNodeMap attrs = node.getAttributes();
		if (attrs != null) {
			for (int i = 0; i < attrs.getLength(); ++i) {
				String name = attrs.item(i).getNodeName();
				if (escape) {
					sb.append(" " + name + "=\"" + escape(attrs.item(i).getNodeValue()) + "\"");
				} else {
					sb.append(" " + name + "=\"" + attrs.item(i).getNodeValue() + "\"");
				}
			}
		}
		sb.append(">" + nl);

		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); ++i) {
			Node n = children.item(i);
			if (n instanceof Element) {
				doToString(n, sb, indent + "", nl, escape);
			} else if (n instanceof Text) {
				String value = ((Text) n).getNodeValue();
				if (value != null && value.trim().length() > 0) {
					if (escape) {
						sb.append(indent + escapeContent(value.trim()));
					} else {
						sb.append(indent + value.trim());
					}
				}
			}
		}
		sb.append(indent + "</" + node.getNodeName() + ">" + nl);
	}

	private static void doToSimpleString(Node node, StringBuffer sb, String indent, String nl) {
		sb.append(indent + "<" + node.getNodeName());
		NamedNodeMap attrs = node.getAttributes();
		for (int i = 0; i < attrs.getLength(); ++i) {
			String name = attrs.item(i).getNodeName();
			sb.append(" " + name + "=\"" + escape(attrs.item(i).getNodeValue()) + "\"");
		}
		sb.append(">" + nl);

		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); ++i) {
			Node n = children.item(i);
			if (n instanceof Element) {
				doToSimpleString(n, sb, indent + "", nl);
			} else if (n instanceof CDATASection) {
				String value = ((Text) n).getNodeValue();
				if (value != null && value.trim().length() > 0) {
					sb.append(indent + escapeContent(value.trim()));
				}
			} else if (n instanceof Text) {
				String value = ((Text) n).getNodeValue();
				if (value != null && value.trim().length() > 0) {
					sb.append(indent + escape(value.trim()));
				}
			}
		}
		sb.append(indent + "</" + node.getNodeName() + ">" + nl);
	}

	/**
	 * Escape a string for inclusion in xml.
	 */
	public static String escape(String s) {
		return escapeAttribute(s);
	}

	public static String escapeAttribute(String s) {
		if (s == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); ++i) {
			char c = s.charAt(i);
			switch (c) {
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '&':
					sb.append("&amp;");
					break;
				case '"':
					sb.append("&quot;");
					break;
				case '\'':
					sb.append("&apos;");
					break;
				default:
					sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String escapeContent(String s) {
		return "<![CDATA[" + s + "]]>";
	}

	// Parses a string containing XML and inserts into the root node
	public static Node createElementWithXmlString(Document targetdoc, String xmlString) {
		// Wrap the fragment in an arbitrary element
		Node node = null;
		try {
			// Create a DOM builder and parse the xml
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			if (logger.isLoggable(java.util.logging.Level.FINER) == true) {
				logger.log(java.util.logging.Level.FINER, "DocumentBuilderFactory [createElementWithXmlString] class " + factory.getClass().getName());
			}
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder docbuilder = factory.newDocumentBuilder();
			if (logger.isLoggable(java.util.logging.Level.FINER) == true) {
				logger.log(java.util.logging.Level.FINER, "DocumentBuilder [createElementWithXmlString] class " + docbuilder.getClass().getName());
			}
			Document doc = docbuilder.parse(new InputSource(new StringReader(xmlString)));
			// Import the nodes of the new document into targetdoc so that they
			// will be compatible with targetdoc
			node = targetdoc.importNode(doc.getDocumentElement(), true);
			if (logger.isLoggable(java.util.logging.Level.FINER) == true) {
				logger.log(java.util.logging.Level.FINER, "Node [createElementWithXmlString-node] class " + node.getClass().getName());
			}
		} catch (ParserConfigurationException pce) {
			logger.log(java.util.logging.Level.WARNING, "Parser failure " + xmlString, pce);
		} catch (SAXException saxe) {
			logger.log(java.util.logging.Level.WARNING, "Failed parsing " + xmlString, saxe);
		} catch (Exception e) {
			logger.log(java.util.logging.Level.WARNING, "Failure processing " + xmlString, e);
		}
		return node;
	}

	public static void main(String[] args) {
		boolean asString = false;
		for (int i = 0; i < args.length; ++i) {
			String arg = args[i];
			try {
				if (arg.startsWith("-")) {
					String opt = arg.substring(1);
					if (opt.equals("string")) {
						asString = true;
					}
					continue;
				}
				Node n = null;
				if (asString) {
					String s = readFile(arg);
					n = XMLUtil.parse(s);
				} else {
					n = XMLUtil.parse(new FileInputStream(arg));
				}
				System.out.println(args[i] + ":\n" + XMLUtil.toString(n));
			} catch (Exception e) {
				logger.log(java.util.logging.Level.SEVERE, "Failed.", e);
			}
		}
	}

	private static String readFile(String name) throws Exception {
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = new BufferedReader(new FileReader(name));
		String s;
		while ((s = reader.readLine()) != null) {
			sb.append(s);
		}
		reader.close();
		return sb.toString();
	}

	public static Document newDocument() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		return dbf.newDocumentBuilder().newDocument();
	}
}
