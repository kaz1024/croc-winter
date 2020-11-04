package outputclasses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для хранения списка актеров.
 */
@XmlRootElement(name = "actors")
public class ActorsList {

    /**
     * Список актеров.
     */
    @XmlElement(name = "actor")
    private List<ActorOutput> actors;

    /**
     * Класс для хранения списка актеров.
     */
    public ActorsList() {
        actors = new ArrayList<>();
    }

    /**
     * Класс для хранения списка актеров.
     *
     * @param actors актеры.
     */
    public ActorsList(List<ActorOutput> actors) {
        this.actors = actors;
    }

    /**
     * Добавить актера в список.
     *
     * @param name имя актера.
     * @param age возраст актера.
     * @param filmOutput фильм актера.
     */
    public void add(String name, int age, FilmOutput filmOutput){
        actors.add(new ActorOutput(name, age, filmOutput));
    }

    public List<ActorOutput> getActors() {
        return actors;
    }

    @Override
    public String toString() {
        return "ActorsList{" +
                "actors=" + actors +
                '}';
    }
}
