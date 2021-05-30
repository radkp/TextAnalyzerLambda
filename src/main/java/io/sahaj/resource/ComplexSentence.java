package io.sahaj.resource;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ComplexSentence {
    private String complexSentence;
    private List<String> complexWords = new ArrayList<>();
    private ReadabilityAnalysis readabilityAnalysis;

    public void addWord(String word) {
        complexWords.add(word);
    }
}
