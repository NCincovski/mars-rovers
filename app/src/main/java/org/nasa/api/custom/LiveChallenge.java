package org.nasa.api.custom;

public class LiveChallenge {
    public static void main(String[] args) {
        System.out.println(lettersCount("aabbbbccdda"));
        System.out.println(lettersCount("aaabbcccdaa"));
        System.out.println(lettersCount("a"));
        System.out.println(lettersCount("aa"));
        System.out.println(lettersCount("Nenad"));
        System.out.println(lettersCount(""));
    }

    /**
     * Contiguous letters sequence counter
     *
     * @param word the word
     * @return String of the letters and their consecutive appearance counter
     */
    private static String lettersCount(String word) {
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
        return result.toString();
    }
}
