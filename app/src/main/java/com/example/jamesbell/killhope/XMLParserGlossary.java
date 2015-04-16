package com.example.jamesbell.killhope;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Stephen on 15/04/2015.
 */
public class XMLParserGlossary {

        private XmlPullParserFactory xmlFactoryObject;
        private XmlPullParser parser;
        private ArrayList<GlossaryTerm> terms;

        public XMLParserGlossary(InputStream fis) {
            try {
                xmlFactoryObject = XmlPullParserFactory.newInstance();
                xmlFactoryObject.setNamespaceAware(true);
                parser = xmlFactoryObject.newPullParser();

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                parser.setInput(fis, null);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            try {
                parseXML(parser);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public ArrayList<GlossaryTerm>
        getTerms(){
            return terms;
        }
        private void parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
        {
            terms = new ArrayList<GlossaryTerm>();
            int eventType = parser.getEventType();
            GlossaryTerm currentTerm = null;
            GlossaryTerm currentSubTerm = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        terms = new ArrayList<GlossaryTerm>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equals("term")) {
                            currentTerm = new GlossaryTerm();
                        } else if (currentTerm != null) {
                            if (name.equals("word")) {
                                currentTerm.setWord(parser.nextText());
                            } else if (name.equals("definition")) {
                                currentTerm.setDefinition(parser.nextText());
                            }
                        } else if (name.equals("subterms")) {
                        } else if (name.equals("subterm")) {
                            currentSubTerm = new GlossaryTerm();
                        } else if (currentSubTerm != null) {
                            if (name.equals("word")) {
                                currentSubTerm.setWord(parser.nextText());
                            } else if (currentSubTerm.getDefinition().equals(null))
                                if (name.equals("definition")) {
                                    currentSubTerm.setDefinition(parser.nextText());
                                }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("term") && currentTerm != null) {
                            terms.add(currentTerm);
                        } else if (name.equalsIgnoreCase("subterm") && currentSubTerm != null) {
                            currentTerm.addSubterm(currentSubTerm);
                        } else if (name.equalsIgnoreCase("subterms")) {
                        }
                }
                eventType = parser.next();
            }
        }
}
