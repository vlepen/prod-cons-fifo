package training.prodconsfifo.persistent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class DBTemplate {
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:mem:prod_cons_fifo;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public static void executeUpdateStatement(String sql) {
        executeUpdateStatement(sql, (preparedStatement -> {}));
    }

    public static void executeUpdateStatement(String sql, Consumer<PreparedStatement> sqlParamsSetter) {
        try (Connection connection = DBTemplate.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            sqlParamsSetter.accept(preparedStatement);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute statement " + sql, e);
        }
    }

    public static <T> List<T> executeQueryStatement(String sql, Function<ResultSet, T> mapper) {
        try (Connection connection = DBTemplate.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet rs = preparedStatement.executeQuery();
            List<T> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapper.apply(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute statement " + sql, e);
        }
    }

    private static Connection getDBConnection() {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to find DB driver " + DB_DRIVER, e);
        }
        try {
            return DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to obtain DB connection", e);
        }
    }
}
