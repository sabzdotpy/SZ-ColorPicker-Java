package szcolorpicker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.beans.value.ObservableValue;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

@SuppressWarnings("ALL")
public class Controller implements Initializable {

    private int processes = 0;
    private Random random = new Random();
    Color colorpicker_color;
    Stage stage;

    double oldX;
    double oldY;

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

    @FXML
    ColorPicker colorpicker;

    @FXML
    Label link;

    private int red_val;
    private int green_val;
    private int blue_val;

    @FXML
    private void titleBarPressed(MouseEvent event) {
        System.out.println("title press");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        oldX = stage.getX()- event.getScreenX();
        oldY = stage.getY()- event.getScreenY();
    }

    @FXML
    private void titleBarDragged(MouseEvent event) {
        System.out.println("title drag");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setX(oldX + event.getScreenX());
        stage.setY(oldY + event.getScreenY());
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    private void minimizeWindow(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).setIconified(true);
    }

    @FXML void openURL() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.github.com/sabzdotpy"));
    }

    private void valueChanged(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
        if (processes == 0) {
            processes++;
            red_val = (int)(red_slider.getValue());
            green_val = (int)(green_slider.getValue());
            blue_val = (int)(blue_slider.getValue());

            slider_base.setStyle("-fx-background-color: rgb(" + red_val + ", " + green_val + "," + blue_val + ");" );


            rgb_input.setText( red_val + ", " + green_val + ", " + blue_val );
            hsl_input.setText(Conversion.rgbToHsv(red_val, green_val, blue_val));
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
                hsl_input.setText(Conversion.rgbToHsv(text_int[0], text_int[1], text_int[2]));

            }
            catch(Exception e) {
                System.out.println(e);
            }

            processes--;

        }

    }

    private void textChangedHSV(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if (processes == 0) {
            processes++;
            try {
                newValue = newValue.replaceAll(" ", "");
                String[] textinfield = newValue.replace("(", "").replace(")", "").split(",");

                String rgbStore = Conversion.hsvToRGB( Integer.parseInt(textinfield[0]), Integer.parseInt(textinfield[1]), Integer.parseInt(textinfield[2]));

                hex_input.setText("#" + Conversion.hsvToHEX( Integer.parseInt(textinfield[0]), Integer.parseInt(textinfield[1]), Integer.parseInt(textinfield[2])).substring(2,8));

                rgb_input.setText(rgbStore);
                slider_base.setStyle("-fx-background-color: rgb(" + rgbStore + ");");
                red_slider.setValue( Integer.parseInt(rgbStore.split(",")[0]) );
                green_slider.setValue( Integer.parseInt(rgbStore.split(",")[1]) );
                blue_slider.setValue( Integer.parseInt(rgbStore.split(",")[2]) );

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
                hsl_input.setText(Conversion.hexToHSV(newValue));
                slider_base.setStyle("-fx-background-color: #" + newValue + ";");

            }
            catch (Exception e) {
                System.out.print("");
            }

            processes--;
        }
    }

    public void enterPressedHEX(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) && processes == 0) {
                processes++;
                try {
                    String newValue = hex_input.getText().replace("#", "");

                    if (newValue.length() == 3) {
                        newValue = newValue + newValue;

                        String rgbValue = Conversion.hexToRGB(newValue);
                        rgb_input.setText(rgbValue);
                        hsl_input.setText(Conversion.hexToHSV(newValue));
                        slider_base.setStyle("-fx-background-color: #" + newValue + ";");
                        red_slider.setValue( Integer.parseInt(rgbValue.split(",")[0]) );
                        green_slider.setValue( Integer.parseInt(rgbValue.split(",")[1]) );
                        blue_slider.setValue( Integer.parseInt(rgbValue.split(",")[2]) );
                        hex_input.setText("#" + newValue);
                    }

                }
                catch (Exception e) {
                    System.out.print("");
                }

                processes--;
        }
    }

    public void colorPickedHandle(ActionEvent t) {
        if (processes == 0) {
            processes++;

            colorpicker_color = colorpicker.getValue();

            rgb_input.setText((int)(colorpicker_color.getRed()*255) + "," + (int)(colorpicker_color.getGreen() * 255) + "," + (int)(colorpicker_color.getBlue() * 255));
            hsl_input.setText((int)(colorpicker_color.getHue()*360) + "," + (int)(colorpicker_color.getSaturation() * 100) + "," + (int)(colorpicker_color.getBrightness() * 100));
            hex_input.setText("#" + colorpicker_color.toString().substring(2, 8));

            red_slider.setValue((int)(colorpicker_color.getRed()*255));
            green_slider.setValue((int)(colorpicker_color.getGreen()*255));
            blue_slider.setValue((int)(colorpicker_color.getBlue()*255));

            slider_base.setStyle("-fx-background-color: #" + colorpicker_color.toString().substring(2, 8) + ";");

            processes--;
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("App initialize");

        int init_red = random.nextInt(256);
        int init_green = random.nextInt(256);
        int init_blue = random.nextInt(256);

        red_slider.valueProperty().addListener(this::valueChanged);
        green_slider.valueProperty().addListener(this::valueChanged);
        blue_slider.valueProperty().addListener(this::valueChanged);

        rgb_input.textProperty().addListener(this::textChangedRGB);
        hsl_input.textProperty().addListener(this::textChangedHSV);
        hex_input.textProperty().addListener(this::textChangedHEX);
        hex_input.setOnKeyPressed(this::enterPressedHEX);

        colorpicker.setOnAction(this::colorPickedHandle);

        slider_base.setStyle("-fx-background-color: rgb(" + init_red + ", " + init_green + "," + init_blue + ");" );

        red_slider.setValue(init_red);
        green_slider.setValue(init_green);
        blue_slider.setValue(init_blue);

        rgb_input.setText( init_red + ", " + init_green + ", " + init_blue );
        hsl_input.setText(Conversion.rgbToHsv(init_red, init_green, init_blue));
        hex_input.setText("#" + Conversion.rgbToHex( init_red, init_green, init_blue ).substring(2, 8));

        colorpicker.setValue(Color.rgb(init_red, init_green, init_blue));

        System.out.println("...");

    }
}