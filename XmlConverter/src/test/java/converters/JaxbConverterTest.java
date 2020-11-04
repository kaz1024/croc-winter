package converters;

import inputclasses.ActorInput;
import inputclasses.FilmInput;
import inputclasses.FilmsList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import outputclasses.ActorsList;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@DisplayName("Проверка конвертации XML списка фильмов в XML список актеров.")
public class JaxbConverterTest {

    Path actorsPath = Paths.get("src/main/resources", "actors.xml");
    Path filmsPath = Paths.get("src/main/resources", "films.xml");

    JaxbConverter jaxbConverter;

    ActorsList actorsList;

    StringReader xml;

    @DisplayName("Проверка конвертации списка фильмов из XML в объект класса FilmsList.")
    @Test
    public void testXmlToFilmsConvert() throws JAXBException, IOException {

        jaxbConverter = new JaxbConverter();
        // считываем список фильмов
        xml = new StringReader(new String(Files.readAllBytes(filmsPath)));

        ActorInput actorInput1 = new ActorInput("Актер 1", 30, "Роль 1");
        ActorInput actorInput2 = new ActorInput("Актер 2", 23, "Роль 2");
        ActorInput actorInput3 = new ActorInput("Актер 3", 40, "Роль 3");
        ActorInput actorInput4 = new ActorInput("Актер 4", 70, "Роль 4");
        ActorInput actorInput5 = new ActorInput("Актер 2", 23, "Роль 5");
        ActorInput actorInput6 = new ActorInput("Актер 3", 40, "Роль 6");

        FilmInput filmInput1 = new FilmInput("Фильм 1", "Описание 1", Arrays.asList(actorInput1, actorInput2, actorInput3));
        FilmInput filmInput2 = new FilmInput("Фильм 2", "Описание 2", Arrays.asList(actorInput4, actorInput5, actorInput6));

        // вручную созданный список актеров
        FilmsList filmsListExpected = new FilmsList(Arrays.asList(filmInput1, filmInput2));

        // сконвертированный из XML список актеров
        FilmsList filmsListActual = jaxbConverter.convertFromXml(xml, FilmsList.class);

        // их сравнение
        Assertions.assertEquals(filmsListExpected, filmsListActual);

    }

    @DisplayName("Проверка конвертации списка актеров в XML.")
    @Test
    public void testActorsToXmlConverter() throws JAXBException, IOException {

        jaxbConverter = new JaxbConverter();

        // считываем список фильмов из XML
        StringReader filmsXml = new StringReader(new String(Files.readAllBytes(filmsPath)));

        // конвертируем из XML.
        FilmsList filmsList = jaxbConverter.convertFromXml(filmsXml, FilmsList.class);

        // конвертер из списка фильмов в список актеров
        FilmsToActorsConverter filmsToActorsConverter = new FilmsToActorsConverter();

        // конвертируем в список актеров
        actorsList = filmsToActorsConverter.convert(filmsList);

        // конвертируем список актеров в XML
        StringWriter actorsXml = jaxbConverter.convertToXml(actorsList, ActorsList.class);

        // переводим в строку (для сравнения)
        String actorsXmlActual = actorsXml.toString().trim();

        // считываем список актеров из XML
        String actorsXmlExpected = new String(Files.readAllBytes(actorsPath));

        // сравниваем строки
        Assertions.assertEquals(actorsXmlExpected, actorsXmlActual);
    }
}
