import java.util.HashMap;
import java.util.Map;

/**
 * Подсчет количества вхождений слов в текст.
 */
public class WordFrequencyCounter {

    /**
     * Подсчет количесва вхождений каждого слова в текст.
     *
     * @param text текст для подсчета.
     * @return Map<Слово, Количество вхождений>
     */
    public Map<String, Integer> count(String text) {

        //форматируем текст
        String formattedText = formatText(text);

        //разбиваем на слова
        String[] wordsArray = formattedText.split(" ");
        Map<String, Integer> wordsFrequency = new HashMap<>();

        for (String word : wordsArray) {
            if (!wordsFrequency.containsKey(word)) {
                //если такого слова еще не встречалось, кладем в Map со значением 1
                wordsFrequency.put(word, 1);
            } else {
                //иначе, заменяем значение на +1
                wordsFrequency.replace(word, wordsFrequency.get(word) + 1);
            }
        }
        return wordsFrequency;
    }


    /**
     * Форматирование текста (удаление пробелов, запятых и тд).
     *
     * Удаляет все лишние символы, подрядидущие пробелы, но не удаляет дефис в слове("какой-то").
     *
     * @param text форматируемый текст.
     * @return отформатированный текст.
     */
    public String formatText(String text) {

        text = text.toLowerCase();

        char[] charArray = text.toCharArray();
        StringBuilder formattedText = new StringBuilder();

        for (int i = 0; i < charArray.length - 1; i++) {
            //если текущий и следующий символ не буква, пропускаем
            if (!isLetter(charArray[i]) && !isLetter(charArray[i + 1])) {
                continue;
            } else {
                //иначе добавляем к строке
                formattedText.append(charArray[i]);
            }

        }
        //если последний символ буква - добавляем
        if(isLetter(charArray[charArray.length-1]))
            formattedText.append(charArray[charArray.length-1]);

        return formattedText.toString();
    }

    /**
     * Проверка, является ли символ буквой.
     *
     * @param c символ.
     * @return True, если буква. False иначе.
     */
    public boolean isLetter(char c) {
        return c >= 'A' && c <= 'Z' ||
                c >= 'a' && c <= 'z' ||
                c >= 'А' && c <= 'Я' ||
                c >= 'а' && c <= 'я';
    }

}

