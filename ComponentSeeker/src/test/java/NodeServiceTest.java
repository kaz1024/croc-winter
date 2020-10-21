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
    Node n7 = new Node("n7");
    Node n8 = new Node("n8");
    Node n9 = new Node("n9");
    Node n10 = new Node("n10");
    Node n11 = new Node("n11");
    Node n12 = new Node("n12");
    Node n13 = new Node("n13");

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

        //тестовая компонента связности 1 с вершинами n1, n2, n3
        Set<Node> c1 = new HashSet<>();
        c1.add(n1);
        c1.add(n2);
        c1.add(n3);

        //тестовая компонента связности 2 с вершинами n4, n5
        Set<Node> c2 = new HashSet<>();
        c2.add(n4);
        c2.add(n5);

        //тестовая компонента связности 3 с вершиной n6
        Set<Node> c3 = new HashSet<>();
        c3.add(n6);

        //тестовая компонента связности 4 с вершинами n7, n8, n9, n10, n11, n12, n13
        Set<Node> c4 = new HashSet<>();
        c4.add(n7);
        c4.add(n8);
        c4.add(n9);
        c4.add(n10);
        c4.add(n11);
        c4.add(n12);
        c4.add(n13);

        //тестовое множество компонент связности
        Set<Set<Node>> testComponents = new HashSet<>();
        testComponents.add(c1);
        testComponents.add(c2);
        testComponents.add(c3);
        testComponents.add(c4);

        NodeService nodeService = new NodeService();

        //соединяем вершины
        nodeService.connectNodes(n1,n2,n3);
        nodeService.connectNodes(n4,n5);
        nodeService.connectNodes(n7,n8);
        nodeService.connectNodes(n8,n9);
        nodeService.connectNodes(n9,n10);
        nodeService.connectNodes(n9,n11);
        nodeService.connectNodes(n11,n12);
        nodeService.connectNodes(n11,n13);

        //множество компонент связности
        Set<HashSet<Node>> components = nodeService.seekForComponents(n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13);

        //для наглядности
        System.out.println(components);

        Assertions.assertEquals(testComponents, components);

    }

}
