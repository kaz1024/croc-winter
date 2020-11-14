package repo;

import db.DataSourceProvider;
import model.Order;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class orderRepositoryTest {

    private static OrderRepository orderRepository;
    private static Order order1;
    private static Order order2;
    private static Order order3;
    private static Order order4;
    private static Order order5;

    @BeforeAll
    public static void init() throws IOException { //статика это плохо!(?)

        DataSourceProvider dataSourceProvider;
        try {
            dataSourceProvider = new DataSourceProvider();
        } catch (IOException e) {
            System.out.println("Ошибка создания провайдера: " + e.getMessage());
            throw e;
        }

        orderRepository = new OrderRepository(dataSourceProvider.getDataSource());

        order1 = new Order(1,
                "Book",
                Boolean.TRUE,
                LocalDate.of(2020, 3, 30),
                LocalTime.of(15,30));

        order2 = new Order(2,
                "Car",
                Boolean.FALSE,
                LocalDate.of(2020, 5, 25),
                LocalTime.of(18,10));

        order3 = new Order(3,
                "House",
                true,
                LocalDate.of(2020, 6, 3),
                LocalTime.of(3,15));

        order4 = new Order(4,
                "Boat", Boolean.TRUE,
                LocalDate.of(2020, 8, 15),
                LocalTime.of(23,42));

        order5 = new Order(5,
                "Island",
                false,
                LocalDate.of(2019, 11, 17),
                LocalTime.of(12,12));

    }

    @Test
    public void testCreateNew(){

        orderRepository.createNew(order1);

        Order actualOrder = orderRepository.findAll().get(0);

        Assertions.assertEquals(order1, actualOrder);

        orderRepository.clearTable();

    }

    @Test
    public void testFindByID(){

        orderRepository.createNew(order1);
        orderRepository.createNew(order2);

        Order actualOrder = orderRepository.findByID(2);

        Assertions.assertEquals(order2, actualOrder);

        orderRepository.clearTable();

    }

    @Test
    public void testFindAll(){

        List<Order> testList = new ArrayList<>();

        testList.add(order1);
        testList.add(order3);
        testList.add(order5);

        orderRepository.createNew(order1);
        orderRepository.createNew(order3);
        orderRepository.createNew(order5);

        Assertions.assertEquals(testList, orderRepository.findAll());

        orderRepository.clearTable();

    }

    @Test
    public void testDeleteRow(){

        orderRepository.createNew(order1);
        orderRepository.createNew(order2);
        orderRepository.createNew(order3);
        orderRepository.createNew(order4);

        Assertions.assertEquals(4, orderRepository.findAll().size());

        orderRepository.deleteRow(2);

        Assertions.assertEquals(3, orderRepository.findAll().size());

        orderRepository.clearTable();

    }

    @Test
    public void testUpdateRow(){

        orderRepository.createNew(order1);
        orderRepository.createNew(order2);
        orderRepository.createNew(order3);

        orderRepository.updateRow(1, "title", "Big house");
        orderRepository.updateRow(2, "isPaid", Boolean.FALSE);
        orderRepository.updateRow(3, "date", LocalDate.of(2020, 12, 22));

        Assertions.assertEquals("Big house", orderRepository.findByID(1).getTitle());
        Assertions.assertEquals(Boolean.FALSE, orderRepository.findByID(2).isPaid());
        Assertions.assertEquals(LocalDate.of(2020, 12, 22), orderRepository.findByID(3).getDate());

        orderRepository.clearTable();

    }

    @Test
    public void testClearTable(){

        orderRepository.createNew(order1);
        orderRepository.createNew(order2);

        orderRepository.clearTable();

        Assertions.assertTrue(orderRepository.findAll().isEmpty());

    }

    @AfterAll
    public static void testDropTable(){

        orderRepository.dropTable();

    }

}
