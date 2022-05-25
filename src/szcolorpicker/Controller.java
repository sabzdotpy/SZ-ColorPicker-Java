package szcolorpicker;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.beans.value.ObservableValue;

import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

@SuppressWarnings("ALL")
public class Controller implements Initializable {

//    private String text_in_display;

    private int processes;

    private Random random = new Random();

    private String hexString;

    @FXML
    Button close;
    @FXML
    Button minimize;

    @FXML
    GridPane root;

    @FXML
    AnchorPane titlebar;
    @FXML
    AnchorPane base;
    @FXML
    AnchorPane slider_base;
    @FXML
    AnchorPane input_base;

    @FXML
    Slider red_slider;
    @FXML
    Slider green_slider;
    @FXML
    Slider blue_slider;

    @FXML
    TextField rgb_input;
    @FXML
    TextField hsl_input;
    @FXML
    TextField hex_input;

    private double red_val;
    private double green_val;
    private double blue_val;

    @FXML
    private void closeWindow(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    private void minimizeWindow(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).setIconified(true);
    }


    private void valueChanged(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
        if (processes == 0) {
            processes++;
            red_val = red_slider.getValue();
            green_val = green_slider.getValue();
            blue_val = blue_slider.getValue();

            slider_base.setStyle("-fx-background-color: rgb(" + red_val + ", " + green_val + "," + blue_val + ");" );

            rgb_input.setText( (int)red_val + ", " + (int)green_val + ", " + (int)blue_val );
            Color color = new Color((int)red_val, (int)green_val, (int)blue_val);

            hexString = Integer.toHexString(color.getRGB());
            System.out.println(hexString);
            hex_input.setText("#" + hexString.substring(2, hexString.length()));

            processes--;
            hexString = "";
        }
    }

    private void textChangedRGB(ObservableValue<? extends String> observableValue, String oldValue, String newValue ) {
        if (processes == 0) {
            processes++;
            try {
                newValue = newValue.replaceAll(" ", "");
                String[] textinfield = newValue.split(",");

                textinfield[0] = textinfield[0].replace("(", "").replace("[", "");
                textinfield[textinfield.length - 1] = textinfield[textinfield.length - 1].replace(")", "").replace("]", "");

                System.out.println(Arrays.toString(textinfield));

                red_slider.setValue(Integer.parseInt(textinfield[0]));
                green_slider.setValue(Integer.parseInt(textinfield[1]));
                blue_slider.setValue(Integer.parseInt(textinfield[2]));

                slider_base.setStyle("-fx-background-color: rgb(" + textinfield[0] + ", " + textinfield[1] + "," + textinfield[2] + ");" );

                Color color = new Color(Integer.parseInt(textinfield[0]), Integer.parseInt(textinfield[1]), Integer.parseInt(textinfield[2]));

                hexString = Integer.toHexString(color.getRGB());
                System.out.println(hexString);
                hex_input.setText("#" + hexString.substring(2, hexString.length()));

            }
            catch(Exception e) {
                System.out.println(e);
            }
            processes--;
            hexString = "";
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("App initialize");

        processes = 0;

        int init_red = random.nextInt(256);
        int init_green = random.nextInt(256);
        int init_blue = random.nextInt(256);

        slider_base.setStyle("-fx-background-color: rgb(" + init_red + ", " + init_green + "," + init_blue + ");" );

        red_slider.setValue(init_red);
        green_slider.setValue(init_green);
        blue_slider.setValue(init_blue);

        rgb_input.setText( (int)init_red + ", " + (int)init_green + ", " + (int)init_blue );

        red_slider.valueProperty().addListener(this::valueChanged);
        green_slider.valueProperty().addListener(this::valueChanged);
        blue_slider.valueProperty().addListener(this::valueChanged);

        rgb_input.textProperty().addListener(this::textChangedRGB);

        System.out.println("...");

    }
}