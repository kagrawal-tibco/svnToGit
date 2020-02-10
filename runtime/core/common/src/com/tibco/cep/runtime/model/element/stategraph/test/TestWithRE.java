package com.tibco.cep.runtime.model.element.stategraph.test;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 19, 2004
 * Time: 5:26:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestWithRE {
    /*

    static void testActivation () {
        try {
            WorkingMemory wm = new WorkingMemory(ParentChildManager.SCHEME_NO_TABLE);
            Rule r1 = new TestWithRE.r1();
            Rule r2 = new TestWithRE.r2();
            Rule r3 = new TestWithRE.r3();
            Rule r4 = new TestWithRE.r4();
            RuleSet ruleSet = new RuleSetImpl("test");
            ruleSet.addRule(r1);
            ruleSet.addRule(r2);
            ruleSet.addRule(r3);
            ruleSet.addRule(r4);
            wm.addRuleSet(ruleSet);
            wm.start();
            wm.printReteNetwork();


            // Create the first event
            startevent se   = new TestWithRE.startevent("123",123);

            // Create the second event
            secondevent se1 = new TestWithRE.secondevent("123",123);

            // Create the third event
            thirdevent thi = new TestWithRE.thirdevent("123",123);

            // Assert the Objects
            wm.assertObj(se);
            wm.assertObj(se1);
            wm.assertObj(thi);
            wm.resolveConflict();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main (String args[]) {
        testActivation();
    }

    static class r1 extends RuleImpl {
        public r1() {
            super("r1", 0);
            m_identifiers = new Identifier[] { new IdentifierImpl(startevent.class, "e1")};
            m_conditions  = new Condition[] { new TestWithRE.r1_c1(this)};
            m_actions     = new Action[] {new TestWithRE.r1_a1(this) };
        }
    }

    static class r1_c1 extends ConditionImpl {
        public r1_c1(Rule rule) {
            super(rule);
            m_identifiers=rule.getIdentifiers();
        }

        public boolean eval(Object[] objects) {
            return true;
        }
    }

    static class r1_a1 extends ActionImpl {
        public r1_a1(Rule rule) {
            super(rule);
        }

        public void execute(Object[] objects) {
            startevent se = (startevent) objects[0];
            RFQ rfq = new RFQ();
            rfq.setId1(123);
            rfq.setTotal(100.00);
            rfq.associateStateMachine(se);
            this.assertObj(rfq);
        }
    }

    static class r2 extends RuleImpl {
        public r2() {
            super("r2", 0);
            m_identifiers = new Identifier[] { new IdentifierImpl(RFQ.class, "s1"), new IdentifierImpl(secondevent.class, "e9")};
            m_conditions  = new Condition[] { new TestWithRE.r2_c1(this),new TestWithRE.r2_c2(this)};
            m_actions     = new Action[] {new TestWithRE.r2_a1(this) };
        }
    }

    static class r2_c1 extends ConditionImpl {
        public r2_c1(Rule rule) {
            super(rule);
            m_identifiers=rule.getIdentifiers();
         }

        public boolean eval(Object[] objects) {
            //str1.length() > str2.length()
            RFQ o1=(RFQ) objects[0];
            secondevent se = (secondevent) objects[1];
            System.out.println("Comparing the Two : " + se.getId1() + " O1=" + o1.getId1().getLong());
            if (se.getId1() == o1.getId1().getLong()) {
                return true;
            } else {
                return false;
            }
        }
    }

    static class r2_c2 extends ConditionImpl {
        public r2_c2(Rule rule) {
            super(rule);
            m_identifiers = new Identifier[] { new IdentifierImpl(RFQ.class, "s1")};
         }

        public boolean eval(Object[] objects) {
            RFQ o1=(RFQ) objects[0];
            return o1.getAssociatedStateMachine().isActivated(o1.RFQ_State.t_s_RFQ_STATE_CREATION_2_s_RFQ_COMPOSITE);
        }
    }

    static class r2_a1 extends ActionImpl {
        public r2_a1(Rule rule) {
            super(rule);
        }

        public void execute(Object[] objects) {
            RFQ o1=(RFQ) objects[0];
            secondevent se = (secondevent) objects[1];
            o1.fire(o1.RFQ_State.t_s_RFQ_STATE_CREATION_2_s_RFQ_COMPOSITE,se);
            this.modifyObj(o1);
            System.out.println("Asserting Object in Rule#2");
        }
    }

    static class r3 extends RuleImpl {
        public r3() {
            super("r3", 0);
            m_identifiers = new Identifier[] { new IdentifierImpl(RFQ.class, "s1"), new IdentifierImpl(thirdevent.class, "e9")};
            m_conditions  = new Condition[] { new TestWithRE.r3_c1(this),new TestWithRE.r3_c2(this)};
            m_actions     = new Action[] {new TestWithRE.r3_a1(this) };
        }
    }

    static class r3_c1 extends ConditionImpl {
        public r3_c1(Rule rule) {
            super(rule);
            m_identifiers=rule.getIdentifiers();
         }

        public boolean eval(Object[] objects) {
            //str1.length() > str2.length()
            RFQ o1=(RFQ) objects[0];
            thirdevent se = (thirdevent) objects[1];
            System.out.println("Comparing the Two : " + se.getId1() + " O1=" + o1.getId1().getLong());
            if (se.getId1() == o1.getId1().getLong()) {
                return true;
            } else {
                return false;
            }
        }
    }

    static class r3_c2 extends ConditionImpl {
        public r3_c2(Rule rule) {
            super(rule);
            m_identifiers = new Identifier[] { new IdentifierImpl(RFQ.class, "s1")};
         }

        public boolean eval(Object[] objects) {
            RFQ rfq=(RFQ) objects[0];
            boolean b=rfq.getAssociatedStateMachine().isActivated(rfq.RFQ_State.t_s_RFQ_COMPOSITE_s_RFQ_LAST);
            System.out.println("Return Value ==== " + b);
            return b;
        }
    }

    static class r3_a1 extends ActionImpl {
        public r3_a1(Rule rule) {
            super(rule);
        }

        public void execute(Object[] objects) {
            RFQ rfq=(RFQ) objects[0];
            thirdevent se = (thirdevent) objects[1];
            rfq.fire(rfq.RFQ_State.t_s_RFQ_COMPOSITE_s_RFQ_LAST,se);
            this.modifyObj(rfq);
        }
    }

    static class r4 extends RuleImpl {
        public r4() {
            super("r4", 0);
            m_identifiers = new Identifier[] { new IdentifierImpl(RFQ.class, "s1")};
            m_conditions  = new Condition[] { new TestWithRE.r4_c1(this)};
            m_actions     = new Action[] {new TestWithRE.r4_a1(this) };
        }
    }

    static class r4_c1 extends ConditionImpl {
        public r4_c1(Rule rule) {
            super(rule);
            m_identifiers=rule.getIdentifiers();
         }

        public boolean eval(Object[] objects) {
            //str1.length() > str2.length()
            RFQ o1=(RFQ) objects[0];
            return o1.getAssociatedStateMachine().isComplete();
        }
    }


    static class r4_a1 extends ActionImpl {
        public r4_a1(Rule rule) {
            super(rule);
        }

        public void execute(Object[] objects) {
            RFQ rfq=(RFQ) objects[0];
            System.out.println("State Machine Complete ::" + rfq);
        }
    }

    static class startevent extends SimpleEventImpl {
        long id1;
        public startevent(String id, long id1) {
            super(id, "startevent", 123455L);    //To change body of overridden methods use File | Settings | File Templates.
            this.id1=id1;
        }
        public long getId1() {
            return id1;
        }
    }

static class secondevent extends SimpleEventImpl {
    long id1;
    public secondevent(String id, long id1) {
        super(id, "secondevent", 123455L);
        this.id1=id1;
    }
    public long getId1() {
        return id1;
    }
}

    static class thirdevent extends SimpleEventImpl {
        long id1;
        public thirdevent(String id, long id1) {
            super(id, "secondevent", 123455L);
            this.id1=id1;
        }
        public long getId1() {
            return id1;
        }
    }
    */
}
