public class Bytes {
    public static void main(String[] args) {

        int num = (int)(Math.random() * Integer.MAX_VALUE); //начальное число от 0 до макс. значения Integer

        System.out.println("Размер в байтах: " + num);
        printBytes(num);

    }

    public static void printBytes(float num) {

        int unitsCount = 0; //счетчик единиц измерения
        String units; // единицы измерения

        while (num > 1024) {
            unitsCount++;
            num /= 1024;
        }

        switch (unitsCount) {
            case 1: units = "KB";
                break;
            case 2: units = "MB";
                break;
            case 3: units = "GB";
                break;
            default: units = "B";
        }

        System.out.println(String.format("В человекочитаемом виде: %.1f ", num) + units);
    }
}
