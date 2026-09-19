public class temperatureConverter {
    public temperatureConverter(){}

    public double fahrenheitToCelsius(double fahrenheit){
        return (fahrenheit - 32) * 5 / 9;
    }
    public double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }
    public boolean isExtremeTemperature(double celsius){
        return celsius > 50 || celsius < -40;
    }
    public double kelvinToCelsius(double kelvin){
        return kelvin - 273.15;
    };
}
