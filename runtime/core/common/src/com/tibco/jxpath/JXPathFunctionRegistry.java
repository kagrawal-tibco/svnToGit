package com.tibco.jxpath;

import java.util.HashMap;

import javax.xml.namespace.QName;

import com.tibco.jxpath.functions.AbsFunc;
import com.tibco.jxpath.functions.AddToDateFunc;
import com.tibco.jxpath.functions.AddToDateTimeFunc;
import com.tibco.jxpath.functions.AddToTimeFunc;
import com.tibco.jxpath.functions.BooleanFunc;
import com.tibco.jxpath.functions.CeilingFunc;
import com.tibco.jxpath.functions.CompareDateFunc;
import com.tibco.jxpath.functions.CompareDateTimeFunc;
import com.tibco.jxpath.functions.CompareTimeFunc;
import com.tibco.jxpath.functions.ConcatFunc;
import com.tibco.jxpath.functions.ContainsFunc;
import com.tibco.jxpath.functions.CountFunc;
import com.tibco.jxpath.functions.CreateDateFunc;
import com.tibco.jxpath.functions.CreateDateTimeFunc;
import com.tibco.jxpath.functions.CreateDateTimeTimezoneFunc;
import com.tibco.jxpath.functions.CreateTimeFunc;
import com.tibco.jxpath.functions.CurrentDateFunc;
import com.tibco.jxpath.functions.CurrentDateTimeFunc;
import com.tibco.jxpath.functions.CurrentDateTimeTimezoneFunc;
import com.tibco.jxpath.functions.FalseFunc;
import com.tibco.jxpath.functions.FloorFunc;
import com.tibco.jxpath.functions.Function;
import com.tibco.jxpath.functions.GetCenturyFromDateFunc;
import com.tibco.jxpath.functions.GetCenturyFromDateTimeFunc;
import com.tibco.jxpath.functions.GetDayFromDateFunc;
import com.tibco.jxpath.functions.GetDayFromDateTimeFunc;
import com.tibco.jxpath.functions.GetHoursFromDateTimeFunc;
import com.tibco.jxpath.functions.GetHoursFromTimeFunc;
import com.tibco.jxpath.functions.GetMinutesFromDateTimeFunc;
import com.tibco.jxpath.functions.GetMinutesFromTimeFunc;
import com.tibco.jxpath.functions.GetMonthFromDateFunc;
import com.tibco.jxpath.functions.GetMonthFromDateTimeFunc;
import com.tibco.jxpath.functions.GetSecondsFromDateTimeFunc;
import com.tibco.jxpath.functions.GetSecondsFromTimeFunc;
import com.tibco.jxpath.functions.GetTimezoneFromDateFunc;
import com.tibco.jxpath.functions.GetTimezoneFromDateTimeFunc;
import com.tibco.jxpath.functions.GetTimezoneFromTimeFunc;
import com.tibco.jxpath.functions.GetYearFromDateFunc;
import com.tibco.jxpath.functions.GetYearFromDateTimeFunc;
import com.tibco.jxpath.functions.IndexOfFunc;
import com.tibco.jxpath.functions.LangFunc;
import com.tibco.jxpath.functions.LastFunc;
import com.tibco.jxpath.functions.LastIndexOfFunc;
import com.tibco.jxpath.functions.LeftFunc;
import com.tibco.jxpath.functions.LocalNameFunc;
import com.tibco.jxpath.functions.LowerCaseFunc;
import com.tibco.jxpath.functions.NameFunc;
import com.tibco.jxpath.functions.NamespaceUriFunc;
import com.tibco.jxpath.functions.NotFunc;
import com.tibco.jxpath.functions.NumberFunc;
import com.tibco.jxpath.functions.PadAndLimitFunc;
import com.tibco.jxpath.functions.PadFrontFunc;
import com.tibco.jxpath.functions.PadFunc;
import com.tibco.jxpath.functions.PositionFunc;
import com.tibco.jxpath.functions.RenderXmlFunc;
import com.tibco.jxpath.functions.RightFunc;
import com.tibco.jxpath.functions.RoundFunc;
import com.tibco.jxpath.functions.StartsWithFunc;
import com.tibco.jxpath.functions.StringFunc;
import com.tibco.jxpath.functions.StringLengthFunc;
import com.tibco.jxpath.functions.SubstringAfterFunc;
import com.tibco.jxpath.functions.SubstringAfterLastFunc;
import com.tibco.jxpath.functions.SubstringBeforeFunc;
import com.tibco.jxpath.functions.SubstringBeforeLastFunc;
import com.tibco.jxpath.functions.SubstringFunc;
import com.tibco.jxpath.functions.SumFunc;
import com.tibco.jxpath.functions.TimestampFunc;
import com.tibco.jxpath.functions.TokenizeAllowEmptyFunc;
import com.tibco.jxpath.functions.TokenizeFunc;
import com.tibco.jxpath.functions.TranslateFunc;
import com.tibco.jxpath.functions.TrimFunc;
import com.tibco.jxpath.functions.TrueFunc;
import com.tibco.jxpath.functions.UpperCaseFunc;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 6:20 PM
*/
public class JXPathFunctionRegistry {

    static JXPathFunctionRegistry gInstance = new JXPathFunctionRegistry();

    private HashMap<QName, Function> functions;

    public static JXPathFunctionRegistry getInstance() { return gInstance;}


    private JXPathFunctionRegistry()
    {
        functions = new HashMap<QName, Function>();
        initFunctions();
    }

    private void initFunctions() {

        putFunction( BooleanFunc.FUNCTION);
        putFunction( CeilingFunc.FUNCTION);
        putFunction( ConcatFunc.FUNCTION);
        putFunction( ContainsFunc.FUNCTION);
        putFunction( CountFunc.FUNCTION);
        putFunction( FalseFunc.FUNCTION);
        putFunction( FloorFunc.FUNCTION);
        putFunction( LangFunc.FUNCTION);
        putFunction( LastFunc.FUNCTION);
        putFunction( LocalNameFunc.FUNCTION);
        putFunction( NameFunc.FUNCTION);
        putFunction( NamespaceUriFunc.FUNCTION);
        putFunction( NotFunc.FUNCTION);
        putFunction( NumberFunc.FUNCTION);
        putFunction( PositionFunc.FUNCTION);
        putFunction( RoundFunc.FUNCTION);
        putFunction( StartsWithFunc.FUNCTION);
        putFunction( StringFunc.FUNCTION);
        putFunction( StringLengthFunc.FUNCTION);
        putFunction( SubstringAfterFunc.FUNCTION);
        putFunction( SubstringBeforeFunc.FUNCTION);
        putFunction( SubstringFunc.FUNCTION);
        putFunction( SumFunc.FUNCTION);
        putFunction( TranslateFunc.FUNCTION);
        putFunction( TrueFunc.FUNCTION);
        
        // XPath 2.0/tib: functions
        // Date/Time functions
        putFunction( AddToDateFunc.FUNCTION);
        putFunction( AddToTimeFunc.FUNCTION);
        putFunction( AddToDateTimeFunc.FUNCTION);
        putFunction( CompareDateFunc.FUNCTION);
        putFunction( CompareDateTimeFunc.FUNCTION);
        putFunction( CompareTimeFunc.FUNCTION);
        putFunction( CreateDateFunc.FUNCTION);
        putFunction( CreateDateTimeFunc.FUNCTION);
        putFunction( CreateDateTimeTimezoneFunc.FUNCTION);
        putFunction( CreateTimeFunc.FUNCTION);
        putFunction( CurrentDateTimeFunc.FUNCTION);
        putFunction( CurrentDateFunc.FUNCTION);
        putFunction( CurrentDateTimeTimezoneFunc.FUNCTION);
        putFunction( GetCenturyFromDateFunc.FUNCTION);
        putFunction( GetCenturyFromDateTimeFunc.FUNCTION);
        putFunction( GetDayFromDateFunc.FUNCTION);
        putFunction( GetDayFromDateTimeFunc.FUNCTION);
        putFunction( GetHoursFromDateTimeFunc.FUNCTION);
        putFunction( GetHoursFromTimeFunc.FUNCTION);
        putFunction( GetMinutesFromDateTimeFunc.FUNCTION);
        putFunction( GetMinutesFromTimeFunc.FUNCTION);
        putFunction( GetMonthFromDateFunc.FUNCTION);
        putFunction( GetMonthFromDateTimeFunc.FUNCTION);
        putFunction( GetSecondsFromDateTimeFunc.FUNCTION);
        putFunction( GetSecondsFromTimeFunc.FUNCTION);
        putFunction( GetTimezoneFromDateFunc.FUNCTION);
        putFunction( GetTimezoneFromDateTimeFunc.FUNCTION);
        putFunction( GetTimezoneFromTimeFunc.FUNCTION);
        putFunction( GetYearFromDateFunc.FUNCTION);
        putFunction( GetYearFromDateTimeFunc.FUNCTION);
        putFunction( TimestampFunc.FUNCTION);
        // TODO : incomplete Date/Time functions
//        putFunction( FormatDateTimeFunc.FUNCTION);
//        putFunction( FormatDateFunc.FUNCTION);
//        putFunction( FormatTimeFunc.FUNCTION);
//        putFunction( ParseDateFunc.FUNCTION);
//        putFunction( ParseDateTimeFunc.FUNCTION);
//        putFunction( ParseTimeFunc.FUNCTION);
//        putFunction( TranslateTimezoneFunc.FUNCTION);
//        putFunction( ValidateDateTimeFunc.FUNCTION);
        
        putFunction( AbsFunc.FUNCTION);
        // TODO : incomplete Number functions
//        putFunction( AvgFunc.FUNCTION);
//        putFunction( MinFunc.FUNCTION);
//        putFunction( MaxFunc.FUNCTION);
//        putFunction( RoundFractionFunc.FUNCTION);
//        putFunction( StringRoundFractionFunc.FUNCTION);
        
        // TODO : incomplete Binary functions
//        putFunction( Base64ToStringFunc.FUNCTION);
//        putFunction( Base64ToHexFunc.FUNCTION);
//        putFunction( BaseLengthFunc.FUNCTION);
//        putFunction( HexToBase64Func.FUNCTION);
//        putFunction( HexToStringFunc.FUNCTION);
//        putFunction( StringToBase64Func.FUNCTION);
//        putFunction( StringToHexFunc.FUNCTION);

        // TODO : incomplete Set functions
//        putFunction( CurrentFunc.FUNCTION);
//        putFunction( EmptyFunc.FUNCTION);
//        putFunction( ExistsFunc.FUNCTION);
//        putFunction( IfAbsentFunc.FUNCTION);
        
        // TODO : incomplete Logical functions
//        putFunction( XorFunc.FUNCTION);
        
        putFunction( IndexOfFunc.FUNCTION);
        putFunction( LastIndexOfFunc.FUNCTION);
        putFunction( LeftFunc.FUNCTION);
        putFunction( LowerCaseFunc.FUNCTION);
        putFunction( PadFunc.FUNCTION);
        putFunction( PadAndLimitFunc.FUNCTION);
        putFunction( PadFrontFunc.FUNCTION);
        putFunction( RightFunc.FUNCTION);
        putFunction( SubstringBeforeLastFunc.FUNCTION);
        putFunction( SubstringAfterLastFunc.FUNCTION);
        putFunction( TrimFunc.FUNCTION);
        putFunction( UpperCaseFunc.FUNCTION);
        // TODO : incomplete String functions
//        putFunction( ConcatSequenceFunc.FUNCTION);
//        putFunction( ConcatSequenceFormatFunc.FUNCTION);
        putFunction( RenderXmlFunc.FUNCTION);
        putFunction( TokenizeFunc.FUNCTION);
        putFunction( TokenizeAllowEmptyFunc.FUNCTION);
        
    }

    private void putFunction(Function func) {
        functions.put(func.name(), func);
    }

    public Function getFunction(QName name)
    {
        return functions.get(name);
    }
}
