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

    public static void main(String[] args){
        System.out.println("Hi.");
        temperatureConverter converter = new temperatureConverter();
        System.out.println(converter.celsiusToFahrenheit(14.7));
        System.out.println(converter.fahrenheitToCelsius(89.9));
        System.out.println(converter.isExtremeTemperature(58));
        System.out.println(converter.kelvinToCelsius(320));
    }
}
