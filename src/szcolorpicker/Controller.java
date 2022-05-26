package szcolorpicker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.beans.value.ObservableValue;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

@SuppressWarnings("ALL")
public class Controller implements Initializable {

//    private String text_in_display;

    private int processes;

    private Random random = new Random();


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

    private int red_val;
    private int green_val;
    private int blue_val;

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
            red_val = (int)(red_slider.getValue());
            green_val = (int)(green_slider.getValue());
            blue_val = (int)(blue_slider.getValue());

            slider_base.setStyle("-fx-background-color: rgb(" + red_val + ", " + green_val + "," + blue_val + ");" );


            rgb_input.setText( red_val + ", " + green_val + ", " + blue_val );
            hsl_input.setText(Conversion.rgbToHsb(red_val, green_val, blue_val));
            hex_input.setText("#" + Conversion.rgbToHex(red_val, green_val, blue_val).substring(2, 8));

            processes--;
        }
    }

    private void textChangedRGB(ObservableValue<? extends String> observableValue, String oldValue, String newValue ) {
        if (processes == 0) {
            processes++;
            try {
                newValue = newValue.replaceAll(" ", "");
                String[] textinfield = newValue.split(",");
                int[] text_int = new int[textinfield.length];

                textinfield[0] = textinfield[0].replace("(", "").replace("[", "");
                textinfield[textinfield.length - 1] = textinfield[textinfield.length - 1].replace(")", "").replace("]", "");


                for (int i = 0; i < textinfield.length; i++) {
                    text_int[i] = Integer.parseInt(textinfield[i]);
                }

                red_slider.setValue(Integer.parseInt(textinfield[0]));
                green_slider.setValue(Integer.parseInt(textinfield[1]));
                blue_slider.setValue(Integer.parseInt(textinfield[2]));

                slider_base.setStyle("-fx-background-color: rgb(" + textinfield[0] + ", " + textinfield[1] + "," + textinfield[2] + ");" );

                hex_input.setText("#" + Conversion.rgbToHex(text_int[0], text_int[1], text_int[2]).substring(2, 8));
                hsl_input.setText(Conversion.rgbToHsb(text_int[0], text_int[1], text_int[2]));

            }
            catch(Exception e) {
                System.out.println(e);
            }

            processes--;

        }

    }

    private void textChangedHSB(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if (processes == 0) {
            processes++;
            try {
                System.out.println(newValue);
                String[] textinfield = newValue.replace("(", "").replace(")", "").split(",");
                Conversion.hsbToRGB( Integer.parseInt(textinfield[0]), Integer.parseInt(textinfield[1]), Integer.parseInt(textinfield[2]) );

            }
            catch (Exception e) {
                System.out.println(e);
            }

            processes--;
        }
    }

    private void textChangedHEX(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if (processes == 0) {
            processes++;
            try {
                newValue = newValue.replace("#", "");

                rgb_input.setText(Conversion.hexToRGB(newValue));
                hsl_input.setText(Conversion.hexToHSB(newValue));
            }
            catch (Exception e) {
                System.out.print("");
            }

            processes--;
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
        hsl_input.textProperty().addListener(this::textChangedHSB);
        hex_input.textProperty().addListener(this::textChangedHEX);

        System.out.println("...");

    }
}