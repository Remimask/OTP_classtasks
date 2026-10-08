package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import javax.swing.text.TabableView;
import java.awt.*;

public class App extends Application {

    private TextField fToC;
    private TextField cToF;
    private TextField iExtrTemp;
    private TextField kToC;

    //private TabableView<>

    public void start(Stage stage){
        stage.setTitle("Temperatures or something");
        GridPane screen = new GridPane();
        screen.setHgap(10);
        screen.setVgap(10);
        //screen.setPadding(new Insets(15));
        //GUI UNDER CONSTRUCTION

        fToC = new TextField();
        fToC.setText("Fahrenheit to Celsius");
        cToF = new TextField();
        cToF.setText("Celsius to Fahrenheit");
        iExtrTemp = new TextField();
        iExtrTemp.setText("Is extreme?");
        kToC = new TextField();
        kToC.setText("Kelvin to Celsius");

        screen.add(new Label("F to C"), 0,0);
        //PLEASE BE PATIENT.

        stage.setScene(new Scene(screen));
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
