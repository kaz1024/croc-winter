package repository;

import model.LocationMark;
import org.apache.derby.jdbc.EmbeddedDataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий для доступа к таблице с данными об отметках местоположения (LocationMark).
 */
public class LocationRepository {

    /**
     * DataSource.
     */
    private final EmbeddedDataSource dataSource;

    /**
     * Название таблицы.
     */
    private final String TABLE_NAME;

    /**
     * Репозиторий для доступа к таблице с данными об отметках местоположения (LocationMark).
     */
    public LocationRepository(EmbeddedDataSource dataSource) {
        this.dataSource = dataSource;
        this.TABLE_NAME = LocationMark.getTableName();
        initTable();
    }

    /**
     * Метод инициализации БД.
     * <p>
     * SQL диалект, используемый JavaDB, не умеет в "IF NOT EXISTS",
     * поэтому предварительно проверяем существование таблицы в базе.
     *
     * @author VKhlybov
     */
    private void initTable() {
        System.out.printf("Start initializing %s table...%n", TABLE_NAME);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            DatabaseMetaData databaseMetadata = connection.getMetaData();
            ResultSet resultSet = databaseMetadata.getTables(
                    null,
                    null,
                    // Создаем таблицу и обращаемся к ней в нижнем регистре,
                    // но поиск осуществляется в верхнем.
                    TABLE_NAME.toUpperCase(),
                    new String[]{"TABLE"});
            if (resultSet.next()) {
                System.out.println("Table has already been initialized!");
            } else {
                statement.executeUpdate(
                        "CREATE TABLE "
                                + TABLE_NAME
                                + " ("
                                + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                                + "transport_Id INTEGER REFERENCES transports (id), "
                                + "dateTime TIMESTAMP, "
                                + "ox INTEGER, "
                                + "oy INTEGER "
                                + ")");
                System.out.println("Table was successfully initialized!");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred during table initializing: " + e.getMessage());
        } finally {
            System.out.println("======================================");
        }
    }

    /**
     * Метод создания записи в БД о новой отметке местоположения.
     *
     * @param locationMark отметка местоположения
     */
    public void createNew(LocationMark locationMark) {

        // для форматирования LocalDateTime в вид, который может быть записан в БД
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String sqlQuery = "INSERT INTO " + TABLE_NAME + " VALUES (DEFAULT, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, locationMark.getTransportID().toString());
            statement.setString(2, locationMark.getLocalDateTime().format(dateTimeFormatter));
            statement.setString(3, locationMark.getOx().toString());
            statement.setString(4, locationMark.getOy().toString());
            statement.execute();
        } catch (Exception e) {
            System.out.println("Ошибка выполнения запроса createNew locationMark: " + e.getMessage());
        }
    }

    /**
     * Метод поиска всех отметок о местоположении в БД.
     *
     * @return список всех меток о местоположении
     */
    public List<LocationMark> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            List<LocationMark> locationMarks = new ArrayList<>();
            while (resultSet.next()) {
                locationMarks.add(new LocationMark(
                        resultSet.getInt("transport_Id"),
                        resultSet.getTimestamp("dateTime").toLocalDateTime(),
                        resultSet.getInt("ox"),
                        resultSet.getInt("oy")));
            }
            return locationMarks;
        } catch (Exception e) {
            System.out.println("Ошибка выполнения запроса findAll locationMarks: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Метод удаления всех записей из таблицы.
     */
    public void deleteAll() {
        String sqlQuery = "DELETE FROM " + TABLE_NAME;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.execute();

        } catch (Exception e) {
            System.out.println("Ошибка выполнения запроса deleteAll locationMarks: " + e.getMessage());
        }
    }
}