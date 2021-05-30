# TextAnalyzerLambda

## HTTP API that handles a POST request

### Input - text to analyze

### Output - Complex sentences within the input

#### A sentence is complex if it has words that have more than 3 syllables or if the sentence has more than 20 words

###### SyllableCounter is based on https://github.com/wfreitag/syllable-counter-java because it is close to https://metacpan.org/pod/Lingua::EN::Fathom 