package com.tibco.cep.studio.mapper.ui.data.bind;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

/**
 * For a type of xslt tag, describes all that is needed for the purpose of GUI display.<br>
 * (i.e. for example, an instance for xsl:for-each describes the icon, display names, editing GUI, etc.)
 * It is designed so that there is only a single instance for a single tag (i.e. for xsl:for-each, there is
 * just a single instance of this that is used for all xsl:for-eaches).
 */
public interface StatementPanel {
   /**
    * Create a new, defaulted binding of the type represented by this panel.
    *
    * @param optionalStartingBinding May be null, may be a binding of <b>arbitrary</b> type.  An implementation may
    *                                choose to look for certain types of bindings to fill in more appropriate defaulted.
    * @return
    */
   public Binding createNewInstance(Binding optionalStartingBinding);

   /**
    * Gets the {@link Binding} subclass that this panel knows about.
    *
    * @return The class, never null.
    */
   @SuppressWarnings("rawtypes")
   public Class getHandlesBindingClass();

   /**
    * Indicates if the type of binding specified can be (ignoring type-checking, etc.) a legal child of this binding type.<br>
    * For example, the 'choose' statement only returns true for 'when', 'otherwise', and 'comment'.
    *
    * @param childBinding A non-null binding to test.  Only the type (class) of the binding should matter.
    * @return true if it can, false if it cannot.
    */
   public boolean canHaveAsChild(Binding binding, Binding childBinding);

   public String getDisplayName();

   public String getDisplayNameFor(TemplateReport report);

   /**
    * Gets the most appropriate cardinality display for this binding.
    *
    * @param report The report containing the binding.
    * @return The most appropriate cardinality, not null, the 'default' should be {@link SmCardinality#EXACTLY_ONE}.
    */
   public SmCardinality getOccurrence(TemplateReport report);

   /**
    * Gets the most appropriate nilled display for this binding.
    *
    * @param report The report containing the binding.
    * @return The most appropriate nilled display, the 'default' should be false.
    */
   public boolean getIsNilled(TemplateReport report);

   /**
    * For a given binding, gets the default thing to add around an it, i.e. for a 'when', it should be 'choose', since
    * that's the only thing that <b>can</b> go around it.
    *
    * @return The default add around binding, or null, for no preference.
    */
   public Binding getDefaultAddAroundBinding();

   /**
    * For a given binding, gets the default thing to add as a child to it, i.e. for a 'choose', it should be 'when', since
    * when and otherwise (and comments) are the only thing that <b>can</b> go inside it.
    *
    * @return The default add around binding, or null, for no preference.
    */
   public Binding getDefaultAddChildBinding();

   /**
    * Gets a display icon appropriate for this type of xslt statement as setup and described in the report, if available.
    * <b>Note:</b> An implementation must support null for the report & provide a default.
    *
    * @param templateReport The report to use, or null if none is available (in which case it should return the 'default')
    * @return The icon for this statement type.
    */
   public Icon getDisplayIcon(TemplateReport templateReport);

   /**
    * Gets the details editor for this type of binding.
    *
    * @param binding
    * @param contextNamespaceContextRegistry The namespace importer for the context this node will be inserted into (the node may or may not already be inserted; it may be an orphan, so the parent context is required)
    * @param outputContext            The output context type for this binding.
    * @param remainderType            A type representing the possible elements/attributes for that position.
    * @return The editor, or null if none.
    */
   public JComponent getDetailsEditor(Binding binding,
                                      NamespaceContextRegistry contextNamespaceContextRegistry,
                                      ImportRegistry importRegistry,
                                      UIAgent uiAgent,
                                      SmSequenceType outputContext,
                                      SmSequenceType remainderType);

   /**
    * Constant indicating binding has no associated editable field.
    * This a constant for {@link #getEditableFieldType(com.tibco.xml.xdata.bind.TemplateReport)}.
    */
   public static final int FIELD_TYPE_NOT_EDITABLE = 0;

   /**
    * Constant indicating binding has an associated XPath formula.
    * This a constant for {@link #getEditableFieldType(com.tibco.xml.xdata.bind.TemplateReport)}.
    */
   public static final int FIELD_TYPE_XPATH = 1;

   /**
    * Constant indicating binding has an associated constant value.
    * This a constant for {@link #getEditableFieldType(com.tibco.xml.xdata.bind.TemplateReport)}.
    */
   public static final int FIELD_TYPE_CONSTANT = 2;

   /**
    * Constant indicating binding has an associated comment value.
    * This a constant for {@link #getEditableFieldType(com.tibco.xml.xdata.bind.TemplateReport)}.
    */
   public static final int FIELD_TYPE_COMMENT = 3;

   /**
    * Constant indicating binding has an associated attribute value template.
    * This a constant for {@link #getEditableFieldType(com.tibco.xml.xdata.bind.TemplateReport)}.
    */
   public static final int FIELD_TYPE_ATTRIBUTE_VALUE_TEMPLATE = 4;

   /**
    * Indicates if this has an associated editable field (i.e. select, value, etc.),
    * returns one of the constants,
    * {@link #FIELD_TYPE_NOT_EDITABLE},
    * {@link #FIELD_TYPE_COMMENT},
    * {@link #FIELD_TYPE_CONSTANT},
    * {@link #FIELD_TYPE_XPATH}, or
    * {@link #FIELD_TYPE_ATTRIBUTE_VALUE_TEMPLATE}.
    */
   public int getEditableFieldType(TemplateReport templateReport);

   /**
    * Indicates if this branch is a logical leaf (as configured now).
    * Implementations should <b>not</b> check if they have any children, etc., as that's not the intent of this call.
    *
    * @param templateReport
    * @return
    */
   public boolean isLeaf(TemplateReport templateReport);
}
