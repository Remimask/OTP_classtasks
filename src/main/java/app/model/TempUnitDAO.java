package app.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TempUnitDAO {
    @FunctionalInterface
    public interface ConnectionProvider {
        Connection get() throws SQLException;
    }

    private static ConnectionProvider connectionSupplier = DB::getConnection;

    public static void setTemp(ConnectionProvider supplier) {
        connectionSupplier = supplier == null ? DB::getConnection : supplier;
    }

    public static void resetTemp() {
        connectionSupplier = DB::getConnection;
    }

    public static List<TempUnit> getAvailableUnits() {
        return getAvailableUnits(Locale.ENGLISH);
    }

    public static List<TempUnit> getAvailableUnits(Locale locale) {
        String sql = "SELECT t.unit_code, COALESCE(ut.name, en.name) AS display_name " +
                "FROM translations t " +
                "LEFT JOIN unit_translation ut ON ut.unit_id = t.id AND ut.lang = ? " +
                "LEFT JOIN unit_translation en ON en.unit_id = t.id AND en.lang = 'en' " +
                "ORDER BY t.id";

        try (Connection connection = connectionSupplier.get();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, locale.getLanguage());

            try (ResultSet resultSet = statement.executeQuery()) {
                List<TempUnit> units = new ArrayList<>();
                while (resultSet.next()) {
                    units.add(new TempUnit(
                            resultSet.getString("unit_code"),
                            resultSet.getString("display_name")
                    ));
                }
                if (!units.isEmpty()) {
                    return units;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Arrays.asList(
                new TempUnit("C", "Celsius"),
                new TempUnit("F", "Fahrenheit"),
                new TempUnit("K", "Kelvin")
        );
    }

    public static String getLocalizedUnitName(String unitCode, Locale locale) {
        String sql = "SELECT COALESCE(ut.name, en.name) AS display_name " +
                "FROM translations t " +
                "LEFT JOIN unit_translation ut ON ut.unit_id = t.id AND ut.lang = ? " +
                "LEFT JOIN unit_translation en ON en.unit_id = t.id AND en.lang = 'en' " +
                "WHERE t.unit_code = ?";

        try (Connection connection = connectionSupplier.get();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, locale.getLanguage());
            statement.setString(2, unitCode);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("display_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String normalized = unitCode == null ? "" : unitCode.toUpperCase();
        if ("C".equals(normalized)) {
            return "Celsius";
        }
        if ("F".equals(normalized)) {
            return "Fahrenheit";
        }
        if ("K".equals(normalized)) {
            return "Kelvin";
        }
        return unitCode;
    }
}
