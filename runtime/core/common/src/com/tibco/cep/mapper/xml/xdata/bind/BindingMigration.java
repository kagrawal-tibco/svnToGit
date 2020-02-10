/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.HashMap;
import java.util.Iterator;

import com.tibco.cep.mapper.xml.nsutils.NamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.UtilitySchema;
import com.tibco.cep.mapper.xml.xdata.bind.fix.BindingFixerChangeList;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.TypeCopyOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualDataComponentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualElementMatcher;
import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprUtilities;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionNamespace;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionResolver;
import com.tibco.cep.mapper.xml.xdata.xpath.Lexer;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.ForExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.func.CurrentDateTimeWithTimeZoneXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.CurrentDateTimeXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.GetCenturyFromDateTimeXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.GetCenturyFromDateXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.GetDayFromDateTimeXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.GetDayFromDateXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.GetHourFromDateTimeXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.GetHourFromTimeXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.GetMinutesFromDateTimeXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.GetMinutesFromTimeXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.GetMonthFromDateTimeXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.GetMonthFromDateXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.GetSecondsFromDateTimeXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.GetSecondsFromTimeXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.GetTimeZoneFromDateTimeXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.GetTimeZoneFromDateXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.GetTimeZoneFromTimeXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.GetYearFromDateTimeXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.GetYearFromDateXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.IfAbsentXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibXPath20Functions;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibcoExtendedFunctionNamespace;
import com.tibco.cep.mapper.xml.xdata.xpath.func.XFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.XPath20FunctionNamespace;
import com.tibco.cep.mapper.xml.xdata.xpath.type.XTypeComparator;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContext;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmDataComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.helpers.NoNamespace;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * Migrates older bindings into 5.0
 */
public class BindingMigration
{
    public static void fix(TemplateEditorConfiguration bindingTemplate, int sourceVersionNumber)
    {
        fix(bindingTemplate,BindingNamespaceManipulationUtils.createNamespaceImporter(bindingTemplate.getBinding()),sourceVersionNumber);
    }

    public static void fix(TemplateEditorConfiguration bindingTemplate, NamespaceContextRegistry ni, int sourceVersionNumber)
    {
        if (bindingTemplate.getExprContext().getInputSchemaProvider()==null)
        {
            throw new NullPointerException("Null provider");
        }
        removeBogusEmptyRoot(bindingTemplate.getBinding());
        adjust10StyleFor(bindingTemplate.getBinding());
        fixXSINil(bindingTemplate.getBinding(),ni,sourceVersionNumber);
        fixXSIType(bindingTemplate.getBinding(),ni,sourceVersionNumber);

        fixExtraneousIfValueOfs(bindingTemplate.getBinding());

        // 1.0 & 2.0 were lenient about this -- if the root element name changed, it would auto-fix; 5.0 doesn't.
        fixWrongRootElement(bindingTemplate.getBinding(),bindingTemplate.getExpectedOutput(),ni);

        TemplateReportFormulaCache fc = new TemplateReportFormulaCache();
        BindingMigration.adjustXalanizedFormulas(bindingTemplate.getBinding(),ni,bindingTemplate.getFunctions());

        BindingMigration.adjustPrefixes(bindingTemplate,fc,ni,sourceVersionNumber);

        BindingMigration.adjustNewFormulaNamespaces(bindingTemplate.getBinding(),ni,bindingTemplate.getFunctions());
        BindingMigration.cleanupOldStyleTypeCopyOfs(bindingTemplate.getBinding());

        BindingMigration.removeGlobalVarIfs(bindingTemplate.getBinding());

        BindingMigration.fixRepoDocumentReferences(bindingTemplate.getBinding());

        TemplateReportArguments args0 = new TemplateReportArguments();
        args0.setRecordingMissing(true);
        TemplateReport report = TemplateReport.create(bindingTemplate,fc,args0);

        fixRepeatingTokenize(report,sourceVersionNumber);

        // rerun:
        TemplateReportArguments args = new TemplateReportArguments();
        args.setRecordingMissing(true);
        args.setCheckForMove(true); // need moves here, too! (though not renames)
        report = TemplateReport.create(bindingTemplate,fc,args);

        BindingFixerChangeList changeList = new BindingFixerChangeList();
        changeList.setIncludeOtherwiseCheck(false);
        changeList.setAutocreateMissingRequired(false);
        changeList.run(report);
        /* do not do this; too dangerous; for (int i=0;i<changeList.size();i++)
        {
            // apply all of them (including delete) --- for those that can't be applied, this line does nothing.
            changeList.getChange(i).setDoApply(true);
        }*/
        changeList.applyChanges();

        boolean hadAny = stripIgnoreComments(bindingTemplate.getBinding());
        adjustAutofill(bindingTemplate,fc,sourceVersionNumber,hadAny);
        // subtle difference in formatting of doubles and floats can cause problems in 5.0, add a round when a

        report = TemplateReport.create(bindingTemplate,fc,args);
        addMiscConversions(report);

        fixBadRootCopyOf(report);

        // Because in 2.0 'dead' (i.e. unmatched bindings) were pruned implicitly, for backward compatibility we
        // need to do this once during migration --- but from here on out, there is no more implicit pruining.
        pruneUnexpected(bindingTemplate, fc);
    }

    /**
     * Does one final pass &, for each element, makes sure that the namespace/prefix declaration is added.
     * @param ni The namespace importer.
     * @param b The binding.
     */
    public static void ensureNamespaceDeclarations(NamespaceContextRegistry ni,Binding b)
    {
        if (b==null)
        {
            return;
        }
        if (b instanceof ElementBinding)
        {
            ExpandedName n = b.getName();
            if (n!=null && !NoNamespace.isNoNamespaceURI(n.getNamespaceURI()))
            {
                ni.getOrAddPrefixForNamespaceURI(n.getNamespaceURI());
            }
        }
        for (int i=0;i<b.getChildCount();i++)
        {
            ensureNamespaceDeclarations(ni,b.getChild(i));
        }
    }

    private static void pruneUnexpected(TemplateEditorConfiguration bindingTemplate, TemplateReportFormulaCache fc)
    {
        // Currently off - we leave them here so that 'phantom' mappings can re-appear, but must resilient in case of bad schema loading.
        TemplateReportArguments args1 = new TemplateReportArguments();
        args1.setRecordingMissing(false); // o.w. will get markers.
        TemplateReport report2 = TemplateReport.create(bindingTemplate,fc,args1);
        BindingFixerChangeList fcl = new BindingFixerChangeList();
        fcl = new BindingFixerChangeList();
        fcl.setAutocreateMissingRequired(false);
        fcl.run(report2);
        if (fcl.size()>0)
        {
            for (int i=0;i<fcl.size();i++)
            {
                if (fcl.getChange(i).canApply())
                {
                    fcl.getChange(i).applyNonMove();
                }
            }
        }
        // seem to be getting put in (by above) sometimes anyway, remove 'em.
        stripMarkers(bindingTemplate.getBinding());
    }

    private static void stripMarkers(Binding b)
    {
        for (int i=b.getChildCount()-1;i>=0;i--)
        {
            Binding t = b.getChild(i);
            stripMarkers(t);
            if (t instanceof MarkerBinding)
            {
                b.removeChild(i);
            }
        }
    }

    /**
     * Some BW 1.0, 2.0 activities wound up with a marker 'empty' input in certain circumstances (i.e. not totally configured).
     * If we have a root containing just that, just chuck it.
     * @param tb
     */
    private static void removeBogusEmptyRoot(TemplateBinding tb)
    {
        if (tb.getChildCount()==1 && tb.getChild(0) instanceof ElementBinding)
        {
            ElementBinding eb = (ElementBinding) tb.getChild(0);
            ExpandedName emptyMarker = ExpandedName.makeName("www.tibco.com/tnt/utilitySchema","empty");
            if (emptyMarker.equals(eb.getName()) || UtilitySchema.EMPTY_ELEMENT.getExpandedName().equals(eb.getName()))
            {
                // Gone!
                tb.removeChild(0);
            }
        }
        // otherwise, leave it alone.
    }

    /**
     * BW 1.0 wrote XPath 2.0 functions as XSLT; migrate to just plain old XPath 2.0
     * @param binding
     */
    private static void adjust10StyleFor(Binding binding)
    {
        String formula = check20LeafFormula(binding);
        if (formula!=null) {
            binding.removeAllChildren();
            binding.addChild(new ValueOfBinding(BindingElementInfo.EMPTY_INFO,formula));
        }

        for (int i=0;i<binding.getChildCount();i++)
        {
            adjust10StyleFor(binding.getChild(i));
        }
    }

    /**
     * Actually strips all comments since the user couldn't add comments:
     * @param tb A binding.
     * @return true if any found, false otherwise.
     */
    private static boolean stripIgnoreComments(Binding tb)
    {
        boolean any = false;
        for (int i=0;i<tb.getChildCount();)
        {
            Binding ch = tb.getChild(i);
            if (ch instanceof CommentBinding)
            {
                int ioc = tb.getIndexOfChild(ch);
                tb.removeChild(ioc);
                CommentBinding cb = (CommentBinding) ch;
                if (cb.getComment()!=null && cb.getComment().trim().startsWith("ignore"))
                {
                    any = true;
                }
                // continue.
            }
            else
            {
                if (stripIgnoreComments(tb.getChild(i)))
                {
                    any = true;
                }
                i++;
            }
        }
        return any;
    }

    private static void adjustAutofill(TemplateEditorConfiguration bindingTemplate, TemplateReportFormulaCache fc, int sourceVersionNumber, boolean hadAnyAutofill)
    {
        // not required for >1
        if (sourceVersionNumber>1)
        {
            return;
        }
        if (!hadAnyAutofill)
        {
            TemplateReportArguments args = new TemplateReportArguments();
            TemplateReport report = TemplateReport.create(bindingTemplate,fc,args);
            if (hasEvidenceOfAutofill(report))
            {
                hadAnyAutofill = true;
            }
        }
        for (int i=0;i<10;i++) // must iterate because of how this works; don't iterate too many times to avoid potential infinite explosion...
        {
            TemplateReportArguments args = new TemplateReportArguments();
            TemplateReport report = TemplateReport.create(bindingTemplate,fc,args);
            boolean any = adjustAutofillInternal(report,hadAnyAutofill);
            if (!any)
            {
                break;
            }
        }
    }

    /**
     * More or greater hacks for detecting auto-fill in 1.0 (Siebel only, really)
     */
    private static boolean hasEvidenceOfAutofill(TemplateReport report)
    {
        Binding b = report.getBinding();
        if (b instanceof ElementBinding && b.getChildCount()==1 && b.getChild(0) instanceof ValueOfBinding)
        {
            String f = b.getChild(0).getFormula();
            if (b.getParent()!=null && b.getParent().getFormula()!=null)
            {
                // would it have been auto-filled?
                SmSequenceType xt = report.getContext().getInput().childAxis().nameTest(b.getName());
                if (xt!=null && !SmSequenceTypeSupport.isPreviousError(xt))
                {
                    if (xt.getParticleTerm() instanceof SmDataComponent)
                    {
                        String name = b.getName().getLocalName();
                        String ns = xt.getParticleTerm().getNamespace();
                        // handle namespaced things:
                        String testFormula;
                        if (ns!=null && ns.length()>0)
                        {
                            String prefix = report.getContext().getNamespaceMapper().getOrAddPrefixForNamespaceURI(ns);
                            testFormula = prefix + ":" + name;
                        }
                        else
                        {
                            testFormula = name;
                        }
                        if (f.equals(testFormula))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        int cc = report.getChildCount();
        // traverse into children:
        for (int i=0;i<cc;i++)
        {
            if (hasEvidenceOfAutofill(report.getChild(i)))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the 'round' function around float->int where, because of a formatting change in floats, we are incompatible.
     */
    private static void addMiscConversions(TemplateReport report)
    {
        Binding b = report.getBinding();
        if (b instanceof ValueOfBinding)
        {
            SmSequenceType etype = report.getExpectedType();
            SmSequenceType ctype = report.getFormulaType();
            if (etype!=null && ctype!=null)
            {
                if (SmSequenceTypeSupport.isIntegerNumber(etype))
                {
                    SmSequenceType tv = ctype.typedValue(false); // false -> 1.0 mode.
                    if (!SmSequenceTypeSupport.isNumber(tv) && !SmSequenceTypeSupport.isIntegerNumber(tv.encodedValue()) && SmSequenceTypeSupport.isNumber(tv.encodedValue()))
                    {
                        // need to add a round.
                        report.getBinding().setFormula("round(" + report.getBinding().getFormula() + ")");
                    }
                }
            }
        }
        // recurse:
        for (int i=0;i<report.getChildCount();i++)
        {
            addMiscConversions(report.getChild(i));
        }
    }

    /**
     * Found this problem in net-app poweron; looks like a 2.0.X mapper bug (only occurs on the root, probably only for choice),
     * simple enough to fix.
     * Look for a copy-of inside a root-choose where the copy is generating the only-child of the root instead of the root itself.
     */
    private static void fixBadRootCopyOf(TemplateReport report)
    {
        if (report.getChildCount()==1 && report.getChild(0).getBinding() instanceof ChooseBinding)
        {
            TemplateReport cb = report.getChild(0);
            for (int i=0;i<cb.getChildCount();i++)
            {
                TemplateReport c = cb.getChild(i);
                if (c.getChildCount()==2) // 1 copy-of, 1 marker.
                {
                    TemplateReport cc = c.getChild(0);
                    if (cc.getBinding() instanceof CopyOfBinding)
                    {
                        SmSequenceType ft = cc.getFormulaType().prime();
                        if (ft!=null && ft.getParticleTerm() instanceof SmElement)
                        {
                            SmElement el = (SmElement) ft.getParticleTerm();
                            SmParticleTerm oc = c.getOutputContext().getParticleTerm();
                            if (c.getOutputContext().childAxis().getParticleTerm() instanceof SmElement && oc instanceof SmElement)
                            {
                                SmElement outEl = (SmElement) cc.getOutputContext().childAxis().getParticleTerm();
                                if (outEl.getExpandedName().equals(el.getExpandedName()))
                                {
                                    // this is the case:
                                    Binding e = new ElementBinding(BindingElementInfo.EMPTY_INFO,oc.getExpandedName());
                                    // Just throw the copy-of inside the correct element:
                                    BindingManipulationUtils.replaceInParent(cc.getBinding(),e);
                                    e.addChild(cc.getBinding());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean adjustAutofillInternal(TemplateReport report, boolean hadAnyAutofill)
    {
        // missaved copy of:
        if (shouldBeCopyOf(report))
        {
            Binding p = report.getBinding();
            Binding cc = new CopyOfBinding(BindingElementInfo.EMPTY_INFO,p.getFormula());
            BindingManipulationUtils.replaceInParent(p,cc);
            return true;
        }
        if (shouldBeTypeCopyOf(report))
        {
            Binding p = report.getBinding();
            ElementBinding eb = (ElementBinding) report.getChild(0).getBinding();
            TypeCopyOfBinding cc = new TypeCopyOfBinding(BindingElementInfo.EMPTY_INFO,eb.getName());
            cc.setFormula(p.getFormula());
            Binding nv = cc.normalize(null);
            BindingManipulationUtils.replaceInParent(p,nv);
            return true;
        }
        if (isTypeCopyOf(report))
        {
            // no further work.
            return false;
        }
        boolean any = false;
        Binding b = report.getBinding();

        if (hadAnyAutofill)
        {
            // Since version 1.1.4 (Siebel) had auto-fill off, too, only fix autofill if there were any in the same mapping.
            if (autoFillNode(b, report))
            {
                any = true;
            }
        }
        int cc = report.getChildCount();
        if (cc==0)
        {
            // We need to try to expand any structures to check for autofill.
            BindingManipulationUtils.expandBinding(report);
            if (b.hasChildren())
            {
                any = true;
            }
        }
        // traverse into children:
        for (int i=0;i<cc;i++)
        {
            if (adjustAutofillInternal(report.getChild(i),hadAnyAutofill))
            {
                any = true;
            }
        }
        return any;
    }

    private static boolean autoFillNode(Binding b, TemplateReport report)
    {
        if (b instanceof MarkerBinding && report.getComputedType().prime().getParticleTerm() instanceof SmElement)
        {
            // would it have been auto-filled?
            // (Add attr test.
            SmElement el = (SmElement) report.getComputedType().prime().getParticleTerm();
            ExpandedName n = ExpandedName.makeName("*",el.getName());
            SmSequenceType inputChild = report.getContext().getInput().childAxis();
            SmSequenceType xt = inputChild.nameTest(n);
            if (b.getParent() instanceof ElementBinding && b.getParent().getParent() instanceof ForEachBinding)
            {
                if (!SmSequenceTypeSupport.isVoid(xt) && !isAutofillAbstract(report.getComputedType()))
                {
                    SmSequenceType pt = xt.prime();
                    if (pt.getParticleTerm() instanceof SmElement)
                    {
                        ExpandedName ename = pt.getName();
                        NamespaceContextRegistry ni = BindingNamespaceManipulationUtils.createNamespaceImporter(b);
                        QName qn = NamespaceManipulationUtils.getOrAddQNameFromExpandedName(ename,ni);
                        if (SmSequenceTypeSupport.isLeafDataComponent(report.getComputedType(),true))
                        {
                            Binding root;
                            if (report.getComputedType().quantifier().getMaxOccurs()>1)
                            {
                                root = new ForEachBinding(BindingElementInfo.EMPTY_INFO,qn.toString());
                                ElementBinding eb = new ElementBinding(BindingElementInfo.EMPTY_INFO,el.getExpandedName());
                                eb.addChild(new ValueOfBinding(BindingElementInfo.EMPTY_INFO,"."));
                                root.addChild(eb);
                            }
                            else
                            {
                                if (report.getComputedType().quantifier().getMinOccurs()==0)
                                {
                                    root = new IfBinding(BindingElementInfo.EMPTY_INFO,qn.toString());
                                    Binding elb = new ElementBinding(BindingElementInfo.EMPTY_INFO,el.getExpandedName());
                                    root.addChild(elb);
                                    elb.addChild(new ValueOfBinding(BindingElementInfo.EMPTY_INFO,qn.toString()));
                                }
                                else
                                {
                                    root = new ElementBinding(BindingElementInfo.EMPTY_INFO,el.getExpandedName());
                                    root.addChild(new ValueOfBinding(BindingElementInfo.EMPTY_INFO,qn.toString()));
                                }
                            }
                            BindingManipulationUtils.replaceInParent(b,root);
                        }
                        else
                        {
                            SmSequenceType l = xt.prime();
                            SmSequenceType r = report.getComputedType().prime();
                            if (XTypeComparator.compareAssignability(l,r)==XTypeComparator.EQUALS)
                            {
                                CopyOfBinding feb = new CopyOfBinding(BindingElementInfo.EMPTY_INFO,qn.toString());
                                BindingManipulationUtils.replaceInParent(b,feb);
                            }
                            else
                            {
                                ForEachBinding feb = new ForEachBinding(BindingElementInfo.EMPTY_INFO,qn.toString());
                                ElementBinding eb = new ElementBinding(BindingElementInfo.EMPTY_INFO,el.getExpandedName());
                                feb.addChild(eb);
                                BindingManipulationUtils.replaceInParent(b,feb);
                            }
                        }
                        return true;
                    }
                }
            }
        }
        if (b instanceof MarkerBinding && report.getComputedType().prime().getParticleTerm() instanceof SmAttribute)
        {
            // would it have been auto-filled?
            SmAttribute el = (SmAttribute) report.getComputedType().prime().getParticleTerm();
            ExpandedName n = ExpandedName.makeName("*",el.getName());
            SmSequenceType inputChild = SmSequenceTypeSupport.stripMagicAttributes(report.getContext().getInput().attributeAxis());
            SmSequenceType xt = inputChild.nameTest(n);
            if (!SmSequenceTypeSupport.isVoid(xt))
            {
                SmSequenceType pt = xt.prime();
                if (pt.getParticleTerm() instanceof SmAttribute)
                {
                    ExpandedName ename = pt.getName();
                    NamespaceContextRegistry ni = BindingNamespaceManipulationUtils.createNamespaceImporter(b);
                    QName qn = NamespaceManipulationUtils.getOrAddQNameFromExpandedName(ename,ni);
                    String form = "@" + qn.toString();
                    Binding root;
                    ExpandedName aname = el.getExpandedName();
                    String attrName = NamespaceManipulationUtils.getOrAddQNameFromExpandedName(aname,ni).toString();
                    if (report.getComputedType().quantifier().getMinOccurs()==0)
                    {
                        root = new IfBinding(BindingElementInfo.EMPTY_INFO,form);
                        AttributeBinding atb = new AttributeBinding(BindingElementInfo.EMPTY_INFO,null,attrName);
                        root.addChild(atb);
                        atb.addChild(new ValueOfBinding(BindingElementInfo.EMPTY_INFO,form));
                    }
                    else
                    {
                        root = new AttributeBinding(BindingElementInfo.EMPTY_INFO,null,attrName);
                        root.addChild(new ValueOfBinding(BindingElementInfo.EMPTY_INFO,form));
                    }
                    BindingManipulationUtils.replaceInParent(b,root);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * For autofill, don't want to auto-fill in anys:
     */
    private static boolean isAutofillAbstract(SmSequenceType t)
    {
        SmType st = SmSequenceTypeSupport.getSchemaType(t);
        return (st==null || st.isAbstract() || st==XSDL.ANY_TYPE);
    }

    private static boolean shouldBeCopyOf(TemplateReport report)
    {
        if (!(report.getBinding() instanceof ForEachBinding))
        {
            return false;
        }
        if (report.getChildCount()!=1)
        {
            return false;
        }
        if (!(report.getChild(0).getBinding() instanceof ElementBinding))
        {
            return false;
        }
        TemplateReport ebr = report.getChild(0);
        if (hasChildContent(ebr))
        {
            return false;
        }

        // Could be this way (i.e. t-tests -c xml -t 1.2):
        SmSequenceType iot = report.getInitialOutputType();
        if (SmSequenceTypeSupport.isSequence(iot))
        {
            iot = SmSequenceTypeSupport.getAllSequences(iot)[0];
        }
        SmParticleTerm p1 = iot.prime().getParticleTerm();
        SmParticleTerm p2 = report.getChildContext().getInput().prime().getParticleTerm();
        if (checkParticleTermCopyOf(p1, p2))
        {
            return true;
        }
        // Or, otherwise, could be this way:
        p1 = ebr.getComputedType().getParticleTerm();
        p2 = ebr.getContext().getInput().prime().getParticleTerm();
        return checkParticleTermCopyOf(p1, p2);
    }

    private static boolean checkParticleTermCopyOf(SmParticleTerm p1, SmParticleTerm p2)
    {
        if (p1==null)
        {
            return false;
        }
        if (p1==p2)
        {
            return true;
        }
        if (p1 instanceof SmElement && p2 instanceof SmElement)
        {
            if (p1.getExpandedName().equals(p2.getExpandedName()))
            {
                // total hack; auto-fill was FUBAR; avoid overly agressive copy-ofs with no-namespace.
                if (!NoNamespace.isNoNamespaceURI(p1.getNamespace()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean shouldBeTypeCopyOf(TemplateReport report)
    {
        if (!(report.getBinding() instanceof ForEachBinding))
        {
            return false;
        }
        if (report.getChildCount()!=1)
        {
            return false;
        }
        if (!(report.getChild(0).getBinding() instanceof ElementBinding))
        {
            return false;
        }
        TemplateReport ebr = report.getChild(0);
        if (hasChildContent(ebr))
        {
            return false;
        }
        SmParticleTerm p1 = ebr.getComputedType().getParticleTerm();
        SmParticleTerm p2 = ebr.getContext().getInput().prime().getParticleTerm();
        if (p1==null)
        {
            return false;
        }
        if (p1==p2)
        {
            return true;
        }
        if (p1 instanceof SmElement && p2 instanceof SmElement)
        {
            SmType t1 = ((SmElement)p1).getType();
            SmType t2 = ((SmElement)p2).getType();

            if (t1!=null && t2!=null && t1.getExpandedName()!=null)
            {
                ExpandedName e1 = t1.getExpandedName();
                ExpandedName e2 = t2.getExpandedName();
                return e1.equals(e2) && !NoNamespace.isNoNamespaceURI(e1.getNamespaceURI());
            }
        }
        return false;
    }

    private static boolean hasChildContent(TemplateReport report)
    {
        if (report.getBinding() instanceof VirtualDataComponentBinding)
        {
            VirtualDataComponentBinding vdc = (VirtualDataComponentBinding) report.getBinding();
            if (vdc.getHasInlineFormula())
            {
                return true;
            }
        }
        if (report.getBinding() instanceof ValueOfBinding)
        {
            return true;
        }
        if (report.getBinding() instanceof ForEachBinding)
        {
            return true;
        }
        if (report.getBinding() instanceof IfBinding)
        {
            return true;
        }
        if (report.getBinding() instanceof ChooseBinding)
        {
            return true;
        }
        if (report.getBinding() instanceof CopyOfBinding)
        {
            return true;
        }
        for (int i=0;i<report.getChildCount();i++)
        {
            if (hasChildContent(report.getChild(i)))
            {
                return true;
            }
        }
        return false;
    }

    private static void adjustPrefixes(TemplateEditorConfiguration bindingTemplate, TemplateReportFormulaCache fc, NamespaceContextRegistry ni, int sourceVersionNumber)
    {
        if (sourceVersionNumber>1)
        {
            return;
        }
        for (int i=0;i<25;i++)
        {
            // Must run several times because if a for-each had a missing prefixes, child reports would be bad until after the
            // fix.

            // hacky, need to clean up namespace importer versus namespace context, for now, just copy all decls here:
            NamespaceManipulationUtils.addAllNamespaceDeclarations(BindingNamespaceManipulationUtils.createNamespaceImporter(bindingTemplate.getBinding().getParent()),ni);

            TemplateReport report = TemplateReport.create(bindingTemplate,fc,new TemplateReportArguments());
            boolean change = adjustPrefixesInternal(report,ni);
            if (!change)
            {
                return;
            }
        }
    }

    private static boolean adjustPrefixesInternal(TemplateReport report, NamespaceContextRegistry ni)
    {
        boolean change = false;
        if (report.getBinding().getFormula()!=null)
        {
            // add prefixes registrations where they weren't before:
            EvalTypeInfo info = new EvalTypeInfo();
            Expr ne = Parser.parse(report.getBinding().getFormula());
            info.setFixCustomFunctionNamespaces(true);
            ne.evalType(report.getContext(),info);
            Expr ne1 = ExprUtilities.encodeNamespaces(ne,report.getContext(),ni);
            String nf = ne1.toExactString();
            if (!nf.equals(report.getBinding().getFormula()))
            {
                change = true;
                report.getBinding().setFormula(ne1.toExactString());
            }
        }
        for (int i=0;i<report.getChildCount();i++)
        {
            if (adjustPrefixesInternal(report.getChild(i),ni))
            {
                change = true;
            }
        }
        return change;
    }

    public static HashMap RENAMED_FUNCTIONS = new HashMap();

    static
    {
        // Add all 2.0 functions in the old ns into the no-namespace:
        // (Subsequent adds may overwrite these, though, see below)
        FunctionNamespace ns20 = XPath20FunctionNamespace.INSTANCE;
        Iterator tf20 = ns20.getFunctions();
        while (tf20.hasNext())
        {
            XFunction fn = (XFunction) tf20.next();
            String ln = fn.getName().getLocalName();
            RENAMED_FUNCTIONS.put(ExpandedName.makeName(TibXPath20Functions.OLD_NAMESPACE,ln),ExpandedName.makeName(ln));
        }


        addRenamedOld20ns("if-absent",IfAbsentXFunction.NAME);

        // All the date/time functions move to our namespace because the spec is up in the air right now:
        addRenamedOld20ns("get-Century-from-dateTime",GetCenturyFromDateTimeXFunction.NAME);
        addRenamedOld20ns("get-Century-from-date",GetCenturyFromDateXFunction.NAME);
        addRenamedOld20ns("get-Year-from-dateTime",GetYearFromDateTimeXFunction.NAME);
        addRenamedOld20ns("get-Year-from-date",GetYearFromDateXFunction.NAME);
        addRenamedOld20ns("get-year-from-dateTime",GetYearFromDateTimeXFunction.NAME);
        addRenamedOld20ns("get-year-from-date",GetYearFromDateXFunction.NAME);

        addRenamedOld20ns("get-month-from-dateTime",GetMonthFromDateTimeXFunction.NAME);
        addRenamedOld20ns("get-month-from-date",GetMonthFromDateXFunction.NAME);

        addRenamedOld20ns("get-day-from-dateTime",GetDayFromDateTimeXFunction.NAME);
        addRenamedOld20ns("get-day-from-date",GetDayFromDateXFunction.NAME);

        addRenamedOld20ns("get-hour-from-dateTime",GetHourFromDateTimeXFunction.NAME);
        addRenamedOld20ns("get-hour-from-time",GetHourFromTimeXFunction.NAME);

        addRenamedOld20ns("get-minutes-from-dateTime",GetMinutesFromDateTimeXFunction.NAME);
        addRenamedOld20ns("get-minutes-from-time",GetMinutesFromTimeXFunction.NAME);

        addRenamedOld20ns("get-seconds-from-dateTime",GetSecondsFromDateTimeXFunction.NAME);
        addRenamedOld20ns("get-seconds-from-time",GetSecondsFromTimeXFunction.NAME);

        addRenamedOld20ns("get-timeZone-from-dateTime",GetTimeZoneFromDateTimeXFunction.NAME);
        addRenamedOld20ns("get-timeZone-from-date",GetTimeZoneFromDateXFunction.NAME);
        addRenamedOld20ns("get-timeZone-from-time",GetTimeZoneFromTimeXFunction.NAME);

        addRenamedOld20ns("current-dateTime",CurrentDateTimeXFunction.NAME);
        addRenamedOld20ns("current-dateTime-timezone",CurrentDateTimeWithTimeZoneXFunction.NAME);

        FunctionNamespace ns = TibcoExtendedFunctionNamespace.INSTANCE;
        Iterator tf = ns.getFunctions();
        while (tf.hasNext())
        {
            XFunction fn = (XFunction) tf.next();
            RENAMED_FUNCTIONS.put(ExpandedName.makeName(fn.getName().getLocalName()),fn.getName());
        }
    }

    /**
     * Adds an entry for both the old20 namespace & no-namespace.
     * @param name
     * @param newName
     */
    private static void addRenamedOld20ns(String name, ExpandedName newName)
    {
        String old20ns = TibXPath20Functions.OLD_NAMESPACE;
        RENAMED_FUNCTIONS.put(ExpandedName.makeName(name),newName);
        RENAMED_FUNCTIONS.put(ExpandedName.makeName(old20ns,name),newName);
    }

    private static void adjustNewFormulaNamespaces(Binding binding, NamespaceContextRegistry ni, FunctionResolver fr)
    {
        if (binding.getFormula()!=null)
        {
            // add prefixes registrations where they weren't before:
            Expr ne = Parser.parse(binding.getFormula());
            Expr ne2 = ExprUtilities.renameFunctions(ne,RENAMED_FUNCTIONS,ni,fr);
            Expr ne3 = add2ndArgToConcat(ne2);
            binding.setFormula(ne3.toExactString());
        }
        for (int i=0;i<binding.getChildCount();i++)
        {
            adjustNewFormulaNamespaces(binding.getChild(i),ni,fr);
        }
    }

    /**
     * Version 1.0 wrote type-copy ofs like
     * <pre>
     * element
     *  for-each (formula)/@*
     *   copy-of .
     *  for-each (formula)/*
     *   copy-of .
     * </pre>
     * Version 2.0 write them a little more compact:
     * <pre>
     * element
     *  copy-of (formula)/@*
     *  copy-of (formula)/*
     * </pre>
     * For 5.0, just a slight refinement:
     * <pre>
     * element
     *  copy-of (formula)/@*
     *  copy-of (formula)/node()
     * </pre>
     * @param binding
     */
    private static void cleanupOldStyleTypeCopyOfs(Binding binding)
    {
        if (binding instanceof ElementBinding)
        {
            if (binding.getChildCount()==2)
            {
                if (binding.getChild(0) instanceof CopyOfBinding && binding.getChild(1) instanceof CopyOfBinding)
                {
                    String f1 = binding.getChild(0).getFormula();
                    String f2 = binding.getChild(1).getFormula();
                    if (f1!=null && f2!=null)
                    {
                        if (f1.endsWith("/@*") && f2.endsWith("/*"))
                        {
                            String l1 = f1.substring(f1.length()-"/@*".length());
                            String l2 = f2.substring(f2.length()-"/*".length());
                            if (l1.equals(l2))
                            {
                                replaceOldStyleTypeCopyOf(binding,l1);
                                return;
                            }
                        }
                    }
                }
                if (binding.getChild(0) instanceof ForEachBinding && binding.getChild(1) instanceof ForEachBinding)
                {
                    Binding c1 = binding.getChild(0);
                    Binding c2 = binding.getChild(1);
                    if (c1.getChildCount()==1 && c1.getChild(0) instanceof CopyOfBinding && c2.getChildCount()==1 && c2.getChild(0) instanceof CopyOfBinding)
                    {
                        if (".".equals(c1.getChild(0).getFormula()) && ".".equals(c2.getChild(0).getFormula()))
                        {
                            String f1 = binding.getChild(0).getFormula();
                            String f2 = binding.getChild(1).getFormula();
                            if (f1!=null && f2!=null)
                            {
                                if (f1.endsWith("/@*") && f2.endsWith("/*"))
                                {
                                    String l1 = f1.substring(0,f1.length()-"/@*".length());
                                    String l2 = f2.substring(0,f2.length()-"/*".length());
                                    if (l1.equals(l2))
                                    {
                                        replaceOldStyleTypeCopyOf(binding,l1);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int i=0;i<binding.getChildCount();i++)
        {
            cleanupOldStyleTypeCopyOfs(binding.getChild(i));
        }
    }

    /**
     * Mostly a cosmetic fixup.
     * In 1.X and 2.X global variables were accidentally recorded as 'optional', when they are required,
     * so the mappings will always have an 'implicit-if' around them; remove that for global variables.
     */
    private static void removeGlobalVarIfs(Binding binding)
    {
        if (binding instanceof IfBinding)
        {
            if (VirtualElementMatcher.INSTANCE.matches(binding))
            {
                String f = binding.getFormula();
                if (f==null) f= ""; // just in case.
                if (f.trim().startsWith("$_globalVariables/"))
                {
                    // Since global variables are always required, can remove this implicit-if.
                    Binding c = binding.getChild(0);
                    BindingManipulationUtils.removeFromParent(c);
                    BindingManipulationUtils.replaceInParent(binding,c);
                }
            }
        }
        for (int i=0;i<binding.getChildCount();i++)
        {
            removeGlobalVarIfs(binding.getChild(i));
        }
    }

    private static void replaceOldStyleTypeCopyOf(Binding binding, String l)
    {
        binding.removeAllChildren();
        binding.addChild(new CopyOfBinding(BindingElementInfo.EMPTY_INFO,l + "/ancestor-or-self::*/namespace::node()"));
        binding.addChild(new CopyOfBinding(BindingElementInfo.EMPTY_INFO,l + "/@*"));
        binding.addChild(new CopyOfBinding(BindingElementInfo.EMPTY_INFO,l + "/node()"));
    }

    private static void fixRepeatingTokenize(TemplateReport report, int srcVersionNumber)
    {
        if (srcVersionNumber>2)
        {
            return;
        }
        Binding b = report.getBinding();
        if (b.getParent() instanceof ElementBinding)
        {
            TemplateReport tp = report.getParent();
            if (tp.getExpectedType()!=null)
            {
                boolean isText = SmSequenceTypeSupport.isText(tp.getExpectedType().childAxis());
                int mo = tp.getExpectedType().quantifier().getMaxOccurs();
                if (mo>1 && isText)
                {
                    // Fix case where, in 2.0.2, repeating leaves where the formula was tokenize (the only function
                    // that produces a node set) were written out incorrectly (didn't write out a for-each).
                    // The adjustment here is to insert that for each:
                    if (b.getFormula()!=null)
                    {
                        String ft = b.getFormula().trim();
                        Expr e = Parser.parse(ft);
                        if (e.getExprTypeCode()==ExprTypeCode.EXPR_FUNCTION_CALL)
                        {
                            QName qn = new QName(e.getExprValue());
                            if (qn.getLocalName().equals("tokenize"))
                            {
                                Binding p = b.getParent();
                                if (p.getChildCount()==1 && p.getFormula()==null && !(p.getParent() instanceof ForEachBinding))
                                {
                                    if (b instanceof ValueOfBinding)
                                    {
                                        // ok, this is the case.
                                        //
                                        // Move up:
                                        String formula = b.getFormula();
                                        b.setFormula(".");
                                        ForEachBinding feb = new ForEachBinding(BindingElementInfo.EMPTY_INFO,formula);
                                        BindingManipulationUtils.replaceInParent(p,feb);
                                        feb.addChild(p.cloneDeep());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        int cc = report.getChildCount();
        for (int i=0;i<cc;i++)
        {
            fixRepeatingTokenize(report.getChild(i),srcVersionNumber);
        }
    }

    /**
     * Replaces xsl:attribute name="xsi:nil",value-of="true" with a plain attribute on the element.
     * @param binding The binding.
     * @param srcVersionNumber
     */
    private static void fixXSINil(Binding binding, NamespaceContextRegistry ni, int srcVersionNumber)
    {
        if (srcVersionNumber>1000) //WCETODO --- all versions so far.
        {
            return;
        }
        if (binding.getParent() instanceof ElementBinding)
        {
            ElementBinding eb = (ElementBinding) binding.getParent();
            if (binding instanceof AttributeBinding && !eb.isExplicitXslRepresentation())
            {
                AttributeBinding ab = (AttributeBinding) binding;
                if (ab.isExplicitXslRepresentation())
                {
                    try
                    {
                        ExpandedName ename = ab.computeComponentExpandedName(ni);//BindingNamespaceManipulationUtils.createNamespaceImporter(ab));
                        if (ename.equals(XSDL.ATTR_NIL.getExpandedName()))
                        {
                            if (ab.getChildCount()==1 && ab.getChild(0) instanceof ValueOfBinding)
                            {
                                Binding vob = ab.getChild(0);
                                String tformula = vob.getFormula();
                                if (tformula==null)
                                {
                                    tformula = "";
                                }
                                tformula = tformula.trim();
                                if (tformula.equals("true()") || tformula.equals("'true'") || tformula.equals("\"true\""))
                                {
                                    int ioc = eb.getIndexOfChild(ab);
                                    eb.removeChild(ioc);
                                    AttributeBinding nab = new AttributeBinding(BindingElementInfo.EMPTY_INFO,XSDL.ATTR_NIL.getExpandedName());
                                    nab.setFormula("true");
                                    eb.addChild(0,nab);
                                }
                            }
                        }
                    }
                    catch (PrefixToNamespaceResolver.PrefixNotFoundException pnfe)
                    {
                        // eat it, let it slip through.
                    }
                }
            }
        }
        int cc = binding.getChildCount();
        for (int i=0;i<cc;i++)
        {
            fixXSINil(binding.getChild(i),ni,srcVersionNumber);
        }
    }

    /**
     * Replaces xsl:attribute name="xsi:type" (constant) with a plain attribute on the element.
     * @param binding The binding.
     * @param ni The namespace importer; assumes (as in 2.0) that all namespaces are on a root node.
     * @param srcVersionNumber
     */
    private static void fixXSIType(Binding binding, NamespaceContextRegistry ni, int srcVersionNumber)
    {
        if (binding.getParent() instanceof ElementBinding)
        {
            ElementBinding eb = (ElementBinding) binding.getParent();
            if (binding instanceof AttributeBinding && !eb.isExplicitXslRepresentation())
            {
                AttributeBinding ab = (AttributeBinding) binding;
                if (ab.isExplicitXslRepresentation())
                {
                    try
                    {
                        ExpandedName ename = ab.computeComponentExpandedName(ni);//BindingNamespaceManipulationUtils.createNamespaceImporter(ab));
                        if (ename.equals(XSDL.ATTR_TYPE.getExpandedName()))
                        {
                            if (ab.getChildCount()==1 && ab.getChild(0) instanceof TextBinding)
                            {
                                Binding vob = ab.getChild(0);
                                String tformula = vob.getFormula();
                                if (tformula==null)
                                {
                                    tformula = "";
                                }
                                int ioc = eb.getIndexOfChild(ab);
                                eb.removeChild(ioc);
                                AttributeBinding nab = new AttributeBinding(BindingElementInfo.EMPTY_INFO,XSDL.ATTR_TYPE.getExpandedName());
                                nab.setFormula(tformula);
                                eb.addChild(0,nab);
                            }
                        }
                    }
                    catch (PrefixToNamespaceResolver.PrefixNotFoundException pnfe)
                    {
                        pnfe.printStackTrace(System.err);
                        // eat it, let it slip through.
                    }
                }
            }
        }
        int cc = binding.getChildCount();
        for (int i=0;i<cc;i++)
        {
            fixXSIType(binding.getChild(i),ni,srcVersionNumber);
        }
    }

    /**
     * Fixes a pattern that could happen on nillable nodes that makes no sense (GUI should have generated something simpler).<br>
     * Pattern is:
     * <code>
     * element
     *   if test="X"
     *      value-of="X'
     * </code>
     * The if test adds no value; if it were to be false, the value of would issue nothing anyway.<br>
     * Fix is to remove the if (so the value-of is directly below the element)
     * @param binding The binding.
     */
    private static void fixExtraneousIfValueOfs(Binding binding)
    {
        if (binding instanceof IfBinding && binding.getParent() instanceof DataComponentBinding)
        {
            if (binding.getChildCount()==1 && binding.getChild(0) instanceof ValueOfBinding)
            {
                Binding cb = binding.getChild(0);
                String f = binding.getFormula();
                String f2 = cb.getFormula();
                if (f!=null && f2!=null && f.equals(f2))
                {
                    // matches the pattern, remove if.
                    BindingManipulationUtils.removeFromParent(cb);
                    BindingManipulationUtils.replaceInParent(binding,cb);
                }
            }
        }
        int cc = binding.getChildCount();
        for (int i=0;i<cc;i++)
        {
            fixExtraneousIfValueOfs(binding.getChild(i));
        }
    }

    /**
     * Fixes character entity refs and xalanized formula.
     * (Xanalized formulas are those custom functions stored in xalan specific format --- early legacy)
     */
    private static void adjustXalanizedFormulas(Binding binding, NamespaceContextRegistry ni, FunctionResolver functions)
    {
        if (binding.getFormula()!=null && !(binding instanceof TemplateBinding) && !(binding instanceof TextBinding) && !(binding instanceof AttributeBinding))
        {
            Expr e = Parser.parse(binding.getFormula());

            NamespaceContextRegistry ni2 = new ChainingNamespaceContextRegistry(ni)
            {
                public String getNamespaceURIForPrefix(String prefix) throws PrefixToNamespaceResolver.PrefixNotFoundException
                {
                    try
                    {
                        return super.getNamespaceURIForPrefix(prefix);
                    }
                    catch (PrefixNotFoundException pnfe)
                    {
                        if (prefix.equals("java"))
                        {
                            return ExprUtilities.XALAN_XSLT_NAMESPACE;
                        }
                        throw pnfe;
                    }
                }
            };
            Expr descripted = ExprUtilities.descriptFunctions(e,functions,ni2);

            // Remove any xalan specific saving (this was done before, new saves won't use it)
            Expr ne1 = ExprUtilities.decodeXalanJavaExtensionFunctions(
                    descripted,
                    functions,
                    ni2
            );

            // add fn. prefixes registrations where they weren't before:
            EvalTypeInfo info = new EvalTypeInfo();
            info.setFixCustomFunctionNamespaces(true);
            ExprContext econtext = new ExprContext(new VariableDefinitionList(),functions).createWithNamespaceMapper(ni);
            ne1.evalType(econtext,info);

            binding.setFormula(ne1.toExactString());
        }
        int cc = binding.getChildCount();
        for (int i=0;i<cc;i++) {
            adjustXalanizedFormulas(binding.getChild(i),ni,functions);
        }
    }

    /**
     * Fixes character entity refs and xalanized formula.
     * (Xanalized formulas are those custom functions stored in xalan specific format --- early legacy)
     */
    private static void fixRepoDocumentReferences(Binding binding)
    {
        if (binding.getFormula()!=null && !(binding instanceof TemplateBinding) && !(binding instanceof TextBinding) && !(binding instanceof AttributeBinding))
        {
            Expr e = Parser.parse(binding.getFormula());

            Expr e2 = fixRepoDocumentReferences(e);

            binding.setFormula(e2.toExactString());
        }
        int cc = binding.getChildCount();
        for (int i=0;i<cc;i++)
        {
            fixRepoDocumentReferences(binding.getChild(i));
        }
    }

    private static Expr fixRepoDocumentReferences(Expr e)
    {
        if (e.getExprTypeCode()==ExprTypeCode.EXPR_FUNCTION_CALL)
        {
            if ("document".equals(e.getExprValue()))
            {
                Expr[] ch = e.getChildren();
                if (ch.length==1)
                {
                    Expr child = ch[0];
                    if (child.getExprTypeCode()==ExprTypeCode.EXPR_LITERAL_STRING)
                    {
                        // The reference may need a new .xml at the end
                        String v = child.getExprValue();
                        if (v.startsWith("tibcr://"))
                        {
                            if (!v.endsWith(".xml"))
                            {
                                String ns = v + ".xml";
                                Expr e2 = Expr.create(child.getExprTypeCode(),new Expr[0],ns,child.getWhitespace(),child.getRepresentationClosure());
                                return Expr.create(e.getExprTypeCode(),new Expr[] {e2},e.getExprValue(),e.getWhitespace(),e.getRepresentationClosure());
                            }
                        }
                    }
                }
            }
        }
        Expr[] ch = e.getChildren();
        Expr[] nc = new Expr[ch.length];
        for (int i=0;i<ch.length;i++)
        {
            nc[i] = fixRepoDocumentReferences(ch[i]);
        }
        return Expr.create(e.getExprTypeCode(),nc,e.getExprValue(),e.getWhitespace(),e.getRepresentationClosure());
    }

    static class ChainingNamespaceContextRegistry implements NamespaceContextRegistry
    {
        private final NamespaceContextRegistry m_contextRegistry;

        public ChainingNamespaceContextRegistry(NamespaceContextRegistry ni)
        {
            m_contextRegistry = ni;
        }

        public String getOrAddPrefixForNamespaceURI(String namespace)
        {
            return m_contextRegistry.getOrAddPrefixForNamespaceURI(namespace);
        }

        public String getOrAddPrefixForNamespaceURI(String namespace, String suggestedPrefix)
        {
            return m_contextRegistry.getOrAddPrefixForNamespaceURI(namespace,suggestedPrefix);
        }

        public Iterator getLocalPrefixes()
        {
            return m_contextRegistry.getLocalPrefixes();
        }

        public Iterator getPrefixes()
        {
            return m_contextRegistry.getPrefixes();
        }

        public NamespaceContext snapshot()
        {
            return m_contextRegistry.snapshot();
        }

        public String getPrefixForNamespaceURI(String namespaceURI) throws NamespaceToPrefixResolver.NamespaceNotFoundException
        {
            return m_contextRegistry.getPrefixForNamespaceURI(namespaceURI);
        }

        public String getNamespaceURIForPrefix(String prefix) throws PrefixToNamespaceResolver.PrefixNotFoundException
        {
            return m_contextRegistry.getNamespaceURIForPrefix(prefix);
        }
    }

    private static boolean isTypeCopyOf(TemplateReport report)
    {
        if (!(report.getBinding() instanceof ElementBinding))
        {
            return false;
        }
        if (report.getChildCount()<=3)
        {
            return false;
        }
        if (!(report.getBinding().getChild(0) instanceof CopyOfBinding) || !(report.getBinding().getChild(1) instanceof CopyOfBinding) || !(report.getBinding().getChild(2) instanceof CopyOfBinding))
        {
            return false;
        }
        for (int i=3;i<report.getBinding().getChildCount();i++)
        {
            if (!(report.getBinding().getChild(i) instanceof MarkerBinding))
            {
                return false;
            }
        }
        // Ok, starts with 2 copy-ofs, followed by only markers (if anything).
        String f1 = report.getBinding().getChild(0).getFormula();
        String f2 = report.getBinding().getChild(1).getFormula();
        String f3 = report.getBinding().getChild(2).getFormula();
        if (f1==null || f2==null || f3==null)
        {
            return false;
        }
        // This will indicate:
        if (f1.endsWith("/ancestor-or-self::*/namespace::node()") && f2.endsWith("/@*") && f3.endsWith("/node()"))
        {
            return true;
        }
        return false;
    }

    private static void fixWrongRootElement(TemplateBinding b, SmSequenceType expectedOutput, NamespaceContextRegistry ni)
    {
        SmParticleTerm t = expectedOutput.prime().getParticleTerm();
        if (!(t instanceof SmElement))
        {
            return;
        }
        SmElement e = (SmElement) t;
        Binding bb = b;
        // Make sure we have a root element:
        if (bb.getChildCount()!=1 || !(bb.getChild(0) instanceof ElementBinding))
        {
            // .... in some cases it can be a root element inside a for-each, check for that, too:
            if (bb.getChildCount()==1 && bb.getChild(0) instanceof ForEachBinding)
            {
                bb = bb.getChild(0);
                if (bb.getChildCount()!=1 || !(bb.getChild(0) instanceof ElementBinding))
                {
                    return;
                }
                // ok, continue on:
            }
            else
            {
                return;
            }
        }
        ElementBinding eb = (ElementBinding) bb.getChild(0);
        ExpandedName ename = eb.getName();

        // Ensure registered at root level:
        if (!NoNamespace.isNoNamespaceURI(e.getExpandedName().getNamespaceURI()))
        {
            ni.getOrAddPrefixForNamespaceURI(e.getExpandedName().getNamespaceURI());
        }

        if (ename.equals(e.getExpandedName()))
        {
            return; // no change.
        }
        // Fix it:
        eb.setLiteralName(e.getExpandedName());
    }

    /**
     * Check for XSLT 2.0 stuff:
     */
    private static String check20LeafFormula(Binding binding)
    {
        if (!(binding instanceof ElementBinding || binding instanceof AttributeBinding))
        {
            return null;
        }
        int size = binding.getChildCount();
        if (size<=1)
        {
            // doesn't contain anything.
            return null;
        }
        Binding c1 = binding.getChild(0);
        if (!(c1 instanceof SetVariableBinding))
        {
            return null;
        }
        SetVariableBinding svb = (SetVariableBinding) c1;
        ExpandedName exVarName = svb.getVariableName();
        String tempVarName = exVarName.getLocalName();
        if (!tempVarName.startsWith("__")) {
            return null;
        }
        if (svb.getChildCount()!=1) {
            return null;
        }
        com.tibco.cep.mapper.xml.xdata.bind.Binding variableContent = svb.getChild(0);
        if (!(variableContent instanceof ForEachBinding)) {
            return null;
        }
        String inFormula = variableContent.getFormula();
        Expr inExpr = new Parser(Lexer.lex(inFormula)).getExpression();
        if (variableContent.getChildCount()!=2) {
            return null;
        }
        if (!(variableContent.getChild(0) instanceof SetVariableBinding)) {
            return null;
        }
        SetVariableBinding setLocalVar = (SetVariableBinding) variableContent.getChild(0);
        ExpandedName exName = setLocalVar.getVariableName();
        String localVarName = exName.getLocalName();
        ForExpr.Clause[] c = new ForExpr.Clause[] {new ForExpr.Clause(localVarName,inExpr," "," "," ")};

        com.tibco.cep.mapper.xml.xdata.bind.Binding returnBinding = variableContent.getChild(1);
        if (returnBinding instanceof ElementBinding) { // temporary element.
            if (returnBinding.getChildCount()!=1) {
                return null;
            }
            // this is the real content we care about:
            returnBinding = returnBinding.getChild(0);
        }
        if (!(returnBinding instanceof ValueOfBinding)) {
            return null;
        }
        String returnFormula = returnBinding.getFormula();
        Expr returnExpr = new Parser(Lexer.lex(returnFormula)).getExpression();

        ForExpr forExpr = new ForExpr(c,returnExpr," ",new TextRange(0,0));

        if (!(binding.getChild(1) instanceof ValueOfBinding)) {
            return null;
        }
        String wrappingFormula = binding.getChild(1).getFormula();
        Expr wrappingExpr = new Parser(Lexer.lex(wrappingFormula)).getExpression();
        Expr replacedVariableRef = new Parser(Lexer.lex("xalan:nodeset($" + tempVarName + ")/*")).getExpression();

        Expr result = Utilities.replace(wrappingExpr,replacedVariableRef,forExpr);
        String resultFormula = result.toExactString();
        // wow... major transform.
        return resultFormula;
    }

    /**
     * Changes 'concat(single-arg) to concat(single-arg,"")'.
     */
    public static Expr add2ndArgToConcat(Expr expr)
    {
        int tc = expr.getExprTypeCode();
        String newValue = expr.getExprValue();
        Expr[] fixedChildren = null;
        if (tc==ExprTypeCode.EXPR_FUNCTION_CALL)
        {
            String vn = newValue;
            if (vn.equals("concat"))
            {
                if (expr.getChildren().length==1)
                {
                    fixedChildren = new Expr[] {expr.getChildren()[0],Parser.parse("\"\"")};
                }
            }
        }

        Expr[] children = fixedChildren!=null ? fixedChildren : expr.getChildren();
        Expr[] newChildren = new Expr[children.length];
        for (int i=0;i<children.length;i++)
        {
            Expr c = children[i];
            newChildren[i] = add2ndArgToConcat(c);
        }
        return Expr.create(tc,newChildren,newValue,expr.getWhitespace(),expr.getRepresentationClosure());
    }
}
