import java.util.*;

public class Sort {
    public static void main(String[] args) {

        int[] array = new int[10];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(Math.random() * 31) - 15;
        }

        System.out.println("Начальный массив: " + Arrays.toString(array));

        for(int i = 0; i < array.length - 1; i++) {
            if (array[i] < array[0]) {
                int temp = array[i];
                array[i] = array[0];
                array[0] = temp;

            } else if (array[i] > array[array.length - 1]) {
                int temp = array[i];
                array[i] = array[array.length - 1];
                array[array.length - 1] = temp;
            }
        }

        System.out.println("Результ: " + Arrays.toString(array));

    }
}
