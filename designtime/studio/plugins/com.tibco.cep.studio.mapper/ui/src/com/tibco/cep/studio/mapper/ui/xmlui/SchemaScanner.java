
package com.tibco.cep.studio.mapper.ui.xmlui;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.tns.parse.TnsFragment;


/**
 * A utility for scanning all schema components for certain things.
 */
public class SchemaScanner
{
    public static ExpandedName[] scanComponents(UIAgent uiAgent, int componentType, ScannerFilter filter, CancelChecker cancelChecker)
    {
        Set<ExpandedName> all = new HashSet<ExpandedName>();
        Set<TnsFragment> allNs = new HashSet<TnsFragment>();


        scan(uiAgent,componentType,filter,allNs,all,cancelChecker);
        ExpandedName[] rr = all.toArray(new ExpandedName[0]);
        if (cancelChecker.hasBeenCancelled())
        {
            return new ExpandedName[0];
        }
        Arrays.sort(rr);
        return rr;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void sortByLocalName(ExpandedName[] s)
    {
        Arrays.sort(s,new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                String e1 = ((ExpandedName) o1).getLocalName();
                String e2 = ((ExpandedName) o2).getLocalName();
                int ct = e1.compareTo(e2);
                return ct;
            }
        });
    }

    private static void scan(UIAgent uiAgent, int componentType, ScannerFilter filter, Set<TnsFragment> allNSes, Set<ExpandedName> allItems, CancelChecker cancelChecker)
    {
        Set<String> is = getInterestingSuffixes(uiAgent);
        if (is==null)
        {
            throw new RuntimeException("Null interesting suffixes");
        }

//        TnsDocumentProvider tnc = uiAgent.getTnsCache();

        //RYANTODO - We need to run through the Indexer and scan for all interesting Resources and other artifacts that match the Suffix

//        if (cancelChecker.hasBeenCancelled())
//        {
//            return;
//        }
//
//        String ext = r.getVFileExtension();
//        if (interestingSuffixes.contains(ext))
//        {
//            try
//            {
//                scanFile(r, agent, tnc, allNSes, componentType, filter, allItems);
//            }
//            catch (Exception e)
//            {
//                // Shouldn't happen, but if it does, just dump it & continue on w/o killing everything.
//                e.printStackTrace(System.err);
//            }
//        }
//        if (r instanceof AEFolder)
//        {
//            AEResource[] ch = r.getChildren();
//            for (int i=0;i<ch.length;i++)
//            {
//                scan(tnc,agent,ch[i],interestingSuffixes,componentType,filter,allNSes,allItems,cancelChecker);
//            }
//        }
    }

    //RYANTODO - same as above.
//    private static void scanFile(AEResource r, RepoAgent agent, TnsDocumentProvider tnc, Set allNSes, int componentType, ScannerFilter filter, Set allItems)
//    {
//        String uri = r.getURI();
//        String curi = agent.getRepoURI(uri);
//        TnsDocument doc2 = tnc.getDocument(curi);
//        if (doc2!=null)
//        {
//            Iterator frags = doc2.getFragments();
//            while (frags.hasNext())
//            {
//                TnsFragment frag = (TnsFragment) frags.next();
//                if (!allNSes.contains(frag))
//                {
//                    allNSes.add(frag);
//                    if (frag.getNamespaceType()==SmNamespace.class)
//                    {
//                        scanNs(frag,componentType,filter,allItems);
//                    }
//                }
//            }
//        }
//    }

//    private static void scanNs(TnsFragment frag, int componentType, ScannerFilter filter, Set<ExpandedName> allItems)
//    {
//        Iterator i = frag.getComponents(componentType);
//        while (i.hasNext())
//        {
//            Object o = i.next();
//            if (filter.isMember(o))
//            {
//                SmComponent sm = (SmComponent) o;
//                ExpandedName en = sm.getExpandedName();
//                if (!allItems.contains(en))
//                {
//                    allItems.add(en);
//                }
//            }
//        }
//    }

    private static Set<String> getInterestingSuffixes(UIAgent uiAgent)
    {

        String[] s = uiAgent.getTnsCache().getInterestingExtensions();
        HashSet<String> set = new HashSet<String>();
        for (int i=0;i<s.length;i++)
        {
            set.add(s[i]);
        }
        return set;
    }
}
