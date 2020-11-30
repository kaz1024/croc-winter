package reports;

import db.DataSourceProvider;
import model.LocationMark;
import model.PublicTransport;
import model.PublicTransportTypes;
import org.junit.jupiter.api.*;
import repository.LocationRepository;
import repository.TransportRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@DisplayName("Проверка класса формирования XML отчетов.")
public class XMLReportsServiceTest {

    /**
     * Репозиторий для доступа к таблице с данными о транспорте (PublicTransport).
     */
    private static TransportRepository transportRepository;

    /**
     * Репозиторий для доступа к таблице с данными об отметках местоположения (LocationMark).
     */
    private static LocationRepository locationRepository;

    /**
     * Класс для создания отчетов.
     */
    private XMLReportsService XMLReportsService;

    /**
     * Путь до образца XML.
     */
    private final Path sampleReportPath = Paths.get("src/main/resources/SampleTransportList.xml");

    /**
     * Путь до сгенерированного XML отчета.
     */
    private final Path generatedReportPath = Paths.get("src/main/resources/PublicTransportList.xml");

    /**
     * Сгенерированный отчет
     */
    private File outputFile;

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
    public void objectsInitialization() throws IOException {

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

        locationRepository.createNew(locationMark1);
        locationRepository.createNew(locationMark2);
        locationRepository.createNew(locationMark3);
        locationRepository.createNew(locationMark4);

        // инициализация класса создания отчетов
        XMLReportsService = new XMLReportsService();

        // инициализируем выходного файл
        outputFile = new File(generatedReportPath.toString());
    }

    @Test
    @DisplayName("Проверка создания отчета об общественном транспорте в радиусе 2000м в заданный момент времени.")
    public void testReportCreation() throws IOException {

        // проверяем что отчет сформировался
        Assertions.assertTrue(XMLReportsService.createTransportInRadiusReport(0, 0,
                LocalDateTime.of(2020, 9, 10, 15, 30)));

        // получаем сгенерированный XML
        String generatedXML = new String(Files.readAllBytes(generatedReportPath));

        // получаем образец XML
        String sampleXML = new String(Files.readAllBytes(sampleReportPath));

        // сравниваем
        Assertions.assertEquals(sampleXML, generatedXML);
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

        XMLReportsService = null;
    }
}
