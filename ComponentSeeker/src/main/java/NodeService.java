import java.util.HashSet;
import java.util.Set;

/**
 * Класс работы с вершинами.
 */
public class NodeService {

    /** Множество компонент связности. */
    private final Set<HashSet<Node>> components = new HashSet<>();

    /**
     * Поиск компонент связности.
     *
     * @param nodes вершины.
     * @return множество компонент связности.
     */
    public Set<HashSet<Node>> seekForComponents(Node... nodes) {

        for(Node node:nodes){

            //создаем новый компонент связности
            HashSet<Node> component = new HashSet<>();
            //добавляем текущую вершину
            component.add(node);
            //и все связанные с ней вершины
            component.addAll(node.getLinks());

            //добавляем компоненту в множество компонент связности
            components.add(component);

        }
        return components;
    }

    /**
     * Соединение вершин.
     *
     * @param nodes вершины.
     */
    public void connectNodes(Node... nodes){
        for(Node node:nodes){
            node.connect(nodes);
        }
    }
}
