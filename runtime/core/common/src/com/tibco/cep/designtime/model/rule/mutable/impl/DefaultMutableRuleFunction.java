package com.tibco.cep.designtime.model.rule.mutable.impl;


import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.mutable.MutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntity;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleFunction;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;


public class DefaultMutableRuleFunction extends AbstractMutableCompilable implements MutableRuleFunction {


    protected static final ExpandedName XNAME_ACTION_ONLY = ExpandedName.makeName("actionOnly");
    protected static final ExpandedName XNAME_VALIDITY = ExpandedName.makeName("validity");
    protected static final ExpandedName XNAME_ARGUMENT = ExpandedName.makeName("argument");
    protected static final ExpandedName XNAME_ARGUMENTS = ExpandedName.makeName("arguments");
    protected static final ExpandedName XNAME_BODY = ExpandedName.makeName("body");
    protected static final ExpandedName XNAME_RETURN = ExpandedName.makeName("returnType");
    protected static final ExpandedName XNAME_IS_VIRTUAL = ExpandedName.makeName("virtual");


    protected Validity validity;
    protected String m_body;
    protected boolean isVirtual = false;


    public void delete() {
//        removeFromParticipant(m_returnType);
//
//        for (Iterator it = getArguments().values().iterator(); it.hasNext();) {
//            final Symbol symbol = (Symbol) it.next();
//            removeFromParticipant(symbol.getType());
//        }
        notifyListeners();
        super.delete();
    }//delete


    public DefaultMutableRuleFunction(DefaultMutableOntology ontology, DefaultMutableFolder folder, String name) {
        super(ontology, folder, name);
        m_body = "";
        m_returnType = null;
        validity = Validity.ACTION;
    }


    public void setName(String name, boolean renameOnConflict) throws ModelException {
        String oldPath = getFullPath();
        super.setName(name, renameOnConflict);
        String newPath = getFullPath();

        pathChanged(oldPath, newPath);
        notifyListeners();
    }


    public void setFolder(MutableFolder folder) throws ModelException {
        String oldPath = getFullPath();
        m_ontology.setEntityFolder(this, folder);
        String newPath = getFullPath();

        pathChanged(oldPath, newPath);
        notifyListeners();
    }


//    public void addToParticipant(String participantPath) {
//        if (m_ontology == null) {
//            return;
//        }
//
//        MutableRuleParticipant rp = (MutableRuleParticipant) m_ontology.getEntity(participantPath);
//        if (rp == null) {
//            return;
//        }
//
//        String path = getFullPath();
//        rp.addRule(path, path);
//    }
//
//
//    public void removeFromParticipant(String participantPath) {
//        if (m_ontology == null) {
//            return;
//        }
//
//        MutableRuleParticipant rp = (MutableRuleParticipant) m_ontology.getEntity(participantPath);
//        if (rp == null) {
//            return;
//        }
//
//        String path = getFullPath();
//        rp.removeRule(path, path);
//    }


    public Validity getValidity() {
        return validity;
    }


    public void setValidity(Validity actionOnly) {
        validity = actionOnly;
        setCompilationStatus(-1);
        notifyListeners();
        notifyOntologyOnChange();
    }


    public boolean isVirtual() {
        return this.isVirtual;
    }


    public void setVirtual(boolean isVirtual) {
        this.isVirtual = isVirtual;
        if (isVirtual) {
            this.setReturnType(null);
        }
    }


    public String getReturnTypeWithExtension() {
        return m_returnType;
    }


    public Symbols getArguments() {
        return getScope();
    }


    public String getArgumentType(String identifier) {
        final Symbol symbol = this.m_decls.getSymbol(identifier);
        if (null == symbol) {
            return null;
        }
        return symbol.getType();
    }


    public void setArgumentType(String identifier, String type) {
        this.m_decls.put(identifier, type);
    }


    public boolean deleteIdentifier(String identifier) {
        return (null != this.m_decls.remove(identifier));
    }


    public String getBody() {
        return m_body;
    }


    public void setBody(String body) {
        if (body == null) {
            m_body = "";
        } else {
            m_body = body;
        }

        notifyListeners();
        notifyOntologyOnChange();
    }


    public String getConditionText() {
        return "";
    }


    public void setConditionText(String text) {
    }


    public String getActionText() {
        return getBody();
    }


    public void setActionText(String text) {
        setBody(text);
    }


    public XiNode toXiNode(XiFactory factory) {
        return this.toXiNode(factory, "ruleFunction");
    }


    public XiNode toXiNode(XiFactory factory, String name) {
        XiNode root = super.toXiNode(factory, name);
        root.removeAttribute(GUID_NAME);

        root.setAttributeStringValue(AbstractMutableEntity.FOLDER_NAME, getFolderPath());

        root.setAttributeStringValue(XNAME_IS_VIRTUAL, "" + this.isVirtual());

        final XiNode actionNode = root.appendElement(XNAME_VALIDITY);
        actionNode.setStringValue(this.validity.name());

        //check for "void" is to correct for some code that may store this as "void" due to various bugs
        if (m_returnType != null && !m_returnType.equals(void.class.getName())) {
            XiNode returnTypeNode = root.appendElement(XNAME_RETURN);
            returnTypeNode.setStringValue(m_returnType);
        }

        root.appendChild(this.scopeToXiNode(factory, XNAME_ARGUMENTS, XNAME_ARGUMENT));

        XiNode bodyNode = root.appendElement(XNAME_BODY);
        bodyNode.setStringValue(m_body);

        return root;
    }


//    public void pathChanged(String oldPath, String newPath) throws ModelException {
//        super.pathChanged(oldPath, newPath);
//        if (m_ontology == null) {
//            return;
//        }
//
//        for (Iterator it = getArguments().values().iterator(); it.hasNext();) {
//            final Symbol symbol = (Symbol) it.next();
//            final String entityPath = symbol.getType();
//            if (entityPath == null) {
//                continue;
//            }
//
//            updateRuleParticipant(entityPath, oldPath, newPath);
//        }
//
//        updateRuleParticipant(m_returnType, oldPath, newPath);
//    }


//    protected void updateRuleParticipant(String participantPath, String oldPath, String newPath) {
//        MutableRuleParticipant rp = (AbstractMutableRuleParticipant) m_ontology.getEntity(participantPath);
//        if (rp == null) {
//            return;
//        }
//
//        rp.removeRule(oldPath, oldPath);
//        rp.addRule(newPath, newPath);
//    }


    public static DefaultMutableRuleFunction createDefaultRuleFunctionFromNode(XiNode root) throws ModelException {
        final String folder = root.getAttributeStringValue(FOLDER_NAME);
        final String name = root.getAttributeStringValue(NAME_NAME);
        final DefaultMutableFolder rootFolder = new DefaultMutableFolder(null, null, String.valueOf(Folder.FOLDER_SEPARATOR_CHAR));
        final DefaultMutableFolder ruleFnFolder = DefaultMutableOntology.createFolder(rootFolder, folder, false);
        final DefaultMutableRuleFunction ruleFn = new DefaultMutableRuleFunction(null, ruleFnFolder, name);

        // Extended properties
         XiNode extPropsNode = XiChild.getChild(root, EXTENDED_PROPERTIES_NAME);
         ruleFn.setExtendedProperties(createExtendedPropsFromXiNode(extPropsNode));
        ruleFn.load(root, true);

        return ruleFn;
    }


    protected void load(XiNode root, boolean disableChangeNotifications) throws ModelException {
        final String description = root.getAttributeStringValue(DESCRIPTION_NAME);
        this.setDescription(null == description ? "" : description);

        final String validity = XiChild.getString(root, XNAME_VALIDITY);
        Validity validityFound = null;
        for (Validity v : Validity.values()) {
            if (v.name().equals(validity)) {
                validityFound = v;
            }
        }
        if (null != validityFound) {
            this.setValidity(validityFound);
        } else { // For backwards compatibility.
            if (XiChild.getBoolean(root, XNAME_ACTION_ONLY, false)) {
                this.setValidity(Validity.ACTION);
            } else {
                this.setValidity(Validity.CONDITION);
            }
        }

        this.setVirtual(Boolean.valueOf(root.getAttributeStringValue(XNAME_IS_VIRTUAL)));
        //to correct for some repos that had stored this as "void" due to various bugs
        String returnType = XiChild.getString(root, XNAME_RETURN, null);
        if(returnType != null && void.class.getName().equals(returnType)) {
            this.setReturnType(null);
        } else {
            this.setReturnType(returnType);
        }
        this.loadScope(root, XNAME_ARGUMENTS, XNAME_ARGUMENT, disableChangeNotifications);
        this.setBody(XiChild.getString(root, XNAME_BODY));
    }


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.RuleFunction#getBodyLineOffset()
	 */
	@Override
	public CodeBlock getBodyCodeBlock() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.RuleFunction#getScopeLineOffset()
	 */
	@Override
	public CodeBlock getScopeCodeBlock() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.RuleFunction#getSource()
	 */
	@Override
	public String getSource() {
		// TODO Auto-generated method stub
		return null;
	}

    
}
