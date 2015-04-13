package com.example.jamesbell.killhope;

import android.content.res.AssetManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by jamesbell on 11/03/15.
 */
public class XMLParser {
    private XmlPullParserFactory xmlFactoryObject;
    private XmlPullParser parser;
    private ArrayList<MineralObject> minerals;
    public XMLParser(InputStream fis) {
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
                    if (name == "mineral") {
                        currentMineral = new MineralObject();
                    } else if (currentMineral != null) {
                        if (name == "name") {
                            currentMineral.setName(parser.nextText());
                        } else if (name == "formula") {
                            currentMineral.setForumla(parser.nextText());
                        } else if (name == "colour") {
                            currentMineral.setColour(parser.nextText());
                        } else if (name == "abundance"){
                            currentMineral.setAbundance(parser.nextText());
                        } else if (name == "hardness") {
                            currentMineral.setHardness(parser.nextText());
                        } else if (name == "lustre") {
                            currentMineral.setLustre(parser.nextText());
                        } else if (name == "ore") {
                            currentMineral.setOre(parser.nextText());
                        } else if (name == "interestingFact") {
                            currentMineral.setInterestingFact(parser.nextText());
                        } else if (name == "uses") {
                            currentMineral.setUses(parser.nextText());
                        } else if (name == "mainCountries") {
                            currentMineral.setMainCountries(parser.nextText());
                        } else if (name == "crystalHabit") {
                            currentMineral.setCrystalHabit(parser.nextText());
                        } else if (name == "crystalStructure") {
                            currentMineral.setCrystalStructure(parser.nextText());
                        } else if (name == "depositionalEnviro") {
                            currentMineral.setDepositionalEnviro(parser.nextText());
                        } else if (name == "transparency") {
                            currentMineral.setTransparency(parser.nextText());
                        } else if (name == "originOfName") {
                            currentMineral.setOriginOfName(parser.nextText());
                        } else if (name == "coloursAtKillhope") {
                            currentMineral.setColoursAtKillhope(parser.nextText());
                        } else if (name == "furtherUses") {
                            currentMineral.setFurtherUses(parser.nextText());
                        } else if (name == "streak") {
                            currentMineral.setStreak(parser.nextText());
                        } else if (name == "cleavage") {
                            currentMineral.setCleavage(parser.nextText());
                        } else if (name == "fracture") {
                            currentMineral.setFracture(parser.nextText());
                        } else if (name == "specificGravity") {
                            currentMineral.setSpecificGravity(parser.nextText());
                        } else if (name == "furtherProperties") {
                            currentMineral.setFurtherProperties(parser.nextText());
                        } else if (name == "relevanceAtKillhope") {
                            currentMineral.setRelevanceAtKillhope(parser.nextText());
                        } else if (name == "opticalProperties"){
                            currentMineral.setOpticalProperties(parser.nextText());
                        } else if (name == "impurities") {
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
