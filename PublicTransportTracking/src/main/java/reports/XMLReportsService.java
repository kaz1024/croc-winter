package reports;

import db.DataSourceProvider;
import dto.DTOCollection;
import jaxb.JaxbConverter;
import model.LocationMark;
import dto.LocationMarkDTO;
import model.PublicTransport;
import repository.LocationRepository;
import repository.TransportRepository;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для создания XML отчетов об общественном транспорте.
 *
 * Учтена возможность добавления других отчетов.
 */
public class XMLReportsService {

    /**
     * Репозиторий для доступа к таблице с данными о транспорте (PublicTransport).
     */
    private final TransportRepository transportRepository;

    /**
     * Репозиторий для доступа к таблице с данными об отметках местоположения (LocationMark).
     */
    private final LocationRepository locationRepository;

    /**
     * Data transfer object для конвертации списка транспорта в XML.
     */
    private DTOCollection dtoCollection;

    /**
     * Конвертер в XML.
     */
    private final JaxbConverter jaxbConverter;

    /**
     * Выходной XML файл.
     */
    private File outputFile;

    /**
     * Класс для создания XML отчетов об общественном транспорте.
     *
     * @throws IOException ошибка создания data source provider
     */
    public XMLReportsService() throws IOException {

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

        // инициализация XML конвертера
        jaxbConverter = new JaxbConverter();
    }

    /**
     * Создать отчет, содержащий весть транспорт,
     * находящийся в пределах radius метрах от заданных координат
     * на конкретный момент времени.
     *
     * @param ox            заданная координата OX
     * @param oy            заданная координата OY
     * @param localDateTime заданный момент времени
     *
     * @return true, при успешном создании отчета, false иначе
     */
    public boolean createTransportInRadiusReport(Integer ox, Integer oy, LocalDateTime localDateTime) {

        // путь до выходного файла
        String outputFilePath = "src/main/resources/PublicTransportList.xml";

        // выходной файл
        outputFile = new File(outputFilePath);

        // радиус поиска
        Integer radius = 2000;

        // получение списка транспорта, удовлетворяющего условию
        // если такого траснпорта нет, выдаем об этом сообщение и выходим из программы
        if (!getTransportInRadius(radius, ox, oy, localDateTime)) {
            System.out.println("Не найдено общественного транспорта, удовлетворяющего заданным критериям.");
            return false;
        }

        // конвертация полученного списка транспорта в XML файл
        if (!convertToXML(outputFile)) {
            System.out.println("Ошибка конвертации в XML");
            return false;
        }
        return true;
    }

    /**
     * Метод получения из базы данных списка транспорта и фильтрации его в соответствии с заданным условием:
     * находящегося в пределах radius метров от заданных координат
     * на конкретный момент времени.
     *
     * @param radius        радиус
     * @param ox            заданная координата OX
     * @param oy            заданная координата OY
     * @param localDateTime заданный момент времени
     *
     * @return true, если такой транспорт имеется, false иначе
     */
    private boolean getTransportInRadius(Integer radius, Integer ox, Integer oy, LocalDateTime localDateTime) {

        // получаем из базы данных все отметки и фильтруем в соответствии с условием
        // то, что транспорт находится в радиусе проверяем по формуле (x - x0)^2 + (y - y0)^ < radius^2
        List<LocationMark> locationMarks = locationRepository.findAll().stream()
                .filter(mark -> Math.pow(mark.getOx() - ox, 2) + Math.pow(mark.getOy() - oy, 2) < Math.pow(radius, 2) &&
                        mark.getLocalDateTime().isEqual(localDateTime))
                .collect(Collectors.toList());

        // если список пустой, возвращаем false
        if (locationMarks.isEmpty()) {
            return false;
        }

        // в каждый объект отметки добавляем объект транспорта из таблицы transports
        for (LocationMark locationMark : locationMarks) {

            // если транспорта с таким ID нет, кидаем NullPointerException
            PublicTransport publicTransport = transportRepository.findTransportByID(locationMark.getTransportID()).
                    orElseThrow(NullPointerException::new);

            locationMark.insertPublicTransport(publicTransport);
        }

        // инициализируем класс, который используется для конвертации в XML файл и
        // передаем ему список найденных отметок
        dtoCollection = new LocationMarkDTO(locationMarks);

        return true;
    }

    /**
     * Конвертация списка отметок в XML файл.
     *
     * @param file выходной файл
     *
     * @return true при успешнм создании файла и записи XML, false иначе
     */
    private boolean convertToXML(File file) {
        try {
            // запись результата конвертации в XML в файл
            jaxbConverter.convertToXml(dtoCollection, file);
        } catch (JAXBException e) {
            // если возникает ошибка конвертации, возвращаем false
            return false;
        }
        return true;
    }
}
