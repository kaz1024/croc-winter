package jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;

/**
 * Адаптер для конвертации объекта LocalDateTime в XML.
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    @Override
    public LocalDateTime unmarshal(String v) {
        return LocalDateTime.parse(v);
    }

    @Override
    public String marshal(LocalDateTime v) {
        return v.toString();
    }
}
