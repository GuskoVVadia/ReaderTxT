/**
 * Задача класса - формирование изображение из классов:
 *  Конструктор принимает имя файла, после чего формируется список считанных данных
 *  Список последовательно передаётся для формирование единиц изображения,
 *  после чего - передаётся отдельному классу - декоратору.
 *  По завершению шагов выше, список передаётся для построения строк.
 *  После завершения формирования строк - класс формирует и отображает в окна приложения страницу
 *  в зависимости от передаваемой ширины окна и высоты.
 *
 *  Как указано в ТЗ - класс формирует список изображения только один раз.
 *  При работе класса не происходит визуального оформления единиц изображения,
 *  а просто добавление их в блок-родитель.
 */

package DependencePack;

import CallPack.GlyphTextComposeCall;
import CallPack.ReadingCall;
import CallPack.RowComposeCall;
import CallPack.TwoWordDecorCall;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PageComposition {

    private static ExecutorService executorService;

    private ArrayList<LinkedList<Canvas>> listRow;  //композиция строк, из чего и формируется страница
    private LinkedList<Glyph> listGlyph;

    private int startViewRow;   //строка с котороый начинается отображение

    private double widthApp;    //значение ширины окна
    private double heightApp;   //значение высоты окна

    private VBox BoxApp;    //блок-родитель - куда именно наполнять своержимое страницы

    /**
     *
     * @param path путь к текстовому файлу
     * @param VBoxApp блок-родитель, куда наполнять страницу
     * @param width требуеммая ширина страницы
     * @param height требуемая высота страницы
     */
    public PageComposition(Path path, VBox VBoxApp, double width, double height){

        try {
            executorService = Executors.newCachedThreadPool();
            Future<LinkedList<String>> fllString = executorService.submit(new ReadingCall(path));
            this.listGlyph = executorService.submit(new GlyphTextComposeCall(fllString.get())).get();

            Future<LinkedList<Glyph>> fTwoDecor = executorService.submit(new TwoWordDecorCall(this.listGlyph));
            fllString = null;
            this.listGlyph = fTwoDecor.get();
            fTwoDecor = null;

            //создание объекта ответственного за формирование строк на странице
            this.startViewRow = 0;
            this.heightApp = height;
            this.widthApp = width;

            this.listRow = executorService.submit(new RowComposeCall(this.listGlyph, this.widthApp)).get();
            this.BoxApp = VBoxApp;
            this.viewPage(width, height);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Метод отображения страницы в окне приложения
     * @param width требуемая ширина страницы.
     * @param height требуемая высота страницы
     */
    public void viewPage(double width, double height){

        if(width <= 125){
            this.widthApp = 100.0;
        }
            if (this.widthApp != width) {
                this.widthApp = width;

                try {
                    this.listRow = executorService.submit(new RowComposeCall(this.listGlyph, this.widthApp)).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

        this.heightApp = height;

        double heightSum = 0;
        int i = this.startViewRow;
        VBox vBox = new VBox();

        while (heightSum < this.heightApp){

            if (i == listRow.size()){
                break;
            }
                LinkedList<Canvas> row = this.listRow.get(i);
                double x = row.peekFirst().getHeight();
                for (Canvas canvas : row) {
                    x = Math.max(x, canvas.getHeight());
                }

                heightSum += x;

                if (heightSum <= this.heightApp) {
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.BOTTOM_LEFT);
                    hBox.getChildren().addAll(row);
                    vBox.getChildren().add(hBox);
                }
                i++;
        }
            if (BoxApp.getChildren().size() != 0) {
                BoxApp.getChildren().clear();
            }
            BoxApp.getChildren().add(vBox);
    }

    /**
     * метод указывающий странице сместиться на строку вверх, передаёт самой странице номер начальной строки отображения
     * содержимого
     */
    public void upRow(){
        if (this.startViewRow > 0){
            this.startViewRow -= 1;
        }
        this.viewPage(this.widthApp, this.heightApp);
    }

    /**
     * метод, указывающий странице сместиться на строку вниз.
     */
    public void downRow(){
        if (this.startViewRow < this.listRow.size()) {
            this.startViewRow += 1;
        }
        this.viewPage(this.widthApp, this.heightApp);
    }
}
