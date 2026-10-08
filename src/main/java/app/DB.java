package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    private static final String MARIA_DB_URL = "jdbc:mariadb://localhost:3308/svg_temperature_db";
    private static final String MARIA_DB_USER = "root";
    private static final String MARIA_DB_PASSWORD = "Test12";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(MARIA_DB_URL, MARIA_DB_USER, MARIA_DB_PASSWORD);
        } catch (SQLException mariaDbException) {
            System.out.println("Yo no shot");
        }
        return null;
    }

    public static void initializeDatabase() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS temperature_records (" +
                            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                            "input_value DOUBLE NOT NULL, " +
                            "input_unit VARCHAR(20) NOT NULL, " +
                            "output_value DOUBLE NOT NULL, " +
                            "output_unit VARCHAR(20) NOT NULL, "
            );
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to initialize the temperature database.", e);
        }
    }
}
