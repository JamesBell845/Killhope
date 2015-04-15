package com.example.jamesbell.killhope;

import java.util.ArrayList;

/**
 * Created by Stephen on 15/04/2015.
 */
public class GlossaryTerm {
    private String word;
    private String definition;
    private ArrayList<GlossaryTerm> subterms;

    public GlossaryTerm(String word,String definition){
        this.word = word;
        this.definition = definition;
    }

    public ArrayList<GlossaryTerm> getSubterms() {
        return subterms;
    }

    public String getDefinition() {
        return definition;
    }

    public String getWord() {
        return word;
    }
}
