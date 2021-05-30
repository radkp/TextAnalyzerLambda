package io.sahaj;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.sahaj.helper.TextAnalyzer;
import io.sahaj.resource.TextAnalysis;

import java.util.LinkedHashMap;

public class TextAnalyzerRequestHandler implements RequestHandler<LinkedHashMap, TextAnalysis> {
    private TextAnalyzer textAnalyzer = new TextAnalyzer();

    @Override
    public TextAnalysis handleRequest(LinkedHashMap input, Context context) {
        return textAnalyzer.analyze(input.get("body").toString());
    }
}
