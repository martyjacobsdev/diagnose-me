package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

/**
 * Created by Marty on 26/11/2016.
 */
public class Controller {

    private Stage stage;
    private Model model;
    private View view;


    public Controller(Stage stage, Model model, View view) {
        this.stage = stage;
        this.model = model;
        this.view = view;
    }

    public void initStage() {

        stage.setTitle("diagnoseMe");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 0, 25, 25));

        final Scene scene = new Scene(grid, 320, 350);
        stage.setScene(scene);


        final Text text = new Text(0, 300, "Enter Symptom:");

        final TextField userTextField = new TextField();

        // load the image
        Image image = new Image("logo.jpg");

        ImageView iv2 = new ImageView();
        iv2.setImage(image);
        iv2.setFitWidth(100);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);
        text.setFont(Font.font("Verdana", 20));
        text.setFill(Color.RED);

        grid.add(iv2, 3, 0);
        grid.add(text, 0, 0);
        grid.add(userTextField, 0, 1);

        Button btn = new Button("ok");
        btn.setTextFill(Color.RED);
        btn.setOpacity(0.75);
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 1);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 0, 3);

        actiontarget.setWrappingWidth(150);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);
                String input = String.valueOf(userTextField.getText());
                model.setSymptom(input.toLowerCase());
                List<String> output = model.process();
                actiontarget.setText(" \n " + output.toString()
                );

            }
        });

        userTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    actiontarget.setFill(Color.FIREBRICK);
                    String input = String.valueOf(userTextField.getText());
                    model.setSymptom(input.toLowerCase());
                    List<String> output = model.process();

                    if (output == null) {

                        actiontarget.setText("Sorry! Please try another symptom\n");

                    } else {

                        if (output.get(0) != null && output.get(1) != null && output.get(2) != null)
                            actiontarget.setText("illness it could be: \n \n " + output.get(0) + " \n "
                                    + output.get(1) + " \n " + output.get(2));
                        else if (output.get(0) != null && output.get(1) != null && output.get(2) == null)
                            actiontarget.setText("illness it could be: \n \n " + output.get(0) + " \n "
                                    + output.get(1) + " \n " + "");
                        else if (output.get(0) != null && output.get(1) == null && output.get(2) == null)
                            actiontarget.setText("illness it could be: \n \n " + output.get(0) + " \n " + "" +
                                    " \n " + "");
                        else if (output.get(0) == null && output.get(1) == null && output.get(2) == null)
                            actiontarget.setText("illness it could be: \n \n " + "" + " \n " + "" +
                                    " \n " + "");

                    }

                }
            }
        });


        stage.getIcons().add(new Image(("logo.jpg")));

        stage.show();

    }

}
