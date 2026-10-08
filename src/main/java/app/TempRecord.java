package app;

public class TempRecord {
    private long id;
    private double inputValue;
    private String inputUnit;
    private double outputValue;
    private String outputUnit;

    public TempRecord() {
    }

    public TempRecord(long id, double inputValue, String inputUnit, double outputValue, String outputUnit) {
        this.id = id;
        this.inputValue = inputValue;
        this.inputUnit = inputUnit;
        this.outputValue = outputValue;
        this.outputUnit = outputUnit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getInputValue() {
        return inputValue;
    }

    public void setInputValue(double inputValue) {
        this.inputValue = inputValue;
    }

    public String getInputUnit() {
        return inputUnit;
    }

    public void setInputUnit(String inputUnit) {
        this.inputUnit = inputUnit;
    }

    public double getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(double outputValue) {
        this.outputValue = outputValue;
    }

    public String getOutputUnit() {
        return outputUnit;
    }

    public void setOutputUnit(String outputUnit) {
        this.outputUnit = outputUnit;
    }



    @Override
    public String toString() {
        return inputValue + " " + inputUnit + " -> " + outputValue + " " + outputUnit;
    }
}
