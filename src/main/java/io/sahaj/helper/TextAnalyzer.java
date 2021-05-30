package io.sahaj.helper;

import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import io.sahaj.resource.ComplexSentence;
import io.sahaj.resource.ReadabilityAnalysis;
import io.sahaj.resource.TextAnalysis;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TextAnalyzer {
    private final SyllableCounter syllableCounter = new SyllableCounter();
    //common words not to be counted as a word for complex sentence because stanford nlp lib treats periods as words also
    List<String> exceptionWords = Arrays.asList("!", ".", "Mr.", "Mrs.", "Dr.", "Prof.", "Ltd.", "etc.", "vs.");

    public TextAnalysis analyze(String text) {
        TextAnalysis textAnalysis = new TextAnalysis();
        Document document = new Document(text);
        List<Sentence> sentences = document.sentences();
        for (Sentence sentence : sentences) {
            int totalWordCount = 0;
            int totalSyllableCount = 0;
            int complexWordCount = 0;
            List<String> words = sentence.words();
            Set<String> filteredWords = filterExceptionWords(words);
            ComplexSentence complexSentence = null;
            for (String word : filteredWords) {
                totalWordCount++;
                int numberOfSyllables = syllableCounter.count(word);
                totalSyllableCount += numberOfSyllables;
                if (numberOfSyllables > 3) {
                    if (complexSentence == null)
                        complexSentence = new ComplexSentence();
                    complexSentence.addWord(word);
                    complexWordCount++;
                    complexSentence.setComplexSentence(sentence.text());
                }
            }
            if (filteredWords.size() > 20) {
                if (complexSentence == null)
                    complexSentence = new ComplexSentence();
                complexSentence.setComplexSentence(sentence.text());
            }
            if (complexSentence != null) {
                double fleschScore = calculateFleschScore(totalWordCount, totalSyllableCount);
                if (fleschScore <= 60 || totalWordCount > 20) {
                    textAnalysis.addComplexSentence(complexSentence);
                    ReadabilityAnalysis readabilityAnalysis = new ReadabilityAnalysis();
                    readabilityAnalysis.setTotalWords(totalWordCount);
                    readabilityAnalysis.setComplexWords(complexWordCount);
                    readabilityAnalysis.setFleschScore(fleschScore);
                    complexSentence.setReadabilityAnalysis(readabilityAnalysis);
                }
            }
        }
        return textAnalysis;
    }

    private double calculateFleschScore(int wordCount, int syllableCount) {
        int syllablesPerWord = syllableCount / wordCount;
        return Math.round((206.835 - (1.015 * wordCount) - (84.6 * syllablesPerWord)) * 100.0) / 100.0;
    }

    private Set<String> filterExceptionWords(List<String> words) {
        Set<String> wordSet = new HashSet<>();
        for (String word : words) {
            if (!exceptionWords.contains(word))
                wordSet.add(word);
        }
        return wordSet;
    }
}
