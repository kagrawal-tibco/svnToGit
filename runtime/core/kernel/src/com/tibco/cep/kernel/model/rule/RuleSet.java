//package com.tibco.cep.kernel.model.rule;
//
//
//import java.util.Collection;
//
///*
// * Created by IntelliJ IDEA.
// * User: nleong
// * Date: Apr 13, 2006
// * Time: 2:13:23 PM
// * To change this template use File | Settings | File Templates.
// */
//
///**
// * Base for runtime rule sets.
// *
// * @version 2.0.0
// * @.category public-api
// * @since 2.0.0
// */
//public interface RuleSet {
//
//
//    /**
//     * Gets the URI of this <code>RuleSet</code>.
//     * The URI should be unique within a session.
//     *
//     * @return the URI of the RuleSet
//     * @.category public-api
//     * @since 2.0.0
//     */
//    String getURI();
//
//
//    /**
//     * Tests if the <code>RuleSet</code> is active.
//     *
//     * @return true if active.
//     * @.category public-api
//     * @since 2.0.0
//     */
//    boolean isActive();
//
//
//    /**
//     * Activates this <code>RuleSet</code>.
//     *
//     * @.category public-api
//     * @since 2.0.0
//     */
//    void activate();
//
//
//    /**
//     * Deactivates this <code>RuleSet</code>.
//     *
//     * @.category public-api
//     * @since 2.0.0
//     */
//    void deactivate();
//
////    /**
////     * check to see if need to activate the RuleSet based on all its scenarios.
////     * Should only called once from the beginning after all scenarios and RuleSet are setup
////     * @.category public-api
////     */
////    void start();
//
//
//    /**
//     * Gets all the rules in this <code>RuleSet</code>.
//     *
//     * @return a collection of {@link Rule}.
//     * @.category public-api
//     * @since 2.0.0
//     */
//    Collection rules();
//}
