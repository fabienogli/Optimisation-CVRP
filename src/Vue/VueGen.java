package Vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VueGen extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vuegen.fxml"));

        // Create a controller instance
        AppGenController controller = new AppGenController();
        // Set it in the FXMLLoader
        loader.setController(controller);
        AnchorPane flowPane = loader.load();
        Scene scene = new Scene(flowPane, 600, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
