package com.tibco.cep.modules.db.functions;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;


/**
 *
 * This class takes an SQL of the form
 * SELECT * FROM FLIGHT WHERE (AIRLINE_CODE = '%AIRLINE_CODE' )
 * 	AND (FLIGHT_NUMBER = '%FLIGHT_NUMBER') AND (FLIGHT_DATE = '%FLIGHT_DATE' )
 * where %AIRLINE_CODE is a pattern, to be treated as an XPath-like expression. This expression
 * has to be applied to the request message to get the actual value to use in the SQL
 *
 * This class further filters out those expressions from the SQL where patterns are not provided
 * (meaning optional clauses)
 *
 * The resulting SQL is converted into a PreparedStatement text. Along with this text, the index and value
 * to be used in the prepared statement is returned in the PSInfo class.
 *
 */
public class SQLHelper {
	
	static Logger logger;
	static {
		try {
			logger = RuleServiceProviderManager.getInstance().getDefaultProvider().getLogger(SQLHelper.class);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	public static final String PATTERN_SYMBOL = "$";

	private Set patternSet;
	private String sqlQueryStr = "";
	private List parsedQueryInfo;
	private List subQueryList;

	private static final String[] func_array={"UPPER","TO_CHAR","NVL","DECODE", "TO_DATE","CONCAT","LOWER","LPAD","RPAD","LTRIM","RTRIM","TRIM",
											   "REPLACE","LENGTH","TO_TIMESTAMP","ROUND","TRUNC","NVL2","SOUNDEX", "IN ", "IN",
											   "ABS", "ACOS" ,"ADD_MONTHS" , "ASCII", "ASIN" , "ATAN", "ATAN2" , "AVG" ,"BFILENAME" ,
											   "CEIL", "CHARTOROWID" , "CHR", "CONVERT", "EXISTS", "COS" , "COSH" , "COUNT" , "DEREF", "DUMP" ,
											   "EXP" , "FLOOR" , "GREATEST", "HEXTORAW" , "INITCAP" , "INSTR" , "INSTRB" , "LAST_DAY" ,
											   "LEAST" , "LN" , "LENGTHB" , 	"LOG", "MAKE_REF", "MAX" ,"MIN" , "MOD" , "MONTHS_BETWEEN" ,
											   "NEW_TIME" , "NEXT_DAY" , "NLS_CHARSET_DECL_LEN", "NLS_CHARSET_ID", "NLS_CHARSET_NAME", "NLS_INITCAP",
											   "NLS_LOWER", "NLSSORT", "NLS_UPPER" , "PERCENT_RANK" , "POWER" ,"RAWTOHEX","REF","REFTOHEX",
											   "ROWIDTOCHAR","SIGN","SIN","SINH","SQRT","STDDEV","SUBSTR","SUBSTRB",
											   "SYSDATE" ,"TAN","TANH", "TO_LOB","TO_MULTI_BYTE","TO_NUMBER","TO_SINGLE_BYTE","TRANSLATE",
											   "USERENV","VARIANCE", "VSIZE", "ROW_NUMBER", "OVER"};

	public SQLHelper(String sqlQueryStr) {
		this.sqlQueryStr = toUpperCase(removeMultipleSpaces(sqlQueryStr));
		parsedQueryInfo=new ArrayList();
		subQueryList=new ArrayList();
	}

//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) throws Exception {
//		//String queryStr="SELECT * FROM CUST WHERE x IN (SELECT * FROM ORDER WHERE y IN (SELECT TEMP FROM TABLE WHERE Y IN (SELECT * FROM CUST WHERE (A='%A/B')))) AND ((Y ='%D/E') OR ((Z='1') AND (C= '%A/D')))";
//		//String queryStr="SELECT * FROM CUST  WHERE X IN (SELECT * FROM ORDER WHERE (X='%A/B')) AND X IN (SELECT * FROM CUST) OR Z IN (SELECT * FROM TABLE) ";
//		//String queryStr ="SELECT * FROM CUST WHERE (x='%A/B') AND  ( Y= '1')";
//		//String queryStr="SELECT * FROM FLIGHT_LEG WHERE (AIRLINE_CODE,FLIGHT_NUMBER, FLIGHT_DATE, LEG_ORIGIN) IN (SELECT AIRLINE_CODE,FLIGHT_NUMBER, FLIGHT_DATE, LEG_ORIGIN FROM AIRBILL_SPLIT_TRIP WHERE (AWB_AIRLINE_CODE = '%A/C') AND (AWB_ORIGIN ='%A/C') AND (AWB_NUMBER = '%A/C') AND (AWB_DATE ='%A/C'))";
//		//String queryStr= "SELECT * FROM WHERE (X ='%A/B') AND (x IN (SELECT * FROM CUST))";
//		//String queryStr = "select * from cust where (x BETWEEN '%A/B' AND '%A/B') and Y = '%A/C' OR ((z='1') and (c= '%A/E')))";
//		//String queryStr="SELECT * FROM CUST WHERE (x LIKE '%A/E%') AND ((y IN ('a','b')) OR (z BETWEEN '%A/E'AND '%A/E') AND (z BETWEEN '%X'AND '%Y') AND (CONCAT(TO_CHAR(1),\"123\") )  )";
//		//String queryStr="SELECT * FROM CUST WHERE (c=_%_A/C) OR (b='_%_A/B') AND (c='_%_A/D')";
//		//String queryStr="SELECT * FROM FLIGHT_LEG WHERE (TAILNUMBER = '%%A/C%%') AND (AIRLINECODE='%%A/C%%') AND (LEGDESTINATION='%%A/C%%') AND (ACTUALINTIME IS NOT NULL) AND ( ACTUALINTIME BETWEEN '%%A/B%%' ) AND (CARRIERCODE = '%%A/C%%') ORDER BY ACTUAL_IN_TIME DESC";
//		//to be tested
//		//String queryStr="SELECT * FROM AIRBILL_UNIT WHERE ((AWB_AIRLINE_CODE, AWB_ORIGIN, AWB_NUMBER, AWB_DATE) IN (SELECT AWB_AIRLINE_CODE, AWB_ORIGIN, AWB_NUMBER, AWB_DATE FROM AIRBILL_SPLIT_TRIP WHERE (AIRLINE_CODE = '%AIRLINE_CODE') AND (FLIGHT_NUMBER = '%FLIGHT_NUMBER') AND (FLIGHT_DATE = '%FLIGHT_DATE') AND (LEG_ORIGIN = '%LEG_ORIGIN') AND CUSTOMER_CONTRACT_FLAG IN ('C','H') AND ((AWB_AIRLINE_CODE,AWB_ORIGIN,AWB_NUMBER,AWB_DATE,REBOOKING_INDEX) IN (SELECT AWB_AIRLINE_CODE, AWB_ORIGIN, AWB_NUMBER, AWB_DATE, MIN(REBOOKING_INDEX) FROM AIRBILL_SPLIT_TRIP AST WHERE AST.AWB_AIRLINE_CODE = AIRBILL_SPLIT_TRIP.AWB_AIRLINE_CODE AND AST.AWB_ORIGIN = AIRBILL_SPLIT_TRIP.AWB_ORIGIN AND AST.AWB_NUMBER = AIRBILL_SPLIT_TRIP.AWB_NUMBER AND AST.AWB_DATE = AIRBILL_SPLIT_TRIP.AWB_DATE AND CUSTOMER_CONTRACT_FLAG IN ('C','H') GROUP BY AWB_AIRLINE_CODE,AWB_ORIGIN,AWB_NUMBER,AWB_DATE)) AND ((AWB_AIRLINE_CODE, AWB_ORIGIN, AWB_NUMBER, AWB_DATE) IN (SELECT AWB_AIRLINE_CODE, AWB_ORIGIN, AWB_NUMBER, AWB_DATE FROM AIRBILL WHERE (AWB_SPLIT_FLAG = '%AWB_SPLIT_FLAG') AND AIRBILL_TYPE_CODE <> 'ADV' AND LEVEL_OF_SERVICE_CODE <> 'PPS'))) AND (UNIT_HISTORY_FLAG = '%UNIT_HISTORY_FLAG'))";
//		//String queryStr="SELECT * FROM AIRBILL WHERE (((AWB_AIRLINE_CODE = '%AWB_AIRLINE_CODE') AND AWB_NUMBER IN (%AWB_NUMBER)) OR ((AWB_AIRLINE_CODE, AWB_ORIGIN, AWB_NUMBER, AWB_DATE) IN (SELECT AWB_AIRLINE_CODE, AWB_ORIGIN, AWB_NUMBER, AWB_DATE FROM AIRBILL_CROSS_REFERENCE WHERE (PREV_AIRLINE_CODE = '%PREV_AIRLINE_CODE') AND PREV_NUMBER IN (%PREV_NUMBER))))";
//		//String queryStr="SELECT /*+ FIRST_ROWS INDEX(AIRBILL_FLIGHT_UNIT IND_AFU_TRACK) */ * FROM AIRBILL_FLIGHT_UNIT WHERE (AWB_AIRLINE_CODE = '%AWB_AIRLINE_CODE') AND (AWB_NUMBER IN (%AWB_NUMBER))(SELECT /*+ FIRST_ROWS INDEX(AIRBILL_SPLIT_TRIP IND_AST_TRACK) */ * FROM AIRBILL_SPLIT_TRIP, FLIGHT_LEG WHERE  (AWB_AIRLINE_CODE = '%AWB_AIRLINE_CODE') AND (AWB_NUMBER IN (%AWB_NUMBER)) AND (LOG_RECEIVED_FLAG = '%LOG_RECEIVED_FLAG') AND AIRBILL_SPLIT_TRIP.AIRLINE_CODE = FLIGHT_LEG.AIRLINE_CODE AND AIRBILL_SPLIT_TRIP.FLIGHT_NUMBER = FLIGHT_LEG.FLIGHT_NUMBER AND AIRBILL_SPLIT_TRIP.FLIGHT_DATE = FLIGHT_LEG.FLIGHT_DATE AND AIRBILL_SPLIT_TRIP.LEG_ORIGIN = FLIGHT_LEG.LEG_ORIGIN) UNION (SELECT /*+ FIRST_ROWS INDEX(AIRBILL_FLIGHT_UNIT IND_AFU_TRACK) */ * FROM AIRBILL_FLIGHT_UNIT, FLIGHT_LEG WHERE  (AWB_AIRLINE_CODE = '%AWB_AIRLINE_CODE') AND (AWB_NUMBER IN (%AWB_NUMBER)) AND AIRBILL_FLIGHT_UNIT.AIRLINE_CODE = FLIGHT_LEG.AIRLINE_CODE AND AIRBILL_FLIGHT_UNIT.FLIGHT_NUMBER = FLIGHT_LEG.FLIGHT_NUMBER AND AIRBILL_FLIGHT_UNIT.FLIGHT_DATE = FLIGHT_LEG.FLIGHT_DATE AND AIRBILL_FLIGHT_UNIT.LEG_ORIGIN = FLIGHT_LEG.LEG_ORIGIN)";
//		//String queryStr="UPDATE AIRBILL_UNIT SET AWB_AIRLINE_CODE = 'a' WHERE (AWB_AIRLINE_CODE = '%AWB_AIRLINE_CODE')";
//		//String queryStr="SELECT DISTINCT * FROM AIRLINE_AFFILIATE, FLIGHT_SPO_STATE, FLIGHT_LEG WHERE ONBOARD_PROCESS_FLAG = 'Y' AND ONBOARD_PROCESS_DATE <= FLIGHT_LEG.LEG_DEPARTURE_DATE AND AIRLINE_AFFILIATE.AFFILIATE_CARRIER_CODE = FLIGHT_LEG.CARRIER_CODE AND (TRACERS_GENERATED_FLAG <> '%TRACERS_GENERATED_FLAG' OR TRACERS_GENERATED_FLAG IS NULL) AND (FLIGHT_LEG.FLIGHT_DATE BETWEEN TO_CHAR(TO_DATE('%ACTUAL_OFF_TIME','YYYYMMDDHH24MISS')-7,'YYYYMMDDHH24MISS') AND '%ACTUAL_OFF_TIME') AND FLIGHT_LEG.AIRLINE_CODE = FLIGHT_SPO_STATE.AIRLINE_CODE AND FLIGHT_LEG.FLIGHT_NUMBER = FLIGHT_SPO_STATE.FLIGHT_NUMBER AND FLIGHT_LEG.FLIGHT_DATE = FLIGHT_SPO_STATE.FLIGHT_DATE AND FLIGHT_LEG.LEG_ORIGIN = FLIGHT_SPO_STATE.LEG_ORIGIN AND (((DEPARTURE_STATUS_CODE = '%DEPARTURE_STATUS_CODE' AND ACTUAL_OFF_TIME <= '%ACTUAL_OFF_TIME') AND SYSTEM_ROE_FLAG = 'Y' AND ONBOARD_WBP_FLAG = 'Y' AND ONBOARD_WBPM_FLAG = 'Y' AND ONBOARD_FLIGHT_LOG_FLAG = 'Y') OR (FLIGHT_STATUS_CODE = 'X' AND SCHEDULED_DEPARTURE_TIME <='%ACTUAL_OFF_TIME')) AND ROWNUM < 101 ORDER BY RECORD_DATE";
//		//String queryStr = "SELECT * FROM ORDERS WHERE ORDERS.CUST_ID IN (SELECT CUST_ID FROM CUSTOMER WHERE LOCATION IN (SELECT LOCATION FROM LOC WHERE (LOC = %REGION) ))";
//		//String queryStr="Select * from orders where orders.cust_id in (select cust_id from customer where location in (select location from loc where C in ('c','a') ))";
//		//String queryStr="Select * from orders where orders.cust_id in (select cust_id from customer where location in (select location from loc where (C in (%abc) )))";
//		//String queryStr="Select * from orders where orders.cust_id in (select cust_id from customer where location in (select location from loc where ( C in %RR ) and (i = %xx/X ))))";
//		//String queryStr="SELECT * FROM APP_CARRIER_PREFERENCES_ITEM WHERE (APPLICATION_CD, AIRLINE_REF_SEQ, PREFERENCE_CD,ACTIVE_FLAG) IN (SELECT DISTINCT APPLICATION_CD, AIRLINE_REF_SEQ, PREFERENCE_CD,ACTIVE_FLAG FROM APP_CARRIER_PREFERENCES_VAL WHERE (HOST_CARRIER_CD = UPPER(%CarrierPreferencesRQ/CarrierCode) ) AND (APPLICATION_CD = UPPER(%CarrierPreferencesRQ/ApplicationCode) ) AND (PREFERENCE_CD = UPPER(%CarrierPreferencesRQ/PreferenceCode) ) AND (NVL(EFFECTIVE_END_DT,'31-DEC-9999') >= %CarrierPreferencesRQ/TransactionStartDate ) --NOTE: EFFECTIVE_END_DT >= THE INPUT START DATE AND  (EFFECTIVE_START_DT <= %CarrierPreferencesRQ/TransactionEndDate ) --NOTE: EFFECTIVE_START_DT <= THE INPUT END DATE AND (ACTIVE_FLAG = %CarrierPreferencesRQ/ActiveFlag)) GROUP BY T";
//		//String queryStr="SELECT * FROM APP_CARRIER_PREFERENCES_ITEM WHERE (APPLICATION_CD, AIRLINE_REF_SEQ, PREFERENCE_CD,ACTIVE_FLAG) IN (SELECT DISTINCT APPLICATION_CD, AIRLINE_REF_SEQ, PREFERENCE_CD,ACTIVE_FLAG FROM APP_CARRIER_PREFERENCES_VAL WHERE (HOST_CARRIER_CD = UPPER(%CarrierPreferencesRQ/CarrierCode) ) AND (APPLICATION_CD = UPPER(%CarrierPreferencesRQ/ApplicationCode) ) AND (PREFERENCE_CD = UPPER(%CarrierPreferencesRQ/PreferenceCode) ) AND (NVL(EFFECTIVE_END_DT,'31-DEC-9999') >= %CarrierPreferencesRQ/TransactionStartDate ) AND  (EFFECTIVE_START_DT <= %CarrierPreferencesRQ/TransactionEndDate ) AND (ACTIVE_FLAG = %CarrierPreferencesRQ/ActiveFlag)) GROUP BY T";
//		//String queryStr="SELECT * FROM EMD_PRODUCTS WHERE (AIRLINE_REF_SEQ, PRODUCT_TYPE_CD) IN (SELECT DISTINCT AIRLINE_REF_SEQ, PRODUCT_TYPE_CD FROM EMD_PRODUCT_AVAILABILITY WHERE ( HOST_CARRIER_CD = UPPER($CarrierCode)) and (nvl(EFFECTIVE_END_DT,'31-DEC-9999') >= $TransactionStartDate) and ( EFFECTIVE_START_DT &lt;= nvl($TransactionEndDate,to_date('31-DEC-9999','DD-MON-YYYY'))) AND (ACTIVE_FLAG = $ActiveFlag)) ORDER BY PRODUCT_TYPE_CD";
//		//String queryStr="SELECT * FROM ITINERARY_VER WHERE (ITINERARY_ID, ITINERARY_VER_NBR) IN (SELECT ITINERARY_ID, ITINERARY_VER_NBR FROM REISSUE_QUOTE WHERE (QUOTE_GROUP_ID = $RetrieveQuoteGroupbyQuoteGroupIDRQ/QuoteGroupID))";
//		//String queryStr="SELECT * FROM FARE_ADJUSTMENT_VER WHERE (ITINERARY_ID, ITINERARY_VER_NBR) IN (SELECT ITINERARY_ID, ITINERARY_VER_NBR FROM TRAVEL_DOCUMENT_VER WHERE (PRIMARY_DOCUMENT_NBR, PRIMARY_ISSUE_DT, PRIMARY_AIRLINE_CD, TRAVEL_DOCUMENT_VER_NBR) IN (SELECT PRIMARY_DOCUMENT_NBR, PRIMARY_ISSUE_DT, PRIMARY_AIRLINE_CD, TRAVEL_DOCUMENT_VER_NBR FROM REFUND_QUOTE WHERE (QUOTE_GROUP_ID = $RetrieveQuoteGroupbyQuoteGroupIDRQ/QuoteGroupID))) UNION SELECT * FROM FARE_ADJUSTMENT_VER WHERE (ITINERARY_ID, ITINERARY_VER_NBR) IN (SELECT ITINERARY_ID, ITINERARY_VER_NBR FROM REFUND_QUOTE WHERE (QUOTE_GROUP_ID = $RetrieveQuoteGroupbyQuoteGroupIDRQ/QuoteGroupID))";
//		//String queryStr="SELECT * FROM FARE_ADJUSTMENT_VER WHERE (ITINERARY_ID, ITINERARY_VER_NBR) IN (SELECT ITINERARY_ID, ITINERARY_VER_NBR FROM TRAVEL_DOCUMENT_VER WHERE (PRIMARY_DOCUMENT_NBR, PRIMARY_ISSUE_DT, PRIMARY_AIRLINE_CD, TRAVEL_DOCUMENT_VER_NBR) IN (SELECT PRIMARY_DOCUMENT_NBR, PRIMARY_ISSUE_DT, PRIMARY_AIRLINE_CD, TRAVEL_DOCUMENT_VER_NBR FROM REFUND_QUOTE WHERE (QUOTE_GROUP_ID = $RetrieveQuoteGroupbyQuoteGroupIDRQ/QuoteGroupID))) UNION SELECT * FROM FARE_ADJUSTMENT_VER WHERE (ITINERARY_ID, ITINERARY_VER_NBR) IN (SELECT ITINERARY_ID, ITINERARY_VER_NBR FROM REFUND_QUOTE WHERE (QUOTE_GROUP_ID = $RetrieveQuoteGroupbyQuoteGroupIDRQ/QuoteGroupID))";
//		//String queryStr="SELECT * FROM FARE_ADJUSTMENT_VER WHERE (ITINERARY_ID, ITINERARY_VER_NBR) IN (SELECT ITINERARY_ID, ITINERARY_VER_NBR FROM TRAVEL_DOCUMENT_VER WHERE (PRIMARY_DOCUMENT_NBR, PRIMARY_ISSUE_DT, PRIMARY_AIRLINE_CD, TRAVEL_DOCUMENT_VER_NBR) IN (SELECT PRIMARY_DOCUMENT_NBR, PRIMARY_ISSUE_DT, PRIMARY_AIRLINE_CD, TRAVEL_DOCUMENT_VER_NBR FROM REFUND_QUOTE WHERE (QUOTE_GROUP_ID = $RetrieveQuoteGroupbyQuoteGroupIDRQ/QuoteGroupID))) UNION SELECT * FROM FARE_ADJUSTMENT_VER WHERE (ITINERARY_ID, ITINERARY_VER_NBR) IN (SELECT ITINERARY_ID, ITINERARY_VER_NBR FROM REFUND_QUOTE WHERE (QUOTE_GROUP_ID = $RetrieveQuoteGroupbyQuoteGroupIDRQ/QuoteGroupID)) UNION SELECT *FROM FARE_ADJUSTMENT_VER WHERE (ITINERARY_ID, ITINERARY_VER_NBR) IN (SELECT ITINERARY_ID, ITINERARY_VER_NBR FROM TRAVEL_DOCUMENT_VER WHERE (PRIMARY_DOCUMENT_NBR, PRIMARY_ISSUE_DT, PRIMARY_AIRLINE_CD, TRAVEL_DOCUMENT_VER_NBR) IN (SELECT PRIMARY_DOCUMENT_NBR, PRIMARY_ISSUE_DT, PRIMARY_AIRLINE_CD, TRAVEL_DOCUMENT_VER_NBR FROM REISSUE_QUOTE WHERE (QUOTE_GROUP_ID = $RetrieveQuoteGroupbyQuoteGroupIDRQ/QuoteGroupID))) UNION SELECT * FROM FARE_ADJUSTMENT_VER WHERE (ITINERARY_ID, ITINERARY_VER_NBR) IN (SELECT ITINERARY_ID, ITINERARY_VER_NBR FROM REISSUE_QUOTE WHERE (QUOTE_GROUP_ID = $RetrieveQuoteGroupbyQuoteGroupIDRQ/QuoteGroupID))";
//		//String queryStr="UPDATE TRAVEL_DOCUMENT_VER SET (PNR_RECORD_LOCATOR = $UpdatePNRTravelDocVerRQ/PNRRecordLocator) WHERE (PRIMARY_AIRLINE_CD = $UpdatePNRTravelDocVerRQ/PrimaryAirlineCd) AND (PRIMARY_DOCUMENT_NBR = $UpdatePNRTravelDocVerRQ/PrimaryDocNbr) AND (PRIMARY_ISSUE_DT = $UpdatePNRTravelDocVerRQ/PrimaryIssueDt) AND (TRAVEL_DOCUEMNT_VER_NBR = $UpdatePNRTravelDocVerRQ/TravelDocVerNbr)";
//		//String queryStr="SELECT * FROM T1 WHERE X IN (SELECT * FROM T2 WHERE Y IN (SELECT * FROM T3 WHERE C=$A/B))";
//		//String queryStr="SELECT DISTINCT * FROM AIRLINE_AFFILIATE, FLIGHT_SPO_STATE, FLIGHT_LEG WHERE (ONBOARD_PROCESS_FLAG = 'Y') AND (ONBOARD_PROCESS_DATE <= FLIGHT_LEG.LEG_DEPARTURE_DATE) AND (AIRLINE_AFFILIATE.AFFILIATE_CARRIER_CODE = FLIGHT_LEG.CARRIER_CODE) AND ((TRACERS_GENERATED_FLAG <> '$TRACERS_GENERATED_FLAG') OR (TRACERS_GENERATED_FLAG IS NULL)) AND (FLIGHT_LEG.FLIGHT_DATE BETWEEN TO_CHAR(TO_DATE('$ACTUAL_OFF_TIME','YYYYMMDDHH24MISS')-7,'YYYYMMDDHH24MISS')) AND ('$ACTUAL_OFF_TIME') AND (FLIGHT_LEG.AIRLINE_CODE = FLIGHT_SPO_STATE.AIRLINE_CODE) AND (FLIGHT_LEG.FLIGHT_NUMBER = FLIGHT_SPO_STATE.FLIGHT_NUMBER) AND (FLIGHT_LEG.FLIGHT_DATE = FLIGHT_SPO_STATE.FLIGHT_DATE) AND (FLIGHT_LEG.LEG_ORIGIN = FLIGHT_SPO_STATE.LEG_ORIGIN) AND ((((DEPARTURE_STATUS_CODE = '$DEPARTURE_STATUS_CODE') AND (ACTUAL_OFF_TIME <= '$ACTUAL_OFF_TIME')) AND (SYSTEM_ROE_FLAG = 'Y') AND (ONBOARD_WBP_FLAG = 'Y') AND (ONBOARD_WBPM_FLAG = 'Y') AND (ONBOARD_FLIGHT_LOG_FLAG = 'Y')) OR ((FLIGHT_STATUS_CODE = 'X') AND (SCHEDULED_DEPARTURE_TIME <='$ACTUAL_OFF_TIME'))) AND (ROWNUM < 101) ORDER BY RECORD_DATE";
//		//String queryStr="SELECT * FROM T1 WHERE ((X='$A/B') AND (Y='$A1'))";
//		//String queryStr="SELECT * FROM SURCHARGE_GROUP_VER WHERE (SURCHARGE_GROUP_VER_ID) IN (SELECT SURCHARGE_GROUP_VER_ID FROM APPLIED_FARE_VER WHERE (ITINERARY_ID, ITINERARY_VER_NBR) IN (SELECT ITINERARY_ID, ITINERARY_VER_NBR FROM TRAVEL_DOCUMENT_VER WHERE (PRIMARY_DOCUMENT_NBR, PRIMARY_ISSUE_DT, PRIMARY_AIRLINE_CD, TRAVEL_DOCUMENT_VER_NBR) IN (SELECT PRIMARY_DOCUMENT_NBR, PRIMARY_ISSUE_DT, PRIMARY_AIRLINE_CD, TRAVEL_DOCUMENT_VER_NBR FROM REFUND_QUOTE WHERE (QUOTE_GROUP_ID = $QuoteGroupID))))";
//		//String queryStr="SELECT * FROM FLIGHT_SEGMENTS WHERE (ITINERARY_ID, ITINERARY_VER_NBR) IN (SELECT ITINERARY_ID, ITINERARY_VER_NBR FROM REISSUE_QUOTE WHERE (QUOTE_GROUP_ID = $QuoteGroupID))";
//		//String queryStr="SELECT * FROM FLIGHT_SEGMENTS WHERE (ITINERARY_ID, ITINERARY_VER_NBR) IN         (SELECT ITINERARY_ID, ITINERARY_VER_NBR         FROM REISSUE_QUOTE         WHERE (QUOTE_GROUP_ID = $QuoteGroupID))                   ";
//		//String queryStr="Select A,B FROM TABLE WHERE X='$Z' ORDER BY A,B";
//		//String queryStr="UPDATE RESSIUE_QUOTE_NOTES SET (NOTE_TXT = $ReissueQuoteNote) WHERE (QUOTE_GROUP_ID = $QuoteGroupID) AND (REISSUE_QUOTE_ID = $ReissueQuoteID)  ";
//		//String queryStr="SELECT * FROM FARE_ADJUSTMENT_VER WHERE (ITINERARY_ID, ITINERARY_VER_NBR) IN (SELECT ITINERARY_ID, ITINERARY_VER_NBR FROM TRAVEL_DOCUMENT_VER WHERE (PRIMARY_DOCUMENT_NBR, PRIMARY_ISSUE_DT, PRIMARY_AIRLINE_CD, TRAVEL_DOCUMENT_VER_NBR) IN (SELECT PRIMARY_DOCUMENT_NBR, PRIMARY_ISSUE_DT, PRIMARY_AIRLINE_CD, TRAVEL_DOCUMENT_VER_NBR FROM REFUND_QUOTE WHERE (QUOTE_GROUP_ID = $RetrieveQuoteGroupbyQuoteGroupIDRQ/QuoteGroupID))) UNION SELECT * FROM FARE_ADJUSTMENT_VER WHERE (ITINERARY_ID, ITINERARY_VER_NBR) IN (SELECT ITINERARY_ID, ITINERARY_VER_NBR FROM REFUND_QUOTE WHERE (QUOTE_GROUP_ID = $RetrieveQuoteGroupbyQuoteGroupIDRQ/QuoteGroupID))";
//		//String queryStr="SELECT ca.application_cd, ca.application_nm FROM cl_applications ca, cl_hosted_customers chc where ca.ruleset_id = chc.ruleset_id";
//		//String queryStr="SELECT L.CARRIER_CD, L.FLITEKEY, L.CLASS_CD,L.PAX_STATUS_CD, L.PASSENGER_CNT, L.INSERT_DT, L.INSERT_SOURCE_ID, L.LAST_UPDATE_DT, L.LAST_UPDATE_SOURCE_ID, L.LAST_UPDATE_USER_ID, L.UPDATE_LOCK_NBR  FROM LP_PAX_CLASS_STATUS_COUNT L WHERE (L.CARRIER_CD=$PaxClassStatusSelectionCriteria/CarrierCode) AND (L.CLASS_CD=$PaxClassStatusSelectionCriteria/ClassCode) AND (L.PAX_STATUS_CD=$PaxClassStatusSelectionCriteria/PassengerStatus) AND ( L.FLITEKEY IN (SELECT FLITEKEY FROM (SELECT FLITEKEY,ROW_NUMBER() OVER(ORDER BY DEPDATE) DEPSEQ, FLITE FROM AFLIGHT WHERE (CARRIER=$PaxClassStatusSelectionCriteria/CarrierCode) AND (FLITE=$PaxClassStatusSelectionCriteria/FlightNumber) AND ( DEPDATE IN (SELECT SCHED_DEPARTURE_UTC_DT FROM FLIGHT_LEG_TIME WHERE (FLIGHT_LEG_CARRIER_CD=$PaxClassStatusSelectionCriteria/CarrierCode) AND (FLIGHT_LEG_FLIGHT_NBR=$PaxClassStatusSelectionCriteria/FlightNumber) AND (FLIGHT_START_DT= $PaxClassStatusSelectionCriteria/FlightStartDate) AND (DEPARTURE_STATION_CD=$PaxClassStatusSelectionCriteria/DepartureStationCode) ) ) AND (DEPSTA=$PaxClassStatusSelectionCriteria/DepartureStationCode) ) WHERE (DEPSEQ=$PaxClassStatusSelectionCriteria/DepartureStationOccurrence) ) )";
//		//String queryStr="SELECT L.CARRIER_CD, L.FLITEKEY, L.CLASS_CD,L.PAX_STATUS_CD, L.PASSENGER_CNT, L.INSERT_DT, L.INSERT_SOURCE_ID, L.LAST_UPDATE_DT, L.LAST_UPDATE_SOURCE_ID, L.LAST_UPDATE_USER_ID, L.UPDATE_LOCK_NBR FROM LP_PAX_CLASS_STATUS_COUNT L WHERE (L.CARRIER_CD=$PaxClassStatusSelectionCriteria/CarrierCode) AND (L.CLASS_CD=$PaxClassStatusSelectionCriteria/ClassCode) AND (L.PAX_STATUS_CD=$PaxClassStatusSelectionCriteria/PassengerStatus) AND ( L.FLITEKEY IN (SELECT FLITEKEY FROM XVIEW WHERE (DEPSEQ=$PaxClassStatusSelectionCriteria/DepartureStationOccurrence) ) ) ";
//		String queryStr="SELECT * FROM SURCHARGE WHERE (surcharge_group_id) IN (SELECT surcharge_group_id FROM TRAVEL_SEGMENT ts WHERE EXISTS (SELECT 1 FROM COUPON_SEGMENTS cs WHERE  EXISTS (SELECT primary_document_nbr, primary_airline_cd, primary_issue_dt FROM TRAVEL_DOCUMENT_BOOKLET B WHERE cs.primary_document_nbr = B.primary_document_nbr AND cs.primary_airline_cd = B.primary_airline_cd  AND cs.primary_issue_dt = B.primary_issue_dt AND ( (CS.primary_document_nbr=$DocumentNumber) OR (booklet_document_nbr=$DocumentNumber) ) AND (CS.primary_airline_cd= $PrimaryAirlineCd) AND (CS.primary_issue_dt = $PrimaryIssueDate)) and (ts.itinerary_id = cs.itinerary_id)  and (ts.seg_seq = cs.seg_seq)))";
//
//		SQLHelper helper = new SQLHelper(queryStr);
//		Map patternValues = new HashMap();
//		//Get pattern values from the query string
//		Set set = helper.getPatterns();
//		Iterator iter = set.iterator();
//		for (int i = 0; iter.hasNext(); i++) {
//			SQLPattern p = (SQLPattern) iter.next();
//			List values = new ArrayList();
//			values.add("a");
//			p.setPatternValues(values);
//			patternValues.put(p.getPattern(), p);
//		}
//		PSInfo psinfo = helper.getPreparedStmtInfo(patternValues);
//
//		patternValues=new HashMap();
//		psinfo = helper.getPreparedStmtInfo(patternValues);
//	}

	/**
	 * This function obtains all the patterns from the input query string
	 * @return- Set- Containing objects, where each object represents SQLPattern class .
	 * here only 2 fields of this class are populated i.e. {  String pattern, INT patternType }
	 * and patterntype is 0 => 1 value, 1=> 2 values (between clause) 2=> Multi-valued (IN clause)
	 */
	public Set getPatterns() throws Exception {

		//Checks whether opening and closing brackets are matching in the SQL query.

		int len = sqlQueryStr.length();
		int openingBracket = 0;
		int closingBracket = 0;
		char c = ' ';
		for(int i=0;i<len;i++){
			c = sqlQueryStr.charAt(i);
			if(c == '(') {
				openingBracket++;
			} else if(c == ')') {
				closingBracket++;
			}
		}
		
		if(openingBracket != closingBracket) {
			throw new IllegalArgumentException("Unmatched braces in query string - " + sqlQueryStr);
		}

		StringTokenizer st = new StringTokenizer(sqlQueryStr);
		StringBuffer sb = new StringBuffer();
		Set patternSet = new HashSet();
		int tokenCount = st.countTokens();
		boolean inFlag = false;
		boolean betweenFlag = false;
		boolean likeFlag = false;
		int argCount = 0;
		String arg[] = { "", "" };
		int ctr = 0;
		String patternRegExpr = "'*(\\" + PATTERN_SYMBOL
				+ ")[A-Za-z_0-9]+/*[A-Za-z_0-9]*'*";

		for (int i = 0; i < tokenCount; i++) {
			String token = st.nextToken();
			if (token.equalsIgnoreCase("IN")) {
				inFlag = true;
				betweenFlag = false;
				likeFlag = false;
			} else if (token.equalsIgnoreCase("BETWEEN")) {
				String temp = sqlQueryStr.substring(sb.length());
				if (temp.matches("BETWEEN\\s*" + ".*" +patternRegExpr+".*"
						+ "\\s*AND\\s*" + ".*"+patternRegExpr + ".*")) {
					argCount = 2;
				} else {
					argCount = 1;
				}
				betweenFlag = true;
				inFlag = false;
				likeFlag = false;
			} else if (token.equalsIgnoreCase("LIKE")) {
				likeFlag = true;
				inFlag = false;
				betweenFlag = false;
			} else if (token.matches((".*" + patternRegExpr + ".*"))) {

				String xPath = getXPathFromToken(token);

				if (xPath != "") {
					SQLPattern p = new SQLPattern();
					if (inFlag) {
						p.setPatternType(3);
						p.setPattern(xPath);
						patternSet.add(p);
					} else if (likeFlag) {
						p.setPattern(xPath);
						p.setPatternType(0);
						patternSet.add(p);
					} else if (betweenFlag) {
						if (argCount == 1) {
							p.setPattern(xPath);
							p.setPatternType(2);
							patternSet.add(p);
							betweenFlag = false;
							ctr = 0;
						} else {
							arg[ctr++] = xPath;
							if (ctr == 2) {
								betweenFlag = false;
								ctr = 0;
								if (arg[0].equalsIgnoreCase(arg[1])) {
									p.setPattern(xPath);
									p.setPatternType(2);
									patternSet.add(p);

								} else {
									p.setPattern(arg[0]);
									p.setPatternType(0);
									patternSet.add(p);
									SQLPattern p1 = new SQLPattern();
									p1.setPattern(arg[1]);
									p1.setPatternType(0);
									patternSet.add(p1);
								}
							}
						}
					} else {
						p.setPattern(xPath);
						p.setPatternType(0);
						patternSet.add(p);
					}

					inFlag = false;
					likeFlag = false;

				}
			} else if (token.equals("=") || token.equals("<>")
					|| token.equals(">") || token.equals("<")
					|| token.equals("<=") || token.equals(">=")) {
				inFlag = false;
				betweenFlag = false;
				likeFlag = false;
			}
			sb.append(token).append(" ");
		}

		return patternSet;
	}

	/**
	 * This function first parses the SQL query string and then converts the parsed SQL
	 * (SQL after removing all null value patterns and their expressions) to a
	 * prepared statement and puts the values for the "?" in the prepared statement in map.
	 * @param patternValues - Map containing pair (String Pattern,SQLPatteren Object)
	 * @return PSInfo Object- containing {String preparedStatment , Map }
	 *                        where map contains pairs( patternIndex and it's value)
	 */
	public PSInfo getPreparedStmtInfo(Map patternValues) {
		patternSet = patternValues.keySet();
		PSInfo psInfoObj = parseSqlQuery(patternValues);
		return psInfoObj;
	}

	/*
	 * This is the main function that parses the input query string for subquries
	 * and then processes each subquery by removing all null value patterns
	 * and then reforms the input query with modified subqueries
	 * @param patternValues - Map containing pair (String Pattern,SQLPatteren Object)
	 * @return -PSInfo Object -containing {String preparedStatment , Map }
	 *                        where map contains pairs( patternIndex and it's value)
	 */
	private PSInfo parseSqlQuery(Map patternValues) {
		StringBuffer sb;
		PSInfo psInfoObj = null;

		if (parsedQueryInfo == null || parsedQueryInfo.size() == 0) {
			subQueryList = getSubqueries(sqlQueryStr);
			sort(subQueryList);
			for (int i = 0; i < subQueryList.size(); i++) {
				SQLQueryInfo sqlQueryInfo=new SQLQueryInfo();
				QueryBean qb=((QueryBean) subQueryList.get(i));
				String subQuery=qb.getQueryString();
				sqlQueryInfo.setSubQuery(subQuery);
				parsedQueryInfo.add(sqlQueryInfo);
			}
		}

		sb = new StringBuffer(processSubQuery(((SQLQueryInfo)parsedQueryInfo.get(0)).getSubQuery(),false,(SQLQueryInfo)parsedQueryInfo.get(0)));
		int lastInsertionIndex = 0;
		for (int i = 1; i < parsedQueryInfo.size(); i++) {
			String oldExpr = ((SQLQueryInfo)parsedQueryInfo.get(i)).getSubQuery();
			String newExpr = processSubQuery(oldExpr,true,(SQLQueryInfo)parsedQueryInfo.get(i));
			String insertionPrefix = ((QueryBean) subQueryList.get(i)).getInsertionPrefix();
			int insertionIndex = sb.indexOf(insertionPrefix,
					lastInsertionIndex)
					+ insertionPrefix.length();
			String tempStr="(" + newExpr + ")";
			sb.insert(insertionIndex,tempStr );
			lastInsertionIndex = insertionIndex;
		}
		String finalSql = "";
		finalSql = sb.toString();
		psInfoObj = getPSInfo(finalSql, patternValues);

		return psInfoObj;

	}
	
	/*
	 * This function formats the input SQL i.e. replaces multiple spaces with a single space
	 * @param str-String
	 * @return String
	 */
	private static String removeMultipleSpaces(String str){
		StringTokenizer st = new StringTokenizer(str);
		StringBuffer strBuf = new StringBuffer();
		while (st.hasMoreTokens()) {
			  strBuf = strBuf.append(st.nextToken()).append(" ");
		}
		return strBuf.toString();
	}
	
	/*
	 * This function converts the input string to UpperCase such that
     * pattern string and string between ' ' is kept as it is
	 * @param str-String
	 * @return String- updated string
	 */
	private static String toUpperCase(String str){
		StringBuffer sb=new StringBuffer();
		StringBuffer tempSb=new StringBuffer();
		int len=str.length();
		int i=0;
		while(i<len){
			char c=str.charAt(i);
			int pattern_len=i+PATTERN_SYMBOL.length();
			if(c== '\''){
				sb.append(tempSb.toString().toUpperCase());
				sb.append(c);
				i++;
				while(str.charAt(i)!='\''){
					sb.append(str.charAt(i));
					i++;
				}
				sb.append(str.charAt(i));
				i++;
				tempSb.delete(0, tempSb.length());
			} else if(pattern_len <len && str.substring(i,pattern_len).equalsIgnoreCase(PATTERN_SYMBOL)){
				sb.append(tempSb.toString().toUpperCase());
				sb.append(str.substring(i,pattern_len));
				i=pattern_len;
				while(str.charAt(i)!=' ' && str.charAt(i)!=')'){
					sb.append(str.charAt(i));
					i++;
				}
				sb.append(str.charAt(i));
				i++;
				tempSb.delete(0, tempSb.length());

			} else {
				tempSb.append(c);
				i++;
			}
		}
		sb.append(tempSb.toString().toUpperCase());
		return sb.toString();
	}

	/*
	 * This function replaces all patterns present in input string with "?" and
	 * puts it's respective value in the map
	 *
	 * @param query-String Sql query after removing all null value patterns and their expressions
	 * @param patternValues Map containing pair (String Pattern,SQLPatteren Object)
	 * @return PSInfo Object PSInfo {String preparedStatment , Map } where map
	 *         contains pairs( patternIndex and it's value)
	 */
	private PSInfo getPSInfo(String query, Map patternValues) {
		Map map = new TreeMap();
		int counter = 1;
		int argCount = 0;
		int argCtr = 0;
		boolean likeFlag = false;
		boolean betweenFlag = false;
		StringBuffer sb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(query);
		int tokenCount = st.countTokens();
		Object nextVal = null;
		String patternRegExpr = "'*(\\" + PATTERN_SYMBOL
				+ ")[A-Za-z_0-9]+/*[A-Za-z_0-9]*'*";
		for (int i = 0; i < tokenCount; i++) {
			String token = st.nextToken();
			if (token.equalsIgnoreCase("LIKE")) {
				likeFlag = true;
				betweenFlag = false;

			} else if (token.equalsIgnoreCase("BETWEEN")) {
				String temp = query.substring(sb.length());
				if (temp.matches("\\s*BETWEEN\\s*" + ".*"+ patternRegExpr +".*"+ "\\s*AND\\s*"
						+".*"+ patternRegExpr + ".*")) {
					argCount = 2;
				} else {
					argCount = 1;
				}
				betweenFlag = true;
				likeFlag = false;
			} else if (token
					.matches((".*'*(\\" + PATTERN_SYMBOL + ")[A-Za-z_0-9]+/*[A-Za-z_0-9]*%*'*.*"))) {

				String xPath = getXPathFromToken(token);

				if (xPath != "") {
					if (likeFlag) {
						SQLPattern obj = (SQLPattern) patternValues.get(xPath);
						List values = (List) obj.getPatternValues();
						Object valueObj = values.get(0);
						String qMark = "?";
						map.put(new Integer(counter), valueObj);
						counter++;
						token = token.replaceFirst("('*(\\" + PATTERN_SYMBOL
								+ ")[A-Za-z_0-9]+/*[A-Za-z_0-9]*'*)", qMark);
					} else if (betweenFlag) {
						if (argCount == 1) {
							SQLPattern obj = (SQLPattern) patternValues
							.get(xPath);
							List values = (List) obj.getPatternValues();
							String qMark = "";
							for (int ctr = 0; ctr < values.size(); ctr++) {
								Object valueObj = values.get(ctr);
								map.put(new Integer(counter), valueObj);
								counter++;
								qMark = qMark + "? and ";
							}
							qMark = qMark.substring(0, qMark.length() - 4);
							token = token.replaceFirst(patternRegExpr, qMark);
							betweenFlag = false;
							argCtr = 0;
						} else {
							argCtr++;
							SQLPattern obj = (SQLPattern) patternValues.get(xPath);
							int patternType = obj.getPatternType();
							List values = (List) obj.getPatternValues();
							Object valueObj;
							if (argCtr == 1) {
								valueObj = values.get(0);
								map.put(new Integer(counter), valueObj);
								counter++;
								if (patternType == 2) {
									valueObj = values.get(1);
									nextVal = valueObj;
								}
								token = token.replaceFirst(patternRegExpr, "?");
							}
							if (argCtr == 2) {
								argCtr = 0;
								betweenFlag = false;
								if (patternType == 2) {
									map.put(new Integer(counter), nextVal);
									counter++;
								} else {
									valueObj = values.get(0);
									map.put(new Integer(counter), valueObj);
									counter++;
								}
								token = token.replaceFirst(patternRegExpr, "?");
							}
						}

					} else {
						SQLPattern obj = (SQLPattern) patternValues.get(xPath);
						List values = (List) obj.getPatternValues();
						String qMark = "";
						for (int ctr = 0; ctr < values.size(); ctr++) {
							Object valueObj = values.get(ctr);
							map.put(new Integer(counter), valueObj);
							counter++;
							qMark = qMark + "?,";
						}
						qMark = qMark.substring(0, qMark.length() - 1);
						token = token.replaceFirst("('*(\\" + PATTERN_SYMBOL
								+ ")[A-Za-z_0-9]+/*[A-Za-z_0-9]*'*)", qMark);
					}
				}
				likeFlag = false;

			} else if (token.equals("=") || token.equals("<>")
					|| token.equals(">") || token.equals("<")
					|| token.equals("<=") || token.equals(">=")) {
				likeFlag = false;
				betweenFlag = false;
			}
			sb.append(token).append(" ");

		}

		String pStmt = sb.toString();
		if (true) {
			int len = pStmt.length();
			int index = map.size() ;
			for (int i = len - 1; i >= 0; i--) {
				char c = sb.charAt(i);
				if (c == '?') {
					sb.deleteCharAt(i);
					if (map.get(new Integer(index)) == null) {
						sb.insert(i, "null" );
					} else {
						sb.insert(i, map.get(new Integer(index)).toString() );
					}
					index--;
				}
			}

		}

		PSInfo obj = new PSInfo();
		obj.setPatternValues(map);
		obj.setPsStmt(pStmt);
		obj.setSqlWithVal(sb.toString());

		return obj;

	}

	/**
	 * This function sorts the input arraylist of "QueryBean" objects in
	 * ascending order of insertionIndex.
	 *
	 * @param queryList list of "QueryBean" objects
	 * @return
	 */
	private void sort(List queryList) {
		for (int i = 0; i < queryList.size(); i++) {
			for (int j = i + 1; j < queryList.size(); j++) {
				QueryBean currentQb = (QueryBean) queryList.get(i);
				int currentIndex = currentQb.getInsertionIndex();
				QueryBean nextQb = (QueryBean) queryList.get(j);
				int nextIndex = nextQb.getInsertionIndex();
				QueryBean tempQb = new QueryBean();
				if (nextIndex < currentIndex) {
					tempQb = currentQb;
					queryList.set(i, nextQb);
					queryList.set(j, tempQb);
				}
			}
		}

	}

	/*
	 * This function separates all the sub-queries from the input Sql query string
	 *
	 * @param -String-Sql
	 *            query
	 * @return ArrayList-arraylist of "QueryBean" objects, each object
	 *         representing one subquery
	 */
	private ArrayList getSubqueries(String queryStr) {

		//replace all spaces between a opening brace and SELECT
		Pattern p = Pattern.compile("[(]\\s*SELECT", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(queryStr);
		queryStr = m.replaceAll("(SELECT");
		
		//this is a counter for opening brace '(', but it excludes a opening brace that is followed by e.g. '(SELECT' 
		int skip = 0;
		
		StringBuffer sb = new StringBuffer();
		ArrayList subQueryList = new ArrayList();
		ArrayList skipCtrList = new ArrayList();
		int deletedStrLen = 0;
		int len = queryStr.length();
		for (int i = 0; i < len; i++) {
			char c = queryStr.charAt(i);
			if (c == '(') {
				sb.append(c);
				if(queryStr.substring(i + 1, i + 7).equalsIgnoreCase("SELECT")) {
					skipCtrList.add(new Integer(skip));
					skip = 0;
				} else {
					skip++;
				}
			} else if (c == ')') {
				sb.append(c);
				if (skip == 0) {
					int openingIndex = sb.lastIndexOf("(SELECT");
					if (openingIndex > -1) {
						QueryBean qb = new QueryBean();
						String subQ = sb.substring(openingIndex + 1, i
								- deletedStrLen);
						qb.setQueryString(subQ);
						String tempStr = "";
						if (subQ.indexOf(" IN ") > -1) {
							tempStr = subQ.substring(0, subQ.indexOf(" IN "));
						} else if(subQ.indexOf(" EXISTS ") > -1){
							tempStr = subQ.substring(0, subQ.indexOf(" EXISTS "));
						} else {
							tempStr = subQ;
						}
						int insertionIndex=queryStr.indexOf("(" + tempStr);
						while(isPresentInsertionIndex(subQueryList,insertionIndex)){
							insertionIndex=queryStr.indexOf("(" + tempStr,insertionIndex+tempStr.length());
						}
						
						int index = sb.indexOf("(" + subQ);
						qb.setInsertionIndex(insertionIndex);
						qb.setInsertionPrefix(sb.substring(index - 5, index));
						subQueryList.add(qb);
						sb.delete(openingIndex, i + 1);
						deletedStrLen = (i + 1) - openingIndex;
						skip = ((Integer) skipCtrList
								.get(skipCtrList.size() - 1)).intValue() + 1;
						skipCtrList.remove(skipCtrList.size() - 1);

					}
				}
				skip--;

			} else {
				sb.append(c);
			}
		}

		QueryBean qb = new QueryBean();
		qb.setQueryString(sb.toString());
		qb.setInsertionIndex(0);
		subQueryList.add(qb);

		return subQueryList;
	}
	
	/*
	 * This function checks whether the insertion index of subquery is already been used or not
	 * @param subQueryList
	 * @param insertionIndex
	 * @return boolean
	 */
	private boolean isPresentInsertionIndex(ArrayList subQueryList,int insertionIndex){
		for (int j = 0; j < subQueryList.size(); j++) {
			QueryBean q=(QueryBean)subQueryList.get(j);
			if(insertionIndex==q.getInsertionIndex()){
				return true;
			}
		}
		return false;
		
	}

	/*
	 * This function separates where part from input query string
	 * @param queryStr -String- Sql query
	 * @return String- parsed sql query
	 */
	private String processSubQuery(String queryStr,boolean isSubquery,SQLQueryInfo sqlQueryInfo) {
		String updatedQuery = "";
		String wherePart = "";
		String orderByPart = "";
		String groupByPart = "";
		String processedWherePart = "";
		String tempStr[] = null;

		Pattern p = Pattern.compile("ORDER BY", Pattern.CASE_INSENSITIVE);
		tempStr = p.split(queryStr);

		if (tempStr.length > 1) {
			orderByPart = tempStr[1];
		}

		p = Pattern.compile("GROUP BY", Pattern.CASE_INSENSITIVE);
		tempStr = p.split(tempStr[0]);
        
		if (tempStr.length > 1) {
			groupByPart = tempStr[1];
		}
		//check if it is update statement
		if(tempStr[0].indexOf(" SET ")>-1){
			wherePart=tempStr[0].substring(tempStr[0].indexOf(" SET "),tempStr[0].length());
			tempStr[0]=tempStr[0].substring(0,tempStr[0].indexOf(" SET "));
			processedWherePart = processWherePart(wherePart,sqlQueryInfo);
			String setPart=processedWherePart.substring(processedWherePart.indexOf("SET")+3,processedWherePart.indexOf("WHERE") );
			setPart=setPart.replaceAll("\\s*,*\\s*(\\*\\*\\*)\\s*,*", "");
			String onlyWherePart=processedWherePart.substring(processedWherePart.indexOf("WHERE")+5 );
			onlyWherePart=onlyWherePart.replaceAll("\\s*(\\*\\*\\*)\\s*", "");
			processedWherePart=(setPart.length()>0 ? " SET "+ setPart:"" )+(onlyWherePart.length()>0 ? " WHERE " + onlyWherePart:"" );
			tempStr[0]=tempStr[0] + processedWherePart;
			processedWherePart="***";
		} else {
			p = Pattern.compile("WHERE", Pattern.CASE_INSENSITIVE);
			if(tempStr[0].indexOf("WHERE")> -1){
				wherePart=tempStr[0].substring(tempStr[0].indexOf("WHERE")+5);
				tempStr[0]=tempStr[0].substring(0,tempStr[0].indexOf("WHERE"));
				processedWherePart = processWherePart(wherePart,sqlQueryInfo);
			}
		}
		
		updatedQuery = tempStr[0]
				+ ((!processedWherePart.matches("\\s*\\*\\*\\*\\s*") && !"".equals(processedWherePart)) ? "WHERE"
						+ processedWherePart: "")
						+ (groupByPart.length() > 0 ? "GROUP BY"
								+ groupByPart : "")
						+ (orderByPart.length() > 0 ? "ORDER BY"
								+ orderByPart : "") ;
	
		return updatedQuery;
	}

	/*
	 * This function removes the null value patterns and corresponding
	 * expressions from wherePart of sql query by parsing it 3 times
	 *
	 * @param wherePart where clause of sql query
	 * @return String- parsed where clause
	 */
	private String processWherePart(String wherePart,SQLQueryInfo sqlQueryInfo) {

		wherePart = firstPass(wherePart,sqlQueryInfo);
	   	wherePart = secondPass(wherePart);
		wherePart = thirdPass(wherePart);
		return wherePart;
	}

	/*
	 * This function replaces 1 or more spaces before and after ) and  ( respectively by a single space.
	 * @param str-String
	 * @return String
	 */
	private String removeSpaces(String str) {
		str = str.replaceAll("\\s+\\)", " )");
		str = str.replaceAll("\\(\\s+", "( ");
		return str;
	}

	/*
	 * This function does the final parsing of the where clause of SQL query and removes all meaning less expressions
	 * @param wherePart-String
	 * @return String
	 */
	private String thirdPass(String wherePart) {

		wherePart = wherePart.replaceAll("\\(\\s*\\*\\*\\*\\s*\\)", "***");
		wherePart = RemoveINAndWhereClause(wherePart);
		wherePart = removeRedundantExpr(wherePart);
		wherePart = removeSpaces(wherePart);

		return wherePart;
	}

	/*
	 * This function removes all (Y IN ***) types expressions and
	 * all sub queries whose 'Where' became "" since all expressions in it got removed
	 * @param str-String
	 * @return String
	 */
	private String RemoveINAndWhereClause(String str) {
		str=str.replaceAll("\\s*,\\s*", ",");
		Pattern p = Pattern.compile(
				"\\s*[(a-zA-Z0-9_.,)]+\\s*IN\\s*(\\*\\*\\*)\\s*",
				Pattern.CASE_INSENSITIVE);
		Pattern p1 = Pattern
				.compile(
						"\\(\\s*SELECT\\s*[a-zA-Z._*0-9\\s,]+\\s*WHERE\\s*(\\*\\*\\*)\\s*\\)",
						Pattern.CASE_INSENSITIVE);

		Matcher m = p.matcher(str);
		Matcher m1 = p1.matcher(str);

		boolean b = m.find();
		boolean b1 = m1.find();
		while (b || b1) {
			str = m.replaceFirst("***");
			m1 = p1.matcher(str);
			str = m1.replaceFirst("***");
			m = p.matcher(str);
			b = m.find();
			b1 = m1.find();
		}
		return str;
	}

	/*
	 * This function removes all expressions whose one of the operands got removed as a result of first pass.
	 * @param wherePart-String -Where clause of sql query
	 * @return -String updated where clause
	 */
	private String secondPass(String wherePart) {
		ArrayList subExprList = new ArrayList();
		StringBuffer sb = new StringBuffer();
		subExprList=parseSecondTime(wherePart);
		sb = new StringBuffer(((QueryBean) subExprList.get(0)).getQueryString());
		int lenDiff = 0;
		for (int i = 1; i < subExprList.size(); i++) {
			String oldExpr = ((QueryBean) subExprList.get(i)).getQueryString();
			String temp = oldExpr.replaceAll("\\(\\*\\*\\*\\)", "***");
			String newExpr = removeRedundantExpr(temp);
			int insertionIndex = ((QueryBean) subExprList.get(i))
					.getInsertionIndex();
			insertionIndex = insertionIndex + lenDiff;
			if (newExpr != "") {
				sb = sb.insert(insertionIndex, "(" + newExpr + ")");
			}
			lenDiff = lenDiff + newExpr.length() - oldExpr.length();
		}

		return removeSpaces(sb.toString());
	}

	/*
	 * This function removes all the redundant expressions in where part.
	 * parses the query to look for AND and OR operators
	 * and if the expressions are missing the function removes or reforms the query
	 * for example, if the where part is where *** AND $A/B then this function
	 * removes the where part completely
	 * @param wherePart
	 * @return wherePart
	 */
	private ArrayList parseSecondTime(String wherePart){
		ArrayList subExprList = new ArrayList();
		StringBuffer sb = new StringBuffer();
		String replacePattern = "[(]\\s*\\(";
		wherePart = wherePart.replaceAll(replacePattern, "((");
		int len = wherePart.length();
		int deletedStrLen = 0;
		int skip = 0;
		for (int i = 0; i < len; i++) {
			char c = wherePart.charAt(i);
			if (c == '(') {
				sb.append(c);
				if (!(wherePart.charAt(i + 1) == '(')) {
					skip++;
				} else {
					skip = 0;
				}
			} else if (c == ')') {
				sb.append(c);
				if (skip <= 0) {
					int openingIndex = sb.lastIndexOf("((");
					if (openingIndex > -1) {
						QueryBean qb = new QueryBean();
						String subQ = sb.substring(openingIndex + 1, i
								- deletedStrLen);
						qb.setQueryString(subQ);
						int index = wherePart.indexOf("(" + subQ);
						if(index>0){
						qb.setInsertionIndex(index);
						subExprList.add(qb);
						sb.delete(openingIndex, i + 1);
						deletedStrLen = (i + 1) - openingIndex;
						}
					}

				}
				skip--;

			} else {
				sb.append(c);
			}
		}
		QueryBean qb = new QueryBean();
		qb.setQueryString(sb.toString());
		qb.setInsertionIndex(0);
		subExprList.add(qb);
		sort(subExprList);

		return subExprList;

	}

    /*
     * This function removes all expressions of type (*** AND|OR ***) ,(*** AND|OR X),(X AND|OR ***) from input string
     * @param str-String
     * @return String
     */
	private String removeRedundantExpr(String str) {

		Pattern p = Pattern.compile("(\\*\\*\\*)\\s+(OR|AND)",
				Pattern.CASE_INSENSITIVE);
		Pattern p1 = Pattern.compile("(OR|AND)\\s+(\\*\\*\\*)",
				Pattern.CASE_INSENSITIVE);

		Matcher m = p.matcher(str);
		Matcher m1 = p1.matcher(str);

		boolean b = m.find();
		boolean b1 = m1.find();
		while (b || b1) {
			str = m.replaceFirst("");
			m1 = p1.matcher(str);
			str = m1.replaceFirst("");
			m = p.matcher(str);
			b = m.find();
			b1 = m1.find();
		}
		return str;
	}

	/*
	 * This function parses the input string and first finds out the null value patterns
	 * and then replaces their expressions with "***"
	 * e.g ( X = '%A/B' ) and (Y >= %A/C ) and if A/B =null then  =>  (***) and (Y >= %A/C )
	 * @param wherePart- String-where part of sql query
	 * @return String-updated where part of sql query
	 */
	private String firstPass(String wherePart,SQLQueryInfo sqlQueryInfo) {
		ArrayList subExprList = new ArrayList();
		StringBuffer sb = null;
		int lenDiff = 0;
		
		if(sqlQueryInfo.getFirstPassResultList()== null || sqlQueryInfo.getFirstPassResultList().size()==0){
			subExprList = parseFirstTime(wherePart);
			sqlQueryInfo.setFirstPassResultList(subExprList);
		}
		else
			subExprList=(ArrayList) sqlQueryInfo.getFirstPassResultList();
		
		String oldFirstSubExpr = ((QueryBean)(subExprList.get(0))).getQueryString();
		
		if (oldFirstSubExpr.indexOf(" SET ")> -1) {
			String newFirstSubExpr = processSetClauses(oldFirstSubExpr);
			lenDiff = newFirstSubExpr.length() - oldFirstSubExpr.length();
			sb = new StringBuffer(replaceXpath(newFirstSubExpr));
		}
		else {
			sb = new StringBuffer(replaceXpath(oldFirstSubExpr));
		}
		
		
		for (int i = 1; i < subExprList.size(); i++) {
			String oldExpr = ((QueryBean) subExprList.get(i)).getQueryString();
			String newExpr = replaceXpath(oldExpr);
			int insertionIndex = ((QueryBean) subExprList.get(i))
					.getInsertionIndex();
			insertionIndex = insertionIndex + lenDiff;
			sb = sb.insert(insertionIndex, "(" + newExpr + ")");
			lenDiff = lenDiff + newExpr.length() - oldExpr.length();
		}
        return sb.toString();
	}

	/*
	 * This function searches for all the expressions in the input string
	 * and puts them in the ArryList as QueryBean objects
	 * @param wherePart -String -Where clause of SQL
	 * @return -ArrayList- containing all subexpressions (in the form of QueryBean objects )in the where clause
	 */
	private ArrayList parseFirstTime(String wherePart) {

		StringBuffer sb = new StringBuffer();
		ArrayList subExprList = new ArrayList();
		int deletedStrLen = 0;
		int previousIndex = 0;
		int skip=0;
		int lastOpeningIndex=0;
		int len = wherePart.length();
		for (int i = 0; i < len; i++) {
			char c = wherePart.charAt(i);
			sb.append(c);
			if(c == '('){
				for(int ctr=0;ctr<func_array.length;ctr++){
					String func=func_array[ctr];
					int temp=i-func.length();
					if(i-func.length() >=0){
					if(wherePart.substring(temp,i).equalsIgnoreCase(func))
						skip++;
					}
				}

				if(skip ==0) {
				  lastOpeningIndex=i;
				}
			}
			
			if (c == ')') {
				if(skip ==0){
					int openingIndex=lastOpeningIndex-deletedStrLen;
					if (openingIndex > -1) {
						QueryBean qb = new QueryBean();
						String subeExpr = sb.substring(openingIndex + 1, i
								- deletedStrLen);
						qb.setQueryString(subeExpr);
						int index = wherePart.indexOf("(" + subeExpr + ")",
								previousIndex + 1);
						if (index >= 0) {
							qb.setInsertionIndex(index);
							subExprList.add(qb);
							sb.delete(openingIndex, i + 1);
							deletedStrLen = (i + 1) - openingIndex;
							previousIndex = index;
						}
					}
				} else {
					skip--;
				}

			}
		}
		
		QueryBean qb = new QueryBean();
		qb.setQueryString(sb.toString());
		qb.setInsertionIndex(0);
		subExprList.add(qb);
		sort(subExprList);

		return subExprList;
	}

	/*
	 * This function checks whether value of pattern in the input expression is null or not.
	 * If it is null entire expression is replaced by ***.
	 * @param expr-String- Any expression of the form (x <|>|=|<>|<=|>= '%A/B')
	 * @return String -Updated expression
	 */
	private String replaceXpath(String expr) {
		StringTokenizer st = new StringTokenizer(expr);
		StringBuffer sb = new StringBuffer();
		int tokenCount = st.countTokens();
		for (int i = 0; i < tokenCount; i++) {
			String token = st.nextToken();
			if (token.matches(".*(\\" + PATTERN_SYMBOL + ").*/*.*.*")) {
				String xPath = getXPathFromToken(token);
				if (!patternSet.contains(xPath)) {
					if(logger != null) {
						logger.log(Level.DEBUG, "Pattern %s not found in statement", xPath);
					}
					token = "***";
				}
			}
			sb.append(token).append(" ");
		}
		
		if (sb.indexOf("***") > -1)
			return "***";
		else
			return expr;
	}
	
	/*
	 * This function extract xpath from the given token
	 */
	private String getXPathFromToken(String token){
		
		String xPath = "";
		
		int patternIndex    = token.indexOf(PATTERN_SYMBOL);
		int patternLength   = PATTERN_SYMBOL.length();
		int xPathStartIndex = patternIndex + patternLength;
		
		if(token.indexOf(",",patternIndex) > -1){
			xPath = token.substring(xPathStartIndex, token.indexOf(",", patternIndex));
		} else if (token.indexOf("|",patternIndex) > -1) {
			xPath = token.substring(xPathStartIndex, token.indexOf("|", patternIndex));
		} else if (token.indexOf("%",patternIndex) > -1) {
			xPath = token.substring(xPathStartIndex, token.indexOf("%", patternIndex));
		} else if (token.indexOf("'",patternIndex) > -1) {
			xPath = token.substring(xPathStartIndex, token.indexOf("'", patternIndex));
		} else if (token.indexOf(")",patternIndex) > -1) {
			xPath = token.substring(xPathStartIndex, token.indexOf(")", patternIndex));
		} else {
			xPath = token.substring(xPathStartIndex);
		}
		
		return xPath;
	}
	
	// This function removes the null SET clauses
	private String processSetClauses(String whereStmt) {
		StringBuffer setStmt = new StringBuffer(whereStmt);
		int whereIdx = setStmt.indexOf("WHERE");
		int setIdx = setStmt.indexOf("SET");
		String sb = setStmt.substring( setIdx + 3, whereIdx-1);
		String[] tokens = sb.split(",");
		StringBuffer processedSetClauses = new StringBuffer("");
		
		int nonNullTokenCnt = 0;
		try {
			if (tokens.length <1)
				throw new RuntimeException("No set clauses found !!");
			for(int i=0;i< tokens.length;i++){
				tokens[i] = replaceXpath(tokens[i]);
				if(!tokens[i].equals("***")) {
					nonNullTokenCnt++;
					processedSetClauses.append(tokens[i] + ", ");
				}	
			}
			if (nonNullTokenCnt==0)
				throw new RuntimeException("All Set clauses have null pattern values");
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
		
		// Remove the last ','
		String processsedClauses = processedSetClauses.substring(0, processedSetClauses.lastIndexOf(","));
		
		// Construct the final Set Clauses String with null parameters replaced by ***
		setStmt.delete(setIdx + 3, whereIdx);
		setStmt.insert(setIdx + 3, processsedClauses + " ");
		return setStmt.toString();
	}
	
   /**
    * This is a tester program that takes a.txt file containing SQL quries ,processes them
    * and writes the processed queries to the output file
    * 
    * @author sborkar
    *
    */
	public static class Tester {

	    static {
	        com.tibco.cep.Bootstrap.ensureBootstrapped();
	    }

		/**
		 * @param args
		 */
		public static void main(String[] args) {

			FileWriter fileWriter = null;
			DataInputStream dis = null;
			try {
				String mode = args[0];
				
				// The default mode of Tester is to remove the patterns randomly.
				if (!mode.equalsIgnoreCase("ONEBYONE") && !mode.equalsIgnoreCase("SPECIFIC") && !mode.equalsIgnoreCase("RANDOM"))
					mode = "DEFAULT-RANDOM";
				
				System.out.println("Using mode : " + mode.toUpperCase() + " to remove the patterns");
				FileInputStream fis = new FileInputStream(new File(args[1]));
				BufferedInputStream bis = new BufferedInputStream(fis);
				dis = new DataInputStream(bis);
				fileWriter = new FileWriter(new File(args[2]));
				String record = null;
				while ((record = dis.readLine()) != null) {

					Map patternValues = new HashMap();
					List patternList = new ArrayList();
					StringBuffer sb = new StringBuffer();
					sb.append("*").append(record).append("\n");

					SQLHelper helper = new SQLHelper(record);
					Set set = helper.getPatterns();
					Iterator iter = set.iterator();
					
					for (int i = 0; iter.hasNext(); i++) {
						SQLPattern p = (SQLPattern) iter.next();
						List values = new ArrayList();
						values.add("a");
						p.setPatternValues(values);
						patternValues.put(p.getPattern(), p);
					}
					
					PSInfo psinfo = helper.getPreparedStmtInfo(patternValues);
					if (psinfo != null) {
						System.out.println("\n" + record + "\n");
						
						patternList = new ArrayList(patternValues.keySet());
						sb.append("#").append(psinfo.getPsStmt()).append("\n");
						sb.append("##").append(psinfo.getSqlWithVal()).append(
						"\n");
						
						
						
						if (mode.equalsIgnoreCase("SPECIFIC")){

							// Use this piece of code to remove some known patterns only
							//String[] patterns = {"PrimaryAirlineCode" , "PrimaryDocumentNumber" , "IATACouponStatus", "CD"};
							String[] patterns = {"StationDataByStationLocationSelectionCriteria/ActiveFlagIATA"};
							for (int i=0;i< patterns.length; i++){
								patternValues.remove(patterns[i]);
								System.out.println("Pattern removed : " + patterns[i]);
							}
							psinfo = helper.getPreparedStmtInfo(patternValues);
							sb.append("###").append(psinfo.getPsStmt())
							.append("\n");
							sb.append("####").append(psinfo.getSqlWithVal())
							.append("\n\n");

						}
						else if (mode.equalsIgnoreCase("RANDOM") || mode.equalsIgnoreCase("DEFAULT-RANDOM") ){

							// This is how we will randomly remove the parameter
							int size = patternValues.size();
							if (size > 0){
								Random r = new Random();
								int no_idx = r.nextInt(size);
								for (int k=0; k<no_idx ; k++){
									int idx = r.nextInt(size);
									String key = (String)patternList.get(idx);
									patternValues.remove(key);
									System.out.println("Pattern removed : " + key);
								}
							}
							psinfo = helper.getPreparedStmtInfo(patternValues);
							sb.append("###").append(psinfo.getPsStmt())
							.append("\n");
							sb.append("####").append(psinfo.getSqlWithVal())
							.append("\n\n");

						}

						else if (mode.equalsIgnoreCase("ONEBYONE")){
							// This removes one pattern at a time in a sequential manner
							// Here, we first remove the pattern from the Map and then again put it back.

							List patternkeys = new ArrayList(patternValues.keySet());
							Iterator listiter = patternkeys.iterator();
							while(listiter.hasNext()){
								String pattern = (String)listiter.next();
								SQLPattern p = (SQLPattern)patternValues.remove(pattern);
								System.out.println("Pattern removed : " + pattern);
								psinfo = helper.getPreparedStmtInfo(patternValues);
								sb.append("<----REMOVING PATTERN : " + pattern + "----->").append("\n");
								sb.append("###").append(psinfo.getPsStmt())
								.append("\n");
								sb.append("####").append(psinfo.getSqlWithVal())
								.append("\n\n");
								
								patternValues.put(pattern, p);
							}
						}

					} else {
						sb.append("# FAILED \n");
					}

					fileWriter.write(sb.toString());
				}

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				try {
					if (fileWriter != null) {
						fileWriter.close();
					}
					if (dis != null) {
						dis.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
   /*
    * This is bean class used to store the information of a SQL query or sub queries in the SQL query
    * 
    * @author sborkar
    */
	private static class QueryBean {
		String queryString = "";

		int insertionIndex = 0;

		String insertionPrefix = "";

		public String getInsertionPrefix() {
			return insertionPrefix;
		}

		public void setInsertionPrefix(String insertionPrefix) {
			this.insertionPrefix = insertionPrefix;
		}

		public int getInsertionIndex() {
			return insertionIndex;
		}

		public void setInsertionIndex(int insertionIndex) {
			this.insertionIndex = insertionIndex;
		}

		public String getQueryString() {
			return queryString;
		}

		public void setQueryString(String queryString) {
			this.queryString = queryString;
		}
	}

	/*
	 * Stores the results of first pass. The expressions identified for a sub query
	 */
	private static class SQLQueryInfo {
		
		String subQuery="";

		List firstPassResultList=new ArrayList();

		public List getFirstPassResultList() {
			return firstPassResultList;
		}

		public void setFirstPassResultList(List firstPassResultList) {
			this.firstPassResultList = firstPassResultList;
		}

		public String getSubQuery() {
			return subQuery;
		}

		public void setSubQuery(String subQuery) {
			this.subQuery = subQuery;
		}
	}
}