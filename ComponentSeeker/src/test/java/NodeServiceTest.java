import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

@DisplayName("Проверка класса работы с вершинами.")
public class NodeServiceTest {

    Node n1 = new Node("n1");
    Node n2 = new Node("n2");
    Node n3 = new Node("n3");
    Node n4 = new Node("n4");
    Node n5 = new Node("n5");
    Node n6 = new Node("n6");

    @DisplayName("Проверка соединения вершин.")
    @Test
    public void connectionTest() {

        NodeService nodeService = new NodeService();

        //соединяем вершины
        nodeService.connectNodes(n1,n2,n3);

        //проверяем что у вершин в списке есть соединенные
        Assertions.assertTrue(n1.getLinks().contains(n2));
        Assertions.assertTrue(n1.getLinks().contains(n3));

        Assertions.assertTrue(n2.getLinks().contains(n1));
        Assertions.assertTrue(n2.getLinks().contains(n3));

        Assertions.assertTrue(n3.getLinks().contains(n1));
        Assertions.assertTrue(n3.getLinks().contains(n2));

    }

    @DisplayName("Проверка поиска компонент связности.")
    @Test
    public void componentSeekerTest() {

        //компонента связности 1 с вершинами n1, n2, n3
        Set<Node> c1 = new HashSet<>();
        c1.add(n1);
        c1.add(n2);
        c1.add(n3);

        //компонента связности 2 с вершинами n4, n5
        Set<Node> c2 = new HashSet<>();
        c2.add(n4);
        c2.add(n5);

        //компонента связности 3 с вершиной n6
        Set<Node> c3 = new HashSet<>();
        c3.add(n6);

        //множество компонент связности
        Set<Set<Node>> testComponents = new HashSet<>();
        testComponents.add(c1);
        testComponents.add(c2);
        testComponents.add(c3);

        NodeService nodeService = new NodeService();

        nodeService.connectNodes(n1,n2,n3);
        nodeService.connectNodes(n4,n5);

        Set<HashSet<Node>> components = nodeService.seekForComponents(n1,n2,n3,n4,n5,n6);

        //для наглядности
        System.out.println(components);

        Assertions.assertEquals(testComponents, components);

    }

}
