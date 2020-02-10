package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.cep.mapper.xml.xdata.xpath.FunctionNamespace;

/**
 * Encapsulates the knowledge of the builtin XPath 1.0 functions that need to be supported.
 */
public class TibcoExtendedFunctionNamespace
{
    public static final FunctionNamespace INSTANCE;
    private static final DefaultFunctionNamespace IINSTANCE;

    static {
        IINSTANCE = new DefaultFunctionNamespace(TibExtFunctions.NAMESPACE);
        IINSTANCE.setSuggestedPrefix(TibExtFunctions.SUGGESTED_PREFIX);
        IINSTANCE.setBuiltIn(true);
        INSTANCE = IINSTANCE;
        addBoolean();
        addNumber();
        addString();
        addDateTime();
        addBinary();
        addSequence();
        addConditional();
        addUndocumented();
        IINSTANCE.lock();
    }

    private static void addBoolean() {
        IINSTANCE.add(new XorXFunction());
    }

    private static void addNumber() {
        IINSTANCE.add(new RoundFractionXFunction());
        IINSTANCE.add(new StringRoundFractionXFunction());
    }

    private static void addString()
    {
        IINSTANCE.add(new SubstringAfterLastXFunction());
        IINSTANCE.add(new SubstringBeforeLastXFunction());
        IINSTANCE.add(new IndexOfXFunction());
        IINSTANCE.add(new LastIndexOfXFunction());
        IINSTANCE.add(new PadXFunction());
        IINSTANCE.add(new PadFrontXFunction());
        IINSTANCE.add(new PadAndLimitXFunction());
        IINSTANCE.add(new TokenizeXFunction());
        IINSTANCE.add(new TokenizeAllowEmptyXFunction());
        IINSTANCE.add(new TrimXFunction());
        IINSTANCE.add(new RightXFunction());
        IINSTANCE.add(new LeftXFunction());

        IINSTANCE.add(new RenderXmlXFunction());
    }

    private static void addDateTime()
    {
        IINSTANCE.add(new CurrentDateTimeWithTimeZoneXFunction());

        IINSTANCE.add(new ParseDateTimeXFunction());
        IINSTANCE.add(new ParseDateXFunction());
        IINSTANCE.add(new ParseTimeXFunction());

        IINSTANCE.add(new FormatDateTimeXFunction());
        IINSTANCE.add(new FormatDateXFunction());
        IINSTANCE.add(new FormatTimeXFunction());

        IINSTANCE.add(new CreateDateTimeXFunction());
        IINSTANCE.add(new CreateDateTimeWithTimeZoneXFunction());
        IINSTANCE.add(new CreateDateXFunction());
        IINSTANCE.add(new CreateTimeXFunction());

        IINSTANCE.add(new AddToDateXFunction());
        IINSTANCE.add(new AddToTimeXFunction());
        IINSTANCE.add(new AddToDateTimeXFunction());

        IINSTANCE.add(new TranslateTimeZoneXFunction());

        IINSTANCE.add(new GetCenturyFromDateTimeXFunction());
        IINSTANCE.add(new GetCenturyFromDateXFunction());
        IINSTANCE.add(new GetYearFromDateTimeXFunction());
        IINSTANCE.add(new GetYearFromDateXFunction());
        IINSTANCE.add(new GetMonthFromDateTimeXFunction());
        IINSTANCE.add(new GetMonthFromDateXFunction());
        IINSTANCE.add(new GetDayFromDateTimeXFunction());
        IINSTANCE.add(new GetDayFromDateXFunction());
        IINSTANCE.add(new GetHourFromDateTimeXFunction());
        IINSTANCE.add(new GetHourFromTimeXFunction());
        IINSTANCE.add(new GetMinutesFromDateTimeXFunction());
        IINSTANCE.add(new GetMinutesFromTimeXFunction());
        IINSTANCE.add(new GetSecondsFromDateTimeXFunction());
        IINSTANCE.add(new GetSecondsFromTimeXFunction());
        IINSTANCE.add(new GetTimeZoneFromDateTimeXFunction());
        IINSTANCE.add(new GetTimeZoneFromDateXFunction());
        IINSTANCE.add(new GetTimeZoneFromTimeXFunction());

        IINSTANCE.add(new CompareDateXFunction());
        IINSTANCE.add(new CompareDateTimeXFunction());
        IINSTANCE.add(new CompareTimeXFunction());

        IINSTANCE.add(new ValidateDateTimeXFunction());
    }

    private static void addBinary()
    {
        IINSTANCE.add(new Base64ToHexXFunction());
        IINSTANCE.add(new Base64ToStringXFunction());
        IINSTANCE.add(new HexToBase64XFunction());
        IINSTANCE.add(new HexToStringXFunction());
        IINSTANCE.add(new StringToBase64XFunction());
        IINSTANCE.add(new StringToHexXFunction());

        IINSTANCE.add(new Base64LengthXFunction());
        IINSTANCE.add(new HexLengthXFunction());
    }

    private static void addSequence() {
        IINSTANCE.add(new ConcatSequenceXFunction());
        IINSTANCE.add(new ConcatSequenceFormatXFunction());
    }

    private static void addConditional()
    {
        IINSTANCE.add(new IfAbsentXFunction());
    }

    private static void addUndocumented()
    {
        IINSTANCE.add(new EvaluateXFunction());
        IINSTANCE.add(new TimestampXFunction());
    }
}
