import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.DocumentRoot;
import com.tibco.be.util.config.cdd.impl.CddPackageImpl;

public class TestEMFCdd {
	public static String[] files = new String[] {
		"ABC.cdd",
		"b.cdd",
		"bcbe.cdd",
		"BE_Archive.cdd",
		"BPE_FrontOffice.cdd",
		"BW_Archive.cdd",
		"checkEvent.cdd",
		"dfdsf.cdd",
		"FraudDetection.cdd",
		"Inference.cdd",
		"InferenceAndQuery.cdd",
		"Insurance.cdd",
		"InternalTimeEventProj.cdd",
		"MissManners_8Guests.cdd",
		"MissManners_API.cdd",
		"OrderProcess.cdd",
		"PCRF.cdd",
		"Query.cdd",
//		"RCOMM.cdd",
		"simpleStateMachine.cdd",
		"simpleStateMachineLambda.cdd",
		"SimpleStateRepo.cdd",
		"SimpleStateRepoJa.cdd",
		"SimpleStateRepoJa蹣欺蛙疑型ｬュョ.cdd",
		"SSRepo.cdd",
		"TimerExpiryRepush.cdd",
		"Uverse.cdd",
//		"workshop.cdd",
		"workshoptest.cdd"	
	};
	String folder = "Q:/be/5.1/runtime/core/common/test/cdd/";
	private ResourceSetImpl resourceSet;
	private HashMap<Object, Object> options;

	private com.tibco.be.util.config.cdd.ClusterConfig loadCdd(URI uri)
			throws IOException {
		
		Resource resource = resourceSet.createResource(uri);
		
		resource.load(options);
		DocumentRoot dr = (DocumentRoot) resource.getContents().get(0);
		return dr.getCluster();
	}

	@Before
	public void setUp() throws Exception {
		this.resourceSet = new ResourceSetImpl();
		// add file extension to registry
		CddPackageImpl.eINSTANCE.eClass();
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put(
				"cdd", new GenericXMLResourceFactoryImpl());
		this.options = new HashMap<Object, Object>();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		for(String f: files) {
			URL r = getClass().getResource(f);
			URI u = URI.createURI(r.toString());
			try {
				ClusterConfig o = loadCdd(u);
				assertNotNull(f, o );
			} catch (IOException e) {
				e.printStackTrace();
				fail(e.toString());
			}
		}
	}

}
