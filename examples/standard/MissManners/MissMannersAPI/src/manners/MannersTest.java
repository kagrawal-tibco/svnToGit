package manners;

import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.SimpleEvent;

import java.util.*;

public class MannersTest {
    RuleSession ruleSession;
    TypeManager typeManager;
    String mannersData;
    List   allGuests;

    MannersTest(String mannersFile, String repoUrl, String traFile, String cddFile, String procUnit) throws Exception {
        Properties env = new Properties();
        env.put("tibco.repourl", repoUrl);
        env.put("be.bootstrap.property.file", traFile);
		env.put("com.tibco.be.config.path", cddFile);
		env.put("com.tibco.be.config.unit.id", procUnit);

        RuleServiceProvider provider = RuleServiceProviderManager.getInstance().newProvider("MannersTest", env);
        RuleServiceProviderManager.getInstance().setDefaultProvider(provider);
        provider.configure(RuleServiceProvider.MODE_API);

        ruleSession = provider.getRuleRuntime().getRuleSession("DefaultSession");
        typeManager = provider.getTypeManager();

        mannersData = mannersFile;
        allGuests = new LinkedList();
    }

    private void readData() throws Exception {
        //read data and put in the rule session
        DataReader reader = new DataReader(mannersData);
        Collection guests = reader.read();

        Iterator ite = guests.iterator();
        while (ite.hasNext()) {
            DataReader.Guest guest = (DataReader.Guest) ite.next();
            Concept BEGuest = (Concept) typeManager.createEntity("/Model/Guest");
            BEGuest.getPropertyAtom("name").setValue(guest.getName());
            BEGuest.getPropertyAtom("sex").setValue(guest.getSex());
            String[] hobbies = guest.getHobbies();
            for (int i = 0; i < hobbies.length; i++) {
                BEGuest.getPropertyArray("hobbies").add(hobbies[i]);
            }
            allGuests.add(BEGuest);
        }

        ite = allGuests.iterator();
        while(ite.hasNext()) {
            Concept BEGuest = (Concept) ite.next();
            ruleSession.assertObject(BEGuest, true);
        }
    }

    private void runTest() throws Exception {
        String firstGuest = (String) ((Concept) allGuests.get(0)).getPropertyAtom("name").getValue();

        SimpleEvent startTest = (SimpleEvent) typeManager.createEntity("/Model/StartTest");
        startTest.setProperty("firstGuest", firstGuest);
        startTest.setProperty("numGuest", new Integer(allGuests.size()));

        ruleSession.assertObject(startTest, true);
    }

    private void resetTest() throws Exception {
        SimpleEvent resetTest = (SimpleEvent) typeManager.createEntity("/Model/ResetTest");
        ruleSession.assertObject(resetTest, true);
    }

    private void done() {
        ruleSession.getRuleServiceProvider().shutdown();
    }

    public static void main(String[] args) throws Exception {
		String traFile  = args[0];
		String cddFile = args[1];
		String procUnit = args[2];
		String repourl  = args[3];
		String mannFile = args[4];

		System.out.println(">>> Running test for " + mannFile + " <<<");
		MannersTest test = new MannersTest(mannFile, repourl, traFile, cddFile, procUnit);
		test.readData();

		//initialized and read the data
		System.out.println(">>> First run - ");
		test.runTest();
		test.resetTest();
		System.out.println(">>> Second run - ");
		test.runTest();
		test.resetTest();
		System.out.println(">>> Third run - ");
		test.runTest();
		test.resetTest();
		System.out.println(">>> Done ");

		test.done();
    }
}
