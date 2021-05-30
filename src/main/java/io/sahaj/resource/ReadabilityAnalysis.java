package io.sahaj.resource;

import lombok.Data;

@Data
public class ReadabilityAnalysis {
    private int totalWords;
    private double fleschScore;
    private int complexWords;
    private String improvementSuggestion = "Readability score: %.2f. Your sentence is %s";

    public void setFleschScore(double fleschScore) {
        this.fleschScore = fleschScore;
        if (fleschScore <= 10)
            setImprovementSuggestion(fleschScore, "extremely difficult to read, consider revising.");
        else if (fleschScore <= 30 && fleschScore > 10)
            setImprovementSuggestion(fleschScore, "very difficult to read, consider revising.");
        else if (fleschScore <= 50 && fleschScore > 30)
            setImprovementSuggestion(fleschScore, "difficult to read, consider revising.");
        else if (fleschScore <= 60 && fleschScore > 50)
            setImprovementSuggestion(fleschScore, "fairly difficult to read, consider revising.");
        else if (fleschScore <= 70 && fleschScore > 60)
            setImprovementSuggestion(fleschScore, "easy to understand.");
        else if (getTotalWords() > 20)
            setImprovementSuggestion(fleschScore, "easy to read but too long, consider revising.");
        else
            setImprovementSuggestion(fleschScore, "easy to read.");
    }

    public void setImprovementSuggestion(double fleschScore, String improvementSuggestion) {
        this.improvementSuggestion = String.format(this.improvementSuggestion, fleschScore, improvementSuggestion);
    }
}