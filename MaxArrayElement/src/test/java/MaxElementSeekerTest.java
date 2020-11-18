import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@DisplayName("Тест класса многопоточного поиска максимального элемента массива.")
public class MaxElementSeekerTest {

    @Test
    @DisplayName("Тест поиска максимального элемента массива.")
    public void testMaxElementSeek() throws ExecutionException, InterruptedException {

        // размер массива (по совместительству максимальный элемент)
        int max = (int) (Math.random() * (10_000_000 - 10 + 1) + 10);
        System.out.println("array size - " + max);

        // массив чисел
        List<Integer> list = new ArrayList<>();

        for(int i = 0; i <= max; i++){
            list.add(i);
        }

        // мешаем массив
        Collections.shuffle(list);

        MaxElementSeeker maxElementSeeker = new MaxElementSeeker(list, 4);

        // засечка для времени работы метода
        long start = System.currentTimeMillis();

        // поиск максмума
        int result = maxElementSeeker.findMax();

        // время работы метода
        System.out.println("seek time - " + (System.currentTimeMillis() - start) + "мс");

        Assertions.assertEquals(max, result);

    }
}
