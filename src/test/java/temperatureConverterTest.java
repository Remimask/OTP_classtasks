import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class temperatureConverterTest {
    temperatureConverter t = new temperatureConverter();
    @Test
    void fahrenheitToCelsius(){
        assertEquals(35, t.fahrenheitToCelsius(95));
        assertEquals(37.77777777777778, t.fahrenheitToCelsius(100));
        assertEquals(-17.77777777777778, t.fahrenheitToCelsius(0));
    }
    @Test
    void celsiusToFahrenheit(){
        assertEquals(50, t.celsiusToFahrenheit(10));
        assertEquals(59, t.celsiusToFahrenheit(15));
        assertEquals(84.2, t.celsiusToFahrenheit(29));
    }
    @Test
    void isExtremeTemperature(){
        assertTrue(t.isExtremeTemperature(68));
        assertFalse(t.isExtremeTemperature(15));
        assertTrue(t.isExtremeTemperature(-70));
    }
}