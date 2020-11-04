package outputclasses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс актера для конвертации в XML.
 */
public class ActorOutput {

    /**
     * Имя.
     */
    @XmlElement
    private String name;

    /**
     * Возраст.
     */
    @XmlElement
    private int age;

    /**
     * Список фильмов.
     */
    @XmlElementWrapper(name = "films")
    @XmlElement(name = "film")
    private List<FilmOutput> films = new ArrayList<>();

    /**
     * Класс актера для конвертации в XML.
     *
     * @param name имя.
     * @param age возраст.
     * @param film фильм.
     */
    public ActorOutput(String name, int age, FilmOutput film) {
        this.name = name;
        this.age = age;
        this.films.add(film);
    }

    /**
     * Добавить фильм.
     *
     * @param title название фильма.
     * @param role роль в фильме.
     */
    public void addFilm(String title, String role){
        films.add(new FilmOutput(title, role));
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Actor2{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", films=" + films +
                '}';
    }
}
