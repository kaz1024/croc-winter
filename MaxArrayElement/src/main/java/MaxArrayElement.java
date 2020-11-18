import java.util.List;
import java.util.concurrent.Callable;

/**
 * Поток для поиска максимума в массиве.
 */
public class MaxArrayElement implements Callable<Integer> {

    /**
     * Массив чисел.
     */
    private List<Integer> numsList;

    /**
     * Поток для поиска максимума в массиве.
     */
    public MaxArrayElement(List<Integer> numsList) {
        this.numsList = numsList;
    }

    @Override
    public Integer call() {

        int max = numsList.get(0);

        for(int i:numsList){
            if(i > max){
                max = i;
            }
        }

        return max;
    }
}
