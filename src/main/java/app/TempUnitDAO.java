package app;

import java.util.Arrays;
import java.util.List;

public class TempUnitDAO {
    public static List<TempUnit> getAvailableUnits() {
        return Arrays.asList(
                new TempUnit("C", "Celsius"),
                new TempUnit("F", "Fahrenheit"),
                new TempUnit("K", "Kelvin")
        );
    }
}
