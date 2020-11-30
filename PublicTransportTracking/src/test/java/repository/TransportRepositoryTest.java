package repository;

import db.DataSourceProvider;
import model.PublicTransport;
import model.PublicTransportTypes;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@DisplayName("Проверка репозитория работы с таблицей transports.")
public class TransportRepositoryTest {

    /**
     * Репозиторий для доступа к таблице с данными о транспорте (PublicTransport).
     */
    private static TransportRepository transportRepository;

    /**
     * Репозиторий для доступа к таблице с данными о транспорте (PublicTransport).
     */
    private static LocationRepository locationRepository;

    private PublicTransport bus;
    private PublicTransport trolleybus;
    private PublicTransport tram;

    @BeforeAll
    @DisplayName("Инициализация репозиториев и таблиц.")
    public static void tablesInitialization() throws IOException {

        // получение DataSource
        DataSourceProvider dataSourceProvider;
        try {
            dataSourceProvider = new DataSourceProvider();
        } catch (IOException e) {
            System.out.println("Ошибка создания провайдера: " + e.getMessage());
            throw e;
        }

        // инициализация репозитория transportRepository и таблицы transports
        transportRepository = new TransportRepository(dataSourceProvider.getDataSource());

        // инициализация репозитория locationRepository и таблицы locations
        locationRepository = new LocationRepository(dataSourceProvider.getDataSource());
    }

    @BeforeEach
    @DisplayName("Инициализация тестовых объектов и очистка таблиц.")
    public void objectsInitialization() {

        // инициализация тестовых объектов
        bus = new PublicTransport(1,
                PublicTransportTypes.Bus,
                "А324АА123",
                "65");

        trolleybus = new PublicTransport(2,
                PublicTransportTypes.Trolleybus,
                "Б523ББ123",
                "133a");

        tram = new PublicTransport(3,
                PublicTransportTypes.Tram,
                "С342СС123",
                "8");

        // очистка таблиц
        locationRepository.deleteAll();
        transportRepository.deleteAll();
    }

    @Test
    @DisplayName("Проверка метода добавления записи в таблицу.")
    public void testCreateNew() {

        // добавляем запись в таблицу
        transportRepository.createNew(bus);

        // получаем из таблицы добавленную запись
        PublicTransport actualTransport = transportRepository.findAll().get(0);

        // сравниваем объекты
        Assertions.assertEquals(bus, actualTransport);
    }

    @Test
    @DisplayName("Проверка метода послучения всех записей из таблицы.")
    public void testFindAll() {

        // создаем тестовый список
        List<PublicTransport> testList = new ArrayList<>();

        // заполняем тестовый список
        testList.add(bus);
        testList.add(trolleybus);
        testList.add(tram);

        // добавляем записи в таблицу
        transportRepository.createNew(bus);
        transportRepository.createNew(trolleybus);
        transportRepository.createNew(tram);

        // сравниваем тестовый список и результат запроса всех записей
        Assertions.assertEquals(testList, transportRepository.findAll());
    }

    @Test
    @DisplayName("Проверка метода поиска записи по ID траспорта.")
    public void testFindTransportByID() {

        // добавляем записи в таблицу
        transportRepository.createNew(bus);
        transportRepository.createNew(trolleybus);

        // получаем запись из таблицы по ID
        PublicTransport actualTransport = transportRepository.findTransportByID(trolleybus.getId()).
                orElseThrow(NullPointerException::new);

        // сравниваем объекты
        Assertions.assertEquals(trolleybus, actualTransport);
    }
    @AfterEach
    @DisplayName("Очистка объектов.")
    public void objectsCleaning() {
        bus = null;
        trolleybus = null;
        tram = null;
    }
}
