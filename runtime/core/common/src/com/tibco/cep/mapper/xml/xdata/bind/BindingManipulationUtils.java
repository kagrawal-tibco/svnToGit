package com.tibco.cep.mapper.xml.xdata.bind;

import com.tibco.cep.mapper.xml.xdata.bind.fix.BindingFixerChange;
import com.tibco.cep.mapper.xml.xdata.bind.fix.BindingFixerChangeList;
import com.tibco.cep.mapper.xml.xdata.bind.fix.MissingOutputBindingFixerChange;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualAttributeBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualBindingSupport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualElementBinding;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * Utility methods for manipulating bindings.
 */
public final class BindingManipulationUtils
{
    /**
     * A destructive move operation that removes all of froms children and tos children.
     * @param from
     * @param to
     */
    public static void copyBindingContents(Binding from, Binding to) {
        Binding[] fc = from.getChildren();
        from.removeAllChildren();
        to.removeAllChildren();
        for (int i=0;i<fc.length;i++) {
            to.addChild(fc[i]);
        }
    }

    public static void removeMarkerChildren(Binding node)
    {
        Binding[] fc = node.getChildren();
        for (int i=0;i<fc.length;i++)
        {
            if (fc[i] instanceof MarkerBinding)
            {
                removeFromParent(fc[i]);
            }
        }
    }

    public static void replaceInParent(Binding old, Binding newb)
    {
        Binding p = old.getParent();
        int i = p.getIndexOfChild(old);
        p.setChildAt(i,newb);
    }

    public static void replaceInParent(Binding old, Binding[] newset)
    {
        Binding p = old.getParent();
        int i = p.getIndexOfChild(old);
        p.removeChild(i);
        for (int x=0;x<newset.length;x++)
        {
            p.addChild(x+i,newset[x]);
        }
    }

    public static void insertAfter(Binding original, Binding next) {
        Binding p = original.getParent();
        int i = p.getIndexOfChild(original);
        p.addChild(i,next);
    }

    public static Binding getRoot(Binding binding)
    {
        if (binding.getParent()==null)
        {
            return binding;
        }
        return getRoot(binding.getParent());
    }

    public static Binding getEquivalentBindingInTemplate(Binding old, TemplateBinding newTemplate) {
        TemplateBinding tb = getContainingTemplateBinding(old);
        return getEquivalentBinding(tb,old,newTemplate);
    }

    public static Binding getEquivalentBinding(Binding oldRoot, Binding old, Binding newRoot) {
        if (oldRoot==old) {
            return newRoot;
        } else {
            if (old.getParent()==null) {
                return null;
            }
            Binding newParent = getEquivalentBinding(oldRoot,old.getParent(),newRoot);
            if (newParent==null) {
                return null;
            }
            int ioc = old.getParent().getIndexOfChild(old);
            if (ioc>=newParent.getChildCount()) {
                return null;
            }
            return newParent.getChild(ioc);
        }
    }

    public static TemplateReport getTemplateReportFor(TemplateEditorConfiguration tec, TemplateReportFormulaCache formulaCache, Binding leafBinding)
    {
        // rerun report & compute new ancestor again:
        if (leafBinding==null)
        {
            throw new NullPointerException("Null leaf binding passed in");
        }
        TemplateReportArguments tra = new TemplateReportArguments();
        TemplateReport newReport = TemplateReport.create(tec,formulaCache,tra);
        return getReportFor(newReport,leafBinding);
    }

    /**
     * Inserts 'markers' into the binding tree indicating missing (from schema) pieces.<br>
     * This method is recursive.
     * @return True if any inserted false, otherwise.
     */
    public static boolean insertMarkers(TemplateReport templateReport)
    {
        return insertMarkers(templateReport,true);
    }

    /**
     * Inserts 'markers' into the binding tree indicating missing (from schema) pieces.
     * @param recursive If set, traverses entire tree, otherwise only affects current node.
     * @return True if any inserted, false otherwise.
     */
    public static boolean insertMarkers(TemplateReport templateReport, boolean recursive)
    {
        BindingFixerChangeList cl = new BindingFixerChangeList();
        cl.setIncludeOtherwiseCheck(false);
        cl.setIncludeFormulaErrors(false);
        cl.setIncludeOutputErrors(false);
        cl.setIncludeValueRequired(false);
        cl.setIncludeStructuralErrors(false);
        cl.setIncludeExtendedErrors(false);
        cl.setAutocreateMissingRequired(false);
        // (We only want marker adding)

        boolean r = false;
        cl.run(templateReport);
        for (int i=0;i<cl.size();i++)
        {
            BindingFixerChange bfc = cl.getChange(i);
            if (!recursive && bfc.getTemplateReport()!=templateReport)
            {
                bfc.setDoApply(false);
            }
            else
            {
                if (!recursive && bfc instanceof MissingOutputBindingFixerChange && ((MissingOutputBindingFixerChange)bfc).isPreceding())
                {
                    // In this case, since it's not recursive, we really don't want to add preceding to the root.
                    bfc.setDoApply(false);
                }
                else
                {
                    if (bfc.canApply())
                    {
                        r = true;
                    }
                }
            }
        }
        cl.applyChanges();

        return r;
    }

    public static final TemplateReport getReportFor(TemplateReport root, Binding binding)
    {
        if (binding==null)
        {
            throw new NullPointerException("Null binding passed in");
        }
        if (root.getBinding()==binding)
        {
            return root;
        }
        Binding p = binding.getParent();
        if (p==null)
        {
            throw new RuntimeException("Report binding mismatch, on binding p:\n" + binding + "\nexpected\n" + root.getBinding());
        }
        TemplateReport tr = getReportFor(root,p);
        for (int i=0;i<tr.getChildCount();i++) {
            if (tr.getChild(i).getBinding()==binding) {
                return tr.getChild(i);
            }
        }
        throw new RuntimeException("No child, binding mismatch, on binding p: " + binding + "\nexpected\n" + root.getBinding());
    }

    /**
     * Expands a binding, if appropriate, filling in stub placeholders for the content value.
     * @param report The type-aware report of the binding.
     */
    public static void expandBinding(TemplateReport report) {
        Binding b = report.getBinding();
        /*        if (b.getParent()==null) { // This commented out code didn't say why it was needed & caused problems elsewhere.
            return;
        }*/
        if (b instanceof TemplateBinding) {
            SmSequenceType t = report.getExpectedType();
            if (!SmSequenceTypeSupport.isPreviousError(t) && !SmSequenceTypeSupport.isVoid(t)) {
                // Provided it's not an error or whatever, add it:
                Binding commentForType = new MarkerBinding(t);
                report.getBinding().addChild(commentForType);
            }
        } else {
            if (b instanceof ElementBinding)
            {
                SmSequenceType xt = report.getComputedType();
                if (xt!=null) // can happen in extreme circumstances (element not allowed in context)
                {
                    expandBinding(report.getBinding(),xt,false); // false-> don't add value-ofs
                }
            }
            else
            {
                if (b instanceof MarkerBinding)
                {
                    expandBinding(((MarkerBinding)b));
                }
            }
        }
    }

    public static Binding getPrecedingSibling(Binding binding)
    {
        Binding p = binding.getParent();
        if (p==null)
        {
            return null;
        }
        int ioc = p.getIndexOfChild(binding);
        if (ioc==0)
        {
            return null;
        }
        return p.getChild(ioc-1);
    }

    public static void expandBinding(MarkerBinding binding)
    {
        expandBinding(binding,binding.getMarkerType(),false);
    }

    private static void expandBinding(Binding binding, SmSequenceType type, boolean addValueOfIfRequired) {
        // expand.
        if (type.getTypeCode()==SmSequenceType.TYPE_CODE_REPEATS)
        {
            // We want to skip through repeats because the expanded bindings should be inside that outer context:
            // (i.e. like inside a for-each)
            expandBinding(binding,type.getFirstChildComponent(),addValueOfIfRequired);
            return;
        }
        if (binding.getChildCount()>0)
        {
            return; // already expanded...
        }
        /*WCETODO if (XTypeSupport.isSequence(type)) {
            XType[] sequenceItems = XTypeSupport.flattenSequence(type);
            for (int i=0;i<sequenceItems.length;i++) {

            }
        }*/
        if (type.isAbstractType(true))  // true-> count ur-type as abstract for this purpose.
        {
            // don't expand abstract types; non-sensical since they can't be instantiated.
            return;
        }
        int tc = type.getTypeCode();
        if (tc==SmSequenceType.TYPE_CODE_SEQUENCE || tc==SmSequenceType.TYPE_CODE_CHOICE || tc==SmSequenceType.TYPE_CODE_INTERLEAVE)
        {
            // can't expand these.
            return;
        }
        SmSequenceType seq;
        // Sort of hacky, but it'll be good enough for now.
        boolean isNilled = (binding instanceof VirtualElementBinding) && ((VirtualElementBinding)binding).isExplicitNil();
        if (!isNilled)
        {
            seq = SmSequenceTypeFactory.createSequence(SmSequenceTypeSupport.stripMagicAttributes(type.attributeAxis()),type.typedValue(true),type.childAxis());
        }
        else
        {
            // nillable, only show attributes:
            seq = SmSequenceTypeSupport.stripMagicAttributes(type.attributeAxis());
        }
        SmSequenceType[] types = SmSequenceTypeSupport.extractTermSequence(seq);
        for (int i=0;i<types.length;i++)
        {
            SmSequenceType t = types[i];
            addExpand(binding,t,addValueOfIfRequired);
        }
    }

    private static final void addExpand(Binding on, SmSequenceType t, boolean addValueOfIfRequired)
    {
        if (!SmSequenceTypeSupport.isPreviousError(t))
        {
            if (SmSequenceTypeSupport.isText(t) || SmSequenceTypeSupport.isValueType(t))
            {
                if (addValueOfIfRequired)
                {
                    on.addChild(new ValueOfBinding(BindingElementInfo.EMPTY_INFO,""));
                }
            }
            else
            {
                if (t.getTypeCode()==SmSequenceType.TYPE_CODE_INTERLEAVE)
                {
                    // flatten these out:
                    addExpand(on,t.getFirstChildComponent(),addValueOfIfRequired);
                    addExpand(on,t.getSecondChildComponent(),addValueOfIfRequired);
                }
                else
                {
                    Binding n = new MarkerBinding(t);
                    on.addChild(n);
                }
            }
        }
    }

    public static final TemplateReport getPreviousSibling(TemplateReport report) {
        TemplateReport p = report.getParent();
        if (p==null) {
            return null;
        }
        int ioc = p.getIndexOfChild(report);
        if (ioc<=0) {
            return null;
        }
        return p.getChild(ioc-1);
    }

    public static TemplateBinding getContainingTemplateBinding(Binding on) {
        if (on instanceof TemplateBinding) {
            return (TemplateBinding) on;
        }
        if (on==null) {
            return null;
        }
        return getContainingTemplateBinding(on.getParent());
    }

    /**
     * Retrieves the nth template from the stylesheet.
     * @param stylesheet The stylesheet
     * @param index The zero based index.
     * @return The template, throws index out of bounds if not found.
     */
    public static TemplateBinding getNthTemplate(StylesheetBinding stylesheet, int index)
    {
        int ct = index;
        int at = 0;
        while (at<stylesheet.getChildCount())
        {
            Binding b = stylesheet.getChild(at);
            if (b instanceof TemplateBinding)
            {
                if (ct--==0)
                {
                    return (TemplateBinding) b;
                }
            }
            at++;
        }
        throw new ArrayIndexOutOfBoundsException(index);
    }

    /**
     * If the binding is a template, returns the first non-parameter (or comments, etc.) that makes up the body,
     * otherwise 0.
     * @param b
     * @return The index of the first non-parameter, may be equal to getChildCount on the binding.
     */
    public static int getTemplateFirstNonParameter(Binding b) {
        if (!(b instanceof TemplateBinding)) {
            return 0;
        }
        int cc = b.getChildCount();
        int lastNonParam = 0;
        for (int i=0;i<cc;i++) {
            Binding ch = b.getChild(i);
            if (ch instanceof ParamBinding) {
                lastNonParam = i+1;
            } else {
                if (ch instanceof CommentBinding) {
                    // do nothing, just continue.
                } else {
                    return lastNonParam;
                }
            }
        }
        return lastNonParam;
    }

    /**
     * Removes the selected binding from its parent.<br>
     * Does nothing if there is no parent.
     * @param binding The binding to remove.
     */
    public static void removeFromParent(Binding binding)
    {
        Binding p = binding.getParent();
        if (p==null)
        {
            return;
        }
        int ioc = p.getIndexOfChild(binding);
        p.removeChild(ioc);
    }

    /**
     * Swaps the two bindings (deep).<br>
     * Does nothing if first==second.
     * @param first The first, must have a non-null parent.
     * @param second The second, must have a non-null parent.
     */
    public static void swap(Binding first, Binding second)
    {
        if (first==second)
        {
            return; // do nothing.
        }
        Binding firstParent = first.getParent();
        Binding secondParent = second.getParent();
        if (firstParent==null)
        {
            throw new IllegalArgumentException("Swap, first parent is null");
        }
        if (secondParent==null)
        {
            throw new IllegalArgumentException("Swap, second parent is null");
        }
        int firstIndex = firstParent.getIndexOfChild(first);
        firstParent.removeChild(firstIndex);
//        Binding[] firstChildren = first.getChildren();

        int secondIndex = secondParent.getIndexOfChild(second);
        secondParent.removeChild(secondIndex);
//        Binding[] secondChildren = first.getChildren();

        if (firstIndex>secondIndex && firstParent==secondParent) // in case they have the same parent.
        {
            secondParent.addChild(secondIndex,first);
            firstParent.addChild(firstIndex,second);
        }
        else
        {
            firstParent.addChild(firstIndex,second);
            secondParent.addChild(secondIndex,first);
        }
    }

    /**
     * Does a swap but where the only the nodes themselves is swapped, the children stay in the same place.<br>
     * (This is equivalent to {@link #swap} then {@link #swapChildren}.)
     * @param first The first, must have a non-null parent.
     * @param second The second, must have a non-null parent.
     */
    public static void swapShallow(Binding first, Binding second)
    {
        swap(first,second);
        swapChildren(first,second);
    }

    /**
     * Swaps the children of the bindings.<br>
     * Does nothing if first==second.
     * @param first
     * @param second
     */
    public static void swapChildren(Binding first, Binding second)
    {
        if (first==second)
        {
            return;
        }
        Binding[] firstChildren = first.getChildren();
        Binding[] secondChildren = second.getChildren();
        first.removeAllChildren();
        second.removeAllChildren();
        for (int i=0;i<firstChildren.length;i++)
        {
            second.addChild(firstChildren[i]);
        }
        for (int i=0;i<secondChildren.length;i++)
        {
            first.addChild(secondChildren[i]);
        }
    }

    /**
     * Moves the moved out, pulling its children to its parents, and surrounds first.
     */
    public static void moveAround(Binding first, Binding moved)
    {
        Binding firstParent = first.getParent();
        int firstIndex = firstParent.getIndexOfChild(first);
        firstParent.removeChild(firstIndex);

        removeSaveChildren(moved);

        firstParent.addChild(firstIndex,moved);
        moved.addChild(first);
    }

    /**
     * Removes the binding from its parent, but moves all the children there.
     * @param binding The binding, all children will be removed (and placed in parent slot)
     */
    public static void removeSaveChildren(Binding binding)
    {
        Binding parent = binding.getParent();
        int ioc = parent.getIndexOfChild(binding);
        parent.removeChild(ioc);
        Binding[] children = binding.getChildren();
        binding.removeAllChildren();
        for (int i=0;i<children.length;i++)
        {
            parent.addChild(i+ioc,children[i]);
        }
    }

    /**
     * If the binding is an xsi:type attribute binding, returns the expanded name value, otherwise null.
     * @param b The candidate binding.
     * @return The expanded name of the xsi:type substitution or null.
     */
    public static ExpandedName getXsiTypeSub(Binding b)
    {
        QName qn = getXsiTypeSubQName(b);
        if (qn!=null)
        {
            try
            {
                ExpandedName en = qn.getExpandedName(BindingNamespaceManipulationUtils.createNamespaceImporter(b));
                return en;
            }
            catch (Exception e)
            {
                // never sure if 'getExpandedName' throws or not; just in case, ignore.
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * If the binding is an xsi:type attribute binding, returns the expanded name value, otherwise null.
     * @param b The candidate binding.
     * @return The expanded name of the xsi:type substitution or null.
     */
    public static QName getXsiTypeSubQName(Binding b)
    {
        if (!(b instanceof AttributeBinding))
        {
            return null;
        }
        AttributeBinding ab = (AttributeBinding) b;
        if (!ab.isExplicitXslRepresentation())
        {
            ExpandedName tn = ab.getName();
            if (tn.equals(XSDL.ATTR_TYPE.getExpandedName()))
            {
                String f = ab.getFormula();
                if (AVTUtilities.isAVTString(f))
                {
                    return null;
                }
                return new QName(f);
            }
            return null;
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns a newly created element or attribute for the marker, never null.<br>
     * Otherwise the same as {@link #createAppropriateAttributeOrElementBinding}.
     * @param markerBinding A marker binding
     * @return The new element or attribute binding.
     */
    public static Binding createAppropriateAttributeOrElementBindingNeverNull(MarkerBinding markerBinding)
    {
        Binding r = createAppropriateAttributeOrElementBinding(markerBinding);
        if (r==null)
        {
            // just stick in an element; whatever, we just want to avoid crashing here.
            return new VirtualElementBinding(BindingElementInfo.EMPTY_INFO,ExpandedName.makeName("el"));
        }
        return r;
    }

    /**
     * Returns a newly created element or attribute for the marker, or null, if not an element or attribute.
     * @param markerBinding A marker binding
     * @return The new element or attribute binding, or null, if none.
     */
    public static Binding createAppropriateAttributeOrElementBinding(MarkerBinding markerBinding)
    {
        SmSequenceType t = markerBinding.getMarkerType();
        NamespaceContextRegistry ni = BindingNamespaceManipulationUtils.createNamespaceImporter(markerBinding);
        Binding r = createAppropriateAttributeOrElementOrMarkerBinding(t,ni);
        if (r instanceof MarkerBinding)
        {
            // kind of hacky --- we want to return null here, though.
            return null;
        }
        return r;
    }


    public static Binding createAppropriateAttributeOrElementOrMarkerBinding(SmSequenceType type, NamespaceContextRegistry ni) {
        SmSequenceType contained = SmSequenceTypeSupport.stripOccursAndParens(type);
        int tc = contained.getTypeCode();
        if (tc==SmSequenceType.TYPE_CODE_ELEMENT) {
            ExpandedName n = contained.getName();
            if (n!=null)
            {
                VirtualElementBinding veb = new VirtualElementBinding(BindingElementInfo.EMPTY_INFO, n);
                // Don't want to fill in formula for an 'any' because, although it could have a typed value, it probably doesn't.
                boolean hasTypedValueAndNotAny = SmSequenceTypeSupport.hasTypedValue(contained,true) && !SmSequenceTypeSupport.isAny(contained);
                if (hasTypedValueAndNotAny)
                {
                    veb.setHasInlineFormula(true);
                    veb.setFormula("");
                }
                return veb;
            }
        }
        if (tc==SmSequenceType.TYPE_CODE_ATTRIBUTE) {
            ExpandedName n = contained.getName();
            if (n!=null) {
                String pfx = ni.getOrAddPrefixForNamespaceURI(n.getNamespaceURI());
                if (pfx==null)
                {
                    pfx = "";
                }
                QName qname = new QName(pfx,n.getLocalName());
                VirtualAttributeBinding vab = new VirtualAttributeBinding(BindingElementInfo.EMPTY_INFO,null,qname.toString());
                boolean hasTypedValue = SmSequenceTypeSupport.hasTypedValue(contained,true);
                if (hasTypedValue)
                {
                    vab.setHasInlineFormula(true);
                    vab.setFormula("");
                }
                return vab;
            }
        }
        // it's a marker:
        return new MarkerBinding(type);
    }

    /**
     * Converts a marker into a real element or attribute ALWAYS setting the xpath as a value-of formula.
     * @param onCommentBinding
     * @param xpath
     * @return
     */
    public static Binding convertMarkerCommentToFormula(TemplateReport onCommentBinding, String xpath) {
        return convertMarkerCommentToFormula(onCommentBinding,xpath,false);
    }

    /**
     * Indicates if, as determined by looking at the computed type, the marker should be converted to a for-each or
     * just an element/value-of.  Is true if either it's not a leaf or if it's repeating.
     * @param report
     * @return
     */
    public static boolean isMarkerConvertedToForEach(TemplateReport report)
    {
        boolean isLeaf = !SmSequenceTypeSupport.isVoid(report.getComputedType().typedValue(true));
        boolean isRepeating = report.getComputedType().quantifier().getMaxOccurs()>1;
        return !isLeaf || isRepeating;
    }

    /**
     * Converts a marker into a real element or attribute, if considerForEach is set, will use for-each when the
     * type calls for it (as computed by {@link #isMarkerConvertedToForEach}).
     * @param onCommentBinding
     * @param xpath
     * @return
     */
    public static Binding convertMarkerCommentToFormula(TemplateReport onCommentBinding, String xpath, boolean considerForEach)
    {
        if (onCommentBinding.getBinding() instanceof MarkerBinding)
        {
            Binding b = createAppropriateAttributeOrElementBinding((MarkerBinding)onCommentBinding.getBinding());
            if (b!=null && onCommentBinding.getComputedType()!=null) {
                boolean isForEach = isMarkerConvertedToForEach(onCommentBinding);
                if (isForEach && considerForEach) {
                    Binding fe = new ForEachBinding(BindingElementInfo.EMPTY_INFO,xpath);
                    fe.addChild(b);
                    BindingManipulationUtils.replaceInParent(onCommentBinding.getBinding(),fe);
                    boolean isLeaf = !SmSequenceTypeSupport.isVoid(onCommentBinding.getComputedType().typedValue(true));
                    if (isLeaf)
                    {
                        b.setFormula(".");
                    }
                } else {
                    VirtualBindingSupport.setXPathOn(b,xpath);
                    BindingManipulationUtils.replaceInParent(onCommentBinding.getBinding(),b);
                }
                return b;
            }
        }
        return null;
    }
}
