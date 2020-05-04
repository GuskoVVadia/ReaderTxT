/**
 *  Задача класса:
 *      формирование окна страницы,
 *      считывание данных с fxml
 *      запуск классов, ответственных за наполнение окна приложения
 *      запуск слушателей клавиатуры и размерности окна c небольшой задержкой, организованной через таймер.
 *      Если задача по предыдущему таймеру не выполнена - происходит сброс и передача новый задачи.
 */

package DependencePack;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Controller{

    private static Stage primaryStage;  //контейнер для сцены.
    private static Scene sceneApp;      //контейнер для графических элементов внутри Stage
    private static Point2D dimensionApp;    //массив для инкаплусяции изменяющихся размеров сцены

    private static ArrayList<String> listStringFromFile;
    private static PageComposition pageComposition;     //класс, отвтественный за формирование страницы в окне приложения

    public FlowPane pane;       //панель компоновки из sample.fxml
    public VBox BoxGlyphGroup;  //контейнер, с которым происходит работа приложения, т.е. в него происходит наполнение

    //метод получения основного контейнера и запуск графической платформы Fx.
    public void setStage(Stage stage){
        Controller.primaryStage = stage;
        primaryStage.setTitle("Reader");
        sceneApp = new Scene(pane, 1000.0, 500.0);  //установка начальных габаритов сцены
        primaryStage.setScene(sceneApp);

        //создаём композицию страницы, инициализация класса ответственного за наполнение окна и работу приложения
        pageComposition = new PageComposition(listStringFromFile, BoxGlyphGroup, sceneApp.getWidth(), sceneApp.getHeight());

        //создаём массив и заполняем координаты точки
        BlockingQueue<Point2D> dimensionChangeQueue = new ArrayBlockingQueue<>(1);
        dimensionApp = new Point2D(sceneApp.getWidth(), sceneApp.getHeight());

        //слушатель для изменение размеров окна, изменения инкапсулируются в массиве
        ChangeListener<Number> dimensionChangeListener = new ChangeListener<Number>() {
            final Timer timer = new Timer();
            TimerTask task = null;
            final long delayTime = 1000;

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (task != null) {
                        task.cancel();
                    }
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            dimensionChangeQueue.clear();
                            dimensionChangeQueue.add(new Point2D(sceneApp.getWidth(), sceneApp.getHeight()));
                        }
                    };
                    timer.schedule(task, delayTime);
                }
        };

        //запуск слушателей для размерности окна
        sceneApp.widthProperty().addListener(dimensionChangeListener);
        sceneApp.heightProperty().addListener(dimensionChangeListener);

        //запуск отдельного потока обновления массива
        Thread processDimensionChangeThread = new Thread(() -> {
            while (true) {
                try {
                    Point2D size = dimensionChangeQueue.take();
                    Controller.setDimensionApp(size);
                    pageAlert();

                } catch (InterruptedException letThreadExit) {
                    letThreadExit.printStackTrace();
                }
            }
        });

        processDimensionChangeThread.setDaemon(true);
        processDimensionChangeThread.start();

        //запуск слушателей клавиатуры
        sceneApp.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP){
                    pageComposition.upRow();
                }
                if (event.getCode() == KeyCode.DOWN){
                    pageComposition.downRow();
                }
            }
        });
    }

    //метод передачи обновлённых координат классу страницы
    public void pageAlert(){
        Platform.runLater(() -> {
            Point2D sizesScene = Controller.getDimensionApp();
            pageComposition.setDimensionScene(sizesScene.getY(), sizesScene.getX());
        });
    }

    //отдача координат
    public static Point2D getDimensionApp() {
        return dimensionApp;
    }

    //получение координат
    public static void setDimensionApp(Point2D dimensionApp) {
        Controller.dimensionApp = dimensionApp;
    }

    public void setListStringFromFile(ArrayList<String> listStringFromFile) {
        Controller.listStringFromFile = listStringFromFile;
    }

}
