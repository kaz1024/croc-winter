package converters;

import inputclasses.FilmsList;
import outputclasses.ActorsList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Jaxb XML конвертер.
 */
public class JaxbConverter {

    /**
     * Конвертация из XML в список фильмов.
     *
     * @param xml XML.
     * @param clazz класс списка фильмов.
     * @return список фильмов.
     * @throws JAXBException JAXBException.
     */
    public FilmsList convertFromXml(StringReader xml, Class<?> clazz) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(clazz);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (FilmsList) unmarshaller.unmarshal(xml);

    }

    /**
     * Конвертация списка актеров в StringWriter.
     *
     * @param actorsList список актеров.
     * @param clazz класс списка актеров.
     * @return StringWriter списка актеров.
     * @throws JAXBException JAXBException.
     */
    public StringWriter convertToXml(ActorsList actorsList, Class<?> clazz) throws JAXBException {

        StringWriter stringWriter = new StringWriter();

        JAXBContext context = JAXBContext.newInstance(clazz);

        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.marshal(actorsList, stringWriter);

        return stringWriter;
    }


}
