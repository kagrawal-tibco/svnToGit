package com.tibco.cep.mapper.xml.xdata.xpath;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.util.Debug;
import com.tibco.xml.data.primitive.XmlSequence;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmException;
import com.tibco.xml.schema.SmFactory;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmSchemaError;
import com.tibco.xml.schema.SmWildcard;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableModelGroup;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.impl.DefaultParticle;
import com.tibco.xml.schema.impl.DefaultWildcard;
import com.tibco.xml.serialization.DefaultXmlContentSerializer;
import com.tibco.xml.xpath.serialization.XPath10Serializer;

public final class Variable implements Serializable
{
    private XiNode mValue;
    private double mNumber;


    public static final SmElement VARIABLE_ELEMENT;

    static {
        MutableSchema ms;
        try {
            ms = SmFactory.newInstance().createMutableSchema();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        ms.setNamespace("http://www.tibco.com/bw/processvars");
        MutableType vart = MutableSupport.createType(ms,"Variable");
        VARIABLE_ELEMENT = MutableSupport.createElement(ms,"Variable",vart);
        ((MutableElement)VARIABLE_ELEMENT).setNamespace(ms.getNamespace());

        MutableSupport.addAttribute(vart,"name",XSDL.NMTOKEN,true);
        SmWildcard wc = new DefaultWildcard();
        SmParticle particle = new DefaultParticle(1,1,wc);
        MutableSupport.addOptionalLocalElement(vart,"bogus",XSDL.STRING);
        MutableModelGroup cm = (MutableModelGroup) vart.getContentModel();
        cm.addParticle(particle);
        SmSchemaError.Handler handler = new SmSchemaError.Handler() {

            public void error(SmSchemaError error) throws SmException {
                throw new RuntimeException("Bad utility schema");
            }

            public void warning(SmSchemaError error) throws SmException {
                throw new RuntimeException("Bad utility schema");
            }

            public boolean isConstraintChecking() {
                return true;
            }
        };

        try {
            ms.lock(handler);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't make variable schema");
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        if (mValue != null) {
            serialize(mValue, out, "UTF-8");
        } else {
            out.writeObject(toString());
        }
    }

    private void serialize(XiNode node, ObjectOutputStream out, String encoding) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(bos, encoding);
        serialize(node, osw, encoding);
        osw.close();
        out.writeObject(bos.toByteArray());
    }

    private void serialize(XiNode node, Writer writer, String encoding) throws IOException {
        DefaultXmlContentSerializer serializer = new DefaultXmlContentSerializer(writer, encoding);
        serializer.setAtomicValueSerializer(XPath10Serializer.getInstance());
        try {
            serializer.startDocument();
           // Note (2/23/05): jtb chose to cast as XmlSequence rather than
           // XmlTreeNode because method XmlTreeNode's serialize method was deprecated;
           // also, the casting was necessary because jikes compiler complained
           // about ambiguous "serialize" methods
            ((XmlSequence)node).serialize(serializer);
            serializer.endDocument();
        }
        catch (SAXException e)
        {
            Exception cause = e.getException();
            if (null != cause)
            {
                if (cause instanceof IOException)
                {
                    throw (IOException)cause;
                }
            }
            throw new RuntimeException(Debug.getErrorMessage(e));
        }
    }

    private void readObject(ObjectInputStream os) throws IOException {
        Object data = null;
        try {
            data = os.readObject();
            if (data instanceof String) {
                String num = ((String) data).substring(1);
                mNumber = Double.parseDouble(num);
            } else {
                // byte array
                byte[] bytes = (byte[]) data;
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                XiParser parser = XiParserFactory.newInstance();
                mValue = parser.parse(new InputSource(bis));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw new IOException(e.getMessage());
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw new IOException(e.getMessage());
        }
    }

    // Use release 1.1.0 serialVersionUID so that checkpoint files will be readable
    // by future versions.
    private static final long serialVersionUID = -7284201962422205497L;

    public Variable(XiNode value) {
        mValue = value;
    }

    public Variable(double value) {
        mNumber = value;
    }

    public XiNode getValue() {
        return mValue;
    }

    public double getNumber() {
        return mNumber;
    }

    public void setNumber(double value) {
        mNumber = value;
    }


    /**
     * Special support for output accumulation:
     */
    public void accumulate(Variable fromValue) {
        XiNode from = fromValue.getValue();
        if (from==null) {
            return;
        }
        // bypass document
        from = from.getFirstChild();      // 1-4BIFD5 correct
        if (from!=null) {
            XiNode firstChild =  mValue.getFirstChild();
            if (firstChild != null)
                firstChild.appendChild(from.copy());
        }
    }

    public String toString() {
        if (mValue!=null) {
            try {
                StringWriter sw = new StringWriter();
                serialize(mValue, sw, "UTF-8");
                return "xml: " + sw.toString();
            } catch (IOException e) {
                return "xml: <error/>";
            }
        }
        return "#" + mNumber;
    }

    /**
     * Goes to an XML string fragment.
     */
/*    public String toXMLString(String varName) {
        XData d = XData.create(VARIABLE_ELEMENT);
        d.setString("@name",varName);
        d.setXData(2,mValue);
        return d.toString();
    }*/

    /**
     * Goes to an XML string real thing (non-fragment).
     */
/*    public String toFullXMLString(String varName) throws IOException {
        XData d = XData.create(VARIABLE_ELEMENT);
        d.setString("@name",varName);
        d.setXData(2,mValue);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XmlIO.write(d,bos);
        return new String(bos.toByteArray());
    }*/
}

