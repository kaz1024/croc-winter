import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Вершина.
 */
public class Node {

    /** Список связанных вершин. */
    private final Set<Node> links = new HashSet<>();

    /** Название вершины. */
    private final String name;

    /**
     * Вершина.
     *
     * @param name название вершины.
     */
    public Node(String name) {
        this.name = name;
    }

    /**
     * Вершина.
     *
     * @param name название вершины.
     * @param nodes связанные вершины.
     */
    public Node(String name, Node... nodes) {
        this.name = name;
        this.links.addAll(Arrays.asList(nodes));
    }

    /**
     * Соединение вершин.
     *
     * @param nodes связанные вершины.
     */
    public void connect(Node... nodes) {
        this.links.addAll(Arrays.asList(nodes));
    }

    public Set<Node> getLinks() {
        return links;
    }

    @Override
    public String toString() {
        return name;
    }
}
