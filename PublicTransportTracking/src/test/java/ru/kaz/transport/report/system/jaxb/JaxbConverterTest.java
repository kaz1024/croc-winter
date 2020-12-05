package ru.kaz.transport.report.system.jaxb;

import ru.kaz.transport.report.system.model.LocationMark;
import ru.kaz.transport.report.system.dto.LocationMarkDTO;
import ru.kaz.transport.report.system.model.PublicTransport;
import ru.kaz.transport.report.system.model.PublicTransportTypes;
import org.junit.jupiter.api.*;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@DisplayName("Проверка класса-конвертера в XML.")
public class JaxbConverterTest {

    /**
     * Jaxb XML конвертер.
     */
    private JaxbConverter jaxbConverter;

    /**
     * Путь до образца XML
     */
    private final Path samplePath = Paths.get("src/main/resources/SampleTransportList.xml");

    /**
     * Пусть до файла, куда будет занесен результат тестового конвертирования.
     */
    private final Path testPath = Paths.get("src/main/resources/JaxbConverterTest.xml");

    /**
     * Выходной файл.
     */
    private File outputFile;

    // тестовые объекты
    private PublicTransport bus;
    private PublicTransport tram;

    private LocationMark locationMark2;
    private LocationMark locationMark4;

    @BeforeEach
    @DisplayName("Инициализация тестовых объектов и очистка таблиц.")
    public void objectsInitialization() {

        // инициализация конвертера
        jaxbConverter = new JaxbConverter();

        // инициализация тестовых объектов
        bus = new PublicTransport(1,
                PublicTransportTypes.Bus,
                "А324АА123",
                "65");
        tram = new PublicTransport(3,
                PublicTransportTypes.Tram,
                "С342СС123",
                "8");

        locationMark2 = new LocationMark(bus.getId(),
                LocalDateTime.of(2020, 9, 10, 15, 30),
                1400, 1400);

        locationMark4 = new LocationMark(tram.getId(),
                LocalDateTime.of(2020, 9, 10, 15, 30),
                58, 1632);

        // инициализация выходного файла
        outputFile = new File(testPath.toString());
    }

    @Test
    @DisplayName("Проверка конвертации списка транспорта в XML файл.")
    public void jaxbTest() throws IOException, JAXBException {

        // формируем тестовые объекты
        locationMark2.insertPublicTransport(bus);
        locationMark4.insertPublicTransport(tram);

        // инициализируем DTO класс для конвертации в XML
        LocationMarkDTO locationMarkDTO = new LocationMarkDTO(locationMark2, locationMark4);

        // конвертируем список в XML файл
        jaxbConverter.convertToXml(locationMarkDTO, outputFile);

        // получаем сгенерированный XML в виде строки для сравнения
        String generatedXML = new String(Files.readAllBytes(testPath));

        // получаем образец XML для сравнения
        String sampleXML = new String(Files.readAllBytes(samplePath));

        // сравниваем
        Assertions.assertEquals(sampleXML, generatedXML);
    }

    @AfterEach
    @DisplayName("Очистка объектов.")
    public void objectsCleaning() {
        jaxbConverter = null;

        bus = null;
        tram = null;

        locationMark2 = null;
        locationMark4 = null;
    }
}