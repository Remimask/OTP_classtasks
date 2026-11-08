import app.controller.temperatureConverter;
import app.model.TempUnit;
import app.model.TempUnitDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class temperatureConverterTest {
    temperatureConverter t = new temperatureConverter();
    @Test
    void fahrenheitToCelsius() {
        assertEquals(35, t.fahrenheitToCelsius(95));
        assertEquals(37.77777777777778, t.fahrenheitToCelsius(100));
        assertEquals(-17.77777777777778, t.fahrenheitToCelsius(0));
    }
    @Test
    void celsiusToFahrenheit() {
        assertEquals(50, t.celsiusToFahrenheit(10));
        assertEquals(59, t.celsiusToFahrenheit(15));
        assertEquals(84.2, t.celsiusToFahrenheit(29));
    }
    @Test
    void isExtremeTemperature() {
        assertTrue(t.isExtremeTemperature(68));
        assertFalse(t.isExtremeTemperature(15));
        assertTrue(t.isExtremeTemperature(-70));
    }
    @Test
    void kelvinToCelsius() {
        assertEquals(26.850000000000023, t.kelvinToCelsius(300));
    }

    @Test
    void convertUsesCorrectLogicAndFallsBackOnUnknownUnits() {
        assertEquals(0.0, t.convert(32, "F", "C"));
        assertEquals(212.0, t.convert(100, "C", "F"));
        assertEquals(26.850000000000023, t.convert(300, "K", "C"));
        assertEquals(100.0, t.convert(100, "X", "C"));
        assertEquals(100.0, t.convert(100, "C", "X"));
    }
}

class TempUnitDAOTest {
    @BeforeEach
    void setUp() throws Exception {
        //CREATES A TEMPORARY DATABASE INTO MEMORY
        String url = "jdbc:h2:mem:tempunitdao_test;MODE=MySQL;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1";
        TempUnitDAO.setTemp(() -> {
            try {
                return DriverManager.getConnection(url, "sa", "");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        try (Connection connection = DriverManager.getConnection(url, "sa", "")) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DROP TABLE IF EXISTS unit_translation");
                statement.executeUpdate("DROP TABLE IF EXISTS translations");
                statement.executeUpdate("CREATE TABLE translations (id BIGINT AUTO_INCREMENT PRIMARY KEY, unit_code VARCHAR(20) NOT NULL UNIQUE)");
                statement.executeUpdate("CREATE TABLE unit_translation (id BIGINT AUTO_INCREMENT PRIMARY KEY, unit_id BIGINT NOT NULL, lang VARCHAR(20) NOT NULL, name VARCHAR(100) NOT NULL, description VARCHAR(255), UNIQUE (unit_id, lang), CONSTRAINT fk_unit_translation_unit FOREIGN KEY (unit_id) REFERENCES translations(id))");
                statement.executeUpdate("INSERT INTO translations (unit_code) VALUES ('C'), ('F'), ('K')");
                statement.executeUpdate("INSERT INTO unit_translation (unit_id, lang, name, description) VALUES (1, 'en', 'Celsius', 'Temperature measured in degrees Celsius')");
                statement.executeUpdate("INSERT INTO unit_translation (unit_id, lang, name, description) VALUES (2, 'en', 'Fahrenheit', 'Temperature measured in degrees Fahrenheit')");
                statement.executeUpdate("INSERT INTO unit_translation (unit_id, lang, name, description) VALUES (3, 'en', 'Kelvin', 'Temperature measured in Kelvin')");
                statement.executeUpdate("INSERT INTO unit_translation (unit_id, lang, name, description) VALUES (1, 'fi', 'Celsius', 'Lämpötila mitattuna celsiusasteina')");
            }
        }
    }

    @AfterEach
    void tearDown() {
        TempUnitDAO.resetTemp();
    }

    @Test
    void getLocalizedUnitNameUsesEnglishFallback() {
        assertEquals("Celsius", TempUnitDAO.getLocalizedUnitName("C", new Locale("de")));
        assertEquals("Fahrenheit", TempUnitDAO.getLocalizedUnitName("F", new Locale("de")));
        assertEquals("Kelvin", TempUnitDAO.getLocalizedUnitName("K", new Locale("de")));
    }

    @Test
    void getAvailableUnitsReturnsLocalizedDisplayNames() {
        List<TempUnit> units = TempUnitDAO.getAvailableUnits(new Locale("fi"));

        assertEquals(3, units.size());
        assertEquals("Celsius", units.get(0).getDisplayName());
        assertEquals("Fahrenheit", units.get(1).getDisplayName());
        assertEquals("Kelvin", units.get(2).getDisplayName());
        assertFalse(units.stream().anyMatch(unit -> unit.getDisplayName() == null || unit.getDisplayName().isEmpty()));
    }
}