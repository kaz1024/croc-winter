import java.io.File;

/**
 * Класс управления потоком очистки директории.
 */
public class DirectoryCleaner {

    /**
     * Поток очистки директории
     */
    private CleanThread cleanThread;

    /**
     * Поток.
     */
    private Thread thread;

    public void startCleaning(File directory, Long mills){

        cleanThread = new CleanThread(directory, mills);

        thread = new Thread(cleanThread);

        thread.start();

    }

    /**
     * Завершение потока.
     */
    public void disable(){
        cleanThread.disable();
    }
}
