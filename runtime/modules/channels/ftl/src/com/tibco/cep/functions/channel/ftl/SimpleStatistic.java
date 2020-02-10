package com.tibco.cep.functions.channel.ftl;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.functions.Enabled;

/*
 * Borrowed from C:\Users\mmalva\MyProjects\01-ems\stdEMSPerfTest\1.0\src\tibjmsMsgConsumerPerf.java X
 */
public class SimpleStatistic implements StatisticIF {
    private static ConcurrentHashMap<String, StatisticIF> concurrentStatMap = new ConcurrentHashMap<String, StatisticIF>();

    int MAX_BUCKET = 16 * 1024; // from 0 milliseconds to 16 seconds
    long m_resetInterval = 0;
    long deltaTime =0;
    long m_resetTime = 0;

    double m_sum = 0.0;
    long m_count = 0;
    long m_max = 0;
    boolean header =false;
    long m_histogram[] = new long[MAX_BUCKET];

    private String name;

    @com.tibco.be.model.functions.BEFunction(
            name = "get",
            signature = "void get (String name, long resetInterval)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "statistic name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resetInterval", type = "long", desc = "reset interval")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "statistic object"),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static Object get(String name, long resetInterval) {
        SimpleStatistic stat = null;
        if (!concurrentStatMap.containsKey(name)){
            stat = new SimpleStatistic(name, resetInterval);
            concurrentStatMap.putIfAbsent(name, stat);
            return stat;
        } else {
            return concurrentStatMap.get(name);
        }
    }
    @com.tibco.be.model.functions.BEFunction(
            name = "loadClass",
            signature = "void loadClass(String className, String name, long resetInterval)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "className", type = "String", desc = "statistic class name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "statistic name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resetInterval", type = "long", desc = "reset interval")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "statistic object"),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static Object loadClass(String clazz, String name, long resetInterval) {
        StatisticIF stat = null;
        try {
            Class<?> statClazz = Class.forName(clazz);
            if (!StatisticIF.class.isAssignableFrom(statClazz)) {
                throw new RuntimeException("Statistic class does not implement com.tibco.cep.functions.channel.ftl.StatisticIF interface");
            }
            Constructor<?> statConstructor = statClazz.getConstructor(String.class, long.class);

            if (!concurrentStatMap.containsKey(name)){
                stat = (StatisticIF) statConstructor.newInstance(name, resetInterval);
                concurrentStatMap.putIfAbsent(name, stat);
                return stat;
            } else {
                return concurrentStatMap.get(name);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "count",
            signature = "void count (String name, long timestamp, long value)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "statistic name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "timestamp", type = "long", desc = "timestamp"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "long", desc = "value")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static void count(String name, long timestamp, long value){
        StatisticIF stat = concurrentStatMap.get(name);
        if (stat == null) return;
        stat.count(Thread.currentThread().getName(), timestamp, value, System.out);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "reset",
            signature = "void count (String name, long timestamp)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "statistic name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "timestamp", type = "timestamp", desc = "timestamp")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static void reset(String name, long timestamp){
        StatisticIF stat = concurrentStatMap.get(name);
        if (stat == null) return;
        stat.reset(timestamp);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "printHeader",
            signature = "void printHeader (String name)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "statistic name")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static void printHeader(String name){
        StatisticIF stat = concurrentStatMap.get(name);
        if (stat == null) return;
        stat.printHeader(System.out);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "printResult",
            signature = "void printResult (String name)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "statistic name")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static void printResult(String name){
        StatisticIF stat = concurrentStatMap.get(name);
        if (stat == null) return;
        stat.printResult(Thread.currentThread().getName(), System.out);
    }

    public SimpleStatistic(String name, long resetInterval) {
		m_resetInterval = resetInterval;
		this.name = name;
	}

    public void count(long when, long value) {
        count("XXXXX", when, value, System.out);
    }

    /* (non-Javadoc)
     * @see StatisticIF#count(long, long)
     */
    @Override
    public void count(String threadName, long when, long value, PrintStream ps) {
        if (m_resetTime == 0) m_resetTime = when + m_resetInterval;
        else if (when > m_resetTime) {
            // time to reset everything
            printResult(threadName, ps);
            reset(when);
        }

        if (value >= MAX_BUCKET) value = MAX_BUCKET - 1;
        ++m_histogram[ (int) value ];

        m_sum += value;
        ++m_count;

        if (value > m_max) m_max = value;
    }

    public void reset(long when) {
        m_resetTime = when + m_resetInterval;
        m_sum = 0;
        m_count = 0;
        m_max = 0;

        for (int i = 0; i < MAX_BUCKET; ++i) m_histogram[i] = 0;
    }

    protected double average()
    { return m_count > 0 ? ((double) m_sum) / ((double) m_count) : 0.0; }

    protected long median() {
        if (m_count == 0) return 0;

        long half = m_count / 2;
        long sum = 0;
        int i = 0;
        while ((sum < half) && (i < MAX_BUCKET)){
            sum += m_histogram[ i ];
            if (sum < half) ++i;
        }
        return i;
    }

    protected long m_p90 = -1;
    protected long m_p99 = -1;

    protected void calc_quantiles() {
        m_p90=-1; m_p99=-1;
        if (m_count == 0) return;

        long p90 = (long) (0.9 * (double)m_count);
        long p99 = (long) (0.99 * (double)m_count);

        long sum = 0;
        int i = 0;
        while ((sum < p90) && (i < MAX_BUCKET)){
            sum += m_histogram[ i ];
            if (sum < p90) ++i;
        }
        m_p90 = i;

        while ((sum < p99) && (i < MAX_BUCKET)){
            sum += m_histogram[ i ];
            if (sum < p99) ++i;
        }
        m_p99 = i;
    }

    /* (non-Javadoc)
     * @see StatisticIF#printResult()
     */
    @Override
    public void printResult(String threadName, PrintStream ps)
    {
        if (!header){
            printHeader(ps);
        }
        double[] a = new double[10];

        if (m_count > 0) {
            a[0] = (double) m_histogram[0] / (double) m_count ;
            a[1] = (double) m_histogram[1] / (double) m_count ;
            a[2] = (double) m_histogram[2] / (double) m_count ;
            a[3] = (double) m_histogram[3] / (double) m_count ;
            a[4] = (double) m_histogram[4] / (double) m_count ;
            a[5] = (double) m_histogram[5] / (double) m_count ;

            {
                double tmp = 0.0;
                tmp += (double) m_histogram[6];
                tmp += (double) m_histogram[7];
                tmp += (double) m_histogram[8];
                tmp += (double) m_histogram[9];
                tmp += (double) m_histogram[10];
                a[6] = tmp / (double) m_count ;
            }

            {
                double tmp = 0.0;
                for (int i = 11; i < 101; ++i)
                    tmp += (double) m_histogram[i];
                a[7] = tmp / (double) m_count ;
            }

            {
                double tmp = 0.0;
                for (int i = 101; i < 1001; ++i)
                    tmp += (double) m_histogram[i];
                a[8] = tmp / (double) m_count ;
            }

            a[9] = 100.0;
            for (int i =0; i < 9; ++i) {
                a[i] *= 100.0;
                a[9] -= a[i];
            }
        }

        double avg = average();
        deltaTime = deltaTime + m_resetInterval;
        ps.println(nowAsString() + "\t" + name
            + "\t" + threadName
            + "\t" + Thread.currentThread().getName()
            + "\t" +  deltaTime/1000
            + "\t" + (m_count*1000)/m_resetInterval
            + "\t" + m_count
            + "\t" + ((double) ((int) (100.0 * avg)) / 100.0)
            + "\t" + median()
            + "\t" + m_p90
            + "\t" + m_p99
            + "\t" + m_max
            + "\t" + "H"
            + "\t" + ((double) ((int) (100.0 * a[0])) / 100.0)
            + "\t" + ((double) ((int) (100.0 * a[1])) / 100.0)
            + "\t" + ((double) ((int) (100.0 * a[2])) / 100.0)
            + "\t" + ((double) ((int) (100.0 * a[3])) / 100.0)
            + "\t" + ((double) ((int) (100.0 * a[4])) / 100.0)
            + "\t" + ((double) ((int) (100.0 * a[5])) / 100.0)
            + "\t" + ((double) ((int) (100.0 * a[6])) / 100.0)
            + "\t" + ((double) ((int) (100.0 * a[7])) / 100.0)
            + "\t" + ((double) ((int) (100.0 * a[8])) / 100.0)
            + "\t" + ((double) ((int) (100.0 * a[9])) / 100.0)
       );
        System.out.flush();
    }

    public void printHeader(PrintStream stream){
        stream.println(nowAsString() + "\tRESP"
                + "\t" + "Req-Thrd"
                + "\t" + "Resp-Thrd"
                + "\t" + "Int"
                + "\t" + "MPS"
                + "\t" + "Count"
                + "\t" + "AVG"
                + "\t" + "Median"
                + "\t" + "P90"
                + "\t" + "P99"
                + "\t" + "Max"
                + "\t" + "H"
                + "\t" + "0-1ms"
                + "\t" + "1-2ms"
                + "\t" + "2-3ms"
                + "\t" + "3-4ms"
                + "\t" + "4-5ms"
                + "\t" + "5-6ms"
                + "\t" + "5-10ms"
                + "\t" + "10-100ms"
                + "\t" + "100-1000ms"
                + "\t" + ">1000ms"
           );
        header = true;
    }

    protected String nowAsString()
    {
        String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }
}
