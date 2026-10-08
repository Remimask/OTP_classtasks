package app;

public class temperatureConverter {
    public temperatureConverter() {
    }

    public double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    public double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    public boolean isExtremeTemperature(double celsius) {
        return celsius > 50 || celsius < -40;
    }

    public double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    public double convert(double value, String fromUnit, String toUnit) {
        double celsiusValue = toCelsius(value, fromUnit);
        return fromCelsius(celsiusValue, toUnit);
    }

    private double toCelsius(double value, String unit) {
        switch (unit.toUpperCase()) {
            case "C":
            case "CELSIUS":
                return value;
            case "F":
            case "FAHRENHEIT":
                return fahrenheitToCelsius(value);
            case "K":
            case "KELVIN":
                return kelvinToCelsius(value);
            default:
                System.out.println("GRRR");
                break;
        }
        return value;
    }

    private double fromCelsius(double celsiusValue, String unit) {
        switch (unit.toUpperCase()) {
            case "C":
            case "CELSIUS":
                return celsiusValue;
            case "F":
            case "FAHRENHEIT":
                return celsiusToFahrenheit(celsiusValue);
            case "K":
            case "KELVIN":
                return celsiusValue + 273.15;
            default:
                System.out.println("GRRR");
        }
        return celsiusValue;
    }
}
