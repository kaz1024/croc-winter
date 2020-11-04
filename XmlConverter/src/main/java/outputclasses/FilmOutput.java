package outputclasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Класс фильма для конвертации в XML.
 */
@XmlRootElement
public class FilmOutput {

    /**
     * Название.
     */
    @XmlAttribute
    private String title;

    /**
     * Роль актера в фильме.
     */
    @XmlAttribute
    private String role;

    /**
     * Класс фильма для конвертации в XML.
     */
    public FilmOutput() {
    }

    /**
     * Класс фильма для конвертации в XML.
     *
     * @param title название фильма.
     * @param role роль актера в фильме.
     */
    public FilmOutput(String title, String role) {
        this.title = title;
        this.role = role;
    }

    @Override
    public String toString() {
        return "Film2{" +
                "title='" + title + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
