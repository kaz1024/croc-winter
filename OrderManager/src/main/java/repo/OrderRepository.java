package repo;

import model.Order;
import org.apache.derby.jdbc.EmbeddedDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий для доступа к таблице с данными о заказах (Order).
 */
public class OrderRepository {

    /**
     * Название таблицы.
     */
    private static final String TABLE_NAME = "orderr";

    /**
     * DataSource.
     */
    private EmbeddedDataSource dataSource;

    /**
     * Репозиторий для доступа к таблице с данными о заказах (Order).
     */
    public OrderRepository(EmbeddedDataSource dataSource) {
        this.dataSource = dataSource;
        initTable();
    }

    /**
     * Метод инициализации БД.
     *
     * Оказывается, SQL диалект, используемый JavaDB, не умеет в "IF NOT EXISTS" :(
     * Поэтому пришлось найти небольшой workaround, который предварительно проверяет существование таблицы в базе.
     */
    private void initTable() {
        System.out.printf("Start initializing %s table%n", TABLE_NAME);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            DatabaseMetaData databaseMetadata = connection.getMetaData();
            ResultSet resultSet = databaseMetadata.getTables(
                    null,
                    null,
                    // Несмотря на то, что мы создаем таблицу в нижнем регистре (и дальше к ней так же обращаемся),
                    // поиск мы осуществляем в верхнем. Такие вот приколы
                    TABLE_NAME.toUpperCase(),
                    new String[]{"TABLE"});
            if (resultSet.next()) {
                System.out.println("Table has already been initialized");
            } else {
                statement.executeUpdate(
                        "CREATE TABLE "
                                + TABLE_NAME
                                + " ("
                                + "id INTEGER PRIMARY KEY, "
                                + "title VARCHAR(255), "
                                + "isPaid BOOLEAN, "
                                + "date DATE, "
                                + "time TIME "
                                + ")");
                System.out.println("Table was successfully initialized");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred during table initializing: " + e.getMessage());
        } finally {
            System.out.println("=========================");
        }
    }

    /**
     * Метод создания записи в БД о новом заказе.
     *
     * @param order заказ
     */
    public void createNew(Order order) {

        String sqlQuery = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, order.getId().toString());
            statement.setString(2, order.getTitle());
            statement.setString(3, order.isPaid().toString());
            statement.setString(4, order.getDate().toString());
            statement.setString(5, order.getTime().toString());
            statement.execute();
        } catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Запись с ID " + order.getId() + " уже существует!");
        } catch (Exception e) {
            System.out.println("Ошибка выполнения запроса insert: " + e.getMessage());
        }
    }

    /**
     * Метод поиска всех заказов в БД.
     *
     * @return список всех созданных заказов
     */
    public List<Order> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            List<Order> orderList = new ArrayList<>();
            while (resultSet.next()) {
                orderList.add(new Order(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getBoolean("isPaid"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime()));
            }
            return orderList;
        } catch (Exception e) {
            System.out.println("Ошибка выполнения запроса findAll: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Метод поиска записи в БД о заказе по ID.
     * Используется для тестов.
     *
     * @return список всех созданных заказов
     */
    public Order findByID(Integer id) {

        String sqlQuery = "SELECT id, title, isPaid, date, time FROM " + TABLE_NAME + " WHERE id=?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, id.toString());
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if(resultSet.next()) {
                return new Order(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getBoolean("isPaid"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime());
            } else {
                System.out.println("Записи с ID " + id + " не существует!");
            }
        } catch (Exception e) {
            System.out.println("Ошибка выполнения запроса findByID: " + e.getMessage());
        }
        return null; // ой не хорошо, а что поделать, лучше не придумал
    }

    /**
     * Метод удаления записи в БД о заказе по ID.
     *
     * @param id ID заказа
     */
    public void deleteRow(Integer id){

        String sqlQuery = "DELETE FROM " + TABLE_NAME + " WHERE id=?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, id.toString());
            statement.execute();

        } catch (Exception e) {
            System.out.println("Ошибка выполнения запроса delete: " + e.getMessage());
            e.toString();
        }
    }

    /**
     * Метод обновления записи в БД о заказе по ID.
     *
     * @param id ID заказа
     * @param column название изменяемого поля
     * @param object новое значение
     */
    public void updateRow(Integer id, String column, Object object){

        String sqlQuery = "UPDATE " + TABLE_NAME + " SET " + column + " = ?" + " WHERE id=?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, object.toString());
            statement.setString(2, id.toString());
            statement.execute();

        } catch (Exception e) {
            System.out.println("Ошибка выполнения запроса update: " + e.getMessage());
            e.toString();
        }
    }

    /**
     * Метод удаления всех записей из БД.
     */
    public void clearTable(){
        String sqlQuery = "DELETE FROM " + TABLE_NAME;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.execute();

        } catch (Exception e) {
            System.out.println("Ошибка выполнения запроса clearTable: " + e.getMessage());
        }
    }

    /**
     * Метод падающий таблицу.
     */
    public void dropTable(){

        String sqlQuery = "DROP TABLE " + TABLE_NAME;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.execute();

        } catch (Exception e) {
            System.out.println("Ошибка выполнения запроса dropTable: " + e.getMessage());
        }

    }

}
