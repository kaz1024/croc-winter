import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

@DisplayName("Проверка класса для очистки директории.")
public class CleanThreadTest {

    @Test
    @DisplayName("Тест фонового потока очистки директории.")
    public void testDeleteFiles() throws InterruptedException, IOException {

        System.out.println("Main thread started...");

        // директория для очистки
        File file = new File("DirectoryToClean");

        // создаём директорию, на случай если кто-то удалил
        // предостерегая себя от NullPointerException в нескольких местах
        file.mkdir();

        DirectoryCleaner directoryCleaner = new DirectoryCleaner();

        // если директория пустая, создаем новый файлик
        // (что бы не копировать каждый раз)
        if(file.listFiles().length == 0){
            new File(file.toString() + "/iAmGoingToDie.txt").createNewFile();
        }

        // проверяем что папка не пустая
        Assertions.assertNotEquals(0, file.listFiles().length);

        // запускаем очищающий поток
        directoryCleaner.startCleaning(file, 5_000L);

        // ждем пока очистится
        Thread.sleep(5_000);

        // останавливаем поток
        directoryCleaner.disable();

        // проверяем что папка пустая
        Assertions.assertEquals(0, file.listFiles().length);

        System.out.println("Main thread finished...");
    }

}
