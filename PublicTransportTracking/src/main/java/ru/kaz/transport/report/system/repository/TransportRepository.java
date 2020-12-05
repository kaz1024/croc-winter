package ru.kaz.transport.report.system.repository;

import ru.kaz.transport.report.system.model.PublicTransport;
import ru.kaz.transport.report.system.model.PublicTransportTypes;
import org.apache.derby.jdbc.EmbeddedDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для доступа к таблице с данными об отметках местоположения (LocationMark).
 */
public class TransportRepository {

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
    public TransportRepository(EmbeddedDataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
        this.TABLE_NAME = PublicTransport.getTableName();
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
    private void initTable() throws SQLException {
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
                                + "id INTEGER PRIMARY KEY, "
                                + "transportType VARCHAR(255), "
                                + "stateNumber VARCHAR(255), "
                                + "routeNumber VARCHAR(255) "
                                + ")");
                System.out.println("Table was successfully initialized!");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred during table initializing: " + e.getMessage());
            throw e;
        } finally {
            System.out.println("======================================");
        }
    }

    /**
     * Метод создания записи в БД о новом транспорте.
     *
     * @param publicTransport транспорт
     */
    public void createNew(PublicTransport publicTransport) throws SQLException {

        String sqlQuery = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            statement.setString(1, publicTransport.getId().toString());
            statement.setString(2, publicTransport.getType().toString());
            statement.setString(3, publicTransport.getStateNumber());
            statement.setString(4, publicTransport.getRouteNumber());
            statement.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Запись о транспорте с ID " + publicTransport.getId() + " уже существует!");
        } catch (Exception e) {
            System.out.println("Ошибка выполнения запроса createNew transport: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Метод поиска всех записей о транспорте в БД.
     *
     * @return список всего транспорта
     */
    public List<PublicTransport> findAll() throws SQLException {

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            List<PublicTransport> publicTransports = new ArrayList<>();
            while (resultSet.next()) {
                publicTransports.add(new PublicTransport(
                        resultSet.getInt("id"),
                        PublicTransportTypes.valueOf(resultSet.getString("transportType")),
                        resultSet.getString("stateNumber"),
                        resultSet.getString("routeNumber")));
            }
            return publicTransports;

        } catch (Exception e) {
            System.out.println("Ошибка выполнения запроса findAll transport: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Метод поиска записи в БД о транспорте по ID.
     *
     * @return optional, объект общественного танспорта или Optional.empty()
     */
    public Optional<PublicTransport> findTransportByID(Integer id) throws SQLException {

        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            statement.setString(1, id.toString());
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return Optional.of(new PublicTransport(resultSet.getInt("id"),
                        PublicTransportTypes.valueOf(resultSet.getString("transportType")),
                        resultSet.getString("stateNumber"),
                        resultSet.getString("routeNumber")));
            } else {
                System.out.println("Записи о транспорте с ID " + id + " не существует!");
            }
        } catch (Exception e) {
            System.out.println("Ошибка выполнения запроса findTransport: " + e.getMessage());
            throw e;
        }
        return Optional.empty();
    }

    /**
     * Метод удаления всех записей из таблицы.
     */
    public void deleteAll() throws SQLException {

        String sqlQuery = "DELETE FROM " + TABLE_NAME;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            statement.execute();

        } catch (Exception e) {
            System.out.println("Ошибка выполнения запроса deleteAll transport: " + e.getMessage());
            throw e;
        }
    }
}
