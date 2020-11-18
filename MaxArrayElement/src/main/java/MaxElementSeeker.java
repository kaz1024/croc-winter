import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Класс для многопоточного поиска максимума массива.
 */
public class MaxElementSeeker {

    /**
     * Массив для поиска максимума.
     */
    private final List<Integer> numsList;

    /**
     * Список результатов выполнения задач.
     */
    private final List<Future<Integer>> futures;

    /**
     * Сервис исполнения задач.
     */
    private final ExecutorService executorService;

    /**
     * Класс для многопоточного поиска максимума массива.
     *
     * @param numsList массив для поиска
     * @param numOfThreads количество потоков
     */
    public MaxElementSeeker(List<Integer> numsList, int numOfThreads) {
        this.numsList = numsList;
        this.executorService = Executors.newFixedThreadPool(numOfThreads);
        this.futures = new ArrayList<>();
    }

    /**
     * Поиск максимума в массиве.
     *
     * Разбиваем исходный массив на части, в каждой ищем максимум (каждый поиск - задача),
     * затем ищем максимум среди максимумов.
     *
     * @return максимум массива
     * @throws ExecutionException executionException
     * @throws InterruptedException interruptedException
     */
    public Integer findMax() throws ExecutionException, InterruptedException {

        // шаг разбиения массива на части
        int step;

        if(numsList.size() > 1000){
            step = 100;
        } else {
            step = numsList.size() % 10;
        }

        // идем по исходному массиву с шагом step
        for(int i = 0; i < numsList.size(); i = i + step){

            int j = i + step;

            // если шаг выходит за границы массива, выставляем шаг как размер массива
            if(i + step > numsList.size()){
                j = numsList.size();
            }

            // часть массива от i до j
            List<Integer> subList = numsList.subList(i, j);

            Future<Integer> future = executorService.submit(new MaxArrayElement(subList));

            futures.add(future);

        }

        // список максимумов каждой части
        List<Integer> maxList = new ArrayList<>();

        for(Future<Integer> future : futures){
            maxList.add(future.get());
        }

        // ищем максимум среди максимумов частей
        Future<Integer> future = executorService.submit(new MaxArrayElement(maxList));

        // общий максимум
        int result = future.get();

        executorService.shutdown();

        return result;

    }

}
