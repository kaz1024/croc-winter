package inputclasses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * Класс фильма для конвертации из XML.
 */
@XmlRootElement
public class FilmInput {

    /**
     * Название.
     */
    @XmlElement(name = "title")
    private String title;

    /**
     * Описание.
     */
    @XmlElement(name = "description")
    private String description;

    /**
     * Список актеров.
     */
    @XmlElementWrapper(name = "actors")
    @XmlElement(name = "actor")
    private List<ActorInput> actorInputs;

    /**
     * Класс фильма для конвертации из XML.
     */
    public FilmInput() {
    }

    /**
     * Класс фильма для конвертации из XML.
     *
     * @param title название.
     * @param description описание.
     * @param actorInputs список актеров.
     */
    public FilmInput(String title, String description, List<ActorInput> actorInputs) {
        this.title = title;
        this.description = description;
        this.actorInputs = actorInputs;
    }

    public String getTitle() {
        return title;
    }

    public List<ActorInput> getActors() {
        return actorInputs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilmInput)) return false;
        FilmInput filmInput = (FilmInput) o;
        return Objects.equals(getTitle(), filmInput.getTitle()) &&
                Objects.equals(description, filmInput.description) &&
                Objects.equals(getActors(), filmInput.getActors());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), description, getActors());
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", actors=" + actorInputs +
                '}';
    }

}
