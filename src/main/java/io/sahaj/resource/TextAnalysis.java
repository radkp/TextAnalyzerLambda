package io.sahaj.resource;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class TextAnalysis {
    @Getter
    private List<ComplexSentence> complexSentences = new ArrayList<>();

    public void addComplexSentence(ComplexSentence complexSentence) {
        complexSentences.add(complexSentence);
    }
}
