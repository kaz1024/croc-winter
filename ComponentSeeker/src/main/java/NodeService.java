import java.util.HashSet;
import java.util.Set;

/**
 * Класс работы с вершинами.
 */
public class NodeService {

    /** Множество компонент связности. */
    private final Set<HashSet<Node>> components = new HashSet<>();

    /** Пройденные вершины */
    private final HashSet<Node> checked = new HashSet<>();

    /**
     * Поиск компонент связности.
     *
     * @param nodes вершины.
     * @return множество компонент связности.
     */
    public Set<HashSet<Node>> seekForComponents(Node... nodes) {

        for(Node node:nodes){

            //если вершина уже добавлена в какой-то компонент связности, пропускаем
            if(checked.contains(node)){
                continue;
            }

            //добавляем компонент связности в множество компонент связности
            components.add(seek(new HashSet<>(), node));

        }
        return components;
    }

    /**
     * Рекурсивный проход по компоненту связности.
     *
     * @param component пустое множество, в которое будут добавлены все вершины данного компонента связности.
     * @param node вершина входа в компонент связности.
     *
     * @return заполненное множество вершин (компонент связности).
     */
    public HashSet<Node> seek(HashSet<Node> component, Node node){

        //добавляем вершину в компонент связности
        component.add(node);
        //помечаем вершину как пройденную
        checked.add(node);

        //дно рекурсии
        if (allChecked(node)){
            return component;
        }

        for(Node node1:node.getLinks()){
            if(!checked.contains(node1)){
                seek(component, node1);
            }
        }
        return component;
    }

    /**
     * Проверка, остались ли еще у вершины непройденные соседи (для поиска дна рекурсии).
     *
     * @param node вершина.
     * @return True, если все соседи пройдены (достигнуто дно рекурсии), False иначе.
     */
    public boolean allChecked(Node node){
        for(Node node1:node.getLinks()){
            if (!checked.contains(node1)) {
                return false;
            }

        }
        return true;
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
