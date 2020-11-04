package inputclasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Класс актера для конвертации из XML.
 */
@XmlRootElement(name = "actor")
public class ActorInput {

    /**
     * Имя.
     */
    @XmlAttribute(name = "name")
    private String name;

    /**
     * Возраст.
     */
    @XmlAttribute(name = "age")
    private int age;

    /**
     * Роль.
     */
    @XmlAttribute(name = "role")
    private String role;

    /**
     * Класс актера для конвертации из XML.
     */
    public ActorInput() {
    }

    /**
     * Класс актера для конвертации из XML.
     *
     * @param name имя.
     * @param age возраст.
     * @param role роль.
     */
    public ActorInput(String name, int age, String role) {
        this.name = name;
        this.age = age;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActorInput)) return false;
        ActorInput actorInput = (ActorInput) o;
        return getAge() == actorInput.getAge() &&
                Objects.equals(getName(), actorInput.getName()) &&
                Objects.equals(getRole(), actorInput.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAge(), getRole());
    }

    @Override
    public String toString() {
        return "Actor{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
