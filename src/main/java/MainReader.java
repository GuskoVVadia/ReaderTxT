import DependencePack.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class MainReader extends Application {

    private static Path pathApp;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample.fxml"));
        loader.load();
        Controller controller = loader.getController();
        controller.setFilePathApp(pathApp);
        controller.setStage(primaryStage);
        primaryStage.show();

    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Input a file path : ");
        String inString = in.next();

        pathApp = Paths.get(inString);
        in.close();

        launch(args);
    }

}
