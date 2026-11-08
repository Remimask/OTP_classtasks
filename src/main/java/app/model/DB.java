package app.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    private static final String MARIA_DB_URL = "jdbc:mariadb://localhost:3306/svg_temperature_db";
    private static final String MARIA_DB_USER = "root";
    private static final String MARIA_DB_PASSWORD = "example";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(MARIA_DB_URL, MARIA_DB_USER, MARIA_DB_PASSWORD);
    }

    public static void initializeDatabase() {
        try {
            TempRecordDAO.createTableIfNotExists();
            createTranslationsTablesIfNotExists();
            defaultTranslations();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to initialize the temperature database.", e);
        }
    }

    private static void createTranslationsTablesIfNotExists() throws SQLException {
        String translationsSql = "CREATE TABLE IF NOT EXISTS translations (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "unit_code VARCHAR(20) NOT NULL UNIQUE)";

        String unitTranslationSql = "CREATE TABLE IF NOT EXISTS unit_translation (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "unit_id BIGINT NOT NULL, " +
                "lang VARCHAR(20) NOT NULL, " +
                "name VARCHAR(100) NOT NULL, " +
                "description VARCHAR(255), " +
                "UNIQUE (unit_id, lang), " +
                "CONSTRAINT fk_unit_translation_unit FOREIGN KEY (unit_id) REFERENCES translations(id))";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(translationsSql);
            statement.executeUpdate(unitTranslationSql);
        }
    }

    public static void defaultTranslations() throws SQLException {
        String[][] translations = {
                {"C", "en", "Celsius", "Temperature measured in degrees Celsius"},
                {"C", "fi", "Celsius", "Lämpötila mitattuna celsiusasteina"},
                {"C", "sv", "Celsius", "Temperatur mätt i grader Celsius"},
                {"C", "ja", "セルシウス", "セルシウス度で測定された温度"},
                {"C", "ar", "سلسيوس", "درجة الحرارة المقاسة بالدرجات المئوية"},
                {"F", "en", "Fahrenheit", "Temperature measured in degrees Fahrenheit"},
                {"F", "fi", "Fahrenheit", "Lämpötila mitattuna fahrenheitasteina"},
                {"F", "sv", "Fahrenheit", "Temperatur mätt i grader Fahrenheit"},
                {"F", "ja", "ファーレンハイト", "華氏で測定された温度"},
                {"F", "ar", "فهرنهايت", "درجة الحرارة المقاسة بالدرجات الفهرنهايتية"},
                {"K", "en", "Kelvin", "Temperature measured in Kelvin"},
                {"K", "fi", "Kelvin", "Lämpötila mitattuna kelvineinä"},
                {"K", "sv", "Kelvin", "Temperatur mätt i kelvin"},
                {"K", "ja", "ケルビン", "ケルビンで測定された温度"},
                {"K", "ar", "كلفن", "درجة الحرارة المقاسة بالكلفن"}
        };

        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("INSERT IGNORE INTO translations (unit_code) VALUES ('C'), ('F'), ('K')");
            }

            String sql = "INSERT INTO unit_translation (unit_id, lang, name, description) " +
                    "SELECT t.id, ?, ?, ? FROM translations t WHERE t.unit_code = ? " +
                    "ON DUPLICATE KEY UPDATE name = VALUES(name), description = VALUES(description)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (String[] row : translations) {
                    preparedStatement.setString(1, row[1]);
                    preparedStatement.setString(2, row[2]);
                    preparedStatement.setString(3, row[3]);
                    preparedStatement.setString(4, row[0]);
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
        }
    }
}
