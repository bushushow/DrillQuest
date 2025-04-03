import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The Main class provides the entry point for the JavaFX application.
 * This class initializes the JavaFX application and creates an instance of JavaFX Stage (window).
 */
public class Main extends Application {

    /**
     * Initializes the application and creates the primary Stage.
     * @param primaryStage The Stage object to be used as the primary stage.
     */
    @Override
    public void start(Stage primaryStage){
        // Create an instance of SetScene class and perform scene setup.
        SetScene setScene = new SetScene();
        setScene.setting(primaryStage);
    }

    /**
     * The main method to launch the application.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        // Launch the JavaFX application.
        launch(args);
    }
}