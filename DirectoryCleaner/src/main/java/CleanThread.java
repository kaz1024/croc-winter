import java.io.File;

/**
 * Поток для очистки директории.
 */
public class CleanThread implements Runnable {

    /**
     * Директория для очистки.
     */
    private File directory;

    /**
     * Промежуток между очисткими.
     */
    private final Long mills;

    /**
     * Переменная для остановки потока.
     */
    private boolean isActive = true;

    /**
     * Поток для очистки директории.
     *
     * @param directory директория для очистки
     * @param mills промежуток между очистками
     */
    public CleanThread(File directory, Long mills) {
        this.directory = directory;
        this.mills = mills;
    }

    @Override
    public void run() {
        System.out.println("Clean thread started...");

        while(isActive){

            // для каждого файла/папки выполняем рекурсивное удаление
            for (File file : directory.listFiles()) {
                recursiveClean(file);
            }

            // ложимся поспать на mills миллисекунд
            try {
                Thread.sleep(mills);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        System.out.println("Clean thread finished...");
    }

    /**
     * Рекурсивное удаление файла/директории.
     *
     * @param file файл/директория
     */
    public void recursiveClean(File file){

        // дно рекурсии
        if(!file.exists()){
            return;
        }

        // если файл - директория, рекурсивно запускаем
        if(file.isDirectory()){
            for (File file1 : file.listFiles()) {
                recursiveClean(file1);
            }
        }

        // удаляем
        file.delete();

    }

    /**
     * Завершение потока.
     */
    public void disable(){
        System.out.println("Clean thread interrupted...");
        this.isActive = false;

    }
}
