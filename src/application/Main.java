package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by Marty on 26/11/2016.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        //Model
        Model model = new Model();

        //View
        View view = new View();

        //Controller
        Controller controller = new Controller(stage, model, view);

        //set the stage
        controller.initStage();
    }

    public static void main(String[] args) {

        /* This is required to silence JavaFX known error:   */
        /* https://bugs.openjdk.java.net/browse/JDK-8143907 */
        try {
            Class<?> macFontFinderClass = Class.forName("com.sun.t2k.MacFontFinder");
            Field psNameToPathMap = macFontFinderClass.getDeclaredField("psNameToPathMap");
            psNameToPathMap.setAccessible(true);
            psNameToPathMap.set(null, new HashMap<String, String>());
        } catch (Exception e) {
            // ignore
        }

        Application.launch(Main.class, args);

    }


}
