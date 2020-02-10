package com.tibco.cep.releases.bom.factories;

import com.tibco.cep.releases.bom.Assemblies;
import com.tibco.cep.releases.bom.Assembly;
import com.tibco.cep.releases.bom.FileSets;
import com.tibco.cep.releases.bom.XNames;
import com.tibco.cep.releases.bom.impl.AssembliesImpl;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 6/27/11
 * Time: 6:46 PM
 */
public class AssembliesFactory
        implements XmlBasedFactory<Assemblies> {


    private FileSets fileSets;
    private AssemblyFactory assemblyFactory;


    public AssembliesFactory(
            FileSets fileSets) {

        this.setFileSets(fileSets);
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public FileSets getFileSets() {

        return this.fileSets;
    }


    @Override
    public Assemblies make(
            XiNode node)
            throws Exception {

         if (null == node) {
            throw new IllegalArgumentException();
        }

        final SortedMap<String, Assembly> nameToAssembly = new TreeMap<String, Assembly>();

        for (Iterator it = XiChild.getIterator(node, XNames.Nodes.ASSEMBLY); it.hasNext(); ) {
            final Assembly assembly = this.assemblyFactory.make((XiNode) it.next());
            nameToAssembly.put(assembly.getName(), assembly);
        }

        return new AssembliesImpl(nameToAssembly);

    }


    public void setFileSets(
            FileSets fileSets) {

        if (!fileSets.equals(this.fileSets)) {
            this.fileSets = fileSets;
            this.assemblyFactory = new AssemblyFactory(fileSets);
        }
    }


}
