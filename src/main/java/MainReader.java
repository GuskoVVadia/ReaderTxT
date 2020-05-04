/**
 * Задача класса:
 *  получить путь к файлу, который нужно считать и передать его в контроллер,
 *  другими словами дальше по ходу алгоритма.
 *  загрузить ресурсы страницы из сформированного отдельного файла sample.fxml
 */

import DependencePack.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class MainReader extends Application {
    //текущая значение ошибки
    private static TypeErrorReading currentStateError;

    //строка, несущая коментарий к ошибке
    private static String commitReadFile;
    //list полученных значений из файла
    private static ArrayList<String> arrayListFromFile;

    //здесь и определяется графический интерфейс
    @Override
    public void start(Stage primaryStage) throws Exception{

        //если ошибок нет - загружаем далее приложение
        //если ошибки есть - выводим окно с уведомлением и ошибкой, также в случае непредвиденной ошибки чтения
        //выводиться трассировка.

        if (currentStateError.equals(TypeErrorReading.NE)){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample.fxml"));
            loader.load();
            Controller controller = loader.getController();
            controller.setListStringFromFile(arrayListFromFile);
            controller.setStage(primaryStage);
            primaryStage.show();

        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File error");
            VBox vBoxInAlert = new VBox();
            Label labelNameError = new Label(currentStateError.getStateError());
            alert.getDialogPane().setHeader(labelNameError);
            TextArea textArea = new TextArea(commitReadFile);
            vBoxInAlert.getChildren().addAll(textArea);
            alert.getDialogPane().setContent(vBoxInAlert);
            alert.showAndWait();
        }
    }

    public static void main(String[] args){

        //если имя файла не указано
        if (args.length != 0) {
            String inString = args[0] + ".txt";
            //вычисляем путь к файлу, считая, что он находиться в папке запуска приложения
            Path calculatedPath = FileSystems.getDefault().getPath(inString).toAbsolutePath();

            //файл по указанному пути существует?
            if (Files.exists(calculatedPath)) {
                //можем ли открыть для чтения этот файл?
                if (Files.isReadable(calculatedPath)) {
                    try {
                        arrayListFromFile = new UnitReadingFile().getList(calculatedPath);
                        currentStateError = TypeErrorReading.NE;
                    } catch (IOException e) {
                        currentStateError = TypeErrorReading.ER;
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        e.printStackTrace(pw);
                        commitReadFile = sw.toString();
                    }

                } else {
                    currentStateError = TypeErrorReading.NR;
                    commitReadFile = "Error reading file";
                }
            } else {
                currentStateError = TypeErrorReading.NF;
                commitReadFile = "File not found";
            }
        }else {
            currentStateError = TypeErrorReading.ENF;
            commitReadFile = "Получить имя файла не удалось";
        }

        launch(args);
    }
}
