package com.tibco.cep.jira.model;

/**
 * User: nprade
 * Date: 7/21/11
 * Time: 12:49 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
public enum JiraCustomFieldName {

    ACCEPT_ID("Accept Id", 10130),
    AFFECTS_BUILD("Affects Build", 10170),
    APP_SERVER("Accept Id", 10054),
    AREA("Accept Id", 10030),
    BROWSER("Browser", 10053),
    CC_LIST("Confirmer", 10110),
    COMPILER("Compiler", 10015),
    CONFIRMER("Confirmer", 10000),
    CONFIRMER_BUILD("Confirmer Build", 10171),
    CONFIRMER_DATE("Confirmer Date", 10200),
    CONFIRMER_VERSION("Confirmer Version", 10012),
    DATABASE("Database", 10152),
    DOC_ACTION("Doc Action", 10019),
    ENCODING("Encoding", 10017),
    HARDWARE("Hardware", 10013),
    LABELS("Labels", 10040),
    LEGACY_CC_LIST("Legacy CC List", 10050),
    LOCALE("Locale", 10018),
    MILESTONES("Locale", 10111),
    ORIGINAL_PRODUCT("Original Product", 10031),
    ORIGINAL_PRODUCT_VERSION("Original Product Version", 10032),
    OS("Original Product Version", 10014),
    PROJECT_CODE("Project Code", 10210),
    RANK("Rank", 10070),
    RANK2("Rank", 10192),
    RELEASE_NOTE("Release Note", 10020),
    REQUEST_FROM("Request from", 10220),
    REQUIREMENT_TYPE("Requirement Type", 10161),
    RESOLVED_DATE("Resolved Date", 10211),
    RESOLVER_BUILD("Resolver Build", 10172),
    RESOLVER("Resolver", 10080),
    SEVERITY("Severity", 10024),
    SIEBEL_CR_ID("Siebel CR Id", 10021),
    SIEBEL_SR_IDS("Siebel SR IDs", 10150),
    SR_ACCOUNT("SR Account", 10051),
    SVN_REVISION("SVN Revision", 10112),
    SVN_URL("SVN URL", 10140),
    TESTCASES_EXECUTED("Testcases Executed", 10141),
    TESTCASES_FAILED("Testcases Failed", 10142),
    TESTCASES_PASSED("Testcases Passed", 10143),
    TOTAL_TESTCASES("Total Testcases", 10144),
    WORKAROUND("Workaround", 10060),
    ;


    private String name;
    private int id;


    JiraCustomFieldName(
            String name,
            int id) {

        this.name = name;
        this.id = id;
    }


    public int getId() {

        return this.id;
    }


    @Override
    public String toString() {

        return this.name;
    }


}
