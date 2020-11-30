package repository;

import db.DataSourceProvider;
import model.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DisplayName("Проверка репозитория работы с таблицей locations.")
public class LocationRepositoryTest {

    /**
     * Репозиторий для доступа к таблице с данными о транспорте (PublicTransport).
     */
    private static TransportRepository transportRepository;

    /**
     * Репозиторий для доступа к таблице с данными об отметках местоположения (LocationMark).
     */
    private static LocationRepository locationRepository;

    // тестовые объекты
    private PublicTransport bus;
    private PublicTransport trolleybus;
    private PublicTransport tram;

    private LocationMark locationMark1;
    private LocationMark locationMark2;
    private LocationMark locationMark3;
    private LocationMark locationMark4;

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

        locationMark1 = new LocationMark(bus.getId(),
                LocalDateTime.of(2020, 9, 10, 15, 30),
                1500, 1500);

        locationMark2 = new LocationMark(bus.getId(),
                LocalDateTime.of(2020, 9, 10, 15, 30),
                1400, 1400);

        locationMark3 = new LocationMark(trolleybus.getId(),
                LocalDateTime.of(2020, 9, 15, 18, 20),
                -2473, 3124);

        locationMark4 = new LocationMark(tram.getId(),
                LocalDateTime.of(2020, 9, 10, 15, 30),
                58, 1632);

        // очистка таблиц
        locationRepository.deleteAll();
        transportRepository.deleteAll();

        // добавление тестовых объектов в таблицу transports
        transportRepository.createNew(bus);
        transportRepository.createNew(trolleybus);
        transportRepository.createNew(tram);
    }

    @Test
    @DisplayName("Проверка метода добавления записи в таблицу.")
    public void testCreateNew() {

        // добавляем запись в таблицу
        locationRepository.createNew(locationMark1);

        // получаем из таблицы добавленную запись
        LocationMark actualLocationMark = locationRepository.findAll().get(0);

        // сравниваем объекты
        Assertions.assertEquals(locationMark1, actualLocationMark);
    }

    @Test
    @DisplayName("Проверка метода послучения всех записей из таблицы.")
    public void testFindAll() {

        // создаем тестовый список
        List<LocationMark> testList = new ArrayList<>();

        // заполняем тестовый список
        testList.add(locationMark1);
        testList.add(locationMark2);
        testList.add(locationMark3);
        testList.add(locationMark4);

        // добавляем записи в таблицу
        locationRepository.createNew(locationMark1);
        locationRepository.createNew(locationMark2);
        locationRepository.createNew(locationMark3);
        locationRepository.createNew(locationMark4);

        // сравниваем тестовый список и результат запроса всех записей
        Assertions.assertEquals(testList, locationRepository.findAll());
    }

    @AfterEach
    @DisplayName("Очистка объектов.")
    public void objectsCleaning() {
        bus = null;
        trolleybus = null;
        tram = null;

        locationMark1 = null;
        locationMark2 = null;
        locationMark3 = null;
        locationMark4 = null;
    }
}
