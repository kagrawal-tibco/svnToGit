package com.tibco.cep.mapper.xml.xdata.xpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.tibco.util.RuntimeWrapException;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmGlobalComponentNotFoundException;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * A set of type coercions (casts) expressed by an ordered set
 * of xpaths to SmType or SmElements (for type and element casting)
 */
public final class CoercionSet {
    private ArrayList m_coercionList = new ArrayList(); // ordered
   /** key = xpath string; value = list of coercions **/
    private HashMap m_coercionMap = new HashMap();

    public CoercionSet() {
    }

    /*public AnySubstitution getCoercion(String xpath) {
        AnySubstitution anySub = (AnySubstitution) mCoercionMap.get(xpath);
        return anySub;
    }*/

    public void add(Coercion coercion)
    {
        if (coercion==null)
        {
            throw new NullPointerException();
        }
        m_coercionList.add(coercion);
        ArrayList list = (ArrayList)(m_coercionMap.get(coercion.getXPath()));
        if(list == null) {
           list = new ArrayList();
           m_coercionMap.put(coercion.getXPath(), list);
        }
        list.add(coercion);
    }

    public void remove(Coercion coercion)
    {
       m_coercionList.remove(coercion);
       ArrayList list = (ArrayList)(m_coercionMap.get(coercion.getXPath()));
       if(list != null) {
          int index = list.indexOf(coercion);
          if(index >= 0) {
             list.remove(index);
          }
       }
    }

    public void clear()
    {
        m_coercionList.clear();
        m_coercionMap.clear();
    }

    public int getCount()
    {
        return m_coercionList.size();
    }

    public Coercion get(int index)
    {
        return (Coercion) m_coercionList.get(index);
    }

   public ArrayList getCoercionList() {
      return m_coercionList;
   }

   private HashMap getCoercionMap() {
      return m_coercionMap;
   }

    /**
     * Applies all coercions in order.
     * @param context The state before coercions.
     * @return The new state.
     */
    public ExprContext applyTo(ExprContext context)
    {
        return applyTo(context,false);
    }

    /**
     * Applies all coercions in order, generating a new state.
     * @param context The state before coercions.
     * @return The new state.
     */
    public ExprContext applyTo(ExprContext context, boolean addMarkers)
    {
       if(getCount() == 1) {
          Coercion c = get(0);
          context = c.applyTo(context,addMarkers);
       }
       else if(getCount() > 1) {
          List coercionChoiceGroups = createCoercionChoices(context, this);
          for(Iterator it = coercionChoiceGroups.iterator(); it.hasNext();) {
             Coercion coercion = (Coercion)(it.next());
             context = coercion.applyTo(context,addMarkers);
          }
       }
        return context;
    }
   /**
    * From the given CoercionSet, combines Coercions with the same coerced-for xpath
    * into CoercionChoiceGroups.  So, the number of returned Coercion objects, of which
    * CoercionChoicGroup is a subclass, may be less that the number of Coercion objects
    * in the given CoercionSet.
    *
    * @param context
    * @param coercionSet the set of coercions from which to create the choice groups
    * @return a list of coercions, which will be of the Coercion or the CoercionChoiceGroup
    * class (i.e. check class type before casting to CoercionChoiceGroup)
    */
    static public List createCoercionChoices(ExprContext context,
                                             CoercionSet coercionSet) {
       HashMap coercionMap = coercionSet.getCoercionMap();
       List coercionChoiceGroups = new ArrayList();

       // Create a choice group for each set available xpath.
       for(Iterator it = coercionMap.keySet().iterator(); it.hasNext();) {
          String xpath = (String)(it.next());
          ArrayList coercionList = (ArrayList)(coercionMap.get(xpath));
          if(coercionList.size() <= 1) {
             // Only one item in list, so no need for a group.  Just add the
             // regular coercion to the list.
             coercionChoiceGroups.add(coercionList.get(0));
          }
          else {
             // Create a choice group.
             ArrayList coercedTypes = new ArrayList(coercionList.size());
             Expr e = Parser.parse(xpath);
             XPathTypeReport tcontextr = e.evalType(context);
             SmSequenceType tcontext = tcontextr.xtype;
/*
             boolean substitutingForAbstract = tcontext.isAbstractType(false);
             // If XType is not abstract, make sure its not a container type, e.g. RepeatsXType.
             // In that case we must check the first child component.
             if(!substitutingForAbstract) {
                if(tcontext.getFirstChildComponent() != null) {
                   substitutingForAbstract = tcontext.getFirstChildComponent().isAbstractType(false);
                }
             }
*/
/*
             if(tcontext.getParticleTerm() != null &&
                     tcontext.getParticleTerm().getComponentType() == SmComponent.ELEMENT_TYPE) {
                // Is the substituted-for element abstract?
                substitutingForAbstract = ((SmElement)tcontext.getParticleTerm()).isAbstract();
             }
             else {
                if(tcontext.getSimpleType() != null) {
                   substitutingForAbstract = tcontext.getSimpleType().isAbstract();
                }
                else if (tcontext.getElementOverrideType() != null) {
                   substitutingForAbstract = tcontext.getElementOverrideType().isAbstract();
                }
             }
*/
             // If element isn't abstract, add it to the choice group.
// Let's add the element no matter what.
//            if(!substitutingForAbstract) {
             SmCardinality occurrence = tcontext.getOccurrence();
             if(occurrence == null) {
                coercedTypes.add(tcontext);
             }
             else if(tcontext.getFirstChildComponent() != null) {
                coercedTypes.add(tcontext.getFirstChildComponent());
             }

             ArrayList originalCoercions = new ArrayList();
             for (int i=0; i<coercionList.size(); i++) {
                Coercion c = (Coercion)(coercionList.get(i));
                originalCoercions.add(c);
                String typeOrElemName = c.getTypeOrElementName();

                // Only process named coercions; unnamed coercions, i.e. coercions named "",
                // are most likely new coercions that have not been associated with a type
                // or element, yet.
                if(typeOrElemName != null && typeOrElemName.length() > 0) {
                   ExpandedName ename = new QName(c.getTypeOrElementName()).getExpandedName(
                           context.getNamespaceMapper());

                   if (c.getType()==Coercion.COERCION_TYPE) {
                       // get el first.
                      try {
                         SmType type = context.getInputSmComponentProvider().getType(ename);
                         if(type != null) {
                            coercedTypes.add(tcontext.assertType(type));
                         }
                      }
                      catch (SmGlobalComponentNotFoundException ex) {
                         throw new RuntimeWrapException(ex);
                      }
/*
                      SmNamespace ns = context.getInputSchemaProvider().getNamespace(ename.getNamespaceURI());
                       if (ns!=null) {
                          SmType t = SmSequenceTypeSupport.getType(ns,ename.getLocalName());
                          if (t!=null) {
                             coercedTypes.add(tcontext.assertType(t));
                          }
                       }
*/
                   }
                   else {
                       if (c.getType()==Coercion.COERCION_ELEMENT) {
                           coercedTypes.add(SmSequenceTypeSupport.getElementInContext(
                                   ename,tcontext,context.getInputSmComponentProvider()));
                       }
                       else {
                          try {
                             coercedTypes.add(SmSequenceTypeSupport.getGroup(
                                     ename,context.getInputSmComponentProvider()));
                          }
                          catch (SmGlobalComponentNotFoundException ex) {
                             throw new RuntimeWrapException(ex);
                          }
                       }
                   }
                }
             }

             SmSequenceType[] xtypeArray = new SmSequenceType[coercedTypes.size()];
             coercedTypes.toArray(xtypeArray);
             SmSequenceType choiceType = SmSequenceTypeFactory.createChoice(null, xtypeArray);
             CoercionChoiceGroup choiceCoercion =
                     new CoercionChoiceGroup(xpath, "dummyChoiceGroup",
                                             choiceType, choiceType.getOccurrence(),
                                             originalCoercions);
             coercionChoiceGroups.add(choiceCoercion);
          }
       }
       return coercionChoiceGroups;
    }
    public Object clone()
    {
        CoercionSet ret = new CoercionSet();
        ret.m_coercionList = (ArrayList) m_coercionList.clone();
        ret.m_coercionMap = (HashMap) m_coercionMap.clone();
        return ret;
    }

    /**
     * Sorts the coercions so that more-specific ones come after less-specific ones.<br>
     */
    public void sort()
    {
        Comparator c = new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                Coercion c1 = (Coercion) o1;
                Coercion c2 = (Coercion) o2;
                String xp1 = stripTrailingAnyElMatch(c1.getXPath());
                String xp2 = stripTrailingAnyElMatch(c2.getXPath());
                if (xp1.startsWith(xp2))
                {
                    // xp1 is longer.
                    return 1;
                }
                if (xp2.startsWith(xp1))
                {
                    // xp2 is longer:
                    return -1;
                }
                // tie:
                return xp1.compareTo(xp2);
            }
        };
        Coercion[] a = (Coercion[]) m_coercionList.toArray(new Coercion[0]);
        Arrays.sort(a,c);
        m_coercionList = new ArrayList(Arrays.asList(a));
    }

    /**
     * Removes a /* or /node()
     * @return
     */
    private static String stripTrailingAnyElMatch(String str)
    {
        if (str.endsWith("/*"))
        {
            return str.substring(0,str.length()-"/*".length());
        }
        if (str.endsWith("/node()"))
        {
            return str.substring(0,str.length()-"/node()".length());
        }
        return str;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for (int i=0;i<getCount();i++) {
            Coercion c = get(i);
            if (i>0) {
                sb.append(", ");
            }
            sb.append(c);
        }
        sb.append("}");
        return sb.toString();
    }
}

