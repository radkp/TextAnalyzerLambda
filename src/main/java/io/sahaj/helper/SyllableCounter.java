/*
 * Copyright 2014 Hugo “m09” Mougard
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.sahaj.helper;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SyllableCounter {

    private int maxCacheSize;

    private final Map<String, Integer> exceptions, cache;

    private final List<Pattern> subSyl, addSyl;

    private final Set<Character> vowels;

    public SyllableCounter() {
        this(0);
    }

    public SyllableCounter(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;

        cache = new HashMap<>();

        exceptions = new HashMap<>();

        subSyl = Stream.of("cial", "cian", "tia", "cius", "cious", "gui", "ion",
                "iou", "sia$", ".ely$", "ves$", "geous$", "gious$", "[^aeiou]eful$", ".red$")
                .map(Pattern::compile)
                .collect(Collectors.toList());

        addSyl = Stream.of("ia", "riet", "dien", "iu", "io", "ii",
                "[aeiouy]bl$", "mbl$", "tl$", "sl$",
                "[aeiou]{3}",
                "^mc", "ism$",
                "(.)(?!\\1)([aeiouy])\\2l$",
                "[^l]llien",
                "^coad.", "^coag.", "^coal.", "^coax.",
                "(.)(?!\\1)[gq]ua(.)(?!\\2)[aeiou]",
                "dnt$", "thm$", "ier$", "iest$", "[^aeiou][aeiouy]ing$")
                .map(Pattern::compile)
                .collect(Collectors.toList());
        vowels = new HashSet<>();
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
        vowels.add('y');

        populateExceptions();
    }

    /**
     * Setter for the maximum cash size.
     *
     * @param maxCacheSize the new maximum size of the cache. Negative to
     *                     disable caching. Won't clean a cache that was bigger than the new size
     *                     before.
     */
    public void setMaxCacheSize(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }

    /**
     * Getter for the current cash size. Here mainly to make the cache behavior
     * testable.
     *
     * @return cacheSize the current size of the cache.
     */
    public int getCurrentCacheSize() {
        return cache.size();
    }

    /**
     * Main point of this library. Method to count the number of syllables of a
     * word using a fallback method as documented at the class level of this
     * documentation.
     *
     * @param word the word you want to count the syllables of.
     * @return the number of syllables of the word.
     */
    public int count(String word) {
        if (word == null) {
            throw new NullPointerException("the word parameter was null.");
        } else if (word.length() == 0) {
            return 0;
        } else if (word.length() == 1) {
            return 1;
        }

        word = word.toLowerCase(Locale.ENGLISH);

        if (exceptions.containsKey(word)) {
            return exceptions.get(word);
        }

        if (maxCacheSize > 0 && cache.containsKey(word)) {
            return cache.get(word);
        }

        if (word.charAt(word.length() - 1) == 'e') {
            word = word.substring(0, word.length() - 1);
        }

        int count = 0;
        boolean prevIsVowel = false;
        for (char c : word.toCharArray()) {
            boolean isVowel = vowels.contains(c);
            if (isVowel && !prevIsVowel) {
                ++count;
            }
            prevIsVowel = isVowel;
        }
        for (Pattern pattern : addSyl) {
            if (pattern.matcher(word).find()) {
                ++count;
            }
        }
        for (Pattern pattern : subSyl) {
            if (pattern.matcher(word).find()) {
                --count;
            }
        }

        if (maxCacheSize > 0 && cache.size() < maxCacheSize) {
            cache.put(word, count);
        }

        return count > 0 ? count : 1;
    }

    private void populateExceptions() {
        exceptions.put("brutes", 1);
        exceptions.put("chummed", 1);
        exceptions.put("flapped", 1);
        exceptions.put("foamed", 1);
        exceptions.put("gaped", 1);
        exceptions.put("h'm", 1);
        exceptions.put("lb", 1);
        exceptions.put("mimes", 1);
        exceptions.put("ms", 1);
        exceptions.put("peeped", 1);
        exceptions.put("sheered", 1);
        exceptions.put("st", 1);
        exceptions.put("queue", 1);
        exceptions.put("60", 2);
        exceptions.put("bepatched", 2);
        exceptions.put("capered", 2);
        exceptions.put("caressed", 2);
        exceptions.put("clattered", 2);
        exceptions.put("deafened", 2);
        exceptions.put("dr", 2);
        exceptions.put("effaced", 2);
        exceptions.put("effaces", 2);
        exceptions.put("fringed", 2);
        exceptions.put("gravesend", 2);
        exceptions.put("greyish", 2);
        exceptions.put("jr", 2);
        exceptions.put("mangroves", 2);
        exceptions.put("messieurs", 2);
        exceptions.put("motioned", 2);
        exceptions.put("moustaches", 2);
        exceptions.put("mr", 2);
        exceptions.put("mrs", 2);
        exceptions.put("pencilled", 2);
        exceptions.put("poleman", 2);
        exceptions.put("quivered", 2);
        exceptions.put("reclined", 2);
        exceptions.put("shivered", 2);
        exceptions.put("sidespring", 2);
        exceptions.put("slandered", 2);
        exceptions.put("sombre", 2);
        exceptions.put("sr", 2);
        exceptions.put("stammered", 2);
        exceptions.put("suavely", 2);
        exceptions.put("tottered", 2);
        exceptions.put("trespassed", 2);
        exceptions.put("truckle", 2);
        exceptions.put("unstained", 2);
        exceptions.put("therefore", 2);
        exceptions.put("businesses", 3);
        exceptions.put("bottleful", 3);
        exceptions.put("discoloured", 3);
        exceptions.put("disinterred", 3);
        exceptions.put("hemispheres", 3);
        exceptions.put("manoeuvred", 3);
        exceptions.put("sepulchre", 3);
        exceptions.put("shamefully", 3);
        exceptions.put("unexpressed", 3);
        exceptions.put("veriest", 3);
        exceptions.put("wyoming", 3);
        exceptions.put("etc", 4);
        exceptions.put("sailmaker", 4);
        exceptions.put("satiated", 4);
        exceptions.put("sententiously", 4);
        exceptions.put("particularized", 5);
        exceptions.put("unostentatious", 5);
        exceptions.put("propitiatory", 6);
    }
}

