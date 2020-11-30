package jaxb;

import dto.DTOCollection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * Jaxb XML конвертер.
 */
public class JaxbConverter {

    /**
     * Jaxb XML конвертер.
     */
    public JaxbConverter() {
    }

    /**
     * Конвертация data transfer объекта в XML файл.
     *
     * @param dtoCollection  data transfer объект
     * @param file           выходной XML файл
     * @throws JAXBException ошибка конвертации в XML
     */
    public void convertToXml(DTOCollection dtoCollection, File file) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(dtoCollection.getClass());

        Marshaller marshaller = context.createMarshaller();

        // форматирование XML в читаемый вид
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        // запись в файл
        marshaller.marshal(dtoCollection, file);
    }
}
