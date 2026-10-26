package app.controller;

import app.model.DB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.Locale;

public class controller {
    temperatureConverter temp = new temperatureConverter();
    languageToggle langToggle = new languageToggle(Locale.ENGLISH);
    ToggleGroup tg = new ToggleGroup();
    @FXML
    private RadioButton english;
    @FXML
    private RadioButton finnish;
    @FXML
    private RadioButton swedish;
    @FXML
    private RadioButton japanese;
    @FXML
    private Label header1;
    @FXML
    private Label header2;
    @FXML
    private Label header3;
    @FXML
    private Label header4;
    @FXML
    private TextField fToC;
    @FXML
    private TextField cToF;
    @FXML
    private TextField isExtreme;
    @FXML
    private TextField kToC;
    @FXML
    private Button converter1;
    @FXML
    private Button converter2;
    @FXML
    private Button converter3;
    @FXML
    private Button converter4;
    @FXML
    private Label result1;
    @FXML
    private Label result2;
    @FXML
    private Label result3;
    @FXML
    private Label result4;

    @FXML
    public void initialize() throws SQLException {
//        DB.initializeDatabase();
//        DB.getConnection();
        english.setToggleGroup(tg);
        finnish.setToggleGroup(tg);
        swedish.setToggleGroup(tg);
        japanese.setToggleGroup(tg);
        converter1.setOnAction(event -> {getConversion(1);});
        converter2.setOnAction(event -> {getConversion(2);});
        converter3.setOnAction(event -> {getConversion(3);});
        converter4.setOnAction(event -> {getConversion(4);});
        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton rb = (RadioButton)tg.getSelectedToggle();
                if(rb == english){
                    langToggle.setLocale(Locale.ENGLISH);
                    updateTranslations();
                } else if (rb == finnish){
                    langToggle.setLocale(languageToggle.FINNISH);
                    updateTranslations();
                } else if (rb == swedish){
                    langToggle.setLocale(languageToggle.SWEDISH);
                    updateTranslations();
                } else if (rb == japanese){
                    langToggle.setLocale(Locale.JAPANESE);
                    updateTranslations();
                }else {
                    System.out.println("uhhhhhhhhhhhhhhhh no lang?");
                }
            }
        });
    }
    public void updateTranslations(){
        header1.setText(langToggle.getString("title1"));
        header2.setText(langToggle.getString("title2"));
        header3.setText(langToggle.getString("title3"));
        header4.setText(langToggle.getString("title4"));
        converter1.setText(langToggle.getString("convertBtn"));
        converter2.setText(langToggle.getString("convertBtn"));
        converter3.setText(langToggle.getString("convertBtn"));
        converter4.setText(langToggle.getString("convertBtn"));
    }

    public void getConversion(int nmbr){
        switch (nmbr){
            case 1:
                double value1 = Double.parseDouble(fToC.getText());
                double output1 = temp.convert(value1, "F", "C");
                result1.setText(String.valueOf(output1));
                break;
            case 2:
                double value2 = Double.parseDouble(cToF.getText());
                double output2 = temp.convert(value2, "C", "F");
                result2.setText(String.valueOf(output2));
                break;
            case 3:
                double value3 = Double.parseDouble(isExtreme.getText());
                boolean output3 = temp.isExtremeTemperature(value3);
                result3.setText(String.valueOf(output3));
                break;
            case 4:
                double value4 = Double.parseDouble(kToC.getText());
                double output4 = temp.convert(value4, "K", "C");
                result4.setText(String.valueOf(output4));
                break;
            default:
                System.out.println("lol fuck no");
                break;
        }
    }
}
