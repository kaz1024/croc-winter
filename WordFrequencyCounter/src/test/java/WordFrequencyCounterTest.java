import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@DisplayName("Проверка подсчета количества вхождений каждого слова в текст.")
public class WordFrequencyCounterTest {

    String originalText = "Eye, f-or      an - eye.";

    @DisplayName("Проверка форматируемости текста.")
    @Test
    public void textPreparingTest() {

        WordFrequencyCounter wordCounter = new WordFrequencyCounter();

        String formattedText = wordCounter.formatText(originalText);

        Assertions.assertEquals("eye f-or an eye", formattedText);

    }

    @DisplayName("Проверка подсчета слов в отформатированном тексте.")
    @Test
    public void frequencyTest() {

        WordFrequencyCounter wordCounter = new WordFrequencyCounter();

        Map<String, Integer> wordsFrequency = new HashMap<>();

        wordsFrequency.put("eye", 2);
        wordsFrequency.put("f-or", 1);
        wordsFrequency.put("an", 1);

        Assertions.assertEquals(wordsFrequency, wordCounter.count(originalText));

    }

}
