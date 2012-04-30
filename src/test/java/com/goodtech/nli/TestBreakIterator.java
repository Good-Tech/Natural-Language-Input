package com.goodtech.nli;

import org.junit.Test;

import java.text.BreakIterator;
import java.util.Locale;

/**
 * Copyright (C) 2012 by Scott Byrns
 * http://github.com/scottbyrns
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p/>
 * Created 4/30/12 6:59 AM
 */
public class TestBreakIterator
{
    /**
     * http://stackoverflow.com/questions/2103598/java-simple-sentence-parser
     */
    @Test
    public void testIt()
    {
        String stringToExamine = "Take a sentence as input. Decompose it into it's many parts. Ask a neural network trained to identify parts of speech to classify the many parts. Use the classifications to identify matching agents within our systems. If those agents interfaces match the remainder of the sentence then ask another neural network to classify our remaining parts of speech with respect to the agents. Identify the best match and map the sentence to the agents chained sequences of contextual inputs (e.g. \"Start conversation with Joe Alan\" | Conversation.start().with(\"Joe Alan\")). Execute the code.";
        BreakIterator boundary = BreakIterator.getWordInstance();
        boundary.setText(stringToExamine);
        //          printEachForward(boundary, stringToExamine);
        //print each sentence in reverse order
        boundary = BreakIterator.getSentenceInstance(Locale.US);
        //          boundary.setText(stringToExamine);

        //print each sentence in reverse order
        boundary = BreakIterator.getSentenceInstance(Locale.US);
        boundary.setText(stringToExamine);
        //        printEachBackward(boundary, stringToExamine);
        printEachForward(boundary,
                         stringToExamine);
        //          printFirst(boundary, stringToExamine);
        //          printLast(boundary, stringToExamine);
    }

    public static void printEachForward(BreakIterator boundary, String source)
    {
        int start = boundary.first();
        for (
                int end = boundary.next(); end != BreakIterator.DONE; start = end, end = boundary.next()
                )
        {
            System.out.println(source.substring(start,
                                                end));
        }
    }

    public static void printEachBackward(BreakIterator boundary, String source)
    {
        int end = boundary.last();
        for (
                int start = boundary.previous(); start != BreakIterator.DONE; end = start, start = boundary.previous()
                )
        {
            System.out.println(source.substring(start,
                                                end));
        }
    }

    public static void printFirst(BreakIterator boundary, String source)
    {
        int start = boundary.first();
        int end = boundary.next();
        System.out.println(source.substring(start,
                                            end));
    }

    public static void printLast(BreakIterator boundary, String source)
    {
        int end = boundary.last();
        int start = boundary.previous();
        System.out.println(source.substring(start,
                                            end));
    }

    public static void printAt(BreakIterator boundary, int pos, String source)
    {
        int end = boundary.following(pos);
        int start = boundary.previous();
        System.out.println(source.substring(start,
                                            end));
    }

    public static int nextWordStartAfter(int pos, String text)
    {
        BreakIterator wb = BreakIterator.getWordInstance();
        wb.setText(text);
        int last = wb.following(pos);
        int current = wb.next();
        while (current != BreakIterator.DONE)
        {
            for (int p = last; p < current; p++)
            {
                if (Character.isLetter(text.codePointAt(p)))
                {
                    return last;
                }
            }
            last = current;
            current = wb.next();
        }
        return BreakIterator.DONE;
    }


    /**
     * http://docs.oracle.com/javase/tutorial/i18n/text/examples/BreakIteratorDemo.java
     *
     * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
     *
     * Redistribution and use in source and binary forms, with or without
     * modification, are permitted provided that the following conditions
     * are met:
     *
     *   - Redistributions of source code must retain the above copyright
     *     notice, this list of conditions and the following disclaimer.
     *
     *   - Redistributions in binary form must reproduce the above copyright
     *     notice, this list of conditions and the following disclaimer in the
     *     documentation and/or other materials provided with the distribution.
     *
     *   - Neither the name of Oracle or the names of its
     *     contributors may be used to endorse or promote products derived
     *     from this software without specific prior written permission.
     *
     * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
     * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
     * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
     * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
     * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
     * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
     * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
     * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
     * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
     * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
     * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
     */

    @Test
    public void testOracleBreakIteratorDemos()
    {
      characterExamples();
      System.out.println();
      wordExamples();
      System.out.println();
      sentenceExamples();
      System.out.println();
      lineExamples();
    }

    static void extractWords(String target, BreakIterator wordIterator)
    {

        wordIterator.setText(target);
        int start = wordIterator.first();
        int end = wordIterator.next();

        while (end != BreakIterator.DONE)
        {
            String word = target.substring(start,
                                           end);
            if (Character.isLetterOrDigit(word.charAt(0)))
            {
                System.out.println(word);
            }
            start = end;
            end = wordIterator.next();
        }
    }

    static void reverseWords(String target, BreakIterator wordIterator)
    {

        wordIterator.setText(target);
        int end = wordIterator.last();
        int start = wordIterator.previous();

        while (start != BreakIterator.DONE)
        {
            String word = target.substring(start,
                                           end);
            if (Character.isLetterOrDigit(word.charAt(0)))
            {
                System.out.println(word);
            }
            end = start;
            start = wordIterator.previous();
        }
    }

    static void markBoundaries(String target, BreakIterator iterator)
    {

        StringBuffer markers = new StringBuffer();
        markers.setLength(target.length() + 1);
        for (int k = 0; k < markers.length(); k++)
        {
            markers.setCharAt(k,
                              ' ');
        }

        iterator.setText(target);
        int boundary = iterator.first();

        while (boundary != BreakIterator.DONE)
        {
            markers.setCharAt(boundary,
                              '^');
            boundary = iterator.next();
        }

        System.out.println(target);
        System.out.println(markers);
    }

    static void formatLines(String target, int maxLength, Locale currentLocale)
    {

        BreakIterator boundary = BreakIterator.getLineInstance(currentLocale);
        boundary.setText(target);
        int start = boundary.first();
        int end = boundary.next();
        int lineLength = 0;

        while (end != BreakIterator.DONE)
        {
            String word = target.substring(start,
                                           end);
            lineLength = lineLength + word.length();
            if (lineLength >= maxLength)
            {
                System.out.println();
                lineLength = word.length();
            }
            System.out.print(word);
            start = end;
            end = boundary.next();
        }
    }

    static void listPositions(String target, BreakIterator iterator)
    {

        iterator.setText(target);
        int boundary = iterator.first();

        while (boundary != BreakIterator.DONE)
        {
            System.out.println(boundary);
            boundary = iterator.next();
        }
    }

    static void characterExamples()
    {

        BreakIterator arCharIterator = BreakIterator.getCharacterInstance(new Locale("ar",
                                                                                     "SA"));
        // Arabic word for "house"
        String house = "\u0628" + "\u064e" + "\u064a" + "\u0652" + "\u067a" + "\u064f";
        listPositions(house,
                      arCharIterator);
    }

    static void wordExamples()
    {

        Locale currentLocale = new Locale("en",
                                          "US");
        BreakIterator wordIterator = BreakIterator.getWordInstance(currentLocale);
        String someText = "She stopped.  " + "She said, \"Hello there,\" and then went on.";

        /**
         *
         * NOTES:
         *  We may be able to do fast classification of sentences by analyzing the boundaries.
         *
         *  Copyright (C) 2012 by Scott Byrns
         *
         */



        markBoundaries(someText,
                       wordIterator);
        System.out.println();
        extractWords(someText,
                     wordIterator);
    }

    static void sentenceExamples()
    {

        Locale currentLocale = new Locale("en",
                                          "US");
        BreakIterator sentenceIterator = BreakIterator.getSentenceInstance(currentLocale);
        String someText = "She stopped.  " + "She said, \"Hello there,\" and then went on.";
        markBoundaries(someText,
                       sentenceIterator);
        String variousText = "He's vanished!  " + "What will we do?  It's up to us.";
        markBoundaries(variousText,
                       sentenceIterator);
        String decimalText = "Please add 1.5 liters to the tank.";
        markBoundaries(decimalText,
                       sentenceIterator);
        String donneText = "\"No man is an island . . . " + "every man . . . \"";
        markBoundaries(donneText,
                       sentenceIterator);
        String dogText = "My friend, Mr. Jones, has a new dog.  " + "The dog's name is Spot.";
        markBoundaries(dogText,
                       sentenceIterator);
    }

    static void lineExamples()
    {

        Locale currentLocale = new Locale("en",
                                          "US");
        BreakIterator lineIterator = BreakIterator.getLineInstance(currentLocale);
        String someText = "She stopped.  " + "She said, \"Hello there,\" and then went on.";
        markBoundaries(someText,
                       lineIterator);
        String hardHyphen = "There are twenty-four hours in a day.";
        markBoundaries(hardHyphen,
                       lineIterator);
        System.out.println();
        String moreText = "She said, \"Hello there,\" and then " + "went on down the street.  When she stopped " + "to look at the fur coats in a shop window, " + "her dog growled.  \"Sorry Jake,\" she said. " + " \"I didn't know you would take it personally.\"";
        formatLines(moreText,
                    30,
                    currentLocale);
        System.out.println();
    }

}
