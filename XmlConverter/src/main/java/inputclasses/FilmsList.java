package inputclasses;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;

/**
 * Класс для хранения списка фильмов.
 */
@XmlRootElement(name = "films")
public class FilmsList {

    /**
     * Список фильмов.
     */
    @XmlElement(name = "film")
    private List<FilmInput> filmInputs;

    /**
     * Класс для хранения списка фильмов.
     */
    public FilmsList() {
    }

    /**
     * Класс для хранения списка фильмов.
     *
     * @param filmInputs список фильмов.
     */
    public FilmsList(List<FilmInput> filmInputs) {
        this.filmInputs = filmInputs;
    }

    public List<FilmInput> getFilms() {
        return filmInputs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilmsList)) return false;
        FilmsList filmsList = (FilmsList) o;
        return Objects.equals(getFilms(), filmsList.getFilms());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFilms());
    }

    @Override
    public String toString() {
        return "FilmsList{" +
                "films=" + filmInputs +
                '}';
    }
}
