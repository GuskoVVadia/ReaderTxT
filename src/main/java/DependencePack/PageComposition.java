package DependencePack;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;

public class PageComposition {

    private RowGlyphCompositor rowCompositor;   //класс, формирующий страницы

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


        ArrayList<String> startListString = new UnitReadingFile().getSymbolComposition(path);
        ArrayList<GlyphText> arrayListGlyph = new ArrayList<>(startListString.size());

        GlyphTextCompositor gtCompositor = new GlyphTextCompositor();
        gtCompositor.compose(arrayListGlyph, startListString);
        clearListString(startListString);
        gtCompositor = null;

        //Декорирование глифов соответственно ТЗ;
        //Создаём класс, ответственный за поиск двух слов в списке и их декорирование
        new TwoWordDecorator(3.0).decorated(arrayListGlyph);

        //создание объекта ответственного за формирование строк на странице
        this.startViewRow = 0;
        this.heightApp = height;
        this.widthApp = width;
        this.rowCompositor = new RowGlyphCompositor(arrayListGlyph, width);

        this.BoxApp = VBoxApp;
        this.viewPage(width, height);
    }

    /**
     * Метод отображения страницы в окне приложения
     * @param width требуемая ширина страницы.
     * @param height требуемая высота страницы
     */
    public void viewPage(double width, double height){

        ArrayList<HBox> arrayListHbox = new ArrayList<>();
        double heightTemp = 0;

        for (int i = startViewRow; heightTemp < height; i++) {

            LinkedList<Canvas> listCanvas = new LinkedList<>();
            if (!this.rowCompositor.getRow(i, listCanvas)){
                break;
            }
            double max = 0;

            for (int j = 0; j <listCanvas.size() ; j++) {
                max = Math.max(max, listCanvas.get(j).getHeight());
            }

            heightTemp += max;
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.BOTTOM_LEFT);
            hBox.getChildren().addAll(listCanvas);
            arrayListHbox.add(hBox);
        }

        if (BoxApp.getChildren().size() > 0){
            BoxApp.getChildren().clear();
        }
        BoxApp.getChildren().addAll(arrayListHbox);
        arrayListHbox.clear();
    }

    public void upRow(){
        if (this.startViewRow > 0){
            this.startViewRow -= 1;
        }
        this.viewPage(this.widthApp, this.heightApp);
    }

    public void downRow(){
        if (this.startViewRow < this.rowCompositor.getLength() - 1) {
            this.startViewRow += 1;
        }
        this.viewPage(this.widthApp, this.heightApp);
    }

    private void clearListString(ArrayList<String> listString){
        for (int i = 0; i < listString.size(); i++) {
            listString.set(i, null);
        }
        listString.clear();
        listString = null;
    }

    public void setDimensionScene(double height, double width){
        this.heightApp = height;
        this.widthApp = width;
        this.rowCompositor.setWidthPage(width);
        viewPage(this.widthApp, this.heightApp);
    }
}
