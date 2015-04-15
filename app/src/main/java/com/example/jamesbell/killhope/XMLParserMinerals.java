package com.example.jamesbell.killhope;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by jamesbell on 11/03/15.
 */
public class XMLParserMinerals {
    private XmlPullParserFactory xmlFactoryObject;
    private XmlPullParser parser;
    private ArrayList<MineralObject> minerals;
    public XMLParserMinerals(InputStream fis) {
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
    public ArrayList<MineralObject> getMinerals(){
        return minerals;
    }
    private void parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {
        minerals = new ArrayList<MineralObject>();
        int eventType = parser.getEventType();
        MineralObject currentMineral = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = null;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    minerals = new ArrayList<MineralObject>();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("mineral")) {
                        currentMineral = new MineralObject();
                    } else if (currentMineral != null) {
                        if (name.equals("name")) {
                            currentMineral.setName(parser.nextText());
                        } else if (name.equals("formula")) {
                            currentMineral.setForumla(parser.nextText());
                        } else if (name.equals("colour")) {
                            currentMineral.setColour(parser.nextText());
                        } else if (name.equals("abundance")){
                            currentMineral.setAbundance(parser.nextText());
                        } else if (name.equals("hardness")) {
                            currentMineral.setHardness(parser.nextText());
                        } else if (name.equals("lustre")) {
                            currentMineral.setLustre(parser.nextText());
                        } else if (name.equals("ore")) {
                            currentMineral.setOre(parser.nextText());
                        } else if (name.equals("interestingFact")) {
                            currentMineral.setInterestingFact(parser.nextText());
                        } else if (name.equals("uses")) {
                            currentMineral.setUses(parser.nextText());
                        } else if (name.equals("mainCountries")) {
                            currentMineral.setMainCountries(parser.nextText());
                        } else if (name.equals("crystalHabit")) {
                            currentMineral.setCrystalHabit(parser.nextText());
                        } else if (name.equals("crystalStructure")) {
                            currentMineral.setCrystalStructure(parser.nextText());
                        } else if (name.equals("depositionalEnviro")) {
                            currentMineral.setDepositionalEnviro(parser.nextText());
                        } else if (name.equals("transparency")) {
                            currentMineral.setTransparency(parser.nextText());
                        } else if (name.equals("originOfName")) {
                            currentMineral.setOriginOfName(parser.nextText());
                        } else if (name.equals("coloursAtKillhope")) {
                            currentMineral.setColoursAtKillhope(parser.nextText());
                        } else if (name.equals("furtherUses")) {
                            currentMineral.setFurtherUses(parser.nextText());
                        } else if (name.equals("streak")) {
                            currentMineral.setStreak(parser.nextText());
                        } else if (name.equals("cleavage")) {
                            currentMineral.setCleavage(parser.nextText());
                        } else if (name.equals("fracture")) {
                            currentMineral.setFracture(parser.nextText());
                        } else if (name.equals("specificGravity")) {
                            currentMineral.setSpecificGravity(parser.nextText());
                        } else if (name.equals("furtherProperties")) {
                            currentMineral.setFurtherProperties(parser.nextText());
                        } else if (name.equals("relevanceAtKillhope")) {
                            currentMineral.setRelevanceAtKillhope(parser.nextText());
                        } else if (name.equals("opticalProperties")){
                            currentMineral.setOpticalProperties(parser.nextText());
                        } else if (name.equals("impurities")) {
                            currentMineral.setImpurities(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("mineral") && currentMineral != null) {
                        minerals.add(currentMineral);
                    }
            }
            eventType = parser.next();
        }
    }
}
