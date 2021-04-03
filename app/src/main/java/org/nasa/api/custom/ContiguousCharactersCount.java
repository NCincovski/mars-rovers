package org.nasa.api.custom;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContiguousCharactersCount {

    /**
     * Contiguous characters sequence counter
     *
     * @param word the word
     */
    public static void lettersCount(String word) {
        char[] arr = word.toCharArray();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < arr.length; ) {
            int counter = 1;
            int j = i + 1;
            for (; j < arr.length; j++) {
                if (arr[i] == arr[j]) {
                    counter++;
                } else {
                    break;
                }
            }
            result.append(arr[i]).append(counter);
            i = j;
        }
        System.out.println(result);
    }

    /**
     * Contiguous characters sequence counter with regex
     *
     * @param word the word
     */
    public static void regexLettersCount(final String word, final boolean caseInsensitive) {
        final String regex = "(\\S)(\\1)*";
        Pattern pattern = caseInsensitive ? Pattern.compile(regex, Pattern.CASE_INSENSITIVE) : Pattern.compile(regex);
        Matcher matcher = pattern.matcher(word);
        StringBuilder builder = new StringBuilder(word + " --> ");
        while (matcher.find()) {
            final String group = matcher.group(0);
            builder.append(group.charAt(0)).append(group.length());
        }
        System.out.println(builder);
    }
}
