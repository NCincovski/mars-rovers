package org.nasa.api.custom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ContiguousCharactersCountTest {
    private final String word;

    public ContiguousCharactersCountTest(String word) {
        this.word = word;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{"Nenad"}, {"Nnennnadd"}, {"aAa"}, {"a"}, {"aaBb"}});
    }

    @Test
    public void testLettersCount() {
        ContiguousCharactersCount.lettersCount(word);
    }

    @Test
    public void testRegexLettersCount_CaseInsensitive() {
        ContiguousCharactersCount.regexLettersCount(word, true);
    }

    @Test
    public void testRegexLettersCount_CaseSensitive() {
        ContiguousCharactersCount.regexLettersCount(word, false);
    }
}