// copyright 2001 TIBCO Software Inc

package com.tibco.cep.mapper.xml.xdata;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A interface to lookup namespace prefixes from a namespace and location.
 * Has also added special character marking
 * (Maybe also add entity referencing)
 */
public class CharacterEntityMap
{
    private HashMap mCharacterToRecord = new HashMap();
    private HashMap mEntityToRecord = new HashMap();
    private ArrayList mRecordsInOrder = new ArrayList();
    private boolean mLocked = false;

    public static final CharacterEntityMap XHTML_MAPS = new CharacterEntityMap();

    static {
        XHTML_MAPS.addXHTMLLatin();
        XHTML_MAPS.addXHTMLSpecial();
        XHTML_MAPS.addXHTMLSymbol();
        XHTML_MAPS.lock();
    }

    public void addXHTMLLatin() {
        try {
            addXHTMLStyleFile(new InputSource(CharacterEntityMap.class.getResourceAsStream("resources/xhtml-lat1.ent")));
        } catch (Exception e) {
            throw new RuntimeException("Couldn't initialize character maps: " + e.getMessage());
        }
    }

    public void addXHTMLSpecial() {
        try {
            addXHTMLStyleFile(new InputSource(CharacterEntityMap.class.getResourceAsStream("resources/xhtml-special.ent")));
        } catch (Exception e) {
            throw new RuntimeException("Couldn't initialize character maps: " + e.getMessage());
        }
    }

    public void addXHTMLSymbol() {
        try {
            addXHTMLStyleFile(new InputSource(CharacterEntityMap.class.getResourceAsStream("resources/xhtml-symbol.ent")));
        } catch (Exception e) {
            throw new RuntimeException("Couldn't initialize character maps: " + e.getMessage());
        }
    }

    private static class Record {
        public char mCharacter;
        public String mEntity;
        public String mComment;

        public String toString() {
            return mCharacter + " - " + mEntity + " - " + mComment;
        }
    }

    /**
     * Returns null if none.
     */
    public String getEntityString(char c) {
        Record r = getRecord(c);
        if (r==null) {
            return null;
        }
        return r.mEntity;
    }

    public int getRecordCount() {
        return mRecordsInOrder.size();
    }

    public char getRecord(int i) {
        return ((Record)mRecordsInOrder.get(i)).mCharacter;
    }

    /**
     * Returns null if none.
     */
    public char getCharacter(String entity) {
        Record r = getRecord(entity);
        if (r==null) {
            return '?';
        }
        return r.mCharacter;
    }

    public String getComment(char c) {
        Record r = getRecord(c);
        if (r==null) {
            return null;
        }
        return r.mComment;
    }

    private Record getRecord(char c) {
        return (Record) mCharacterToRecord.get(new Character(c));
    }

    private Record getRecord(String entity) {
        return (Record) mEntityToRecord.get(entity);
    }

    private class Handler extends DefaultHandler implements LexicalHandler, DeclHandler {
        private InputSource mEntityFile;
        private char mCurrentCharacter;
        private String mCurrentEntity;
        private String mCurrentComment;

        public Handler(InputSource entityFile) {
            mEntityFile = entityFile;
        }

        public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
            return mEntityFile;
        }

        public void startEntity(String name) throws SAXException {
        }

        public void endEntity(String name) throws SAXException {
        }

        public void startDTD(String name, String publicId, String systemId) throws SAXException {
        }

        public void endDTD() throws SAXException {
            flush();
        }

        public void startCDATA() throws SAXException {
        }

        public void endCDATA() throws SAXException {
        }

        public void comment(char ch[], int start, int length) throws SAXException {
            String comment = new String(ch,start,length);
            mCurrentComment = comment.trim();
            flush();
        }

        public void internalEntityDecl(String name, String value) throws SAXException {
            flush();
            mCurrentCharacter = value.charAt(0);
            mCurrentEntity = name;
            mCurrentComment = null;
        }

        public void externalEntityDecl(String name, String value) throws SAXException {
        }

        public void elementDecl(String name, String model) throws SAXException {
        }

        public void attributeDecl(String eName,
                                  String aName,
                                  String type,
                                  String valueDefault,
                                  String value)
                throws SAXException {
        }

        public void externalEntityDecl(String name, String publicId,
                                       String systemId) throws SAXException {
        }

        private void flush() {
            if (mCurrentCharacter==0) {
                return;
            }
            Record r = new Record();
            r.mCharacter = mCurrentCharacter;
            r.mEntity = mCurrentEntity;
            r.mComment = mCurrentComment;
            mCharacterToRecord.put(new Character(r.mCharacter),r);
            mEntityToRecord.put(r.mEntity,r);
            mRecordsInOrder.add(r);

            mCurrentComment = null;
            mCurrentCharacter = 0;
        }
    }

    /**
     * Adds entries from a file in the format used by XHTML to specify character references
     * An example file is <a href=\"http://www.w3.org/TR/xhtml-modularization/dtd_module_defs.html#a_xhtml_character_entities\">here</a>
     */
    public void addXHTMLStyleFile(InputSource is) throws SAXException, IOException, ParserConfigurationException {
        if (mLocked) {
            throw new IllegalStateException("Locked");
        }

        SAXParserFactory factory = SAXParserFactory.newInstance();
        XMLReader parser = factory.newSAXParser().getXMLReader();
        Handler handler = new Handler(is);
        parser.setContentHandler(handler);
        parser.setEntityResolver(handler);
        parser.setErrorHandler(handler);
        parser.setProperty("http://xml.org/sax/properties/lexical-handler",handler);
        parser.setProperty("http://xml.org/sax/properties/declaration-handler",handler);
        String loader =
                "<!DOCTYPE bogus [\n" +
                "  <!ENTITY % bogus_entity\n" +
                "    PUBLIC \"-//W3C//ENTITIES Latin 1 for XHTML//EN\"\n" +
                "    \"http://bogus.ent\" >\n" +
                "  %bogus_entity;\n" +
                "]>\n" +
                "<bogus>\n" +
                "</bogus>";
        parser.parse(new InputSource(new StringReader(loader)));
    }

    public void lock() {
        mLocked = true;
    }
}
