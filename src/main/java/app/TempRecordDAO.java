package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TempRecordDAO {
    public static void createTableIfNotExists() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS temperature_records (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "input_value DOUBLE NOT NULL, " +
                "input_unit VARCHAR(20) NOT NULL, " +
                "output_value DOUBLE NOT NULL, " +
                "output_unit VARCHAR(20) NOT NULL, " +
                "converted_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)";

        try (Connection connection = DB.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    public static void saveConversion(double inputValue, String inputUnit, double outputValue, String outputUnit) throws SQLException {
        String sql = "INSERT INTO temperature_records (input_value, input_unit, output_value, output_unit, converted_at) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DB.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, inputValue);
            preparedStatement.setString(2, inputUnit);
            preparedStatement.setDouble(3, outputValue);
            preparedStatement.setString(4, outputUnit);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.executeUpdate();
        }
    }

    public static List<TempRecord> getAllConversions() throws SQLException {
        String sql = "SELECT * FROM temperature_records ORDER BY converted_at DESC";
        List<TempRecord> records = new ArrayList<TempRecord>();

        try (Connection connection = DB.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                TempRecord tempRecord = new TempRecord();
                tempRecord.setId(resultSet.getLong("id"));
                tempRecord.setInputValue(resultSet.getDouble("input_value"));
                tempRecord.setInputUnit(resultSet.getString("input_unit"));
                tempRecord.setOutputValue(resultSet.getDouble("output_value"));
                tempRecord.setOutputUnit(resultSet.getString("output_unit"));
                records.add(tempRecord);
            }
        }

        return records;
    }
}
